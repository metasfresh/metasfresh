package de.metas.distribution.mobileui.workflows_api.activity_handlers;

import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.DistributionMobileApplication;
import de.metas.distribution.mobileui.job.service.DistributionRestService;
import de.metas.i18n.TranslatableStrings;
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
public class CompleteDistributionWFActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("distribution.complete");

	@NonNull private final DistributionRestService distributionRestService;

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
						.question(TranslatableStrings.adMessage(ARE_YOU_SURE).translate(jsonOpts.getAdLanguage()))
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
