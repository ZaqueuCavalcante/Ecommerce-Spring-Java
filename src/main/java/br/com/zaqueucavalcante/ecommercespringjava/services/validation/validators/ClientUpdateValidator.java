package br.com.zaqueucavalcante.ecommercespringjava.services.validation.validators;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.resources.exceptions.FieldMessage;
import br.com.zaqueucavalcante.ecommercespringjava.services.validation.ClientUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClientRepository repository;
	
	@Override
	public void initialize(ClientUpdate clientUpdate) {}

	@Override
	public boolean isValid(ClientDTO client, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> uriMap = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong(uriMap.get("id"));
		List<FieldMessage> fieldMessages = new ArrayList<>();
		emailValidation(client, fieldMessages, uriId);
		buildConstraintViolation(context, fieldMessages);
		return fieldMessages.isEmpty();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void emailValidation(ClientDTO client, List<FieldMessage> fieldMessages, Long uriId) {
		Client clientWithSameEmail = repository.findByEmail(client.getEmail());
		if (clientWithSameEmail != null && !clientWithSameEmail.getId().equals(uriId)) {
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
