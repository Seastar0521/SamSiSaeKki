package com.example.Sam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class shareboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer txtnum;

    private String memid;

    private String title;

    private String content;

    private String filename;

    private String filepath;

    private String date;

    private Integer count;
}