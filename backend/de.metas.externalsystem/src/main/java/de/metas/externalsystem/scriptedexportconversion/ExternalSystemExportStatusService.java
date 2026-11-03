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
import java.util.Optional;

/**
 * State machine for the scripted-export-conversion status row.
 *
 * <p>States (one row per export ATTEMPT): Pending → Enqueued → Sent | Error | Invalid; plus the
 * terminal DontSend (evaluated but excluded by the config WhereClause). A re-send starts a NEW
 * attempt row; the roll-up and re-send selection consider the latest attempt per config.
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
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.DontSend)
				.build());
	}

	/**
	 * Manually changes the export status of (config, sourceRecord) to {@code targetStatus} by recording a
	 * NEW attempt row, stamped with the triggering process instance ({@code pInstanceId}) for a who/when
	 * audit trail. Prior attempt rows are kept as history. Used by the "Change EPCIS Export Status" process.
	 */
	public void recordManualStatusChange(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			@NonNull final ExternalSystemExportStatus targetStatus,
			@NonNull final PInstanceId pInstanceId)
	{
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(targetStatus)
				.pInstanceId(pInstanceId)
				.build());
	}

	/**
	 * The current status per config for the given source record: the LATEST attempt row per config
	 * (one row per config, all states). Deduped via {@link ExternalSystemExportStatusRepository#getLatestPerConfigBySourceRecord}
	 * — NOT the raw per-attempt history, so a config that has been re-sent (>=2 rows) reports only its
	 * newest attempt, never a stale earlier one.
	 */
	@NonNull
	public List<ScriptedExportConversionStatus> getLatestStatusesBySourceRecord(@NonNull final TableRecordReference sourceRecord)
	{
		return repo.getLatestPerConfigBySourceRecord(sourceRecord);
	}

	/**
	 * Records that the source record was included by the config WhereClause.
	 * Status {@link ExternalSystemExportStatus#Pending}, no PInstance yet.
	 */
	public void recordPending(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.build());
	}

	/**
	 * Records a re-send as a NEW attempt row in state {@link ExternalSystemExportStatus#Pending} with
	 * {@code IsResend=Y}. Prior attempts (the original send + any earlier re-sends) remain as history;
	 * the aggregated status and the lifecycle transitions then track this newest attempt.
	 */
	public void recordPendingAsResend(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.isResend(true)
				.build());
	}

	/**
	 * Returns the config IDs whose latest status row for the given source record is re-sendable:
	 * a terminal {@link ExternalSystemExportStatus#Error}/{@link ExternalSystemExportStatus#Invalid} or a
	 * suppressed {@link ExternalSystemExportStatus#DontSend} (re-offered so a suppressed record can be
	 * re-evaluated), OR an operator-parked {@link ExternalSystemExportStatus#Pending} — a Pending row that
	 * carries an {@code AD_PInstance_ID}, i.e. was set deliberately via the "Change EPCIS Export Status"
	 * action. {@link ExternalSystemExportStatus#Sent} and the actively-in-flight
	 * {@link ExternalSystemExportStatus#Enqueued}/{@link ExternalSystemExportStatus#SendingStarted} rows —
	 * and a transient auto-flow Pending (no PInstance, momentarily awaiting Enqueued) — are excluded to
	 * prevent double-sending.
	 */
	@NonNull
	public List<ExternalSystemScriptedExportConversionConfigId> getResendableConfigsBySourceRecord(
			@NonNull final TableRecordReference sourceRecord)
	{
		// Only the LATEST attempt per config decides. Without that reduction a config whose latest attempt
		// already SUCCEEDED would still be offered because an OLDER attempt errored — re-triggering an
		// already-delivered export.
		return repo.getLatestPerConfigBySourceRecord(sourceRecord).stream()
				.filter(ExternalSystemExportStatusService::isResendableAttempt)
				.map(ScriptedExportConversionStatus::getConfigId)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Whether a config's LATEST attempt row may be re-sent. Terminal Error/Invalid/DontSend qualify on
	 * status alone ({@link ExternalSystemExportStatus#isResendable()}); an operator-parked Pending qualifies
	 * per row (see {@link #isOperatorParkedPending(ScriptedExportConversionStatus)}).
	 */
	private static boolean isResendableAttempt(@NonNull final ScriptedExportConversionStatus latest)
	{
		return latest.getStatus().isResendable() || isOperatorParkedPending(latest);
	}

	/**
	 * Whether the attempt is a {@link ExternalSystemExportStatus#Pending} row that an operator parked
	 * deliberately via the "Change EPCIS Export Status" action — the distinguishing mark is the
	 * {@code AD_PInstance_ID} that {@link #recordManualStatusChange} stamps on it. Such a row is NOT in
	 * flight (nothing is being sent), so a re-send is the first send, not a double-send.
	 *
	 * <p>A Pending row WITHOUT a PInstance is the transient auto-flow attempt that
	 * {@link #recordPending}/{@link #recordPendingAsResend} write momentarily before the Pending→Enqueued
	 * transition; re-sending that one WOULD double-send, so it does not qualify.
	 */
	private static boolean isOperatorParkedPending(@NonNull final ScriptedExportConversionStatus attempt)
	{
		return attempt.getStatus().isPending() && attempt.getPInstanceId() != null;
	}

	/**
	 * Returns all config IDs in a re-triggerable state for the given source record — the force-resend
	 * selection ({@code IsOnlyNotSentSuccessfully=N}), which deliberately includes
	 * {@link ExternalSystemExportStatus#Sent}: re-triggering a successfully-sent record is intentional
	 * (e.g. the external system lost its data).
	 * <p>
	 * Only the config's LATEST attempt decides. Excluded:
	 * <ul>
	 *   <li>{@link ExternalSystemExportStatus#DontSend} — WhereClause explicitly excluded this record; must not be re-triggered.</li>
	 *   <li>Actively-in-flight attempts — {@link ExternalSystemExportStatus#Enqueued},
	 *       {@link ExternalSystemExportStatus#SendingStarted}, and a transient auto-flow
	 *       {@link ExternalSystemExportStatus#Pending} — already being processed; re-triggering would cause a duplicate send.</li>
	 * </ul>
	 * An operator-parked Pending ({@link #isOperatorParkedPending}) is NOT in flight and IS included — as in
	 * {@link #getResendableConfigsBySourceRecord}; otherwise this broader mode would send LESS than the
	 * not-yet-sent-only mode and silently skip a shipment the operator had parked.
	 * <p>
	 * DontSend is the one state this mode deliberately does NOT share with
	 * {@link #getResendableConfigsBySourceRecord}: there it is re-offered so a re-send can RE-EVALUATE a
	 * suppressed record against the WhereClause, whereas this mode re-transmits without re-evaluating — and
	 * the WhereClause explicitly excluded that record, so firing the export anyway would contradict the config.
	 */
	@NonNull
	public List<ExternalSystemScriptedExportConversionConfigId> getMatchingConfigIdsBySourceRecord(
			@NonNull final TableRecordReference sourceRecord)
	{
		return repo.getLatestPerConfigBySourceRecord(sourceRecord).stream()
				.filter(latest -> !latest.getStatus().isDontSend())
				.filter(latest -> !latest.getStatus().isProcessing())
				.filter(latest -> !latest.getStatus().isPending() || isOperatorParkedPending(latest))
				.map(ScriptedExportConversionStatus::getConfigId)
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
	// Queries
	// ------------------------------------------------------------------

	/**
	 * Returns the status row bound to the given {@code pInstanceId}, if any — the read-only
	 * counterpart of the pInstance-keyed {@code markXxx} transitions below. Callers use this to
	 * resolve the (config, sourceRecord) that a successful/failed invocation belongs to.
	 */
	@NonNull
	public Optional<ScriptedExportConversionStatus> getLatestByPInstanceId(@NonNull final PInstanceId pInstanceId)
	{
		return repo.getLatestByPInstanceId(pInstanceId);
	}

	/**
	 * Returns the distinct config IDs whose <b>active</b> status row for the source record is in-flight
	 * (Enqueued or SendingStarted — dispatched but not yet confirmed/failed). Used to prevent
	 * reversing/reactivating a source document whose export is still in-flight (it may already be at
	 * the receiver). Only active rows count, so deactivating a stuck status row releases the document.
	 */
	@NonNull
	public List<ExternalSystemScriptedExportConversionConfigId> getInflightConfigsBySourceRecord(
			@NonNull final TableRecordReference sourceRecord)
	{
		return repo.getInflightConfigsBySourceRecord(sourceRecord);
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
