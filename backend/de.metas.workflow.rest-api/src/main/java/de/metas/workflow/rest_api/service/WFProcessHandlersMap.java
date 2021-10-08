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

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.workflow.rest_api.model.WFProcessHandlerId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

final class WFProcessHandlersMap
{
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static WFProcessHandlersMap of(@NonNull final Optional<List<WFProcessHandler>> optionalHandlers)
	{
		final List<WFProcessHandler> handlers = optionalHandlers.orElseGet(ImmutableList::of);
		return !handlers.isEmpty()
				? new WFProcessHandlersMap(handlers)
				: EMPTY;
	}

	private static final WFProcessHandlersMap EMPTY = new WFProcessHandlersMap(ImmutableList.of());

	private final ImmutableList<WFProcessHandler> handlers;
	private final ImmutableMap<WFProcessHandlerId, WFProcessHandler> handlersById;

	private WFProcessHandlersMap(@NonNull final List<WFProcessHandler> handlers)
	{
		this.handlers = ImmutableList.copyOf(handlers);
		this.handlersById = Maps.uniqueIndex(handlers, WFProcessHandler::getId);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("handlers", handlers)
				.toString();
	}

	public Stream<WFProcessHandler> stream() {return handlers.stream();}

	public WFProcessHandler getById(@NonNull final WFProcessHandlerId handlerId)
	{
		final WFProcessHandler handler = handlersById.get(handlerId);
		if (handler == null)
		{
			throw new AdempiereException("No handler found for " + handlerId + ". Available handlers are: " + handlers);
		}
		return handler;
	}
}
