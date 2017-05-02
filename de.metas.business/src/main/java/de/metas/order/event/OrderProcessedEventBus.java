package de.metas.order.event;

import org.adempiere.util.Services;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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

public class OrderProcessedEventBus extends QueueableForwardingEventBus
{

	public static final OrderProcessedEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new OrderProcessedEventBus(eventBus);
	}

	/** Topic used to send notifications about sales and purchase orders that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.setName("de.metas.order.Order.ProcessedEvents")
			.setType(Type.REMOTE)
			.build();

	private OrderProcessedEventBus(IEventBus delegate)
	{
		super(delegate);
	}

}
