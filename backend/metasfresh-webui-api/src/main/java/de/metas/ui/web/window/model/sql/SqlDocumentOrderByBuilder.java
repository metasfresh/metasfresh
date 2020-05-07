package de.metas.ui.web.window.model.sql;

import java.util.List;

import org.adempiere.ad.expression.api.IStringExpression;

import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class SqlDocumentOrderByBuilder
{
	public static final SqlDocumentOrderByBuilder newInstance(final SqlEntityBinding entityBinding)
	{
		return new SqlDocumentOrderByBuilder(entityBinding::getFieldOrderBy);
	}

	public static final SqlDocumentOrderByBuilder newInstance(final SqlOrderByBindings bindings)
	{
		return new SqlDocumentOrderByBuilder(bindings);
	}

	@FunctionalInterface
	public static interface SqlOrderByBindings
	{
		IStringExpression getFieldOrderBy(String fieldName);
	}

	private final SqlOrderByBindings bindings;

	private SqlDocumentOrderByBuilder(@NonNull final SqlOrderByBindings bindings)
	{
		this.bindings = bindings;
	}

	/**
	 * @return SQL order by (e.g. Column1 ASC, Column2 DESC)
	 */
	public IStringExpression buildSqlOrderBy(final List<DocumentQueryOrderBy> orderBys)
	{
		if (orderBys.isEmpty())
		{
			return null;
		}

		final IStringExpression sqlOrderByFinal = orderBys
				.stream()
				.map(orderBy -> buildSqlOrderBy(orderBy))
				.filter(sql -> sql != null && !sql.isNullExpression())
				.collect(IStringExpression.collectJoining(", "));

		return sqlOrderByFinal;
	}

	private final IStringExpression buildSqlOrderBy(final DocumentQueryOrderBy orderBy)
	{
		final String fieldName = orderBy.getFieldName();
		final IStringExpression sqlExpression = bindings.getFieldOrderBy(fieldName);
		return buildSqlOrderBy(sqlExpression, orderBy.isAscending(), orderBy.isNullsLast());
	}

	/**
	 * 
	 * @param sqlExpression
	 * @param ascending
	 * @return ORDER BY SQL or empty
	 */
	private static final IStringExpression buildSqlOrderBy(final IStringExpression sqlExpression, final boolean ascending, final boolean nullsLast)
	{
		if (sqlExpression.isNullExpression())
		{
			return sqlExpression;
		}

		return IStringExpression.composer()
				.append("(").append(sqlExpression).append(")")
				.append(ascending ? " ASC" : " DESC")
				.append(nullsLast ? " NULLS LAST" : " NULLS FIRST")
				.build();
	}

}
