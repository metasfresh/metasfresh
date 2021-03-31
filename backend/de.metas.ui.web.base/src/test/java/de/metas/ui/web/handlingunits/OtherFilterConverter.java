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

package de.metas.ui.web.handlingunits;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlFilter;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;

class OtherFilterConverter implements SqlDocumentFilterConverter
{
	private static final String FILTER_ID = "OTHER_FILTER";

	public static DocumentFilter newFilter()
	{
		return DocumentFilter.builder()
				.setFilterId(FILTER_ID)
				.build();
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return FILTER_ID.equals(filterId);
	}

	@Override
	public SqlFilter getSql(
			final DocumentFilter filter,
			final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext context)
	{
		return SqlFilter.builder()
				.filterClause(SqlAndParams.of("OTHER_FILTER_CLAUSE"))
				.includeClause(SqlAndParams.of("OTHER_FILTER_INCLUDE_CLAUSE"))
				.excludeClause(SqlAndParams.of("OTHER_FILTER_EXCLUDE_CLAUSE"))
				.build();
	}
}
