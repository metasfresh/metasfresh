/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.window.descriptor.sql;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.i18n.TranslatableParameterizedString;
import de.metas.security.IUserRolePermissions;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.security.permissions.Access;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.descriptor.factory.standard.DescriptorsFactoryHelper;
import de.metas.ui.web.window.model.lookup.GenericSqlLookupDataSourceFetcher;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.ui.web.window.model.sql.DocActionValidationRule;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ICachedStringExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.TranslatableParameterizedStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.db.DBConstants;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.util.DisplayType;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Immutable
public final class SqlLookupDescriptor implements ISqlLookupDescriptor
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static SqlLookupDescriptor cast(final LookupDescriptor descriptor)
	{
		return (SqlLookupDescriptor)descriptor;
	}

	public static LookupDescriptorProvider searchInTable(final String lookupTableName)
	{
		return builder()
				.setCtxTableName(null) // tableName
				.setCtxColumnName(InterfaceWrapperHelper.getKeyColumnName(lookupTableName))
				.setDisplayType(DisplayType.Search)
				.setReadOnlyAccess()
				.buildProvider();
	}

	public static LookupDescriptorProvider productAttributes()
	{
		return builder()
				.setCtxTableName(null) // tableName
				.setCtxColumnName(I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID)
				.setDisplayType(DisplayType.PAttribute)
				.setReadOnlyAccess()
				.buildProvider();
	}

	public static LookupDescriptorProvider listByAD_Reference_Value_ID(final int AD_Reference_Value_ID)
	{
		Check.assumeGreaterThanZero(AD_Reference_Value_ID, "AD_Reference_Value_ID");

		return builder()
				.setCtxTableName(null) // tableName
				.setCtxColumnName(null)
				.setDisplayType(DisplayType.List)
				.setAD_Reference_Value_ID(AD_Reference_Value_ID)
				.setReadOnlyAccess()
				.buildProvider();
	}

	/**
	 * @param AD_Reference_Value_ID has to be > 0
	 * @param AD_Val_Rule_ID        may be <= 0
	 */
	public static LookupDescriptorProvider searchByAD_Val_Rule_ID(
			final int AD_Reference_Value_ID,
			final int AD_Val_Rule_ID)
	{
		Check.assumeGreaterThanZero(AD_Reference_Value_ID, "AD_Reference_Value_ID");

		return builder()
				.setCtxTableName(null) // tableName
				.setCtxColumnName(null)
				.setAD_Reference_Value_ID(AD_Reference_Value_ID)
				.setAD_Val_Rule_ID(AD_Val_Rule_ID)
				.setDisplayType(DisplayType.Search)
				.setReadOnlyAccess()
				.buildProvider();
	}

	private static final int WINDOWNO_Dummy = 99999;

	private final Optional<String> tableName;
	private final Optional<WindowId> zoomIntoWindowId;
	private final SqlForFetchingLookups sqlForFetchingExpression;
	private final SqlForFetchingLookupById sqlForFetchingLookupByIdExpression;
	private final int entityTypeIndex;
	private final INamePairPredicate postQueryPredicate;

	private final boolean highVolume;
	private final boolean numericKey;

	private final LookupSource lookupSourceType;
	private final ImmutableSet<String> dependsOnFieldNames;
	private final ImmutableSet<String> dependsOnTableNames;
	private final GenericSqlLookupDataSourceFetcher lookupDataSourceFetcher;
	@NonNull
	private final TooltipType tooltipType;

	private SqlLookupDescriptor(final Builder builder)
	{
		tableName = Optional.of(builder.sqlTableName);
		zoomIntoWindowId = builder.getZoomIntoWindowId();
		sqlForFetchingExpression = builder.sqlForFetchingExpression;
		sqlForFetchingLookupByIdExpression = builder.sqlForFetchingLookupByIdExpression;
		entityTypeIndex = builder.entityTypeIndex;

		postQueryPredicate = builder.getPostQueryPredicate();

		numericKey = builder.numericKey;
		highVolume = builder.isHighVolume();

		lookupSourceType = builder.getLookupSourceType();
		dependsOnFieldNames = ImmutableSet.copyOf(builder.dependsOnFieldNames);
		dependsOnTableNames = ImmutableSet.copyOf(builder.getDependsOnTableNames());
		tooltipType = builder.getTooltipType();

		lookupDataSourceFetcher = GenericSqlLookupDataSourceFetcher.of(this); // keep it last!
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", tableName)
				.add("zoomIntoWindowId", zoomIntoWindowId.orElse(null))
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
				zoomIntoWindowId,
				sqlForFetchingExpression,
				sqlForFetchingLookupByIdExpression,
				entityTypeIndex,
				postQueryPredicate,
				//
				highVolume,
				numericKey
				// dependsOnFieldNames // not needed because it's computed
				// lookupSourceType // not needed because it's computed
				// lookupDataSourceFetcher // not needed because it's computed
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
				&& Objects.equals(zoomIntoWindowId, other.zoomIntoWindowId)
				&& Objects.equals(sqlForFetchingExpression, other.sqlForFetchingExpression)
				&& Objects.equals(sqlForFetchingLookupByIdExpression, other.sqlForFetchingLookupByIdExpression)
				&& entityTypeIndex == other.entityTypeIndex
				&& Objects.equals(postQueryPredicate, other.postQueryPredicate)
				&& highVolume == other.highVolume
				&& numericKey == other.numericKey
				// dependsOnFieldNames // not needed because it's computed
				// lookupSourceType // not needed because it's computed
				// lookupDataSourceFetcher // not needed because it's computed
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

	@Override
	public Optional<String> getTableName()
	{
		return tableName;
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return zoomIntoWindowId;
	}

	public SqlForFetchingLookups getSqlForFetchingExpression()
	{
		return sqlForFetchingExpression;
	}

	@Override
	public SqlForFetchingLookupById getSqlForFetchingLookupByIdExpression()
	{
		return sqlForFetchingLookupByIdExpression;
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

	@Override
	public Set<String> getDependsOnTableNames()
	{
		return dependsOnTableNames;
	}

	@Override
	public TooltipType getTooltipType()
	{
		return tooltipType;
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

	@ToString(of = { "sqlTableName", "ctxTableName", "ctxColumnName", "widgetType" })
	public static final class Builder
	{
		// Parameters
		private String ctxColumnName;
		private String ctxTableName;

		private DocumentFieldWidgetType widgetType;
		private Integer displayType;
		private int AD_Reference_Value_ID = -1;
		private int AD_Val_Rule_ID = -1;
		private LookupScope scope = LookupScope.DocumentField;
		private Access requiredAccess = null;

		//
		// Built/prepared values
		private boolean numericKey;
		private Set<String> dependsOnFieldNames;

		private final List<IValidationRule> validationRules = new ArrayList<>();
		private IValidationRule validationRuleEffective = NullValidationRule.instance;
		private String sqlTableName;
		private SqlForFetchingLookups sqlForFetchingExpression;
		private SqlForFetchingLookupById sqlForFetchingLookupByIdExpression;
		private int entityTypeIndex = -1;

		private AdWindowId zoomIntoAdWindowId = null;
		@NonNull
		private TooltipType tooltipType = TooltipType.DEFAULT;

		private Builder()
		{
		}

		public LookupDescriptorProvider buildProvider()
		{
			Check.assumeNotNull(displayType, "Parameter displayType is not null");

			return buildProvider(ctxTableName, ctxColumnName, widgetType, displayType, AD_Reference_Value_ID, AD_Val_Rule_ID, validationRules);
		}

		public LookupDescriptor buildForDefaultScope()
		{
			return buildProvider()
					.provide()
					.orElseThrow(() -> new AdempiereException("No lookup for " + this));
		}

		private static LookupDescriptorProvider buildProvider(
				final String sqlTableName,
				final String sqlColumnName,
				final DocumentFieldWidgetType widgetType, final int displayType,
				final int AD_Reference_Value_ID,
				final int AD_Val_Rule_ID,
				final List<IValidationRule> additionalValidationRules)
		{
			if (widgetType == DocumentFieldWidgetType.ProcessButton)
			{
				return LookupDescriptorProviders.NULL;
			}
			else if (DisplayType.isAnyLookup(displayType)
					|| DisplayType.Button == displayType && AD_Reference_Value_ID > 0)
			{
				return LookupDescriptorProviders.fromMemoizingFunction(scope -> SqlLookupDescriptor.builder()
						.setCtxTableName(sqlTableName)
						.setCtxColumnName(sqlColumnName)
						.setDisplayType(displayType)
						.setAD_Reference_Value_ID(AD_Reference_Value_ID)
						.setAD_Val_Rule_ID(AD_Val_Rule_ID)
						.setScope(scope)
						.addValidationRules(additionalValidationRules)
						.build());
			}
			else
			{
				return LookupDescriptorProviders.NULL;
			}
		}

		private SqlLookupDescriptor build()
		{
			final boolean IsParent = false;

			if (displayType == DisplayType.PAttribute && AD_Reference_Value_ID <= 0)
			{
				numericKey = true;
				validationRuleEffective = NullValidationRule.instance;
				dependsOnFieldNames = ImmutableSet.of();
				setSqlExpressions_PAttribute();
			}
			else
			{
				final MLookupInfo lookupInfo = MLookupFactory.getLookupInfo(
						WINDOWNO_Dummy,
						displayType,
						ctxTableName,
						ctxColumnName,
						AD_Reference_Value_ID,
						IsParent,
						AD_Val_Rule_ID);

				numericKey = lookupInfo.isNumericKey();
				validationRuleEffective = extractValidationRule(lookupInfo);
				dependsOnFieldNames = ImmutableSet.<String>builder()
						.addAll(validationRuleEffective.getPrefilterWhereClause().getParameterNames())
						.addAll(validationRuleEffective.getPostQueryFilter().getParameters())
						.build();
				setSqlExpressions(lookupInfo);
			}

			return new SqlLookupDescriptor(this);
		}

		private IValidationRule extractValidationRule(final MLookupInfo lookupInfo)
		{
			final CompositeValidationRule.Builder validationRuleBuilder = CompositeValidationRule.builder();
			validationRuleBuilder.add(lookupInfo.getValidationRule());

			//
			// Case: DocAction button => inject the DocActionValidationRule
			// FIXME: hardcoded
			if (displayType == DisplayType.Button && WindowConstants.FIELDNAME_DocAction.equals(ctxColumnName))
			{
				validationRuleBuilder.add(DocActionValidationRule.instance);
			}

			// Additional validation rules registered
			validationRules.forEach(validationRuleBuilder::add);

			return validationRuleBuilder.build();
		}

		private void setSqlExpressions(final MLookupInfo lookupInfo)
		{
			//
			// WHERE
			final IStringExpression sqlWhereFinal = buildSqlWhere(lookupInfo, scope, validationRuleEffective);

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
				zoomIntoAdWindowId = lookupInfo.getZoomAD_Window_ID_Override();
				sqlForFetchingExpression = buildSqlForFetching(lookupInfo, sqlWhereFinal, lookup_SqlOrderBy);
				sqlForFetchingLookupByIdExpression = buildSqlForFetchingById(lookupInfo);

				if (lookupInfo.isQueryHasEntityType())
				{
					entityTypeIndex = MLookupFactory.COLUMNINDEX_EntityType;
				}
			}

			if (lookupInfo.getTooltipType() != null)
			{
				tooltipType = lookupInfo.getTooltipType();
			}
		}

		private void setSqlExpressions_PAttribute()
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
				final IStringExpression validationRuleWhereClause = buildSqlWhereClauseFromValidationRule(validationRuleEffective, scope);
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
			final SqlForFetchingLookups sqlForFetching = SqlForFetchingLookups.builder()
					.sql(IStringExpression.composer()
							.append(sqlSelectFrom) // SELECT ... FROM ...
							.append("\n WHERE \n").append(sqlWhereFinal) // WHERE
							.append("\n ORDER BY ").append(lookup_SqlOrderBy) // ORDER BY
							.append("\n OFFSET ").append(SqlForFetchingLookups.PARAM_Offset.toStringWithMarkers())
							.append("\n LIMIT ").append(SqlForFetchingLookups.PARAM_Limit.toStringWithMarkers()) // LIMIT
							.wrap(AccessSqlStringExpression.wrapper(tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, getRequiredAccess(tableName))) // security
							.build()
							.caching())
					.build();

			final SqlForFetchingLookupById sqlForFetchingLookupById = SqlForFetchingLookupById.builder()
					.sql(IStringExpression.composer()
							.append("SELECT ").append("ARRAY[").append(displayColumnSql).append(", NULL]")
							.append("\n FROM ").append(tableName) // FROM
							.append("\n WHERE ").append(keyColumnNameFQ).append("=").append(SqlForFetchingLookupById.SQL_PARAM_KeyId)
							.build()
							.caching())
					.build();

			//
			// Set the SQLs
			{
				sqlTableName = tableName;
				sqlForFetchingExpression = sqlForFetching;
				sqlForFetchingLookupByIdExpression = sqlForFetchingLookupById;
			}
		}

		private static IStringExpression buildSqlWhere(final MLookupInfo lookupInfo, final LookupScope scope, final IValidationRule validationRuleEffective)
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
			final IStringExpression validationRuleWhereClause = buildSqlWhereClauseFromValidationRule(validationRuleEffective, scope);
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
			if (!lookupInfo.isShowInactiveValues())
			{
				sqlWhereFinal.appendIfNotEmpty("\n AND ");
				sqlWhereFinal.append(" /* active */ ('").append(SqlForFetchingLookupById.SQL_PARAM_ShowInactive)
						.append("'='Y' OR ").append(tableName).append(".IsActive='Y')");
			}

			return sqlWhereFinal.build();
		}

		private static IStringExpression buildSqlWhereClauseFromValidationRule(final IValidationRule validationRule, final LookupScope scope)
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

		private SqlForFetchingLookups buildSqlForFetching(final MLookupInfo lookupInfo, final IStringExpression sqlWhere, final String sqlOrderBy)
		{
			final String tableName = lookupInfo.getTableName();
			return SqlForFetchingLookups.builder()
					.sql(IStringExpression.composer()
							.append(lookupInfo.getSelectSqlPart()) // SELECT .. FROM ...
							.append("\n WHERE \n").append(sqlWhere) // WHERE
							.append("\n ORDER BY ").append(sqlOrderBy) // ORDER BY
							.append("\n OFFSET ").append(SqlForFetchingLookups.PARAM_Offset) // OFFSET
							.append("\n LIMIT ").append(SqlForFetchingLookups.PARAM_Limit) // LIMIT
							.wrapIfTrue(!lookupInfo.isSecurityDisabled(), AccessSqlStringExpression.wrapper(tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, getRequiredAccess(tableName))) // security
							.build()
							.caching())
					.build();
		}

		private SqlForFetchingLookupById buildSqlForFetchingById(final MLookupInfo lookupInfo)
		{
			final IStringExpression displayColumnSQL = TranslatableParameterizedStringExpression.of(lookupInfo.getDisplayColumnSql());

			final IStringExpression descriptionColumnSqlOrNull = TranslatableParameterizedStringExpression.of(lookupInfo.getDescriptionColumnSQL());
			final IStringExpression descriptionColumnSQL;
			if (descriptionColumnSqlOrNull == null || descriptionColumnSqlOrNull.isNullExpression())
			{
				descriptionColumnSQL = ConstantStringExpression.of("NULL");
			}
			else
			{
				descriptionColumnSQL = descriptionColumnSqlOrNull;
			}

			final IStringExpression validationMsgColumnSqlOrNull = TranslatableParameterizedStringExpression.of(lookupInfo.getValidationMsgColumnSQL());
			final IStringExpression validationMsgColumnSQL;
			if (validationMsgColumnSqlOrNull == null || validationMsgColumnSqlOrNull.isNullExpression())
			{
				validationMsgColumnSQL = ConstantStringExpression.of("NULL");
			}
			else
			{
				validationMsgColumnSQL = validationMsgColumnSqlOrNull;
			}

			final IStringExpression fromSqlPart = TranslatableParameterizedStringExpression.of(lookupInfo.getFromSqlPart());

			final String keyColumnFQ = lookupInfo.getKeyColumnFQ();
			final int displayType = lookupInfo.getDisplayType();
			final String whereClauseSqlPart = lookupInfo.getWhereClauseSqlPart(); // assuming this is constant!

			final CompositeStringExpression.Builder sqlBuilder = IStringExpression
					.composer()
					.append("SELECT ")
					.append("\n ARRAY[").append(displayColumnSQL).append(", ").append(descriptionColumnSQL).append(",").append(lookupInfo.getActiveColumnSQL()).append(", ").append(validationMsgColumnSQL).append("]")
					.append("\n FROM ")
					.append(fromSqlPart)
					.append("\n WHERE ")
					.append(keyColumnFQ).append("=").append(SqlForFetchingLookupById.SQL_PARAM_KeyId)
					.append(" ");

			final boolean listOrButton = DisplayType.List == displayType || DisplayType.Button == displayType;
			if (listOrButton)
			{
				// FIXME: make it better: this is actually adding the AD_Ref_List.AD_Reference_ID=....
				sqlBuilder.append(" AND " + whereClauseSqlPart);
			}

			final ICachedStringExpression sql = sqlBuilder.build().caching();
			return SqlForFetchingLookupById.builder()
					.sql(sql)
					.build();
		}

		private INamePairPredicate getPostQueryPredicate()
		{
			final INamePairPredicate postQueryPredicate = validationRuleEffective.getPostQueryFilter();
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

		public Builder setCtxColumnName(final String columnName)
		{
			this.ctxColumnName = columnName;
			return this;
		}

		public Builder setCtxTableName(final String tableName)
		{
			this.ctxTableName = tableName;
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

		public Optional<WindowId> getZoomIntoWindowId()
		{
			return Optional.ofNullable(WindowId.ofNullable(zoomIntoAdWindowId));
		}

		private LookupSource getLookupSourceType()
		{
			return DescriptorsFactoryHelper.extractLookupSource(displayType, AD_Reference_Value_ID);
		}

		/**
		 * Advice the lookup to show all records on which current user has at least read only access
		 */
		public Builder setReadOnlyAccess()
		{
			this.requiredAccess = Access.READ;
			return this;
		}

		private Access getRequiredAccess(final String tableName)
		{
			if (requiredAccess != null)
			{
				return requiredAccess;
			}

			// AD_Client_ID/AD_Org_ID (security fields): shall display only those entries on which current user has read-write access
			if (I_AD_Client.Table_Name.equals(tableName)
					|| I_AD_Org.Table_Name.equals(tableName))
			{
				return Access.WRITE;
			}

			// Default: all entries on which current user has at least readonly access
			return Access.READ;
		}

		public Builder addValidationRule(final IValidationRule validationRule)
		{
			validationRules.add(validationRule);
			return this;
		}

		public Builder addValidationRules(final Collection<IValidationRule> validationRules)
		{
			this.validationRules.addAll(validationRules);
			return this;
		}

		public Set<String> getDependsOnTableNames()
		{
			return validationRuleEffective.getDependsOnTableNames();
		}

		public TooltipType getTooltipType()
		{
			return tooltipType;
		}

		public void setTooltipType(@NonNull final TooltipType tooltipType)
		{
			this.tooltipType = tooltipType;
		}
	}
}
