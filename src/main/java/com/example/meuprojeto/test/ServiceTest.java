package com.example.meuprojeto.test;

import com.example.meuprojeto.dto.UsuarioDTOInput;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServiceTest {

    @Test
    public void testInserirUsuario() {
        com.example.meuprojeto.service.UsuarioService usuarioService = new com.example.meuprojeto.service.UsuarioService();
        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setId(1);
        usuarioDTOInput.setNome("Teste");
        usuarioDTOInput.setSenha("Senha123");

        usuarioService.inserir(usuarioDTOInput);

        assertEquals(1, usuarioService.listar().size());
    }
}
