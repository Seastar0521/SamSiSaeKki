package com.example.Sam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memno;

    private String memid;

    private String mempw;

    private String memnick;

    //private Date membd;
}