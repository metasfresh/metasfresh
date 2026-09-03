package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryInstructionService;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.inout.InOutId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOut.class)
@Component
@RequiredArgsConstructor
public class M_InOut
{
	@NonNull private final DeliveryPlanningService deliveryPlanningService;
	@NonNull private final DeliveryPlanningRepository deliveryPlanningRepository;
	@NonNull private final DeliveryInstructionService deliveryInstructionService;

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(final I_M_InOut inout)
	{
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoIdOrNull(inout.getM_Delivery_Planning_ID());
		if (deliveryPlanningId != null && inout.getReversal_ID() <= 0)
		{
			final InOutId inoutId = InOutId.ofRepoId(inout.getM_InOut_ID());
			if (inout.isSOTrx())
			{
				deliveryPlanningService.updateShipmentInfoById(deliveryPlanningId, shipmentInfo -> shipmentInfo.setShipmentId(inoutId));
				// Task Q11: writes the booked quantity onto the end(s) this shipment occupies, and marks the
				// planning Processed - alongside the recompute below, not instead of it.
				deliveryPlanningRepository.recordActualQtyOnComplete(deliveryPlanningId, false, inout);
			}
			else
			{
				deliveryPlanningService.updateReceiptInfoById(deliveryPlanningId, receiptInfo -> receiptInfo.setReceiptId(inoutId));
				// Task Q11: writes the booked quantity onto the end this receipt occupies, and marks the
				// planning Processed - alongside the recompute below, not instead of it.
				deliveryPlanningRepository.recordActualQtyOnComplete(deliveryPlanningId, true, inout);
			}

			// DeliveredState recompute wiring (Task Q9): the planning's IsDelivered just changed (M_InOut_ID
			// was set above), so every delivery instruction it is actively allocated to must be recomputed.
			deliveryInstructionService.recomputeDeliveredStateForAllocatedInstructions(deliveryPlanningId);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void afterReverseCorrect(final I_M_InOut inout)
	{
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoIdOrNull(inout.getM_Delivery_Planning_ID());
		if (deliveryPlanningId != null)
		{
			final InOutId inoutId = InOutId.ofRepoId(inout.getM_InOut_ID());
			if (inout.isSOTrx())
			{
				deliveryPlanningService.updateShipmentInfoById(
						deliveryPlanningId,
						shipmentInfo -> {
							if (InOutId.equals(shipmentInfo.getShipmentId(), inoutId))
							{
								shipmentInfo.setShipmentId(null);
							}
						});
				// Task Q11: the undo of the shipment's completion write-back - clears every end it wrote, and
				// clears Processed unless the planning is closed (Task Q10's invariant, symmetric with complete).
				deliveryPlanningRepository.clearActualQtyOnReverse(deliveryPlanningId, false);
			}
			else
			{
				deliveryPlanningService.updateReceiptInfoById(
						deliveryPlanningId,
						receiptInfo -> {
							if (InOutId.equals(receiptInfo.getReceiptId(), inoutId))
							{
								receiptInfo.setReceiptId(null);
							}
						});
				// Task Q11: the undo of the receipt's completion write-back - see above.
				deliveryPlanningRepository.clearActualQtyOnReverse(deliveryPlanningId, true);
			}

			// DeliveredState recompute wiring (Task Q9): the reversal case a stored implementation would get
			// wrong (spec 5.7) if this call were missing - the planning's IsDelivered just went back to false,
			// so an instruction previously FullyDelivered must fall back to PartlyDelivered (or NotDelivered).
			deliveryInstructionService.recomputeDeliveredStateForAllocatedInstructions(deliveryPlanningId);
		}
	}

}
