package de.metas.ui.web.document.filter.sql;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.ui.web.window.model.sql.SqlDocumentsRepository;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.dao.impl.NullQueryFilterModifier;
import org.adempiere.db.DBConstants;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Default {@link SqlDocumentFilterConverter}.
 * <p>
 * It simply converts the filters to SQL using a given {@link SqlEntityBinding} to map filter parameters.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@RequiredArgsConstructor
public final class SqlDefaultDocumentFilterConverter implements SqlDocumentFilterConverter
{
	static SqlDefaultDocumentFilterConverter newInstance(final SqlEntityBinding entityBinding)
	{
		return new SqlDefaultDocumentFilterConverter(entityBinding, SqlViewBinding.getIncludedEntitiesDescriptors(entityBinding));
	}

	@NonNull private final SqlEntityBinding entityBinding;
	@NonNull private final ImmutableMap<DetailId, SqlDocumentEntityDataBindingDescriptor> includedEntities;

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("entityBinding", entityBinding)
				.toString();
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return true;
	}

	/**
	 * Build document filter where clause
	 */
	@Override
	public FilterSql getSql(
			@NonNull final DocumentFilter filter,
			@NonNull final SqlOptions sqlOptsParam,
			@NonNull final SqlDocumentFilterConverterContext context)
	{
		final String filterId = filter.getFilterId();

		final SqlAndParams.Builder mainTabSql = SqlAndParams.builder();
		final HashMap<DetailId, SqlAndParams.Builder> includedTabSqlByTabId = new HashMap<>();

		for (final DocumentFilterParam filterParam : filter.getParameters())
		{
			if (filterParam.getValue() == null && filterParam.getSqlWhereClause() == null)
			{
				// don't include "null" parameters; The frontend might send them e.g. if a string field was cleared. 
				// we assume that the user never filters for value = "NULL"
				continue;
			}

			final DetailId includedTabId = filterParam.getFieldName() != null
					? ParameterNameFQ.ofParameterNameFQ(filterParam.getFieldName()).getTabId()
					: null;
			
			final SqlOptions sqlOpts = includedTabId == null
					? sqlOptsParam
					: SqlOptions.usingTableAlias(includedTabId.getTableAlias());

			final SqlAndParams sqlFilterParam = buildSqlWhereClause(filterId, filterParam, sqlOpts);
			if (sqlFilterParam == null || sqlFilterParam.isEmpty())
			{
				continue;
			}

			final SqlAndParams.Builder sql = includedTabId == null
					? mainTabSql
					: includedTabSqlByTabId.computeIfAbsent(includedTabId, (k) -> SqlAndParams.builder());

			if (!sql.isEmpty())
			{
				sql.append(filterParam.isJoinAnd() ? " AND " : " OR ");
			}
			sql.append("(").append(sqlFilterParam).append(")");
		}

		//
		//
		for (final Map.Entry<DetailId, SqlAndParams.Builder> tabIdAndSql : includedTabSqlByTabId.entrySet())
		{
			final SqlAndParams.Builder includedTabSql = tabIdAndSql.getValue();
			if (includedTabSql.isEmpty())
			{
				continue;
			}

			final DetailId tabId = tabIdAndSql.getKey();
			final SqlDocumentEntityDataBindingDescriptor includedEntityBinding = getIncludedEntityBinding(tabId);

			if (!mainTabSql.isEmpty())
			{
				mainTabSql.append("\n AND ");
			}
			mainTabSql.append("EXISTS (select 1 from ").append(includedEntityBinding.getTableName()).append(" ").append(tabId.getTableAlias())
					.append(" where ")
					.append(tabId.getTableAlias()).append(".").append(includedEntityBinding.getLinkColumnName()).append("=").append(sqlOptsParam.getTableNameOrAlias()).append(".").append(includedEntityBinding.getParentLinkColumnName())
					.append(" AND ").append(includedTabSql)
					.append(")");
		}

		//
		//
		return FilterSql.ofWhereClause(mainTabSql.build());
	}

	/**
	 * Build document filter parameter where clause
	 */
	@Nullable
	private SqlAndParams buildSqlWhereClause(
			final String filterId,
			final DocumentFilterParam filterParam,
			final SqlOptions sqlOpts)
	{
		//
		// SQL filter
		if (filterParam.isSqlFilter())
		{
			final SqlAndParams sqlWhereClause = filterParam.getSqlWhereClause();
			if (sqlOpts.isUseTableAlias())
			{
				return SqlAndParams.of(
						replaceTableNameWithTableAlias(sqlWhereClause.getSql(), sqlOpts.getTableAlias()),
						sqlWhereClause.getSqlParams());
			}
			else
			{
				return sqlWhereClause;
			}
		}

		//
		// Labels filter
		final ParameterNameFQ parameterNameFQ = ParameterNameFQ.ofParameterNameFQ(filterParam.getFieldName());
		final DocumentFieldWidgetType widgetType = getParameterWidgetType(parameterNameFQ);
		if (widgetType == DocumentFieldWidgetType.Labels)
		{
			final DocumentFilterParamDescriptor paramDescriptor = getParameterDescriptor(filterId, parameterNameFQ);
			return buildSqlWhereClause_LabelsWidget(filterParam, paramDescriptor, sqlOpts);
		}
		//
		// Standard filter
		else
		{
			return buildSqlWhereClause_StandardWidget(filterParam, sqlOpts);
		}
	}

	private SqlEntityFieldBinding getParameterBinding(final ParameterNameFQ parameterNameFQ)
	{
		if (parameterNameFQ.getTabId() == null)
		{
			return entityBinding.getFieldByFieldName(parameterNameFQ.getParameterName());
		}
		else
		{
			return getIncludedEntityBinding(parameterNameFQ.getTabId()).getFieldByFieldName(parameterNameFQ.getParameterName());
		}
	}

	@NonNull
	private SqlDocumentEntityDataBindingDescriptor getIncludedEntityBinding(final DetailId tabId)
	{
		final SqlDocumentEntityDataBindingDescriptor includedEntityBinding = includedEntities.get(tabId);
		if (includedEntityBinding == null)
		{
			throw new AdempiereException("Included entity `" + tabId + "` not found");
		}
		return includedEntityBinding;
	}

	private DocumentFieldWidgetType getParameterWidgetType(final ParameterNameFQ parameterNameFQ)
	{
		return getParameterBinding(parameterNameFQ).getWidgetType();
	}

	private DocumentFilterParamDescriptor getParameterDescriptor(@NonNull final String filterId, @NonNull final ParameterNameFQ parameterNameFQ)
	{
		if (parameterNameFQ.getTabId() == null)
		{
			return entityBinding.getFilterDescriptors().getByFilterId(filterId).getParameterByName(parameterNameFQ.getParameterName());
		}
		else
		{
			return getIncludedEntityBinding(parameterNameFQ.getTabId())
					.getFilterDescriptors()
					.getByFilterId(parameterNameFQ.getIncludedFilterId())
					.getParameterByName(parameterNameFQ.getParameterName());
		}
	}

	private static IQueryFilterModifier extractFieldModifier(final DocumentFieldWidgetType widgetType)
	{
		// NOTE: for performance reasons we are not truncating the field if the field is already Date.
		// If we would do so, it might happen that the index we have on that column will not be used.
		// More, we assume the column already contains truncated Date(s).
		if (widgetType.isDateWithTime())
		{
			return DateTruncQueryFilterModifier.DAY;
		}
		else
		{
			return NullQueryFilterModifier.instance;
		}
	}

	private static IQueryFilterModifier extractValueModifier(final DocumentFieldWidgetType widgetType)
	{
		if (widgetType.isDateWithTime())
		{
			return DateTruncQueryFilterModifier.DAY;
		}
		else
		{
			return NullQueryFilterModifier.instance;
		}
	}

	@Nullable
	private Object convertToSqlValue(final Object value, final SqlEntityFieldBinding fieldBinding, final IQueryFilterModifier modifier)
	{
		final String columnName = fieldBinding.getColumnName();
		final DocumentFieldWidgetType widgetType = fieldBinding.getWidgetType();
		final Class<?> targetClass = fieldBinding.getSqlValueClass();
		final Object sqlValue = SqlDocumentsRepository.convertValueToPO(value, columnName, widgetType, targetClass);

		return modifier.convertValue(columnName, sqlValue, null/* model */);
	}

	@Nullable
	private SqlAndParams buildSqlWhereClause_StandardWidget(final DocumentFilterParam filterParam, final SqlOptions sqlOpts)
	{
		final SqlEntityFieldBinding paramBinding = getParameterBinding(ParameterNameFQ.ofParameterNameFQ(filterParam.getFieldName()));
		final DocumentFieldWidgetType widgetType = paramBinding.getWidgetType();

		final String columnSqlString;
		{
			final IQueryFilterModifier fieldModifier = extractFieldModifier(widgetType);
			final SqlSelectValue columnSqlEffective = replaceTableNameWithTableAliasIfNeeded(
					paramBinding.getSqlSelectValue(),
					sqlOpts);
			columnSqlString = fieldModifier.getColumnSql(columnSqlEffective.toSqlString());
		}

		final IQueryFilterModifier valueModifier = extractValueModifier(widgetType);
		final Operator operator = filterParam.getOperator();
		switch (operator)
		{
			case EQUAL:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = false;
				return buildSqlWhereClause_Equals(columnSqlString, sqlValue, negate);
			}
			case NOT_EQUAL:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = true;
				return buildSqlWhereClause_Equals(columnSqlString, sqlValue, negate);
			}
			case IN_ARRAY:
			{
				final List<Object> sqlValuesList = filterParam.getValueAsList(itemObj -> convertToSqlValue(itemObj, paramBinding, valueModifier));
				return buildSqlWhereClause_InArray(columnSqlString, sqlValuesList);
			}
			case GREATER:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				return buildSqlWhereClause_Compare(columnSqlString, ">", sqlValue);
			}
			case GREATER_OR_EQUAL:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				return buildSqlWhereClause_Compare(columnSqlString, ">=", sqlValue);
			}
			case LESS:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				return buildSqlWhereClause_Compare(columnSqlString, "<", sqlValue);
			}
			case LESS_OR_EQUAL:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				return buildSqlWhereClause_Compare(columnSqlString, "<=", sqlValue);
			}
			case LIKE:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = false;
				final boolean ignoreCase = false;
				return buildSqlWhereClause_Like(columnSqlString, negate, ignoreCase, sqlValue);
			}
			case NOT_LIKE:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = true;
				final boolean ignoreCase = false;
				return buildSqlWhereClause_Like(columnSqlString, negate, ignoreCase, sqlValue);
			}
			case LIKE_I:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = false;
				final boolean ignoreCase = true;
				return buildSqlWhereClause_Like(columnSqlString, negate, ignoreCase, sqlValue);
			}
			case NOT_LIKE_I:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = true;
				final boolean ignoreCase = true;
				return buildSqlWhereClause_Like(columnSqlString, negate, ignoreCase, sqlValue);
			}
			case BETWEEN:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final Object sqlValueTo = convertToSqlValue(filterParam.getValueTo(), paramBinding, valueModifier);
				return buildSqlWhereClause_Between(columnSqlString, sqlValue, sqlValueTo);
			}
			case NOT_NULL_IF_TRUE:
			{
				final Boolean notNullFlag = StringUtils.toBoolean(filterParam.getValue(), null);
				return buildSqlWhereClause_IsNull(columnSqlString, NullOperator.ofNotNullFlag(notNullFlag));
			}
			default:
			{
				throw new IllegalArgumentException("Operator not supported: " + operator);
			}
		}
	}

	@VisibleForTesting
	static SqlAndParams buildSqlWhereClause_Equals(final String sqlColumnExpr, @Nullable final Object sqlValue, final boolean negate)
	{
		if (sqlValue == null)
		{
			return buildSqlWhereClause_IsNull(sqlColumnExpr, NullOperator.IS_NULL.negateIf(negate));
		}
		else
		{
			return SqlAndParams.of(sqlColumnExpr + (negate ? " <> " : " = ") + "?", sqlValue);
		}
	}

	@VisibleForTesting
	static SqlAndParams buildSqlWhereClause_IsNull(
			@NonNull final String sqlColumnExpr,
			@NonNull final NullOperator operator)
	{
		switch (operator)
		{
			case IS_NULL:
				return SqlAndParams.of(sqlColumnExpr + " IS NULL");
			case IS_NOT_NULL:
				return SqlAndParams.of(sqlColumnExpr + " IS NOT NULL");
			case ANY:
				return SqlAndParams.EMPTY;
			default:
				throw new AdempiereException("Unknown operator: " + operator);
		}
	}

	private static SqlAndParams buildSqlWhereClause_Compare(final String sqlColumnExpr, final String sqlOperator, @Nullable final Object sqlValue)
	{
		return SqlAndParams.of(sqlColumnExpr + sqlOperator + "?", sqlValue);
	}

	@Nullable
	private static SqlAndParams buildSqlWhereClause_InArray(final String sqlColumnExpr, final List<Object> sqlValues)
	{
		if (sqlValues == null || sqlValues.isEmpty())
		{
			// TODO log a warning or throw exception?!
			return null;
		}

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = DB.buildSqlList(sqlColumnExpr, sqlValues, sqlParams);

		return SqlAndParams.of(sql, sqlParams);
	}

	@VisibleForTesting
	static SqlAndParams buildSqlWhereClause_Like(final String sqlColumnExpr, final boolean negate, final boolean ignoreCase, @Nullable final Object sqlValue)
	{
		if (sqlValue == null)
		{
			return buildSqlWhereClause_IsNull(sqlColumnExpr, NullOperator.IS_NULL.negateIf(negate));
		}

		String sqlValueStr = sqlValue.toString().trim();
		if (sqlValueStr.isEmpty())
		{
			// NO value supplied, it's pointless to enforce a LIKE on that...
			// => considering all matches
			return SqlAndParams.EMPTY;
		}

		if (!sqlValueStr.startsWith("%"))
		{
			sqlValueStr = "%" + sqlValueStr;
		}
		if (!sqlValueStr.endsWith("%"))
		{
			sqlValueStr = sqlValueStr + "%";
		}

		final String sqlOperator = (negate ? " NOT " : " ") + (ignoreCase ? "ILIKE " : "LIKE ");

		return SqlAndParams.of(
				DBConstants.FUNCNAME_unaccent_string + "(" + sqlColumnExpr + ", 1)"
						+ sqlOperator
						+ DBConstants.FUNCNAME_unaccent_string + "(?, 1)",
				sqlValueStr);
	}

	private static SqlAndParams buildSqlWhereClause_Between(final String sqlColumnExpr, @Nullable final Object sqlValue, @Nullable final Object sqlValueTo)
	{
		if (sqlValue == null)
		{
			if (sqlValueTo == null)
			{
				// Both values are null => considering all matches
				return SqlAndParams.EMPTY;
			}
			return buildSqlWhereClause_Compare(sqlColumnExpr, "<=", sqlValueTo);
		}
		if (sqlValueTo == null)
		{
			// NOTE: at this point sqlValue is not null!
			return buildSqlWhereClause_Compare(sqlColumnExpr, ">=", sqlValue);
		}

		return SqlAndParams.of(sqlColumnExpr + " BETWEEN ? AND ?", sqlValue, sqlValueTo);
	}

	@VisibleForTesting
	SqlSelectValue replaceTableNameWithTableAliasIfNeeded(
			@NonNull final SqlSelectValue columnSql,
			@NonNull final SqlOptions sqlOpts)
	{
		return sqlOpts.isUseTableAlias()
				? columnSql.withJoinOnTableNameOrAlias(sqlOpts.getTableAlias())
				: columnSql;
	}

	private String replaceTableNameWithTableAlias(final String sql, @NonNull final String tableAlias)
	{
		return SqlEntityBinding.replaceTableNameWithTableAlias(
				sql,
				entityBinding.getTableName(),
				tableAlias);
	}

	@Nullable
	private SqlAndParams buildSqlWhereClause_LabelsWidget(
			final DocumentFilterParam filterParam,
			final DocumentFilterParamDescriptor paramDescriptor,
			final SqlOptions sqlOpts)
	{
		final LookupValuesList lookupValues = extractLookupValuesList(filterParam);
		if (lookupValues.isEmpty())
		{
			return null;
		}

		final String tableAlias = sqlOpts.getTableNameOrAlias();

		final LabelsLookup lookup = paramDescriptor.getLookupDescriptor()
				.map(LabelsLookup::cast)
				.orElseThrow(() -> new AdempiereException("No lookup defined for " + paramDescriptor));
		final String labelsTableName = lookup.getLabelsTableName();
		final String labelsLinkColumnName = lookup.getLabelsLinkColumnName();
		final String linkColumnName = lookup.getLinkColumnName();
		final String labelsValueColumnName = lookup.getLabelsValueColumnName();

		final SqlAndParams.Builder sql = SqlAndParams.builder();
		for (final LookupValue lookupValue : lookupValues)
		{
			if (!sql.isEmpty())
			{
				sql.append(" AND ");
			}

			final Object labelValue = lookup.isLabelsValuesUseNumericKey() ? lookupValue.getIdAsInt() : lookupValue.getIdAsString();

			sql.append("EXISTS (SELECT 1 FROM ").append(labelsTableName).append(" labels ")
					.append(" WHERE labels.").append(labelsLinkColumnName).append("=").append(tableAlias).append(".").append(linkColumnName)
					.append(" AND labels.").append(labelsValueColumnName).append("=?", labelValue)
					.append(")");
		}

		return sql.build();
	}

	private static LookupValuesList extractLookupValuesList(final DocumentFilterParam filterParam)
	{
		final Object valueObj = filterParam.getValue();
		if (valueObj == null)
		{
			return LookupValuesList.EMPTY;
		}
		else if (valueObj instanceof String && ((String)valueObj).isEmpty())
		{
			return LookupValuesList.EMPTY;
		}
		else if (valueObj instanceof LookupValuesList)
		{
			return (LookupValuesList)valueObj;
		}
		else
		{
			throw new AdempiereException("Cannot convert " + valueObj + " to " + LookupValuesList.class + " for " + filterParam);
		}
	}

	public static String includedFilterParameterName(@NonNull final DetailId tabId, @NonNull final String filterId, @NonNull final String parameterName)
	{
		return ParameterNameFQ.of(tabId, filterId, parameterName).getAsString();
	}

	@Value(staticConstructor = "of")
	private static class ParameterNameFQ
	{
		@Nullable DetailId tabId;
		@Nullable String includedFilterId;
		@NonNull String parameterName;

		private static final Splitter SPLITTER = Splitter.on(".");

		public static ParameterNameFQ ofParameterNameFQ(@NonNull String parameterNameFQ)
		{
			final List<String> parts = SPLITTER.splitToList(parameterNameFQ);
			if (parts.size() == 1)
			{
				return of(null, null, parameterNameFQ);
			}
			else if (parts.size() == 3)
			{
				return of(
						DetailId.fromJson(parts.get(0)),
						parts.get(1),
						parts.get(2)
				);
			}
			else
			{
				throw new AdempiereException("Invalid parameter name `" + parameterNameFQ + "`");
			}
		}

		public String getAsString()
		{
			if (tabId == null)
			{
				return parameterName;
			}
			else
			{
				return tabId.toJson() + "." + includedFilterId + "." + parameterName;
			}
		}
	}
}
