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
public class Skills {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne
    private Category category;        //Framework, Lenguaje, Basics, Herramienta
    
    @OneToOne
    private Grupo grupo;            //FrontEnd, BackEnd, Database, Dev Ops, Mobile App
     
    @OneToOne
    private Status status;          //Principante, intermedio, avanzado, experto
    
    private String name;            //HTML, CSS, JavaScript, Idiomas
    private String img;
    
}
