package de.metas.order.costs.interceptor;

import de.metas.inout.InOutId;
import de.metas.order.costs.OrderCostService;
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
	private final OrderCostService orderCostService;

	public M_InOut(@NonNull final OrderCostService orderCostService)
	{
		this.orderCostService = orderCostService;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void onBeforeComplete(final I_M_InOut inout)
	{
		final InOutId inoutId = InOutId.ofRepoId(inout.getM_InOut_ID());
		final InOutId initialReversalId = InOutId.ofRepoIdOrNull(inout.getReversal_ID());
		if (initialReversalId == null)
		{
			orderCostService.createInOutCosts(inoutId);
		}
		else
		{
			orderCostService.reverseInOutCosts(inoutId, initialReversalId);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void onBeforeReactivate(final I_M_InOut inout)
	{
		orderCostService.deleteInOutCosts(InOutId.ofRepoId(inout.getM_InOut_ID()));
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID })
	public void onBeforeVoid(final I_M_InOut inout)
	{
		orderCostService.deleteInOutCosts(InOutId.ofRepoId(inout.getM_InOut_ID()));
	}
}
