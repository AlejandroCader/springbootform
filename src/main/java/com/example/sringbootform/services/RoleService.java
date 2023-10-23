package com.example.sringbootform.services;

import com.example.sringbootform.models.domain.Role;

import java.util.List;

public interface RoleService {
    public List<Role> listar();
    public Role obtenerPorId(Integer id);
}
