package de.metas.ui.web.window.descriptor.sql;

import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.compiere.util.Evaluatee;

import de.metas.printing.esb.base.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode
@ToString
public class SqlSelectDisplayValue
{
	private final String joinOnTableNameOrAlias;
	private final String joinOnColumnName;
	private final SqlForFetchingLookupById sqlExpression;
	@Getter
	private final String columnNameAlias;

	@Builder(toBuilder = true)
	private SqlSelectDisplayValue(
			@Nullable final String joinOnTableNameOrAlias,
			@NonNull final String joinOnColumnName,
			@Nullable final SqlForFetchingLookupById sqlExpression,
			@NonNull final String columnNameAlias)
	{
		this.joinOnTableNameOrAlias = joinOnTableNameOrAlias;
		this.joinOnColumnName = joinOnColumnName;
		this.sqlExpression = sqlExpression;
		this.columnNameAlias = columnNameAlias;
	}

	public String toSqlStringWithColumnNameAlias(@NonNull final Evaluatee ctx)
	{
		return toStringExpressionWithColumnNameAlias().evaluate(ctx, OnVariableNotFound.Fail);
	}

	public IStringExpression toStringExpressionWithColumnNameAlias()
	{
		return IStringExpression.composer()
				.append("(").append(toStringExpression()).append(") AS ").append(columnNameAlias)
				.build();
	}

	public IStringExpression toStringExpression()
	{
		final String joinOnColumnNameFQ = !Check.isEmpty(joinOnTableNameOrAlias)
				? joinOnTableNameOrAlias + "." + joinOnColumnName
				: joinOnColumnName;

		if (sqlExpression == null)
		{
			return ConstantStringExpression.of(joinOnColumnNameFQ);
		}
		else
		{
			return sqlExpression.toStringExpression(joinOnColumnNameFQ);
		}
	}

	public SqlSelectDisplayValue withJoinOnTableNameOrAlias(final String joinOnTableNameOrAlias)
	{
		return !Objects.equals(this.joinOnTableNameOrAlias, joinOnTableNameOrAlias)
				? toBuilder().joinOnTableNameOrAlias(joinOnTableNameOrAlias).build()
				: this;
	}
}
