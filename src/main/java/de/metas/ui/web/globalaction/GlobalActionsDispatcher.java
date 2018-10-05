package de.metas.ui.web.globalaction;

import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
public class GlobalActionsDispatcher
{
	private static final Logger logger = LogManager.getLogger(GlobalActionsDispatcher.class);

	private ImmutableMap<GlobalActionType, GlobalActionHandler> handlers;

	public GlobalActionsDispatcher(final Optional<List<GlobalActionHandler>> handlers)
	{
		if (handlers.isPresent())
		{
			this.handlers = Maps.uniqueIndex(handlers.get(), GlobalActionHandler::getTypeHandled);
			logger.info("Registered handlers: {}", handlers);
		}
		else
		{
			this.handlers = ImmutableMap.of();
			logger.warn("No handlers registered");
		}
	}

	public GlobalActionHandlerResult dispatchEvent(@NonNull final GlobalActionEvent event)
	{
		return getHandler(event.getType()).handleEvent(event);
	}

	private GlobalActionHandler getHandler(final GlobalActionType type)
	{
		final GlobalActionHandler handler = handlers.get(type);
		if (handler == null)
		{
			throw new AdempiereException("No handler found for " + type);
		}
		return handler;
	}
}
