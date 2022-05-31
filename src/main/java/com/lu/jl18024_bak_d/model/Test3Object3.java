package com.lu.jl18024_bak_d.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Test3Object3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String field1;
    private String field2;
    private String field3;
}
