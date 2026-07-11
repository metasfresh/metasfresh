/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

/**
 * State machine for the scripted-export-conversion status row.
 *
 * <p>States (one row per config+record): Pending → Enqueued → Sent | Error | Invalid; plus the
 * terminal DontSend (evaluated but excluded by the config WhereClause).
 */
@Service
@RequiredArgsConstructor
public class ExternalSystemExportStatusService
{
	private static final Logger log = LogManager.getLogger(ExternalSystemExportStatusService.class);

	@NonNull private final ExternalSystemExportStatusRepository repo;

	// IErrorManager is an ISingletonService (obtained via Services.get), not a Spring bean — it must NOT be a
	// constructor parameter, else this @Service fails to wire at context boot with NoSuchBeanDefinitionException.
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	@VisibleForTesting
	public static ExternalSystemExportStatusService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ExternalSystemExportStatusService(
				SpringContextHolder.getBeanOrSupply(ExternalSystemExportStatusRepository.class, ExternalSystemExportStatusRepository::newInstanceForUnitTesting));
	}

	// ------------------------------------------------------------------
	// Entry transitions (config + record)
	// ------------------------------------------------------------------

	/**
	 * Records that the source record was evaluated but excluded by the config WhereClause.
	 * Terminal state {@link ExternalSystemExportStatus#DontSend}.
	 */
	public void recordDontSend(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		repo.upsert(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.DontSend)
				.build());
	}

	/**
	 * Records that the source record was included by the config WhereClause.
	 * Status {@link ExternalSystemExportStatus#Pending}, no PInstance yet.
	 */
	public void recordPending(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		repo.upsert(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.build());
	}

	/**
	 * Flips the (config, record) status row back to {@link ExternalSystemExportStatus#Pending} with
	 * {@code IsResend=Y} using an in-place upsert — matching the single-row-per-key design.
	 * Creates the row if somehow absent; otherwise updates the existing row so no duplicate key
	 * can arise.
	 */
	public void recordPendingAsResend(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		repo.upsert(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.isResend(true)
				.build());
	}

	/**
	 * Returns the config IDs whose status row for the given source record is in a terminal-failure
	 * state ({@link ExternalSystemExportStatus#Error} or {@link ExternalSystemExportStatus#Invalid}).
	 * In-flight rows (Pending, Enqueued, SendingStarted) are excluded to prevent double-sending.
	 */
	@NonNull
	public List<ExternalSystemScriptedExportConversionConfigId> getResendableConfigsBySourceRecord(
			@NonNull final TableRecordReference sourceRecord)
	{
		return repo.getLatestBySourceRecord(sourceRecord)
				.stream()
				.filter(s -> s.getStatus().isErrorOrInvalid())
				.map(ScriptedExportConversionStatus::getConfigId)
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Binds the PInstance to the (config, record) status row and transitions it to the in-flight
	 * {@link ExternalSystemExportStatus#Enqueued} state.
	 *
	 * <p>At-most-one-in-flight guard: skips the transition unless the existing row is Pending,
	 * so a record that is already in-flight is not double-enqueued.
	 * No-op (no throw) when no status row exists for (config, record).
	 */
	public void markEnqueued(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			@NonNull final PInstanceId pInstanceId)
	{
		final ScriptedExportConversionStatus existing = repo.getLatestByConfigAndRecord(configId, sourceRecord).orElse(null);
		if (existing == null)
		{
			log.warn("No status row found for configId={}, sourceRecord={} – skipping Enqueued transition", configId, sourceRecord);
			return;
		}
		if (!existing.getStatus().isPending())
		{
			log.warn("Skipping Enqueued transition for configId={}, sourceRecord={} — existing status is {} (expected Pending)", configId, sourceRecord, existing.getStatus());
			return;
		}

		repo.update(existing
				.withPInstanceId(pInstanceId)
				.withStatus(ExternalSystemExportStatus.Enqueued)
				.withHttpResponseCode(null)
				.withAdIssueId(null)
				.withStatusMessage(null));
	}

	/**
	 * Pre-send revalidation failed — transitions the (config, record) row to the terminal
	 * {@link ExternalSystemExportStatus#Invalid} state.
	 * No-op (no throw) when no status row exists for (config, record).
	 */
	public void markInvalidByRecord(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			@Nullable final String message)
	{
		final ScriptedExportConversionStatus existing = repo.getLatestByConfigAndRecord(configId, sourceRecord).orElse(null);
		if (existing == null)
		{
			log.warn("No status row found for configId={}, sourceRecord={} – skipping Invalid transition", configId, sourceRecord);
			return;
		}

		repo.update(existing
				.withStatus(ExternalSystemExportStatus.Invalid)
				.withStatusMessage(message));
	}

	// ------------------------------------------------------------------
	// Outcome transitions (pInstance)
	// ------------------------------------------------------------------

	/**
	 * Scripted-adapter returned 2xx — transitions the pInstance-bound row to
	 * {@link ExternalSystemExportStatus#Sent}.
	 * No-op (no throw) when no status row exists for the pInstance.
	 */
	public void markSent(@NonNull final PInstanceId pInstanceId, @NonNull final HttpStatus httpStatus)
	{
		updateStatus(pInstanceId, ExternalSystemExportStatus.Sent, httpStatus, null, null);
	}

	/**
	 * Any send failure — transitions the pInstance-bound row to the terminal
	 * {@link ExternalSystemExportStatus#Error} state and links an {@link AdIssueId}.
	 * When {@code adIssueId} is null, an issue is created via {@link IErrorManager}.
	 * No-op (no throw) when no status row exists for the pInstance.
	 */
	public void markError(
			@NonNull final PInstanceId pInstanceId,
			@Nullable final AdIssueId adIssueId,
			@Nullable final String message)
	{
		final ScriptedExportConversionStatus existing = repo.getLatestByPInstanceId(pInstanceId).orElse(null);
		if (existing == null)
		{
			log.debug("No status row found for pInstanceId={}, status=Error – skipping", pInstanceId);
			return;
		}

		final AdIssueId resolvedAdIssueId = adIssueId != null
				? adIssueId
				: errorManager.createIssue(IssueCreateRequest.builder()
						.summary(message)
						.sourceClassname(ExternalSystemExportStatusService.class.getName())
						.build());

		repo.update(existing
				.withStatus(ExternalSystemExportStatus.Error)
				.withHttpResponseCode(null)
				.withAdIssueId(resolvedAdIssueId)
				.withStatusMessage(message));
	}

	/**
	 * Transitions the pInstance-bound row to the terminal {@link ExternalSystemExportStatus#Invalid} state.
	 * No-op (no throw) when no status row exists for the pInstance.
	 */
	public void markInvalid(
			@NonNull final PInstanceId pInstanceId,
			@Nullable final String message)
	{
		updateStatus(pInstanceId, ExternalSystemExportStatus.Invalid, null, null, message);
	}

	// ------------------------------------------------------------------
	// Roll-up
	// ------------------------------------------------------------------

	/**
	 * Computes the aggregate roll-up status from the latest status per config.
	 * Precedence: Error/Invalid ⟫ in-flight (Pending/Enqueued/SendingStarted) ⟫ Sent.
	 * Returns {@link ExternalSystemExportStatus#Pending} when the list is empty.
	 */
	@NonNull
	public ExternalSystemExportStatus computeRollUp(@NonNull final List<ScriptedExportConversionStatus> latestEntries)
	{
		if (latestEntries.isEmpty())
		{
			return ExternalSystemExportStatus.Pending;
		}

		ExternalSystemExportStatus result = ExternalSystemExportStatus.Sent;
		for (final ScriptedExportConversionStatus entry : latestEntries)
		{
			final ExternalSystemExportStatus s = entry.getStatus();
			if (s.isErrorOrInvalid())
			{
				return s.isError() ? ExternalSystemExportStatus.Error : ExternalSystemExportStatus.Invalid;
			}
			if (s.isPending() || s.isProcessing())
			{
				result = s;
			}
		}
		return result;
	}

	// ------------------------------------------------------------------
	// Internal helpers
	// ------------------------------------------------------------------

	private void updateStatus(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final ExternalSystemExportStatus newStatus,
			@Nullable final HttpStatus httpResponseCode,
			@Nullable final AdIssueId adIssueId,
			@Nullable final String message)
	{
		final ScriptedExportConversionStatus existing = repo.getLatestByPInstanceId(pInstanceId).orElse(null);
		if (existing == null)
		{
			log.debug("No status row found for pInstanceId={}, status={} – skipping", pInstanceId, newStatus);
			return;
		}

		repo.update(existing
				.withStatus(newStatus)
				.withHttpResponseCode(httpResponseCode)
				.withAdIssueId(adIssueId)
				.withStatusMessage(message));
	}
}
