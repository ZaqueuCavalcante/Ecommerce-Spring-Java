package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
