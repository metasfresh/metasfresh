package org.adempiere.ad.table.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MTable;
import org.compiere.util.Env;

public class ADTableDAO implements IADTableDAO
{
	@Override
	public I_AD_Column retrieveColumn(final String tableName, final String columnName)
	{
		final I_AD_Column column = retrieveColumnOrNull(tableName, columnName);
		if (column == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Column_ID@ " + columnName + " (@AD_Table_ID@=" + tableName + ")");
		}
		return column;
	}

	@Override
	public I_AD_Column retrieveColumnOrNull(final String tableName, final String columnName)
	{
		final IQueryBuilder<I_AD_Column> queryBuilder = retrieveColumnQueryBuilder(tableName, columnName);
		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_AD_Column.class);
	}

	@Override
	public boolean hasColumnName(final String tableName, final String columnName)
	{
		final IQueryBuilder<I_AD_Column> queryBuilder = retrieveColumnQueryBuilder(tableName, columnName);
		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.match();
	}

	private IQueryBuilder<I_AD_Column> retrieveColumnQueryBuilder(final String tableName, final String columnName)
	{
		//
		// Create queryBuilder with default context (not needed for tables)
		final IQueryBuilder<I_AD_Column> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Column.class)
				.setContext(Env.getCtx(), ITrx.TRXNAME_None);

		//
		// Filter by tableName
		queryBuilder.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, retrieveTableId(tableName));
		//
		// Filter by columnName
		queryBuilder.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName, UpperCaseQueryFilterModifier.instance);

		return queryBuilder;
	}

	@Override
	public String retrieveTableName(final int adTableId)
	{
		final Properties ctx = Env.getCtx();
		
		// guard against 0 AD_Table_ID
		if (adTableId <= 0)
		{
			return null;
		}
		
		@SuppressWarnings("deprecation")
		final String tableName = MTable.getTableName(ctx, adTableId);

		return tableName;
	}

	@Override
	public int retrieveTableId(final String tableName)
	{
		// NOTE: make sure we are returning -1 in case tableName was not found (and NOT throw exception),
		// because there is business logic which depends on this
		
		@SuppressWarnings("deprecation")
		// TODO move getTable_ID out of MTable
		final int tableId = MTable.getTable_ID(tableName);

		return tableId;
	}

	@Override
	public boolean isTableId(final String tableName, final int adTableId)
	{
		if (adTableId <= 0)
		{
			return false;
		}
		if (Check.isEmpty(tableName))
		{
			return false;
		}
		return adTableId == retrieveTableId(tableName);
	}

	@Override
	public List<I_AD_Table> retrieveAllTables(final Properties ctx, final String trxName)
	{
		final IQueryBuilder<I_AD_Table> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Table.class)
				.setContext(ctx, trxName);

		queryBuilder.orderBy()
				.addColumn(I_AD_Table.COLUMNNAME_TableName);

		return queryBuilder.create()
				.list();
	}

	@Override
	public boolean isVirtualColumn(final I_AD_Column column)
	{
		final String s = column.getColumnSQL();
		return !Check.isEmpty(s, true);
	}

	@Override
	public String retrieveWindowName(final Properties ctx, final String tableName)
	{
		// NOTE: atm we use MTable.get because that's the only place where we have the table cached.
		// In future we shall replace it with something which is database independent.
		final I_AD_Table adTable = MTable.get(ctx, tableName);
		if(adTable == null)
		{
			return "";
		}
		final I_AD_Window adWindow = adTable.getAD_Window();
		if(adWindow == null)
		{
			return "";
		}
		final I_AD_Window adWindowTrl = InterfaceWrapperHelper.translate(adWindow, I_AD_Window.class);
		return adWindowTrl.getName();
	}
}
