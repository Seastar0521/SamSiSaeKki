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

}

