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

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.adempiere.util.Check;

import com.google.common.base.Optional;
import com.google.common.io.Closeables;

/**
 * Adempiere Log File Handler
 *
 * @author Jorg Janke
 * @version $Id: CLogFile.java,v 1.3 2006/07/30 00:54:35 jjanke Exp $
 */
public final class CLogFile extends Handler
{
	public static final CLogFile get()
	{
		return instance;
	}

	/** Singleton instance */
	private static final transient CLogFile instance = new CLogFile();

	private File logDirectory;

	private boolean _enabled = false;
	private LogFile _logFile = null;

	private CLogFile()
	{
		super();

		// Log directory
		final String adempiereHome = Ini.getAdempiereHome();
		final String logDirectoryName = adempiereHome + File.separator + "log";
		setLogDirectory(logDirectoryName);

		// Formatting
		setFormatter(CLogFormatter.get());
		// Level
		setLevel(Level.ALL);
		// Filter
		setFilter(CLogFilter.get());
	}

	private synchronized final LogFile getLogFile(final long timeMillis) throws IOException
	{
		// Rotate log if necessary
		if (_logFile != null && !_logFile.acceptTimestamp(timeMillis))
		{
			_logFile.close();
			_logFile = null;
		}

		// Create log file if needed
		if (_logFile == null)
		{
			_logFile = new LogFile(getLogDirectory(), timeMillis);
		}

		return _logFile;
	}

	private final synchronized LogFile getCurrentLogFile()
	{
		return _logFile;
	}

	private final synchronized void closeCurrentLogFile() throws IOException
	{
		final LogFile logFile = _logFile;
		_logFile = null;

		if (logFile == null)
		{
			return;
		}

		logFile.close();
	}

