package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
