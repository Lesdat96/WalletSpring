package cl.bootcamp.apirest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cl.bootcamp.apirest.model.Transaccion;

public interface ITransaccionService {
	public ResponseEntity<List<Transaccion>> obtener();
	public ResponseEntity<Transaccion> guardar(Transaccion usuario);
	Transaccion saveTransaccion(Transaccion transaccion);
	List<Transaccion> getTransaccionesBySenderId(Integer userId);
}
