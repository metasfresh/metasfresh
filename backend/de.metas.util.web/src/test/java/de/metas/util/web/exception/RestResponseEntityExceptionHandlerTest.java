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

package de.metas.util.web.exception;

import de.metas.Profiles;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring MVC slice (Boot's MVC auto-configuration, as in the app server) with the app server's {@link RestResponseEntityExceptionHandler}.
 */
class RestResponseEntityExceptionHandlerTest
{
	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(
					JacksonAutoConfiguration.class,
					HttpMessageConvertersAutoConfiguration.class,
					WebMvcAutoConfiguration.class))
			.withInitializer(context -> context.getEnvironment().setActiveProfiles(Profiles.PROFILE_App))
			.withUserConfiguration(RestResponseEntityExceptionHandler.class, DummyRestController.class);

	@RestController
	static class DummyRestController
	{
		@GetMapping("/api/v2/dummy")
		public String dummy() {return "OK";}
	}

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * With Boot's static resource handler on {@code /**}, an unknown route ends in a {@code NoResourceFoundException}.
	 */
	@Test
	void unknownRoute_isNotFound()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(get("/api/v2/doesNotExist"))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.errors[0].message").isNotEmpty());
		});
	}

	/**
	 * Without a static resource handler, an unknown route ends in a {@code NoHandlerFoundException}.
	 */
	@Test
	void unknownRoute_withoutStaticResourceHandler_isNotFound()
	{
		contextRunner
				.withPropertyValues("spring.web.resources.add-mappings=false")
				.run(context -> {
					final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

					mockMvc.perform(get("/api/v2/doesNotExist"))
							.andExpect(status().isNotFound())
							.andExpect(jsonPath("$.errors[0].message").isNotEmpty());
				});
	}

	@Test
	void knownRoute_isOK()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(get("/api/v2/dummy"))
					.andExpect(status().isOk());
		});
	}

	/**
	 * Other Spring MVC exceptions keep the catch-all's status, as before the Spring Boot 3 migration.
	 */
	@Test
	void unsupportedMethod_staysUnprocessableEntity()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			mockMvc.perform(delete("/api/v2/dummy"))
					.andExpect(status().isUnprocessableEntity());
		});
	}
}
