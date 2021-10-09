package org.adempiere.ad.dao.impl;

import static org.adempiere.ad.dao.impl.CompareQueryFilter.normalizeValue;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.ecs.xhtml.code;
import org.compiere.util.DB;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * In Array Query filter: Checks if given columnName is in a list of values.
 *
 * The built SQL where clause will look something like:
 *
 * <pre>
 * ColumnName IN (Value1, Value2 ...) - for non null values. If there are no non-null values, this part will be skipped
 * OR ColumnName IS NULL - for null values. If there are no NULL values, this part will be skipped
 * </pre>
 *
 * In case your values list is empty then {@link #isDefaultReturnWhenEmpty()} constant will be used to evaluate the expression.
 *
 * NOTES:
 * <ul>
 * <li>NULL case is covered (i.e. if one of your values is NULL, the built SQL will contain an "ColumnName IS NULL" check
 * <li>maximum values list length is not checked and we rely on database. However in PostgreSQL there is no limit (see
 * http://stackoverflow.com/questions/1009706/postgresql-max-number-of-parameters-in-in-clause)
 * </ul>
 *
 * @author tsa
 *
 * @param <T>
 */
@EqualsAndHashCode(exclude = { "sqlBuilt", "sqlWhereClause", "sqlParams" })
public final class InArrayQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	protected static final String SQL_TRUE = "1=1";
	protected static final String SQL_FALSE = "1=0";

	private final String columnName;
	private final List<Object> values;
	private boolean defaultReturnWhenEmpty = true;
	private boolean embedSqlParams = false;

	private boolean sqlBuilt = false; // lazy
	private String sqlWhereClause = null; // lazy
	private List<Object> sqlParams = null; // lazy

	/**
	 * Creates filter that accepts a model if the given {@link code columnName} has one of the given {@code values}.
	 *
	 * @param columnName
	 * @param values
	 */
	public InArrayQueryFilter(@NonNull final String columnName, final Object... values)
	{
		this(columnName, Arrays.asList(values));
	}

	/**
	 * Creates filter that accepts a model if the given {@link code columnName} has one of the given {@code values}.
	 *
	 * @param columnName
	 * @param values may also be {@link RepoIdAware}s
	 */
	public InArrayQueryFilter(@NonNull final String columnName, final Collection<? extends Object> values)
	{
		this.columnName = columnName;

		if (values == null || values.isEmpty())
		{
			this.values = null;
		}
		else
		{
			this.values = values
					.stream()
					.map(value -> normalizeValue(value))
					.collect(Collectors.toCollection(ArrayList::new)); // note that guava's immutableList doesn't allow null values
		}
	}

	@Override
	public String toString()
	{
		return "InArrayQueryFilter ["
				+ (columnName != null ? "columnName=" + columnName + ", " : "")
				+ "defaultReturnWhenEmpty=" + defaultReturnWhenEmpty
				+ (values != null ? ", values=" + values : "") // values last, to make this more readable in case of *many* values
				+ "]";
	}

	public boolean isDefaultReturnWhenEmpty()
	{
		return defaultReturnWhenEmpty;
	}

	/**
	 * Sets default value to be returned when the "values" list is empty.
	 *
	 * @param defaultReturnWhenEmpty
	 * @return
	 */
	public InArrayQueryFilter<T> setDefaultReturnWhenEmpty(boolean defaultReturnWhenEmpty)
	{
		this.defaultReturnWhenEmpty = defaultReturnWhenEmpty;
		return this;
	}

	public InArrayQueryFilter<T> setEmbedSqlParams(final boolean embedSqlParams)
	{
		if (this.embedSqlParams == embedSqlParams)
		{
			return this;
		}

		this.embedSqlParams = embedSqlParams;
		this.sqlBuilt = false;
		this.sqlWhereClause = null;
		this.sqlParams = null;
		return this;
	}

	@Override
	public boolean accept(final T model)
	{
		if (values == null)
		{
			return defaultReturnWhenEmpty;
		}

		final Object modelValueNorm = normalizeValue(InterfaceWrapperHelper.getValue(model, columnName).orElse(null));
		final boolean modelValueIsNull = InterfaceWrapperHelper.isNull(model, columnName);

		for (final Object value : values)
		{
			if (value == null && modelValueIsNull)
			{
				return true;
			}

			final Object valueNorm = normalizeValue(value);
			if (Objects.equals(modelValueNorm, valueNorm))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		buildSql();
		return sqlParams;
	}

	@VisibleForTesting
	public String getColumnName()
	{
		return columnName;
	}

	@VisibleForTesting
	public List<Object> getValuesOrNull()
	{
		return values;
	}

	private void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		if (values == null || values.isEmpty())
		{
			sqlWhereClause = defaultReturnWhenEmpty ? SQL_TRUE : SQL_FALSE;
			sqlParams = ImmutableList.of();
		}
		else if (values.size() == 1)
		{
			final Object value = values.get(0);
			if (value == null)
			{
				sqlWhereClause = columnName + " IS NULL";
				sqlParams = ImmutableList.of();
			}
			else if (embedSqlParams)
			{
				sqlWhereClause = columnName + "=" + DB.TO_SQL(value);
				sqlParams = ImmutableList.of();
			}
			else
			{
				sqlWhereClause = columnName + "=?";
				sqlParams = ImmutableList.of(value);
			}
		}
		else
		{
			final List<Object> sqlParamsBuilt = new ArrayList<>(values.size());
			final StringBuilder sqlWhereClauseBuilt = new StringBuilder();
			boolean hasNullValues = false;
			boolean hasNonNullValues = false;
			for (final Object value : values)
			{
				//
				// Null Value - just set the flag, we need to add the where clause separately
				if (value == null)
				{
					hasNullValues = true;
					continue;
				}

				//
				// Non Null Value
				if (hasNonNullValues)
				{
					sqlWhereClauseBuilt.append(",");
				}
				else
				{
					// First time we are adding a non-NULL value => we are adding "ColumnName IN (" first
					sqlWhereClauseBuilt.append(columnName).append(" IN (");
				}

				if (embedSqlParams)
				{
					sqlWhereClauseBuilt.append(DB.TO_SQL(value));
				}
				else
				{
					sqlWhereClauseBuilt.append("?");
					sqlParamsBuilt.add(value);
				}
				hasNonNullValues = true;
			}
			// Closing the bracket from "ColumnName IN (".
			if (hasNonNullValues)
			{
				sqlWhereClauseBuilt.append(")");
			}

			// We have NULL and non-NULL values => we need to join the expressions with an OR
			if (hasNonNullValues && hasNullValues)
			{
				sqlWhereClauseBuilt.append(" OR ");
			}

			// Add the IS NULL expression
			if (hasNullValues)
			{
				sqlWhereClauseBuilt.append(columnName).append(" IS NULL");
			}

			// If we have both NULL and non-NULL values, we need to enclose the expression in brackets because of the "OR"
			if (hasNonNullValues && hasNullValues)
			{
				sqlWhereClauseBuilt.insert(0, '(').append(')');
			}

			this.sqlWhereClause = sqlWhereClauseBuilt.toString();
			this.sqlParams = sqlParamsBuilt;
		}

		sqlBuilt = true;
	}
}
