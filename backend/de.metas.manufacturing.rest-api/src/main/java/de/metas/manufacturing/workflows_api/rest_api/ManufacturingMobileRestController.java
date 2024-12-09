package de.metas.manufacturing.workflows_api.rest_api;

import de.metas.Profiles;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateRequest;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEventResult;
<<<<<<< HEAD
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
=======
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
public class ManufacturingMobileRestController
{
	private final ManufacturingMobileApplication manufacturingMobileApplication;

	public ManufacturingMobileRestController(
			@NonNull final ManufacturingMobileApplication manufacturingMobileApplication)
	{
		this.manufacturingMobileApplication = manufacturingMobileApplication;
=======
@RequiredArgsConstructor
public class ManufacturingMobileRestController
{
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private final ManufacturingMobileApplication manufacturingMobileApplication;

	private void assertApplicationAccess()
	{
		mobileApplicationService.assertAccess(manufacturingMobileApplication.getApplicationId(), Env.getUserRolePermissions());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@PostMapping("/event")
	public JsonManufacturingOrderEventResult postEvents(@RequestBody @NonNull final JsonManufacturingOrderEvent event)
	{
<<<<<<< HEAD
=======
		assertApplicationAccess();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return manufacturingMobileApplication.processEvent(event, Env.getLoggedUserId());
	}

	@PostMapping("/generateHUQRCodes")
	public void generateHUQRCodes(@RequestBody @NonNull JsonFinishGoodsReceiveQRCodesGenerateRequest request)
	{
<<<<<<< HEAD
=======
		assertApplicationAccess();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		manufacturingMobileApplication.generateFinishGoodsReceiveQRCodes(request);
	}
}
