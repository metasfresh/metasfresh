package de.metas.event.remote;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;

/**
 * Defines an integration point between a remote endpoint and {@link IEventBus}. Binding the endpoint to the event bus is done by registering a forwarding {@link IEventListener}.
 * 
 * @author tsa
 *
 */
public interface IEventBusRemoteEndpoint
{
	/**
	 * Set the given <code>event</code> to the event bus identified by the given <code>topicName</code>.
	 * 
	 * @param topicName
	 * @param event
	 */
	void sendEvent(String topicName, Event event);

	/**
	 * Bind given event bus to this remote endpoint, so events the from bus will be forwarded to remote endpoint and vice-versa.<br>
	 * Binding is done by subscribing an event listener to the given <code>eventBus</code> whose job it is to forward events to a remote topic.<br>
	 * Do nothing if binding is disabled according to the current config. See <a href="http://dewiki908/mediawiki/index.php/de.metas.event-Overview">de.metas.event-Overview</a> and/or
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
