package de.metas.manufacturing.workflows_api.rest_api;

import de.metas.Profiles;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonCreateIssueScheduleOnTheFlyRequest;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateRequest;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateResponse;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.security.mobile_application.MobileApplicationPermissions;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/manufacturing" })
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class ManufacturingMobileRestController
{
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private final ManufacturingMobileApplication manufacturingMobileApplication;
	@NonNull private final WorkflowRestController workflowRestController;

	private void assertApplicationAccess()
	{
		final MobileApplicationPermissions permissions = Env.getUserRolePermissions().getMobileApplicationPermissions();
		mobileApplicationService.assertAccess(manufacturingMobileApplication.getApplicationId(), permissions);
	}

	@PostMapping("/event")
	public JsonWFProcess postEvents(@RequestBody @NonNull final JsonManufacturingOrderEvent event)
	{
		assertApplicationAccess();
		final WFProcess wfProcess = manufacturingMobileApplication.processEvent(event, Env.getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/issueSchedule/createOnTheFly")
	public JsonWFProcess createIssueScheduleOnTheFly(@RequestBody @NonNull final JsonCreateIssueScheduleOnTheFlyRequest request)
	{
		assertApplicationAccess();
		final WFProcessId wfProcessId = WFProcessId.ofString(request.getWfProcessId());
		final WFProcess wfProcess = manufacturingMobileApplication.createIssueScheduleOnTheFly(
				wfProcessId,
				Env.getLoggedUserId(),
				request.getHuQRCode());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/generateHUQRCodes")
	public JsonFinishGoodsReceiveQRCodesGenerateResponse generateHUQRCodes(@RequestBody @NonNull JsonFinishGoodsReceiveQRCodesGenerateRequest request)
	{
		assertApplicationAccess();
		return manufacturingMobileApplication.generateFinishGoodsReceiveQRCodes(request);
	}
}
