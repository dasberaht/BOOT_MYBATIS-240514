package com.ezen.www.service;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;

    @Override
    public int insert(MemberVO mvo) {
        int isOk = memberMapper.insert(mvo);
        // 권한설정
        return isOk > 0 ? memberMapper.insertAuth(mvo.getEmail()) : 0;
    }

    @Override
    public List<MemberVO> getMemberList() {
        List<MemberVO> mlist = memberMapper.getMemberList();
        for(MemberVO mvo : mlist){
            mvo.setAuthList(memberMapper.selectAuths(mvo.getEmail()));
        }
        return mlist;
    }

    @Override
    public void modify(MemberVO mvo) {
        memberMapper.modify(mvo);
    }

    @Override
    public void pwdmodify(MemberVO mvo) {
        memberMapper.pwdmodify(mvo);
    }

    @Transactional
    @Override
    public void delete(String name) {
        memberMapper.deleteauth(name);
        memberMapper.finaldelete(name);
    }


//    @Transactional
//    @Override
//    public void deleteauth(String email) {
//        memberMapper.deleteauth(email);
//        memberMapper.finaldelete(email);
//    }


}
