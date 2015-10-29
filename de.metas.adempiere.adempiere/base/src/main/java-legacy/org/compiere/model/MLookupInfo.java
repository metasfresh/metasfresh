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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.util.Check;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 * Info Class for Lookup SQL (ValueObject)
 *
 * @author Jorg Janke
 * @version $Id: MLookupInfo.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MLookupInfo implements Serializable, Cloneable
{
	/**
	 * Get first AD_Reference_ID of a matching Reference Name.
	 * Can have SQL LIKE placeholders.
	 * (This is more a development tool than used for production)
	 * 
	 * @param referenceName reference name
	 * @return AD_Reference_ID
	 */
	public static int getAD_Reference_ID(String referenceName)
	{
		int retValue = 0;
		String sql = "SELECT AD_Reference_ID,Name,ValidationType,IsActive "
				+ "FROM AD_Reference WHERE Name LIKE ?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, referenceName);
			ResultSet rs = pstmt.executeQuery();
			//
			int i = 0;
			int id = 0;
			String refName = "";
			String validationType = "";
			boolean isActive = false;
			while (rs.next())
			{
				id = rs.getInt(1);
				if (i == 0)
					retValue = id;
				refName = rs.getString(2);
				validationType = rs.getString(3);
				isActive = rs.getString(4).equals("Y");
				CLogger.get().config("AD_Reference Name=" + refName + ", ID=" + id + ", Type=" + validationType + ", Active=" + isActive);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			CLogger.get().log(Level.SEVERE, sql, e);
		}
		return retValue;
	}   // getAD_Reference_ID

	// public static int getAD_Column_ID (String columnName) // metas: removed

	/**************************************************************************
	 * Constructor.
	 * (called from MLookupFactory)
	 * 
	 * @param sqlQuery SQL query
	 * @param tableName table name
	 * @param keyColumn key column
	 * @param zoomWindow zoom window
	 * @param zoomWindowPO PO zoom window
	 * @param zoomQuery zoom query
	 */
	/* package */MLookupInfo(final String sqlQuery,
			final String tableName, final String keyColumn,
			final int zoomWindow, final int zoomWindowPO, final MQuery zoomQuery)
	{
		super();
		if (sqlQuery == null)
			throw new IllegalArgumentException("SqlQuery is null");
		Query = sqlQuery;
		if (keyColumn == null)
			throw new IllegalArgumentException("KeyColumn is null");
		if (tableName == null)
			throw new IllegalArgumentException("TableName is null");
		TableName = tableName;
		KeyColumn = keyColumn;
		ZoomWindow = zoomWindow;
		ZoomWindowPO = zoomWindowPO;
		ZoomQuery = zoomQuery;
	}   // MLookupInfo

	static final long serialVersionUID = -7958664359250070233L;

	/** SQL Query */
	public String Query;
	/** Table Name */
	public final String TableName;
	/** Key Column */
	public final String KeyColumn;
	/** Display Column SQL */
	private String displayColumnSQL = null;
	private List<ILookupDisplayColumn> displayColumns = Collections.emptyList();
	private String selectSqlPart = null;
	private String fromSqlPart = null;
	private String whereClauseSqlPart = null;
	private String orderBySqlPart = null;
	/** True if this lookup does not need security validation (e.g. AD_Ref_Lists does not need security validation) */
	private boolean securityDisabled = false;
	/** Zoom Window */
	public int ZoomWindow;
	/** Zoom Window */
	public int ZoomWindowPO;
	/** Zoom Query */
	public MQuery ZoomQuery = null;

	/** Direct Access Query (i.e. SELECT Key, Value, Name ... FROM TableName WHERE KeyColumn=?) */
	public String QueryDirect = "";
	/** Parent Flag */
	public boolean IsParent = false;
	/** Key Flag */
	private boolean IsKey = false;
	// /** Validation code */
	// public String ValidationCode = "";
	// /** Validation flag */
	// public boolean IsValidated = true;
	private IValidationRule validationRule = NullValidationRule.instance;

	/** Context */
	private Properties ctx = null;
	/** WindowNo */
	public int WindowNo;

	/** AD_Column_Info or AD_Process_Para */
	public int Column_ID;
	/** AD_Reference_ID */
	private int DisplayType;
	/** Real AD_Reference_ID */
	public int AD_Reference_Value_ID;
	/** CreadedBy?updatedBy */
	private boolean IsCreadedUpdatedBy = false;
	public String InfoFactoryClass = null;
	private boolean autoComplete = false;
	private boolean queryHasEntityType = false;

	/**
	 * String representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MLookupInfo[")
				.append(KeyColumn)
				.append("-Direct=").append(QueryDirect)
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Clone
	 *
	 * @return deep copy
	 */
	public MLookupInfo cloneIt()
	{
		try
		{
			final MLookupInfo clone = (MLookupInfo)super.clone();
			return clone;
		}
		catch (Exception e)
		{
			CLogger.get().log(Level.SEVERE, "", e);
		}
		return null;
	}	// clone

	public Properties getCtx()
	{
		return ctx;
	}

	/* package */void setCtx(final Properties ctxNew)
	{
		Check.assumeNotNull(ctxNew, "ctxNew not null");
		this.ctx = ctxNew;
	}
	
	public String getSqlQuery()
	{
		return Query;
	}

	// metas
	public IValidationRule getValidationRule()
	{
		return validationRule;
	}

	/* package */void setValidationRule(IValidationRule validationRule)
	{
		this.validationRule = validationRule;
	}

	public String getQuery()
	{
		return Query;
	}

	public String getDisplayColumnSQL()
	{
		return displayColumnSQL;
	}

	/* package */void setDisplayColumnSQL(final String displayColumnSQL)
	{
		this.displayColumnSQL = displayColumnSQL;
	}

	public void setDisplayColumns(final List<ILookupDisplayColumn> displayColumns)
	{
		if (displayColumns == null || displayColumns.isEmpty())
		{
			this.displayColumns = Collections.emptyList();
		}
		else
		{
			this.displayColumns = Collections.unmodifiableList(displayColumns);
		}
	}

	public List<ILookupDisplayColumn> getDisplayColumns()
	{
		return displayColumns;
	}

	public String getSelectSqlPart()
	{
		return selectSqlPart;
	}

	/* package */void setSelectSqlPart(String selectSqlPart)
	{
		this.selectSqlPart = selectSqlPart;
	}

	public String getFromSqlPart()
	{
		return fromSqlPart;
	}

	/* package */void setFromSqlPart(final String fromSqlPart)
	{
		this.fromSqlPart = fromSqlPart;
	}

	public String getWhereClauseSqlPart()
	{
		return whereClauseSqlPart;
	}

	/* package */void setWhereClauseSqlPart(String whereClauseSqlPart)
	{
		this.whereClauseSqlPart = whereClauseSqlPart;
	}

	public String getOrderBySqlPart()
	{
		return orderBySqlPart;
	}

	/* package */void setOrderBySqlPart(String orderBySqlPart)
	{
		this.orderBySqlPart = orderBySqlPart;
	}

	/**
	 * 
	 * @return true if this lookup does not need security validation (e.g. AD_Ref_Lists does not need security validation)
	 */
	public boolean isSecurityDisabled()
	{
		return securityDisabled;
	}

	/* package */void setSecurityDisabled(final boolean securityDisabled)
	{
		this.securityDisabled = securityDisabled;
	}

	public String getTableName()
	{
		return TableName;
	}

	public String getKeyColumnFQ()
	{
		return KeyColumn;
	}

	public String getKeyColumn()
	{
		if (KeyColumn == null)
		{
			return null;
		}

		final int idx = KeyColumn.lastIndexOf('.');
		if (idx < 0)
		{
			return KeyColumn;
		}

		return KeyColumn.substring(idx + 1);
	}

	public boolean isCreadedUpdatedBy()
	{
		return this.IsCreadedUpdatedBy;
	}

	/* package */void setIsCreadedUpdatedBy(boolean isCreadedUpdatedBy)
	{
		this.IsCreadedUpdatedBy = isCreadedUpdatedBy;
	}

	public boolean isNumericKey()
	{
		final boolean isNumeric = KeyColumn != null && KeyColumn.endsWith("_ID");
		return isNumeric;
	}

	public int getDisplayType()
	{
		return DisplayType;
	}

	/* package */void setDisplayType(final int displayType)
	{
		this.DisplayType = displayType;
	}

	public boolean isKey()
	{
		return this.IsKey;
	}

	/* package */void setIsKey(final boolean isKey)
	{
		this.IsKey = isKey;
	}

	public int getWindowNo()
	{
		return this.WindowNo;
	}

	public boolean isParent()
	{
		return this.IsParent;
	}

	public int getAD_Column_ID()
	{
		return this.Column_ID;
	}

	public void setAutoComplete(boolean autoComplete)
	{
		this.autoComplete = autoComplete;
	}

	public boolean isAutoComplete()
	{
		return this.autoComplete;
	}

	void setQueryHasEntityType(boolean queryHasEntityType)
	{
		this.queryHasEntityType = queryHasEntityType;
	}
	
	public final boolean isQueryHasEntityType()
	{
		return queryHasEntityType;
	}

}   // MLookupInfo
