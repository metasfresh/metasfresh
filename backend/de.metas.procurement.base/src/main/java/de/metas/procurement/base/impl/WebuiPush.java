package de.metas.procurement.base.impl;

import de.metas.common.procurement.sync.IAgentSync;
import de.metas.common.procurement.sync.protocol.SyncBPartner;
import de.metas.common.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.common.procurement.sync.protocol.SyncInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.SyncProduct;
import de.metas.common.procurement.sync.protocol.SyncProductsRequest;
import de.metas.common.procurement.sync.protocol.SyncProductsRequest.SyncProductsRequestBuilder;
import de.metas.common.procurement.sync.protocol.SyncRfQ;
import de.metas.common.procurement.sync.protocol.SyncRfQCloseEvent;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.logging.LogManager;
import de.metas.procurement.base.IAgentSyncBL;
import de.metas.procurement.base.IPMMProductDAO;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * de.metas.procurement.base
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
@ManagedResource(objectName = "de.metas.procurement:type=WebuiPush", description = "Allows to push data from metasfresh to the procurement webUI")
public class WebuiPush implements IWebuiPush
{
	private static final Logger logger = LogManager.getLogger(WebuiPush.class);

	/**
	 * Return an instance of {@link IAgentSync} that can be used to communicate with the procurement webUI.
	 * If no such client endpoint is available, return <code>null</code>.
	 */
	@NonNull
	private IAgentSync getAgentSync()
	{
		// if (true) return NullAgentSync.instance; // DEBUGGING: mock the agent sync

		return Services.get(IAgentSyncBL.class);
	}

	@Override
	public void pushBPartnerAndUsers(final I_C_BPartner bpartner)
	{
		final IAgentSync agent = getAgentSync();
		final SyncObjectsFactory syncFactory = SyncObjectsFactory.newFactory();
// @formatter:off
// FRESH-168: all users with IsMfProcurementUser='Y' are allowed to record a supply via procurement-webui,
// even if they don't have a contract/flatrate term.
//		if (!syncFactory.hasRunningContracts(bpartner))
//		{
//			return;
//		}
// @formatter:on

		final SyncBPartner syncBPartner = syncFactory.createSyncBPartnerWithoutContracts(bpartner);
		if (syncBPartner == null)
		{
			return;
		}

		final SyncBPartnersRequest syncBPartnersRequest = SyncBPartnersRequest.of(syncBPartner);
		agent.syncBPartners(syncBPartnersRequest);
	}

	@Override
	public void pushBPartnerForContract(final I_C_Flatrate_Term contract)
	{
		final IAgentSync agent = getAgentSync();

		final int bpartnerId = contract.getDropShip_BPartner_ID();

		final SyncObjectsFactory syncFactory = SyncObjectsFactory.newFactory();
		final SyncBPartner syncBPartner = syncFactory.createSyncBPartner(bpartnerId);
		if (syncBPartner == null)
		{
			return;
		}

		final SyncBPartnersRequest syncBPartnersRequest = SyncBPartnersRequest.of(syncBPartner);
		agent.syncBPartners(syncBPartnersRequest);
	}

	@Override
	public void pushBPartnerForContact(final I_AD_User contact)
	{
		final I_C_BPartner bpartner = contact.getC_BPartner();
		if (bpartner == null)
		{
			return;
		}
		pushBPartnerAndUsers(bpartner);
	}

	@Override
	@ManagedOperation(description = "Pushes/synchronizes all currently valid PMM_Products to the procurement webUI.")
	public void pushAllProducts()
	{
		final IAgentSync agent = getAgentSync();

		final IPMMProductDAO pmmProductDAO = Services.get(IPMMProductDAO.class);

		final IQueryBuilder<I_PMM_Product> allPMMProductsQuery = pmmProductDAO.retrievePMMProductsValidOnDateQuery(SystemTime.asTimestamp());
		final List<I_PMM_Product> allPMMProducts = allPMMProductsQuery.create().list();

		final SyncProductsRequestBuilder syncProductsRequest = SyncProductsRequest.builder();

		for (final I_PMM_Product pmmProduct : allPMMProducts)
		{
			final SyncProduct syncProduct = SyncObjectsFactory.newFactory().createSyncProduct(pmmProduct);
			syncProductsRequest.product(syncProduct);
		}
		agent.syncProducts(syncProductsRequest.build());
	}

	@Override
	@ManagedOperation(description="Pushes/synchronizes Businesspartners with their contracts to the procurement webUI.")
	public void pushAllBPartners()
	{
		final IAgentSync agent = getAgentSync();

		final List<SyncBPartner> allSyncBPartners = SyncObjectsFactory.newFactory().createAllSyncBPartners();


		final SyncBPartnersRequest request = SyncBPartnersRequest.builder().bpartners(allSyncBPartners).build();
		agent.syncBPartners(request);
	}

	@Override
	public void pushProduct(final I_PMM_Product pmmProduct)
	{
		final IAgentSync agent = getAgentSync();

		final SyncProduct syncProduct = SyncObjectsFactory.newFactory().createSyncProduct(pmmProduct);
		final SyncProductsRequest syncProductsRequest = SyncProductsRequest.of(syncProduct);

		agent.syncProducts(syncProductsRequest);
	}

	@Override
	@ManagedOperation
	public void pushAllInfoMessages()
	{
		final IAgentSync agent = getAgentSync();

		final String infoMessage = SyncObjectsFactory.newFactory().createSyncInfoMessage();
		agent.syncInfoMessage(SyncInfoMessageRequest.of(infoMessage));
	}

	@Override
	public void pushRfQs(@Nullable final List<SyncRfQ> syncRfqs)
	{
		if(syncRfqs == null || syncRfqs.isEmpty())
		{
			return;
		}

		final IAgentSync agent = getAgentSync();
		agent.syncRfQs(syncRfqs);
	}

	@Override
	public void pushRfQCloseEvents(@Nullable final List<SyncRfQCloseEvent> syncRfQCloseEvents)
	{
		if(syncRfQCloseEvents == null || syncRfQCloseEvents.isEmpty())
		{
			return;
		}

		final IAgentSync agent = getAgentSync();

		agent.closeRfQs(syncRfQCloseEvents);
	}
}
