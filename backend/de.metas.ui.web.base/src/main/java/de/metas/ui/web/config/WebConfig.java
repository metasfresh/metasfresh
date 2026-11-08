/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.ui.web.CORSFilter;
import de.metas.ui.web.WebuiURLs;
import de.metas.util.web.filter.DuplicateSlashCollapsingFilter;
import de.metas.util.web.filter.PathNormalizingFilterRegistrations;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.filter.UrlHandlerFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
// @EnableWebMvc // NOTE: if u enable it, the swagger won't work!
public class WebConfig implements WebMvcConfigurer
{
	public static final String ENDPOINT_ROOT = "/rest/api";

	/** {@link org.springframework.web.util.pattern.PathPattern} matching every webui-api REST path (any depth) under the {@code /rest} root that {@link #ENDPOINT_ROOT} lives in. */
	private static final String URL_PATHPATTERN_REST = "/rest/**";

	/** Servlet URL pattern the {@link #trailingSlashRestUrlHandlerFilter()} and {@link #duplicateSlashRestFilter()} are mapped to; the webui-api serves its whole REST surface under {@code /rest}. */
	private static final String URL_PATTERN_REST = "/rest/*";

	/**
	 * Restores trailing-slash tolerance for the webui-api REST endpoints (the {@code /rest/api/**} surface the React frontend drives),
	 * e.g. the frontend's {@code GET /rest/api/window/{windowId}/{documentId}/} document load must match {@code @GetMapping("/{windowId}/{documentId}")}.
	 * See {@link PathNormalizingFilterRegistrations#trailingSlashRegistration(String, String)}.
	 * <p>
	 * The app server's {@code /api} gets the same filters in {@code de.metas.util.web.filter.FilterRegistrationConfig};
	 * the webui-api runs a separate Spring context and needs its own registration here.
	 */
	@Bean
	public FilterRegistrationBean<UrlHandlerFilter> trailingSlashRestUrlHandlerFilter()
	{
		return PathNormalizingFilterRegistrations.trailingSlashRegistration(URL_PATTERN_REST, URL_PATHPATTERN_REST);
	}

	/**
	 * Restores the Spring Boot 2 tolerance for duplicate slashes in the webui-api REST path. {@code frontend/docker/nginx/default.conf}'s
	 * {@code location /rest { proxy_pass .../rest/; }} forwards {@code /rest/api/x} as {@code /rest//api/x}.
	 * See {@link DuplicateSlashCollapsingFilter}.
	 */
	@Bean
	public FilterRegistrationBean<DuplicateSlashCollapsingFilter> duplicateSlashRestFilter()
	{
		return PathNormalizingFilterRegistrations.duplicateSlashRegistration(URL_PATTERN_REST);
	}

	@Bean
	public CookieSerializer cookieSerializer()
	{
		final DefaultCookieSerializer serializer = new DefaultCookieSerializer();

		final WebuiURLs webuiURLs = WebuiURLs.newInstance();
		if (webuiURLs.isCrossSiteUsageAllowed())
		{
			serializer.setSameSite("None");
			serializer.setUseSecureCookie(true);
		}
		return serializer;
	}

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer)
	{
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	public void extendMessageConverters(final List<HttpMessageConverter<?>> converters)
	{
		// prevent errors on serializing de.metas.ui.web.config.WebuiExceptionHandler#getErrorAttributes
		final MediaType javascriptMediaType = MediaType.valueOf("application/javascript");
		for (final HttpMessageConverter<?> converter : converters)
		{
			if (converter instanceof MappingJackson2HttpMessageConverter)
			{
				final MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
				if (jacksonConverter.getSupportedMediaTypes().contains(javascriptMediaType))
				{
					break;
				}

				final List<MediaType> supportedMediaTypes = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
				supportedMediaTypes.add(javascriptMediaType);
				jacksonConverter.setSupportedMediaTypes(supportedMediaTypes);

				break;
			}
		}

	}


	@Bean
	public Filter corsFilter()
	{
		return new CORSFilter();
	}

	@Bean
	public Filter addMissingHeadersFilter()
	{
		return new Filter()
		{
			@Override
			public void init(final FilterConfig filterConfig)
			{
			}

			@Override
			public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
			{
				try
				{
					chain.doFilter(request, response);
				}
				finally
				{
					if (response instanceof HttpServletResponse)
					{
						final HttpServletResponse httpResponse = (HttpServletResponse)response;

						//
						// If the Cache-Control is not set then set it to no-cache.
						// In this way we precisely tell to browser that it shall not cache our REST calls.
						// The Cache-Control is usually defined by features like ETag
						if (!httpResponse.containsHeader("Cache-Control"))
						{
							httpResponse.setHeader("Cache-Control", "no-cache");
						}
					}
				}
			}

			@Override
			public void destroy()
			{
			}
		};
	}
}