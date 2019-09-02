package de.metas.event.remote;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.util.Loggables;
import lombok.NonNull;

/**
 * Forward {@link Event}s from {@link IEventBus} to the remote endpoint (AMQP).
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
		final String topicName = eventBus.getTopicName();
		remoteEndpoint.sendEvent(topicName, event);

		// doesn't hurt, but note that oftentimes this send is done in an "after-commit" listener;
		// therefore the thread-local Logaable that you as a dev have in mind might already be gone when this is executed
		Loggables.addLog("Send event with UUID={} to remoteEndpoint={}", event.getUuid(), remoteEndpoint);
	}
}
