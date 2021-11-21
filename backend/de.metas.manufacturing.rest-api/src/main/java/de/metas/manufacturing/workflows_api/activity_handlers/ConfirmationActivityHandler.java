package de.metas.manufacturing.workflows_api.activity_handlers;

import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.workflows_api.ManufacturingRestService;
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
public class ConfirmationActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.confirmation");

	private final ManufacturingRestService manufacturingRestService;

	public ConfirmationActivityHandler(final ManufacturingRestService manufacturingRestService) {this.manufacturingRestService = manufacturingRestService;}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		return UserConfirmationSupportUtil.createUIComponent(
				UserConfirmationSupportUtil.UIComponentProps.builder()
						.confirmed(wfActivity.getStatus().isCompleted())
						.build());
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return wfActivity.getStatus();
	}

	@Override
	public WFProcess userConfirmed(@NonNull final UserConfirmationRequest request)
	{
		final WFProcess wfProcess = request.getWfProcess();
		request.getWfActivity().getWfActivityType().assertExpected(HANDLED_ACTIVITY_TYPE);

		final ManufacturingJobActivityId jobActivityId = request.getWfActivity().getId().getAsId(ManufacturingJobActivityId.class);

		final ManufacturingJob job = wfProcess.getDocumentAs(ManufacturingJob.class);
		job.assertUserReporting();

		final ManufacturingJob changedJob = manufacturingRestService.withActivityCompleted(job, jobActivityId);
		return ManufacturingRestService.toWFProcess(changedJob);
	}
}
