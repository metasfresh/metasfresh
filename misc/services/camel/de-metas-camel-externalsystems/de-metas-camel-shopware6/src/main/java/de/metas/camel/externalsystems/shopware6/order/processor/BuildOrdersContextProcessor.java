/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2022 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.currency.CurrencyInfoProvider;
import de.metas.camel.externalsystems.shopware6.currency.GetCurrenciesRequest;
import de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteHelper;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.camel.externalsystems.shopware6.order.query.OrderQueryHelper;
import de.metas.camel.externalsystems.shopware6.product.GetProductsRouteHelper;
import de.metas.camel.externalsystems.shopware6.salutation.GetSalutationsRequest;
import de.metas.camel.externalsystems.shopware6.salutation.SalutationInfoProvider;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonOrderProcessingConfig;
import de.metas.common.externalsystem.JsonProductLookup;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import de.metas.common.util.NumberUtils;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.shopware6.currency.GetCurrenciesRoute.GET_CURRENCY_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.salutation.GetSalutationsRoute.GET_SALUTATION_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_NORMAL_VAT_RATES;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_REDUCED_VAT_RATES;


public class BuildOrdersContextProcessor implements Processor
{

	private final ProcessLogger processLogger;
	private final ProducerTemplate producerTemplate;

	public BuildOrdersContextProcessor(@NonNull final ProcessLogger processLogger, @NonNull final ProducerTemplate producerTemplate)
	{
		this.processLogger = processLogger;
		this.producerTemplate = producerTemplate;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(HEADER_ORG_CODE, request.getOrgCode());
		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());

			processLogger.logMessage("Shopware6:GetOrders process started!" + Instant.now(), request.getAdPInstanceId().getValue());
		}

		final String clientId = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_ID);
		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final String clientSecret = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_SECRET);

		final PInstanceLogger pInstanceLogger = PInstanceLogger.builder()
				.processLogger(processLogger)
				.pInstanceId(request.getAdPInstanceId())
				.build();

		final ShopwareClient shopwareClient = ShopwareClient.of(clientId, clientSecret, basePath, pInstanceLogger);

		final CurrencyInfoProvider currencyInfoProvider = getCurrencyInfoProvider(basePath, clientId, clientSecret);

		final SalutationInfoProvider salutationInfoProvider = getSalutationInfoProvider(basePath, clientId, clientSecret);

		final ImportOrdersRouteContext ordersContext = buildContext(request,
																	shopwareClient,
																	currencyInfoProvider,
																	salutationInfoProvider,
																	ExternalSystemConstants.DEFAULT_SW6_ORDER_PAGE_SIZE);

		exchange.setProperty(ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ordersContext);
	}

	@VisibleForTesting
	public static ImportOrdersRouteContext buildContext(
			@NonNull final JsonExternalSystemRequest request,
			@NonNull final ShopwareClient shopwareClient,
			@NonNull final CurrencyInfoProvider currencyInfoProvider,
			@NonNull final SalutationInfoProvider salutationInfoProvider,
			final int pageLimit)
	{
		final String bpLocationCustomJsonPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID);
		final String emailJsonPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_EMAIL);

		final String productLookup = request.getParameters().get(ExternalSystemConstants.PARAM_PRODUCT_LOOKUP);

		Check.assumeNotNull(productLookup, "JsonExternalSystemRequest.parameters[ProductLookup] can't be missing");
		final String orderProcessing = request.getParameters().get(ExternalSystemConstants.PARAM_ORDER_PROCESSING);

		Check.assumeNotNull(orderProcessing, "JsonExternalSystemRequest.parameters[OrderProcessing] can't be missing");

		return ImportOrdersRouteContext.builder()
				.orgCode(request.getOrgCode())
				.externalSystemRequest(request)
				.shopware6ConfigMappings(GetOrdersRouteHelper.getSalesOrderMappingRules(request).orElse(null))
				.shopwareClient(shopwareClient)
				.pInstanceId(request.getAdPInstanceId())
				.bpLocationCustomJsonPath(bpLocationCustomJsonPath)
				.emailJsonPath(emailJsonPath)
				.currencyInfoProvider(currencyInfoProvider)
				.salutationInfoProvider(salutationInfoProvider)
				.taxProductIdProvider(getTaxProductIdProvider(request))
				.skipNextImportStartingTimestamp(OrderQueryHelper.shouldIgnoreNextImportTimestamp(request))
				.jsonProductLookup(JsonProductLookup.valueOf(productLookup))
				.metasfreshIdJsonPath(request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_METASFRESH_ID))
				.shopwareIdJsonPath(request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_SHOPWARE_ID))
				.ordersResponsePageIndex(1)
				.pageLimit(pageLimit)
				.priceListBasicInfo(GetProductsRouteHelper.getTargetPriceListInfo(request))
				.ordersResponsePageIndex(1)
				.pageLimit(pageLimit)
				.orderProcessingConfig(JsonOrderProcessingConfig.ofCode(orderProcessing))
				.build();
	}

	@Nullable
	private static TaxProductIdProvider getTaxProductIdProvider(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		final ImmutableMap.Builder<JsonMetasfreshId, List<BigDecimal>> productId2VatRatesBuilder = ImmutableMap.builder();
		final Map<String, String> parameters = externalSystemRequest.getParameters();

		final String normalVatRates = parameters.get(PARAM_NORMAL_VAT_RATES);
		final String normalVatProductId = parameters.get(PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID);
		if (Check.isNotBlank(normalVatProductId) && Check.isNotBlank(normalVatRates))
		{
			final List<BigDecimal> rates = NumberUtils.asBigDecimalList(normalVatRates, ",");
			final JsonMetasfreshId productId = JsonMetasfreshId.of(Integer.parseInt(normalVatProductId));

			productId2VatRatesBuilder.put(productId, rates);
		}

		final String reducedVatRates = parameters.get(PARAM_REDUCED_VAT_RATES);
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

	@NonNull
	private CurrencyInfoProvider getCurrencyInfoProvider(@NonNull final String basePath, @NonNull final String clientId, @NonNull final String clientSecret)
	{
		final GetCurrenciesRequest getCurrenciesRequest = GetCurrenciesRequest.builder()
				.baseUrl(basePath)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.build();

		return (CurrencyInfoProvider)producerTemplate
				.sendBody("direct:" + GET_CURRENCY_ROUTE_ID, ExchangePattern.InOut, getCurrenciesRequest);
	}

	@NonNull
	private SalutationInfoProvider getSalutationInfoProvider(@NonNull final String basePath, @NonNull final String clientId, @NonNull final String clientSecret)
	{
		final GetSalutationsRequest getSalutationsRequest = GetSalutationsRequest.builder()
				.baseUrl(basePath)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.build();

		return (SalutationInfoProvider)producerTemplate
				.sendBody("direct:" + GET_SALUTATION_ROUTE_ID, ExchangePattern.InOut, getSalutationsRequest);
	}
}
