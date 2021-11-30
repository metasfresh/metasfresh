package de.metas.mobileui.inventory_disposal;

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
import org.springframework.stereotype.Component;

@Component
public class CompleteHUDisposalWFActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("huDisposal.complete");

	private final HUDisposalJobService huDisposalJobService;

	public CompleteHUDisposalWFActivityHandler(final HUDisposalJobService huDisposalJobService) {this.huDisposalJobService = huDisposalJobService;}

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		return UserConfirmationSupportUtil.createUIComponent(
				UserConfirmationSupportUtil.UIComponentProps.builder()
						.question("Are you sure?")
						.confirmed(wfActivity.getStatus().isCompleted())
						.build());
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return WFActivityStatus.NOT_STARTED;
	}

	@Override
	public WFProcess userConfirmed(final UserConfirmationRequest request)
	{
		final WFProcess wfProcess = request.getWfProcess();
		request.getWfActivity().getWfActivityType().assertExpected(HANDLED_ACTIVITY_TYPE);

		return wfProcess.mapDocument(huDisposalJobService::completeDisposal);
	}
}
