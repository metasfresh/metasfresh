package de.metas.contracts.flatrate.api.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.contracts.flatrate.api.IFlatrateHandler;
import de.metas.contracts.flatrate.api.IFlatrateHandlersService;
import de.metas.logging.LogManager;

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

public class FlatrateHandlersService implements IFlatrateHandlersService
{
	private static final Logger logger = LogManager.getLogger(FlatrateHandlersService.class);

	private final Map<String, IFlatrateHandler> typeConditions2handlers = new ConcurrentHashMap<>();

	private final IFlatrateHandler defaultHandler = new DefaultFlatrateHandler();

	public FlatrateHandlersService()
	{
		super();

		// Register standard handlers
		registerHandler(AbonamentFlatrateHandler.TYPE_CONDITIONS, new AbonamentFlatrateHandler());
	}

	@Override
	public synchronized void registerHandler(final String typeConditions, final IFlatrateHandler handlerNew)
	{
		Check.assumeNotEmpty(typeConditions, "typeConditions not empty");

		// Validate handler
		Check.assumeNotNull(handlerNew, "handler not null");
		if (handlerNew instanceof CompositeFlatrateHandler)
		{
			// enforce immutability of given handler: we are not accepting composite because it might be that on next registerHandler calls will will add a handler inside it.
			throw new AdempiereException("Composite shall not be used: " + handlerNew);
		}

		//
		final IFlatrateHandler handlerCurrent = typeConditions2handlers.get(typeConditions);
		final IFlatrateHandler handlerComposed = CompositeFlatrateHandler.compose(handlerCurrent, handlerNew);
		typeConditions2handlers.put(typeConditions, handlerComposed);

		logger.info("Registered {} for TYPE_CONDITIONS={}", handlerNew, typeConditions);
	}

	@Override
	public IFlatrateHandler getHandler(final String typeConditions)
	{
		final IFlatrateHandler handlers = typeConditions2handlers.get(typeConditions);
		if (handlers == null)
		{
			logger.debug("No handler found for TYPE_CONDITIONS={}. Returning default: {}", typeConditions, defaultHandler);
			return defaultHandler;
		}

		return handlers;
	}
}
