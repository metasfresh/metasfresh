package de.metas.material.event;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import de.metas.event.log.EventLogUserTools;
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
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class MaterialEventHandlerRegistry
{
	private final ImmutableMultimap<Class, MaterialEventHandler> eventType2Handler;

	public MaterialEventHandlerRegistry(
			@NonNull final Optional<Collection<MaterialEventHandler>> handlers)
	{
		final Builder<Class, MaterialEventHandler> builder = ImmutableMultimap.builder();

		for (final MaterialEventHandler handler : handlers.orElse(ImmutableList.of()))
		{
			final Collection<Class<? extends MaterialEventHandler>> handeledEventType = //
					handler.getHandeledEventType();

			handeledEventType.forEach(type -> builder.put(type, handler));
		}

		eventType2Handler = builder.build();
	}

	public final void onEvent(@NonNull final MaterialEvent event)
	{
		final ImmutableCollection<MaterialEventHandler> handlersForEventClass = //
				eventType2Handler.get(event.getClass());

		handlersForEventClass.forEach(handler -> {
			invokeHandlerAndLog(event, handler);
		});
	}

	private void invokeHandlerAndLog(
			@NonNull final MaterialEvent event,
			@NonNull final MaterialEventHandler handler)
	{
		final Class<? extends MaterialEventHandler> handlerClass = handler.getClass();
		if (EventLogUserTools.wasEventProcessedbyHandler(handlerClass))
		{
			return;
		}

		handler.handleEvent(event);

		EventLogUserTools.newEventLogRequest(handlerClass)
				.processed(true)
				.storeEventLog();
	}
}
