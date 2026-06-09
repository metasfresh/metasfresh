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
import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Issue;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Manages the export-status lifecycle for scripted-export-conversion status rows.
 *
 * <p>The normal flow for an AFTER_COMPLETE triggered export is:
 * <pre>
 *   recordPending(configId, sourceRecord)          ← interceptor, no PInstance yet
 *     → bindPInstanceAndMarkEnqueued(...)          ← after successful RabbitMQ dispatch
 *       → markSent / markError                    ← driven by Camel response listener
 * </pre>
 * On process failure before dispatch:
 * <pre>
 *   recordPending(configId, sourceRecord)
 *     → markInvalidByRecord(configId, sourceRecord, msg)
 * </pre>
 * The legacy {@code recordPending(pInstanceId, configId, sourceRecord)} / {@code recordEnqueued(pInstanceId)}
 * overloads remain available for callers that obtain the PInstance before creating the Pending row.
 *
 * <p>TODO(R2.2): deep state-machine rework — this service retains its old structure for compile
 * compatibility; the full behavioural rework (roll-up via virtual column, AD_Issue via IErrorManager,
 * upsert-first semantics) is scheduled for R2.2.
 */
@Service
@RequiredArgsConstructor
public class ExternalSystemExportStatusService
{
	private static final Logger log = LogManager.getLogger(ExternalSystemExportStatusService.class);

	@NonNull private final ExternalSystemExportStatusRepository repo;

	// TODO(R2.2): inject ExternalSystemScriptedExportConversionRepository for config-column lookups
	// TODO(R2.2): inject IErrorManager for AD_Issue resolution (replace inline AD_Issue query)

	@VisibleForTesting
	public static ExternalSystemExportStatusService newInstanceForUnitTesting(
			@NonNull final ExternalSystemExportStatusRepository repo)
	{
		Adempiere.assertUnitTestMode();
		return new ExternalSystemExportStatusService(repo);
	}

	// ------------------------------------------------------------------
	// Lifecycle methods
	// ------------------------------------------------------------------

	/**
	 * Creates (or updates) the status row with status {@link ExternalSystemExportStatus#Pending}.
	 */
	public void recordPending(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		final ExternalSystemExportStatusLogEntry entry = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(pInstanceId)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.build();
		final ExternalSystemExportStatusLogEntry saved = repo.upsert(entry);
		writeRollUpToSourceRecord(saved);
	}

	/**
	 * Creates (or updates) the status row with status {@link ExternalSystemExportStatus#Pending} and no PInstance yet.
	 * Called at AFTER_COMPLETE time in the interceptor, before the process is invoked.
	 * Use {@link #bindPInstanceAndMarkEnqueued} or {@link #markInvalidByRecord} after the process completes.
	 */
	public void recordPending(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		final ExternalSystemExportStatusLogEntry entry = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(null)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.build();
		final ExternalSystemExportStatusLogEntry saved = repo.upsert(entry);
		writeRollUpToSourceRecord(saved);
	}

	/**
	 * Returns the distinct config IDs whose status row for the given source record
	 * is not yet fully processed (i.e. neither {@link ExternalSystemExportStatus#Sent} nor
	 * {@link ExternalSystemExportStatus#DontSend}).
	 * Delegates to the repository; exposed here so the re-send AD process can inject only
	 * services (no repositories).
	 */
	@NonNull
	public List<ExternalSystemScriptedExportConversionConfigId> getConfigsWithNonSentAttemptBySourceRecord(
			@NonNull final TableRecordReference sourceRecord)
	{
		return repo.getConfigsWithNonSentAttemptBySourceRecord(sourceRecord);
	}

	/**
	 * Creates (or updates) the status row with status {@link ExternalSystemExportStatus#Pending} and {@code IsResend=Y}.
	 * Called by the re-send AD process before invoking the scripted-export-conversion action for each config.
	 *
	 * <p>TODO(R2.2): evaluate whether re-send should use insert (new row for history) or upsert; for now uses upsert.
	 */
	public void recordPendingAsResend(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		final ExternalSystemExportStatusLogEntry entry = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(null)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.isResend(true)
				.build();
		final ExternalSystemExportStatusLogEntry saved = repo.insert(entry);
		writeRollUpToSourceRecord(saved);
	}

