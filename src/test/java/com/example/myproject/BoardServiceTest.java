package com.example.myproject;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.myproject.dto.AttachDTO;
import com.example.myproject.dto.BoardDTO;
import com.example.myproject.service.BoardService;


// 단위 테스트 : 서버를 실행하지 않고 특정 위치의 코드를 
// 실행하여 테스트 진행 가능
@SpringBootTest // Test가 가능한 클래스로 컴퍼넌트 선언
public class BoardServiceTest {

    @Autowired // 선언된 클래스(인터페이스)의 인스턴스 주입
    private BoardService boardService;

    @Test
    void test() {
        saveBoard();
        // getBoardById();
        // getBoardsByPage();
    }

    private void getBoardsByPage() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("boardId").descending());
        // of(pageNumber, pageSize, sort) :
        // pageNumber(페이지 번호) : 0 -> 1페이지, 1 -> 2페이지
        // pageSize(페이지 크기) : 10 -> 한 페이지 당 10개
        // sort(정렬 방식) : Sort.by("boardId").descending() 
        //                  -> boardId를 기준으로 내림차순 정렬

        Page<BoardDTO> list = boardService.getBoards(pageable);

        System.out.println("현재 페이지 : " + list.getNumber());
        System.out.println("페이지 크기 : " + list.getSize());
        System.out.println("전체 게시글 수 : " + list.getTotalElements());
        System.out.println("전체 페이지 수 : " + list.getTotalPages());

        // 각 데이터 출력
        list.getContent().forEach(System.out::println);
    }

    private void getBoardById() {
        BoardDTO boardDTO = boardService.getBoardById(1L);
        System.out.println(boardDTO);
    }

    private void saveBoard() {

        List<AttachDTO> attachList = List.of(
            AttachDTO.builder()
                    .filePath("/upload/2026/06/18")
                    .fileRealName("spring-guide.pdf")
                    .fileChgName("a1b2c3d4.pdf")
                    .build(),

            AttachDTO.builder()
                    .filePath("/upload/2026/06/18")
                    .fileRealName("sample-image.png")
                    .fileChgName("e5f6g7h8.png")
                    .build()
        );

        BoardDTO boardDTO = BoardDTO.builder()
                .title("게시글 테스트")
                .content("첨부파일 등록 테스트입니다.")
                .writer("관리자")
                .commentCount(0)
                .attachCount(2)
                .attachList(attachList)
                .build();

        boardService.saveBoard(boardDTO);
    }

}
