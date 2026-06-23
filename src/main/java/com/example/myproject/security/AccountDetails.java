package com.example.myproject.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.myproject.entity.Account;

// UserDetails를 구현하여 Account 정보를 context에 전송할 객체
public class AccountDetails implements UserDetails{

    // 로그인한 사용자의 account 정보를 저장
    private Account account;

    public AccountDetails(Account account) {
        this.account = account;
    }

    // 로그인한 사용자의 권한 정보를 적용
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(account.getRole().name()));
    }

    // 로그인한 사용자의 패스워드 적용
    @Override
    public String getPassword() {
        return account.getPassword();
    }

    // 로그인한 사용자의 아이디 적용
    @Override
    public String getUsername() {
        return account.getUsername();   
    }

    public Account getAccount() {
        return account;
    }
    
}
