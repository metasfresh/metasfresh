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

package de.metas.ui.web.handlingunits.filter;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;

import java.util.Objects;

public final class HUIdsSqlDocumentFilterConverter implements SqlDocumentFilterConverter
{
	private final HUIdsFilterDataToSqlCaseConverter FILTER_DATA_CONVERTER = new HUIdsFilterDataToSqlCaseConverter();

	HUIdsSqlDocumentFilterConverter()
	{
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return Objects.equals(filterId, HUIdsFilterHelper.FILTER_ID);
	}

	@Override
	public FilterSql getSql(
			@NonNull final DocumentFilter filter,
			final SqlOptions sqlOpts_NOTUSED,
			@NonNull final SqlDocumentFilterConverterContext context)
	{
		final HUIdsFilterData huIdsFilter = HUIdsFilterHelper.extractFilterData(filter);
		return huIdsFilter.convert(FILTER_DATA_CONVERTER);
	}

}
