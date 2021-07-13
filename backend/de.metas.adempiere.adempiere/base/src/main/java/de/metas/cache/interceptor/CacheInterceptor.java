package de.metas.cache.interceptor;

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

import com.google.common.base.Supplier;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.adempiere.util.proxy.AroundInvoke;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.IInvocationContext;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public @Cached
class CacheInterceptor implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6740693287832574641L;
	
	/**
	 * Temporary disable the caching on current thread.
	 * 
	 * Please keep in mind this method will disable the cache retrieval and WILL NOT disable the cache invalidation.
	 * 
	 * @return an auto-closeable used to re-enable the caching 
	 */
	public static IAutoCloseable temporaryDisableCaching()
	{
		final Boolean cacheDisabled = THREADLOCAL_CacheDisabled.get();
		if(Boolean.TRUE.equals(cacheDisabled))
		{
			return NullAutoCloseable.instance;
		}
		
		THREADLOCAL_CacheDisabled.set(Boolean.TRUE);
		logger.debug("Caching temporary disabled for current thread");
		
		return new IAutoCloseable()
		{
			private boolean closed = false;
			@Override
			public void close()
			{
				if(closed)
				{
					return;
				}
				closed = true;

				// restore old value
				logger.debug("Restoring cache disabled flag to {}", cacheDisabled);
				THREADLOCAL_CacheDisabled.set(cacheDisabled);
			}
		};
	}
	
	/** @return true if the caching is disabled for this thread */
	public static boolean isCacheDisabled()
	{
		final Boolean cacheDisabled = THREADLOCAL_CacheDisabled.get();
		return Boolean.TRUE.equals(cacheDisabled);
	}
	
	private static final ThreadLocal<Boolean> THREADLOCAL_CacheDisabled = ThreadLocal.withInitial(() -> Boolean.FALSE);

	// services
	private static final transient Logger logger = LogManager.getLogger(CacheInterceptor.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private static final LoadingCache<Method, CachedMethodDescriptor> cachedMethodsDescriptor = CacheBuilder.newBuilder()
			.build(new CacheLoader<Method, CachedMethodDescriptor>()
			{
				@Override
				public CachedMethodDescriptor load(final Method method)
				{
					try
					{
						return new CachedMethodDescriptor(method);
					}
					catch (final Exception e)
					{
						throw CacheIntrospectionException.wrapIfNeeded(e)
								.setMethod(method);
					}
				}
			});

	private static final CacheBuilder<Object, Object> _cacheStorageBuilder = CacheBuilder.newBuilder();
	private final Cache<String, CCache<ArrayKey, Object>> _cacheStorage = _cacheStorageBuilder.build();

	private static final String TRX_PROPERTY_CacheStorage = CacheInterceptor.class.getName() + ".CacheStorage";
	private static final Supplier<Cache<String, CCache<ArrayKey, Object>>> TRX_PROPERTY_CacheStorageInitializer = () -> _cacheStorageBuilder.build();

	/**
	 * @throws Throwable could throw throwable as the cached method could also throw it.
	 */
	@AroundInvoke
	@Nullable
	public Object invokeCache(@NonNull final IInvocationContext invCtx) throws Throwable
	{
		if (logger.isTraceEnabled())
		{
			logger.trace("Entering - invCtx: " + invCtx);
		}
		
		//
		// If caching is temporary disabled, just invoke the method directly
		if (isCacheDisabled())
		{
			logger.trace("Caching is disabled for current thread. Invoking {} directly.", invCtx);
			
			// Invoke the cached method directly
			return invCtx.proceed();
		}

		//
		// Get method descriptor
		final Method method = invCtx.getMethod();
		final CachedMethodDescriptor methodDescriptor;
		try
		{
			methodDescriptor = cachedMethodsDescriptor.get(method);
		}
		catch (final Exception e)
		{
			final CacheIntrospectionException cacheEx = CacheIntrospectionException.wrapIfNeeded(e);
			if (JavaAssistInterceptor.FAIL_ON_ERROR)
			{
				throw cacheEx;
			}
			else
			{
				logger.warn("Failed introspecting cached method: " + method, cacheEx);
			}

			// Invoke the cached method directly
			return invCtx.proceed();
		}

		//
		// Build cache key
		final CacheKeyBuilder cacheKeyBuilder = methodDescriptor.createKeyBuilder(invCtx.getTarget(), invCtx.getParameters());
		if (cacheKeyBuilder.isSkipCaching())
		{
			// Invoke the cached method directly
			return invCtx.proceed();
		}
		
		//
		// Get the Cache Storage.
		// In case the cache storage could not be retrieved, we are invoking the cached method directly (by-pass the cache).
		final Cache<String, CCache<ArrayKey, Object>> cacheStorage = getCacheStorage(cacheKeyBuilder.getTrxName());
		if (cacheStorage == null)
		{
			final CacheGetException ex = new CacheGetException("Could not get the cache storage, maybe because transaction was not found"
					+ "\n TrxName: " + cacheKeyBuilder.getTrxName()
					+ "\n Method: " + method
					+ "\n Method arguments: " + Arrays.asList(invCtx.getParameters())
					+ "\n Target Object: " + invCtx.getTarget()
					+ "\n Method descriptor: " + methodDescriptor);
			logger.warn("No cache storage found. Invoking cached method directly.", ex);

			// Invoke the cached method directly
			return invCtx.proceed();
		}

		//
		// Get the method level cache container (Method's parameters key -> cached value) 
		final ArrayKey cacheKey = cacheKeyBuilder.buildKey();
		final CCache<ArrayKey, Object> methodCache = cacheStorage.get(methodDescriptor.getCacheName(), methodDescriptor.createCCacheCallable());

		//
		// Get method's cached value / update method's cached value
		final Object cacheResult;
		if (cacheKeyBuilder.isCacheReload())
		{
			cacheResult = invCtx.call();
			methodCache.put(cacheKey, cacheResult);
		}
		else
		{
			cacheResult = methodCache.get(cacheKey, invCtx);
		}

		// Unbox the NullResult and return the cached value
		return cacheResult == IInvocationContext.NullResult ? null : cacheResult;
	}

	/**
	 * @return cache storage or null if not found
	 */
	@Nullable
	private Cache<String, CCache<ArrayKey, Object>> getCacheStorage(@Nullable final String trxName)
	{
		//
		// If we have a transaction, we shall use transaction's cache
		if (!trxManager.isNull(trxName))
		{
			final ITrx trx = trxManager.get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
			if (trxManager.isNull(trx))
			{
				// If it was about a thread inherited trxName, and no trx was found
				// => we shall go with the local cache, for sure
				if (ITrx.TRXNAME_ThreadInherited.equals(trxName))
				{
					return _cacheStorage;
				}

				// Else, return "null", i.e. cache storage not found
				return null;
			}

			return trx.getProperty(TRX_PROPERTY_CacheStorage, TRX_PROPERTY_CacheStorageInitializer::get);
		}

		//
		// Use our local cache
		return _cacheStorage;
	}
}
