package de.metas.vertical.pharma.msv3.server;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.server.stockAvailability.RequirementType;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityResponseItemPart;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityService;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilitySubstitution;
import de.metas.vertical.pharma.msv3.server.types.PZN;
import de.metas.vertical.pharma.msv3.server.types.Quantity;
import de.metas.vertical.pharma.msv3.server.util.JAXBDateUtils;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitSubstitution;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsantwortArtikel;

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
public class StockAvailabilityWebService
{
	public static final String WSDL_BEAN_NAME = "Msv3VerfuegbarkeitAnfragenService";
	
	@Autowired
	private StockAvailabilityService stockAvailabilityService;
	@Autowired
	private ObjectFactory jaxbObjectFactory;

	@PayloadRoot(localPart = "verfuegbarkeitAnfragen", namespace = "urn:msv3:v2")
	public @ResponsePayload JAXBElement<VerfuegbarkeitAnfragenResponse> verfuegbarkeitAnfragen(@RequestPayload final JAXBElement<VerfuegbarkeitAnfragen> jaxbRequest)
	{
		final VerfuegbarkeitAnfragen soapRequest = jaxbRequest.getValue();
		assertValidClientSoftwareId(soapRequest.getClientSoftwareKennung());

		final StockAvailabilityQuery stockAvailabilityQuery = createStockAvailabilityQuery(soapRequest.getVerfuegbarkeitsanfrage());
		final StockAvailabilityResponse stockAvailabilityResponse = stockAvailabilityService.checkAvailability(stockAvailabilityQuery);

		return createSOAPStockAvailabilityResponse(stockAvailabilityResponse);
	}

	private void assertValidClientSoftwareId(final String clientSoftwareId)
	{
		// TODO
	}

	private StockAvailabilityQuery createStockAvailabilityQuery(final VerfuegbarkeitsanfrageEinzelne soapAvailabilityRequest)
	{
		return StockAvailabilityQuery.builder()
				.id(soapAvailabilityRequest.getId())
				.items(soapAvailabilityRequest.getArtikel().stream()
						.map(this::createStockAvailabilityQueryItem)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private StockAvailabilityQueryItem createStockAvailabilityQueryItem(final VerfuegbarkeitsanfrageEinzelne.Artikel artikel)
	{
		return StockAvailabilityQueryItem.builder()
				.pzn(PZN.of(artikel.getPzn()))
				.qtyRequired(Quantity.of(artikel.getMenge()))
				.requirementType(RequirementType.fromCode(artikel.getBedarf()))
				.build();
	}

	private JAXBElement<VerfuegbarkeitAnfragenResponse> createSOAPStockAvailabilityResponse(final StockAvailabilityResponse stockAvailabilityResponse)
	{
		final VerfuegbarkeitsanfrageEinzelneAntwort soapResponseContent = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelneAntwort();
		soapResponseContent.setId(stockAvailabilityResponse.getId());
		soapResponseContent.setRTyp(stockAvailabilityResponse.getAvailabilityType().getSoapCode());
		soapResponseContent.getArtikel().addAll(
				stockAvailabilityResponse.getItems().stream()
						.map(this::createSOAPStockAvailabilityResponseItem)
						.collect(ImmutableList.toImmutableList()));

		final VerfuegbarkeitAnfragenResponse response = jaxbObjectFactory.createVerfuegbarkeitAnfragenResponse();
		response.setReturn(soapResponseContent);
		return jaxbObjectFactory.createVerfuegbarkeitAnfragenResponse(response);
	}

	private VerfuegbarkeitsantwortArtikel createSOAPStockAvailabilityResponseItem(final StockAvailabilityResponseItem item)
	{
		final VerfuegbarkeitsantwortArtikel soapItem = jaxbObjectFactory.createVerfuegbarkeitsantwortArtikel();
		soapItem.setAnfragePzn(item.getPzn().getValueAsLong());
		soapItem.setAnfrageMenge(item.getQty().getValueAsInt());
		soapItem.setSubstitution(createSOAPAvailabilitySubstitution(item.getSubstitution()));
		soapItem.getAnteile().addAll(item.getParts().stream()
				.map(this::creatSOAPStockAvailabilityShare)
				.collect(ImmutableList.toImmutableList()));
		return soapItem;
	}

	private VerfuegbarkeitSubstitution createSOAPAvailabilitySubstitution(final StockAvailabilitySubstitution substitution)
	{
		if (substitution == null)
		{
			return null;
		}

		final VerfuegbarkeitSubstitution soapSubstitution = jaxbObjectFactory.createVerfuegbarkeitSubstitution();
		soapSubstitution.setLieferPzn(substitution.getPzn().getValueAsLong());
		soapSubstitution.setGrund(substitution.getReason().getSoapCode());
		soapSubstitution.setSubstitutionsgrund(substitution.getType().getSoapCode());
		return soapSubstitution;
	}

	private VerfuegbarkeitAnteil creatSOAPStockAvailabilityShare(final StockAvailabilityResponseItemPart share)
	{
		final VerfuegbarkeitAnteil soapShare = jaxbObjectFactory.createVerfuegbarkeitAnteil();
		soapShare.setMenge(share.getQty().getValueAsInt());
		soapShare.setTyp(share.getType().getSoapCode());
		soapShare.setLieferzeitpunkt(share.getDeliveryDate() != null ? JAXBDateUtils.toXMLGregorianCalendar(share.getDeliveryDate()) : null);
		soapShare.setTour(share.getTour());
		soapShare.setGrund(share.getReason().getSoapCode());
		soapShare.setTourabweichung(share.isTourDeviation());
		return soapShare;
	}
}
