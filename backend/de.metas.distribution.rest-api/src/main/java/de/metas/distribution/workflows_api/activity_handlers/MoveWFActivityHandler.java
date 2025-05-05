package de.metas.distribution.workflows_api.activity_handlers;

import de.metas.distribution.workflows_api.DistributionJob;
import de.metas.distribution.workflows_api.DistributionMobileApplication;
import de.metas.distribution.workflows_api.DistributionRestService;
import de.metas.distribution.workflows_api.json.JsonDistributionJob;
import de.metas.distribution.workflows_api.json.JsonDistributionJobLine;
import de.metas.distribution.workflows_api.json.JsonRejectReasonsList;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import org.adempiere.util.api.Params;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoveWFActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("distribution.move");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("distribution/move");

	private final DistributionRestService distributionRestService;

	public MoveWFActivityHandler(
			@NonNull final DistributionRestService distributionRestService)
	{
		this.distributionRestService = distributionRestService;
	}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final DistributionJob job = DistributionMobileApplication.getDistributionJob(wfProcess);

		final JsonRejectReasonsList qtyRejectedReasons = JsonRejectReasonsList.of(distributionRestService.getQtyRejectedReasons(), jsonOpts);

		final List<JsonDistributionJobLine> lines = JsonDistributionJob.of(job, jsonOpts).getLines();

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						.valueObj("lines", lines)
						.valueObj("qtyRejectedReasons", qtyRejectedReasons)
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
