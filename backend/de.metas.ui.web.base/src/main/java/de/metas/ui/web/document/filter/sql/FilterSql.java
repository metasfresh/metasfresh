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
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Result of {@link SqlDocumentFilterConverter}.
 */
@Value
public class FilterSql
{
	public static FilterSql ALLOW_ALL = builder().whereClause(null).build();
	public static FilterSql ALLOW_NONE = builder().whereClause(SqlAndParams.of(ConstantQueryFilter.of(false).getSql())).build();

	@Nullable SqlAndParams whereClause;

	@Nullable RecordsToAlwaysIncludeSql alwaysIncludeSql;

	@Nullable SqlAndParams orderBy;

	@Nullable String sqlComment;

	@Builder(toBuilder = true)
	private FilterSql(
			@Nullable final SqlAndParams whereClause,
			@Nullable final RecordsToAlwaysIncludeSql alwaysIncludeSql,
			@Nullable final SqlAndParams orderBy,
			@Nullable final String sqlComment)
	{
		this.whereClause = SqlAndParams.emptyToNull(whereClause);
		this.alwaysIncludeSql = alwaysIncludeSql;
		this.orderBy = SqlAndParams.emptyToNull(orderBy);
		this.sqlComment = StringUtils.trimBlankToNull(sqlComment);
	}

	public static FilterSql ofWhereClause(@Nullable final SqlAndParams whereClause)
	{
		return whereClause != null && !whereClause.isEmpty()
				? builder().whereClause(whereClause).build()
				: ALLOW_ALL;
	}

	public static FilterSql ofWhereClause(@NonNull final String whereClause, @Nullable final Object... sqlParams)
	{
		return ofWhereClause(SqlAndParams.of(whereClause, sqlParams));
	}

	public static FilterSql allowNoneWithComment(@Nullable final String comment)
	{
		return comment != null && !Check.isBlank(comment)
				? ALLOW_NONE.toBuilder().sqlComment(comment).build()
				: ALLOW_NONE;
	}

	public <T> IQueryFilter<T> toQueryFilterOrAllowAll(@NonNull final SqlOptions sqlOpts)
	{
		return toSqlAndParams(sqlOpts)
				.orElse(SqlAndParams.EMPTY)
				.toQueryFilterOrAllowAll();
	}

	public Optional<SqlAndParams> toSqlAndParams(@NonNull final SqlOptions sqlOpts)
	{
		final SqlAndParams alwaysIncludeSql = this.alwaysIncludeSql != null ? this.alwaysIncludeSql.toSqlAndParams() : null;

		return SqlAndParams.orNullables(this.whereClause, alwaysIncludeSql);
	}

	//
	//
	// ------------------------------
	//
	//

	@EqualsAndHashCode
	@ToString
	@NonFinal // because we want to mock it while testing
	public static class RecordsToAlwaysIncludeSql
	{
		@NonNull private final SqlAndParams sqlAndParams;

		private RecordsToAlwaysIncludeSql(@NonNull final SqlAndParams sqlAndParams)
		{
			if (sqlAndParams.isEmpty())
			{
				throw new AdempiereException("empty sqlAndParams is not allowed");
			}

			this.sqlAndParams = sqlAndParams;
		}

		public static RecordsToAlwaysIncludeSql ofColumnNameAndRecordIds(@NonNull final String columnName, @NonNull final Collection<?> recordIds)
		{
			final InArrayQueryFilter<?> builder = new InArrayQueryFilter<>(columnName, recordIds)
					.setEmbedSqlParams(false);
			final SqlAndParams sqlAndParams = SqlAndParams.of(builder.getSql(), builder.getSqlParams(Env.getCtx()));
			return new RecordsToAlwaysIncludeSql(sqlAndParams);
		}

		public static RecordsToAlwaysIncludeSql ofColumnNameAndRecordIds(@NonNull final String columnName, @NonNull final Object... recordIds)
		{
			return ofColumnNameAndRecordIds(columnName, Arrays.asList(recordIds));
		}

		@Nullable
		public static RecordsToAlwaysIncludeSql mergeOrNull(@Nullable final Collection<RecordsToAlwaysIncludeSql> collection)
		{
			if (collection == null || collection.isEmpty())
			{
				return null;
			}
			else if (collection.size() == 1)
			{
				return collection.iterator().next();
			}
			else
			{
				return SqlAndParams.orNullables(
								collection.stream()
										.filter(Objects::nonNull)
										.map(RecordsToAlwaysIncludeSql::toSqlAndParams)
										.collect(ImmutableSet.toImmutableSet()))
						.map(RecordsToAlwaysIncludeSql::new)
						.orElse(null);
			}
		}

		public SqlAndParams toSqlAndParams()
		{
			return sqlAndParams;
		}
	}
}
