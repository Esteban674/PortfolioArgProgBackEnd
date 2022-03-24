package com.portfolioegjp.Portfolio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    private String school;
    private String title;
    private String career; //Disciplina Academica
    private String startDate;   
    private String endDate;
    private String comments;
    private String img;
    
}

