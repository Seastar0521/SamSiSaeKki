package com.example.Sam.controller;
import com.example.Sam.entity.board;
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

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private shareBoardService shareboardService;


    //게시글 작성 창 불러오기
    @GetMapping("/board/write")
    public String boardWriteForm() throws Exception{
        return "boardwrite";
    }

    //작성된 게시글 데이터베이스로 보내기
    @PostMapping("/board/writepro")
    public String boardWritePro(Model model, board board, MultipartFile file) throws Exception {

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    //게시글 목록 불러오기
    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "txtnum", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 4, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("share", false);
        return "boardlist";
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
        model.addAttribute("share", true);

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

    //공유 게시판 목록 표시

}
