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

import de.metas.adempiere.service.IColumnBL;
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
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_EP;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_METHOD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_TOKEN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME;

@Service
@RequiredArgsConstructor
public class ExternalSystemScriptedExportConversionService
{
	private final Logger log = LogManager.getLogger(getClass());

	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IColumnBL columnBL = Services.get(IColumnBL.class);

	@NonNull
	private final ExternalSystemScriptedExportConversionRepository externalSystemScriptedExportConversionRepository;

	@NonNull
	public Stream<ExternalSystemScriptedExportConversionConfig> retrieveActiveConfigs()
	{
		return externalSystemScriptedExportConversionRepository.retrieveActiveConfigs();
	}

	@NonNull
	public Optional<ExternalSystemScriptedExportConversionConfig> retrieveBestMatchingConfig(
			@NonNull final AdTableAndClientId tableAndClientId,
			@NonNull final Integer recordId)
	{
		return externalSystemScriptedExportConversionRepository.retrieveActiveBy(tableAndClientId)
				.filter(config -> isConfigMatchingRecord(config, recordId))
				.findAny();
	}

	public boolean existsActive(@NonNull final AdTableAndClientId tableAndClientId)
	{
		return externalSystemScriptedExportConversionRepository.existsActive(tableAndClientId);
	}

	public boolean existsActiveOutOfTrx(@NonNull final AdTableAndClientId tableAndClientId)
	{
		return externalSystemScriptedExportConversionRepository.existsActiveOutOfTrx(tableAndClientId);
	}

	@NonNull
	public Map<String, String> getParameters(
			@NonNull final ExternalSystemScriptedExportConversionConfig config,
			@NonNull final Properties context,
			@NonNull final String outboundDataProcessRecordId)
	{
		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, getOutboundProcessResponse(config, context, outboundDataProcessRecordId));
		parameters.put(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, config.getScriptIdentifier());
		parameters.put(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_EP, config.getOutboundHttpEndpoint());
		parameters.put(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_TOKEN, config.getOutboundHttpToken());
		parameters.put(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_METHOD, config.getOutboundHttpMethod());
		parameters.put(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, tableDAO.retrieveTableName(config.getAdTableId()));
		parameters.put(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, outboundDataProcessRecordId);

		return parameters;
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
		final String rootTableName = tableDAO.retrieveTableName(config.getAdTableId());
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
		final String rootTableName = tableDAO.retrieveTableName(config.getAdTableId());
		final String rootKeyColumnName = columnBL.getSingleKeyColumn(rootTableName);

		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(context)
				.setRecord(TableRecordReference.of(config.getAdTableId(), StringUtils.toIntegerOrZero(outboundDataProcessRecordId)))
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
}
