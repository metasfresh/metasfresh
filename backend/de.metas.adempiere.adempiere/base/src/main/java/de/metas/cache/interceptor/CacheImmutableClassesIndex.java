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
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.compiere.util.NamePair;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

/**
 * Singleton used to hold the immutable classes list.
 *
 * @author tsa
 *
 */
public final class CacheImmutableClassesIndex
{
	public static final transient CacheImmutableClassesIndex instance = new CacheImmutableClassesIndex();

	private final Set<Class<?>> immutableClassesSeed = ImmutableSet.<Class<?>> builder()
			// Primitives
			.add(Integer.class)
			.add(Double.class)
			.add(Short.class)
			.add(Float.class)
			.add(Boolean.class)
			.add(Character.class)
			.add(Byte.class)
			.add(Void.class)
			//
			// Some common java types:
			.add(Enum.class)
			.add(String.class)
			.add(BigDecimal.class)
			//
			// Some java reflect types:
			.add(Class.class)
			.add(Method.class)
			.add(Annotation.class)
			//
			// Java Date/Time
			.add(LocalDate.class)
			.add(LocalDateTime.class)
			.add(LocalTime.class)
			.add(ZonedDateTime.class)
			.add(Duration.class)
			.add(Instant.class)
			//
			// Some Adempiere types:
			.add(NamePair.class)
			.add(ArrayKey.class)
			//
			// guava immutable stuff
			.add(ImmutableList.class)
			.add(ImmutableSet.class)
			.add(ImmutableMap.class)

			.build();

	private final CopyOnWriteArraySet<Class<?>> immutableClasses = new CopyOnWriteArraySet<>();

	private CacheImmutableClassesIndex()
	{
		immutableClasses.addAll(immutableClassesSeed);
	}

	/**
	 * @param clazz
	 * @return true if given class is considered to be immutable
	 */
	public boolean isImmutable(@NonNull final Class<?> clazz)
	{
		// Primitive types are always immutable
		if (clazz.isPrimitive())
		{
			return true;
		}

		// Assume IDs are immutable
		if (RepoIdAware.class.isAssignableFrom(clazz))
		{
			return true;
		}

		for (final Class<?> immutableClass : immutableClasses)
		{
			if (immutableClass.isAssignableFrom(clazz))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Register a new immutable class.
	 *
	 * WARNING: to be used only if it's really needed because this is kind of dirty hack which could affect our caching system.
	 *
	 * @param immutableClass
	 */
	public void registerImmutableClass(final Class<?> immutableClass)
	{
		Check.assumeNotNull(immutableClass, "immutableClass not null");
		immutableClasses.add(immutableClass);
	}
}
