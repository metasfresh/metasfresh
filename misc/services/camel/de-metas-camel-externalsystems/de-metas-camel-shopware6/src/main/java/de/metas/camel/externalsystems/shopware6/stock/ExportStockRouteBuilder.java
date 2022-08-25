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

package de.metas.camel.externalsystems.shopware6.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.stock.JsonStock;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonAvailableForSales;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_EXPORT_STOCK_CONTEXT;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ExportStockRouteBuilder extends RouteBuilder
{
	public static final String EXPORT_STOCK_ROUTE_ID = "Shopware6-exportStock";
	public static final String ATTACH_CONTEXT_PROCESSOR_ID = "Shopware6-Stock-contextProcessorId";
	public static final String CREATE_JSON_STOCK_PROCESSOR_ID = "Shopware6-Stock-createJsonStockProcessorId";
	public static final String EXPORT_STOCK_PROCESSOR_ID = "Shopware6-Stock-exportStockProcessorId";

	private static final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final ProcessLogger processLogger;

	public ExportStockRouteBuilder(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(direct(EXPORT_STOCK_ROUTE_ID))
				.routeId(EXPORT_STOCK_ROUTE_ID)
				.log("Route invoked")
				.streamCaching()
				.process(this::buildAndAttachRouteContext).id(ATTACH_CONTEXT_PROCESSOR_ID)
				.process(this::createJsonStock).id(CREATE_JSON_STOCK_PROCESSOR_ID)
				.process(this::exportStockToShopware).id(EXPORT_STOCK_PROCESSOR_ID);

	}

	private void exportStockToShopware(@NonNull final Exchange exchange)
	{
		final ExportStockRouteContext exportStockRouteContext = exchange.getProperty(ROUTE_PROPERTY_EXPORT_STOCK_CONTEXT, ExportStockRouteContext.class);

		final JsonStock jsonStock = exchange.getIn().getBody(JsonStock.class);

		final ShopwareClient shopwareClient = exportStockRouteContext.getShopwareClient();

		shopwareClient.exportStock(jsonStock, exportStockRouteContext.getJsonAvailableForSales().getProductIdentifier().getExternalReference());
	}

	private void createJsonStock(@NonNull final Exchange exchange)
	{
		final ExportStockRouteContext exportStockRouteContext = exchange.getProperty(ROUTE_PROPERTY_EXPORT_STOCK_CONTEXT, ExportStockRouteContext.class);

		final BigDecimal stockBD = exportStockRouteContext.getJsonAvailableForSales()
				.getStock()
				.setScale(0, RoundingMode.CEILING);

		final JsonStock jsonStock = JsonStock.builder()
				.stock(stockBD.intValueExact())
				.build();

		exchange.getIn().setBody(jsonStock);
	}

	private void buildAndAttachRouteContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());

			processLogger.logMessage("Shopware6:ExportStock process started!" + Instant.now(), request.getAdPInstanceId().getValue());
		}

		final String clientId = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_ID);
		final String clientSecret = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_SECRET);
		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);

		final PInstanceLogger pInstanceLogger = PInstanceLogger.builder()
				.processLogger(processLogger)
				.pInstanceId(request.getAdPInstanceId())
				.build();

		final ShopwareClient shopwareClient = ShopwareClient.of(clientId, clientSecret, basePath, pInstanceLogger);

		final JsonAvailableForSales jsonAvailableForSales = getJsonAvailableForSales(request);

		final ExportStockRouteContext exportStockRouteContext = ExportStockRouteContext.builder()
				.shopwareClient(shopwareClient)
				.jsonAvailableForSales(jsonAvailableForSales)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_EXPORT_STOCK_CONTEXT, exportStockRouteContext);
	}

	@NonNull
	private JsonAvailableForSales getJsonAvailableForSales(@NonNull final JsonExternalSystemRequest request)
	{
		try
		{
			return objectMapper.readValue(request.getParameters().get(ExternalSystemConstants.PARAM_JSON_AVAILABLE_FOR_SALES), JsonAvailableForSales.class);
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Unable to deserialize JsonAvailableStock", e);
		}
	}
}
