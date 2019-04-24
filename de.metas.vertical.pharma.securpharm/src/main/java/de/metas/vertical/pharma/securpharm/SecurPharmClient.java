/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.vertical.pharma.securpharm.model.*;
import de.metas.vertical.pharma.securpharm.model.schema.APIResponse;
import de.metas.vertical.pharma.securpharm.model.schema.AuthResponse;
import de.metas.vertical.pharma.securpharm.model.schema.State;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

public class SecurPharmClient
{

	private final transient Logger log = LogManager.getLogger(getClass());

	@Getter
	private final SecurPharmConfig config;

	private final RestTemplate apiRestTemplate;

	private AuthResponse authResponse;

	private long authTimestamp;

	private SecurPharmClient(@NonNull final SecurPharmConfig config, @NonNull final AuthResponse authResponse)
	{
		final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
				.rootUri(config.getPharmaAPIBaseUrl());
		this.apiRestTemplate = restTemplateBuilder.build();
		this.config = config;
		this.authResponse = authResponse;
		this.authTimestamp = Instant.now().getEpochSecond();
	}

	public static SecurPharmClient createAndAuthenticate(@NonNull final SecurPharmConfig config)
	{
		try
		{
			return new SecurPharmClient(config, authenticate(config));
		}
		catch (Exception e)
		{
			throw new AdempiereException(SecurPharmConstants.AUTHORIZATION_FAILED_MESSAGE, e.getCause());
		}
	}

	private static AuthResponse authenticate(@NonNull final SecurPharmConfig config) throws Exception
	{

		final char[] password = config.getKeystorePassword().toCharArray();
		final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		final File key = new File(config.getCertificatePath());
		try (final InputStream in = new FileInputStream(key))
		{
			keyStore.load(in, password);
		}
		final SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(keyStore, password)
				.loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

		final HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add(SecurPharmConstants.AUTH_PARAM_GRANT_TYPE, SecurPharmConstants.AUTH_PARAM_GRANT_TYPE_VALUE);
		params.add(SecurPharmConstants.AUTH_PARAM_CLIENT_ID, SecurPharmConstants.AUTH_PARAM_CLIENT_ID_VALUE);

		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		final ResponseEntity<AuthResponse> response = restTemplate.postForEntity(config.getAuthBaseUrl() + SecurPharmConstants.AUTH_RELATIVE_PATH, request, AuthResponse.class);
		AuthResponse authResponse = response.getBody();
		if (response.getStatusCode() == HttpStatus.OK)
		{
			return authResponse;
		}
		else
		{
			throw new AdempiereException(SecurPharmConstants.AUTHORIZATION_FAILED_MESSAGE)
					.appendParametersToMessage()
					.setParameter(SecurPharmConstants.HTTP_STATUS, response.getStatusCode())
					.setParameter(SecurPharmConstants.ERROR, authResponse.getError())
					.setParameter(SecurPharmConstants.ERROR_DESCRIPTION, authResponse.getErrorDescription());
		}

	}

