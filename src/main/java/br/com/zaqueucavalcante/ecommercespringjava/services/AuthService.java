package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {
	
	private final ClientRepository clientRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final EmailService emailService;

	public AuthService(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
		this.clientRepository = clientRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void sendNewPassword(String email) {
		Client client = clientRepository.findByEmail(email);
		checkIfClientExists(client);
		
		String newPassword = getNewPassword();
		client.setPassword(passwordEncoder.encode(newPassword));
		
		clientRepository.save(client);
		emailService.sendNewPasswordEmail(client, newPassword);
		
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void checkIfClientExists(Client client) {
		if (client == null) {
			throw new DatabaseException("Client not found.");
		}
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private String getNewPassword() {
		int passwordLength = 10;
		char[] password = new char[passwordLength];
		for (int i=0; i<passwordLength; i++) {
			password[i] = getRandomChar();
		}
		return new String(password);
	}
	
	private char getRandomChar() {
		Random random = new Random();
		int opt = random.nextInt(3);
		if (opt == 0) {
			return getRandomCharBetween(48, 10);
		}
		else if (opt == 1) {
			return getRandomCharBetween(65, 26);
		}
		else {
			return getRandomCharBetween(97, 26);
		}
	}
	
	private char getRandomCharBetween(int initialCharCode, int charsRange) {
		Random random = new Random();
		return (char) (initialCharCode + random.nextInt(charsRange));
	}

}
