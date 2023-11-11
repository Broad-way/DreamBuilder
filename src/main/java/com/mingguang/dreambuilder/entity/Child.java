package com.mingguang.dreambuilder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    @JsonBackReference
    @JoinTable(name = "child_volunteer",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id"))
    private Volunteer volunteer;
    private String name;
    private Boolean gender;
    private Date birthday;
    private String SchoolName;
    private String grade;
    private boolean isValidate;

    public Child() { this.isValidate = true; }
}
