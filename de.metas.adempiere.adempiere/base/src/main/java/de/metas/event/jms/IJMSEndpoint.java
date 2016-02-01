package de.metas.event.jms;

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


import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;

/**
 * Defines an integration point between JMS and {@link IEventBus}. Binding the endpoint to the event bus is done by registering a forwarding {@link IEventListener}.
 * 
 * @author tsa
 *
 */
public interface IJMSEndpoint
{
	/**
	 * Set the given <code>event</code> to the event bus identified by the given <code>topicName</code>.
	 * 
	 * @param topicName
	 * @param event
	 */

	void sendEvent(String topicName, Event event);

	/**
	 * Bind given event bus to this JMS endpoint, so events the from bus will be forwarded to JMS and vice-versa.<br>
	 * Binding is done by subscribing an event listener to the given <code>eventBus</code> whose job it is to forward events to a JMS topic.<br>
	 * Do nothing if binding is disabled according to the current config. See <a href="http://dewiki908/mediawiki/index.php/de.metas.event-Overview">de.metas.event-Overvie</a> and/or
	 * {@link SysconfigBackedAllowRemoteBindingPredicate} for details.
	 * 
	 * @param eventBus
	 * @return <code>true</code> iff the event bus was actually bound to the endpoint.
	 * 
	 */
	boolean bindIfNeeded(IEventBus eventBus);
	/** @return true if the connection to endpoint is alive */
	boolean isConnected();

	/**
	 * Check remote endpoint connection status and send notifications in case it's down.
	 * 
	 * @return true if remote endpoint connection is up
	 */
	void checkConnection();
}
