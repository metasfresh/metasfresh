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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.adempiere.service.IColumnBL;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.IDocumentBL;
import de.metas.externalsystem.ExternalSystemConfigRepository;
import de.metas.externalsystem.ExternalSystemInvocationContext;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.audit.CreateExportAuditRequest;
import de.metas.externalsystem.audit.ExternalSystemExportAuditRepo;
import de.metas.externalsystem.endpoint.ExternalSystemEndpoint;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.process.InvokeScriptedExportConversionAction;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.common.externalsystem.ExternalSystemConstants.COMMAND_CONVERT_MESSAGE_FROM_METASFRESH;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ERROR_CONTEXT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_DOCUMENT_NO;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME;
import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_CHILD_CONFIG_ID;
import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_EXTERNAL_REQUEST;
import static de.metas.externalsystem.process.InvokeScriptedExportConversionAction.PARAM_Record_ID;
import static org.compiere.util.Env.getCtx;

@Service
@RequiredArgsConstructor
public class ExternalSystemScriptedExportConversionService
{
	private final Logger log = LogManager.getLogger(getClass());

	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IColumnBL columnBL = Services.get(IColumnBL.class);
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ExternalSystemExportStatusService exportStatusService;

	@NonNull private final ExternalSystemScriptedExportConversionRepository externalSystemScriptedExportConversionRepository;
	@NonNull private final ExternalSystemEndpointRepository externalSystemEndpointRepository;
	@NonNull private final ExternalSystemExportAuditRepo exportAuditRepo;
	@NonNull private final ExternalSystemConfigRepository externalSystemConfigRepository;

	public void addCacheResetListener(@NonNull final ExternalSystemScriptedExportConversionConfigChangedListener listener)
	{
		externalSystemScriptedExportConversionRepository.addCacheResetListener(listener);
	}

	@NonNull
	public ImmutableSet<AdTableAndClientId> getTriggerOnCompleteDistinctTableAndClientIds()
	{
		return externalSystemScriptedExportConversionRepository.getTriggerOnCompleteDistinctTableAndClientIds();
	}

	@NonNull
	public Map<String, String> getParameters(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			@NonNull final Properties context,
			@NonNull final String outboundDataProcessRecordId)
	{
		return getParameters(config, context, outboundDataProcessRecordId, null);
	}

	@NonNull
	public Map<String, String> getParameters(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			@NonNull final Properties context,
			@NonNull final String outboundDataProcessRecordId,
			@Nullable final String errorContext)
	{

		final ExternalSystemEndpoint endpoint = externalSystemEndpointRepository.getById(config.getExternalSystemEndpointId());
		final String outboundEndpointData = toJson(endpoint);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, getOutboundProcessResponse(config, context, outboundDataProcessRecordId));
		parameters.put(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, config.getScriptIdentifier());
		parameters.put(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS, outboundEndpointData);
		final String tableName = tableDAO.retrieveTableName(config.getTableId());
		parameters.put(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, tableName);
		parameters.put(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, outboundDataProcessRecordId);

		final String documentNo = retrieveDocumentNo(tableName, outboundDataProcessRecordId);
		if (documentNo != null)
		{
			parameters.put(PARAM_SCRIPTEDADAPTER_OUTBOUND_DOCUMENT_NO, documentNo);
		}

		if (errorContext != null)
		{
			parameters.put(PARAM_ERROR_CONTEXT, errorContext);
		}

