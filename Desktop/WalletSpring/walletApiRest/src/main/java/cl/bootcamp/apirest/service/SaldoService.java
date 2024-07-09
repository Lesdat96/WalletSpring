package cl.bootcamp.apirest.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.bootcamp.apirest.dao.SaldoDao;
import cl.bootcamp.apirest.model.Saldo;
import jakarta.transaction.Transactional;

@Service
public class SaldoService implements ISaldoService {
	private static final Logger log = LoggerFactory.getLogger(SaldoService.class);

	@Autowired
	private SaldoDao saldoDao;

	public ResponseEntity<List<Saldo>> obtener() {
		log.info("Entrando al método SucursalServiceImpl.obtener()");
		List<Saldo> response = new ArrayList<>();
		try {
			response = (List<Saldo>) saldoDao.findAll();
		} catch (Exception ex) {
			log.error("Ocurrió un error en SucursalServiceImpl.obtener(): " + ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Saldo> guardar(Saldo saldo) {
		log.info("Entrando al método SaldoService.guardar()");
		try {
			Saldo savedSaldo = saldoDao.save(saldo);
			return new ResponseEntity<>(savedSaldo, HttpStatus.CREATED);
		} catch (Exception ex) {
			log.error("Ocurrió un error en SaldoService.guardar(): " + ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


    @Override
    public ResponseEntity<Saldo> findByUserIdAndCurrency(int userId, int currency) {
        log.info("Buscando saldo para userId: {} y currency: {}", userId, currency);
        try {
            Saldo saldo = saldoDao.findByUserIdAndCurrency(userId, currency);
            if (saldo != null) {
                return new ResponseEntity<>(saldo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error("Error al buscar saldo: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    @Transactional
    public ResponseEntity<Saldo> updateSaldo(int userId, int currency, double newSaldo) {
        int updatedRows = saldoDao.updateSaldoByUserIdAndCurrency(userId, currency, newSaldo);
        if (updatedRows > 0) {
            Saldo updatedSaldo = saldoDao.findByUserIdAndCurrency(userId, currency);
            return new ResponseEntity<>(updatedSaldo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}