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
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Info Class for Lookup SQL (ValueObject)
 *
 * @author Jorg Janke
 * @version $Id: MLookupInfo.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public final class MLookupInfo implements Serializable, Cloneable
{
	private static final transient Logger logger = LogManager.getLogger(MLookupInfo.class);

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
	private String Query;
	/** Table Name */
	private final String TableName;
	/** Key Column */
	private final String KeyColumn;
	/** Display Column SQL */
	private String displayColumnSQL = null;
	private List<ILookupDisplayColumn> displayColumns = Collections.emptyList();
	private String selectSqlPart = null;
	private String fromSqlPart = null;
	private String whereClauseSqlPart = null;
	/** SQL WHERE part (without WHERE keyword); this SQL includes context variables references */
	private String whereClauseDynamicSqlPart = null;
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
	private String QueryDirect = "";
	/** Parent Flag */
	private boolean IsParent = false;
	/** Key Flag */
	private boolean IsKey = false;
	// /** Validation code */
	// public String ValidationCode = "";
	// /** Validation flag */
	// public boolean IsValidated = true;
	private IValidationRule validationRule = NullValidationRule.instance;
	private IValidationRule _validationRuleEffective = null; // lazy

	/** Context */
	private Properties ctx = null;
	/** WindowNo */
	private int WindowNo;

	/** AD_Column_Info or AD_Process_Para */
	private int Column_ID;
	/** AD_Reference_ID */
	private int DisplayType;
	/** Real AD_Reference_ID */
	private int AD_Reference_Value_ID;
	/** CreadedBy?updatedBy */
	private boolean IsCreadedUpdatedBy = false;
	@Deprecated
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
		StringBuilder sb = new StringBuilder("MLookupInfo[")
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
			logger.error("", e);
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
	
	/** @return the whole SQL query, including SELECT, FROM, WHERE, ORDER BY */
	public String getSqlQuery()
	{
		return Query;
	}
	
	void setSqlQuery(final String sqlQuery)
	{
		this.Query = sqlQuery;
	}

	/** @return Direct Access Query (i.e. SELECT Key, Value, Name ... FROM TableName WHERE KeyColumn=?) */
	public String getSqlQueryDirect()
	{
		return this.QueryDirect;
	}
	
	void setSqlQueryDirect(final String sqlQueryDirect)
	{
		this.QueryDirect = sqlQueryDirect;
	}

	// metas
	/** @return effective validation rule */
	public IValidationRule getValidationRule()
	{
		if (_validationRuleEffective == null)
		{
			final IValidationRule whereClauseDynamicValidationRule;
			if(!Check.isEmpty(whereClauseDynamicSqlPart, true))
			{
				whereClauseDynamicValidationRule = Services.get(IValidationRuleFactory.class).createSQLValidationRule(whereClauseDynamicSqlPart);
			}
			else
			{
				whereClauseDynamicValidationRule = NullValidationRule.instance;
			}
			
			_validationRuleEffective = CompositeValidationRule.compose(validationRule, whereClauseDynamicValidationRule);
		}
		return _validationRuleEffective;
	}

	/* package */void setValidationRule(IValidationRule validationRule)
	{
		this.validationRule = validationRule;
		this._validationRuleEffective = null; // reset
	}

	public String getDisplayColumnSQL()
	{
		return displayColumnSQL;
	}

	/* package */void setDisplayColumnSQL(final String displayColumnSQL)
	{
		this.displayColumnSQL = displayColumnSQL;
	}

	void setDisplayColumns(final List<ILookupDisplayColumn> displayColumns)
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

	/** @return SELECT Key, Value, Name, IsActive, EntityType FROM ... (without WHERE!) */
	public String getSelectSqlPart()
	{
		return selectSqlPart;
	}

	/* package */void setSelectSqlPart(String selectSqlPart)
	{
		this.selectSqlPart = selectSqlPart;
	}

	/** @return SQL FROM part, with joins to translation tables if needed, without FROM keyword */
	public String getFromSqlPart()
	{
		return fromSqlPart;
	}

	/* package */void setFromSqlPart(final String fromSqlPart)
	{
		this.fromSqlPart = fromSqlPart;
	}

	/** @return SQL WHERE part (without WHERE keyword); this SQL is NOT including the {@link #getValidationRule()} code */
	public String getWhereClauseSqlPart()
	{
		return whereClauseSqlPart;
	}
	
	/* package */void setWhereClauseSqlPart(String whereClauseSqlPart)
	{
		this.whereClauseSqlPart = whereClauseSqlPart;
	}

	/* package */void setWhereClauseDynamicSqlPart(String whereClauseDynamicSqlPart)
	{
		this.whereClauseDynamicSqlPart = whereClauseDynamicSqlPart;
		this._validationRuleEffective = null; // reset
	}

	/** @return SQL ORDER BY part (without ORDER BY keyword) */
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
		return isNumericKey(KeyColumn);
	}
	
	public static final boolean isNumericKey(final String keyColumn)
	{
		if (keyColumn == null)
		{
			return false; // shall not happen
		}
		
		// FIXME: hardcoded
		if ("CreatedBy".equals(keyColumn) || "UpdatedBy".equals(keyColumn))
		{
			return true;
		}
		
		if (keyColumn.endsWith("_ID"))
		{
			return true;
		}
		return false;
	}

	public int getDisplayType()
	{
		return DisplayType;
	}

	/* package */void setDisplayType(final int displayType)
	{
		this.DisplayType = displayType;
	}
	
	public int getAD_Reference_Value_ID()
	{
		return AD_Reference_Value_ID;
	}
	
	void setAD_Reference_Value_ID(int aD_Reference_Value_ID)
	{
		AD_Reference_Value_ID = aD_Reference_Value_ID;
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
	
	void setWindowNo(int windowNo)
	{
		this.WindowNo = windowNo;
	}

	public boolean isParent()
	{
		return this.IsParent;
	}

	void setIsParent(final boolean isParent)
	{
		this.IsParent = isParent;
	}

	public int getAD_Column_ID()
	{
		return this.Column_ID;
	}
	
	void setAD_Column_ID(int column_ID)
	{
		this.Column_ID = column_ID;
	}

	void setAutoComplete(boolean autoComplete)
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
