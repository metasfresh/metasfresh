package de.metas.manufacturing.workflows_api.activity_handlers;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.manufacturing.job.model.FinishedGoodsReceive;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.workflows_api.activity_handlers.json.JsonFinishedGoodsReceiveLine;
import de.metas.manufacturing.workflows_api.activity_handlers.json.JsonReceiveToNewPackingMaterialTarget;
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
import org.adempiere.util.api.Params;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Objects;

@Component
public class MaterialReceiptActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.materialReceipt");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/materialReceipt");

	private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final ManufacturingJob job = wfProcess.getDocumentAs(ManufacturingJob.class);
		final ManufacturingJobActivity jobActivity = job.getActivityById(wfActivity.getId().getAsId(ManufacturingJobActivityId.class));
		final FinishedGoodsReceive finishedGoodsReceive = Objects.requireNonNull(jobActivity.getFinishedGoodsReceive());

		final ImmutableList<JsonFinishedGoodsReceiveLine> lines = finishedGoodsReceive.getLines()
				.stream()
				.map(line -> toJson(line, job.getCustomerId(), jsonOpts))
				.collect(ImmutableList.toImmutableList());

		return UIComponent.builder()
				.type(COMPONENT_TYPE)
				.properties(Params.builder()
						.valueObj("lines", lines)
						.build())
				.build();
	}

	private JsonFinishedGoodsReceiveLine toJson(
			@NonNull final FinishedGoodsReceiveLine line,
			@Nullable final BPartnerId customerId,
			@NonNull final JsonOpts jsonOpts)
	{
		final ImmutableList<JsonReceiveToNewPackingMaterialTarget> availablePackingMaterials = huPIItemProductDAO.retrieveTUs(Env.getCtx(), line.getProductId(), customerId, true)
				.stream()
				.map(this::toJson)
				.collect(ImmutableList.toImmutableList());
		return JsonFinishedGoodsReceiveLine.of(line, availablePackingMaterials, jsonOpts);
	}

	private JsonReceiveToNewPackingMaterialTarget toJson(final I_M_HU_PI_Item_Product record)
	{
		return JsonReceiveToNewPackingMaterialTarget.builder()
				.id(String.valueOf(record.getM_HU_PI_Item_Product_ID()))
				.caption(record.getName())
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return WFActivityStatus.NOT_STARTED;
	}
}
