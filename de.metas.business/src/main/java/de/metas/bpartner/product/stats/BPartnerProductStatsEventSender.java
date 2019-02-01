package de.metas.bpartner.product.stats;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.SimpleObjectSerializer;
import de.metas.event.Topic;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class BPartnerProductStatsEventSender
{
	public static final Topic TOPIC = Topic.remote("de.metas.bpartner.product.stats.updates");

	private static final String EVENT_PROPERTY_Content = "content";

	public void send(@NonNull final InOutChangedEvent event)
	{
		sendEventObj(event);
	}

	private void sendEventObj(@NonNull final Object event)
	{
		getEventBus().postEvent(toEvent(event));
	}

	private static Event toEvent(final Object event)
	{
		return Event.builder()
				.putProperty(EVENT_PROPERTY_Content, SimpleObjectSerializer.get().serialize(event))
				.storeEvent()
				.build();
	}

	public static InOutChangedEvent fromEvent(@NonNull final Event event)
	{
		final String json = event.getPropertyAsString(EVENT_PROPERTY_Content);
		return SimpleObjectSerializer.get().deserialize(json, InOutChangedEvent.class);
	}

	private IEventBus getEventBus()
	{
		return Services.get(IEventBusFactory.class).getEventBus(TOPIC);
	}

}
