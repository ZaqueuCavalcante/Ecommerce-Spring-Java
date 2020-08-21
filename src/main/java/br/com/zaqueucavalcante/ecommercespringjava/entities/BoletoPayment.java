package br.com.zaqueucavalcante.ecommercespringjava.entities;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class BoletoPayment extends Payment {

	private static final long serialVersionUID = 1L;
	
	private Date dueDate;
	private Date paymentDate;
	
	public BoletoPayment() {
	}

	public BoletoPayment(Long id, Instant instant, Order order, Date dueDate, Date paymentDate) {
		super(id, instant, order);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
}
