package de.metas.procurement.webui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.IServerSync;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSupply;
import de.metas.procurement.sync.protocol.SyncProductsRequest;
import de.metas.procurement.sync.protocol.SyncWeeklySupply;
import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.SpringIntegrationTest.TestConfig;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.repository.ProductRepository;
import de.metas.procurement.webui.repository.ProductSupplyRepository;
import de.metas.procurement.webui.repository.UserRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.sync.IServerSyncService;
import de.metas.procurement.webui.util.DateRange;
import de.metas.procurement.webui.util.DateUtils;

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
@SpringApplicationConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SpringIntegrationTest
{
	@Configuration
	@Import(Application.class)
	public static class TestConfig
	{
		@Bean
		public IServerSync serverSync()
		{
			return new MockedTestServerSync();
		}
	}

	@Autowired
	private IServerSyncService serverSyncService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IProductSuppliesService productSuppliesService;
	@Autowired
	private ProductSupplyRepository productSupplyRepository;
	@Autowired
	private ProductRepository productsRepo;

	// see https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html
	@Value("${local.server.port}")
	private int serverPort;

	@Autowired
	private IServerSync serverSync;

	@Autowired
	private IAgentSync agentSync;

	private final Random random = new Random();

	private MockedTestServerSync getMockedTestServerSync()
	{
		return (MockedTestServerSync)serverSync;
	}

	@Test
	public void testCreateProductSupply() throws Exception
	{
		serverSyncService.awaitInitialSyncComplete(20, TimeUnit.SECONDS);

		final Date day = DateUtils.truncToDay(new Date());

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
			final SyncProductsRequest request = new SyncProductsRequest();
			for (final Product product : productsRepo.findAll())
			{
				final SyncProduct syncProduct = new SyncProduct();
				syncProduct.setUuid(product.getUuid());
				syncProduct.setDeleted(true);
				request.getProducts().add(syncProduct);
			}
			agentSync.syncProducts(request);

			Assert.assertEquals(ImmutableList.<Product> of(), productSuppliesService.getAllProducts());
		}

		//
		//
		{
			// final IAgentSync client = JAXRSClientFactory.create("http://localhost:" + serverPort + "/rest", IAgentSync.class);
			// WebClient.client(client).type(MediaType.APPLICATION_JSON_TYPE);

			// TODO

		}
	}

	private void reportProductSupplyAndTest(final BPartner bpartner, final Product product, final Date day, final BigDecimal qty) throws Exception
	{
		// Report the product supply
		final ContractLine contractLine = null;
		productSuppliesService.reportSupply(bpartner, product, contractLine, day, qty);

		// Make sure it's saved in database
		final ProductSupply productSupply = productSupplyRepository.findByProductAndBpartnerAndDay(product, bpartner, day);
		Assert.assertThat("Invalid Qty", productSupply.getQty(), Matchers.comparesEqualTo(qty));

		// Make sure it was reported
		final SyncProductSupply syncProductSupply = getMockedTestServerSync().getAndRemoveReportedProductSupply(productSupply.getUuid());
		assertEquals(productSupply, syncProductSupply);
	}

	private void assertEquals(final ProductSupply expected, final SyncProductSupply actual)
	{
		final String expectedContractLineUUID = expected.getContractLine() == null ? null : expected.getContractLine().getUuid();

		Assert.assertEquals(expected.getUuid(), actual.getUuid());
		Assert.assertEquals(expected.getBpartner().getUuid(), actual.getBpartner_uuid());
		Assert.assertEquals(expected.getProduct().getUuid(), actual.getProduct_uuid());
		Assert.assertEquals(expectedContractLineUUID, actual.getContractLine_uuid());
		Assert.assertEquals(expected.getDay().getTime(), actual.getDay().getTime());
		Assert.assertThat(actual.getQty(), Matchers.comparesEqualTo(expected.getQty()));
	}

	private void reportNextWeekTrend(final BPartner bpartner, final Product product, final Date day, final Trend trend) throws Exception
	{
		// Report the trend
		final DateRange week = DateRange.createWeek(day);
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
		Assert.assertEquals(expected.getBpartner().getUuid(), actual.getBpartner_uuid());
		Assert.assertEquals(expected.getProduct().getUuid(), actual.getProduct_uuid());
		Assert.assertEquals(expected.getDay().getTime(), actual.getWeekDay().getTime());
		Assert.assertEquals(expected.getTrend(), actual.getTrend());
	}
}
