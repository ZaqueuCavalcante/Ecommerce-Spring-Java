package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaqueucavalcante.ecommercespringjava.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
