package com.example.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myproject.dto.AccountDTO;
import com.example.myproject.service.AccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    // 회원가입 폼
    @GetMapping("/register")
    public void registerForm() {}
   
    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute AccountDTO accountDTO) {
        accountService.register(accountDTO);
        return "redirect:/auth/login";
    }

}
