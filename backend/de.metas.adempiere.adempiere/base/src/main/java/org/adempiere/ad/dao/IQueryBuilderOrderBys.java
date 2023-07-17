package org.adempiere.ad.dao;

import org.adempiere.model.ModelColumn;

public interface IQueryBuilderOrderBys<T>
{
	IQueryBuilder<T> self();

	/**
	 * Make sure this instance now has an order-by-builder and return it.
	 */
	IQueryBuilderOrderByClause<T> orderBy();

	default IQueryBuilder<T> clearOrderBys()
	{
		orderBy().clear();
		return self();
	}

	/**
	 * order ascending, with {@code NULLS LAST}
	 */
	default IQueryBuilder<T> orderBy(final String columnName)
	{
		orderBy().addColumn(columnName);
		return self();
	}

	/**
	 * order ascending, with {@code NULLS LAST}
	 */
	default IQueryBuilder<T> orderBy(final ModelColumn<T, ?> column)
	{
		orderBy().addColumn(column);
		return self();
	}

	default IQueryBuilder<T> orderByDescending(final String columnName)
	{
		orderBy().addColumnDescending(columnName);
		return self();
	}

	default IQueryBuilder<T> orderByDescending(final ModelColumn<T, ?> column)
	{
		orderBy().addColumnDescending(column.getColumnName());
		return self();
	}
}
