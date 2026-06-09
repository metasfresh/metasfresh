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
import de.metas.externalsystem.model.I_ExternalSystem_ScriptedExportConversion_Status;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.springframework.http.HttpStatus;
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
	// Upsert / insert
	// ------------------------------------------------------------------

	/**
	 * Upserts the status row for the given (config, sourceRecord) key.
	 * Creates the row if it does not exist yet; updates it in-place otherwise.
	 */
	@NonNull
	public ScriptedExportConversionStatus upsert(@NonNull final ScriptedExportConversionStatusCreateRequest request)
	{
		final I_ExternalSystem_ScriptedExportConversion_Status record =
				queryStatusRecord(request.getConfigId(), request.getSourceRecord())
						.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_ExternalSystem_ScriptedExportConversion_Status.class));
		updateRecord(record, request);
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}

	/**
	 * Inserts a new status row, for callers that explicitly need a fresh row (e.g. re-send
	 * creates a new row to preserve history).
	 */
	@NonNull
	public ScriptedExportConversionStatus insert(@NonNull final ScriptedExportConversionStatusCreateRequest request)
	{
		final I_ExternalSystem_ScriptedExportConversion_Status record =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_ScriptedExportConversion_Status.class);
		updateRecord(record, request);
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}

	/**
	 * Persists the loaded status identified by its (config, sourceRecord) key.
	 * No-op (no throw) when no row exists for that key.
	 */
	public void update(@NonNull final ScriptedExportConversionStatus status)
	{
		final I_ExternalSystem_ScriptedExportConversion_Status record =
				queryStatusRecord(status.getConfigId(), status.getSourceRecord()).orElse(null);
		if (record == null)
		{
			return;
		}
		updateRecord(record, status);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * Loads the status row bound to the given {@code pInstanceId}, applies the operator,
	 * and saves the result. No-op (no throw) when no row is bound to the pInstance.
	 */
	public void updateLatestByPInstanceId(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final UnaryOperator<ScriptedExportConversionStatus> operator)
	{
		final ScriptedExportConversionStatus existing = getLatestByPInstanceId(pInstanceId).orElse(null);
		if (existing == null)
		{
			return;
		}
		update(operator.apply(existing));
	}

	// ------------------------------------------------------------------
	// Queries
	// ------------------------------------------------------------------

	/**
	 * Returns the status row for the given pInstance, if any.
	 */
	@NonNull
	public Optional<ScriptedExportConversionStatus> getLatestByPInstanceId(@NonNull final PInstanceId pInstanceId)
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
	public List<ScriptedExportConversionStatus> getByConfigId(@NonNull final ExternalSystemScriptedExportConversionConfigId configId)
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
	 */
	@NonNull
	public Optional<ScriptedExportConversionStatus> getLatestByConfigAndRecord(
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
		final List<ScriptedExportConversionStatus> allEntries = getLatestBySourceRecord(sourceRecord);

		final LinkedHashMap<ExternalSystemScriptedExportConversionConfigId, ScriptedExportConversionStatus> latestPerConfig =
				new LinkedHashMap<>();
		for (final ScriptedExportConversionStatus entry : allEntries)
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
	public List<ScriptedExportConversionStatus> getLatestBySourceRecord(@NonNull final TableRecordReference sourceRecord)
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
	// Mapping helpers
	// ------------------------------------------------------------------

	@NonNull
	public static ScriptedExportConversionStatus fromRecord(
			@NonNull final I_ExternalSystem_ScriptedExportConversion_Status record)
	{
		final int httpCode = record.getHttpResponseCode();
		return ScriptedExportConversionStatus.builder()
				.pInstanceId(PInstanceId.ofRepoIdOrNull(record.getAD_PInstance_ID()))
				.configId(ExternalSystemScriptedExportConversionConfigId.ofRepoId(record.getExternalSystem_Config_ScriptedExportConversion_ID()))
				.sourceRecord(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.status(ExternalSystemExportStatus.ofCode(record.getExportStatus()))
				.httpResponseCode(httpCode > 0 ? HttpStatus.valueOf(httpCode) : null)
				.adIssueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
				.statusMessage(record.getStatusMessage())
				.isResend(record.isResend())
				.build();
	}

	private static void updateRecord(
			@NonNull final I_ExternalSystem_ScriptedExportConversion_Status record,
			@NonNull final ScriptedExportConversionStatusCreateRequest request)
	{
		applyFields(
				record,
				request.getPInstanceId(),
				request.getConfigId(),
				request.getSourceRecord(),
				request.getStatus(),
				request.getHttpResponseCode(),
				request.getAdIssueId(),
				request.getStatusMessage(),
				request.isResend());
	}

	@VisibleForTesting
	static void updateRecord(
			@NonNull final I_ExternalSystem_ScriptedExportConversion_Status record,
			@NonNull final ScriptedExportConversionStatus status)
	{
		applyFields(
				record,
				status.getPInstanceId(),
				status.getConfigId(),
				status.getSourceRecord(),
				status.getStatus(),
				status.getHttpResponseCode(),
				status.getAdIssueId(),
				status.getStatusMessage(),
				status.isResend());
	}

	private static void applyFields(
			@NonNull final I_ExternalSystem_ScriptedExportConversion_Status record,
			@Nullable final PInstanceId pInstanceId,
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			@NonNull final ExternalSystemExportStatus status,
			@Nullable final HttpStatus httpResponseCode,
			@Nullable final AdIssueId adIssueId,
			@Nullable final String statusMessage,
			final boolean isResend)
	{
		record.setExternalSystem_Config_ScriptedExportConversion_ID(configId.getRepoId());
		record.setAD_Table_ID(sourceRecord.getAD_Table_ID());
		record.setRecord_ID(sourceRecord.getRecord_ID());
		record.setExportStatus(status.getCode());
		record.setAD_PInstance_ID(pInstanceId != null ? pInstanceId.getRepoId() : 0);
		record.setHttpResponseCode(httpResponseCode != null ? httpResponseCode.value() : 0);
		record.setAD_Issue_ID(adIssueId != null ? adIssueId.getRepoId() : 0);
		record.setStatusMessage(statusMessage);
		record.setIsResend(isResend);
	}
}
