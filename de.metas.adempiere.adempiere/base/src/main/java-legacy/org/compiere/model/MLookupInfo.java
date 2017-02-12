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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MLookupFactory.LanguageInfo;
import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.TranslatableParameterizedString;
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
	/* package */ MLookupInfo(
			final String sqlQuery_BaseLang, final String sqlQuery_Trl //
			, final String tableName, final String keyColumn //
			, final int zoomWindow, final int zoomWindowPO, final MQuery zoomQuery //
	)
	{
		super();
		Check.assumeNotNull(sqlQuery_BaseLang, "Parameter sqlQuery_BaseLang is not null");
		Check.assumeNotNull(sqlQuery_Trl, "Parameter sqlQuery_Trl is not null");
		this.sqlQuery = TranslatableParameterizedString.of(CTXNAME_AD_Language, sqlQuery_BaseLang, sqlQuery_Trl);

		if (keyColumn == null)
			throw new IllegalArgumentException("KeyColumn is null");
		if (tableName == null)
			throw new IllegalArgumentException("TableName is null");
		TableName = tableName;
		KeyColumn = keyColumn;
		ZoomWindow = zoomWindow;
		ZoomWindowPO = zoomWindowPO;
		this.zoomQuery = zoomQuery;
	}   // MLookupInfo

	static final long serialVersionUID = -7958664359250070233L;

	/* package */static final CtxName CTXNAME_AD_Language = CtxName.parse(Env.CTXNAME_AD_Language);

	/** SQL Query */
	private final TranslatableParameterizedString sqlQuery;
	/** Table Name */
	private final String TableName;
	/** Key Column */
	private final String KeyColumn;
	/** Display Column SQL */
	private TranslatableParameterizedString displayColumnSQL = TranslatableParameterizedString.EMPTY;
	private List<ILookupDisplayColumn> displayColumns = Collections.emptyList();
	private TranslatableParameterizedString selectSqlPart = TranslatableParameterizedString.EMPTY;
	private TranslatableParameterizedString fromSqlPart = TranslatableParameterizedString.EMPTY;
	private String whereClauseSqlPart = null;
	/** SQL WHERE part (without WHERE keyword); this SQL includes context variables references */
	private String whereClauseDynamicSqlPart = null;
	private String orderBySqlPart = null;
	/** True if this lookup does not need security validation (e.g. AD_Ref_Lists does not need security validation) */
	private boolean securityDisabled = false;
	/** Zoom Window */
	public final int ZoomWindow;
	/** Zoom Window */
	public final int ZoomWindowPO;
	/** Zoom Query */
	private final MQuery zoomQuery;

	/** Direct Access Query (i.e. SELECT Key, Value, Name ... FROM TableName WHERE KeyColumn=?) */
	private TranslatableParameterizedString sqlQueryDirect = TranslatableParameterizedString.EMPTY;
	/** Parent Flag */
	private boolean IsParent = false;
	/** Key Flag */
	private boolean IsKey = false;
	private IValidationRule _validationRule = NullValidationRule.instance;
	private IValidationRule _validationRuleEffective = null; // lazy

	/** WindowNo */
	private int WindowNo;

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
				.append("-Direct=").append(sqlQueryDirect)
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Clone
	 *
	 * @return deep copy
	 */
	public MLookupInfo cloneIt(final int windowNo)
	{
		try
		{
			final MLookupInfo clone = (MLookupInfo)super.clone();
			clone.setWindowNo(windowNo);
			return clone;
		}
		catch (Exception e)
		{
			logger.error("Failed cloning: " + this, e);
		}
		return null;
	}	// clone

	/**
	 * WARNING: this method is supported to be used EXCLUSIVELLY in Swing UI
	 * 
	 * @return the whole SQL query, including SELECT, FROM, WHERE, ORDER BY
	 */
	public String getSqlQuery()
	{
		return getSqlQueryEffective().translate();
	}

	private final TranslatableParameterizedString getSqlQueryEffective()
	{
		if (isSecurityDisabled())
		{
			return sqlQuery;
		}

		// FIXME: we shall get rid of any context data as userRolePermissions from our built queries
		final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions();
		return _adRoleId2sqlQuery.computeIfAbsent(userRolePermissions.getAD_Role_ID(),
				(AD_Role_ID) -> sqlQuery.transform((sql) -> userRolePermissions.addAccessSQL(sql, TableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)));
	}

	private final Map<Integer, TranslatableParameterizedString> _adRoleId2sqlQuery = new ConcurrentHashMap<>();

	/**
	 * WARNING: this method is supported to be used EXCLUSIVELLY in Swing UI
	 * 
	 * @return Direct Access Query (i.e. SELECT Key, Value, Name ... FROM TableName WHERE KeyColumn=?)
	 */
	public String getSqlQueryDirect()
	{
		return sqlQueryDirect.translate();
	}

	void setSqlQueryDirect(final String sqlQueryDirect_BaseLang, final String sqlQueryDirect_Trl)
	{
		this.sqlQueryDirect = TranslatableParameterizedString.of(CTXNAME_AD_Language, sqlQueryDirect_BaseLang, sqlQueryDirect_Trl);
	}

	// metas
	/**
	 * @return effective validation rule
	 */
	public IValidationRule getValidationRule()
	{
		if (_validationRuleEffective == null)
		{
			final IValidationRule whereClauseDynamicValidationRule;
			if (!Check.isEmpty(whereClauseDynamicSqlPart, true))
			{
				whereClauseDynamicValidationRule = Services.get(IValidationRuleFactory.class).createSQLValidationRule(whereClauseDynamicSqlPart);
			}
			else
			{
				whereClauseDynamicValidationRule = NullValidationRule.instance;
			}

			_validationRuleEffective = CompositeValidationRule.compose(_validationRule, whereClauseDynamicValidationRule);
		}
		return _validationRuleEffective;
	}

	/* package */void setValidationRule(final IValidationRule validationRule)
	{
		this._validationRule = validationRule == null ? NullValidationRule.instance : validationRule;
		this._validationRuleEffective = null; // reset
	}

	public String getDisplayColumnSqlAsString()
	{
		return displayColumnSQL.translate();
	}

	public TranslatableParameterizedString getDisplayColumnSql()
	{
		return displayColumnSQL;
	}

	public String getDisplayColumnSQL(final LanguageInfo languageInfo)
	{
		return languageInfo.extractString(displayColumnSQL);
	}

	public String getDisplayColumnSQL_BaseLang()
	{
		return displayColumnSQL.getStringBaseLanguage();
	}

	public String getDisplayColumnSQL_Trl()
	{
		return displayColumnSQL.getStringTrlPattern();
	}

	/* package */void setDisplayColumnSQL(final String displayColumnSQL_BaseLang, final String displayColumnSQL_Trl)
	{
		this.displayColumnSQL = TranslatableParameterizedString.of(CTXNAME_AD_Language, displayColumnSQL_BaseLang, displayColumnSQL_Trl);
	}

	void setDisplayColumns(final List<ILookupDisplayColumn> displayColumns)
	{
		if (displayColumns == null || displayColumns.isEmpty())
		{
			this.displayColumns = ImmutableList.of();
		}
		else
		{
			this.displayColumns = ImmutableList.copyOf(displayColumns);
		}
	}

	public List<ILookupDisplayColumn> getDisplayColumns()
	{
		return displayColumns;
	}

	/**
	 * @return SELECT Key, Value, Name, IsActive, EntityType FROM ... (without WHERE!)
	 */
	public String getSelectSqlPartAsString()
	{
		return selectSqlPart.translate();
	}

	public String getSelectSqlPart_BaseLang()
	{
		return selectSqlPart.getStringBaseLanguage();
	}

	public String getSelectSqlPart_Trl()
	{
		return selectSqlPart.getStringTrlPattern();
	}

	public TranslatableParameterizedString getSelectSqlPart()
	{
		return selectSqlPart;
	}

	/* package */void setSelectSqlPart(final String selectSqlPart_BaseLang, final String selectSqlPart_Trl)
	{
		this.selectSqlPart = TranslatableParameterizedString.of(CTXNAME_AD_Language, selectSqlPart_BaseLang, selectSqlPart_Trl);
	}

	/**
	 * @return SQL FROM part, with joins to translation tables if needed, without FROM keyword
	 */
	public String getFromSqlPartAsString()
	{
		return fromSqlPart.translate();
	}

	public TranslatableParameterizedString getFromSqlPart()
	{
		return fromSqlPart;
	}

	public String getFromSqlPart(final LanguageInfo languageInfo)
	{
		return languageInfo.extractString(fromSqlPart);
	}

	public String getFromSqlPart_BaseLang()
	{
		return fromSqlPart.getStringBaseLanguage();
	}

	public String getFromSqlPart_Trl()
	{
		return fromSqlPart.getStringTrlPattern();
	}

	/* package */void setFromSqlPart(final String fromSqlPart_BaseLang, final String fromSqlPart_Trl)
	{
		this.fromSqlPart = TranslatableParameterizedString.of(CTXNAME_AD_Language, fromSqlPart_BaseLang, fromSqlPart_Trl);
	}

	/**
	 * @return static SQL WHERE part (without WHERE keyword); this SQL is NOT including the {@link #getValidationRule()} code
	 */
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

	/**
	 * @return SQL ORDER BY part (without ORDER BY keyword)
	 */
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

	public MQuery getZoomQuery()
	{
		return zoomQuery;
	}
}   // MLookupInfo
