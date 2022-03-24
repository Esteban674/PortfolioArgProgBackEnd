package com.portfolioegjp.Portfolio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Course {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    private String name;         //Nombre del curso o certificacion
    private String issuingAuthority;    //Empresa o autoridad emisora
    private String expeditionDate;
    private String credentialId;
    private String credentialUrl;
    private String comments;
    private String img;
}
