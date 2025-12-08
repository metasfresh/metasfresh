/**
 *
 */
package org.compiere.model;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import de.metas.i18n.ITranslatableString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import de.metas.cache.CCache;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * AD Index Table
 *
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class MIndexTable extends X_AD_Index_Table
{
	private static final CCache<Integer, TableIndexesMap> cache = CCache.<Integer, TableIndexesMap> builder()
			.tableName(I_AD_Index_Table.Table_Name)
			.initialCapacity(1)
			.additionalTableNameToResetFor(I_AD_Index_Column.Table_Name)
			.build();

	private ImmutableList<MIndexColumn> indexColumns = null; // lazy

	private static TableIndexesMap getTableIndexesMap()
	{
		return cache.getOrLoad(0, MIndexTable::retrieveTableIndexesMap);
	}

	private static TableIndexesMap retrieveTableIndexesMap()
	{
		final List<MIndexTable> indexes = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(MIndexTable.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(MIndexTable.class);
		return new TableIndexesMap(indexes);
	}

	private static ImmutableList<MIndexTable> getByTableName(final String tableName)
	{
		return getTableIndexesMap().getByTableName(tableName);
	}

	public static MIndexTable getByNameIgnoringCase(@NonNull final String name)
	{
		return getTableIndexesMap().getByNameIgnoringCase(name);
	}

	public MIndexTable(final Properties ctx, final int AD_Index_Table_ID, final String trxName)
	{
		super(ctx, AD_Index_Table_ID, trxName);
	}

	@SuppressWarnings("unused")
	public MIndexTable(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public ITranslatableString getErrorMsgTrl() {return get_ModelTranslationMap().getColumnTrl(COLUMNNAME_ErrorMsg, getErrorMsg());}

	private ImmutableList<MIndexColumn> getIndexColumns()
	{
		ImmutableList<MIndexColumn> indexColumns = this.indexColumns;
		if (indexColumns == null)
		{
			indexColumns = this.indexColumns = retrieveIndexColumns();
		}
		return indexColumns;
	}

	private ImmutableList<MIndexColumn> retrieveIndexColumns()
	{
		final int adIndexTableId = getAD_Index_Table_ID();
		Check.assumeGreaterThanZero(adIndexTableId, "adIndexTableId");

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Index_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Index_Column.COLUMNNAME_AD_Index_Table_ID, adIndexTableId)
				.orderBy(I_AD_Index_Column.COLUMNNAME_SeqNo)
				.create()
				.listImmutable(MIndexColumn.class);
	}

	public Set<String> getColumnNames()
	{
		return getIndexColumns()
				.stream()
				.map(MIndexColumn::getColumnName)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Get Table Name
	 *
	 * @return table name
	 */
	public String getTableName()
	{
		return Services.get(IADTableDAO.class).retrieveTableName(getAD_Table_ID());
	}

	public String getDDL()
	{
		final StringBuilder sql_columns = new StringBuilder();
		for (final MIndexColumn ic : getIndexColumns())
		{
			if (sql_columns.length() > 0)
			{
				sql_columns.append(",");
			}
			sql_columns.append(ic.getColumnName());
		}
		if (sql_columns.length() == 0)
		{
			throw new AdempiereException("Index has no columns defined");
		}
		//
		final StringBuilder sql = new StringBuilder("CREATE ");
		if (isUnique())
		{
			sql.append("UNIQUE ");
		}
		sql.append("INDEX ").append(getName()).append(" ON ").append(
				getTableName()).append(" (").append(sql_columns).append(")");
		//
		final String whereClause = getWhereClause();
		if (!Check.isEmpty(whereClause, true))
		{
			if (DB.isPostgreSQL())
			{
				sql.append(" WHERE ").append(whereClause);
			}
			else
			{
				throw new AdempiereException(
						"Partial Index not supported for this database");
			}
		}
		//
		return sql.toString();
	}

	public String getDBTriggerName()
	{
		return getName() + "_tg";
	}

	public String getDBFunctionName()
	{
		return getName() + "_tgfn";
	}

	public boolean hasBeforeChangeCode()
	{
		return !Check.isEmpty(getBeforeChangeCode(), true);
	}

	public String getBeforeChangeCodeFunctionDDL()
	{
		final String code = getBeforeChangeCode();
		if (Check.isEmpty(code, true))
		{
			return null;
		}

		final String codeType = getBeforeChangeCodeType();

		final StringBuilder sqlFunc = new StringBuilder();
		if (BEFORECHANGECODETYPE_SQLSimple.equals(codeType))
		{
			sqlFunc.append("UPDATE " + getTableName() + " SET " + code);
			sqlFunc.append(", Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy");
			sqlFunc.append(" WHERE 1=1 ");
			for (final String columnName : getColumnNames())
			{
				sqlFunc.append(" AND " + columnName + "=NEW." + columnName);
			}
			sqlFunc.append(" AND " + getTableName() + "_ID<>NEW."
					+ getTableName() + "_ID");
			if (!Check.isEmpty(getWhereClause(), true))
			{
				sqlFunc.append(" AND ").append(getWhereClause());
			}
		}
		else if (BEFORECHANGECODETYPE_SQLComplexScript.equals(codeType))
		{
			sqlFunc.append(code);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @"
					+ COLUMNNAME_BeforeChangeCodeType + "@ " + codeType);
		}
		//
		if (!sqlFunc.toString().trim().endsWith(";"))
		{
			sqlFunc.append(";");
		}

		// Check modified columns only
		final StringBuilder sqlCheckBegin = new StringBuilder();
		for (final String columnName : getColumnNames())
		{
			if (sqlCheckBegin.length() > 0)
			{
				sqlCheckBegin.append(" OR ");
			}
			sqlCheckBegin.append("OLD." + columnName + "<>NEW." + columnName);
		}
		sqlCheckBegin.insert(0, "IF ").append(" THEN\n");
		final StringBuilder sqlCheckEnd = new StringBuilder(" END IF;\n");

		final String triggerName = getDBTriggerName();
		final String functionName = getDBFunctionName();

		final StringBuilder sql = new StringBuilder();
		sql.append("CREATE OR REPLACE FUNCTION " + functionName + "()\n")
				.append(" RETURNS TRIGGER AS $" + triggerName + "$\n").append(
						" BEGIN\n")
				.append(" IF TG_OP='INSERT' THEN\n").append(
						sqlFunc)
				.append("\n").append(" ELSE\n").append(
						sqlCheckBegin)
				.append(sqlFunc).append("\n").append(
						sqlCheckEnd)
				.append(" END IF;\n").append(
						" RETURN NEW;\n")
				.append(" END;\n").append(
						" $" + triggerName + "$ LANGUAGE plpgsql;");
		return sql.toString();
	}

	public String getBeforeChangeCodeTriggerDDL()
	{
		if (Check.isEmpty(getBeforeChangeCode(), true))
		{
			return null;
		}

		final String triggerName = getDBTriggerName();
		final String functionName = getDBFunctionName();

		final StringBuilder sqlColumns = new StringBuilder();
		for (final String columnName : getColumnNames())
		{
			if (sqlColumns.length() > 0)
			{
				sqlColumns.append(",");
			}
			sqlColumns.append(columnName);
		}

		final StringBuilder sql = new StringBuilder();
		sql.append("CREATE TRIGGER " + triggerName).append(
				" BEFORE INSERT OR UPDATE ")
				// .append(" OF ").append(sqlColumns) // PG 8.5 specific
				.append(" ON " + getTableName()).append(" FOR EACH ROW")
				.append(" EXECUTE PROCEDURE " + functionName + "()");
		return sql.toString();
	}

	/**
	 * Validate table data and throw exception if any error occur
	 *
	 * @param trxName
	 * @throws DBUniqueConstraintException if not unique data found
	 */
	public void validateData(final String trxName)
	{
		if (!isActive())
		{
			return;
		}
		if (!isUnique())
		{
			return;
		}
		//
		final StringBuilder sqlGroupBy = new StringBuilder();
		for (final MIndexColumn c : getIndexColumns())
		{
			if (sqlGroupBy.length() > 0)
			{
				sqlGroupBy.append(",");
			}
			sqlGroupBy.append(c.getColumnName());
		}
		if (sqlGroupBy.length() == 0)
		{
			return;
		}

		//
		final StringBuilder sql = new StringBuilder("SELECT " + sqlGroupBy + " FROM " + getTableName());
		final String whereClause = getWhereClause();

		if (!Check.isEmpty(whereClause, true))
		{
			sql.append(" WHERE ").append(whereClause);
		}
		//
		sql.append(" GROUP BY ").append(sqlGroupBy);
		sql.append(" HAVING COUNT(1) > 1");
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), trxName);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				throw new DBUniqueConstraintException(this);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	public static boolean isAnyIndexedValueChanged(final GridTab tab, final boolean newRecord)
	{
		for (final MIndexTable index : getByTableName(tab.getTableName()))
		{
			if (index.isIndexedValuesChanged(tab, newRecord))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isMatched(final GridTab tab, final boolean newRecord)
	{
		return isIndexedValuesChanged(tab, newRecord)
				&& isWhereClauseMatched(tab);
	}

	/**
	 * Check if there are changed values in index columns for the grid tab
	 *
	 * @param tab
	 * @param newRecord
	 * @return
	 */
	public boolean isIndexedValuesChanged(final GridTab tab, final boolean newRecord)
	{
		if (!tab.getTableName().equalsIgnoreCase(getTableName()))
		{
			throw new IllegalArgumentException("GridTab " + tab
					+ " should be from table " + getTableName());
		}
		for (final String columnName : getColumnNames())
		{
			if (isValueChanged(tab, columnName, newRecord))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Introduced to avoid false index qualification on before save.
	 * <p>
	 * Before this functionality was introduced, ADempiere was identifying which indexes will be affected by a GridTab save only based on which columns were modified. In case of a new record, all
	 * columns are considered as modified.
	 * <p>
	 * The index's SQL where clause was not considered. That is the source of our issue.
	 * <p>
	 * To avoid this case, we introduced this method which actually generates a dummy SELECT on "dual" table and apply the where clause on it.
	 * <p>
	 * For example, consider a table called "MyTable" with Column1, Column2... ColumnN columns.
	 * <p>
	 * Our index has following where clause: Column1='Y' and Column2='N'.
	 * <p>
	 * In this case, this method will try to check if the where clause matches our GridTab data by generating following SQL SELECT:
	 *
	 * <pre>
	 * SELECT * FROM (SELECT ? AS Column1, ? AS Column2, ..., ? AS ColumnN) MyTable
	 * WHERE
	 *     Column1='Y' AND Column2='N'
	 * </pre>
	 *
	 * @task 02627
	 */
	private boolean isWhereClauseMatched(final GridTab tab)
	{
		final String trxName = null;

		final String whereClause = getWhereClause();
		if (Check.isEmpty(whereClause, true))
		{
			return true;
		}

		final StringBuilder sql = new StringBuilder();
		final List<Object> params = new ArrayList<>();
		for (int i = 0; i < tab.getFieldCount(); i++)
		{
			final GridField field = tab.getField(i);
			if (field.isVirtualColumn())
			{
				continue;
			}

			final String columnName = field.getColumnName();
			final Object value = field.getValue();

			if (log.isDebugEnabled())
			{
				log.debug("column: " + columnName + "=" + value + ", index=" + i);
			}

			if (sql.length() > 0)
			{
				sql.append(", ");
			}

			if (value == null)
			{

				// NOTE: If we are not specify the datatype, we get
				// "org.postgresql.util.PSQLException: ERROR: could not determine data type of parameter..."
				// see http://archives.postgresql.org/pgsql-jdbc/2006-08/msg00163.php
				// this case is for when we have those null fields in where clause
				if (DisplayType.isText(field.getDisplayType()))
				{
					sql.append("NULL::text");
				}
				else if (DisplayType.isID(field.getDisplayType()) || DisplayType.Integer == field.getDisplayType())
				{
					sql.append("NULL::integer");
				}
				else if (DisplayType.isNumeric(field.getDisplayType()))
				{
					sql.append("NULL::numeric");
				}
				else
				{
					sql.append("NULL");
				}
			}
			else if (value instanceof java.util.Date)
			{
				// NOTE: If we are not specify the datatype, we get
				// "org.postgresql.util.PSQLException: ERROR: could not determine data type of parameter..."
				// see http://archives.postgresql.org/pgsql-jdbc/2006-08/msg00163.php
				sql.append("?::timestamp");
				params.add(value);
			}
			else
			{
				sql.append("?");
				params.add(value);
			}
			sql.append(" AS ").append(columnName);
		}

		if (sql.length() <= 0)
		{
			return true;
		}

		sql.insert(0, "SELECT * FROM (SELECT ").append(") ")
				.append(tab.getTableName())
				.append(" WHERE ")
				.append(getWhereClause());

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), trxName);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			final boolean matched = rs.next();
			return matched;
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql.toString(), params);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	/**
	 *
	 * @param ctx
	 * @param whereClause
	 * @param trxName
	 * @return true if records selected by given <code>whereClause</code> are subject of this index
	 */
	public boolean isWhereClauseMatched(final Properties ctx, final String whereClause, final String trxName)
	{
		Check.assume(!Check.isEmpty(whereClause, true), "whereClause parameter not empty");

		// If this index does not have an where clause, it means all records are matched, so there is no need to do an actual query
		if (Check.isEmpty(getWhereClause(), true))
		{
			return true;
		}

		final StringBuilder whereClauseFinal = new StringBuilder()
				.append("(").append(getWhereClause()).append(")")
				.append(" AND ").append("(").append(whereClause).append(")");

		final String tableName = getTableName();
		final boolean matched = new Query(ctx, tableName, whereClauseFinal.toString(), trxName)
				.anyMatch();

		return matched;
	}

	public static List<I_AD_Index_Table> getAffectedIndexes(final GridTab tab, final boolean newRecord)
	{
		return MIndexTable.getByTableName(tab.getTableName())
				.stream()
				.filter(index -> index.isMatched(tab, newRecord))
				.collect(ImmutableList.toImmutableList());
	}

	public static String getBeforeChangeWarning(final GridTab tab, final boolean newRecord)
	{
		final List<I_AD_Index_Table> indexes = getAffectedIndexes(tab, newRecord);
		if (indexes.isEmpty())
		{
			return null;
		}
		// metas start: R.Craciunescu@metas.ro : 02280
		final int rowCount = tab.getRowCount();
		// metas end: R.Craciunescu@metas.ro : 02280

		final StringBuilder msg = new StringBuilder();
		for (final I_AD_Index_Table index : indexes)
		{
			if (Check.isEmpty(index.getBeforeChangeWarning()))
			{
				continue;
			}
			// metas start: R.Craciunescu@metas.ro : 02280
			// if the new entry is the only row, there is nothing to be changed, so a before change warning is not needed.
			if (rowCount == 1)
			{
				return null;
			}
			// metas end: R.Craciunescu@metas.ro : 02280
			if (msg.length() > 0)
			{
				msg.append("\n");
			}
			msg.append(index.getBeforeChangeWarning());
		}
		return msg.toString();
	}

	private static final boolean isValueChanged(final GridTab tab, final String columnName,
			final boolean newRecord)
	{
		final GridTable table = tab.getTableModel();
		final int index = table.findColumn(columnName);
		if (index == -1)
		{
			return false;
		}
		if (newRecord)
		{
			return true;
		}
		final Object valueOld = table.getOldValue(tab.getCurrentRow(), index);
		if (valueOld == null)
		{
			return false;
		}
		final Object value = tab.getValue(columnName);
		if (!valueOld.equals(value))
		{
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MTableIndex[");
		sb.append(get_ID()).append("-").append(getName()).append(
				",AD_Table_ID=").append(getAD_Table_ID()).append("]");
		return sb.toString();
	}

	private static class TableIndexesMap
	{
		private final ImmutableListMultimap<String, MIndexTable> indexesByTableName;
		private final ImmutableMap<String, MIndexTable> indexesByNameUC;

		public TableIndexesMap(final List<MIndexTable> indexes)
		{
			indexesByTableName = Multimaps.index(indexes, MIndexTable::getTableName);
			indexesByNameUC = Maps.uniqueIndex(indexes, index -> index.getName().toUpperCase());
		}

		public ImmutableList<MIndexTable> getByTableName(@NonNull final String tableName)
		{
			return indexesByTableName.get(tableName);
		}

		public MIndexTable getByNameIgnoringCase(@NonNull final String name)
		{
			String nameUC = name.toUpperCase();
			return indexesByNameUC.get(nameUC);
		}
	}
}
