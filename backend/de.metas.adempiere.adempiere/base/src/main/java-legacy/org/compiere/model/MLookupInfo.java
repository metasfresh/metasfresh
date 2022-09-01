/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
package org.compiere.model;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.i18n.TranslatableParameterizedString;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.compiere.model.MLookupFactory.LanguageInfo;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Info Class for Lookup SQL (ValueObject)
 *
 * @author Jorg Janke
 * @version $Id: MLookupInfo.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public final class MLookupInfo implements Serializable, Cloneable
{
	private static final Logger logger = LogManager.getLogger(MLookupInfo.class);

	static final long serialVersionUID = -7958664359250070233L;

	/* package */static final CtxName CTXNAME_AD_Language = CtxNames.parse(Env.CTXNAME_AD_Language);

	private final TranslatableParameterizedString sqlQuery;

	private final String TableName;

	private final String KeyColumn;

	private TranslatableParameterizedString displayColumnSQL = TranslatableParameterizedString.EMPTY;
	private List<ILookupDisplayColumn> displayColumns = Collections.emptyList();

	private TranslatableParameterizedString descriptionColumnSQL = TranslatableParameterizedString.EMPTY;
	private TranslatableParameterizedString validationMsgColumnSQL = TranslatableParameterizedString.EMPTY;

	private TranslatableParameterizedString selectSqlPart = TranslatableParameterizedString.EMPTY;
	private TranslatableParameterizedString fromSqlPart = TranslatableParameterizedString.EMPTY;
	private String whereClauseSqlPart = null;

	private String orderBySqlPart = null;

	/**
	 * True if this lookup does not need security validation (e.g. AD_Ref_Lists does not need security validation)
	 */
	private boolean securityDisabled = false;

	private final AdWindowId zoomSO_Window_ID;
	private final AdWindowId zoomPO_Window_ID;
	private final AdWindowId zoomAD_Window_ID_Override;
	private final MQuery zoomQuery;

	/**
	 * Direct Access Query (i.e. SELECT Key, Value, Name ... FROM TableName WHERE KeyColumn=?)
	 */
	private TranslatableParameterizedString sqlQueryDirect = TranslatableParameterizedString.EMPTY;
	/**
	 * Parent Flag
	 */
	private boolean IsParent = false;
	/**
	 * Key Flag
	 */
	private boolean IsKey = false;
	private final EffectiveValidationRuleSupplier effectiveValidationRuleSupplier = new EffectiveValidationRuleSupplier();

	/**
	 * WindowNo
	 */
	private int WindowNo;

	/**
	 * AD_Reference_ID
	 */
	private int DisplayType;
	/**
	 * CreadedBy?updatedBy
	 */
	private boolean IsCreadedUpdatedBy = false;
	@Deprecated
	public String InfoFactoryClass = null;
	private boolean autoComplete = false;
	private boolean queryHasEntityType = false;
	private boolean showInactiveValues = false;

	private boolean translated = false;

	private TooltipType tooltipType;

	/* package */ MLookupInfo(
			@NonNull final String sqlQuery_BaseLang,
			@NonNull final String sqlQuery_Trl,
			@NonNull final String tableName,
			@NonNull final String keyColumn,
			final AdWindowId zoomSO_Window_ID,
			final AdWindowId zoomPO_Window_ID,
			final AdWindowId zoomAD_Window_ID_Override,
			final MQuery zoomQuery)
	{
		this.sqlQuery = TranslatableParameterizedString.of(CTXNAME_AD_Language, sqlQuery_BaseLang, sqlQuery_Trl);
		TableName = tableName;
		KeyColumn = keyColumn;
		this.zoomSO_Window_ID = zoomSO_Window_ID;
		this.zoomPO_Window_ID = zoomPO_Window_ID;
		this.zoomAD_Window_ID_Override = zoomAD_Window_ID_Override;
		this.zoomQuery = zoomQuery;
	}   // MLookupInfo

	/**
	 * String representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return "MLookupInfo[" + KeyColumn + "-Direct=" + sqlQueryDirect + "]";
	}

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
	}    // clone

	public AdWindowId getZoomSO_Window_ID()
	{
		return zoomSO_Window_ID;
	}

	public AdWindowId getZoomPO_Window_ID()
	{
		return zoomPO_Window_ID;
	}

	public AdWindowId getZoomAD_Window_ID_Override()
	{
		return zoomAD_Window_ID_Override;
	}

	/**
	 * WARNING: this method is supported to be used EXCLUSIVELLY in Swing UI
	 *
	 * @return the whole SQL query, including SELECT, FROM, WHERE, ORDER BY
	 */
	public String getSqlQuery()
	{
		return getSqlQueryEffective().translate();
	}

	private TranslatableParameterizedString getSqlQueryEffective()
	{
		if (isSecurityDisabled())
		{
			return sqlQuery;
		}

		// FIXME: we shall get rid of any context data as userRolePermissions from our built queries
		final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions();
		return _adRoleId2sqlQuery.computeIfAbsent(userRolePermissions.getRoleId(),
				(AD_Role_ID) -> sqlQuery.transform((sql) -> userRolePermissions.addAccessSQL(sql, TableName, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ)));
	}

	private final Map<RoleId, TranslatableParameterizedString> _adRoleId2sqlQuery = new ConcurrentHashMap<>();

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
		return effectiveValidationRuleSupplier.get();
	}

	/* package */void setValidationRule(@Nullable final IValidationRule validationRule)
	{
		this.effectiveValidationRuleSupplier.setValidationRule(validationRule);
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

	public TranslatableParameterizedString getDescriptionColumnSQL()
	{
		return descriptionColumnSQL;
	}

	public TranslatableParameterizedString getValidationMsgColumnSQL()
	{
		return validationMsgColumnSQL;
	}

	/* package */ void setValidationMsgColumnSQL(final String validationMsgColumnSQL_BaseLang)
	{
		this.validationMsgColumnSQL = TranslatableParameterizedString.of(CTXNAME_AD_Language, validationMsgColumnSQL_BaseLang, validationMsgColumnSQL_BaseLang);
	}

	/* package */ void setDescriptionColumnSQL(
			final String descriptionColumnSQL_BaseLang,
			final String descriptionColumnSQL_Trl)
	{
		this.descriptionColumnSQL = TranslatableParameterizedString.of(CTXNAME_AD_Language,
				descriptionColumnSQL_BaseLang,
				descriptionColumnSQL_Trl);
	}

	public String getActiveColumnSQL()
	{
		return getTableName() + ".IsActive";
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

	public TranslatableParameterizedString getFromSqlPart()
	{
		return fromSqlPart;
	}

	public String getFromSqlPart(final LanguageInfo languageInfo)
	{
		return languageInfo.extractString(fromSqlPart);
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

	/**
	 * Sets SQL WHERE part (without WHERE keyword); this SQL includes context variables references
	 */
	/* package */void setWhereClauseDynamicSqlPart(@Nullable final String whereClauseDynamicSqlPart)
	{
		this.effectiveValidationRuleSupplier.setWhereClauseDynamicSqlPart(whereClauseDynamicSqlPart);
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

	public static boolean isNumericKey(final String keyColumn)
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

	void setShowInactiveValues(boolean showInactiveValues)
	{
		this.showInactiveValues = showInactiveValues;
	}

	public boolean isShowInactiveValues()
	{
		return this.showInactiveValues;
	}

	void setQueryHasEntityType(boolean queryHasEntityType)
	{
		this.queryHasEntityType = queryHasEntityType;
	}

	public boolean isQueryHasEntityType()
	{
		return queryHasEntityType;
	}

	public MQuery getZoomQuery()
	{
		return zoomQuery;
	}

	void setTranslated(final boolean translated)
	{
		this.translated = translated;
	}

	public boolean isTranslated()
	{
		return translated;
	}

	public void setTooltipType(@NonNull final TooltipType tooltipType)
	{
		this.tooltipType = tooltipType;
	}

	public TooltipType getTooltipType()
	{
		return tooltipType;
	}

	//
	//
	//
	//
	//

	private static class EffectiveValidationRuleSupplier
	{
		private IValidationRule result = null;

		// params:
		/**
		 * SQL WHERE part (without WHERE keyword); this SQL includes context variables references
		 */
		private String whereClauseDynamicSqlPart = null;

		private IValidationRule validationRule = NullValidationRule.instance;

		public IValidationRule get()
		{
			IValidationRule result = this.result;
			if (result == null)
			{
				result = this.result = compute();
			}
			return result;
		}

		private IValidationRule compute()
		{
			final IValidationRule whereClauseDynamicValidationRule;
			if (!Check.isBlank(whereClauseDynamicSqlPart))
			{
				final IValidationRuleFactory validationRuleFactory = Services.get(IValidationRuleFactory.class);
				whereClauseDynamicValidationRule = validationRuleFactory.createSQLValidationRule(whereClauseDynamicSqlPart);
			}
			else
			{
				whereClauseDynamicValidationRule = NullValidationRule.instance;
			}

			return CompositeValidationRule.compose(validationRule, whereClauseDynamicValidationRule);
		}

		void setWhereClauseDynamicSqlPart(final String whereClauseDynamicSqlPart)
		{
			this.whereClauseDynamicSqlPart = whereClauseDynamicSqlPart;
			this.result = null; // reset
		}

		void setValidationRule(@Nullable final IValidationRule validationRule)
		{
			this.validationRule = validationRule == null ? NullValidationRule.instance : validationRule;
			this.result = null; // reset
		}
	}
}   // MLookupInfo
