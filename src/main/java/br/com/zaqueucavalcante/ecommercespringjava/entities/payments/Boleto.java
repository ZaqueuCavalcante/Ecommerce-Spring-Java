package br.com.zaqueucavalcante.ecommercespringjava.entities.payments;

import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Date;

@Entity
@JsonTypeName("BoletoPayment")
@Table(name = "boleto_payment_table")
public class Boleto extends Payment {

	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date dueDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date paymentDate;
	
	public Boleto() {
	}

	public Boleto(Long id, Instant instant, Order order, Date dueDate) {
		super(id, instant, order);
		this.dueDate = dueDate;
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
