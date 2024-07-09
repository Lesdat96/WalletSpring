package cl.bootcamp.apirest.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import cl.bootcamp.apirest.dao.TransaccionDao;
import cl.bootcamp.apirest.model.Transaccion;

@Service
public class TransaccionService implements ITransaccionService {

    @Autowired
    private TransaccionDao transaccionDao;

    @Override
    public Transaccion saveTransaccion(Transaccion transaccion) {
        return transaccionDao.save(transaccion);
    }

    @Override
    public List<Transaccion> getTransaccionesBySenderId(Integer senderId) {
        return transaccionDao.findBySenderId(senderId);
    }

    @Override
    public ResponseEntity<List<Transaccion>> obtener() {
        List<Transaccion> transacciones = transaccionDao.findAll();
        return ResponseEntity.ok(transacciones);
    }

    @Override
    public ResponseEntity<Transaccion> guardar(Transaccion transaccion) {
        Transaccion nuevaTransaccion = transaccionDao.save(transaccion);
        return ResponseEntity.ok(nuevaTransaccion);
    }
}
