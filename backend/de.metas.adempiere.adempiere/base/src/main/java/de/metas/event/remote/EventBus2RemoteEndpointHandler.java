package de.metas.event.remote;

import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import lombok.NonNull;

/**
 * Forward {@link Event}s from {@link IEventBus} to the remote endpoint (RabbitMQ).
 *
 * @author tsa
 *
 */
class EventBus2RemoteEndpointHandler implements IEventListener
{
	private static final Logger logger = LogManager.getLogger(EventBus2RemoteEndpointHandler.class);

	public static EventBus2RemoteEndpointHandler newInstance(final IEventBusRemoteEndpoint remoteEndpoint)
	{
		return new EventBus2RemoteEndpointHandler(remoteEndpoint);
	}

	private final IEventBusRemoteEndpoint remoteEndpoint;

	private EventBus2RemoteEndpointHandler(@NonNull final IEventBusRemoteEndpoint remoteEndpoint)
	{
		this.remoteEndpoint = remoteEndpoint;
	}

	@Override
	public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
	{
		logger.debug("onEvent - Sending event with UUID={} to remoteEndpoint={}", event.getUuid(), remoteEndpoint);

		final String topicName = eventBus.getTopicName();
		remoteEndpoint.sendEvent(topicName, event);
	}
}
