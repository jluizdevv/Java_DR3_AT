package com.example.meuprojeto.controller;

import com.example.meuprojeto.dto.UsuarioDTOInput;
import com.example.meuprojeto.dto.UsuarioDTOOutput;
import com.example.meuprojeto.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.List;

import static spark.Spark.*;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        // Inicializa objectMapper no construtor
        this.objectMapper = new ObjectMapper();
    }

    public void iniciarSpark() {
        // Configuração do endpoint para listagem de usuários
        get("/usuarios", respostasRequisicoes);

        // Resto do código...
    }

    private Route respostasRequisicoes = (Request request, Response response) -> {
        response.type("application/json");

        // Certifique-se de que usuarioService está inicializado antes de utilizá-lo
        if (usuarioService == null) {
            response.status(500);  // Internal Server Error
            return "Erro interno do servidor: usuarioService não inicializado";
        }

        // Restante do código...

        // Endpoint para listagem de usuários
        if (request.pathInfo().equals("/usuarios") && request.requestMethod().equals("GET")) {
            List<UsuarioDTOOutput> usuarios = usuarioService.listar();
            response.status(200);
            return objectMapper.writeValueAsString(usuarios);
        }

        // Endpoint para busca de um usuário pelo ID
        if (request.pathInfo().matches("/usuarios/\\d+") && request.requestMethod().equals("GET")) {
            int id = Integer.parseInt(request.params(":id"));
            UsuarioDTOOutput usuario = usuarioService.buscar(id);
            if (usuario != null) {
                response.status(200);
                return usuario;
            } else {
                response.status(404);
                return "Usuário não encontrado";
            }
        }

        // Endpoint para exclusão de um usuário pelo ID
        if (request.pathInfo().matches("/usuarios/\\d+") && request.requestMethod().equals("DELETE")) {
            int id = Integer.parseInt(request.params(":id"));
            usuarioService.excluir(id);
            response.status(204);
            return "";
        }

        // Endpoint para inserção de um novo usuário
        if (request.pathInfo().equals("/usuarios") && request.requestMethod().equals("POST")) {
            try {
                UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
                usuarioService.inserir(usuarioDTOInput);
                response.status(201);
                return "";
            } catch (IOException e) {
                response.status(400);  // Bad Request
                return "Erro ao processar o corpo da solicitação JSON";
            }
        }

        // Endpoint para atualização de um usuário
        if (request.pathInfo().equals("/usuarios") && request.requestMethod().equals("PUT")) {
            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.alterar(usuarioDTOInput);
            response.status(204);
            return "";
        }

        // Outros endpoints podem ser adicionados conforme necessário

        response.status(404);
        return "Endpoint não encontrado";
    };
}
