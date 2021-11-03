package de.metas.manufacturing.workflows_api.activity_handlers;

import com.google.common.collect.ImmutableList;
import de.metas.manufacturing.job.ManufacturingJob;
import de.metas.manufacturing.job.ManufacturingJobActivity;
import de.metas.manufacturing.job.ManufacturingJobActivityId;
import de.metas.manufacturing.workflows_api.activity_handlers.json.JsonFinishedGoodsReceiveLine;
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

import java.util.Objects;

@Component
public class MaterialReceiptActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.materialReceipt");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/materialReceipt");

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final ManufacturingJobActivity jobActivity = wfProcess
				.getDocumentAs(ManufacturingJob.class)
				.getActivityById(wfActivity.getId().getAsId(ManufacturingJobActivityId.class));
		final ManufacturingJobActivity.FinishedGoodsReceive finishedGoodsReceive = Objects.requireNonNull(jobActivity.getFinishedGoodsReceive());

		final ImmutableList<JsonFinishedGoodsReceiveLine> lines = finishedGoodsReceive.getLines()
				.stream()
				.map(line -> JsonFinishedGoodsReceiveLine.of(line, jsonOpts))
				.collect(ImmutableList.toImmutableList());

		return UIComponent.builder()
				.type(COMPONENT_TYPE)
				.properties(Params.builder()
						.valueObj("lines", lines)
						.build())
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return WFActivityStatus.NOT_STARTED;
	}
}
