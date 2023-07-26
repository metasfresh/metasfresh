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

import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.PharmacyApi;
import io.swagger.client.model.ArrayOfOrders;
import io.swagger.client.model.Order;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExternalReferenceLookupProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final List<Order> orders = exchange.getIn().getBody(ArrayOfOrders.class);

		if (CollectionUtils.isEmpty(orders))
		{
			return; //nothing to do
		}

		final DoctorApi doctorApi = ProcessorHelper.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_DOCTOR_API, DoctorApi.class);
		final PharmacyApi pharmacyApi = ProcessorHelper.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PHARMACY_API, PharmacyApi.class);

		final JsonExternalReferenceLookupRequest externalReferenceLookupRequest =
				buildESRLookupRequest(orders, exchange, pharmacyApi, doctorApi)
						.orElse(null);

		exchange.getIn().setBody(externalReferenceLookupRequest);
	}

	@NonNull
	private Optional<JsonExternalReferenceLookupRequest> buildESRLookupRequest(
			@NonNull final List<Order> orders,
			@NonNull final Exchange exchange,
			@NonNull final PharmacyApi pharmacyApi,
			@NonNull final DoctorApi doctorApi)
	{
		final Map<String, Object> externalBPId2Api = new HashMap<>();

		orders.forEach(order -> {
			if (!StringUtils.isEmpty(order.getDoctorId()))
			{
				externalBPId2Api.put(order.getDoctorId(), doctorApi);
			}

			if (!StringUtils.isEmpty(order.getPharmacyId()))
			{
				externalBPId2Api.put(order.getPharmacyId(), pharmacyApi);
			}
		});

		if (externalBPId2Api.isEmpty())
		{
			return Optional.empty();
		}
		exchange.setProperty(GetPatientsRouteConstants.ROUTE_PROPERTY_EXTERNAL_BP_IDENTIFIER_TO_API, externalBPId2Api);

		final JsonExternalReferenceLookupRequest.JsonExternalReferenceLookupRequestBuilder jsonExternalReferenceLookupRequest =
				JsonExternalReferenceLookupRequest.builder()
						.systemName(JsonExternalSystemName.of(GetPatientsRouteConstants.ALBERTA_SYSTEM_NAME));

		externalBPId2Api.keySet().stream()
				.map(this::getBPartnerLookupItem)
				.forEach(jsonExternalReferenceLookupRequest::item);

		return Optional.of(jsonExternalReferenceLookupRequest.build());
	}

	@NonNull
	private JsonExternalReferenceLookupItem getBPartnerLookupItem(@NonNull final String externalId)
	{
		return JsonExternalReferenceLookupItem.builder()
				.type(GetPatientsRouteConstants.ESR_TYPE_BPARTNER)
				.id(externalId)
				.build();
	}
}
