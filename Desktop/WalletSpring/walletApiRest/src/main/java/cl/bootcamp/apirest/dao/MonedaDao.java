package cl.bootcamp.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.bootcamp.apirest.model.Moneda;

public interface MonedaDao extends JpaRepository<Moneda, Long> {

}
