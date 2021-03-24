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

package de.metas.ui.web.document.filter.sql;

import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Value
public class SqlFilter
{
	public static final SqlFilter ACCEPT_ALL = builder().constantValue(true).build();
	public static final SqlFilter ACCEPT_NONE = builder().constantValue(false).build();

	Boolean constantValue;
	@NonNull ImmutableSet<SqlAndParams> filterClauses;
	@NonNull ImmutableSet<SqlAndParams> includeClauses;
	@NonNull ImmutableSet<SqlAndParams> excludeClauses;

	@Builder
	private SqlFilter(
			@Nullable final Boolean constantValue,
			@Singular @Nullable final ImmutableSet<SqlAndParams> filterClauses,
			@Singular @Nullable final ImmutableSet<SqlAndParams> includeClauses,
			@Singular @Nullable final ImmutableSet<SqlAndParams> excludeClauses
	)
	{
		if (constantValue == null)
		{
			this.filterClauses = normalizeClausesSet(filterClauses);
			this.includeClauses = normalizeClausesSet(includeClauses);
			this.excludeClauses = normalizeClausesSet(excludeClauses);

			this.constantValue = this.filterClauses.isEmpty() && this.includeClauses.isEmpty() && this.excludeClauses.isEmpty() ? true : null;
		}
		else
		{
			this.constantValue = constantValue;
			this.filterClauses = ImmutableSet.of();
			this.includeClauses = ImmutableSet.of();
			this.excludeClauses = ImmutableSet.of();
		}
	}

	private static ImmutableSet<SqlAndParams> normalizeClausesSet(@Nullable final ImmutableSet<SqlAndParams> clauses)
	{
		if (clauses == null || clauses.isEmpty())
		{
			return ImmutableSet.of();
		}
		else
		{
			return clauses.stream().filter(clause -> !clause.isEmpty()).collect(ImmutableSet.toImmutableSet());
		}
	}

	public static SqlFilter of(@NonNull final String sql, @Nullable final Object... sqlParams)
	{
		return of(SqlAndParams.of(sql, sqlParams));
	}

	public static SqlFilter of(@NonNull final String sql, @Nullable final List<Object> sqlParams)
	{
		return of(SqlAndParams.of(sql, sqlParams));
	}

	public static SqlFilter of(@NonNull final String sql, @Nullable final SqlParamsCollector sqlParams)
	{
		return of(SqlAndParams.of(sql, sqlParams));
	}

	public static SqlFilter of(@NonNull final ISqlQueryFilter sqlQueryFilter)
	{
		return of(sqlQueryFilter.getSql(), sqlQueryFilter.getSqlParams(Env.getCtx()));
	}

	public static SqlFilter of(@NonNull final SqlAndParams sqlAndParams)
	{
		return !sqlAndParams.isEmpty()
				? builder().filterClause(sqlAndParams).build()
				: ACCEPT_ALL;
	}

	public static SqlFilter merge(@NonNull final Collection<SqlFilter> sqlFilters)
	{
		if (sqlFilters.isEmpty())
		{
			return ACCEPT_ALL;
		}

		SqlFilterBuilder builder = null;

		for (final SqlFilter sqlFilter : sqlFilters)
		{
			//noinspection StatementWithEmptyBody
			if (sqlFilter.isConstantTrue())
			{
				// skip this filter
			}
			else if (sqlFilter.isConstantFalse())
			{
				return ACCEPT_NONE;
			}
			else
			{
				if (builder == null)
				{
					builder = builder();
				}

				builder.filterClauses(sqlFilter.getFilterClauses());
				builder.includeClauses(sqlFilter.getIncludeClauses());
				builder.excludeClauses(sqlFilter.getExcludeClauses());
			}
		}

		return builder != null
				? builder.build()
				: ACCEPT_ALL;
	}

	public boolean isConstantTrue() { return constantValue != null && constantValue; }

	public boolean isConstantFalse() { return constantValue != null && !constantValue; }

