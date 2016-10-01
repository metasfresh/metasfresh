package de.metas.ui.web.window.descriptor.filters;

import java.util.Collection;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.google.common.collect.ImmutableList;

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

class CompositeDocumentFilterDescriptorsProvider implements DocumentFilterDescriptorsProvider
{
	public static CompositeDocumentFilterDescriptorsProvider of(final DocumentFilterDescriptorsProvider... providers)
	{
		return new CompositeDocumentFilterDescriptorsProvider(providers);
	}

	private final List<DocumentFilterDescriptorsProvider> providers;

	private CompositeDocumentFilterDescriptorsProvider(final DocumentFilterDescriptorsProvider... providers)
	{
		super();
		this.providers = ImmutableList.copyOf(providers);
	}

	@Override
	public Collection<DocumentFilterDescriptor> getAll()
	{
		return providers
				.stream()
				.map(provider -> provider.getAll())
				.flatMap(descriptors -> descriptors.stream())
				.collect(GuavaCollectors.toImmutableMapByKey(descriptor -> descriptor.getFilterId())) // make sure each filterId is unique!
				.values();
	}

	@Override
	public DocumentFilterDescriptor getByFilterIdOrNull(final String filterId)
	{
		return providers
				.stream()
				.map(provider -> provider.getByFilterIdOrNull(filterId))
				.filter(descriptor -> descriptor != null)
				.findFirst()
				.orElse(null);
	}

}
