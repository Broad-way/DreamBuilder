package com.mingguang.dreambuilder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @OneToOne(mappedBy = "volunteer")
    private Child child;
    private String name;
    private Boolean gender;
    private Date birthday;
    private boolean isValidate;

    public Volunteer() { this.isValidate = true; }
}
