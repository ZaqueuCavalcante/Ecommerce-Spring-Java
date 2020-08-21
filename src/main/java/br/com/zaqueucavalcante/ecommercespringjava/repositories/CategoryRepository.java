package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
