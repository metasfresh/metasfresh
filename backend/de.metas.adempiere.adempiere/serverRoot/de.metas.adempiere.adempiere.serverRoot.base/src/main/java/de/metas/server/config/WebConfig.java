package de.metas.server.config;

import lombok.NonNull;
import org.compiere.Adempiere;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	@Override
	public void addCorsMappings(@NonNull final CorsRegistry registry)
	{
		// Disable CORS
		registry.addMapping("/**")
				.allowedMethods("*")
				.allowedHeaders("*");
	}

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer)
	{
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	/**
	 * Forbids caching of the REST API responses, unless the endpoint set a Cache-Control itself.
	 * <p>
	 * Without it these responses carry no freshness information and no validator at all, which leaves a
	 * caching client or intermediary free to apply heuristic freshness and keep serving a previous
	 * response for minutes without contacting us. That is silent data corruption for the session-global
	 * operator context the mobile UI re-reads on every screen mount (/workstation, /workplace,
	 * userWorkflows/trolley): the operator scans a new workstation and the screen keeps showing the old
	 * one, while the request never reaches the server.
	 * <p>
	 * Mirrors the webapi's addMissingHeadersFilter (de.metas.ui.web.config.WebConfig), which does the
	 * same for the webapi's REST calls; the app server had no equivalent.
	 */
	@Bean
	public FilterRegistrationBean<Filter> apiNoStoreCacheControlFilter()
	{
		final Filter filter = new Filter()
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
						// Set only when the endpoint did not decide for itself (e.g. ImageRestController's max-age).
						if (!httpResponse.containsHeader(HttpHeaders.CACHE_CONTROL))
						{
							httpResponse.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
						}
					}
				}
			}

			@Override
			public void destroy()
			{
			}
		};

		final FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(filter);
		registration.addUrlPatterns(Adempiere.ENDPOINT_API_V2 + "/*");
		return registration;
	}
}
