package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.adempiere.test.AdempiereTestHelper;
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
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3TestingTools;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3SubstitutionDataPersister;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Auftragsart;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellenResponse;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.ObjectFactory;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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
		MSV3PurchaseOrderDataPersister.class, Msv3FaultInfoDataPersister.class, Msv3SubstitutionDataPersister.class })
public class MSV3PurchaseOrderClientTest
{
	private MockWebServiceServer mockServer;
	private MSV3PurchaseOrderClient msv3PurchaseOrderClient;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		msv3PurchaseOrderClient = MSV3PurchaseOrderClient.builder()
				.config(MSV3TestingTools.createMSV3ClientConfig())
				.connectionFactory(new MSV3ConnectionFactory())
				.build();

		mockServer = MockWebServiceServer.createServer(msv3PurchaseOrderClient.getWebServiceTemplate());
		MSV3TestingTools.setDBVersion(MSV3PurchaseOrderClientTest.class.getSimpleName());
	}

	@Test
	public void placeOrder() throws Exception
	{
		final PurchaseOrderRequestItem purchaseOrderRequestItem = new PurchaseOrderRequestItem(
				1, // id
				new ProductAndQuantity("10055555", BigDecimal.TEN));
		final List<PurchaseOrderRequestItem> purchaseOrderRequestItems = ImmutableList.of(purchaseOrderRequestItem);

		final PurchaseOrderRequest request = new PurchaseOrderRequest(10, 20, purchaseOrderRequestItems);

		msv3PurchaseOrderClient.prepare(request);

		// set up the mock server
		final Source requestPayload = createRequest(msv3PurchaseOrderClient.getPurchaseOrderRequestPayload());
		final Source responsePayload = createResponse();
		mockServer
				.expect(RequestMatchers.payload(requestPayload))
				.andRespond(ResponseCreators.withPayload(responsePayload));

		// invoke the method under test
		final RemotePurchaseOrderCreated purchaseOrderResponse = msv3PurchaseOrderClient.placeOrder(request);

		assertThat(purchaseOrderResponse).isNotNull();
		assertThat(purchaseOrderResponse.getException()).isNull();
	}

	private Source createResponse() throws Exception
	{
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();

		final ObjectFactory objectFactory = new ObjectFactory();

		final BestellungAntwortAuftrag bestellungAntwortAuftrag = objectFactory.createBestellungAntwortAuftrag();
		bestellungAntwortAuftrag.setAuftragsart(Auftragsart.NORMAL);
		final BestellungAntwort bestellungAntwort = objectFactory.createBestellungAntwort();
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
}
