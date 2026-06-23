package com.example.myproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myproject.dto.CommentDTO;
import com.example.myproject.entity.Board;
import com.example.myproject.entity.Comment;
import com.example.myproject.repository.BoardRepository;
import com.example.myproject.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    // Service 계층은 비지니스 로직의 연결을 수행
    // 1개 이상의 DB간 데이터 변경을 수행하고,
    // 이를 트랜잭션으로 처리하는 계층
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 등록 : 댓글 추가 + 게시글 테이블 수정
    @Transactional
    // 이 메소드 안의 DB 작업들을 하나의 작업 단위로 묶어주는 어노테이션
    // 데이터 무결성을 보장하는 기능
    // 또한, 변경 감지(Dirty Checking) 기능도 수행
    public int save(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                        .writer(commentDTO.getWriter())
                        .content(commentDTO.getContent())
                        .boardId(commentDTO.getBoardId())
                        .build();
        commentRepository.save(comment);
        Board board = boardRepository.findById(commentDTO.getBoardId())
                                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
        board.setCommentCount(board.getCommentCount() + 1);
        // boardRepository.save(board);
        // 변경 감지(Dirty Checking) 기능 : 
        // @Transactional 설정이 되어 있으면 엔티티(객체)의 변경을 감지하여
        // 변경된 데이터를 바탕으로 자동으로 update 쿼리를 수행하는 기능
        // 트랜잭션 종료 시 자동으로 적용
        return 1;
    }
    
    // 특정 게시글의 댓글 전체 조회
    public List<CommentDTO> getCommentsByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);

        return comments.stream().map(this::toDTD)
        .collect(Collectors.toList());
    }

    // 댓글 삭제 : 댓글 제거 + 게시글 댓글 수 감소
    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        commentRepository.deleteById(commentId);
        Board board = boardRepository.findById(comment.getBoardId()).get();
        board.setCommentCount(board.getCommentCount() - 1);
    }

    // Entity -> DTO
    private CommentDTO toDTD(Comment comment) {
        return CommentDTO.builder()
                .boardId(comment.getBoardId())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .createAt(comment.getCreateAt())
                .commentId(comment.getCommentId())
                .build();
    }
       
}


