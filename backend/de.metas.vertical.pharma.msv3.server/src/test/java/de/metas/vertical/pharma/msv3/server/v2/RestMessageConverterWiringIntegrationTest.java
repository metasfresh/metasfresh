package de.metas.vertical.pharma.msv3.server.v2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import de.metas.vertical.pharma.msv3.server.JsonConfiguration;
import de.metas.vertical.pharma.msv3.server.v2.IntegrationTest.IntegrationTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import static org.assertj.core.api.Assertions.assertThat;

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
 * Pins the wiring of the real msv3 server {@link de.metas.vertical.pharma.msv3.server.Application} context (same setup as {@link IntegrationTest}):
 * Spring MVC must use the lenient converter bean from {@link JsonConfiguration}.
 * <p>
 * Fails if the converter bean is no longer defined in the application context (e.g. {@link JsonConfiguration} is removed or no longer scanned),
 * or if Spring MVC bypasses it, e.g. because {@code @EnableWebMvc} or a {@code WebMvcConfigurationSupport} subclass is (re-)added to msv3 main code,
 * as {@code SwaggerConfig} had it before the springdoc migration.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = IntegrationTestConfiguration.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class RestMessageConverterWiringIntegrationTest
{
	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@Autowired
	private MappingJackson2HttpMessageConverter converterBean;

	@Test
	void springMvc_uses_the_lenient_converter_bean()
	{
		assertThat(converterBean.getObjectMapper().isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES))
				.as("the REST converter must tolerate unknown request properties")
				.isFalse();

		// not "the first Jackson converter": Spring Data's ProjectingJackson2HttpMessageConverter comes first, but it only reads @ProjectedPayload interfaces
		assertThat(requestMappingHandlerAdapter.getMessageConverters())
				.as("Spring MVC must use the converter bean, not converters of its own")
				.anySatisfy(converter -> assertThat(converter).isSameAs(converterBean));
	}
}
