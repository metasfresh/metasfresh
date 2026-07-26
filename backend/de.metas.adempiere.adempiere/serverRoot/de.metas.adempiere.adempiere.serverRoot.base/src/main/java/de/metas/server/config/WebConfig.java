package de.metas.server.config;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@Override
	public void addInterceptors(@NonNull final InterceptorRegistry registry)
	{
		registry.addInterceptor(new CacheControlInterceptor())
				.addPathPatterns("/api/**");
	}

	/**
	 * Sets a default <code>Cache-Control: no-cache, private</code> header on all REST API responses,
	 * unless the controller has already set a Cache-Control header.
	 * <p>
	 * This ensures that dynamic business data is not cached by proxies/CDNs,
	 * while still allowing individual endpoints (e.g., image serving) to override it.
	 */
	private static class CacheControlInterceptor implements HandlerInterceptor
	{
		@Override
		public boolean preHandle(
				@NonNull final HttpServletRequest request,
				@NonNull final HttpServletResponse response,
				@NonNull final Object handler)
		{
			if (response.getHeader(HttpHeaders.CACHE_CONTROL) == null)
			{
				response.setHeader(
						HttpHeaders.CACHE_CONTROL,
						CacheControl.noCache().cachePrivate().getHeaderValue());
			}
			return true;
		}
	}
}
