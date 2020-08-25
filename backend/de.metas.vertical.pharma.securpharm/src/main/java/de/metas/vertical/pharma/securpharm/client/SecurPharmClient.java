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
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.JsonObjectMapperHolder;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.common.util.CoalesceUtil;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmAction;
import de.metas.vertical.pharma.securpharm.client.schema.JsonAPIResponse;
import de.metas.vertical.pharma.securpharm.client.schema.JsonPackage;
import de.metas.vertical.pharma.securpharm.client.schema.JsonProduct;
import de.metas.vertical.pharma.securpharm.client.schema.JsonProductPackageState;
import de.metas.vertical.pharma.securpharm.client.schema.JsonResult;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.ProductCodeType;
import de.metas.vertical.pharma.securpharm.product.ProductDetails;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

public class SecurPharmClient
{
	static SecurPharmClient createAndAuthenticate(@NonNull final SecurPharmConfig config)
	{
		final SecurPharmClient client = new SecurPharmClient(config);
		client.authenticate(); // make sure it's authenticated
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
	@VisibleForTesting
	private final SecurPharmConfig config;

	private final RestTemplate apiRestTemplate;
	private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private final SecurPharmClientAuthenticator authenticator;

	private SecurPharmClient(@NonNull final SecurPharmConfig config)
	{
		this.config = config;

		apiRestTemplate = new RestTemplateBuilder()
				.rootUri(config.getPharmaAPIBaseUrl())
				.build();

		authenticator = SecurPharmClientAuthenticator.ofConfig(config);
	}

	public UserId getSupportUserId()
	{
		return getConfig().getSupportUserId();
	}

	public DecodeDataMatrixClientResponse decodeDataMatrix(@NonNull final DataMatrixCode dataMatrix)
	{
		final String clientTransactionId = newClientTransactionId();
		final UriComponentsBuilder url = UriComponentsBuilder.fromPath(API_RELATIVE_PATH_UIS)
				.path(dataMatrix.toBase64Url());

		final APIReponseWithLog responseAndLogData = getFromUrl(url, clientTransactionId);

		final JsonAPIResponse apiResponse = responseAndLogData.getResponse();
		final SecurPharmLog log = responseAndLogData.getLog();
		final boolean error = log.isError();

		return DecodeDataMatrixClientResponse.builder()
				.error(error)
				.resultCode(apiResponse.getResultCode())
				.resultMessage(apiResponse.getResultMessage())
				.productDetails(!error ? extractProductDetailsFromAPIResponse(apiResponse) : null)
				.log(log)
				.build();
	}

	public VerifyProductClientResponse verifyProduct(@NonNull final ProductDetails requestProductDetails)
	{
		final String clientTransactionId = newClientTransactionId();
		final UriComponentsBuilder url = prepareProductURL(requestProductDetails);

		final APIReponseWithLog responseAndLogData = getFromUrl(url, clientTransactionId);

		final SecurPharmLog log = responseAndLogData.getLog();
		final JsonAPIResponse response = responseAndLogData.getResponse();
		final ProductDetails responseProductDetails = !log.isError()
				? extractProductDetailsFromAPIResponse(response)
				: null;

		return VerifyProductClientResponse.builder()
				.resultCode(response.getResultCode())
				.resultMessage(response.getResultMessage())
				.productDetails(responseProductDetails)
				.log(log)
				.build();
	}

	private static String newClientTransactionId()
	{
		return UUID.randomUUID().toString();
	}

	private APIReponseWithLog getFromUrl(
			@NonNull final UriComponentsBuilder url,
			@NonNull final String clientTransactionId)
	{
		url.replaceQueryParam(QUERY_PARAM_CTX, clientTransactionId);
		url.replaceQueryParam(QUERY_PARAM_SID, config.getApplicationUUID());

		final SecurPharmLog.SecurPharmLogBuilder log = SecurPharmLog.builder()
				.clientTransactionId(clientTransactionId)
				.requestTime(Instant.now())
				.requestUrl(config.getPharmaAPIBaseUrl() + url.build())
				.requestMethod(HttpMethod.GET);

		JsonAPIResponse apiResponse = null;
		try
		{
			final ResponseEntity<String> response = performRequest(url, HttpMethod.GET);
			final String apiResponseString = response.getBody();
			log.responseCode(response.getStatusCode());
			log.responseData(apiResponseString);
			log.responseTime(Instant.now());

			apiResponse = fromJsonString(apiResponseString, JsonAPIResponse.class);
			log.serverTransactionId(apiResponse.getServerTransactionId());
			log.error(response.getStatusCode() != HttpStatus.OK);
		}
		catch (final HttpClientErrorException ex)
		{
			logger.debug("Got HTTP client error", ex);

			final String apiResponseString = ex.getResponseBodyAsString();
			apiResponse = createJsonAPIResponseFromJsonString(apiResponseString);

			log.error(true);
			log.responseCode(ex.getStatusCode());
			log.responseData(apiResponseString);
			log.responseTime(Instant.now());
		}
		catch (final Exception ex)
		{
			logger.debug("Got unknown error", ex);

			apiResponse = createJsonAPIResponseFromErrorMessage(ex.getMessage());

			log.error(true);
			// logData.responseCode(ex.getStatusCode());
			// logData.responseData(apiResponseString);
			log.responseTime(Instant.now());
		}

		return APIReponseWithLog.of(log.build(), apiResponse);
	}

	private JsonAPIResponse createJsonAPIResponseFromJsonString(final String json)
	{
		final JsonAPIResponse apiResponse = fromJsonStringOrNull(json, JsonAPIResponse.class);
		return apiResponse != null
				? apiResponse
				: createJsonAPIResponseFromErrorMessage(json);
	}

	private static JsonAPIResponse createJsonAPIResponseFromErrorMessage(final String errorMessage)
	{
		final JsonResult apiResponseResult = new JsonResult();
		apiResponseResult.setCode("?");
		apiResponseResult.setMessage(errorMessage);

		final JsonAPIResponse apiResponse = new JsonAPIResponse();
		apiResponse.setRes(apiResponseResult);
		return apiResponse;
	}

	private static ProductDetails extractProductDetailsFromAPIResponse(@NonNull final JsonAPIResponse apiResponse)
	{
		final JsonProduct product = apiResponse.getProd();
		final JsonPackage pack = apiResponse.getPack();

		final ProductDetails.ProductDetailsBuilder productDetailsBuilder = ProductDetails.builder()
				.lot(product.getLot())
				// IMPORTANT: setting productCode as Lot is not a mistake.
				// For some reason the product.getProductCode() is useless and looks like using provided Lot as ProductCode and Lot works!
				.productCode(product.getLot())
				.productCodeType(ProductCodeType.ofCode(product.getPcs()))
				.expirationDate(product.getExpirationDate())
				.serialNumber(pack.getSerialNumber());

		final JsonProductPackageState activeStatus = CoalesceUtil.coalesce(pack.getState(), JsonProductPackageState.UNKNOWN);
		productDetailsBuilder.activeStatus(activeStatus);

		if (pack.getReasons() != null)
		{
			productDetailsBuilder.inactiveReason(String.join(DELIMITER, pack.getReasons()));
		}

		return productDetailsBuilder.build();
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

	public DecommissionClientResponse decommission(@NonNull final ProductDetails productDetails)
	{
		final String clientTransactionId = newClientTransactionId();
		final UriComponentsBuilder url = prepareActionURL(productDetails, SecurPharmAction.DECOMMISSION)
				.replaceQueryParam(QUERY_PARAM_CTX, clientTransactionId);
		final SecurPharmLog log = executeAction(url, clientTransactionId);

		return DecommissionClientResponse.builder()
				.productDetails(productDetails)
				.log(log)
				.build();
	}

	public UndoDecommissionClientResponse undoDecommission(
			@NonNull final ProductDetails productDetails,
			@NonNull final String serverTransactionId)
	{
		final String clientTransactionId = newClientTransactionId();
		final UriComponentsBuilder url = prepareActionURL(productDetails, SecurPharmAction.UNDO_DECOMMISSION)
				.replaceQueryParam(QUERY_PARAM_CTX, clientTransactionId)
				.replaceQueryParam(QUERY_PARAM_TRX, serverTransactionId);

		final SecurPharmLog log = executeAction(url, clientTransactionId);

		return UndoDecommissionClientResponse.builder()
				.productDetails(productDetails)
				.log(log)
				.build();
	}

	private SecurPharmLog executeAction(
			@NonNull final UriComponentsBuilder url,
			@NonNull final String clientTransactionId)
	{
		final SecurPharmLog.SecurPharmLogBuilder log = SecurPharmLog.builder()
				.clientTransactionId(clientTransactionId)
				.requestTime(Instant.now())
				.requestUrl(config.getPharmaAPIBaseUrl() + url.build())
				.requestMethod(HttpMethod.PUT);

		try
		{
			final ResponseEntity<String> response = performRequest(url, HttpMethod.PUT);
			final String apiResponseString = response.getBody();
			log.responseTime(Instant.now());
			log.responseCode(response.getStatusCode());
			log.responseData(apiResponseString);

			final JsonAPIResponse apiResponse = fromJsonString(apiResponseString, JsonAPIResponse.class);
			log.serverTransactionId(apiResponse.getServerTransactionId());

			if (response.getStatusCode() == HttpStatus.OK && apiResponse.isTransactionSet())
			{
				log.error(false);
			}
			else
			{
				log.error(true);
			}
		}
		catch (final HttpClientErrorException ex)
		{
			logger.debug("Got HTTP client error", ex);

			log.error(true);
			log.responseTime(Instant.now());
			log.responseCode(ex.getStatusCode());
			log.responseData(ex.getResponseBodyAsString());
		}
		catch (final Throwable ex)
		{
			logger.debug("Got internal error", ex);

			log.error(true);
			log.responseTime(Instant.now());
			// log.responseCode(ex.getStatusCode());
			log.responseData(ex.getLocalizedMessage());
		}

		return log.build();
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
			@NonNull final ProductDetails productDetails,
			@NonNull final SecurPharmAction action)
	{
		return prepareProductURL(productDetails)
				.replaceQueryParam(QUERY_PARAM_SID, config.getApplicationUUID())
				.replaceQueryParam(QUERY_PARAM_ACT, action.getCode());
	}

	private UriComponentsBuilder prepareProductURL(@NonNull final ProductDetails productDetails)
	{
		return UriComponentsBuilder.fromPath(API_RELATIVE_PATH_PRODUCTS)
				.path(productDetails.getProductCode())
				.path(API_RELATIVE_PATH_PACKS)
				.path(encodeBase64url(productDetails.getSerialNumber()))
				// .queryParam(QUERY_PARAM_CTX, clientTransactionId)
				// .queryParam(QUERY_PARAM_SID, config.getApplicationUUID())
				.queryParam(QUERY_PARAM_LOT, encodeBase64url(productDetails.getLot()))
				.queryParam(QUERY_PARAM_EXP, productDetails.getExpirationDate().toYYMMDDString())
				.queryParam(QUERY_PARAM_PCS, productDetails.getProductCodeType().getCode());
	}

	public void authenticate()
	{
		authenticator.authenticate();
	}

	private String getAuthorizationHttpHeader()
	{
		return authenticator.getAuthorizationHttpHeader();
	}

	@Value(staticConstructor = "of")
	private static class APIReponseWithLog
	{
		@NonNull
		SecurPharmLog log;
		@NonNull
		JsonAPIResponse response;
	}
}
