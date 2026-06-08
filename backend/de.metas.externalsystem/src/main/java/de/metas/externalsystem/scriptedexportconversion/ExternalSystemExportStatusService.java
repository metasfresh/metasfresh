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
import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
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
 * <p>Each scripted-export invocation is identified by a unique {@code AD_PInstance_ID}.
 * The service upserts (insert-then-update) the single log row keyed by pInstance and
 * drives it through the status transitions:
 * <pre>
 *   recordPending → recordEnqueued → markSent
 *                              ↘ markError
 *                              ↘ markInvalid
 * </pre>
 *
 * <p>After each update the roll-up across all configs for the same source record is
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
	 */
	public void markError(
			@NonNull final PInstanceId pInstanceId,
			final int adIssueId,
			@Nullable final String message)
	{
		updateStatus(pInstanceId, ExternalSystemExportStatus.Error, 0, adIssueId, message);
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
			// AC-10 safety: no matching row → silently ignore
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
			// 1. Load config record to get Status_AD_Column_ID
			final I_ExternalSystem_Config_ScriptedExportConversion configRecord =
					InterfaceWrapperHelper.load(
							entry.getConfigId().getRepoId(),
							I_ExternalSystem_Config_ScriptedExportConversion.class);

			final int statusAdColumnId = configRecord.getStatus_AD_Column_ID();
			if (statusAdColumnId <= 0)
			{
				return; // no target column configured – log-rows-only mode
			}

			// 2. Resolve the column name from AD_Column_ID.
			// We load the I_AD_Column record directly; this works in both production and
			// unit-test mode (POJOWrapper in-memory) without triggering a CCache reload.
			final org.compiere.model.I_AD_Column adColumn =
					InterfaceWrapperHelper.load(statusAdColumnId, org.compiere.model.I_AD_Column.class);
			if (adColumn == null)
			{
				log.debug("AD_Column {} not found – skipping roll-up write", statusAdColumnId);
				return;
			}
			final String columnName = adColumn.getColumnName();

			// 3. Get the source record reference.
			// Use the table name stored in the reference directly.
			final TableRecordReference sourceRecord = entry.getSourceRecord();

			// 4. Compute roll-up across ALL latest entries for this source record
			final List<ExternalSystemExportStatusLogEntry> latestEntries =
					repo.getLatestBySourceRecord(sourceRecord);
			// Deduplicate: keep only the latest row per configId
			final Map<ExternalSystemScriptedExportConversionConfigId, ExternalSystemExportStatusLogEntry> latestPerConfig =
					new LinkedHashMap<>();
			for (final ExternalSystemExportStatusLogEntry e : latestEntries)
			{
				latestPerConfig.putIfAbsent(e.getConfigId(), e);
			}
			final ExternalSystemExportStatus rollUp =
					computeRollUp(com.google.common.collect.ImmutableList.copyOf(latestPerConfig.values()));

			// 5. Load source record and set the column value
			final Object sourceModel = sourceRecord.getModel();
			InterfaceWrapperHelper.setValue(sourceModel, columnName, rollUp.getCode());
			InterfaceWrapperHelper.saveRecord(sourceModel);
		}
		catch (final Exception e)
		{
			// AC-10: never throw – log the failure only
			log.warn("Failed to write roll-up status for entry {}: {}", entry, e.getMessage(), e);
		}
	}
}
