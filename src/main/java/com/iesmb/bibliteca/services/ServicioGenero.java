package com.iesmb.bibliteca.services;

import com.iesmb.bibliteca.entities.Genero;
import com.iesmb.bibliteca.repositories.RepositorioGenero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioGenero implements ServicioBase<Genero> {

    @Autowired
    private RepositorioGenero generoRep;

    @Override
    @Transactional
    public List<Genero> findAll() throws Exception {
        try {
            List<Genero> generos = this.generoRep.findAll();
            return generos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Genero findById(long id) throws Exception {
        try {
            Optional<Genero> opt = this.generoRep.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Genero saveOne(Genero entity) throws Exception {
        try {
            Genero genero = this.generoRep.save(entity);
            return genero;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Genero updateOne(Genero entity, long id) throws Exception {
        try {
            Optional<Genero> opt = this.generoRep.findById(id);
            Genero genero = opt.get();
            genero = this.generoRep.save(entity);
            return genero;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            Optional<Genero> opt = this.generoRep.findById(id);
            if (!opt.isEmpty()) {
                Genero genero = opt.get();
                genero.setActivo(!genero.isActivo());
                this.generoRep.save(genero);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
