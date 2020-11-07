package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Function;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreators;
import org.w3c.dom.Document;

import com.google.common.collect.ImmutableList;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderClientJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPart.Type;
import de.metas.vertical.pharma.msv3.protocol.order.v1.OrderJAXBConvertersV1;
import de.metas.vertical.pharma.msv3.protocol.order.v2.OrderJAXBConvertersV2;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3TestingTools;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungRueckmeldungTyp;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

public class MSV3PurchaseOrderClientTest
{
	private static final int UOM_ID = 1;

	@lombok.Value
	@lombok.Builder
	private static final class Context
	{
		MSV3ClientConfig config;
		OrderClientJAXBConverters jaxbConverters;
		MSV3PurchaseOrderClientImpl client;

		Class<?> bestellenClass;
		Function<Context, Source> responseProducer;

		BigDecimal qtyToPurchase;
		BigDecimal confirmedOrderQty;

		Type responseItemType;
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void placeOrder_V1() throws Exception
	{
		final MSV3ClientConfig config = MSV3TestingTools.createMSV3ClientConfig(MSV3ClientConfig.VERSION_1);
		final OrderClientJAXBConverters jaxbConverters = OrderJAXBConvertersV1.instance;
		final MSV3PurchaseOrderClientImpl client = MSV3PurchaseOrderClientImpl.builder()
				.connectionFactory(new MSV3ConnectionFactory())
				.config(config)
				.supportIdProvider(new MockedSupportIdProvider())
				.jaxbConverters(jaxbConverters)
				.build();

		placeOrder(Context.builder()
				.config(config)
				.jaxbConverters(jaxbConverters)
				.client(client)
				.qtyToPurchase(new BigDecimal("23"))
				.confirmedOrderQty(new BigDecimal("10"))
				.responseItemType(Type.NORMAL)
				.bestellenClass(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Bestellen.class)
				.responseProducer(this::createResponseV1)
				.build());
	}

	@Test
	public void placeOrder_V2() throws Exception
	{
		final MSV3ClientConfig config = MSV3TestingTools.createMSV3ClientConfig(MSV3ClientConfig.VERSION_2);
		final OrderClientJAXBConverters jaxbConverters = OrderJAXBConvertersV2.instance;
		final MSV3PurchaseOrderClientImpl client = MSV3PurchaseOrderClientImpl.builder()
				.connectionFactory(new MSV3ConnectionFactory())
				.config(config)
				.supportIdProvider(new MockedSupportIdProvider())
				.jaxbConverters(jaxbConverters)
				.build();

		placeOrder(Context.builder()
				.config(config)
				.jaxbConverters(jaxbConverters)
				.client(client)
				.qtyToPurchase(new BigDecimal("23"))
				.confirmedOrderQty(new BigDecimal("10"))
				.responseItemType(Type.NORMAL)
				.bestellenClass(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellen.class)
				.responseProducer(this::createResponseV2)
				.build());
	}

	private void placeOrder(final Context context) throws Exception
	{
		final PurchaseOrderRequestItem purchaseOrderRequestItem = PurchaseOrderRequestItem.builder()
				.purchaseCandidateId(1)
				.productAndQuantity(ProductAndQuantity.of("10055555", context.qtyToPurchase, UOM_ID))
				.build();
		final List<PurchaseOrderRequestItem> purchaseOrderRequestItems = ImmutableList.of(purchaseOrderRequestItem);

		final PurchaseOrderRequest request = PurchaseOrderRequest.builder()
				.orgId(10)
				.vendorId(20)
				.items(purchaseOrderRequestItems)
				.build();

		context.client.prepare(request);

		// set up the mock server
		final Source requestPayload = createRequest(context);
		final Source responsePayload = context.responseProducer.apply(context);

		final MockWebServiceServer mockServer = MockWebServiceServer.createServer(context.client.getWebServiceTemplate());
		mockServer
				.expect(RequestMatchers.payload(requestPayload))
				.andRespond(ResponseCreators.withPayload(responsePayload));

		// invoke the method under test
		final RemotePurchaseOrderCreated purchaseOrderResponse = context.client.placeOrder();

		assertThat(purchaseOrderResponse).isNotNull();

		if (purchaseOrderResponse.getException() != null)
		{
			purchaseOrderResponse.getException().printStackTrace();
		}
		assertThat(purchaseOrderResponse.getException()).isNull();

		final List<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems = purchaseOrderResponse.getPurchaseOrderResponseItems();
		assertThat(purchaseOrderResponseItems).hasSize(1);
		assertThat(purchaseOrderResponseItems.get(0).getInternalItemId()).isNotNull();
		assertThat(purchaseOrderResponseItems.get(0).getConfirmedOrderQuantity()).isEqualByComparingTo(context.confirmedOrderQty);
	}

