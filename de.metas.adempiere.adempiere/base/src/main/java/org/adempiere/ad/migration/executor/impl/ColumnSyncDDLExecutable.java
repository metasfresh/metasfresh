package org.adempiere.ad.migration.executor.impl;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.executor.IPostponedExecutable;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MColumn;

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

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

public class ColumnSyncDDLExecutable implements IPostponedExecutable
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	private final IMigrationExecutorContext migrationCtx;
	private final int adColumnId;
	private final boolean drop;

	public ColumnSyncDDLExecutable(IMigrationExecutorContext migrationCtx, final int adColumnId, final boolean drop)
	{
		Check.assume(adColumnId > 0, "adColumnId > 0");

		this.migrationCtx = migrationCtx;
		this.adColumnId = adColumnId;
		this.drop = drop;
	}

	@Override
	public void execute()
	{
		final I_AD_Column column = retrieveColumn();
		if (column == null)
		{
			logger.info("No AD_Column found for " + adColumnId + ". Skip");
			return;
		}

		//
		if (drop)
		{
			// TODO unsync column?
			final String tableName = Services.get(IADTableDAO.class).retrieveTableName(column.getAD_Table_ID());
			logger.warn("Please manualy drop column {}.{}", tableName, column.getColumnName());
		}
		else
		{
			final MColumn columnPO = LegacyAdapters.convertToPO(column);
			columnPO.syncDatabase();
		}
	}

	private I_AD_Column retrieveColumn()
	{
		final I_AD_Column column = new TypedSqlQuery<>(migrationCtx.getCtx(), I_AD_Column.class, I_AD_Column.COLUMNNAME_AD_Column_ID + "=?", ITrx.TRXNAME_None)
				.setParameters(adColumnId)
				.firstOnly();
		return column;
	}
}
