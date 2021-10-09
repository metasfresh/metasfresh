package de.metas.procurement.webui.util;

import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.dto.SyncBPartner.SyncBPartnerBuilder;
import de.metas.common.procurement.sync.protocol.dto.SyncContract;
import de.metas.common.procurement.sync.protocol.dto.SyncContract.SyncContractBuilder;
import de.metas.common.procurement.sync.protocol.dto.SyncContractLine;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncUser;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest.PutBPartnersRequestBuilder;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest.PutProductsRequestBuilder;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Contract;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.repository.ContractRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.sync.ReceiverFromMetasfreshHandler;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
	private final BPartnerRepository bpartnersRepo;
	private final ContractRepository contractsRepo;
	private final IProductSuppliesService productSuppliesService;
	private final ReceiverFromMetasfreshHandler receiverFromMetasfreshHandler;

	private final LocalDate contractDateFrom = LocalDate.of(2015, 4, 1);
	private final LocalDate contractDateTo = LocalDate.of(2016, 3, 31);

	private final List<String> languages = Arrays.asList(
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

	private PutBPartnersRequest _syncBPartnersRequest;
	private PutProductsRequest _syncProductsRequest;

	public DummyDataProducer(
			@NonNull final BPartnerRepository bpartnersRepo,
			@NonNull final ContractRepository contractsRepo,
			@NonNull final IProductSuppliesService productSuppliesService,
			@NonNull final ReceiverFromMetasfreshHandler receiverFromMetasfreshHandler)
	{
		this.bpartnersRepo = bpartnersRepo;
		this.contractsRepo = contractsRepo;
		this.productSuppliesService = productSuppliesService;
		this.receiverFromMetasfreshHandler = receiverFromMetasfreshHandler;
	}

	public void createDummyData()
	{
		final PutBPartnersRequest request = getSyncBPartnersRequest();
		receiverFromMetasfreshHandler.handlePutBPartnersRequest(request);

		createDummyProductSupplies();
	}

	public PutBPartnersRequest getSyncBPartnersRequest()
	{
		if (_syncBPartnersRequest == null)
		{
			_syncBPartnersRequest = createSyncBPartnersRequest();
		}
		return _syncBPartnersRequest;
	}

	private PutBPartnersRequest createSyncBPartnersRequest()
	{
		final PutBPartnersRequestBuilder request = PutBPartnersRequest.builder();

		//
		// BPartner
		{
			final String syncBPartnerUUID = randomUUID();
			final SyncBPartnerBuilder syncBPartner = SyncBPartner.builder()
					.uuid(syncBPartnerUUID)
					.name("test-bp01")
					.users(Arrays.asList(
							createSyncUser("test", "q", null)
							, createSyncUser("teo.sarca@gmail.com", "q", null)
							, createSyncUser("test_en", "q", "en_US")
							, createSyncUser("test_de", "q", "de_DE")
					));

			//
			// Contract
			{
				syncBPartner.syncContracts(true);

				final SyncContractBuilder syncContract = SyncContract.builder()
						.uuid(randomUUID())
						.dateFrom(contractDateFrom)
						.dateTo(contractDateTo);

				final PutProductsRequest syncProductsRequest = getSyncProductsRequest();
				for (final SyncProduct syncProduct : syncProductsRequest.getProducts().subList(0, 6))
				{
					final SyncContractLine syncContractLine = SyncContractLine.builder()
							.uuid(randomUUID())
							.product(syncProduct).build();

					syncContract.contractLine(syncContractLine);
				}

				syncBPartner.contract(syncContract.build());
			}

			//
			// RfQ
			final List<SyncProduct> syncProducts = getSyncProductsRequest().getProducts();
			for (int rfqNo = 0; rfqNo < 4 && rfqNo < syncProducts.size(); rfqNo++)
			{
				final LocalDate dateStart = LocalDate.now().withDayOfMonth(1).plusMonths(2);
				final LocalDate dateEnd = dateStart.plusDays(14);
				final LocalDate dateClose = dateStart.plusDays(-10);

				final SyncRfQ.SyncRfQBuilder syncRfQ = SyncRfQ.builder()
						.uuid(randomUUID())

						.dateStart(dateStart)
						.dateEnd(dateEnd)

						.bpartner_uuid(syncBPartnerUUID)

						.dateClose(dateClose);

				final SyncProduct syncProduct = syncProducts.get(rfqNo);
				syncRfQ.product(syncProduct)
						.qtyRequested(BigDecimal.valueOf(100))
						.qtyCUInfo("Kg")
						.currencyCode("CHF");

				syncBPartner.rfq(syncRfQ.build());
			}

			request.bpartner(syncBPartner.build());
		}

		return request.build();
	}

	public PutProductsRequest getSyncProductsRequest()
	{
		if (_syncProductsRequest == null)
		{
			_syncProductsRequest = createSyncProductsRequest();
		}
		return _syncProductsRequest;
	}

	private PutProductsRequest createSyncProductsRequest()
	{
		final PutProductsRequestBuilder request = PutProductsRequest.builder();

		//
		// Products
		for (final String productName : productNames)
		{
			final SyncProduct syncProduct = createSyncProduct(productName, "10x1 Stk");
			request.product(syncProduct);
		}

		return request.build();
	}

	public SyncUser createSyncUser(final String email, final String password, @Nullable final String language)
	{
		return SyncUser.builder()
				.uuid(randomUUID())
				.email(email)
				.password(password)
				.language(language)
				.build();
	}

	public SyncProduct createSyncProduct(final String name, final String packingInfo)
	{
		final SyncProduct.SyncProductBuilder product = SyncProduct.builder()
				.uuid(randomUUID())
				.name(name)
				.packingInfo(packingInfo)
				.shared(true);

		for (final String language : languages)
		{
			product.nameTrl(language, name + " " + language);
		}

		return product.build();
	}

	private static String randomUUID()
	{
		return UUID.randomUUID().toString();
	}

	@Transactional
	void createDummyProductSupplies()
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

				productSuppliesService.reportSupply(IProductSuppliesService.ReportDailySupplyRequest.builder()
						.bpartner(bpartner)
						.contractLine(contractLine)
						.productId(product.getId())
						.date(LocalDate.now()) // today
						.qty(new BigDecimal("10"))
						.qtyConfirmedByUser(true)
						.build());
				productSuppliesService.reportSupply(IProductSuppliesService.ReportDailySupplyRequest.builder()
						.bpartner(bpartner)
						.contractLine(contractLine)
						.productId(product.getId())
						.date(LocalDate.now().plusDays(1)) // tomorrow
						.qty(new BigDecimal("3"))
						.qtyConfirmedByUser(true)
						.build());
			}
		}
	}
}
