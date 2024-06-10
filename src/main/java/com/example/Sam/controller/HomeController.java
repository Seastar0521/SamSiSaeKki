package com.example.Sam.controller;


import com.example.Sam.entity.member;
import com.example.Sam.entity.shareboard;
import com.example.Sam.repository.MemberRepository;
import com.example.Sam.service.MemberService;
import com.example.Sam.service.shareBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final shareBoardService shareBoardService;

    //기본페이지 요청 메서드
    @GetMapping("/homepage")
    public String home(@CookieValue(name = "id", required = false) String memberId, Model model, @PageableDefault(page = 0, size = 8, sort = "txtnum", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<shareboard> list = shareBoardService.shareboardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 2, 1);
        int endPage = Math.min(nowPage + 2, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("share", true);

        if (memberId == null) {
            return "homepage";
        }

        member loginMember = memberRepository.findById(memberId).get();
        if (!loginMember.getMemid().isEmpty()) {
            model.addAttribute("member", loginMember);
            return "homepage";
        }

        return "homepage";
    }
}


