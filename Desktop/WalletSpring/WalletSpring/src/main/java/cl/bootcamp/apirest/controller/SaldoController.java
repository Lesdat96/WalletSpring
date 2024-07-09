package cl.bootcamp.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import cl.bootcamp.apirest.model.Moneda;
import cl.bootcamp.apirest.model.Saldo;
import cl.bootcamp.apirest.model.Usuario;
import cl.bootcamp.apirest.service.ISaldoService;
import cl.bootcamp.apirest.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import cl.bootcamp.apirest.service.IMonedaService;

@Controller
public class SaldoController {
    
	@Autowired
	private ISaldoService saldoService;

	@Autowired
	private IUsuarioService usuarioService;
    
	@Autowired
	private IMonedaService monedaService;
	
    @PostMapping("/sumar-saldo")
    public String sumarSaldo(@RequestParam("user_id") Integer userId,
                             @RequestParam("saldo") Double saldo,
                             @RequestParam("moneda") int currency,
                             Saldo saldoModel, Model model) {
        ResponseEntity<Saldo> saldoEncontrado = saldoService.findByUserIdAndCurrency(userId, currency);
        
        if (saldoEncontrado.hasBody()) {
            Saldo saldoExistente = saldoEncontrado.getBody();
            saldoExistente.setSaldo(saldoExistente.getSaldo() + saldo);
            saldoService.updateSaldo(userId, currency, saldoExistente.getSaldo());
        } else {
            saldoModel.setUserId(userId);
            saldoModel.setCurrency(currency);
            saldoModel.setSaldo(saldo);
            saldoService.guardar(saldoModel);
        }
        
        return "redirect:menu";
    }
    
	@GetMapping("/retiro")
	public String retiro(Model model, HttpSession session) {
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
		return "restar_saldo";
	}
	
    @PostMapping("/restar-saldo")
    public String restarSaldo(@RequestParam("user_id") Integer userId,
                             @RequestParam("saldo") Double saldo,
                             @RequestParam("moneda") int currency,
                             Saldo saldoModel,
                             Model model,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
    	
        ResponseEntity<Saldo> saldoEncontrado = saldoService.findByUserIdAndCurrency(userId, currency);
        if (saldoEncontrado.hasBody()) {
        	Saldo saldoExistente = saldoEncontrado.getBody();
        	if (saldoExistente.getSaldo() < saldo) {
        		 redirectAttributes.addFlashAttribute("error", "Saldo insuficiente");
        		return "redirect:retiro";
        	} else {
            saldoExistente.setSaldo(saldoExistente.getSaldo() - saldo);
            saldoService.updateSaldo(userId, currency, saldoExistente.getSaldo());
            	return "redirect:retiro";
        	}
        }
        return "redirect:menu";
    }
}
