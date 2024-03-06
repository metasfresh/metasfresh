package de.metas.manufacturing.workflows_api.rest_api;

import de.metas.Profiles;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateRequest;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEventResult;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
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
public class ManufacturingMobileRestController
{
	private final ManufacturingMobileApplication manufacturingMobileApplication;

	public ManufacturingMobileRestController(
			@NonNull final ManufacturingMobileApplication manufacturingMobileApplication)
	{
		this.manufacturingMobileApplication = manufacturingMobileApplication;
	}

	@PostMapping("/event")
	public JsonManufacturingOrderEventResult postEvents(@RequestBody @NonNull final JsonManufacturingOrderEvent event)
	{
		return manufacturingMobileApplication.processEvent(event, Env.getLoggedUserId());
	}

	@PostMapping("/generateHUQRCodes")
	public void generateHUQRCodes(@RequestBody @NonNull JsonFinishGoodsReceiveQRCodesGenerateRequest request)
	{
		manufacturingMobileApplication.generateFinishGoodsReceiveQRCodes(request);
	}
}
