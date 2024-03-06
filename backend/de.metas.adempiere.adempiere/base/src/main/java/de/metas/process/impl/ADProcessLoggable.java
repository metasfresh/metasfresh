/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.process.impl;

import de.metas.error.LoggableWithThrowableUtil;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfoLog;
import de.metas.util.ILoggable;
import lombok.Builder;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
   * This logabble can writes logs to {@code AD_PInstance_Log} just like a JavaProcess. It's supposed to be used when you have an {@code AD_PInstance_ID} and want to log to it, but you are not inside a java-process.
   */
public class ADProcessLoggable implements ILoggable
{
	private static final Logger logger = LogManager.getLogger(ADProcessLoggable.class);

	private final ReentrantLock mainLock = new ReentrantLock();
	private final int bufferSize = 100;
	@Nullable
	private List<ProcessInfoLog> buffer;

	@NonNull
	private final PInstanceId pInstanceId;
	@NonNull
	private final IADPInstanceDAO pInstanceDAO;

	@Builder
	private ADProcessLoggable(
			final @NonNull PInstanceId pInstanceId,
			final @NonNull IADPInstanceDAO pInstanceDAO)
	{
		this.pInstanceId = pInstanceId;
		this.pInstanceDAO = pInstanceDAO;
	}

	/**
	 * @param msgParameters The last parameter may be a {@link Throwable}. In that case, an AD_Issue is created and added to the log.
	 */
	@Override
	public ILoggable addLog(final String msg, final Object... msgParameters)
	{
		if (msg == null)
		{
			logger.warn("Called with msg=null; msgParameters={}; -> ignoring;", msgParameters);
			return this;
		}

		final LoggableWithThrowableUtil.FormattedMsgWithAdIssueId msgAndAdIssueId = LoggableWithThrowableUtil.extractMsgAndAdIssue(msg, msgParameters);

		final ProcessInfoLog processInfoLog = ProcessInfoLog.ofMessage(msgAndAdIssueId.getFormattedMessage());

		addToBuffer(processInfoLog);

		return this;
	}

	@Override
	public void flush()
	{
		List<ProcessInfoLog> logEntries = null;

		try
		{
			//note: synchronized with addToBuffer
			mainLock.lock();

			logEntries = buffer;
			this.buffer = null;

			if (logEntries == null || logEntries.isEmpty())
			{
				return;
			}

			pInstanceDAO.saveProcessInfoLogs(pInstanceId, logEntries);

		}
		catch (final Exception ex)
		{
			// make sure flush never fails
			logger.warn("Failed saving {} log entries but IGNORED: {}", logEntries != null ? logEntries.size() : 0, logEntries, ex);
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private void addToBuffer(final ProcessInfoLog logEntry)
	{
		try
		{
			//making sure there is no flush going on in a diff thread while adding to buffer
			mainLock.lock();

			List<ProcessInfoLog> buffer = this.buffer;
			if (buffer == null)
			{
				buffer = this.buffer = new ArrayList<>(bufferSize);
			}
			buffer.add(logEntry);

			if (buffer.size() >= bufferSize)
			{
				flush();
			}
		}
		finally
		{
			mainLock.unlock();
		}
	}
}
