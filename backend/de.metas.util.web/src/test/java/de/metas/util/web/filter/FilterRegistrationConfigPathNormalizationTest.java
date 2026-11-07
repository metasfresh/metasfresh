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

package de.metas.util.web.filter;

import de.metas.util.web.MetasfreshRestAPIConstants;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.pattern.PathPatternParser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Drives requests through the app server's own path-normalizing filters (every parameterless
 * {@code FilterRegistrationBean} bean of {@link FilterRegistrationConfig}, in registration order) into a real
 * {@code RequestMappingHandlerMapping} that uses {@link PathPatternParser} — the Spring Boot 3 default
 * ({@code spring.mvc.pathmatch.matching-strategy=path_pattern_parser}).
 * <p>
 * The request is shaped like Tomcat delivers it behind a reverse proxy that maps {@code /app/api/...} to
 * {@code /api//...}: the raw {@code requestURI} keeps the double slash, while the decoded {@code servletPath} is
 * already normalized by the container.
 */
class FilterRegistrationConfigPathNormalizationTest
{
	private MockMvc mockMvc;

	@RestController
	@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2)
	static class DummyRestController
	{
		@GetMapping("/version")
		public String version() {return "OK";}
	}

	@BeforeEach
	void beforeEach() throws Exception
	{
		AdempiereTestHelper.get().init();

		final StandaloneMockMvcBuilder builder = MockMvcBuilders.standaloneSetup(new DummyRestController())
				.setPatternParser(new PathPatternParser());
		for (final FilterRegistrationBean<?> registration : parameterlessFilterRegistrations(new FilterRegistrationConfig()))
		{
			builder.addFilter(registration.getFilter(), registration.getUrlPatterns().toArray(new String[0]));
		}
		mockMvc = builder.build();
	}

	static List<FilterRegistrationBean<?>> parameterlessFilterRegistrations(final Object configuration) throws Exception
	{
		final List<Method> beanMethods = Arrays.stream(configuration.getClass().getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(Bean.class))
				.filter(method -> method.getParameterCount() == 0)
				.filter(method -> FilterRegistrationBean.class.isAssignableFrom(method.getReturnType()))
				.toList();

		final List<FilterRegistrationBean<?>> result = new java.util.ArrayList<>();
		for (final Method beanMethod : beanMethods)
		{
			result.add((FilterRegistrationBean<?>)beanMethod.invoke(configuration));
		}
		result.sort(Comparator.comparingInt(FilterRegistrationBean::getOrder));
		return result;
	}

	private static MockHttpServletRequestBuilder tomcatLikeGet(final String rawRequestURI, final String normalizedServletPath)
	{
		// NOTE: MockMvcRequestBuilders.get(String) collapses "//" while building the URI (UriComponentsBuilder), so we set the raw URI explicitly afterwards
		return get(normalizedServletPath).with(request -> {
			request.setRequestURI(rawRequestURI);
			request.setServletPath(normalizedServletPath);
			return request;
		});
	}

	@Test
	void plainPath()
	{
		assertOK(tomcatLikeGet("/api/v2/version", "/api/v2/version"));
	}

	@Test
	void trailingSlash()
	{
		assertOK(tomcatLikeGet("/api/v2/version/", "/api/v2/version/"));
	}

	@Test
	void doubleSlashAfterApi_asSentByReverseProxy()
	{
		assertOK(tomcatLikeGet("/api//v2/version", "/api/v2/version"));
	}

	@Test
	void multipleDuplicateSlashes()
	{
		assertOK(tomcatLikeGet("/api//v2///version", "/api/v2/version"));
	}

	@Test
	void doubleSlashAndTrailingSlash()
	{
		assertOK(tomcatLikeGet("/api//v2/version/", "/api/v2/version/"));
	}

	private void assertOK(final MockHttpServletRequestBuilder request)
	{
		try
		{
			mockMvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().string("OK"));
		}
		catch (final Exception e)
		{
			throw new AssertionError(e);
		}
	}
}
