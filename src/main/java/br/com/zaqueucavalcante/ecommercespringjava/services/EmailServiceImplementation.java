package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import org.springframework.mail.SimpleMailMessage;

public class EmailServiceImplementation implements EmailService {

    @Override
    public void sendOrderConfirmationEmail(Order order) {

    }

    @Override
    public void sendEmail(SimpleMailMessage message) {

    }

    @Override
    public void sendNewPasswordEmail(Client client, String newPassword) {

    }

}
