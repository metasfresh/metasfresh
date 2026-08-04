package de.metas.server.config;

import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	private static final String CACHE_CONTROL_NO_STORE = CacheControl.noStore().getHeaderValue();

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
	 * Forbids caching of the /api/v2 responses, which carry no freshness information at all and are therefore
	 * heuristically cacheable. An endpoint that wants caching opts out by setting its own Cache-Control.
	 * <p>
	 * Knowingly not covered: replies that never reach this innermost filter (UserAuthTokenFilter's 401, an
	 * audit-matched ApiAuditFilter reply): both short-circuit at a higher filter precedence, before this one runs.
	 */
	@Bean
	public FilterRegistrationBean<Filter> apiNoStoreCacheControlFilter()
	{
		final FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(new ApiNoStoreCacheControlFilter());
		// the very constant ApiAuditFilter registers on, so "the same pattern" stays true by construction
		registration.addUrlPatterns(MetasfreshRestAPIConstants.URL_PATTERN_API_V2);
		// spring's default for a FilterRegistrationBean, pinned because the javadoc above reasons about it
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}

	private static class ApiNoStoreCacheControlFilter implements Filter
	{
		@Override
		public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
		{
			if (!(response instanceof HttpServletResponse))
			{
				chain.doFilter(request, response);
				return;
			}

			final HttpServletResponse httpResponse = (HttpServletResponse)response;

			// Up-front, not after the chain: setHeader is a silent no-op once the response is committed, and the
			// large-body endpoints (the order / shipment / invoice PDF downloads) commit while writing their body.
			httpResponse.setHeader(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_NO_STORE);

			chain.doFilter(request, new CacheControlDefaultReplacingResponse(httpResponse));
		}
	}

	/**
	 * Makes an endpoint's own {@code Cache-Control} REPLACE the up-front default of {@link #apiNoStoreCacheControlFilter()}
	 * instead of appending to it, so that an opting-out endpoint does not emit two contradicting values.
	 */
	private static class CacheControlDefaultReplacingResponse extends HttpServletResponseWrapper
	{
		private CacheControlDefaultReplacingResponse(@NonNull final HttpServletResponse delegate)
		{
			super(delegate);
		}

		@Override
		public void addHeader(final String name, final String value)
		{
			if (HttpHeaders.CACHE_CONTROL.equalsIgnoreCase(name))
			{
				super.setHeader(name, value);
			}
			else
			{
				super.addHeader(name, value);
			}
		}
	}
}
