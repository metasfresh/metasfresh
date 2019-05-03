package de.metas.ui.web.document.filter.provider;

import java.util.Collection;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
class CompositeDocumentFilterDescriptorsProvider implements DocumentFilterDescriptorsProvider
{
	public static DocumentFilterDescriptorsProvider compose(final DocumentFilterDescriptorsProvider... providers)
	{
		if (providers == null || providers.length <= 0)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		final ImmutableList<DocumentFilterDescriptorsProvider> providersList = Stream.of(providers)
				.filter(NullDocumentFilterDescriptorsProvider::isNotNull)
				.collect(ImmutableList.toImmutableList());

		if (providersList.isEmpty())
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}
		else if (providersList.size() == 1)
		{
			return providersList.get(0);
		}

		return new CompositeDocumentFilterDescriptorsProvider(providersList);
	}

	private final ImmutableList<DocumentFilterDescriptorsProvider> providers;

	private CompositeDocumentFilterDescriptorsProvider(@NonNull final ImmutableList<DocumentFilterDescriptorsProvider> providers)
	{
		this.providers = providers;
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
