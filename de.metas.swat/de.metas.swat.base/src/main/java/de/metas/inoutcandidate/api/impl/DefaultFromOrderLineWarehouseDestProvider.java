package de.metas.inoutcandidate.api.impl;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.api.IProductPlanningDAO;
import org.eevolution.drp.api.IDistributionNetworkDAO;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.inoutcandidate.spi.IReceiptScheduleWarehouseDestProvider;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Default destination warehouse provider.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */final class DefaultFromOrderLineWarehouseDestProvider implements IReceiptScheduleWarehouseDestProvider
{
	public static final transient DefaultFromOrderLineWarehouseDestProvider instance = new DefaultFromOrderLineWarehouseDestProvider();

	private DefaultFromOrderLineWarehouseDestProvider()
	{
		super();
	}

	@Override
	public I_M_Warehouse getWarehouseDest(final IContext context)
	{
		//
		// Try to retrieve destination warehouse from planning
		// see: http://dewiki908/mediawiki/index.php/07058_Destination_Warehouse_Wareneingang_%28102083181965%29#Development_infrastructure
		final I_M_Warehouse distributionNetworkWarehouseDestination = getDistributionNetworkWarehouseDestination(context);
		if (distributionNetworkWarehouseDestination != null)
		{
			return distributionNetworkWarehouseDestination;
		}

		//
		// Fallback if no planning destination warehouse was found
		// see: http://dewiki908/mediawiki/index.php/05940_Wareneingang_Lagerumbuchung
		final I_M_Locator locator = context.getM_Product().getM_Locator();
		if (locator != null && locator.getM_Locator_ID() > 0)
		{
			return locator.getM_Warehouse();
		}

		//
		// We don't have anything to match
		return null;
	}

	/**
	 * @param context
	 * @return first planning-distribution (i.e. with lowest <code>PriorityNo</code>) network-warehouse destination found for product (no warehouse / source filter).
	 */
	private I_M_Warehouse getDistributionNetworkWarehouseDestination(final IContext context)
	{
		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(
				context.getCtx() //
				, context.getAD_Org_ID() //
				, 0  // M_Warehouse_ID
				, 0  // S_Resource_ID
				, context.getM_Product_ID() // M_Product_ID
				, ITrx.TRXNAME_None //
		);

		if (productPlanning == null)
		{
			return null;
		}

		final I_DD_NetworkDistribution distributionNetwork = productPlanning.getDD_NetworkDistribution();
		if (distributionNetwork == null)
		{
			return null;
		}

		final IDistributionNetworkDAO distributionNetworkDAO = Services.get(IDistributionNetworkDAO.class);
		final List<I_DD_NetworkDistributionLine> distributionNetworkLines = distributionNetworkDAO
				.retrieveNetworkLinesBySourceWarehouse(distributionNetwork, context.getM_Warehouse_ID());

		if (distributionNetworkLines.isEmpty())
		{
			return null;
		}

		// the lines are ordered by PriorityNo, M_Shipper_ID
		final I_DD_NetworkDistributionLine firstFoundDistributionNetworkLine = distributionNetworkLines.get(0);
		return firstFoundDistributionNetworkLine.getM_Warehouse();
	}

}
