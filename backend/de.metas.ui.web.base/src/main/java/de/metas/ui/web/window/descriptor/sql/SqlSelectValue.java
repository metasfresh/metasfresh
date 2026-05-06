package de.metas.ui.web.window.descriptor.sql;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;
import java.util.Objects;

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

/**
 * SQL to be used in expressions like <code>SELECT ... 'this field's sql' ... FROM ...</code>
 */
@EqualsAndHashCode
@ToString
public class SqlSelectValue
{
	@Nullable private final String tableNameOrAlias;
	@Nullable private final String columnName;

	@Getter
	@Nullable private final ColumnSql virtualColumnSql;

	@Getter
	private final String columnNameAlias;

	@Builder(toBuilder = true)
	private SqlSelectValue(
			@Nullable final String tableNameOrAlias,
			@Nullable final String columnName,
			@Nullable final ColumnSql virtualColumnSql,
			@NonNull final String columnNameAlias)
	{
		this.columnNameAlias = columnNameAlias;
		this.tableNameOrAlias = StringUtils.trimBlankToNull(tableNameOrAlias);

		if (virtualColumnSql != null)
		{
			this.columnName = null;
			this.virtualColumnSql = tableNameOrAlias != null ? virtualColumnSql.withJoinOnTableNameOrAlias(tableNameOrAlias) : virtualColumnSql;
		}
		else
		{
			Check.assumeNotEmpty(columnName, "columnName is not empty");

			this.columnName = columnName;
			this.virtualColumnSql = null;
		}
	}

	public String toSqlStringWithColumnNameAlias()
	{
		return toSqlString() + " AS " + columnNameAlias;
	}

	/**
	 * Like {@link #toSqlStringWithColumnNameAlias()}, but returns an {@link IStringExpression}
	 * so that any context-variable tokens (e.g. {@code @#AD_Org_ID@}) inside a virtual ColumnSQL
	 * remain visible as parameters of the resulting expression — and thus get resolved later
	 * against the user-session evaluator instead of being baked in as constant text.
	 */
	public IStringExpression toStringExpressionWithColumnNameAlias()
	{
		return IStringExpression.compile(toSqlStringWithColumnNameAlias());
	}

	/**
	 * Like {@link #toSqlStringWithColumnNameAlias()}, but resolves any context-variable tokens
	 * (e.g. {@code @#AD_Org_ID@}) against the supplied evaluator before returning the SQL string.
	 * Use this on String-builder paths (e.g. {@link de.metas.ui.web.view.descriptor.SqlAndParams})
	 * which cannot carry expression parameters through to a later evaluation step.
	 */
	public String toSqlStringWithColumnNameAlias(@NonNull final Evaluatee ctx)
	{
		return toStringExpressionWithColumnNameAlias().evaluate(ctx, OnVariableNotFound.Fail);
	}

	public String toSqlString()
	{
		if (virtualColumnSql != null)
		{
			return virtualColumnSql.toSqlStringWrappedInBracketsIfNeeded();
		}
		else if (tableNameOrAlias != null)
		{
			return tableNameOrAlias + "." + columnName;
		}
		else
		{
			return columnName;
		}
	}

	public boolean isVirtualColumn()
	{
		return virtualColumnSql != null;
	}

	public SqlSelectValue withJoinOnTableNameOrAlias(final String tableNameOrAlias)
	{
		return !Objects.equals(this.tableNameOrAlias, tableNameOrAlias)
				? toBuilder().tableNameOrAlias(tableNameOrAlias).build()
				: this;
	}

	public SqlSelectValue withColumnNameAlias(@NonNull final String columnNameAlias)
	{
		return !Objects.equals(this.columnNameAlias, columnNameAlias)
				? toBuilder().columnNameAlias(columnNameAlias).build()
				: this;
	}
}
