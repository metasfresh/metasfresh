package de.metas.hu_consolidation.mobile.rest_api;

import de.metas.Profiles;
import de.metas.hu_consolidation.mobile.HUConsolidationApplication;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonConsolidateRequest;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJobAvailableTargets;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationTarget;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/mobile/huConsolidation")
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class HUConsolidationRestController
{
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private final HUConsolidationApplication mobileApplication;
	@NonNull private final WorkflowRestController workflowRestController;

	private void assertApplicationAccess()
	{
		mobileApplicationService.assertAccess(mobileApplication.getApplicationId(), Env.getUserRolePermissions());
	}

	private static @NotNull UserId getLoggedUserId() {return Env.getLoggedUserId();}

	@GetMapping("/job/{wfProcessId}/target/available")
	public JsonHUConsolidationJobAvailableTargets getAvailableTargets(@PathVariable("wfProcessId") final String wfProcessIdStr)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		return mobileApplication.getAvailableTargets(wfProcessId, getLoggedUserId());
	}

	@PostMapping("/job/{wfProcessId}/target")
	public JsonWFProcess setTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestBody(required = false) @Nullable final JsonHUConsolidationTarget jsonTarget)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final HUConsolidationTarget target = jsonTarget != null ? jsonTarget.unbox() : null;
		final WFProcess wfProcess = mobileApplication.setTarget(wfProcessId, target, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/job/{wfProcessId}/target/close")
	public JsonWFProcess closeTarget(@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final WFProcess wfProcess = mobileApplication.closeTarget(wfProcessId, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/job/{wfProcessId}/consolidate")
	public JsonWFProcess consolidate(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestBody @NonNull final JsonConsolidateRequest request)
	{
		assertApplicationAccess();

		final WFProcess wfProcess = mobileApplication.consolidate(request.withWFProcessId(wfProcessIdStr), getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

}