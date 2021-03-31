/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.shopware6.api.model.GetBearerRequest;
import de.metas.camel.externalsystems.shopware6.api.model.JsonOauthResponse;
import de.metas.camel.externalsystems.shopware6.api.model.PathSegmentsEnum;
import de.metas.camel.externalsystems.shopware6.api.model.QueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrder;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddressAndCustomId;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAndCustomId;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.CONNECTION_TIMEOUT_SECONDS;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.JSON_NODE_DATA;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.JSON_NODE_DELIVERY_ADDRESS;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.JSON_NODE_ORDER_CUSTOMER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.READ_TIMEOUT_SECONDS;
import static java.time.temporal.ChronoUnit.SECONDS;

@AllArgsConstructor
public class ShopwareClient
{
	private static final Logger logger = Logger.getLogger(ShopwareClient.class.getName());

	@NonNull
	private final AuthToken authToken;
	@NonNull
	private final String baseUrl;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static ShopwareClient of(
			@NonNull final String clientId,
			@NonNull final String clientSecret,
			@NonNull final String baseUrl)
	{
		final AuthToken authToken = AuthToken.builder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.validUntil(Instant.ofEpochMilli(0))
				.build();

		return new ShopwareClient(authToken, baseUrl);
	}

	@NonNull
	public List<JsonOrderAndCustomId> getOrders(@NonNull final QueryRequest queryRequest, @Nullable final String customIdentifierJSONPath)
	{
		final URI resourceURI;
		final List<JsonOrderAndCustomId> ordersAndCustomIds = new ArrayList<>();


		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.V3.getValue())
				.pathSegment(PathSegmentsEnum.SEARCH.getValue())
				.pathSegment(PathSegmentsEnum.ORDER.getValue());

		refreshTokenIfExpired();

		resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.POST, String.class, queryRequest);

		if (response == null || response.getBody() == null)
		{
			return ordersAndCustomIds;
		}
		else
		{
			final JsonNode arrayJsonNode;

			try
			{
				final JsonNode rootJsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);
				arrayJsonNode = rootJsonNode.get(JSON_NODE_DATA);
			}
			catch (final JsonProcessingException e)
			{
				throw new RuntimeException(e);
			}

			if(arrayJsonNode != null)
			{
				for(final JsonNode orderCustomerNode : arrayJsonNode)
				{
					final Optional<JsonOrderAndCustomId> orderAddressCustomId =
							getJsonOrderCustomId(orderCustomerNode, customIdentifierJSONPath);

					orderAddressCustomId.ifPresent(ordersAndCustomIds::add);
				}
			}
		}

		return ordersAndCustomIds;
	}

	@NonNull
	public Optional<JsonOrderAddressAndCustomId> getOrderAddressDetails(@NonNull final String orderAddressId, @Nullable final String customIdentifierJSONPath)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.V3.getValue())
				.pathSegment(PathSegmentsEnum.ORDER_ADDRESS.getValue())
				.pathSegment(orderAddressId);

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.GET, String.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		try
		{
			final JsonNode jsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);

			return getJsonOrderAddressCustomId(jsonNode.get(JSON_NODE_DATA), customIdentifierJSONPath);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}

	}

	@NonNull
	public List<JsonOrderAddressAndCustomId> getDeliveryAddresses(@NonNull final String orderId, @Nullable final String customIdentifierJSONPath)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
		final List<JsonOrderAddressAndCustomId> deliveryAddresses = new ArrayList<>();

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.V3.getValue())
				.pathSegment(PathSegmentsEnum.ORDER.getValue())
				.pathSegment(orderId)
				.pathSegment(PathSegmentsEnum.DELIVERIES.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.GET, String.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return deliveryAddresses;
		}
		else
		{
			final JsonNode arrayJsonNode;

			try
			{
				final JsonNode rootJsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);
				arrayJsonNode = rootJsonNode.get(JSON_NODE_DATA);
			}
			catch (final JsonProcessingException e)
			{
				throw new RuntimeException(e);
			}

			if(arrayJsonNode != null)
			{
				for(final JsonNode deliveryNode : arrayJsonNode)
				{
					final Optional<JsonOrderAddressAndCustomId> orderAddressCustomId =
							getJsonOrderAddressCustomId(deliveryNode.get(JSON_NODE_DELIVERY_ADDRESS), customIdentifierJSONPath);

					orderAddressCustomId.ifPresent(deliveryAddresses::add);
				}
			}
		}

		return deliveryAddresses;
	}

	@NonNull
	public Optional<JsonCountry> getCountryDetails(@NonNull final String countryId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.V3.getValue())
				.pathSegment(PathSegmentsEnum.COUNTRY.getValue())
				.pathSegment(countryId);

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.GET, String.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		final ObjectMapper objectMapper = new ObjectMapper();

		try
		{
			final JsonNode jsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);
			return jsonNode.get(JSON_NODE_DATA) != null
					? Optional.ofNullable(objectMapper.treeToValue(jsonNode.get(JSON_NODE_DATA), JsonCountry.class))
					: Optional.empty();
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	private Optional<JsonOrderAndCustomId> getJsonOrderCustomId(@Nullable final JsonNode orderJson, @Nullable final String customIdJSONPath)
	{
		if(orderJson == null)
		{
			return Optional.empty();
		}

		try
		{
			final JsonOrder jsonOrder = objectMapper.treeToValue(orderJson, JsonOrder.class);

			final JsonOrderAndCustomId.JsonOrderAndCustomIdBuilder jsonOrderAndCustomIdBuilder =
					JsonOrderAndCustomId.builder()
					.jsonOrder(jsonOrder);

			if (jsonOrder != null && customIdJSONPath != null)
			{
				final String customId = orderJson.at(customIdJSONPath).asText();

				if (!Strings.isBlank(customId))
				{
					jsonOrderAndCustomIdBuilder.customId(customId);
				}
				else
				{
					throw new RuntimeException("Custom Identifier path provided for BPartner, but no custom identifier found. Order default identifier:" + jsonOrder.getId());
				}
			}

			return Optional.ofNullable(jsonOrderAndCustomIdBuilder.build());

		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}

	}


	@NonNull
	private Optional<JsonOrderAddressAndCustomId> getJsonOrderAddressCustomId(@Nullable final JsonNode orderAddressJson, @Nullable final String customIdJSONPath)
	{
		if(orderAddressJson == null)
		{
			return Optional.empty();
		}

		try
		{
			final JsonOrderAddress jsonOrderAddress = objectMapper.treeToValue(orderAddressJson, JsonOrderAddress.class);

			final JsonOrderAddressAndCustomId.JsonOrderAddressAndCustomIdBuilder jsonOrderAddressWithCustomId = JsonOrderAddressAndCustomId.builder()
					.jsonOrderAddress(jsonOrderAddress);

			if (jsonOrderAddress != null && customIdJSONPath != null)
			{
				final String customId = orderAddressJson.at(customIdJSONPath).asText();

				if (!Strings.isBlank(customId))
				{
					jsonOrderAddressWithCustomId.customId(customId);
				}
				else
				{
					throw new RuntimeException("Custom Identifier path provided for Location, but no custom identifier found. Location default identifier:" + jsonOrderAddress.getId());
				}
			}

			return Optional.ofNullable(jsonOrderAddressWithCustomId.build());

		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Nullable
	private <T> ResponseEntity<T> performWithRetry(
			@NonNull final URI resourceURI,
			@NonNull final HttpMethod httpMethod,
			@NonNull final Class<T> responseType,
			@Nullable final Object requestBody)
	{
		ResponseEntity<T> response = null;

		int retryCount = 0;
		boolean retry = true;

		while (retry)
		{
			try
			{

				final HttpEntity<Object> request = requestBody != null
						? new HttpEntity<>(requestBody, getHttpHeaders())
						: new HttpEntity<>(getHttpHeaders());

				response = restTemplate().exchange(resourceURI, httpMethod, request, responseType);

				retry = false;
			}
			catch (final UnauthorizedException unauthorizedException)
			{
				logger.log(Level.WARNING, "Will refresh authToken as an UnauthorizedException was thrown while calling: " + resourceURI.toString());
				refreshToken();
				retryCount++;
				retry = retryCount < 2;
			}
			catch (final Throwable t)
			{
				logger.log(Level.SEVERE, "Exception while calling: " + resourceURI.toString(), t);
				throw t;
			}
		}
		return response;
	}

	private void refreshTokenIfExpired()
	{
		if (authToken.isExpired())
		{
			refreshToken();
		}
	}

	private void refreshToken()
	{
		try
		{
			final URI resourceURI;

			final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
			uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
					.pathSegment(PathSegmentsEnum.OATH.getValue())
					.pathSegment(PathSegmentsEnum.TOKEN.getValue());

			final HttpEntity<GetBearerRequest> request = new HttpEntity<>(authToken.toGetBearerRequest(), getHttpHeaders());

			resourceURI = uriBuilder.build().toUri();

			final ResponseEntity<JsonOauthResponse> response = restTemplate().exchange(resourceURI, HttpMethod.POST, request, JsonOauthResponse.class);

			if (response.getBody() == null)
			{
				throw new RuntimeException("No access token returned from shopware!");
			}

			final JsonOauthResponse oauthResponse = response.getBody();

			final Instant validUntil = Instant.now().plusSeconds(oauthResponse.getExpiresIn());

			this.authToken.updateBearer(oauthResponse.getAccessToken(), validUntil);
		}
		catch (final Exception exception)
		{
			logger.log(Level.SEVERE, "Exception while trying to refresh the auth token: " + exception.getLocalizedMessage(), exception);
			throw exception;
		}
	}

	private HttpHeaders getHttpHeaders()
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		if (authToken.getBearer() != null)
		{
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + authToken.getBearerNotNull());
		}

		return headers;
	}

	private RestTemplate restTemplate()
	{
		return new RestTemplateBuilder()
				.errorHandler(new ShopwareApiErrorHandler())
				.setConnectTimeout(Duration.of(CONNECTION_TIMEOUT_SECONDS, SECONDS))
				.setReadTimeout(Duration.of(READ_TIMEOUT_SECONDS, SECONDS))
				.build();
	}

	@Builder
	private static class AuthToken
	{
		@NonNull
		private final String clientId;

		@NonNull
		private final String clientSecret;

		@NonNull
		private Instant validUntil;

		@Nullable
		@Getter
		private String bearer;

		public GetBearerRequest toGetBearerRequest()
		{
			return GetBearerRequest.builder()
					.grantType("client_credentials")
					.clientId(clientId)
					.clientSecret(clientSecret)
					.build();
		}

		public void updateBearer(@NonNull final String bearer, @NonNull final Instant validUntil)
		{
			this.bearer = bearer;
			this.validUntil = validUntil;
		}

		public boolean isExpired()
		{
			return bearer == null || Instant.now().isAfter(validUntil);
		}

		@NonNull
		public String getBearerNotNull()
		{
			if (bearer == null)
			{
				throw new RuntimeException("AuthToken.bearer is null!");
			}

			return bearer;
		}
	}
}
