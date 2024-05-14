package com.ezen.www.service;


import com.ezen.www.domain.BoardVO;

import java.util.List;

public interface BoardService {

    int register(BoardVO bvo);

    List<BoardVO> list();

    Object detail(long bno);

    void modify(BoardVO bvo);

    void delete(long bno);
}
