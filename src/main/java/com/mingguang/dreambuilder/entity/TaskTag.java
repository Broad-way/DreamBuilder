package com.mingguang.dreambuilder.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TaskTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String tagName;
    @ManyToOne
    @JoinColumn(name = "visible_user_id")
    User visibleUser;
    @ManyToOne
    @JoinColumn(name = "task_id")
    Task task;
    Boolean isValidate;
}
