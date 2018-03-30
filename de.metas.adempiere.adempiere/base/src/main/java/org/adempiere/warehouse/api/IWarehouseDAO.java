package org.adempiere.warehouse.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import org.adempiere.warehouse.model.WarehousePickingGroup;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

public interface IWarehouseDAO extends ISingletonService
{
	/**
	 * Checks if the warehouse is covered in M_Warehouse_Routing as specific to one or more doc types
	 * 
	 * @param ctx
	 * @param warehouseId
	 * @param trxName
	 * @return True if the warehouse is generic (can be used for all doc types).
	 */
	boolean hasAvailableDocTypes(final Properties ctx, final int warehouseId, final String trxName);

	/**
	 * Checks if the warehouse is valid for the given doc type.
	 * 
	 * @param ctx
	 * @param warehouseId
	 * @param docType
	 * @param trxName
	 * @return
	 */
	boolean isDocTypeAllowed(final Properties ctx, final int warehouseId, final I_C_DocType docType, final String trxName);

	List<I_M_Locator> retrieveLocators(I_M_Warehouse warehouse);

	/**
	 * retrieve warehouses for a specific docBaseType
	 * 
	 * @param ctx
	 * @param docBaseType
	 * @return
	 */
	List<I_M_Warehouse> retrieveWarehouses(final Properties ctx, final String docBaseType);

	/**
	 * retrieve the warehouse for the given org. The warehouse is taken from AD_OrgInfo. if no warehouse found returns null.
	 * 
	 * @param ctx
	 * @param org
	 * @return
	 */
	I_M_Warehouse retrieveOrgWarehouse(Properties ctx, int adOrgId);

	int retrieveOrgWarehousePOId(int adOrgId);

	/**
	 * Retrieve all warehouses for given organization
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @return list of warehouses
	 */
	List<I_M_Warehouse> retrieveForOrg(Properties ctx, int AD_Org_ID);

	/**
	 * Retrieve all warehouses that have the IsInTransit flag set, for given organization.
	 * 
	 * @param ctx
	 * @param adOrgId
	 * @return list of in transit warehouses
	 */
	List<I_M_Warehouse> retrieveWarehousesInTransitForOrg(Properties ctx, int adOrgId);

	/**
	 * Retrieve first InTransit warehouse
	 * 
	 * @param ctx
	 * @param adOrgId
	 * @return in transit warehouse or null
	 * @see #retrieveWarehousesInTransitForOrg(Properties, int)
	 */
	I_M_Warehouse retrieveWarehouseInTransitForOrg(Properties ctx, int adOrgId);

	/**
	 * 
	 * Retrieve all warehouses for a given context
	 * 
	 * @param ctx
	 * @return
	 */
	List<I_M_Warehouse> retrieveWarehousesForCtx(Properties ctx);

	WarehousePickingGroup getWarehousePickingGroupContainingWarehouseId(int warehouseId);

	WarehousePickingGroup getWarehousePickingGroupById(int warehousePickingGroupId);

	int retrieveLocatorIdByBarcode(String barcode);

}
