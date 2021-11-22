package com.iesmb.bibliteca.services;

import com.iesmb.bibliteca.entities.Libro;
import com.iesmb.bibliteca.repositories.RepositorioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioLibro implements ServicioBase<Libro>{

    @Autowired //Autowire para indicar que tenemos inyeccion de dependencias
    private RepositorioLibro libroRep;

    @Override
    @Transactional //Esta anotación es por si ocurre un error en el caso de insertar o recuperar valores en la bd, se realice un rollback y no modifique los registros
    public List<Libro> findAll() throws Exception {
        try {
            List<Libro> entities = this.libroRep.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro findById(long id) throws Exception {
        try {
            Optional<Libro> opt = this.libroRep.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro saveOne(Libro entity) throws Exception {
        try {
            Libro libro = this.libroRep.save(entity);
            return libro;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro updateOne(Libro entity, long id) throws Exception {
        try {
            Optional<Libro> opt = this.libroRep.findById(id);
            Libro libro = opt.get();
            libro = this.libroRep.save(entity);
            return libro;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //Soft-delete. Cambia el estado de activo, no lo borra de la bd
    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            Optional<Libro> opt = this.libroRep.findById(id);
            if (!opt.isEmpty()) {
                Libro libro = opt.get();
                libro.setActivo(!libro.isActivo());
                this.libroRep.save(libro);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //Métodos de busqueda
    @Transactional
    public List<Libro> findAllByActivo() throws Exception{
        try {
            List<Libro> entities = this.libroRep.findAllByActivo();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Libro findByIdAndActivo(long id) throws Exception {
        try {
            Optional<Libro> opt = this.libroRep.findByIdAndActivo(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Libro> findByTitle(String q) throws Exception{
        try{
            List<Libro> entities = this.libroRep.findByTitle(q);
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }



}
