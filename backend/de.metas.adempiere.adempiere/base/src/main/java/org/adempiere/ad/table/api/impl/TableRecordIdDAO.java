package org.adempiere.ad.table.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.service.IColumnBL;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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

	private CCache<String, ImmutableList<TableRecordIdDescriptor>> tableRecordIdDescriptorsByOriginTableName = CCache.<String, ImmutableList<TableRecordIdDescriptor>>builder()
			.tableName(I_AD_Table.Table_Name)
			.build();

	@Override
	public List<TableRecordIdDescriptor> getTableRecordIdReferences(@NonNull final String tableName)
	{
		return tableRecordIdDescriptorsByOriginTableName.getOrLoad(tableName, this::retrieveTableRecordIdReferences);
	}

	@Override
	public List<TableRecordIdDescriptor> retrieveAllTableRecordIdReferences()
	{
		final String onlyTableName = null;
		return retrieveTableRecordIdReferences(onlyTableName);
	}

	private ImmutableList<TableRecordIdDescriptor> retrieveTableRecordIdReferences(@Nullable final String onlyTableName)
	{
		final IColumnBL columnBL = Services.get(IColumnBL.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		//
		final ImmutableList.Builder<TableRecordIdDescriptor> result = ImmutableList.builder();

		// get the list of all columns whose names that end with "Record_ID". They probably belong to a column-record table (but we will make sure).
		// we could have queried for columns ending with "Table_ID", but there might be more "*Table_ID" columns that don't have a "*Record_ID" column than the other way around.
		final IQueryBuilder<I_AD_Column> recordIdColumnsQuery = queryBL.createQueryBuilderOutOfTrx(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEndsWithQueryFilter(I_AD_Column.COLUMNNAME_ColumnName, ITableRecordReference.COLUMNNAME_Record_ID)
				.orderBy(I_AD_Column.COLUMN_AD_Column_ID);

		if (onlyTableName != null)
		{
			recordIdColumnsQuery.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, adTableDAO.retrieveTableId(onlyTableName));
		}

		final List<I_AD_Column> recordIdColumns = recordIdColumnsQuery
				.create()
				.list(I_AD_Column.class);

		for (final I_AD_Column recordIdColumn : recordIdColumns)
		{
			final AdTableId adTableId = AdTableId.ofRepoId(recordIdColumn.getAD_Table_ID());
			final I_AD_Table adTable = adTableDAO.retrieveTable(adTableId);
			if (adTable.isView())
			{
				continue;
			}

			final String tableName = adTable.getTableName();
			final String recordIdColumnName = recordIdColumn.getColumnName();
			final String tableColumnName = columnBL.getTableIdColumnName(tableName, recordIdColumnName).orElse(null);
			if (tableColumnName == null)
			{
				continue;
			}

			// now we know for sure that the records "table" of table can reference other records via Table_ID/Record_ID
			retrieveDistinctIds(tableName, tableColumnName)
					.stream()
					.map(referencedTableId -> adTableDAO.getTableNameIfPresent(referencedTableId).orElse(null))
					.filter(Objects::nonNull)
					.map(referencedTableName -> TableRecordIdDescriptor.builder()
							.originTableName(tableName)
							.recordIdColumnName(recordIdColumnName)
							.targetTableName(referencedTableName)
							.build())
					.forEach(result::add);
		}

		return result.build();
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
	ImmutableSet<AdTableId> retrieveDistinctIds(
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

			final ImmutableSet.Builder<AdTableId> result = ImmutableSet.builder();
			while (rs.next())
			{
				final AdTableId adTableId = AdTableId.ofRepoIdOrNull(rs.getInt(1));
				if (adTableId != null)
				{
					result.add(adTableId);
				}
			}
			return result.build();
		}
		catch (final SQLException ex)
		{
			throw DBException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("sql", sql)
					.setParameter("sqlParams", sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
