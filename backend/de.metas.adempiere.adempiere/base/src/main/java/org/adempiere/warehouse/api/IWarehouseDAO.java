package org.adempiere.warehouse.api;

import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehousePickingGroup;
import org.adempiere.warehouse.WarehousePickingGroupId;
import org.adempiere.warehouse.WarehouseType;
import org.adempiere.warehouse.WarehouseTypeId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

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

public interface IWarehouseDAO extends ISingletonService
{
	I_M_Warehouse getById(WarehouseId warehouseId);

	<T extends I_M_Warehouse> T getById(WarehouseId warehouseId, Class<T> modelType);

	List<I_M_Warehouse> getByIds(Collection<WarehouseId> warehouseIds);

	<T extends I_M_Warehouse> List<T> getByIds(Collection<WarehouseId> warehouseIds, Class<T> modelType);

	String getWarehouseName(WarehouseId warehouseId);

	/**
	 * Checks if the warehouse is covered in M_Warehouse_Routing as specific to one or more doc types
	 *
	 * @return true if the warehouse is generic (can be used for all doc types).
	 */
	boolean isAllowAnyDocType(WarehouseId warehouseId);

	/**
	 * Checks if the warehouse is valid for the given doc type.
	 */
	boolean isDocTypeAllowed(WarehouseId warehouseId, final String docBaseType);

	WarehouseId getWarehouseIdByValue(String value);

	@Deprecated
	WarehouseId getWarehouseIdByLocatorRepoId(int locatorId);

	@Deprecated
	Set<WarehouseId> getWarehouseIdsForLocatorRepoIds(Set<Integer> locatorRepoIds);

	@Deprecated
	I_M_Locator getLocatorByRepoId(final int locatorId);

	@Nullable
	LocatorId getLocatorIdByRepoIdOrNull(int locatorId);

	LocatorId getLocatorIdByRepoId(int locatorId);

	I_M_Locator getLocatorById(final LocatorId locatorId);

	<T extends I_M_Locator> T getLocatorById(final LocatorId locatorId, Class<T> modelClass);

	<T extends I_M_Locator> T getLocatorByIdInTrx(LocatorId locatorId, Class<T> modelClass);

	List<I_M_Locator> getLocators(WarehouseId warehouseId);

	<T extends I_M_Locator> List<T> getLocators(WarehouseId warehouseId, Class<T> modelType);

	List<LocatorId> getLocatorIds(WarehouseId warehouseId);

	/**
	 * Retrieve warehouses for a specific docBaseType
	 */
	List<I_M_Warehouse> getWarehousesAllowedForDocBaseType(final String docBaseType);

	/**
	 * @return all warehouses for given organization
	 */
	List<I_M_Warehouse> getByOrgId(OrgId orgId);

	WarehouseId getInTransitWarehouseId(OrgId adOrgId);

	Optional<WarehouseId> getInTransitWarehouseIdIfExists(OrgId adOrgId);

	List<I_M_Warehouse> getAllWarehouses();

	Set<WarehouseId> getWarehouseIdsOfSamePickingGroup(WarehouseId warehouseId);

	WarehousePickingGroup getWarehousePickingGroupById(WarehousePickingGroupId warehousePickingGroupId);

	int retrieveLocatorIdByBarcode(String barcode);

	OrgId retrieveOrgIdByLocatorId(int locatorId);

	LocatorId createOrUpdateLocator(CreateOrUpdateLocatorRequest request);

	LocatorId retrieveLocatorIdByValueAndWarehouseId(String locatorValue, WarehouseId warehouseId);

	I_M_Locator getOrCreateLocatorByCoordinates(WarehouseId warehouseId, String value, String x, String y, String z);

	LocatorId createDefaultLocator(WarehouseId warehouseId);

	WarehouseType getWarehouseTypeById(WarehouseTypeId id);

	/**
	 * Retrieve the warehouse marked as IsIssueWarehouse; There should be one and only one entry of this kind (unique index).
	 */
	I_M_Warehouse retrieveWarehouseForIssuesOrNull(Properties ctx);

	/**
	 * Same as {@link #retrieveWarehouseForIssuesOrNull(Properties)} but it will fail if no warehouse found.
	 */
	I_M_Warehouse retrieveWarehouseForIssues(Properties ctx);

	/**
	 * Retrieve the warehouse marked as IsQuarantineWarehouse.
	 */
	org.adempiere.warehouse.model.I_M_Warehouse retrieveQuarantineWarehouseOrNull();
}
