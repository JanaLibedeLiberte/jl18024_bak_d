package com.lu.jl18024_bak_d.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@ToString
@Entity
public class Test1Object1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nullableField1;
    private String nullableField2;
    private String nullableField3;
    private String nullableField4;
    private String nullableField5;
    private String nullableField6;
    private String nullableField7;
    private String nullableField8;
    private String nullableField9;
    private String nullableField10;
    private String nullableField11;
    private String nullableField12;
    private String nullableField13;
    private String nullableField14;
    private String nullableField15;
    private String nullableField16;
    private String nullableField17;
    private String nullableField18;
    private String nullableField19;
    private String nullableField20;
}
