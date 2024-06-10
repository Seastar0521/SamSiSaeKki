package com.example.Sam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class disease {
    @Id
    private String memid;

    private Boolean diabete;

    private Boolean highbloodpressure;

    private Boolean heartdisease;

    private Boolean obesity;

    private Boolean osteoporosis;

    private Boolean anemia;

    private Boolean urinarystones;

    private Boolean gout;
}
