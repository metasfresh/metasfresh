package de.metas.logging;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.model.I_AD_Issue;
import org.compiere.util.DB;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Creates an {@link I_AD_Issue} for messages that are logged on level {@link Level#ERROR}.
 */
public class MetasfreshIssueAppender extends UnsynchronizedAppenderBase<ILoggingEvent>
{
	public static final MetasfreshIssueAppender get()
	{
		final ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
		if (loggerFactory instanceof LoggerContext)
		{
			final LoggerContext loggerContext = (LoggerContext)loggerFactory;
			final Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
			return (MetasfreshIssueAppender)rootLogger.getAppender(NAME);
		}

		return null;
	}

	/** Appender's name, as defined in logback-spring.xml */
	private static final String NAME = "AD_ISSUE";

	/** List of logger names which shall be ignored for AD_Issue reporting */
	private final CopyOnWriteArraySet<String> skipIssueReporting_LoggerNames = new CopyOnWriteArraySet<>();

	/** List of Source Class Names which shall be ignored for AD_Issue reporting */
	private final CopyOnWriteArraySet<String> skipIssueReporting_SourceClassNames = new CopyOnWriteArraySet<>();

	/**
	 * Is Issue reporting enabled.
	 *
	 * To be changed exclusively from {@link #temporaryDisableIssueReporting()}
	 */
	private final ThreadLocal<Boolean> issueReportingEnabledInThread = ThreadLocal.withInitial(() -> Boolean.TRUE);

	private volatile AtomicBoolean issueReportingEnabledGlobally = new AtomicBoolean(false);

	public final void enableIssueReporting()
	{
		issueReportingEnabledGlobally.set(true);
	}

	/** Temporary disable the issue reporting and returns an {@link IAutoCloseable} which will enable it back */
	public final IAutoCloseable temporaryDisableIssueReporting()
	{
		// If it's already disabled then do nothing
		if (!issueReportingEnabledInThread.get())
		{
			return NullAutoCloseable.instance;
		}

		// Disable issue reporting
		issueReportingEnabledInThread.set(false);

		// Return an auto-closeable which will put it back to on
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

				// Enable issue reporting
				issueReportingEnabledInThread.set(true);
				closed = true;
			}
		};
	}

	/**
	 * @return true if issue reporting is enabled
	 */
	private final boolean isIssueReportingEnabled()
	{
		return issueReportingEnabledGlobally.get() && issueReportingEnabledInThread.get();
	}

	/** Advice the issue reporter to skip reporting for given <code>loggerName</code> */
	public final void skipIssueReportingForLoggerName(final String loggerName)
	{
		if (!skipIssueReporting_LoggerNames.contains(loggerName))
		{
			// NOTE: first we checked if exists because CopyOnWriteArrayList/Set is first creating the new array and then is checking if the value exists...
			skipIssueReporting_LoggerNames.add(loggerName);
		}
	}

	/** Advice the issue reporter to skip reporting for given <code>sourceClass</code> */
	public final void skipIssueReportingForSourceClass(final Class<?> sourceClass)
	{
		if (sourceClass == null)
		{
			return;
		}

		final String sourceClassName = sourceClass.getName();
		if (!skipIssueReporting_SourceClassNames.contains(sourceClassName))
		{
			// NOTE: first we checked if exists because CopyOnWriteArrayList/Set is first creating the new array and then is checking if the value exists...
			skipIssueReporting_SourceClassNames.add(sourceClassName);
		}
	}

	@Override
	protected void append(final ILoggingEvent event)
	{
		// Report only ERRORs
		final Level level = event.getLevel();
		if (level != Level.ERROR)
		{
			return;
		}

		// Don't report the issue if the reporting is currently disabled
		if (!isIssueReportingEnabled())
		{
			return;
		}

		// Don't report the issue if there is no database connection.
		if (!DB.isConnected())
		{
			return;
		}

		// Don't report the issue if the log record is not eligible
		if (!isEligibleForIssueReporting(event))
		{
			return;
		}

		// Don't report the issue if it was already reported
		final Throwable throwable = extractThrowable(event);
		if (IssueReportableExceptions.isReported(throwable))
		{
			return;
		}

		//
		// Report the issue
		try (final IAutoCloseable c = temporaryDisableIssueReporting())
		{
			reportAD_Issue(event);
		}
		catch (final Throwable e)
		{
			// failed to save exception to db, print to console
			System.err.println("Failed reporting the issue for: " + event);
			e.printStackTrace();
		}
	}

	/**
	 * @return true if given {@link ILoggingEvent} is eligible for reporting an issue
	 */
	private final boolean isEligibleForIssueReporting(final ILoggingEvent event)
	{
		//
		// Don't report for logger names on which we were explicitly asked to not report
		final String loggerName = event.getLoggerName(); // in most of the cases is actually the class name
		if (skipIssueReporting_LoggerNames.contains(loggerName))
		{
			return false;
		}

		final String sourceClassName = extractSourceClassName(event);
		if (skipIssueReporting_SourceClassNames.contains(sourceClassName))
		{
			return false;
		}

		return true;
	}

	private final void reportAD_Issue(final ILoggingEvent event)
	{
		// Skip creating the issue if database connection is not available or if the system was not configured to AutoReportError
		if (!DB.isConnected())
		{
			return;
		}

		// Skip creating the issue if database connection is not available or if the system was not configured to AutoReportError
		final ISystemBL systemBL = Services.get(ISystemBL.class);
		if (!systemBL.isAutoErrorReport())
		{
			return;
		}

		final IErrorManager errorManager = Services.get(IErrorManager.class);
		errorManager.createIssue(IssueCreateRequest.builder()
				.summary(event.getMessage())
				.sourceClassname(extractSourceClassName(event))
				.sourceMethodName(extractSourceMethodName(event))
				.loggerName(event.getLoggerName())
				.throwable(extractThrowable(event))
				.build());
	}

	private static final String extractSourceClassName(final ILoggingEvent event)
	{
		if (event == null)
		{
			return null;
		}

		final StackTraceElement[] callerData = event.getCallerData();
		if (callerData == null || callerData.length < 1)
		{
			return null;
		}
		return callerData[0].getClassName();
	}

	private static final String extractSourceMethodName(final ILoggingEvent event)
	{
		if (event == null)
		{
			return null;
		}

		final StackTraceElement[] callerData = event.getCallerData();
		if (callerData == null || callerData.length < 1)
		{
			return null;
		}
		return callerData[0].getMethodName();
	}

	private static final Throwable extractThrowable(final ILoggingEvent event)
	{
		if (event == null)
		{
			return null;
		}

		final IThrowableProxy throwableProxy = event.getThrowableProxy();
		if (throwableProxy instanceof ThrowableProxy)
		{
			return ((ThrowableProxy)throwableProxy).getThrowable();
		}

		return null;
	}
}
