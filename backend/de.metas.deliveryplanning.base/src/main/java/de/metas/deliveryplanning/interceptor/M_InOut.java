package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.inout.InOutId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	private final DeliveryPlanningService deliveryPlanningService;
	private final DeliveryPlanningRepository deliveryPlanningRepository;

	public M_InOut(
			@NonNull final DeliveryPlanningService deliveryPlanningService,
			@NonNull final DeliveryPlanningRepository deliveryPlanningRepository)
	{
		this.deliveryPlanningService = deliveryPlanningService;
		this.deliveryPlanningRepository = deliveryPlanningRepository;
	}

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
			}
			else
			{
				deliveryPlanningService.updateReceiptInfoById(deliveryPlanningId, receiptInfo -> receiptInfo.setReceiptId(inoutId));
			}

			// DeliveredState recompute wiring (Task Q9): the planning's IsDelivered just changed (M_InOut_ID
			// was set above), so every delivery instruction it is actively allocated to must be recomputed.
			// Task Q11 later adds its own actual-quantity write-back here too, alongside this call, not
			// instead of it - see the plan's "no fifth mutation path" guarantee.
			deliveryPlanningRepository.recomputeDeliveredStateForAllocatedInstructions(deliveryPlanningId);
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
			}

			// DeliveredState recompute wiring (Task Q9): the reversal case a stored implementation would get
			// wrong (spec 5.7) if this call were missing - the planning's IsDelivered just went back to false,
			// so an instruction previously FullyDelivered must fall back to PartlyDelivered (or NotDelivered).
			// Task Q11 adds its own write-back here too, alongside this call, not instead of it.
			deliveryPlanningRepository.recomputeDeliveredStateForAllocatedInstructions(deliveryPlanningId);
		}
	}

}
