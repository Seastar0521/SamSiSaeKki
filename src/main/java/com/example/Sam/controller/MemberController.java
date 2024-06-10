package com.example.Sam.controller;
import com.example.Sam.entity.allergy;
import com.example.Sam.entity.disease;
import com.example.Sam.entity.member;
import com.example.Sam.repository.AllergyRepository;
import com.example.Sam.repository.DiseaseRepository;
import com.example.Sam.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final AllergyRepository allergyrepository;
    @Autowired
    private final DiseaseRepository diseaserepository;

    //생성자 주입
    @Autowired
    private final MemberService memberService;

    @PostMapping("/allergy")
    public @ResponseBody void test(@ModelAttribute allergy allergy, @CookieValue(value = "id", defaultValue = "") String memid) {
        System.out.println(allergy);
        allergy.setMemid(memid);
        allergyrepository.save(allergy);
    }

    @PostMapping("/disease")
    public @ResponseBody void test(@ModelAttribute disease disease, @CookieValue(value = "id", defaultValue = "") String memid) {
        System.out.println(disease);
        disease.setMemid(memid);
        diseaserepository.save(disease);
    }

    @GetMapping("/signup") //회원가입 페이지 출력 요청
    public String memberSignup() {
        return "membersignup";
    }

    @PostMapping("/signuppro")
    public String signup(member memberform, RedirectAttributes model) {
        System.out.println("MemberController.signup");
        System.out.println("memberform = " + memberform);
        if(memberService.signup(memberform)) {
            model.addFlashAttribute("message", "회원가입 성공");
            return "redirect:/homepage";
        }
        else
        {
            model.addFlashAttribute("message", "회원가입 실패");
            return  "redirect:/signup";
        }
    }

    @GetMapping("/searchid")
    public String searchingid() {
        return "searchingID";

    }

    @GetMapping("/searchpw")
    public String searchingpw() {
        return "searchingPW";
    }

    @GetMapping("/login")
    public String loginpage() {
        return "memberlogin";
    }

    @PostMapping("/login/pro")
    public String login(Model model, @Valid @ModelAttribute member member, HttpServletResponse response) {
        member calcMem = memberService.login(member);
        if(calcMem == null) {
            model.addAttribute("message", "로그인이 실패하였습니다.");
            model.addAttribute("searchUrl", "/login");
            return "message";
        }
        String inputPw = member.getMempw();
        String checkPw = calcMem.getMempw();
        if(Objects.equals(inputPw, checkPw)){
            //sessionManager.createSession(member.getMemid(), response);
            String id = member.getMemid();

            model.addAttribute("message", "로그인이 완료되었습니다.");
            model.addAttribute("searchUrl", "/homepage");

            Cookie idCookie = new Cookie("id", id);
            idCookie.setPath("/");
            response.addCookie(idCookie);

            return "message";
        } else {
            model.addAttribute("message", "로그인이 실패하였습니다.");
            model.addAttribute("searchUrl", "/login");
            return "message";
        }
    }


    // 로그아웃 구현
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        System.out.println("a");
        expireCookie(response, "id");
        System.out.println("b");
        return "redirect:/homepage";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        System.out.println("c");
        Cookie cookie = new Cookie(cookieName, null);

        System.out.println("d");
        cookie.setMaxAge(0);
        System.out.println("e");
        response.addCookie(cookie);
    }


    @GetMapping("/member/personinfo")
    public String memberPersoninfo(Model model, @CookieValue(value = "id", defaultValue = "") String mem)
    {
        member member = memberService.checkid(mem);
        allergy allergy = allergyrepository.findById(mem).get();
        disease disease = diseaserepository.findById(mem).get();
        System.out.println(allergy);
        System.out.println(disease);
        model.addAttribute("allergy", allergy);
        model.addAttribute("disease", disease);
        model.addAttribute("member", member);
        return "memberpersoninfo";
    }

    @PostMapping("/member/personinfosave")
    public @ResponseBody String memberPersoninfosave(@ModelAttribute member member,
                                                     @CookieValue(value = "id", defaultValue = "") String mem)
    {
        member forsave = memberService.checkid(mem);
        forsave.setMemnick(member.getMemnick());
        forsave.setMemgender(member.getMemgender());
        forsave.setMememail(member.getMememail());
        forsave.setMembd(member.getMembd());
        forsave.setMemweight(member.getMemweight());
        forsave.setMemheight(member.getMemheight());
        System.out.println(member);
        System.out.println(forsave);

        memberService.infosave(forsave);
        return "redirect:/member/mypage";
    }

    @PostMapping("/member/profilechange")
    public void memberprofilechange(@CookieValue(value = "id", defaultValue = "") String id, MultipartFile file) throws Exception {
        memberService.changeprofile(id, file);
    }

    @GetMapping("/member/mypage")
    public String memberMypage(Model model, @CookieValue(value = "id", defaultValue = "") String mem)
    {
        member member = memberService.checkid(mem);
        model.addAttribute("member" ,member);
        return "mymainpage";
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
