package org.compiere.util;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.dao.cache.CacheInvalidateRequest;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;

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

	private static final Topic TOPIC_CacheInvalidation = Topic.builder()
			.setName("de.metas.cache.CacheInvalidationRemoteHandler")
			.setType(Type.REMOTE)
			.build();
	private static final String EVENT_PROPERTY_RootTableName = "RootTableName";
	private static final String EVENT_PROPERTY_RootRecord_ID = "RootRecord_ID";
	private static final String EVENT_PROPERTY_ChildTableName = "ChildTableName";
	private static final String EVENT_PROPERTY_ChildRecord_ID = "ChildRecord_ID";

	private final AtomicBoolean _initalized = new AtomicBoolean(false);
	private final CopyOnWriteArraySet<String> tableNamesToBroadcast = new CopyOnWriteArraySet<>();

	private CacheInvalidationRemoteHandler()
	{
	}

	public final void enable()
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

	private final boolean isEnabled()
	{
		return _initalized.get();
	}

	/**
	 * Enable cache invalidation broadcasting for given table name.
	 *
	 * @param tableName
	 */
	public void enableForTableName(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");

		enable();
		tableNamesToBroadcast.add(tableName);
	}

	public Set<String> getTableNamesToBroadcast()
	{
		return ImmutableSet.copyOf(tableNamesToBroadcast);
	}

	/**
	 * Broadcast a cache invalidation request.
	 *
	 * @param tableName
	 * @param recordId
	 */
	public void postEvent(final CacheInvalidateRequest request)
	{
		// Do nothing if cache invalidation broadcasting is not enabled
		if (!isEnabled())
		{
			return;
		}

		// Do nothing if given table name is not in our table names to broadcast list
		if (!tableNamesToBroadcast.contains(request.getRootTableName())
				&& !tableNamesToBroadcast.contains(request.getChildTableName()))
		{
			return;
		}

		// Broadcast the event.
		final Event event = createEventFromRequest(request);
		Services.get(IEventBusFactory.class)
				.getEventBus(TOPIC_CacheInvalidation)
				.postEvent(event);

		logger.debug("Broadcasting cache invalidation of {}, event={}", request, event);
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
			return;
		}

		final CacheInvalidateRequest request = createRequestFromEvent(event);
		if (request == null)
		{
			logger.debug("Ignored event: {}", event);
			return;
		}

		//
		// Reset cache for TableName/Record_ID
		logger.debug("Reseting cache for {} because we got remote event: {}", request, event);
		final boolean broadcast = false; // don't broadcast it anymore because else we would introduce recursion
		CacheMgt.get().reset(request, broadcast);
	}

	private static final Event createEventFromRequest(final CacheInvalidateRequest request)
	{
		final Event event = Event.builder()
				.putProperty(EVENT_PROPERTY_RootTableName, request.getRootTableName())
				.putProperty(EVENT_PROPERTY_RootRecord_ID, request.getRootRecordId())
				.putProperty(EVENT_PROPERTY_ChildTableName, request.getChildTableName())
				.putProperty(EVENT_PROPERTY_ChildRecord_ID, request.getChildRecordId())
				.build();

		return event;
	}

	private static final CacheInvalidateRequest createRequestFromEvent(final Event event)
	{
		//
		// RootTableName
		final String rootTableName = event.getProperty(EVENT_PROPERTY_RootTableName);
		if (Check.isEmpty(rootTableName, true))
		{
			logger.debug("Ignored event without rootTableName set: {}", event);
			return null;
		}

		//
		// RootRecord_ID
		final Integer rootRecordId = event.getProperty(EVENT_PROPERTY_RootRecord_ID);
		if (rootRecordId == null || rootRecordId < 0)
		{
			return CacheInvalidateRequest.allRecordsForTable(rootTableName);
		}

		//
		// ChildTableName
		final String childTableName = event.getProperty(EVENT_PROPERTY_ChildTableName);
		if (Check.isEmpty(childTableName, true))
		{
			return CacheInvalidateRequest.rootRecord(rootTableName, rootRecordId);
		}

		//
		// ChildRecordId
		final Integer childRecordId = event.getProperty(EVENT_PROPERTY_ChildRecord_ID);
		if (childRecordId == null || childRecordId < 0)
		{
			return CacheInvalidateRequest.allChildRecords(rootTableName, rootRecordId, childTableName);
		}

		return CacheInvalidateRequest.builder()
				.rootRecord(rootTableName, rootRecordId)
				.childRecord(childTableName, childRecordId)
				.build();
	}
}
