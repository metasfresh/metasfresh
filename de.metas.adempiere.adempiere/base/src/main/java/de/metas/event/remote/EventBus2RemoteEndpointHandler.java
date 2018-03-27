package de.metas.event.remote;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import lombok.NonNull;

/**
 * Forward {@link Event}s from {@link IEventBus} to JMS.
 *
 * @author tsa
 *
 */
class EventBus2RemoteEndpointHandler implements IEventListener
{
	public static final EventBus2RemoteEndpointHandler newInstance(final IEventBusRemoteEndpoint remoteEndpoint)
	{
		return new EventBus2RemoteEndpointHandler(remoteEndpoint);
	}

	private final IEventBusRemoteEndpoint remoteEndpoint;

	private EventBus2RemoteEndpointHandler(@NonNull final IEventBusRemoteEndpoint remoteEndpoint)
	{
		this.remoteEndpoint = remoteEndpoint;
	}

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final String topicName = eventBus.getName();
		remoteEndpoint.sendEvent(topicName, event);
	}
}
