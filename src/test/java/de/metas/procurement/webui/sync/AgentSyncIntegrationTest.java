package de.metas.procurement.webui.sync;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.IServerSync;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.procurement.sync.protocol.SyncContract;
import de.metas.procurement.sync.protocol.SyncContractLine;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncUser;
import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.repository.ContractLineRepository;
import de.metas.procurement.webui.repository.ContractRepository;
import de.metas.procurement.webui.repository.ProductRepository;
import de.metas.procurement.webui.repository.ProductSupplyRepository;
import de.metas.procurement.webui.repository.UserRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.sync.AgentSyncIntegrationTest.AgentSyncTestConfig;
import de.metas.procurement.webui.util.DateUtils;
import de.metas.procurement.webui.util.DummyDataProducer;

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
@SpringApplicationConfiguration(classes = { AgentSyncTestConfig.class })
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AgentSyncIntegrationTest
{
	@Configuration
	@Import(Application.class)
	public static class AgentSyncTestConfig
	{
		@Bean
		public IServerSync serverSync()
		{
			return new NullServerSync();
		}
	}

	@Rule
	public TestWatcher testWatcher = new TestWatcher()
	{
		@Override
		protected void failed(final Throwable e, final Description description)
		{
			dumpAll("After " + description + " failed");
		}
	};

	//
	// Services
	@Autowired
	IAgentSync agentSync;
	@Autowired
	DummyDataProducer dummyDataProducer;
	@Autowired
	IProductSuppliesService productSuppliesService;

	//
	// Repositories
	@Autowired
	BPartnerRepository bpartnerRepo;
	@Autowired
	UserRepository usersRepo;
	@Autowired
	ProductRepository productsRepo;
	@Autowired
	ContractRepository contractsRepo;
	@Autowired
	ContractLineRepository contractLinesRepo;
	@Autowired
	ProductSupplyRepository productSuppliesRepo;

	private final Date contractDateFrom = DateUtils.toDayDate(2015, 04, 01);
	private final Date contractDateTo = DateUtils.toDayDate(2016, 03, 31);

	@Test
	public void test_ImportContracts_ReportQty() throws Exception
	{
		//
		// Master data
		final SyncProduct syncProduct1 = dummyDataProducer.createSyncProduct("P1", "P1 packing info");

		//
		// Create a partner with one contract with one line
		final SyncBPartner syncBPartner1 = new SyncBPartner();
		final SyncContract syncContract1 = new SyncContract();
		final SyncContractLine syncContractLine1 = new SyncContractLine();
		{
			syncBPartner1.setUuid(newUUID());
			syncBPartner1.setName("Test");
			syncBPartner1.setSyncContracts(true);

			syncBPartner1.getContracts().add(syncContract1);
			syncContract1.setUuid(newUUID());
			syncContract1.setDateFrom(contractDateFrom);
			syncContract1.setDateTo(contractDateTo);

			syncContract1.getContractLines().add(syncContractLine1);
			syncContractLine1.setUuid(newUUID());
			syncContractLine1.setProduct(syncProduct1);

			agentSync.syncBPartners(SyncBPartnersRequest.of(syncBPartner1));

			Assert.assertEquals(
					"only our contract line shall be present in database"
					// expected
					, Arrays.asList(contractLinesRepo.findByUuid(syncContractLine1.getUuid()))
					// actual
					, contractLinesRepo.findAll());
		}

		//
		// Report a supply on this contract line
		{
			final BPartner bpartner = bpartnerRepo.findByUuid(syncBPartner1.getUuid());
			final Product product = productsRepo.findByUuid(syncProduct1.getUuid());
			final ContractLine contractLine = contractLinesRepo.findByUuid(syncContractLine1.getUuid());
			final Date day = DateUtils.truncToDay(new Date());
			final BigDecimal qty = new BigDecimal("10");
			productSuppliesService.reportSupply(bpartner, product, contractLine, day, qty);
		}

		//
		// Create a new contract line with same product and send the partner with this new line and without the old one
		//
		// case: a contract line for a product was canceled and a new contract line for same product was created
		// expectation: the preview contract line is marked as deleted and the new contract line is created
		{
			final SyncContractLine syncContractLine2 = new SyncContractLine();
			syncContractLine2.setUuid(newUUID());
			syncContractLine2.setProduct(syncProduct1);
			//
			syncContract1.getContractLines().clear();
			syncContract1.getContractLines().add(syncContractLine2);
			//
			agentSync.syncBPartners(SyncBPartnersRequest.of(syncBPartner1));

			Assert.assertEquals(
					"Expect only our second line to be present in database"
					// expected
					, Arrays.asList(contractLinesRepo.findByUuid(syncContractLine2.getUuid()))
					// actual
					, contractLinesRepo.findAll());
		}

		//
		// Delete the contract
		{
			syncBPartner1.getContracts().clear();
			agentSync.syncBPartners(SyncBPartnersRequest.of(syncBPartner1));
			Assert.assertEquals("No contracts", Arrays.asList(), contractLinesRepo.findAll());
			Assert.assertEquals("No contract lines", Arrays.asList(), contractLinesRepo.findAll());
		}

		// //
		// // Dump database:
		// dump("Product Supplies", productSuppliesRepo.findAll());
		// dump("Contract lines", contractLinesRepo.findAll());
	}

	@Test
	public void test_ImportUser_DeleteUser_and_ImportAnotherOneWithSameEmail()
	{
		final String email = "email1";

		final SyncBPartner syncBPartner = new SyncBPartner();
		syncBPartner.setUuid(newUUID());
		syncBPartner.setName("Test");
		syncBPartner.setSyncContracts(false);

		usersRepo.deleteAllInBatch(); // make sure the users table is really empty
		
		//
		// Import the BPartner with a User
		final SyncUser syncUser1 = new SyncUser();
		{
			syncUser1.setUuid(newUUID());
			syncUser1.setEmail(email);
			syncUser1.setDeleted(false);
			syncBPartner.getUsers().add(syncUser1);

			agentSync.syncBPartners(SyncBPartnersRequest.of(syncBPartner));

			final User user1 = usersRepo.findByUuid(syncUser1.getUuid());
			Assert.assertNotNull("User1 was imported", user1);
			Assert.assertEquals("User1 - BPartner", syncBPartner.getUuid(), user1.getBpartner().getUuid());
			Assert.assertEquals("User1 - Deleted", false, user1.isDeleted());
			Assert.assertEquals("User1 - Deleted_ID", null, user1.getDeleted_id());
			Assert.assertEquals("User1 - EMail", email, user1.getEmail());
			//
			Assert.assertEquals("Database shall contain only that user", Arrays.asList(user1), usersRepo.findAll());
		}

		//
		// Delete the first user and import another one with same email
		final SyncUser syncUser2 = new SyncUser();
		{
			syncUser1.setDeleted(true);

			syncUser2.setUuid(newUUID());
			syncUser2.setEmail(email);
			syncUser2.setDeleted(false);
			syncBPartner.getUsers().clear();
			syncBPartner.getUsers().add(syncUser1);
			syncBPartner.getUsers().add(syncUser2);

			agentSync.syncBPartners(SyncBPartnersRequest.of(syncBPartner));

			final User user1 = usersRepo.findByUuid(syncUser1.getUuid());
			Assert.assertNotNull("User1 was imported", user1);
			Assert.assertEquals("User1 - BPartner", syncBPartner.getUuid(), user1.getBpartner().getUuid());
			Assert.assertEquals("User1 - Deleted", true, user1.isDeleted());
			Assert.assertEquals("User1 - Deleted_ID", user1.getId(), user1.getDeleted_id());
			Assert.assertEquals("User1 - EMail", email, user1.getEmail());
			//
			final User user2 = usersRepo.findByUuid(syncUser2.getUuid());
			Assert.assertNotNull("User2 was imported", user2);
			Assert.assertEquals("User2 - BPartner", syncBPartner.getUuid(), user2.getBpartner().getUuid());
			Assert.assertEquals("User2 - Deleted", false, user2.isDeleted());
			Assert.assertEquals("User1 - Deleted_ID", null, user2.getDeleted_id());
			Assert.assertEquals("User2 - EMail", email, user1.getEmail());
			//
			assertThat("Database shall contain only that user", usersRepo.findAll(), containsInAnyOrder(user1, user2));
		}
	}

	/**
	 * If just a single BPartner with deleted=true is synched, then that bpartner and its users shall be deleted.
	 */
	@Test
	public void test_ImportUser_DeleteExistingBPartner()
	{
		//
		// set up the database
		final String bpartnerUUID = newUUID();
		final BPartner bpartner = new BPartner();
		bpartner.setUuid(bpartnerUUID);
		bpartner.setName("Test");
		bpartnerRepo.save(bpartner);

		assertThat(bpartnerRepo.findByUuid(bpartnerUUID), is(bpartner)); // just to gain confidence :-)

		final String userUUID = newUUID();
		User user = new User();
		user.setUuid(userUUID);
		user.setEmail("userEmail");
		user.setBpartner(bpartner);
		usersRepo.save(user);

		//
		// set up the delete-em-all sync request
		final SyncBPartner syncBPartner = new SyncBPartner();

		syncBPartner.setUuid(bpartnerUUID);
		syncBPartner.setName("Test");
		syncBPartner.setSyncContracts(false);
		syncBPartner.setDeleted(true);

		agentSync.syncBPartners(SyncBPartnersRequest.of(syncBPartner));

		final BPartner deletedBPartner = bpartnerRepo.findByUuid(bpartnerUUID);
		assertThat(deletedBPartner.isDeleted(), is(true));

		final User deletedUser = usersRepo.findByUuid(userUUID);
		assertThat(deletedUser.isDeleted(), is(true));
	}


	/**
	 * If just a single BPartner with deleted=true is synched, but there is no such local bpartner, it shall be not a problem.
	 */
	@Test
	public void test_ImportUser_DeleteNotExistingBPartner()
	{
		final String bpartnerUUID = newUUID();

		//
		// no need to set up the database since there shall be no such bpartner

		//
		// set up the delete-em-all sync request
		final SyncBPartner syncBPartner = new SyncBPartner();

		syncBPartner.setUuid(bpartnerUUID);
		syncBPartner.setName("Test");
		syncBPartner.setSyncContracts(false);
		syncBPartner.setDeleted(true);

		agentSync.syncBPartners(SyncBPartnersRequest.of(syncBPartner));
	}

	@Test
	public void test_ImportUser_DeleteNotExistingUser()
	{
		final String bpartnerUUID = newUUID();

		//
		// set up the database
		final BPartner bpartner = new BPartner();
		bpartner.setUuid(bpartnerUUID);
		bpartner.setName("Test");
		bpartnerRepo.save(bpartner);

		final String userUUID = newUUID();
		User user = new User();
		user.setUuid(userUUID);
		user.setEmail("userEmail");
		user.setBpartner(bpartner);
		usersRepo.save(user);

		//
		// set up the delete-em-all sync request
		final SyncBPartner syncBPartner = new SyncBPartner();

		syncBPartner.setUuid(bpartnerUUID);
		syncBPartner.setName("Test");
		syncBPartner.setSyncContracts(false);
		syncBPartner.setDeleted(false);

		final SyncUser syncUser = new SyncUser();
		syncUser.setUuid(newUUID());
		syncUser.setDeleted(true);
		syncUser.setEmail("nonExistingUserEmail");

		syncBPartner.getUsers().add(syncUser);

		agentSync.syncBPartners(SyncBPartnersRequest.of(syncBPartner));

		assertThat(bpartnerRepo.findByUuid(bpartnerUUID), is(bpartner)); // bpartner shall still exist
		assertThat(usersRepo.findByUuid(userUUID), is(user)); // other user shall still exist
	}

	private static final String newUUID()
	{
		return UUID.randomUUID().toString();
	}

	private final void dump(final String msg, final List<?> entries)
	{
		System.out.println(msg + ": ");
		if (entries == null || entries.isEmpty())
		{
			System.out.println("\n\t (no entries)");
		}
		else
		{
			for (final Object entry : entries)
			{
				System.out.println("\n\t" + entry);
			}
		}
	}

	private final void dumpAll(final String msg)
	{
		dump(msg + " - BPartners", bpartnerRepo.findAll());
		dump(msg + " - Users", usersRepo.findAll());
		dump(msg + " - Contracts", contractsRepo.findAll());
		dump(msg + " - Products", productsRepo.findAll());
	}
}
