package de.metas.notification.spi.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;

import de.metas.notification.spi.INotificationCtxProvider;

/*
 * #%L
 * de.metas.printing.base
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

/**
 * task 09833
 * Composite to gather all the registered notification ctx providers and return the most fit message from them
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class CompositePrintingNotificationCtxProvider implements INotificationCtxProvider
{
	private final CopyOnWriteArrayList<INotificationCtxProvider> ctxProviders = new CopyOnWriteArrayList<>();

	public final void addCtxProvider(final INotificationCtxProvider ctxProvider)
	{
		Check.assumeNotNull(ctxProvider, "ctx provider not null");
		ctxProviders.addIfAbsent(ctxProvider);
	}

	@Override
	public boolean appliesFor(final ITableRecordReference referencedRecord)
	{
		// the appliesFor will come from the specific ctx providers
		return true;
	}

	@Override
	public String getTextMessageOrNull(final ITableRecordReference referencedRecord)
	{
		INotificationCtxProvider defaultCtxProvider = null;

		// take the providers one by one and see if any of them applies to the given referenced record
		for (final INotificationCtxProvider ctxProvider : ctxProviders)
		{
			if (ctxProvider.appliesFor(referencedRecord))
			{
				// in case there is a ctx provider that is not default and fits the reference record, return its text message
				if (!ctxProvider.isDefault())
				{
					return ctxProvider.getTextMessageOrNull(referencedRecord);
				}
				else
				{
					// if the default was found, keep it
					defaultCtxProvider = ctxProvider;
				}
			}
		}

		// no specific context provider was found. If there is a default one, use it.
		if (defaultCtxProvider != null)
		{
			return defaultCtxProvider.getTextMessageOrNull(referencedRecord);
		}

		// return null if no ctx provider was found.
		return null;
	}

	@Override
	public boolean isDefault()
	{
		// not default
		return false;
	}
}
