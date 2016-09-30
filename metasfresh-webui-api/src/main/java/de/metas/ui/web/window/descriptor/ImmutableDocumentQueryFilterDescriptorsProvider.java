package de.metas.ui.web.window.descriptor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ImmutableDocumentQueryFilterDescriptorsProvider implements DocumentQueryFilterDescriptorsProvider
{
	public static final ImmutableDocumentQueryFilterDescriptorsProvider of(final List<DocumentQueryFilterDescriptor> descriptors)
	{
		if (descriptors == null || descriptors.isEmpty())
		{
			return NULL;
		}
		return new ImmutableDocumentQueryFilterDescriptorsProvider(descriptors);
	}

	private static final ImmutableDocumentQueryFilterDescriptorsProvider NULL = new ImmutableDocumentQueryFilterDescriptorsProvider(ImmutableList.of());

	private final Map<String, DocumentQueryFilterDescriptor> descriptors;

	public ImmutableDocumentQueryFilterDescriptorsProvider(final List<DocumentQueryFilterDescriptor> descriptors)
	{
		super();
		this.descriptors = Maps.uniqueIndex(descriptors, descriptor -> descriptor.getFilterId());
	}

	@Override
	public Collection<DocumentQueryFilterDescriptor> getAll()
	{
		return descriptors.values();
	}

	@Override
	public DocumentQueryFilterDescriptor getByFilterIdOrNull(final String filterId) throws NoSuchElementException
	{
		final DocumentQueryFilterDescriptor descriptor = descriptors.get(filterId);
		return descriptor;
	}

}
