package com.example.Sam.repository;

import com.example.Sam.entity.member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<member, Integer>{
}


