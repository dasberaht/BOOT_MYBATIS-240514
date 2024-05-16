package com.ezen.www.service;

import com.ezen.www.domain.MemberVO;

import java.security.Principal;
import java.util.List;

public interface MemberService {
    int insert(MemberVO mvo);

    List<MemberVO> getMemberList();

    void modify(MemberVO mvo);

    void pwdmodify(MemberVO mvo);

    void delete(String name);

//    void deleteauth(String email);


}
