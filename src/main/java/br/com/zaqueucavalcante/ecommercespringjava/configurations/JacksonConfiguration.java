package br.com.zaqueucavalcante.ecommercespringjava.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zaqueucavalcante.ecommercespringjava.entities.BoletoPayment;
import br.com.zaqueucavalcante.ecommercespringjava.entities.CreditCardPayment;

@Configuration
@Profile("test")
public class JacksonConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(BoletoPayment.class);
				objectMapper.registerSubtypes(CreditCardPayment.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}
