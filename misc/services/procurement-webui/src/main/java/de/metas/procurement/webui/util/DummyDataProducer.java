package de.metas.procurement.webui.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.procurement.sync.protocol.SyncContract;
import de.metas.procurement.sync.protocol.SyncContractLine;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductsRequest;
import de.metas.procurement.sync.protocol.SyncRfQ;
import de.metas.procurement.sync.protocol.SyncUser;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Contract;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.repository.ContractRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;

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

@Service
public class DummyDataProducer
{
	@Autowired
	private BPartnerRepository bpartnersRepo;
	@Autowired
	private ContractRepository contractsRepo;
	@Autowired
	private IProductSuppliesService productSuppliesService;

	private final Date contractDateFrom = DateUtils.toDayDate(2015, 04, 01);
	private final Date contractDateTo = DateUtils.toDayDate(2016, 03, 31);

	private List<String> languages = Arrays.asList(
			"en_US"
			, "de_DE"
			, "de_CH"
			);

	private final List<String> productNames = Arrays.asList(
			"Batavia"
			, "Batavia rot Multileaf Industrie"
			, "Tomaten"
			, "Tomaten Roma"
			, "Rispentomaten Cherry"
			, "Auberginen"
			, "Trauben blau"
			, "Erdbeeren 250g"
			, "Erdbeeren 500g"
			, "Eisbergsalat"
			, "Knollensellerie"
			, "Karotten ungewaschen"
			//
			);

	@Value("${server.port}")
	private int serverPort;

	private SyncBPartnersRequest _syncBPartnersRequest;
	private SyncProductsRequest _syncProductsRequest;

	public void createDummyData()
	{
		createDummyData(serverPort);
	}

	public void createDummyData(final int serverPort)
	{
		final SyncBPartnersRequest request = getSyncBPartnersRequest();

		final int serverPortToUse = serverPort > 0 ? serverPort : this.serverPort;
		if (serverPortToUse <= 0)
		{
			throw new RuntimeException("Server port could not be determined");
		}
		final IAgentSync client = JAXRSClientFactory.create("http://localhost:" + serverPortToUse + "/rest", IAgentSync.class);
		WebClient.client(client).type(MediaType.APPLICATION_JSON_TYPE);
		client.syncBPartners(request);

		createDummyProductSupplies();
	}

	public SyncBPartnersRequest getSyncBPartnersRequest()
	{
		if (_syncBPartnersRequest == null)
		{
			_syncBPartnersRequest = createSyncBPartnersRequest();
		}
		return _syncBPartnersRequest;
	}

	private SyncBPartnersRequest createSyncBPartnersRequest()
	{
		final SyncBPartnersRequest request = new SyncBPartnersRequest();

		//
		// BPartner
		{
			final SyncBPartner syncBPartner = new SyncBPartner();
			syncBPartner.setUuid(randomUUID());
			syncBPartner.setName("test-bp01");
			syncBPartner.setUsers(Arrays.asList(
					createSyncUser("test", "q", null)
					, createSyncUser("teo.sarca@gmail.com", "q", null)
					, createSyncUser("test_en", "q", "en_US")
					, createSyncUser("test_de", "q", "de_DE")
					));

			//
			// Contract
			{
				syncBPartner.setSyncContracts(true);
				
				final SyncContract syncContract = new SyncContract();
				syncContract.setUuid(randomUUID());
				syncContract.setDateFrom(contractDateFrom);
				syncContract.setDateTo(contractDateTo);

				final SyncProductsRequest syncProductsRequest = getSyncProductsRequest();
				for (final SyncProduct syncProduct : syncProductsRequest.getProducts().subList(0, 6))
				{
					final SyncContractLine syncContractLine = new SyncContractLine();
					syncContractLine.setUuid(randomUUID());
					syncContractLine.setProduct(syncProduct);

					syncContract.getContractLines().add(syncContractLine);
				}

				syncBPartner.getContracts().add(syncContract);
			}
			
			//
			// RfQ
			final List<SyncProduct> syncProducts = getSyncProductsRequest().getProducts();
			for (int rfqNo = 0; rfqNo < 4 && rfqNo < syncProducts.size(); rfqNo++)
			{
				final Date dateStart = DateUtils.addMonths(DateUtils.truncToMonth(new Date()), 2);
				final Date dateEnd = DateUtils.addDays(dateStart, 14);
				final Date dateClose = DateUtils.addDays(dateStart, -10);
				
				final SyncRfQ syncRfQ = new SyncRfQ();
				syncRfQ.setUuid(randomUUID());
				
				syncRfQ.setDateStart(dateStart);
				syncRfQ.setDateEnd(dateEnd);
				
				syncRfQ.setBpartner_uuid(syncBPartner.getUuid());
				
				syncRfQ.setDateClose(dateClose);

				final SyncProduct syncProduct = syncProducts.get(rfqNo);
				syncRfQ.setProduct(syncProduct);

				syncRfQ.setQtyRequested(BigDecimal.valueOf(100));
				syncRfQ.setQtyCUInfo("Kg");
				syncRfQ.setCurrencyCode("CHF");
				
				syncBPartner.getRfqs().add(syncRfQ);
			}

			request.getBpartners().add(syncBPartner);
		}

		return request;
	}

	public SyncProductsRequest getSyncProductsRequest()
	{
		if (_syncProductsRequest == null)
		{
			_syncProductsRequest = createSyncProductsRequest();
		}
		return _syncProductsRequest;
	}

	private SyncProductsRequest createSyncProductsRequest()
	{
		final SyncProductsRequest request = new SyncProductsRequest();

		//
		// Products
		for (final String productName : productNames)
		{
			final SyncProduct syncProduct = createSyncProduct(productName, "10x1 Stk");
			request.getProducts().add(syncProduct);
		}

		return request;
	}

	public SyncUser createSyncUser(final String email, final String password, final String language)
	{
		final SyncUser syncUser = new SyncUser();
		syncUser.setUuid(randomUUID());
		syncUser.setEmail(email);
		syncUser.setPassword(password);
		syncUser.setLanguage(language);
		return syncUser;
	}

	public SyncProduct createSyncProduct(final String name, final String packingInfo)
	{
		final SyncProduct product = new SyncProduct();
		product.setUuid(randomUUID());
		product.setName(name);
		product.setPackingInfo(packingInfo);
		product.setShared(true);

		for (final String language : languages)
		{
			product.getNamesTrl().put(language, name + " " + language);
		}

		return product;
	}

	private static final String randomUUID()
	{
		return UUID.randomUUID().toString();
	}

	@Transactional
	private void createDummyProductSupplies()
	{
		for (final BPartner bpartner : bpartnersRepo.findAll())
		{
			for (final Contract contract : contractsRepo.findByBpartnerAndDeletedFalse(bpartner))
			{
				final List<ContractLine> contractLines = contract.getContractLines();
				if (contractLines.isEmpty())
				{
					continue;
				}

				final ContractLine contractLine = contractLines.get(0);
				final Product product = contractLine.getProduct();

				productSuppliesService.reportSupply(bpartner, product, contractLine
						, DateUtils.getToday()
						, new BigDecimal("10") // today
						);
				productSuppliesService.reportSupply(bpartner, product, contractLine
						, DateUtils.addDays(DateUtils.getToday(), 1) // tomorrow
						, new BigDecimal("3")
						);
			}
		}
	}
}
