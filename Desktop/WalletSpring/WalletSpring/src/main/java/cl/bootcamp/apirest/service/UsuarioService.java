package cl.bootcamp.apirest.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.bootcamp.apirest.dao.UsuarioDao;
import cl.bootcamp.apirest.model.Usuario;
import cl.bootcamp.apirest.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsuarioService implements IUsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    
   	@Autowired
    private PasswordEncoder passwordEncoder;
   	
    @Autowired
    private UsuarioDao usuarioDao;
    

    @Override
    public ResponseEntity<List<Usuario>> obtener() {
        List<Usuario> response = new ArrayList<>();
        try {
            response = (List<Usuario>) usuarioDao.findAll();
        } catch (Exception ex) {
            log.error("Ocurrió un error en SucursalServiceImpl.obtener(): " + ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

 
    @Override
    public ResponseEntity<Usuario> guardar(Usuario usuario) {
        try {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            Usuario usuarioGuardado = usuarioDao.save(usuario);
            return ResponseEntity.ok(usuarioGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
        
    }

    @Override
    public ResponseEntity<Usuario> autenticar(String username, String contrasena) {
        Usuario usuarioEncontrado = usuarioDao.findByUsername(username);
        if (usuarioEncontrado != null && passwordEncoder.matches(contrasena, usuarioEncontrado.getContrasena())) {
            return new ResponseEntity<>(usuarioEncontrado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    public ResponseEntity<Usuario> obtenerUsuarioPorId(int id) {
        Optional<Usuario> usuario = usuarioDao.findById(id);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
