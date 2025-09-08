/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.oauth2;

import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import lombok.NonNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class OAuth2AuthorizationInterceptor implements ClientHttpRequestInterceptor
{

	private final CustomOAuth2TokenRetriever tokenRetriever;
	private final DhlClientConfig clientConfig;

	public OAuth2AuthorizationInterceptor(final CustomOAuth2TokenRetriever tokenRetriever, final DhlClientConfig clientConfig)
	{
		this.tokenRetriever = tokenRetriever;
		this.clientConfig = clientConfig;
	}

	@Override
	@NonNull
	public ClientHttpResponse intercept(final HttpRequest request, final byte @NonNull [] body, final ClientHttpRequestExecution execution) throws IOException
	{
		// Fetch the access token using the custom retriever
		final String accessToken = tokenRetriever.retrieveAccessToken(clientConfig);

		// Add the Authorization Bearer token to the request headers
		request.getHeaders().add("Authorization", "Bearer " + accessToken);

		// Proceed with the execution
		return execution.execute(request, body);
	}
}