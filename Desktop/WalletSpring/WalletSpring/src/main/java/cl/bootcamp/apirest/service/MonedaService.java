package cl.bootcamp.apirest.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.bootcamp.apirest.dao.MonedaDao;
import cl.bootcamp.apirest.model.Moneda;

@Service
public class MonedaService implements IMonedaService {
	 private static final Logger log = LoggerFactory.getLogger(MonedaService.class);

	    @Autowired
	    private MonedaDao monedaDao;

	    public ResponseEntity<List<Moneda>> obtener() {
	        log.info("Entrando al método SucursalServiceImpl.obtener()");
	        List<Moneda> response = new ArrayList<>();
	        try {
	            response = (List<Moneda>) monedaDao.findAll();
	        } catch (Exception ex) {
	            log.error("Ocurrió un error en SucursalServiceImpl.obtener(): " + ex.getMessage());
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    public ResponseEntity<Moneda> guardar(Moneda moneda) {
	        // Implementation of guardar method if needed
	        return null;
	    }
}
