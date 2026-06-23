package com.example.myproject.dto;

import java.time.LocalDateTime;

import com.example.myproject.common.AccountRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long accountId;
    private String username; // 계정 아이디
    private String password;
    private String name;
    private String email;
    private String nickname;
    private LocalDateTime regDate;
    private AccountRole role;
}
