package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.OrderItem;
import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Product;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.OrderItemRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.OrderRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.PaymentRepository;
import br.com.zaqueucavalcante.ecommercespringjava.security.UserSecurity;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.AuthorizationException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static br.com.zaqueucavalcante.ecommercespringjava.entities.payments.PaymentStatus.PENDING;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final ClientService clientService;
	private final ProductService productService;
	private final PaymentRepository paymentRepository;
	private final OrderItemRepository orderItemRepository;

	public OrderService(OrderRepository orderRepository, ClientService clientService,
						ProductService productService,
						PaymentRepository paymentRepository, OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.clientService = clientService;
		this.productService = productService;
		this.paymentRepository = paymentRepository;
		this.orderItemRepository = orderItemRepository;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	public Order findById(Long id) {
		Optional<Order> entity = orderRepository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	private void authorizationCheck(UserSecurity userLoggedIn) {
		if (userLoggedIn == null) {
			throw new AuthorizationException("Access denied.");
		}
	}
	
	public Page<Order> findPage(Integer pageNumber, Integer entitiesPerPage, String direction, String orderBy) {
		UserSecurity userLoggedIn = UserService.authenticated();
		authorizationCheck(userLoggedIn);
		PageRequest pageRequest = PageRequest.of(pageNumber, entitiesPerPage, Direction.valueOf(direction), orderBy);
		Client client = clientService.findById(userLoggedIn.getId());
		return orderRepository.findByClient(client, pageRequest);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Transactional
	public Order insert(Order order) {
		order.setId(null);
		order.setInstant(Instant.now());
		order.setPaymentStatus(PENDING);
		order.getPayment().setOrder(order);
		// emailService.sendOrderConfirmationEmail(order);
		//
		order = orderRepository.save(order);
		paymentRepository.save(order.getPayment());
		for (OrderItem orderItem : order.getItems()) {
			orderItem.setDiscountPercentage(0.0);
			Product product = productService.findById(orderItem.getProduct().getId());
			orderItem.setPrice(product.getPrice());
			orderItem.setOrder(order);
		}
		orderItemRepository.saveAll(order.getItems());
		//
		return order;
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
