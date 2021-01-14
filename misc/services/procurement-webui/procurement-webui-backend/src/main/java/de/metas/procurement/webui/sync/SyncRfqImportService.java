package de.metas.procurement.webui.sync;

import com.google.common.base.MoreObjects;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.dto.SyncProductSupply;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQCloseEvent;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.repository.ContractLineRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.service.IRfQService;
import de.metas.procurement.webui.service.impl.ProductSuppliesService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/*
 * #%L
 * metasfresh-procurement-webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@Transactional
class SyncRfqImportService extends AbstractSyncImportService
{
	private final IRfQService rfqService;
	private final SyncProductImportService productImportService;
	private final BPartnerRepository bpartnerRepo;
	private final ContractLineRepository contractLineRepo;
	private final ProductSuppliesService productSuppliesService;
	// @Autowired @Lazy private MFEventBus applicationEventBus;

	public SyncRfqImportService(
			@NonNull final IRfQService rfqService,
			@NonNull final SyncProductImportService productImportService,
			@NonNull final BPartnerRepository bpartnerRepo,
			@NonNull final ContractLineRepository contractLineRepo,
			@NonNull final ProductSuppliesService productSuppliesService)
	{
		this.rfqService = rfqService;
		this.productImportService = productImportService;
		this.bpartnerRepo = bpartnerRepo;
		this.contractLineRepo = contractLineRepo;
		this.productSuppliesService = productSuppliesService;
	}

	public void importRfQs(final BPartner bpartner, final List<SyncRfQ> syncRfQs)
	{
		for (final SyncRfQ syncRfQ : syncRfQs)
		{
			importRfQ(bpartner, syncRfQ);
		}
	}

	public void importRfQ(final SyncRfQ syncRfQ)
	{
		importRfQ(null, syncRfQ);
	}

	public void importRfQ(@Nullable final BPartner bpartner, @NonNull final SyncRfQ syncRfQ)
	{
		final String rfq_uuid = syncRfQ.getUuid();
		Rfq rfq = rfqService.getRfqByUUID(rfq_uuid);
		if (rfq == null)
		{
			final BPartner bpartnerEffective = bpartner != null
					? bpartner
					: bpartnerRepo.findByUuid(syncRfQ.getBpartner_uuid());

			rfq = Rfq.builder()
					.bpartner(bpartnerEffective)
					.build();
			rfq.setUuid(rfq_uuid);
		}

		rfq.setDeleted(false);

		//
		// Dates
		rfq.setDateStart(syncRfQ.getDateStart());
		rfq.setDateEnd(syncRfQ.getDateEnd());
		rfq.setDateClose(syncRfQ.getDateClose());

		//
		// Product
		final SyncProduct syncProduct = syncRfQ.getProduct();
		final Product product = productImportService.importProduct(syncProduct)
				.orElseThrow(() -> new RuntimeException("Deleted product cannot be used in " + syncRfQ));
		rfq.setProduct_id(product.getId());

		//
		// Quantity
		rfq.setQtyRequested(syncRfQ.getQtyRequested());
		rfq.setQtyCUInfo(syncRfQ.getQtyCUInfo());

		//
		// Price & currency
		rfq.setCurrencyCode(syncRfQ.getCurrencyCode());

		//
		// Save & return
		rfqService.saveRecursively(rfq);
		logger.debug("Imported: {} -> {}", syncRfQ, rfq);

		// applicationEventBus.post(RfqChangedEvent.of(rfq));

	}

	public void importRfQCloseEvent(final SyncRfQCloseEvent syncRfQCloseEvent)
	{
		final String rfq_uuid = syncRfQCloseEvent.getRfq_uuid();
		final Rfq rfq = rfqService.getRfqByUUID(rfq_uuid);
		if (rfq == null)
		{
			logger.warn("No RfQ found for {}. Skip importing the event.", syncRfQCloseEvent);
			return;
		}

		if (rfq.isClosed() && rfq.isWinnerKnown())
		{
			logger.warn("RfQ {} is already closed when we got {}. Skip importing the event.", rfq, syncRfQCloseEvent);
			return;
		}

		//
		// Update the RfQ
		rfq.closeIt();
		if (syncRfQCloseEvent.isWinnerKnown())
		{
			rfq.setWinnerKnown(true);
			rfq.setWinner(syncRfQCloseEvent.isWinner());
		}

		rfqService.saveRecursively(rfq);
		// applicationEventBus.post(RfqChangedEvent.of(rfq));

		if (syncRfQCloseEvent.isWinnerKnown() && syncRfQCloseEvent.isWinner())
		{
			final List<SyncProductSupply> plannedSupplies = syncRfQCloseEvent.getPlannedSupplies();
			if (plannedSupplies != null && !plannedSupplies.isEmpty())
			{
				final BPartner bpartner = rfq.getBpartner();

				for (final SyncProductSupply syncProductSupply : plannedSupplies)
				{
					importPlannedProductSupply(syncProductSupply, bpartner);
				}
			}
		}
	}

	private void importPlannedProductSupply(final SyncProductSupply syncProductSupply, final BPartner bpartner)
	{
		final String contractLine_uuid = syncProductSupply.getContractLine_uuid();
		final ContractLine contractLine = contractLineRepo.findByUuid(contractLine_uuid);

		productSuppliesService.importPlanningSupply(
				IProductSuppliesService.ImportPlanningSupplyRequest.builder()
						.bpartner(bpartner)
						.contractLine(contractLine)
						.product_uuid(syncProductSupply.getProduct_uuid())
						.date(syncProductSupply.getDay())
						.qty(MoreObjects.firstNonNull(syncProductSupply.getQty(), BigDecimal.ZERO))
						.build());

		// applicationEventBus.post(ProductSupplyChangedEvent.of(productSupply));
	}
}
