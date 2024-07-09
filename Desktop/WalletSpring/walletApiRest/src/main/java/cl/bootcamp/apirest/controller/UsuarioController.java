package cl.bootcamp.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.bootcamp.apirest.model.Usuario;
import cl.bootcamp.apirest.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import cl.bootcamp.apirest.dao.UsuarioDao;
import cl.bootcamp.apirest.service.IMonedaService;

@Controller
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired 
	private UsuarioDao usuarioDao;
	
    @PostMapping("/signin")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("contrasena") String contrasena,
                               Model model, HttpSession session,
                               RedirectAttributes redirectAttributes) {
        ResponseEntity<Usuario> isAuthenticated = (ResponseEntity<Usuario>) usuarioService.autenticar(username, contrasena);
        if (isAuthenticated != null ) {
        	
        	Usuario user = usuarioDao.findByUsername(username);
        	session.setAttribute("userId", user.getUserId());
            return "redirect:menu";
        } else {
            return "ingreso";
        }
    }
    
    @PostMapping("/register/process_register")
    public String processRegistration(@ModelAttribute("Usuario") Usuario registrationDtoW, Model model) {
    	Usuario user = usuarioDao.findByUsername(registrationDtoW.getUsername());
    	if(user != null) {
    		model.addAttribute("mensaje", "El usuario ya existe");
    		return "redirect:/registro";
    	} else {
    	
    	usuarioService.guardar(registrationDtoW);
    	Usuario user1 = usuarioDao.findByUsername(registrationDtoW.getUsername());
        System.out.println(registrationDtoW.getUsername());
        model.addAttribute("usuario", user1);
        return "ingreso";
    	}
    }
}
