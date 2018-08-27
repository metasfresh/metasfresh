package de.metas.vertical.pharma.msv3.protocol.order.v1;

import javax.xml.bind.JAXBElement;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusServerJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellstatusAbfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellstatusAbfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellstatusAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.ObjectFactory;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class OrderStatusJAXBConvertersV1 implements OrderStatusServerJAXBConverters
{
	public static final transient OrderStatusJAXBConvertersV1 instance = new OrderStatusJAXBConvertersV1();

	private final OrderJAXBConvertersV1 orderConverters;
	private final ObjectFactory jaxbObjectFactory;

	private OrderStatusJAXBConvertersV1()
	{
		this.orderConverters = OrderJAXBConvertersV1.instance;
		jaxbObjectFactory = orderConverters.getJaxbObjectFactory();
	}

	@Override
	public Id getOrderIdFromClientRequest(final Object soapRequestObj)
	{
		final BestellstatusAbfragen soapRequest = castToRequestFromClient(soapRequestObj);
		final Id orderId = Id.of(soapRequest.getBestellId());
		return orderId;
	}

	@Override
	public ClientSoftwareId getClientSoftwareIdFromClientRequest(final Object soapRequestObj)
	{
		final BestellstatusAbfragen soapRequest = castToRequestFromClient(soapRequestObj);
		return ClientSoftwareId.of(soapRequest.getClientSoftwareKennung());
	}

	private static BestellstatusAbfragen castToRequestFromClient(final Object soapRequestObj)
	{
		final BestellstatusAbfragen soapRequest = (BestellstatusAbfragen)soapRequestObj;
		return soapRequest;
	}

	@Override
	public JAXBElement<BestellstatusAbfragenResponse> encodeResponseToClient(final OrderStatusResponse response)
	{
		return toJAXB(response);
	}

	private JAXBElement<BestellstatusAbfragenResponse> toJAXB(final OrderStatusResponse response)
	{
		final BestellstatusAntwort soapResponseContent = jaxbObjectFactory.createBestellstatusAntwort();
		soapResponseContent.setId(response.getOrderId().getValueAsString());
		soapResponseContent.setBestellSupportId(response.getSupportId().getValueAsInt());
		soapResponseContent.setStatus(response.getOrderStatus().getV1SoapCode());
		soapResponseContent.getAuftraege().addAll(response.getOrderPackages().stream()
				.map(orderConverters::toJAXB)
				.collect(ImmutableList.toImmutableList()));

		final BestellstatusAbfragenResponse soapResponse = jaxbObjectFactory.createBestellstatusAbfragenResponse();
		soapResponse.setReturn(soapResponseContent);
		return jaxbObjectFactory.createBestellstatusAbfragenResponse(soapResponse);
	}

}
