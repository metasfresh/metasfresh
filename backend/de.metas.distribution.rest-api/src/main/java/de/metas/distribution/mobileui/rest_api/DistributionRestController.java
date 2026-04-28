package de.metas.distribution.mobileui.rest_api;

import de.metas.Profiles;
import de.metas.distribution.mobileui.DistributionMobileApplication;
import de.metas.distribution.mobileui.external_services.hu.DistributionHUService;
import de.metas.distribution.mobileui.rest_api.json.JsonDistributionEvent;
import de.metas.distribution.mobileui.rest_api.json.JsonDropAllRequest;
import de.metas.distribution.mobileui.rest_api.json.JsonGetHUInfoByScannedCodeRequest;
import de.metas.distribution.mobileui.rest_api.json.JsonGetNextEligiblePickFromLineRequest;
import de.metas.distribution.mobileui.rest_api.json.JsonGetNextEligiblePickFromLineResponse;
import de.metas.distribution.mobileui.rest_api.json.JsonHUInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.scannable_code.ScannedCode;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.compiere.util.Env.getADLanguageOrBaseLanguage;
import static org.compiere.util.Env.getLoggedUserId;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/distribution")
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class DistributionRestController
{
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private final DistributionMobileApplication distributionMobileApplication;
	@NonNull private final WorkflowRestController workflowRestController;
	@NonNull private final DistributionHUService huService;

	private void assertApplicationAccess()
	{
		final MobileApplicationPermissions permissions = Env.getUserRolePermissions().getMobileApplicationPermissions();
		mobileApplicationService.assertAccess(distributionMobileApplication.getApplicationId(), permissions);
	}

	@PostMapping("/hu/byScannedCode")
	public @NonNull JsonHUInfo getHUInfoByQRCode(@RequestBody @NonNull final JsonGetHUInfoByScannedCodeRequest request)
	{
		assertApplicationAccess();
		final HUQRCode huQRCode = huService.resolveHUQRCode(ScannedCode.ofString(request.getScannedCode()));
		return JsonHUInfo.of(huQRCode.toRenderedJson());
	}

	@PostMapping("/nextEligiblePickFromLine")
	public JsonGetNextEligiblePickFromLineResponse getNextEligiblePickFromLine(@RequestBody @NonNull final JsonGetNextEligiblePickFromLineRequest request)
	{
		assertApplicationAccess();
		return distributionMobileApplication.getNextEligiblePickFromLine(request, getLoggedUserId());
	}

	@PostMapping("/event")
	public JsonWFProcess postEvent(@RequestBody @NonNull final JsonDistributionEvent event)
	{
		assertApplicationAccess();
		final WFProcess wfProcess = distributionMobileApplication.processEvent(event, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/dropAll")
	public void dropAllTo(@RequestBody @NonNull final JsonDropAllRequest request)
	{
		assertApplicationAccess();
		distributionMobileApplication.dropAll(request, getLoggedUserId());
	}

	@PostMapping("/job/{wfProcessId}/complete")
	public WFProcess complete(@PathVariable("wfProcessId") final String wfProcessIdStr)
	{
		assertApplicationAccess();
		return distributionMobileApplication.complete(WFProcessId.ofString(wfProcessIdStr), getLoggedUserId());
	}

	@PostMapping("/print/materialInTransitReport")
	public void printMaterialInTransitReport()
	{
		assertApplicationAccess();
		distributionMobileApplication.printMaterialInTransitReport(getLoggedUserId(), getADLanguageOrBaseLanguage());
	}

}
