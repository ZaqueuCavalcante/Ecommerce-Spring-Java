package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

	void sendOrderConfirmationEmail(Order order);
	
	void sendEmail(SimpleMailMessage message);
	
	void sendNewPasswordEmail(Client client, String newPassword);

}
