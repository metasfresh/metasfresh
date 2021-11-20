package de.metas.manufacturing.workflows_api.activity_handlers;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.RawMaterialsIssue;
import de.metas.manufacturing.workflows_api.activity_handlers.json.JsonRawMaterialsIssueLine;
import de.metas.manufacturing.workflows_api.activity_handlers.json.JsonRejectReasonsList;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.util.api.Params;
import org.springframework.stereotype.Component;

@Component
public class RawMaterialsIssueActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.rawMaterialsIssue");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/rawMaterialsIssue");

	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final RawMaterialsIssue rawMaterialsIssue = getRawMaterialsIssue(wfProcess, wfActivity);

		final ImmutableList<JsonRawMaterialsIssueLine> lines = rawMaterialsIssue.getLines()
				.stream()
				.map(line -> JsonRawMaterialsIssueLine.of(line, jsonOpts))
				.collect(ImmutableList.toImmutableList());

		final JsonRejectReasonsList qtyRejectedReasons = JsonRejectReasonsList.of(
				adReferenceDAO.getRefListById(QtyRejectedReasonCode.REFERENCE_ID),
				jsonOpts);

		return UIComponent.builder()
				.type(COMPONENT_TYPE)
				.properties(Params.builder()
						.valueObj("lines", lines)
						.valueObj("qtyRejectedReasons", qtyRejectedReasons)
						.build())
				.build();
	}

	@NonNull
	private static RawMaterialsIssue getRawMaterialsIssue(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity)
	{
		final ManufacturingJob job = wfProcess.getDocumentAs(ManufacturingJob.class);
		final ManufacturingJobActivity jobActivity = job.getActivityById(wfActivity.getId());
		return jobActivity.getRawMaterialsIssueAssumingNotNull();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return getRawMaterialsIssue(wfProcess, wfActivity).getStatus();
	}
}
