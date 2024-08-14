package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class MemberController {
//    @Autowired
//    private MemberService memberService;

    private final MemberService memberService;

    //    @Autowired : 생략가능
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password, HttpServletResponse resp) {
        if (username.isEmpty()) {
            return RsData.of("F-3", "username(을)를 입력해주세요");
        }
        if (password.trim().isEmpty()) {
            return RsData.of("F-4", "password(을)를 입력해주세요");
        }

        RsData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            long memberId = (long) rsData.getData();
            resp.addCookie(new Cookie("loginMemberId", memberId + ""));
        }

        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginMemberId"))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }

        return RsData.of("S-1", "로그아웃 되었습니다");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req) {
        long loginMemberId = 0;

        if (req.getCookies() != null) {
            loginMemberId= Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginMemberId"))
                    .map(cookie -> cookie.getValue())
                    .mapToLong(Long::parseLong)
                    .findFirst()
                    .orElse(0);
        }

        boolean isLogin = loginMemberId > 0;

        if (!isLogin) {
            return RsData.of("F-1", "로그인 후 이용해주세요");
        }

        Member member = memberService.findById(loginMemberId);
        return RsData.of("S-1", "당신의 username(은) %s 입니다".formatted(member.getUsername()));
    }
}
