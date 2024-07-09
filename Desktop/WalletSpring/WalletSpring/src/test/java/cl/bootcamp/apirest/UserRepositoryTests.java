package cl.bootcamp.apirest;
import static org.assertj.core.api.Assertions.assertThat;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import cl.bootcamp.apirest.dao.UsuarioDao;
import cl.bootcamp.apirest.model.Usuario;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UsuarioDao repo;
     
    // test methods go below
    @Test
    public void testCreateUser() {
    	Usuario user = new Usuario();
    	user.setUsername("Usuario2");
    	user.setCorreoElectronico("ravikumar@gmail.com");
    	user.setContrasena("ravi2020");
    	user.setNombre("Ravi");
     
    	Usuario savedUser = repo.save(user);
     
    	Usuario existUser = entityManager.find(Usuario.class, savedUser.getUserId());
     
    	assertThat(user.getCorreoElectronico()).isEqualTo(existUser.getCorreoElectronico());
     
}
}