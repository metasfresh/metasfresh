package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreators;
import org.w3c.dom.Document;

import com.google.common.collect.ImmutableList;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3TestingTools;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3SubstitutionDataPersister;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungDefektgrund;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory;
import lombok.NonNull;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class,
		MSV3PurchaseOrderRequestPersister.class, Msv3FaultInfoDataPersister.class, Msv3SubstitutionDataPersister.class })
public class MSV3PurchaseOrderClientV2Test
{
	private static final BigDecimal CONFIRMED_ORDER_QTY = BigDecimal.TEN;
	private static final BigDecimal QTY_TO_PURCHASE = new BigDecimal("23");
	private static final int UOM_ID = 1;

	private MockWebServiceServer mockServer;
	private MSV3PurchaseOrderClientV2 msv3PurchaseOrderClient;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		msv3PurchaseOrderClient = MSV3PurchaseOrderClientV2.builder()
				.config(MSV3TestingTools.createMSV3ClientConfig())
				.connectionFactory(new MSV3ConnectionFactory())
				.supportIdProvider(new MockedSupportIdProvider())
				.build();

		mockServer = MockWebServiceServer.createServer(msv3PurchaseOrderClient.getWebServiceTemplate());
	}

	@Test
	public void placeOrder() throws Exception
	{
		final PurchaseOrderRequestItem purchaseOrderRequestItem = PurchaseOrderRequestItem.builder()
				.purchaseCandidateId(1)
				.productAndQuantity(ProductAndQuantity.of("10055555", QTY_TO_PURCHASE, UOM_ID))
				.build();
		final List<PurchaseOrderRequestItem> purchaseOrderRequestItems = ImmutableList.of(purchaseOrderRequestItem);

		final PurchaseOrderRequest request = PurchaseOrderRequest.builder()
				.orgId(10)
				.vendorId(20)
				.items(purchaseOrderRequestItems)
				.build();

		msv3PurchaseOrderClient.prepare(request);

		// set up the mock server
		final Source requestPayload = createRequest(msv3PurchaseOrderClient.getPurchaseOrderRequestPayload());
		final Source responsePayload = createResponse();
		mockServer
				.expect(RequestMatchers.payload(requestPayload))
				.andRespond(ResponseCreators.withPayload(responsePayload));

		// invoke the method under test
		final RemotePurchaseOrderCreated purchaseOrderResponse = msv3PurchaseOrderClient.placeOrder();

		assertThat(purchaseOrderResponse).isNotNull();

		if (purchaseOrderResponse.getException() != null)
		{
			purchaseOrderResponse.getException().printStackTrace();
		}
		assertThat(purchaseOrderResponse.getException()).isNull();

		final List<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems = purchaseOrderResponse.getPurchaseOrderResponseItems();
		assertThat(purchaseOrderResponseItems).hasSize(1);
		assertThat(purchaseOrderResponseItems.get(0).getInternalItemId()).isNotNull();
		assertThat(purchaseOrderResponseItems.get(0).getConfirmedOrderQuantity()).isEqualByComparingTo(CONFIRMED_ORDER_QTY);
	}

	private Source createRequest(@NonNull final JAXBElement<Bestellen> requestPayload) throws Exception
	{
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Document requestDocument = db.newDocument();

		final JAXBContext requestJc = JAXBContext.newInstance(Bestellen.class);
		final Marshaller requestMarshaller = requestJc.createMarshaller();
		requestMarshaller.marshal(requestPayload, requestDocument);
		final DOMSource payload = new DOMSource(requestDocument);

		return payload;
	}

	private Source createResponse() throws Exception
	{
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();

		final ObjectFactory objectFactory = new ObjectFactory();

		final GregorianCalendar c = new GregorianCalendar();
		c.setTime(SystemTime.asDate());
		final XMLGregorianCalendar lieferzeitpunkt = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		final BestellungAnteil bestellungAnteil1 = objectFactory.createBestellungAnteil();
		bestellungAnteil1.setLieferzeitpunkt(lieferzeitpunkt);
		bestellungAnteil1.setGrund(BestellungDefektgrund.KEINE_ANGABE);
		bestellungAnteil1.setMenge(CONFIRMED_ORDER_QTY.intValueExact());

		final BestellungAnteil bestellungAnteil2 = objectFactory.createBestellungAnteil();
		bestellungAnteil2.setLieferzeitpunkt(null);
		bestellungAnteil2.setGrund(BestellungDefektgrund.NICHT_GEFUEHRT);
		bestellungAnteil2.setMenge(QTY_TO_PURCHASE.subtract(CONFIRMED_ORDER_QTY).intValueExact());

		final BestellungAntwortPosition bestellungAntwortPosition = objectFactory.createBestellungAntwortPosition();
		bestellungAntwortPosition.getAnteile().add(bestellungAnteil1);
		bestellungAntwortPosition.getAnteile().add(bestellungAnteil2);
		bestellungAntwortPosition.setBestellLiefervorgabe(Liefervorgabe.NORMAL);
		bestellungAntwortPosition.setBestellMenge(QTY_TO_PURCHASE.intValueExact());
		bestellungAntwortPosition.setBestellPzn(1234);

		final BestellungAntwortAuftrag bestellungAntwortAuftrag = objectFactory.createBestellungAntwortAuftrag();
		bestellungAntwortAuftrag.setId("bestellungAntwortAuftrag.id");
		bestellungAntwortAuftrag.setAuftragsSupportID(1234);
		bestellungAntwortAuftrag.setAuftragskennung("1234");
		bestellungAntwortAuftrag.setAuftragsart(Auftragsart.NORMAL);
		bestellungAntwortAuftrag.getPositionen().add(bestellungAntwortPosition);

		final BestellungAntwort bestellungAntwort = objectFactory.createBestellungAntwort();
		bestellungAntwort.setId("bestellungAntwort.id");
		bestellungAntwort.setBestellSupportId(1234);
		bestellungAntwort.getAuftraege().add(bestellungAntwortAuftrag);

		final BestellenResponse bestellenResponse = objectFactory.createBestellenResponse();
		bestellenResponse.setReturn(bestellungAntwort);

		final JAXBElement<BestellenResponse> responsePayload = objectFactory.createBestellenResponse(bestellenResponse);

		final JAXBContext responseJc = JAXBContext.newInstance(BestellenResponse.class);
		final Marshaller responseMarshaller = responseJc.createMarshaller();
		final Document responseDocument = db.newDocument();
		responseMarshaller.marshal(responsePayload, responseDocument);

		return new DOMSource(responseDocument);
	}
}
