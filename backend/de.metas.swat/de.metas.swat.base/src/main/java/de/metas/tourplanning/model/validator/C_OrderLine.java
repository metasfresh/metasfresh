package de.metas.tourplanning.model.validator;

import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.tourplanning.api.IOrderDeliveryDayBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final IOrderDeliveryDayBL orderDeliveryDayBL = Services.get(IOrderDeliveryDayBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void updatePreparationDate(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		if (order.isSOTrx())
		{
			return;
		}
		orderDeliveryDayBL.setPreparationDateAndTour(order, /* fallbackToDatePromised= */ true);
		orderBL.save(order);
	}
}
