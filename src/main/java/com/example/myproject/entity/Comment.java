package com.example.myproject.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
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
public class Comment {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String writer;
    private String content;
    @CreationTimestamp
    private LocalDateTime createAt;
    // boardId는 외래키
    // 하지만 JPA에서 따로 정의하지 않았기 때문에
    // 테이블에 외래키로 제약조건이 생성되지 않음
    private Long boardId;
}
