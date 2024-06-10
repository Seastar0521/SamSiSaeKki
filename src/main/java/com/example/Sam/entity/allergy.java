package com.example.Sam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class allergy {
    @Id
    private String memid;

    private Boolean egg;

    private Boolean milk;

    private Boolean wheat;

    private Boolean bean;

    private Boolean peanut;

    private Boolean chestnut;

    private Boolean fish;

    private Boolean clam;

    private Boolean crab;

    private Boolean nut;

    private Boolean peach;
}
