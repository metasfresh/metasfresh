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
import de.metas.externalsystem.ExternalSystemErrorContext;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.endpoint.ExternalSystemEndpoint;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository;
import de.metas.externalsystem.process.InvokeScriptedExportConversionAction;
import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
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

import static de.metas.common.externalsystem.ExternalSystemConstants.COMMAND_CONVERT_MESSAGE_FROM_METASFRESH;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ERROR_CONTEXT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
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

	@NonNull private final ExternalSystemScriptedExportConversionRepository externalSystemScriptedExportConversionRepository;
	@NonNull private final ExternalSystemEndpointRepository externalSystemEndpointRepository;

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
	public ImmutableList<ExternalSystemScriptedExportConversionConfig> getMatchingTriggerOnCompleteConfigsByTableAndClientId(@NonNull final AdTableAndClientId tableAndClientId, @NonNull final Integer recordId)
	{
		return externalSystemScriptedExportConversionRepository.getTriggerOnCompleteConfigsByTableAndClientIds(tableAndClientId).stream()
				.filter(config -> isConfigMatchingRecord(config, recordId))
				.collect(ImmutableList.toImmutableList());
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
		parameters.put(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, tableDAO.retrieveTableName(config.getTableId()));
		parameters.put(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, outboundDataProcessRecordId);

		if (errorContext != null)
		{
			parameters.put(PARAM_ERROR_CONTEXT, errorContext);
		}

		return parameters;
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

		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(context)
				.setRecord(TableRecordReference.of(config.getTableId(), StringUtils.toIntegerOrZero(outboundDataProcessRecordId)))
				.setAD_Process_ID(config.getOutboundDataProcessId())
				.addParameter(rootKeyColumnName, outboundDataProcessRecordId)
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
		return executeInvokeScriptedExportConversionActionAndGetResult(config, recordId, null).getExceptions();
	}

	@NonNull
	public ExternalSystemInvocationResult executeInvokeScriptedExportConversionActionAndGetResult(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			final int recordId,
			@Nullable final ExternalSystemErrorContext errorContext)
	{
		final int configTableId = tableDAO.retrieveTableId(I_ExternalSystem_Config_ScriptedExportConversion.Table_Name);
		try
		{
			final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder()
					.setCtx(getCtx())
					.setRecord(configTableId, config.getId().getRepoId())
					.setAD_ProcessByClassname(InvokeScriptedExportConversionAction.class.getName())
					.addParameter(PARAM_EXTERNAL_REQUEST, COMMAND_CONVERT_MESSAGE_FROM_METASFRESH)
					.addParameter(PARAM_CHILD_CONFIG_ID, config.getId().getRepoId())
					.addParameter(PARAM_Record_ID, recordId);

			if (errorContext != null)
			{
				processInfoBuilder.addParameter(PARAM_ERROR_CONTEXT, errorContext.getCode());
			}

			final ProcessInfo processInfo = processInfoBuilder.buildAndPrepareExecution().executeSync().getProcessInfo();

			return ExternalSystemInvocationResult.success(processInfo.getPinstanceId());
		}
		catch (final Exception e)
		{
			log.warn("{} process failed for Config ID {}, Record ID: {}",
					InvokeScriptedExportConversionAction.class.getName(),
					config.getId(), recordId, e);
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
}
