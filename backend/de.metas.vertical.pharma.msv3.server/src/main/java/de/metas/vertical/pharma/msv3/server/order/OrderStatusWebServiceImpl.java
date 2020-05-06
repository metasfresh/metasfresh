package de.metas.vertical.pharma.msv3.server.order;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusServerJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.server.security.MSV3ServerAuthenticationService;
import de.metas.vertical.pharma.msv3.server.util.JAXBUtils;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

class OrderStatusWebServiceImpl
{
	private static final Logger logger = LoggerFactory.getLogger(OrderStatusWebServiceImpl.class);

	private final MSV3ServerAuthenticationService authService;
	private final OrderStatusServerJAXBConverters jaxbConverters;
	private final OrderService orderService;

	@Builder
	private OrderStatusWebServiceImpl(
			@NonNull final MSV3ServerAuthenticationService authService,
			@NonNull final OrderService orderService,
			@NonNull final OrderStatusServerJAXBConverters jaxbConverters)
	{
		this.authService = authService;
		this.orderService = orderService;
		this.jaxbConverters = jaxbConverters;
	}

	public JAXBElement<?> getOrderStatus(final JAXBElement<?> jaxbRequest)
	{
		logXML("getOrderStatus - request", jaxbRequest);

		final Object soapRequest = jaxbRequest.getValue();
		authService.assertValidClientSoftwareId(jaxbConverters.getClientSoftwareIdFromClientRequest(soapRequest));
		final BPartnerId bpartner = authService.getCurrentBPartner();

		final Id orderId = jaxbConverters.getOrderIdFromClientRequest(soapRequest);
		final OrderStatusResponse response = orderService.getOrderStatus(orderId, bpartner);

		final JAXBElement<?> jaxbResponse = jaxbConverters.encodeResponseToClient(response);
		logXML("getOrderStatus - response", jaxbResponse);
		return jaxbResponse;
	}

	private void logXML(final String name, final JAXBElement<?> element)
	{
		if (!logger.isDebugEnabled())
		{
			return;
		}

		logger.debug("{}: {}", name, JAXBUtils.toXml(element));
	}
}
