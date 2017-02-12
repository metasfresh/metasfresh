package de.metas.notification.spi.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import de.metas.logging.LogManager;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * task 09833
 * Composite to gather all the registered notification ctx providers and return the most fit message from them
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CompositePrintingNotificationCtxProvider implements INotificationCtxProvider
{
	private static final Logger logger = LogManager.getLogger(CompositePrintingNotificationCtxProvider.class);

	private final CopyOnWriteArrayList<INotificationCtxProvider> ctxProviders = new CopyOnWriteArrayList<>();
	private INotificationCtxProvider defaultCtxProvider = NullNotificationCtxProvider.instance;

	public final void addCtxProvider(final INotificationCtxProvider ctxProvider)
	{
		Check.assumeNotNull(ctxProvider, "ctx provider not null");
		ctxProviders.addIfAbsent(ctxProvider);
	}

	public void setDefaultCtxProvider(final INotificationCtxProvider defaultCtxProvider)
	{
		Check.assumeNotNull(defaultCtxProvider, "defaultCtxProvider not null");
		this.defaultCtxProvider = defaultCtxProvider;
	}

	@Override
	public Optional<String> getTextMessageIfApplies(final ITableRecordReference referencedRecord)
	{
		// take the providers one by one and see if any of them applies to the given referenced record
		for (final INotificationCtxProvider ctxProvider : ctxProviders)
		{
			final Optional<String> textMessage = ctxProvider.getTextMessageIfApplies(referencedRecord);
			if (textMessage == null || !textMessage.isPresent())
			{
				continue;
			}

			return textMessage;
		}

		// Fallback to default provider
		final Optional<String> textMessage = defaultCtxProvider.getTextMessageIfApplies(referencedRecord);
		// guard against development issues (usually the text message shall never be null)
		if (textMessage == null)
		{
			new AdempiereException("Possible development issue. " + defaultCtxProvider + " returned null for " + referencedRecord)
					.throwIfDeveloperModeOrLogWarningElse(logger);
			return Optional.absent();
		}
		return textMessage;
	}
}
