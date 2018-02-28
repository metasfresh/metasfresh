package de.metas.ordercandidate.api;

import java.util.List;
import java.util.stream.Collectors;

import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.ordercandidate.spi.IOLCandGroupingProvider;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.ordercandidate.spi.NullOLCandListener;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class OLCandRegistry
{
	private final IOLCandListener listeners;
	private final IOLCandGroupingProvider groupingValuesProviders;

	public OLCandRegistry(
			final List<IOLCandListener> listeners,
			final List<IOLCandGroupingProvider> groupingValuesProviders)
	{
		this.listeners = !listeners.isEmpty() ? new CompositeOLCandListener(listeners) : NullOLCandListener.instance;
		this.groupingValuesProviders = !groupingValuesProviders.isEmpty() ? new CompositeOLCandGroupingProvider(groupingValuesProviders) : NullOLCandGroupingProvider.instance;
	}

	public IOLCandListener getListeners()
	{
		return listeners;
	}

	public IOLCandGroupingProvider getGroupingValuesProviders()
	{
		return groupingValuesProviders;
	}

	@ToString
	private static final class CompositeOLCandListener implements IOLCandListener
	{
		private final ImmutableList<IOLCandListener> listeners;

		private CompositeOLCandListener(final List<IOLCandListener> listeners)
		{
			this.listeners = ImmutableList.copyOf(listeners);
		}

		@Override
		public void onOrderLineCreated(final OLCand olCand, final I_C_OrderLine newOrderLine)
		{
			listeners.forEach(listener -> onOrderLineCreated(olCand, newOrderLine));
		}
	}

	private static final class NullOLCandGroupingProvider implements IOLCandGroupingProvider
	{
		public static final transient NullOLCandGroupingProvider instance = new NullOLCandGroupingProvider();

		@Override
		public List<Object> provideLineGroupingValues(final OLCand cand)
		{
			return ImmutableList.of();
		}

	}

	@ToString
	private static final class CompositeOLCandGroupingProvider implements IOLCandGroupingProvider
	{
		private final List<IOLCandGroupingProvider> providers;

		private CompositeOLCandGroupingProvider(final List<IOLCandGroupingProvider> providers)
		{
			this.providers = ImmutableList.copyOf(providers);
		}

		@Override
		public List<Object> provideLineGroupingValues(final OLCand cand)
		{
			return providers.stream()
					.flatMap(provider -> provider.provideLineGroupingValues(cand).stream())
					.collect(Collectors.toList());
		}

	}

}
