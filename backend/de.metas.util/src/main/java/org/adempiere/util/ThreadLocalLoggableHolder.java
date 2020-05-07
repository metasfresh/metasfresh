package org.adempiere.util;

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
 * Holds the {@link ILoggable} instance of current thread.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ThreadLocalLoggableHolder
{
	/**
	 * Temporary set the current thread level loggable to given one.
	 *
	 * The method is designed to be used in try-with-resources block, so the previous thread level loggable will be restored when the returned closeable will be closed.
	 *
	 * @param loggable
	 * @return
	 */
	/* package */ IAutoCloseable temporarySetLoggable(final ILoggable loggable)
	{
		Check.assumeNotNull(loggable, "loggable not null");
		final ILoggable loggableOld = loggableRef.get();

		loggableRef.set(loggable);
		return new IAutoCloseable()
		{
			boolean closed = false;

			@Override
			public void close()
			{
				if (closed)
				{
					return;
				}
				closed = true;
				loggableRef.set(loggableOld);
			}

		};
	}

	/**
	 * @return current thread's {@link ILoggable} instance or the {@link NullLoggable}. Never returns <code>null</code>
	 */
	/* package */ ILoggable getLoggable()
	{
		return getLoggableOr(Loggables.getNullLoggable());
	}

	/** @return current thread's {@link ILoggable} instance or <code>defaultLoggable</code> if there was no thread level {@link ILoggable} */
	/* package */ ILoggable getLoggableOr(final ILoggable defaultLoggable)
	{
		final ILoggable loggable = loggableRef.get();
		return loggable != null ? loggable : defaultLoggable;
	}

	/**
	 * Holds the {@link ILoggable} instance of current thread
	 *
	 */
	public static final transient ThreadLocalLoggableHolder instance = new ThreadLocalLoggableHolder();

	private final ThreadLocal<ILoggable> loggableRef = new ThreadLocal<>();

	private ThreadLocalLoggableHolder()
	{
		super();
	}
}
