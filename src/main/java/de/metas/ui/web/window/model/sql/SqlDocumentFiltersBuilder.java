package de.metas.ui.web.window.model.sql;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.db.DBConstants;
import org.compiere.util.DB;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;
import de.metas.ui.web.window.model.filters.DocumentFilter;
import de.metas.ui.web.window.model.filters.DocumentFilterParam;
import de.metas.ui.web.window.model.filters.DocumentFilterParam.Operator;
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
 * Helper class to build SQL where clauses from {@link DocumentFilter}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class SqlDocumentFiltersBuilder
{
	public static final SqlDocumentFiltersBuilder newInstance(final SqlEntityBinding entityBinding)
	{
		return new SqlDocumentFiltersBuilder(entityBinding);
	}

	private final SqlEntityBinding entityBinding;
	private final List<DocumentFilter> filters = new ArrayList<>();

	private SqlDocumentFiltersBuilder(@NonNull final SqlEntityBinding entityBinding)
	{
		this.entityBinding = entityBinding;
	}

	public String buildSqlWhereClause(final List<Object> sqlParams)
	{
		if (filters.isEmpty())
		{
			return "";
		}

		final StringBuilder sqlWhereClauseBuilder = new StringBuilder();

		for (final DocumentFilter filter : filters)
		{
			final String sqlFilter = buildSqlWhereClause(sqlParams, filter);
			if (Check.isEmpty(sqlFilter, true))
			{
				continue;
			}

			if (sqlWhereClauseBuilder.length() > 0)
			{
				sqlWhereClauseBuilder.append("\n AND ");
			}
			sqlWhereClauseBuilder.append(DB.TO_COMMENT(filter.getFilterId())).append("(").append(sqlFilter).append(")");
		}

		return sqlWhereClauseBuilder.toString();
	}

	/** Build document filter where clause */
	private String buildSqlWhereClause(final List<Object> sqlParams, final DocumentFilter filter)
	{
		final StringBuilder sql = new StringBuilder();

		for (final DocumentFilterParam filterParam : filter.getParameters())
		{
			final String sqlFilterParam = buildSqlWhereClause(sqlParams, filterParam);
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
	private String buildSqlWhereClause(final List<Object> sqlParams, final DocumentFilterParam filterParam)
	{
		//
		// SQL filter
		if (filterParam.isSqlFilter())
		{
			final String sqlWhereClause = replaceTableNameWithTableAlias(filterParam.getSqlWhereClause());
			return sqlWhereClause;
		}

		//
		// Regular filter
		final String fieldName = filterParam.getFieldName();
		final SqlEntityFieldBinding fieldBinding = entityBinding.getFieldByFieldName(fieldName);

		final String columnSql = fieldBinding.getColumnSql();
		final String columnName = fieldBinding.getColumnName();
		final DocumentFieldWidgetType widgetType = fieldBinding.getWidgetType();
		final Class<?> targetClass = fieldBinding.getSqlValueClass();
		final Object sqlValue = SqlDocumentsRepository.convertValueToPO(filterParam.getValue(), columnName, widgetType, targetClass);

		final Operator operator = filterParam.getOperator();
		switch (operator)
		{
			case EQUAL:
			{
				final boolean negate = false;
				return buildSqlWhereClause_Equals(columnSql, sqlValue, negate, sqlParams);
			}
			case NOT_EQUAL:
			{
				final boolean negate = true;
				return buildSqlWhereClause_Equals(columnSql, sqlValue, negate, sqlParams);
			}
			case GREATER:
			{
				return buildSqlWhereClause_Compare(columnSql, ">", sqlValue, sqlParams);
			}
			case GREATER_OR_EQUAL:
			{
				return buildSqlWhereClause_Compare(columnSql, ">=", sqlValue, sqlParams);
			}
			case LESS:
			{
				return buildSqlWhereClause_Compare(columnSql, "<", sqlValue, sqlParams);
			}
			case LESS_OR_EQUAL:
			{
				return buildSqlWhereClause_Compare(columnSql, "<=", sqlValue, sqlParams);
			}
			case LIKE:
			{
				final boolean negate = false;
				final boolean ignoreCase = false;
				return buildSqlWhereClause_Like(columnSql, negate, ignoreCase, sqlValue, sqlParams);
			}
			case NOT_LIKE:
			{
				final boolean negate = true;
				final boolean ignoreCase = false;
				return buildSqlWhereClause_Like(columnSql, negate, ignoreCase, sqlValue, sqlParams);
			}
			case LIKE_I:
			{
				final boolean negate = false;
				final boolean ignoreCase = true;
				return buildSqlWhereClause_Like(columnSql, negate, ignoreCase, sqlValue, sqlParams);
			}
			case NOT_LIKE_I:
			{
				final boolean negate = true;
				final boolean ignoreCase = true;
				return buildSqlWhereClause_Like(columnSql, negate, ignoreCase, sqlValue, sqlParams);
			}
			case BETWEEN:
			{
				final Object sqlValueTo = SqlDocumentsRepository.convertValueToPO(filterParam.getValueTo(), columnName, widgetType, targetClass);
				return buildSqlWhereClause_Between(columnSql, sqlValue, sqlValueTo, sqlParams);
			}
			default:
			{
				throw new IllegalArgumentException("Operator not supported: " + operator);
			}
		}
	}

	private static final String buildSqlWhereClause_Equals(final String sqlColumnExpr, final Object sqlValue, final boolean negate, final List<Object> sqlParams)
	{
		if (sqlValue == null)
		{
			return buildSqlWhereClause_IsNull(sqlColumnExpr, negate);
		}

		sqlParams.add(sqlValue);
		return new StringBuilder()
				.append(sqlColumnExpr).append(negate ? " <> ?" : " = ?")
				.toString();
	}

	private static final String buildSqlWhereClause_IsNull(final String sqlColumnExpr, final boolean negate)
	{
		return new StringBuilder()
				.append(sqlColumnExpr).append(negate ? " IS NOT NULL" : " IS NULL")
				.toString();
	}

	private static final String buildSqlWhereClause_Compare(final String sqlColumnExpr, final String sqlOperator, final Object sqlValue, final List<Object> sqlParams)
	{
		sqlParams.add(sqlValue);
		return new StringBuilder()
				.append(sqlColumnExpr).append(sqlOperator).append("?")
				.toString();
	}

	private static final String buildSqlWhereClause_Like(final String sqlColumnExpr, final boolean negate, final boolean ignoreCase, final Object sqlValue, final List<Object> sqlParams)
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

		sqlParams.add(sqlValueStr);
		return new StringBuilder()
				.append(DBConstants.FUNCNAME_unaccent_string).append("(").append(sqlColumnExpr).append(", 1)")
				.append(sqlOperator)
				.append(DBConstants.FUNCNAME_unaccent_string).append("(?, 1)")
				.toString();
	}

	private static final String buildSqlWhereClause_Between(final String sqlColumnExpr, final Object sqlValue, final Object sqlValueTo, final List<Object> sqlParams)
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

		sqlParams.add(sqlValue);
		sqlParams.add(sqlValueTo);
		return new StringBuilder()
				.append(sqlColumnExpr).append(" BETWEEN ").append("? AND ?")
				.toString();
	}

	public String replaceTableNameWithTableAlias(final String sql)
	{
		if (sql == null || sql.isEmpty())
		{
			return sql;
		}

		final String sqlFixed = sql.replace(entityBinding.getTableName() + ".", entityBinding.getTableAlias() + ".");
		return sqlFixed;
	}

	public SqlDocumentFiltersBuilder addFilters(final List<DocumentFilter> filters)
	{
		if(filters == null || filters.isEmpty())
		{
			return this;
		}
		
		this.filters.addAll(filters);
		return this;
	}
}
