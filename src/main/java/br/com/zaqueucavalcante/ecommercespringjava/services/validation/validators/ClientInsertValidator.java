package br.com.zaqueucavalcante.ecommercespringjava.services.validation.validators;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientFullDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.enums.ClientType;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.resources.exceptions.FieldMessage;
import br.com.zaqueucavalcante.ecommercespringjava.services.validation.ClientInsert;
import br.com.zaqueucavalcante.ecommercespringjava.services.validation.utils.CpfAndCnpj;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientFullDTO> {

	@Autowired
	private ClientRepository repository;
	
	@Override
	public void initialize(ClientInsert clientInsert) {
	}

	@Override
	public boolean isValid(ClientFullDTO client, ConstraintValidatorContext context) {
		List<FieldMessage> fieldMessageList = new ArrayList<>();

		cpfValidation(client, fieldMessageList);
		cnpjValidation(client, fieldMessageList);
		
		emailValidation(client, fieldMessageList);

		buildConstraintViolation(context, fieldMessageList);
		
		return fieldMessageList.isEmpty();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void cpfValidation(ClientFullDTO client, List<FieldMessage> fieldMessageList) {
		boolean clientIsPhysicalPerson = client.getType().equals(ClientType.PHYSICAL_PERSON.getCode());
		boolean clientCpfIsNotValid = !CpfAndCnpj.isValidCPF(client.getCpfOrCnpj());
		if (clientIsPhysicalPerson && clientCpfIsNotValid) {
			fieldMessageList.add(new FieldMessage("cpfOrCnpj", "Invalid cpf."));
		}
	}
	
	private void cnpjValidation(ClientFullDTO client, List<FieldMessage> fieldMessageList) {
		boolean clientIsLegalPerson = client.getType().equals(ClientType.LEGAL_PERSON.getCode());
		boolean clientCnpjIsNotValid = !CpfAndCnpj.isValidCNPJ(client.getCpfOrCnpj());
		if (clientIsLegalPerson && clientCnpjIsNotValid) {
			fieldMessageList.add(new FieldMessage("cpfOrCnpj", "Invalid cnpj."));
		}
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void emailValidation(ClientFullDTO client, List<FieldMessage> fieldMessageList) {
		Client clientWithSameEmail = repository.findByEmail(client.getEmail());
		if (clientWithSameEmail != null) {
			fieldMessageList.add(new FieldMessage("email", "This email is already registered."));
		}
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void buildConstraintViolation(ConstraintValidatorContext context, List<FieldMessage> fieldMessageList) {
		for (FieldMessage fieldMessage : fieldMessageList) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
					.addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
	}
}
