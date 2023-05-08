package de.metas.order.costs.interceptor;

import de.metas.order.OrderAndLineId;
import de.metas.order.costs.OrderCostRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final OrderCostRepository orderCostRepository;

	public C_OrderLine(
			@NonNull final OrderCostRepository orderCostRepository)
	{
		this.orderCostRepository = orderCostRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeDelete(final I_C_OrderLine record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID());
			orderCostRepository.deleteByCreatedOrderLineId(orderAndLineId);
		}
	}

}
