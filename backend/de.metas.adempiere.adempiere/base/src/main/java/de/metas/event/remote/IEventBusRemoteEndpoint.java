package de.metas.event.remote;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import lombok.NonNull;

/**
 * Defines an integration point between a remote endpoint and {@link IEventBus}. Binding the endpoint to the event bus is done by registering a forwarding {@link IEventListener}.
 */
public interface IEventBusRemoteEndpoint
{
	/** Called by API when this endpoint is registered to event bus factory */
	void setEventBusFactory(@NonNull IEventBusFactory eventBusFactory);

	/** @return true if the connection to endpoint is alive */
	boolean isConnected();

	/**
	 * Check remote endpoint connection status and send notifications in case it's down.
	 *
	 * @return true if remote endpoint connection is up
	 */
	void checkConnection();
}
