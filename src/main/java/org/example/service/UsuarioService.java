package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.input.UsuarioDTOInput;
import org.example.dto.output.UsuarioDTOOutput;
import org.example.model.Usuario;

import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UsuarioService {
    private final List<Usuario> listaUsuarios = new ArrayList<>();
    private final ModelMapper modelMapper = new ModelMapper();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public void inserir(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        usuario.setId(listaUsuarios.size() + 1);
        listaUsuarios.add(usuario);
    }

    public List<UsuarioDTOOutput> listar() {
        return listaUsuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTOInput gerarUsuarioAleatorio() throws IOException {
        URL randomUserUrl = new URL("https://randomuser.me/api/");
        HttpURLConnection randomUserConnection = (HttpURLConnection) randomUserUrl.openConnection();
        randomUserConnection.setRequestMethod("GET");

        JsonNode jsonNode = new ObjectMapper().readTree(randomUserConnection.getInputStream());

        String nome = jsonNode.at("/results/0/name/first").asText();
        String sobrenome = jsonNode.at("/results/0/name/last").asText();

        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setNome(nome + " " + sobrenome);

        return usuarioDTOInput;
    }

    public Optional<UsuarioDTOOutput> buscar(long id) {
        Optional<Usuario> usuarioOptional = listaUsuarios.stream()
                .filter(usuario -> usuario.getId() == id)
                .findFirst();

        return usuarioOptional.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class));
    }

    public void alterar(UsuarioDTOInput usuarioDTOInput) {
        long id = usuarioDTOInput.getId();
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(usuario -> usuario.getId() == id)
                .findFirst();

        usuarioExistente.ifPresent(usuario -> {
            modelMapper.map(usuarioDTOInput, usuario);
        });
    }

    public void excluir(long id) {
        listaUsuarios.removeIf(usuario -> usuario.getId() == id);
    }
}
