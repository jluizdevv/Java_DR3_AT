package com.example.meuprojeto.service;

import com.example.meuprojeto.dto.UsuarioDTOInput;
import com.example.meuprojeto.dto.UsuarioDTOOutput;
import com.example.meuprojeto.model.Usuario;
import org.modelmapper.ModelMapper;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    public List<UsuarioDTOOutput> listar() {
        Type listType = new TypeToken<List<UsuarioDTOOutput>>() {}.getType();
        return modelMapper.map(listaUsuarios, listType);
    }

    public void inserir(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(usuario);
    }

    public void alterar(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.removeIf(u -> u.getId() == usuario.getId());
        listaUsuarios.add(usuario);
    }

    public UsuarioDTOOutput buscar(int id) {
        return listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .orElse(null);
    }

    public void excluir(int id) {
        listaUsuarios.removeIf(u -> u.getId() == id);
    }
}
