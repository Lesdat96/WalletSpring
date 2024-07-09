package cl.bootcamp.apirest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cl.bootcamp.apirest.model.Usuario;

public interface IUsuarioService {
	public ResponseEntity<List<Usuario>> obtener();
	public ResponseEntity<Usuario> guardar(Usuario usuario);
	public ResponseEntity<Usuario> autenticar(String username, String contrasena);
	ResponseEntity<Usuario> obtenerUsuarioPorId(int id);
}
