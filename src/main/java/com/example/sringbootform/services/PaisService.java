package com.example.sringbootform.services;

import com.example.sringbootform.models.domain.Pais;

import java.util.List;

public interface PaisService {

    public List<Pais> listar();
    public Pais obtenerPorId(Integer id);

}
