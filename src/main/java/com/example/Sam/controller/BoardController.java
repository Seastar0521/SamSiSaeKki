package com.example.Sam.controller;
import com.example.Sam.entity.allergy;
import com.example.Sam.entity.board;
import com.example.Sam.entity.test;
import com.example.Sam.repository.TestRepository;
import com.example.Sam.service.BoardService;
import com.example.Sam.service.shareBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private shareBoardService shareboardService;
    @Autowired
    private TestRepository testRepository;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/tests")
    public @ResponseBody void tests(@ModelAttribute test test) {
        System.out.println(test);
        testRepository.save(test);
        String[] list = test.getParam2().split(",");
        for(int i = 0; i < 3; i++){
            System.out.println(list[i]);
        }
    }

    //게시글 작성 창 불러오기
    @GetMapping("/board/write")
    public String boardWriteForm(board board , @CookieValue String id, Model model) throws Exception{
        String content = board.getContent();
        System.out.println(content);
        System.out.println(board);
        model.addAttribute("content", content);
        return "boardwrite";
    }

    //작성된 게시글 데이터베이스로 보내기
    @PostMapping("/board/writepro")
    public String boardWritePro(Model model, board board, MultipartFile file, @CookieValue String id) throws Exception {

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formated = date.format(formatter);
        board.setDate(formated);
        board.setMemid(id);
        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    //게시글 목록 불러오기
    @GetMapping("/board/list")
    public String boardList(@CookieValue String id ,Model model, @PageableDefault(page = 0, size = 4, sort = "txtnum", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<board> list = boardService.boardList(pageable, id);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 2, 1);
        int endPage = Math.min(nowPage + 2, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("share", false);
        return "board";
    }

    //게시글 상세 페이지 불러오기
    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardView(Model model,@RequestParam("id") Integer id) {
        board a = boardService.boardView(id);
        if(a != null){
            model.addAttribute("view", boardService.boardView(id));
            model.addAttribute("share", false);
            return "boardview";
        } else
            return "nothing";
    }

    //게시글 지우기
    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id") Integer id) {
        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    //게시글 수정하기 창 불러오기
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));
        model.addAttribute("share", false);

        return "boardmodify";
    }

    //수정한 게시글 DB에 업데이트
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, board board, MultipartFile file) throws Exception {

        board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }

    //게시글 공개처리 비공개 처리
    @GetMapping("/board/share/{id}")
    public String boardshare(@PathVariable("id") Integer id) throws Exception {

        boardService.boardShare(id);

        return "redirect:/board/list";
    }

    //게시글 공개처리
    @PostMapping("/board/shareselected")
    public String boardshareselected(@RequestParam List<String> checked) throws Exception {

        System.out.println(checked);
        System.out.println(checked.size());
        for (int i = 0 ; i < checked.size() ; i++) {
            boardService.boardShare(Integer.valueOf(checked.get(i)));
        }

        return "redirect:/board/list";
    }

    //선택된 게시글들 삭제
    @PostMapping("/board/deleteselected")
    public String boardDeleteSelected(@RequestParam List<String> checked) throws Exception {

        System.out.println(checked);
        System.out.println(checked.size());
        for (int i = 0 ; i < checked.size() ; i++) {
            boardService.boardDelete(Integer.valueOf(checked.get(i)));
        }

        return "redirect:/board/list";
    }

}
