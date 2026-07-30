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
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	private static final String CACHE_CONTROL_NO_STORE = "no-store";

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
	 * The header is written BEFORE the chain runs, not after: {@link HttpServletResponse#setHeader} is a
	 * silent no-op once the response is committed, and the container commits as soon as its output buffer
	 * fills (~8 KB) while the body is being written. Setting it afterwards would therefore be lost for
	 * exactly the large-body endpoints on this server (the order / shipment / invoice PDF downloads),
	 * leaving them with no Cache-Control at all and no error to show for it.
	 * <p>
	 * Writing it up-front alone is however NOT enough to let an endpoint opt out - see
	 * {@link CacheControlDefaultReplacingResponse}, which is what makes the opt-out actually work.
	 * <p>
	 * Applies to EVERY /api/v2 endpoint here, deliberately — including the order / shipment / invoice PDF
	 * downloads, which lose the heuristic caching they got from having no header at all. That cost is
	 * accepted: the defect exists precisely because a response said nothing and the client guessed, so
	 * scoping the filter to the few endpoints we currently know about would leave the same silence — and
	 * the same latent bug — on every other one, including endpoints added later. Safe-by-default is the
	 * point. An endpoint that genuinely wants caching opts out by setting its own Cache-Control, the way
	 * ImageRestController already does; that is the cheap follow-up if PDF re-downloads ever measurably
	 * hurt, rather than a speculative optimisation now.
	 * <p>
	 * One known gap, pre-dating this filter and left alone here: de.metas.util.web's ApiAuditFilter is
	 * registered on the same /api/v2/* pattern with order 3, i.e. OUTSIDE this one. For a request matched
	 * by an audit config it answers from its own reference to the raw response - the async "no-wait" reply
	 * never enters this chain at all, and the error path resets the response - so those replies still carry
	 * no Cache-Control. Closing that belongs in the audit code, not here.
	 * <p>
	 * The app server's counterpart to the webapi's addMissingHeadersFilter
	 * (de.metas.ui.web.config.WebConfig), which had no equivalent here. Note it deliberately does NOT
	 * behave identically: that one sends {@code no-cache}, this one sends {@code no-store}. {@code
	 * no-cache} permits storing and relies on revalidation before reuse — but these responses carry no
	 * validator at all (no ETag, no Last-Modified), so there is nothing to revalidate against and the
	 * guarantee reduces to trusting every intermediary to always re-fetch. That is precisely the
	 * behaviour this fix exists to stop relying on, so {@code no-store} is the correct strength here.
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
				if (!(response instanceof HttpServletResponse))
				{
					chain.doFilter(request, response);
					return;
				}

				final HttpServletResponse httpResponse = (HttpServletResponse)response;

				// Up-front default, so it is already on the response no matter when the container commits it.
				// See the javadoc on why this cannot be deferred to after the chain.
				httpResponse.setHeader(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_NO_STORE);

				// ...and let an endpoint that sets its own Cache-Control REPLACE that default instead of
				// appending a second, contradicting value to it.
				chain.doFilter(request, new CacheControlDefaultReplacingResponse(httpResponse));
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

	/**
	 * Makes the application's <b>first</b> {@code Cache-Control} write REPLACE the {@code no-store} default that
	 * {@link #apiNoStoreCacheControlFilter()} put on the response up-front, instead of appending to it.
	 * <p>
	 * Needed because spring writes a {@code ResponseEntity}'s headers with
	 * {@link HttpServletResponse#addHeader(String, String)} (append), not {@code setHeader}: without this wrapper an
	 * endpoint that opts out of the default (ImageRestController) emits two contradicting {@code Cache-Control}
	 * headers and a cache honours the stricter {@code no-store} - i.e. the opt-out silently does nothing.
	 * <p>
	 * Wrapping is the only option that also holds for a response that COMMITS while the body is written: removing the
	 * duplicate after the chain would come too late for exactly those endpoints (that is the bug the up-front write
	 * exists for), and dropping the up-front write in favour of setting the default lazily right before the commit
	 * would mean intercepting every path that can commit a response (getOutputStream, getWriter, flushBuffer,
	 * sendError, ...) - far more surface for the same result.
	 * <p>
	 * Only the FIRST write is turned into a replace: an endpoint that deliberately sends several {@code Cache-Control}
	 * directives as separate headers still gets all of them, just without our default in front.
	 */
	private static class CacheControlDefaultReplacingResponse extends HttpServletResponseWrapper
	{
		/**
		 * Not volatile: no /api/v2 endpoint uses async MVC (DeferredResult / Callable / WebAsyncTask), so the response
		 * is written by the one request thread. Revisit if one ever does.
		 */
		private boolean cacheControlSetByApplication = false;

		private CacheControlDefaultReplacingResponse(@NonNull final HttpServletResponse delegate)
		{
			super(delegate);
		}

		@Override
		public void setHeader(final String name, final String value)
		{
			if (isCacheControl(name))
			{
				// already replaces, but it has to be remembered so that a following addHeader appends to the
				// application's value instead of wiping it
				cacheControlSetByApplication = true;
			}
			super.setHeader(name, value);
		}

		@Override
		public void addHeader(final String name, final String value)
		{
			if (isCacheControl(name) && !cacheControlSetByApplication)
			{
				cacheControlSetByApplication = true;
				super.setHeader(name, value);
				return;
			}
			super.addHeader(name, value);
		}

		private static boolean isCacheControl(final String name)
		{
			return HttpHeaders.CACHE_CONTROL.equalsIgnoreCase(name);
		}
	}
}
