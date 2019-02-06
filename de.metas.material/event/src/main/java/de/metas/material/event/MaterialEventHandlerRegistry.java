package de.metas.material.event;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;

import de.metas.event.log.EventLogUserService;
import de.metas.event.log.EventLogUserService.InvokeHandlerandLogRequest;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-event-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
@SuppressWarnings(value = { "rawtypes" })
public class MaterialEventHandlerRegistry
{
	private static final Logger logger = LogManager.getLogger(MaterialEventHandlerRegistry.class);

	private final ImmutableMultimap<Class, MaterialEventHandler> eventType2Handler;
	private final EventLogUserService eventLogUserService;

	public MaterialEventHandlerRegistry(
			@NonNull final Optional<Collection<MaterialEventHandler>> handlers,
			@NonNull final EventLogUserService eventLogUserService)
	{
		this.eventLogUserService = eventLogUserService;
		eventType2Handler = createEventHandlerMapping(handlers);
		logger.info("Registered {}", eventType2Handler);
	}

	private ImmutableMultimap<Class, MaterialEventHandler> createEventHandlerMapping(
			@NonNull final Optional<Collection<MaterialEventHandler>> handlers)
	{
		final ImmutableMultimap.Builder<Class, MaterialEventHandler> builder = ImmutableMultimap.builder();
		for (final MaterialEventHandler handler : handlers.orElse(ImmutableList.of()))
		{
			@SuppressWarnings("unchecked")
			final Collection<Class<? extends MaterialEventHandler>> handeledEventType = handler.getHandeledEventType();

			handeledEventType.forEach(type -> builder.put(type, handler));
		}
		return builder.build();
	}

	public final void onEvent(@NonNull final MaterialEvent event)
	{
		final ImmutableCollection<MaterialEventHandler> handlersForEventClass = eventType2Handler.get(event.getClass());

		handlersForEventClass.forEach(handler -> {

			@SuppressWarnings("unchecked")
			final InvokeHandlerandLogRequest request = InvokeHandlerandLogRequest.builder()
					.handlerClass(handler.getClass())
					.invokaction(() -> handler.handleEvent(event))
					.build();

			eventLogUserService.invokeHandlerAndLog(request);
		});
	}
}
