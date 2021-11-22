package com.iesmb.bibliteca.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "generos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String genero;


    private boolean activo = true;

    //Relacion bidireccional con libro. Un genero puede pertencer a varios libros
    @OneToMany(mappedBy = "genero")
    private List<Libro> libros;
}
