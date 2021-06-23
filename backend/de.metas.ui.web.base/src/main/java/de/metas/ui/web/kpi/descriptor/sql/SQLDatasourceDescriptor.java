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

package de.metas.ui.web.kpi.descriptor.sql;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@Value
public class SQLDatasourceDescriptor
{
	@Getter(AccessLevel.NONE)
	ImmutableMap<String, SQLDatasourceFieldDescriptor> fields;

	IStringExpression sqlSelect;

	@Builder
	private SQLDatasourceDescriptor(
			@NonNull final String sqlFrom,
			@NonNull final List<SQLDatasourceFieldDescriptor> fields)
	{
		Check.assumeNotEmpty(sqlFrom, "sqlFrom shall not be empty");
		Check.assumeNotEmpty(fields, "fields shall not be empty");

		this.fields = Maps.uniqueIndex(fields, SQLDatasourceFieldDescriptor::getFieldName);
		this.sqlSelect = buildSqlSelect(fields, sqlFrom);
	}

	public ImmutableCollection<SQLDatasourceFieldDescriptor> getFields()
	{
		return fields.values();
	}

	public SQLDatasourceFieldDescriptor getFieldByName(@NonNull final String fieldName)
	{
		final SQLDatasourceFieldDescriptor field = fields.get(fieldName);
		if (field == null)
		{
			throw new AdempiereException("Field `" + fieldName + "` was not found in " + this);
		}
		return field;
	}

	private static IStringExpression buildSqlSelect(
			@NonNull final List<SQLDatasourceFieldDescriptor> fields,
			@NonNull final String sqlFrom)
	{
		final StringBuilder sqlFields = new StringBuilder();
		for (final SQLDatasourceFieldDescriptor field : fields)
		{
			final String fieldName = field.getFieldName();
			final String sqlField = field.getSqlSelect();
			if (sqlFields.length() > 0)
			{
				sqlFields.append("\n, ");
			}

			sqlFields.append("(").append(sqlField).append(") AS ").append(fieldName);
		}

		return StringExpressionCompiler.instance.compile(
				"SELECT \n"
						+ sqlFields
						+ "\n" + sqlFrom);
	}

}
