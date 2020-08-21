package br.com.zaqueucavalcante.ecommercespringjava.entities;

import java.time.Instant;

import javax.persistence.Entity;

@Entity
public class CreditCardPayment extends Payment {

	private static final long serialVersionUID = 1L;
	
	private Integer parcelsNumber;
	
	public CreditCardPayment() {}

	public CreditCardPayment(Long id, Instant instant, Order order, Integer parcelsNumber) {
		super(id, instant, order);
		this.parcelsNumber = parcelsNumber;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Integer getParcelsNumber() {
		return parcelsNumber;
	}

	public void setParcelsNumber(Integer parcelsNumber) {
		this.parcelsNumber = parcelsNumber;
	}
}
