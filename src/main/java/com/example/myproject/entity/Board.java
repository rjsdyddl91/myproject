package com.example.myproject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Board {
    
    @Id // 아래 멤버 변수(boardId)를 entity(테이블)의 PK로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본 키의 값을 자동 생성(AUTO_INCREMENT)
    private Long boardId;
    private String title;
    private String content;
    private String writer;

    @CreationTimestamp // 처음 생성될 때 시간 자동 생성
    private LocalDateTime regDate;

    @ColumnDefault("0") // 컬럼의 기본 값을 0으로 설정
    private int commentCount;

    @ColumnDefault("0")
    private int attachCount;

    @Builder.Default
    @OneToMany(
        mappedBy="board",
        fetch=FetchType.LAZY,
        cascade=CascadeType.ALL,
        orphanRemoval=true
    )
    private List<Attach> attachList = new ArrayList<>();
    // @Builder.Default
    // Builder 사용 시 attachList가 null이 되는 것을 방지
    // new ArrayList<>()를 기본값으로 유지

    // @OneToMany
    // 하나의 게시글(Board)에 여러 개의 첨부파일(Attach)이 연결되는
    // 1:N(일대다) 관계를 의미

    // mappedBy="board"
    // 연관관계의 주인은 Attach 엔티티의 board 필드
    // 실제 외래키는 attach 테이블의 board_id 컬럼이 관리

    // FetchType.LAZY
    // Board 조회 시 첨부파일 목록을 즉시 조회하지 않음
    // board.getAttachList()를 호출하는 시점에 조회 쿼리가 실행됨

    // CascadeType.ALL
    // Board 저장, 수정, 삭제 시
    // 연관된 Attach 엔티티에도 동일한 작업을 전파

    // orphanRemoval=true
    // Board의 attachList에서 제거된 Attach는
    // DB에서도 자동으로 삭제됨 

}
