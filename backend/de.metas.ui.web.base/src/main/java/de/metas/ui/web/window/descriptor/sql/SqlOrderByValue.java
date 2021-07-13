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

import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.compiere.util.Evaluatee;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/** Field's SQL expression to be used in ORDER BY constructions */
@EqualsAndHashCode
@ToString
public class SqlOrderByValue
{
	public static SqlOrderByValue ofColumnName(@NonNull final String joinOnTableNameOrAlias, @NonNull final String columnName)
	{
		return SqlOrderByValue.builder()
				.joinOnTableNameOrAlias(joinOnTableNameOrAlias)
				.columnName(columnName)
				.build();
	}

	private final SqlSelectDisplayValue sqlSelectDisplayValue;
	private final SqlSelectValue sqlSelectValue;
	private final String columnName;
	private final String joinOnTableNameOrAlias;

	private final String columnNameFQ; // computed
	private final String columnNameAliasFQ; // computed

	@Builder(toBuilder = true)
	private SqlOrderByValue(
			final SqlSelectDisplayValue sqlSelectDisplayValue,
			final SqlSelectValue sqlSelectValue,
			final String columnName,
			final String joinOnTableNameOrAlias)
	{
		this.joinOnTableNameOrAlias = StringUtils.trimBlankToNull(joinOnTableNameOrAlias);

		if (sqlSelectDisplayValue != null)
		{
			this.sqlSelectDisplayValue = sqlSelectDisplayValue.withJoinOnTableNameOrAlias(joinOnTableNameOrAlias);
			this.sqlSelectValue = null;
			this.columnName = null;
			this.columnNameFQ = null;
			this.columnNameAliasFQ = computeColumnNameFQ(this.joinOnTableNameOrAlias, sqlSelectDisplayValue.getColumnNameAlias());
		}
		else if (sqlSelectValue != null)
		{
			this.sqlSelectDisplayValue = null;
			this.sqlSelectValue = sqlSelectValue.withJoinOnTableNameOrAlias(joinOnTableNameOrAlias);
			this.columnName = null;
			this.columnNameFQ = null;
			this.columnNameAliasFQ = computeColumnNameFQ(this.joinOnTableNameOrAlias, sqlSelectValue.getColumnNameAlias());
		}
		else if (Check.isNotBlank(columnName))
		{
			this.sqlSelectDisplayValue = null;
			this.sqlSelectValue = null;
			this.columnName = columnName;
			this.columnNameFQ = computeColumnNameFQ(this.joinOnTableNameOrAlias, this.columnName);
			this.columnNameAliasFQ = this.columnNameFQ;
		}
		else
		{
			this.sqlSelectDisplayValue = null;
			this.sqlSelectValue = null;
			this.columnName = null;
			this.columnNameFQ = null;
			this.columnNameAliasFQ = null;
		}
	}

	private static String computeColumnNameFQ(@Nullable final String tableName, @NonNull final String columnName)
	{
		return tableName != null
				? tableName + "." + columnName
				: columnName;
	}

	public SqlOrderByValue withJoinOnTableNameOrAlias(final String joinOnTableNameOrAlias)
	{
		final String joinOnTableNameOrAliasEffective = StringUtils.trimBlankToNull(joinOnTableNameOrAlias);
		return !Objects.equals(this.joinOnTableNameOrAlias, joinOnTableNameOrAliasEffective)
				? toBuilder().joinOnTableNameOrAlias(joinOnTableNameOrAliasEffective).build()
				: this;
	}

	public boolean isNullExpression()
	{
		return toStringExpression().isNullExpression();
	}

	public IStringExpression toStringExpression()
	{
		if (sqlSelectDisplayValue != null)
		{
			return sqlSelectDisplayValue.toStringExpression();
		}
		else if (sqlSelectValue != null)
		{
			return ConstantStringExpression.of(sqlSelectValue.toSqlString());
		}
		else if (columnNameFQ != null)
		{
			return ConstantStringExpression.of(columnNameFQ);
		}
		else
		{
			return IStringExpression.NULL;
		}
	}

	public String toSqlString(@NonNull final Evaluatee ctx)
	{
		return toStringExpression().evaluate(ctx, OnVariableNotFound.Fail);
	}

	public String toSqlStringUsingColumnAlias()
	{
		return columnNameAliasFQ;
	}
}
