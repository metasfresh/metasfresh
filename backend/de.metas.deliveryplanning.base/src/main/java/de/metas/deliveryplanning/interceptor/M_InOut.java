package de.metas.deliveryplanning.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.deliveryplanning.DeliveryInstructionService;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Interceptor(I_M_InOut.class)
@Component
@RequiredArgsConstructor
public class M_InOut
{
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	@NonNull private final DeliveryPlanningService deliveryPlanningService;
	@NonNull private final DeliveryPlanningRepository deliveryPlanningRepository;
	@NonNull private final DeliveryInstructionService deliveryInstructionService;

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(final I_M_InOut inout)
	{
		if (inout.getReversal_ID() > 0)
		{
			return;
		}

		final InOutId inoutId = InOutId.ofRepoId(inout.getM_InOut_ID());
		for (final DeliveryPlanningId deliveryPlanningId : extractDeliveryPlanningIds(inout))
		{
			if (inout.isSOTrx())
			{
				deliveryPlanningService.updateShipmentInfoById(deliveryPlanningId, shipmentInfo -> shipmentInfo.setShipmentId(inoutId));
				// Writes the booked quantity onto the end(s) this shipment occupies, and marks the
				// planning Processed - alongside the recompute below, not instead of it.
				deliveryPlanningService.recordActualQtyOnComplete(deliveryPlanningId, false, inout);
			}
			else
			{
				deliveryPlanningService.updateReceiptInfoById(deliveryPlanningId, receiptInfo -> receiptInfo.setReceiptId(inoutId));
				// Writes the booked quantity onto the end this receipt occupies, and marks the
				// planning Processed - alongside the recompute below, not instead of it.
				deliveryPlanningService.recordActualQtyOnComplete(deliveryPlanningId, true, inout);
			}

			// DeliveredState recompute wiring: the planning's IsDelivered just changed (M_InOut_ID
			// was set above), so every delivery instruction it is actively allocated to must be recomputed.
			deliveryInstructionService.recomputeDeliveredStateForAllocatedInstructions(deliveryPlanningId);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void afterReverseCorrect(final I_M_InOut inout)
	{
		final InOutId inoutId = InOutId.ofRepoId(inout.getM_InOut_ID());
		for (final DeliveryPlanningId deliveryPlanningId : extractDeliveryPlanningIds(inout))
		{
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
				// The undo of the shipment's completion write-back - clears every end it wrote, and
				// clears Processed unless the planning is closed (the Processed invariant, symmetric with complete).
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
				// The undo of the receipt's completion write-back - see above.
				deliveryPlanningRepository.clearActualQtyOnReverse(deliveryPlanningId, true);
			}

			// the planning's IsDelivered just went back to false, so an instruction previously FullyDelivered must fall
			// back to PartlyDelivered (or NotDelivered) - the reversal case a stored implementation gets wrong
			deliveryInstructionService.recomputeDeliveredStateForAllocatedInstructions(deliveryPlanningId);
		}
	}

	/**
	 * Read off the LINES, because that is where the grain is: a line corresponds to one schedule and therefore to
	 * one planning, while this document may aggregate several. A reversal's lines are copies of the original's and
	 * carry the same ids, so the same read serves both timings.
	 */
	private ImmutableSet<DeliveryPlanningId> extractDeliveryPlanningIds(@NonNull final I_M_InOut inout)
	{
		return inOutDAO.retrieveLines(inout).stream()
				.map(I_M_InOutLine::getM_Delivery_Planning_ID)
				.map(DeliveryPlanningId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
