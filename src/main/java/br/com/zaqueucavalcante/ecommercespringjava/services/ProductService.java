package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Product;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ProductRepository;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		Optional<Product> entity = productRepository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Product insert(Product product) {
		product.setId(null);
		return productRepository.save(product);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Product update(Long id, Product updatedProduct) {
		try {
			Product product = productRepository.getOne(id);
			updateProduct(product, updatedProduct);
			return productRepository.save(product);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateProduct(Product product, Product updatedProduct) {
		product.setName(updatedProduct.getName());
	}

}
