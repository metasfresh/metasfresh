package de.metas.procurement.base.impl;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.IAgentSyncBL;
import de.metas.procurement.base.ISyncBL;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.util.UUIDs;

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

public class SyncBL implements ISyncBL
{
	private static final CLogger logger = CLogger.getCLogger(SyncBL.class);

	@Override
	public SyncProduct createSyncProduct(final String productName, final I_PMM_Product pmmProduct)
	{
		final I_M_HU_PI_Item_Product piip = pmmProduct.getM_HU_PI_Item_Product();

		final SyncProduct syncProduct = new SyncProduct();

		final boolean valid = pmmProduct.getM_Warehouse_ID() > 0
				&& pmmProduct.getM_Product_ID() > 0
				&& pmmProduct.getM_HU_PI_Item_Product_ID() > 0;

		syncProduct.setUuid(UUIDs.fromIdAsString(pmmProduct.getPMM_Product_ID()));
		syncProduct.setName(productName);
		syncProduct.setPackingInfo(piip == null ? null : piip.getDescription());

		syncProduct.setShared(pmmProduct.getC_BPartner_ID() <= 0); // share, unless it is assigned to a particular BPartner
		syncProduct.setDeleted(!valid);

		return syncProduct;
	}

	@Override
	public IAgentSync getAgentSyncOrNull()
	{
		final IAgentSyncBL agentSyncBL = Services.get(IAgentSyncBL.class);
		if (agentSyncBL == null)
		{
			new AdempiereException("Unable to obtain IAgentSyncBL client endpoint proxy. Please check its AD_JAXRS_Endpoint config")
					.throwOrLogSevere(Services.get(IDeveloperModeBL.class).isEnabled(), logger);
		}
		return agentSyncBL;
	}
}
