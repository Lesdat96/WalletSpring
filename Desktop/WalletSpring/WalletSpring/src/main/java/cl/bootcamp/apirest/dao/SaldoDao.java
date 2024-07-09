package cl.bootcamp.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cl.bootcamp.apirest.model.Saldo;
import jakarta.transaction.Transactional;

public interface SaldoDao extends JpaRepository<Saldo, Long> {
    // Método para buscar por userId y currencyId
    Saldo findByUserIdAndCurrency(int userId, int currency);
    @Transactional
    @Modifying
    @Query("UPDATE Saldo s SET s.saldo = :newSaldo WHERE s.userId = :userId AND s.currency = :currency")
    int updateSaldoByUserIdAndCurrency(int userId, int currency, double newSaldo);
}