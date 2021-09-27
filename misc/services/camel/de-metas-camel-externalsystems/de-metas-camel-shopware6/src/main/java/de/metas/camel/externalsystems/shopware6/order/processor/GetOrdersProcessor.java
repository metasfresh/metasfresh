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

package de.metas.camel.externalsystems.shopware6.order.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.Shopware6Constants;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient.GetOrdersResponse;
import de.metas.camel.externalsystems.shopware6.api.model.JsonQuery;
import de.metas.camel.externalsystems.shopware6.api.model.MultiJsonFilter;
import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.QueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.Shopware6QueryRequest;
import de.metas.camel.externalsystems.shopware6.currency.CurrencyInfoProvider;
import de.metas.camel.externalsystems.shopware6.currency.GetCurrenciesRequest;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.NumberUtils;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ROUTE_PROPERTY_RAW_DATA;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_CREATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_UPDATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.PARAMETERS_DATE_GTE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.shopware6.currency.GetCurrenciesRoute.GET_CURRENCY_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_NORMAL_VAT_RATES;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_REDUCED_VAT_RATES;

public class GetOrdersProcessor implements Processor
{
	private final ProcessLogger processLogger;

	public GetOrdersProcessor(final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(HEADER_ORG_CODE, request.getOrgCode());
		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());

			processLogger.logMessage("Shopware6:GetOrders process started!" + Instant.now(), request.getAdPInstanceId().getValue());
		}

		final String clientId = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_ID);
		final String clientSecret = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_SECRET);

		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);

		final Shopware6QueryRequest getOrdersRequest;
		final String orderNo = request.getParameters().get(ExternalSystemConstants.PARAM_ORDER_NO);
		if (Check.isNotBlank(orderNo))
		{
			getOrdersRequest = buildOrderNoQueryRequest(orderNo);
		}
		else
		{
			final String updatedAfter = CoalesceUtil.coalesceNotNull(
					request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE),
					request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
					Instant.ofEpochSecond(0).toString());

			getOrdersRequest = buildUpdatedAfterQueryRequest(updatedAfter);
		}

		final String bPartnerIdJSONPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_ID);
		final String salesRepJSONPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_SALES_REP_ID);

		final ShopwareClient shopwareClient = ShopwareClient.of(clientId, clientSecret, basePath);
		
		final GetOrdersResponse ordersToProcess = shopwareClient.getOrders(getOrdersRequest, bPartnerIdJSONPath, salesRepJSONPath);

		exchange.getIn().setBody(ordersToProcess.getOrderCandidates());
		exchange.setProperty(ROUTE_PROPERTY_RAW_DATA, ordersToProcess.getRawData());
		
		final GetCurrenciesRequest getCurrenciesRequest = GetCurrenciesRequest.builder()
				.baseUrl(basePath)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.build();

		final CurrencyInfoProvider currencyInfoProvider = (CurrencyInfoProvider)exchange.getContext().createProducerTemplate()
				.sendBody("direct:" + GET_CURRENCY_ROUTE_ID, ExchangePattern.InOut, getCurrenciesRequest);

		final ImportOrdersRouteContext ordersContext = buildContext(request, shopwareClient, currencyInfoProvider);

		exchange.setProperty(ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ordersContext);
	}

	@VisibleForTesting
	public ImportOrdersRouteContext buildContext(
			@NonNull final JsonExternalSystemRequest request,
			@NonNull final ShopwareClient shopwareClient,
			@NonNull final CurrencyInfoProvider currencyInfoProvider)
	{
		final String bpLocationCustomJsonPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID);

		return ImportOrdersRouteContext.builder()
				.orgCode(request.getOrgCode())
				.externalSystemRequest(request)
				.shopware6ConfigMappings(getSalesOrderMappingRules(request).orElse(null))
				.shopwareClient(shopwareClient)
				.bpLocationCustomJsonPath(bpLocationCustomJsonPath)
				.currencyInfoProvider(currencyInfoProvider)
				.taxProductIdProvider(getTaxProductIdProvider(request))
				.build();
	}

	@NonNull
	private Optional<JsonExternalSystemShopware6ConfigMappings> getSalesOrderMappingRules(@NonNull final JsonExternalSystemRequest request)
	{
		final String shopware6Mappings = request.getParameters().get(ExternalSystemConstants.PARAM_CONFIG_MAPPINGS);

		if (Check.isBlank(shopware6Mappings))
		{
			return Optional.empty();
		}

		final ObjectMapper mapper = new ObjectMapper();
		try
		{
			return Optional.of(mapper.readValue(shopware6Mappings, JsonExternalSystemShopware6ConfigMappings.class));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	@VisibleForTesting
	public static  QueryRequest buildOrderNoQueryRequest(@NonNull final String orderNo)
	{
		return QueryRequest.builder()
				.query(JsonQuery.builder()
							   .field(Shopware6Constants.FIELD_ORDER_NUMBER)
							   .queryType(JsonQuery.QueryType.EQUALS)
							   .value(orderNo)
							   .build())
				.build();
	}
	
	@NonNull
	@VisibleForTesting
	public static MultiQueryRequest buildUpdatedAfterQueryRequest(@NonNull final String updatedAfter)
	{
		final HashMap<String, String> parameters = new HashMap<>();
		parameters.put(PARAMETERS_DATE_GTE, updatedAfter);

		return MultiQueryRequest.builder()
				.filter(MultiJsonFilter.builder()
								.operatorType(MultiJsonFilter.OperatorType.OR)
								.jsonQuery(JsonQuery.builder()
												   .field(FIELD_UPDATED_AT)
												   .queryType(JsonQuery.QueryType.RANGE)
												   .parameters(parameters)
												   .build())
								.jsonQuery(JsonQuery.builder()
												   .field(FIELD_CREATED_AT)
												   .queryType(JsonQuery.QueryType.RANGE)
												   .parameters(parameters)
												   .build())
								.build())
				.build();
	}

	@Nullable
	private TaxProductIdProvider getTaxProductIdProvider(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		final ImmutableMap.Builder<JsonMetasfreshId, List<BigDecimal>> productId2VatRatesBuilder = ImmutableMap.builder();
		final Map<String, String> parameters = externalSystemRequest.getParameters();

		final String normalVatRates = parameters.get(PARAM_FREIGHT_COST_NORMAL_VAT_RATES);
		final String normalVatProductId = parameters.get(PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID);
		if (Check.isNotBlank(normalVatProductId) && Check.isNotBlank(normalVatRates))
		{
			final List<BigDecimal> rates = NumberUtils.asBigDecimalList(normalVatRates, ",");
			final JsonMetasfreshId productId = JsonMetasfreshId.of(Integer.parseInt(normalVatProductId));

			productId2VatRatesBuilder.put(productId, rates);
		}

		final String reducedVatRates = parameters.get(PARAM_FREIGHT_COST_REDUCED_VAT_RATES);
		final String reducedVatProductId = parameters.get(PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID);
		if (Check.isNotBlank(reducedVatProductId) && Check.isNotBlank(reducedVatRates))
		{
			final List<BigDecimal> rates = NumberUtils.asBigDecimalList(reducedVatRates, ",");
			final JsonMetasfreshId productId = JsonMetasfreshId.of(Integer.parseInt(reducedVatProductId));

			productId2VatRatesBuilder.put(productId, rates);
		}

		final ImmutableMap<JsonMetasfreshId, List<BigDecimal>> productId2VatRates = productId2VatRatesBuilder.build();

		if (productId2VatRates.isEmpty())
		{
			return null;
		}

		return TaxProductIdProvider.of(productId2VatRates);
	}
}
