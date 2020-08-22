package br.com.zaqueucavalcante.ecommercespringjava.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Client> findAll() {
		return repository.findAll();
	}

	public Client findById(Long id) {
		Optional<Client> entity = repository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Client insert(Client client) {
		client.setId(null);
		return repository.save(client);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Client update(Long id, Client updatedClient) {
		try {
			Client client = repository.getOne(id);
			updateUser(client, updatedClient);
			return repository.save(client);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateUser(Client client, Client updatedClient) {
		client.setName(updatedClient.getName());
		client.setEmail(updatedClient.getEmail());
	}
}
