package com.godcoder.myhome.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Board {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String title;
    private String content;
}
