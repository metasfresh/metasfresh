/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.ad.dao.cache.CacheInvalidateMultiRequest;
import org.adempiere.ad.dao.cache.CacheInvalidateRequest;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.WeakList;
import org.adempiere.util.jmx.JMXRegistry;
import org.adempiere.util.jmx.JMXRegistry.OnJMXAlreadyExistsPolicy;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.Value;

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
	private final WeakList<CacheInterface> cacheInstances = new WeakList<>();
	private final ReentrantLock cacheInstancesLock = cacheInstances.getReentrantLock();

	private CopyOnWriteArrayList<ICacheResetListener> globalCacheResetListeners = new CopyOnWriteArrayList<>();

	/**
	 * List of Table Names.
	 *
	 * i.e. map of TableName to "how many cache instances do we have for that table name"
	 */
	private final Map<String, AtomicInteger> tableNames = new HashMap<>();

	/* package */ static final transient Logger log = LogManager.getLogger(CacheMgt.class);

	private final AtomicBoolean cacheResetRunning = new AtomicBoolean();
	private final AtomicLong lastCacheReset = new AtomicLong();

	/**
	 * Enable caches for the given table to be invalidated by remote events. Example: if a user somewhere else opens/closes a period, we can allow the system to invalidate the local cache to avoid it
	 * becoming stale.
	 *
	 * @param tableName
	 */
	public final void enableRemoteCacheInvalidationForTableName(final String tableName)
	{
		CacheInvalidationRemoteHandler.instance.enableForTableName(tableName);
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
		2 >>> 3;
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
		return CacheInvalidationRemoteHandler.instance.getTableNamesToBroadcast();
	}

	/** @return last time cache reset timestamp */
	public long getLastCacheReset()
	{
		return lastCacheReset.get();
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

			fireGlobalCacheResetListeners(CacheInvalidateMultiRequest.all());

			lastCacheReset.incrementAndGet();
		}
		finally
		{
			cacheInstancesLock.unlock();
			cacheResetRunning.set(false);
		}

		log.info("Reset all: {} cache instances invalidated ({} cached items invalidated)", counter, total);

		return total;
	}

	private void fireGlobalCacheResetListeners(final CacheInvalidateMultiRequest multiRequest)
	{
		for (final ICacheResetListener globalCacheResetListener : globalCacheResetListeners)
		{
			try
			{
				globalCacheResetListener.reset(multiRequest);
			}
			catch (final Exception ex)
			{
				log.warn("Failed reseting {}. Ignored.", globalCacheResetListener, ex);
			}
		}
	}

	/**
	 * Invalidate all cached entries for given TableName.
	 *
	 * @param tableName table name
	 * @return how many cache entries were invalidated
	 */
	public int reset(final String tableName)
	{
		final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.fromTableNameAndRecordId(tableName, RECORD_ID_ALL);
		return reset(request, ResetMode.LOCAL_AND_BROADCAST);
	}	// reset

	/**
	 * Invalidate all cached entries for given TableName.
	 *
	 * The event won't be broadcasted.
	 *
	 * @param tableName table name
	 * @return how many cache entries were invalidated
	 */
	public int resetLocal(final String tableName)
	{
		final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.fromTableNameAndRecordId(tableName, RECORD_ID_ALL);
		return reset(request, ResetMode.LOCAL);
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
		final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.fromTableNameAndRecordId(tableName, recordId);
		return reset(request, ResetMode.LOCAL_AND_BROADCAST);
	}

	public int reset(final CacheInvalidateRequest request)
	{
		return reset(CacheInvalidateMultiRequest.of(request), ResetMode.LOCAL_AND_BROADCAST);
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
	public void resetLocalNowAndBroadcastOnTrxCommit(final String trxName, final CacheInvalidateRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(trx))
		{
			reset(request);
			return;
		}
		else
		{
			reset(CacheInvalidateMultiRequest.of(request), ResetMode.LOCAL);
			RecordsToResetOnTrxCommitCollector.getCreate(trx).addRecord(request, ResetMode.JUST_BROADCAST);
		}
	}

	static enum ResetMode
	{
		LOCAL, LOCAL_AND_BROADCAST, JUST_BROADCAST;

		public boolean isResetLocal()
		{
			return this == LOCAL || this == LOCAL_AND_BROADCAST;
		}

		public boolean isBroadcast()
		{
			return this == LOCAL_AND_BROADCAST || this == JUST_BROADCAST;
		}
	}

	/**
	 * Invalidate all cached entries for given TableName/Record_ID.
	 *
	 * @param tableName
	 * @param recordId
	 * @param broadcast true if we shall also broadcast this remotely.
	 * @return how many cache entries were invalidated (estimated!)
	 */
	final int reset(@NonNull final CacheInvalidateMultiRequest multiRequest, @NonNull final ResetMode mode)
	{
		final int resetCount;
		if (mode.isResetLocal())
		{
			resetCount = resetCacheInstances(multiRequest);

			//
			fireGlobalCacheResetListeners(multiRequest);
		}
		else
		{
			resetCount = 0;
		}

		//
		// Broadcast cache invalidation.
		// We do this, even if we don't have any cache interface registered locally, because there might be remotely.
		if (mode.isBroadcast())
		{
			CacheInvalidationRemoteHandler.instance.postEvent(multiRequest);
		}

		return resetCount;
	}	// reset

	private final int resetCacheInstances(final CacheInvalidateMultiRequest multiRequest)
	{
		if (multiRequest.isResetAll())
		{
			return reset();
		}

		cacheInstancesLock.lock();

		final Map<String, AtomicInteger> tableNamesSnapshot;
		final List<CacheInterface> cacheInstancesSnapshot;
		try
		{
			tableNamesSnapshot = new HashMap<>(tableNames);
			cacheInstancesSnapshot = cacheInstances.hardList();
		}
		finally
		{
			cacheInstancesLock.unlock();
		}

		int total = 0;

		for (final CacheInvalidateRequest request : multiRequest.getRequests())
		{
			final int totalPerRequest = resetCacheInterfacesNoLock(
					request,
					tableNamesSnapshot,
					cacheInstancesSnapshot);
			total += totalPerRequest;
		}

		return total;

	}

	private static int resetCacheInterfacesNoLock(final CacheInvalidateRequest request,
			final Map<String, AtomicInteger> tableNamesSnapshot,
			final List<CacheInterface> cacheInstancesSnapshot)
	{
		final String tableName = request.getTableNameEffective();
		final int recordId = request.getRecordIdEffective();

		// optimization: skip if there is no cache interface registered for request's tableName
		if (!tableNamesSnapshot.containsKey(tableName))
		{
			return 0;
		}

		int total = 0;
		int counter = 0;

		//
		// Invalidate local caches if we have at least one cache interface about our table
		for (final CacheInterface cacheInstance : cacheInstancesSnapshot)
		{
			if (cacheInstance == null)
			{
				// nothing to reset
			}
			else if (cacheInstance instanceof CCache)
			{
				// NOTE: CCache requires all reset events, even if they were not it's table.
				// inside checks if table matches OR if it's cache name starts with given table name.
				// A total fucked up, not performant.
				// FIXME at least we shall use ConcurrentSkipListMap and prepare the steps to switch to some well known cache frameworks.
				final ITableAwareCacheInterface recordsCache = (ITableAwareCacheInterface)cacheInstance;
				final int itemsRemoved = recordsCache.resetForRecordId(tableName, recordId);
				if (itemsRemoved > 0)
				{
					log.debug("Rest cache instance for {}/{}: {}", tableName, recordId, cacheInstance);
					total += itemsRemoved;
					counter++;
				}
			}
			else if (cacheInstance instanceof ITableAwareCacheInterface)
			{
				if (tableName.equals(((ITableAwareCacheInterface)cacheInstance).getTableName()))
				{
					final ITableAwareCacheInterface recordsCache = (ITableAwareCacheInterface)cacheInstance;
					final int itemsRemoved = recordsCache.resetForRecordId(tableName, recordId);
					if (itemsRemoved > 0)
					{
						log.debug("Rest cache instance for {}/{}: {}", tableName, recordId, cacheInstance);
						total += itemsRemoved;
						counter++;
					}
				}
			}
			else
			{
				// NOTE: for other cache implementations we shall skip reseting by tableName/key because they don't support it.
				// e.g. de.metas.adempiere.report.jasper.client.JRClient.cacheListener, org.adempiere.ad.dao.cache.impl.ModelCacheService.ModelCacheService()
				log.debug("Unknown cache instance to reset: {}", cacheInstance);
			}
		}

		log.debug("Reset {}: {} cache interfaces affected ({} records invalidated)", tableName, counter, total);

		return total;
	}

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
		final StringBuilder sb = new StringBuilder("CacheMgt[");
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
		final StringBuilder sb = new StringBuilder("CacheMgt[");
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
		catch (final Exception e)
		{
			// log but don't fail
			log.warn("Error while reseting {}. Ignored.", cacheInstance, e);
			return 0;
		}
	}

	/**
	 * Adds an listener which will be fired when the cache for given table is about to be reset.
	 *
	 * @param tableName
	 * @param cacheResetListener
	 */
	public void addCacheResetListener(@NonNull final String tableName, @NonNull final ICacheResetListener cacheResetListener)
	{
		final Boolean registerWeak = Boolean.FALSE;
		register(CacheResetListener2CacheInterface.of(tableName, cacheResetListener), registerWeak);
	}

	public void addCacheResetListener(@NonNull final ICacheResetListener cacheResetListener)
	{
		globalCacheResetListeners.addIfAbsent(cacheResetListener);
	}

	@Value
	private static final class CacheResetListener2CacheInterface implements ITableAwareCacheInterface
	{
		public static final CacheResetListener2CacheInterface of(@NonNull final String tableName, @NonNull final ICacheResetListener listener)
		{
			return new CacheResetListener2CacheInterface(tableName, listener);
		}

		private final String tableName;
		private final ICacheResetListener listener;

		private CacheResetListener2CacheInterface(@NonNull final String tableName, @NonNull final ICacheResetListener listener)
		{
			Check.assumeNotEmpty(tableName, "tableName not empty");
			this.tableName = tableName;
			this.listener = listener;
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
			return listener.reset(CacheInvalidateMultiRequest.allRecordsForTable(tableName));
		}

		@Override
		public int resetForRecordId(final String tableName, final int recordId)
		{
			return listener.reset(CacheInvalidateMultiRequest.fromTableNameAndRecordId(tableName, recordId));
		}
	}

	/** Collects records that needs to be removed from cache when a given transaction is committed */
	private static final class RecordsToResetOnTrxCommitCollector
	{
		/** Gets/creates the records collector which needs to be reset when transaction is committed */
		public static final RecordsToResetOnTrxCommitCollector getCreate(final ITrx trx)
		{
			return trx.getProperty(TRX_PROPERTY, () -> {

				final RecordsToResetOnTrxCommitCollector collector = new RecordsToResetOnTrxCommitCollector();

				// Listens {@link ITrx}'s after-commit and fires enqueued cache invalidation requests
				trx.getTrxListenerManager()
						.newEventListener(TrxEventTiming.AFTER_COMMIT)
						.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
						.registerHandlingMethod(innerTrx -> {

							final RecordsToResetOnTrxCommitCollector innerCollector = innerTrx.getProperty(TRX_PROPERTY);
							if (innerCollector == null)
							{
								return;
							}
							innerCollector.sendRequestsAndClear();
						});

				return collector;
			});
		}

		private static final String TRX_PROPERTY = RecordsToResetOnTrxCommitCollector.class.getName();

		private final Map<CacheInvalidateRequest, ResetMode> request2resetMode = Maps.newConcurrentMap();

		/** Enqueues a record */
		public final void addRecord(@NonNull final CacheInvalidateRequest request, @NonNull final ResetMode resetMode)
		{
			request2resetMode.put(request, resetMode);
			log.debug("Scheduled cache invalidation on transaction commit: {} ({})", request, resetMode);
		}

		/** Reset the cache for all enqueued records */
		private void sendRequestsAndClear()
		{
			if (request2resetMode.isEmpty())
			{
				return;
			}

			final CacheMgt cacheMgt = CacheMgt.get();

			final ImmutableList.Builder<CacheInvalidateRequest> resetLocalRequestsBuilder = ImmutableList.builder();
			final ImmutableList.Builder<CacheInvalidateRequest> broadcastRequestsBuilder = ImmutableList.builder();
			request2resetMode.forEach((request, resetMode) -> {
				if (resetMode.isResetLocal())
				{
					resetLocalRequestsBuilder.add(request);
				}
				if (resetMode.isBroadcast())
				{
					broadcastRequestsBuilder.add(request);
				}
			});

			final ImmutableList<CacheInvalidateRequest> resetLocalRequests = resetLocalRequestsBuilder.build();
			if (!resetLocalRequests.isEmpty())
			{
				cacheMgt.reset(CacheInvalidateMultiRequest.of(resetLocalRequests), ResetMode.LOCAL);
			}

			final ImmutableList<CacheInvalidateRequest> broadcastRequests = broadcastRequestsBuilder.build();
			if (!broadcastRequests.isEmpty())
			{
				cacheMgt.reset(CacheInvalidateMultiRequest.of(broadcastRequests), ResetMode.JUST_BROADCAST);
			}

			request2resetMode.clear();
		}
	}
}
