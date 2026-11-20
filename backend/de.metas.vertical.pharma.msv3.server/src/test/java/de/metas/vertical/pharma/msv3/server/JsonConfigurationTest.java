package de.metas.vertical.pharma.msv3.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

/**
 * Spring MVC slice (Boot's MVC + Jackson auto-configuration, no app context, no DB) with the msv3 server's {@link JsonConfiguration}.
 * It pins the configuration class's behaviour, not the wiring of the msv3 {@link Application}.
 */
class JsonConfigurationTest
{
	private static final String JSON_WITH_UNKNOWN_PROPERTY = "{\"name\":\"abc\",\"value\":7,\"someUnknownProperty\":\"x\"}";

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(
					JacksonAutoConfiguration.class,
					HttpMessageConvertersAutoConfiguration.class,
					WebMvcAutoConfiguration.class))
			.withUserConfiguration(JsonConfiguration.class, EchoRestController.class);

	// plain public-field POJO so Jackson binds by field access
	public static class EchoDto
	{
		public String name;
		public int value;
	}

	@RestController
	static class EchoRestController
	{
		@PostMapping("/echo")
		public EchoDto echo(@RequestBody final EchoDto request)
		{
			return request;
		}
	}

	/**
	 * Inbound REST tolerates unknown properties, as it did with springfox's {@code @EnableWebMvc} before the springdoc migration.
	 */
	@Test
	void requestBody_with_unknown_property_is_bound()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(post("/echo")
							.contentType(MediaType.APPLICATION_JSON)
							.content(JSON_WITH_UNKNOWN_PROPERTY))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value("abc"))
					.andExpect(jsonPath("$.value").value(7));
		});
	}

	/**
	 * Pins that, under Boot's MVC auto-configuration, Spring MVC uses the converter bean of {@link JsonConfiguration}.
	 * <p>
	 * This runs in its own small context, not in the msv3 {@link Application} context, so it does not catch an {@code @EnableWebMvc} added
	 * elsewhere in msv3 main code: that is caught by {@code v2.RestMessageConverterWiringIntegrationTest}.
	 */
	@Test
	void springMvc_uses_the_converter_bean()
	{
		contextRunner.run(context -> {
			final MappingJackson2HttpMessageConverter converterBean = context.getBean(MappingJackson2HttpMessageConverter.class);
			final List<HttpMessageConverter<?>> mvcConverters = context.getBean(RequestMappingHandlerAdapter.class).getMessageConverters();

			assertThat(mvcConverters).anySatisfy(converter -> assertThat(converter).isSameAs(converterBean));
		});
	}

	/**
	 * The general-purpose mapper (RabbitMQ) is unchanged: still strict.
	 */
	@Test
	void jsonObjectMapper_rejects_unknown_properties()
	{
		contextRunner.run(context -> {
			final ObjectMapper jsonObjectMapper = context.getBean("jsonObjectMapper", ObjectMapper.class);

			assertThatThrownBy(() -> jsonObjectMapper.readValue(JSON_WITH_UNKNOWN_PROPERTY, EchoDto.class))
					.isInstanceOf(UnrecognizedPropertyException.class);
		});
	}
}
