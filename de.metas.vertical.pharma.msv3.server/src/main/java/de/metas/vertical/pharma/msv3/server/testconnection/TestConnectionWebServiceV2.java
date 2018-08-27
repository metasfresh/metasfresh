package de.metas.vertical.pharma.msv3.server.testconnection;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;
import de.metas.vertical.pharma.msv3.server.security.MSV3ServerAuthenticationService;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerbindungTesten;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerbindungTestenResponse;
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
public class TestConnectionWebServiceV2
{
	public static final String WSDL_BEAN_NAME = "Msv3VerbindungTestenService";

	private static final Logger logger = LoggerFactory.getLogger(TestConnectionWebServiceV2.class);

	private MSV3ServerAuthenticationService authService;
	private final ObjectFactory jaxbObjectFactory;

	public TestConnectionWebServiceV2(
			@NonNull final MSV3ServerAuthenticationService authService)
	{
		this.authService = authService;
		jaxbObjectFactory = new ObjectFactory();
	}

	@PayloadRoot(localPart = "verbindungTesten", namespace = MSV3ServerConstants.SOAP_NAMESPACE_V2)
	public @ResponsePayload JAXBElement<VerbindungTestenResponse> testConnection(@RequestPayload final JAXBElement<VerbindungTesten> jaxbRequest)
	{
		final ClientSoftwareId clientSoftwareId = ClientSoftwareId.of(jaxbRequest.getValue().getClientSoftwareKennung());
		logger.debug("testConnection: {}", clientSoftwareId);

		authService.assertValidClientSoftwareId(clientSoftwareId);

		final VerbindungTestenResponse response = jaxbObjectFactory.createVerbindungTestenResponse();
		final JAXBElement<VerbindungTestenResponse> jaxbResponse = jaxbObjectFactory.createVerbindungTestenResponse(response);
		return jaxbResponse;
	}
}
