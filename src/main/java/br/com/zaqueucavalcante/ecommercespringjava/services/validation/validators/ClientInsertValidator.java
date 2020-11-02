package br.com.zaqueucavalcante.ecommercespringjava.services.validation.validators;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientFullDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.resources.exceptions.FieldMessage;
import br.com.zaqueucavalcante.ecommercespringjava.services.validation.ClientInsert;
import br.com.zaqueucavalcante.ecommercespringjava.services.validation.utils.CpfAndCnpj;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

import static br.com.zaqueucavalcante.ecommercespringjava.entities.clients.ClientType.LEGAL_PERSON;
import static br.com.zaqueucavalcante.ecommercespringjava.entities.clients.ClientType.PHYSICAL_PERSON;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientFullDTO> {

	@Autowired
	private ClientRepository repository;
	
	@Override
	public void initialize(ClientInsert clientInsert) {}

	@Override
	public boolean isValid(ClientFullDTO client, ConstraintValidatorContext context) {
		List<FieldMessage> fieldMessages = new ArrayList<>();
		cpfValidation(client, fieldMessages);
		cnpjValidation(client, fieldMessages);
		emailValidation(client, fieldMessages);
		buildConstraintViolation(context, fieldMessages);
		return fieldMessages.isEmpty();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void cpfValidation(ClientFullDTO client, List<FieldMessage> fieldMessages) {
		boolean clientCpfIsInvalid = !CpfAndCnpj.isValidCPF(client.getCpfOrCnpj());
		if (client.is(PHYSICAL_PERSON) && clientCpfIsInvalid) {
			fieldMessages.add(new FieldMessage("cpfOrCnpj", "Invalid cpf."));
		}
	}
	
	private void cnpjValidation(ClientFullDTO client, List<FieldMessage> fieldMessages) {
		boolean clientCnpjIsInvalid = !CpfAndCnpj.isValidCNPJ(client.getCpfOrCnpj());
		if (client.is(LEGAL_PERSON) && clientCnpjIsInvalid) {
			fieldMessages.add(new FieldMessage("cpfOrCnpj", "Invalid cnpj."));
		}
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void emailValidation(ClientFullDTO client, List<FieldMessage> fieldMessages) {
		Client clientWithSameEmail = repository.findByEmail(client.getEmail());
		if (clientWithSameEmail != null) {
			fieldMessages.add(new FieldMessage("email", "This email is already registered."));
		}
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void buildConstraintViolation(ConstraintValidatorContext context, List<FieldMessage> fieldMessages) {
		for (FieldMessage fieldMessage : fieldMessages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
					.addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
	}

}
