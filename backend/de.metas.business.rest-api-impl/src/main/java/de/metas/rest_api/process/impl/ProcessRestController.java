/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.process.impl;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessBasicInfo;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessType;
import de.metas.process.impl.ADProcessDAO;
import de.metas.rest_api.process.request.RunProcessRequest;
import de.metas.rest_api.process.response.GetAvailableProcessesResponse;
import de.metas.rest_api.process.response.JSONProcessBasicInfo;
import de.metas.rest_api.process.response.JSONProcessParamBasicInfo;
import de.metas.rest_api.process.response.Message;
import de.metas.rest_api.process.response.RunProcessResponse;
import de.metas.security.PermissionServiceFactories;
import de.metas.security.PermissionServiceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ProcessRestController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class ProcessRestController
{
	private static final transient Logger logger = LogManager.getLogger(ADProcessDAO.class);

	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/process";

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final PermissionServiceFactory permissionServiceFactory = PermissionServiceFactories.currentContext();

	private final ProcessService processService;

	public ProcessRestController(final ProcessService processService)
	{
		this.processService = processService;
	}

	@PostMapping("{value}/invoke")
	public ResponseEntity<?> invokeProcess(
			@NonNull @PathVariable("value") final String processValue,
			@Nullable @RequestBody final RunProcessRequest request)
	{

		final Optional<AdProcessId> processId = getProcessIdIfRunnable(processValue);

		if (!processId.isPresent())
		{
			return ResponseEntity.badRequest().body(Message.of("The requested process is not runnable or it doesn't exist!"));
		}

		final boolean cannotRunProcess = !permissionServiceFactory.createPermissionService().canRunProcess(processId.get());

		if (cannotRunProcess)
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder();
		processInfoBuilder.setAD_Process_ID(processId.get());

		final boolean parametersPresent = request != null && !Check.isEmpty(request.getProcessParameters());

		if (parametersPresent)
		{
			request.getProcessParameters().forEach(param -> processInfoBuilder.addParameter(param.getName(), param.getValue()));
		}

		final ProcessExecutionResult processExecutionResult = processInfoBuilder
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		return getResponse(processExecutionResult);
	}

	@GetMapping("/available")
	public ResponseEntity<GetAvailableProcessesResponse> getAllProcesses()
	{
		final List<ProcessBasicInfo> processList = processService.getProcessesByType(ProcessType.getTypesRunnableFromAppRestController());

		final List<JSONProcessBasicInfo> jsonProcessList = processList
				.stream()
				.map(this::buildJSONProcessBasicInfo)
				.collect(Collectors.toList());

		final GetAvailableProcessesResponse response = GetAvailableProcessesResponse
				.builder()
				.availableProcesses(jsonProcessList)
				.build();

		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(response);
	}

	private ResponseEntity<?> getResponse(@NonNull final ProcessExecutionResult processExecutionResult)
	{
		if (processExecutionResult.isError())
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(processExecutionResult.getThrowable());
		}
		else if (Check.isNotBlank(processExecutionResult.getJsonResult()))
		{
			return ResponseEntity
					.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(processExecutionResult.getJsonResult());
		}
		else if (Check.isNotBlank(processExecutionResult.getReportFilename()))
		{
			final String contentType = Check.isNotBlank(processExecutionResult.getReportContentType())
					? processExecutionResult.getReportContentType()
					: MediaType.APPLICATION_OCTET_STREAM_VALUE;

			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + processExecutionResult.getReportFilename() + "\"")
					.body(processExecutionResult.getReportData());
		}
		else
		{
			final RunProcessResponse runProcessResponse = RunProcessResponse.builder()
					.pInstanceID(String.valueOf(processExecutionResult.getPinstanceId().getRepoId()))
					.summary(processExecutionResult.getSummary())
					.build();

			return ResponseEntity
					.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(runProcessResponse);
		}
	}

	private Optional<AdProcessId> getProcessIdIfRunnable(@NonNull final String processValue)
	{
		AdProcessId processId;
		try
		{
			final I_AD_Process process = adProcessDAO.retrieveProcessByValue(Env.getCtx(), processValue);

			final ProcessType processType = ProcessType.ofCode(process.getType());

			final boolean isNotRunnable = !ProcessType.getTypesRunnableFromAppRestController().contains(processType);

			if (isNotRunnable)
			{
				throw new AdempiereException("The given process type is not runnable from the app ProcessRestController!")
						.appendParametersToMessage()
						.setParameter("ProcessValue", processValue)
						.setParameter("ProcessType", processType);
			}

			processId = AdProcessId.ofRepoId(process.getAD_Process_ID());
		}
		catch (final Exception e)
		{
			processId = null;
			logger.error(e.getMessage(), e);
		}

		return Optional.ofNullable(processId);
	}

	private JSONProcessBasicInfo buildJSONProcessBasicInfo(@NonNull final ProcessBasicInfo processBasicInfo)
	{
		final String language = Env.getAD_Language();

		final JSONProcessBasicInfo.JSONProcessBasicInfoBuilder jsonProcessInfoBuilder = JSONProcessBasicInfo.builder()
				.processId(String.valueOf(processBasicInfo.getProcessId().getRepoId()))
				.value(processBasicInfo.getValue())
				.type(processBasicInfo.getType().getCode())
				.name(processBasicInfo.getName().translate(language))
				.description(processBasicInfo.getTranslatedDescriptionOrNull(language));

		if (processBasicInfo.getParameters() != null)
		{
			final List<JSONProcessParamBasicInfo> processParamBasicInfos = processBasicInfo.getParameters()
					.stream()
					.map(paramInfo -> JSONProcessParamBasicInfo
							.builder()
							.columnName(paramInfo.getColumnName())
							.type(paramInfo.getType())
							.name(paramInfo.getName().translate(language))
							.description(paramInfo.getTranslatedDescriptionOrNull(language))
							.build())
					.collect(Collectors.toList());

			jsonProcessInfoBuilder.parameters(processParamBasicInfos);
		}

		return jsonProcessInfoBuilder.build();
	}
}