		return parameters;
	}

	@Nullable
	private String retrieveDocumentNo(@NonNull final String tableName, @NonNull final String recordId)
	{
		try
		{
			final int adTableId = tableDAO.retrieveTableId(tableName);
			return Services.get(IDocumentBL.class).getDocumentNo(getCtx(), adTableId, Integer.parseInt(recordId));
		}
		catch (final Exception e)
		{
			// Table may not have a DocumentNo column — that's fine, just return null
			return null;
		}
	}

	private String toJson(@NonNull final ExternalSystemEndpoint endpoint)
	{
		try
		{
			return objectMapper.writeValueAsString(endpoint.toJson());
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed converting endpoint's properties to json-string: " + endpoint, e);
		}
	}

	public boolean isConfigMatchingRecord(@NonNull final ExternalSystemScriptedExportConversionConfig config,
										  @NonNull final Integer recordId)
	{
		return Optional.ofNullable(getTargetRecordIdMatchingConfig(config, recordId))
				.isPresent();
	}

	@Nullable
	private Integer getTargetRecordIdMatchingConfig(@NonNull final ExternalSystemScriptedExportConversionConfig config,
													@NonNull final Integer recordId)
	{
		final String sql = getSqlWithWhereClauseAndDocBaseTypeIfPresent(config);

		try
		{
			return DB.retrieveFirstRowOrNull(sql, Collections.singletonList(recordId), rs -> {
				final int intValue = rs.getInt(1);
				return rs.wasNull() ? null : intValue;
			});
		}
		catch (final Exception exception)
		{
			log.warn("Error executing SQL: {} with param: {}", sql, recordId);
		}

		return null;
	}

	@NonNull
	private String getSqlWithWhereClauseAndDocBaseTypeIfPresent(@NonNull final ExternalSystemScriptedExportConversionConfig config)
	{
		final String rootTableName = tableDAO.retrieveTableName(config.getTableId());
		final String rootKeyColumnName = columnBL.getSingleKeyColumn(rootTableName);

		return Optional.ofNullable(config.getDocBaseType())
				.map(docBaseType -> "SELECT " + rootKeyColumnName +
						" FROM " + rootTableName + " root" +
						" WHERE " + config.getWhereClause() +
						" AND root." + rootKeyColumnName + " = ?" +
						" AND EXISTS (" +
						"   SELECT 1 FROM C_DocType targetType" +
						"   WHERE targetType.DocBaseType = '" + docBaseType.getCode() + "'" +
						"     AND targetType.C_DocType_ID = root.C_DocType_ID" +
						")")
				.orElseGet(() -> "SELECT " + rootKeyColumnName
						+ " FROM " + rootTableName
						+ " WHERE " + rootKeyColumnName + "=?"
						+ " AND " + config.getWhereClause());
	}

	@NonNull
	private String getOutboundProcessResponse(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			@NonNull final Properties context,
			@NonNull final String outboundDataProcessRecordId)
	{
		final String rootTableName = tableDAO.retrieveTableName(config.getTableId());
		final String rootKeyColumnName = columnBL.getSingleKeyColumn(rootTableName);

		final ProcessInfo.ProcessInfoBuilder outboundProcessInfoBuilder = ProcessInfo.builder()
				.setCtx(context)
				.setRecord(TableRecordReference.of(config.getTableId(), StringUtils.toIntegerOrZero(outboundDataProcessRecordId)))
				.setAD_Process_ID(config.getOutboundDataProcessId())
				.addParameter(rootKeyColumnName, outboundDataProcessRecordId);

		final ProcessExecutor processExecutor = outboundProcessInfoBuilder
				.buildAndPrepareExecution()
				.executeSync();

		final Resource resource = Optional.ofNullable(processExecutor.getResult())
				.map(ProcessExecutionResult::getReportDataResource)
				.orElse(null);

		if (resource == null || !resource.exists())
		{
			throw new AdempiereException("Process did not return a valid Resource")
					.appendParametersToMessage()
					.setParameter("OutboundDataProcessId", config.getOutboundDataProcessId());
		}

		try (final InputStream in = resource.getInputStream())
		{
			return StreamUtils.copyToString(in, StandardCharsets.UTF_8);
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed to read process output Resource", ex);
		}
	}

	public void executeInvokeScriptedExportConversionActionAfterCommit(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			final int recordId)
	{
		trxManager.runAfterCommit(() -> executeInvokeScriptedExportConversionAction(config, recordId));
	}

	public List<Exception> executeInvokeScriptedExportConversionAction(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			final int recordId)
	{
		// UNKNOWN (not null): the auto-trigger / archive-listener path must propagate a non-null error
		// context so a downstream failure reaches the scripted-export error listener (createIssue only
		// notifies it when errorContext != null) and the status row flips to Error. The EDI and Re-send
		// paths pass EDI / RESEND; auto-trigger has no specific context, so it uses the neutral UNKNOWN.
		return executeInvokeScriptedExportConversionActionAndGetResult(config, recordId, ExternalSystemInvocationContext.UNKNOWN).getExceptions();
	}

	/**
	 * Re-sends the scripted export for one config + record, but ONLY if the config's WhereClause still
	 * matches the record — i.e. there is still something to export. For the EPCIS config the WhereClause
	 * is {@code epcis_has_events(m_inout_id)}, which is false once every SSCC of the shipment is already
	 * in the transmission ledger. When nothing is left to export the re-send records a terminal
	 * {@link ExternalSystemExportStatus#DontSend} and does NOT invoke the adapter, so an empty EPCIS event
	 * is never sent (mirroring the relevance gate the auto-complete path applies in
	 * {@link #recordEligibilityAndInvoke}).
	 *
	 * <p>The config is resolved (getById, fail-fast — throws for inactive/missing) <em>before</em> any
	 * status-row write, so a bad config throws before a Pending/DontSend row is created (no orphan row).
	 *
	 * @return {@code true} if the conversion was invoked (something to send), {@code false} if suppressed
	 *         because nothing was left to export.
	 */
	public boolean resendConfigIfRelevant(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final TableRecordReference sourceRecord,
			final int recordId)
	{
		// Resolve config first — throws for inactive/missing configs (fail-fast, before any status-row write)
		final ExternalSystemScriptedExportConversionConfig config =
				externalSystemScriptedExportConversionRepository.getById(configId);

		if (!isConfigMatchingRecord(config, recordId))
		{
			// nothing left to export (e.g. every SSCC already in the EPCIS ledger) — record DontSend and
			// do NOT invoke the adapter, so a re-send with nothing new never fires an empty EPCIS event
			exportStatusService.recordDontSend(configId, sourceRecord);
			Loggables.addLog("Re-send: config {} record {} → skipped (nothing to export / WhereClause no longer matches)", configId, sourceRecord);
			return false;
		}

		exportStatusService.recordPendingAsResend(configId, sourceRecord);
		executeInvokeScriptedExportConversionActionAndGetResult(config, recordId, ExternalSystemInvocationContext.RESEND);
		Loggables.addLog("Re-send: config {} record {} → invoked", configId, sourceRecord);
		return true;
	}

	@NonNull
	public List<ExternalSystemScriptedExportConversionConfigId> getResendableConfigsBySourceRecord(
			@NonNull final TableRecordReference sourceRecord)
	{
		return exportStatusService.getResendableConfigsBySourceRecord(sourceRecord);
	}

	/**
	 * Returns all config IDs in a re-triggerable state for the given source record, judged on each config's
	 * LATEST attempt. Includes {@link de.metas.externalsystem.ExternalSystemExportStatus#Sent} (re-send even
	 * when previously successful) and an operator-parked
	 * {@link de.metas.externalsystem.ExternalSystemExportStatus#Pending}; excludes
	 * {@link de.metas.externalsystem.ExternalSystemExportStatus#DontSend} and the actively-in-flight states
	 * (Enqueued, SendingStarted, and a transient auto-flow Pending) to avoid duplicate concurrent sends.
	 *
	 * @see ExternalSystemExportStatusService#getMatchingConfigIdsBySourceRecord(TableRecordReference)
	 */
	@NonNull
	public List<ExternalSystemScriptedExportConversionConfigId> getMatchingConfigIdsBySourceRecord(
			@NonNull final TableRecordReference sourceRecord)
	{
		return exportStatusService.getMatchingConfigIdsBySourceRecord(sourceRecord);
	}

	/**
	 * Records a {@link ExternalSystemExportStatus#Pending} log entry for the given config and source record.
	 * Called by the interceptor's AFTER_COMPLETE branch for each matching config, before scheduling
	 * the after-commit execution.
	 */
	public void recordPendingForConfig(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			final int recordId)
	{
		final TableRecordReference sourceRecord = TableRecordReference.of(config.getTableId(), recordId);
		exportStatusService.recordPending(config.getId(), sourceRecord);
	}

	/**
	 * Records eligibility (matching WhereClause → {@link de.metas.externalsystem.ExternalSystemExportStatus#Pending},
	 * else {@link de.metas.externalsystem.ExternalSystemExportStatus#DontSend}) for every
	 * trigger-on-complete config of the record's table, then invokes the export for the matching ones.
	 *
	 * <p>After-commit because the WhereClause may read committed document state — e.g. the EPCIS
	 * {@code epcis_has_events(m_inout_id)} clause requires {@code DocStatus IN ('CO','CL')}, which the
	 * AFTER_COMPLETE interceptor fires before the engine saves. A per-config status-write failure is
	 * swallowed so document completion is never aborted.
	 */
	public void recordEligibilityAndInvokeAfterCommit(
			@NonNull final AdTableAndClientId tableAndClientId,
			final int recordId)
	{
		trxManager.runAfterCommit(() -> recordEligibilityAndInvoke(tableAndClientId, recordId));
	}

	private void recordEligibilityAndInvoke(
			@NonNull final AdTableAndClientId tableAndClientId,
			final int recordId)
	{
		final ImmutableList<ExternalSystemScriptedExportConversionConfig> allConfigs =
				externalSystemScriptedExportConversionRepository.getTriggerOnCompleteConfigsByTableAndClientIds(tableAndClientId);

		// Partition in a single pass — isConfigMatchingRecord issues a SQL query per config,
		// so we must not call it twice for the same config.
		final Map<Boolean, List<ExternalSystemScriptedExportConversionConfig>> partitioned = allConfigs.stream()
				.collect(Collectors.partitioningBy(config -> isConfigMatchingRecord(config, recordId)));

		final ImmutableList<ExternalSystemScriptedExportConversionConfig> matchingConfigs =
				ImmutableList.copyOf(partitioned.get(Boolean.TRUE));
		final ImmutableList<ExternalSystemScriptedExportConversionConfig> nonMatchingConfigs =
				ImmutableList.copyOf(partitioned.get(Boolean.FALSE));

		recordCompleteTimeEligibilityStatusesOnly(matchingConfigs, nonMatchingConfigs, recordId);

		// Already running after-commit — invoke synchronously rather than re-scheduling after-commit.
		matchingConfigs.forEach(config -> executeInvokeScriptedExportConversionAction(config, recordId));
	}

	/**
	 * Pure inner method: given a pre-partitioned list of configs, writes the eligibility statuses
	 * in the current transaction.
	 *
	 * <p>This method is intentionally separated from the DB-matching logic so it can be unit-tested
	 * without a live DB.
	 *
	 * <p>Each status write is individually guarded: a failure for one config is logged and swallowed
	 * so that document completion is never aborted.
	 *
	 * @param matchingConfigs    configs whose WhereClause matches the record → written as Pending
	 * @param nonMatchingConfigs configs whose WhereClause does NOT match the record → written as DontSend
	 * @param recordId           the source record ID (table is taken from each config)
	 */
	/* package-private for testing */ void recordCompleteTimeEligibilityStatusesOnly(
			@NonNull final ImmutableList<ExternalSystemScriptedExportConversionConfig> matchingConfigs,
			@NonNull final ImmutableList<ExternalSystemScriptedExportConversionConfig> nonMatchingConfigs,
			final int recordId)
	{
		for (final ExternalSystemScriptedExportConversionConfig config : matchingConfigs)
		{
			try
			{
				recordPendingForConfig(config, recordId);
			}
			catch (final Exception e)
			{
				log.warn("Failed to record Pending status for config={}, recordId={} — continuing", config.getId(), recordId, e);
			}
		}

		for (final ExternalSystemScriptedExportConversionConfig config : nonMatchingConfigs)
		{
			try
			{
				final TableRecordReference sourceRecord = TableRecordReference.of(config.getTableId(), recordId);
				exportStatusService.recordDontSend(config.getId(), sourceRecord);
			}
			catch (final Exception e)
			{
				log.warn("Failed to record DontSend status for config={}, recordId={} — continuing", config.getId(), recordId, e);
			}
		}
	}

	@NonNull
	public ExternalSystemInvocationResult executeInvokeScriptedExportConversionActionAndGetResult(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			final int recordId,
			@NonNull final ExternalSystemInvocationContext errorContext)
	{
		final int configTableId = tableDAO.retrieveTableId(I_ExternalSystem_Config_ScriptedExportConversion.Table_Name);
		final TableRecordReference sourceRecord = TableRecordReference.of(config.getTableId(), recordId);

		try
		{
			final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder()
					.setCtx(getCtx())
					.setRecord(configTableId, config.getId().getRepoId())
					.setAD_ProcessByClassname(InvokeScriptedExportConversionAction.class.getName())
					.addParameter(PARAM_EXTERNAL_REQUEST, COMMAND_CONVERT_MESSAGE_FROM_METASFRESH)
					.addParameter(PARAM_CHILD_CONFIG_ID, config.getId().getRepoId())
					.addParameter(PARAM_Record_ID, recordId)
					.addParameter(PARAM_ERROR_CONTEXT, errorContext.getCode());

			final ProcessInfo processInfo = processInfoBuilder.buildAndPrepareExecution().executeSync().getProcessInfo();
			final ProcessExecutionResult result = processInfo.getResult();

			if (result.isError())
			{
				// (c) Invalid — the script did not produce a valid Resource (or another process error)
				exportStatusService.markInvalidByRecord(config.getId(), sourceRecord, result.getSummary());
			}
			else if (processInfo.getPinstanceId() != null)
			{
				// (b) Enqueued — successfully dispatched to RabbitMQ
				exportStatusService.markEnqueued(config.getId(), sourceRecord, processInfo.getPinstanceId());
				writeExportAudit(config, sourceRecord, processInfo.getPinstanceId());
			}
			else
			{
				log.warn("Process succeeded but PInstance is null for Config ID {}, Record ID {} — skipping Enqueued status write", config.getId(), recordId);
			}

			return ExternalSystemInvocationResult.success(processInfo.getPinstanceId());
		}
		catch (final Exception e)
		{
			log.warn("{} process failed for Config ID {}, Record ID: {}",
					InvokeScriptedExportConversionAction.class.getName(),
					config.getId(), recordId, e);
			// Mark invalid if the process threw before the PInstance was established
			try
			{
				exportStatusService.markInvalidByRecord(config.getId(), sourceRecord, e.getMessage());
			}
			catch (final Exception statusEx)
			{
				log.warn("Failed to write Invalid status for config={}, sourceRecord={} — continuing", config.getId(), sourceRecord, statusEx);
			}
			return ExternalSystemInvocationResult.error(e);
		}
	}

	public List<ExternalSystemScriptedExportConversionConfig> getByParentConfigIdAndTableAndClientId(@NonNull final ExternalSystemParentConfigId parentConfigId,
																									 @NonNull final AdTableAndClientId tableAndClientId)
	{
		return externalSystemScriptedExportConversionRepository.getByParentConfigId(parentConfigId).stream()
				.filter(config -> config.isMatching(tableAndClientId))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Writes one {@code ExternalSystem_ExportAudit} row for a successfully enqueued send.
	 *
	 * <p>The audit row carries the pinstance that was returned by the enqueue step, the source
	 * record reference, and the external-system type resolved from the config's parent.
	 * Both the status row (written by {@link ExternalSystemExportStatusService#markEnqueued}) and
	 * this audit row carry the same {@code pInstanceId}, establishing the correlation between the
	 * two tables.
	 *
	 * <p>Separated into its own method so the audit write can be exercised in a unit test without
	 * driving the full ProcessInfo/DB send path.
	 *
	 * <p>A failure to write the audit row is logged and swallowed so that a non-critical audit
	 * failure never rolls back the enqueue.
	 */
	/* package-private for testing */ void writeExportAudit(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			@NonNull final TableRecordReference sourceRecord,
			@NonNull final PInstanceId pInstanceId)
	{
		try
		{
			final ExternalSystemType externalSystemType = ExternalSystemType.ofValue(
					externalSystemConfigRepository.getParentTypeById(config.getParentId()));

			final UserId exportUserId = Env.getLoggedUserIdIfExists(getCtx()).orElse(UserId.SYSTEM);
			final RoleId exportRoleId = Env.getLoggedRoleIdIfExists(getCtx()).orElse(RoleId.SYSTEM);

			exportAuditRepo.createESExportAudit(CreateExportAuditRequest.builder()
					.tableRecordReference(sourceRecord)
					.exportTime(SystemTime.asInstant())
					.exportUserId(exportUserId)
					.exportRoleId(exportRoleId)
					.externalSystemType(externalSystemType)
					.pInstanceId(pInstanceId)
					.build());
		}
		catch (final Exception e)
		{
			// Intentional swallow: the audit row is best-effort observability; failing to write it
			// must never roll back the already-enqueued export or the document completion.
			log.warn("Failed to write export audit for config={}, sourceRecord={}, pInstanceId={} — continuing",
					config.getId(), sourceRecord, pInstanceId, e);
		}
	}
}
