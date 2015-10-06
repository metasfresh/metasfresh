/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.annotation.concurrent.ThreadSafe;

import org.adempiere.exceptions.IssueReportableExceptions;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.MIssue;

import com.google.common.base.Supplier;

/**
 * Client Error Buffer Handler.
 * 
 * This handler is responsible for:
 * <ul>
 * <li>keeping a buffer of last reported errors (see {@link #getLogData(boolean)}, {@link #getErrorInfo(Properties, boolean)} etc)
 * <li>creating {@link I_AD_Issue} records for severe issues ( {@link #reportIssueIfNeeded(CLogErrorBufferInstance, LogRecord)} )
 * </ul>
 *
 * @author Jorg Janke
 * @author tsa
 */
public final class CLogErrorBuffer extends Handler
{
	/* package */static final CLogErrorBuffer get()
	{
		return instance;
	}

	/** Singleton instance */
	private static final CLogErrorBuffer instance = new CLogErrorBuffer();

	/** Key PropertyName used to store the {@link CLogErrorBufferInstance} in context */
	private static final String BUFFERINSTANCE_CTXKEY = CLogErrorBufferInstance.class.getName();

	/** List of logger names which shall be ignored for AD_Issue reporting */
	private final CopyOnWriteArraySet<String> skipIssueReporting_LoggerNames = new CopyOnWriteArraySet<String>();

	/** List of Source Class Names which shall be ignored for AD_Issue reporting */
	private final CopyOnWriteArraySet<String> skipIssueReporting_SourceClassNames = new CopyOnWriteArraySet<>();

	/**
	 * Is Issue reporting enabled.
	 * 
	 * To be changed exclusivelly from {@link #temporaryDisableIssueReporting()}
	 */
	private final InheritableThreadLocal<Boolean> issueReportingEnabled = new InheritableThreadLocal<Boolean>()
	{
		@Override
		protected Boolean initialValue()
		{
			return true; // enabled by default
		}
	};

	/**************************************************************************
	 * Constructor
	 */
	private CLogErrorBuffer()
	{
		super();

		// Formatting
		setFormatter(CLogFormatter.get());
		// Default Level
		super.setLevel(Level.INFO);
		// Filter
		setFilter(CLogFilter.get());

		//
		// Configure which source class names shall be skipped from issue reporting (by default)
		skipIssueReportingForSourceClass(CLogger.class); // Skip errors reported from CLogger (i.e. CLogger.saveError)
		// NOTE: on legacy code we also skipped following SourceMethodNames:
		// * saveError - this one was for CLogger.saveError - we already cover it
		// * get_Value - this one was for PO.get_Value - i don't see the need
		// * dataSave - this one was for GridTab/GridTable.dataSave - because they tried to avoid reporting more then one. We shall solve it differently
	}	// CLogErrorBuffer

	/** How many error records shall we keep in buffer */
	private static final int ERROR_SIZE = 20;

	/** How many log records shall we keep in buffer */
	private static final int LOG_SIZE = 100;

	/** How many previous log records shall we keep for an error record */
	private static final int HISTORY_SIZE = 10;

