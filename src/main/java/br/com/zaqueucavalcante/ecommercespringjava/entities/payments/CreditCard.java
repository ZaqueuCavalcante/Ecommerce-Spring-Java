package br.com.zaqueucavalcante.ecommercespringjava.entities.payments;

import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@JsonTypeName("CreditCardPayment")
@Table(name = "credit_card_payment_table")
public class CreditCard extends Payment {

	private static final long serialVersionUID = 1L;
	
	private Integer parcelsNumber;
	
	public CreditCard() {}

	public CreditCard(Long id, Instant instant, Order order, Integer parcelsNumber) {
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
