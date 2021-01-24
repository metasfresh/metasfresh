package de.metas.ui.web.window.model.sql;

import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.descriptor.sql.SqlOrderByValue;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;

import java.util.Optional;

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
	public interface SqlOrderByBindings
	{
		SqlOrderByValue getFieldOrderBy(String fieldName);
	}

	private final SqlOrderByBindings bindings;
	private String joinOnTableNameOrAlias;
	private boolean useColumnNameAlias;

	private SqlDocumentOrderByBuilder(@NonNull final SqlOrderByBindings bindings)
	{
		this.bindings = bindings;
	}

	public SqlDocumentOrderByBuilder joinOnTableNameOrAlias(final String joinOnTableNameOrAlias)
	{
		this.joinOnTableNameOrAlias = joinOnTableNameOrAlias;
		return this;
	}

	public SqlDocumentOrderByBuilder useColumnNameAlias(final boolean useColumnNameAlias)
	{
		this.useColumnNameAlias = useColumnNameAlias;
		return this;
	}

	/**
	 * @return SQL order by (e.g. Column1 ASC, Column2 DESC)
	 */
	public Optional<IStringExpression> buildSqlOrderBy(final DocumentQueryOrderByList orderBys)
	{
		if (orderBys.isEmpty())
		{
			return Optional.empty();
		}

		final IStringExpression result = orderBys
				.stream()
				.map(this::buildSqlOrderBy)
				.filter(sql -> sql != null && !sql.isNullExpression())
				.collect(IStringExpression.collectJoining(", "));

		return result != null && !result.isNullExpression()
				? Optional.of(result)
				: Optional.empty();
	}

	private final IStringExpression buildSqlOrderBy(final DocumentQueryOrderBy orderBy)
	{
		final String fieldName = orderBy.getFieldName();
		final SqlOrderByValue sqlExpression = bindings.getFieldOrderBy(fieldName);
		return buildSqlOrderBy(sqlExpression, orderBy.isAscending(), orderBy.isNullsLast());
	}

	private final IStringExpression buildSqlOrderBy(
			final SqlOrderByValue orderBy,
			final boolean ascending,
			final boolean nullsLast)
	{
		if (orderBy.isNullExpression())
		{
			return IStringExpression.NULL;
		}

		final CompositeStringExpression.Builder sql = IStringExpression.composer();
		if (useColumnNameAlias)
		{
			sql.append(orderBy.withJoinOnTableNameOrAlias(joinOnTableNameOrAlias).toSqlStringUsingColumnAlias());
		}
		else
		{
			sql.append("(").append(orderBy.withJoinOnTableNameOrAlias(joinOnTableNameOrAlias).toStringExpression()).append(")");

		}

		return sql.append(ascending ? " ASC" : " DESC")
				.append(nullsLast ? " NULLS LAST" : " NULLS FIRST")
				.build();
	}

}
