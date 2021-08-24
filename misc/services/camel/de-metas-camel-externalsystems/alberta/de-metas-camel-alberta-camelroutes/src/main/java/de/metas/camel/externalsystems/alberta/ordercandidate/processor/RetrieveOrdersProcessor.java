/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.ordercandidate.processor;

import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.camel.externalsystems.alberta.ordercandidate.GetOrdersRouteConstants;
import de.metas.camel.externalsystems.alberta.ordercandidate.NextImportSinceTimestamp;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.CoalesceUtil;
import io.swagger.client.ApiClient;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.OrderApi;
import io.swagger.client.api.PharmacyApi;
import io.swagger.client.model.ArrayOfOrders;
import io.swagger.client.model.Order;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;

public class RetrieveOrdersProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(GetPatientsRouteConstants.HEADER_ORG_CODE, request.getOrgCode());
		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());
		}

		final String apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);
		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final String tenant = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT);

		final String updatedAfter = CoalesceUtil.coalesceNotNull(
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE),
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
				Instant.ofEpochMilli(0).toString());

		final AlbertaConnectionDetails albertaConnectionDetails = AlbertaConnectionDetails.builder()
				.apiKey(apiKey)
				.basePath(basePath)
				.tenant(tenant)
				.build();

		final ApiClient apiClient = new ApiClient();
		apiClient.setBasePath(basePath);

		final OrderApi orderApi = new OrderApi(apiClient);

		final ArrayOfOrders createdOrders = orderApi.getCreatedOrders(apiKey, GetOrdersRouteConstants.OrderStatus.CREATED.getValue(), updatedAfter);

		final @NonNull List<Order> ordersToImport = createdOrders == null || createdOrders.isEmpty()
				? new ArrayList<>()
				: createdOrders;

		final Set<String> createOrderIds = ordersToImport.stream()
				.map(Order::getId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		final List<Order> updatedOrders = orderApi.getCreatedOrders(apiKey, GetOrdersRouteConstants.OrderStatus.UPDATED.getValue(), updatedAfter);

		if (updatedOrders != null && !updatedOrders.isEmpty())
		{
			updatedOrders.stream()
					.filter(order -> order.getId() != null && !createOrderIds.contains(order.getId()))
					.forEach(ordersToImport::add);
		}

		exchange.setProperty(GetOrdersRouteConstants.ROUTE_PROPERTY_ORG_CODE, request.getOrgCode());
		exchange.setProperty(GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_CONN_DETAILS, albertaConnectionDetails);
		exchange.setProperty(GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PHARMACY_API, new PharmacyApi(apiClient));
		exchange.setProperty(GetPatientsRouteConstants.ROUTE_PROPERTY_DOCTOR_API, new DoctorApi(apiClient));

		exchange.setProperty(GetOrdersRouteConstants.ROUTE_PROPERTY_UPDATED_AFTER, new NextImportSinceTimestamp(Instant.parse(updatedAfter)));
		exchange.setProperty(GetOrdersRouteConstants.ROUTE_PROPERTY_COMMAND, request.getCommand());
		exchange.setProperty(GetOrdersRouteConstants.ROUTE_PROPERTY_EXTERNAL_SYSTEM_CONFIG_ID, request.getExternalSystemConfigId());

		exchange.getIn().setBody(ordersToImport.isEmpty() ? null : ordersToImport);
	}
}
