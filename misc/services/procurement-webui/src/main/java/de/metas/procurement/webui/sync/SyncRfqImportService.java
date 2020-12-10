package de.metas.procurement.webui.sync;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.dto.SyncProductSupply;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQCloseEvent;
import de.metas.procurement.webui.event.MFEventBus;
import de.metas.procurement.webui.event.ProductSupplyChangedEvent;
import de.metas.procurement.webui.event.RfqChangedEvent;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.repository.ContractLineRepository;
import de.metas.procurement.webui.repository.ProductRepository;
import de.metas.procurement.webui.repository.ProductSupplyRepository;
import de.metas.procurement.webui.repository.RfqRepository;
import de.metas.procurement.webui.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
public class SyncRfqImportService extends AbstractSyncImportService
{
	@Autowired
	private RfqRepository rfqRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	@Lazy
	private SyncProductImportService productImportService;
	@Autowired
	@Lazy
	private BPartnerRepository bpartnerRepo;
	@Autowired
	@Lazy
	private ContractLineRepository contractLineRepo;
	@Autowired
	@Lazy
	private ProductSupplyRepository productSupplyRepo;
	
	@Autowired
	@Lazy
	private MFEventBus applicationEventBus;

	public void importRfQs(final BPartner bpartner, final List<SyncRfQ> syncRfQs)
	{
		for (final SyncRfQ syncRfQ : syncRfQs)
		{
			importRfQ(bpartner, syncRfQ);
		}
	}

	public Rfq importRfQ(final SyncRfQ syncRfQ)
	{
		final BPartner bpartner = null;
		return importRfQ(bpartner, syncRfQ);
	}

	public Rfq importRfQ(BPartner bpartner, final SyncRfQ syncRfQ)
	{
		final String rfq_uuid = syncRfQ.getUuid();
		Rfq rfq = rfqRepo.findByUuid(rfq_uuid);
		if (rfq == null)
		{
			rfq = new Rfq();
			rfq.setUuid(rfq_uuid);

			if (bpartner == null)
			{
				bpartner = bpartnerRepo.findByUuid(syncRfQ.getBpartner_uuid());
			}
			rfq.setBpartner(bpartner);
		}

		rfq.setDeleted(false);

		//
		// Dates
		rfq.setDateStart(DateUtils.truncToDay(syncRfQ.getDateStart()));
		rfq.setDateEnd(DateUtils.truncToDay(syncRfQ.getDateEnd()));
		rfq.setDateClose(DateUtils.truncToDay(syncRfQ.getDateClose()));

		//
		// Product
		final SyncProduct syncProduct = syncRfQ.getProduct();
		final Product product = productImportService.importProduct(syncProduct);
		if (product == null)
		{
			throw new RuntimeException("No product found for "+syncProduct);
		}
		rfq.setProduct(product);

		//
		// Quantity
		rfq.setQtyRequested(syncRfQ.getQtyRequested());
		rfq.setQtyCUInfo(syncRfQ.getQtyCUInfo());
		
		//
		// Price & currency
		rfq.setCurrencyCode(syncRfQ.getCurrencyCode());
		
		//
		// Save & return
		rfqRepo.save(rfq);
		logger.debug("Imported: {} -> {}", syncRfQ, rfq);

		applicationEventBus.post(RfqChangedEvent.of(rfq));
		
		return rfq;
	}

	public void importRfQCloseEvent(final SyncRfQCloseEvent syncRfQCloseEvent)
	{
		final String rfq_uuid = syncRfQCloseEvent.getRfq_uuid();
		final Rfq rfq = rfqRepo.findByUuid(rfq_uuid);
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
		rfq.setClosed(true);
		if (syncRfQCloseEvent.isWinnerKnown())
		{
			rfq.setWinnerKnown(true);
			rfq.setWinner(syncRfQCloseEvent.isWinner());
		}
		
		rfqRepo.save(rfq);
		applicationEventBus.post(RfqChangedEvent.of(rfq));

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
		final String product_uuid = syncProductSupply.getProduct_uuid();
		final Product product = productRepo.findByUuid(product_uuid);
		//
		final String contractLine_uuid = syncProductSupply.getContractLine_uuid();
		final ContractLine contractLine = contractLineRepo.findByUuid(contractLine_uuid);
		//
		final Date day = DateUtils.truncToDay(syncProductSupply.getDay());
		final BigDecimal qty = Objects.firstNonNull(syncProductSupply.getQty(), BigDecimal.ZERO);
		
		ProductSupply productSupply = productSupplyRepo.findByProductAndBpartnerAndDay(product, bpartner, day);
		final boolean isNew;
		if (productSupply == null)
		{
			isNew = true;
			productSupply = ProductSupply.build(bpartner, product, contractLine, day);
		}
		else
		{
			isNew = false;
		}
		
		//
		// Contract line
		if(!isNew)
		{
			final ContractLine contractLineOld = productSupply.getContractLine();
			if (!Objects.equal(contractLine, contractLineOld))
			{
				logger.warn("Changing contract line {}->{} for {} because of planning supply: {}", contractLineOld, contractLine, productSupply, syncProductSupply);
			}
			productSupply.setContractLine(contractLine);
		}
		
		//
		// Quantity
		if(!isNew)
		{
			final BigDecimal qtyOld = productSupply.getQty();
			if (qty.compareTo(qtyOld) != 0)
			{
				logger.warn("Changing quantity {}->{} for {} because of planning supply: {}", qtyOld, qty, productSupply, syncProductSupply);
			}
		}
		productSupply.setQty(qty);

		//
		// Save the product supply
		productSupplyRepo.save(productSupply);
		
		applicationEventBus.post(ProductSupplyChangedEvent.of(productSupply));
	}
}
