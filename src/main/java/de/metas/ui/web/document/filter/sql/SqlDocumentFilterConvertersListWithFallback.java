package de.metas.ui.web.document.filter.sql;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;
import lombok.ToString;

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
 * Composite {@link SqlDocumentFilterConverter} implementation which internally uses a converters list and a default converter.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
/* package */final class SqlDocumentFilterConvertersListWithFallback implements SqlDocumentFilterConverter
{
	public static final SqlDocumentFilterConvertersListWithFallback newInstance(final SqlDocumentFilterConvertersList converters, final SqlDocumentFilterConverter defaultConverter)
	{
		return new SqlDocumentFilterConvertersListWithFallback(converters, defaultConverter);
	}

	private final SqlDocumentFilterConvertersList converters;
	private final SqlDocumentFilterConverter defaultConverter;

	private SqlDocumentFilterConvertersListWithFallback(@NonNull final SqlDocumentFilterConvertersList converters, @NonNull final SqlDocumentFilterConverter defaultConverter)
	{
		this.converters = converters;
		this.defaultConverter = defaultConverter;
	}

	@Override
	public String getSql(
			@NonNull final SqlParamsCollector sqlParamsOut, 
			@NonNull final DocumentFilter filter, 
			@NonNull final SqlOptions sqlOpts)
	{
		// Find the effective converter to be used for given filter
		final String filterId = filter.getFilterId();
		final SqlDocumentFilterConverter effectiveConverter = converters.getConverterOrDefault(filterId, defaultConverter);

		// Convert the filter to SQL using the effective converter
		final String sqlFilter = effectiveConverter.getSql(sqlParamsOut, filter, sqlOpts);
		return sqlFilter;
	}
}
