package com.example.myproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myproject.dto.CommentDTO;
import com.example.myproject.service.CommentService;

// 단위 테스트 :  서버를 실행하지 않고 특정 위치의 코드를
// 실행하여 테스트 진행 가능
@SpringBootTest // Test가 가능한 클래스로 컴퍼넌트 선언
public class CommentServiceTest {
    
    @Autowired // 선언된 클래스(인터페이스)의 인스턴스 주입 
    private CommentService commentService;

    @Test
    void test() {
        saveComment();
    }

    private void saveComment() {
        CommentDTO commentDTO = CommentDTO.builder()
                                .boardId(1L)
                                .content("댓글 테스트")
                                .writer("작성자")
                                .build();
        commentService.save(commentDTO);

    }


    
}
