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

package de.metas.ui.web.view.descriptor;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.IStringExpressionWrapper;
import org.adempiere.ad.expression.api.NullStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Value
public class SqlAndParamsExpression
{
	public static Builder builder() { return new Builder(); }

	@NonNull IStringExpression sql;
	@NonNull List<Object> sqlParams;

	private SqlAndParamsExpression(@NonNull final SqlAndParamsExpression.Builder builder)
	{
		this.sql = builder.sql != null ? builder.sql.build() : NullStringExpression.NULL;
		this.sqlParams = builder.sqlParams != null && !builder.sqlParams.isEmpty()
				? Collections.unmodifiableList(new ArrayList<>(builder.sqlParams))
				: ImmutableList.of();
	}

	public static SqlAndParamsExpression of(@NonNull final String sql)
	{
		return builder().append(sql).build();
	}

	public boolean isEmpty()
	{
		return sql.isNullExpression() && sqlParams.isEmpty();
	}

	public SqlAndParams evaluate(@NonNull final Evaluatee evalCtx)
	{
		return SqlAndParams.of(
				this.sql.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Fail),
				this.sqlParams);
	}

	//
	//
	// -------------------------------------------------------
	//
	//

	@SuppressWarnings("UnusedReturnValue")
	public static class Builder
	{
		private final CompositeStringExpression.Builder sql = IStringExpression.composer();
		private final ArrayList<Object> sqlParams = new ArrayList<>();

		private Builder() {}

		public SqlAndParamsExpression build() { return new SqlAndParamsExpression(this); }

		public Builder appendIfNotEmpty(@Nullable final String sqlToAppend)
		{
			if (sqlToAppend != null)
			{
				sql.appendIfNotEmpty(sqlToAppend);
			}
			return this;
		}

		public Builder append(@NonNull final String sqlToAppend, final Object... sqlParamsToAppend)
		{
			sql.append(sqlToAppend);
			sqlParams.addAll(Arrays.asList(sqlParamsToAppend));
			return this;
		}

		public Builder append(@Nullable final SqlAndParams sqlAndParams)
		{
			if (sqlAndParams != null)
			{
				sql.append(sqlAndParams.getSql());
				sqlParams.addAll(sqlAndParams.getSqlParams());
			}
			return this;
		}

		public Builder append(@NonNull final SqlAndParamsExpression sqlToAppend)
		{
			sql.append(sqlToAppend.getSql());
			sqlParams.addAll(sqlToAppend.getSqlParams());
			return this;
		}

		public Builder append(@NonNull final SqlAndParamsExpression.Builder sqlToAppend)
		{
			sql.append(sqlToAppend.sql);
			sqlParams.addAll(sqlToAppend.sqlParams);
			return this;
		}

		public Builder append(@NonNull final IStringExpression sqlToAppend)
		{
			sql.append(sqlToAppend);
			return this;
		}

		public Builder append(@Nullable final String sqlToAppend)
		{
			if (sqlToAppend != null)
			{
				sql.append(sqlToAppend);
			}
			return this;
		}

		public Builder appendSqlList(@NonNull final String sqlColumnName, @NonNull final Collection<?> values)
		{
			final String sqlToAppend = DB.buildSqlList(sqlColumnName, values, sqlParams);
			sql.append(sqlToAppend);
			return this;
		}

		public boolean isEmpty()
		{
			return sql.isEmpty() && sqlParams.isEmpty();
		}

		public Builder wrap(@NonNull final IStringExpressionWrapper wrapper)
		{
			sql.wrap(wrapper);
			return this;
		}
	}
}
