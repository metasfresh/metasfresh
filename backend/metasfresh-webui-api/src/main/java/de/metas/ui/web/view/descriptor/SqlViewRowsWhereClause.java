package de.metas.ui.web.view.descriptor;

import java.util.Objects;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;

import lombok.Builder;
import lombok.Value;

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
 * Target table: rows original table
 */
@Value
@Builder(toBuilder = true)
public class SqlViewRowsWhereClause
{
	public static SqlViewRowsWhereClause noRecords()
	{
		return NO_RECORDS;
	}

	private static final SqlViewRowsWhereClause NO_RECORDS = builder().noRecords(true).build();
	private static final SqlAndParams SQL_ALWAYS_FALSE = SqlAndParams.of("1=2");

	boolean noRecords;
	SqlAndParams rowsPresentInViewSelection;
	SqlAndParams rowsPresentInTable;
	SqlAndParams rowsMatchingFilter;

	public boolean isNoRecords()
	{
		return toSqlAndParams() == SQL_ALWAYS_FALSE;
	}

	public SqlAndParams toSqlAndParams()
	{
		if (noRecords)
		{
			return SQL_ALWAYS_FALSE;
		}

		return SqlAndParams.andNullables(
				rowsPresentInViewSelection,
				rowsPresentInTable,
				rowsMatchingFilter)
				.orElse(SQL_ALWAYS_FALSE);
	}

	public String toSqlString()
	{
		final SqlAndParams sqlAndParams = toSqlAndParams();
		if (sqlAndParams.hasParams())
		{
			throw new AdempiereException("Cannot convert SQL to String because it has parameters: " + sqlAndParams);
		}

		return sqlAndParams.getSql();
	}

	public <T> IQueryFilter<T> toQueryFilter()
	{
		final SqlAndParams sqlAndParams = toSqlAndParams();
		return TypedSqlQueryFilter.of(sqlAndParams.getSql(), sqlAndParams.getSqlParams());

	}

	public SqlViewRowsWhereClause withRowsMatchingFilter(final SqlAndParams rowsMatchingFilter)
	{
		if (Objects.equals(this.rowsMatchingFilter, rowsMatchingFilter))
		{
			return this;
		}

		return toBuilder().rowsMatchingFilter(rowsMatchingFilter).build();
	}
}
