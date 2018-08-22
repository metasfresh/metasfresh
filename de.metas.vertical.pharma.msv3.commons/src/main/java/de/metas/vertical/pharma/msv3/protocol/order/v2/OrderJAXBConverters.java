package de.metas.vertical.pharma.msv3.protocol.order.v2;

import java.time.LocalDateTime;

import javax.xml.bind.JAXBElement;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.order.DeliverySpecifications;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderType;
import de.metas.vertical.pharma.msv3.protocol.order.SupportIDType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.protocol.util.JAXBDateUtils;
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

	public OrderJAXBConverters(@NonNull final ObjectFactory jaxbObjectFactory)
	{
		this.jaxbObjectFactory = jaxbObjectFactory;
	}

	public OrderCreateRequest fromJAXB(final Bestellung soapOrder, final BPartnerId bpartnerId)
	{
		return OrderCreateRequest.builder()
				.orderId(Id.of(soapOrder.getId()))
				.supportId(SupportIDType.of(soapOrder.getBestellSupportId()))
				.bpartnerId(bpartnerId)
				.orderPackages(soapOrder.getAuftraege().stream()
						.map(this::createOrderPackage)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderCreateRequestPackage createOrderPackage(final BestellungAuftrag soapOrderPackage)
	{
		return OrderCreateRequestPackage.builder()
				.id(Id.of(soapOrderPackage.getId()))
				.orderType(OrderType.fromV2SoapCode(soapOrderPackage.getAuftragsart()))
				.orderIdentification(soapOrderPackage.getAuftragskennung())
				.supportId(SupportIDType.of(soapOrderPackage.getAuftragsSupportID()))
				.packingMaterialId(soapOrderPackage.getGebindeId())
				.items(soapOrderPackage.getPositionen().stream()
						.map(this::createOrderPackageItem)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderCreateRequestPackageItem createOrderPackageItem(final BestellungPosition soapOrderPackageItem)
	{
		return OrderCreateRequestPackageItem.builder()
				.pzn(PZN.of(soapOrderPackageItem.getPzn()))
				.qty(Quantity.of(soapOrderPackageItem.getMenge()))
				.deliverySpecifications(DeliverySpecifications.fromV2SoapCode(soapOrderPackageItem.getLiefervorgabe()))
				.build();
	}

	public JAXBElement<BestellenResponse> toJAXB(final OrderCreateResponse response)
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

	private BestellungAntwortAuftrag toJAXB(final OrderResponsePackage orderPackage)
	{
		final BestellungAntwortAuftrag soapOrderPackage = jaxbObjectFactory.createBestellungAntwortAuftrag();
		soapOrderPackage.setId(orderPackage.getId().getValueAsString());
		soapOrderPackage.setAuftragsart(orderPackage.getOrderType().getV2SoapCode());
		soapOrderPackage.setAuftragskennung(orderPackage.getOrderIdentification());
		soapOrderPackage.setAuftragsSupportID(orderPackage.getSupportId().getValueAsInt());
		soapOrderPackage.setGebindeId(orderPackage.getPackingMaterialId());
		// soapOrderPackage.setAuftragsfehler(value); // TODO
		soapOrderPackage.getPositionen().addAll(orderPackage.getItems().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));
		return soapOrderPackage;
	}

	private BestellungAntwortPosition toJAXB(final OrderResponsePackageItem orderPackageItem)
	{
		final int qty = orderPackageItem.getQty().getValueAsInt();

		final BestellungAnteil soapItemPart = jaxbObjectFactory.createBestellungAnteil();
		soapItemPart.setMenge(qty);
		soapItemPart.setTyp(BestellungRueckmeldungTyp.NORMAL);
		soapItemPart.setGrund(BestellungDefektgrund.KEINE_ANGABE);
		soapItemPart.setLieferzeitpunkt(JAXBDateUtils.toXMLGregorianCalendar(LocalDateTime.now().plusDays(1))); // FIXME: hardcoded
		soapItemPart.setTourId("1"); // FIXME: hardcoded

		final BestellungAntwortPosition soapItem = jaxbObjectFactory.createBestellungAntwortPosition();
		soapItem.setBestellPzn(orderPackageItem.getPzn().getValueAsLong());
		soapItem.setBestellMenge(qty);
		soapItem.setBestellLiefervorgabe(orderPackageItem.getDeliverySpecifications().getV2SoapCode());
		soapItem.getAnteile().add(soapItemPart);
		// soapItem.setSubstitution(value); // TODO
		return soapItem;
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
}
