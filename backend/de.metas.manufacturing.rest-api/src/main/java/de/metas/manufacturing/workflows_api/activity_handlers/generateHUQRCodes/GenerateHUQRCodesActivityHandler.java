package de.metas.manufacturing.workflows_api.activity_handlers.generateHUQRCodes;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.manufacturing.job.model.FinishedGoodsReceive;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenerateHUQRCodesActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.generateHUQRCodes");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/generateHUQRCodes");

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final ManufacturingJob job = wfProcess.getDocumentAs(ManufacturingJob.class);
		final BPartnerId customerId = job.getCustomerId();

		final ImmutableList<JsonTUPackingInstructions> tuPackingInstructionsList = job.getActivities()
				.stream()
				.filter(activity -> activity.getType() == PPRoutingActivityType.MaterialReceipt)
				.map(ManufacturingJobActivity::getFinishedGoodsReceiveAssumingNotNull)
				.flatMap(FinishedGoodsReceive::streamLines)
				.flatMap(finishedGoodsReceiveLine -> getTUPackingInstructionsList(finishedGoodsReceiveLine, customerId).stream())
				.distinct()
				.collect(ImmutableList.toImmutableList());

		return UIComponent.builder()
				.type(COMPONENT_TYPE)
				.properties(Params.builder()
						.valueObj("options", tuPackingInstructionsList)
						.build())
				.build();
	}

	public List<JsonTUPackingInstructions> getTUPackingInstructionsList(
			final @NonNull FinishedGoodsReceiveLine finishedGoodsReceiveLine,
			final @Nullable BPartnerId customerId)
	{
		final ArrayList<JsonTUPackingInstructions> result = new ArrayList<>();

		for (final I_M_HU_PI_Item_Product tuPIItemProduct : getTUPIItemProducts(finishedGoodsReceiveLine, customerId))
		{
			final HuPackingInstructionsId tuPackingInstructionsId = getTUPackingInstructionsId(tuPIItemProduct);
			final QtyTU qtyTUs = computeQtyTUsRequired(finishedGoodsReceiveLine, tuPIItemProduct);

			result.add(JsonTUPackingInstructions.builder()
					.caption(tuPIItemProduct.getName())
					.tuPackingInstructionsId(tuPackingInstructionsId)
					.finishedGoodsReceiveLineId(finishedGoodsReceiveLine.getId())
					.qtyTUs(qtyTUs)
					.build());
		}

		return result;
	}

	private List<I_M_HU_PI_Item_Product> getTUPIItemProducts(final @NonNull FinishedGoodsReceiveLine finishedGoodsReceiveLine, final @Nullable BPartnerId customerId)
	{
		return huPIItemProductDAO.retrieveTUs(
				Env.getCtx(),
				finishedGoodsReceiveLine.getProductId(),
				customerId,
				false);
	}

	private HuPackingInstructionsId getTUPackingInstructionsId(final I_M_HU_PI_Item_Product tuPIItemProduct)
	{
		final HuPackingInstructionsItemId tuPackingInstructionsItemId = HuPackingInstructionsItemId.ofRepoId(tuPIItemProduct.getM_HU_PI_Item_ID());
		return handlingUnitsBL.getPackingInstructionsId(tuPackingInstructionsItemId);
	}

	private QtyTU computeQtyTUsRequired(final @NonNull FinishedGoodsReceiveLine finishedGoodsReceiveLine, final I_M_HU_PI_Item_Product tuPIItemProduct)
	{
		final UomId uomId = UomId.ofRepoId(tuPIItemProduct.getC_UOM_ID());
		final Quantity qtyCusPerTU = Quantitys.create(tuPIItemProduct.getQty(), uomId);
		final Quantity qtyCUs = uomConversionBL.convertQuantityTo(finishedGoodsReceiveLine.getQtyToReceive(), finishedGoodsReceiveLine.getProductId(), uomId);
		return QtyTU.ofBigDecimal(qtyCUs.toBigDecimal().divide(qtyCusPerTU.toBigDecimal(), 0, RoundingMode.UP));
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return wfActivity.getStatus();
	}

}
