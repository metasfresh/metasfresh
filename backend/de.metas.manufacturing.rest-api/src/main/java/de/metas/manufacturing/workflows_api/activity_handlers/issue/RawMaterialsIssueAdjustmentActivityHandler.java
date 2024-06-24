package de.metas.manufacturing.workflows_api.activity_handlers.issue;

import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;
import org.springframework.stereotype.Component;

@Component
public class RawMaterialsIssueAdjustmentActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.rawMaterialsIssueAdjust");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/rawMaterialsIssueAdjust");

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity) {return wfActivity.getStatus();}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final WFActivity issueActivity = wfProcess.getActivities()
				.stream()
				.filter(activity -> WFActivityType.equals(activity.getWfActivityType(), RawMaterialsIssueActivityHandler.HANDLED_ACTIVITY_TYPE))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No " + RawMaterialsIssueActivityHandler.HANDLED_ACTIVITY_TYPE + " activity found in " + wfProcess));

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						.value("rawMaterialsIssueActivityId", issueActivity.getId().getAsString())
						.build())
				.build();
	}
}
