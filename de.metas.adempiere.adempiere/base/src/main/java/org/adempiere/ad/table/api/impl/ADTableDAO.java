package org.adempiere.ad.table.api.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MTable;
import org.compiere.util.DB;
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
		final IQueryBuilder<I_AD_Column> queryBuilder = retrieveColumnQueryBuilder(tableName, columnName, ITrx.TRXNAME_None);
		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_AD_Column.class);
	}

	@Override
	public boolean hasColumnName(final String tableName, final String columnName)
	{
		final IQueryBuilder<I_AD_Column> queryBuilder = retrieveColumnQueryBuilder(tableName, columnName, ITrx.TRXNAME_None);
		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.match();
	}

	@Override
	public IQueryBuilder<I_AD_Column> retrieveColumnQueryBuilder(final String tableName,
			final String columnName,
			final String trxName)
	{
		final String trxNametoUse = trxName == null ? ITrx.TRXNAME_None : trxName;

		//
		// Create queryBuilder with default context (not needed for tables)
		final IQueryBuilder<I_AD_Column> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Column.class, Env.getCtx(), trxNametoUse);

		//
		// Filter by tableName
		queryBuilder.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, retrieveTableId(tableName));
		//
		// Filter by columnName
		queryBuilder.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName, UpperCaseQueryFilterModifier.instance);

		return queryBuilder;
	}

	@Override
	public String retrieveColumnName(final int adColumnId)
	{
		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?", adColumnId);
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
	public boolean isExistingTable(final String tableName)
	{
		if (Check.isEmpty(tableName, true))
		{
			return false;
		}
		return retrieveTableId(tableName) > 0;
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
				.createQueryBuilder(I_AD_Table.class, ctx, trxName);

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
		if (adTable == null)
		{
			return "";
		}
		final I_AD_Window adWindow = adTable.getAD_Window();
		if (adWindow == null)
		{
			return "";
		}
		final I_AD_Window adWindowTrl = InterfaceWrapperHelper.translate(adWindow, I_AD_Window.class);
		return adWindowTrl.getName();
	}

	@Override
	public void onTableNameRename(final I_AD_Table table)
	{
		Check.assumeNotNull(table, "table not null");
		final I_AD_Table tableOld = InterfaceWrapperHelper.createOld(table, I_AD_Table.class);
		final String tableNameOld = tableOld.getTableName();
		final String tableNameNew = table.getTableName();

		// Do nothing if the table name was not actually changed
		if (Check.equals(tableNameOld, tableNameNew))
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(table);
		Services.get(ISequenceDAO.class).renameTableSequence(ctx, tableNameOld, tableNameNew);
	}

	@Override
	public I_AD_Element retrieveElement(final String columnName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_AD_Element element = queryBL.createQueryBuilder(I_AD_Element.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Element.COLUMNNAME_ColumnName, columnName)
				.create()
				.firstOnly(I_AD_Element.class);
		return element;
	}

	@Override
	public I_AD_Table retrieveTable(final String tableName)
	{
		return MTable.get(Env.getCtx(), tableName);
	}
}
