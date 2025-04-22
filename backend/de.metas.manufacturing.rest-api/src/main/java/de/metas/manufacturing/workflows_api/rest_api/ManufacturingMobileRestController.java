package de.metas.manufacturing.workflows_api.rest_api;

import de.metas.Profiles;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateRequest;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateResponse;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEventResult;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.util.web.MetasfreshRestAPIConstants;
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

	private void assertApplicationAccess()
	{
		mobileApplicationService.assertAccess(manufacturingMobileApplication.getApplicationId(), Env.getUserRolePermissions());
	}

	@PostMapping("/event")
	public JsonManufacturingOrderEventResult postEvents(@RequestBody @NonNull final JsonManufacturingOrderEvent event)
	{
		assertApplicationAccess();
		return manufacturingMobileApplication.processEvent(event, Env.getLoggedUserId());
	}

	@PostMapping("/generateHUQRCodes")
	public JsonFinishGoodsReceiveQRCodesGenerateResponse generateHUQRCodes(@RequestBody @NonNull JsonFinishGoodsReceiveQRCodesGenerateRequest request)
	{
		assertApplicationAccess();
		return manufacturingMobileApplication.generateFinishGoodsReceiveQRCodes(request);
	}
}
