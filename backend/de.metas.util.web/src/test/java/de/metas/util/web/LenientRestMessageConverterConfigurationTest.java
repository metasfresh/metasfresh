/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring MVC slice (Boot's MVC + Jackson auto-configuration, no app context, no DB), set up like {@code ServerBoot} / {@code WebRestApiApplication}:
 * a strict {@code @Primary} shared mapper plus the imported {@link LenientRestMessageConverterConfiguration}.
 * It pins the configuration class's behaviour, not the wiring of the real applications (see {@link #springMvc_uses_the_converter_bean()}).
 */
class LenientRestMessageConverterConfigurationTest
{
	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(
					JacksonAutoConfiguration.class,
					HttpMessageConvertersAutoConfiguration.class,
					WebMvcAutoConfiguration.class))
			.withUserConfiguration(AppLikeConfig.class, EchoRestController.class);

	@Configuration
	@Import(LenientRestMessageConverterConfiguration.class)
	static class AppLikeConfig
	{
		@Bean
		@Primary
		public ObjectMapper jsonObjectMapper()
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper();
		}
	}

	// plain public-field POJO so Jackson binds by field access, no lombok @Jacksonized
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
	 * Request bodies with properties the backend does not model (e.g. a price {@code currencyCode}, a mobile {@code title} test-step label)
	 * must be bound, not rejected with a 400.
	 */
	@Test
	void requestBody_with_unknown_property_is_bound()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(post("/echo")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{\"name\":\"abc\",\"value\":7,\"currencyCode\":\"EUR\",\"title\":\"some step label\"}"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value("abc"))
					.andExpect(jsonPath("$.value").value(7));
		});
	}

	/**
	 * Pins that, under Boot's MVC auto-configuration, Spring MVC uses the converter bean of {@link LenientRestMessageConverterConfiguration}
	 * and that its mapper is not the strict shared one.
	 * <p>
	 * This runs in its own small context, not in {@code ServerBoot} / {@code WebRestApiApplication}. It does not catch a change to those apps:
	 * a missing {@code @Import} is caught by {@code ServerBootWiringTest} / {@code WebRestApiApplicationWiringTest}, and an
	 * {@code @EnableWebMvc} or {@code WebMvcConfigurationSupport} subclass in main code by the ArchUnit rule
	 * {@code MetasfreshArchRules.noCustomSpringMvcConfiguration()}.
	 */
	@Test
	void springMvc_uses_the_converter_bean()
	{
		contextRunner.run(context -> {
			final MappingJackson2HttpMessageConverter converterBean = context.getBean(MappingJackson2HttpMessageConverter.class);
			final List<HttpMessageConverter<?>> mvcConverters = context.getBean(RequestMappingHandlerAdapter.class).getMessageConverters();

			assertThat(mvcConverters).anySatisfy(converter -> assertThat(converter).isSameAs(converterBean));
			assertThat(converterBean.getObjectMapper())
					.as("the converter must not use the strict shared mapper")
					.isNotSameAs(JsonObjectMapperHolder.sharedJsonObjectMapper());
		});
	}
}
