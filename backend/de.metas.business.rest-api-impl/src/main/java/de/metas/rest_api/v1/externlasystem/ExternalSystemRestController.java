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

package de.metas.rest_api.v1.externlasystem;

import de.metas.Profiles;
import de.metas.common.rest_api.v1.CreatePInstanceLogRequest;
import de.metas.common.rest_api.v1.JsonError;
import de.metas.common.rest_api.v1.issue.JsonCreateIssueResponse;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.rest_api.process.response.RunProcessResponse;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.v1.externlasystem.dto.ExternalSystemService;
import de.metas.rest_api.v1.externlasystem.dto.InvokeExternalSystemProcessRequest;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @deprecated please consider migrating to version 2 of this API.
 */
@Deprecated
@RestController
@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/externalsystem",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/externalsystem"})
@Profile(Profiles.PROFILE_App)
public class ExternalSystemRestController
{
	private final ExternalSystemService externalSystemService;

	public ExternalSystemRestController(final ExternalSystemService externalSystemService)
	{
		this.externalSystemService = externalSystemService;
	}

	@ApiOperation("Invoke an external system.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully invoked external system"),
			@ApiResponse(code = 401, message = "You are not authorized to invoke process"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request could not be processed")
	})
	@PostMapping(path = "{externalSystemConfigType}/{externalSystemChildConfigValue}/{request}")
	public ResponseEntity<?> invokeExternalSystem(
			@PathVariable final String externalSystemConfigType,
			@PathVariable final String externalSystemChildConfigValue,
			@PathVariable final String request)
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
						.build();

		return getResponse(externalSystemService.invokeExternalSystem(invokeExternalSystemProcessRequest));
	}

	@ApiOperation("Store external AD_PInstance logs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully stored external AD_PInstance logs"),
			@ApiResponse(code = 401, message = "You are not authorized to store AD_PInstance logs"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})

	@PostMapping(path = "{adPInstanceId}/externalstatus/message", consumes = "application/json")
	public ResponseEntity<?> storeLogs(@RequestBody @NonNull final CreatePInstanceLogRequest request, @PathVariable final Integer adPInstanceId)
	{
		externalSystemService.storeExternalPinstanceLog(request, PInstanceId.ofRepoId(adPInstanceId));
		return ResponseEntity.ok().build();
	}

	@ApiOperation("Create an AD_Issue. Note: it's not necessary that the process in question was started by the `invoke` endpoint.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created issue"),
			@ApiResponse(code = 401, message = "You are not authorized to create new issue"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PostMapping(path = "{AD_PInstance_ID}/externalstatus/error", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JsonCreateIssueResponse> handleError(@RequestBody @NonNull final JsonError request, @PathVariable final Integer AD_PInstance_ID)
	{
		final JsonCreateIssueResponse issueResponse = externalSystemService.createIssue(request, PInstanceId.ofRepoId(AD_PInstance_ID));
		return ResponseEntity.ok(issueResponse);
	}

	private ResponseEntity<?> getResponse(@NonNull final ProcessExecutionResult processExecutionResult)
	{
		final RunProcessResponse runProcessResponse;

		runProcessResponse = RunProcessResponse.builder()
				.pInstanceID(String.valueOf(processExecutionResult.getPinstanceId().getRepoId()))
				.errors(processExecutionResult.getThrowable() != null ?
						JsonError.ofSingleItem(JsonErrors.ofThrowable(processExecutionResult.getThrowable(), Env.getADLanguageOrBaseLanguage()))
						: null)
				.build();

		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(runProcessResponse);
	}
}
