package de.metas.picking.workflow.handlers.activity_handlers;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.i18n.AdMessageKey;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
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

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;

@Component
public class RequestReviewWFActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("picking.requestReview");

	public static final AdMessageKey MSG_Caption = AdMessageKey.of("de.metas.picking.workflow.handlers.activity_handlers.RequestReviewWFActivityHandler.caption");

	private final PickingJobRestService pickingJobRestService;

	public RequestReviewWFActivityHandler(
			@NonNull final PickingJobRestService pickingJobRestService)
	{
		this.pickingJobRestService = pickingJobRestService;
	}

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(
			final @NonNull WFProcess wfProcess,
			final @NonNull WFActivity wfActivity,
			final @NonNull JsonOpts jsonOpts)
	{
		return UserConfirmationSupportUtil.createUIComponent(
				UserConfirmationSupportUtil.UIComponentProps.builder()
						.question("Are you sure?")
						.confirmed(wfActivity.getStatus().isCompleted())
						.build());
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity completePickingWFActivity)
	{
		final PickingJob pickingJob = getPickingJob(wfProcess);
		return computeActivityState(pickingJob);
	}

	public static WFActivityStatus computeActivityState(final PickingJob pickingJob)
	{
		return pickingJob.isApproved() ? WFActivityStatus.COMPLETED : WFActivityStatus.NOT_STARTED;
	}

	@Override
	public WFProcess userConfirmed(final UserConfirmationRequest request)
	{
		request.assertActivityType(HANDLED_ACTIVITY_TYPE);
		return PickingMobileApplication.mapPickingJob(request.getWfProcess(), pickingJobRestService::requestReview);
	}
}
