package de.metas.vertical.pharma.msv3.protocol.stockAvailability.v2;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.stockAvailability.AvailabilityType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.RequirementType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityClientJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPart;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPartType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityServerJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitution;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitutionReason;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitutionType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.protocol.util.JAXBDateUtils;
import de.metas.vertical.pharma.msv3.protocol.util.v2.MiscJAXBConvertersV2;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitAnfragen;
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
public class StockAvailabilityJAXBConvertersV2 implements StockAvailabilityClientJAXBConverters, StockAvailabilityServerJAXBConverters
{
	public static final transient StockAvailabilityJAXBConvertersV2 instance = new StockAvailabilityJAXBConvertersV2();

	private final ObjectFactory jaxbObjectFactory;

	private StockAvailabilityJAXBConvertersV2()
	{
		this(new ObjectFactory());
	}

	public StockAvailabilityJAXBConvertersV2(@NonNull final ObjectFactory jaxbObjectFactory)
	{
		this.jaxbObjectFactory = jaxbObjectFactory;
	}

	@Override
	public FaultInfo extractFaultInfoOrNull(final Object value)
	{
		return MiscJAXBConvertersV2.extractFaultInfoOrNull(value);
	}

	@Override
	public JAXBElement<?> encodeRequest(final StockAvailabilityQuery query, final ClientSoftwareId clientSoftwareId)
	{
		return toJAXBElement(query, clientSoftwareId);
	}

	@Override
	public Class<?> getResponseClass()
	{
		return VerfuegbarkeitAnfragenResponse.class;
	}

	@Override
	public StockAvailabilityResponse decodeResponse(final Object soap)
	{
		final VerfuegbarkeitAnfragenResponse soapResponse = (VerfuegbarkeitAnfragenResponse)soap;
		return fromJAXB(soapResponse);
	}

	@Override
	public ClientSoftwareId getClientSoftwareIdFromClientRequest(final Object soapRequestObj)
	{
		final VerfuegbarkeitAnfragen soapRequest = castToRequestFromClient(soapRequestObj);
		return ClientSoftwareId.of(soapRequest.getClientSoftwareKennung());
	}

	@Override
	public StockAvailabilityQuery decodeRequestFromClient(final Object soapRequestObj, final BPartnerId bpartnerId)
	{
		final VerfuegbarkeitAnfragen soapRequest = castToRequestFromClient(soapRequestObj);
		return fromJAXB(soapRequest.getVerfuegbarkeitsanfrage(), bpartnerId);
	}

	private static VerfuegbarkeitAnfragen castToRequestFromClient(final Object soapRequestObj)
	{
		final VerfuegbarkeitAnfragen soapRequest = (VerfuegbarkeitAnfragen)soapRequestObj;
		return soapRequest;
	}

	@Override
	public JAXBElement<VerfuegbarkeitAnfragenResponse> encodeResponseToClient(final StockAvailabilityResponse response)
	{
		return toJAXBElement(response);
	}

	public StockAvailabilityQuery fromJAXB(@NonNull final VerfuegbarkeitsanfrageEinzelne soapAvailabilityRequest, @NonNull final BPartnerId bpartnerId)
	{
		return StockAvailabilityQuery.builder()
				.id(soapAvailabilityRequest.getId())
				.bpartner(bpartnerId)
				.items(soapAvailabilityRequest.getArtikel().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public JAXBElement<VerfuegbarkeitAnfragen> toJAXBElement(final StockAvailabilityQuery query, final ClientSoftwareId clientSoftwareId)
	{
		final VerfuegbarkeitAnfragen soap = jaxbObjectFactory.createVerfuegbarkeitAnfragen();
		soap.setVerfuegbarkeitsanfrage(toJAXB(query));
		soap.setClientSoftwareKennung(clientSoftwareId.getValueAsString());

		return jaxbObjectFactory.createVerfuegbarkeitAnfragen(soap);
	}

	public VerfuegbarkeitsanfrageEinzelne toJAXB(final StockAvailabilityQuery query)
	{
		final VerfuegbarkeitsanfrageEinzelne soap = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelne();
		soap.setId(query.getId());
		soap.getArtikel().addAll(query.getItems().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));
		return soap;
	}

	private StockAvailabilityQueryItem fromJAXB(final VerfuegbarkeitsanfrageEinzelne.Artikel artikel)
	{
		return StockAvailabilityQueryItem.builder()
				.pzn(PZN.of(artikel.getPzn()))
				.qtyRequired(Quantity.of(artikel.getMenge()))
				.requirementType(RequirementType.fromCode(artikel.getBedarf()))
				.build();
	}

	private VerfuegbarkeitsanfrageEinzelne.Artikel toJAXB(final StockAvailabilityQueryItem queryItem)
	{
		final VerfuegbarkeitsanfrageEinzelne.Artikel soap = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelneArtikel();
		soap.setPzn(queryItem.getPzn().getValueAsLong());
		soap.setMenge(queryItem.getQtyRequired().getValueAsInt());
		soap.setBedarf(queryItem.getRequirementType().getCode());
		return soap;
	}

	public JAXBElement<VerfuegbarkeitAnfragenResponse> toJAXBElement(final StockAvailabilityResponse stockAvailabilityResponse)
	{
		final VerfuegbarkeitAnfragenResponse response = toJAXB(stockAvailabilityResponse);
		return jaxbObjectFactory.createVerfuegbarkeitAnfragenResponse(response);
	}

	private VerfuegbarkeitAnfragenResponse toJAXB(final StockAvailabilityResponse stockAvailabilityResponse)
	{
		final VerfuegbarkeitsanfrageEinzelneAntwort soapContent = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelneAntwort();
		soapContent.setId(stockAvailabilityResponse.getId());
		soapContent.setRTyp(stockAvailabilityResponse.getAvailabilityType().getV2SoapCode());
		soapContent.getArtikel().addAll(stockAvailabilityResponse.getItems().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));

		final VerfuegbarkeitAnfragenResponse soap = jaxbObjectFactory.createVerfuegbarkeitAnfragenResponse();
		soap.setReturn(soapContent);
		return soap;
	}

