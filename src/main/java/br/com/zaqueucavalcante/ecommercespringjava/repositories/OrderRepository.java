package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Transactional(readOnly = true)
	Page<Order> findByClient(Client client, Pageable pageRequest);
}
