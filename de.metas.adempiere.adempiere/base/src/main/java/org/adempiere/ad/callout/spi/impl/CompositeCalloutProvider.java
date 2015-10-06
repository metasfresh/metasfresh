package org.adempiere.ad.callout.spi.impl;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.util.Check;
import org.compiere.util.CLogger;

public class CompositeCalloutProvider implements ICalloutProvider
{
	private static final transient CLogger logger = CLogger.getCLogger(CompositeCalloutProvider.class);

	private final List<ICalloutProvider> providers = new ArrayList<ICalloutProvider>();

	/**
	 * 
	 * @param provider
	 * @return true if provider was added
	 */
	public boolean addCalloutProvider(final ICalloutProvider provider)
	{
		Check.assumeNotNull(provider, "provider not null");

		if (providers.contains(provider))
		{
			return false;
		}

		providers.add(provider);
		return true;
	}

	public List<ICalloutProvider> getProviders()
	{
		return new ArrayList<ICalloutProvider>(providers);
	}

	@Override
	public List<ICalloutInstance> getCallouts(final ICalloutField field)
	{
		final List<ICalloutInstance> result = new ArrayList<ICalloutInstance>();
		final Set<String> calloutIds = new HashSet<String>();

		for (final ICalloutProvider provider : providers)
		{
			final List<ICalloutInstance> callouts = provider.getCallouts(field);
			if (callouts == null || callouts.isEmpty())
			{
				continue;
			}

			for (final ICalloutInstance callout : callouts)
			{
				final String calloutId = callout.getId();
				if (calloutIds.contains(calloutId))
				{
					logger.log(Level.WARNING, "Callout with ID '" + calloutId + "' was already added. Skipping");
					continue;
				}

				calloutIds.add(calloutId);
				result.add(callout);
			}
		}

		return result;
	}

}
