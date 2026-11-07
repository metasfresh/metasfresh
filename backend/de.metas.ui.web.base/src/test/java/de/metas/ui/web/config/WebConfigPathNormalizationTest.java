/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.config;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Drives requests through the webui-api's own path-normalizing filters (every parameterless
 * {@code FilterRegistrationBean} bean of {@link WebConfig}, in registration order) into a real
 * {@code RequestMappingHandlerMapping} that uses {@link PathPatternParser} — the Spring Boot 3 default.
 * <p>
 * The request is shaped like Tomcat delivers it behind {@code frontend/docker/nginx/default.conf}'s
 * {@code location /rest { proxy_pass .../rest/; }}, which forwards {@code /rest/api/...} as {@code /rest//api/...}:
 * the raw {@code requestURI} keeps the double slash, the decoded {@code servletPath} is normalized by the container.
 */
class WebConfigPathNormalizationTest
{
	private MockMvc mockMvc;

	@RestController
	@RequestMapping(WebConfig.ENDPOINT_ROOT + "/login")
	static class DummyRestController
	{
		@GetMapping("/isLoggedIn")
		public String isLoggedIn() {return "OK";}
	}

	@BeforeEach
	void beforeEach() throws Exception
	{
		final StandaloneMockMvcBuilder builder = MockMvcBuilders.standaloneSetup(new DummyRestController())
				.setPatternParser(new PathPatternParser());
		for (final FilterRegistrationBean<?> registration : parameterlessFilterRegistrations(new WebConfig()))
		{
			builder.addFilter(registration.getFilter(), registration.getUrlPatterns().toArray(new String[0]));
		}
		mockMvc = builder.build();
	}

	private static List<FilterRegistrationBean<?>> parameterlessFilterRegistrations(final Object configuration) throws Exception
	{
		final List<Method> beanMethods = Arrays.stream(configuration.getClass().getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(Bean.class))
				.filter(method -> method.getParameterCount() == 0)
				.filter(method -> FilterRegistrationBean.class.isAssignableFrom(method.getReturnType()))
				.toList();

		final List<FilterRegistrationBean<?>> result = new ArrayList<>();
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
		assertOK(tomcatLikeGet("/rest/api/login/isLoggedIn", "/rest/api/login/isLoggedIn"));
	}

	@Test
	void trailingSlash()
	{
		assertOK(tomcatLikeGet("/rest/api/login/isLoggedIn/", "/rest/api/login/isLoggedIn/"));
	}

	@Test
	void doubleSlashAfterRest_asSentByFrontendNginx()
	{
		assertOK(tomcatLikeGet("/rest//api/login/isLoggedIn", "/rest/api/login/isLoggedIn"));
	}

	@Test
	void doubleSlashAndTrailingSlash()
	{
		assertOK(tomcatLikeGet("/rest//api/login/isLoggedIn/", "/rest/api/login/isLoggedIn/"));
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
