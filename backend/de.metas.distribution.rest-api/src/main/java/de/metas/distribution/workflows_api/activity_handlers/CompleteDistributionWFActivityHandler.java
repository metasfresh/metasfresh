package de.metas.distribution.workflows_api.activity_handlers;

import de.metas.distribution.workflows_api.DistributionJob;
import de.metas.distribution.workflows_api.DistributionMobileApplication;
import de.metas.distribution.workflows_api.DistributionRestService;
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
public class CompleteDistributionWFActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("distribution.complete");

	private final DistributionRestService distributionRestService;

	public CompleteDistributionWFActivityHandler(final DistributionRestService distributionRestService)
	{
		this.distributionRestService = distributionRestService;
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
				UserConfirmationSupportUtil.UIComponentProps.builderFrom(wfActivity)
						.question("Are you sure?")
						.build());
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity completeDistributionWFActivity)
	{
		final DistributionJob job = DistributionMobileApplication.getDistributionJob(wfProcess);
		return computeActivityState(job);
	}

	public static WFActivityStatus computeActivityState(final DistributionJob job)
	{
		return job.isClosed() ? WFActivityStatus.COMPLETED : WFActivityStatus.NOT_STARTED;
	}

	@Override
	public WFProcess userConfirmed(final UserConfirmationRequest request)
	{
		request.assertActivityType(HANDLED_ACTIVITY_TYPE);
		return DistributionMobileApplication.mapDocument(request.getWfProcess(), distributionRestService::complete);
	}
}
