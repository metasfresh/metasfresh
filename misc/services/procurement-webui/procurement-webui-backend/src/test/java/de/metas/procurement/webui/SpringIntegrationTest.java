package de.metas.procurement.webui;

import com.google.common.collect.ImmutableList;
import de.metas.common.procurement.sync.protocol.dto.IConfirmableDTO;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.dto.SyncProductSupply;
import de.metas.common.procurement.sync.protocol.dto.SyncWeeklySupply;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import de.metas.procurement.webui.model.AbstractSyncConfirmAwareEntity;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.repository.ProductRepository;
import de.metas.procurement.webui.repository.ProductSupplyRepository;
import de.metas.procurement.webui.repository.SyncConfirmRepository;
import de.metas.procurement.webui.repository.UserRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.sync.ISenderToMetasfreshService;
import de.metas.procurement.webui.sync.ReceiverFromMetasfreshHandler;
import de.metas.procurement.webui.util.DateUtils;
import de.metas.procurement.webui.util.YearWeekUtil;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.threeten.extra.YearWeek;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@RunWith(SpringJUnit4ClassRunner.class)
// @SpringApplicationConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
// @IntegrationTest("server.port:0")
public class SpringIntegrationTest
{
	@Autowired
	private ISenderToMetasfreshService serverSyncService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IProductSuppliesService productSuppliesService;
	@Autowired
	private ProductSupplyRepository productSupplyRepository;
	@Autowired
	private ProductRepository productsRepo;
	@Autowired
	private SyncConfirmRepository syncConfirmRepository;

	// see https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html
	@Value("${local.server.port}")
	private int serverPort;

	@Autowired
	private MockedTestServerSync serverSync;

	@Autowired
	private ReceiverFromMetasfreshHandler receiverFromMetasfreshHandler;

	private final Random random = new Random();

	private MockedTestServerSync getMockedTestServerSync()
	{
		return serverSync;
	}

	@Test
	public void testCreateProductSupply() throws Exception
	{
		serverSyncService.awaitInitialSyncComplete(20, TimeUnit.SECONDS);

		final LocalDate day = LocalDate.now();

		final List<User> users = userRepository.findAll();
		Assert.assertFalse("Users shall not be empty", users.isEmpty());

		final List<Product> products = productSuppliesService.getAllProducts();
		Assert.assertFalse("Products shall not be empty", products.isEmpty());

		//
		// Test reporting
		for (final User user : users)
		{
			final BPartner bpartner = user.getBpartner();
			for (final Product product : products)
			{
				//
				// Test reporting product supply
				for (int i = 1; i <= 10; i++)
				{
					final BigDecimal qty = new BigDecimal(random.nextInt(9999));
					reportProductSupplyAndTest(bpartner, product, day, qty);
				}

				//
				// Test reporting next week trends
				reportNextWeekTrend(bpartner, product, day, null); // null trend
				for (final Trend trend : Trend.values())
				{
					reportNextWeekTrend(bpartner, product, day, trend);
				}
			}
		}

		//
		// Test delete all product
		{
			final PutProductsRequest.PutProductsRequestBuilder request = PutProductsRequest.builder();
			for (final Product product : productsRepo.findAll())
			{
				final SyncProduct syncProduct = SyncProduct.builder()
						.uuid(product.getUuid())
						.deleted(true)
						.build();
				request.product(syncProduct);
			}
			receiverFromMetasfreshHandler.handlePutProductsRequest(request.build());

			Assert.assertEquals(ImmutableList.<Product>of(), productSuppliesService.getAllProducts());
		}

		//
		//
		{
			// final IAgentSync client = JAXRSClientFactory.create("http://localhost:" + serverPort + "/rest", IAgentSync.class);
			// WebClient.client(client).type(MediaType.APPLICATION_JSON_TYPE);

			// TODO

		}
	}

	private void reportProductSupplyAndTest(final BPartner bpartner, final Product product, final LocalDate day, final BigDecimal qty) throws Exception
	{
		// Report the product supply
		productSuppliesService.reportSupply(IProductSuppliesService.ReportDailySupplyRequest.builder()
				.bpartner(bpartner)
				.contractLine(null)
				.productId(product.getId())
				.date(day)
				.qty(qty)
				.qtyConfirmedByUser(true)
				.build());

		// Make sure it's saved in database
		final ProductSupply productSupply = productSupplyRepository.findByProductAndBpartnerAndDay(product, bpartner, DateUtils.toSqlDate(day));
		Assertions.assertThat(productSupply.getQty()).isEqualByComparingTo(qty);

		// Make sure it was reported
		final SyncProductSupply syncProductSupply = getMockedTestServerSync().getAndRemoveReportedProductSupply(productSupply.getUuid());
		assertEquals(productSupply, syncProductSupply);
	}

	private void assertEquals(final ProductSupply expected, final SyncProductSupply actual)
	{
		final String expectedContractLineUUID = expected.getContractLine() == null ? null : expected.getContractLine().getUuid();

		Assert.assertEquals(expected.getUuid(), actual.getUuid());
		Assert.assertEquals(expected.getBpartnerUUID(), actual.getBpartner_uuid());
		Assert.assertEquals(expected.getProductUUID(), actual.getProduct_uuid());
		Assert.assertEquals(expectedContractLineUUID, actual.getContractLine_uuid());
		Assert.assertEquals(expected.getDay(), actual.getDay());
		Assertions.assertThat(actual.getQty()).isEqualByComparingTo(expected.getQty());

		assertConfirmOK(expected, actual);
	}

	private void reportNextWeekTrend(final BPartner bpartner, final Product product, final LocalDate day, final Trend trend) throws Exception
	{
		// Report the trend
		final YearWeek week = YearWeekUtil.ofLocalDate(day);
		final WeekSupply weeklySupply = productSuppliesService.setNextWeekTrend(bpartner, product, week, trend);

		// Make sure it's saved in database
		final Trend trendActual = productSuppliesService.getNextWeekTrend(bpartner, product, week);
		Assert.assertEquals("Invalid trend", trend, trendActual);

		// Make sure it was reported
		final SyncWeeklySupply syncWeeklySupply = getMockedTestServerSync().getAndRemoveReportedWeeklySupply(weeklySupply.getUuid());
		assertEquals(weeklySupply, syncWeeklySupply);
	}

	private void assertEquals(final WeekSupply expected, final SyncWeeklySupply actual)
	{
		Assert.assertEquals(expected.getUuid(), actual.getUuid());
		Assert.assertEquals(expected.getBpartnerUUID(), actual.getBpartner_uuid());
		Assert.assertEquals(expected.getProductUUID(), actual.getProduct_uuid());
		Assert.assertEquals(expected.getDay(), actual.getWeekDay());
		Assert.assertEquals(expected.getTrend(), Trend.ofNullableCode(actual.getTrend()));

		assertConfirmOK(expected, actual);
	}

	/**
	 * task https://metasfresh.atlassian.net/browse/FRESH-206
	 */
	private void assertConfirmOK(final AbstractSyncConfirmAwareEntity expected, final IConfirmableDTO actual)
	{
		Assertions.assertThat(actual.getSyncConfirmationId()).isGreaterThan(0L);
		// final SyncConfirm confirmRecord = syncConfirmRepository.findOne(actual.getSyncConfirmationId());
		// assertThat(confirmRecord, notNullValue());
		// assertThat(confirmRecord.getEntryId(), is(expected.getId()));
		// assertThat(confirmRecord.getEntryUuid(), is(expected.getUuid()));
		// assertThat(confirmRecord.getEntryType(), is(expected.getClass().getSimpleName()));
	}

}
