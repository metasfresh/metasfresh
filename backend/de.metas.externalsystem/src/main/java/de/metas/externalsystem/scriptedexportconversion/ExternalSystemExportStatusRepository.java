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
import de.metas.externalsystem.model.I_ExternalSystem_ScriptedExportConversion_Status;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Repository for {@code ExternalSystem_ScriptedExportConversion_Status}.
 *
 * <p>Repository Tables: ExternalSystem_ScriptedExportConversion_Status
 *
 * <p>Repository Cluster: sole owner of {@code ExternalSystem_ScriptedExportConversion_Status}.
 * Service layer: {@link ExternalSystemExportStatusService}.
 *
 * <p>Grain: ONE row per (ExternalSystem_Config_ScriptedExportConversion_ID, AD_Table_ID, Record_ID).
 * All status transitions are done via in-place update on that single row (upsert).
 */
@Repository
public class ExternalSystemExportStatusRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static ExternalSystemExportStatusRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ExternalSystemExportStatusRepository();
	}

	// ------------------------------------------------------------------
	// Upsert
	// ------------------------------------------------------------------

	/**
	 * Upserts the status row for the given (config, sourceRecord) key.
	 * Creates the row if it does not exist yet; updates it in-place otherwise.
	 * Returns the persisted VO (with the Status row ID set in {@code logId}).
	 */
	@NonNull
	public ExternalSystemExportStatusLogEntry upsert(@NonNull final ExternalSystemExportStatusLogEntry entry)
	{
		final I_ExternalSystem_ScriptedExportConversion_Status existing =
				queryStatusRecord(entry.getConfigId(), entry.getSourceRecord()).orElse(null);

		if (existing != null)
		{
			updateRecord(existing, entry);
			InterfaceWrapperHelper.saveRecord(existing);
			return entry.withLogId(existing.getExternalSystem_ScriptedExportConversion_Status_ID());
		}
		else
		{
			final I_ExternalSystem_ScriptedExportConversion_Status record =
					InterfaceWrapperHelper.newInstance(I_ExternalSystem_ScriptedExportConversion_Status.class);
			updateRecord(record, entry);
			InterfaceWrapperHelper.saveRecord(record);
			return entry.withLogId(record.getExternalSystem_ScriptedExportConversion_Status_ID());
		}
	}

	/**
	 * Inserts a new status row and returns the persisted entry (with its new logId).
	 *
	 * <p>Note: for the _Status table the grain is one row per (config, table, record),
	 * so callers that want upsert semantics should use {@link #upsert(ExternalSystemExportStatusLogEntry)}.
	 * This method is kept for callers that explicitly need a fresh row (e.g. re-send creates
	 * a new row to preserve history — TODO(R2.2): evaluate if re-send should upsert instead).
	 */
	@NonNull
	public ExternalSystemExportStatusLogEntry insert(@NonNull final ExternalSystemExportStatusLogEntry entry)
	{
		final I_ExternalSystem_ScriptedExportConversion_Status record =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_ScriptedExportConversion_Status.class);
		updateRecord(record, entry);
		InterfaceWrapperHelper.saveRecord(record);
		return entry.withLogId(record.getExternalSystem_ScriptedExportConversion_Status_ID());
	}

	/**
	 * Updates the status row identified by its {@code logId} (Status row PK).
	 */
	public void update(@NonNull final ExternalSystemExportStatusLogEntry entry)
	{
		final I_ExternalSystem_ScriptedExportConversion_Status record =
				InterfaceWrapperHelper.load(entry.getLogId(), I_ExternalSystem_ScriptedExportConversion_Status.class);
		updateRecord(record, entry);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * Loads the status row bound to the given {@code pInstanceId}, applies the operator,
	 * and saves the result. No-op (no throw) when no row is bound to the pInstance.
	 *
	 * @param pInstanceId the process-instance whose status row to update
	 * @param operator    applied to the loaded VO to produce the updated VO
	 */
	public void updateLatestByPInstanceId(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final UnaryOperator<ExternalSystemExportStatusLogEntry> operator)
	{
		final Optional<ExternalSystemExportStatusLogEntry> existing = getLatestByPInstanceId(pInstanceId);
		if (!existing.isPresent())
		{
			return;
		}
		final ExternalSystemExportStatusLogEntry updated = operator.apply(existing.get());
		update(updated);
	}

	// ------------------------------------------------------------------
	// Queries
	// ------------------------------------------------------------------

	/**
	 * Returns the status row for the given pInstance, if any.
	 * (Since each key has one row, "latest" is the single row whose AD_PInstance_ID matches.)
	 */
	@NonNull
	public Optional<ExternalSystemExportStatusLogEntry> getLatestByPInstanceId(@NonNull final PInstanceId pInstanceId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_PInstance_ID, pInstanceId.getRepoId())
				.orderByDescending(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID)
				.create()
				.firstOptional(I_ExternalSystem_ScriptedExportConversion_Status.class)
				.map(ExternalSystemExportStatusRepository::fromRecord);
	}

	/**
	 * Returns all status rows for the given config.
	 */
	@NonNull
	public List<ExternalSystemExportStatusLogEntry> getByConfigId(@NonNull final ExternalSystemScriptedExportConversionConfigId configId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
				.addEqualsFilter(
						I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID,
						configId.getRepoId())
				.create()
				.stream()
				.map(ExternalSystemExportStatusRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Returns the single status row for the given config + source record combination, if any.
	 * Since the grain is one row per (config, table, record), this returns the unique row.
	 */
	@NonNull
	public Optional<ExternalSystemExportStatusLogEntry> getLatestByConfigAndRecord(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		return queryStatusRecord(configId, sourceRecord)
				.map(ExternalSystemExportStatusRepository::fromRecord);
	}

	/**
	 * Returns the distinct config IDs whose status row for the given source record is
	 * not yet fully processed (i.e. neither {@link ExternalSystemExportStatus#Sent} nor
	 * {@link ExternalSystemExportStatus#DontSend}).
	 */
	@NonNull
	public List<ExternalSystemScriptedExportConversionConfigId> getConfigsWithNonSentAttemptBySourceRecord(
			@NonNull final TableRecordReference sourceRecord)
	{
		final List<ExternalSystemExportStatusLogEntry> allEntries = getLatestBySourceRecord(sourceRecord);

		// Deduplicate: keep only the most-recent row per configId
		final LinkedHashMap<ExternalSystemScriptedExportConversionConfigId, ExternalSystemExportStatusLogEntry> latestPerConfig =
				new LinkedHashMap<>();
		for (final ExternalSystemExportStatusLogEntry entry : allEntries)
		{
			latestPerConfig.putIfAbsent(entry.getConfigId(), entry);
		}

		return latestPerConfig.entrySet().stream()
				.filter(e -> !e.getValue().getStatus().isProcessed())
				.map(Map.Entry::getKey)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Returns all status rows for the given source record, ordered newest-first.
	 */
	@NonNull
	public List<ExternalSystemExportStatusLogEntry> getLatestBySourceRecord(@NonNull final TableRecordReference sourceRecord)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_Table_ID, sourceRecord.getAD_Table_ID())
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_Record_ID, sourceRecord.getRecord_ID())
				.orderByDescending(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID)
				.create()
				.stream()
				.map(ExternalSystemExportStatusRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	// ------------------------------------------------------------------
	// Internal
	// ------------------------------------------------------------------

	@NonNull
	private Optional<I_ExternalSystem_ScriptedExportConversion_Status> queryStatusRecord(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
				.addEqualsFilter(
						I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID,
						configId.getRepoId())
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_Table_ID, sourceRecord.getAD_Table_ID())
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_Record_ID, sourceRecord.getRecord_ID())
				.create()
				.firstOptional(I_ExternalSystem_ScriptedExportConversion_Status.class);
	}

	// ------------------------------------------------------------------
	// Mapping helpers (static for easy access from tests)
	// ------------------------------------------------------------------

	/**
	 * Maps a persisted status record to the VO.
	 * The {@code logId} field carries the Status row PK.
	 */
	@NonNull
	public static ExternalSystemExportStatusLogEntry fromRecord(
			@NonNull final I_ExternalSystem_ScriptedExportConversion_Status record)
	{
		return ExternalSystemExportStatusLogEntry.builder()
				.logId(record.getExternalSystem_ScriptedExportConversion_Status_ID())
				.pInstanceId(PInstanceId.ofRepoIdOrNull(record.getAD_PInstance_ID()))
				.configId(ExternalSystemScriptedExportConversionConfigId.ofRepoId(record.getExternalSystem_Config_ScriptedExportConversion_ID()))
				.sourceRecord(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.status(ExternalSystemExportStatus.ofCode(record.getExportStatus()))
				.httpResponseCode(record.getHttpResponseCode())
				.adIssueId(record.getAD_Issue_ID())
				.statusMessage(record.getStatusMessage())
				.isResend(record.isResend())
				.build();
	}

	/**
	 * Applies the VO fields onto the given record (for insert or update).
	 * Does NOT save — caller must call {@link InterfaceWrapperHelper#saveRecord(Object)}.
	 */
	public static void updateRecord(
			@NonNull final I_ExternalSystem_ScriptedExportConversion_Status record,
			@NonNull final ExternalSystemExportStatusLogEntry entry)
	{
		record.setExternalSystem_Config_ScriptedExportConversion_ID(entry.getConfigId().getRepoId());
		record.setAD_Table_ID(entry.getSourceRecord().getAD_Table_ID());
		record.setRecord_ID(entry.getSourceRecord().getRecord_ID());
		record.setExportStatus(entry.getStatus().getCode());

		final PInstanceId pInstanceId = entry.getPInstanceId();
		if (pInstanceId != null)
		{
			record.setAD_PInstance_ID(pInstanceId.getRepoId());
		}
		else
		{
			record.setAD_PInstance_ID(0);
		}

		record.setHttpResponseCode(entry.getHttpResponseCode());

		if (entry.getAdIssueId() > 0)
		{
			record.setAD_Issue_ID(entry.getAdIssueId());
		}
		else
		{
			record.setAD_Issue_ID(0);
		}

		record.setStatusMessage(entry.getStatusMessage());
		record.setIsResend(entry.isResend());
	}
}
