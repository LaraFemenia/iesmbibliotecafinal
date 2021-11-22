package com.iesmb.bibliteca.repositories;
import com.iesmb.bibliteca.entities.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioGenero extends JpaRepository<Genero, Long> {
}
