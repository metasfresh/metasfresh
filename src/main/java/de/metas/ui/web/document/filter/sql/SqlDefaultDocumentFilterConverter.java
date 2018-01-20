package de.metas.ui.web.document.filter.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.dao.impl.NullQueryFilterModifier;
import org.adempiere.db.DBConstants;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import com.google.common.base.MoreObjects;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.ui.web.window.model.sql.SqlDocumentsRepository;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;

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
 *
 * It simply converts the filters to SQL using a given {@link SqlEntityBinding} to map filter parameters.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */final class SqlDefaultDocumentFilterConverter implements SqlDocumentFilterConverter
{
	static SqlDefaultDocumentFilterConverter newInstance(final SqlEntityBinding entityBinding)
	{
		return new SqlDefaultDocumentFilterConverter(entityBinding);
	}

	private final SqlEntityBinding entityBinding;

	private SqlDefaultDocumentFilterConverter(final @NonNull SqlEntityBinding entityBinding)
	{
		this.entityBinding = entityBinding;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("entityBinding", entityBinding)
				.toString();
	}

	/** Build document filter where clause */
	@Override
	public String getSql(
			@NonNull final SqlParamsCollector sqlParams,
			@NonNull final DocumentFilter filter,
			@NonNull final SqlOptions sqlOpts)
	{
		final String filterId = filter.getFilterId();

		final StringBuilder sql = new StringBuilder();
		for (final DocumentFilterParam filterParam : filter.getParameters())
		{
			final String sqlFilterParam = buildSqlWhereClause(sqlParams, filterId, filterParam, sqlOpts);
			if (Check.isEmpty(sqlFilterParam, true))
			{
				continue;
			}

			if (sql.length() > 0)
			{
				sql.append(filterParam.isJoinAnd() ? " AND " : " OR ");
			}

			sql.append("(").append(sqlFilterParam).append(")");
		}

		return sql.toString();
	}

	/** Build document filter parameter where clause */
	private String buildSqlWhereClause(final SqlParamsCollector sqlParams, final String filterId, final DocumentFilterParam filterParam, final SqlOptions sqlOpts)
	{
		//
		// SQL filter
		if (filterParam.isSqlFilter())
		{
			String sqlWhereClause = filterParam.getSqlWhereClause();
			if (sqlOpts.isUseTableAlias())
			{
				sqlWhereClause = replaceTableNameWithTableAlias(sqlWhereClause, sqlOpts.getTableAlias());
			}

			final List<Object> sqlWhereClauseParams = filterParam.getSqlWhereClauseParams();
			sqlParams.collectAll(sqlWhereClauseParams);
			return sqlWhereClause;
		}

		//
		// Labels filter
		final String parameterName = filterParam.getFieldName();
		final DocumentFieldWidgetType widgetType = getParameterWidgetType(parameterName);
		if (widgetType == DocumentFieldWidgetType.Labels)
		{
			final DocumentFilterParamDescriptor paramDescriptor = getParameterDescriptor(filterId, parameterName);
			return buildSqlWhereClause_LabelsWidget(filterParam, paramDescriptor, sqlParams, sqlOpts);
		}
		//
		// Standard filter
		else
		{
			return buildSqlWhereClause_StandardWidget(sqlParams, filterParam, sqlOpts);
		}
	}

	private SqlEntityFieldBinding getParameterBinding(final String parameterName)
	{
		final SqlEntityFieldBinding fieldBinding = entityBinding.getFieldByFieldName(parameterName);
		return fieldBinding;
	}

	private DocumentFieldWidgetType getParameterWidgetType(final String parameterName)
	{
		return getParameterBinding(parameterName).getWidgetType();
	}

	private DocumentFilterParamDescriptor getParameterDescriptor(final String filterId, final String parameterName)
	{
		return entityBinding.getFilterDescriptors().getByFilterId(filterId).getParameterByName(parameterName);
	}

	private static IQueryFilterModifier extractFieldModifier(final DocumentFieldWidgetType widgetType)
	{
		if (widgetType == DocumentFieldWidgetType.DateTime)
		{
			return DateTruncQueryFilterModifier.DAY;
		}
		// NOTE: for performance reasons we are not truncating the field if the field is already Date.
		// If we would do so, it might happen that the index we have on that column will not be used.
		// More, we assume the column already contains truncated Date(s).
		else if (widgetType == DocumentFieldWidgetType.Date)
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
		if (widgetType == DocumentFieldWidgetType.DateTime)
		{
			return DateTruncQueryFilterModifier.DAY;
		}
		else if (widgetType == DocumentFieldWidgetType.Date)
		{
			return DateTruncQueryFilterModifier.DAY;
		}
		else
		{
			return NullQueryFilterModifier.instance;
		}
	}

	private String extractColumnSql(@NonNull final SqlEntityFieldBinding fieldBinding, final IQueryFilterModifier modifier, final SqlOptions sqlOpts)
	{
		String columnSql = fieldBinding.getColumnSql();
		if (sqlOpts.isUseTableAlias())
		{
			columnSql = replaceTableNameWithTableAlias(columnSql, sqlOpts.getTableAlias());
		}

		return modifier.getColumnSql(columnSql);
	}

	private Object convertToSqlValue(final Object value, final SqlEntityFieldBinding fieldBinding, final IQueryFilterModifier modifier)
	{
		final String columnName = fieldBinding.getColumnName();
		final DocumentFieldWidgetType widgetType = fieldBinding.getWidgetType();
		final Class<?> targetClass = fieldBinding.getSqlValueClass();
		final Object sqlValue = SqlDocumentsRepository.convertValueToPO(value, columnName, widgetType, targetClass);

		return modifier.convertValue(columnName, sqlValue, null/* model */);
	}

	private final String buildSqlWhereClause_StandardWidget(final SqlParamsCollector sqlParams, final DocumentFilterParam filterParam, final SqlOptions sqlOpts)
	{
		final SqlEntityFieldBinding paramBinding = getParameterBinding(filterParam.getFieldName());
		final DocumentFieldWidgetType widgetType = paramBinding.getWidgetType();

		//
		// Regular filter
		final IQueryFilterModifier fieldModifier = extractFieldModifier(widgetType);
		final IQueryFilterModifier valueModifier = extractValueModifier(widgetType);
		final String columnSql = extractColumnSql(paramBinding, fieldModifier, sqlOpts);

		final Operator operator = filterParam.getOperator();
		switch (operator)
		{
			case EQUAL:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = false;
				return buildSqlWhereClause_Equals(columnSql, sqlValue, negate, sqlParams);
			}
			case NOT_EQUAL:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = true;
				return buildSqlWhereClause_Equals(columnSql, sqlValue, negate, sqlParams);
			}
			case IN_ARRAY:
			{
				final List<Object> sqlValuesList = filterParam.getValueAsList(itemObj -> convertToSqlValue(itemObj, paramBinding, valueModifier));
				return buildSqlWhereClause_InArray(columnSql, sqlValuesList, sqlParams);
			}
			case GREATER:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				return buildSqlWhereClause_Compare(columnSql, ">", sqlValue, sqlParams);
			}
			case GREATER_OR_EQUAL:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				return buildSqlWhereClause_Compare(columnSql, ">=", sqlValue, sqlParams);
			}
			case LESS:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				return buildSqlWhereClause_Compare(columnSql, "<", sqlValue, sqlParams);
			}
			case LESS_OR_EQUAL:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				return buildSqlWhereClause_Compare(columnSql, "<=", sqlValue, sqlParams);
			}
			case LIKE:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = false;
				final boolean ignoreCase = false;
				return buildSqlWhereClause_Like(columnSql, negate, ignoreCase, sqlValue, sqlParams);
			}
			case NOT_LIKE:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = true;
				final boolean ignoreCase = false;
				return buildSqlWhereClause_Like(columnSql, negate, ignoreCase, sqlValue, sqlParams);
			}
			case LIKE_I:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = false;
				final boolean ignoreCase = true;
				return buildSqlWhereClause_Like(columnSql, negate, ignoreCase, sqlValue, sqlParams);
			}
			case NOT_LIKE_I:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final boolean negate = true;
				final boolean ignoreCase = true;
				return buildSqlWhereClause_Like(columnSql, negate, ignoreCase, sqlValue, sqlParams);
			}
			case BETWEEN:
			{
				final Object sqlValue = convertToSqlValue(filterParam.getValue(), paramBinding, valueModifier);
				final Object sqlValueTo = convertToSqlValue(filterParam.getValueTo(), paramBinding, valueModifier);
				return buildSqlWhereClause_Between(columnSql, sqlValue, sqlValueTo, sqlParams);
			}
			default:
			{
				throw new IllegalArgumentException("Operator not supported: " + operator);
			}
		}
	}

	private static final String buildSqlWhereClause_Equals(final String sqlColumnExpr, final Object sqlValue, final boolean negate, final SqlParamsCollector sqlParams)
	{
		if (sqlValue == null)
		{
			return buildSqlWhereClause_IsNull(sqlColumnExpr, negate);
		}

		return new StringBuilder()
				.append(sqlColumnExpr)
				.append(negate ? " <> " : " = ")
				.append(sqlParams.placeholder(sqlValue))
				.toString();
	}

	private static final String buildSqlWhereClause_IsNull(final String sqlColumnExpr, final boolean negate)
	{
		return new StringBuilder()
				.append(sqlColumnExpr).append(negate ? " IS NOT NULL" : " IS NULL")
				.toString();
	}

	private static final String buildSqlWhereClause_Compare(final String sqlColumnExpr, final String sqlOperator, final Object sqlValue, final SqlParamsCollector sqlParams)
	{
		return new StringBuilder()
				.append(sqlColumnExpr).append(sqlOperator).append(sqlParams.placeholder(sqlValue))
				.toString();
	}

	private static final String buildSqlWhereClause_InArray(final String sqlColumnExpr, final List<Object> sqlValues, final SqlParamsCollector sqlParams)
	{
		if (sqlValues == null || sqlValues.isEmpty())
		{
			// TODO log a warning or throw exception?!
			return null;
		}

		if (sqlParams.isCollecting())
		{
			final List<Object> sqlValuesEffective = new ArrayList<>();
			final String sql = DB.buildSqlList(sqlColumnExpr, sqlValues, sqlValuesEffective);
			sqlParams.collectAll(sqlValuesEffective); // safe
			return sql;
		}
		else
		{
			return DB.buildSqlList(sqlParams.toList());
		}
	}

	private static final String buildSqlWhereClause_Like(final String sqlColumnExpr, final boolean negate, final boolean ignoreCase, final Object sqlValue, final SqlParamsCollector sqlParams)
	{
		if (sqlValue == null)
		{
			return buildSqlWhereClause_IsNull(sqlColumnExpr, negate);
		}

		String sqlValueStr = sqlValue.toString();
		if (sqlValueStr.isEmpty())
		{
			// NO value supplied, it's pointless to enforce a LIKE on that...
			// => considering all matches
			return "";
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

		return new StringBuilder()
				.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(sqlColumnExpr).append(", 1)")
				.append(sqlOperator)
				.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(sqlParams.placeholder(sqlValueStr)).append(", 1)")
				.toString();
	}

	private static final String buildSqlWhereClause_Between(final String sqlColumnExpr, final Object sqlValue, final Object sqlValueTo, final SqlParamsCollector sqlParams)
	{
		if (sqlValue == null)
		{
			if (sqlValueTo == null)
			{
				// Both values are null => considering all matches
				return "";
			}
			return buildSqlWhereClause_Compare(sqlColumnExpr, "<=", sqlValueTo, sqlParams);
		}
		if (sqlValueTo == null)
		{
			// NOTE: at this point sqlValue is not null!
			return buildSqlWhereClause_Compare(sqlColumnExpr, ">=", sqlValue, sqlParams);
		}

		return new StringBuilder()
				.append(sqlColumnExpr).append(" BETWEEN ").append(sqlParams.placeholder(sqlValue)).append(" AND ").append(sqlParams.placeholder(sqlValueTo))
				.toString();
	}

	private String replaceTableNameWithTableAlias(final String sql, @NonNull final String tableAlias)
	{
		if (sql == null || sql.isEmpty())
		{
			return sql;
		}

		final String matchTableNameIgnoringCase = "(?i)" + Pattern.quote(entityBinding.getTableName() + ".");
		final String sqlFixed = sql.replaceAll(matchTableNameIgnoringCase, tableAlias + ".");
		return sqlFixed;
	}

	private final String buildSqlWhereClause_LabelsWidget(
			final DocumentFilterParam filterParam,
			final DocumentFilterParamDescriptor paramDescriptor,
			final SqlParamsCollector sqlParams,
			final SqlOptions sqlOpts)
	{
		final LookupValuesList lookupValues = extractLookupValuesList(filterParam);
		if (lookupValues.isEmpty())
		{
			return null;
		}

		final String tableAlias = sqlOpts.getTableNameOrAlias();

		final LabelsLookup lookup = LabelsLookup.cast(paramDescriptor.getLookupDescriptor());
		final String labelsTableName = lookup.getLabelsTableName();
		final String labelsLinkColumnName = lookup.getLabelsLinkColumnName();
		final String linkColumnName = lookup.getLinkColumnName();
		final String labelsValueColumnName = lookup.getLabelsListColumnName();

		final StringBuilder sql = new StringBuilder();
		for (final LookupValue lookupValue : lookupValues)
		{
			if (sql.length() > 0)
			{
				sql.append(" AND ");
			}

			sql.append("EXISTS (SELECT 1 FROM " + labelsTableName + " labels "
					+ " WHERE labels." + labelsLinkColumnName + "=" + tableAlias + "." + linkColumnName
					+ " AND labels." + labelsValueColumnName + "=" + sqlParams.placeholder(lookupValue.getIdAsString())
					+ ")");
		}

		return sql.toString();
	}

	private static final LookupValuesList extractLookupValuesList(final DocumentFilterParam filterParam)
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
			throw new AdempiereException("Connot convert " + valueObj + " to " + LookupValuesList.class + " for " + filterParam);
		}
	}

}
