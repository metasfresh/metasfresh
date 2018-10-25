package de.metas.cache.model.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.NullTrxPlaceholder;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.IDCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.interceptor.CacheCtxParamDescriptor;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.IModelCacheService;
import de.metas.cache.model.IMutableTableCacheConfig;
import de.metas.cache.model.ITableCacheConfig;
import de.metas.cache.model.ITableCacheConfigBuilder;
import de.metas.cache.model.ITableCacheStatisticsCollector;
import de.metas.cache.model.ITableCacheConfig.TrxLevel;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

public class ModelCacheService implements IModelCacheService
{
	private static final transient Logger logger = LogManager.getLogger(ModelCacheService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final LoadingCache<ITrx, TrxCacheMap> _cacheMapsByTrx = CacheBuilder.newBuilder()
			.weakKeys()
			.build(new CacheLoader<ITrx, TrxCacheMap>()
			{
				@Override
				public TrxCacheMap load(final ITrx trx)
				{
					return new TrxCacheMap(trx);
				}
			});
	private final ReentrantLock lock = new ReentrantLock();

	private final ConcurrentHashMap<String, ITableCacheConfig> tableName2cacheConfig = new ConcurrentHashMap<>();

	private final ITableCacheStatisticsCollector statisticsCollector;

	public ModelCacheService()
	{
		statisticsCollector = new TableCacheStatisticsCollector(getClass().getSimpleName());

		CacheMgt.get().addCacheResetListener(this::onCacheResetRequest);
	}

	@Override
	public ITableCacheConfigBuilder createTableCacheConfigBuilder(final String tableName)
	{
		return new TableCacheConfigBuilder(this, tableName);
	}

	@Override
	public ITableCacheConfigBuilder createTableCacheConfigBuilder(final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		return createTableCacheConfigBuilder(tableName);
	}

	private final ITableCacheConfig getTableCacheConfig(final String tableName)
	{
		//
		// If statistics are enabled, then create a dummy cache config (disabled) if there is no cache config
		// because we want to record Cache Misses statistics
		final boolean statisticsEnabled = statisticsCollector.isEnabled();
		if (statisticsEnabled)
		{
			final IMutableTableCacheConfig cacheConfigDisabled = new MutableTableCacheConfig(tableName);
			cacheConfigDisabled.setEnabled(false);
			cacheConfigDisabled.setTrxLevel(TrxLevel.All);

			final ITableCacheConfig cacheConfigOld = tableName2cacheConfig.putIfAbsent(tableName, cacheConfigDisabled);
			if (cacheConfigOld != null)
			{
				// there was an already existing cache config, return it
				return cacheConfigOld;
			}
			else
			{
				// return our diabled cache config
				return cacheConfigDisabled;
			}
		}

		final ITableCacheConfig cacheConfig = tableName2cacheConfig.get(tableName);
		if (cacheConfig == null)
		{
			return null;
		}

		if (!cacheConfig.isEnabled())
		{
			return null;
		}

		return cacheConfig;
	}

	@Override
	public final void addTableCacheConfig(final Class<?> modelClass)
	{
		final ITableCacheConfig cacheConfig = createDefaultTableCacheConfig(modelClass);
		final boolean override = true;
		addTableCacheConfig(cacheConfig, override);
	}

	@Override
	public void addTableCacheConfigIfAbsent(final Class<?> modelClass)
	{
		final ITableCacheConfig cacheConfig = createDefaultTableCacheConfig(modelClass);
		final boolean override = false;
		addTableCacheConfig(cacheConfig, override);
	}

	@Override
	public ITableCacheConfig createDefaultTableCacheConfig(final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		final IMutableTableCacheConfig cacheConfig = new MutableTableCacheConfig(tableName);
		cacheConfig.setEnabled(true);
		cacheConfig.setTrxLevel(TrxLevel.All);
		return cacheConfig;
	}

	protected final void addTableCacheConfig(final ITableCacheConfig cacheConfig, final boolean override)
	{
		// TODO: allow caching config only for tables with single primary key

		tableName2cacheConfig.compute(cacheConfig.getTableName(), (tableName, previousConfig) -> {
			if (previousConfig == null)
			{
				logger.info("Registering new table cache config: {}", cacheConfig);
				return cacheConfig;
			}
			else if (override)
			{
				logger.info("Registering table cache config: {} \nPrevious table caching config: {}", cacheConfig, previousConfig);
				return cacheConfig;
			}
			else
			{
				logger.info("Skip registering table cache config: {} \nPreserving current table cache config: {}", cacheConfig, previousConfig);
				return previousConfig;
			}
		});
	}

	@Override
	public final PO retrieveObject(final Properties ctx, final String tableName, final int recordId, final String trxName)
	{
		//
		// Get cache configuration
		// No cache config => we don't do caching
		// NOTE: we are not checking for cacheConfig.isEnabled because we want to record the statistics
		final ITableCacheConfig cacheConfig = getTableCacheConfig(tableName);
		if (cacheConfig == null)
		{
			return null;
		}

		//
		// Get the right transaction
		final ITrx trx = trxManager.getTrxOrNull(trxName);
		final boolean inTransaction = trx != null;
		// Check if we got the right transaction
		if (trx == null && !trxManager.isNull(trxName) && !Util.same(trxName, ITrx.TRXNAME_ThreadInherited))
		{
			final TrxException ex = new TrxException("No transaction was found for " + trxName + ". Skip cache."
					+ "\ntableName=" + tableName
					+ "\nrecordId=" + recordId
					+ "\ntrxName=" + trxName
					+ "\ntrx=" + trx
					+ "\nThread TrxName=" + trxManager.getThreadInheritedTrxName()
					+ "\nActive transactions: " + trxManager.getActiveTransactionsList()
					+ "\nClosed transactions: " + trxManager.getDebugClosedTransactions());
			logger.warn(ex.getLocalizedMessage(), ex);

			// return null (not found)
			return null;
		}

		lock.lock();
		try
		{
			//
			// Search cache on transaction level
			// (at this point "trx" can be an actual transaction or out-of-transaction=None)
			PO poToReturn = retrieveObjectFromTrx(cacheConfig, ctx, tableName, recordId, trx);

			//
			// If nothing found and we are not out-of-transaction
			// then try searching on out-of-transaction level and if something found clone it and return it
			if (poToReturn == null && inTransaction)
			{
				final PO poOutOfTrx = retrieveObjectFromTrx(cacheConfig, ctx, tableName, recordId, ITrx.TRX_None);
				if (poOutOfTrx != null)
				{
					final PO poOutOfTrxCopy = copyPO(poOutOfTrx, trxName);
					if (poOutOfTrxCopy != null)
					{
						addToCache(poOutOfTrxCopy);
					}
					poToReturn = poOutOfTrxCopy;
				}
			}

			//
			// Update statistics
			statisticsCollector.record(cacheConfig, inTransaction, poToReturn);
			// Logging
			if (logger.isTraceEnabled())
			{
				logger.trace("Cache {} (inTrx={}) - tableName/recordId={}/{}", poToReturn != null ? "HIT" : "MISS", inTransaction, tableName, recordId);
			}

			// each caller gets their own copy because in case they loaded the PO from database, they would also have gotten an instance of their own.
			return copyPO(poToReturn, trxName);
		}
		finally
		{
			lock.unlock();
		}
	}

	/**
	 * Creates a copy of orginal PO, having given <code>trxName</code>.
	 *
	 * @param originalPO
	 * @param trxName
	 * @return
	 *         <ul>
	 *         <li>copy of <code>originalPO</code>;
	 *         <li>if original PO is null, null will be returned;
	 *         <li>if there is an error while cloning given PO, null will be returned but an warning will be logged (just to not stop the current execution)
	 *         </ul>
	 */
	private static final PO copyPO(final PO originalPO, final String trxName)
	{
		if (originalPO == null)
		{
			return null;
		}

		PO poToReturn;
		try
		{
			final PO poNoTrxCopy = originalPO.copy();
			poNoTrxCopy.set_TrxName(trxName);
			poToReturn = poNoTrxCopy;
		}
		catch (final Exception e)
		{
			logger.warn("Cannot create a copy of " + originalPO + ". Returning null.", e);
			poToReturn = null;
		}
		return poToReturn;
	}

	private TrxCacheMap getTrxCacheMapIfPresent(final ITrx trx)
	{
		return _cacheMapsByTrx.getIfPresent(NullTrxPlaceholder.boxNotNull(trx));
	}

	private TrxCacheMap getTrxCacheMap(final ITrx trx)
	{
		try
		{
			return _cacheMapsByTrx.get(NullTrxPlaceholder.boxNotNull(trx));
		}
		catch (final ExecutionException ex)
		{
			// shall not happen
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private final PO retrieveObjectFromTrx(final ITableCacheConfig cacheConfig, final Properties ctx, final String tableName, final int recordId, final ITrx trx)
	{
		//
		// Check if we can cache on this transaction level?
		if (!isTrxLevelEnabled(cacheConfig, trx))
		{
			return null;
		}

		final TrxCacheMap trxCacheMap = getTrxCacheMapIfPresent(trx);
		if (trxCacheMap == null)
		{
			return null;
		}

		final CCache<Object, PO> cache = trxCacheMap.getByTableNameIfExists(tableName);
		if (cache == null)
		{
			return null;
		}

		PO poCached = cache.get(recordId);
		if (poCached == null)
		{
			return null;
		}

		//
		// Check if our cached object is expired (i.e. trxName changed etc)
		if (isExpired(ctx, tableName, recordId, trx, poCached))
		{
			cache.remove(recordId);
			logger.trace("Removed {} from model cache because it's expired (trx={})", poCached, trx);
			poCached = null;
		}

		return poCached;
	}

	private final boolean isTrxLevelEnabled(final ITableCacheConfig cacheConfig, final ITrx trx)
	{
		// If caching is not enabled in cache config, simply return false
		if (!cacheConfig.isEnabled())
		{
			return false;
		}

		// If transaction is not active, we shall not use the caching for it.
		// NOTE: We are not deleting it (let it expire on WeakHashMap).
		if (trx != null && !trx.isActive())
		{
			return false;
		}

		final boolean isInTransaction = !trxManager.isNull(trx);
		final TrxLevel trxLevel = cacheConfig.getTrxLevel();
		if (isInTransaction)
		{
			return trxLevel == TrxLevel.All || trxLevel == TrxLevel.InTransactionOnly;
		}
		else
		{
			return trxLevel == TrxLevel.All || trxLevel == TrxLevel.OutOfTransactionOnly;
		}
	}

	/**
	 *
	 * @param ctx
	 * @param tableName
	 * @param recordId
	 * @param trxName
	 * @param po
	 * @return true if given PO is expired
	 */
	private boolean isExpired(final Properties ctx, final String tableName, final int recordId, final ITrx trx, final PO po)
	{
		// Check if our cached PO was deleted
		final int poRecordId = po.get_ID();
		if (poRecordId < 0)
		{
			return true;
		}

		// Check if PO has the same trxName as we required
		final String poTrxName = po.get_TrxName();
		if (!trxManager.isSameTrxName(trx, poTrxName))
		{
			return true;
		}

		// Check if PO has the same ID as we required
		if (recordId != poRecordId)
		{
			return true;
		}

		// Check if PO has the same Context as we required
		final Properties poCtx = po.getCtx();
		if (!CacheCtxParamDescriptor.isSameCtx(poCtx, ctx)) // gh #1036
		{
			return true;
		}

		//
		// We assume our PO is not expired
		return false;
	}

	@Override
	public void addToCache(@Nullable final PO po)
	{
		// Don't cache null objects
		if (po == null)
		{
			return;
		}

		// Don't cache new objects
		if (po.is_new())
		{
			return;
		}

		//
		// Do we cache for this model's table?
		final String tableName = po.get_TableName();
		final ITableCacheConfig cacheConfig = getTableCacheConfig(tableName);
		// No cache config => we don't do caching
		if (cacheConfig == null || !cacheConfig.isEnabled())
		{
			return;
		}

		//
		// Do we cache for model's transaction?
		final String trxName = po.get_TrxName();
		final ITrx trx = trxManager.getTrxOrNull(trxName);
		if (!isTrxLevelEnabled(cacheConfig, trx))
		{
			return;
		}

		lock.lock();
		try
		{
			getTrxCacheMap(trx).put(po, cacheConfig);

			//
			// Our model's transaction is not null (i.e. it's in transaction)
			// and for this table out-of-transaction caching is also enabled
			// TODO: shall we do this only for "Master Data" tables?
			// => create a copy in out-of-transaction cache too
			final boolean inTransaction = trxManager.getTrxOrNull(trxName) != null;
			if (inTransaction && isTrxLevelEnabled(cacheConfig, ITrx.TRX_None))
			{
				final PO poOutOfTrxCopy = copyPO(po, ITrx.TRXNAME_None);
				if (poOutOfTrxCopy != null)
				{
					getTrxCacheMap(ITrx.TRX_None).put(poOutOfTrxCopy, cacheConfig);
				}
			}
		}
		finally
		{
			lock.unlock();
		}
	}

	@Override
	public void invalidate(@NonNull final CacheInvalidateMultiRequest request)
	{
		final ITrx trx = trxManager.getTrxOrNull(ITrx.TRXNAME_ThreadInherited);

		lock.lock();
		try
		{
			for (final CacheInvalidateRequest singleRequest : request.getRequests())
			{
				if (singleRequest.isAll())
				{
					invalidateAll(trx);
					break;
				}
				else if (singleRequest.isAllRecords())
				{
					invalidateTable(singleRequest.getTableNameEffective(), trx);
				}
				else
				{
					final TableRecordReference record = singleRequest.getRecordEffective();
					invalidateRecord(record, trx);
				}
			}
		}
		finally
		{
			lock.unlock();
		}
	}

	private void invalidateAll(@Nullable final ITrx trx)
	{
		final TrxCacheMap trxCacheMap = getTrxCacheMapIfPresent(trx);
		if (trxCacheMap == null)
		{
			return;
		}
		trxCacheMap.invalidateAll();
	}

	private void invalidateTable(@NonNull final String tableName, @Nullable final ITrx trx)
	{
		final TrxCacheMap trxCacheMap = getTrxCacheMapIfPresent(trx);
		if (trxCacheMap == null)
		{
			return;
		}

		trxCacheMap.invalidateByTableName(tableName);
	}

	private void invalidateRecord(@NonNull final TableRecordReference recordRef, @Nullable final ITrx trx)
	{
		final TrxCacheMap trxCacheMap = getTrxCacheMapIfPresent(trx);
		if (trxCacheMap == null)
		{
			return;
		}

		trxCacheMap.invalidateByRecord(recordRef);
	}

	/**
	 * Full cache reset
	 */
	private int onCacheResetRequest(final CacheInvalidateMultiRequest multiRequest)
	{
		if (!multiRequest.isResetAll())
		{
			return 0;
		}

		logger.debug("Clearing all cache instances of {}", this);

		final ImmutableList<TrxCacheMap> cacheMapsToInvalidate;

		lock.lock();
		try
		{
			cacheMapsToInvalidate = ImmutableList.copyOf(_cacheMapsByTrx.asMap().values());

			// Full reset of our caching map
			_cacheMapsByTrx.invalidateAll();
			_cacheMapsByTrx.cleanUp();
		}
		finally
		{
			lock.unlock();
		}

		cacheMapsToInvalidate.forEach(TrxCacheMap::invalidateAll);

		return 1;
	}

	private static class TrxCacheMap
	{
		private final String trxName;

		// TODO consider using guava cache
		private final HashMap<String, IDCache<PO>> cachesByTableName = new HashMap<>(50);

		public TrxCacheMap(final ITrx trx)
		{
			trxName = NullTrxPlaceholder.unboxToNull(trx) != null ? trx.getTrxName() : ITrx.TRXNAME_None;
		}

		public IDCache<PO> getByTableNameIfExists(final String tableName)
		{
			return cachesByTableName.get(tableName);
		}

		public IDCache<PO> getByTableNameOrCreate(final ITableCacheConfig cacheConfig)
		{
			final String tableName = cacheConfig.getTableName();
			return cachesByTableName.computeIfAbsent(tableName, k -> create(cacheConfig));
		}

		private IDCache<PO> create(final ITableCacheConfig cacheConfig)
		{
			final String tableName = cacheConfig.getTableName();
			final int expireMinutes = cacheConfig.getExpireMinutes();
			final CacheMapType cacheMapType = cacheConfig.getCacheMapType();
			final int initialCapacity = cacheConfig.getInitialCapacity();
			int maxCapacity = cacheConfig.getMaxCapacity();
			if (maxCapacity <= 0)
			{
				maxCapacity = initialCapacity;
			}
			return new IDCache<>(tableName, trxName, maxCapacity, expireMinutes, cacheMapType);
		}

		public void invalidateAll()
		{
			cachesByTableName.values().forEach(IDCache::reset);
			cachesByTableName.clear();

			if (logger.isTraceEnabled())
			{
				logger.trace("Cleared all caches for trx={} ", trxName);
			}
		}

		public void invalidateByTableName(final String tableName)
		{
			final IDCache<PO> cache = getByTableNameIfExists(tableName);
			if (cache == null)
			{
				return;
			}

			// Invalidate the cache
			cache.reset();

			// Logging
			if (logger.isTraceEnabled())
			{
				logger.trace("Cleared cache {} for tableName={}, trx={} ", cache.getCacheName(), tableName, trxName);
			}
		}

		public void invalidateByRecord(final TableRecordReference recordRef)
		{
			final String tableName = recordRef.getTableName();
			final int recordId = recordRef.getRecord_ID();

			//
			// Get table's cache map.
			final IDCache<PO> cache = getByTableNameIfExists(tableName);
			if (cache == null)
			{
				return;
			}

			// Invalidate the cache
			final boolean invalidated = cache.remove(recordId) != null;

			// Logging
			if (invalidated && logger.isTraceEnabled())
			{
				logger.trace("Model removed from cache {}: record={}/{}, trx={} ", cache.getCacheName(), tableName, recordId, trxName);
			}
		}

		public void put(@NonNull final PO po, @NonNull final ITableCacheConfig cacheConfig)
		{
			//
			// Get table's cache map.
			// If does not exist, create one.
			final IDCache<PO> cache = getByTableNameOrCreate(cacheConfig);

			//
			// Add our PO to cache
			final int recordId = po.get_ID();
			cache.put(recordId, po);

			// Logging
			if (logger.isTraceEnabled())
			{
				logger.trace("Model added to cache {}: {} ", cache.getCacheName(), po);
			}
		}
	}
}
