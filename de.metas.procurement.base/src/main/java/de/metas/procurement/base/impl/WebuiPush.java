package de.metas.procurement.base.impl;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.logging.LogManager;
import de.metas.procurement.base.IAgentSyncBL;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductsRequest;

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

public class WebuiPush implements IWebuiPush
{
	private static final Logger logger = LogManager.getLogger(WebuiPush.class);

	/**
	 * Return an instance of {@link IAgentSync} that can be used to communicate with the procurement webUI.
	 * If no such client endpoint is available, return <code>null</code>.
	 *
	 * @return
	 */
	private IAgentSync getAgentSyncOrNull()
	{
		//if (true) return NullAgentSync.instance; // DEBUGGING: mock the agent sync

		final IAgentSyncBL agentSyncBL = Services.get(IAgentSyncBL.class);
		if (agentSyncBL == null)
		{
			new AdempiereException("Unable to obtain IAgentSyncBL client endpoint proxy. Please check its AD_JAXRS_Endpoint config")
					.throwOrLogSevere(Services.get(IDeveloperModeBL.class).isEnabled(), logger);
		}
		return agentSyncBL;
	}

	@Override
	public boolean checkAvailable()
	{
		return getAgentSyncOrNull() != null;
	}

	@Override
	public void pushBPartnerWithoutContracts(final I_C_BPartner bpartner)
	{
		final IAgentSync agent = getAgentSyncOrNull();
		if (agent == null)
		{
			return;
		}

		final SyncObjectsFactory syncFactory = SyncObjectsFactory.newFactory();
		if (!syncFactory.hasRunningContracts(bpartner))
		{
			return;
		}

		// Validate
		if (!bpartner.isVendor())
		{
			throw new AdempiereException("@C_BPartner_ID@ @IsVendor@=@N@: " + bpartner.getValue() + "_" + bpartner.getName());
		}

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
		final IAgentSync agent = getAgentSyncOrNull();
		if (agent == null)
		{
			return;
		}

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
	public void pushBPartnerForContact(I_AD_User contact)
	{
		final I_C_BPartner bpartner = contact.getC_BPartner();
		if (bpartner == null)
		{
			return;
		}

		pushBPartnerWithoutContracts(bpartner);
	}

	@Override
	public void pushProduct(final I_PMM_Product pmmProduct)
	{
		final IAgentSync agent = getAgentSyncOrNull();
		if (agent == null)
		{
			return;
		}

		final SyncProduct syncProduct = SyncObjectsFactory.newFactory().createSyncProduct(pmmProduct);
		final SyncProductsRequest syncProductsRequest = SyncProductsRequest.of(syncProduct);

		agent.syncProducts(syncProductsRequest);
	}
}
