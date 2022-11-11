package com.sbboard.user;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    // * 회원가입
    @GetMapping("/user/signup")
    public String signup(UserDto userDto) {
        System.out.println("회원가입 진입");
        return "user/signup_form";
    }

    @PostMapping("/user/signup")
    public String signup(@Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signup_form";
        }

        // * 아이디 중복체크
        int isExist = userService.isExist(userDto.getUser_id());
        if (isExist == 1) {
            bindingResult.reject("signupFailed", "이미 등록된 아이디입니다.");
            return "user/signup_form";
        }

        // * 회원가입
        int isCreate = userService.createUser(userDto);
        if (isCreate == 1) {
            return "redirect:/login";
        } else {
            return "user/signup_form";
        }
    }

    // * 로그인
    @GetMapping("/user/login")
    public String loginView(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        System.out.println(session);
        return "user/login_form";
    }

    @PostMapping("/user/login")
    public String loginPost(UserDto userDto, HttpSession session) {
        int isUser = userService.userCheck(userDto);
        if (isUser == 1) {
            session.setAttribute("id", userDto.getUser_id());
            return "redirect:/board/board";
        } else {
            return "redirect:user/login_form";
        }
    }

}
