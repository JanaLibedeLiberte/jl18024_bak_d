package com.lu.jl18024_bak_d.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Test2Object1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Test2Object1 parent;
}
