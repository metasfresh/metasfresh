package de.metas.event.remote.rabbitmq;

import de.metas.event.Event;
import de.metas.event.EventBusConfig;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.event.impl.EventBusMonitoringService;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class RabbitMQReceiveFromEndpointTemplate
{
	private final Logger logger = LogManager.getLogger(getClass());

	public static final String HEADER_SenderId = "metasfresh-events.SenderId";
	public static final String HEADER_TopicName = "metasfresh-events.TopicName";
	public static final String HEADER_TopicType = "metasfresh-events.TopicType";

	@NonNull private final String senderId = EventBusConfig.getSenderId();

	@NonNull private final IEventBusFactory eventBusFactory;
	@NonNull private final EventBusMonitoringService eventBusMonitoringService;

	protected final void onRemoteEvent(
			final Event event,
			final String senderId,
			final String topicName,
			final Type topicType)
	{
		final Topic topic = Topic.of(topicName, topicType);

		if (topic.getType() != topicType)
		{
			logger.debug("onEvent - different local topicType for topicName:[{}], remote type:[{}] local type=[{}]; -> ignoring event", topicName, topicType, topic.getType());
			return;
		}

		final IEventBus localEventBus = eventBusFactory.getEventBus(topic);

		if (Type.LOCAL == topic.getType() && !Objects.equals(getSenderId(), senderId))
		{
			logger.debug("onEvent - type LOCAL but event's senderId={} is not equal to the *local* senderId={}; -> ignoring event", senderId, getSenderId());
			return;
		}

		final boolean monitorIncomingEvents = eventBusMonitoringService.isMonitorIncomingEvents();
		final boolean localEventBusAsync = localEventBus.isAsync();
		final Topic localEventBusTopic = localEventBus.getTopic();
		try
		{
			if (monitorIncomingEvents && !localEventBusAsync)
			{
				logger.debug("onEvent - localEventBus is not async and isMonitorIncomingEvents=true; -> monitoring event processing; localEventBus={}", localEventBus);
				eventBusMonitoringService.extractInfosAndMonitor(event,
						localEventBusTopic,
						() -> onRemoteEvent0(localEventBus, event, localEventBusTopic.getName()));
			}
			else
			{
				logger.debug("onEvent - localEventBus.isAsync={} and isMonitorIncomingEvents={}; -> cannot monitor event processing; localEventBus={}", localEventBusAsync, monitorIncomingEvents, localEventBus);
				onRemoteEvent0(localEventBus, event, localEventBusTopic.getName());
			}
		}
		catch (final Exception ex)
		{
			logger.warn("onEvent - Failed forwarding event to topic {}: {}", localEventBusTopic.getName(), event, ex);
		}
	}

	private void onRemoteEvent0(
			@NonNull final IEventBus localEventBus,
			@NonNull final Event event,
			@NonNull final String topicName)
	{
		localEventBus.processEvent(event);
		logger.debug("Received and processed event in {}ms, topic={}: {}", computeElapsedDuration(event), topicName, event);
	}

	private static long computeElapsedDuration(@NonNull final Event event)
	{
		final Instant eventTime = event.getWhen();
		if (eventTime == null)
		{
			return -1;
		}
		else
		{
			return System.currentTimeMillis() - eventTime.toEpochMilli();
		}
	}

	@NonNull
	private String getSenderId() {return senderId;}
}
