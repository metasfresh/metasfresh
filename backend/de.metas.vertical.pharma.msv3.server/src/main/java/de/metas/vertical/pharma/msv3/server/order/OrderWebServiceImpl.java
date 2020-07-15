package de.metas.vertical.pharma.msv3.server.order;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderServerJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
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

class OrderWebServiceImpl
{
	private static final Logger logger = LoggerFactory.getLogger(OrderWebServiceImpl.class);

	private final MSV3ServerAuthenticationService authService;
	private final OrderService orderService;
	private final OrderServerJAXBConverters jaxbConverters;

	@Builder
	private OrderWebServiceImpl(
			@NonNull final MSV3ServerAuthenticationService authService,
			@NonNull final OrderService orderService,
			@NonNull final OrderServerJAXBConverters jaxbConverters)
	{
		this.authService = authService;
		this.orderService = orderService;
		this.jaxbConverters = jaxbConverters;
	}

	public JAXBElement<?> createOrder(@RequestPayload final JAXBElement<?> jaxbRequest)
	{
		logXML("createOrder - request", jaxbRequest);

		final Object soapRequest = jaxbRequest.getValue();

		final ClientSoftwareId clientSoftwareId = jaxbConverters.getClientSoftwareIdFromClientRequest(soapRequest);
		authService.assertValidClientSoftwareId(clientSoftwareId);

		final BPartnerId bpartnerId = authService.getCurrentBPartner();
		final OrderCreateRequest request = jaxbConverters.decodeRequestFromClient(soapRequest, bpartnerId);
		final OrderCreateResponse response = orderService.createOrder(request);

		final JAXBElement<?> jaxbResponse = jaxbConverters.encodeResponseToClient(response);
		logXML("createOrder - response", jaxbResponse);
		return jaxbResponse;
	}

	private static void logXML(final String name, final JAXBElement<?> element)
	{
		if (!logger.isDebugEnabled())
		{
			return;
		}

		logger.debug("{}: {}", name, JAXBUtils.toXml(element));
	}

}
