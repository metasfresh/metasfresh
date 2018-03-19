package org.adempiere.util.lang;

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

import java.io.Serializable;

/**
 * An memorizing supplier which also allows to forget the value or to peek current value.
 *
 * @param <T>
 */
public final class ExtendedMemorizingSupplier<T> implements java.util.function.Supplier<T>, Serializable
{
	public static final <T> ExtendedMemorizingSupplier<T> of(final java.util.function.Supplier<T> supplier)
	{
		if (supplier instanceof ExtendedMemorizingSupplier)
		{
			return (ExtendedMemorizingSupplier<T>)supplier;
		}
		return new ExtendedMemorizingSupplier<>(supplier);
	}

	private final java.util.function.Supplier<T> delegate;

	private transient volatile boolean initialized;
	// "value" does not need to be volatile; visibility piggy-backs
	// on volatile read of "initialized".
	private transient T value;

	private ExtendedMemorizingSupplier(final java.util.function.Supplier<T> delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public T get()
	{
		// A 2-field variant of Double Checked Locking.
		if (!initialized)
		{
			synchronized (this)
			{
				if (!initialized)
				{
					final T t = delegate.get();
					value = t;
					initialized = true;
					return t;
				}
			}
		}
		return value;
	}

	/** @return memorized value or <code>null</code> if not initialized */
	public T peek()
	{
		synchronized (this)
		{
			return value;
		}
	}

	/**
	 * Forget memorized value
	 *
	 * @return
	 * @return current value if any
	 */
	public T forget()
	{
		// https://github.com/metasfresh/metasfresh-webui-api/issues/787 - similar to the code of get();
		// if the instance known to not be initialized
		// then don't attempt to acquire lock (and to other time consuming stuff..)
		if (initialized)
		{
			synchronized (this)
			{
				if (initialized)
				{
					final T valueOld = this.value;
					initialized = false;
					value = null;
					return valueOld;
				}
			}
		}
		return null;
	}

	/** @return true if this supplier has a value memorized */
	public boolean isInitialized()
	{
		return initialized;
	}

	@Override
	public String toString()
	{
		return "ExtendedMemorizingSupplier[" + delegate + "]";
	}

	private static final long serialVersionUID = 0;
}
