package org.adempiere.ad.table.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.util.Env;

import de.metas.adempiere.service.IColumnBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

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

public class TableRecordIdDAO implements ITableRecordIdDAO
{

	@Override
	public List<TableRecordIdDescriptor> retrieveTableRecordIdReferences(final String tableName)
	{

		final PlainContextAware ctxAware = PlainContextAware.newWithTrxName(Env.getCtx(), ITrx.TRXNAME_None);

		return retrieveTableRecordIdReferences(ctxAware, tableName);

	}

	@Cached(cacheName = I_AD_Table.Table_Name + "#and#" + I_AD_Column.Table_Name + "#referencedTableId2TableRecordTableIDs")
	public List<TableRecordIdDescriptor> retrieveTableRecordIdReferences(@CacheCtx final Properties ctx, @CacheTrx final String trxName)
	{
		final PlainContextAware ctxAware = PlainContextAware.newWithTrxName(ctx, trxName);

		return retrieveTableRecordIdReferences(ctxAware, null);

	}

	@Override
	public List<TableRecordIdDescriptor> retrieveAllTableRecordIdReferences()
	{
		return retrieveTableRecordIdReferences(Env.getCtx(), ITrx.TRXNAME_None);
	}

	private List<TableRecordIdDescriptor> retrieveTableRecordIdReferences(final IContextAware ctxAware, final String tableName)
	{
		final IColumnBL columnBL = Services.get(IColumnBL.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		//
		final List<TableRecordIdDescriptor> result = new ArrayList<>();

		// get the list of all columns whose names that end with "Record_ID". They probably belong to a column-record table (but we will make sure).
		// we could have queried for columns ending with "Table_ID", but there might be more "*Table_ID" columns that don't have a "*Record_ID" column than the other way around.
		final IQueryBuilder<I_AD_Column> recordIdColumnsQuery = queryBL.createQueryBuilder(I_AD_Column.class, ctxAware)
				.addOnlyActiveRecordsFilter();

		if(tableName != null)
		{
			recordIdColumnsQuery.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, Services.get(IADTableDAO.class).retrieveTableId(tableName));
		}
	

		recordIdColumnsQuery.addEndsWithQueryFilter(I_AD_Column.COLUMNNAME_ColumnName, ITableRecordReference.COLUMNNAME_Record_ID)
				.orderBy().addColumn(I_AD_Column.COLUMN_AD_Column_ID).endOrderBy();

		final List<I_AD_Column> recordIdColumns = recordIdColumnsQuery
				.create()
				.list(I_AD_Column.class);

		for (final I_AD_Column recordIdColumn : recordIdColumns)
		{
			final I_AD_Table table = recordIdColumn.getAD_Table();
			if (table.isView())
			{
				continue;
			}
			final Optional<String> tableColumnName = columnBL.getTableIdColumnName(table.getTableName(), recordIdColumn.getColumnName());
			if (!tableColumnName.isPresent())
			{
				continue;
			}

			// now we know for sure that the records "table" of table can reference other records via Table_ID/Record_ID
			queryBL
					.createQueryBuilder(Object.class, table.getTableName(), ctxAware)
					// .addOnlyActiveRecordsFilter() we also want inactive records, because being active doesn't count when it comes to references
					.addCompareFilter(tableColumnName.get(), Operator.GREATER, 0)

					.create()
					.listDistinct(tableColumnName.get(), Integer.class)
					.stream()
					.filter(referencedTableID -> referencedTableID > 0)
					.map(referencedTableID -> adTableDAO.retrieveTableName(referencedTableID))
					.forEach(referencedTableName -> result.add(
							TableRecordIdDescriptor.of(table.getTableName(), recordIdColumn.getColumnName(), referencedTableName)));
		}

		return result;

	}
}
