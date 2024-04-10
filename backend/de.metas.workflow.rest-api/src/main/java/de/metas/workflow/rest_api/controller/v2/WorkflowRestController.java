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
import de.metas.Profiles;
import de.metas.RestUtils;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.document.DocumentNoFilter;
import de.metas.error.IErrorManager;
import de.metas.error.InsertRemoteIssueRequest;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.json.JsonLaunchersQuery;
import de.metas.workflow.rest_api.controller.v2.json.JsonMobileApplication;
import de.metas.workflow.rest_api.controller.v2.json.JsonMobileApplicationsList;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.controller.v2.json.JsonSetScannedBarcodeRequest;
import de.metas.workflow.rest_api.controller.v2.json.JsonSettings;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.controller.v2.json.JsonWorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.controller.v2.json.JsonWorkflowLaunchersFacetsQuery;
import de.metas.workflow.rest_api.controller.v2.json.JsonWorkflowLaunchersList;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.api.Params;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.Map;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/userWorkflows")
@RestController
@Profile(Profiles.PROFILE_App)
public class WorkflowRestController
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final WorkflowRestAPIService workflowRestAPIService;
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	private static final String SYSCONFIG_SETTINGS_PREFIX = "mobileui.frontend.";

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

	@PostMapping("/logout")
	public void logout()
	{
		final UserId loggedUserId = Env.getLoggedUserId();
		workflowRestAPIService.logout(loggedUserId);
	}

	@GetMapping("/apps")
	public JsonMobileApplicationsList getMobileApplications()
	{
		final UserId loggedUserId = Env.getLoggedUserId();
		final JsonOpts jsonOpts = newJsonOpts();
		return JsonMobileApplicationsList.builder()
				.applications(
						workflowRestAPIService.streamMobileApplicationInfos(loggedUserId)
								.map(applicationInfo -> JsonMobileApplication.of(applicationInfo, jsonOpts))
								.sorted(Comparator.comparing(JsonMobileApplication::getSortNo).thenComparing(JsonMobileApplication::getCaption))
								.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@GetMapping("/launchers")
	@Deprecated
	public JsonWorkflowLaunchersList getLaunchers(
			@RequestParam("applicationId") final String applicationIdStr,
			@RequestParam(value = "filterByQRCode", required = false) final String filterByQRCodeStr)
	{
		return getLaunchers(JsonLaunchersQuery.builder()
				.applicationId(MobileApplicationId.ofString(applicationIdStr))
				.filterByQRCode(GlobalQRCode.ofNullableString(filterByQRCodeStr))
				.build());
	}

	@PostMapping("/launchers/query")
	public JsonWorkflowLaunchersList getLaunchers(@RequestBody @NonNull final JsonLaunchersQuery query)
	{
		final WorkflowLaunchersList launchers = workflowRestAPIService.getLaunchers(toWorkflowLaunchersQuery(query));

		return JsonWorkflowLaunchersList.of(launchers, query, newJsonOpts());
	}

	private static WorkflowLaunchersQuery toWorkflowLaunchersQuery(final @NonNull JsonLaunchersQuery query)
	{
		return WorkflowLaunchersQuery.builder()
				.applicationId(query.getApplicationId())
				.userId(Env.getLoggedUserId())
				.filterByQRCode(query.getFilterByQRCode())
				.filterByDocumentNo(DocumentNoFilter.ofNullableString(query.getFilterByDocumentNo()))
				.facetIds(CollectionUtils.toImmutableSetOrNullIfEmpty(query.getFacetIds()))
				.limit(query.isCountOnly() ? QueryLimit.NO_LIMIT : null)
				.build();
	}

	@PostMapping("/facets")
	public JsonWorkflowLaunchersFacetGroupList getFacets(@RequestBody @NonNull final JsonWorkflowLaunchersFacetsQuery query)
	{
		final WorkflowLaunchersFacetGroupList result = workflowRestAPIService.getFacets(
				WorkflowLaunchersFacetQuery.builder()
						.applicationId(query.getApplicationId())
						.userId(Env.getLoggedUserId())
						.filterByDocumentNo(DocumentNoFilter.ofNullableString(query.getFilterByDocumentNo()))
						.activeFacetIds(CollectionUtils.toImmutableSetOrEmpty(query.getActiveFacetIds()))
						.build()
		);
		return JsonWorkflowLaunchersFacetGroupList.of(result, newJsonOpts());
	}

	@GetMapping("/wfProcess/{wfProcessId}")
	public JsonWFProcess getWFProcessById(@PathVariable("wfProcessId") final @NonNull String wfProcessIdStr)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final WFProcess wfProcess = workflowRestAPIService.getWFProcessById(wfProcessId);

		final UserId loggedUserId = Env.getLoggedUserId();
		wfProcess.assertHasAccess(loggedUserId);

		return toJson(wfProcess);
	}

	@PostMapping("/wfProcess/{wfProcessId}/continue")
	public JsonWFProcess continueWFProcess(@PathVariable("wfProcessId") final @NonNull String wfProcessIdStr)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final UserId loggedUserId = Env.getLoggedUserId();
		final WFProcess wfProcess = workflowRestAPIService.continueWFProcess(wfProcessId, loggedUserId);
		wfProcess.assertHasAccess(loggedUserId);
		return toJson(wfProcess);
	}

	@PostMapping("/wfProcess/start")
	public JsonWFProcess start(@RequestBody final @NonNull JsonWFProcessStartRequest request)
	{
		final UserId loggedUserId = Env.getLoggedUserId();
		final JsonOpts jsonOpts = newJsonOpts();

		final WFProcess wfProcess = workflowRestAPIService.startWorkflow(
				WorkflowStartRequest.builder()
						.applicationId(request.getApplicationId())
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

	@PostMapping("/wfProcess/abortAll")
	public void abortAll()
	{
		final UserId loggedUserId = Env.getLoggedUserId();

		workflowRestAPIService.abortAllWFProcesses(loggedUserId);
	}

	public JsonWFProcess toJson(final WFProcess wfProcess)
	{
		final JsonOpts jsonOpts = newJsonOpts();
		return toJson(wfProcess, jsonOpts);
	}

	private JsonWFProcess toJson(@NonNull final WFProcess wfProcess, @NonNull final JsonOpts jsonOpts)
	{
		return JsonWFProcess.of(
				wfProcess,
				workflowRestAPIService.getHeaderProperties(wfProcess),
				workflowRestAPIService.getUIComponents(wfProcess, jsonOpts),
				jsonOpts);
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
		final WFProcess wfProcess = workflowRestAPIService.setScannedBarcode(invokerId, wfProcessId, wfActivityId, request.getBarcode());

		return toJson(wfProcess);
	}

	@PostMapping("/wfProcess/{wfProcessId}/{wfActivityId}/userConfirmation")
	public JsonWFProcess setUserConfirmation(
			@PathVariable("wfProcessId") final String wfProcessIdStr,
			@PathVariable("wfActivityId") final String wfActivityIdStr)
	{
		final UserId invokerId = Env.getLoggedUserId();
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final WFActivityId wfActivityId = WFActivityId.ofString(wfActivityIdStr);
		final WFProcess wfProcess = workflowRestAPIService.setUserConfirmation(invokerId, wfProcessId, wfActivityId);

		return toJson(wfProcess);
	}

	@GetMapping("/settings")
	public JsonSettings getSettings()
	{
		final Map<String, String> map = sysConfigBL.getValuesForPrefix(SYSCONFIG_SETTINGS_PREFIX, true, Env.getClientAndOrgId());
		return JsonSettings.ofMap(map);
	}

	@PostMapping("/errors")
	public void logErrors(@RequestBody @NonNull final JsonError error)
	{
		error.getErrors().stream()
				.map(WorkflowRestController::toInsertRemoteIssueRequest)
				.forEach(errorManager::insertRemoteIssue);
	}

	private static InsertRemoteIssueRequest toInsertRemoteIssueRequest(final JsonErrorItem jsonErrorItem)
	{
		return InsertRemoteIssueRequest.builder()
				.issueCategory(jsonErrorItem.getIssueCategory())
				.issueSummary(StringUtils.trimBlankToOptional(jsonErrorItem.getMessage()).orElse("Error"))
				.sourceClassName(jsonErrorItem.getSourceClassName())
				.sourceMethodName(jsonErrorItem.getSourceMethodName())
				.stacktrace(jsonErrorItem.getStackTrace())
				.orgId(RestUtils.retrieveOrgIdOrDefault(jsonErrorItem.getOrgCode()))
				.frontendUrl(jsonErrorItem.getFrontendUrl())
				.build();
	}

}
