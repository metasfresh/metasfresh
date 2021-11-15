/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.audit.apirequest;

import com.google.common.collect.ImmutableSet;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.audit.apirequest.request.log.ApiAuditRequestLogDAO;
import de.metas.audit.apirequest.request.log.ApiRequestAuditLog;
import de.metas.audit.apirequest.request.log.StateType;
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
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.model.I_API_Request_Audit_Log;
import org.compiere.model.I_API_Response_Audit;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class ApiAuditLoggable implements ILoggable
{
	private static final ImmutableSet<String> SKIP_LOGGING_FOR_TABLES = ImmutableSet
			.of(I_API_Response_Audit.Table_Name, I_API_Request_Audit.Table_Name, I_API_Request_Audit_Log.Table_Name);

	private static final Logger logger = LogManager.getLogger(ApiAuditLoggable.class);

	private final ApiAuditRequestLogDAO apiAuditRequestLogDAO;
	private final ClientId clientId;
	private final UserId userId;
	private final ApiRequestAuditId apiRequestAuditId;
	private final int bufferSize;

	@Nullable
	private List<ApiRequestAuditLog> buffer;

	@Builder
	public ApiAuditLoggable(
			@NonNull final ApiAuditRequestLogDAO apiAuditRequestLogDAO,
			@NonNull final ClientId clientId,
			@NonNull final UserId userId,
			@NonNull final ApiRequestAuditId apiRequestAuditId,
			final int bufferSize)
	{
		Check.assumeGreaterThanZero(bufferSize, "bufferSize");

		this.apiAuditRequestLogDAO = apiAuditRequestLogDAO;
		this.clientId = clientId;
		this.userId = userId;
		this.apiRequestAuditId = apiRequestAuditId;
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
		final ApiRequestAuditLog logEntry = createLogEntry(msg, msgParameters);

		addToBuffer(logEntry);

		return this;
	}

	@Override
	public ILoggable addTableRecordReferenceLog(final @NonNull ITableRecordReference recordRef, final @NonNull String type, final @NonNull String trxName)
	{
		if (SKIP_LOGGING_FOR_TABLES.contains(recordRef.getTableName()))
		{
			return this;
		}

		final ApiRequestAuditLog logEntry = createLogEntry(recordRef, type, trxName);

		addToBuffer(logEntry);

		return this;
	}

	@Override
	public void flush()
	{
		final List<ApiRequestAuditLog> logEntries = buffer;
		this.buffer = null;

		if (logEntries == null || logEntries.isEmpty())
		{
			return;
		}

		try
		{
			apiAuditRequestLogDAO.insertLogs(logEntries);
		}
		catch (final Exception ex)
		{
			// make sure flush never fails
			logger.warn("Failed saving {} log entries but IGNORED: {}", logEntries.size(), logEntries, ex);
		}
	}

	private ApiRequestAuditLog createLogEntry(@NonNull final String msg, final Object... msgParameters)
	{
		final LoggableWithThrowableUtil.FormattedMsgWithAdIssueId msgAndAdIssueId = LoggableWithThrowableUtil.extractMsgAndAdIssue(msg, msgParameters);

		return ApiRequestAuditLog.builder()
				.message(msgAndAdIssueId.getFormattedMessage())
				.adIssueId(msgAndAdIssueId.getAdIsueId().orElse(null))
				.timestamp(SystemTime.asInstant())
				.apiRequestAuditId(apiRequestAuditId)
				.adClientId(clientId)
				.userId(userId)
				.build();
	}

	private ApiRequestAuditLog createLogEntry(final @NonNull ITableRecordReference recordRef, final @NonNull String type, final @NonNull String trxName)
	{
		return ApiRequestAuditLog.builder()
				.timestamp(SystemTime.asInstant())
				.apiRequestAuditId(apiRequestAuditId)
				.adClientId(clientId)
				.userId(userId)
				.recordReference(recordRef)
				.type(StateType.ofCode(type))
				.trxName(trxName)
				.build();
	}

	private void addToBuffer(final ApiRequestAuditLog logEntry)
	{
		List<ApiRequestAuditLog> buffer = this.buffer;
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