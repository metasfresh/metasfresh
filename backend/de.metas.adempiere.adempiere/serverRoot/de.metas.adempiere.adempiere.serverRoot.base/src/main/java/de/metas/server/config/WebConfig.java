package de.metas.server.config;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	@Override
	public void addCorsMappings(@NonNull final CorsRegistry registry)
	{
		// Disable CORS
		registry.addMapping("/**").allowedMethods("*");
	}
}
