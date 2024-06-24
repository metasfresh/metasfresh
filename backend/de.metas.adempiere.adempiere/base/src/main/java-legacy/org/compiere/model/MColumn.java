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
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	/**
	 * Cache
	 */
	private static final CCache<Integer, MColumn> s_cache = new CCache<>("AD_Column", 20);

	/**
	 * Static Logger
	 */
	private static Logger s_log = LogManager.getLogger(MColumn.class);
	;

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
				int no = DB.executeUpdate(sql.toString(), get_TrxName());
				log.debug("afterSave - Fields updated #" + no);
			}
		}
		return success;
	}    // afterSave

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Column_ID", getAD_Column_ID())
				.add("ColumnName", getColumnName())
				.toString();
	}

	@Deprecated
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
}    // MColumn
