package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public void sendOrderConfirmationEmail(Order order) {
		SimpleMailMessage message = prepareSimpleMessageFromOrder(order);
		sendEmail(message);
	}

	protected SimpleMailMessage prepareSimpleMessageFromOrder(Order order) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(order.getClient().getEmail());
		message.setFrom(sender);
		message.setSubject("Order confirmed! Code: " + order.getId());
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText(order.toString());
		return message;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public void sendNewPasswordEmail(Client client, String newPassword) {
		SimpleMailMessage message = prepareNewPasswordEmail(client, newPassword);
		sendEmail(message);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPassword) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(client.getEmail());
		message.setFrom(sender);
		message.setSubject("New password request.");
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText("New password: " + newPassword);
		return message;
	}

}
