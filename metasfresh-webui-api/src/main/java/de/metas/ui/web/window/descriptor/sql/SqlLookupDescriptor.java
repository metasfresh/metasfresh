package de.metas.ui.web.window.descriptor.sql;

import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.expression.api.ICachedStringExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.TranslatableParameterizedStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.db.DBConstants;
import org.adempiere.util.Check;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.util.CtxName;
import org.compiere.util.DisplayType;
import org.compiere.util.Evaluatees;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.TranslatableParameterizedString;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.factory.standard.DescriptorsFactoryHelper;
import de.metas.ui.web.window.model.lookup.GenericSqlLookupDataSourceFetcher;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.ui.web.window.model.sql.DocActionValidationRule;
import groovy.transform.Immutable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Immutable
public final class SqlLookupDescriptor implements LookupDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final CtxName SQL_PARAM_KeyId = CtxName.parse("SqlKeyId");

	public static final String SQL_PARAM_VALUE_ShowInactive_Yes = "Y"; // i.e. show all
	public static final String SQL_PARAM_VALUE_ShowInactive_No = "N";
	public static final CtxName SQL_PARAM_ShowInactive = CtxName.parse("SqlShowInactive/N");

	private static final int WINDOWNO_Dummy = 99999;

	private final String tableName;
	private final ICachedStringExpression sqlForFetchingExpression;
	private final ICachedStringExpression sqlForFetchingDisplayNameByIdExpression;
	private final int entityTypeIndex;
	private final INamePairPredicate postQueryPredicate;

	private final boolean highVolume;
	private final boolean numericKey;
	private final LookupSource lookupSourceType;

	private final Set<String> dependsOnFieldNames;

	private final GenericSqlLookupDataSourceFetcher lookupDataSourceFetcher;

	private SqlLookupDescriptor(final Builder builder)
	{
		super();

		tableName = builder.sqlTableName;
		sqlForFetchingExpression = builder.sqlForFetchingExpression;
		sqlForFetchingDisplayNameByIdExpression = builder.sqlForFetchingDisplayNameByIdExpression;
		entityTypeIndex = builder.entityTypeIndex;

		postQueryPredicate = builder.getPostQueryPredicate();

		numericKey = builder.numericKey;
		highVolume = builder.isHighVolume();
		lookupSourceType = builder.getLookupSourceType();

		dependsOnFieldNames = ImmutableSet.copyOf(builder.dependsOnFieldNames);

		lookupDataSourceFetcher = GenericSqlLookupDataSourceFetcher.of(this); // keep it last!
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", tableName)
				.add("highVolume", highVolume ? highVolume : null)
				.add("sqlForFetching", sqlForFetchingExpression.toOneLineString())
				.add("postQueryPredicate", postQueryPredicate == null || postQueryPredicate == INamePairPredicate.NULL ? null : postQueryPredicate)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(
				tableName,
				sqlForFetchingExpression,
				sqlForFetchingDisplayNameByIdExpression,
				entityTypeIndex,
				postQueryPredicate,
				//
				highVolume,
				numericKey
		// dependsOnFieldNames // not needed because it's computed
		// lookupSourceType // not needed because it's computed
		);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}

		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		final SqlLookupDescriptor other = (SqlLookupDescriptor)obj;
		return Objects.equals(tableName, other.tableName)
				&& Objects.equals(sqlForFetchingExpression, other.sqlForFetchingExpression)
				&& Objects.equals(sqlForFetchingDisplayNameByIdExpression, other.sqlForFetchingDisplayNameByIdExpression)
				&& entityTypeIndex == other.entityTypeIndex
				&& Objects.equals(postQueryPredicate, other.postQueryPredicate)
				&& highVolume == other.highVolume
				&& numericKey == other.numericKey
				// && Objects.equals(dependsOnFieldNames, other.dependsOnFieldNames) // not needed because it's computed
				// lookupSourceType // not needed because it's computed
		;
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return lookupDataSourceFetcher;
	}

	@Override
	public boolean isNumericKey()
	{
		return numericKey;
	}

	public String getTableName()
	{
		return tableName;
	}

	public IStringExpression getSqlForFetchingExpression()
	{
		return sqlForFetchingExpression;
	}

	public IStringExpression getSqlForFetchingDisplayNameByIdExpression()
	{
		return sqlForFetchingDisplayNameByIdExpression;
	}

	public IStringExpression getSqlForFetchingDisplayNameByIdExpression(final String sqlKeyColumn)
	{
		return sqlForFetchingDisplayNameByIdExpression.resolvePartial(Evaluatees.mapBuilder()
				.put(SQL_PARAM_KeyId, sqlKeyColumn)
				.put(SQL_PARAM_ShowInactive, SQL_PARAM_VALUE_ShowInactive_Yes)
				.build());
	}

	public int getEntityTypeIndex()
	{
		return entityTypeIndex;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return dependsOnFieldNames;
	}

	public INamePairPredicate getPostQueryPredicate()
	{
		return postQueryPredicate;
	}

	@Override
	public boolean hasParameters()
	{
		return !getSqlForFetchingExpression().getParameters().isEmpty()
				|| !getPostQueryPredicate().getParameters().isEmpty();
	}

	@Override
	public boolean isHighVolume()
	{
		return highVolume;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return lookupSourceType;
	}

	public static final class Builder
	{
		// Parameters
		private String columnName;
		private DocumentFieldWidgetType widgetType;
		private Integer displayType;
		private int AD_Reference_Value_ID = -1;
		private int AD_Val_Rule_ID = -1;
		private LookupScope scope = LookupScope.DocumentField;

		//
		// Built/prepared values
		private boolean numericKey;
		private Set<String> dependsOnFieldNames;

		private IValidationRule validationRule = NullValidationRule.instance;
		private String sqlTableName;
		private ICachedStringExpression sqlForFetchingExpression;
		private ICachedStringExpression sqlForFetchingDisplayNameByIdExpression;
		private int entityTypeIndex = -1;

		private Builder()
		{
			super();
		}

		public LookupDescriptorProvider buildProvider()
		{
			Check.assumeNotNull(displayType, "Parameter displayType is not null");
			
			return buildProvider(columnName, widgetType, displayType, AD_Reference_Value_ID, AD_Val_Rule_ID);
		}

		private static LookupDescriptorProvider buildProvider(
				final String sqlColumnName //
				, final DocumentFieldWidgetType widgetType
				, final int displayType //
				, final int AD_Reference_Value_ID //
				, final int AD_Val_Rule_ID //
		)
		{
			if(widgetType == DocumentFieldWidgetType.ProcessButton)
			{
				return LookupDescriptorProvider.NULL;
			}
			
			if (DisplayType.isAnyLookup(displayType)
					|| DisplayType.Button == displayType && AD_Reference_Value_ID > 0)
			{
				return LookupDescriptorProvider.fromMemoizingFunction(scope -> SqlLookupDescriptor.builder()
						.setColumnName(sqlColumnName)
						.setDisplayType(displayType)
						.setAD_Reference_Value_ID(AD_Reference_Value_ID)
						.setAD_Val_Rule_ID(AD_Val_Rule_ID)
						.setScope(scope)
						.build());
			}
			return LookupDescriptorProvider.NULL;
		}

		private SqlLookupDescriptor build()
		{
			Check.assumeNotEmpty(columnName, "columnName is not empty");

			final boolean IsParent = false;

			if (displayType == DisplayType.PAttribute && AD_Reference_Value_ID <= 0)
			{
				numericKey = true;
				setSqlExpressions_PAttribute(columnName);
				validationRule = NullValidationRule.instance;
				dependsOnFieldNames = ImmutableSet.of();
			}
			else
			{
				final MLookupInfo lookupInfo = MLookupFactory.getLookupInfo(WINDOWNO_Dummy, displayType, columnName, AD_Reference_Value_ID, IsParent, AD_Val_Rule_ID);

				numericKey = lookupInfo.isNumericKey();
				setSqlExpressions(lookupInfo);
				validationRule = lookupInfo.getValidationRule();

				//
				// Case: DocAction button => inject the DocActionValidationRule
				// FIXME: hardcoded
				if (displayType == DisplayType.Button && WindowConstants.FIELDNAME_DocAction.equals(columnName))
				{
					validationRule = CompositeValidationRule.compose(validationRule, DocActionValidationRule.instance);
				}

				dependsOnFieldNames = ImmutableSet.<String> builder()
						.addAll(validationRule.getPrefilterWhereClause().getParameters())
						.addAll(validationRule.getPostQueryFilter().getParameters())
						.build();
			}

			return new SqlLookupDescriptor(this);
		}

		private void setSqlExpressions(final MLookupInfo lookupInfo)
		{
			//
			// WHERE
			final IStringExpression sqlWhereFinal = buildSqlWhere(lookupInfo, scope);

			//
			// ORDER BY
			String lookup_SqlOrderBy = lookupInfo.getOrderBySqlPart();
			if (Check.isEmpty(lookup_SqlOrderBy, true))
			{
				lookup_SqlOrderBy = String.valueOf(MLookupFactory.COLUMNINDEX_DisplayName);
			}

			//
			// Set the SQLs
			{
				sqlTableName = lookupInfo.getTableName();
				sqlForFetchingExpression = buildSqlForFetching(lookupInfo, sqlWhereFinal, lookup_SqlOrderBy)
						.caching();
				sqlForFetchingDisplayNameByIdExpression = buildSqlForFetchingDisplayNameById(lookupInfo)
						.caching();

				if (lookupInfo.isQueryHasEntityType())
				{
					entityTypeIndex = MLookupFactory.COLUMNINDEX_EntityType;
				}
			}
		}

		private void setSqlExpressions_PAttribute(final String columnName)
		{
			final String tableName = I_M_AttributeSetInstance.Table_Name;
			final String keyColumnNameFQ = tableName + "." + I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID;
			final String displayColumnSql = tableName + "." + I_M_AttributeSetInstance.COLUMNNAME_Description;
			final CompositeStringExpression.Builder sqlSelectFrom = IStringExpression.composer()
					.append("SELECT ")
					.append(" ").append(keyColumnNameFQ) // Key
					.append(", NULL") // Value
					.append(",").append(displayColumnSql) // DisplayName
					.append(", M_AttributeSetInstance.IsActive") // IsActive
					.append(", NULL") // EntityType
					.append(" FROM ").append(tableName);
			//
			// WHERE
			final CompositeStringExpression.Builder sqlWhereFinal = IStringExpression.composer();
			{
				// Validation Rule's WHERE
				final IStringExpression validationRuleWhereClause = buildSqlWhereClauseFromValidationRule(validationRule, scope);
				if (!validationRuleWhereClause.isNullExpression())
				{
					sqlWhereFinal.appendIfNotEmpty("\n AND ");
					sqlWhereFinal.append(" /* validation rule */ ").append("(").append(validationRuleWhereClause).append(")");
				}

				// Filter's WHERE
				sqlWhereFinal.appendIfNotEmpty("\n AND ");
				sqlWhereFinal.append(" /* filter */ ").append("(").append(displayColumnSql).append(") ILIKE ").append(LookupDataSourceContext.PARAM_FilterSql);
			}

			//
			// ORDER BY
			String lookup_SqlOrderBy = I_M_AttributeSetInstance.COLUMNNAME_Description;
			if (Check.isEmpty(lookup_SqlOrderBy, true))
			{
				lookup_SqlOrderBy = String.valueOf(MLookupFactory.COLUMNINDEX_DisplayName);
			}

			//
			// Assemble the SQLs
			final IStringExpression sqlForFetching = IStringExpression.composer()
					.append(sqlSelectFrom) // SELECT ... FROM ...
					.append("\n WHERE \n").append(sqlWhereFinal) // WHERE
					.append("\n ORDER BY ").append(lookup_SqlOrderBy) // ORDER BY
					.append("\n OFFSET ").append(LookupDataSourceContext.PARAM_Offset.toStringWithMarkers())
					.append("\n LIMIT ").append(LookupDataSourceContext.PARAM_Limit.toStringWithMarkers()) // LIMIT
					.wrap(AccessSqlStringExpression.wrapper(tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)) // security
					.build();
			final IStringExpression sqlForFetchingDisplayNameById = IStringExpression.composer()
					.append("SELECT ").append(displayColumnSql) // SELECT
					.append("\n FROM ").append(tableName) // FROM
					.append("\n WHERE ").append(keyColumnNameFQ).append("=").append(SQL_PARAM_KeyId)
					.build();

			//
			// Set the SQLs
			{
				sqlTableName = tableName;
				sqlForFetchingExpression = sqlForFetching.caching();
				sqlForFetchingDisplayNameByIdExpression = sqlForFetchingDisplayNameById.caching();
			}
		}

		private static final IStringExpression buildSqlWhere(final MLookupInfo lookupInfo, final LookupScope scope)
		{
			final String tableName = lookupInfo.getTableName();
			final String lookup_SqlWhere = lookupInfo.getWhereClauseSqlPart();
			final TranslatableParameterizedString displayColumnSql = lookupInfo.getDisplayColumnSql();

			final CompositeStringExpression.Builder sqlWhereFinal = IStringExpression.composer();

			// Static lookup's WHERE
			if (!Check.isEmpty(lookup_SqlWhere, true))
			{
				sqlWhereFinal.append(" /* lookup where clause */ ").append("(").append(lookup_SqlWhere).append(")");
			}

			// Validation Rule's WHERE
			final IStringExpression validationRuleWhereClause = buildSqlWhereClauseFromValidationRule(lookupInfo.getValidationRule(), scope);
			if (!validationRuleWhereClause.isNullExpression())
			{
				sqlWhereFinal.appendIfNotEmpty("\n AND ");
				sqlWhereFinal.append(" /* validation rule */ ").append("(\n").append(validationRuleWhereClause).append("\n)\n");
			}

			// Filter's WHERE
			sqlWhereFinal.appendIfNotEmpty("\n AND ");
			sqlWhereFinal.append(" /* filter */ ")
					.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(displayColumnSql).append(", 1)")
					.append(" ILIKE ")
					.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(LookupDataSourceContext.PARAM_FilterSql).append(", 1)");

			// IsActive WHERE
			sqlWhereFinal.appendIfNotEmpty("\n AND ");
			sqlWhereFinal.append(" /* active */ ('").append(SQL_PARAM_ShowInactive).append("'='Y' OR ").append(tableName).append(".IsActive='Y')");

			return sqlWhereFinal.build();
		}

		private static final IStringExpression buildSqlWhereClauseFromValidationRule(final IValidationRule validationRule, final LookupScope scope)
		{
			final IStringExpression validationRuleWhereClause = validationRule.getPrefilterWhereClause();
			if (validationRuleWhereClause.isNullExpression())
			{
				return IStringExpression.NULL;
			}

			if (scope == LookupScope.DocumentFilter && !validationRuleWhereClause.getParameters().isEmpty())
			{
				// don't add the validation rule if it has parameters
				return IStringExpression.NULL;
			}

			return validationRuleWhereClause;
		}

		private final IStringExpression buildSqlForFetching(final MLookupInfo lookupInfo, final IStringExpression sqlWhere, final String sqlOrderBy)
		{
			final String tableName = lookupInfo.getTableName();
			return IStringExpression.composer()
					.append(lookupInfo.getSelectSqlPart()) // SELECT .. FROM ...
					.append("\n WHERE \n").append(sqlWhere) // WHERE
					.append("\n ORDER BY ").append(sqlOrderBy) // ORDER BY
					.append("\n OFFSET ").append(LookupDataSourceContext.PARAM_Offset) // OFFSET
					.append("\n LIMIT ").append(LookupDataSourceContext.PARAM_Limit) // LIMIT
					.wrapIfTrue(!lookupInfo.isSecurityDisabled(), AccessSqlStringExpression.wrapper(tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)) // security
					.build();
		}

		private final IStringExpression buildSqlForFetchingDisplayNameById(final MLookupInfo lookupInfo)
		{
			final IStringExpression displayColumnSQL = TranslatableParameterizedStringExpression.of(lookupInfo.getDisplayColumnSql());
			// useBaseLanguage ? lookupInfo.getDisplayColumnSQL_BaseLang() : lookupInfo.getDisplayColumnSQL_Trl();
			final IStringExpression fromSqlPart = TranslatableParameterizedStringExpression.of(lookupInfo.getFromSqlPart());
			// useBaseLanguage ? lookupInfo.getFromSqlPart_BaseLang() : lookupInfo.getFromSqlPart_Trl();
			final String keyColumnFQ = lookupInfo.getKeyColumnFQ();
			final int displayType = lookupInfo.getDisplayType();
			final String whereClauseSqlPart = lookupInfo.getWhereClauseSqlPart(); // assuming this is constant!

			final IStringExpression sqlForFetchingDisplayNameById = IStringExpression.composer()
					.append("SELECT ").append(displayColumnSQL) // SELECT ...
					.append("\n FROM ").append(fromSqlPart) // FROM
					.append("\n WHERE ").append(keyColumnFQ).append("=").append(SQL_PARAM_KeyId)
					.append(" ")
					// FIXME: make it better: this is actually adding the AD_Ref_List.AD_Reference_ID=....
					.append(DisplayType.List == displayType || DisplayType.Button == displayType ? " AND " + whereClauseSqlPart : "")
					.build();

			return sqlForFetchingDisplayNameById;
		}

		private INamePairPredicate getPostQueryPredicate()
		{
			final INamePairPredicate postQueryPredicate = validationRule.getPostQueryFilter();
			if (postQueryPredicate == null)
			{
				return INamePairPredicate.NULL;
			}

			if (scope == LookupScope.DocumentFilter && !postQueryPredicate.getParameters().isEmpty())
			{
				return INamePairPredicate.NULL;
			}

			return postQueryPredicate;
		}

		public Builder setColumnName(final String columnName)
		{
			this.columnName = columnName;
			return this;
		}
		
		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this.widgetType = widgetType;
			return this;
		}

		public Builder setDisplayType(final int displayType)
		{
			this.displayType = displayType;
			return this;
		}

		public Builder setAD_Reference_Value_ID(final int AD_Reference_Value_ID)
		{
			this.AD_Reference_Value_ID = AD_Reference_Value_ID;
			return this;
		}

		public Builder setAD_Val_Rule_ID(final int AD_Val_Rule_ID)
		{
			this.AD_Val_Rule_ID = AD_Val_Rule_ID;
			return this;
		}

		private boolean isHighVolume()
		{
			return DisplayType.TableDir != displayType && DisplayType.Table != displayType && DisplayType.List != displayType && DisplayType.Button != displayType;
		}

		private Builder setScope(final LookupScope scope)
		{
			Check.assumeNotNull(scope, "Parameter scope is not null");
			this.scope = scope;
			return this;
		}

		private LookupSource getLookupSourceType()
		{
			return DescriptorsFactoryHelper.extractLookupSource(displayType, AD_Reference_Value_ID);
		}
	}
}
