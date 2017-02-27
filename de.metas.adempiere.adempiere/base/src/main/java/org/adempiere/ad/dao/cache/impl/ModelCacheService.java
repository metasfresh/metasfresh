package org.adempiere.ad.dao.cache.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.dao.cache.IMutableTableCacheConfig;
import org.adempiere.ad.dao.cache.ITableCacheConfig;
import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.adempiere.ad.dao.cache.ITableCacheConfigBuilder;
import org.adempiere.ad.dao.cache.ITableCacheStatisticsCollector;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.util.CCache;
import org.compiere.util.CCache.CacheMapType;
import org.compiere.util.CacheInterface;
import org.compiere.util.CacheMgt;
import org.compiere.util.IDCache;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.util.cache.CacheCtxParamDescriptor;
import de.metas.logging.LogManager;

public class ModelCacheService implements IModelCacheService
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Cache map: ITrx to TableName to RecordId to PO
	 */
	private final WeakHashMap<ITrx, Map<String, CCache<Object, PO>>> trx2tableName2cache = new WeakHashMap<ITrx, Map<String, CCache<Object, PO>>>();
	private final ReentrantLock lock = new ReentrantLock();

	private final ConcurrentHashMap<String, ITableCacheConfig> tableName2cacheConfig = new ConcurrentHashMap<String, ITableCacheConfig>();

	private final ITableCacheStatisticsCollector statisticsCollector;

	public ModelCacheService()
	{
		super();

		this.statisticsCollector = new TableCacheStatisticsCollector(getClass().getSimpleName());

		//
		// Cache Management Listener
		CacheMgt.get().register(new CacheInterface()
		{

			@Override
			public int size()
			{
				// NOTE: for optimization purposes, we consider always size=1
				return 1;
			}

			@Override
			public int reset()
			{
				ModelCacheService.this.reset();
				// NOTE: for optimization purposes, we consider always size=1
				return 1;
			}
		});
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
		final String tableName = cacheConfig.getTableName();
		// TODO: allow caching config only for tables with single primary key

		if (override)
		{
			tableName2cacheConfig.put(tableName, cacheConfig);
		}
		else
		{
			tableName2cacheConfig.putIfAbsent(tableName, cacheConfig);
		}
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
					+ "\nClosed transactions: " + trxManager.getDebugClosedTransactions()
					);
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
				final PO poNoTrx = retrieveObjectFromTrx(cacheConfig, ctx, tableName, recordId, ITrx.TRX_None);
				if (poNoTrx != null)
				{
					final PO poNoTrxCopy = copyPO(poNoTrx, trxName);
					if (poNoTrxCopy != null)
					{
						addToCache(poNoTrxCopy);
					}
					poToReturn = poNoTrxCopy;
				}
			}

			//
			// Update statistics
			statisticsCollector.record(cacheConfig, inTransaction, poToReturn);

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
	 * @return <ul>
	 *         <li>copy of <code>originalPO</code>;
	 *         <li>if original PO is null, null will be returned;
	 *         <li>if there is an error while cloning given PO, null will be returned but an warning will be logged (just to not stop the current execution)
	 *         </ul>
	 */
	private final PO copyPO(final PO originalPO, final String trxName)
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
		catch (Exception e)
		{
			logger.warn("Cannot create a copy of " + originalPO + ". Returning null.", e);
			poToReturn = null;
		}
		return poToReturn;
	}

	private final PO retrieveObjectFromTrx(final ITableCacheConfig cacheConfig,
			final Properties ctx,
			final String tableName,
			final int recordId,
			final ITrx trx)
	{
		//
		// Check if we can cache on this transaction level?
		if (!isTrxLevelEnabled(cacheConfig, trx))
		{
			return null;
		}

		final Map<String, CCache<Object, PO>> tableName2cache = trx2tableName2cache.get(trx);
		if (tableName2cache == null)
		{
			return null;
		}

		final CCache<Object, PO> cache = tableName2cache.get(tableName);
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
		if(!CacheCtxParamDescriptor.isSameCtx(poCtx, ctx))
		{
			return true;
		}

		//
		// We assume our PO is not expired
		return false;
	}

	@Override
	public void addToCache(final PO po)
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
			addToCache0(cacheConfig, trx, po);

			//
			// Our model's transaction is not null (i.e. it's in transaction)
			// and for this table out-of-transaction caching is also enabled
			// TODO: shall we do this only for "Master Data" tables?
			// => create a copy in out-of-transaction cache too
			final boolean inTransaction = trxManager.getTrxOrNull(trxName) != null;
			if (inTransaction
					&& isTrxLevelEnabled(cacheConfig, ITrx.TRX_None))
			{
				final PO poNoTrxCopy = copyPO(po, ITrx.TRXNAME_None);
				if (poNoTrxCopy != null)
				{
					addToCache0(cacheConfig, ITrx.TRX_None, poNoTrxCopy);
				}
			}
		}
		finally
		{
			lock.unlock();
		}
	}

	/**
	 * Actually adds given PO to cache, nothing more, i.e.
	 * <ul>
	 * <li>it's not thread safe
	 * <li>does not check if parameters are ok
	 * <li>does not check if cacheConfig is configured to not cache
	 * <li>etc
	 * </ul>
	 * All those checkings and orchestrations are done in it's caller {@link #addToCache(PO)}.
	 * 
	 * @param cacheConfig
	 * @param trx transaction on which we cache
	 * @param po
	 */
	private void addToCache0(final ITableCacheConfig cacheConfig, final ITrx trx, final PO po)
	{
		final int recordId = po.get_ID();

		//
		// Get current cache map for our transaction
		Map<String, CCache<Object, PO>> tableName2cache = trx2tableName2cache.get(trx);

		//
		// If there is no cache map for our transaction, create one and register it
		if (tableName2cache == null)
		{
			final int initialCapacity = 50;
			tableName2cache = new HashMap<String, CCache<Object, PO>>(initialCapacity)
			{
				private static final long serialVersionUID = -2110362924896836989L;

				@Override
				protected final void finalize()
				{
					// NOTE: when this object is garbage-collected we need to clear all content inside
					// If not, the CCache references won't be released so even if we have Weak references to them (in CacheMgt) those won't be collected.
					this.clear();
				}

			};
			trx2tableName2cache.put(trx, tableName2cache);
		}

		//
		// Get table's cache map.
		// If does not exist, create one.
		final String tableName = cacheConfig.getTableName();
		CCache<Object, PO> cache = tableName2cache.get(tableName);
		if (cache == null)
		{
			final int expireMinutes = cacheConfig.getExpireMinutes();
			final String trxName = trx == null ? ITrx.TRXNAME_None : trx.getTrxName();
			final CacheMapType cacheMapType = cacheConfig.getCacheMapType();
			final int initialCapacity = cacheConfig.getInitialCapacity();
			int maxCapacity = cacheConfig.getMaxCapacity();
			if (maxCapacity <= 0)
			{
				maxCapacity = initialCapacity;
			}
			cache = new IDCache<PO>(tableName, trxName, maxCapacity, expireMinutes, cacheMapType);
			tableName2cache.put(tableName, cache);
		}

		//
		// Add our PO to cache
		cache.put(recordId, po);
	}

	/**
	 * Cache Full Reset
	 */
	public void reset()
	{
		lock.lock();
		try
		{
			// Clean-up all tableName2cache maps
			for (final Map<String, CCache<Object, PO>> tableName2cache : trx2tableName2cache.values())
			{
				if (tableName2cache != null)
				{
					tableName2cache.clear();
				}
			}

			// Full reset of our caching map
			trx2tableName2cache.clear();
		}
		finally
		{
			lock.unlock();
		}
	}
}
