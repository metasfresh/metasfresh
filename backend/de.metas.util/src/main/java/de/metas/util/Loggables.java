package de.metas.util;

import ch.qos.logback.classic.Level;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.logging.LogbackLoggable;
import org.slf4j.Logger;

import javax.annotation.Nullable;

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
 */
@UtilityClass
public final class Loggables
{
	private static ILoggable debuggingLoggable;

	/**
	 * @return the loggable instance currently associated with this thread or the {@link NullLoggable}. Never returns <code>null</code>.
	 */
	public static ILoggable get()
	{
		return ThreadLocalLoggableHolder.instance.getLoggable();
	}

	/**
	 * @return current thread's {@link ILoggable} instance or a loggable which is forwarding to given logger instance if there was no thread level {@link ILoggable}
	 */
	public static ILoggable getLoggableOrLogger(@NonNull final Logger logger, @NonNull final Level logLevel)
	{
		final ILoggable loggable = ThreadLocalLoggableHolder.instance.getLoggableOr(null);
		return loggable != null ? loggable : logback(logger, logLevel);
	}

	public static ILoggable console()
	{
		return ConsoleLoggable.withPrefix(null);
	}

	public static ILoggable console(@Nullable final String prefix)
	{
		return ConsoleLoggable.withPrefix(prefix);
	}

	public static ILoggable logback(@NonNull final Logger logger, @NonNull final Level logLevel)
	{
		return new LogbackLoggable(logger, logLevel);
	}

	public static IAutoCloseable temporarySetLoggable(final ILoggable loggable)
	{
		return ThreadLocalLoggableHolder.instance.temporarySetLoggable(composeWithDebuggingLoggable(loggable));
	}

	public static void setDebuggingLoggable(@Nullable final ILoggable debuggingLoggable)
	{
		Loggables.debuggingLoggable = debuggingLoggable;
	}

	private static ILoggable composeWithDebuggingLoggable(@Nullable final ILoggable loggable)
	{
		return CompositeLoggable2.compose(loggable, debuggingLoggable);
	}

	/**
	 * @return The null loggable which can be used without NPE, but doesn't do anything
	 */
	@NonNull
	public static ILoggable nop()
	{
		return NullLoggable.instance;
	}

	public static boolean isNull(@Nullable final ILoggable loggable)
	{
		return NullLoggable.isNull(loggable);
	}

	/**
	 * Create a new {@link ILoggable} instance that delegates {@link #addLog(String, Object...)} invocations to the thread-local instance and in addition logs to the given logger.
	 */
	public static ILoggable withLogger(@NonNull final Logger logger, @NonNull final Level level)
	{
		return new LoggableWithLogger(get(), logger, level);
	}

	public static ILoggable withLogger(@NonNull final ILoggable loggable, @NonNull final Logger logger, @NonNull final Level level)
	{
		return new LoggableWithLogger(loggable, logger, level);
	}

	public static ILoggable withWarnLoggerToo(@NonNull final Logger logger)
	{
		return withLogger(logger, Level.WARN);
	}

	public static PlainStringLoggable newPlainStringLoggable()
	{
		return new PlainStringLoggable();
	}

	public static ILoggable addLog(final String msg, final Object... msgParameters)
	{
		return get().addLog(msg, msgParameters);
	}
}
