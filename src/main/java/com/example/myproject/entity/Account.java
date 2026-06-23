package com.example.myproject.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.myproject.common.AccountRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 이 클래스가 JPA 엔티티임을 정의
// Board 클래스의 구조에 맞게 DB 테이블이 생성됨
// @Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long accountId;
    @Column(nullable=false, unique=true)
    private String username; // 계정 아이디 
    @Column(nullable=false)
    private String password;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(nullable=false, unique=true)
    private String nickname;
    @CreationTimestamp
    private LocalDateTime regDate;

    @Enumerated(EnumType.STRING)
    // enum 저장할 때 문자열(ROLE_ADMIN 등)으로 DB에 저장
    @Column(nullable = false)
    private AccountRole role;
}
