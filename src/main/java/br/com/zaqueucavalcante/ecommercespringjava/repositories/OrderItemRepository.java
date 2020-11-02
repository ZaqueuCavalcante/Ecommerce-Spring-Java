package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
