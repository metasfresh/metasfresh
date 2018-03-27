package de.metas.event;

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


import java.util.Set;

import org.adempiere.util.ISingletonService;

/**
 * Creates and manages {@link IEventBus}es.
 *
 * This is the main entry point for events module.
 *
 * @author tsa
 *
 */
public interface IEventBusFactory extends ISingletonService
{
	/**
	 * @return event bus; never returns <code>null</code>
	 */
	public IEventBus getEventBus(Topic topic);

	/** 
	 * @return event bus or null
	 */
	IEventBus getEventBusIfExists(Topic topic);

	/**
	 * Create an remotely bind all {@link IEventBus}es for which we have global listeners registered.
	 * 
	 * @see #registerGlobalEventListener(Topic, IEventListener)
	 */
	public void initEventBussesWithGlobalListeners();

	/**
	 * Destroy all created event bus objects.
	 */
	void destroyAllEventBusses();

	/**
	 * Register a global {@link IEventListener}.
	 * 
	 * Global event listeners will be automatically registered when the {@link IEventBus} is created. Also, the listener will be registered to event bus.
	 * 
	 * The advantage of using this method instead of {@link IEventBus#subscribe(IEventListener)} is that those listeners will survive also after an reset (i.e. {@link #destroyAllEventBusses()}).
	 * 
	 * @param topic
	 * @param listener
	 * @see #initEventBussesWithGlobalListeners()
	 */
	void registerGlobalEventListener(final Topic topic, final IEventListener listener);

	/**
	 * Adds a topic on which currently login user shall subscribe for UI notifications.
	 * 
	 * @param topic
	 */
	void addAvailableUserNotificationsTopic(final Topic topic);

	/**
	 * @return list of available topics on which user can subscribe for UI notifications
	 */
	Set<Topic> getAvailableUserNotificationsTopics();

	/**
	 * Check remote endpoint connection status and send notifications in case it's down.
	 * 
	 * @return true if remote endpoint connection is up
	 */
	boolean checkRemoteEndpointStatus();
}
