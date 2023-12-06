package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.input.UsuarioDTOInput;
import org.example.service.UsuarioService;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.put;

public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UsuarioController() {
        get("/usuarios", (request, response) -> {
            response.type("application.json");
            response.status(200);
            String json = objectMapper.writeValueAsString(usuarioService.listar());
            return json;
        });

        get("/usuario/:id", (request, response) -> {
            response.type("application.json");
            String idParam = request.params("id");
            Long id = Long.valueOf(idParam);
            String json = objectMapper.writeValueAsString(usuarioService.buscar(id));
            return json;
        });

        post("/usuario", (request, response) -> {
            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.inserir(usuarioDTOInput);
            response.type("application/json");
            response.status(201);
            return "Usuário criado";
        });

        put("/usuario", (request, response) -> {
            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.alterar(usuarioDTOInput);
            response.type("application/json");
            response.status(200);
            return "Usuário alterado";
        });

        delete("/usuario/:id", (request, response) -> {
            response.type("application.json");
            String idParam = request.params("id");
            Long id = Long.valueOf(idParam);
            usuarioService.excluir(id);
            response.status(200);
            return "Usuário excluído";
        });
    }
}
