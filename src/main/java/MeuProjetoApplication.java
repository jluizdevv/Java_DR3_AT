package com.example.meuprojeto;

import com.example.meuprojeto.controller.UsuarioController;
import com.example.meuprojeto.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeuProjetoApplication {

    public static void main(String[] args) {
        // Crie instâncias de UsuarioService e ObjectMapper
        UsuarioService usuarioService = new UsuarioService();
        ObjectMapper objectMapper = new ObjectMapper();

        // Passe as instâncias como parâmetros ao criar UsuarioController
        UsuarioController usuarioController = new UsuarioController(usuarioService, objectMapper);
        usuarioController.iniciarSpark();
    }
}
