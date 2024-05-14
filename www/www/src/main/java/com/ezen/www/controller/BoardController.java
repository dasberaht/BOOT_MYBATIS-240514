package com.ezen.www.controller;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.FileHandler;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.BoardService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService bsv;
    private final FileHandler fh;


    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(BoardVO bvo, @RequestParam(name = "files", required = false)MultipartFile[] files){
        log.info("> board register bvo {}", bvo);
        List<FileVO> flist = null;
        if (files[0].getSize() > 0 || files != null) {
            flist = fh.uploadFiles(files);
        }
        BoardDTO bdto = new BoardDTO(bvo, flist);
        int isOk = bsv.register(bdto);

        return "redirect:/board/list";
    }

    //    @GetMapping("/list")
    //    public void list(Model m){
    //        List<BoardVO> list = bsv.list();
    //        m.addAttribute("list", list);
    //    }
    // 위와 같이 List<BoardVO> 를 생성하지 않아도, BoardService에서 생성하여 작성 가능
    @GetMapping("/list")
    public void list(Model m, PagingVO pgvo){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> pgvo {}", pgvo);
        // totalCount DB에서 가져오기
        int totalCount = bsv.getTotalCount(pgvo);       // 검색어 같이 가져가야한다.
        // PagingHandler 객체 생성
        PagingHandler ph = new PagingHandler(pgvo, totalCount);
        m.addAttribute("list", bsv.list(pgvo));
        m.addAttribute("ph", ph);
    }

    @GetMapping("/detail")
    public void detail(Model m, @RequestParam("bno") long bno){
        m.addAttribute("bdto", bsv.detail(bno));
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
