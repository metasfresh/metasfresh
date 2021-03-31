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

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlFilter;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;

public class CompositeSqlDocumentFilterConverter implements SqlDocumentFilterConverter
{
	private final ImmutableList<SqlDocumentFilterConverter> converters;

	@Builder
	private CompositeSqlDocumentFilterConverter(
			@NonNull @Singular final ImmutableList<SqlDocumentFilterConverter> converters)
	{
		this.converters = converters;
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return converters.stream().anyMatch(converter -> converter.canConvert(filterId));
	}

	@Override
	public SqlFilter getSql(
			final DocumentFilter filter,
			final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext context)
	{
		return converters.stream()
				.filter(converter -> converter.canConvert(filter.getFilterId()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Cannot convert " + filter))
				.getSql(filter, sqlOpts, context);
	}
}
