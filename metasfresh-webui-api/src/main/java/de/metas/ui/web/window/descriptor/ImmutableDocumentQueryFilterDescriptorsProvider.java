package de.metas.ui.web.window.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

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

	public static final Collector<DocumentQueryFilterDescriptor, ?, ImmutableDocumentQueryFilterDescriptorsProvider> collector()
	{
		final Supplier<List<DocumentQueryFilterDescriptor>> supplier = ArrayList::new;
		final BiConsumer<List<DocumentQueryFilterDescriptor>, DocumentQueryFilterDescriptor> accumulator = (list, filter) -> list.add(filter);
		final BinaryOperator<List<DocumentQueryFilterDescriptor>> combiner = (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
		final Function<List<DocumentQueryFilterDescriptor>, ImmutableDocumentQueryFilterDescriptorsProvider> finisher = ImmutableDocumentQueryFilterDescriptorsProvider::of;

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	private static final ImmutableDocumentQueryFilterDescriptorsProvider NULL = new ImmutableDocumentQueryFilterDescriptorsProvider(ImmutableList.of());

	private final Map<String, DocumentQueryFilterDescriptor> descriptorsByFilterId;

	public ImmutableDocumentQueryFilterDescriptorsProvider(final List<DocumentQueryFilterDescriptor> descriptors)
	{
		super();
		descriptorsByFilterId = Maps.uniqueIndex(descriptors, descriptor -> descriptor.getFilterId());
	}

	@Override
	public Collection<DocumentQueryFilterDescriptor> getAll()
	{
		return descriptorsByFilterId.values();
	}

	@Override
	public DocumentQueryFilterDescriptor getByFilterIdOrNull(final String filterId) throws NoSuchElementException
	{
		final DocumentQueryFilterDescriptor descriptor = descriptorsByFilterId.get(filterId);
		return descriptor;
	}

}
