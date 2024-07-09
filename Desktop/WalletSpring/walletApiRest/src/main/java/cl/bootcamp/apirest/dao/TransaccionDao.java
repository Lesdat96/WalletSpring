package cl.bootcamp.apirest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.bootcamp.apirest.model.Transaccion;

@Repository
public interface TransaccionDao extends JpaRepository<Transaccion, Long> {
	List<Transaccion> findBySenderId(Integer senderId);
}
