package org.adempiere.ad.table.api.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.IColumnBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import lombok.NonNull;

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

	private static final String DB_FUNCTION_RETRIEVE_DISTINCT_IDS = "table_record_reference_retrieve_distinct_ids";

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

		if (tableName != null)
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
			retrieveDistinctIds(table.getTableName(), tableColumnName.get()).stream()
					.filter(referencedTableID -> referencedTableID > 0)
					.map(referencedTableID -> adTableDAO.retrieveTableName(referencedTableID))
					.forEach(referencedTableName -> result.add(
							TableRecordIdDescriptor.of(table.getTableName(), recordIdColumn.getColumnName(), referencedTableName)));
		}
		return result;
	}

	/**
	 * This method executes the equivalent of the following query:
	 * <p>
	 * {@code SELECT DISTINCT(<p_id_columnname>) FROM <p_tablename> WHERE COALESCE(<p_id_columnname>,0)!=0}
	 * <p>
	 * ..just faster.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/3389
	 */
	@VisibleForTesting
	Set<Integer> retrieveDistinctIds(
			@NonNull final String tableName,
			@NonNull final String idColumnName)
	{
		final String sql = "SELECT " + DB_FUNCTION_RETRIEVE_DISTINCT_IDS + "(?,?)";
		final Object[] sqlParams = new Object[] { tableName, idColumnName };

		final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);

		ResultSet rs = null;
		try
		{
			DB.setParameters(pstmt, ImmutableList.of(tableName, idColumnName));
			rs = pstmt.executeQuery();

			final ImmutableSet.Builder<Integer> result = ImmutableSet.builder();
			while (rs.next())
			{
				result.add(rs.getInt(1));
			}
			return result.build();
		}
		catch (final SQLException e)
		{
			throw DBException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("sql", sql)
					.setParameter("sqlParams", sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
