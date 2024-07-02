/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.importfile.log;

import de.metas.audit.apirequest.ApiAuditLoggable;
import de.metas.banking.importfile.BankStatementImportFileId;
import de.metas.common.util.time.SystemTime;
import de.metas.error.LoggableWithThrowableUtil;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class BankStatementImportFileLoggable implements ILoggable
{
	private static final Logger logger = LogManager.getLogger(ApiAuditLoggable.class);

	private final BankStatementImportFileLogRepository bankStatementImportFileLogRepository;
	private final BankStatementImportFileId bankStatementImportFileId;
	private final ClientId clientId;
	private final UserId userId;
	private final int bufferSize;

	@Nullable
	private List<BankStatementImportFileRequestLog> buffer;

	@Builder
	public BankStatementImportFileLoggable(
			@NonNull final BankStatementImportFileLogRepository bankStatementImportFileLogRepository,
			@NonNull final BankStatementImportFileId bankStatementImportFileId,
			@NonNull final ClientId clientId,
			@NonNull final UserId userId,
			final int bufferSize)
	{
		Check.assumeGreaterThanZero(bufferSize, "bufferSize");

		this.bankStatementImportFileLogRepository = bankStatementImportFileLogRepository;
		this.bankStatementImportFileId = bankStatementImportFileId;
		this.clientId = clientId;
		this.userId = userId;
		this.bufferSize = bufferSize;
	}

	@Override
	public ILoggable addLog(@Nullable final String msg, final Object... msgParameters)
	{
		if (msg == null)
		{
			logger.warn("Called with msg=null; msgParameters={}; -> ignoring;", msgParameters);
			return this;
		}
		final BankStatementImportFileRequestLog logEntry = createLogEntry(msg, msgParameters);

		addToBuffer(logEntry);

		return this;
	}

	@Override
	public void flush()
	{
		final List<BankStatementImportFileRequestLog> logEntries = buffer;
		this.buffer = null;

		if (logEntries == null || logEntries.isEmpty())
		{
			return;
		}

		try
		{
			bankStatementImportFileLogRepository.insertLogs(logEntries);
		}
		catch (final Exception ex)
		{
			// make sure flush never fails
			logger.warn("Failed saving {} log entries but IGNORED: {}", logEntries.size(), logEntries, ex);
		}
	}

	@NonNull
	private BankStatementImportFileRequestLog createLogEntry(@NonNull final String msg, final Object... msgParameters)
	{
		final LoggableWithThrowableUtil.FormattedMsgWithAdIssueId msgAndAdIssueId = LoggableWithThrowableUtil.extractMsgAndAdIssue(msg, msgParameters);

		return BankStatementImportFileRequestLog.builder()
				.message(msgAndAdIssueId.getFormattedMessage())
				.adIssueId(msgAndAdIssueId.getAdIsueId().orElse(null))
				.timestamp(SystemTime.asInstant())
				.bankStatementImportFileId(bankStatementImportFileId)
				.clientId(clientId)
				.userId(userId)
				.build();
	}

	private void addToBuffer(@NonNull final BankStatementImportFileRequestLog logEntry)
	{
		List<BankStatementImportFileRequestLog> buffer = this.buffer;
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
}
