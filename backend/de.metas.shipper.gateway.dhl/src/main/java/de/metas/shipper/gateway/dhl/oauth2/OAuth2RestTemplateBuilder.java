package de.metas.shipper.gateway.dhl.oauth2;

import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import org.springframework.web.client.RestTemplate;

public class OAuth2RestTemplateBuilder
{

	private final RestTemplate restTemplate;

	public OAuth2RestTemplateBuilder(final RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}

	public RestTemplate build(final DhlClientConfig clientConfig)
	{
		// Create a custom token retriever
		final CustomOAuth2TokenRetriever tokenRetriever = new CustomOAuth2TokenRetriever(restTemplate);

		// Build the RestTemplate with the OAuth interceptor
		final RestTemplate template = new RestTemplate();
		template.getInterceptors().add(new OAuth2AuthorizationInterceptor(tokenRetriever, clientConfig));

		return template;
	}
}