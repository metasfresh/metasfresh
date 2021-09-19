/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.controller.v2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.Profiles;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.controller.v2.json.JsonSetScannedBarcodeRequest;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessHeaderProperties;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.controller.v2.json.JsonWorkflowLaunchersList;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherProviderId;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.util.api.Params;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/userWorkflows")
@RestController
@Profile(Profiles.PROFILE_App)
public class WorkflowRestController
{
	private final WorkflowRestAPIService workflowRestAPIService;

	public WorkflowRestController(
			@NonNull final WorkflowRestAPIService workflowRestAPIService)
	{
		this.workflowRestAPIService = workflowRestAPIService;
	}

	private JsonOpts newJsonOpts()
	{
		return JsonOpts.builder()
				.adLanguage(Env.getADLanguageOrBaseLanguage())
				.build();
	}

	@GetMapping("/launchers")
	public JsonWorkflowLaunchersList getLaunchers()
	{
		final UserId loggedUserId = Env.getLoggedUserId();

		final List<WorkflowLauncher> launchers = workflowRestAPIService.getLaunchers(loggedUserId);
		return JsonWorkflowLaunchersList.of(launchers, newJsonOpts());
	}

	@GetMapping("/wfProcess/{wfProcessId}")
	public JsonWFProcess getWFProcessById(@PathVariable("wfProcessId") final @NonNull String wfProcessIdStr)
	{
		return getWFProcessById(WFProcessId.ofString(wfProcessIdStr));
	}

	private JsonWFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final WFProcess wfProcess = workflowRestAPIService.getWFProcessById(wfProcessId);

		final UserId loggedUserId = Env.getLoggedUserId();
		wfProcess.assertHasAccess(loggedUserId);

		final JsonOpts jsonOpts = newJsonOpts();
		return toJson(wfProcess, jsonOpts);
	}

	@PostMapping("/wfProcess/start")
	public JsonWFProcess start(@RequestBody final @NonNull JsonWFProcessStartRequest request)
	{
		final UserId loggedUserId = Env.getLoggedUserId();
		final JsonOpts jsonOpts = newJsonOpts();

		final WFProcess wfProcess = workflowRestAPIService.startWorkflow(
				WorkflowStartRequest.builder()
						.providerId(WorkflowLauncherProviderId.ofString(request.getWfProviderId()))
						.wfParameters(Params.ofMap(request.getWfParameters()))
						.invokerId(loggedUserId)
						.build());

		return toJson(wfProcess, jsonOpts);
	}

	@PostMapping("/wfProcess/{wfProcessId}/abort")
	public void abort(@PathVariable("wfProcessId") final @NonNull String wfProcessIdStr)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final UserId loggedUserId = Env.getLoggedUserId();

		workflowRestAPIService.abortWFProcess(wfProcessId, loggedUserId);
	}

	private JsonWFProcess toJson(final WFProcess wfProcess, final JsonOpts jsonOpts)
	{
		final WFProcessHeaderProperties headerProperties = workflowRestAPIService.getHeaderProperties(wfProcess);

		final ImmutableMap<WFActivityId, UIComponent> uiComponents = workflowRestAPIService.getUIComponents(wfProcess, jsonOpts);

		return JsonWFProcess.builder()
				.id(wfProcess.getId().getAsString())
				.headerProperties(JsonWFProcessHeaderProperties.of(headerProperties, jsonOpts))
				.activities(wfProcess.getActivitiesInOrder()
						.stream()
						.map(activity -> toJson(
								activity,
								uiComponents.get(activity.getId()),
								jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static JsonWFActivity toJson(
			@NonNull final WFActivity activity,
			@NonNull final UIComponent uiComponent,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		return JsonWFActivity.builder()
				.activityId(activity.getId().getAsString())
				.caption(activity.getCaption().translate(adLanguage))
				.componentType(uiComponent.getType().getAsString())
				.readonly(uiComponent.isReadonly())
				.componentProps(uiComponent.getProperties().toJson(jsonOpts::convertValueToJson))
				.build();
	}

	@PostMapping("/wfProcess/{wfProcessId}/{wfActivityId}/scannedBarcode")
	public JsonWFProcess setScannedBarcode(
			@PathVariable("wfProcessId") final String wfProcessIdStr,
			@PathVariable("wfActivityId") final String wfActivityIdStr,
			@RequestBody final JsonSetScannedBarcodeRequest request)
	{
		final UserId invokerId = Env.getLoggedUserId();
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final WFActivityId wfActivityId = WFActivityId.ofString(wfActivityIdStr);
		workflowRestAPIService.setScannedBarcode(invokerId, wfProcessId, wfActivityId, request.getBarcode());

		return getWFProcessById(wfProcessId);
	}

	@PostMapping("/wfProcess/{wfProcessId}/{wfActivityId}/userConfirmation")
	public JsonWFProcess setUserConfirmation(
			@PathVariable("wfProcessId") final String wfProcessIdStr,
			@PathVariable("wfActivityId") final String wfActivityIdStr)
	{
		final UserId invokerId = Env.getLoggedUserId();
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final WFActivityId wfActivityId = WFActivityId.ofString(wfActivityIdStr);
		workflowRestAPIService.setUserConfirmation(invokerId, wfProcessId, wfActivityId);

		return getWFProcessById(wfProcessId);
	}
}
