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

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.CacheStats;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * Adempiere Cache.
 *
 * @param <K> Key
 * @param <V> Value
 *
 * @author Jorg Janke
 * @version $Id: CCache.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public class CCache<K, V> implements ITableAwareCacheInterface
{
	/**
	 * Creates a new LRU cache
	 * 
	 * @param cacheName cache name (shall respect the current naming conventions)
	 * @param maxSize LRU cache maximum size
	 * @param expireAfterMinutes if positive, the entries will expire after given number of minutes
	 * @return new cache instance
	 */
	public static final <K, V> CCache<K, V> newLRUCache(final String cacheName, final int maxSize, final int expireAfterMinutes)
	{
		final String tableName = extractTableNameForCacheName(cacheName);
		return new CCache<>(cacheName //
				, tableName //
				, maxSize // initialCapacity // FIXME this is confusing because in case of LRU, initialCapacity is used as maxSize
				, expireAfterMinutes //
				, CacheMapType.LRU //
		);
	}
	
	public static final <K, V> CCache<K, V> newCache(final String cacheName, final int initialCapacity, final int expireAfterMinutes)
	{
		final String tableName = extractTableNameForCacheName(cacheName);
		return new CCache<>(cacheName //
				, tableName //
				, initialCapacity
				, expireAfterMinutes //
				, CacheMapType.HashMap //
		);
	}

	public static enum CacheMapType
	{
		/**
		 * Data is cached in a normal hash map
		 */
		HashMap,

		/**
		 * Data is cached in a LRU map (least recently used). This means that if the caches size limit is reached, then the oldest record is removed from cache in order to add a new record.
		 * This means that we can have a have a cache with a defined (limited) size without any expiration time.
		 */
		LRU,
	}

	/**
	 * If active, following informations will be stored:
	 * <ul>
	 * <li>{@link #debugId} - unique JVM object ID
	 * <li>{@link #debugAquireStacktrace} - stack trace for when the cache object was instantiated
	 * </ul>
	 */
	private static final boolean DEBUG = false;

	/**
	 * Adempiere Cache - expires after 2 hours
	 * 
	 * @param name (table) name of the cache
	 * @param initialCapacity initial capacity
	 */
	public CCache(String name, int initialCapacity)
	{
		this(name, initialCapacity, 120);
	}	// CCache

	/**
	 * Adempiere Cache
	 * 
	 * @param name (table) name of the cache
	 * @param initialCapacity initial capacity
	 * @param expireMinutes expire after minutes (0=no expire)
	 */
	public CCache(String name, int initialCapacity, int expireMinutes)
	{
		this(name, // cache name
				extractTableNameForCacheName(name), // tableName
				initialCapacity,
				expireMinutes);
	}

	protected CCache(
			final String name,
			final String tableName,
			final int initialCapacity,
			final int expireMinutes)
	{
		this(name, tableName, initialCapacity, expireMinutes, CacheMapType.HashMap);
	}

	protected CCache(
			final String name,
			final String tableName,
			final int initialCapacity,
			final int expireMinutes,
			final CacheMapType cacheMapType)
	{
		super();
		this.cacheId = NEXT_CACHE_ID.getAndIncrement();
		this.initialCapacity = initialCapacity;
		this.m_name = name;
		this.m_tableName = tableName;
		this.expireMinutes = expireMinutes;
		this.cacheMapType = cacheMapType;
		this.cache = buildCache();

		if (DEBUG)
		{
			final Exception ex = new Exception("Aquire stack trace");
			this.debugAquireStacktrace = Util.dumpStackTraceToString(ex);
			this.debugId = Integer.toHexString(System.identityHashCode(this));
		}
		else
		{
			this.debugAquireStacktrace = null; // N/A
			this.debugId = null; // N/A
		}

		//
		// Register it to CacheMgt
		CacheMgt.get().register(this);
	}	// CCache

	/**
	 * Extracts TableName from given <code>cacheName</code>.
	 * 
	 * NOTE: we assume cacheName has following format: TableName#by#ColumnName1#ColumnName2...
	 * 
	 * @param cacheName
	 * @return tableName or null
	 */
	// NOTE: public for testing
	public static final String extractTableNameForCacheName(final String cacheName)
	{
		if (cacheName == null || cacheName.isEmpty())
		{
			return null;
		}
		final int idx = cacheName.indexOf("#");
		if (idx <= 0)
		{
			return null;
		}

		final String tableName = cacheName.substring(0, idx).trim();
		if (tableName.isEmpty())
		{
			return null;
		}

		return tableName;
	}

	// private static final transient Logger logger = CLogMgt.getLogger(CCache.class);

	private final CacheMapType cacheMapType;
	/** Internal map that is used as cache */
	private final Cache<K, V> cache;

	private static final AtomicLong NEXT_CACHE_ID = new AtomicLong(1);
	/** unique cache ID, mainly used for tracking, logging and debugging */
	private final long cacheId;
	
	/** Name */
	private final String m_name;
	private final String m_tableName;
	private final int initialCapacity;
	/** Expire after minutes */
	private final int expireMinutes;
	public static final int EXPIREMINUTES_Never = 0;
	/** Just reset */
	private boolean m_justReset = true;

	/**
	 * If {@link #DEBUG} is enabled, this variable contains the object's identity code (see {@link System#identityHashCode(Object)}).
	 */
	private final String debugId;

	/**
	 * If {@link #DEBUG} is enabled, this variable containts the constructor's stack trace (when this object was created)
	 */
	private final String debugAquireStacktrace;

	private final Cache<K, V> buildCache()
	{
		Check.assumeNotNull(cacheMapType, "cacheMapType not null");

		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
		if (cacheMapType == CacheMapType.HashMap)
		{
			cacheBuilder = cacheBuilder
					.initialCapacity(initialCapacity);
		}
		else if (cacheMapType == CacheMapType.LRU)
		{
			cacheBuilder = cacheBuilder
					.maximumSize(initialCapacity); // FIXME: this is confusing
		}
		else
		{
			throw new AdempiereException("Unknown CacheMapType: " + cacheMapType);
		}

		if (expireMinutes > 0)
		{
			cacheBuilder = cacheBuilder.expireAfterWrite(expireMinutes, TimeUnit.MINUTES);
		}

		return cacheBuilder.build();
	}

	/**
	 * @return unique cache ID
	 */
	public final long getCacheId()
	{
		return cacheId;
	}

	@Override
	public final String getName()
	{
		return m_name;
	}	// getName

	@Override
	public final String getTableName()
	{
		return m_tableName;
	}

	/**
	 * Cache was just reset.
	 * 
	 * NOTE:
	 * <ul>
	 * <li>this flag is set to <code>false</code> after any change operation (e.g. {@link #put(Object, Object)})
	 * <li>this flag is set to <code>false</code> programatically by calling {@link #setUsed()}
	 * <li>this flag is set to <code>true</code> after {@link #reset()}
	 *
	 * @return true if it was just reset
	 */
	public final boolean isReset()
	{
		return m_justReset;
	}	// isReset

	/**
	 * Resets the Reset flag
	 * 
	 * @see #isReset()
	 */
	public final void setUsed()
	{
		m_justReset = false;
	}	// setUsed

	/**
	 * Reset Cache.
	 * 
	 * It is the same as calling {@link #clear()} but this method will return how many items were cleared.
	 * 
	 * @return number of items cleared
	 * @see org.compiere.util.CacheInterface#reset()
	 */
	@Override
	public int reset()
	{
		final int no = (int)cache.size();
		clear();
		return no;
	}	// reset

	@Override
	public int resetForRecordId(final String tableName, final Object key)
	{
		if (tableName == null)
		{
			return reset();
		}
		
		//
		// Try matching by cache's TableName (if any)
		final String cacheTableName = getTableName();
		if (cacheTableName != null && !cacheTableName.isEmpty() && !tableName.equals(cacheTableName))
		{
			return 0;
		}

		//
		// Try matching by cache's name
		final String cacheName = getName();
		if (cacheName == null || !cacheName.startsWith(tableName))
		{
			return 0;
		}

		// NOTE: reseting only by "key" is not supported at this level, so we are reseting everything
		final int count = reset();
		return count;
	}

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("CCache[");
		sb.append(m_name)
				.append(", id=").append(cacheId)
				.append(", Exp=").append(expireMinutes)
				.append(", #").append(cache.size());

		if (DEBUG)
		{
			sb.append("\ncacheId=").append(debugId);
			sb.append("\n").append(this.debugAquireStacktrace);
		}

		sb.append("]");

		return sb.toString();
	}	// toString

	/**
	 * Clear cache
	 */
	public void clear()
	{
		// Clear
		cache.invalidateAll();
		cache.cleanUp();

		m_justReset = true;
	}	// clear

	/**
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(final K key)
	{
		return cache.getIfPresent(key) != null;
	}	// containsKey

	public V remove(final K key)
	{
		final V value = cache.getIfPresent(key);
		cache.invalidate(key);
		return value;
	}

	/**
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public V get(final K key)
	{
		return cache.getIfPresent(key);
	}	// get

	/**
	 * Gets cached value by <code>key</code>.
	 * 
	 * For more informations, see {@link #get(Object, Callable)}.
	 * 
	 * @param key
	 * @param valueInitializer
	 * @return cached value
	 */
	public V get(final K key, final Supplier<V> valueInitializer)
	{
		if (valueInitializer == null)
		{
			return cache.getIfPresent(key);
		}

		return get(key, new Callable<V>(){
			@Override
			public String toString()
			{
				return "Callable[" + valueInitializer + "]";
			}

			@Override
			public V call() throws Exception
			{
				return valueInitializer.get();
			}});
	}
	
	/**
	 * Gets cached value by <code>key</code>.
	 * 
	 * If value is not present in case it will try to initialize it by using <code>valueInitializer</code>.
	 * 
	 * If the <code>valueInitializer</code> returns null then this method will return <code>null</code> and the value will NOT be cached. 
	 * 
	 * @param key
	 * @param valueInitializer optional cache initializer.
	 * @return cached value or <code>null</code>
	 */
	public V get(final K key, final Callable<V> valueInitializer)
	{
		if (valueInitializer == null)
		{
			return cache.getIfPresent(key);
		}

		try
		{
			return cache.get(key, valueInitializer);
		}
		catch (InvalidCacheLoadException e)
		{
			// Exception thrown when the Callable returns null
			// We can safely ignore it and return null.
			// The value was not cached.
			return null;
		}
		catch (ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		catch (UncheckedExecutionException e)
		{
			throw (RuntimeException)e.getCause();
		}
		catch (ExecutionError e)
		{
			throw (Error)e.getCause();
		}
	}

	/**
	 * Same as {@link #get(Object, Callable)}. Introduced here to be able to use it with lambdas, without having ambiguous method calls.
	 * 
	 * @param key
	 * @param valueLoader
	 * @return
	 * @see #get(Object, Callable).
	 * @see #get(Object, Supplier)
	 */
	public V getOrLoad(final K key, final Callable<V> valueLoader)
	{
		return get(key, valueLoader);
	}

	/**
     * Return the value, if present, otherwise throw an exception to be created by the provided supplier.
     * 
	 * @param key
	 * @param exceptionSupplier
	 * @return value; not null
	 * @throws E
	 */
	public <E extends Throwable> V getOrElseThrow(final K key, Supplier<? extends E> exceptionSupplier) throws E
	{
		final V value = get(key);
		if(value == null)
		{
			throw exceptionSupplier.get();
		}
		return value;
	}

	/**
	 * Put value
	 *
	 * @param key key
	 * @param value value
	 * @return previous value
	 */
	public void put(final K key, final V value)
	{
		m_justReset = false;
		if (value == null)
		{
			cache.invalidate(key);
		}
		else
		{
			cache.put(key, value);
		}
	}	// put
	
	/**
	 * Add all key/value pairs to this cache.
	 * 
	 * @param map key/value pairs
	 */
	public void putAll(Map<? extends K,? extends V> map)
	{
		cache.putAll(map);
	}
	
	/**
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty()
	{
		return cache.size() == 0;
	}	// isEmpty

	/**
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet()
	{
		return cache.asMap().keySet();
	}	// keySet

	/**
	 * @see java.util.Map#size()
	 */
	@Override
	public int size()
	{
		return (int)cache.size();
	}	// size

	/**
	 * @see java.util.Map#values()
	 */
	public Collection<V> values()
	{
		return cache.asMap().values();
	}	// values

	@Override
	protected final void finalize() throws Throwable
	{
		// NOTE: to avoid memory leaks we need to programatically clear our internal state

		if (cache != null)
		{
			cache.invalidateAll();
		}
	}
	
	/**
	 * Adds an additional table which when the cache is reset for that one, it also shall reset this cache.
	 * 
	 * @param tableName
	 * @return this
	 */
	public CCache<K, V> addResetForTableName(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		CacheMgt.get().addCacheResetListener(tableName, new ICacheResetListener()
		{
			@Override
			public int reset(final String tableNameToReset, final Object key)
			{
				if (tableName != null && !Check.equals(tableName, tableNameToReset))
				{
					return 0;
				}
				
				return CCache.this.reset();
			}
			
			@Override
			public String toString()
			{
				return "->" + CCache.this.toString();
			}
		});
		
		return this;
	}
	
	/**
	 * @return cache statistics
	 */
	public CCacheStats stats()
	{
		return new CCacheStats(cacheId, m_name, cache.size(), cache.stats());
	}
	
	
	@SuppressWarnings("serial")
	public static final class CCacheStats implements Serializable
	{
		// NOTE: must be Json serializable!!!
		
		private final long cacheId;
		private final String name;
		private final long size;
		private final CacheStats guavaStats;

		private CCacheStats(final long cacheId, final String name, final long size, CacheStats guavaStats)
		{
			super();
			this.cacheId = cacheId;
			this.name = name;
			this.size = size;
			this.guavaStats = guavaStats;
		}
		
		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("name", name)
					.add("size", size)
					.add("guavaStats", guavaStats)
					.add("cacheId", cacheId)
					.toString();
		}
		
		@Override
		public int hashCode()
		{
			return Objects.hash(cacheId, name, size, guavaStats);
		}
		
		@Override
		public boolean equals(final Object obj)
		{
			if (obj instanceof CCacheStats)
			{
				final CCacheStats other = (CCacheStats)obj;
				return cacheId == other.cacheId
						&& name.equals(other.name)
						&& size == other.size
						&& guavaStats.equals(other.guavaStats);
			}
			return false;
		}
		
		public long getCacheId()
		{
			return cacheId;
		}
		
		public String getName()
		{
			return name;
		}
		
		public long getSize()
		{
			return size;
		}
		
		public CacheStats getGuavaStats()
		{
			return guavaStats;
		}
	}
}	// CCache
