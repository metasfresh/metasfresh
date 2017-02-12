package org.adempiere.util.concurrent;

import org.adempiere.util.lang.IAutoCloseable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * A thread local boolean value which supports:
 * <ul>
 * <li>getting current value: {@link #booleanValue()}
 * <li>temporary enabling it which will return an {@link IAutoCloseable}: {@link #enable()}
 * </ul>
 *
 * NOTE:
 * <ul>
 * <li>on purpose, there is no value setter method
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class AutoClosableThreadLocalBoolean
{
	public static final AutoClosableThreadLocalBoolean newInstance()
	{
		return new AutoClosableThreadLocalBoolean();
	}

	private AutoClosableThreadLocalBoolean()
	{
		super();
	}

	private final ThreadLocal<Boolean> threadLocal = new ThreadLocal<Boolean>()
	{
		@Override
		protected Boolean initialValue()
		{
			return super.initialValue();
		}
	};

	public boolean booleanValue()
	{
		final Boolean value = threadLocal.get();

		return value == null ? false : value.booleanValue();
	}

	/**
	 * Enables the flag and returns an {@link IAutoCloseable} which restores the flag status.
	 *
	 * @return auto closeable which restores the flag status
	 */
	public IAutoCloseable enable()
	{
		final Boolean valueOld = threadLocal.get();

		threadLocal.set(true);

		// Return an auto-closeable which will put it back
		return new IAutoCloseable()
		{
			private volatile boolean closed = false;

			@Override
			public void close()
			{
				if (closed)
				{
					return;
				}

				// restore the old value
				threadLocal.set(valueOld);
				closed = true;
			}
		};
	}
}
