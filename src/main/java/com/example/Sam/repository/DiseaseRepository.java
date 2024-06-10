package com.example.Sam.repository;

import com.example.Sam.entity.disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<disease, String> {
}
