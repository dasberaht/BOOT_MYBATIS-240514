package com.ezen.www.controller;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.service.BoardService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService bsv;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(BoardVO bvo){
        log.info("> board register bvo {}", bvo);
        int isOk = bsv.register(bvo);

        return "/index";
    }

//    @GetMapping("/list")
//    public void list(Model m){
//        List<BoardVO> list = bsv.list();
//        m.addAttribute("list", list);
//    }

    // 위와 같이 List<BoardVO> 를 생성하지 않아도, BoardService에서 생성하여 작성해도 무방
    @GetMapping("/list")
    public void list(Model m){
        m.addAttribute("list", bsv.list());
    }

    @GetMapping("/detail")
    public void detail(Model m, @RequestParam("bno") long bno){
        m.addAttribute("bvo", bsv.detail(bno));
    }

    @PostMapping("/modify")
    public String modify(BoardVO bvo){
        bsv.modify(bvo);
        return "redirect:/board/detail?bno="+bvo.getBno();
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno")long bno){
        bsv.delete(bno);
        return "redirect:/board/list";
    }



}
