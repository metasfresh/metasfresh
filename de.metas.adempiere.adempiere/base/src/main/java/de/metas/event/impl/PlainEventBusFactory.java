package de.metas.event.impl;

import java.util.HashMap;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Empty dummy factory that does nothing
 */
public class PlainEventBusFactory implements IEventBusFactory
{
	private final HashMap<Topic, EventBus> topic2Eventbus = new HashMap<>();

	@Override
	public IEventBus getEventBus(Topic topic)
	{
		return topic2Eventbus.computeIfAbsent(topic, t->new EventBus(t.getFullName(), null));
	}

	@Override
	public IEventBus getEventBusIfExists(Topic topic)
	{
		return null;
	}

	@Override
	public void initEventBussesWithGlobalListeners()
	{
	}

	@Override
	public void destroyAllEventBusses()
	{
	}

	@Override
	public void registerGlobalEventListener(Topic topic, IEventListener listener)
	{
	}

	@Override
	public void addAvailableUserNotificationsTopic(Topic topic)
	{
	}

	@Override
	public void registerUserNotificationsListener(IEventListener listener)
	{
	}

	@Override
	public void registerWeakUserNotificationsListener(IEventListener listener)
	{
	}

	@Override
	public boolean checkRemoteEndpointStatus()
	{
		return false;
	}

}
