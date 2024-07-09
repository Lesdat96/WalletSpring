package cl.bootcamp.apirest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cl.bootcamp.apirest.model.Moneda;

public interface IMonedaService {
	public ResponseEntity<List<Moneda>> obtener();
	public ResponseEntity<Moneda> guardar(Moneda moneda);
}