/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v1.externlasystem.dto;

import de.metas.RestUtils;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.CreatePInstanceLogRequest;
import de.metas.common.rest_api.v1.JsonError;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.common.rest_api.v1.JsonPInstanceLog;
import de.metas.common.rest_api.v1.issue.JsonCreateIssueResponse;
import de.metas.common.rest_api.v1.issue.JsonCreateIssueResponseItem;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.InsertRemoteIssueRequest;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.audit.CreateExportAuditRequest;
import de.metas.externalsystem.audit.ExternalSystemExportAudit;
import de.metas.externalsystem.audit.ExternalSystemExportAuditRepo;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoLog;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_CHILD_CONFIG_ID;
import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_EXTERNAL_REQUEST;

@Service
public class ExternalSystemService
{
	private static final transient Logger logger = LogManager.getLogger(ExternalSystemService.class);
	private static final String DEFAULT_ISSUE_SUMMARY = "No summary provided.";

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IADPInstanceDAO instanceDAO = Services.get(IADPInstanceDAO.class);
	private final ExternalSystemConfigRepo externalSystemConfigRepo;
	private final ExternalSystemExportAuditRepo externalSystemExportAuditRepo;

	public ExternalSystemService(
			final ExternalSystemConfigRepo externalSystemConfigRepo,
			final ExternalSystemExportAuditRepo externalSystemExportAuditRepo)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
		this.externalSystemExportAuditRepo = externalSystemExportAuditRepo;
	}

	@NonNull
	public ProcessExecutionResult invokeExternalSystem(@NonNull final InvokeExternalSystemProcessRequest invokeExternalSystemProcessRequest)
	{

		final ExternalSystemParentConfig externalSystemParentConfig =
				externalSystemConfigRepo.getByTypeAndValue(invokeExternalSystemProcessRequest.getExternalSystemType(),
														   invokeExternalSystemProcessRequest.getChildSystemConfigValue())
				.orElseThrow(() -> new AdempiereException("ExternalSystemParentConfig @NotFound@")
						.appendParametersToMessage()
						.setParameter("invokeExternalSystemProcessRequest", invokeExternalSystemProcessRequest));

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClassIfUnique(invokeExternalSystemProcessRequest
																							.getExternalSystemType()
																							.getExternalSystemProcessClassName());

		// note: when the AD_PInstance is created by the schedule, it's also stored as string
		final String configIdAsString = Integer.toString(externalSystemParentConfig.getChildConfig().getId().getRepoId());
		
		final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder();
		processInfoBuilder.setAD_Process_ID(processId.getRepoId());
		processInfoBuilder.addParameter(PARAM_EXTERNAL_REQUEST, invokeExternalSystemProcessRequest.getRequest());
		processInfoBuilder.addParameter(PARAM_CHILD_CONFIG_ID, configIdAsString);

		processInfoBuilder.setRecord(externalSystemParentConfig.getTableRecordReference());

		final ProcessExecutionResult processExecutionResult = processInfoBuilder
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		adPInstanceDAO.unlockAndSaveResult(processExecutionResult);

		return processExecutionResult;
	}

	@NonNull
	public JsonCreateIssueResponse createIssue(final @NonNull JsonError jsonError, final @NonNull PInstanceId pInstanceId)
	{
		final List<JsonCreateIssueResponseItem> adIssueIds = jsonError
				.getErrors()
				.stream()
				.map(error -> createInsertRemoteIssueRequest(error, pInstanceId))
				.map(errorManager::insertRemoteIssue)
				.map(id -> JsonCreateIssueResponseItem.builder().issueId(JsonMetasfreshId.of(id.getRepoId())).build())
				.collect(Collectors.toList());

		return JsonCreateIssueResponse.builder()
				.ids(adIssueIds)
				.build();
	}

	public void storeExternalPinstanceLog(@NonNull final CreatePInstanceLogRequest request, @NonNull final PInstanceId pInstanceId)
	{
		try
		{
			final List<ProcessInfoLog> processInfoLogList = request.getLogs()
					.stream()
					.map(JsonPInstanceLog::getMessage)
					.map(ProcessInfoLog::ofMessage)
					.collect(Collectors.toList());

			instanceDAO.saveProcessInfoLogs(pInstanceId, processInfoLogList);
		}
		catch (final Exception e)
		{
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(e);
			logger.error("Could not save the given model; message={}; AD_Issue_ID={}", e.getLocalizedMessage(), issueId);
			throw e;
		}
	}


	@NonNull
	public Optional<ExternalSystemParentConfig> getByTypeAndValue(@NonNull final ExternalSystemType type, @NonNull final String childConfigValue)
	{
		return externalSystemConfigRepo.getByTypeAndValue(type, childConfigValue);
	}

	@NonNull
	public Optional<ExternalSystemExportAudit> getMostRecentByTableReferenceAndSystem(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final ExternalSystemType externalSystemType)
	{
		return externalSystemExportAuditRepo.getMostRecentByTableReferenceAndSystem(tableRecordReference, externalSystemType);
	}

	@NonNull
	public ExternalSystemExportAudit createESExportAudit(@NonNull final CreateExportAuditRequest request)
	{
		return externalSystemExportAuditRepo.createESExportAudit(request);
	}

	@NonNull
	private InsertRemoteIssueRequest createInsertRemoteIssueRequest(final JsonErrorItem jsonErrorItem, final PInstanceId pInstanceId)
	{
		return InsertRemoteIssueRequest.builder()
				.issueCategory(jsonErrorItem.getIssueCategory())
				.issueSummary(StringUtils.isEmpty(jsonErrorItem.getMessage()) ? DEFAULT_ISSUE_SUMMARY : jsonErrorItem.getMessage())
				.sourceClassName(jsonErrorItem.getSourceClassName())
				.sourceMethodName(jsonErrorItem.getSourceMethodName())
				.stacktrace(jsonErrorItem.getStackTrace())
				.pInstance_ID(pInstanceId)
				.orgId(RestUtils.retrieveOrgIdOrDefault(jsonErrorItem.getOrgCode()))
				.build();
	}
}
