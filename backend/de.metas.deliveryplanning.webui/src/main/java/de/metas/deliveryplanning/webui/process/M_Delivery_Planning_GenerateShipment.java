package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_M_Delivery_Planning;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class M_Delivery_Planning_GenerateShipment extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	// package-visible, non-final: overwritten with a mock by same-package unit tests (e.g.
	// M_Delivery_Planning_GenerateShipmentWriteBackTest) that cannot otherwise stub the heavy
	// production shipment-generation chain (async batch + ShipmentService).
	DeliveryPlanningGenerateProcessesHelper helper = DeliveryPlanningGenerateProcessesHelper.newInstance();

	private static final String PARAM_DeliveryDate = "DeliveryDate";
	@Param(parameterName = PARAM_DeliveryDate, mandatory = true)
	private LocalDate p_DeliveryDate;

	private static final String PARAM_Qty = "Qty";
	@Param(parameterName = PARAM_Qty, mandatory = true)
	private BigDecimal p_QtyBD;

	// Optional, read-only display only (see AD_Process_Para.ReadOnlyLogic): doIt() never reads either
	// this field or p_QtyAvailable, they only inform the user of the order line's open quantity and the
	// shipment schedule's qty-on-hand before they type the Qty override above.
	private static final String PARAM_QtyToDeliver = "QtyTotalOpen";
	@Param(parameterName = PARAM_QtyToDeliver, mandatory = false)
	private BigDecimal p_QtyToDeliverBD;

	private static final String PARAM_QtyAvailable = "QtyAvailableParam";
	@Param(parameterName = PARAM_QtyAvailable, mandatory = false)
	private BigDecimal p_QtyAvailable;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());
		return helper.checkEligibleToCreateShipment(deliveryPlanningId);
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if(parameter.getColumnName().equals(PARAM_QtyToDeliver))
		{
			final ShipmentScheduleId shipmentScheduleId = helper.getShipmentInfo(getDeliveryPlanningId()).getShipmentScheduleId();
			return helper.getQtyToDeliverByShipmentScheduleId(shipmentScheduleId);
		}
		else if(parameter.getColumnName().equals(PARAM_QtyAvailable))
		{
			final ShipmentScheduleId shipmentScheduleId = helper.getShipmentInfo(getDeliveryPlanningId()).getShipmentScheduleId();
			return helper.getQtyOnHandByShipmentScheduleId(shipmentScheduleId).toBigDecimal();
		}
		else
		{
			return null;
		}

	}

	@NonNull
	private DeliveryPlanningId getDeliveryPlanningId() {return DeliveryPlanningId.ofRepoId(getRecord_ID());}

	@Override
	protected String doIt()
	{
		final BigDecimal qtyToShipBD = DeliveryPlanningGenerateProcessesHelper.assumePositive(p_QtyBD, PARAM_Qty);

		helper.generateShipment(
				DeliveryPlanningGenerateShipmentRequest.builder()
						.deliveryPlanningId(getDeliveryPlanningId())
						.deliveryDate(FillMandatoryException.assumeNotNull(p_DeliveryDate, PARAM_DeliveryDate))
						.qtyToShipBD(qtyToShipBD)
						.build());

		// Write the Qty override back onto the planning: a shipment reads/occupies the load end, so the
		// override - the requested qty, not necessarily what was actually shipped - becomes the planning's new
		// PlannedLoadedQuantity (spec direction rule, restated by Task Q12).
		final ProductId productId = ProductId.ofRepoId(getRecord(I_M_Delivery_Planning.class).getM_Product_ID());
		final Quantity qtyToShip = Quantitys.of(qtyToShipBD, productId);
		helper.writeBackPlannedLoadedQuantity(getDeliveryPlanningId(), qtyToShip);

		return MSG_OK;
	}
}
