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

package de.metas.camel.externalsystems.shopware6.processor;

import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.JsonFilter;
import de.metas.camel.externalsystems.shopware6.api.model.JsonQuery;
import de.metas.camel.externalsystems.shopware6.api.model.QueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAndCustomId;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_CREATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_UPDATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.PARAMETERS_DATE_GTE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_ORG_CODE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_LOCATION_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_SHOPWARE_CLIENT;

public class GetOrdersProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(HEADER_ORG_CODE, request.getOrgCode());
		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());

			ProcessorHelper.logProcessMessage(exchange, "Shopware6:GetOrders process started!" + Instant.now(), request.getAdPInstanceId().getValue());
		}

		final String clientId = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_ID);
		final String clientSecret = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_SECRET);

		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final String updatedAfter = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER);

		final String bPartnerIdJSONPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_ID);
		final String bPartnerLocationIdJSONPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID);

		final ShopwareClient shopwareClient = ShopwareClient.of(clientId, clientSecret, basePath);
		final QueryRequest getOrdersRequest = buildQueryOrdersRequest(updatedAfter);

		final List<JsonOrderAndCustomId> ordersToProcess = shopwareClient.getOrders(getOrdersRequest, bPartnerIdJSONPath);

		exchange.getIn().setBody(ordersToProcess);
		exchange.setProperty(ROUTE_PROPERTY_ORG_CODE, request.getOrgCode());
		exchange.setProperty(ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_LOCATION_ID, bPartnerLocationIdJSONPath);
		exchange.setProperty(ROUTE_PROPERTY_SHOPWARE_CLIENT, shopwareClient);

	}

	@NonNull
	private QueryRequest buildQueryOrdersRequest(@NonNull final String updatedAfter)
	{
		final HashMap<String, String> parameters = new HashMap<>();
		parameters.put(PARAMETERS_DATE_GTE, updatedAfter);

		return QueryRequest.builder()
				.filter(JsonFilter.builder()
								.filterType(JsonFilter.FilterType.MULTI)
								.operatorType(JsonFilter.OperatorType.OR)
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
}
