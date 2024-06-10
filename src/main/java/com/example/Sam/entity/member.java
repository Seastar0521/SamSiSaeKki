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
    private String memid;

    private String memname;

    private String mempw;

    private String memnick;

    private double memgender;

    private String mememail;

    private String membd;

    private double memheight;

    private double memweight;

    private String profilename;

    private String profilepath;
}