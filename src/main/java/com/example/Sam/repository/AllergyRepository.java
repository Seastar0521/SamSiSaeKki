package com.example.Sam.repository;

import com.example.Sam.entity.allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends JpaRepository<allergy, String> {
}
