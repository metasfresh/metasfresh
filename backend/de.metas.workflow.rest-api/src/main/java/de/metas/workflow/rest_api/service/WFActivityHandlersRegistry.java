/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.logging.LogManager;
import de.metas.workflow.rest_api.model.WFActivityType;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WFActivityHandlersRegistry
{
	private static final Logger logger = LogManager.getLogger(WFActivityHandlersRegistry.class);

	private final ImmutableMap<WFActivityType, WFActivityHandler> handlers;

	public WFActivityHandlersRegistry(@NonNull final Optional<List<WFActivityHandler>> optionalHandlers)
	{
		this.handlers = optionalHandlers
				.map(handlers -> Maps.uniqueIndex(handlers, WFActivityHandler::getHandledActivityType))
				.orElse(ImmutableMap.of());
		logger.info("Handlers: {}", handlers);
	}

	public WFActivityHandler getHandler(final WFActivityType wfActivityType)
	{
		final WFActivityHandler handler = handlers.get(wfActivityType);
		if (handler == null)
		{
			throw new AdempiereException("No handler registered for activity type: " + wfActivityType);
		}
		return handler;
	}

	public <T> T getFeature(
			@NonNull final WFActivityType wfActivityType,
			@NonNull final Class<T> featureType)
	{
		final WFActivityHandler handler = getHandler(wfActivityType);
		if (featureType.isInstance(handler))
		{
			return featureType.cast(handler);
		}
		else
		{
			throw new AdempiereException("" + wfActivityType + " does not support " + featureType.getSimpleName());
		}
	}
}