	public StockAvailabilityResponse fromJAXB(final VerfuegbarkeitAnfragenResponse soap)
	{
		return fromJAXB(soap.getReturn());
	}

	private StockAvailabilityResponse fromJAXB(final VerfuegbarkeitsanfrageEinzelneAntwort soap)
	{
		return StockAvailabilityResponse.builder()
				.id(soap.getId())
				.availabilityType(AvailabilityType.fromV2SoapCode(soap.getRTyp()))
				.items(soap.getArtikel().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private VerfuegbarkeitsantwortArtikel toJAXB(final StockAvailabilityResponseItem item)
	{
		final VerfuegbarkeitsantwortArtikel soapItem = jaxbObjectFactory.createVerfuegbarkeitsantwortArtikel();
		soapItem.setAnfragePzn(item.getPzn().getValueAsLong());
		soapItem.setAnfrageMenge(item.getQty().getValueAsInt());
		soapItem.setSubstitution(toJAXB(item.getSubstitution()));
		soapItem.getAnteile().addAll(item.getParts().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));
		return soapItem;
	}

	private StockAvailabilityResponseItem fromJAXB(final VerfuegbarkeitsantwortArtikel soap)
	{
		return StockAvailabilityResponseItem.builder()
				.pzn(PZN.of(soap.getAnfragePzn()))
				.qty(Quantity.of(soap.getAnfrageMenge()))
				.substitution(fromJAXBorNull(soap.getSubstitution()))
				.parts(soap.getAnteile().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private VerfuegbarkeitSubstitution toJAXB(final StockAvailabilitySubstitution substitution)
	{
		if (substitution == null)
		{
			return null;
		}

		final VerfuegbarkeitSubstitution soap = jaxbObjectFactory.createVerfuegbarkeitSubstitution();
		soap.setLieferPzn(substitution.getPzn().getValueAsLong());
		soap.setGrund(substitution.getReason().getV2SoapCode());
		soap.setSubstitutionsgrund(substitution.getType().getV2SoapCode());
		return soap;
	}

	private StockAvailabilitySubstitution fromJAXBorNull(@Nullable final VerfuegbarkeitSubstitution soap)
	{
		if (soap == null)
		{
			return null;
		}
		return StockAvailabilitySubstitution.builder()
				.pzn(PZN.of(soap.getLieferPzn()))
				.reason(StockAvailabilitySubstitutionReason.fromV2SoapCode(soap.getGrund()))
				.type(StockAvailabilitySubstitutionType.fromV2SoapCode(soap.getSubstitutionsgrund()))
				.build();
	}

	private VerfuegbarkeitAnteil toJAXB(final StockAvailabilityResponseItemPart itemPart)
	{
		final VerfuegbarkeitAnteil soap = jaxbObjectFactory.createVerfuegbarkeitAnteil();
		soap.setMenge(itemPart.getQty().getValueAsInt());
		soap.setTyp(itemPart.getType().getV2SoapCode());
		soap.setLieferzeitpunkt(itemPart.getDeliveryDate() != null ? JAXBDateUtils.toXMLGregorianCalendar(itemPart.getDeliveryDate()) : null);
		soap.setTour(itemPart.getTour());
		soap.setGrund(itemPart.getReason() != null ? itemPart.getReason().getV2SoapCode() : null);
		soap.setTourabweichung(itemPart.isTourDeviation());
		return soap;
	}

	private StockAvailabilityResponseItemPart fromJAXB(@NonNull final VerfuegbarkeitAnteil soap)
	{
		return StockAvailabilityResponseItemPart.builder()
				.qty(Quantity.of(soap.getMenge()))
				.type(StockAvailabilityResponseItemPartType.fromV2SoapCode(soap.getTyp()))
				.deliveryDate(JAXBDateUtils.toZonedDateTime(soap.getLieferzeitpunkt()))
				.reason(StockAvailabilitySubstitutionReason.fromV2SoapCode(soap.getGrund()))
				.tour(soap.getTour())
				.tourDeviation(soap.isTourabweichung())
				.build();
	}
}