	/**
	 * Finds the status row for (configId, sourceRecord), sets its PInstance, and transitions
	 * to {@link ExternalSystemExportStatus#Enqueued}.
	 * No-op (no throw) when no status row exists for (configId, sourceRecord).
	 */
	public void bindPInstanceAndMarkEnqueued(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			@NonNull final PInstanceId pInstanceId)
	{
		final ExternalSystemExportStatusLogEntry existing = repo.getLatestByConfigAndRecord(configId, sourceRecord).orElse(null);
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

		final ExternalSystemExportStatusLogEntry updated = existing
				.withPInstanceId(pInstanceId)
				.withStatus(ExternalSystemExportStatus.Enqueued)
				.withHttpResponseCode(0)
				.withAdIssueId(0)
				.withStatusMessage(null);

		repo.update(updated);
		writeRollUpToSourceRecord(updated);
	}

	/**
	 * Finds the status row for (configId, sourceRecord) and transitions to
	 * {@link ExternalSystemExportStatus#Invalid}.
	 * No-op (no throw) when no status row exists for (configId, sourceRecord).
	 */
	public void markInvalidByRecord(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			@Nullable final String message)
	{
		final ExternalSystemExportStatusLogEntry existing = repo.getLatestByConfigAndRecord(configId, sourceRecord).orElse(null);
		if (existing == null)
		{
			log.warn("No status row found for configId={}, sourceRecord={} – skipping Invalid transition", configId, sourceRecord);
			return;
		}

		final ExternalSystemExportStatusLogEntry updated = existing
				.withStatus(ExternalSystemExportStatus.Invalid)
				.withStatusMessage(message);

		repo.update(updated);
		writeRollUpToSourceRecord(updated);
	}

	/**
	 * Transitions the existing status row for the given pInstance to {@link ExternalSystemExportStatus#Enqueued}.
	 * No-op (no throw) when no status row exists for the pInstance.
	 */
	public void recordEnqueued(@NonNull final PInstanceId pInstanceId)
	{
		updateStatus(pInstanceId, ExternalSystemExportStatus.Enqueued, 0, 0, null);
	}

	/**
	 * Transitions the status row to {@link ExternalSystemExportStatus#Sent}.
	 * No-op (no throw) when no status row exists for the pInstance.
	 */
	public void markSent(@NonNull final PInstanceId pInstanceId, final int httpResponseCode)
	{
		updateStatus(pInstanceId, ExternalSystemExportStatus.Sent, httpResponseCode, 0, null);
	}

	/**
	 * Transitions the status row to {@link ExternalSystemExportStatus#Error}.
	 * No-op (no throw) when no status row exists for the pInstance.
	 *
	 * <p>When {@code adIssueId == 0}, resolves the most-recent {@code AD_Issue} stamped with
	 * this {@code pInstanceId} from the database.
	 *
	 * <p>TODO(R2.2): replace inline AD_Issue query with IErrorManager.getByPInstanceId().
	 */
	public void markError(
			@NonNull final PInstanceId pInstanceId,
			final int adIssueId,
			@Nullable final String message)
	{
		final int resolvedAdIssueId;
		if (adIssueId > 0)
		{
			resolvedAdIssueId = adIssueId;
		}
		else
		{
			// TODO(R2.2): replace with IErrorManager call
			resolvedAdIssueId = resolveLatestAdIssueIdByPInstanceId(pInstanceId);
		}
		updateStatus(pInstanceId, ExternalSystemExportStatus.Error, 0, resolvedAdIssueId, message);
	}

