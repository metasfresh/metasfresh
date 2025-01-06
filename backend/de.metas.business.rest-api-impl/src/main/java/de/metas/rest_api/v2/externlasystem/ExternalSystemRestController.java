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

package de.metas.rest_api.v2.externlasystem;

import de.metas.Profiles;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonExternalSystemInfo;
import de.metas.common.externalsystem.JsonInvokeExternalSystemParams;
import de.metas.common.externalsystem.status.JsonExternalStatusResponse;
import de.metas.common.externalsystem.status.JsonStatusRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.CreatePInstanceLogRequest;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.rest_api.v2.issue.JsonCreateIssueResponse;
import de.metas.error.AdIssueId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.externalservice.common.ExternalStatus;
import de.metas.externalsystem.externalservice.status.StoreExternalSystemStatusRequest;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.rest_api.v2.externlasystem.dto.InvokeExternalSystemProcessRequest;
import de.metas.rest_api.v2.process.response.RunProcessResponse;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@RestController
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/externalsystem" })
@Profile(Profiles.PROFILE_App)
public class ExternalSystemRestController
{
	private final ExternalSystemService externalSystemService;

	public ExternalSystemRestController(final ExternalSystemService externalSystemService)
	{
		this.externalSystemService = externalSystemService;
	}

	@Operation(summary = "Invoke an external system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully invoked external system"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to invoke process"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request could not be processed")
	})
	@PostMapping(path = "/invoke/{externalSystemConfigType}/{externalSystemChildConfigValue}/{request}")
	public ResponseEntity<?> invokeExternalSystem(
			@PathVariable final String externalSystemConfigType,
			@PathVariable final String externalSystemChildConfigValue,
			@Parameter(description = "The actual request like `getOrders` of the external system invocation process") @PathVariable final String request,
			@RequestBody @Nullable final JsonInvokeExternalSystemParams externalSystemParams)
	{
		final ExternalSystemType externalSystemType = ExternalSystemType.ofCodeOrNameOrNull(externalSystemConfigType);
		if (externalSystemType == null)
		{
			throw new AdempiereException("Unsupported externalSystemConfigType=" + externalSystemConfigType);
		}
		final InvokeExternalSystemProcessRequest invokeExternalSystemProcessRequest =
				InvokeExternalSystemProcessRequest.builder()
						.externalSystemType(externalSystemType)
						.childSystemConfigValue(externalSystemChildConfigValue)
						.request(request)
						.jsonInvokeExternalSystemParams(externalSystemParams)
						.build();

		final ProcessExecutionResult processExecutionResult = externalSystemService.invokeExternalSystem(invokeExternalSystemProcessRequest);
		return getResponse(processExecutionResult);
	}

	@Operation(summary = "Enables an external system to create an `AD_PInstance_Log`." 
			+ "\nThe `AD_PInstance_ID` is the one the external system was invoked with.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully stored external AD_PInstance logs"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to store AD_PInstance logs"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})

	@PostMapping(path = "/externalstatus/{AD_PInstance_ID}/message", consumes = "application/json")
	public ResponseEntity<?> storeLogs(
			@RequestBody @NonNull final CreatePInstanceLogRequest request,
			@PathVariable final Integer AD_PInstance_ID)
	{
		externalSystemService.storeExternalPinstanceLog(request, PInstanceId.ofRepoId(AD_PInstance_ID));
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Create an AD_Issue. "
			+ "\nThe `AD_PInstance_ID` is the one the external system was invoked with."
			+ "\nNote: it's not necessary that the process in question was started by the `invoke` endpoint.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created issue"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create new issue"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})
	@PostMapping(path = "/externalstatus/{AD_PInstance_ID}/error", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JsonCreateIssueResponse> handleError(
			@RequestBody @NonNull final JsonError request,
			@PathVariable final Integer AD_PInstance_ID)
	{
		final JsonCreateIssueResponse issueResponse = externalSystemService.createIssue(request, PInstanceId.ofRepoId(AD_PInstance_ID));
		return ResponseEntity.ok(issueResponse);
	}

	@Operation(summary = "Upsert external system runtime parameter")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully stored external system runtime parameter"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to store external system runtime parameters"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})
	@PutMapping(path = "/runtimeParameter/bulk", consumes = "application/json")
	public ResponseEntity<?> storeRuntimeParameters(@RequestBody @NonNull final JsonESRuntimeParameterUpsertRequest request)
	{
		externalSystemService.upsertRuntimeParameters(request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Store external system status.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully stored external system status"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to store external system status"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request could not be processed")
	})
	@PostMapping(path = "/service/{externalSystemConfigType}/{externalSystemChildConfigValue}/{serviceValue}/status")
	public ResponseEntity<?> storeExternalSystemStatus(@RequestBody @NonNull final JsonStatusRequest request,
			@Parameter(description = "Used to identify the type of the external system. Translates to 'ExternalSystem_Config.Type'.") @PathVariable @NonNull final String externalSystemConfigType,
			@Parameter(description = "Used to identify an IExternalSystemChildConfig together with 'externalSystemConfigType'.") @PathVariable @NonNull final String externalSystemChildConfigValue,
			@Parameter(description = "Used to identify an ExternalSystemService. Translates to 'ExternalSystem_Service.Value'.") @PathVariable @NonNull final String serviceValue)
	{
		final ExternalSystemType externalSystemType = ExternalSystemType.ofCodeOrNameOrNull(externalSystemConfigType);

		if (externalSystemType == null)
		{
			throw new AdempiereException("Unsupported externalSystemConfigType=" + externalSystemConfigType);
		}

		final AdIssueId adIssueId = JsonMetasfreshId.mapToOrNull(request.getAdIssueId(), AdIssueId::ofRepoId);

		final PInstanceId pInstanceId = JsonMetasfreshId.mapToOrNull(request.getPInstanceId(), PInstanceId::ofRepoId);

		externalSystemService.storeExternalSystemStatus(StoreExternalSystemStatusRequest.builder()
																.status(ExternalStatus.ofCode(request.getStatus().name()))
																.childSystemConfigValue(externalSystemChildConfigValue)
																.serviceValue(serviceValue)
																.systemType(externalSystemType)
																.adIssueId(adIssueId)
																.pInstanceId(pInstanceId)
																.message(request.getMessage())
																.build());
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/service/{externalSystemConfigType}/status")
	public ResponseEntity<?> getExternalSystemStatus(@PathVariable @NonNull final String externalSystemConfigType)
	{
		final ExternalSystemType externalSystemType = ExternalSystemType.ofCodeOrNameOrNull(externalSystemConfigType);
		if (externalSystemType == null)
		{
			throw new AdempiereException("Unsupported externalSystemConfigType=" + externalSystemConfigType);
		}

		final JsonExternalStatusResponse statusInfo = externalSystemService.getStatusInfo(externalSystemType);
		return ResponseEntity.ok().body(statusInfo);
	}

	@Operation(summary = "Get external system info.\n Note, only externalSystemConfigType=GRSSignum is supported at the moment.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved external system info"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to retrieve external system info"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request could not be processed")
	})
	@GetMapping(path = "/{externalSystemConfigType}/{externalSystemChildConfigValue}/info")
	public ResponseEntity<?> getExternalSystemInfo(
			@PathVariable @NonNull final String externalSystemConfigType,
			@PathVariable @NonNull final String externalSystemChildConfigValue)
	{
		final ExternalSystemType externalSystemType = ExternalSystemType.ofCodeOrNameOrNull(externalSystemConfigType);
		if (externalSystemType == null)
		{
			throw new AdempiereException("Unsupported externalSystemConfigType=" + externalSystemConfigType);
		}

		final JsonExternalSystemInfo systemInfo = externalSystemService.getExternalSystemInfo(externalSystemType, externalSystemChildConfigValue);
		return ResponseEntity.ok().body(systemInfo);
	}

	private ResponseEntity<?> getResponse(@NonNull final ProcessExecutionResult processExecutionResult)
	{
		final ResponseEntity.BodyBuilder responseEntity;
		
		final RunProcessResponse.RunProcessResponseBuilder responseBodyBuilder = RunProcessResponse.builder()
				.pInstanceID(String.valueOf(processExecutionResult.getPinstanceId().getRepoId()));

		if (processExecutionResult.getThrowable() != null)
		{
			final JsonError error = JsonError.ofSingleItem(JsonErrors.ofThrowable(processExecutionResult.getThrowable(), Env.getADLanguageOrBaseLanguage()));
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

			responseBodyBuilder.errors(error);
		}
		else
		{
			responseEntity = ResponseEntity.ok();
		}

		return responseEntity
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseBodyBuilder.build());
	}
}
