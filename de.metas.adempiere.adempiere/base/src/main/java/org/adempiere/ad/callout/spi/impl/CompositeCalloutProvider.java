package org.adempiere.ad.callout.spi.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.util.Check;

public class CompositeCalloutProvider implements ICalloutProvider
{
	private final CopyOnWriteArrayList<ICalloutProvider> providers = new CopyOnWriteArrayList<ICalloutProvider>();

	/**
	 *
	 * @param provider
	 * @return true if provider was added
	 */
	public boolean addCalloutProvider(final ICalloutProvider provider)
	{
		Check.assumeNotNull(provider, "provider not null");
		return providers.addIfAbsent(provider);
	}

	public List<ICalloutProvider> getProviders()
	{
		return new ArrayList<ICalloutProvider>(providers);
	}

	@Override
	public TableCalloutsMap getCallouts(final Properties ctx, final int adTableId)
	{
		final TableCalloutsMap.Builder resultBuilder = TableCalloutsMap.builder();

		for (final ICalloutProvider provider : providers)
		{
			final TableCalloutsMap callouts = provider.getCallouts(ctx, adTableId);
			resultBuilder.putAll(callouts);
		}

		return resultBuilder.build();
	}

}
