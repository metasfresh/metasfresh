package de.metas.deliveryplanning.interceptor;

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

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void afterReverseCorrect(final I_M_InOut inout)
	{
		final InOutId inoutId = InOutId.ofRepoId(inout.getM_InOut_ID());
		if (inout.isSOTrx())
		{
			// TODO
		}
		else
		{
			deliveryPlanningService.updateReceiptInfoByInOutId(
					inoutId,
					receiptInfo -> receiptInfo.setReceiptId(null));
		}
	}
}
