package de.metas.vertical.pharma.msv3.server.stockAvailability;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import de.metas.vertical.pharma.msv3.protocol.stockAvailability.v2.StockAvailabilityJAXBConvertersV2;
import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;
import de.metas.vertical.pharma.msv3.server.security.MSV3ServerAuthenticationService;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitAnfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitAnfragenResponse;
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
public class StockAvailabilityWebServiceV2
{
	public static final String WSDL_BEAN_NAME = "Msv3VerfuegbarkeitAnfragenService";

	private final StockAvailabilityWebServiceImpl impl;

	public StockAvailabilityWebServiceV2(
			@NonNull final MSV3ServerAuthenticationService authService,
			@NonNull final StockAvailabilityService stockAvailabilityService)
	{
		this.impl = StockAvailabilityWebServiceImpl.builder()
				.authService(authService)
				.stockAvailabilityService(stockAvailabilityService)
				.jaxbConverters(StockAvailabilityJAXBConvertersV2.instance)
				.build();
	}

	@PayloadRoot(localPart = "verfuegbarkeitAnfragen", namespace = MSV3ServerConstants.SOAP_NAMESPACE_V2)
	public @ResponsePayload JAXBElement<VerfuegbarkeitAnfragenResponse> getStockAvailability(@RequestPayload final JAXBElement<VerfuegbarkeitAnfragen> jaxbRequest)
	{
		@SuppressWarnings("unchecked")
		final JAXBElement<VerfuegbarkeitAnfragenResponse> jaxbResponse = (JAXBElement<VerfuegbarkeitAnfragenResponse>)impl.getStockAvailability(jaxbRequest);
		return jaxbResponse;
	}
}
