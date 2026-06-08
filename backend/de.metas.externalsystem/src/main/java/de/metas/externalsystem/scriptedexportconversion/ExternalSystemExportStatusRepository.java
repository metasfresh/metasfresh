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
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.model.I_ExternalSystem_ScriptedExportConversion_Log;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Column;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Repository for {@code ExternalSystem_ScriptedExportConversion_Log}.
 *
 * <p>Repository Tables: ExternalSystem_ScriptedExportConversion_Log,
 * ExternalSystem_Config_ScriptedExportConversion, AD_Column
 */
@Repository
public class ExternalSystemExportStatusRepository
{
	private static final Logger log = LogManager.getLogger(ExternalSystemExportStatusRepository.class);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static ExternalSystemExportStatusRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ExternalSystemExportStatusRepository();
	}

	// ------------------------------------------------------------------
	// Save (insert or update)
	// ------------------------------------------------------------------

	/**
	 * Inserts a new log row and returns the persisted entry (with its new logId).
	 */
	@NonNull
	public ExternalSystemExportStatusLogEntry insert(@NonNull final ExternalSystemExportStatusLogEntry entry)
	{
		final I_ExternalSystem_ScriptedExportConversion_Log record =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_ScriptedExportConversion_Log.class);
		toRecord(entry, record);
		InterfaceWrapperHelper.saveRecord(record);
		return entry.withLogId(record.getExternalSystem_ScriptedExportConversion_Log_ID());
	}

	/**
	 * Updates an existing log row.
	 */
	public void update(@NonNull final ExternalSystemExportStatusLogEntry entry)
	{
		final I_ExternalSystem_ScriptedExportConversion_Log record =
				InterfaceWrapperHelper.load(entry.getLogId(), I_ExternalSystem_ScriptedExportConversion_Log.class);
		toRecord(entry, record);
		InterfaceWrapperHelper.saveRecord(record);
	}

	private void toRecord(
			@NonNull final ExternalSystemExportStatusLogEntry entry,
			@NonNull final I_ExternalSystem_ScriptedExportConversion_Log record)
	{
		if (entry.getPInstanceId() != null)
		{
			record.setAD_PInstance_ID(entry.getPInstanceId().getRepoId());
		}
		record.setExternalSystem_Config_ScriptedExportConversion_ID(entry.getConfigId().getRepoId());
		record.setAD_Table_ID(entry.getSourceRecord().getAD_Table_ID());
		record.setRecord_ID(entry.getSourceRecord().getRecord_ID());
		record.setExportStatus(entry.getStatus().getCode());
		record.setHttpResponseCode(entry.getHttpResponseCode());
		if (entry.getAdIssueId() > 0)
		{
			record.setAD_Issue_ID(entry.getAdIssueId());
		}
		record.setStatusMessage(entry.getStatusMessage());
		record.setIsResend(entry.isResend());
	}

	// ------------------------------------------------------------------
	// Queries
	// ------------------------------------------------------------------

	/**
	 * Returns the most-recent log entry for the given process instance, if any.
	 */
	@NonNull
	public Optional<ExternalSystemExportStatusLogEntry> getLatestByPInstanceId(@NonNull final PInstanceId pInstanceId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Log.class)
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Log.COLUMNNAME_AD_PInstance_ID, pInstanceId.getRepoId())
				.orderByDescending(I_ExternalSystem_ScriptedExportConversion_Log.COLUMNNAME_ExternalSystem_ScriptedExportConversion_Log_ID)
				.create()
				.firstOptional(I_ExternalSystem_ScriptedExportConversion_Log.class)
				.map(this::fromRecord);
	}

	/**
	 * Returns all log entries for the given config, one per pInstance (the single row per config assumption holds because we upsert per pInstance).
	 */
	@NonNull
	public List<ExternalSystemExportStatusLogEntry> getByConfigId(@NonNull final ExternalSystemScriptedExportConversionConfigId configId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Log.class)
				.addEqualsFilter(
						I_ExternalSystem_ScriptedExportConversion_Log.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID,
						configId.getRepoId())
				.create()
				.stream()
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Returns the latest log entry per distinct config for the given source record.
	 * Used to compute the roll-up status across all active configs.
	 */
	@NonNull
	public List<ExternalSystemExportStatusLogEntry> getLatestBySourceRecord(@NonNull final TableRecordReference sourceRecord)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Log.class)
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Log.COLUMNNAME_AD_Table_ID, sourceRecord.getAD_Table_ID())
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Log.COLUMNNAME_Record_ID, sourceRecord.getRecord_ID())
				.orderByDescending(I_ExternalSystem_ScriptedExportConversion_Log.COLUMNNAME_ExternalSystem_ScriptedExportConversion_Log_ID)
				.create()
				.stream()
				.map(this::fromRecord)
				// keep only the latest entry per configId
				.collect(ImmutableList.toImmutableList());
	}

	// ------------------------------------------------------------------
	// Source-record roll-up write
	// ------------------------------------------------------------------

	/**
	 * Looks up the {@code Status_AD_Column_ID} configured for the given config.
	 * Returns {@code null} when no target column is set (log-rows-only mode).
	 */
	@Nullable
	public String getStatusColumnNameForConfig(@NonNull final ExternalSystemScriptedExportConversionConfigId configId)
	{
		final I_ExternalSystem_Config_ScriptedExportConversion configRecord =
				InterfaceWrapperHelper.load(configId.getRepoId(), I_ExternalSystem_Config_ScriptedExportConversion.class);

		final int statusAdColumnId = configRecord.getStatus_AD_Column_ID();
		if (statusAdColumnId <= 0)
		{
			return null;
		}

		final I_AD_Column adColumn = InterfaceWrapperHelper.load(statusAdColumnId, I_AD_Column.class);
		if (adColumn == null)
		{
			log.debug("AD_Column {} not found for configId={} – skipping roll-up write", statusAdColumnId, configId);
			return null;
		}
		return adColumn.getColumnName();
	}

	/**
	 * Writes the given status code into the named column of the source record.
	 * Uses dynamic column assignment so any String column can be the roll-up target.
	 */
	public void writeStatusToSourceRecord(
			@NonNull final TableRecordReference sourceRecord,
			@NonNull final String columnName,
			@NonNull final String statusCode)
	{
		final Object sourceModel = sourceRecord.getModel();
		InterfaceWrapperHelper.setValue(sourceModel, columnName, statusCode);
		InterfaceWrapperHelper.saveRecord(sourceModel);
	}

	// ------------------------------------------------------------------
	// Mapping
	// ------------------------------------------------------------------

	@NonNull
	private ExternalSystemExportStatusLogEntry fromRecord(@NonNull final I_ExternalSystem_ScriptedExportConversion_Log record)
	{
		return ExternalSystemExportStatusLogEntry.builder()
				.logId(record.getExternalSystem_ScriptedExportConversion_Log_ID())
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
}
