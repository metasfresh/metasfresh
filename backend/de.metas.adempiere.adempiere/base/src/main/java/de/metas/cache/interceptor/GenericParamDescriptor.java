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


import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Predicate;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.adempiere.util.cache.annotations.CacheSkipIfNotNull;
import de.metas.util.Check;
import de.metas.util.collections.Converter;

/**
 * Handles a generic method parameter.
 *
 * Used when no better handlers were found.
 *
 * @author tsa
 *
 */
final class GenericParamDescriptor implements ICachedMethodPartDescriptor
{
	public static GenericParamDescriptor of(final Class<?> parameterType, final int parameterIndex, final List<Annotation> parameterAnnotations)
	{
		//
		// Skip caching predicate:
		final Predicate<Object> skipCachingPredicate;
		if (CachedMethodDescriptor.containsAnnotationType(parameterAnnotations, CacheSkipIfNotNull.class))
		{
			// Cache: we were asked to skip if not null for a primitive type
			// => does not make sense
			if (parameterType.isPrimitive())
			{
				throw new CacheIntrospectionException("Cannot annotate an primitive argument with " + CacheSkipIfNotNull.class + " because they will never get null.")
						.setParameter(parameterIndex, parameterType);
			}
			skipCachingPredicate = SkipCaching_IfNotNull;
		}
		else
		{
			skipCachingPredicate = null;
		}

		//
		// Argument converter:
		final Converter<Object, Object> argumentConverter;
		if (CacheImmutableClassesIndex.instance.isImmutable(parameterType))
		{
			argumentConverter = null;
		}
		else if (java.util.Date.class.isAssignableFrom(parameterType))
		{
			argumentConverter = Converter_Date;
		}
		else
		{
			// Case: argument is mutable and we don't have any "Skip caching" rule
			// => development error
			final boolean allowMutable = CachedMethodDescriptor.containsAnnotationType(parameterAnnotations, CacheAllowMutable.class);
			if (!allowMutable && skipCachingPredicate == null)
			{
				throw new CacheIntrospectionException("Possible development error: parameter is not immutable.")
						.setParameter(parameterIndex, parameterType)
						.addHintToFix("Use immutable parameters or if you want to ignore this issue, annotate the parameter with " + CacheAllowMutable.class)
						.addHintToFix("Use a cache skip annotation like " + CacheSkipIfNotNull.class);
			}

			argumentConverter = null;
		}

		//
		// Create the parameter descriptor instance.
		// In case there is no skip caching rule and no argument converter, we can use our cached instance (which covers almost all cases).
		if (skipCachingPredicate == null
				&& argumentConverter == null
				&& (parameterIndex >= 0 && parameterIndex < cache.length))
		{
			return cache[parameterIndex];
		}
		else
		{
			return new GenericParamDescriptor(parameterIndex, argumentConverter, skipCachingPredicate);
		}

	}

	/** Argument converter: Date to "millis" */
	private static final Converter<Object, Object> Converter_Date = new Converter<Object, Object>()
	{
		@Override
		public Long convert(final Object value)
		{
			return value == null ? 0 : ((java.util.Date)value).getTime();
		}
	};

	/** Skip caching predicate: skip if argument is NOT null */
	private static final Predicate<Object> SkipCaching_IfNotNull = value -> value != null;

	private static final GenericParamDescriptor[] cache = new GenericParamDescriptor[] {
			new GenericParamDescriptor(0)
			, new GenericParamDescriptor(1)
			, new GenericParamDescriptor(2)
			, new GenericParamDescriptor(3)
			, new GenericParamDescriptor(4)
			, new GenericParamDescriptor(5)
			, new GenericParamDescriptor(6)
			, new GenericParamDescriptor(7)
			, new GenericParamDescriptor(8)
			, new GenericParamDescriptor(9)
			, new GenericParamDescriptor(10)
	};

	private final int parameterIndex;
	private final Converter<Object, Object> argumentConverter;
	private final Predicate<Object> skipCachingPredicate;

	private GenericParamDescriptor(final int parameterIndex)
	{
		this(parameterIndex, null, null); // converter = null, skipCachingPredicate = null;
	}

	private GenericParamDescriptor(final int parameterIndex, final Converter<Object, Object> argumentConverter, final Predicate<Object> skipCachingPredicate)
	{
		Check.assume(parameterIndex >= 0, "parameterIndex >= 0");
		this.parameterIndex = parameterIndex;
		this.argumentConverter = argumentConverter;
		this.skipCachingPredicate = skipCachingPredicate;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public void extractKeyParts(final CacheKeyBuilder keyBuilder, final Object targetObject, final Object[] params)
	{
		final Object param = params[parameterIndex];

		//
		// Check if we shall skip caching
		if (skipCachingPredicate != null && skipCachingPredicate.test(param))
		{
			keyBuilder.setSkipCaching();
			return;
		}

		//
		// Convert argument if needed
		final Object paramConverted = argumentConverter == null ? param : argumentConverter.convert(param);

		// Add parameter to cache key builder
		keyBuilder.add(paramConverted);
	}
}
