package com.example.Sam.repository;

import com.example.Sam.entity.test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository <test, String> {
}
