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
import de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.alberta.ordercandidate.GetOrdersRouteConstants;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.camel.externalsystems.common.v2.BPLocationCamelRequest;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.client.model.Order;
import io.swagger.client.model.OrderDeliveryAddress;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class DeliveryAddressUpsertProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final Order order = exchange.getIn().getBody(Order.class);

		if (order == null)
		{
			throw new RuntimeException("Empty body!");
		}

		if (order.getDeliveryAddress() == null)
		{
			throw new RuntimeException("Missing delivery address! OrderId: " + order.getId());
		}

		final String orgCode = ProcessorHelper.getPropertyOrThrowError(exchange, GetOrdersRouteConstants.ROUTE_PROPERTY_ORG_CODE, String.class);

		final JsonRequestLocationUpsert locationUpsert = getDeliveryAddressUpsertRequest(order.getDeliveryAddress(), order.getPatientId());

		final BPLocationCamelRequest camelRequest = BPLocationCamelRequest.builder()
				.jsonRequestLocationUpsert(locationUpsert)
				.bPartnerIdentifier(ExternalIdentifierFormat.formatExternalId(order.getPatientId()))
				.orgCode(orgCode)
				.build();

		exchange.getIn().setBody(camelRequest);
		exchange.setProperty(GetOrdersRouteConstants.ROUTE_PROPERTY_CURRENT_ORDER, order);
	}

	@NonNull
	private JsonRequestLocationUpsert getDeliveryAddressUpsertRequest(
			@NonNull final OrderDeliveryAddress orderDeliveryAddress,
			@NonNull final String patientId)
	{
		final String bPartnerLocationIdentifier = ExternalIdentifierFormat.formatDeliveryAddressExternalId(patientId);

		final JsonRequestLocation deliveryAddressRequest = new JsonRequestLocation();
		deliveryAddressRequest.setBpartnerName(orderDeliveryAddress.getName());
		deliveryAddressRequest.setAddress1(orderDeliveryAddress.getAddress());
		deliveryAddressRequest.setAddress2(orderDeliveryAddress.getAdditionalAddress());
		deliveryAddressRequest.setAddress3(orderDeliveryAddress.getAdditionalAddress2());
		deliveryAddressRequest.setPostal(orderDeliveryAddress.getPostalCode());
		deliveryAddressRequest.setCity(orderDeliveryAddress.getCity());
		deliveryAddressRequest.setCountryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE);
		deliveryAddressRequest.setShipTo(true);

		return JsonRequestLocationUpsert.builder()
				.requestItem(JsonRequestLocationUpsertItem.builder()
									 .locationIdentifier(bPartnerLocationIdentifier)
									 .location(deliveryAddressRequest)
									 .build())
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();
	}
}
