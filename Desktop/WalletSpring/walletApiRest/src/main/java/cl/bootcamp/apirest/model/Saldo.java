package cl.bootcamp.apirest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "saldo")
public class Saldo {
    
    @Id
    @Column(name = "idsaldo")
    private long saldoId;
    
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "saldo")
    private double saldo;
    
    @Column(name = "moneda_currency_id")
    private int currency;
    
    // Getters and setters
    public long getSaldoId() {
        return saldoId;
    }

    public void setSaldoId(long saldoId) {
        this.saldoId = saldoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }
}
