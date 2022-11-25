package de.metas.handlingunits.picking.interceptor;

import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.order.OrderId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final PickingJobService pickingJobService;

	public C_Order(
			@NonNull final PickingJobService pickingJobService)
	{
		this.pickingJobService = pickingJobService;
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_CLOSE })
	public void beforeClose(@NonNull final I_C_Order order)
	{
		abortPickingJobs(order);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void beforeReverse(@NonNull final I_C_Order order)
	{
		abortPickingJobs(order);
	}

	private void abortPickingJobs(final @NonNull I_C_Order order)
	{
		if (order.isSOTrx())
		{
			final OrderId salesOrderId = OrderId.ofRepoId(order.getC_Order_ID());
			pickingJobService.abortForSalesOrderId(salesOrderId);
		}
	}
}
