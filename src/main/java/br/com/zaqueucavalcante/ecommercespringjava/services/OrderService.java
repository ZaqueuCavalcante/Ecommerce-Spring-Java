package br.com.zaqueucavalcante.ecommercespringjava.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Order;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.OrderRepository;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Order> findAll() {
		return repository.findAll();
	}
	
	public Order findById(Long id) {
		Optional<Order> entity = repository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
