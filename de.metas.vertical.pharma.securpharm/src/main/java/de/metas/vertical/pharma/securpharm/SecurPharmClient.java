/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.vertical.pharma.securpharm.model.DecodeDataMatrixResponse;
import de.metas.vertical.pharma.securpharm.model.DecommissionAction;
import de.metas.vertical.pharma.securpharm.model.ProductCodeType;
import de.metas.vertical.pharma.securpharm.model.ProductData;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmRequestLogData;
import de.metas.vertical.pharma.securpharm.model.SecurPharmRequestLogData.SecurPharmRequestLogDataBuilder;
import de.metas.vertical.pharma.securpharm.model.schema.APIResponse;
import de.metas.vertical.pharma.securpharm.model.schema.Package;
import de.metas.vertical.pharma.securpharm.model.schema.Product;
import de.metas.vertical.pharma.securpharm.model.schema.State;
import lombok.Getter;
import lombok.NonNull;

public class SecurPharmClient
{
	static SecurPharmClient createAndAuthenticate(@NonNull final SecurPharmConfig config)
	{
		final SecurPharmClient client = new SecurPharmClient(config);
		client.getAuthorizationHttpHeader(); // make sure it's authenticated
		return client;
	}

	private static final Logger logger = LogManager.getLogger(SecurPharmClient.class);

	private static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	private static final String API_RELATIVE_PATH_UIS = "/uis/";
	private static final String QUERY_PARAM_CTX = "ctx";
	private static final String QUERY_PARAM_SID = "sid";
	private static final String QUERY_PARAM_TRX = "trx";
	private static final String QUERY_PARAM_LOT = "lot";
	private static final String QUERY_PARAM_EXP = "exp";
	private static final String QUERY_PARAM_PCS = "pcs";
	private static final String QUERY_PARAM_ACT = "act";
	private static final String API_RELATIVE_PATH_PRODUCTS = "/products";
	private static final String API_RELATIVE_PATH_PACKS = "packs";
	private static final String DELIMITER = ",";

	@Getter
	private final SecurPharmConfig config;
	private final RestTemplate apiRestTemplate;
	private final ObjectMapper jsonObjectMapper = new ObjectMapper();
	private final SecurPharmClientAuthenticator authenticator;

	private SecurPharmClient(@NonNull final SecurPharmConfig config)
	{
		this.config = config;

		apiRestTemplate = new RestTemplateBuilder()
				.rootUri(config.getPharmaAPIBaseUrl())
				.build();

		authenticator = SecurPharmClientAuthenticator.ofConfig(config);
	}

	public DecodeDataMatrixResponse decodeDataMatrix(@NonNull final String dataMatrix)
	{
		final String clientTrx = UUID.randomUUID().toString();
		final UriComponentsBuilder url = UriComponentsBuilder.fromPath(API_RELATIVE_PATH_UIS)
				.path(encodeBase64(dataMatrix))
				.queryParam(QUERY_PARAM_CTX, clientTrx)
				.queryParam(QUERY_PARAM_SID, config.getApplicationUUID());

		final SecurPharmRequestLogDataBuilder logData = SecurPharmRequestLogData.builder()
				.clientTransactionId(clientTrx)
				.requestTime(Instant.now())
				.requestUrl(config.getPharmaAPIBaseUrl() + url.build());

		ProductData productData = null;

		try
		{
			final ResponseEntity<String> response = performRequest(url, HttpMethod.GET);
			final String apiResponseString = response.getBody();
			logData.responseData(apiResponseString);
			logData.responseTime(Instant.now());

			final APIResponse apiResponse = fromJsonString(apiResponseString, APIResponse.class);

			if (response.getStatusCode() == HttpStatus.OK
					&& apiResponse.getProd() != null
					&& apiResponse.getPack() != null)
			{
				logData.serverTransactionId(apiResponse.getServerTransactionId());
				logData.error(false);
				productData = extractProductDataFromAPIResponse(apiResponse);
			}
			else
			{
				logData.error(true);
				productData = null;
			}

		}
		catch (final HttpClientErrorException ex)
		{
			logger.error("Got HTTP client error", ex);
			logData.error(true);
			logData.responseData(ex.getStatusCode() + ex.getResponseBodyAsString());
			logData.responseTime(Instant.now());
		}

		return DecodeDataMatrixResponse.builder()
				.logData(logData.build())
				.productData(productData)
				.build();
	}

