package de.metas.vertical.pharma.msv3.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Configuration
public class JsonConfiguration
{
	/**
	 * General-purpose mapper (e.g. RabbitMQ messages, see {@link AMQPConfiguration}). Strict: unknown JSON properties are rejected.
	 */
	@Bean
	public ObjectMapper jsonObjectMapper()
	{
		return newJsonObjectMapper();
	}

	/**
	 * REST message converter: like {@link #jsonObjectMapper()}, but tolerant of unknown request properties, as the REST layer was before the springdoc migration.
	 * Back then, springfox's {@code @EnableWebMvc} (on {@link SwaggerConfig}) made Spring MVC use its own default converter, which ignores unknown properties.
	 * Without {@code @EnableWebMvc}, Spring Boot's converter would use the strict {@link #jsonObjectMapper()}; it backs off in favour of this bean.
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		return new MappingJackson2HttpMessageConverter(newJsonObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
	}

	private static ObjectMapper newJsonObjectMapper()
	{
		final ObjectMapper jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.findAndRegisterModules();
		return jsonObjectMapper;
	}
}
