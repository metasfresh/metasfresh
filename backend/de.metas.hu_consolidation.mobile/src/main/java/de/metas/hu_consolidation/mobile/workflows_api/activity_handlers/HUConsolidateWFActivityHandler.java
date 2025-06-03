package de.metas.hu_consolidation.mobile.workflows_api.activity_handlers;

import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobService;
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

import static de.metas.hu_consolidation.mobile.HUConsolidationApplication.getHUConsolidationJob;

@Component
@RequiredArgsConstructor
public class HUConsolidateWFActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("huConsolidation.consolidate");
	public static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("huConsolidation/consolidate");

	private final HUConsolidationJobService jobService;

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final HUConsolidationJob job = getHUConsolidationJob(wfProcess);

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						// TODO
						// .valueObj("lines", lines)
						// .valueObj("qtyRejectedReasons", qtyRejectedReasons)
						.build())
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity completeDistributionWFActivity)
	{
		final HUConsolidationJob job = getHUConsolidationJob(wfProcess);
		return computeActivityState(job);
	}

	public static WFActivityStatus computeActivityState(final HUConsolidationJob job)
	{
		// TODO
		return WFActivityStatus.NOT_STARTED;
	}
}
