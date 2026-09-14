package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningShipmentInfo;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.order.OrderAndLineId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class M_Delivery_Planning_GenerateReceipt extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final DeliveryPlanningGenerateProcessesHelper helper = DeliveryPlanningGenerateProcessesHelper.newInstance();

	private static final String PARAM_ReceiptDate = "ReceiptDate";
	@Param(parameterName = PARAM_ReceiptDate, mandatory = true)
	private Instant p_ReceiptDate;

	private static final String PARAM_Qty = "Qty";
	@Param(parameterName = PARAM_Qty, mandatory = true)
	private BigDecimal p_QtyBD;

	public static final String PARAM_IsB2B = "IsB2B";
	// @Param(parameterName = PARAM_IsB2B)
	// private boolean p_IsB2B;

	public static final String PARAM_IsGenerateB2BShipment = "IsGenerateB2BShipment";
	@Param(parameterName = PARAM_IsGenerateB2BShipment)
	private boolean p_IsGenerateB2BShipment;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());

		return helper.checkEligibleToCreateReceipt(deliveryPlanningId);
	}

	@NonNull
	private DeliveryPlanningId getDeliveryPlanningId() {return DeliveryPlanningId.ofRepoId(getRecord_ID());}

	private DeliveryPlanningReceiptInfo getReceiptInfo() {return helper.getReceiptInfo(getDeliveryPlanningId());}

	private Optional<DeliveryPlanningShipmentInfo> getB2BShipmentInfo() {return helper.getB2BShipmentInfo(getReceiptInfo());}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final String externalParameterName = parameter.getColumnName();

		if (PARAM_IsB2B.equals(externalParameterName))
		{
			return getB2BShipmentInfo().isPresent();
		}
		if (PARAM_IsGenerateB2BShipment.equals(externalParameterName))
		{
			return getB2BShipmentInfo().isPresent();
		}
		else
		{
			return null;
		}
	}

	@Override
	protected String doIt()
	{
		final Instant receiptDate = FillMandatoryException.assumeNotNull(p_ReceiptDate, PARAM_ReceiptDate);
		final BigDecimal qty = DeliveryPlanningGenerateProcessesHelper.assumePositive(p_QtyBD, PARAM_Qty);

		final DeliveryPlanningGenerateReceiptResult receiptResult = helper.generateReceipt(
				DeliveryPlanningGenerateReceiptRequest.builder()
						.deliveryPlanningId(getDeliveryPlanningId())
						.receiptDate(receiptDate)
						.qtyToReceiveBD(qty)
						.build());

		if (p_IsGenerateB2BShipment)
		{
			final DeliveryPlanningShipmentInfo b2bShipmentInfo = getB2BShipmentInfo()
					.orElseThrow(() -> new AdempiereException("No B2B shipment found")); // shall not happen

			final OrderAndLineId salesOrderAndLineId = Check.assumeNotNull(b2bShipmentInfo.getSalesOrderAndLineId(), "B2B Sales Order Line is set");

			final HUReservationService huReservationService = SpringContextHolder.instance.getBean(HUReservationService.class);
			huReservationService.makeReservation(ReserveHUsRequest.builder()
					.qtyToReserve(receiptResult.getQty())
					.documentRef(HUReservationDocRef.ofSalesOrderLineId(salesOrderAndLineId.getOrderLineId()))
					.productId(receiptResult.getProductId())
					.customerId(b2bShipmentInfo.getCustomerId())
					.huId(receiptResult.getReceivedVHUId())
					.build());

			helper.generateShipment(DeliveryPlanningGenerateShipmentRequest.builder()
					.deliveryPlanningId(b2bShipmentInfo.getDeliveryPlanningId())
					.deliveryDate(TimeUtil.asLocalDate(receiptDate))
					.qtyToShipBD(qty)
					.b2bReceiptId(receiptResult.getReceiptId())
					.build());
		}

		return MSG_OK;
	}
}
