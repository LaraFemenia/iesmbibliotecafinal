package com.iesmb.bibliteca.repositories;
import com.iesmb.bibliteca.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RepositorioAutor extends JpaRepository<Autor, Long> {
}
