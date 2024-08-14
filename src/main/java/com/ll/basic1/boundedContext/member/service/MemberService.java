package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// 아래 클래스는 IOC 컨테이너에 의해 생사소멸이 관리된다.
//@Component @Service 는 동일한 의미, 가독성 때문에 이렇게 표
@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = new MemberRepository();
//    }  롬복으로 생성가능

    public RsData tryLogin(String username, String password) {
        Member member = memberRepository.findByUserName(username);
        if (member == null) {
            return RsData.of("F-2", "%s는 존재하지 않는 회원입니다.".formatted(username));
        }

        if (!member.getPassword().equals(password)) {
            return RsData.of("F-1", "비밀번호가 일치하지 않습니다");
        }

        return RsData.of("S-1", "%s님 환영합니다.".formatted(username), member.getId());
    }

    public Member findById(long id) {
        return memberRepository.findById(id);
    }
}
