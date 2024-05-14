package com.ezen.www.repository;

import com.ezen.www.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    int register(BoardVO bvo);

    List<BoardVO> list();

    Object detail(long bno);

    void modify(BoardVO bvo);

    void delete(long bno);
}
