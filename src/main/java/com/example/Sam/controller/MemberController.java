package com.example.Sam.controller;
import com.example.Sam.entity.member;
import com.example.Sam.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입

    @Autowired
    private final MemberService memberService;

    @GetMapping("/member/signup") //회원가입 페이지 출력 요청
    public String memberSignup() {
        return "membersignup";
    }

    @PostMapping("/member/signup")
    public String signup(member memberform) {
        System.out.println("MemberController.signup");
        System.out.println("memberform = " + memberform);
        memberService.signup(memberform);
        return "login";
    }

    @GetMapping("/member/login")
    public String memberLogin()
    {
        return "login";
    }

    @PostMapping("/member/login") // session : 로그인 유지
    public String login(@ModelAttribute member memberform, HttpSession session) {
        member loginResult = memberService.login(memberform);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/member/personinfo")
    public String memberPersoninfo()
    {
        return "memberpersoninfo";
    }

    @GetMapping("/member/mypage")
    public String memberMypage()
    {
        return "membermypage";
    }

    @GetMapping("/member/mymainpage")
    public String memberMymainpage(Model a,member member)
    {

        System.out.println(a);
        System.out.println(member);

        memberService.write(member);

        System.out.println(a);
        System.out.println(member);

        a.addAttribute("list", memberService.memberList());

        System.out.println(a);
        System.out.println(member);

        return "memberlist";

    }


}
