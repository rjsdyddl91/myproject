package com.example.myproject.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.myproject.entity.Account;
import com.example.myproject.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

// DaoAuthenticationProvider로부터 전송받은 username으로 
// Account 테이블에서 사용자 정보 존재 여부를 확인하고
// Account 객체를 daoAuthenticationProvider로 전송

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService{
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username : 클라이언트에서 전송된 사용자 아이디(username)
        Account account = accountRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));

        // account 데이터를 Details 형태에 적용하여 클라이언트로 전송
        // account 데이터에는 username, password, role, 사용자 정보 등이 존재
        return new AccountDetails(account);
     
    }
    
}
