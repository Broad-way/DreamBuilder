package com.mingguang.dreambuilder.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String name;
    private int point;
    private Timestamp validateFrom;
    private Timestamp validateUntil;
    private boolean isValidate;

    public Task() {
        this.isValidate = true;
    }
}