	public Optional<SqlAndParams> toSqlAndParams()
	{
		if (isConstantTrue())
		{
			return Optional.empty();
		}
		else if (isConstantFalse())
		{
			return Optional.of(SqlAndParams.ACCEPT_NONE);
		}
		else
		{
			final SqlAndParams filterClauses = sql_AND(this.filterClauses).orElse(null);

			final Set<SqlAndParams> includeClausesEffective;
			if (filterClauses == null)
			{
				includeClausesEffective = this.includeClauses;
			}
			else if (this.includeClauses.isEmpty())
			{
				includeClausesEffective = ImmutableSet.of(filterClauses);
			}
			else
			{
				includeClausesEffective = ImmutableSet.<SqlAndParams>builder()
						.add(filterClauses)
						.addAll(this.includeClauses)
						.build();
			}

			final SqlAndParams excludeClauses = sql_OR(this.excludeClauses).orElse(null);

			return sql_A_AND_NOT_B(
					sql_OR(includeClausesEffective).orElse(null),
					excludeClauses);
		}
	}

	private static Optional<SqlAndParams> sql_AND(@Nullable final Collection<SqlAndParams> sqls)
	{
		if (sqls == null || sqls.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableSet.Builder<SqlAndParams> sqlsNorm = ImmutableSet.builder();
		for (final SqlAndParams sql : sqls)
		{
			// skip nulls
			// skip TRUE values because those are not relevant in ANDs
			if (sql == null || sql.isAcceptAll())
			{
				continue;
			}

			if (sql.isAcceptNone())
			{
				return Optional.of(SqlAndParams.ACCEPT_NONE);
			}
			else
			{
				sqlsNorm.add(sql);
			}
		}

		return SqlAndParams.andNullables(sqlsNorm.build());
	}

	private static Optional<SqlAndParams> sql_OR(@Nullable final Collection<SqlAndParams> sqls)
	{
		if (sqls == null || sqls.isEmpty())
		{
			return Optional.empty();
		}

		if (sqls.stream().anyMatch(sqlAndParam -> sqlAndParam != null && sqlAndParam.isAcceptAll()))
		{
			return Optional.of(SqlAndParams.ACCEPT_ALL);
		}

		final ImmutableSet.Builder<SqlAndParams> sqlsNorm = ImmutableSet.builder();
		for (final SqlAndParams sql : sqls)
		{
			// skip nulls
			// skip FALSE values because those are not relevant in ORs
			if (sql == null || sql.isAcceptNone())
			{
				continue;
			}

			if (sql.isAcceptAll())
			{
				return Optional.of(SqlAndParams.ACCEPT_ALL);
			}
			else
			{
				sqlsNorm.add(sql);
			}
		}

		return SqlAndParams.orNullables(sqlsNorm.build());
	}

	private static Optional<SqlAndParams> sql_A_AND_NOT_B(@Nullable final SqlAndParams a, @Nullable final SqlAndParams b)
	{
		if (b != null)
		{
			if (a == null)
			{
				return Optional.of(sql_NOT(b));
			}
			else
			{
				if (a.isAcceptNone() || b.isAcceptAll())
				{
					return Optional.of(SqlAndParams.ACCEPT_NONE);
				}
				else if (a.isAcceptAll() || b.isAcceptNone())
				{
					return Optional.of(SqlAndParams.ACCEPT_ALL);
				}
				else
				{
					return Optional.of(a.andNot(b));
				}
			}
		}
		else if (a != null)
		{
			return Optional.of(a);
		}
		else
		{
			return Optional.empty();
		}
	}

	private static SqlAndParams sql_NOT(@NonNull final SqlAndParams sql)
	{
		if (sql.isAcceptAll())
		{
			return SqlAndParams.ACCEPT_NONE;
		}
		else if (sql.isAcceptNone())
		{
			return SqlAndParams.ACCEPT_ALL;
		}
		else
		{
			return sql.not();
		}
	}
}
