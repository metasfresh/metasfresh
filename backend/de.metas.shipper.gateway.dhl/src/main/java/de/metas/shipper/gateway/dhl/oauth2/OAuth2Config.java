package de.metas.shipper.gateway.dhl.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OAuth2Config
{

	@Bean
	public RestTemplate restTemplate()
	{
		// The basic RestTemplate used for internal HTTP calls
		return new RestTemplate();
	}

	@Bean
	public CustomOAuth2TokenRetriever customOAuth2TokenRetriever(final RestTemplate restTemplate)
	{
		// The custom token retriever for handling DHL-specific OAuth flows
		return new CustomOAuth2TokenRetriever(restTemplate);
	}
}