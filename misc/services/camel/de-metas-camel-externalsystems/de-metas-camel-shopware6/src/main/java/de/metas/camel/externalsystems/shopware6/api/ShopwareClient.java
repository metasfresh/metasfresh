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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.shopware6.api.model.GetBearerRequest;
import de.metas.camel.externalsystems.shopware6.api.model.JsonOauthResponse;
import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.PathSegmentsEnum;
import de.metas.camel.externalsystems.shopware6.api.model.Shopware6QueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.api.model.currency.JsonCurrencies;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroup;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroups;
import de.metas.camel.externalsystems.shopware6.api.model.order.AddressDetail;
import de.metas.camel.externalsystems.shopware6.api.model.order.Customer;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonCustomer;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrder;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDelivery;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderLines;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderTransactions;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonPaymentMethod;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderDeliveryItem;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProducts;
import de.metas.camel.externalsystems.shopware6.api.model.salutation.JsonSalutation;
import de.metas.camel.externalsystems.shopware6.api.model.stock.JsonStock;
import de.metas.camel.externalsystems.shopware6.api.model.unit.JsonUnits;
import de.metas.common.util.Check;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
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
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.READ_TIMEOUT_SECONDS;
import static java.time.temporal.ChronoUnit.SECONDS;

@AllArgsConstructor
public class ShopwareClient
{
	private static final Logger logger = Logger.getLogger(ShopwareClient.class.getName());

	@NonNull
	private final PInstanceLogger pInstanceLogger;
	@NonNull
	private final AuthToken authToken;
	@NonNull
	private final String baseUrl;

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	public static ShopwareClient of(
			@NonNull final String clientId,
			@NonNull final String clientSecret,
			@NonNull final String baseUrl,
			@NonNull final PInstanceLogger pInstanceLogger)
	{
		final AuthToken authToken = AuthToken.builder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.validUntil(Instant.ofEpochMilli(0))
				.build();

		return new ShopwareClient(pInstanceLogger, authToken, baseUrl);
	}

	public void exportStock(
			@NonNull final JsonStock jsonStock,
			@NonNull final String externalReference)
	{
		final URI resourceURI;
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.PRODUCT.getValue())
				.pathSegment(externalReference);

		refreshTokenIfExpired();

		resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.PATCH, String.class, jsonStock);

		if (response == null || !response.getStatusCode().is2xxSuccessful())
		{
			throw new RuntimeException("Error while exporting stock to Shopware");
		}
	}

	@NonNull
	public GetOrdersResponse getOrders(
			@NonNull final Shopware6QueryRequest queryRequest,
			@Nullable final String salesRepJSONPath)
	{
		final URI resourceURI;

		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.SEARCH.getValue())
				.pathSegment(PathSegmentsEnum.ORDER.getValue());

		refreshTokenIfExpired();

		resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.POST, String.class, queryRequest);

		if (response == null || Check.isBlank(response.getBody()))
		{
			final int rawSize = 0;
			return new GetOrdersResponse(ImmutableList.of(), null, rawSize);
		}

		final ImmutableList.Builder<OrderCandidate> orderCandidates = ImmutableList.builder();

		final Optional<JsonNode> rootJsonNodeOpt = readDataJsonNode(response);

		if (rootJsonNodeOpt.isEmpty())
		{
			final int rawSize = 0;
			return new GetOrdersResponse(orderCandidates.build(), response.getBody(), rawSize);
		}

		final JsonNode rootJsonNode = rootJsonNodeOpt.get();

		for (final JsonNode orderCustomerNode : rootJsonNode)
		{
			getOrderCandidate(orderCustomerNode, salesRepJSONPath)
					.ifPresent(orderCandidates::add);
		}

		final int rawSize = Optional.of(rootJsonNode).map(JsonNode::size).orElse(0);

		return new GetOrdersResponse(orderCandidates.build(), response.getBody(), rawSize);
		}

	@Value
	public static class GetOrdersResponse
	{
		ImmutableList<OrderCandidate> orderCandidates;
		String rawData;
		int rawSize;
	}

	@NonNull
	public Optional<AddressDetail> getOrderAddressDetails(
			@NonNull final String orderAddressId,
			@Nullable final String customShopwareIdJSONPath,
			@Nullable final String customMetasfreshIdJSONPath,
			@Nullable final String emailJSONPath)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
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

			return getAddressDetails(jsonNode.get(JSON_NODE_DATA),
									 customShopwareIdJSONPath,
									 customMetasfreshIdJSONPath,
									 emailJSONPath);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	public List<OrderDeliveryItem> getDeliveryAddresses(
			@NonNull final String orderId,
			@Nullable final String customShopwareIdentifierJSONPath,
			@Nullable final String customMetasfreshIdentifierJSONPath,
			@Nullable final String emailJSONPath)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
		final List<OrderDeliveryItem> deliveryItemList = new ArrayList<>();

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.ORDER.getValue())
				.pathSegment(orderId)
				.pathSegment(PathSegmentsEnum.DELIVERIES.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.GET, String.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return deliveryItemList;
		}

		final JsonNode arrayJsonNode;

		try
		{
			final JsonNode rootJsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);
			arrayJsonNode = rootJsonNode.get(JSON_NODE_DATA);

			if (arrayJsonNode == null)
			{
				return deliveryItemList;
			}

			for (final JsonNode deliveryNode : arrayJsonNode)
			{
				final Optional<AddressDetail> orderAddressDetails =
						getAddressDetails(deliveryNode.get(JSON_NODE_DELIVERY_ADDRESS),
										  customShopwareIdentifierJSONPath,
										  customMetasfreshIdentifierJSONPath,
										  emailJSONPath);

				final JsonOrderDelivery orderDelivery = objectMapper.treeToValue(deliveryNode, JsonOrderDelivery.class);

				orderAddressDetails
						.map(address -> OrderDeliveryItem.builder()
								.orderAddressDetails(address)
								.jsonOrderDelivery(orderDelivery)
								.build())
						.ifPresent(deliveryItemList::add);
			}
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}

		return deliveryItemList;
	}

	@NonNull
	public Optional<JsonCountry> getCountryDetails(@NonNull final String countryId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
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
	public Optional<JsonOrderLines> getOrderLines(@NonNull final String orderId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.ORDER.getValue())
				.pathSegment(orderId)
				.pathSegment(PathSegmentsEnum.LINE_ITEMS.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonOrderLines> response = performWithRetry(resourceURI, HttpMethod.GET, JsonOrderLines.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		return Optional.of(response.getBody());
	}

	@NonNull
	public Optional<JsonOrderTransactions> getOrderTransactions(@NonNull final String orderId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.ORDER.getValue())
				.pathSegment(orderId)
				.pathSegment(PathSegmentsEnum.TRANSACTIONS.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonOrderTransactions> response = performWithRetry(resourceURI, HttpMethod.GET, JsonOrderTransactions.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		return Optional.of(response.getBody());
	}

	@NonNull
	public Optional<JsonPaymentMethod> getPaymentMethod(@NonNull final String paymentMethodId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.PAYMENT_METHOD.getValue())
				.pathSegment(paymentMethodId);

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonNode> response = performWithRetry(resourceURI, HttpMethod.GET, JsonNode.class, null /*requestBody*/);

		if (response == null || response.getBody() == null || response.getBody().get(JSON_NODE_DATA) == null)
		{
			return Optional.empty();
		}

		final JsonPaymentMethod jsonPaymentMethod;
		try
		{
			jsonPaymentMethod = new ObjectMapper().treeToValue(response.getBody().get(JSON_NODE_DATA), JsonPaymentMethod.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}

		return Optional.of(jsonPaymentMethod);
	}

	@NonNull
	public JsonCurrencies getCurrencies()
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.CURRENCY.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonCurrencies> response = performWithRetry(resourceURI, HttpMethod.GET, JsonCurrencies.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			throw new RuntimeException("No currencies return from Shopware!");
		}

		return response.getBody();
	}

	@NonNull
	public Optional<JsonSalutation> getSalutations()
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.SALUTATION.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonSalutation> response = performWithRetry(resourceURI, HttpMethod.GET, JsonSalutation.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		return Optional.of(response.getBody());
	}

	@NonNull
	public Optional<JsonCustomerGroups> getCustomerGroup(@NonNull final String customerId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.CUSTOMER.getValue())
				.pathSegment(customerId)
				.pathSegment(PathSegmentsEnum.GROUP.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonCustomerGroups> response = performWithRetry(resourceURI, HttpMethod.GET, JsonCustomerGroups.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		return Optional.of(response.getBody());
	}

	@NonNull
	public Optional<JsonProducts> getProducts(@NonNull final MultiQueryRequest queryRequest)
	{
		final URI resourceURI;

		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.SEARCH.getValue())
				.pathSegment(PathSegmentsEnum.PRODUCT.getValue());

		refreshTokenIfExpired();

		resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonProducts> response = performWithRetry(resourceURI, HttpMethod.POST, JsonProducts.class, queryRequest);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		return Optional.of(response.getBody());
	}

	@NonNull
	public JsonUnits getUnits()
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.UNIT.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<JsonUnits> response = performWithRetry(resourceURI, HttpMethod.GET, JsonUnits.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			throw new RuntimeException("No units return from Shopware!");
		}

		return response.getBody();
	}

	@NonNull
	public List<Customer> getCustomerCandidates(@NonNull final MultiQueryRequest queryRequest)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.SEARCH.getValue())
				.pathSegment(PathSegmentsEnum.CUSTOMER.getValue());

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.POST, String.class, queryRequest);

		if (response == null || Check.isBlank(response.getBody()))
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<Customer> customerCandidatesBuilder = ImmutableList.builder();

		final Optional<JsonNode> rootJsonNodeOpt = readDataJsonNode(response);

		if (rootJsonNodeOpt.isPresent())
		{
			final JsonNode rootJsonNode = rootJsonNodeOpt.get();

			for (final JsonNode customerNode : rootJsonNode)
			{
				getCustomerCandidate(customerNode)
						.ifPresent(customerCandidatesBuilder::add);
			}
		}

		return customerCandidatesBuilder.build();
	}

	@NonNull
	public Optional<JsonCustomerGroup> getGroupInformation(@NonNull final String groupId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.CUSTOMER_GROUP.getValue())
				.pathSegment(groupId);

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.GET, String.class, null);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		final Optional<JsonNode> rootJsonNodeOpt = readDataJsonNode(response);

		if (rootJsonNodeOpt.isEmpty())
		{
			return Optional.empty();
		}

		final JsonNode rootJsonNode = rootJsonNodeOpt.get();

		try
		{
			return Optional.of(objectMapper.treeToValue(rootJsonNode, JsonCustomerGroup.class));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	public Optional<AddressDetail> getCustomerAddressDetail(@NonNull final String customerAddressDetailId)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);

		uriBuilder.pathSegment(PathSegmentsEnum.API.getValue())
				.pathSegment(PathSegmentsEnum.CUSTOMER_ADDRESS.getValue())
				.pathSegment(customerAddressDetailId);

		refreshTokenIfExpired();

		final URI resourceURI = uriBuilder.build().toUri();

		final ResponseEntity<String> response = performWithRetry(resourceURI, HttpMethod.GET, String.class, null /*requestBody*/);

		if (response == null || response.getBody() == null)
		{
			return Optional.empty();
		}

		return readDataJsonNode(response)
				.flatMap(dataNode -> getAddressDetails(dataNode, null, null, null));
	}

	@NonNull
	private Optional<OrderCandidate> getOrderCandidate(
			@Nullable final JsonNode orderJson,
			@Nullable final String salesRepJSONPath)
	{
		if (orderJson == null)
		{
			pInstanceLogger.logMessage("Skipping the current 'order' because orderJson is null!");
			return Optional.empty();
		}

		try
		{
			final JsonOrder jsonOrder = objectMapper.treeToValue(orderJson, JsonOrder.class);

			if (Check.isBlank(jsonOrder.getOrderCustomer().getCustomerId()))
			{
				pInstanceLogger.logMessage("Order " + jsonOrder.getOrderNumber() + " (ID=" + jsonOrder.getId() + "): Skipping because jsonOrder.getOrderCustomer().getCustomerId() is null!");
				return Optional.empty();
			}

			final OrderCandidate.OrderCandidateBuilder orderCandidateBuilder =
					OrderCandidate.builder()
							.jsonOrder(jsonOrder)
							.orderNode(orderJson);

			if (Check.isNotBlank(salesRepJSONPath))
			{
				final JsonNode node = orderJson.at(salesRepJSONPath);
				final String salesRepId = node.isNull() ? null : node.asText();
				orderCandidateBuilder.salesRepId(Check.isBlank(salesRepId) ? null : salesRepId);
			}

			return Optional.of(orderCandidateBuilder.build());

		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param customShopwareIdJSONPath optional; if given, then the given JSON needs to contain the respective element
	 */
	@NonNull
	private Optional<AddressDetail> getAddressDetails(
			@Nullable final JsonNode orderAddressJson,
			@Nullable final String customShopwareIdJSONPath,
			@Nullable final String customMetasfreshIdJSONPath,
			@Nullable final String emailJSONPath)
	{
		if (orderAddressJson == null)
		{
			return Optional.empty();
		}

		try
		{
			final JsonAddress jsonAddress = objectMapper.treeToValue(orderAddressJson, JsonAddress.class);

			final AddressDetail.AddressDetailBuilder addressDetailBuilder = AddressDetail.builder()
					.jsonAddress(jsonAddress);

			if (Check.isNotBlank(customShopwareIdJSONPath))
			{
				final JsonNode node = orderAddressJson.at(customShopwareIdJSONPath);
				final String customShopwareId = node.isNull() ? null : node.asText();
				if (Check.isNotBlank(customShopwareId))
				{
					addressDetailBuilder.customShopwareId(customShopwareId);
				}
				else
				{
					throw new RuntimeException("Custom Identifier path provided for Location, but no custom identifier found. Location default identifier:" + jsonAddress.getId());
				}
			}
			if (Check.isNotBlank(customMetasfreshIdJSONPath))
			{
				final JsonNode node = orderAddressJson.at(customMetasfreshIdJSONPath);
				final String customMetasfreshId = node.isNull() ? null : node.asText();
				addressDetailBuilder.customMetasfreshId(Check.isBlank(customMetasfreshId) ? null : customMetasfreshId);
			}

			if (Check.isNotBlank(emailJSONPath))
			{
				final JsonNode node = orderAddressJson.at(emailJSONPath);
				final String email = node.isNull() ? null : node.asText();
				addressDetailBuilder.customEmail(Check.isBlank(email) ? null : email);
			}

			return Optional.ofNullable(addressDetailBuilder.build());

		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	private Optional<Customer> getCustomerCandidate(@Nullable final JsonNode customerNode)
	{
		if (customerNode == null)
		{
			pInstanceLogger.logMessage("Skipping the current 'customer' because JsonNode is null!");
			return Optional.empty();
		}

		try
		{
			final JsonCustomer jsonCustomer = objectMapper.treeToValue(customerNode, JsonCustomer.class);

			return Optional.of(Customer.of(customerNode, jsonCustomer));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Nullable
	@VisibleForTesting
	public <T> ResponseEntity<T> performWithRetry(
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
				logger.log(Level.WARNING, "Will refresh authToken as an UnauthorizedException was thrown while calling: " + resourceURI);
				refreshToken();
				retryCount++;
				retry = retryCount < 2;
			}
			catch (final Throwable t)
			{
				logger.log(Level.SEVERE, "Exception while calling: " + resourceURI, t);
				throw t;
			}
		}
		return response;
	}

	@VisibleForTesting
	public void refreshTokenIfExpired()
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
		headers.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));

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

	@NonNull
	private static Optional<JsonNode> readDataJsonNode(@NonNull final ResponseEntity<String> response)
	{
		try
		{
			final JsonNode rootJsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);
			return Optional.ofNullable(rootJsonNode.get(JSON_NODE_DATA));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
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
