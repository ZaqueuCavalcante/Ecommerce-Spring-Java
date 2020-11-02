package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Category;
import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Transactional(readOnly = true)
	Page<Product> findDistinctByNameContainingAndCategoriesIn(
			String productName, List<Category> categoryList, Pageable pageRequest);

}
