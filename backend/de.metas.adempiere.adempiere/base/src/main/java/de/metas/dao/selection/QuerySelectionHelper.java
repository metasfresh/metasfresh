package de.metas.dao.selection;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.dao.selection.model.I_T_Query_Selection;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.lang.UIDStringUtil;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@UtilityClass
public class QuerySelectionHelper
{

	public static final String SELECTION_LINE_ALIAS = "ZZ_Line";

	private static final Logger logger = LogManager.getLogger(QuerySelectionHelper.class);

	/**
	 * Uses the given query and uuid to insert records into the {@code T_Query_Selection} table.
	 */
	public UUISelection createUUIDSelection(@NonNull final TypedSqlQuery<?> query)
	{
		final String querySelectionUUID = UIDStringUtil.createRandomUUID();
		final String sql = buildUUIDSelectionSqlSelectFrom(
				querySelectionUUID,
				query,
				query.getKeyColumnName());
		final List<Object> params = query.getParametersEffective();
		final String trxName = query.getTrxName();

		final Instant now = retrieveDatabaseCurrentTime();
		final int rowsCount = DB.executeUpdateAndThrowExceptionOnFail(
				sql,
				params == null ? null : params.toArray(),
				trxName);

		logger.trace("createUUIDSelection: sql={}, params={}, trxName={}, rowsCount={}", sql, params, trxName, rowsCount);

		return new UUISelection(
				rowsCount,
				querySelectionUUID,
				now);
	}

	private Instant retrieveDatabaseCurrentTime()
	{
		final Timestamp now = DB.getSQLValueTSEx(ITrx.TRXNAME_ThreadInherited, "select now()");
		return now.toInstant();
	}

	@VisibleForTesting
	static String buildUUIDSelectionSqlSelectFrom(
			@NonNull final String querySelectionUUID,
			@NonNull final TypedSqlQuery<?> query,
			@NonNull final String keyColumnName)
	{
		if (query.hasUnions())
		{
			return buildUUIDSelectionSqlSelectFrom_queryWithUnions(querySelectionUUID, query, keyColumnName);
		}
		else
		{
			return buildUUIDSelectionSqlSelectFrom_simpleQuery(querySelectionUUID, query, keyColumnName);
		}
	}

	private String buildUUIDSelectionSqlSelectFrom_simpleQuery(
			@NonNull final String querySelectionUUID,
			@NonNull final TypedSqlQuery<?> query,
			@NonNull final String keyColumnName)
	{
		final String tableName = query.getTableName();
		final String keyColumnNameFQ = tableName + "." + keyColumnName;

		final String orderBy = query.getOrderBy();

		final StringBuilder sqlRowNumber = new StringBuilder("row_number() OVER (");
		if (!Check.isEmpty(orderBy, true))
		{
			sqlRowNumber.append("ORDER BY ").append(orderBy);
		}
		sqlRowNumber.append(")");

		final StringBuilder sqlInsertIntoBuilder = new StringBuilder()
				.append("INSERT INTO ")
				.append(I_T_Query_Selection.Table_Name)
				.append(" (")
				.append(I_T_Query_Selection.COLUMNNAME_UUID)
				.append(", ").append(I_T_Query_Selection.COLUMNNAME_Line)
				.append(", ").append(I_T_Query_Selection.COLUMNNAME_Record_ID)
				.append(")");

		final StringBuilder sqlSelectBuilder = new StringBuilder()
				.append(" SELECT ")
				.append(DB.TO_STRING(querySelectionUUID))
				.append(", ").append(sqlRowNumber)
				.append(", ").append(keyColumnNameFQ);

		final StringBuilder sqlFromBuilder = new StringBuilder(" FROM ").append(tableName);

		// be sure to only pass the "SELECT", not the "INSERT" sql to avoid invalid SQL when ORs are exploded to unions
		final String sqlSelect = query.buildSQL(
				sqlSelectBuilder,
				sqlFromBuilder,
				null/* groupByClause */,
				true/* useOrderByClause */);

		return sqlInsertIntoBuilder.append(sqlSelect).toString();
	}

	private String buildUUIDSelectionSqlSelectFrom_queryWithUnions(
			@NonNull final String querySelectionUUID,
			@NonNull final TypedSqlQuery<?> query,
			@NonNull final String keyColumnName)
	{
		final String sqlInnerSelect = query.buildSQL(
				"SELECT " + keyColumnName,
				/* fromClause */ null,
				/* groupByClause */ null,
				/* useOrderByClause */true);

		return new StringBuilder()
				.append("INSERT INTO ")
				.append(I_T_Query_Selection.Table_Name)
				.append(" (")
				.append(I_T_Query_Selection.COLUMNNAME_UUID)
				.append(", ").append(I_T_Query_Selection.COLUMNNAME_Line)
				.append(", ").append(I_T_Query_Selection.COLUMNNAME_Record_ID)
				.append(")")
				//
				.append("\nSELECT ")
				.append(DB.TO_STRING(querySelectionUUID))
				.append(", row_number() over ()")
				.append(", ").append(keyColumnName)
				.append("\nFROM (\n")
				.append(sqlInnerSelect)
				.append("\n) t")
				//
				.toString();
	}

	@Value
	public static class UUISelection
	{
		int size;
		String uuid;
		Instant time;
	}

	public <T, ET extends T> TypedSqlQuery<ET> createUUIDSelectionQuery(
			@NonNull final IContextAware ctx,
			@NonNull final Class<ET> clazz,
			@NonNull final String querySelectionUUID)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(clazz);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(clazz);
		final String keyColumnNameFQ = tableName + "." + keyColumnName;

		//
		// Build the query used to retrieve models by querying the selection.
		// NOTE: we are using LEFT OUTER JOIN instead of INNER JOIN because
		// * methods like hasNext() are comparing the rowsFetched counter with rowsCount to detect if we reached the end of the selection (optimization).
		// * POBufferedIterator is using LIMIT/OFFSET clause for fetching the next page and eliminating rows from here would fuck the paging if one record was deleted in meantime.
		// So we decided to load everything here, and let the hasNext() method to deal with the case when the record is really missing.
		final String selectionSqlFrom = "(SELECT "
				+ I_T_Query_Selection.COLUMNNAME_UUID + " as ZZ_UUID"
				+ ", " + I_T_Query_Selection.COLUMNNAME_Record_ID + " as ZZ_Record_ID"
				+ ", " + I_T_Query_Selection.COLUMNNAME_Line + " as " + SELECTION_LINE_ALIAS
				+ " FROM " + I_T_Query_Selection.Table_Name
				+ ") s "
				+ "\n LEFT OUTER JOIN " + tableName + " ON (" + keyColumnNameFQ + "=s.ZZ_Record_ID)";

		final String selectionWhereClause = "s.ZZ_UUID=?";
		final String selectionOrderBy = "s." + SELECTION_LINE_ALIAS;

		final TypedSqlQuery<ET> querySelection = new TypedSqlQuery<>(
				ctx.getCtx(),
				clazz,
				selectionWhereClause,
				ctx.getTrxName())
						.setParameters(querySelectionUUID)
						.setSqlFrom(selectionSqlFrom)
						.setOrderBy(selectionOrderBy);

		return querySelection;
	}
}
