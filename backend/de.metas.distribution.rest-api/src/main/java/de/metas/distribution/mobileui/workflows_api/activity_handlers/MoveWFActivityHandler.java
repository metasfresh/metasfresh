package de.metas.distribution.mobileui.workflows_api.activity_handlers;

import de.metas.distribution.mobileui.DistributionMobileApplication;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.service.DistributionRestService;
import de.metas.distribution.mobileui.rest_api.json.JsonDistributionJob;
import de.metas.distribution.mobileui.rest_api.json.JsonRejectReasonsList;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.api.Params;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoveWFActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("distribution.move");
	public static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("distribution/move");

	@NonNull private final DistributionRestService distributionRestService;

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final MobileUIDistributionConfig config = distributionRestService.getConfig();
		final DistributionJob job = DistributionMobileApplication.getDistributionJob(wfProcess);

		final JsonDistributionJob json = JsonDistributionJob.builderFrom(job, jsonOpts)
				.qtyRejectedReasons(JsonRejectReasonsList.of(distributionRestService.getQtyRejectedReasons(), jsonOpts))
				.requireScanningProductCode(config.isRequireScanningProductCode())
				.completeJobAutomatically(config.isCompleteJobAutomatically())
				.navigateToJobsListAfterPickFromComplete(config.isNavigateToJobsListAfterPickFromComplete())
				.build();

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						.valueObj("job", json)
						.build())
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		final DistributionJob job = DistributionMobileApplication.getDistributionJob(wfProcess);
		return job.getStatus();
	}
}