	private static ProductData extractProductDataFromAPIResponse(final APIResponse apiResponse)
	{
		final Product product = apiResponse.getProd();
		final Package pack = apiResponse.getPack();

		final ProductData.ProductDataBuilder productDataBuilder = ProductData.builder()
				.lot(decodeBase64(product.getLot()))
				.productCode(product.getProductCode())
				.productCodeType(ProductCodeType.ofCode(product.getPcs()))
				.expirationDate(product.getExpirationDate())
				.serialNumber(decodeBase64(pack.getSerialNumber()));
		if (pack.getState() == State.ACTIVE)
		{
			productDataBuilder.active(true);
		}
		else
		{
			productDataBuilder.active(false);
			if (pack.getReasons() != null)
			{
				productDataBuilder.inactiveReason(String.join(DELIMITER, pack.getReasons()));
			}
		}

		return productDataBuilder.build();
	}

	private static String decodeBase64(final String str)
	{
		return new String(Base64.getDecoder().decode(str));
	}

	private static String encodeBase64(final String str)
	{
		return new String(Base64.getEncoder().encode(str.getBytes()));
	}

	private <T> T fromJsonString(final String json, final Class<T> type)
	{
		try
		{
			return jsonObjectMapper.readValue(json, type);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting JSON to " + type.getSimpleName(), ex)
					.appendParametersToMessage()
					.setParameter("json", json);
		}
	}

	public SecurPharmActionResult decommission(@NonNull final ProductData productData)
	{
		final UriComponentsBuilder url = prepareActionURL(productData, DecommissionAction.DESTROY);
		final SecurPharmRequestLogData logData = executeAction(url);

		return SecurPharmActionResult.builder()
				.action(DecommissionAction.DESTROY)
				.productData(productData)
				.requestLogData(logData)
				.build();
	}

	public SecurPharmActionResult undoDecommission(
			@NonNull final ProductData productData,
			@NonNull final String serverTransactionId)
	{
		final UriComponentsBuilder url = prepareActionURL(productData, DecommissionAction.UNDO_DISPENSE)
				.queryParam(QUERY_PARAM_TRX, serverTransactionId);

		final SecurPharmRequestLogData logData = executeAction(url);

		return SecurPharmActionResult.builder()
				.action(DecommissionAction.UNDO_DISPENSE)
				.productData(productData)
				.requestLogData(logData)
				.build();
	}

	private SecurPharmRequestLogData executeAction(final UriComponentsBuilder url)
	{

		final SecurPharmRequestLogDataBuilder logData = SecurPharmRequestLogData.builder()
				.requestTime(Instant.now())
				.requestUrl(config.getPharmaAPIBaseUrl() + url.build());

		try
		{
			final ResponseEntity<String> response = performRequest(url, HttpMethod.PUT);
			final String apiResponseString = response.getBody();
			logData.responseTime(Instant.now());
			logData.responseData(apiResponseString);

			final APIResponse apiResponse = fromJsonString(apiResponseString, APIResponse.class);
			if (response.getStatusCode() == HttpStatus.OK && apiResponse.isTransactionSet())
			{
				logData.serverTransactionId(apiResponse.getServerTransactionId());
				logData.error(false);
			}
			else
			{
				logData.error(true);
			}
		}
		catch (final HttpClientErrorException ex)
		{
			logger.error("Got HTTP client error", ex);

			logData.error(true);
			logData.responseTime(Instant.now());
			logData.responseData(ex.getStatusCode() + ex.getResponseBodyAsString());
		}

		return logData.build();
	}

	private ResponseEntity<String> performRequest(final UriComponentsBuilder uri, final HttpMethod method)
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.set(HTTP_HEADER_AUTHORIZATION, getAuthorizationHttpHeader());

		final HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

		return apiRestTemplate.exchange(uri.toUriString(), method, entity, String.class);
	}

	private UriComponentsBuilder prepareActionURL(
			@NonNull final ProductData productData,
			@NonNull final DecommissionAction action)
	{
		return prepareProductURL(productData)
				.queryParam(QUERY_PARAM_ACT, action.getCode());
	}

	private UriComponentsBuilder prepareProductURL(@NonNull final ProductData productData)
	{
		final String clientTransactionId = UUID.randomUUID().toString();

		return UriComponentsBuilder.fromPath(API_RELATIVE_PATH_PRODUCTS)
				.path(productData.getProductCode())
				.path(API_RELATIVE_PATH_PACKS)
				.path(encodeBase64(productData.getSerialNumber()))
				.queryParam(QUERY_PARAM_CTX, clientTransactionId)
				.queryParam(QUERY_PARAM_SID, config.getApplicationUUID())
				.queryParam(QUERY_PARAM_LOT, encodeBase64(productData.getLot()))
				.queryParam(QUERY_PARAM_EXP, productData.getExpirationDate().toYYMMDDString())
				.queryParam(QUERY_PARAM_PCS, productData.getProductCodeType().getCode());
	}

	private String getAuthorizationHttpHeader()
	{
		return authenticator.getAuthorizationHttpHeader();
	}
}
