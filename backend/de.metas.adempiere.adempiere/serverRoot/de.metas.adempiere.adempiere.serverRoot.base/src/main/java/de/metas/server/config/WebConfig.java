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
	 * Forbids caching of the /api/v2 responses, which otherwise carry no freshness information and no validator at
	 * all and are therefore heuristically cacheable - i.e. a client may keep serving a stale operator context
	 * without ever contacting us. An endpoint that wants caching opts out by setting its own Cache-Control.
	 * <p>
	 * A servlet filter registered like de.metas.util.web's other /api filters, and not a HandlerInterceptor: for
	 * {@code @ResponseBody} / {@code ResponseEntity} methods the response is written and committed before
	 * {@code postHandle} runs (spring reference, "Handler Interception"), and a {@code preHandle} - a
	 * {@code WebContentInterceptor} included - only ever sees a request that reaches an MVC handler, so neither the
	 * 401 from UserAuthTokenFilter nor an audited response served by ApiAuditFilter would be covered.
	 * <p>
	 * Runs innermost (hence the explicit order), which leaves de.metas.util.web's ApiAuditFilter - registered on the
	 * same pattern with order 3 - outside it: an audited request answered from that filter's own response reference
	 * never enters this chain and still carries no Cache-Control.
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
	 * Makes an endpoint's own {@code Cache-Control} REPLACE the default that {@link #apiNoStoreCacheControlFilter()}
	 * put on the response up-front, instead of appending to it: spring writes a {@code ResponseEntity}'s headers with
	 * {@link HttpServletResponse#addHeader(String, String)}, so without this an opting-out endpoint emits two
	 * contradicting values and a cache honours the stricter {@code no-store}.
	 * <p>
	 * Only {@code addHeader} needs the treatment; a plain {@code setHeader} replaces already. An endpoint emitting
	 * several {@code Cache-Control} headers would end up with the last one only - no /api/v2 endpoint does, and
	 * {@code CacheControl} puts all its directives into one header value.
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
