package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.inout.ShipmentScheduleId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.NestedParams;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class M_Delivery_Planning_GenerateShipment extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
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

	@NestedParams
	private final ForexContractParameters p_FECParams = ForexContractParameters.newInstance();

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

	private ForexContracts getContracts() {return helper.getShipmentContracts(getDeliveryPlanningId());}

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
			final BigDecimal qtyOnHand = helper.getQtyOnHandByShipmentScheduleId(shipmentScheduleId);
			return qtyOnHand == null ? BigDecimal.ZERO : qtyOnHand;
		}
		else
		{
			return p_FECParams.getParameterDefaultValue(parameter.getColumnName(), getContracts());
		}

	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		p_FECParams.updateOnParameterChanged(parameterName, getContracts());
	}

	@ProcessParamLookupValuesProvider(parameterName = ForexContractParameters.PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getAvailableForexContracts() {return helper.toLookupValuesList(getContracts());}

	@NonNull
	private DeliveryPlanningId getDeliveryPlanningId() {return DeliveryPlanningId.ofRepoId(getRecord_ID());}

	@Override
	protected String doIt()
	{
		helper.generateShipment(
				DeliveryPlanningGenerateShipmentRequest.builder()
						.adPInstanceId(getPinstanceId())
						.deliveryPlanningId(getDeliveryPlanningId())
						.deliveryDate(FillMandatoryException.assertNotNull(p_DeliveryDate, PARAM_DeliveryDate))
						.qtyToShipBD(FillMandatoryException.assertPositive(p_QtyBD, PARAM_Qty))
						.forexContractRef(p_FECParams.getForexContractRef())
						.build());

		return MSG_OK;
	}
}
