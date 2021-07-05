/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.location.adapter;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@ToString
final class CompositeDocumentLocationAdapterFactory
{
	private final ImmutableList<DocumentLocationAdapterFactory> factories;

	public CompositeDocumentLocationAdapterFactory(@NonNull final List<DocumentLocationAdapterFactory> factories)
	{
		this.factories = ImmutableList.copyOf(factories);
	}

	public Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(final Object record)
	{
		for (final DocumentLocationAdapterFactory factory : factories)
		{
			final Optional<IDocumentLocationAdapter> adapter = factory.getDocumentLocationAdapterIfHandled(record);
			if (adapter.isPresent())
			{
				return adapter;
			}
		}

		return Optional.empty();
	}

	public Optional<IDocumentBillLocationAdapter> getDocumentBillLocationAdapterIfHandled(final Object record)
	{
		for (final DocumentLocationAdapterFactory factory : factories)
		{
			final Optional<IDocumentBillLocationAdapter> adapter = factory.getDocumentBillLocationAdapterIfHandled(record);
			if (adapter.isPresent())
			{
				return adapter;
			}
		}

		return Optional.empty();
	}

	public Optional<IDocumentDeliveryLocationAdapter> getDocumentDeliveryLocationAdapter(final Object record)
	{
		for (final DocumentLocationAdapterFactory factory : factories)
		{
			final Optional<IDocumentDeliveryLocationAdapter> adapter = factory.getDocumentDeliveryLocationAdapter(record);
			if (adapter.isPresent())
			{
				return adapter;
			}
		}

		return Optional.empty();
	}

	public Optional<IDocumentHandOverLocationAdapter> getDocumentHandOverLocationAdapter(final Object record)
	{
		for (final DocumentLocationAdapterFactory factory : factories)
		{
			final Optional<IDocumentHandOverLocationAdapter> adapter = factory.getDocumentHandOverLocationAdapter(record);
			if (adapter.isPresent())
			{
				return adapter;
			}
		}

		return Optional.empty();
	}

}
