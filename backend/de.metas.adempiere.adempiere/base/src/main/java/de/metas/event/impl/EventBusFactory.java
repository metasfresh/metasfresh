package de.metas.event.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import de.metas.event.EventBusConfig;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.event.jmx.JMXEventBusManager;
import de.metas.event.remote.IEventBusRemoteEndpoint;
import de.metas.logging.LogManager;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.adempiere.util.jmx.JMXRegistry;
import org.adempiere.util.jmx.JMXRegistry.OnJMXAlreadyExistsPolicy;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventBusFactory implements IEventBusFactory
{
	private static final Logger logger = LogManager.getLogger(EventBusFactory.class);

	/**
	 * Map of "topic name" to list of {@link IEventListener}s.
	 */
	private final SetMultimap<Topic, IEventListener> globalEventListeners = HashMultimap.create();

	private final LoadingCache<Topic, EventBus> topic2eventBus = CacheBuilder.newBuilder()
			.removalListener((RemovalListener<Topic, EventBus>)notification -> {
				final EventBus eventBus = notification.getValue();
				destroyEventBus(eventBus);
			})
			.build(new CacheLoader<Topic, EventBus>()
			{
				@Override
				public EventBus load(final @NonNull Topic topic)
				{
					return createEventBus(topic, meterRegistry);
				}
			});

	private final IEventBusRemoteEndpoint remoteEndpoint;
	private final MeterRegistry meterRegistry;

	private final Set<Topic> availableUserNotificationsTopic = ConcurrentHashMap.newKeySet(10);

	public EventBusFactory(
			@NonNull final IEventBusRemoteEndpoint remoteEndpoint,
			@NonNull final MeterRegistry meterRegistry)
	{
		this.remoteEndpoint = remoteEndpoint;
		this.meterRegistry = meterRegistry;
		logger.info("Using remote endpoint: {}", remoteEndpoint);

		JMXRegistry.get().registerJMX(new JMXEventBusManager(remoteEndpoint), OnJMXAlreadyExistsPolicy.Replace);

		// Setup default user notification topics
		addAvailableUserNotificationsTopic(EventBusConfig.TOPIC_GeneralUserNotifications);
		addAvailableUserNotificationsTopic(EventBusConfig.TOPIC_GeneralUserNotificationsLocal);

		remoteEndpoint.setEventBusFactory(this);
	}

	@Override
	public IEventBus getEventBus(@NonNull final Topic topic)
	{
		try
		{
			EventBus eventBus = topic2eventBus.get(topic);

			final boolean typeMismatchBetweenTopicAndBus = topic.getType().equals(Type.REMOTE) && !eventBus.getType().equals(Type.REMOTE);
			if (typeMismatchBetweenTopicAndBus)
			{
				topic2eventBus.invalidate(topic);
				eventBus = topic2eventBus.get(topic); // 2nd try
			}
			return eventBus;
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@Nullable
	@Override
	public IEventBus getEventBusIfExists(@NonNull final Topic topic)
	{
		return topic2eventBus.getIfPresent(topic);
	}

	@Override
	public List<IEventBus> getAllEventBusInstances()
	{
		return ImmutableList.copyOf(topic2eventBus.asMap().values());
	}

	@Override
	public void initEventBussesWithGlobalListeners()
	{
		final ImmutableSet<Topic> topics = ImmutableSet.copyOf(globalEventListeners.keySet());
		for (final Topic topic : topics)
		{
			getEventBus(topic);
		}
	}

	@Override
	public void destroyAllEventBusses()
	{
		topic2eventBus.invalidateAll();
		topic2eventBus.cleanUp();
	}

	/**
	 * Creates the event bus.
	 * If the remote event forwarding system is enabled <b>and</b> if the type of the given <code>topic</code> is {@link Type#REMOTE},
	 * then the event bus is also bound to a remote endpoint.
	 * Otherwise the event bus will only be local.
	 */
	private EventBus createEventBus(@NonNull final Topic topic, @NonNull final MeterRegistry meterRegistry)
	{
		final MicrometerEventBusStatsCollector statsCollector = createMicrometerEventBusStatsCollector(topic, meterRegistry);

		// Create the event bus
		final EventBus eventBus = new EventBus(topic.getName(), createExecutorOrNull(topic), statsCollector);

		// Bind the EventBus to remote endpoint (only if the system is enabled).
		// If is not enabled we will use only local event buses,
		// because if we would return null or fail here a lot of BLs could fail.
		if (Type.REMOTE.equals(topic.getType()))
		{
			if (!EventBusConfig.isEnabled())
			{
				logger.warn("Remote events are disabled via EventBusConstants. Creating local-only eventBus for topic={}", topic);
			}
			else if (remoteEndpoint.bindIfNeeded(eventBus))
			{
				eventBus.setTypeRemote();
			}
		}

		// Add our global listeners
		final Set<IEventListener> globalListeners = globalEventListeners.get(topic);
		for (final IEventListener globalListener : globalListeners)
		{
			eventBus.subscribe(globalListener);
		}

		return eventBus;
	}

	static MicrometerEventBusStatsCollector createMicrometerEventBusStatsCollector(
			@NonNull final Topic topic,
			@NonNull final MeterRegistry meterRegistry)
	{
		final ImmutableList<Tag> tags = ImmutableList.of(
				Tag.of("type", topic.getType().toString()));

		final Timer eventProcessing = meterRegistry.timer("mf.eventBus.processor", tags);

		final AtomicLong queueLength = meterRegistry.gauge("mf.eventBus." + topic.getFullName() + ".queueSize", tags, new AtomicLong(0));
		final Counter enqueued = meterRegistry.counter("mf.eventBus." + topic.getFullName() + ".enqueued", tags);
		final Counter dequeued = meterRegistry.counter("mf.eventBus." + topic.getFullName() + ".dequeued", tags);

		return MicrometerEventBusStatsCollector.builder()
				.eventsEnqueued(enqueued)
				.eventsDequeued(dequeued)
				.eventProcessingTimer(eventProcessing)
				.queueLength(queueLength).build();
	}

	@Nullable
	private ExecutorService createExecutorOrNull(@NonNull final Topic topic)
	{
		// Setup EventBus executor
		if (EventBusConfig.isEventBusPostAsync(topic))
		{
			return Executors.newSingleThreadExecutor(CustomizableThreadFactory.builder()
															 .setThreadNamePrefix(getClass().getName() + "-" + topic.getName() + "-AsyncExecutor")
															 .setDaemon(true)
															 .build());
		}
		else
		{
			return null;
		}
	}

	private void destroyEventBus(@NonNull final EventBus eventBus)
	{
		eventBus.destroy();
	}

	@Override
	public void registerGlobalEventListener(
			@NonNull final Topic topic,
			@NonNull final IEventListener listener)
	{
		// Register the listener to EventBus
		getEventBus(topic).subscribe(listener);

		// Add the listener to our global listeners-multimap.
		// Note that getEventBus(topic) creates the bus on the fly if needed **and subscribes all global listeners to it**
		// Therefore we need to add this listener to the global map *after* having gotten and possibly on-the-fly-created the event bus.
		if (!globalEventListeners.put(topic, listener))
		{
			// listener already exists => do nothing
			return;
		}
		logger.info("Registered global listener to {}: {}", topic, listener);
	}

	@Override
	public void addAvailableUserNotificationsTopic(@NonNull final Topic topic)
	{
		final boolean added = availableUserNotificationsTopic.add(topic);
		logger.info("Registered user notifications topic: {} (already registered: {})", topic, !added);
	}

	/**
	 * @return list of available topics on which user can subscribe for UI notifications
	 */
	private Set<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableSet.copyOf(availableUserNotificationsTopic);
	}

	@Override
	public void registerUserNotificationsListener(@NonNull final IEventListener listener)
	{
		getAvailableUserNotificationsTopics()
				.stream()
				.map(this::getEventBus)
				.forEach(eventBus -> eventBus.subscribe(listener));
	}

	@Override
	public void unregisterUserNotificationsListener(@NonNull final IEventListener listener)
	{
		getAvailableUserNotificationsTopics()
				.stream()
				.map(this::getEventBus)
				.forEach(eventBus -> eventBus.unsubscribe(listener));
	}

	@Override
	public boolean checkRemoteEndpointStatus()
	{
		remoteEndpoint.checkConnection();
		return remoteEndpoint.isConnected();
	}
}
