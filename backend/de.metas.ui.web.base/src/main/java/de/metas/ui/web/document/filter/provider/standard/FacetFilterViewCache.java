package de.metas.ui.web.document.filter.provider.standard;

import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.OptionalInt;

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

@Builder
@EqualsAndHashCode
@ToString
public class FacetFilterViewCache
{
	@NonNull @Getter private final String filterId;
	@NonNull private final LookupValuesList availableValues;
	private final boolean hasMoreResults;

	public LookupValuesPage pageByOffsetAndLimit(final int offset, final int limit)
	{
		LookupValuesPage page = availableValues.pageByOffsetAndLimit(offset, limit);
		if (hasMoreResults)
		{
			page = page
					.withTotalRows(OptionalInt.empty())
					.withHasMoreResults(OptionalBoolean.TRUE);
		}
		
		return page;
	}
}

