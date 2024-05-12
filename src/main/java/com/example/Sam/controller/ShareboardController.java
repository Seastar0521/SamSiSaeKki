package com.example.Sam.controller;
import com.example.Sam.entity.board;
import com.example.Sam.entity.shareboard;
import com.example.Sam.service.shareBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ShareboardController {

    @Autowired
    private shareBoardService shareboardService;

    @GetMapping("/shareboard/list")
    public String boardList(Model model, @PageableDefault(page = 0, size = 4, sort = "txtnum", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<shareboard> list = shareboardService.shareboardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 2, 1);
        int endPage = Math.min(nowPage + 2, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("share", true);
        return "board";
    }

    //게시글 상세 페이지 불러오기
    @GetMapping("/shareboard/view") // localhost:8080/board/view?id=1
    public String boardView(Model model,@RequestParam("id") Integer id) {
        shareboard a = shareboardService.boardView(id);
        if(a != null){
            model.addAttribute("view", shareboardService.boardView(id));
            model.addAttribute("share", true);
            return "boardview";
        } else
            return "nothing";
    }

    //게시글 수정하기 창 불러오기
    @GetMapping("/shareboard/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", shareboardService.boardView(id));
        model.addAttribute("share", true);

        return "boardmodify";
    }

    //수정한 게시글 DB에 업데이트
    @PostMapping("/shareboard/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, board board, MultipartFile file) throws Exception {

        shareboard boardTemp = shareboardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        shareboardService.update(boardTemp, file);

        return "redirect:/shareboard/list";
    }

    //게시글 지우기
    @GetMapping("/shareboard/delete")
    public String boardDelete(@RequestParam("id") Integer id) {
        shareboardService.boardDelete(id);

        return "redirect:/shareboard/list";
    }
}
