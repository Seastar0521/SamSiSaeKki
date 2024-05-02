package com.example.Sam.service;
import com.example.Sam.entity.member;
import com.example.Sam.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberrepository;


    public void signup(member memberDTO) {
        memberrepository.save(memberDTO);
    }

    public void write(member member) {
    }

    public Object memberList() {
        return null;
    }

    public member login(member memberform) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            // 조회 결과가 있다
            MemberEntity memberEntity = byMemberEmail.get(); // Optional에서 꺼냄
            if(memberEntity.getMemberPassword().equals(member.getMemberPassword())) {
                //비밀번호 일치
                //entity -> dto 변환 후 리턴
                member dto = member.tomember(memberEntity);
                return dto;
            } else {
                //비밀번호 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다
            return null;
        }
    }

}

