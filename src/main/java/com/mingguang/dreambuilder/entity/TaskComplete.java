package com.mingguang.dreambuilder.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class TaskComplete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "child_id",referencedColumnName = "id",nullable = false)
    private Child child;
    @OneToOne(optional = false)
    @JoinColumn(name = "task_id",referencedColumnName = "id",nullable = false)
    private Task task;
    private Timestamp time;
    private Long volunteerId;
    private Boolean passed;
    private Boolean appealed;
    private String commentText;
    private String pic;
    private boolean isValidate = true;
}
