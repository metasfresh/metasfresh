package org.adempiere.ad.dao.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class CoalesceEqualsQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	@NonNull private final ImmutableList<String> columnNames;
	@Getter @Nullable private final Object value;
	@NonNull private final IQueryFilterModifier modifier;

	public CoalesceEqualsQueryFilter(
			@Nullable final Object value,
			@NonNull final ImmutableList<String> columnNames,
			@Nullable IQueryFilterModifier modifier)
	{
		Check.assume(columnNames.size() > 1, "more than one column is expected but we got {}", columnNames);
		this.columnNames = ImmutableList.copyOf(columnNames);
		this.value = value;
		this.modifier = modifier != null ? modifier : NullQueryFilterModifier.instance;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		for (final String columnName : columnNames)
		{
			if (sb.length() > 0)
			{
				sb.append(",");
			}
			sb.append(columnName);
		}
		sb.insert(0, "COALESCE(").append(")");

		sb.append("==").append(value);

		return sb.toString();
	}

	@Override
	public boolean accept(final T model)
	{
		final ImmutablePair<String, Object> modelColumnAndValue = getFirstNonNullModelValue(model);
		if (modelColumnAndValue == null)
		{
			return value == null;
		}

		final String columnName = modelColumnAndValue.getLeft();
		final Object modelValue = modelColumnAndValue.getRight();
		final Object modelValueModified = modifier.convertValue(columnName, modelValue, model);
		final Object expectedValueModified = modifier.convertValue(columnName, this.value, model);

		return Objects.equals(modelValueModified, expectedValueModified);
	}

	@Nullable
	private ImmutablePair<String, Object> getFirstNonNullModelValue(final T model)
	{
		for (final String columnName : columnNames)
		{
			if (InterfaceWrapperHelper.isNull(model, columnName))
			{
				continue;
			}

			final Object modelValue = InterfaceWrapperHelper.getValue(model, columnName).orElse(null);
			if (modelValue != null)
			{
				return ImmutablePair.of(columnName, modelValue);
			}
		}

		return null;
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
		return getSqlParams();
	}

	public List<Object> getSqlParams()
	{
		buildSql();
		return sqlParams;
	}

	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;

	private void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		final StringBuilder sqlWhereClause = new StringBuilder();
		final List<Object> sqlParams;

		final String sqlColumnNames = modifier.getColumnSql("COALESCE(" + String.join(",", columnNames) + ")");
		sqlWhereClause.append(sqlColumnNames);

		if (value == null)
		{
			sqlWhereClause.append(" IS NULL");
			sqlParams = null;
		}
		else
		{
			sqlParams = new ArrayList<>();
			sqlWhereClause.append("=").append(modifier.getValueSql(value, sqlParams));
		}

		this.sqlWhereClause = sqlWhereClause.toString();
		this.sqlParams = sqlParams != null && !sqlParams.isEmpty() ? Collections.unmodifiableList(sqlParams) : ImmutableList.of();
		this.sqlBuilt = true;
	}
}
