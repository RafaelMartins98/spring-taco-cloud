package tacos.messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import tacos.Order;

@Configuration
public class MessagingConfig {

	@Bean
	public MappingJackson2MessageConverter messageConverter() {

		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTypeIdPropertyName("_typeId");

		Map<String, Class<?>> mappings = new HashMap<>();
		mappings.put("order", Order.class);

		converter.setTypeIdMappings(mappings);

		return converter;

	}

}
