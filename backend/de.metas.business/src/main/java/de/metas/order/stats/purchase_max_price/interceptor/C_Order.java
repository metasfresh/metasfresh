package de.metas.order.stats.purchase_max_price.interceptor;

import de.metas.order.stats.purchase_max_price.PurchaseLastMaxPriceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order
{
	@NonNull private final PurchaseLastMaxPriceService purchaseLastMaxPriceService;

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(final I_C_Order order)
	{
		purchaseLastMaxPriceService.invalidateByOrder(order);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REACTIVATE })
	public void afterReverse(final I_C_Order order)
	{
		purchaseLastMaxPriceService.invalidateByOrder(order);
	}
}
