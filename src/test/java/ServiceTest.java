import org.example.Main;
import org.example.dto.input.UsuarioDTOInput;
import org.example.dto.output.UsuarioDTOOutput;
import org.example.service.UsuarioService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

   @Test
   public void testeInsercao() throws IOException {
       UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();

       usuarioDTOInput.setNome("João");
       usuarioDTOInput.setSenha("123");


       UsuarioService usuarioService = new UsuarioService();
       usuarioService.inserir(usuarioDTOInput);

       List<UsuarioDTOOutput> listaUsuarios = usuarioService.listar();
       assertEquals(1, listaUsuarios.size());

       UsuarioDTOOutput usuarioInserido = listaUsuarios.get(0);
       System.out.println("ID: " + usuarioInserido.getId());
       System.out.println("Nome: " + usuarioInserido.getNome());
   }

   @Test
    public void testeListagem() {
       UsuarioService usuarioService = new UsuarioService();

       List<UsuarioDTOOutput> listaUsuarios = usuarioService.listar();
       assertEquals(0, listaUsuarios.size());
   }

   @Test
    public void testeInsercaoRandom() throws IOException {
       UsuarioService usuarioService = new UsuarioService();

       UsuarioDTOInput usuarioDTOInput = usuarioService.gerarUsuarioAleatorio();

       usuarioService.inserir(usuarioDTOInput);

       List<UsuarioDTOOutput> listaUsuarios = usuarioService.listar();
       assertEquals(1, listaUsuarios.size());


       UsuarioDTOOutput usuarioInserido = listaUsuarios.get(0);
       System.out.println("ID: " + usuarioInserido.getId());
       System.out.println("Nome: " + usuarioInserido.getNome());
   }
}
