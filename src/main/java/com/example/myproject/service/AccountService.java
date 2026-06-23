package com.example.myproject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myproject.common.AccountRole;
import com.example.myproject.dto.AccountDTO;
import com.example.myproject.entity.Account;
import com.example.myproject.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final   AccountRepository accountRepository;
    // SecurityConfig에 생성된 passwordEncoder 객체 주입
    private final PasswordEncoder passwordEncoder;

    // 관리자 계정 생성 메소드
    public void createAdmin() {
        // admin 계정이 존재할 경우
        if(accountRepository.findByUsername("admin").isPresent()){
            return; // 메소드 종료
        }

        Account admin = Account.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("1234"))
                        .name("관리자")
                        .email("admin@test.com")
                        .nickname("관리자")
                        .role(AccountRole.ROLE_ADMIN)
                        .build();
        accountRepository.save(admin);                
    }

    public int register(AccountDTO accountDTO) {
        Account account = Account.builder()
                        .username(accountDTO.getUsername())
                        .password(passwordEncoder.encode(accountDTO.getPassword()))
                        .name(accountDTO.getName())
                        .email(accountDTO.getEmail())
                        .nickname(accountDTO.getNickname())
                        .role(AccountRole.ROLE_USER)
                        .build();
        accountRepository.save(account);
        return 1;
    }

}
