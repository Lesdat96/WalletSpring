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
import cl.bootcamp.apirest.model.Moneda;
import cl.bootcamp.apirest.model.Transaccion;
import cl.bootcamp.apirest.service.IMonedaService;
import cl.bootcamp.apirest.service.ISaldoService;
import cl.bootcamp.apirest.service.ITransaccionService;
import cl.bootcamp.apirest.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class TransactionController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private ISaldoService saldoService;

	@Autowired
	private IMonedaService monedaService;

	@Autowired
	private ITransaccionService transaccionService;

	@GetMapping("/transferir")
	public String transferir(Model model, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			return "redirect:/";
		}

		List<Transaccion> transacciones = transaccionService.getTransaccionesBySenderId(userId);
		ResponseEntity<Usuario> usuario = usuarioService.obtenerUsuarioPorId(userId);
		ResponseEntity<List<Usuario>> usuarios = usuarioService.obtener();
		ResponseEntity<List<Moneda>> monedas = monedaService.obtener();
		
		model.addAttribute("usuario", usuario.getBody());
		model.addAttribute("usuarios", usuarios.getBody());
		model.addAttribute("monedas", monedas.getBody());
		model.addAttribute("transacciones", transacciones);

		return "transferir";
	}

	@PostMapping("/realizar-transaccion")
	public String realizarTransaccion(@RequestParam("sender_id") int senderId,
			@RequestParam("receiver_id") int receiverId, 
			@RequestParam("importe") double valor,
			@RequestParam("moneda") int currency, 
			Saldo saldoModel, Model model,
			RedirectAttributes redirectAttributes) {

		ResponseEntity<Saldo> saldo_sender = saldoService.findByUserIdAndCurrency(senderId, currency);

		if (saldo_sender.getBody().getSaldo() < valor) {
			redirectAttributes.addFlashAttribute("error", "Saldo insuficiente");
			return "redirect:/transferir";
		}

		ResponseEntity<Saldo> saldo_receiver = saldoService.findByUserIdAndCurrency(receiverId, currency);
		Transaccion transaccion = new Transaccion();
		transaccion.setSenderId(senderId);
		transaccion.setReceiverId(receiverId);
		transaccion.setValor(valor);
		transaccion.setCurrency(currency);

		if (saldo_receiver.hasBody()) {
			try {
				saldoService.updateSaldo(receiverId, currency, saldo_receiver.getBody().getSaldo() + valor);
				redirectAttributes.addFlashAttribute("error", "Transferencia realizada con éxito");
			} catch (Exception e) {
				model.addAttribute("errorMensaje", "Error al realizar la transacción");
			}
		} else {
			saldoModel.setUserId(receiverId);
			saldoModel.setCurrency(currency);
			saldoModel.setSaldo(valor);
			saldoService.guardar(saldoModel);
			redirectAttributes.addFlashAttribute("error", "Transferencia realizada con éxito");
		}

		transaccionService.saveTransaccion(transaccion);
		saldoService.updateSaldo(senderId, currency, saldo_sender.getBody().getSaldo() - valor);
		return "redirect:/transferir";
	}

}
