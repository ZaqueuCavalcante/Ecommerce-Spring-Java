package br.com.zaqueucavalcante.ecommercespringjava.entities.clients;

import br.com.zaqueucavalcante.ecommercespringjava.entities.addresses.Address;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import br.com.zaqueucavalcante.ecommercespringjava.entities.users.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "client_table")
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Integer type;
	private String cpfOrCnpj;
	
	@ElementCollection
	@CollectionTable(name = "phone_table")
	private Set<String> phones = new HashSet<>();
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private Set<Address> addresses = new HashSet<>();
	
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "profile_table")
	private Set<Integer> profiles = new HashSet<>();
	
	public Client() {
		addProfile(UserProfile.CLIENT);
	}
	
	public Client(Long id, String name, String email, ClientType type, String cpfOrCnpj) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.type = type.getCode();
		this.cpfOrCnpj = cpfOrCnpj;
		addProfile(UserProfile.CLIENT);
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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientType getType() {
		return ClientType.valueOf(type);
	}

	public int getTypeCode() {
		return ClientType.valueOf(type).getCode();
	}

	public void setType(ClientType type) {
		this.type = type.getCode();
	}

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public List<Order> getOrders() {
		return orders;
	}
	
	public Set<UserProfile> getProfiles() {
		return profiles.stream().map(code -> UserProfile.valueOf(code)).collect(Collectors.toSet());
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
		addresses.add(address);
	}
	
	public void addAddresses(List<Address> addressList) {
		addresses.addAll(addressList);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public void addOrders(List<Order> orderList) {
		orders.addAll(orderList);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void addProfile(UserProfile userProfile) {
		profiles.add(userProfile.getCode());
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
