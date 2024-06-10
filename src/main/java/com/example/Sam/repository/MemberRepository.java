package com.example.Sam.repository;

import com.example.Sam.entity.board;
import com.example.Sam.entity.member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<member, String>{
    Page<board> findByMemid(String memid, Pageable pageable);
}


