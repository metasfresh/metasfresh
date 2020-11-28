package de.metas.vertical.pharma.msv3.server.v2;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.vertical.pharma.msv3.protocol.order.DeliverySpecifications;
import de.metas.vertical.pharma.msv3.protocol.order.OrderType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.RequirementType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.server.Application;
import de.metas.vertical.pharma.msv3.server.MockedAmqpTemplate;
import de.metas.vertical.pharma.msv3.server.order.OrderWebServiceV2;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3EventVersion;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3MetasfreshUserId;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExclude;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ProductExcludesUpdateEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailability;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedBatchEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import de.metas.vertical.pharma.msv3.server.security.MSV3ServerAuthenticationService;
import de.metas.vertical.pharma.msv3.server.security.MSV3User;
import de.metas.vertical.pharma.msv3.server.security.MockedMSV3ServerAuthenticationService;
import de.metas.vertical.pharma.msv3.server.security.jpa.JpaUserRepository;
import de.metas.vertical.pharma.msv3.server.security.sync.UserSyncRabbitMQListener;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityWebServiceV2;
import de.metas.vertical.pharma.msv3.server.stockAvailability.sync.StockAvailabilityRabbitMQListener;
import de.metas.vertical.pharma.msv3.server.v2.IntegrationTest.IntegrationTestConfiguration;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellungPosition;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitAnfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitAnfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitRueckmeldungTyp;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitsanfrageEinzelne.Artikel;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerfuegbarkeitsantwortArtikel;
import lombok.NonNull;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-integrationtest.properties") // shall reset the DB
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

		@Bean
		@Primary
		public MSV3ServerAuthenticationService msv3ServerAuthenticationService(final JpaUserRepository usersRepo)
		{
			return new MockedMSV3ServerAuthenticationService(usersRepo);
		}
	}

	private static final PZN PZN_1 = PZN.of(123456789);
	private static final PZN PZN_2 = PZN.of(223456789);
	private static final PZN PZN_3 = PZN.of(323456789);
	private static final PZN PZN_4 = PZN.of(423456789);

	private static final PZN PZN_MISSING = PZN.of(1234567891);

	private final ObjectFactory jaxbObjectFactory = new ObjectFactory();

	@Autowired
	private MockedMSV3ServerAuthenticationService authService;

	@Autowired
	private UserSyncRabbitMQListener usersListener;

	@Autowired
	private StockAvailabilityRabbitMQListener stockAvailabilityListener;
	@Autowired
	private StockAvailabilityWebServiceV2 stockAvailabilityWebService;

	@Autowired
	private OrderWebServiceV2 orderWebService;

	private int eventVersionCounter = 0;

	private void setupDummyCurrentUserForBPartnerId(final int bpartnerId)
	{
		authService.setCurrentUser(MSV3User.builder()
				.username("user")
				.password("pass")
				.bpartnerId(BPartnerId.of(bpartnerId, 1234567))
				.build());
	}

	@Test
	public void testUsers()
	{
		createOrUpdateUser(MSV3MetasfreshUserId.of(10), "user", "pass", BPartnerId.of(1234, 5678));
	}

	@Test
	public void testStockAvailability()
	{
		setupDummyCurrentUserForBPartnerId(1234);

		createOrUpdateStockAvailability(PZN_1, 33);
		testStockAvailability(PZN_1, 10, 10);
		testStockAvailability(PZN_1, 33, 33);
		testStockAvailability(PZN_1, 100, 0);

		createOrUpdateStockAvailability(PZN_1, 200);
		testStockAvailability(PZN_1, 100, 100);
	}

	@Test
	public void testStockAvailability_delete_other_items()
	{
		setupDummyCurrentUserForBPartnerId(1234);

		createOrUpdateStockAvailability(PZN_1, 11);
		createOrUpdateStockAvailability(PZN_2, 22);
		testStockAvailability(PZN_1, 11, 11); // guard
		testStockAvailability(PZN_2, 22, 22); // guard

		final MSV3StockAvailabilityUpdatedEvent event = MSV3StockAvailabilityUpdatedEvent
				.builder()
				.eventVersion(MSV3EventVersion.of(++eventVersionCounter))
				.deleteAllOtherItems(true)
				.item(MSV3StockAvailability.builder()
						.pzn(PZN_2.getValueAsLong())
						.qty(23)
						.build())
				.item(MSV3StockAvailability.builder()
						.pzn(PZN_3.getValueAsLong())
						.qty(33)
						.build())
				.build();
		stockAvailabilityListener.onStockAvailabilityUpdatedEvent(event);

		testStockAvailability(PZN_1, 11, 0);
		testStockAvailability(PZN_2, 23, 23);
		testStockAvailability(PZN_3, 33, 33);
	}

	@Test
	public void testStockAvailability_delete_older_items()
	{
		// setup
		setupDummyCurrentUserForBPartnerId(1234);

		createOrUpdateStockAvailability(PZN_1, 11);
		createOrUpdateStockAvailability(PZN_2, 22);
		testStockAvailability(PZN_1, 11, 11); // guard
		testStockAvailability(PZN_2, 22, 22); // guard

		final MSV3EventVersion eventVersion = MSV3EventVersion.of(++eventVersionCounter);

		final MSV3StockAvailabilityUpdatedEvent event = MSV3StockAvailabilityUpdatedEvent
				.builder()
				.eventVersion(eventVersion)
				.deleteAllOtherItems(false)
				.item(MSV3StockAvailability.builder()
						.pzn(PZN_2.getValueAsLong())
						.qty(23)
						.build())
				.item(MSV3StockAvailability.builder()
						.pzn(PZN_3.getValueAsLong())
						.qty(33)
						.build())
				.build();
		stockAvailabilityListener.onStockAvailabilityUpdatedEvent(event);
		testStockAvailability(PZN_1, 11, 11); // guard
		testStockAvailability(PZN_2, 23, 23); // guard
		testStockAvailability(PZN_3, 33, 33); // guard

		createOrUpdateStockAvailability(PZN_4, 44);
		testStockAvailability(PZN_4, 44, 44); // guard

		// test
		stockAvailabilityListener.onStockAvailabilityUpdatedEvent(MSV3StockAvailabilityUpdatedEvent.deleteAllOlderThan(eventVersion));

		// verify
		testStockAvailability(PZN_1, 11, 0);
		testStockAvailability(PZN_2, 23, 23);
		testStockAvailability(PZN_3, 33, 33);
		testStockAvailability(PZN_4, 44, 44); // PZN_4 is still there because of its higher event version!
	}


	@Test
	public void testStockAvailability_UnknownPZN()
	{
		setupDummyCurrentUserForBPartnerId(1234);

		testStockAvailability(PZN_MISSING, 100, 0);
	}

	@Test
	public void testStockAvailability_ExcludedProduct()
	{
		setupDummyCurrentUserForBPartnerId(1234);

		createOrUpdateStockAvailability(PZN_1, 100);
		testStockAvailability(PZN_1, 90, 90);
		testStockAvailability(PZN_1, 100, 100);
		testStockAvailability(PZN_1, 110, 0);

		excludeProduct(PZN_1, 1234);
		testStockAvailability(PZN_1, 150, 0);
	}

	@Test
	@Ignore // ATM it's failing on some H2 unique index issue
	public void testOrder()
	{
		createOrUpdateUser(MSV3MetasfreshUserId.of(10), "user", "pass", BPartnerId.of(1234, 5678));
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
		soapOrderPackageItem.setLiefervorgabe(DeliverySpecifications.NORMAL.getV2SoapCode());

		final BestellungAuftrag soapOrderPackage = jaxbObjectFactory.createBestellungAuftrag();
		soapOrderPackage.setId("id");
		soapOrderPackage.setAuftragsSupportID(123);
		soapOrderPackage.setAuftragskennung("orderIdentification");
		soapOrderPackage.setAuftragsart(OrderType.NORMAL.getV2SoapCode());
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

	private void createOrUpdateUser(
			@NonNull MSV3MetasfreshUserId metasfreshMSV3CustomerId,
			final String username,
			final String password,
			final BPartnerId bpartner)
	{
		final MSV3UserChangedEvent event = MSV3UserChangedEvent.prepareCreatedOrUpdatedEvent(metasfreshMSV3CustomerId)
				.username(username)
				.password(password)
				.bpartnerId(bpartner.getBpartnerId())
				.bpartnerLocationId(bpartner.getBpartnerLocationId())
				.build();

		usersListener.onUserEvent(MSV3UserChangedBatchEvent.builder()
				.event(event)
				.build());

	}

	private void createOrUpdateStockAvailability(
			final PZN pzn,
			final int qtyAvailable)
	{
		final MSV3StockAvailabilityUpdatedEvent event = MSV3StockAvailabilityUpdatedEvent
				.builder()
				.eventVersion(MSV3EventVersion.of(++eventVersionCounter))
				.deleteAllOtherItems(false)
				.item(MSV3StockAvailability.builder()
						.pzn(pzn.getValueAsLong())
						.qty(qtyAvailable)
						.build())
				.build();
		stockAvailabilityListener.onStockAvailabilityUpdatedEvent(event);
	}

	private void excludeProduct(final PZN pzn, final int bpartnerId)
	{
		stockAvailabilityListener.onProductExcludesUpdateEvent(MSV3ProductExcludesUpdateEvent.builder()
				.item(MSV3ProductExclude.builder()
						.pzn(pzn)
						.bpartnerId(bpartnerId)
						.build())
				.build());
	}

	private void testStockAvailability(final PZN pzn, final int qtyRequired, final int qtyExpected)
	{
		final VerfuegbarkeitAnfragenResponse soapResponse = stockAvailabilityWebService.getStockAvailability(createStockAvailabilityQuery(pzn, qtyRequired)).getValue();
		final VerfuegbarkeitsantwortArtikel item = soapResponse.getReturn().getArtikel().get(0);
		assertThat(item.getAnfragePzn()).isEqualTo(pzn.getValueAsLong());
		assertThat(item.getAnfrageMenge()).isEqualTo(qtyRequired);

		final VerfuegbarkeitAnteil itemPart = item.getAnteile().get(0);
		if (qtyExpected == 0)
		{
			assertThat(itemPart.getMenge()).isEqualTo(qtyRequired);
			assertThat(itemPart.getTyp()).isEqualTo(VerfuegbarkeitRueckmeldungTyp.NICHT_LIEFERBAR);
		}
		else
		{
			assertThat(itemPart.getMenge()).isEqualTo(qtyExpected);
			assertThat(itemPart.getTyp()).isEqualTo(VerfuegbarkeitRueckmeldungTyp.NORMAL);
		}
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
		soapRequest.setClientSoftwareKennung("clientSofwareId");
		soapRequest.setVerfuegbarkeitsanfrage(stockAvailabilityQuery);
		return jaxbObjectFactory.createVerfuegbarkeitAnfragen(soapRequest);
	}
}
