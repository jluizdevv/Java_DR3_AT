package com.example.meuprojeto.test;

import com.example.meuprojeto.dto.UsuarioDTOInput;
import com.example.meuprojeto.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;


import static org.junit.Assert.assertEquals;

public class ServiceTest {

    @Test
    public void testInsercaoUsuario() {
        UsuarioService usuarioService = new UsuarioService();
        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setNome("Novo Usuário");
        usuarioDTOInput.setEmail("novo.usuario@example.com");

        usuarioService.inserir(usuarioDTOInput);

        assertEquals(1, usuarioService.listar().size());
    }

    @Test
    public void testListagemUsuariosAPI() throws IOException {
        String apiUrl = "http://localhost:4567/usuarios";
        assertEquals(200, fazerRequisicaoHttp(apiUrl, "GET").getResponseCode());
    }

    @Test
    public void testInsercaoUsuarioAPI() throws IOException {
        String randomUserApiUrl = "https://randomuser.me/api/";
        UsuarioDTOInput usuarioDTOInput = obterUsuarioAleatorio(randomUserApiUrl);

        String apiUrl = "http://localhost:4567/usuarios";
        HttpURLConnection connection = fazerRequisicaoHttp(apiUrl, "POST");
        enviarJsonNaRequisicao(connection, usuarioDTOInput);

        assertEquals(201, connection.getResponseCode());
    }

    private UsuarioDTOInput obterUsuarioAleatorio(String apiUrl) throws IOException {
        HttpURLConnection connection = fazerRequisicaoHttp(apiUrl, "GET");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(connection.getInputStream(), UsuarioDTOInput.class);
    }

    private HttpURLConnection fazerRequisicaoHttp(String apiUrl, String metodo) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(metodo);
        connection.setDoOutput(true);
        return connection;
    }

    private void enviarJsonNaRequisicao(HttpURLConnection connection, Object body) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInputString = objectMapper.writeValueAsString(body);

        try (java.io.OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }
}
