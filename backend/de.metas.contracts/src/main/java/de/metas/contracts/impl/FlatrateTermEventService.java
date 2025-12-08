package de.metas.contracts.impl;

import de.metas.contracts.IFlatrateTermEventService;
import de.metas.contracts.spi.FallbackFlatrateTermEventListener;
import de.metas.contracts.spi.IFlatrateTermEventListener;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * #%L
 * de.metas.contracts
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

public class FlatrateTermEventService implements IFlatrateTermEventService
{
	private static final Logger logger = LogManager.getLogger(FlatrateTermEventService.class);

	private final Map<String, IFlatrateTermEventListener> typeConditions2handlers = new ConcurrentHashMap<>();

	private final IFlatrateTermEventListener fallbackListener = new FallbackFlatrateTermEventListener();

	public FlatrateTermEventService()
	{
		// Register standard handlers
		registerEventListenerForConditionsType(new SubscriptionTermEventListener(), SubscriptionTermEventListener.TYPE_CONDITIONS_SUBSCRIPTION);
	}

	@Override
	public synchronized void registerEventListenerForConditionsType(@NonNull final IFlatrateTermEventListener handlerNew, final String typeConditions)
	{
		Check.assumeNotEmpty(typeConditions, "typeConditions not empty");

		// Validate handler
		if (handlerNew instanceof CompositeFlatrateTermEventListener)
		{
			// enforce immutability of given handler: we are not accepting composite because it might be that on next registerHandler calls will will add a handler inside it.
			throw new AdempiereException("Composite shall not be used: " + handlerNew);
		}

		//
		final IFlatrateTermEventListener handlerCurrent = typeConditions2handlers.get(typeConditions);
		final IFlatrateTermEventListener handlerComposed = CompositeFlatrateTermEventListener.compose(handlerCurrent, handlerNew);
		typeConditions2handlers.put(typeConditions, handlerComposed);

		logger.info("Registered {} for TYPE_CONDITIONS={}", handlerNew, typeConditions);
	}

	@Override
	public IFlatrateTermEventListener getHandler(final String typeConditions)
	{
		final IFlatrateTermEventListener handlers = typeConditions2handlers.get(typeConditions);
		if (handlers == null)
		{
			logger.debug("No handler found for TYPE_CONDITIONS={}. Returning default: {}", typeConditions, fallbackListener);
			return fallbackListener;
		}

		return handlers;
	}
}
