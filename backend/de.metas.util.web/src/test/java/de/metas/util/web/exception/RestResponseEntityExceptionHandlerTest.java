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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import de.metas.Profiles;
import de.metas.util.Loggables;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import static org.assertj.core.api.Assertions.assertThat;
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

	private Logger handlerLogger;
	private Logger pageNotFoundLogger;
	private ListAppender<ILoggingEvent> logAppender;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		logAppender = new ListAppender<>();
		logAppender.start();
		handlerLogger = (Logger)LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);
		handlerLogger.addAppender(logAppender);
		pageNotFoundLogger = (Logger)LoggerFactory.getLogger(DispatcherServlet.PAGE_NOT_FOUND_LOG_CATEGORY);
		pageNotFoundLogger.addAppender(logAppender);
	}

	@AfterEach
	void afterEach()
	{
		handlerLogger.detachAppender(logAppender);
		pageNotFoundLogger.detachAppender(logAppender);
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

	/**
	 * An unknown route is logged as before the Spring Boot 3 migration, when Spring 5's {@code DispatcherServlet.noHandlerFound} logged it:
	 * one WARN line {@code No mapping for <method> <uri>} on the {@code PageNotFound} category, without stack trace; no ERROR.
	 */
	@Test
	void unknownRoute_isLoggedAsWarnWithoutStackTrace()
	{
		contextRunner.run(context -> {
			final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

			// no thread-bound loggable, as on a servlet thread (the test helper binds a console loggable that would swallow a handler log)
			try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(Loggables.nop()))
			{
				mockMvc.perform(get("/api/v2/doesNotExist"))
						.andExpect(status().isNotFound());
			}

			assertThat(logAppender.list)
					.singleElement()
					.satisfies(event -> {
						assertThat(event.getLoggerName()).isEqualTo(DispatcherServlet.PAGE_NOT_FOUND_LOG_CATEGORY);
						assertThat(event.getLevel()).isEqualTo(Level.WARN);
						assertThat(event.getFormattedMessage()).isEqualTo("No mapping for GET /api/v2/doesNotExist");
						assertThat(event.getThrowableProxy()).isNull();
					});
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
