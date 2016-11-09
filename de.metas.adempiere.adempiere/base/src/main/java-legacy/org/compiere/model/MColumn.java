/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.CtxName;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

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
	private static CCache<Integer, MColumn> s_cache = new CCache<Integer, MColumn>("AD_Column", 20);

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
			setVersion(Env.ZERO);
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
		String columnName = getColumnName();
		if (columnName.equals("AD_Client_ID")
				|| columnName.equals("AD_Org_ID")
				|| columnName.equals("IsActive")
				|| columnName.startsWith("Created")
				|| columnName.startsWith("Updated"))
			return true;

		return false;
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
		if (DisplayType.isLOB(displayType)) 	// LOBs are 0
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
			// Make sure there are no context variables in ColumnSQL
			final String columnSql = getColumnSQL();
			if (columnSql != null && columnSql.indexOf(CtxName.NAME_Marker) >= 0)
			{
				throw new AdempiereException("Context variables are not allowed in ColumnSQL: " + columnSql);
			}
			
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
			setColumnName(element.getColumnName());
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
				StringBuffer sql = new StringBuffer("UPDATE AD_Field SET Name=")
						.append(DB.TO_STRING(getName()))
						.append(", Description=").append(DB.TO_STRING(getDescription()))
						.append(", Help=").append(DB.TO_STRING(getHelp()))
						.append(" WHERE AD_Column_ID=").append(get_ID())
						.append(" AND IsCentrallyMaintained='Y'");
				int no = DB.executeUpdate(sql.toString(), get_TrxName());
				log.debug("afterSave - Fields updated #" + no);
			}
		}
		return success;
	}	// afterSave

	/**
	 * Get SQL Add command
	 * 
	 * @param table table
	 * @return sql
	 */
	public String getSQLAdd(I_AD_Table table)
	{
		final String tableName = table.getTableName();

		final StringBuilder sql = new StringBuilder("ALTER TABLE ")
				.append(tableName)
				.append(" ADD ").append(getSQLDDL());

		final String constraint = getConstraint(tableName);
		if (constraint != null && constraint.length() > 0)
		{
			sql.append(DB.SQLSTATEMENT_SEPARATOR).append("ALTER TABLE ")
					.append(tableName)
					.append(" ADD ").append(constraint);
		}
		return sql.toString();
	}	// getSQLAdd

	/**
	 * Get SQL DDL
	 * 
	 * @return columnName datataype ..
	 */
	public String getSQLDDL()
	{
		if (isVirtualColumn())
			return null;

		StringBuffer sql = new StringBuffer(getColumnName())
				.append(" ").append(getSQLDataType());

		// Default
		String defaultValue = getDefaultValue();
		if (defaultValue != null
				&& defaultValue.length() > 0
				&& defaultValue.indexOf('@') == -1		// no variables
				&& (!(DisplayType.isID(getAD_Reference_ID()) && defaultValue.equals("-1"))))   // not for ID's with default -1
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
			if (!isMandatory())
				sql.append(" DEFAULT NULL ");
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
	 * @param table table
	 * @param setNullOption generate null / not null statement
	 * @return sql separated by ;
	 */
	public String getSQLModify(final I_AD_Table table, final boolean setNullOption)
	{
		final String tableName = table.getTableName();
		final String columnName = getColumnName();
		final int displayType = getAD_Reference_ID();
		String defaultValue = getDefaultValue();
		final boolean mandatory = isMandatory();
		final String sqlDataType = getSQLDataType();

		final StringBuilder sql = new StringBuilder();
		final StringBuilder sqlBase = new StringBuilder("ALTER TABLE ")
				.append(tableName)
				.append(" MODIFY ").append(columnName);

		// Default
		final StringBuilder sqlDefault = new StringBuilder(sqlBase)
				.append(" ").append(sqlDataType);
		if (defaultValue != null
				&& defaultValue.length() > 0
				&& defaultValue.indexOf('@') == -1		// no variables
				&& (!(DisplayType.isID(displayType) && defaultValue.equals("-1"))))   // not for ID's with default -1
		{
			if (DisplayType.isText(displayType)
					|| displayType == DisplayType.List
					|| displayType == DisplayType.YesNo
					// Two special columns: Defined as Table but DB Type is String
					|| columnName.equals("EntityType") || columnName.equals("AD_Language")
					|| (displayType == DisplayType.Button &&
							!(columnName.endsWith("_ID"))))
			{
				if (!defaultValue.startsWith("'") && !defaultValue.endsWith("'"))
					defaultValue = DB.TO_STRING(defaultValue);
			}
			sqlDefault.append(" DEFAULT ").append(defaultValue);
		}
		else
		{
			if (!mandatory)
				sqlDefault.append(" DEFAULT NULL ");
			defaultValue = null;
		}
		sql.append(DB.convertSqlToNative(sqlDefault.toString()));

		// Constraint

		// Null Values
		if (mandatory && defaultValue != null && defaultValue.length() > 0)
		{
			StringBuffer sqlSet = new StringBuffer("UPDATE ")
					.append(tableName)
					.append(" SET ").append(columnName)
					.append("=").append(defaultValue)
					.append(" WHERE ").append(columnName).append(" IS NULL");
			sql.append(DB.SQLSTATEMENT_SEPARATOR).append(sqlSet);
		}

		// Null
		if (setNullOption)
		{
			StringBuffer sqlNull = new StringBuffer(sqlBase);
			if (mandatory)
				sqlNull.append(" NOT NULL");
			else
				sqlNull.append(" NULL");
			sql.append(DB.SQLSTATEMENT_SEPARATOR).append(DB.convertSqlToNative(sqlNull.toString()));
		}
		//
		return sql.toString();
	}	// getSQLModify

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
	public String getConstraint(String tableName)
	{
		if (isKey())
		{
			final String constraintName = tableName + "_Key";
			return "CONSTRAINT " + constraintName + " PRIMARY KEY (" + getColumnName() + ")";
		}
		/**
		 * if (getAD_Reference_ID() == DisplayType.TableDir
		 * || getAD_Reference_ID() == DisplayType.Search)
		 * return "CONSTRAINT " ADTable_ADTableTrl
		 * + " FOREIGN KEY (" + getColumnName() + ") REFERENCES "
		 * + AD_Table(AD_Table_ID) ON DELETE CASCADE
		 **/

		return "";
	}	// getConstraint

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MColumn[");
		sb.append(get_ID()).append("-").append(getColumnName()).append("]");
		return sb.toString();
	}	// toString

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
	 * @return
	 */
	public String syncDatabase()
	{

		MTable table = new MTable(getCtx(), getAD_Table_ID(), get_TrxName());
		table.set_TrxName(get_TrxName());  // otherwise table.getSQLCreate may miss current column
		if (table.get_ID() == 0)
			throw new AdempiereException("@NotFound@ @AD_Table_ID@ " + getAD_Table_ID());

		// Find Column in Database
		Connection conn = null;
		try
		{
			conn = DB.getConnectionRO();
			DatabaseMetaData md = conn.getMetaData();
			String catalog = DB.getDatabase().getCatalog();
			String schema = DB.getDatabase().getSchema();
			String tableName = table.getTableName();
			if (md.storesUpperCaseIdentifiers())
			{
				tableName = tableName.toUpperCase();
			}
			else if (md.storesLowerCaseIdentifiers())
			{
				tableName = tableName.toLowerCase();
			}
			int noColumns = 0;
			String sql = null;
			//
			ResultSet rs = md.getColumns(catalog, schema, tableName, null);
			while (rs.next())
			{
				noColumns++;
				String columnName = rs.getString("COLUMN_NAME");
				if (!columnName.equalsIgnoreCase(getColumnName()))
					continue;

				// update existing column
				boolean notNull = DatabaseMetaData.columnNoNulls == rs.getInt("NULLABLE");
				sql = getSQLModify(table, isMandatory() != notNull);
				break;
			}
			rs.close();
			rs = null;

			// No Table
			if (noColumns == 0)
				sql = table.getSQLCreate();
			// No existing column
			else if (sql == null)
				sql = getSQLAdd(table);

			if (sql == null)
				return "No sql";

			int no = 0;
			if (sql.indexOf(DB.SQLSTATEMENT_SEPARATOR) == -1)
			{
				DB.executeUpdateEx(sql, get_TrxName());
			}
			else
			{
				String statements[] = sql.split(DB.SQLSTATEMENT_SEPARATOR);
				for (int i = 0; i < statements.length; i++)
				{
					DB.executeUpdateEx(statements[i], get_TrxName());
				}
			}

			return sql;

		}
		catch (SQLException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (Exception e)
				{
				}
			}
		}
	}

	public static boolean isSuggestSelectionColumn(String columnName, boolean caseSensitive)
	{
		if (Check.isEmpty(columnName, true))
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
		else if (columnName.indexOf("Name") != -1
				|| (!caseSensitive && columnName.toUpperCase().indexOf("Name".toUpperCase()) != -1))
			return true;
		else
			return false;
	}
}	// MColumn
