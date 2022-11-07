package com.sbboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요, Spring Boot Board로 오신 것을 환영합니다";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:board/board";
    }
}
