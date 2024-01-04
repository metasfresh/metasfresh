package de.metas.bpartner.product.stats;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.SimpleObjectSerializer;
import de.metas.event.Topic;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;

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
	public static final Topic TOPIC_InOut = Topic.distributed("de.metas.bpartner.product.stats.updates.inout");
	public static final Topic TOPIC_Invoice = Topic.distributed("de.metas.bpartner.product.stats.updates.invoice");

	private static final String EVENT_PROPERTY_Content = "content";

	public void send(@NonNull final InOutChangedEvent event)
	{
		sendEventObj(TOPIC_InOut, event);
	}

	public void send(@NonNull final InvoiceChangedEvent event)
	{
		sendEventObj(TOPIC_Invoice, event);
	}

	private void sendEventObj(final Topic topic, final Object eventObj)
	{
		final Event event = toEvent(eventObj);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(true)
				.registerHandlingMethod(trx -> sendEventNow(topic, event));
	}

	private void sendEventNow(final Topic topic, final Event event)
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(topic);
		eventBus.enqueueEvent(event);
	}

	private static Event toEvent(final Object event)
	{
		final SimpleObjectSerializer objectSerializer = SimpleObjectSerializer.get();

		return Event.builder()
				.putProperty(EVENT_PROPERTY_Content, objectSerializer.serialize(event))
				.shallBeLogged()
				.build();
	}

	public static InOutChangedEvent extractInOutChangedEvent(@NonNull final Event event)
	{
		return extractEvent(event, InOutChangedEvent.class);
	}

	public static InvoiceChangedEvent extractInvoiceChangedEvent(@NonNull final Event event)
	{
		return extractEvent(event, InvoiceChangedEvent.class);
	}

	public static <T> T extractEvent(@NonNull final Event event, @NonNull final Class<T> eventType)
	{
		final String json = event.getPropertyAsString(EVENT_PROPERTY_Content);

		final SimpleObjectSerializer objectSerializer = SimpleObjectSerializer.get();
		return objectSerializer.deserialize(json, eventType);
	}

}
