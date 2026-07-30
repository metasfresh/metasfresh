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
	 * The header is written BEFORE the chain runs, not after: {@link HttpServletResponse#setHeader} is a
	 * silent no-op once the response is committed, and the container commits as soon as its output buffer
	 * fills (~8 KB) while the body is being written. Setting it afterwards would therefore be lost for
	 * exactly the large-body endpoints on this server (the order / shipment / invoice PDF downloads),
	 * leaving them with no Cache-Control at all and no error to show for it. Writing it up-front is safe
	 * because an endpoint that wants its own policy (e.g. ImageRestController's max-age + ETag) sets its
	 * headers before the body is written and so simply overwrites this default — the same ordering Spring
	 * Security's HeaderWriterFilter relies on.
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
				if (response instanceof HttpServletResponse)
				{
					// Up-front default; an endpoint that sets its own Cache-Control (e.g. ImageRestController's
					// max-age) overwrites it before the body is written. See the javadoc on why this cannot be
					// deferred to after the chain.
					((HttpServletResponse)response).setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
				}

				chain.doFilter(request, response);
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
