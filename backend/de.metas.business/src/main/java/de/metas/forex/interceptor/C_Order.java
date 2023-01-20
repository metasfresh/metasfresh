package de.metas.forex.interceptor;

import de.metas.forex.ForexContractService;
import de.metas.order.OrderId;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final ForexContractService forexContractService;

	public C_Order(final ForexContractService forexContractService) {this.forexContractService = forexContractService;}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void beforeReactivate(final I_C_Order record)
	{
		final OrderId orderId = OrderId.ofRepoId(record.getC_Order_ID());
		if (forexContractService.hasAllocations(orderId))
		{
			throw new AdempiereException("Cannot re-activate an order with FEC allocations");
		}
	}
}
