package de.metas.document.archive.process;

/*
 * #%L
 * de.metas.document.archive.base
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;

/**
 * Collects and aggregates problems
 *
 * @author al
 */
public class ProblemCollector
{
	private static class LogEntry
	{
		private final Object[] params;

		public LogEntry(final Object[] params)
		{
			super();

			this.params = params;
		}

		public Object[] getParams()
		{
			return params;
		}
	}

	private final Map<String, List<LogEntry>> problem2LogEntries = new HashMap<>();

	public ProblemCollector()
	{
		super();
	}

	public void collectException(final String problem, final Object... params)
	{
		List<LogEntry> logEntries = problem2LogEntries.get(problem);
		if (logEntries == null)
		{
			logEntries = new ArrayList<>();
			problem2LogEntries.put(problem, logEntries);
		}

		final LogEntry logEntry = new LogEntry(params);
		logEntries.add(logEntry);
	}

	public Exception getCollectedExceptionsOrNull(final Properties ctx)
	{
		//
		// Services
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final StringBuilder messageBuilder = new StringBuilder();

		for (final Entry<String, List<LogEntry>> problem2LogEntry : problem2LogEntries.entrySet())
		{
			final String message = problem2LogEntry.getKey();
			for (final LogEntry logEntry : problem2LogEntry.getValue())
			{
				final String messageTrl = msgBL.getMsg(ctx, message, logEntry.getParams());
				messageBuilder.append("\n").append(messageTrl);
			}
		}

		final String fullMessage = messageBuilder.toString();
		if (Check.isEmpty(fullMessage))
		{
			return null; // no exception
		}

		final Exception ex = new Exception(fullMessage);
		return ex;
	}
}
