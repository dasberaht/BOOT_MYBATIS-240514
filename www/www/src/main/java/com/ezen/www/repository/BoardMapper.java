package com.ezen.www.repository;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    int register(BoardVO bvo);

    List<BoardVO> list(PagingVO pgvo);

    BoardVO detail(long bno);

    void modify(BoardVO bvo);

    void delete(long bno);

    void insert(BoardVO bvo);

    int getTotalCount(PagingVO pgvo);

    long getBno();
}
