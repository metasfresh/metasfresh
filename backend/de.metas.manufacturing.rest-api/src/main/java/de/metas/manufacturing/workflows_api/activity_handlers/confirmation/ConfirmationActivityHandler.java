package de.metas.manufacturing.workflows_api.activity_handlers.confirmation;

import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
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

	private final ManufacturingJobService manufacturingJobService;

	public ConfirmationActivityHandler(final ManufacturingJobService manufacturingJobService) {this.manufacturingJobService = manufacturingJobService;}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		return UserConfirmationSupportUtil.createUIComponent(wfActivity);
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

		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);
		job.assertUserReporting();

		final ManufacturingJob changedJob = manufacturingJobService.withActivityCompleted(job, jobActivityId);
		return ManufacturingRestService.toWFProcess(changedJob);
	}
}
