package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.adempiere.util.comparator.ComparableComparatorNullsEqual;
import org.adempiere.util.comparator.ComparatorChain;
import org.adempiere.util.comparator.NullComparator;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;

@EqualsAndHashCode(of = "items")
class QueryOrderBy implements IQueryOrderBy
{
	public static final QueryOrderBy NONE = new QueryOrderBy(ImmutableList.of());

	@NonNull private final ImmutableList<QueryOrderByItem> items;

	private boolean sqlOrderByCompiled = false;
	private String sqlOrderBy = null; // lazy

	private Comparator<Object> comparator = null; // lazy

	private QueryOrderBy(@NonNull final List<QueryOrderByItem> items)
	{
		this.items = ImmutableList.copyOf(items);
	}

	public static QueryOrderBy of(@Nullable final List<QueryOrderByItem> items)
	{
		return items != null && !items.isEmpty() ? new QueryOrderBy(items) : NONE;
	}

	/** @deprecated kept only for legacy human-readable logging; use {@link #getSql()} to obtain the SQL ORDER BY clause. */
	@Override
	@Deprecated
	public String toString()
	{
		final String sql = getSql();
		return sql != null ? sql : "";
	}

	@Override
	public String getSql()
	{
		String sqlOrderBy = this.sqlOrderBy;
		if (!sqlOrderByCompiled)
		{
			sqlOrderBy = this.sqlOrderBy = getSql(UnaryOperator.identity());
			this.sqlOrderByCompiled = true;
		}
		return sqlOrderBy;
	}

	public String getSql(@NonNull final UnaryOperator<String> columnNameMapper)
	{
		if (items.isEmpty())
		{
			return null;
		}

		final StringBuilder sqlBuf = new StringBuilder();
		for (final QueryOrderByItem item : items)
		{
			appendSql(sqlBuf, item, columnNameMapper);
		}

		// null (not "") when every item was skipped by the mapper — consistent with the empty-items case above and the @Nullable contract.
		return StringUtils.trimBlankToNull(sqlBuf.toString());
	}

	private static void appendSql(
			@NonNull final StringBuilder sql,
			@NonNull final QueryOrderByItem item,
			@NonNull final UnaryOperator<String> columnNameMapper)
	{
		final String columnName = item.getColumnName();
		final String columnSql = StringUtils.trimBlankToNull(columnNameMapper.apply(columnName));
		if (columnSql == null)
		{
			return;
		}

		if (sql.length() > 0)
		{
			sql.append(", ");
		}

		//
		// ColumnName/ColumnSql
		if (columnName.equals(columnSql))
		{
			sql.append(columnName);
		}
		else
		{
			sql.append("(").append(columnSql).append(")");
		}

		//
		// Direction ASC/DESC
		final Direction direction = item.getDirection();
		if (direction == Direction.Ascending)
		{
			sql.append(" ASC");
		}
		else if (direction == Direction.Descending)
		{
			sql.append(" DESC");
		}

		//
		// Nulls First/Last
		final Nulls nulls = item.getNulls();
		if (nulls == Nulls.First)
		{
			sql.append(" NULLS FIRST");
		}
		else if (nulls == Nulls.Last)
		{
			sql.append(" NULLS LAST");
		}
	}

	@Override
	@NonNull
	public Comparator<Object> getComparator()
	{
		Comparator<Object> comparator = this.comparator;
		if (comparator == null)
		{
			comparator = this.comparator = computeComparator();
		}
		return comparator;
	}

	private Comparator<Object> computeComparator()
	{
		if (items.isEmpty()) {return NullComparator.getInstance();}

		final ComparatorChain<Object> cmpChain = new ComparatorChain<>();
		for (final QueryOrderByItem item : items)
		{
			@SuppressWarnings("rawtypes") final ModelAccessor<Comparable> accessor = new ModelAccessor<>(item.getColumnName());

			final boolean reverse = item.getDirection() == Direction.Descending;
			final Comparator<Object> cmpDirection = new AccessorComparator<>(
					ComparableComparatorNullsEqual.getInstance(),
					accessor);
			cmpChain.addComparator(cmpDirection, reverse);

			final boolean nullsFirst = item.getNulls() == Nulls.First;
			final Comparator<Object> cmpNulls = new AccessorComparator<>(
					ComparableComparator.getInstance(nullsFirst),
					accessor);

			cmpChain.addComparator(cmpNulls);
		}

		return cmpChain;
	}
}