	/**
	 * Transitions the status row to {@link ExternalSystemExportStatus#Invalid}.
	 * No-op (no throw) when no status row exists for the pInstance.
	 */
	public void markInvalid(
			@NonNull final PInstanceId pInstanceId,
			@Nullable final String message)
	{
		updateStatus(pInstanceId, ExternalSystemExportStatus.Invalid, 0, 0, message);
	}

	// ------------------------------------------------------------------
	// Roll-up
	// ------------------------------------------------------------------

	/**
	 * Computes the aggregate roll-up status from a set of status entries
	 * (one per config, typically the latest attempt).
	 *
	 * <p>Precedence: Error ⟫ in-flight (Pending/Enqueued/SendingStarted) ⟫ Sent
	 * Returns {@link ExternalSystemExportStatus#Pending} when the list is empty.
	 */
	@NonNull
	public ExternalSystemExportStatus computeRollUp(@NonNull final List<ExternalSystemExportStatusLogEntry> latestEntries)
	{
		if (latestEntries.isEmpty())
		{
			return ExternalSystemExportStatus.Pending;
		}

		ExternalSystemExportStatus result = ExternalSystemExportStatus.Sent;

		for (final ExternalSystemExportStatusLogEntry entry : latestEntries)
		{
			final ExternalSystemExportStatus s = entry.getStatus();
			if (s.isErrorOrInvalid())
			{
				// Error / Invalid is the highest priority – return immediately
				return s.isError() ? ExternalSystemExportStatus.Error : ExternalSystemExportStatus.Invalid;
			}
			if (s.isPending() || s.isProcessing())
			{
				// in-flight beats Sent
				result = s;
			}
			// Sent stays as-is
		}
		return result;
	}

	// ------------------------------------------------------------------
	// Internal helpers
	// ------------------------------------------------------------------

	private void updateStatus(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final ExternalSystemExportStatus newStatus,
			final int httpResponseCode,
			final int adIssueId,
			@Nullable final String message)
	{
		final ExternalSystemExportStatusLogEntry existing = repo.getLatestByPInstanceId(pInstanceId).orElse(null);
		if (existing == null)
		{
			// No matching row for this pInstance — silently ignore; the caller may have skipped recordPending
			log.debug("No status row found for pInstanceId={}, status={} – skipping", pInstanceId, newStatus);
			return;
		}

		final ExternalSystemExportStatusLogEntry updated = existing
				.withStatus(newStatus)
				.withHttpResponseCode(httpResponseCode)
				.withAdIssueId(adIssueId)
				.withStatusMessage(message);

		repo.update(updated);
		writeRollUpToSourceRecord(updated);
	}

	/**
	 * Writes the computed roll-up status code into the source record's target column.
	 *
	 * <p>TODO(R2.2): The _Status table exposes ExportStatus as a virtual column on the source record;
	 * write-back via a separate column may no longer be needed. This method is a stub that
	 * preserves existing behaviour (no-op when no column configured) until R2.2 rework.
	 * All failure modes are caught and logged – never throws.
	 */
	private void writeRollUpToSourceRecord(@NonNull final ExternalSystemExportStatusLogEntry entry)
	{
		// TODO(R2.2): replace with virtual-column approach or delegate to ExternalSystemScriptedExportConversionRepository
		// For now: no-op — the Status_AD_Column_ID roll-up write-back is removed with the old Log table.
		// The virtual column on _Status makes this unnecessary.
	}

	/**
	 * Resolves the most-recent {@code AD_Issue_ID} created for the given {@code AD_PInstance_ID}.
	 *
	 * <p>TODO(R2.2): replace with {@code IErrorManager.getByPInstanceId()} call.
	 */
	private int resolveLatestAdIssueIdByPInstanceId(@NonNull final PInstanceId pInstanceId)
	{
		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_PInstance_ID, pInstanceId.getRepoId())
				.orderByDescending(I_AD_Issue.COLUMNNAME_AD_Issue_ID)
				.create()
				.firstOptional(I_AD_Issue.class)
				.map(issue -> issue.getAD_Issue_ID())
				.orElse(0);
	}
}
