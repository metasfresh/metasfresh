package de.metas.vertical.pharma.msv3.server;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne.Artikel;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;

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
public class Msv3VerfuegbarkeitAnfragenService
{
	@Autowired
	private ObjectFactory jaxbObjectFactory;

	@PayloadRoot(localPart = "verfuegbarkeitAnfragen", namespace = "urn:msv3:v2")
	public @ResponsePayload JAXBElement<VerfuegbarkeitAnfragenResponse> verfuegbarkeitAnfragen(@RequestPayload JAXBElement<VerfuegbarkeitAnfragen> jaxbRequest)
	{
		final VerfuegbarkeitAnfragen request = jaxbRequest.getValue();
		final String clientSoftwareKennung = request.getClientSoftwareKennung();
		final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrage = request.getVerfuegbarkeitsanfrage();
		final List<Artikel> artikel = verfuegbarkeitsanfrage.getArtikel();

		// TODO: implement it

		VerfuegbarkeitsanfrageEinzelneAntwort responseContent = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelneAntwort();
		responseContent.setId("1234");

		final VerfuegbarkeitAnfragenResponse response = jaxbObjectFactory.createVerfuegbarkeitAnfragenResponse();
		response.setReturn(responseContent);
		return jaxbObjectFactory.createVerfuegbarkeitAnfragenResponse(response);
	}
}
