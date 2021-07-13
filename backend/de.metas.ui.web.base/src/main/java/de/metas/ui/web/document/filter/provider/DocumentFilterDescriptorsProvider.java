package de.metas.ui.web.document.filter.provider;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Document filter descriptors provider
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface DocumentFilterDescriptorsProvider
{
	/**
	 * @return all available filter descriptors
	 */
	Collection<DocumentFilterDescriptor> getAll();

	/**
	 * @return filter descriptor or <code>null</code>
	 */
	@Nullable
	DocumentFilterDescriptor getByFilterIdOrNull(final String filterId);

	/**
	 * @return filter descriptor
	 */
	default DocumentFilterDescriptor getByFilterId(final String filterId)
	{
		final DocumentFilterDescriptor filterDescriptor = getByFilterIdOrNull(filterId);
		if (filterDescriptor == null)
		{
			throw new AdempiereException("Filter '" + filterId + "' was not found in " + this);
		}
		return filterDescriptor;
	}

	default DocumentFilter unwrap(@NonNull final JSONDocumentFilter jsonFilter)
	{
		final String filterId = jsonFilter.getFilterId();
		final DocumentFilterDescriptor filterDescriptor = getByFilterIdOrNull(filterId);

		// Ad-hoc filters (e.g. zoom references)
		if (filterDescriptor == null)
		{
			return JSONDocumentFilter.unwrapAsGenericFilter(jsonFilter);
		}
		// Filter with descriptor
		else
		{
			return filterDescriptor.unwrap(jsonFilter);
		}
	}
}
