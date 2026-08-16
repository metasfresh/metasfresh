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
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class M_Delivery_Planning_GenerateShipment extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final DeliveryPlanningGenerateProcessesHelper helper = DeliveryPlanningGenerateProcessesHelper.newInstance();

	private static final String PARAM_DeliveryDate = "DeliveryDate";
	@Param(parameterName = PARAM_DeliveryDate, mandatory = true)
	private LocalDate p_DeliveryDate;

	private static final String PARAM_Qty = "Qty";
	@Param(parameterName = PARAM_Qty, mandatory = true)
	private BigDecimal p_QtyBD;

	private static final String PARAM_QtyToDeliver = "QtyTotalOpen";
	@Param(parameterName = PARAM_QtyToDeliver, mandatory = true)
	private BigDecimal p_QtyToDeliverBD;

	private static final String PARAM_QtyAvailable = "QtyAvailableParam";
	@Param(parameterName = PARAM_QtyToDeliver, mandatory = true)
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
		helper.generateShipment(
				DeliveryPlanningGenerateShipmentRequest.builder()
						.deliveryPlanningId(getDeliveryPlanningId())
						.deliveryDate(FillMandatoryException.assumeNotNull(p_DeliveryDate, PARAM_DeliveryDate))
						.qtyToShipBD(DeliveryPlanningGenerateProcessesHelper.assumePositive(p_QtyBD, PARAM_Qty))
						.build());

		return MSG_OK;
	}
}
