package com.ezen.www.service;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public int register(BoardVO bvo) {
        log.info(">> board register Service in");
        int isOk = boardMapper.register(bvo);
        return isOk;
    }

    @Override
    public List<BoardVO> list() {
        return boardMapper.list();
    }

    @Override
    public Object detail(long bno) {
        return boardMapper.detail(bno);
    }

    @Override
    public void modify(BoardVO bvo) {
        boardMapper.modify(bvo);
    }

    @Override
    public void delete(long bno) {
        boardMapper.delete(bno);
    }


}
