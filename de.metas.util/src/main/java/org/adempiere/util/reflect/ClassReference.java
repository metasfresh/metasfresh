package org.adempiere.util.reflect;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.util
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

/**
 * Holds a weak reference to a given {@link Class} to prevent classloader memory leaks.
 * 
 * If the weak reference expired, it will try to load the {@link Class} using current thread's class loader.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T>
 */
public final class ClassReference<T>
{
	public static final <T> ClassReference<T> of(final Class<T> clazz)
	{
		return new ClassReference<>(clazz);
	}

	private final String classname;
	private final transient AtomicReference<WeakReference<Class<T>>> clazzRef;

	private ClassReference(final Class<T> clazz)
	{
		if (clazz == null)
		{
			throw new NullPointerException("clazz is null");
		}

		this.classname = clazz.getName();
		this.clazzRef = new AtomicReference<>(new WeakReference<>(clazz));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(classname)
				.toString();
	}

	public Class<T> getReferencedClass()
	{
		// Get if not expired
		{
			final WeakReference<Class<T>> weakRef = clazzRef.get();
			final Class<T> clazz = weakRef != null ? weakRef.get() : null;
			if (clazz != null)
			{
				return clazz;
			}
		}

		// Load the class
		try
		{
			@SuppressWarnings("unchecked")
			final Class<T> clazzNew = (Class<T>)Thread.currentThread().getContextClassLoader().loadClass(classname);
			clazzRef.set(new WeakReference<>(clazzNew));
			return clazzNew;
		}
		catch (final ClassNotFoundException ex)
		{
			throw new IllegalStateException("Cannot load expired class: " + classname, ex);
		}
	}
}
