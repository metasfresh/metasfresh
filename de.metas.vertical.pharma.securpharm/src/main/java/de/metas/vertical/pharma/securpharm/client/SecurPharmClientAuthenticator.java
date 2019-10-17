package de.metas.vertical.pharma.securpharm.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.adempiere.exceptions.AdempiereException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import de.metas.vertical.pharma.securpharm.client.schema.JsonAuthResponse;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class SecurPharmClientAuthenticator
{
	public static SecurPharmClientAuthenticator ofConfig(@NonNull final SecurPharmConfig config)
	{
		return new SecurPharmClientAuthenticator(config);
	}

	private static final String AUTH_PARAM_CLIENT_ID_VALUE = "securPharm";
	private static final String AUTH_PARAM_GRANT_TYPE = "grant_type";
	private static final String AUTH_PARAM_GRANT_TYPE_VALUE = "password";
	private static final String AUTH_PARAM_CLIENT_ID = "client_id";
	private static final String AUTH_RELATIVE_PATH = "/auth/realms/testentity/protocol/openid-connect/token";

	private static final String AUTHORIZATION_TYPE_BEARER = "Bearer ";

	private static final String MSG_AUTHORIZATION_FAILED = "Authorization failed";

	@NonNull
	private final SecurPharmConfig config;
	private JsonAuthResponse _authResponse; // lazy

	private SecurPharmClientAuthenticator(@NonNull final SecurPharmConfig config)
	{
		this.config = config;
	}

	public String getAuthorizationHttpHeader()
	{
		return AUTHORIZATION_TYPE_BEARER + getAccessToken();
	}

	private String getAccessToken()
	{
		return getAuthResponse().getAccessToken();
	}

	private synchronized JsonAuthResponse getAuthResponse()
	{
		JsonAuthResponse authResponse = _authResponse;

		if (authResponse == null
				|| authResponse.isExpired())
		{
			authResponse = _authResponse = authenticate(config);
		}

		return authResponse;
	}

	public void authenticate()
	{
		getAuthResponse();
	}

	private static JsonAuthResponse authenticate(@NonNull final SecurPharmConfig config)
	{
		try
		{
			final SSLContext sslContext = createSSLContext(config);

			final HttpClient client = HttpClients.custom()
					.setSSLContext(sslContext)
					.build();

			final RestTemplate restTemplate = new RestTemplate();
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add(AUTH_PARAM_GRANT_TYPE, AUTH_PARAM_GRANT_TYPE_VALUE);
			params.add(AUTH_PARAM_CLIENT_ID, AUTH_PARAM_CLIENT_ID_VALUE);

			final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

			final ResponseEntity<JsonAuthResponse> response = restTemplate.postForEntity(config.getAuthBaseUrl() + AUTH_RELATIVE_PATH, request, JsonAuthResponse.class);
			final JsonAuthResponse authResponse = response.getBody();
			if (response.getStatusCode() == HttpStatus.OK)
			{
				return authResponse;
			}
			else
			{
				throw new AdempiereException(MSG_AUTHORIZATION_FAILED)
						.appendParametersToMessage()
						.setParameter("HTTPStatus", response.getStatusCode())
						.setParameter("error", authResponse.getError())
						.setParameter("errorDescription", authResponse.getErrorDescription());
			}
		}
		catch (final AdempiereException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			final Throwable cause = AdempiereException.extractCause(ex);
			throw new AdempiereException(MSG_AUTHORIZATION_FAILED, cause);
		}
	}

	private static SSLContext createSSLContext(final SecurPharmConfig config)
	{
		try
		{
			final char[] password = config.getKeystorePassword().toCharArray();
			final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			final File key = new File(config.getCertificatePath());
			try (final InputStream in = new FileInputStream(key))
			{
				keyStore.load(in, password);
			}

			return SSLContextBuilder.create()
					.loadKeyMaterial(keyStore, password)
					.loadTrustMaterial(null, new TrustSelfSignedStrategy())
					.build();
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed creating SSL context " + ex.getLocalizedMessage(), ex)
					.appendParametersToMessage()
					.setParameter("config", config);
		}
	}
}
