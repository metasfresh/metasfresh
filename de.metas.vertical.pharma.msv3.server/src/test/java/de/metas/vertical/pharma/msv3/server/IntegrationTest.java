package de.metas.vertical.pharma.msv3.server;

import static org.assertj.core.api.Assertions.assertThat;

import javax.xml.bind.JAXBElement;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.vertical.pharma.msv3.protocol.order.DeliverySpecifications;
import de.metas.vertical.pharma.msv3.protocol.order.OrderType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.RequirementType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.server.IntegrationTest.IntegrationTestConfiguration;
import de.metas.vertical.pharma.msv3.server.order.OrderWebService;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailability;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedBatchEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import de.metas.vertical.pharma.msv3.server.security.sync.UserSyncRabbitMQListener;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityWebService;
import de.metas.vertical.pharma.msv3.server.stockAvailability.sync.StockAvailabilityRabbitMQListener;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.BestellenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.BestellungPosition;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne.Artikel;

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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTestConfiguration.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class IntegrationTest
{
	@TestConfiguration
	@Import(Application.class)
	public static class IntegrationTestConfiguration
	{
		@Bean
		@Primary
		public AmqpTemplate amqpTemplate()
		{
			return new MockedAmqpTemplate();
		}
	}

	private static final PZN PZN_1 = PZN.of(123456789);
	private static final PZN PZN_MISSING = PZN.of(1234567891);

	private ObjectFactory jaxbObjectFactory = new ObjectFactory();

	@Autowired
	private UserSyncRabbitMQListener usersListener;

	@Autowired
	private StockAvailabilityRabbitMQListener stockAvailabilityListener;
	@Autowired
	private StockAvailabilityWebService stockAvailabilityWebService;

	@Autowired
	private OrderWebService orderWebService;

	@Test
	public void testUsers()
	{
		createOrUpdateUser("user", "pass", BPartnerId.of(1234, 5678));
	}

	@Test
	public void testStockAvailability()
	{
		createOrUpdateStockAvailability(PZN_1, 33);
		testStockAvailability(PZN_1, 10, 10);
		testStockAvailability(PZN_1, 33, 33);
		testStockAvailability(PZN_1, 100, 33);

		createOrUpdateStockAvailability(PZN_1, 200);
		testStockAvailability(PZN_1, 100, 100);
	}

	@Test
	public void testStockAvailability_UnknownPZN()
	{
		testStockAvailability(PZN_MISSING, 100, 0);
	}

	@Test
	@Ignore // ATM it's failing on some H2 unique index issue
	public void testOrder()
	{
		createOrUpdateUser("user", "pass", BPartnerId.of(1234, 5678));
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", "pass"));
		
		createOrUpdateStockAvailability(PZN_1, 10);
		testOrder(PZN_1, 5, 5);
		testOrder(PZN_1, 10, 10);
		testOrder(PZN_1, 15, 10);
	}

	private void testOrder(final PZN pzn, final int qtyOrdered, final int qtyOrderedExpected)
	{
		final BestellungPosition soapOrderPackageItem = jaxbObjectFactory.createBestellungPosition();
		soapOrderPackageItem.setPzn(pzn.getValueAsLong());
		soapOrderPackageItem.setMenge(qtyOrdered);
		soapOrderPackageItem.setLiefervorgabe(DeliverySpecifications.NORMAL.getSoapCode());

		final BestellungAuftrag soapOrderPackage = jaxbObjectFactory.createBestellungAuftrag();
		soapOrderPackage.setId("id");
		soapOrderPackage.setAuftragsSupportID(123);
		soapOrderPackage.setAuftragskennung("orderIdentification");
		soapOrderPackage.setAuftragsart(OrderType.NORMAL.getSoapCode());
		// soapOrderPackage.setGebindeId("gebindeId");
		soapOrderPackage.getPositionen().add(soapOrderPackageItem);

		final Bestellung soapOrder = jaxbObjectFactory.createBestellung();
		soapOrder.setId("999");
		soapOrder.setBestellSupportId(123);
		soapOrder.getAuftraege().add(soapOrderPackage);

		final Bestellen soapRequest = jaxbObjectFactory.createBestellen();
		soapRequest.setBestellung(soapOrder);

		final BestellenResponse soapResponse = orderWebService.createOrder(jaxbObjectFactory.createBestellen(soapRequest)).getValue();
		final BestellungAntwortAuftrag soapResponseOrderPackage = soapResponse.getReturn().getAuftraege().get(0);
		final BestellungAntwortPosition soapResponseOrderPackageItem = soapResponseOrderPackage.getPositionen().get(0);
		assertThat(soapResponseOrderPackageItem.getBestellPzn()).isEqualTo(pzn.getValueAsLong());
		assertThat(soapResponseOrderPackageItem.getBestellMenge()).isEqualTo(qtyOrderedExpected);
	}

	private void createOrUpdateUser(final String username, final String password, final BPartnerId bpartner)
	{
		usersListener.onUserEvent(MSV3UserChangedBatchEvent.builder()
				.event(MSV3UserChangedEvent.prepareCreatedOrUpdatedEvent()
						.username(username)
						.password(password)
						.bpartnerId(bpartner.getBpartnerId())
						.bpartnerLocationId(bpartner.getBpartnerLocationId())
						.build())
				.build());

	}

	private void createOrUpdateStockAvailability(final PZN pzn, final int qtyAvailable)
	{
		stockAvailabilityListener.onEvent(MSV3StockAvailabilityUpdatedEvent.builder()
				.item(MSV3StockAvailability.builder()
						.pzn(pzn.getValueAsLong())
						.qty(qtyAvailable)
						.build())
				.build());
	}

	private void testStockAvailability(PZN pzn, final int qtyRequired, final int qtyExpected)
	{
		final VerfuegbarkeitAnfragenResponse soapResponse = stockAvailabilityWebService.getStockAvailability(createStockAvailabilityQuery(pzn, qtyRequired)).getValue();
		assertThat(soapResponse.getReturn().getArtikel().get(0).getAnfragePzn()).isEqualTo(pzn.getValueAsLong());
		assertThat(soapResponse.getReturn().getArtikel().get(0).getAnfrageMenge()).isEqualTo(qtyExpected);
	}

	private final JAXBElement<VerfuegbarkeitAnfragen> createStockAvailabilityQuery(final PZN pzn, final int qtyRequired)
	{
		final Artikel stockAvailabilityQueryItem = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelneArtikel();
		stockAvailabilityQueryItem.setPzn(pzn.getValueAsLong());
		stockAvailabilityQueryItem.setMenge(qtyRequired);
		stockAvailabilityQueryItem.setBedarf(RequirementType.DIRECT.getCode());

		final VerfuegbarkeitsanfrageEinzelne stockAvailabilityQuery = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelne();
		stockAvailabilityQuery.setId("111");
		stockAvailabilityQuery.getArtikel().add(stockAvailabilityQueryItem);

		final VerfuegbarkeitAnfragen soapRequest = jaxbObjectFactory.createVerfuegbarkeitAnfragen();
		soapRequest.setVerfuegbarkeitsanfrage(stockAvailabilityQuery);
		return jaxbObjectFactory.createVerfuegbarkeitAnfragen(soapRequest);
	}
}
