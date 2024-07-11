package de.metas.material.planning.ddorder;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;

import de.metas.util.ISingletonService;

public interface IDistributionNetworkDAO extends ISingletonService
{
	I_DD_NetworkDistribution getById(@NonNull DistributionNetworkId id);

	/**
	 * Retrieve all (including inactive ones) {@link I_DD_NetworkDistributionLine}s for given {@link I_DD_NetworkDistribution}.
	 *
	 * @return distribution network lines, ordered by <code>PriorityNo</code> and <code>M_Shipper_ID</code>
	 */
	List<I_DD_NetworkDistributionLine> retrieveAllNetworkLines(I_DD_NetworkDistribution distributionNetwork);

	/**
	 * Retrieve {@link I_DD_NetworkDistributionLine}s for given {@link I_DD_NetworkDistribution} which match the given target warehouse (<code>M_Warehouse_ID</code>).
	 *
	 * @return distribution network lines
	 */
	List<I_DD_NetworkDistributionLine> retrieveNetworkLinesByTargetWarehouse(I_DD_NetworkDistribution distributionNetwork, WarehouseId targetWarehouseId);

	/**
	 * Retrieve {@link I_DD_NetworkDistributionLine}s for given {@link I_DD_NetworkDistribution} which match the given source warehouse (<code>M_WarehouseSource_ID</code>).
	 *
	 * @return distribution network lines
	 */
	List<I_DD_NetworkDistributionLine> retrieveNetworkLinesBySourceWarehouse(I_DD_NetworkDistribution distributionNetwork, int sourceWarehouseId);

	/**
	 * Check the {@link I_DD_NetworkDistributionLine}s and collect the {@link I_DD_NetworkDistributionLine#COLUMN_M_WarehouseSource_ID}s for those who have the given target warehouse and which have
	 * {@link I_DD_NetworkDistributionLine#COLUMN_IsKeepTargetPlant} set.
	 */
	List<I_M_Warehouse> retrieveWarehousesInSamePlantAs(Properties ctx, int targetWarehouseId);
}
