package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class M_Delivery_Planning_GenerateReceipt extends JavaProcess implements IProcessPrecondition
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

	@Param(parameterName = "ReceiptDate", mandatory = true)
	private Instant p_ReceiptDate;

	private static final String PARAM_Qty = "Qty";
	@Param(parameterName = PARAM_Qty, mandatory = true)
	private BigDecimal p_QtyBD;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());
		final Optional<DeliveryPlanningReceiptInfo> optionalDeliveryPlanningReceipt = deliveryPlanningService.getReceiptInfoIfIncomingType(deliveryPlanningId);
		if (!optionalDeliveryPlanningReceipt.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an incoming delivery planning");
		}

		final DeliveryPlanningReceiptInfo deliveryPlanningReceiptInfo = optionalDeliveryPlanningReceipt.get();
		if (deliveryPlanningReceiptInfo.isReceived())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already received");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(getRecord_ID());
		final DeliveryPlanningReceiptInfo receiptInfo = deliveryPlanningService.getReceiptInfo(deliveryPlanningId);
		if (receiptInfo.isReceived())
		{
			throw new AdempiereException("Already received");
		}

		createReceipt(receiptInfo.getReceiptScheduleId(), deliveryPlanningId);

		return MSG_OK;
	}

	private void createReceipt(
			@NonNull final ReceiptScheduleId receiptScheduleId,
			@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(receiptScheduleId);
		final Quantity qty = Quantitys.create(p_QtyBD, extractUomId(receiptSchedule));
		if (!qty.isPositive())
		{
			throw new FillMandatoryException(PARAM_Qty);
		}

		final I_M_HU vhu = huReceiptScheduleBL.createPlanningVHU(receiptSchedule, qty);
		if (vhu == null)
		{
			throw new AdempiereException("Failed receiving"); // shall not happen
		}

		final InOutGenerateResult result = huReceiptScheduleBL.processReceiptSchedules(
				CreateReceiptsParameters.builder()
						.commitEachReceiptIndividually(false)
						.movementDateRule(ReceiptMovementDateRule.fixedDate(p_ReceiptDate))
						.ctx(getCtx())
						.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
						.printReceiptLabels(true)
						.receiptSchedule(receiptSchedule)
						.selectedHuId(HuId.ofRepoId(vhu.getM_HU_ID()))
						.deliveryPlanningId(deliveryPlanningId)
						.build());

		result.getSingleInOutId();
	}

	private static UomId extractUomId(@NonNull I_M_ReceiptSchedule rs)
	{
		return UomId.ofRepoId(rs.getC_UOM_ID());
	}
}
