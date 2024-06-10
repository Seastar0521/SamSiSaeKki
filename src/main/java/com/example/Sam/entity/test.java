package com.example.Sam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class test {
    @Id
    private String param1;
    private String param2;
}
