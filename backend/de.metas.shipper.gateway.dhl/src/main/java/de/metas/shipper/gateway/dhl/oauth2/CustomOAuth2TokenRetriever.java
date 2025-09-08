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
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class CustomOAuth2TokenRetriever
{

	public static final String DHL_OAUTH2_PATH = "/parcel/de/account/auth/ropc/v1/token";
	private final RestTemplate restTemplate;

	public CustomOAuth2TokenRetriever(final RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}

	public String retrieveAccessToken(final DhlClientConfig clientConfig)
	{
		// Build the form-encoded body for the request
		final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "password"); // As described by DHL
		formData.add("client_id", clientConfig.getApplicationID());
		formData.add("client_secret", clientConfig.getApplicationToken());
		formData.add("username", clientConfig.getUsername());
		formData.add("password", clientConfig.getSignature()); // Assuming `signature` is the password field

		// Prepare the headers
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Create the request entity
		final HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

		// Call the token endpoint
		final ResponseEntity<DhlOAuthTokenResponse> response = restTemplate.exchange(
				clientConfig.getBaseUrl() + DHL_OAUTH2_PATH,
				HttpMethod.POST,
				requestEntity,
				DhlOAuthTokenResponse.class
		);

		// Extract and return the access token
		return Objects.requireNonNull(response.getBody()).getAccess_token();
	}

	// Response handling class for parsing the token response
	@Getter
	public static class DhlOAuthTokenResponse
	{
		private String access_token;
	}
}