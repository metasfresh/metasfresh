package de.metas.material.event.impl;

import org.adempiere.util.Services;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;

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

public class ManufacturingEventBus extends QueueableForwardingEventBus
{
	private static final String EVENTBUS_TOPIC_NAME = "de.metas.manufacturing.dispo";

	/** Topic used to send notifications about sales and purchase orders that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.setName(EVENTBUS_TOPIC_NAME)
			.setType(Type.REMOTE)
			.build();

	public static final ManufacturingEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new ManufacturingEventBus(eventBus);
	}

	private ManufacturingEventBus(final IEventBus delegate)
	{
		super(delegate);
	}
}
