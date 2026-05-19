package com.minhho.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false, unique = true)
    private String name; //ADMIN, MANAGER, EMPLOYEE, ...

    public Role(){}

    public Role(String name){
        this.name = name;
    }
    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
