package de.metas.inventory.mobileui.rest_api;

import de.metas.handlingunits.inventory.Inventory;
import de.metas.i18n.IMsgBL;
import de.metas.inventory.mobileui.InventoryMobileApplication;
import de.metas.inventory.mobileui.job.service.InventoryJobService;
import de.metas.util.Services;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationRequest;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupport;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupportUtil;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static de.metas.workflow.rest_api.service.Constants.ARE_YOU_SURE;

@Component
@RequiredArgsConstructor
public class CompleteWFActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("inventory.complete");

	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final InventoryJobService jobService;

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		return UserConfirmationSupportUtil.createUIComponent(
				UserConfirmationSupportUtil.UIComponentProps.builderFrom(wfActivity)
						.question(msgBL.getMsg(jsonOpts.getAdLanguage(), ARE_YOU_SURE))
						.build());
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		// TODO
		return WFActivityStatus.NOT_STARTED;
	}

	public static WFActivityStatus computeActivityState(final Inventory inventory)
	{
		// TODO
		return WFActivityStatus.NOT_STARTED;
	}

	@Override
	public WFProcess userConfirmed(final UserConfirmationRequest request)
	{
		request.assertActivityType(HANDLED_ACTIVITY_TYPE);
		return InventoryMobileApplication.mapJob(request.getWfProcess(), jobService::complete);
	}
}
