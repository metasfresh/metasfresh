package de.metas.ui.web.document.filter.sql;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;

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

/**
 * Converts a {@link DocumentFilter} to SQL.
 * <p>
 * To create and manipulate {@link SqlDocumentFilterConverter}s please use {@link SqlDocumentFilterConverters}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface SqlDocumentFilterConverter
{
	/**
	 * @return true if the filter identified by <code>filterId</code> can be converted to SQL by this converter
	 */
	boolean canConvert(final String filterId);

	/**
	 * Converts given <code>filter</code> to SQL and returns it.
	 * In case the produced SQL requires parameters, those parameters will be added to <code>sqlParamsOut</code> parameter.
	 *
	 * @return SQL
	 */
	SqlFilter getSql(
			DocumentFilter filter,
			final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext context);

	/* final */
	default SqlFilter getSql(
			@NonNull final DocumentFilterList filters,
			@NonNull final SqlOptions sqlOpts,
			@NonNull final SqlDocumentFilterConverterContext context)
	{
		if (filters.isEmpty())
		{
			return SqlFilter.ACCEPT_ALL;
		}

		final ArrayList<SqlFilter> sqlFilters = new ArrayList<>();
		for (final DocumentFilter filter : filters.toList())
		{
			final SqlFilter sqlFilter = getSql(filter, sqlOpts, context);
			if (sqlFilter != null)
			{
				sqlFilters.add(sqlFilter);
			}
		}

		return SqlFilter.merge(sqlFilters);
	}

	default <T> IQueryFilter<T> createQueryFilter(
			@NonNull final DocumentFilterList filters,
			@NonNull final SqlOptions sqlOpts,
			@NonNull final SqlDocumentFilterConverterContext context)
	{
		final SqlAndParams sqlAndParams = getSql(filters, sqlOpts, context)
				.toSqlAndParams()
				.orElseThrow(() -> new AdempiereException("Empty filters are not allowed: " + filters));

		return TypedSqlQueryFilter.of(sqlAndParams.getSql(), sqlAndParams.getSqlParams());
	}
}
