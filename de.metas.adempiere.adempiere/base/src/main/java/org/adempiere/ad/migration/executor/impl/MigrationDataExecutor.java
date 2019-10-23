package org.adempiere.ad.migration.executor.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.executor.MigrationExecutorException;
import org.adempiere.ad.migration.model.I_AD_MigrationData;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.ad.migration.util.IDataConverter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.PO;

public class MigrationDataExecutor
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final IMigrationExecutorContext migrationCtx;
	private final I_AD_MigrationStep step;
	private final I_AD_MigrationData data;
	private final PO po;
	private final IDataConverter converter;

	private final int adTableId;

	public MigrationDataExecutor(final IMigrationExecutorContext migrationCtx, final I_AD_MigrationStep step, final I_AD_MigrationData data, final PO po, final IDataConverter converter)
	{
		this.migrationCtx = migrationCtx;
		this.step = step;
		this.data = data;
		this.po = po;
		this.converter = converter;

		this.adTableId = po.get_Table_ID();
	}

	public void apply()
	{
		final I_AD_Column column = getAD_Column();
		if (column == null)
		{
			final boolean fatal = !migrationCtx.isSkipMissingColumns();
			final MigrationExecutorException ex = new MigrationExecutorException("Column name '" + data.getColumnName() + " not found for " + data, fatal);

			if (fatal)
			{
				throw ex;
			}

			logger.info(ex.getLocalizedMessage(), ex);
			return;
		}

		// TODO: option to apply only when existing value equals reference value

		final String value;
		if (data.isNewNull())
		{
			value = null;
		}
		else
		{
			value = data.getNewValue();
		}

		// backup existing value
		if (po != null && !po.is_new())
		{
			final Object backupValue = po.get_Value(column.getColumnName());
			if (backupValue == null)
			{
				data.setBackupValue(null);
				data.setIsBackupNull(true);
			}
			else
			{
				final String backupValueStr = converter.objectToString(column, backupValue);
				data.setBackupValue(backupValueStr);
				data.setIsBackupNull(false);
			}

			InterfaceWrapperHelper.save(data);
		}

		// apply new values
		if (X_AD_MigrationStep.ACTION_Insert.equals(step.getAction()) || X_AD_MigrationStep.ACTION_Update.equals(step.getAction()))
		{
			final Object valueObj = converter.stringToObject(column, value);
			if (IDataConverter.VALUE_Unknown != valueObj)
			{
				po.set_ValueNoCheck(column.getColumnName(), valueObj);
			}
		}
	}

	public void rollback()
	{
		final I_AD_Column column = getAD_Column();

		final Object value;
		if (data.isBackupNull())
		{
			value = null;
		}
		else
		{
			final String valueStr = data.getBackupValue();
			value = converter.stringToObject(column, valueStr);
		}

		if (value == IDataConverter.VALUE_Unknown)
		{
			return;
		}

		po.set_ValueNoCheck(column.getColumnName(), value);
	}

	private I_AD_Column getAD_Column()
	{
		I_AD_Column column = data.getAD_Column();

		if (column != null && column.getAD_Table_ID() != adTableId)
		{
			logger.warn("Column '" + column.getColumnName()
					+ "' (ID=" + column.getAD_Column_ID() + ") does not match AD_Table_ID from parent. Attempting to retrieve column by name and tableId.");
			column = null;
		}

		final String dataColumnName = data.getColumnName();
		if (column != null && !dataColumnName.equalsIgnoreCase(column.getColumnName()))
		{
			logger.warn("Column ID collision '" + dataColumnName + "' with existing '" + column.getColumnName()
					+ "' (ID=" + column.getAD_Column_ID() + "). Attempting to retrieve column by name and tableId.");
			column = null;
		}

		if (column == null)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(data);
			final String trxName = InterfaceWrapperHelper.getTrxName(data);
			final String whereClause = I_AD_Column.COLUMNNAME_AD_Table_ID + "=?"
					+ " AND " + I_AD_Column.COLUMNNAME_ColumnName + "=?";
			column = new TypedSqlQuery<I_AD_Column>(ctx, I_AD_Column.class, whereClause, trxName)
					.setParameters(adTableId, dataColumnName)
					.firstOnly(I_AD_Column.class);
		}

		return column;
	}
}
