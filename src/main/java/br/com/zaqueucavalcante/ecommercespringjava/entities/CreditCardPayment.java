package br.com.zaqueucavalcante.ecommercespringjava.entities;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("CreditCardPayment")
@Table(name = "credit_card_payment_table")
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
