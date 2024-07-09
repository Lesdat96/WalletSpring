package cl.bootcamp.apirest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import cl.bootcamp.apirest.model.Usuario;
import cl.bootcamp.apirest.model.Moneda;
import cl.bootcamp.apirest.model.Saldo;
import cl.bootcamp.apirest.service.IMonedaService;
import cl.bootcamp.apirest.service.ISaldoService;
import cl.bootcamp.apirest.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private ISaldoService saldoService;

	@Autowired
	private IMonedaService monedaService;

	@GetMapping("/")
	public String signIn(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "ingreso";
	}

	@GetMapping("/menu")
	public String menu(Model model, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId == null) {
			return "redirect:/";
		}
		
		ResponseEntity<Usuario> usuario = usuarioService.obtenerUsuarioPorId(userId);
		ResponseEntity<List<Moneda>> monedas = monedaService.obtener();
		ResponseEntity<Saldo> saldoClp = saldoService.findByUserIdAndCurrency(userId, 1);
		ResponseEntity<Saldo> saldoUsd = saldoService.findByUserIdAndCurrency(userId, 2);
		ResponseEntity<Saldo> saldoEur = saldoService.findByUserIdAndCurrency(userId, 3);

		String saldoClpString = saldoClp.hasBody() ? String.valueOf(saldoClp.getBody().getSaldo()) : "0.0";
		String saldoUsdString = saldoUsd.hasBody() ? String.valueOf(saldoUsd.getBody().getSaldo()) : "0.0";
		String saldoEurString = saldoEur.hasBody() ? String.valueOf(saldoEur.getBody().getSaldo()) : "0.0";

		model.addAttribute("usuario", usuario.getBody());
		model.addAttribute("saldo_clp", saldoClpString);
		model.addAttribute("saldo_usd", saldoUsdString);
		model.addAttribute("saldo_eur", saldoEurString);
		model.addAttribute("monedas", monedas.getBody());
		
		return "menu";
	}

	@GetMapping("/registro")
	public String showRegistrationForm(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "crear_user";
	}
}
