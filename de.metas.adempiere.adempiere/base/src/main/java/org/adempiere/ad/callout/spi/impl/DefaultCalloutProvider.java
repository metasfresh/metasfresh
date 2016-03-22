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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.callout.api.IADColumnCalloutDAO;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.ICalloutInstanceFactory;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_ColumnCallout;

public class DefaultCalloutProvider implements ICalloutProvider
{
	private static final transient Logger logger = LogManager.getLogger(DefaultCalloutProvider.class);

	@Override
	public List<ICalloutInstance> getCallouts(final ICalloutField field)
	{
		final Properties ctx = field.getCtx();
		final int adColumnId = field.getAD_Column_ID();

		final List<I_AD_ColumnCallout> calloutsDef = Services.get(IADColumnCalloutDAO.class).retrieveActiveColumnCallouts(ctx, adColumnId);
		if (calloutsDef == null || calloutsDef.isEmpty())
		{
			return Collections.emptyList();
		}

		final Set<String> calloutIds = new HashSet<String>();
		final List<ICalloutInstance> callouts = new ArrayList<ICalloutInstance>();
		for (final I_AD_ColumnCallout calloutDef : calloutsDef)
		{
			final ICalloutInstance callout = createCalloutOrNull(calloutDef);
			if (callout == null)
			{
				continue;
			}

			final String calloutId = callout.getId();
			if (calloutIds.contains(calloutId))
			{
				logger.warn("Callout with ID '" + calloutId + "' was already added. Skipping");
				continue;
			}

			calloutIds.add(calloutId);
			callouts.add(callout);
		}

		return callouts;
	}

	private ICalloutInstance createCalloutOrNull(final I_AD_ColumnCallout calloutDef)
	{
		if (calloutDef == null)
		{
			return null;
		}
		if (!calloutDef.isActive())
		{
			return null;
		}

		try
		{
			return Services.get(ICalloutInstanceFactory.class)
					.createFromString(calloutDef.getClassname());
		}
		catch (Exception e)
		{
			// We are just logging and discarding the error because there is nothing that we can do about it
			// More, we want to load the other callouts and not just fail
			logger.error(e.getLocalizedMessage(), e);
		}

		return null;
	}

}
