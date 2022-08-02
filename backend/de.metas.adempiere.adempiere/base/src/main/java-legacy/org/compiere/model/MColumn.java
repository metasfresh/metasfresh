/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Persistent Column Model
 *
 * @author Jorg Janke
 * @version $Id: MColumn.java,v 1.6 2006/08/09 05:23:49 jjanke Exp $
 */
public class MColumn extends X_AD_Column
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6543789555737635129L;

	/**
	 * Get MColumn from Cache
	 *
	 * @param ctx context
	 * @param AD_Column_ID id
	 * @return MColumn
	 */
	public static MColumn get(Properties ctx, int AD_Column_ID)
	{
		Integer key = new Integer(AD_Column_ID);
		MColumn retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MColumn(ctx, AD_Column_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	}	// get

	/**
	 * Get Column Name
	 *
	 * @param ctx context
	 * @param AD_Column_ID id
	 * @return Column Name or null
	 */
	public static String getColumnName(Properties ctx, int AD_Column_ID)
	{
		MColumn col = MColumn.get(ctx, AD_Column_ID);
		if (col == null || col.getAD_Column_ID() <= 0)
			return null;
		return col.getColumnName();
	}	// getColumnName

	/** Cache */
	private static CCache<Integer, MColumn> s_cache = new CCache<>("AD_Column", 20);

	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(MColumn.class);;

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_Column_ID
	 * @param trxName transaction
	 */
	public MColumn(Properties ctx, int AD_Column_ID, String trxName)
	{
		super(ctx, AD_Column_ID, trxName);
		if (AD_Column_ID == 0)
		{
			// setAD_Element_ID (0);
			// setAD_Reference_ID (0);
			// setColumnName (null);
			// setName (null);
			// setEntityType (null); // U
			setIsAlwaysUpdateable(false);	// N
			setIsEncrypted(false);
			setIsIdentifier(false);
			setIsKey(false);
			setIsMandatory(false);
			setIsParent(false);
			setIsSelectionColumn(false);
			setIsTranslated(false);
			setIsUpdateable(true);	// Y
			setVersion(BigDecimal.ZERO);
		}
	}	// MColumn

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MColumn(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MColumn

	/**
	 * Parent Constructor
	 *
	 * @param parent table
	 */
	public MColumn(MTable parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setAD_Table_ID(parent.getAD_Table_ID());
		setEntityType(parent.getEntityType());
	}	// MColumn

	/**
	 * Is Standard Column
	 *
	 * @return true for AD_Client_ID, etc.
	 */
	public boolean isStandardColumn()
	{
		final String columnName = getColumnName();
		return Services.get(IADTableDAO.class).isStandardColumn(columnName);
	}	// isStandardColumn

	/**
	 * Is Virtual Column
	 *
	 * @return true if virtual column
	 * @deprecated please use {@link IADTableDAO#isVirtualColumn(I_AD_Column)}
	 */
	@Deprecated
	public boolean isVirtualColumn()
	{
		final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
		return tableDAO.isVirtualColumn(this);
	}	// isVirtualColumn

	/**
	 * Is the Column Encrypted?
	 *
	 * @return true if encrypted
	 */
	public boolean isEncrypted()
	{
		String s = getIsEncrypted();
		return "Y".equals(s);
	}	// isEncrypted

	/**
	 * Set Encrypted
	 *
	 * @param IsEncrypted encrypted
	 */
	public void setIsEncrypted(boolean IsEncrypted)
	{
		setIsEncrypted(IsEncrypted ? "Y" : "N");
	}	// setIsEncrypted

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		int displayType = getAD_Reference_ID();
		if (DisplayType.isLOB(displayType))  	// LOBs are 0
		{
			if (getFieldLength() != 0)
				setFieldLength(0);
		}
		else if (getFieldLength() == 0)
		{
			if (DisplayType.isID(displayType))
				setFieldLength(10);
			else if (DisplayType.isNumeric(displayType))
				setFieldLength(14);
			else if (DisplayType.isDate(displayType))
				setFieldLength(7);
			else
			{
				throw new FillMandatoryException(COLUMNNAME_FieldLength);
			}
		}

		/**
		 * Views are not updateable
		 * UPDATE AD_Column c
		 * SET IsUpdateable='N', IsAlwaysUpdateable='N'
		 * WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE IsView='Y')
		 **/

		/* Diego Ruiz - globalqss - BF [1651899] - AD_Column: Avoid dup. SeqNo for IsIdentifier='Y' */
		if (isIdentifier())
		{
			int cnt = DB.getSQLValue(get_TrxName(), "SELECT COUNT(*) FROM AD_Column " +
					"WHERE AD_Table_ID=?" +
					" AND AD_Column_ID!=?" +
					" AND IsIdentifier='Y'" +
					" AND SeqNo=?",
					new Object[] { getAD_Table_ID(), getAD_Column_ID(), getSeqNo() });
			if (cnt > 0)
			{
				throw new AdempiereException("@SaveErrorNotUnique@ @" + COLUMNNAME_SeqNo + "@: " + getSeqNo());
			}
		}

		// Virtual Column
		if (isVirtualColumn())
		{
			if (isMandatory())
				setIsMandatory(false);
			if (isUpdateable())
				setIsUpdateable(false);
		}
		// Updateable
		if (isParent() || isKey())
			setIsUpdateable(false);
		if (isAlwaysUpdateable() && !isUpdateable())
			setIsAlwaysUpdateable(false);
		// Encrypted
		if (isEncrypted())
		{
			int dt = getAD_Reference_ID();
			if (isKey() || isParent() || isStandardColumn()
					|| isVirtualColumn() || isIdentifier() || isTranslated()
					|| DisplayType.isLookup(dt) || DisplayType.isLOB(dt)
					|| "DocumentNo".equalsIgnoreCase(getColumnName())
					|| "Value".equalsIgnoreCase(getColumnName())
					|| "Name".equalsIgnoreCase(getColumnName()))
			{
				log.warn("Encryption not sensible - " + getColumnName());
				setIsEncrypted(false);
			}
		}

		// Sync Terminology
		if ((newRecord || is_ValueChanged("AD_Element_ID"))
				&& getAD_Element_ID() != 0)
		{
			M_Element element = new M_Element(getCtx(), getAD_Element_ID(), get_TrxName());

			final String elementColumnName = element.getColumnName();
			Check.assumeNotNull(elementColumnName, "The element {} does not have a column name set", element);

			setColumnName(elementColumnName);
			setName(element.getName());
			setDescription(element.getDescription());
			setHelp(element.getHelp());
		}
		return true;
	}	// beforeSave

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		// Update Fields
		if (!newRecord)
		{
			if (is_ValueChanged(MColumn.COLUMNNAME_Name)
					|| is_ValueChanged(MColumn.COLUMNNAME_Description)
					|| is_ValueChanged(MColumn.COLUMNNAME_Help))
			{
				StringBuilder sql = new StringBuilder("UPDATE AD_Field SET Name=")
						.append(DB.TO_STRING(getName()))
						.append(", Description=").append(DB.TO_STRING(getDescription()))
						.append(", Help=").append(DB.TO_STRING(getHelp()))
						.append(" WHERE AD_Column_ID=").append(get_ID());
				int no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), get_TrxName());
				log.debug("afterSave - Fields updated #" + no);
			}
		}
		return success;
	}	// afterSave

	/**
	 * Create and return the SQL add column DDL sstatement.
	 *
	 * @param table table
	 * @return sql
	 */
	private List<String> getSQLAdd(final String tableName)
	{
		final List<String> sqlStatements = new ArrayList<>();

		sqlStatements.add(new StringBuilder("ALTER TABLE ")
				.append("public.") // if the table is already DLM'ed then there is a view with the same name in the dlm schema.
				.append(tableName)
				.append(" ADD COLUMN ") // not just "ADD" but "ADD COLUMN" to make it easier to distinguish the sort of this DDL further down the road.
				.append(getSQLDDL())
				.toString());

		final String sqlConstraint = getSQLConstraint(tableName);
		if (!Check.isEmpty(sqlConstraint, true))
		{
			sqlStatements.add(new StringBuilder("ALTER TABLE ")
					.append(tableName)
					.append(" ADD ").append(sqlConstraint)
					.toString());
		}

		return sqlStatements;
	}	// getSQLAdd

	/**
	 * Get SQL DDL
	 *
	 * @return columnName datataype ..
	 */
	/* package */String getSQLDDL()
	{
		if (isVirtualColumn())
		{
			return null;
		}
		StringBuilder sql = new StringBuilder(getColumnName())
				.append(" ").append(getSQLDataType());

		// Default
		String defaultValue = getDefaultValue();
		if (defaultValue != null
				&& defaultValue.length() > 0
				&& defaultValue.indexOf('@') == -1		// no variables
				&& (!(DisplayType.isID(getAD_Reference_ID()) && defaultValue.equals("-1"))))    // not for ID's with default -1
		{
			if (DisplayType.isText(getAD_Reference_ID())
					|| getAD_Reference_ID() == DisplayType.List
					|| getAD_Reference_ID() == DisplayType.YesNo
					// Two special columns: Defined as Table but DB Type is String
					|| getColumnName().equals("EntityType") || getColumnName().equals("AD_Language")
					|| (getAD_Reference_ID() == DisplayType.Button &&
							!(getColumnName().endsWith("_ID"))))
			{
				if (!defaultValue.startsWith("'") && !defaultValue.endsWith("'"))
					defaultValue = DB.TO_STRING(defaultValue);
			}
			sql.append(" DEFAULT ").append(defaultValue);
		}
		else
		{
			// avoid the explicit DEFAULT NULL, because apparently it causes an extra cost
			// if (!isMandatory())
			// sql.append(" DEFAULT NULL ");
			defaultValue = null;
		}

		// Inline Constraint
		if (getAD_Reference_ID() == DisplayType.YesNo)
			sql.append(" CHECK (").append(getColumnName()).append(" IN ('Y','N'))");

		// Null
		if (isMandatory())
			sql.append(" NOT NULL");
		return sql.toString();
	}	// getSQLDDL

	/**
	 * Get SQL Modify command
	 *
	 * @param tableName table name
	 * @param setNullOption generate null / not null statement
	 * @return sql separated by ;
	 */
	private List<String> getSQLModify(final String tableName, final boolean setNullOption)
	{
		final String columnName = getColumnName();
		final int displayType = getAD_Reference_ID();
		final String sqlDefaultValue = extractSqlDefaultValue(getDefaultValue(), columnName, displayType);
		final boolean mandatory = isMandatory();
		final String sqlDataType = getSQLDataType();

		final StringBuilder sqlBase_ModifyColumn = new StringBuilder("ALTER TABLE ")
				.append(tableName)
				.append(" MODIFY ").append(columnName);

		final List<String> sqlStatements = new ArrayList<>();

		//
		// Modify data type and DEFAULT value
		{
			final StringBuilder sqlDefault = new StringBuilder(sqlBase_ModifyColumn);

			// Datatype
			sqlDefault.append(" ").append(sqlDataType);

			// Default
			if (sqlDefaultValue != null)
			{
				sqlDefault.append(" DEFAULT ").append(sqlDefaultValue);
			}
			else
			{
				// avoid the explicit DEFAULT NULL, because apparently it causes an extra cost
				// if (!mandatory)
				// sqlDefault.append(" DEFAULT NULL ");
			}

			sqlStatements.add(DB.convertSqlToNative(sqlDefault.toString()));
		}

		//
		// Update NULL values
		if (mandatory && sqlDefaultValue != null && !sqlDefaultValue.isEmpty())
		{
			final String sqlSet = new StringBuilder("UPDATE ")
					.append(tableName)
					.append(" SET ").append(columnName)
					.append("=").append(sqlDefaultValue)
					.append(" WHERE ").append(columnName).append(" IS NULL")
					.toString();
			sqlStatements.add(sqlSet);
		}

		//
		// Set NULL/NOT NULL constraint
		if (setNullOption)
		{
			final StringBuilder sqlNull = new StringBuilder(sqlBase_ModifyColumn);
			if (mandatory)
				sqlNull.append(" NOT NULL");
			else
				sqlNull.append(" NULL");
			sqlStatements.add(DB.convertSqlToNative(sqlNull.toString()));
		}

		//
		return sqlStatements;
	}

	private static final String extractSqlDefaultValue(final String defaultValue, final String columnName, final int displayType)
	{
		if (defaultValue != null
				&& !defaultValue.isEmpty()
				&& defaultValue.indexOf('@') == -1		// no variables
				&& (!(DisplayType.isID(displayType) && defaultValue.equals("-1"))))    // not for ID's with default -1
		{
			if (DisplayType.isText(displayType)
					|| displayType == DisplayType.List
					|| displayType == DisplayType.YesNo
					// Two special columns: Defined as Table but DB Type is String
					|| columnName.equals("EntityType") || columnName.equals("AD_Language")
					|| (displayType == DisplayType.Button && !(columnName.endsWith("_ID"))))
			{
				if (!defaultValue.startsWith(DB.QUOTE_STRING) && !defaultValue.endsWith(DB.QUOTE_STRING))
				{
					return DB.TO_STRING(defaultValue);
				}
				else
				{
					return defaultValue;
				}
			}
			else
			{
				return defaultValue;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Get SQL Data Type
	 *
	 * @return e.g. NVARCHAR2(60)
	 */
	private String getSQLDataType()
	{
		final String columnName = getColumnName();
		final int displayType = getAD_Reference_ID();
		final int fieldLength = getFieldLength();
		return DB.getSQLDataType(displayType, columnName, fieldLength);
	}	// getSQLDataType

	/**
	 * Get Table Constraint
	 *
	 * @param tableName table name
	 * @return table constraint
	 */
	String getSQLConstraint(final String tableName)
	{
		if (isKey())
		{
			final String constraintName = tableName + "_Key";
			return "CONSTRAINT " + constraintName + " PRIMARY KEY (" + getColumnName() + ")";
		}
		else if (DisplayType.isID(getAD_Reference_ID()) && !isDDL_NoForeignKey())
		{
			// gh #539 Add missing FK constraints
			// create a FK-constraint, using the same view we also used to "manually" create FK-constraints in the past.

			// get an FK-constraint for this table, if any
			// this returns something like
			// "ALTER TABLE A_Asset_Change ADD CONSTRAINT ADepreciationCalcT_AAssetChang FOREIGN KEY (A_Depreciation_Calc_Type) REFERENCES A_Depreciation_Method DEFERRABLE INITIALLY DEFERRED;"
			final String fkConstraintDDL = DB.getSQLValueStringEx(ITrx.TRXNAME_None, "SELECT SqlText FROM db_columns_fk WHERE TableName=? AND ColumnName=?", tableName, getColumnName());
			if (!Check.isEmpty(fkConstraintDDL, true))
			{
				// remove the "ALTER TABLE ... ADD" and the trailing ";"
				// the (?iu) means the the pattern is created with Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
				// thanks to https://blogs.oracle.com/xuemingshen/entry/case_insensitive_matching_in_java
				final String constraint = fkConstraintDDL
						.replaceFirst("(?iu)ALTER *TABLE *" + tableName + " *ADD *", "")
						.replaceFirst(";$", "");
				return constraint;
			}
		}
		return "";
	}	// getConstraint

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Column_ID", getAD_Column_ID())
				.add("ColumnName", getColumnName())
				.toString();
	}

	// begin vpj-cd e-evolution
	/**
	 * get Column ID
	 *
	 * @param String windowName
	 * @param String columnName
	 * @return int retValue
	 */
	public static int getColumn_ID(String TableName, String columnName)
	{
		int m_table_id = MTable.getTable_ID(TableName);
		if (m_table_id == 0)
			return 0;

		int retValue = 0;
		String SQL = "SELECT AD_Column_ID FROM AD_Column WHERE AD_Table_ID = ?  AND columnname = ?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(SQL, null);
			pstmt.setInt(1, m_table_id);
			pstmt.setString(2, columnName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getInt(1);
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			s_log.error(SQL, e);
			retValue = -1;
		}
		return retValue;
	}
	// end vpj-cd e-evolution

	/**
	 * Get Table Id for a column
	 *
	 * @param ctx context
	 * @param AD_Column_ID id
	 * @param trxName transaction
	 * @return MColumn
	 */
	public static int getTable_ID(Properties ctx, int AD_Column_ID, String trxName)
	{
		String sqlStmt = "SELECT AD_Table_ID FROM AD_Column WHERE AD_Column_ID=?";
		return DB.getSQLValue(trxName, sqlStmt, AD_Column_ID);
	}

	/**
	 * Sync this column with the database
	 *
	 * @return SQLs
	 */
	public List<String> syncDatabase()
	{
		final MTable table = new MTable(getCtx(), getAD_Table_ID(), get_TrxName());
		table.set_TrxName(get_TrxName());  // otherwise table.getSQLCreate may miss current column
		if (table.get_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@ " + getAD_Table_ID());
		}

		final List<String> sqlStatements;
		final boolean addingSingleColumn;
		final String tableName = table.getTableName();
		if (isDBTableExists(tableName))
		{
			final DBColumn dbColumn = retrieveDBColumn(tableName, getColumnName());
			if (dbColumn != null)
			{
				// Update existing column
				sqlStatements = getSQLModify(tableName, isMandatory() != dbColumn.isMandatory());
				addingSingleColumn = false;
			}
			else
			{
				// No existing column
				sqlStatements = getSQLAdd(tableName);
				addingSingleColumn = true;
			}
		}
		else
		{
			// No DB table
			sqlStatements = ImmutableList.of(table.getSQLCreate());
			addingSingleColumn = false;
		}

		//
		// Execute
		sqlStatements.forEach(sqlStatement -> executeSQL(tableName, sqlStatement, addingSingleColumn));

		return sqlStatements;
	}

	private static boolean isDBTableExists(final String tableName)
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
			if (rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
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

	@Value
	@Builder
	private static final class DBColumn
	{
		final String columnName;
		final boolean mandatory; // i.e. NOT NULL
	}

	@Nullable
	private static DBColumn retrieveDBColumn(@NonNull final String tableName, @NonNull final String columnName)
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

				return DBColumn.builder()
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

	/**
	 * Executes the given SQL statement.
	 * 
	 * @param tableName the table name that needs to be changed
	 * @param sqlStatement the DDL that needs to be executed
	 * @param addingSingleColumn tells if the given {@code sqlStatement} is about adding a (single) column, as opposed to creating a whole table.
	 *            If this parameter's value is {@code true} and if {@link #isAddColumnDDL(String)} returns {@code true} on the given {@code statement},
	 *            then the given statement is wrapped into an invocation of the {@code db_alter_table()} DB function.
	 *            See that function and its documentation for more infos.
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
			DB.executeUpdateAndThrowExceptionOnFail(sqlStatement, ITrx.TRXNAME_ThreadInherited);
		}
	}

	/**
	 * 
	 * @param statement
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

	public static boolean isSuggestSelectionColumn(String columnName, boolean caseSensitive)
	{
		if (columnName == null || Check.isBlank(columnName))
			return false;
		//
		if (columnName.equals("Value") || (!caseSensitive && columnName.equalsIgnoreCase("Value")))
			return true;
		else if (columnName.equals("Name") || (!caseSensitive && columnName.equalsIgnoreCase("Name")))
			return true;
		else if (columnName.equals("DocumentNo") || (!caseSensitive && columnName.equalsIgnoreCase("DocumentNo")))
			return true;
		else if (columnName.equals("Description") || (!caseSensitive && columnName.equalsIgnoreCase("Description")))
			return true;
		else if (columnName.contains("Name") || (!caseSensitive && columnName.toUpperCase().contains("Name".toUpperCase())))
			return true;
		else if(columnName.equals("DocStatus") || (!caseSensitive && columnName.equalsIgnoreCase("DocStatus")))
			return true;
		else
			return false;
	}
}	// MColumn
