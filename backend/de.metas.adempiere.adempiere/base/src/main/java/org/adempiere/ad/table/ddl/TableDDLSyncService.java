package org.adempiere.ad.table.ddl;

import com.google.common.collect.ImmutableList;
import de.metas.reflist.ReferenceId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TableDDLSyncService
{
	public boolean isSynchronizableColumn(@NonNull final AdColumnId adColumnId)
	{
		return retrieveAppDictColumnById(adColumnId).isPresent();
	}

	public List<String> syncToDatabase(@NonNull final AdColumnId adColumnId)
	{
		final ColumnDDL column = retrieveAppDictColumnById(adColumnId)
				.orElseThrow(() -> new AdempiereException("No synchronizable column found for " + adColumnId));

		return syncToDatabase(column);
	}

	public List<String> syncToDatabase(final ColumnDDL column)
	{
		final String tableName = column.getTableName();

		final List<String> sqlStatements;
		final boolean addingSingleColumn;
		if (isJdbcTableExists(tableName))
		{
			final JdbcColumn jdbcColumn = retrieveJdbcColumn(tableName, column.getColumnName());
			if (jdbcColumn != null)
			{
				// Update existing column
				sqlStatements = column.getSQLModify(column.isMandatory() != jdbcColumn.isMandatory());
				addingSingleColumn = false;
			}
			else
			{
				// No existing column
				sqlStatements = column.getSQLAdd();
				addingSingleColumn = true;
			}
		}
		else
		{
			// No DB table
			final TableDDL table = retrieveAppDictTable(tableName);
			sqlStatements = ImmutableList.of(table.getSQLCreate());
			addingSingleColumn = false;
		}

		//
		// Execute
		sqlStatements.forEach(sqlStatement -> executeSQL(tableName, sqlStatement, addingSingleColumn));

		return sqlStatements;
	}

	/**
	 * Executes the given SQL statement.
	 *
	 * @param tableName          the table name that needs to be changed
	 * @param sqlStatement       the DDL that needs to be executed
	 * @param addingSingleColumn tells if the given {@code sqlStatement} is about adding a (single) column, as opposed to creating a whole table.
	 *                           If this parameter's value is {@code true} and if {@link #isAddColumnDDL(String)} returns {@code true} on the given {@code statement},
	 *                           then the given statement is wrapped into an invocation of the {@code db_alter_table()} DB function.
	 *                           See that function and its documentation for more infos.
	 */
	private static void executeSQL(final String tableName, final String sqlStatement, final boolean addingSingleColumn)
	{
		if (addingSingleColumn && isAddColumnDDL(sqlStatement))
		{
			final String sql = MigrationScriptFileLoggerHolder.DDL_PREFIX + "SELECT public.db_alter_table(" + DB.TO_STRING(tableName) + "," + DB.TO_STRING(sqlStatement) + ")";
			final Object[] sqlParams = null; // IMPORTANT: don't use any parameters because we want to log this command to migration script file
			DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
		}
		else
		{
			DB.executeUpdateEx(sqlStatement, ITrx.TRXNAME_ThreadInherited);
		}
	}

	/**
	 * @return {@code true} if the given statement is something like {@code ... ALTER TABLE ... ADD COLUMN ...} (case-insensitive!).
	 */
	private static boolean isAddColumnDDL(final String statement)
	{
		if (Check.isEmpty(statement, true))
		{
			return false;
		}

		// example: ALTER TABLE public.C_BPartner ADD COLUMN AD_User_ID NUMERIC(10)
		return statement.matches("(?i).*alter table [^ ]+ add column .*");
	}

	private static boolean isJdbcTableExists(final String tableName)
	{
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = DB.getConnectionRO();
			final DatabaseMetaData md = conn.getMetaData();
			final String catalog = DB.getDatabase().getCatalog();
			final String schema = DB.getDatabase().getSchema();
			final String tableNameNorm = DB.normalizeDBIdentifier(tableName, md);

			//
			rs = md.getTables(catalog, schema, tableNameNorm, null);
			return rs.next();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex);
		}
		finally
		{
			DB.close(rs);
			DB.close(conn);
		}

	}

	@Nullable
	private static JdbcColumn retrieveJdbcColumn(@NonNull final String tableName, @NonNull final String columnName)
	{
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = DB.getConnectionRO();
			final DatabaseMetaData md = conn.getMetaData();
			final String catalog = DB.getDatabase().getCatalog();
			final String schema = DB.getDatabase().getSchema();
			final String tableNameNorm = DB.normalizeDBIdentifier(tableName, md);

			//
			rs = md.getColumns(catalog, schema, tableNameNorm, null);
			while (rs.next())
			{
				final String currColumnName = rs.getString("COLUMN_NAME");
				if (!currColumnName.equalsIgnoreCase(columnName))
				{
					continue;
				}

				// update existing column
				final boolean mandatory = DatabaseMetaData.columnNoNulls == rs.getInt("NULLABLE");

				return JdbcColumn.builder()
						.columnName(currColumnName)
						.mandatory(mandatory)
						.build();
			}

			// Column not found
			return null;
		}
		catch (SQLException ex)
		{
			throw new DBException(ex);
		}
		finally
		{
			DB.close(rs);
			DB.close(conn);
		}
	}

	private static TableDDL retrieveAppDictTable(@NonNull final String tableName)
	{
		final ImmutableList<ColumnDDL> columns = retrieveAppDictColumns(tableName, null);
		if (columns.isEmpty())
		{
			throw new AdempiereException("No table found or table is a view or table has no columns: " + tableName);
		}

		return TableDDL.builder()
				.tableName(tableName)
				.columns(columns)
				.build();
	}

	private static Optional<ColumnDDL> retrieveAppDictColumnById(@Nullable AdColumnId adColumnId)
	{
		final ImmutableList<ColumnDDL> columns = retrieveAppDictColumns(null, adColumnId);
		if (columns.isEmpty())
		{
			return Optional.empty();
		}
		else if (columns.size() == 1)
		{
			return Optional.of(columns.get(0));
		}
		else
		{
			// shall not happen
			throw new IllegalStateException("More than one column found for " + adColumnId + ": " + columns);
		}
	}

	private static ImmutableList<ColumnDDL> retrieveAppDictColumns(@Nullable final String tableName, @Nullable AdColumnId adColumnId)
	{
		final StringBuilder sqlWhereClause = new StringBuilder();
		final ArrayList<Object> sqlWhereClauseParams = new ArrayList<>();
		if (!Check.isBlank(tableName))
		{
			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append(" AND ");
			}
			sqlWhereClause.append("t.TableName=?");
			sqlWhereClauseParams.add(tableName);
		}
		if (adColumnId != null)
		{
			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append(" AND ");
			}
			sqlWhereClause.append("c.AD_Column_ID=?");
			sqlWhereClauseParams.add(adColumnId);
		}
		if (sqlWhereClause.length() <= 0)
		{
			throw new AdempiereException("Retrieving all columns from database is not allowed. Possible development mistake.");
		}

		final String sql = "SELECT"
				+ " t." + I_AD_Table.COLUMNNAME_TableName
				+ ", c." + I_AD_Column.COLUMNNAME_ColumnName
				+ ", c." + I_AD_Column.COLUMNNAME_AD_Reference_ID
				+ ", c." + I_AD_Column.COLUMNNAME_DefaultValue
				+ ", c." + I_AD_Column.COLUMNNAME_IsMandatory
				+ ", c." + I_AD_Column.COLUMNNAME_FieldLength
				+ ", c." + I_AD_Column.COLUMNNAME_IsKey
				+ ", c." + I_AD_Column.COLUMNNAME_IsParent
				+ ", c." + I_AD_Column.COLUMNNAME_DDL_NoForeignKey
				+ " FROM " + I_AD_Column.Table_Name + " c "
				+ " INNER JOIN " + I_AD_Table.Table_Name + " t ON t.AD_Table_ID=c.AD_Table_ID"
				+ " WHERE t.IsActive='Y' AND t.IsView='N'"
				+ " AND c.IsActive='Y'"
				+ " AND (c." + I_AD_Column.COLUMNNAME_ColumnSQL + " is null or length(TRIM(c.ColumnSQL)) = 0)" // no virtual columns
				+ " AND (" + sqlWhereClause + ")"
				+ " ORDER BY t.TableName, c.ColumnName";

		return DB.retrieveRows(sql, sqlWhereClauseParams, TableDDLSyncService::retrieveAppDictColumn);
	}

	private static ColumnDDL retrieveAppDictColumn(final ResultSet rs) throws SQLException
	{
		return ColumnDDL.builder()
				.tableName(rs.getString(I_AD_Table.COLUMNNAME_TableName))
				.columnName(rs.getString(I_AD_Column.COLUMNNAME_ColumnName))
				.referenceId(ReferenceId.ofRepoId(rs.getInt(I_AD_Column.COLUMNNAME_AD_Reference_ID)))
				.defaultValue(rs.getString(I_AD_Column.COLUMNNAME_DefaultValue))
				.mandatory(StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsMandatory)))
				.fieldLength(rs.getInt(I_AD_Column.COLUMNNAME_FieldLength))
				.isPrimaryKey(StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsKey)))
				.isParentLink(StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsParent)))
				.noForeignKey(StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_DDL_NoForeignKey)))
				.build();
	}
}
