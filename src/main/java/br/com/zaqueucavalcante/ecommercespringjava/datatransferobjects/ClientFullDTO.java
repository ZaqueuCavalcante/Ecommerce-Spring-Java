package br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.ClientType;
import br.com.zaqueucavalcante.ecommercespringjava.services.validation.ClientInsert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClientInsert
public class ClientFullDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Required field.")
	@Length(min = 5, max = 142, message = "The name must be 5-142 characters.")
	private String name;

	@NotEmpty(message = "Required field.")
	@Email(message = "Invalid email.")
	private String email;
	
	@NotEmpty(message = "Required field.")
	private String password;

	@NotEmpty(message = "Required field.")
	private Integer type;
	
	@NotEmpty(message = "Required field.")
	private String cpfOrCnpj;

	private Long cityId;
	private String street;
	private String avenue;
	private String zipCode;
	
	private String phone;
	
	public ClientFullDTO() {}

	public ClientFullDTO(Client client) {
		id = client.getId();
		name = client.getName();
		email = client.getEmail();
		type = client.getTypeCode();
		cpfOrCnpj = client.getCpfOrCnpj();
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAvenue() {
		return avenue;
	}

	public void setAvenue(String avenue) {
		this.avenue = avenue;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public boolean is(ClientType type) {
		return this.type.equals(type.getCode());
	}



}
