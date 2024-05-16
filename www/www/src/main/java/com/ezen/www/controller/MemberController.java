package com.ezen.www.controller;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
@Controller
public class MemberController {

    private final MemberService msv;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public void join(){}

    @PostMapping("/register")
    public String register(MemberVO mvo){
        mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
        int isOk = msv.insert(mvo);
        return "/index";
    }

    @GetMapping("/login")
    public void login(){}

    @GetMapping("/memberlist")
    public String memberlist(Model m){
        List<MemberVO> mlist = msv.getMemberList();
        m.addAttribute("mlist", mlist);
        return "/member/memberlist";
    }

    @GetMapping("/modify")
    public void modify(){}

    @PostMapping("/modify")
    public String modify(MemberVO mvo){
        if(mvo.getPwd()==null || mvo.getPwd().equals("") || mvo.getPwd().length()==0){
            msv.modify(mvo);
        }else{
            mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
            msv.pwdmodify(mvo);
        }
        return "redirect:/member/logout";
    }

//    @GetMapping("/delete")
//    public String delete(@RequestParam("email")String email){
//        msv.deleteauth(email);
//        return "redirect:/member/logout";
//    }

    @GetMapping("/delete")
    public String delete(Principal principal){
        msv.delete(principal.getName());
        return "redirect:/member/logout";
    }



}
