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
import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Manages the export-status lifecycle for scripted-export-conversion log rows.
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
 * <p>After each status write the roll-up across all configs for the same source record is
 * computed and – when the config carries a non-null {@code Status_AD_Column_ID} –
 * written back to the source record's target column.
 *
 * <p>Roll-up precedence: Error ⟫ in-flight (Pending / Enqueued / SendingStarted) ⟫ Sent
 */
@Service
@RequiredArgsConstructor
public class ExternalSystemExportStatusService
{
	private static final Logger log = LogManager.getLogger(ExternalSystemExportStatusService.class);

	@NonNull private final ExternalSystemExportStatusRepository repo;

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
	 * Creates a new log row with status {@link ExternalSystemExportStatus#Pending}.
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
		final ExternalSystemExportStatusLogEntry saved = repo.insert(entry);
		writeRollUpToSourceRecord(saved);
	}

	/**
	 * Creates a new log row with status {@link ExternalSystemExportStatus#Pending} and no PInstance yet.
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
		final ExternalSystemExportStatusLogEntry saved = repo.insert(entry);
		writeRollUpToSourceRecord(saved);
	}

	/**
	 * Finds the most-recent log row for (configId, sourceRecord), sets its PInstance, and transitions
	 * to {@link ExternalSystemExportStatus#Enqueued}.
	 * Called after the process has been successfully enqueued to RabbitMQ and the PInstance is known.
	 * No-op (no throw) when no log row exists for (configId, sourceRecord).
	 */
	public void bindPInstanceAndMarkEnqueued(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			@NonNull final PInstanceId pInstanceId)
	{
		final ExternalSystemExportStatusLogEntry existing = repo.getLatestByConfigAndRecord(configId, sourceRecord).orElse(null);
		if (existing == null)
		{
			log.warn("No log row found for configId={}, sourceRecord={} – skipping Enqueued transition", configId, sourceRecord);
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
	 * Finds the most-recent log row for (configId, sourceRecord) and transitions to
	 * {@link ExternalSystemExportStatus#Invalid}.
	 * Called when the scripted outbound process did not produce a valid Resource.
	 * No-op (no throw) when no log row exists for (configId, sourceRecord).
	 */
	public void markInvalidByRecord(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			@Nullable final String message)
	{
		final ExternalSystemExportStatusLogEntry existing = repo.getLatestByConfigAndRecord(configId, sourceRecord).orElse(null);
		if (existing == null)
		{
			log.warn("No log row found for configId={}, sourceRecord={} – skipping Invalid transition", configId, sourceRecord);
			return;
		}

		final ExternalSystemExportStatusLogEntry updated = existing
				.withStatus(ExternalSystemExportStatus.Invalid)
				.withStatusMessage(message);

		repo.update(updated);
		writeRollUpToSourceRecord(updated);
	}

	/**
	 * Transitions the existing log row for the given pInstance to {@link ExternalSystemExportStatus#Enqueued}.
	 * No-op (no throw) when no log row exists for the pInstance.
	 */
	public void recordEnqueued(@NonNull final PInstanceId pInstanceId)
	{
		updateStatus(pInstanceId, ExternalSystemExportStatus.Enqueued, 0, 0, null);
	}

	/**
	 * Transitions the log row to {@link ExternalSystemExportStatus#Sent}.
	 * No-op (no throw) when no log row exists for the pInstance.
	 */
	public void markSent(@NonNull final PInstanceId pInstanceId, final int httpResponseCode)
	{
		updateStatus(pInstanceId, ExternalSystemExportStatus.Sent, httpResponseCode, 0, null);
	}

	/**
	 * Transitions the log row to {@link ExternalSystemExportStatus#Error}.
	 * No-op (no throw) when no log row exists for the pInstance.
	 *
	 * <p>When {@code adIssueId == 0} (caller does not know the issue ID, e.g. the error-listener
	 * SPI path), the most-recent {@code AD_Issue} stamped with this {@code pInstanceId} is
	 * resolved from the database and linked automatically. This closes the DoD gap: the
	 * {@code AD_Issue} is created by {@code ExternalSystemService.createIssue()} — stamped with
	 * {@code AD_PInstance_ID} — before any listener is notified, so the resolution always finds
	 * the correct record.
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
			final AdIssueId resolved = repo.resolveLatestAdIssueIdByPInstanceId(pInstanceId);
			resolvedAdIssueId = resolved != null ? resolved.getRepoId() : 0;
		}
		updateStatus(pInstanceId, ExternalSystemExportStatus.Error, 0, resolvedAdIssueId, message);
	}

	/**
	 * Transitions the log row to {@link ExternalSystemExportStatus#Invalid}.
	 * No-op (no throw) when no log row exists for the pInstance.
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
	 * Computes the aggregate roll-up status from a set of log entries
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
		final Optional<ExternalSystemExportStatusLogEntry> existing = repo.getLatestByPInstanceId(pInstanceId);
		if (!existing.isPresent())
		{
			// No matching row for this pInstance — silently ignore; the caller may have skipped recordPending
			log.debug("No log row found for pInstanceId={}, status={} – skipping", pInstanceId, newStatus);
			return;
		}

		final ExternalSystemExportStatusLogEntry updated = existing.get()
				.withStatus(newStatus)
				.withHttpResponseCode(httpResponseCode)
				.withAdIssueId(adIssueId)
				.withStatusMessage(message);

		repo.update(updated);
		writeRollUpToSourceRecord(updated);
	}

	/**
	 * Writes the computed roll-up status code into the source record's target column,
	 * if the config has a {@code Status_AD_Column_ID} set.
	 * All failure modes are caught and logged – never throws.
	 */
	private void writeRollUpToSourceRecord(@NonNull final ExternalSystemExportStatusLogEntry entry)
	{
		try
		{
			final String columnName = repo.getStatusColumnNameForConfig(entry.getConfigId());
			if (columnName == null)
			{
				return; // no target column configured – log-rows-only mode
			}

			final TableRecordReference sourceRecord = entry.getSourceRecord();
			final List<ExternalSystemExportStatusLogEntry> allEntries =
					repo.getLatestBySourceRecord(sourceRecord);
			final Map<ExternalSystemScriptedExportConversionConfigId, ExternalSystemExportStatusLogEntry> latestPerConfig =
					new LinkedHashMap<>();
			for (final ExternalSystemExportStatusLogEntry e : allEntries)
			{
				latestPerConfig.putIfAbsent(e.getConfigId(), e);
			}
			final ExternalSystemExportStatus rollUp =
					computeRollUp(ImmutableList.copyOf(latestPerConfig.values()));

			repo.writeStatusToSourceRecord(sourceRecord, columnName, rollUp.getCode());
		}
		catch (final Exception e)
		{
			// never throw – log the failure only
			log.warn("Failed to write roll-up status for entry {}: {}", entry, e.getMessage(), e);
		}
	}
}
