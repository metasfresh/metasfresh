package org.adempiere.impexp.inventory;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_M_Locator;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * A helper class for {@link InventoryImportProcess} that performs the "dirty" but efficient SQL updates on the {@link I_I_Inventory} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class MInventoryImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(MInventoryImportTableSqlUpdater.class);

	@Builder(buildMethodName = "updateIInventory")
	private void updateInventoryImportTable(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		dbUpdateOrgs(whereClause);
		dbUpdateLocatorDimensions(whereClause, ctx);
		dbUpdateWarehouse(whereClause, ctx);
		dbUpdateCreateLocators(whereClause, ctx);
		dbUpdateProducts(whereClause, ctx);

		dbUpdateErrorMessages(whereClause, ctx);
	}

	private void dbUpdateOrgs(@NonNull final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_Inventory i "
				+ "SET AD_Org_ID=(SELECT AD_Org_ID FROM AD_Org o"
				+ " WHERE i.OrgValue=o.Value AND o.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Org ={}", no);
	}

	private void dbUpdateLocatorDimensions(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);

		// Set M_Warehouse_ID
		StringBuilder sql = new StringBuilder("UPDATE I_Inventory i ")
				.append("SET warehouseValue = dimensions.warehouseValue, X = dimensions.locatorX , Y = dimensions.locatorY, Z = dimensions.locatorZ, X1 = dimensions.locatorX1 ")
				.append("FROM (SELECT I_Inventory_ID, d.warehouseValue, d.locatorValue, d.locatorX, d.locatorY, d.locatorZ, d.locatorX1 ")
				.append("	FROM I_Inventory as inv")
				.append("	JOIN extractLocatorDimensions(inv.locatorvalue) as d on d.locatorvalue=inv.locatorvalue")
				.append(") AS dimensions")
				.append("WHERE AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		int no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Warehouse from Locator =" + no);
	}

	private void dbUpdateCreateLocators(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		dbUpdateLocators(whereClause, ctx);
		dbCreateLocators(whereClause, ctx);
	}

	private void dbUpdateLocators(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		StringBuilder sql = new StringBuilder("UPDATE I_Inventory i ")
				.append("SET M_Locator_ID=(SELECT MAX(M_Locator_ID) FROM M_Locator l ")
				.append("WHERE i.LocatorValue=l.Value AND i.AD_Client_ID=l.AD_Client_ID) ")
				.append("WHERE M_Locator_ID IS NULL AND LocatorValue IS NOT NULL ")
				.append("AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		int no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Locator from Value =" + no);
	}

	private void dbCreateLocators(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		final List<I_I_Inventory> unmatchedLocator = Services.get(IQueryBL.class).createQueryBuilder(I_I_Inventory.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_I_Inventory.COLUMNNAME_I_IsImported, false)
				.addNotNull(I_I_Inventory.COLUMN_LocatorValue)
				.addNotNull(I_I_Inventory.COLUMN_WarehouseValue)
				.addEqualsFilter(I_I_Inventory.COLUMNNAME_M_Locator_ID, null)
				.create()
				.list(I_I_Inventory.class);

		unmatchedLocator.forEach(importRecord -> {
			final I_M_Locator locator = createNewMLocator(importRecord);
			importRecord.setM_Locator(locator);
			InterfaceWrapperHelper.save(importRecord);
		});
	}

	private I_M_Locator createNewMLocator(@NonNull final I_I_Inventory importRecord)
	{
		final I_M_Locator locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		locator.setAD_Org_ID(importRecord.getAD_Org_ID());
		locator.setM_Warehouse_ID(importRecord.getM_Warehouse_ID());
		locator.setValue(importRecord.getLocatorValue());
		locator.setX(importRecord.getX());
		locator.setY(importRecord.getY());
		locator.setZ(importRecord.getZ());
		locator.setX1(importRecord.getX1());
		locator.setDateLastInventory(importRecord.getDateLastInventory());
		InterfaceWrapperHelper.save(locator);
		return locator;
	}

	private void dbUpdateWarehouse(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);

		// Set M_Warehouse_ID
		final StringBuilder sql = new StringBuilder("UPDATE I_Inventory i ")
				.append("SET M_Warehouse_ID=(SELECT M_Warehouse_ID FROM M_Warehouse w WHERE i.warehouseValue=w.value) ")
				.append("WHERE M_Warehouse_ID IS NOT NULL ")
				.append("AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		int no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Warehouse =" + no);
	}

	private void dbUpdateProducts(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		StringBuilder sql = new StringBuilder("UPDATE I_Inventory i ")
				.append("SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p ")
				.append(" WHERE i.Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL AND Value IS NOT NULL ")
				.append("AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		int no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Product from Value=" + no);

		sql = new StringBuilder("UPDATE I_Inventory i ")
				.append("SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p ")
				.append(" WHERE i.UPC=p.UPC AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL AND UPC IS NOT NULL ")
				.append("AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Product from UPC=" + no);
	}

	private void dbUpdateErrorMessages(@NonNull final String whereClause, @NonNull final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE I_Inventory i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid org, ' "
				+ "WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Org={}", no);

		sql = new StringBuilder("UPDATE I_Inventory ")
				.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Locator, ' ")
				.append("WHERE M_Locator_ID IS NULL")
				.append("AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No Location=" + no);
		}

		sql = new StringBuilder("UPDATE I_Inventory ")
				.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Warehouse, ' ")
				.append("WHERE M_Warehouse_ID IS NULL ")
				.append("AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No Warehouse=" + no);
		}

		sql = new StringBuilder("UPDATE I_Inventory ")
				.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Product, ' ")
				.append("WHERE M_Product_ID IS NULL")
				.append("AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No Product=" + no);
		}

		// No QtyCount
		sql = new StringBuilder("UPDATE I_Inventory ")
				.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No QtyInternalUse, ' ")
				.append("WHERE QtyInternalUse IS NULL ")
				.append("AND I_IsImported<>'Y' AND AD_Client_ID = ")
				.append(adClientId)
				.append(whereClause);
		no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No QtyInternalUse=" + no);
		}

	}
}
