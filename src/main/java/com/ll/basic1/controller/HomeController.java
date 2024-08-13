package com.ll.basic1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller : 개발자가 이것은 컨트롤러라고 말하는 것.

@Controller
public class HomeController {
//    @GetMapping("url") : 만약에 "url" 페이지에 들어오면 아래의 메서드를 시켜줘
//    @ResponseBody : 아래 메서드를 실행한 후 그 리턴값을 응답으로 삼아서 body에 출력해줘
    @GetMapping("/home/main")
    @ResponseBody
    public String showHome() {
        return "안녕하세요";
    }
}
