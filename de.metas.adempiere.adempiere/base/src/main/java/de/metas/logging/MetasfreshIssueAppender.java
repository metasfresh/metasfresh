package de.metas.logging;

import java.util.Properties;
import java.util.concurrent.CopyOnWriteArraySet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.MSystem;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
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
	private final CopyOnWriteArraySet<String> skipIssueReporting_LoggerNames = new CopyOnWriteArraySet<String>();

	/** List of Source Class Names which shall be ignored for AD_Issue reporting */
	private final CopyOnWriteArraySet<String> skipIssueReporting_SourceClassNames = new CopyOnWriteArraySet<>();

	/**
	 * Is Issue reporting enabled.
	 *
	 * To be changed exclusively from {@link #temporaryDisableIssueReporting()}
	 */
	private final ThreadLocal<Boolean> issueReportingEnabled = new ThreadLocal<Boolean>()
	{
		@Override
		protected Boolean initialValue()
		{
			return Boolean.TRUE; // enabled by default
		}
	};

	/**************************************************************************
	 * Constructor
	 */
	public MetasfreshIssueAppender()
	{
		super();
	}

	/** Temporary disable the issue reporting and returns an {@link IAutoCloseable} which will enable it back */
	public final IAutoCloseable temporaryDisableIssueReporting()
	{
		// If it's already disabled then do nothing
		if (!issueReportingEnabled.get())
		{
			return NullAutoCloseable.instance;
		}

		// Disable issue reporting
		issueReportingEnabled.set(false);

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
				issueReportingEnabled.set(true);
				closed = true;
			}
		};
	}

	/**
	 * @return true if issue reporting is enabled
	 */
	private final boolean isIssueReportingEnabled()
	{
		return issueReportingEnabled.get();
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

	private final void reportAD_Issue(final ILoggingEvent event)
	{
		// Temporarily relax our DB constraints
		DB.saveConstraints();
		try
		{
			DB.getConstraints().setOnlyAllowedTrxNamePrefixes(false).incMaxTrx(1);

			// Skip creating the issue if database connection is not available or if the system was not configured to AutoReportError
			if (!DB.isConnected())
			{
				return;
			}
			// Skip creating the issue if database connection is not available or if the system was not configured to AutoReportError
			final Properties ctx = Env.getCtx();
			final MSystem system = MSystem.get(ctx);
			if (system == null || !system.isAutoErrorReport())
			{
				return;
			}

			final Throwable throwable = extractThrowable(event);

			//
			// Create AD_Issue
			final I_AD_Issue issue = InterfaceWrapperHelper.create(ctx, I_AD_Issue.class, ITrx.TRXNAME_None);
			{
				String summary = event.getMessage();
				issue.setSourceClassName(extractSourceClassName(event));
				issue.setSourceMethodName(extractSourceMethodName(event));
				issue.setLoggerName(event.getLoggerName());

				if (throwable != null)
				{
					//
					// Summary
					if (summary != null && summary.length() > 0)
					{
						summary = throwable.toString() + " " + summary;
					}
					if (summary == null || summary.length() == 0)
					{
						summary = throwable.toString();
					}

					//
					// ErrorTrace
					final StringBuilder errorTrace = new StringBuilder();
					final StackTraceElement[] tes = throwable.getStackTrace();
					int count = 0;
					for (int i = 0; i < tes.length; i++)
					{
						final StackTraceElement element = tes[i];
						final String s = element.toString();
						if (s.indexOf("adempiere") != -1)
						{
							errorTrace.append(s).append("\n");
							if (count == 0)
							{
								final String source = element.getClassName() + "." + element.getMethodName();
								issue.setSourceClassName(source);
								issue.setLineNo(element.getLineNumber());
							}
							count++;
						}
						if (count > 5 || errorTrace.length() > 2000)
						{
							break;
						}
					}
					issue.setErrorTrace(errorTrace.toString());

					//
					// StackTrace
					final String stackTrace = Util.dumpStackTraceToString(throwable);
					issue.setStackTrace(stackTrace);
				}

				if (summary == null || summary.isEmpty())
				{
					summary = "??";
				}

				issue.setIssueSummary(summary);
				issue.setRecord_ID(1); // just to have something there because it's mandatory

				InterfaceWrapperHelper.save(issue);
			}

			IssueReportableExceptions.markReportedIfPossible(throwable, issue.getAD_Issue_ID());
		}
		finally
		{
			DB.restoreConstraints();
		}
	}
}
