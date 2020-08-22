package br.com.zaqueucavalcante.ecommercespringjava.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Product;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ProductRepository;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> entity = repository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