	/**
	 * Gets the {@link CLogErrorBufferInstance} from current context.
	 * 
	 * @return buffer instance; never returns null
	 */
	private final CLogErrorBufferInstance getBufferInstance()
	{
		final Properties ctx = Env.getCtx();
		return Env.get(ctx, BUFFERINSTANCE_CTXKEY, CLogErrorBufferInstance.supplier);
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
				this.closed = true;
			}
		};
	}

	/** @return true if issue reporting is enabled */
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

	/**
	 * Set Level.
	 * Ignore OFF - and higher then FINE
	 *
	 * @see java.util.logging.Handler#setLevel(java.util.logging.Level)
	 * @param newLevel ignored
	 * @throws java.lang.SecurityException
	 */
	@Override
	public synchronized void setLevel(final Level newLevel) throws SecurityException
	{
		if (newLevel == null)
			return;
		if (newLevel == Level.OFF)
			super.setLevel(Level.SEVERE);
		else if (newLevel == Level.ALL || newLevel == Level.FINEST || newLevel == Level.FINER)
			super.setLevel(Level.FINE);
		else
			super.setLevel(newLevel);
	}	// SetLevel

	/**
	 * Publish
	 *
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 * @param record log record
	 */
	@Override
	public void publish(final LogRecord record)
	{
		// Skip if not loggable
		if (!isLoggable(record))
		{
			return;
		}

		final CLogErrorBufferInstance bufferInstance = getBufferInstance();

		// Output
		bufferInstance.addToLogs(record);

		// We have an error
		if (record.getLevel() == Level.SEVERE)
		{
			// forces Class Name eval
			// NOTE-tsa: i think this is no longer needed because it's done automatically
			record.getSourceClassName();

			bufferInstance.addToErrors(record);

			// Issue Reporting
			reportIssueIfNeeded(record);
		}
	}	// publish

	private final void reportIssueIfNeeded(final LogRecord record)
	{
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
		if (!isEligibleForIssueReporting(record))
		{
			return;
		}

		// Don't report the issue if it was already reported
		if (IssueReportableExceptions.isReported(record.getThrown()))
		{
			return;
		}

		//
		// Report the issue
		try (final IAutoCloseable c = temporaryDisableIssueReporting())
		{
			MIssue.create(record);
		}
		catch (Throwable e)
		{
			// failed to save exception to db, print to console
			System.err.println("Failed reporting the issue for: " + getFormatter().format(record));
			e.printStackTrace();
		}
	}

	/**
	 * @return true if given {@link LogRecord} is eligible for reporting an issue
	 */
	private final boolean isEligibleForIssueReporting(final LogRecord record)
	{
		//
		// Don't report for logger names on which we were explicitelly asked to not report
		final String loggerName = record.getLoggerName(); // in most of the cases is actually the class name
		if (skipIssueReporting_LoggerNames.contains(loggerName))
		{
			return false;
		}

		
		final String sourceClassName = record.getSourceClassName();
		if (skipIssueReporting_SourceClassNames.contains(sourceClassName))
		{
			return false;
		}

		return true;
	}

	/**
	 * Flush (NOP)
	 * 
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public final void flush()
	{
	}	// flush

	/**
	 * Close
	 * 
	 * @see java.util.logging.Handler#close()
	 * @throws SecurityException
	 */
	@Override
	public void close() throws SecurityException
	{
		Env.getCtx().remove(BUFFERINSTANCE_CTXKEY);
	}	// close

	/**************************************************************************
	 * Get ColumnNames of Log Entries
	 * 
	 * @param ctx context (not used)
	 * @return string vector
	 * @see #getLogData(boolean)
	 */
	public Vector<String> getColumnNames(final Properties ctx)
	{
		Vector<String> cn = new Vector<String>();
		cn.add("Time");
		cn.add("Level");
		//
		cn.add("Class.Method");
		cn.add("Message");
		// 2
		cn.add("Parameter");
		cn.add("Trace");
		//
		return cn;
	}	// getColumnNames

	/**
	 * Get Log Data.
	 * 
	 * @param errorsOnly if true errors otherwise log
	 * @return data array
	 * @see #getColumnNames(Properties)
	 */
	public Vector<Vector<Object>> getLogData(boolean errorsOnly)
	{
		final List<LogRecord> records = getRecords(errorsOnly);
		final Vector<Vector<Object>> rows = new Vector<>(records.size());

		for (final LogRecord record : records)
		{
			final Vector<Object> row = new Vector<>(6);
			//
			row.add(new Timestamp(record.getMillis()));
			row.add(record.getLevel().getName());
			//
			row.add(CLogFormatter.getClassMethod(record));
			row.add(record.getMessage());
			//
			row.add(CLogFormatter.getParameters(record));
			row.add(CLogFormatter.getExceptionTrace(record));
			//
			rows.add(row);
		}
		return rows;
	}	// getData

	/**
	 * Get Array of events with most recent first
	 * 
	 * @param errorsOnly if true errors otherwise log
	 * @return array of events
	 */
	private final List<LogRecord> getRecords(final boolean errorsOnly)
	{
		final CLogErrorBufferInstance bufferInstance = getBufferInstance();

		if (errorsOnly)
		{
			return bufferInstance.getErrors();
		}
		else
		{
			return bufferInstance.getLogs();
		}
	}

	/**
	 * Reset Error Buffer
	 * 
	 * @param errorsOnly if true errors otherwise log
	 */
	public void resetBuffer(final boolean errorsOnly)
	{
		final CLogErrorBufferInstance bufferInstance = getBufferInstance();

		bufferInstance.clearErrors();

		if (!errorsOnly)
		{
			bufferInstance.clearLogs();
		}
	}	// resetBuffer

	/**
	 * Get/Put Error Info in String
	 *
	 * @param ctx context
	 * @param errorsOnly if true errors otherwise log
	 * @return error info
	 */
	public String getErrorInfo(final Properties ctx, final boolean errorsOnly)
	{
		final CLogErrorBufferInstance bufferInstance = getBufferInstance();
		final StringBuffer sb = new StringBuffer();
		final Formatter formatter = getFormatter();

		//
		if (errorsOnly)
		{
			final List<List<LogRecord>> history = bufferInstance.getHistory();
			for (final List<LogRecord> records : history)
			{
				sb.append("-------------------------------\n");
				for (final LogRecord record : records)
				{
					sb.append(formatter.format(record));
				}
			}
		}
		else
		{
			final List<LogRecord> logs = bufferInstance.getLogs();
			for (final LogRecord record : logs)
			{
				sb.append(formatter.format(record));
			}
		}
		sb.append("\n");
		CLogMgt.getInfo(sb);
		CLogMgt.getInfoDetail(sb, ctx);
		//
		return sb.toString();
	}	// getErrorInfo

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final CLogErrorBufferInstance bufferInstance = getBufferInstance();

		final StringBuilder sb = new StringBuilder("CLogErrorBuffer[");
		sb.append("Errors=").append(bufferInstance.getErrorsSize())
				.append(",Logs=").append(bufferInstance.getLogsSize())
				.append(",Level=").append(getLevel())
				.append(",SkipIssueReportingLoggerNames=").append(skipIssueReporting_LoggerNames)
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Buffer instance implementation.
	 * 
	 * @author tsa
	 *
	 */
	@ThreadSafe
	private static class CLogErrorBufferInstance
	{
		/** Supplier which creates a new instance each time when it's called */
		public static final Supplier<CLogErrorBufferInstance> supplier = new Supplier<CLogErrorBufferInstance>()
		{

			@Override
			public CLogErrorBufferInstance get()
			{
				return new CLogErrorBufferInstance();
			}
		};

		private final LinkedList<LogRecord> logs = new LinkedList<>();
		private final LinkedList<LogRecord> errors = new LinkedList<>();
		private final LinkedList<List<LogRecord>> history = new LinkedList<>(); // note: it's always handled along with "errors"

		private CLogErrorBufferInstance()
		{
			super();
		}

		public List<LogRecord> getLogs()
		{
			synchronized (logs)
			{
				return new ArrayList<>(logs);
			}
		}

		public int getLogsSize()
		{
			synchronized (logs)
			{
				return logs.size();
			}
		}

		private List<LogRecord> getLogsTail(final int tailSize)
		{
			synchronized (logs)
			{
				if (logs.isEmpty())
				{
					return Collections.emptyList();
				}

				final List<LogRecord> history = new ArrayList<>(tailSize);
				for (int i = logs.size() - 1; i >= 0; i--)
				{
					final LogRecord rec = logs.get(i);
					if (rec.getLevel() == Level.SEVERE)
					{
						if (history.isEmpty())
						{
							history.add(0, rec);
						}
						else
						{
							break;		// don't include previous error
						}
					}
					else
					{
						history.add(0, rec);
						if (history.size() > tailSize)
						{
							break;		// no more then "tailSize" history records
						}
					}
				}

				return history;
			}
		}

		public void addToLogs(final LogRecord logRecord)
		{
			synchronized (logs)
			{
				if (logs.size() >= LOG_SIZE)
				{
					logs.removeFirst();
				}
				logs.add(logRecord);
			}
		}

		private void clearLogs()
		{
			synchronized (logs)
			{
				logs.clear();
			}
		}

		public void addToErrors(final LogRecord logRecord)
		{
			synchronized (errors)
			{
				if (errors.size() >= ERROR_SIZE)
				{
					errors.removeFirst();
					history.removeFirst();
				}

				//
				// Add Error
				errors.add(logRecord);

				//
				// Create History
				final List<LogRecord> logsTail = getLogsTail(HISTORY_SIZE);
				history.add(logsTail);

			}
		}

		public List<LogRecord> getErrors()
		{
			synchronized (errors)
			{
				return new ArrayList<>(errors);
			}
		}

		public int getErrorsSize()
		{
			synchronized (errors)
			{
				return errors.size();
			}
		}

		public void clearErrors()
		{
			synchronized (errors)
			{
				errors.clear();
				history.clear();
			}
		}

		public List<List<LogRecord>> getHistory()
		{
			synchronized (errors)
			{
				return new ArrayList<List<LogRecord>>(history);
			}
		}
	}
}	// CLogErrorBuffer
