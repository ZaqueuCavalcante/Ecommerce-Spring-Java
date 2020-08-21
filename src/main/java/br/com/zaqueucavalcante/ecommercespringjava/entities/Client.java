package br.com.zaqueucavalcante.ecommercespringjava.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zaqueucavalcante.ecommercespringjava.entities.enums.UserType;

@Entity
@Table(name = "client_table")
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;
	private Integer type;
	private String cpfOrCnpj;
	
	@ElementCollection
	@CollectionTable(name = "phone_table")
	private Set<String> phones = new HashSet<>();
	
	@OneToMany(mappedBy = "client")
	private Set<Address> adresses = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();

	public Client() {}
	
	public Client(Long id, String name, String email, UserType type, String cpfOrCnpj) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.type = type.getCode();
		this.cpfOrCnpj = cpfOrCnpj;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public UserType getType() {
		return UserType.valueOf(type);
	}

	public void setType(UserType type) {
		this.type = type.getCode();
	}

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
	}

	public List<Order> getOrders() {
		return orders;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void addPhone(String phone) {
		phones.add(phone);
	}
	
	public void addPhones(List<String> phonesList) {
		phones.addAll(phonesList);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void addAddress(Address address) {
		adresses.add(address);
	}
	
	public void addAdresses(List<Address> addressList) {
		adresses.addAll(addressList);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
