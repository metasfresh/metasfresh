package de.metas.inventory.mobileui.rest_api;

import de.metas.Profiles;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.inventory.mobileui.InventoryMobileApplication;
import de.metas.inventory.mobileui.job.qrcode.ResolveHURequest;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import de.metas.inventory.mobileui.job.service.InventoryJobService;
import de.metas.inventory.mobileui.mappers.InventoryWFProcessMapper;
import de.metas.inventory.mobileui.rest_api.json.JsonCountRequest;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveHURequest;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveHUResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveLocatorRequest;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveLocatorResponse;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.security.mobile_application.MobileApplicationPermissions;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/mobile/inventory")
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class InventoryRestController
{
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private InventoryJobService jobService;
	@NonNull private final WorkflowRestController workflowRestController;

	private void assertApplicationAccess()
	{
		final MobileApplicationPermissions permissions = Env.getUserRolePermissions().getMobileApplicationPermissions();
		mobileApplicationService.assertAccess(InventoryMobileApplication.APPLICATION_ID, permissions);
	}

	@PostMapping("/resolveLocator")
	public JsonResolveLocatorResponse resolveLocator(@RequestBody @NonNull final JsonResolveLocatorRequest request)
	{
		assertApplicationAccess();

		final LocatorScannedCodeResolverResult result = jobService.resolveLocator(
				request.getScannedCode(),
				request.getWfProcessId(),
				request.getLineId(),
				Env.getLoggedUserId()
		);
		return JsonResolveLocatorResponse.of(result);
	}

	@PostMapping("/resolveHU")
	public JsonResolveHUResponse resolveHU(@RequestBody @NonNull final JsonResolveHURequest request)
	{
		assertApplicationAccess();

		final ResolveHUResponse response = jobService.resolveHU(
				ResolveHURequest.builder()
						.scannedCode(request.getScannedCode())
						.callerId(Env.getLoggedUserId())
						.wfProcessId(request.getWfProcessId())
						.lineId(request.getLineId())
						.locatorId(request.getLocatorQRCode().getLocatorId())
						.build()
		);

		return JsonResolveHUResponse.of(response, Env.getADLanguageOrBaseLanguage());
	}

	@PostMapping("/count")
	public JsonWFProcess reportCounting(@RequestBody @NonNull final JsonCountRequest request)
	{
		assertApplicationAccess();

		final Inventory inventory = jobService.reportCounting(request, Env.getLoggedUserId());
		return toJsonWFProcess(inventory);
	}

	private JsonWFProcess toJsonWFProcess(final Inventory inventory)
	{
		return workflowRestController.toJson(InventoryWFProcessMapper.toWFProcess(inventory));
	}
}
