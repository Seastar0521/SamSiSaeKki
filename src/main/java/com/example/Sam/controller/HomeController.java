package com.example.Sam.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //기본페이지 요청 메서드
    @GetMapping("/")
    public String index(){
        return "homepage"; //templates 폴더의 homepage라는 html 파일을 브라우저에 띄움
    }
}


