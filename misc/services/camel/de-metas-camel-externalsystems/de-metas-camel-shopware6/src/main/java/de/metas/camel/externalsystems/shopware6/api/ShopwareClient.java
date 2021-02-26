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
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDeliveries;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.CONNECTION_TIMEOUT_SECONDS;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.JSON_NODE_DATA;
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
	public Optional<JsonOrders> getOrders(@NonNull final QueryRequest queryRequest)
	{
		final URI resourceURI;

		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.V3.getValue())
				.pathSegment(PathSegmentsEnum.SEARCH.getValue())
				.pathSegment(PathSegmentsEnum.ORDER.getValue());

		refreshTokenIfExpired();

		resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonOrders> response = performWithRetry(resourceURI, HttpMethod.POST, JsonOrders.class, queryRequest);

		return response != null ? Optional.ofNullable(response.getBody()) : Optional.empty();
	}

	@NonNull
	public Optional<JsonOrderAddress> getOrderAddressDetails(@NonNull final String orderAddressId) throws JsonProcessingException
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

		final ObjectMapper objectMapper = new ObjectMapper();

		final JsonNode jsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);

		return jsonNode.get(JSON_NODE_DATA) != null
				? Optional.ofNullable(objectMapper.treeToValue(jsonNode.get(JSON_NODE_DATA), JsonOrderAddress.class))
				: Optional.empty();
	}

	@NonNull
	public Optional<JsonOrderDeliveries> getDeliveries(@NonNull final String orderId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.V3.getValue())
				.pathSegment(PathSegmentsEnum.ORDER.getValue())
				.pathSegment(orderId)
				.pathSegment(PathSegmentsEnum.DELIVERIES.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonOrderDeliveries> response = performWithRetry(resourceURI, HttpMethod.GET, JsonOrderDeliveries.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		return Optional.of(response.getBody());
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
