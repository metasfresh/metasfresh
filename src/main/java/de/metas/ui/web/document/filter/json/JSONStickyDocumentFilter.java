package de.metas.ui.web.document.filter.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility=Visibility.NONE, setterVisibility = Visibility.NONE)
@lombok.Data
public class JSONStickyDocumentFilter
{
	private final String id;
	private final String caption;
	
	public static final List<JSONStickyDocumentFilter> ofStickyFiltersList(final DocumentFilterList filters, final String adLanguage)
	{
		if (filters == null || filters.isEmpty())
		{
			return ImmutableList.of();
		}

		return filters.stream()
				.map(filter -> ofStickyFilterOrNull(filter, adLanguage))
				.filter(filter -> filter != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final JSONStickyDocumentFilter ofStickyFilterOrNull(final DocumentFilter filter, final String adLanguage)
	{
		// Don't expose the sticky filter if it does not have a caption,
		// because usually that's an internal filter.
		// (see https://github.com/metasfresh/metasfresh-webui-api/issues/481)
		final String caption = filter.getCaption(adLanguage);
		if(Check.isEmpty(caption, true))
		{
			return null;
		}
		
		final String filterId = filter.getFilterId();
		
		return new JSONStickyDocumentFilter(filterId, caption);
	}

}
