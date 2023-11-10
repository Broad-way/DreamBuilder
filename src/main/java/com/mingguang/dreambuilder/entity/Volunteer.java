package com.mingguang.dreambuilder.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne(mappedBy = "volunteer")
    private Child child;
    private String name;
    private Boolean gender;
    private Date birthday;
    private boolean isValidate;
}
