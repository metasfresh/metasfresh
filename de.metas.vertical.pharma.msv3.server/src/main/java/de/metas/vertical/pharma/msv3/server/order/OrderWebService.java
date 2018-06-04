package de.metas.vertical.pharma.msv3.server.order;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;
import de.metas.vertical.pharma.msv3.server.security.MSV3ServerAuthenticationService;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.BestellenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.ObjectFactory;
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

@Endpoint
public class OrderWebService
{
	public static final String WSDL_BEAN_NAME = "Msv3BestellenService";

	private final MSV3ServerAuthenticationService authService;
	private final OrderJAXBConverters jaxbConverters;
	private final OrderService orderService;

	public OrderWebService(
			@NonNull final MSV3ServerAuthenticationService authService,
			@NonNull final ObjectFactory jaxbObjectFactory,
			@NonNull final OrderService orderService)
	{
		this.authService = authService;
		jaxbConverters = new OrderJAXBConverters(jaxbObjectFactory);
		this.orderService = orderService;
	}

	@PayloadRoot(localPart = "bestellen", namespace = MSV3ServerConstants.SOAP_NAMESPACE)
	public @ResponsePayload JAXBElement<BestellenResponse> createOrder(@RequestPayload final JAXBElement<Bestellen> jaxbRequest)
	{
		final Bestellen soapRequest = jaxbRequest.getValue();
		authService.assertValidClientSoftwareId(soapRequest.getClientSoftwareKennung());

		final BPartnerId bpartner = authService.getCurrentBPartner();
		final OrderCreateRequest request = jaxbConverters.fromJAXB(soapRequest.getBestellung(), bpartner);
		final OrderCreateResponse response = orderService.createOrder(request);

		return jaxbConverters.toJAXB(response);
	}

}
