/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.spi.ITrxListener;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.WeakList;
import org.adempiere.util.jmx.JMXRegistry;
import org.adempiere.util.jmx.JMXRegistry.OnJMXAlreadyExistsPolicy;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;

/**
 * Adempiere Cache Management
 *
 * @author Jorg Janke
 * @version $Id: CacheMgt.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public final class CacheMgt
{
	/**
	 * Get Cache Management
	 * 
	 * @return Cache Manager
	 */
	public static final CacheMgt get()
	{
		return s_cache;
	}	// get

	/** Singleton */
	private static final CacheMgt s_cache = new CacheMgt();

	public static final String JMX_BASE_NAME = "org.adempiere.cache";

	/**
	 * Private Constructor
	 */
	private CacheMgt()
	{
		super();

		JMXRegistry.get().registerJMX(new JMXCacheMgt(), OnJMXAlreadyExistsPolicy.Replace);
	}

	public static final int RECORD_ID_ALL = -1;

	/** List of Instances */
	private final WeakList<CacheInterface> cacheInstances = new WeakList<CacheInterface>();
	private final ReentrantLock cacheInstancesLock = cacheInstances.getReentrantLock();

	/**
	 * List of Table Names.
	 * 
	 * i.e. map of TableName to "how many cache instances do we have for that table name"
	 */
	private final Map<String, AtomicInteger> tableNames = new HashMap<>();

	/** Logger */
	static final transient Logger log = LogManager.getLogger(CacheMgt.class);

	/**
	 * Enable caches for the given table to be invalidated by remote events. Example: if a user somewhere else opens/closes a period, we can allow the system to invalidate the local cache to avoid it
	 * becoming stale.
	 * 
	 * @param tableName
	 */
	public final void enableRemoteCacheInvalidationForTableName(final String tableName)
	{
		RemoteCacheInvalidationHandler.instance.enableForTableName(tableName);
	}

	/**************************************************************************
	 * Register Cache Instance
	 *
	 * @param instance Cache
	 * @return true if added
	 */
	public boolean register(final CacheInterface instance)
	{
		final Boolean registerWeak = null; // auto
		return register(instance, registerWeak);
	}
	
	private boolean register(final CacheInterface instance, final Boolean registerWeak)
	{
		if (instance == null)
		{
			return false;
		}

		//
		// Extract cache instance's tableName (if any)
		final String tableName = getTableNameOrNull(instance);
		
		//
		// Determine if we shall register the cache instance weakly or not.
		final boolean registerWeakEffective;
		if (tableName != null)
		{
			registerWeakEffective = registerWeak == null ? true : registerWeak;
		}
		else
		{
			// NOTE: if the cache is not providing an TableName, we register them with a hard-reference because probably is a cache listener
			registerWeakEffective = registerWeak == null ? false : registerWeak;
		}


		cacheInstancesLock.lock();
		try
		{
			if (tableName != null)
			{
				//
				// Increment tableName counter
				tableNames
					.computeIfAbsent(tableName, k -> new AtomicInteger(0))
					.incrementAndGet();
			}

			return cacheInstances.add(instance, registerWeakEffective);
		}
		finally
		{
			cacheInstancesLock.unlock();
		}
	}	// register

	/**
	 * Un-Register Cache Instance
	 *
	 * @param instance Cache
	 * @return true if removed
	 */
	public boolean unregister(final CacheInterface instance)
	{
		if (instance == null)
		{
			return false;
		}

		final String tableName = getTableNameOrNull(instance);

		cacheInstancesLock.lock();
		try
		{
			int countRemoved = 0;

			// Could be included multiple times
			final int size = cacheInstances.size();
			for (int i = size - 1; i >= 0; i--)
			{
				final CacheInterface stored = cacheInstances.get(i);
				if (instance.equals(stored))
				{
					cacheInstances.remove(i);
					countRemoved++;
				}
			}

			//
			// Remove it from tableNames
			if (tableName != null)
			{
				final AtomicInteger count = tableNames.remove(tableName);
				if (count == null)
				{
					// let it removed
				}
				else
				{
					final int countNew = count.get() - countRemoved;
					if (countNew > 0)
					{
						count.set(countNew);
						tableNames.put(tableName, count);
					}
				}
			}

			final boolean found = countRemoved > 0;
			return found;
		}
		finally
		{
			cacheInstancesLock.unlock();
		}
	}	// unregister

	/**
	 * Extracts the TableName from given cache instance.
	 * 
	 * @param instance
	 * @return table name or <code>null</code> if table name could not be extracted
	 */
	private static final String getTableNameOrNull(final CacheInterface instance)
	{
		if (instance instanceof ITableAwareCacheInterface)
		{
			final ITableAwareCacheInterface recordsCache = (ITableAwareCacheInterface)instance;

			// Try cache TableName
			final String tableName = recordsCache.getTableName();
			if (tableName != null && !tableName.isEmpty())
			{
				return tableName;
			}

			// Try cache Name
			final String cacheName = recordsCache.getName();
			if (cacheName != null && !cacheName.isEmpty())
			{
				return cacheName;
			}

			// Fallback: return null because there is no table, no cache name
			return null;
		}

		// Fallback for any other cache interfaces: return null because TableName is not available
		return null;
	}

	public Set<String> getTableNames()
	{
		return ImmutableSet.copyOf(tableNames.keySet());
	}

	public Set<String> getTableNamesToBroadcast()
	{
		return RemoteCacheInvalidationHandler.instance.getTableNamesToBroadcast();
	}

	/**
	 * Invalidate ALL cached entries of all registered {@link CacheInterface}s.
	 * 
	 * @return how many cache entries were invalidated
	 */
	public int reset()
	{
		// Do nothing if already running (i.e. avoid recursion)
		if (cacheResetRunning.getAndSet(true))
		{
			return 0;
		}

		cacheInstancesLock.lock();
		int counter = 0;
		int total = 0;
		try
		{

			for (final CacheInterface cacheInstance : cacheInstances)
			{
				if (cacheInstance != null && cacheInstance.size() > 0)
				{
					total += resetNoFail(cacheInstance);
					counter++;
				}
			}
		}
		finally
		{
			cacheInstancesLock.unlock();
			cacheResetRunning.set(false);
		}

		log.info("Reset all: {} cache instances invalidated ({} cached items invalidated)", counter, total);

		return total;
	}	// reset

	private final transient AtomicBoolean cacheResetRunning = new AtomicBoolean();

	/**
	 * Invalidate all cached entries for given TableName.
	 * 
	 * @param tableName table name
	 * @return how many cache entries were invalidated
	 */
	public int reset(final String tableName)
	{
		final int recordId = RECORD_ID_ALL;
		return reset(tableName, recordId);
	}	// reset

	/**
	 * Invalidate all cached entries for given TableName/Record_ID.
	 * 
	 * @param tableName table name
	 * @param recordId record if applicable or {@link #RECORD_ID_ALL} for all
	 * @return how many cache entries were invalidated
	 */
	public int reset(final String tableName, final int recordId)
	{
		final boolean broadcast = true;
		return reset(tableName, recordId, broadcast);
	}

	/**
	 * Reset cache for TableName/Record_ID when given transaction is committed.
	 * 
	 * If no transaction was given or given transaction was not found, the cache is reset right away.
	 * 
	 * @param trxName
	 * @param tableName
	 * @param recordId
	 */
	public void resetOnTrxCommit(final String trxName, final String tableName, final int recordId)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(trx))
		{
			reset(tableName, recordId);
			return;
		}

		RecordsToResetOnTrxCommitCollector.getCreate(trx).addRecord(tableName, recordId);
	}

	/**
	 * Invalidate all cached entries for given TableName/Record_ID.
	 * 
	 * @param tableName
	 * @param recordId
	 * @param broadcast true if we shall also broadcast this remotely.
	 * @return how many cache entries were invalidated
	 */
	private final int reset(final String tableName, final int recordId, final boolean broadcast)
	{
		if (tableName == null)
		{
			return reset();
		}

		cacheInstancesLock.lock();
		try
		{
			int counter = 0;
			int total = 0;

			//
			// Invalidate local caches if we have at least one cache interface about our table
			if (tableNames.containsKey(tableName))
			{
				for (final CacheInterface cacheInstance : cacheInstances)
				{
					if (cacheInstance == null)
					{
						// nothing to reset
					}
					else if (cacheInstance instanceof ITableAwareCacheInterface)
					{
						final ITableAwareCacheInterface recordsCache = (ITableAwareCacheInterface)cacheInstance;
						final int itemsRemoved = recordsCache.resetForRecordId(tableName, recordId);
						if (itemsRemoved > 0)
						{
							log.debug("Rest cache instance: {}", cacheInstance);
							total += itemsRemoved;
							counter++;
						}
					}
				}
			}
			//
			log.debug("Reset {}: {} cache interfaces checked ({} records invalidated)", tableName, counter, total);

			//
			// Broadcast cache invalidation.
			// We do this, even if we don't have any cache interface registered locally, because there might be remotely.
			if (broadcast)
			{
				RemoteCacheInvalidationHandler.instance.postEvent(tableName, recordId);
			}

			return total;
		}
		finally
		{
			cacheInstancesLock.unlock();
		}
	}	// reset

	/**
	 * Total Cached Elements
	 *
	 * @return count
	 */
	public int getElementCount()
	{
		int total = 0;
		cacheInstancesLock.lock();
		try
		{
			for (final CacheInterface cacheInstance : cacheInstances)
			{
				if (cacheInstance == null)
				{
					// do nothing
				}
				else
				{
					total += cacheInstance.size();
				}
			}
		}
		finally
		{
			cacheInstancesLock.unlock();
		}
		return total;
	}	// getElementCount

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("CacheMgt[");
		sb.append("Instances=")
				.append(cacheInstances.size())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Extended String Representation
	 *
	 * @return info
	 */
	public String toStringX()
	{
		StringBuilder sb = new StringBuilder("CacheMgt[");
		sb.append("Instances=")
				.append(cacheInstances.size())
				.append(", Elements=").append(getElementCount())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Reset cache and clear ALL registered {@link CacheInterface}s.
	 */
	public void clear()
	{
		cacheInstancesLock.lock();
		try
		{
			reset();

			// Make sure all cache instances are reset
			for (final CacheInterface cacheInstance : cacheInstances)
			{
				if (cacheInstance == null)
				{
					continue;
				}
				resetNoFail(cacheInstance);
			}

			cacheInstances.clear();
			tableNames.clear();
		}
		finally
		{
			cacheInstancesLock.unlock();
		}

	}

	private int resetNoFail(final CacheInterface cacheInstance)
	{
		try
		{
			return cacheInstance.reset();
		}
		catch (Exception e)
		{
			// log but don't fail
			log.warn("Error while reseting {}", cacheInstance, e);
			return 0;
		}
	}
	
	/**
	 * Adds an listener which will be fired when the cache for given table is about to be reset.
	 * 
	 * @param tableName
	 * @param cacheResetListener
	 */
	public void addCacheResetListener(final String tableName, final ICacheResetListener cacheResetListener)
	{
		final Boolean registerWeak = Boolean.FALSE;
		register(new CacheResetListener2CacheInterface(tableName, cacheResetListener), registerWeak);
	}

	private static final class CacheResetListener2CacheInterface implements ITableAwareCacheInterface
	{
		private final String tableName;
		private final ICacheResetListener listener;

		public CacheResetListener2CacheInterface(final String tableName, final ICacheResetListener listener)
		{
			super();
			Check.assumeNotEmpty(tableName, "tableName not empty");
			this.tableName = tableName;
			Check.assumeNotNull(listener, "listener not null");
			this.listener = listener;
		}
		
		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("tableName", tableName)
					.add("listener", listener)
					.toString();
		}
		
		@Override
		public int hashCode()
		{
			return new HashcodeBuilder()
					.append(tableName)
					.append(listener)
					.toHashcode();
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}

			final CacheResetListener2CacheInterface other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}
			return new EqualsBuilder()
					.append(this.tableName, other.tableName)
					.append(this.listener, other.listener)
					.isEqual();
		}

		@Override
		public int size()
		{
			return 1;
		}

		@Override
		public String getName()
		{
			return tableName;
		}

		@Override
		public String getTableName()
		{
			return tableName;
		}

		@Override
		public int reset()
		{
			final Object key = null;
			return listener.reset(tableName, key);
		}

		@Override
		public int resetForRecordId(String tableName, Object key)
		{
			return listener.reset(tableName, key);
		}
	}

	/** Bidirectional binding between local cache system and remote cache systems */
	private static final class RemoteCacheInvalidationHandler implements IEventListener
	{
		public static final transient CacheMgt.RemoteCacheInvalidationHandler instance = new CacheMgt.RemoteCacheInvalidationHandler();

		private static final Topic TOPIC_CacheInvalidation = Topic.builder()
				.setName("org.compiere.util.CacheMgt.CacheInvalidation")
				.setType(Type.REMOTE)
				.build();
		private static final String EVENT_PROPERTY_TableName = "TableName";
		private static final String EVENT_PROPERTY_Record_ID = "Record_ID";

		private boolean _initalized = false;
		private final Set<String> tableNamesToBroadcast = Sets.newConcurrentHashSet();

		private RemoteCacheInvalidationHandler()
		{
			super();
		}

		public final synchronized void enable()
		{
			// Do nothing if already registered.
			if (_initalized)
			{
				return;
			}

			//
			// Globally register this listener.
			// We register it globally because we want to survive.
			final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
			eventBusFactory.registerGlobalEventListener(TOPIC_CacheInvalidation, instance);

			_initalized = true;
		}

		private final boolean isEnabled()
		{
			return _initalized;
		}

		/**
		 * Enable cache invalidation broadcasting for given table name.
		 * 
		 * @param tableName
		 */
		public synchronized void enableForTableName(final String tableName)
		{
			Check.assumeNotEmpty(tableName, "tableName not empty");

			enable();
			tableNamesToBroadcast.add(tableName);
		}

		public synchronized Set<String> getTableNamesToBroadcast()
		{
			return ImmutableSet.copyOf(tableNamesToBroadcast);
		}

		/**
		 * Broadcast a cache invalidation request.
		 * 
		 * @param tableName
		 * @param recordId
		 */
		public void postEvent(final String tableName, final int recordId)
		{
			// Do nothing if cache invalidation broadcasting is not enabled
			if (!isEnabled())
			{
				return;
			}

			// Do nothing if given table name is not in our table names to broadcast list
			if (!tableNamesToBroadcast.contains(tableName))
			{
				return;
			}

			// Broadcast the event.
			final Event event = Event.builder()
					.putProperty(EVENT_PROPERTY_TableName, tableName)
					.putProperty(EVENT_PROPERTY_Record_ID, recordId < 0 ? RECORD_ID_ALL : recordId)
					.build();
			Services.get(IEventBusFactory.class)
					.getEventBus(TOPIC_CacheInvalidation)
					.postEvent(event);
			log.debug("Broadcasting cache invalidation of {}/{}, event={}", tableName, recordId, event);
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

			//
			// TableName
			final String tableName = event.getProperty(EVENT_PROPERTY_TableName);
			if (Check.isEmpty(tableName, true))
			{
				log.debug("Ignored event without tableName set: {}", event);
				return;
			}
			// NOTE: we try to invalidate the local cache even if the tableName is not in our tableNames to broadcast list.

			//
			// Record_ID
			Integer recordId = event.getProperty(EVENT_PROPERTY_Record_ID);
			if (recordId == null || recordId < 0)
			{
				recordId = RECORD_ID_ALL;
			}

			//
			// Reset cache for TableName/Record_ID
			log.debug("Reseting cache for {}/{} because we got remote event: {}", tableName, recordId, event);
			final boolean broadcast = false; // don't broadcast it anymore because else we would introduce recursion
			CacheMgt.get().reset(tableName, recordId, broadcast);
		}
	}

	/** Collects records that needs to be removed from cache when a given transaction is committed */
	private static final class RecordsToResetOnTrxCommitCollector extends TrxListenerAdapter
	{
		/** Gets/creates the records collector which needs to be reset when transaction is committed */
		public static final RecordsToResetOnTrxCommitCollector getCreate(final ITrx trx)
		{
			return trx.getProperty(TRX_PROPERTY, new Supplier<RecordsToResetOnTrxCommitCollector>()
			{

				@Override
				public RecordsToResetOnTrxCommitCollector get()
				{
					final RecordsToResetOnTrxCommitCollector collector = new RecordsToResetOnTrxCommitCollector();
					trx.getTrxListenerManager().registerListener(ResetCacheOnCommitTrxListener);
					return collector;

				}
			});
		}

		private static final String TRX_PROPERTY = RecordsToResetOnTrxCommitCollector.class.getName();

		/** Listens {@link ITrx}'s after-commit and fires enqueued cache invalidation requests */
		private static final ITrxListener ResetCacheOnCommitTrxListener = new TrxListenerAdapter()
		{
			@Override
			public void afterCommit(final ITrx trx)
			{
				final RecordsToResetOnTrxCommitCollector collector = trx.getProperty(TRX_PROPERTY);
				if (collector == null)
				{
					return;
				}

				collector.run();
			}
		};

		private final Set<ITableRecordReference> records = Sets.newConcurrentHashSet();

		/** Enqueues a record */
		public final void addRecord(final String tableName, final int recordId)
		{
			if (Check.isEmpty(tableName, true))
			{
				return;
			}
			if (recordId <= 0)
			{
				return;
			}
			final TableRecordReference record = new TableRecordReference(tableName, recordId);
			records.add(record);
			
			log.debug("Scheduled cache invalidation on transaction commit: {}", record);
		}

		/** Reset the cache for all enqueued records */
		private void run()
		{
			if (records.isEmpty())
			{
				return;
			}

			final CacheMgt cacheMgt = CacheMgt.get();
			for (final ITableRecordReference record : records)
			{
				cacheMgt.reset(record.getTableName(), record.getRecord_ID());
			}

			records.clear();
		}
	}
}
