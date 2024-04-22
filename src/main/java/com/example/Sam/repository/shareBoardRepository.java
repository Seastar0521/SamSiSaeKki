package com.example.Sam.repository;

import com.example.Sam.entity.shareboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shareBoardRepository extends JpaRepository<shareboard, Integer> {
}
