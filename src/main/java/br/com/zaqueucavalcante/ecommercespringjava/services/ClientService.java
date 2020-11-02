package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientDTO;
import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientFullDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.addresses.Address;
import br.com.zaqueucavalcante.ecommercespringjava.entities.addresses.City;
import br.com.zaqueucavalcante.ecommercespringjava.entities.addresses.State;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.ClientType;
import br.com.zaqueucavalcante.ecommercespringjava.entities.users.UserProfile;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.security.UserSecurity;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.AuthorizationException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

	private final ClientRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;

	public ClientService(ClientRepository repository, BCryptPasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Client> findAll() {
		return repository.findAll();
	}

	public Client findById(Long id) {
		authorizationCheck(id);
		Optional<Client> entity = repository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	private void authorizationCheck(Long id) {
		UserSecurity user = UserService.authenticated();
		if (user == null || !user.hasRole(UserProfile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Access denied.");
		}
	}

	public Page<Client> findPage(Integer pageNumber, Integer entitiesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(pageNumber, entitiesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Transactional
	public Client insert(Client client) {
		client.setId(null);
		return repository.save(client);
	}

	public Client fromDTO(ClientDTO clientDTO) {
		Long id = clientDTO.getId();
		String name = clientDTO.getName();
		String email = clientDTO.getEmail();
		ClientType type = ClientType.valueOf(clientDTO.getType());
		String cpfOrCnpj = clientDTO.getCpfOrCnpj();
		return new Client(id, name, email, type, cpfOrCnpj);
	}

	public Client fromDTO(ClientFullDTO clientFullDTO) {
		Long id = clientFullDTO.getId();
		String name = clientFullDTO.getName();
		String email = clientFullDTO.getEmail();
		String password = passwordEncoder.encode(clientFullDTO.getPassword());
		ClientType type = ClientType.valueOf(clientFullDTO.getType());
		String cpfOrCnpj = clientFullDTO.getCpfOrCnpj();
		Client client = new Client(id, name, email, type, cpfOrCnpj);
		client.setPassword(password);

		Long cityId = clientFullDTO.getCityId();
		String cityName = "New New York";
		State cityState = new State();
		City city = new City(cityId, cityName, cityState);
		
		String street = clientFullDTO.getStreet();
		String avenue = clientFullDTO.getAvenue();
		String zipCode = clientFullDTO.getZipCode();
		
		Address	address = new Address(1L, city, street, avenue, zipCode, client);	 

		client.addAddress(address);
		client.addPhone(clientFullDTO.getPhone());
		
		return client;
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
		client.setType(updatedClient.getType());
		client.setCpfOrCnpj(updatedClient.getCpfOrCnpj());
	}

}
