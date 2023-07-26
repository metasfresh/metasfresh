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

import de.metas.elasticsearch.model.I_T_ES_FTS_Search_Result;
import de.metas.fulltextsearch.config.FTSJoinColumnList;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * Result of {@link SqlDocumentFilterConverter}.
 */
@Value
public class FilterSql
{
	public static FilterSql NULL = builder().build();

	public static FilterSql ALLOW_ALL = NULL;

	private static SqlAndParams WHERECLAUSE_ALLOW_NONE = SqlAndParams.of(ConstantQueryFilter.of(false).getSql());
	public static FilterSql ALLOW_NONE = builder().whereClause(WHERECLAUSE_ALLOW_NONE).build();

	@Nullable SqlAndParams whereClause;
	@Nullable FullTextSearchResult filterByFTS;
	@Nullable SqlAndParams orderBy;

	@Nullable String sqlComment;

	@Builder
	private FilterSql(
			@Nullable final SqlAndParams whereClause,
			@Nullable final FullTextSearchResult filterByFTS,
			@Nullable final SqlAndParams orderBy,
			@Nullable final String sqlComment)
	{
		this.whereClause = SqlAndParams.emptyToNull(whereClause);
		this.filterByFTS = filterByFTS;
		this.orderBy = SqlAndParams.emptyToNull(orderBy);
		this.sqlComment = StringUtils.trimBlankToNull(sqlComment);
	}

	public static FilterSql ofWhereClause(@Nullable final SqlAndParams whereClause)
	{
		return whereClause != null && !whereClause.isEmpty()
				? builder().whereClause(whereClause).build()
				: NULL;
	}

	public static FilterSql ofWhereClause(@NonNull final String whereClause, @Nullable final Object... sqlParams)
	{
		return ofWhereClause(SqlAndParams.of(whereClause, sqlParams));
	}

	public static FilterSql allowNoneWithComment(@Nullable final String comment)
	{
		if (comment == null || Check.isBlank(comment))
		{
			return ALLOW_NONE;
		}
		else
		{
			return builder().whereClause(WHERECLAUSE_ALLOW_NONE).sqlComment(comment).build();
		}
	}

	public boolean isNull()
	{
		return whereClause == null
				&& filterByFTS == null
				&& orderBy == null;
	}

	public boolean isAllowAll()
	{
		return whereClause == null
				&& filterByFTS == null;
	}

	public boolean isAllowNone()
	{
		return WHERECLAUSE_ALLOW_NONE.equals(whereClause);
	}

	public <T> IQueryFilter<T> toQueryFilterOrAllowAll()
	{
		if (filterByFTS != null)
		{
			throw new AdempiereException("FTS filters cannot be converted to " + IQueryFilter.class + ": " + this);
		}

		return whereClause != null ? whereClause.toQueryFilterOrAllowAll() : ConstantQueryFilter.of(true);
	}

	//
	//
	// ------------------------------
	//
	//

	@Value
	@Builder
	public static class FullTextSearchResult
	{
		@NonNull String searchId;
		@NonNull FTSJoinColumnList joinColumns;

		public SqlAndParams buildInnerJoinClause(
				@NonNull final String targetTableNameOrAlias,
				@NonNull final String selectionTableAlias)
		{
			return SqlAndParams.builder()
					.append("\n INNER JOIN ").append(I_T_ES_FTS_Search_Result.Table_Name).append(" ").append(selectionTableAlias).append(" ON (")
					.append(buildJoinCondition(targetTableNameOrAlias, selectionTableAlias))
					.append(")")
					.build();
		}

		public SqlAndParams buildJoinCondition(
				@NonNull final String targetTableNameOrAlias,
				@NonNull final String selectionTableAlias)
		{
			return SqlAndParams.builder()
					.append(selectionTableAlias).append(".").append(I_T_ES_FTS_Search_Result.COLUMNNAME_Search_UUID).append("=?", searchId)
					.append(" AND ").append(joinColumns.buildJoinCondition(targetTableNameOrAlias, selectionTableAlias))
					.build();
		}

		public SqlAndParams buildExistsWhereClause(@NonNull final String targetTableNameOrAlias)
		{
			return SqlAndParams.builder()
					.append("EXISTS (SELECT 1 FROM ").append(I_T_ES_FTS_Search_Result.Table_Name).append("fts")
					.append(" WHERE ").append(buildJoinCondition(targetTableNameOrAlias, "fts"))
					.append(")")
					.build();
		}

		public SqlAndParams buildOrderBy(@NonNull final String selectionTableAlias)
		{
			return SqlAndParams.of(selectionTableAlias + "." + I_T_ES_FTS_Search_Result.COLUMNNAME_Line);
		}

	}
}
