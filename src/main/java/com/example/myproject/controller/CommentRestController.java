package com.example.myproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.dto.CommentDTO;
import com.example.myproject.service.CommentService;

import lombok.RequiredArgsConstructor;

// REST API : URI 정보를 바탕으로 기능을 이해할 수 있도록 설계
// POST : /api/comments -> 댓글 등록
// GET : /api/comments/board/{boardId} -> 댓글 목록
// PUT : /api/comments/{commentId} -> 댓글 수정
// DELETE : /api/comments/{commentId} -> 댓글 삭제

// 만약, 모든 Controller가 RestController로 구성되면
// /api는 생략하고 설계한다.
@RestController // 모든 메소드가 RESTful하게 설정됨
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping()
    public ResponseEntity<Integer> create(
        @RequestBody CommentDTO commentDTO
    ) {
        // @RequestBody : JSON 형태로 클라이언트에서 전송된 데이터를
        // Java 객체로 바인딩해주는 역할
        int result = commentService.save(commentDTO);
        // ResponseEntity<T> : 상태 코드, 헤더, 데이터를 직접 제어하는 객체
        // 특정 상태 메시지(200, 201 등)을 전송할 때 사용
        // T는 전송할 데이터 타입을 선언
        return ResponseEntity.ok(result);
    }

    // 게시글 별 댓글 목록 조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByBoardId(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.getCommentsByBoardId(boardId));
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
        @PathVariable Long commentId
    ) {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }

}