	public SecurPharmProductDataResult decodeDataMatrix(@NonNull final String dataMatrix) throws Exception
	{

		final String encodedDataMatrix = Base64.getEncoder().encodeToString(dataMatrix.getBytes());
		final String clientTrx = UUID.randomUUID().toString();
		final UriComponentsBuilder builder = UriComponentsBuilder.fromPath(SecurPharmConstants.API_RELATIVE_PATH_UIS)
				.path(encodedDataMatrix)
				.queryParam(SecurPharmConstants.QUERY_PARAM_CTX, clientTrx)
				.queryParam(SecurPharmConstants.QUERY_PARAM_SID, config.getApplicationUUID());
		ObjectMapper mapper = new ObjectMapper();

		final SecurPharmRequestLogData.SecurPharmRequestLogDataBuilder logDataBuilder = SecurPharmRequestLogData.builder()
				.requestTime(LocalDateTime.now())
				.clientTransactionID(clientTrx)
				.requestUrl(config.getPharmaAPIBaseUrl() + builder.build());
		final SecurPharmProductDataResult securPharmProductDataResult = new SecurPharmProductDataResult();

		try
		{
			final ResponseEntity<APIResponse> response = performRequest(builder, HttpMethod.GET);
			final APIResponse apiResponse = response.getBody();
			logDataBuilder.responseTime(LocalDateTime.now())
					.responseData(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiResponse));
			securPharmProductDataResult.setRequestLogData(logDataBuilder.build());
			if (response.getStatusCode() == HttpStatus.OK && apiResponse.getProduct() != null && apiResponse.getPack() != null && apiResponse.getTransaction() != null)
			{
				logDataBuilder.serverTransactionID(apiResponse.getTransaction().getServerTransactionId());
				byte[] decodedSerialNumber = Base64.getDecoder().decode(apiResponse.getPack().getSerialNumber());
				byte[] decodedLot = Base64.getDecoder().decode(apiResponse.getProduct().getLot());
				final ProductData.ProductDataBuilder productDataBuilder = ProductData.builder()
						.lot(new String(decodedLot))
						.productCode(apiResponse.getProduct().getProductCode())
						.expirationDate(apiResponse.getProduct().getExpirationDate())
						.serialNumber(new String(decodedSerialNumber));
				if (apiResponse.getPack().getState() == State.ACTIVE)
				{
					productDataBuilder.isActive(true);
				}
				else
				{
					productDataBuilder.isActive(false);
					if (apiResponse.getPack().getReason() != null)
					{
						productDataBuilder.inactiveReason(String.join(SecurPharmConstants.DELIMITER, apiResponse.getPack().getReason()));
					}
				}
				securPharmProductDataResult.setError(false);
				securPharmProductDataResult.setProductData(productDataBuilder.build());
			}
			else
			{
				//should not get here
				securPharmProductDataResult.setError(true);
			}
		}
		catch (final HttpClientErrorException e)
		{
			log.error(e.getLocalizedMessage(), e.getCause());
			securPharmProductDataResult.setError(true);
			logDataBuilder.responseTime(LocalDateTime.now())
					.responseData(e.getStatusCode() + e.getResponseBodyAsString());
			securPharmProductDataResult.setRequestLogData(logDataBuilder.build());
		}
		return securPharmProductDataResult;
	}

	public SecurPharmActionResult decommission(@NonNull final ProductData productData, @NonNull final DecommissionAction action) throws Exception
	{
		final UriComponentsBuilder builder = getDecommisionPathBuilder(productData, action);

		return getSecurPharmActionResult(action, builder);
	}

	private SecurPharmActionResult getSecurPharmActionResult(@NonNull final DecommissionAction action, @NonNull final UriComponentsBuilder builder) throws Exception
	{
		final SecurPharmRequestLogData.SecurPharmRequestLogDataBuilder logDataBuilder = SecurPharmRequestLogData.builder()
				.requestTime(LocalDateTime.now())
				.requestUrl(config.getPharmaAPIBaseUrl() + builder.build());
		final SecurPharmActionResult result = new SecurPharmActionResult();
		result.setAction(action);
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			final ResponseEntity<APIResponse> response = performRequest(builder, HttpMethod.PUT);
			final APIResponse apiResponse = response.getBody();
			logDataBuilder.responseData(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiResponse));
			if (response.getStatusCode() == HttpStatus.OK && apiResponse.getTransaction() != null)
			{
				result.setError(false);
				logDataBuilder.serverTransactionID(apiResponse.getTransaction().getServerTransactionId());
			}
			else
			{
				//should not get here
				result.setError(true);
			}
		}
		catch (final HttpClientErrorException e)
		{
			log.error(e.getLocalizedMessage(), e.getCause());
			result.setError(true);
			logDataBuilder.responseTime(LocalDateTime.now())
					.responseData(e.getStatusCode() + e.getResponseBodyAsString());
			result.setRequestLogData(logDataBuilder.build());
		}
		return result;
	}

	public SecurPharmActionResult undoDecommission(@NonNull final ProductData productData, @NonNull final DecommissionAction action, @NonNull final String trx) throws Exception
	{
		final UriComponentsBuilder builder = getDecommisionPathBuilder(productData, action).queryParam(SecurPharmConstants.QUERY_PARAM_TRX, trx);

		return getSecurPharmActionResult(action, builder);
	}

	private ResponseEntity<APIResponse> performRequest(UriComponentsBuilder builder, HttpMethod put)
	{
		refreshAuthIfExpired();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.set(SecurPharmConstants.HEADER_AUTHORIZATION, SecurPharmConstants.AUTHORIZATION_TYPE_BEARER + authResponse.getAccessToken());

		final HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

		return apiRestTemplate.exchange(builder.toUriString(), put, entity, APIResponse.class);
	}

	private UriComponentsBuilder getDecommisionPathBuilder(@NonNull ProductData productData, @NonNull DecommissionAction action)
	{
		final String encodedSerialNumber = Base64.getEncoder().encodeToString(productData.getSerialNumber().getBytes());
		final String encodedLot = Base64.getEncoder().encodeToString(productData.getLot().getBytes());
		return UriComponentsBuilder.fromPath(SecurPharmConstants.API_RELATIVE_PATH_PRODUCTS)
				.path(productData.getProductCode())
				.path(SecurPharmConstants.API_RELATIVE_PATH_PACKS)
				.path(encodedSerialNumber)
				.queryParam(SecurPharmConstants.QUERY_PARAM_CTX, UUID.randomUUID().toString())
				.queryParam(SecurPharmConstants.QUERY_PARAM_SID, config.getApplicationUUID())
				.queryParam(SecurPharmConstants.QUERY_PARAM_LOT, encodedLot)
				.queryParam(SecurPharmConstants.QUERY_PARAM_EXP, productData.getExpirationDate())
				.queryParam(SecurPharmConstants.QUERY_PARAM_PCS, productData.getProductCodeType().name())
				.queryParam(SecurPharmConstants.QUERY_PARAM_ACT, action.name());
	}

	private void refreshAuthIfExpired()
	{
		if ((Instant.now().getEpochSecond() - this.authTimestamp) >= this.authResponse.getExpiresIn())
		{
			try
			{
				this.authResponse = authenticate(this.config);
			}
			catch (Exception e)
			{
				throw new AdempiereException(SecurPharmConstants.AUTHORIZATION_FAILED_MESSAGE, e.getCause());
			}
		}
	}

}
