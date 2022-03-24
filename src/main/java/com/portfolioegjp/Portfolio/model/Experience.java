package com.portfolioegjp.Portfolio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Experience {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    private String position;
    @OneToOne
    private Mode mode;
    
    private String company;
    private String location;
    private String startDate;
    private String endDate;
    private String comments;
    private String img;
}
