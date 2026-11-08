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

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
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
 * Test support for driving requests through the path-normalizing filters that a {@code @Configuration} class registers.
 */
@UtilityClass
public class PathNormalizationTestSupport
{
	/**
	 * @return a {@link MockMvc} for {@code controller}, with a real {@code RequestMappingHandlerMapping} that uses {@link PathPatternParser}
	 * (the Spring Boot 3 default) and every filter of the given configuration's parameterless {@code FilterRegistrationBean} {@code @Bean} methods, in registration order
	 */
	public static MockMvc mockMvcWithParameterlessFilters(@NonNull final Object controller, @NonNull final Object filterConfiguration) throws Exception
	{
		final StandaloneMockMvcBuilder builder = MockMvcBuilders.standaloneSetup(controller)
				.setPatternParser(new PathPatternParser());
		for (final FilterRegistrationBean<?> registration : parameterlessFilterRegistrations(filterConfiguration))
		{
			builder.addFilter(registration.getFilter(), registration.getUrlPatterns().toArray(new String[0]));
		}
		return builder.build();
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

	/**
	 * @return a GET request shaped like Tomcat delivers it: the raw {@code requestURI} keeps duplicate slashes, the decoded {@code servletPath} is already normalized by the container
	 */
	public static MockHttpServletRequestBuilder tomcatLikeGet(@NonNull final String rawRequestURI, @NonNull final String normalizedServletPath)
	{
		// NOTE: MockMvcRequestBuilders.get(String) collapses "//" while building the URI (UriComponentsBuilder), so we set the raw URI explicitly afterwards
		return get(normalizedServletPath).with(request -> {
			request.setRequestURI(rawRequestURI);
			request.setServletPath(normalizedServletPath);
			return request;
		});
	}

	public static void assertOK(@NonNull final MockMvc mockMvc, @NonNull final MockHttpServletRequestBuilder request)
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