	private static Source createRequest(final Context context) throws Exception
	{
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Document requestDocument = db.newDocument();

		final JAXBContext requestJc = JAXBContext.newInstance(context.bestellenClass);
		final Marshaller requestMarshaller = requestJc.createMarshaller();

		final JAXBElement<?> soap = context.jaxbConverters.encodeRequestToServer(context.client.getRequest(), context.client.getClientSoftwareId());
		requestMarshaller.marshal(soap, requestDocument);
		final DOMSource payload = new DOMSource(requestDocument);

		return payload;
	}

	private Source createResponseV1(final Context context)
	{
		try
		{
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			final DocumentBuilder db = dbf.newDocumentBuilder();

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.ObjectFactory objectFactory = new de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.ObjectFactory();

			final GregorianCalendar c = new GregorianCalendar();
			c.setTime(de.metas.common.util.time.SystemTime.asDate());
			final XMLGregorianCalendar lieferzeitpunkt = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungAnteil bestellungAnteil1 = objectFactory.createBestellungAnteil();
			bestellungAnteil1.setTyp(BestellungRueckmeldungTyp.fromValue(Type.getValueOrNull(context.responseItemType)));
			bestellungAnteil1.setLieferzeitpunkt(lieferzeitpunkt);
			bestellungAnteil1.setGrund(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.KEINE_ANGABE);
			bestellungAnteil1.setMenge(context.confirmedOrderQty.intValueExact());

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungAnteil bestellungAnteil2 = objectFactory.createBestellungAnteil();
			bestellungAnteil2.setTyp(BestellungRueckmeldungTyp.NICHT_LIEFERBAR);
			bestellungAnteil2.setLieferzeitpunkt(null);
			bestellungAnteil2.setGrund(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungDefektgrund.NICHT_GEFUEHRT);
			bestellungAnteil2.setMenge(context.qtyToPurchase.subtract(context.confirmedOrderQty).intValueExact());

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungAntwortPosition bestellungAntwortPosition = objectFactory.createBestellungAntwortPosition();
			bestellungAntwortPosition.getAnteile().add(bestellungAnteil1);
			bestellungAntwortPosition.getAnteile().add(bestellungAnteil2);
			bestellungAntwortPosition.setBestellLiefervorgabe(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Liefervorgabe.NORMAL);
			bestellungAntwortPosition.setBestellMenge(context.qtyToPurchase.intValueExact());
			bestellungAntwortPosition.setBestellPzn(1234);

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungAntwortAuftrag bestellungAntwortAuftrag = objectFactory.createBestellungAntwortAuftrag();
			bestellungAntwortAuftrag.setId("bestellungAntwortAuftrag.id");
			bestellungAntwortAuftrag.setAuftragsSupportID(1234);
			bestellungAntwortAuftrag.setAuftragskennung("1234");
			bestellungAntwortAuftrag.setAuftragsart(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Auftragsart.NORMAL);
			bestellungAntwortAuftrag.getPositionen().add(bestellungAntwortPosition);

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellungAntwort bestellungAntwort = objectFactory.createBestellungAntwort();
			bestellungAntwort.setId("bestellungAntwort.id");
			bestellungAntwort.setBestellSupportId(1234);
			bestellungAntwort.getAuftraege().add(bestellungAntwortAuftrag);

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellenResponse bestellenResponse = objectFactory.createBestellenResponse();
			bestellenResponse.setReturn(bestellungAntwort);

			final JAXBElement<de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellenResponse> responsePayload = objectFactory.createBestellenResponse(bestellenResponse);

			final JAXBContext responseJc = JAXBContext.newInstance(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.BestellenResponse.class);
			final Marshaller responseMarshaller = responseJc.createMarshaller();
			final Document responseDocument = db.newDocument();
			responseMarshaller.marshal(responsePayload, responseDocument);

			return new DOMSource(responseDocument);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private Source createResponseV2(final Context context)
	{
		try
		{
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			final DocumentBuilder db = dbf.newDocumentBuilder();

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory objectFactory = new de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory();

			final GregorianCalendar c = new GregorianCalendar();
			c.setTime(SystemTime.asDate());
			final XMLGregorianCalendar lieferzeitpunkt = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAnteil bestellungAnteil1 = objectFactory.createBestellungAnteil();
			bestellungAnteil1.setTyp(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungRueckmeldungTyp.fromValue(Type.getValueOrNull(context.responseItemType)));
			bestellungAnteil1.setLieferzeitpunkt(lieferzeitpunkt);
			bestellungAnteil1.setGrund(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.KEINE_ANGABE);
			bestellungAnteil1.setMenge(context.confirmedOrderQty.intValueExact());

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAnteil bestellungAnteil2 = objectFactory.createBestellungAnteil();
			bestellungAnteil2.setTyp(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungRueckmeldungTyp.NICHT_LIEFERBAR);
			bestellungAnteil2.setLieferzeitpunkt(null);
			bestellungAnteil2.setGrund(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund.NICHT_GEFUEHRT);
			bestellungAnteil2.setMenge(context.qtyToPurchase.subtract(context.confirmedOrderQty).intValueExact());

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwortPosition bestellungAntwortPosition = objectFactory.createBestellungAntwortPosition();
			bestellungAntwortPosition.getAnteile().add(bestellungAnteil1);
			bestellungAntwortPosition.getAnteile().add(bestellungAnteil2);
			bestellungAntwortPosition.setBestellLiefervorgabe(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe.NORMAL);
			bestellungAntwortPosition.setBestellMenge(context.qtyToPurchase.intValueExact());
			bestellungAntwortPosition.setBestellPzn(1234);

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwortAuftrag bestellungAntwortAuftrag = objectFactory.createBestellungAntwortAuftrag();
			bestellungAntwortAuftrag.setId("bestellungAntwortAuftrag.id");
			bestellungAntwortAuftrag.setAuftragsSupportID(1234);
			bestellungAntwortAuftrag.setAuftragskennung("1234");
			bestellungAntwortAuftrag.setAuftragsart(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart.NORMAL);
			bestellungAntwortAuftrag.getPositionen().add(bestellungAntwortPosition);

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwort bestellungAntwort = objectFactory.createBestellungAntwort();
			bestellungAntwort.setId("bestellungAntwort.id");
			bestellungAntwort.setBestellSupportId(1234);
			bestellungAntwort.getAuftraege().add(bestellungAntwortAuftrag);

			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellenResponse bestellenResponse = objectFactory.createBestellenResponse();
			bestellenResponse.setReturn(bestellungAntwort);

			final JAXBElement<de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellenResponse> responsePayload = objectFactory.createBestellenResponse(bestellenResponse);

			final JAXBContext responseJc = JAXBContext.newInstance(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellenResponse.class);
			final Marshaller responseMarshaller = responseJc.createMarshaller();
			final Document responseDocument = db.newDocument();
			responseMarshaller.marshal(responsePayload, responseDocument);

			return new DOMSource(responseDocument);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}
}
