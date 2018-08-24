package de.metas.vertical.pharma.msv3.protocol.order.v2;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.order.DeliverySpecifications;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderDefectReason;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse.OrderResponseBuilder;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage.OrderResponsePackageBuilder;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPart;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemSubstitution;
import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderSubstitutionReason;
import de.metas.vertical.pharma.msv3.protocol.order.OrderType;
import de.metas.vertical.pharma.msv3.protocol.order.SupportIDType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.protocol.util.JAXBDateUtils;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellstatusAbfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellstatusAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungPosition;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungRueckmeldungTyp;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungSubstitution;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Msv3FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory;
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

public class OrderJAXBConverters
{
	private final ObjectFactory jaxbObjectFactory;

	public OrderJAXBConverters()
	{
		this(new ObjectFactory());
	}

	public OrderJAXBConverters(@NonNull final ObjectFactory jaxbObjectFactory)
	{
		this.jaxbObjectFactory = jaxbObjectFactory;
	}

	public JAXBElement<Bestellen> toJAXBElement(final OrderCreateRequest request, final ClientSoftwareId clientSoftwareId)
	{
		final Bestellen bestellen = jaxbObjectFactory.createBestellen();
		bestellen.setClientSoftwareKennung(clientSoftwareId.getValueAsString());
		bestellen.setBestellung(toJAXB(request));

		return jaxbObjectFactory.createBestellen(bestellen);
	}

