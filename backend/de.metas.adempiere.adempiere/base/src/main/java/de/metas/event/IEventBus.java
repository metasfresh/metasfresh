package de.metas.event;

import java.util.function.Consumer;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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
 * Bus of {@link Event}s.
 *
 * To get an instance of this object, please use {@link IEventBusFactory}.
 * <p>
 * Note: if an event bus is only local or also remote (i.e. events are send to other hosts as well) just depends on wheter the event bus factory registered a an apprpriate subscriber to forward events
 * to other hosts.
 * 
 * @author tsa
 *
 */
public interface IEventBus
{
	/**
	 * 
	 * @return the (topic-) name of this event bus.
	 */
	String getName();

	/**
	 * 
	 * @return the type (remote or local) of this event bus. If the event bus is "remote" then subscriber on other hosts will be notified about events on this host.
	 */
	Type getType();

	/**
	 * Subscribe to this bus.
	 *
	 * @param listener
	 */
	void subscribe(IEventListener listener);

	/**
	 * Subscribe to this bus.
	 * 
	 * @param eventConsumer
	 */
	void subscribe(Consumer<Event> eventConsumer);

	/**
	 * Subscribe to this bus. Same as {@link #subscribe(IEventListener)} but this will register a weak reference to listener, so as long as the listener is no longer referenced, it will auto-magically
	 * unregistered from this bus.
	 *
	 * @param listener
	 */
	void subscribeWeak(IEventListener listener);

	/**
	 * Post given event on this bus.
	 *
	 * @param event
	 */
	void postEvent(Event event);

	/**
	 * @return true if the bus was destroyed and it no longer accepts events.
	 */
	boolean isDestroyed();
}
