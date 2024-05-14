package com.ezen.www.service;


import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.PagingVO;

import java.util.List;

public interface BoardService {

    int register(BoardDTO bdto);

    List<BoardVO> list(PagingVO pgvo);

    BoardDTO detail(long bno);

    void modify(BoardVO bvo);

    void delete(long bno);

    int getTotalCount(PagingVO pgvo);


}
