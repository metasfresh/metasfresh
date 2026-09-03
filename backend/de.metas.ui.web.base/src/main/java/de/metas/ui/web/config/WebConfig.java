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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
// @EnableWebMvc // NOTE: if u enable it, the swagger won't work!
public class WebConfig implements WebMvcConfigurer
{
	public static final String ENDPOINT_ROOT = "/rest/api";

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