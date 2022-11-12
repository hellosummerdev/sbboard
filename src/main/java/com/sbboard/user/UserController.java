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
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // * 회원가입
    @GetMapping("/signup")
    public String signup(UserDto userDto) {
        System.out.println("회원가입 진입");
        return "user/signup_form";
    }

    @PostMapping("/signup")
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
    @GetMapping("/login")
    public String login() {
        return "user/login_form";
    }

    @PostMapping("/login")
    public String loginPost(UserDto userDto, HttpServletRequest request) {
        int isUser = userService.userCheck(userDto);
        if (isUser == 1) {
//          HttpSession이 존재하면 현재 HttpSession을 반환하고
//          존재하지 않으면 새로이 세션을 생성
            HttpSession session = request.getSession(true);
            System.out.println("###### " + session + " #######");
            session.setAttribute("userDto", userDto);
            session.setAttribute("user_name", userDto.getUser_name());
            return "redirect:/board/board";
        } else {
            return "user/login_form";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        System.out.println("로그아웃 진입");
//        HttpSession이 존재하면 현재 HttpSession을 반환하고
//        존재하지 않으면 새로이 생성하지 않고 그냥 null을 반환
        HttpSession session = request.getSession(false);
        System.out.println("###### " + session + " #######");
        if (session != null) {
            System.out.println("session을 삭제합니다.");
            session.invalidate();
        }
        return "redirect:/board/board";
    }
}
