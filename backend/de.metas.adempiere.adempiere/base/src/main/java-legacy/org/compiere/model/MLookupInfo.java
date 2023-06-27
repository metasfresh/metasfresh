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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.TranslatableParameterizedStringExpression;
import org.adempiere.ad.service.impl.LookupDisplayColumn;
import org.adempiere.ad.table.api.ColumnNameFQ;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.Serializable;
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

	@Getter @NonNull private final MLookupInfo.SqlQuery sqlQuery;
	@Getter private final AdWindowId zoomAD_Window_ID_Override;
	@Getter @Setter private TooltipType tooltipType;
	/**
	 * Direct Access Query (i.e. SELECT Key, Value, Name ... FROM TableName WHERE KeyColumn=?)
	 */
	private TranslatableParameterizedString sqlQueryDirect = TranslatableParameterizedString.EMPTY;
	private final EffectiveValidationRuleSupplier effectiveValidationRuleSupplier = new EffectiveValidationRuleSupplier();

	private final ConcurrentHashMap<RoleId, TranslatableParameterizedString> _adRoleId2sqlQuery = new ConcurrentHashMap<>();

	//
	// Legacy/Swing only fields
	@Deprecated @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PACKAGE) @NonNull private ImmutableList<LookupDisplayColumn> displayColumns = ImmutableList.of();
	@Deprecated @Getter(AccessLevel.PACKAGE) @Setter(AccessLevel.PACKAGE) private int displayType;
	@Deprecated @Getter(AccessLevel.PACKAGE) @Setter(AccessLevel.PACKAGE) private boolean isCreatedUpdatedBy = false;
	@Deprecated @Getter(AccessLevel.PACKAGE) @Setter(AccessLevel.PACKAGE) private int windowNo;
	@Deprecated @Getter(AccessLevel.PACKAGE) @Setter(AccessLevel.PACKAGE) private boolean isParent = false;
	@Deprecated @Getter(AccessLevel.PACKAGE) @Setter(AccessLevel.PACKAGE) private boolean autoComplete = false;
	@Deprecated @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PACKAGE) private boolean translated = false;
	@Deprecated @Getter(AccessLevel.PACKAGE) private final AdWindowId zoomSO_Window_ID;
	@Deprecated @Getter(AccessLevel.PACKAGE) private final AdWindowId zoomPO_Window_ID;
	@Deprecated @Getter(AccessLevel.PACKAGE) private final MQuery zoomQuery;
	@Deprecated @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PACKAGE) private String infoFactoryClass = null;

	/* package */ MLookupInfo(
			@NonNull final MLookupInfo.SqlQuery sqlQuery,
			final AdWindowId zoomSO_Window_ID,
			final AdWindowId zoomPO_Window_ID,
			final AdWindowId zoomAD_Window_ID_Override,
			final MQuery zoomQuery)
	{
		this.sqlQuery = sqlQuery;
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
		return "MLookupInfo[" + getSqlQuery().getKeyColumn() + "-Direct=" + sqlQueryDirect + "]";
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

	/**
	 * WARNING: this method is supported to be used EXCLUSIVELLY in Swing UI
	 *
	 * @return the whole SQL query, including SELECT, FROM, WHERE, ORDER BY
	 */
	public String getSqlQueryAsString()
	{
		return getSqlQueryEffective().translate();
	}

	private TranslatableParameterizedString getSqlQueryEffective()
	{
		final SqlQuery sqlQuery = getSqlQuery();
		if (sqlQuery.isSecurityDisabled())
		{
			return sqlQuery.toTranslatableParameterizedString();
		}
		else
		{
			// FIXME: we shall get rid of any context data as userRolePermissions from our built queries
			final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions();
			return _adRoleId2sqlQuery.computeIfAbsent(
					userRolePermissions.getRoleId(),
					(AD_Role_ID) -> {
						final TableName tableName = sqlQuery.getTableName();
						final TranslatableParameterizedString result = sqlQuery.toTranslatableParameterizedString();
						return result.transform((sql) -> userRolePermissions.addAccessSQL(sql, tableName.getAsString(), IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ));
					});
		}
	}

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

	public TableName getTableName() {return getSqlQuery().getTableName();}

	/**
	 * Sets SQL WHERE part (without WHERE keyword); this SQL includes context variables references
	 */
	/* package */void setWhereClauseDynamicSqlPart(@Nullable final String whereClauseDynamicSqlPart)
	{
		this.effectiveValidationRuleSupplier.setWhereClauseDynamicSqlPart(whereClauseDynamicSqlPart);
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
			if (whereClauseDynamicSqlPart != null && !Check.isBlank(whereClauseDynamicSqlPart))
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

	//
	//
	//
	//
	//

	@Value
	@Builder
	public static class SqlFromPart
	{
		@NonNull String sqlFrom_BaseLang;
		@NonNull String sqlFrom_Trl;

		public TranslatableParameterizedString toTranslatableParameterizedString()
		{
			return TranslatableParameterizedString.of(CTXNAME_AD_Language, sqlFrom_BaseLang, sqlFrom_Trl);
		}

		public IStringExpression toStringExpression()
		{
			return TranslatableParameterizedStringExpression.of(toTranslatableParameterizedString());
		}

	}

	@Value
	public static class SqlQuery
	{
		public static final int COLUMNINDEX_Key = 1;
		public static final int COLUMNINDEX_Value = 2;
		public static final int COLUMNINDEX_DisplayName = 3;
		public static final int COLUMNINDEX_IsActive = 4;
		public static final int COLUMNINDEX_Description = 5;
		public static final int COLUMNINDEX_EntityType = 6;
		public static final int COLUMNINDEX_ValidationInformation = 7;

		@NonNull TableName tableName;
		@NonNull ColumnNameFQ keyColumn;
		boolean isNumericKey;
		@NonNull String displayColumnSQL_BaseLang;
		@NonNull String displayColumnSQL_Trl;
		@NonNull ColumnNameFQ activeColumnSQL;
		@Nullable String descriptionColumnSQL_BaseLang;
		@Nullable String descriptionColumnSQL_Trl;
		@Nullable String validationMsgColumnSQL;
		boolean hasEntityTypeColumn;
		@NonNull SqlFromPart sqlFromPart;
		@Nullable String sqlWhereClauseStatic;
		@NonNull String sqlOrderBy;

		boolean showInactiveValues;
		/**
		 * True if this lookup does not need security validation (e.g. AD_Ref_Lists does not need security validation)
		 */
		boolean securityDisabled;

		@Builder
		private SqlQuery(
				@NonNull final ColumnNameFQ keyColumn,
				final boolean isNumericKey,
				@NonNull final String displayColumnSQL_BaseLang,
				@NonNull final String displayColumnSQL_Trl,
				@NonNull final ColumnNameFQ activeColumnSQL,
				@Nullable final String descriptionColumnSQL_BaseLang,
				@Nullable final String descriptionColumnSQL_Trl,
				@Nullable final String validationMsgColumnSQL,
				final boolean hasEntityTypeColumn,
				@NonNull final String sqlFrom_BaseLang,
				@NonNull final String sqlFrom_Trl,
				@Nullable final String sqlWhereClauseStatic,
				@NonNull final String sqlOrderBy,
				boolean showInactiveValues,
				boolean securityDisabled)
		{
			this.tableName = ColumnNameFQ.extractSingleTableName(keyColumn, activeColumnSQL);
			this.keyColumn = keyColumn;
			this.isNumericKey = isNumericKey;
			this.displayColumnSQL_BaseLang = displayColumnSQL_BaseLang;
			this.displayColumnSQL_Trl = displayColumnSQL_Trl;
			this.activeColumnSQL = activeColumnSQL;
			this.descriptionColumnSQL_BaseLang = descriptionColumnSQL_BaseLang;
			this.descriptionColumnSQL_Trl = descriptionColumnSQL_Trl;
			this.validationMsgColumnSQL = validationMsgColumnSQL;
			this.hasEntityTypeColumn = hasEntityTypeColumn;
			this.sqlFromPart = SqlFromPart.builder()
					.sqlFrom_BaseLang(sqlFrom_BaseLang)
					.sqlFrom_Trl(sqlFrom_Trl)
					.build();
			this.sqlWhereClauseStatic = sqlWhereClauseStatic;
			this.sqlOrderBy = sqlOrderBy;
			this.showInactiveValues = showInactiveValues;
			this.securityDisabled = securityDisabled;
		}

		public TranslatableParameterizedString toTranslatableParameterizedString()
		{
			final StringBuilder sqlQueryFinal_BaseLang = new StringBuilder(getSelectSqlPart_BaseLang());
			final StringBuilder sqlQueryFinal_Trl = new StringBuilder(getSelectSqlPart_Trl());
			if (!Check.isBlank(sqlWhereClauseStatic))
			{
				sqlQueryFinal_BaseLang.append(" WHERE ").append(sqlWhereClauseStatic);
				sqlQueryFinal_Trl.append(" WHERE ").append(sqlWhereClauseStatic);
			}
			sqlQueryFinal_BaseLang.append(" ORDER BY ").append(sqlOrderBy);
			sqlQueryFinal_Trl.append(" ORDER BY ").append(sqlOrderBy);

			return TranslatableParameterizedString.of(
					CTXNAME_AD_Language,
					sqlQueryFinal_BaseLang.toString(),
					sqlQueryFinal_Trl.toString());
		}

		public TranslatableParameterizedString getSelectSqlPart()
		{
			return TranslatableParameterizedString.of(CTXNAME_AD_Language, getSelectSqlPart_BaseLang(), getSelectSqlPart_Trl());
		}

		@NonNull
		public String getSelectSqlPart_BaseLang()
		{
			return "SELECT "
					+ (isNumericKey ? keyColumn.getAsString() : "NULL") // 1 - Key
					+ "," + (!isNumericKey ? keyColumn.getAsString() : "NULL") // 2 - Value
					+ "," + displayColumnSQL_BaseLang // 3 - Display
					+ "," + activeColumnSQL // 4 - IsActive
					+ "," + (descriptionColumnSQL_BaseLang != null ? descriptionColumnSQL_BaseLang : "NULL") // 5 - Description
					+ "," + (hasEntityTypeColumn ? tableName + ".EntityType" : "NULL") // 6 - EntityType
					+ " FROM " + sqlFromPart.getSqlFrom_BaseLang();
		}

		@NonNull
		public String getSelectSqlPart_Trl()
		{
			return "SELECT "
					+ (isNumericKey ? keyColumn.getAsString() : "NULL") // 1 - Key
					+ "," + (!isNumericKey ? keyColumn.getAsString() : "NULL") // 2 - Value
					+ "," + displayColumnSQL_Trl // 3 - Display
					+ "," + activeColumnSQL // 4 - IsActive
					+ "," + (descriptionColumnSQL_Trl != null ? descriptionColumnSQL_Trl : "NULL") // 5 - Description
					+ "," + (hasEntityTypeColumn ? tableName + ".EntityType" : "NULL") // 6 - EntityType
					+ " FROM " + sqlFromPart.getSqlFrom_Trl();
		}

		public TranslatableParameterizedString getDisplayColumnSql()
		{
			return TranslatableParameterizedString.of(CTXNAME_AD_Language, displayColumnSQL_BaseLang, displayColumnSQL_Trl);
		}

		public TranslatableParameterizedString getDescriptionColumnSql()
		{
			if (!Check.isBlank(descriptionColumnSQL_BaseLang))
			{
				return TranslatableParameterizedString.of(CTXNAME_AD_Language, descriptionColumnSQL_BaseLang, descriptionColumnSQL_Trl);
			}
			else
			{
				return TranslatableParameterizedString.EMPTY;
			}
		}

		public int getEntityTypeQueryColumnIndex() {return isHasEntityTypeColumn() ? COLUMNINDEX_EntityType : -1;}
	}

}   // MLookupInfo
