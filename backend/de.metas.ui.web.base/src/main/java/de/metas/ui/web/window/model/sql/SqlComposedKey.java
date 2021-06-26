/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.window.model.sql;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.BiConsumer;

@EqualsAndHashCode
@ToString(of = "values")
public class SqlComposedKey
{
	public static SqlComposedKey of(
			@NonNull final ImmutableSet<String> keyColumnNames,
			@NonNull final ImmutableMap<String, Object> values)
	{
		return new SqlComposedKey(keyColumnNames, values);
	}

	@Getter
	@NonNull private final ImmutableSet<String> keyColumnNames;

	@NonNull private final ImmutableMap<String, Object> values;

	@Builder
	private SqlComposedKey(
			@NonNull @Singular final ImmutableSet<String> keyColumnNames,
			@NonNull @Singular final ImmutableMap<String, Object> values)
	{
		Check.assumeNotEmpty(keyColumnNames, "keyColumnNames is not empty");
		this.keyColumnNames = keyColumnNames;
		this.values = values;
	}

	@Nullable
	public Object getValue(@NonNull final String keyColumnName)
	{
		return values.get(keyColumnName);
	}

	/**
	 * @return all values (including nulls) in the same order as the key column names are
	 */
	public ArrayList<Object> getSqlValuesList()
	{
		final ArrayList<Object> allValuesIncludingNulls = new ArrayList<>(keyColumnNames.size());
		for (final String keyColumnName : keyColumnNames)
		{
			final Object value = getValue(keyColumnName);
			allValuesIncludingNulls.add(value);
		}

		return allValuesIncludingNulls;
	}

	public void forEach(@NonNull final BiConsumer<String, Object> keyAndValueConsumer)
	{
		for (final String keyColumnName : keyColumnNames)
		{
			final Object value = getValue(keyColumnName);
			keyAndValueConsumer.accept(keyColumnName, value);
		}
	}

	public SqlAndParams getSqlValuesCommaSeparated()
	{
		final SqlAndParams.Builder sqlBuilder = SqlAndParams.builder();

		for (final String keyColumnName : keyColumnNames)
		{
			final Object value = getValue(keyColumnName);
			if (!sqlBuilder.isEmpty())
			{
				sqlBuilder.append(", ");
			}
			sqlBuilder.append("?", value);
		}

		return sqlBuilder.build();
	}

	public String getSqlWhereClauseById(@NonNull final String tableAlias)
	{
		final StringBuilder sql = new StringBuilder();
		for (final String keyFieldName : keyColumnNames)
		{
			final Object idPart = getValue(keyFieldName);

			if (sql.length() > 0)
			{
				sql.append(" AND ");
			}

			sql.append(tableAlias).append(".").append(keyFieldName);

			if (!JSONNullValue.isNull(idPart))
			{
				sql.append("=").append(DB.TO_SQL(idPart));
			}
			else
			{
				sql.append(" IS NULL");
			}
		}

		return sql.toString();
	}

}
