package org.example;

import org.example.controller.UsuarioController;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {
    public static void main(String[] args) throws IOException {
        inserirUsuario();

        UsuarioController usuarioController = new UsuarioController();
    }

    public static void inserirUsuario() throws IOException {
        URL urlObj = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection conexao = (HttpURLConnection) urlObj.openConnection();
        conexao.setRequestProperty("Accept", "application/json");
        conexao.setDoOutput(true);
        conexao.setRequestMethod("POST");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("userId", 1);
        jsonObject.put("title", "delectus aut autem");
        jsonObject.put("completed", true);

        String json = jsonObject.toString();
        conexao.getOutputStream().write(json.getBytes());

        BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
    }
}
