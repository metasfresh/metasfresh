package de.metas.logging;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

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

public class MetasfreshTimeBasedRollingPolicy<E> extends TimeBasedRollingPolicy<E>
{
	private static final String EMPTY_MARKER = "-";
	
	private String logDir = LoggingConstants.DEFAULT_LogDir;
	private String logFilePrefix = LoggingConstants.DEFAULT_LogFilePrefix;
	private String logFileDatePattern = LoggingConstants.DEFAULT_LogFileDatePattern;
	private final String logFileSuffix = ".log";

	private final FilenameFilter logFileNameFilter = new FilenameFilter()
	{
		@Override
		public boolean accept(final File dir, final String name)
		{
			if (logFilePrefix != null && !name.startsWith(logFilePrefix))
			{
				return false;
			}
			if (!name.endsWith(logFileSuffix))
			{
				return false;
			}
			return true;
		}
	};

	public MetasfreshTimeBasedRollingPolicy()
	{
		super();
		updateFileNamePattern();
	}

	public void setLogDir(final String logDir)
	{
		final String logDirNorm = normalizeFilename(logDir);
		if (Check.equals(this.logDir, logDirNorm))
		{
			return;
		}
		if (Check.isEmpty(this.logDir, true))
		{
			addError("Skip setting LogDir to null");
		}

		this.logDir = logDirNorm;
		updateFileNamePattern();
	}

	private static final String normalizeLogDirPrefix(final String logFilePrefix)
	{
		if (Check.isEmpty(logFilePrefix, true))
		{
			return "";
		}

		final String logFilePrefixNorm = logFilePrefix.trim();

		// Special "no prefix" marker.
		// NOTE: we do this because if the LogDirPrefix is empty in logback.xml, the setLogFilePrefix setter won't be called ?!
		if (EMPTY_MARKER.equals(logFilePrefixNorm))
		{
			return "";
		}

		return logFilePrefixNorm;
	}

	private static final String normalizeFilename(final String filename)
	{
		return filename.replaceAll("[^a-zA-Z0-9.-/\\\\]", "_");
	}

	public String getLogDir()
	{
		return logDir;
	}

	public void setLogFilePrefix(final String logFilePrefix)
	{
		final String logFilePrefixNorm = normalizeLogDirPrefix(logFilePrefix);
		if (Check.equals(this.logFilePrefix, logFilePrefixNorm))
		{
			return;
		}

		this.logFilePrefix = logFilePrefixNorm;
		updateFileNamePattern();
	}

	public String getLogFilePrefix()
	{
		return logFilePrefix;
	}

	public void setLogFileDatePattern(final String logFileDatePattern)
	{
		if (Check.equals(this.logFileDatePattern, logFileDatePattern))
		{
			return;
		}
		if (Check.isEmpty(logFileDatePattern, true))
		{
			addError("Skip setting LogFileDatePattern to null");
			return;
		}

		this.logFileDatePattern = logFileDatePattern;
		updateFileNamePattern();
	}

	public String getLogFileDatePattern()
	{
		return logFileDatePattern;
	}

	private final void updateFileNamePattern()
	{
		final StringBuilder fileNamePatternBuilder = new StringBuilder();

		final String logDir = getLogDir();
		if (!Check.isEmpty(logDir, true))
		{
			fileNamePatternBuilder.append(logDir);
			if (!logDir.endsWith("\\") && !logDir.endsWith("/"))
			{
				fileNamePatternBuilder.append(File.separator);
			}
		}

		final String logFilePrefix = getLogFilePrefix();
		fileNamePatternBuilder.append(logFilePrefix);

		if (!Check.isEmpty(logFilePrefix))
		{
			fileNamePatternBuilder.append(".");
		}
		fileNamePatternBuilder.append(getLogFileDatePattern());

		fileNamePatternBuilder.append(logFileSuffix);

		final String fileNamePattern = fileNamePatternBuilder.toString();
		super.setFileNamePattern(fileNamePattern);
		// System.out.println("Using FileNamePattern: " + fileNamePattern);
	}

	@Override
	public void setFileNamePattern(final String fnp)
	{
		throw new UnsupportedOperationException("Setting FileNamePattern directly is not allowed");
	}

	public File getLogDirAsFile()
	{
		if (logDir == null)
		{
			return null;
		}
		return new File(logDir);
	}

	public File getActiveFileOrNull()
	{
		try
		{
			final String filename = getActiveFileName();
			if (filename == null)
			{
				return null;
			}

			final File file = new File(filename).getAbsoluteFile();
			return file;
		}
		catch (Exception e)
		{
			addError("Failed fetching active file name", e);
			return null;
		}
	}

	public List<File> getLogFiles()
	{
		final File logDir = getLogDirAsFile();
		if (logDir != null && logDir.isDirectory())
		{
			final File[] logs = logDir.listFiles(logFileNameFilter);
			for (int i = 0; i < logs.length; i++)
			{
				try
				{
					logs[i] = logs[i].getCanonicalFile();
				}
				catch (Exception e)
				{
				}
			}
			return ImmutableList.copyOf(logs);
		}

		return ImmutableList.of();
	}

	public void flush()
	{
		// TODO
	}
}
