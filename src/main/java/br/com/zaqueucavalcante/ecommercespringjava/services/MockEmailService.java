package br.com.zaqueucavalcante.ecommercespringjava.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage message) {
		logger.info("Simulating email sending...");
		logger.info(message.toString());
		logger.info("Email sent!");
	}

}
