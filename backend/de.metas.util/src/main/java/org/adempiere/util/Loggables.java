package org.adempiere.util;

import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.logging.LogbackLoggable;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * This class contains methods to obtain and work with {@link ILoggable}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class Loggables
{
	private Loggables()
	{

	}

	/**
	 *
	 *
	 * @return the loggable instance currently associated with this thread or the {@link NullLoggable}. Never returns <code>null</code>.
	 */
	public static ILoggable get()
	{
		return ThreadLocalLoggableHolder.instance.getLoggable();
	}

	/**
	 * @return current thread's {@link ILoggable} instance or a loggable which is forwarding to given logger instance if there was no thread level {@link ILoggable}
	 *
	 * @see ILoggable#withLogger(Logger, Level).
	 */
	public static ILoggable getLoggableOrLogger(final Logger logger, final Level logLevel)
	{
		final ILoggable loggable = ThreadLocalLoggableHolder.instance.getLoggableOr(null);
		if (loggable != null)
		{
			return loggable;
		}

		return new LogbackLoggable(logger, logLevel);
	}

	public static IAutoCloseable temporarySetLoggable(final ILoggable loggable)
	{
		return ThreadLocalLoggableHolder.instance.temporarySetLoggable(loggable);
	}

	/**
	 * @return The null loggable which can be used without NPE, but doesn't do anything
	 */
	public static ILoggable getNullLoggable()
	{
		return NullLoggable.instance;
	}
}
