package de.metas.material.planning;

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

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.material.planning.exception.NoPlantForWarehouseException;

public interface IProductPlanningDAO extends ISingletonService
{

	/**
	 * Find best matching product planning.
	 * 
	 * @param orgId
	 * @param warehouseId
	 * @param plantId
	 * @param productId
	 * @param attributeSetInstanceId
	 * @return matching product planning or null
	 */
	I_PP_Product_Planning find(int orgId, int warehouseId, int plantId, int productId, int attributeSetInstanceId);

	/**
	 * Search product plannings to find out which is the plant({@link I_S_Resource}) for given Org/Warehouse/Product.
	 *
	 * @param adOrgId
	 * @param warehouseId
	 * @param productId
	 * @return plant
	 * @throws NoPlantForWarehouseException if there was no plant found or if there was more then one plant found.
	 */
	I_S_Resource findPlant(final int adOrgId, final I_M_Warehouse warehouse, final int productId, int attributeSetInstanceId);

	/**
	 * Retrieve all warehouses which are directly assigned to our Org and Plant.
	 *
	 * @param ctx
	 * @param org
	 * @param plant
	 * @return
	 */
	List<I_M_Warehouse> retrieveWarehousesForPlant(Properties ctx, I_AD_Org org, I_S_Resource plant);
}
