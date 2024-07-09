package cl.bootcamp.apirest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cl.bootcamp.apirest.model.Saldo;

public interface ISaldoService {
	public ResponseEntity<List<Saldo>> obtener();
	public ResponseEntity<Saldo> guardar(Saldo saldo);
	ResponseEntity<Saldo> updateSaldo(int userId, int currency, double newSaldo);
	public ResponseEntity<Saldo> findByUserIdAndCurrency(int userId, int currency);
}
