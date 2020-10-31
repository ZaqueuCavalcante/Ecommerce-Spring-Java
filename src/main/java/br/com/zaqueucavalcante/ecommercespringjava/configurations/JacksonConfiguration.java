package br.com.zaqueucavalcante.ecommercespringjava.configurations;

import br.com.zaqueucavalcante.ecommercespringjava.entities.payments.Boleto;
import br.com.zaqueucavalcante.ecommercespringjava.entities.payments.CreditCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
@Profile("test")
public class JacksonConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(Boleto.class);
				objectMapper.registerSubtypes(CreditCard.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}

}
