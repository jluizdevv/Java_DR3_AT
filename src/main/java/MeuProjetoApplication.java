package com.example.meuprojeto;

import com.example.meuprojeto.controller.UsuarioController;
import com.example.meuprojeto.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeuProjetoApplication {

    public static void main(String[] args) {

        UsuarioService usuarioService = new UsuarioService();
        ObjectMapper objectMapper = new ObjectMapper();


        UsuarioController usuarioController = new UsuarioController(usuarioService, objectMapper);
        usuarioController.iniciarSpark();
    }
}
