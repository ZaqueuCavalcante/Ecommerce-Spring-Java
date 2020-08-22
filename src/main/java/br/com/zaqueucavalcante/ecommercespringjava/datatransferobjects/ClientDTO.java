package br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;

public class ClientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotEmpty(message = "Required field.")
	@Length(min = 5, max = 142, message = "The name must be 5-142 characters.")
	private String name;
	
	@NotEmpty(message = "Required field.")
	@Email(message = "Invalid email.")
	private String email;
	
	private Integer type;
	private String cpfOrCnpj;
	
	public ClientDTO() {
	}

	public ClientDTO(Client client) {
		id = client.getId();
		name = client.getName();
		email = client.getEmail();
		type = client.getType().getCode();
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
}
