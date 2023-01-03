/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.column;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public final class ColumnSql
{
	public static final ColumnSql SQL_NULL = ofSql("NULL", null);

	private static final CtxName CTX_JoinTableNameOrAliasIncludingDot = CtxNames.ofNameAndDefaultValue("JoinTableNameOrAliasIncludingDot", null);
	@NonNull private final IStringExpression sqlExpression;
	@Nullable private final String joinTableNameOrAlias;

	private transient String _sqlBuilt;

	private ColumnSql(
			@Nullable final String joinTableNameOrAlias,
			@NonNull final IStringExpression sqlExpression)
	{
		this.joinTableNameOrAlias = StringUtils.trimBlankToNull(joinTableNameOrAlias);
		this.sqlExpression = sqlExpression;
	}

	public static ColumnSql ofSql(@NonNull final String sql, @Nullable final String joinTableName)
	{
		final IStringExpression sqlExpression = toStringExpression(sql, joinTableName);
		return new ColumnSql(joinTableName, sqlExpression);
	}

	public static ColumnSql ofSql(@NonNull final String sql)
	{
		return ofSql(sql, null);
	}

	private static IStringExpression toStringExpression(@NonNull final String sql, @Nullable final String joinTableName)
	{
		String sqlNorm = StringUtils.trimBlankToOptional(sql)
				.orElseThrow(() -> new AdempiereException("sql shall not be empty"));

		final String joinTableNameNorm = StringUtils.trimBlankToNull(joinTableName);
		if (joinTableNameNorm != null)
		{
			sqlNorm = sqlNorm.replace(joinTableNameNorm + ".", CTX_JoinTableNameOrAliasIncludingDot.toStringWithMarkers());
		}

		return StringExpressionCompiler.instance.compile(sqlNorm); // throws exception if sql is invalid
	}

	public ColumnSql withJoinOnTableNameOrAlias(@Nullable final String joinTableNameOrAlias)
	{
		return !Objects.equals(this.joinTableNameOrAlias, joinTableNameOrAlias)
				? new ColumnSql(joinTableNameOrAlias, sqlExpression)
				: this;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return toSqlString();
	}

	public String toSqlString()
	{
		String sqlBuilt = this._sqlBuilt;
		if (sqlBuilt == null)
		{
			final String joinTableNameOrAliasIncludingDot = joinTableNameOrAlias != null ? joinTableNameOrAlias + "." : "";
			final Evaluatee2 evalCtx = Evaluatees.ofSingleton(CTX_JoinTableNameOrAliasIncludingDot.toStringWithoutMarkers(), joinTableNameOrAliasIncludingDot);
			sqlBuilt = this._sqlBuilt = sqlExpression.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Preserve);
		}

		return sqlBuilt;
	}

	public String toSqlStringWrappedInBracketsIfNeeded()
	{
		final String sql = toSqlString();
		if (sql.contains(" ") && !sql.startsWith("("))
		{
			return "(" + sql + ")";
		}
		else
		{
			return sql;
		}
	}
}
