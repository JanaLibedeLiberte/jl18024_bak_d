package com.lu.jl18024_bak_d.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Test3Object1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Test3Object2 field1;
    @OneToOne(cascade = CascadeType.ALL)
    private Test3Object3 field2;
}
