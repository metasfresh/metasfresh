package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryPlanningId;
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

	public M_InOut(@NonNull final DeliveryPlanningService deliveryPlanningService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
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
		}
	}

}
