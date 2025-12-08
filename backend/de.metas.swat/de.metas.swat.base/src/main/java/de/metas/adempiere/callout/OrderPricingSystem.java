package de.metas.adempiere.callout;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.IOrderBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.CalloutEngine;

public class OrderPricingSystem extends CalloutEngine
{
	public String mPricingSystemId(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}
		
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		Services.get(IOrderBL.class).setPriceList(order);
		
		return NO_ERROR;
	}
}
