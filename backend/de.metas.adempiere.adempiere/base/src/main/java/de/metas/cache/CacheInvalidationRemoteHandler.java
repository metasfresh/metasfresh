package de.metas.cache;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.adempiere.ad.dao.cache.CacheInvalidateMultiRequestSerializer;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.impl.EventMDC;
import de.metas.event.remote.RabbitMQEventBusConfiguration;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/** Bidirectional binding between local cache system and remote cache systems */
final class CacheInvalidationRemoteHandler implements IEventListener
{
	public static final transient CacheInvalidationRemoteHandler instance = new CacheInvalidationRemoteHandler();

	private static final Logger logger = LogManager.getLogger(CacheInvalidationRemoteHandler.class);

	private static final Topic TOPIC_CacheInvalidation = RabbitMQEventBusConfiguration.CacheInvalidationQueueConfiguration.EVENTBUS_TOPIC;

	private static final String EVENT_PROPERTY = CacheInvalidateRequest.class.getSimpleName();

	private final AtomicBoolean _initalized = new AtomicBoolean(false);
	private ImmutableTableNamesGroupsIndex _tableNamesToBroadcastIndex = ImmutableTableNamesGroupsIndex.EMPTY;

	private final CacheInvalidateMultiRequestSerializer jsonSerializer = new CacheInvalidateMultiRequestSerializer();

	private CacheInvalidationRemoteHandler()
	{
	}

	public void enable()
	{
		// Do nothing if already registered.
		if (_initalized.getAndSet(true))
		{
			return;
		}

		//
		// Globally register this listener.
		// We register it globally because we want to survive.
		final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
		eventBusFactory.registerGlobalEventListener(TOPIC_CacheInvalidation, instance);
	}

	private boolean isEnabled()
	{
		return _initalized.get();
	}

	/**
	 * Enable cache invalidation broadcasting for given table name.
	 *
	 * @param tableName
	 */
	public void enableForTableName(@NonNull final String tableName)
	{
		enable();
		changeTableNamesToBroadcastIndex(tableNamesToBroadcast -> tableNamesToBroadcast.addingToDefaultGroup(tableName));
	}

	/**
	 * Enable cache invalidation broadcasting for given table names.
	 *
	 * @param tableName
	 */
	public void enableForTableNamesGroup(@NonNull final TableNamesGroup group)
	{
		enable();
		changeTableNamesToBroadcastIndex(index -> index.replacingGroup(group));
		logger.info("Enabled for {}", group);
	}

	private synchronized void changeTableNamesToBroadcastIndex(@NonNull final Function<ImmutableTableNamesGroupsIndex, ImmutableTableNamesGroupsIndex> mapper)
	{
		_tableNamesToBroadcastIndex = mapper.apply(_tableNamesToBroadcastIndex);
	}

	private ImmutableTableNamesGroupsIndex getTableNamesToBroadcastIndex()
	{
		return _tableNamesToBroadcastIndex;
	}

	public ImmutableSet<String> getTableNamesToBroadcast()
	{
		return getTableNamesToBroadcastIndex().getTableNames();
	}

	/**
	 * Broadcast a cache invalidation request.
	 */
	public void postEvent(final CacheInvalidateMultiRequest request)
	{
		// Do nothing if cache invalidation broadcasting is not enabled
		if (!isEnabled())
		{
			logger.trace("Skip broadcasting {} because feature is not enabled", request);
			return;
		}

		// Do nothing if given table name is not in our table names to broadcast list
		if (!isAllowBroadcast(request))
		{
			logger.trace("Skip broadcasting {} because it's not allowed", request);
			return;
		}

		// Broadcast the event.
		final Event event = createEventFromRequest(request);
		try (final MDCCloseable mdc = EventMDC.putEvent(event))
		{
			logger.debug("Broadcasting cacheInvalidateMultiRequest={}", request);
			Services.get(IEventBusFactory.class)
					.getEventBus(TOPIC_CacheInvalidation)
					.postEvent(event);
		}
	}

	private boolean isAllowBroadcast(final CacheInvalidateMultiRequest multiRequest)
	{
		return multiRequest.getRequests().stream().anyMatch(this::isAllowBroadcast);
	}

	private boolean isAllowBroadcast(final CacheInvalidateRequest request)
	{
		final ImmutableTableNamesGroupsIndex index = this.getTableNamesToBroadcastIndex();
		return index.containsTableName(request.getRootTableName())
				|| index.containsTableName(request.getChildTableName());
	}

	/**
	 * Called when we got a remote cache invalidation request. It tries to invalidate local caches.
	 */
	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		// Ignore local events because they were fired from CacheMgt.reset methods.
		// If we would not do so, we would have an infinite loop here.
		if (event.isLocalEvent())
		{
			logger.debug("onEvent - ignoring local event={}", event);
			return;
		}

		final CacheInvalidateMultiRequest request = createRequestFromEvent(event);
		if (request == null)
		{
			logger.debug("onEvent - ignoring event without payload; event={}", event);
			return;
		}

		//
		// Reset cache for TableName/Record_ID
		logger.debug("onEvent - resetting local cache for request {} because we got remote event={}", request, event);
		CacheMgt.get().reset(request, CacheMgt.ResetMode.LOCAL); // don't broadcast it anymore because else we would introduce recursion
	}

	@VisibleForTesting
	Event createEventFromRequest(@NonNull final CacheInvalidateMultiRequest request)
	{
		final Event event = Event.builder()
				.putProperty(EVENT_PROPERTY, jsonSerializer.toJson(request))
				.build();

		return event;
	}

	private CacheInvalidateMultiRequest createRequestFromEvent(final Event event)
	{
		final String jsonRequest = event.getProperty(EVENT_PROPERTY);
		if (Check.isEmpty(jsonRequest, true))
		{
			return null;
		}

		return jsonSerializer.fromJson(jsonRequest);
	}
}
