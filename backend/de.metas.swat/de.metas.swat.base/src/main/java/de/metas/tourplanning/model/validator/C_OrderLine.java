package de.metas.tourplanning.model.validator;

import de.metas.tourplanning.api.IOrderDeliveryDayBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final IOrderDeliveryDayBL orderDeliveryDayBL = Services.get(IOrderDeliveryDayBL.class);

	/**
	 * Purchase: a line's product change can change the order's tour, so recompute the order header's preparation
	 * date + tour. No-op for sales orders (gated inside the BL).
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void updatePurchaseHeaderPreparationDate(@NonNull final I_C_OrderLine orderLine)
	{
		orderDeliveryDayBL.updatePurchaseHeaderPreparationDate(orderLine);
	}

	/**
	 * Sales: each line's {@code PreparationDate} mirrors the shipment schedule's initial value, derived from the
	 * line's own delivery date. The line is always re-derived (never preserved) — overrides live on
	 * {@code M_ShipmentSchedule.PreparationDate_Override}. No-op for purchase orders (gated inside the BL).
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_DatePromised, I_C_OrderLine.COLUMNNAME_PresetDateShipped })
	public void setLinePreparationDate(@NonNull final I_C_OrderLine orderLine)
	{
		orderDeliveryDayBL.setLinePreparationDate(orderLine);
	}
}
