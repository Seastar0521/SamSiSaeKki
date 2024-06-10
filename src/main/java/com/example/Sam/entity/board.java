package com.example.Sam.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer txtnum;

    private String memid;

    private String title;

    private String content;

    private String filename;

    private String filepath;

    private String date;
}
