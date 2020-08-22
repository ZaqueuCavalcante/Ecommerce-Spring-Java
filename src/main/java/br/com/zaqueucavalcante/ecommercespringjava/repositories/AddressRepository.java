package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
