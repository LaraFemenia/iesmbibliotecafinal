package com.iesmb.bibliteca.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "libros")
@Getter //loombok genera getters, setters y constructores con argumentos y sin argumentos de forma automatica con estas anotaciones
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Debe completar un titulo")
    private String titulo;

    @Size(min=5,max=750,message= "La sinópsis debe poseer entre 5 y 750 caractéres")
    private String sinopsis;

    //@NotEmpty(message = "Debe cargar una portada")
    private String imagen;
    @Min(value = 1,message="Seleccione una edición")
    @Max(value = 10000, message="Cantidad máxima excedida")
    private int edicion;

    private boolean activo = true;

    @Min(value = 1,message="La cantidad debe tener un minimo de 1")
    @Max(value = 10000, message="La cantidad debe ser menor a 1000")
    private short cantidad;

    //Relación con autor
    @NotNull(message="Autor requerido.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_autor", nullable = false)
    private Autor autor;

    //Relación con Género
    @NotNull(message="Genero requerido.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_genero", nullable = false)
    private Genero genero;


}
