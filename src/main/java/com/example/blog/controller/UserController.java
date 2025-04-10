package com.example.blog.controller;

import com.example.blog.dto.AddUserRequest;
import com.example.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;
    UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 /user 사용자 정보 저장, 로그인화면으로 연결
    @PostMapping("/user")
    public String signup(@ModelAttribute AddUserRequest request){
        userService.signup(request);
        return "redirect:/login";
    }

    @PostMapping("/logoutPage")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        new SecurityContextLogoutHandler().logout(request,response,authentication);
        return "redirect:/login";
    }
}
