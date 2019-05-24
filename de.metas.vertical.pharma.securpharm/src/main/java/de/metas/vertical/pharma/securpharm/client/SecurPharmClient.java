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

package de.metas.vertical.pharma.securpharm.client;

import java.nio.charset.StandardCharsets;
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
import de.metas.vertical.pharma.securpharm.model.VerifyProductResponse;
import de.metas.vertical.pharma.securpharm.model.schema.APIResponse;
import de.metas.vertical.pharma.securpharm.model.schema.Package;
import de.metas.vertical.pharma.securpharm.model.schema.Product;
import de.metas.vertical.pharma.securpharm.model.schema.Result;
import de.metas.vertical.pharma.securpharm.model.schema.State;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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
	private static final String API_RELATIVE_PATH_PRODUCTS = "/products/";
	private static final String API_RELATIVE_PATH_PACKS = "/packs/";
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
		final String clientTransactionId = newClientTransactionId();
		final UriComponentsBuilder url = UriComponentsBuilder.fromPath(API_RELATIVE_PATH_UIS)
				.path(encodeBase64url(dataMatrix));

		final APIReponseWithLogData responseAndLogData = getFromUrl(url, clientTransactionId);

		final SecurPharmRequestLogData logData = responseAndLogData.getLogData();
		final ProductData productData = !logData.isError()
				? extractProductDataFromAPIResponse(responseAndLogData.getResponse())
				: null;

		return DecodeDataMatrixResponse.builder()
				.logData(logData)
				.productData(productData)
				.build();
	}

	public VerifyProductResponse verifyProduct(@NonNull final ProductData requestProductData)
	{
		final String clientTransactionId = newClientTransactionId();
		final UriComponentsBuilder url = prepareProductURL(requestProductData, clientTransactionId);

		final APIReponseWithLogData responseAndLogData = getFromUrl(url, clientTransactionId);

		final SecurPharmRequestLogData logData = responseAndLogData.getLogData();
		final APIResponse response = responseAndLogData.getResponse();
		final ProductData responseProductData = !logData.isError()
				? extractProductDataFromAPIResponse(response)
				: null;

		return VerifyProductResponse.builder()
				.resultCode(response.getResultCode())
				.resultMessage(response.getResultMessage())
				.productData(responseProductData)
				.logData(logData)
				.build();
	}

	private static String newClientTransactionId()
	{
		return UUID.randomUUID().toString();
	}

	private APIReponseWithLogData getFromUrl(
			@NonNull final UriComponentsBuilder url,
			@NonNull final String clientTransactionId)
	{
		url.replaceQueryParam(QUERY_PARAM_CTX, clientTransactionId);
		url.replaceQueryParam(QUERY_PARAM_SID, config.getApplicationUUID());

		final SecurPharmRequestLogDataBuilder logData = SecurPharmRequestLogData.builder()
				.clientTransactionId(clientTransactionId)
				.requestTime(Instant.now())
				.requestUrl(config.getPharmaAPIBaseUrl() + url.build());

		APIResponse apiResponse = null;
		try
		{
			final ResponseEntity<String> response = performRequest(url, HttpMethod.GET);
			final String apiResponseString = response.getBody();
			logData.responseCode(response.getStatusCode());
			logData.responseData(apiResponseString);
			logData.responseTime(Instant.now());

			apiResponse = fromJsonString(apiResponseString, APIResponse.class);
			logData.serverTransactionId(apiResponse.getServerTransactionId());
			logData.error(response.getStatusCode() != HttpStatus.OK);
		}
		catch (final HttpClientErrorException ex)
		{
			logger.debug("Got HTTP client error", ex);

			final String apiResponseString = ex.getResponseBodyAsString();
			apiResponse = fromJsonStringOrNull(apiResponseString, APIResponse.class);

			logData.error(true);
			logData.responseCode(ex.getStatusCode());
			logData.responseData(apiResponseString);
			logData.responseTime(Instant.now());
		}
		catch (final Exception ex)
		{
			logger.debug("Got unknown error", ex);

			final Result apiResponseResult = new Result();
			apiResponseResult.setCode("?");
			apiResponseResult.setMessage(ex.getMessage());

			apiResponse = new APIResponse();
			apiResponse.setRes(apiResponseResult);

			logData.error(true);
			// logData.responseCode(ex.getStatusCode());
			// logData.responseData(apiResponseString);
			logData.responseTime(Instant.now());
		}

		return APIReponseWithLogData.of(logData.build(), apiResponse);
	}

	private static ProductData extractProductDataFromAPIResponse(@NonNull final APIResponse apiResponse)
	{
		final Product product = apiResponse.getProd();
		final Package pack = apiResponse.getPack();

		final ProductData.ProductDataBuilder productDataBuilder = ProductData.builder()
				.lot(product.getLot())
				.productCode(product.getProductCode())
				.productCodeType(ProductCodeType.ofCode(product.getPcs()))
				.expirationDate(product.getExpirationDate())
				.serialNumber(pack.getSerialNumber());
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

	private static String encodeBase64url(final String str)
	{
		return Base64.getEncoder()
				.withoutPadding()
				.encodeToString(str.getBytes(StandardCharsets.UTF_8));
	}

	private <T> T fromJsonStringOrNull(final String json, final Class<T> type)
	{
		try
		{
			return jsonObjectMapper.readValue(json, type);
		}
		catch (final Exception ex)
		{
			logger.debug("Failed converting JSON to {}: {}\nReturning null.", type, json);
			return null;
		}
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
		final String clientTransactionId = newClientTransactionId();
		final UriComponentsBuilder url = prepareActionURL(productData, DecommissionAction.DESTROY, clientTransactionId);
		final SecurPharmRequestLogData logData = executeAction(url, clientTransactionId);

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
		final String clientTransactionId = newClientTransactionId();
		final UriComponentsBuilder url = prepareActionURL(productData, DecommissionAction.UNDO_DISPENSE, clientTransactionId)
				.queryParam(QUERY_PARAM_TRX, serverTransactionId);

		final SecurPharmRequestLogData logData = executeAction(url, clientTransactionId);

		return SecurPharmActionResult.builder()
				.action(DecommissionAction.UNDO_DISPENSE)
				.productData(productData)
				.requestLogData(logData)
				.build();
	}

	private SecurPharmRequestLogData executeAction(
			@NonNull final UriComponentsBuilder url,
			@NonNull final String clientTransactionId)
	{
		final SecurPharmRequestLogDataBuilder logData = SecurPharmRequestLogData.builder()
				.clientTransactionId(clientTransactionId)
				.requestTime(Instant.now())
				.requestUrl(config.getPharmaAPIBaseUrl() + url.build());

		try
		{
			final ResponseEntity<String> response = performRequest(url, HttpMethod.PUT);
			final String apiResponseString = response.getBody();
			logData.responseCode(response.getStatusCode());
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
			logger.debug("Got HTTP client error", ex);

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
			@NonNull final DecommissionAction action,
			@NonNull final String clientTransactionId)
	{
		return prepareProductURL(productData, clientTransactionId)
				.queryParam(QUERY_PARAM_ACT, action.getCode());
	}

	private UriComponentsBuilder prepareProductURL(
			@NonNull final ProductData productData,
			@NonNull final String clientTransactionId)
	{
		return UriComponentsBuilder.fromPath(API_RELATIVE_PATH_PRODUCTS)
				.path(productData.getProductCode())
				.path(API_RELATIVE_PATH_PACKS)
				.path(encodeBase64url(productData.getSerialNumber()))
				// .queryParam(QUERY_PARAM_CTX, clientTransactionId)
				// .queryParam(QUERY_PARAM_SID, config.getApplicationUUID())
				.queryParam(QUERY_PARAM_LOT, encodeBase64url(productData.getLot()))
				.queryParam(QUERY_PARAM_EXP, productData.getExpirationDate().toYYMMDDString())
				.queryParam(QUERY_PARAM_PCS, productData.getProductCodeType().getCode());
	}

	private String getAuthorizationHttpHeader()
	{
		return authenticator.getAuthorizationHttpHeader();
	}

	@Value(staticConstructor = "of")
	private static class APIReponseWithLogData
	{
		@NonNull
		SecurPharmRequestLogData logData;
		@NonNull
		APIResponse response;
	}
}