	private final void closeCurrentLogFileSilently()
	{
		try
		{
			closeCurrentLogFile();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get File Name Date portion
	 *
	 * @param timeMillis time in ms
	 * @return Date String
	 */
	private static final String createLogFileKey(final long timeMillis)
	{
		final Timestamp ts = new Timestamp(timeMillis);
		final String s = ts.toString();
		return s.substring(0, 10);
	}	// getFileNameDate

	/**
	 * Rotate Log file now
	 */
	public void rotateLog()
	{
		if (!isEnabled())
		{
			return;
		}
		close();
	}	// rotateLog

	/**
	 * Get File Name
	 *
	 * @return file name or <code>null</code>
	 */
	public Optional<String> getFileName()
	{
		final LogFile logFile = getCurrentLogFile();
		if (logFile == null)
		{
			return Optional.absent();
		}
		return Optional.of(logFile.getLogFileName());
	}	// getFileName

	public CLogFile setLogDirectory(final String logDirectoryName)
	{
		Check.assumeNotEmpty(logDirectoryName, "logDirectoryName not empty");
		final File logDirectoryOld = logDirectory;
		logDirectory = new File(logDirectoryName);

		//
		// Close current log file if the log directory was changed
		if (!Check.equals(logDirectoryOld, logDirectory))
		{
			closeCurrentLogFileSilently();
		}

		return this;
	}

	/**
	 * Get Log Directory
	 *
	 * @return log directory
	 */
	public File getLogDirectory()
	{
		return logDirectory;
	}	// getLogDirectory

	public void updateConfigurationFromIni()
	{
		setEnabled(Ini.isPropertyBool(Ini.P_TRACEFILE));
	}

	public final CLogFile setEnabled(final boolean enabled)
	{
		if (this._enabled == enabled)
		{
			return this;
		}

		this._enabled = enabled;
		if (!enabled)
		{
			closeCurrentLogFileSilently();
		}

		return this;
	}

	private final boolean isEnabled()
	{
		return _enabled;
	}

	/**
	 * Set Level
	 *
	 * @see java.util.logging.Handler#setLevel(java.util.logging.Level)
	 * @param newLevel new Level
	 * @throws java.lang.SecurityException
	 */
	@Override
	public synchronized void setLevel(final Level newLevel) throws SecurityException
	{
		if (newLevel == null)
		{
			return;
		}
		super.setLevel(newLevel);
	}	// setLevel

	/**
	 * Publish
	 *
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 * @param record log record
	 */
	@Override
	public void publish(final LogRecord record)
	{
		// NOTE (metas-tsa): We are not validating again if we shall log given record because loggers already validated.
		// Also we want to cover following case: the global log level is WARNING, but for one logger we set the log level to FINE (e.g. via JConsole).
		// if (!isLoggable (record))
		// {
		// return;
		// }

		if (!isEnabled())
		{
			return;
		}

		// Format
		String msg = null;
		try
		{
			msg = getFormatter().format(record);
		}
		catch (final Exception ex)
		{
			reportError("formatting", ex, ErrorManager.FORMAT_FAILURE);
			return;
		}

		// Output
		try
		{
			final LogFile logFile = getLogFile(record.getMillis());
			logFile.write(record.getLevel(), msg);
		}
		catch (final Exception ex)
		{
			reportError("writing", ex, ErrorManager.WRITE_FAILURE);
		}
	}

	/**
	 * Flush
	 *
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush()
	{
		try
		{
			final LogFile logFile = getCurrentLogFile();
			if (logFile != null)
			{
				logFile.flush();
			}
		}
		catch (final Exception ex)
		{
			reportError("flush", ex, ErrorManager.FLUSH_FAILURE);
		}
	}	// flush

	/**
	 * Close
	 *
	 * @see java.util.logging.Handler#close()
	 * @throws java.lang.SecurityException
	 */
	@Override
	public void close() throws SecurityException
	{
		try
		{
			closeCurrentLogFile();
		}
		catch (final Exception ex)
		{
			reportError("close", ex, ErrorManager.CLOSE_FAILURE);
		}
	}

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return new StringBuilder("CLogFile[")
				.append(getFileName().or("dir=" + getLogDirectory()))
				.append(",Level=").append(getLevel())
				.append("]")
				.toString();
	}	// toString

	private final class LogFile implements Closeable
	{
		private final File _logDirectory;

		/** Output file */
		private final File _logFile;
		/** Current File Name Date */
		private final String _logFileKey;

		/** Printed header */
		private boolean _doneHeader = false;
		/** Record Counter */
		private int _recordsCount = 0;

		/** File writer */
		private FileWriter logWriter = null;

		public LogFile(final File logDirectory, final long timestampMillis) throws IOException
		{
			super();

			// Log directory
			Check.assumeNotNull(logDirectory, "logDirectoryName not null");
			_logDirectory = logDirectory;
			if (!_logDirectory.exists())
			{
				_logDirectory.mkdirs();
			}

			_logFileKey = createLogFileKey(timestampMillis);

			final String basename = "adempiere_" + _logFileKey + ".log";
			_logFile = new File(_logDirectory, basename);

			logWriter = new FileWriter(_logFile, true); // append=true
		}

		public String getLogFileName()
		{
			return _logFile.getAbsolutePath();
		}

		public void write(final Level logLevel, final String msg) throws IOException
		{
			final FileWriter logWriter = this.logWriter;
			if (logWriter == null)
			{
				return;
			}

			writeHead(logWriter);
			logWriter.write(msg);
			final int recordsCount = _recordsCount++;

			//
			// Flush if needed
			if (logLevel == Level.SEVERE
					|| logLevel == Level.WARNING
					|| recordsCount % 10 == 0)	// flush every 10 records
			{
				flush();
			}

		}

		private final String getLogHeader()
		{
			return getFormatter().getHead(CLogFile.this);
		}

		private final String getLogTail()
		{
			return getFormatter().getTail(CLogFile.this);
		}

		private final void writeHead(final FileWriter logWriter) throws IOException
		{
			if (_doneHeader)
			{
				return;
			}

			logWriter.write(getLogHeader());
			_doneHeader = true;
		}

		public void flush() throws IOException
		{
			final FileWriter logWriter = this.logWriter;
			if (logWriter == null)
			{
				return;
			}
			logWriter.flush();
		}

		@Override
		public void close() throws IOException
		{
			final FileWriter logWriter = this.logWriter;
			this.logWriter = null;

			if (logWriter == null)
			{
				return;
			}

			try
			{
				//
				// Write tail
				writeHead(logWriter);
				logWriter.write(getLogTail());

				// Flush it
				logWriter.flush();
			}
			finally
			{
				Closeables.close(logWriter, true); // swallowIOException=true
			}
		}

		public boolean acceptTimestamp(final long timeMillis)
		{
			final String givenLogFileKey = createLogFileKey(timeMillis);
			return givenLogFileKey.equals(_logFileKey);
		}
	}
}	// CLogFile
