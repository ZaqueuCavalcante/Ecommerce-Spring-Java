package br.com.zaqueucavalcante.ecommercespringjava.services.validation.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.resources.exceptions.FieldMessage;
import br.com.zaqueucavalcante.ecommercespringjava.services.validation.ClientUpdate;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClientRepository repository;
	
	@Override
	public void initialize(ClientUpdate clientUpdate) {
	}

	@Override
	public boolean isValid(ClientDTO client, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> uriMap = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong(uriMap.get("id"));
		
		List<FieldMessage> fieldMessageList = new ArrayList<>();
		
		emailValidation(client, fieldMessageList, uriId);

		buildConstraintViolation(context, fieldMessageList);
		
		return fieldMessageList.isEmpty();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void emailValidation(ClientDTO client, List<FieldMessage> fieldMessageList, Long uriId) {
		Client clientWithSameEmail = repository.findByEmail(client.getEmail());
		if (clientWithSameEmail != null && !clientWithSameEmail.getId().equals(uriId)) {
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
