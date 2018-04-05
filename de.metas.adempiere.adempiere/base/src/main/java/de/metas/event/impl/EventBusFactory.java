package de.metas.event.impl;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.adempiere.util.jmx.JMXRegistry;
import org.adempiere.util.jmx.JMXRegistry.OnJMXAlreadyExistsPolicy;
import org.slf4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;

import de.metas.event.EventBusConstants;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.event.jms.ActiveMQJMSEndpoint;
import de.metas.event.jms.IJMSEndpoint;
import de.metas.event.jmx.JMXEventBusManager;
import de.metas.event.log.EventBus2EventLogHandler;
import de.metas.logging.LogManager;
import lombok.NonNull;

public class EventBusFactory implements IEventBusFactory
{

	private static final Logger logger = LogManager.getLogger(EventBusFactory.class);

	/**
	 * Map of "topic name" to list of {@link IEventListener}s.
	 */
	private final SetMultimap<Topic, IEventListener> globalEventListeners = HashMultimap.create();

	private final LoadingCache<Topic, EventBus> topic2eventBus = CacheBuilder.newBuilder()
			.removalListener(new RemovalListener<Topic, EventBus>()
			{
				@Override
				public void onRemoval(final RemovalNotification<Topic, EventBus> notification)
				{
					final EventBus eventBus = notification.getValue();
					destroyEventBus(eventBus);
				}
			})
			.build(new CacheLoader<Topic, EventBus>()
			{
				@Override
				public EventBus load(final Topic topic) throws Exception
				{
					return createEventBus(topic);
				}
			});

	private final IJMSEndpoint jmsEndpoint = new ActiveMQJMSEndpoint();

	private final ExecutorService eventBusExecutor;

	private final Set<Topic> availableUserNotificationsTopic = ConcurrentHashMap.newKeySet(10);

	public EventBusFactory()
	{
		JMXRegistry.get().registerJMX(new JMXEventBusManager(jmsEndpoint), OnJMXAlreadyExistsPolicy.Replace);

		// Setup EventBus executor
		if (EventBusConstants.isEventBusPostEventsAsync())
		{
			eventBusExecutor = Executors.newSingleThreadExecutor(CustomizableThreadFactory.builder()
					.setThreadNamePrefix(getClass().getName() + "-AsyncExecutor")
					.setDaemon(true)
					.build());
		}
		else
		{
			eventBusExecutor = null;
		}

		//
		// Setup default user notification topics
		addAvailableUserNotificationsTopic(EventBusConstants.TOPIC_GeneralUserNotifications);
		addAvailableUserNotificationsTopic(EventBusConstants.TOPIC_GeneralUserNotificationsLocal);
	}

	@Override
	public IEventBus getEventBus(final Topic topic)
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
	 * Creates the event bus. If the remove event forwarding system is enabled <b>and</b> if the type of the given <code>topic</code> is {@link Type#REMOTE}, then the event bus is also bould to a JMS
	 * endpoint. Otherwise the event bus will only be local.
	 *
	 * @param topic
	 * @return
	 */
	private final EventBus createEventBus(final Topic topic)
	{
		// Create the event bus
		final EventBus eventBus = new EventBus(topic.getName(), eventBusExecutor);

		// whether the event is really stored is determined for each individual event
		eventBus.subscribe(EventBus2EventLogHandler.INSTANCE);

		// Bind the EventBus to JMS (only if the system is enabled).
		// If is not enabled we will use only local event buses,
		// because if we would return null or fail here a lot of BLs could fail.
		if (Type.REMOTE.equals(topic.getType()))
		{
			if (!EventBusConstants.isEnabled())
			{
				logger.warn("Remote events are disabled via EventBusConstants. Creating local-only eventBus for topic={}", topic);
			}
			else if (jmsEndpoint.bindIfNeeded(eventBus))
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

	private void destroyEventBus(final EventBus eventBus)
	{
		eventBus.destroy();
	}

	@Override
	public void registerGlobalEventListener(
			@NonNull final Topic topic,
			@NonNull final IEventListener listener)
	{
		//
		// Add the listener to our global listeners multimap.
		if (!globalEventListeners.put(topic, listener))
		{
			// listener already exists => do nothing
			return;
		}

		//
		// Also register the listener to EventBus
		getEventBus(topic).subscribe(listener);
	}

	@Override
	public void addAvailableUserNotificationsTopic(@NonNull final Topic topic)
	{
		availableUserNotificationsTopic.add(topic);
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
	public void registerWeakUserNotificationsListener(@NonNull final IEventListener listener)
	{
		getAvailableUserNotificationsTopics()
				.stream()
				.map(this::getEventBus)
				.forEach(eventBus -> eventBus.subscribeWeak(listener));
	}

	@Override
	public boolean checkRemoteEndpointStatus()
	{
		jmsEndpoint.checkConnection();
		return jmsEndpoint.isConnected();
	}
}
