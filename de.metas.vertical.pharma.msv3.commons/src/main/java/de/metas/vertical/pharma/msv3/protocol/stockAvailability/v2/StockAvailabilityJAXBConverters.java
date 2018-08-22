package de.metas.vertical.pharma.msv3.protocol.stockAvailability.v2;

import javax.xml.bind.JAXBElement;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.stockAvailability.RequirementType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPart;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitution;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.protocol.util.JAXBDateUtils;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitAnfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitSubstitution;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitsantwortArtikel;
import lombok.NonNull;

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
public class StockAvailabilityJAXBConverters
{
	private final ObjectFactory jaxbObjectFactory;

	public StockAvailabilityJAXBConverters(@NonNull final ObjectFactory jaxbObjectFactory)
	{
		this.jaxbObjectFactory = jaxbObjectFactory;
	}

	public StockAvailabilityQuery fromJAXB(@NonNull final VerfuegbarkeitsanfrageEinzelne soapAvailabilityRequest, @NonNull final BPartnerId bpartner)
	{
		return StockAvailabilityQuery.builder()
				.id(soapAvailabilityRequest.getId())
				.bpartner(bpartner)
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

	public JAXBElement<VerfuegbarkeitAnfragenResponse> toJAXB(final StockAvailabilityResponse stockAvailabilityResponse)
	{
		final VerfuegbarkeitsanfrageEinzelneAntwort soapResponseContent = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelneAntwort();
		soapResponseContent.setId(stockAvailabilityResponse.getId());
		soapResponseContent.setRTyp(stockAvailabilityResponse.getAvailabilityType().getV2SoapCode());
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
		soapSubstitution.setGrund(substitution.getReason().getV2SoapCode());
		soapSubstitution.setSubstitutionsgrund(substitution.getType().getV2SoapCode());
		return soapSubstitution;
	}

	private VerfuegbarkeitAnteil creatSOAPStockAvailabilityShare(final StockAvailabilityResponseItemPart share)
	{
		final VerfuegbarkeitAnteil soapShare = jaxbObjectFactory.createVerfuegbarkeitAnteil();
		soapShare.setMenge(share.getQty().getValueAsInt());
		soapShare.setTyp(share.getType().getV2SoapCode());
		soapShare.setLieferzeitpunkt(share.getDeliveryDate() != null ? JAXBDateUtils.toXMLGregorianCalendar(share.getDeliveryDate()) : null);
		soapShare.setTour(share.getTour());
		soapShare.setGrund(share.getReason() != null ? share.getReason().getV2SoapCode() : null);
		soapShare.setTourabweichung(share.isTourDeviation());
		return soapShare;
	}
}
