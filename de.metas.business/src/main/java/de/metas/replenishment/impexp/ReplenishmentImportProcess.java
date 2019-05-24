package de.metas.replenishment.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_Replenish;
import org.compiere.model.I_M_Replenish;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Replenish;

import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ReplenishmentImportProcess extends AbstractImportProcess<I_I_Replenish>
{

	@Override
	public Class<I_I_Replenish> getImportModelClass()
	{
		return I_I_Replenish.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Replenish.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_BPartner.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		RepelnishmentImportTableSqlUpdater.updateReplenishmentImortTable(getWhereClause());
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Replenish.COLUMNNAME_ProductValue;
	}

	@Override
	protected I_I_Replenish retrieveImportRecord(Properties ctx, ResultSet rs) throws SQLException
	{
		return new X_I_Replenish(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	/*
	 * @param isInsertOnly ignored. This import is only for updates.
	 */
	@Override
	protected ImportRecordResult importRecord(@NonNull IMutable<Object> state,
			@NonNull final I_I_Replenish importRecord,
			final boolean isInsertOnly)
	{
		if (isValidRecordForImport(importRecord))
		{

			return importReplenish(importRecord);
		}
		return null;

	}

	private boolean isValidRecordForImport(@NonNull final I_I_Replenish importRecord)
	{
		if (importRecord.getM_Product_ID() <= 0)
		{
			return false;
		}

		if (importRecord.getM_Warehouse_ID() <= 0)
		{
			return false;
		}

		if (importRecord.getLevel_Max() == null)
		{
			return false;
		}

		if (importRecord.getLevel_Min() == null)
		{
			return false;
		}

		if (Check.isEmpty(importRecord.getReplenishType(), true))
		{
			return false;
		}

		return true;
	}

	private ImportRecordResult importReplenish(@NonNull final I_I_Replenish importRecord)
	{
		final ImportRecordResult replenishImportResult;

		final I_M_Replenish replenish;
		if (importRecord.getM_Replenish_ID() <= 0)
		{
			replenish = createNewReplenish(importRecord);
			replenishImportResult = ImportRecordResult.Inserted;
		}
		else
		{
			replenish = uppdateReplenish(importRecord);
			replenishImportResult = ImportRecordResult.Updated;
		}

		ModelValidationEngine.get().fireImportValidate(this, importRecord, replenish, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(replenish);

		importRecord.setM_Replenish_ID(replenish.getM_Replenish_ID());
		InterfaceWrapperHelper.save(importRecord);

		return replenishImportResult;
	}

	private I_M_Replenish createNewReplenish(@NonNull final I_I_Replenish importRecord)
	{
		final I_M_Replenish replenish = InterfaceWrapperHelper.newInstance(I_M_Replenish.class, importRecord);
		setReplenishmenttValueFields(importRecord, replenish);
		return replenish;
	}
	
	private I_M_Replenish uppdateReplenish(@NonNull final I_I_Replenish importRecord)
	{
		final I_M_Replenish replenish = InterfaceWrapperHelper.newInstance(I_M_Replenish.class, importRecord);
		setReplenishmenttValueFields(importRecord, replenish);
		return replenish;
	}

	private void setReplenishmenttValueFields(@NonNull final I_I_Replenish importRecord, @NonNull final I_M_Replenish replenish)
	{
		// mandatory fields
		replenish.setM_Product_ID(importRecord.getM_Product_ID());
		replenish.setM_Warehouse_ID(importRecord.getM_Warehouse_ID());
		replenish.setLevel_Max(importRecord.getLevel_Max());
		replenish.setLevel_Min(importRecord.getLevel_Min());
		replenish.setReplenishType(importRecord.getReplenishType());
		replenish.setTimeToMarket(importRecord.getTimeToMarket());

		// optional fields
		if (importRecord.getM_WarehouseSource_ID() > 0)
		{
			replenish.setM_WarehouseSource_ID(importRecord.getM_WarehouseSource_ID());
		}
		if (importRecord.getM_Locator_ID() > 0)
		{
			replenish.setM_Locator_ID(importRecord.getM_Locator_ID());
		}
		if (importRecord.getC_Calendar_ID() > 0)
		{
			replenish.setC_Calendar_ID(importRecord.getC_Calendar_ID());
		}
		if (importRecord.getC_Period_ID() > 0)
		{
			replenish.setC_Period_ID(importRecord.getC_Period_ID());
		}
	}
}
