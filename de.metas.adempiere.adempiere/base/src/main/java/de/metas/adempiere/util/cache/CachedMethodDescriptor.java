package de.metas.adempiere.util.cache;

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


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.CCache;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.adempiere.util.CacheModel;
import de.metas.adempiere.util.CacheModelId;
import de.metas.adempiere.util.CacheReloadIfTrue;
import de.metas.adempiere.util.CacheTrx;

/* package */final class CachedMethodDescriptor
{
	private static final String DEFAULT_CacheName = CachedMethodDescriptor.class.getName() + "#defaultCache";
	private static final int DEFAULT_CacheInitialCapacity = 20;
	private static final int DEFAULT_CacheExpireMinutes = 10;

	private final Method method;
	private final Cached cachedAnnotation;
	private final boolean staticMethod;
	private final String cacheName;
	private final List<ICachedMethodPartDescriptor> descriptors;
	
	private final Callable<CCache<ArrayKey, Object>> createCCacheCallable = new Callable<CCache<ArrayKey,Object>>()
	{
		@Override
		public CCache<ArrayKey, Object> call() throws Exception
		{
			return createCCache();
		}
	};

	CachedMethodDescriptor(final Method method)
	{
		super();

		Check.assumeNotNull(method, "method not null"); // shall not happen if we reach this point
		this.method = method;

		cachedAnnotation = method.getAnnotation(Cached.class);
		Check.assumeNotNull(cachedAnnotation, "cachedAnnotation not null"); // shall not happen if we reach this point

		staticMethod = Modifier.isStatic(method.getModifiers());

		cacheName = mkCacheName(cachedAnnotation);

		final Builder<ICachedMethodPartDescriptor> descriptorsBuilder = ImmutableList.<ICachedMethodPartDescriptor> builder();

		//
		// Caching part: Target PO
		{
			final TargetPOPartDescriptor descriptor = TargetPOPartDescriptor.createIfApplies(method, cachedAnnotation);
			if (descriptor != null)
			{
				descriptorsBuilder.add(descriptor);
			}
		}

		//
		// Parse cached method parameters
		final Class<?>[] parameterTypes = method.getParameterTypes();
		final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++)
		{
			final Class<?> parameterType = parameterTypes[parameterIndex];
			final List<Annotation> parameterAnnotations = Arrays.asList(parametersAnnotations[parameterIndex]);

			// Skip parameters which were marked with @CacheIgnore
			if (containsAnnotationType(parameterAnnotations, CacheIgnore.class))
			{
				continue;
			}

			//
			// Create descriptors from parameter annotations
			boolean createGenericDescriptor = true;
			for (final Annotation annotation : parameterAnnotations)
			{
				if (annotation instanceof CacheTrx)
				{
					final CacheTrxParamDescriptor paramDescriptor = new CacheTrxParamDescriptor(parameterType, parameterIndex, annotation);
					descriptorsBuilder.add(paramDescriptor);
					createGenericDescriptor = false;
				}
				else if (annotation instanceof CacheCtx)
				{
					final CacheCtxParamDescriptor paramDescriptor = new CacheCtxParamDescriptor(parameterType, parameterIndex, annotation);
					descriptorsBuilder.add(paramDescriptor);
					createGenericDescriptor = false;
				}
				else if (annotation instanceof CacheReloadIfTrue)
				{
					final CacheReloadIfTrueParamDescriptor paramDescriptor = new CacheReloadIfTrueParamDescriptor(parameterType, parameterIndex, annotation);
					descriptorsBuilder.add(paramDescriptor);
					createGenericDescriptor = false;
				}
				else if (annotation instanceof CacheModelId)
				{
					final CacheModelIdParamDescriptor paramDescriptor = new CacheModelIdParamDescriptor(parameterType, parameterIndex, annotation);
					descriptorsBuilder.add(paramDescriptor);
					createGenericDescriptor = false;
				}
				else if (annotation instanceof CacheModel)
				{
					descriptorsBuilder.add(new CacheCtxParamDescriptor(parameterType, parameterIndex, annotation));
					descriptorsBuilder.add(new CacheTrxParamDescriptor(parameterType, parameterIndex, annotation));
					descriptorsBuilder.add(new CacheModelIdParamDescriptor(parameterType, parameterIndex, annotation));
					createGenericDescriptor = false;
				}
			}

			//
			// Create a generic descriptor, if no other descriptors were created
			if (createGenericDescriptor)
			{
				descriptorsBuilder.add(GenericParamDescriptor.of(parameterType, parameterIndex, parameterAnnotations));
			}
		}

		//
		// Caching part: Target PO KeyProperties
		{
			final TargetPOKeyPropertiesPartDescriptor descriptor = TargetPOKeyPropertiesPartDescriptor.createIfApplies(method, cachedAnnotation);
			if (descriptor != null)
			{
				descriptorsBuilder.add(descriptor);
			}
		}

		descriptors = descriptorsBuilder.build();
	}

	private static final String mkCacheName(final Cached annotation)
	{
		final String cacheName = annotation.cacheName();
		if (Check.isEmpty(cacheName, true))
		{
			return DEFAULT_CacheName;
		}
		return cacheName;
	}

	static final boolean containsAnnotationType(final List<Annotation> annotations, final Class<? extends Annotation> annotationType)
	{
		if (annotations == null || annotations.isEmpty())
		{
			return false;
		}

		for (final Annotation annotation : annotations)
		{
			if (annotationType.isAssignableFrom(annotation.getClass()))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int hashCode()
	{
		// NOTE: only "method" counts, everything else is deducted

		return new HashcodeBuilder()
				.append(method)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		// NOTE: only "method" counts, everything else is deducted

		if (this == obj)
		{
			return true;
		}

		final CachedMethodDescriptor other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.method, other.method)
				.isEqual();
	}

	/**
	 * @return cache name to use; never returns null/empty
	 */
	public String getCacheName()
	{
		return cacheName;
	}

	public int getCacheInitExpireMinutes()
	{
		return cachedAnnotation.expireMinutes();
	}

	public Method getMethod()
	{
		return method;
	}

	public CacheKeyBuilder createKeyBuilder(final Object targetObject, final Object[] methodArgs)
	{
		final Class<?> methodDeclaringClass = method.getDeclaringClass();
		final Object targetObjToUse = staticMethod ? methodDeclaringClass : targetObject;

		final CacheKeyBuilder keyBuilder = new CacheKeyBuilder();

		//
		// Key: Method signature
		// NOTE: avoid adding Class/Field/Method etc to key => would lead to ClassLoader(s) memory leaks/fucked-up
		keyBuilder.add(methodDeclaringClass.getName());
		keyBuilder.add(method.getName());
		keyBuilder.add(method.getReturnType().getName());

		for (final ICachedMethodPartDescriptor descriptor : descriptors)
		{
			descriptor.extractKeyParts(keyBuilder, targetObjToUse, methodArgs);
			if (keyBuilder.isSkipCaching())
			{
				return keyBuilder;
			}
		}

		return keyBuilder;
	}
	
	/**
	 * Creates a new {@link CCache} based on given {@link CachedMethodDescriptor}.
	 * 
	 * @param methodDescriptor
	 * @return {@link CCache}; never returns null
	 */
	private final CCache<ArrayKey, Object> createCCache()
	{
		final String cacheName = getCacheName();

		//
		// Create a new cache
		//
		final int initialCapacity = DEFAULT_CacheInitialCapacity;

		// Expire Minutes
		int expireMinutes = getCacheInitExpireMinutes();
		if (expireMinutes == Cached.EXPIREMINUTES_Never)
		{
			expireMinutes = 0; // never expire
		}
		else if (expireMinutes <= 0)
		{
			expireMinutes = DEFAULT_CacheExpireMinutes;
		}

		final CCache<ArrayKey, Object> cache = new CCache<>(cacheName, initialCapacity, expireMinutes);
		return cache;
	}

	/**
	 * Callable used to create method level cache container.
	 */
	public Callable<CCache<ArrayKey, Object>> createCCacheCallable()
	{
		return createCCacheCallable;
	}
}
