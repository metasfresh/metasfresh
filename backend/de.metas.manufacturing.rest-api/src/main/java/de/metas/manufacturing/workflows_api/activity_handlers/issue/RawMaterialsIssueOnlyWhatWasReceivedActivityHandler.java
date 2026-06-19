package de.metas.manufacturing.workflows_api.activity_handlers.issue;

import de.metas.manufacturing.job.model.IssueOnlyWhatWasReceivedConfig;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.ManufacturingRestService;
import de.metas.material.planning.pporder.RawMaterialsIssueStrategy;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RawMaterialsIssueOnlyWhatWasReceivedActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.rawMaterialsIssueOnlyWhatWasReceived");

	private final ManufacturingJobService jobService;

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
				UserConfirmationSupportUtil.UIComponentProps.builderFrom(wfActivity).build()
		);
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return wfActivity.getStatus();
	}

	@Override
	public WFProcess userConfirmed(final UserConfirmationRequest request)
	{
		ManufacturingJob job = issueForWhatWasReceived(request);
		job = jobService.withActivityCompleted(job, extractManufacturingJobActivityId(request));

		return ManufacturingRestService.toWFProcess(job);
	}

	@NonNull
	private ManufacturingJob issueForWhatWasReceived(@NonNull final UserConfirmationRequest request)
	{
		return jobService.autoIssueWhatWasReceived(
				extractManufacturingJob(request),
				extractIssueStrategy(request)
		);
	}

	@NonNull
	private static RawMaterialsIssueStrategy extractIssueStrategy(@NonNull final UserConfirmationRequest request)
	{
		final ManufacturingJobActivity activity = extractManufacturingJobActivity(request);
		final IssueOnlyWhatWasReceivedConfig config = activity.getIssueOnlyWhatWasReceivedConfig();
		return config != null ? config.getIssueStrategy() : RawMaterialsIssueStrategy.DEFAULT;
	}

	@NonNull
	private static ManufacturingJobActivity extractManufacturingJobActivity(final @NotNull UserConfirmationRequest request)
	{
		final ManufacturingJob job = extractManufacturingJob(request);
		final ManufacturingJobActivityId manufacturingJobActivityId = extractManufacturingJobActivityId(request);
		return job.getActivityById(manufacturingJobActivityId);
	}

	@NonNull
	private static ManufacturingJob extractManufacturingJob(final @NotNull UserConfirmationRequest request)
	{
		return ManufacturingMobileApplication.getManufacturingJob(request.getWfProcess());
	}

	@NonNull
	private static ManufacturingJobActivityId extractManufacturingJobActivityId(final @NotNull UserConfirmationRequest request)
	{
		return request.getWfActivity().getId().getAsId(ManufacturingJobActivityId.class);
	}
}
