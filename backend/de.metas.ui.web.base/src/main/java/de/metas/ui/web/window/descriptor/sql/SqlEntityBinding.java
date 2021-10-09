package de.metas.ui.web.window.descriptor.sql;

import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConvertersList;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IStringExpression;

import java.util.Optional;
import java.util.regex.Pattern;

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

public interface SqlEntityBinding
{
	String getTableName();

	String getTableAlias();

	IStringExpression getSqlWhereClause();

	/**
	 * @return field binding or throws exception in case it was not found
	 */
	SqlEntityFieldBinding getFieldByFieldName(String fieldName);

	/**
	 * @return SQL expression to be used when ordering by given field; if the field was not found and exception will be thrown
	 */
	default SqlOrderByValue getFieldOrderBy(String fieldName)
	{
		return getFieldByFieldName(fieldName).getSqlOrderBy();
	}

	default DocumentFilterDescriptorsProvider getFilterDescriptors()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * @return registered document filter to SQL converters
	 */
	default SqlDocumentFilterConvertersList getFilterConverters()
	{
		return SqlDocumentFilterConverters.emptyList();
	}

	default Optional<SqlDocumentFilterConverterDecorator> getFilterConverterDecorator()
	{
		return Optional.empty();
	}

	default String replaceTableNameWithTableAlias(final String sql)
	{
		final String tableName = getTableName();
		final String tableAlias = getTableAlias();
		return replaceTableNameWithTableAlias(sql, tableName, tableAlias);
	}

	default String replaceTableNameWithTableAlias(final String sql, @NonNull final String tableAlias)
	{
		final String tableName = getTableName();
		return replaceTableNameWithTableAlias(sql, tableName, tableAlias);
	}

	default SqlAndParams replaceTableNameWithTableAlias(final SqlAndParams sql, @NonNull final String tableAlias)
	{
		return SqlAndParams.of(
				replaceTableNameWithTableAlias(sql.getSql(), tableAlias),
				sql.getSqlParams());
	}

	static String replaceTableNameWithTableAlias(final String sql, @NonNull final String tableName, @NonNull final String tableAlias)
	{
		if (sql == null || sql.isEmpty())
		{
			return sql;
		}

		final String matchTableNameIgnoringCase = "(?i)" + Pattern.quote(tableName + ".");
		final String sqlFixed = sql.replaceAll(matchTableNameIgnoringCase, tableAlias + ".");
		return sqlFixed;
	}
}
