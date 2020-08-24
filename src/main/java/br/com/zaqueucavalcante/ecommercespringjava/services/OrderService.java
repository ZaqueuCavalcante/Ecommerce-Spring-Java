package br.com.zaqueucavalcante.ecommercespringjava.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Order;
import br.com.zaqueucavalcante.ecommercespringjava.entities.enums.PaymentStatus;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.OrderRepository;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	public Order findById(Long id) {
		Optional<Order> entity = orderRepository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Transactional
	public Order insert(Order order) {
		order.setId(null);
		order.setInstant(Instant.now());
		order.getPayment().setStatus(PaymentStatus.PENDING);
		order.getPayment().setOrder(order);
		return orderRepository.save(order);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void delete(Long id) {
		try {
			orderRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Order update(Long id, Order updatedOrder) {
		try {
			Order order = orderRepository.getOne(id);
			updateOrder(order, updatedOrder);
			return orderRepository.save(order);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateOrder(Order order, Order updatedOrder) {
		order.setStatus(updatedOrder.getStatus());
	}
}