	public Bestellung toJAXB(final OrderCreateRequest request)
	{
		final Bestellung soap = jaxbObjectFactory.createBestellung();
		soap.setId(request.getOrderId().getValueAsString());
		soap.setBestellSupportId(request.getSupportId().getValueAsInt());
		soap.getAuftraege().addAll(request.getOrderPackages().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));
		return soap;
	}

	public OrderCreateRequest fromJAXB(final Bestellung soapOrder, final BPartnerId bpartnerId)
	{
		return OrderCreateRequest.builder()
				.orderId(Id.of(soapOrder.getId()))
				.supportId(SupportIDType.of(soapOrder.getBestellSupportId()))
				.bpartnerId(bpartnerId)
				.orderPackages(soapOrder.getAuftraege().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private BestellungAuftrag toJAXB(final OrderCreateRequestPackage requestPackage)
	{
		final BestellungAuftrag soap = jaxbObjectFactory.createBestellungAuftrag();
		soap.setId(requestPackage.getId().getValueAsString());
		soap.setAuftragsart(requestPackage.getOrderType().getV2SoapCode());
		soap.setAuftragskennung(requestPackage.getOrderIdentification());
		soap.setAuftragsSupportID(requestPackage.getSupportId().getValueAsInt());
		soap.setGebindeId(requestPackage.getPackingMaterialId());
		soap.getPositionen().addAll(requestPackage.getItems().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));
		return soap;
	}

	private OrderCreateRequestPackage fromJAXB(final BestellungAuftrag soapOrderPackage)
	{
		return OrderCreateRequestPackage.builder()
				.id(Id.of(soapOrderPackage.getId()))
				.orderType(OrderType.fromV2SoapCode(soapOrderPackage.getAuftragsart()))
				.orderIdentification(soapOrderPackage.getAuftragskennung())
				.supportId(SupportIDType.of(soapOrderPackage.getAuftragsSupportID()))
				.packingMaterialId(soapOrderPackage.getGebindeId())
				.items(soapOrderPackage.getPositionen().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private BestellungPosition toJAXB(final OrderCreateRequestPackageItem item)
	{
		final BestellungPosition soap = jaxbObjectFactory.createBestellungPosition();
		soap.setPzn(item.getPzn().getValueAsLong());
		soap.setMenge(item.getQty().getValueAsInt());
		soap.setLiefervorgabe(item.getDeliverySpecifications().getV2SoapCode());
		return soap;
	}

	private OrderCreateRequestPackageItem fromJAXB(final BestellungPosition soapOrderPackageItem)
	{
		return OrderCreateRequestPackageItem.builder()
				.pzn(PZN.of(soapOrderPackageItem.getPzn()))
				.qty(Quantity.of(soapOrderPackageItem.getMenge()))
				.deliverySpecifications(DeliverySpecifications.fromV2SoapCode(soapOrderPackageItem.getLiefervorgabe()))
				.build();
	}

	public OrderCreateResponse fromJAXB(final BestellenResponse soap, final OrderCreateRequest request)
	{
		return OrderCreateResponse.ok(fromJAXB(soap.getReturn(), request));
	}

	private OrderResponse fromJAXB(final BestellungAntwort soap, final OrderCreateRequest request)
	{
		final OrderResponseBuilder builder = OrderResponse.builder()
				.bpartnerId(request.getBpartnerId())
				.orderId(Id.of(soap.getId()))
				.supportId(SupportIDType.of(soap.getBestellSupportId()))
				.nightOperation(soap.isNachtBetrieb());

		final List<BestellungAntwortAuftrag> responseOrders = soap.getAuftraege();
		final List<OrderCreateRequestPackage> requestOrders = request.getOrderPackages();
		if (requestOrders.size() != responseOrders.size())
		{
			throw new IllegalArgumentException("Invalid " + BestellstatusAntwort.class + ". `Auftraege` count does not match the request."
					+ "\n request: " + request);
		}

		for (int i = 0; i < requestOrders.size(); i++)
		{
			final BestellungAntwortAuftrag responseOrder = responseOrders.get(i);
			final OrderCreateRequestPackage requestOrder = requestOrders.get(i);

			builder.orderPackage(fromJAXB(responseOrder, requestOrder));
		}

		return builder.build();
	}

	public JAXBElement<BestellenResponse> toJAXBElement(final OrderCreateResponse response)
	{
		final OrderResponse order = response.getOrder();
		final BestellungAntwort soapResponseContent = jaxbObjectFactory.createBestellungAntwort();
		soapResponseContent.setId(order.getOrderId().getValueAsString());
		soapResponseContent.setBestellSupportId(order.getSupportId().getValueAsInt());
		soapResponseContent.setNachtBetrieb(order.isNightOperation());
		soapResponseContent.getAuftraege().addAll(order.getOrderPackages().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));

		final BestellenResponse soapResponse = jaxbObjectFactory.createBestellenResponse();
		soapResponse.setReturn(soapResponseContent);
		return jaxbObjectFactory.createBestellenResponse(soapResponse);
	}

	private OrderResponsePackage fromJAXB(final BestellungAntwortAuftrag soap, final OrderCreateRequestPackage requestOrder)
	{
		final OrderResponsePackageBuilder builder = OrderResponsePackage.builder()
				.id(Id.of(soap.getId()))
				.orderType(OrderType.fromV2SoapCode(soap.getAuftragsart()))
				.orderIdentification(soap.getAuftragskennung())
				.supportId(SupportIDType.of(soap.getAuftragsSupportID()))
				.packingMaterialId(soap.getGebindeId())
				.faultInfo(fromJAXB(soap.getAuftragsfehler()));

		final List<BestellungAntwortPosition> responseItems = soap.getPositionen();
		final List<OrderCreateRequestPackageItem> requestItems = requestOrder.getItems();
		if (requestItems.size() != responseItems.size())
		{
			throw new IllegalArgumentException("Invalid " + BestellungAntwortAuftrag.class + ". `Positionen` count does not match the request."
					+ "\n request: " + requestOrder);
		}

		for (int i = 0; i < requestItems.size(); i++)
		{
			final OrderCreateRequestPackageItem requestItem = requestItems.get(i);
			final BestellungAntwortPosition responseItem = responseItems.get(i);

			builder.item(fromJAXB(responseItem, requestItem));
		}

		return builder.build();
	}

	private BestellungAntwortAuftrag toJAXB(final OrderResponsePackage orderPackage)
	{
		final BestellungAntwortAuftrag soapOrderPackage = jaxbObjectFactory.createBestellungAntwortAuftrag();
		soapOrderPackage.setId(orderPackage.getId().getValueAsString());
		soapOrderPackage.setAuftragsart(orderPackage.getOrderType().getV2SoapCode());
		soapOrderPackage.setAuftragskennung(orderPackage.getOrderIdentification());
		soapOrderPackage.setAuftragsSupportID(orderPackage.getSupportId().getValueAsInt());
		soapOrderPackage.setGebindeId(orderPackage.getPackingMaterialId());
		soapOrderPackage.setAuftragsfehler(toJAXB(orderPackage.getFaultInfo()));
		soapOrderPackage.getPositionen().addAll(orderPackage.getItems().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));
		return soapOrderPackage;
	}

	private OrderResponsePackageItem fromJAXB(final BestellungAntwortPosition soap, final OrderCreateRequestPackageItem requestItem)
	{
		return OrderResponsePackageItem.builder()
				.requestId(requestItem.getId())
				.pzn(PZN.of(soap.getBestellPzn()))
				.qty(Quantity.of(soap.getBestellMenge()))
				.deliverySpecifications(DeliverySpecifications.fromV2SoapCode(soap.getBestellLiefervorgabe()))
				.parts(soap.getAnteile().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.substitution(fromJAXB(soap.getSubstitution()))
				.purchaseCandidateId(requestItem.getPurchaseCandidateId())
				.build();
	}

	private BestellungAntwortPosition toJAXB(final OrderResponsePackageItem orderPackageItem)
	{
		final int qty = orderPackageItem.getQty().getValueAsInt();

		final ImmutableList<BestellungAnteil> soapItemParts;
		if (orderPackageItem.getParts().isEmpty())
		{
			final BestellungAnteil soapItemPart = jaxbObjectFactory.createBestellungAnteil();
			soapItemPart.setMenge(qty);
			soapItemPart.setTyp(BestellungRueckmeldungTyp.NORMAL);
			soapItemPart.setGrund(BestellungDefektgrund.KEINE_ANGABE);
			soapItemPart.setLieferzeitpunkt(JAXBDateUtils.toXMLGregorianCalendar(LocalDateTime.now().plusDays(1))); // FIXME: hardcoded
			soapItemPart.setTourId("1"); // FIXME: hardcoded
			soapItemParts = ImmutableList.of(soapItemPart);
		}
		else
		{
			soapItemParts = orderPackageItem.getParts().stream()
					.map(this::toJAXB)
					.collect(ImmutableList.toImmutableList());
		}

		final BestellungAntwortPosition soapItem = jaxbObjectFactory.createBestellungAntwortPosition();
		soapItem.setBestellPzn(orderPackageItem.getPzn().getValueAsLong());
		soapItem.setBestellMenge(qty);
		soapItem.setBestellLiefervorgabe(orderPackageItem.getDeliverySpecifications().getV2SoapCode());
		soapItem.getAnteile().addAll(soapItemParts);
		soapItem.setSubstitution(toJAXB(orderPackageItem.getSubstitution()));
		return soapItem;
	}

	private OrderResponsePackageItemPart fromJAXB(final BestellungAnteil soap)
	{
		return OrderResponsePackageItemPart.builder()
				.qty(Quantity.of(soap.getMenge()))
				.type(soap.getTyp() != null ? soap.getTyp().value() : null)
				.deliveryDate(JAXBDateUtils.toLocalDateTime(soap.getLieferzeitpunkt()))
				.defectReason(OrderDefectReason.fromV2SoapCode(soap.getGrund()))
				.tour(soap.getTour())
				.tourId(soap.getTourId())
				.tourDeviation(soap.isTourabweichung())
				.build();
	}

	private BestellungAnteil toJAXB(final OrderResponsePackageItemPart itemPart)
	{
		final BestellungAnteil soap = jaxbObjectFactory.createBestellungAnteil();
		soap.setMenge(itemPart.getQty().getValueAsInt());
		soap.setTyp(BestellungRueckmeldungTyp.fromValue(itemPart.getType()));
		soap.setLieferzeitpunkt(JAXBDateUtils.toXMLGregorianCalendar(itemPart.getDeliveryDate()));
		soap.setGrund(itemPart.getDefectReason().getV2SoapCode());
		soap.setTour(itemPart.getTour());
		soap.setTourId(itemPart.getTourId());
		soap.setTourabweichung(itemPart.isTourDeviation());
		return soap;
	}

	private OrderResponsePackageItemSubstitution fromJAXB(final BestellungSubstitution soap)
	{
		if (soap == null)
		{
			return null;
		}

		return OrderResponsePackageItemSubstitution.builder()
				.substitutionReason(OrderSubstitutionReason.fromV2SoapCode(soap.getSubstitutionsgrund()))
				.defectReason(OrderDefectReason.fromV2SoapCode(soap.getGrund()))
				.pzn(PZN.of(soap.getLieferPzn()))
				.build();
	}

	private BestellungSubstitution toJAXB(final OrderResponsePackageItemSubstitution itemSubstitution)
	{
		if (itemSubstitution == null)
		{
			return null;
		}

		final BestellungSubstitution soap = jaxbObjectFactory.createBestellungSubstitution();
		soap.setSubstitutionsgrund(itemSubstitution.getSubstitutionReason().getV2SoapCode());
		soap.setGrund(itemSubstitution.getDefectReason().getV2SoapCode());
		soap.setLieferPzn(itemSubstitution.getPzn().getValueAsLong());
		return soap;
	}

	public JAXBElement<BestellstatusAbfragenResponse> toJAXB(final OrderStatusResponse response)
	{
		final BestellstatusAntwort soapResponseContent = jaxbObjectFactory.createBestellstatusAntwort();
		soapResponseContent.setId(response.getOrderId().getValueAsString());
		soapResponseContent.setBestellSupportId(response.getSupportId().getValueAsInt());
		soapResponseContent.setStatus(response.getOrderStatus().getV2SoapCode());
		soapResponseContent.getAuftraege().addAll(response.getOrderPackages().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));

		final BestellstatusAbfragenResponse soapResponse = jaxbObjectFactory.createBestellstatusAbfragenResponse();
		soapResponse.setReturn(soapResponseContent);
		return jaxbObjectFactory.createBestellstatusAbfragenResponse(soapResponse);
	}

	public static final FaultInfo fromJAXB(final Msv3FaultInfo msv3FaultInfo)
	{
		if (msv3FaultInfo == null)
		{
			return null;
		}

		return FaultInfo.builder()
				.errorCode(msv3FaultInfo.getErrorCode())
				.userMessage(msv3FaultInfo.getEndanwenderFehlertext())
				.technicalMessage(msv3FaultInfo.getTechnischerFehlertext())
				.build();
	}

	public final Msv3FaultInfo toJAXB(final FaultInfo faultInfo)
	{
		if (faultInfo == null)
		{
			return null;
		}

		final Msv3FaultInfo soap = jaxbObjectFactory.createMsv3FaultInfo();
		soap.setErrorCode(faultInfo.getErrorCode());
		soap.setEndanwenderFehlertext(faultInfo.getUserMessage());
		soap.setTechnischerFehlertext(faultInfo.getTechnicalMessage());
		return soap;
	}
}
