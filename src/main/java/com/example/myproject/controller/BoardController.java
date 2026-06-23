package com.example.myproject.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.myproject.dto.AttachDTO;
import com.example.myproject.dto.BoardDTO;
import com.example.myproject.service.BoardService;

import lombok.RequiredArgsConstructor;

// Presentation Layer(표현 계층) : 클라이언트의 요청/응답을 처리
@Controller 
@RequiredArgsConstructor
@RequestMapping("/board") // localhost:9090/board
public class BoardController {
    
    // BoardService 빈 객체 주입
    private final BoardService boardService;
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    // application.properties에서 정의된
    // 파일 업로드 경로를 uploadDir 변수에 주입

    // 게시글 목록 페이지(데이터 + html)
    @GetMapping("/list")
    public void listBoards(
        @PageableDefault(
            size = 5,
            sort = "boardId",
            direction = Sort.Direction.DESC
        ) Pageable pageable,
        Model model
    ) {
        // @PageableDefault() :
        // Pageable에 들어갈 값에 대해 default로 설정하는 기능
        // Pageable에 page 변수가 적용되어 있기 때문에
        // parameter로 전송된 "page"의 값이 Pageable 객체에 적용
        // 적용되는 요청 파라미터 : page, size, sort

        Page<BoardDTO> boards = boardService.getBoards(pageable);
        model.addAttribute("boards", boards);
    }

    // 게시글 등록 페이지
    @GetMapping("/register")
    public void registerForm() {

    }

    // 게시글 등록 처리
    @PostMapping("/register")
    public String registerBoard(@ModelAttribute BoardDTO boardDTO, 
        List<MultipartFile> files
    ) {
        // @ModelAttribute : form data, request parameter로 전송된
        //      데이터를 객체에 바인딩 해주는 역할
        List<AttachDTO> attachList = new ArrayList<>();

        for(MultipartFile file : files) {
            if(!file.isEmpty()) {
                String originalName = file.getOriginalFilename();
                // UUID : 유니크한 랜덤 아이디를 생성해주는 유틸리티
                String changedName = UUID.randomUUID().toString();

                // 파일 객체 생성
                File savedFile = new File(uploadDir + changedName);

                try {
                    // 서버의 특정 위치에 파일을 생성
                    file.transferTo(savedFile);
                } catch (IOException e) {
                    throw new RuntimeException("파일 업로드 실패");
                }

                AttachDTO attachDTO = AttachDTO.builder()
                        .fileRealName(originalName)
                        .fileChgName(changedName)
                        .filePath(uploadDir)
                        .build();
                
                attachList.add(attachDTO);

            }
        }

        boardDTO.setAttachList(attachList);
        boardService.saveBoard(boardDTO);
        return "redirect:/board/list";
    }

    // 게시글 상세 페이지
    @GetMapping("/detail")
    public void detailBoard(Long boardId, Model model) {
        BoardDTO board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
    }

    // 게시글 수정 페이지
    @GetMapping("/edit")
    public void editForm(Long boardId, Model model) {
        BoardDTO board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
    }

    @PostMapping("/edit")
    public String editBoard(Long boardId, 
        BoardDTO boardDTO,
        List<MultipartFile> files
    ) {
        List<AttachDTO> attachList = new ArrayList<>();

        for(MultipartFile file : files) {
            if(!file.isEmpty()) {
                String originalName = file.getOriginalFilename();
                // UUID : 유니크한 랜덤 아이디를 생성해주는 유틸리티
                String changedName = UUID.randomUUID().toString();

                // 파일 객체 생성
                File savedFile = new File(uploadDir + changedName);

                try {
                    // 서버의 특정 위치에 파일을 생성
                    file.transferTo(savedFile);
                } catch (IOException e) {
                    throw new RuntimeException("파일 업로드 실패");
                }

                AttachDTO attachDTO = AttachDTO.builder()
                        .fileRealName(originalName)
                        .fileChgName(changedName)
                        .filePath(uploadDir)
                        .build();
                
                attachList.add(attachDTO);

            }
        }

        boardDTO.setAttachList(attachList);

        boardService.updateBoard(boardId, boardDTO);

        return "redirect:/board/detail?boardId=" + boardId;
    }

    @PostMapping("/delete")
    public String deleteBoard(Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/board/list";
    }

}
