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
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
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
	 * Restores trailing-slash tolerance for the webui-api REST endpoints (the {@code /rest/api/**} surface the
	 * React frontend drives). Spring 6.0 switched trailing-slash matching off by default and deprecated the
	 * {@code PathMatchConfigurer.setUseTrailingSlashMatch} switch (removed in Spring 7), so a request whose path ends in a slash
	 * — e.g. the frontend's {@code GET /rest/api/window/{windowId}/{documentId}/} document load — no longer
	 * matches {@code @GetMapping("/{windowId}/{documentId}")} and fails with {@code NoResourceFoundException}
	 * (404). That 404 breaks every document window in the frontend (the window never renders its fields), which
	 * is what hung all six {@code frontend webui test} shards to the 60-min CI timeout.
	 * <p>
	 * We normalize the path with Spring's {@link UrlHandlerFilter} in {@code wrapRequest} (mutate) mode — NOT a
	 * redirect, because the frontend XHR clients must not be bounced with a 308 — so the stripped path is visible
	 * to the whole filter chain (CORS, logging, auth) and the {@code DispatcherServlet}. Registered at
	 * {@link Ordered#HIGHEST_PRECEDENCE}{@code  + 1} (right after {@link #duplicateSlashRestFilter()}) so every downstream filter and the handler mapping see the normalized path.
	 * <p>
	 * This mirrors {@code de.metas.util.web.filter.FilterRegistrationConfig}, which restored the same tolerance on
	 * the app-server {@code /api} profile; the webui-api runs a separate Spring context and needs its own
	 * registration here. Behaviour-restoring (the SB2-era base {@code new_dawn_uat} tolerated trailing slashes)
	 * and forward-compatible.
	 */
	@Bean
	public FilterRegistrationBean<UrlHandlerFilter> trailingSlashRestUrlHandlerFilter()
	{
		final UrlHandlerFilter urlHandlerFilter = UrlHandlerFilter
				.trailingSlashHandler(URL_PATHPATTERN_REST)
				.wrapRequest()
				.build();

		final FilterRegistrationBean<UrlHandlerFilter> registrationBean = new FilterRegistrationBean<>(urlHandlerFilter);
		registrationBean.addUrlPatterns(URL_PATTERN_REST);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		return registrationBean;
	}

	/**
	 * Restores the Spring Boot 2 tolerance for duplicate slashes in the webui-api REST path. {@code frontend/docker/nginx/default.conf}'s
	 * {@code location /rest { proxy_pass .../rest/; }} forwards {@code /rest/api/x} as {@code /rest//api/x}.
	 * See {@link DuplicateSlashCollapsingFilter}; runs right before {@link #trailingSlashRestUrlHandlerFilter()}.
	 */
	@Bean
	public FilterRegistrationBean<DuplicateSlashCollapsingFilter> duplicateSlashRestFilter()
	{
		final FilterRegistrationBean<DuplicateSlashCollapsingFilter> registrationBean = new FilterRegistrationBean<>(new DuplicateSlashCollapsingFilter());
		registrationBean.addUrlPatterns(URL_PATTERN_REST);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
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