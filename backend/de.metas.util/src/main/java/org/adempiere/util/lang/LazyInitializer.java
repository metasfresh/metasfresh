package org.adempiere.util.lang;

import java.util.function.Supplier;

import org.adempiere.util.Check;

/*
 * #%L
 * de.metas.util
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

/**
 * This {@link IReference} implementation provides a generic implementation of the lazy initialization pattern.
 *
 * Sometimes an application has to deal with an object only under certain circumstances, e.g. when the user selects a specific menu item or if a special event is received. If the creation of the
 * object is costly or the consumption of memory or other system resources is significant, it may make sense to defer the creation of this object until it is really needed. This is a use case for the
 * lazy initialization pattern.
 *
 * NOTE: this implementation is NOT thread safe.
 *
 * @author tsa
 *
 * @param <T>
 */
public abstract class LazyInitializer<T> implements IReference<T>
{
	public static final <T> LazyInitializer<T> of(final Supplier<T> supplier)
	{
		Check.assumeNotNull(supplier, "Parameter supplier is not null");
		return new LazyInitializer<T>()
		{
			@Override
			protected T initialize()
			{
				return supplier.get();
			}
		};
	}

	private T value = null;
	private boolean initialized = false;

	/**
	 * Creates and initializes the object managed by this LazyInitializer.
	 *
	 * This method is called by {@link #getValue()} when the object is accessed for the first time. An implementation can focus on the creation of the object.
	 *
	 * @return
	 */
	protected abstract T initialize();

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("LazyInitializer[");
		if (initialized)
		{
			sb.append(value);
		}
		else
		{
			sb.append("not initialized");
		}
		sb.append("]");

		return sb.toString();
	}

	@Override
	public final T getValue()
	{
		if (!initialized)
		{
			value = initialize();
			initialized = true;
		}

		return value;
	}

	/**
	 *
	 * @return true if the reference was already initialized (i.e. {@link #initialize()} was called)
	 */
	public final boolean isInitialized()
	{
		return initialized;
	}
}
