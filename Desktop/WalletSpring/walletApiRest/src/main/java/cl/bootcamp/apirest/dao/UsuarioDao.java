package cl.bootcamp.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bootcamp.apirest.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Integer> {
    // Puedes definir métodos adicionales si los necesitas
	 Usuario findByUsername(String username);
}