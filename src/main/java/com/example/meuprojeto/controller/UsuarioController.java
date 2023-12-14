package com.example.meuprojeto.controller;

import com.example.meuprojeto.dto.UsuarioDTOInput;
import com.example.meuprojeto.dto.UsuarioDTOOutput;
import com.example.meuprojeto.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService, ObjectMapper objectMapper) {
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
    }

    public void iniciarSpark() {

        get("/usuarios", this::listarUsuarios, objectMapper::writeValueAsString);


        get("/usuarios/:id", this::buscarUsuario, objectMapper::writeValueAsString);


        delete("/usuarios/:id", this::excluirUsuario, objectMapper::writeValueAsString);


        post("/usuarios", this::inserirUsuario, objectMapper::writeValueAsString);


        put("/usuarios", this::atualizarUsuario, objectMapper::writeValueAsString);
    }

    private List<UsuarioDTOOutput> listarUsuarios(Request request, Response response) {
        List<UsuarioDTOOutput> usuarios = usuarioService.listar();
        response.status(200);
        return usuarios;
    }

    private UsuarioDTOOutput buscarUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        UsuarioDTOOutput usuario = usuarioService.buscar(id);
        if (usuario != null) {
            response.status(200);
            return usuario;
        } else {
            response.status(404);
            return null;
        }
    }

    private String excluirUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        usuarioService.excluir(id);
        response.status(204);
        return "";
    }

    private String inserirUsuario(Request request, Response response) {
        try {
            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.inserir(usuarioDTOInput);
            response.status(201);
            return "";
        } catch (Exception e) {
            response.status(400);
            return "Corpo da solicitação JSON inválido";
        }
    }

    private String atualizarUsuario(Request request, Response response) {
        try {
            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.alterar(usuarioDTOInput);
            response.status(204);
            return "";
        } catch (Exception e) {
            response.status(400);
            return "Corpo da solicitação JSON inválido";
        }
    }
}