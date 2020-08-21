package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
