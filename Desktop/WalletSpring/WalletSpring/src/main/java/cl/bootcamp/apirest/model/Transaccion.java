package cl.bootcamp.apirest.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaccion")
public class Transaccion {
    
    @Id
    @Column(name = "id_transacción")
    private long saldoId;
    
    @Column(name = "sender_id")
    private int senderId;
    
    @Column(name = "recibe_id")
    private int receiverId;
    
    @Column(name = "valor")
    private double valor;
    
    @Column(name = "Moneda_id")
    private int currency;
    
    @Column(name = "transaccion_date")
    private LocalDateTime transactionDate;
    
    @PrePersist
    protected void onCreate() {
    	this.transactionDate = LocalDateTime.now();
    }

    // Getters and setters

    public long getSaldoId() {
        return saldoId;
    }

    public void setSaldoId(long saldoId) {
        this.saldoId = saldoId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
