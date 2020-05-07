package de.metas.fresh.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.CalloutEngine;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.model.I_C_Order;

public class C_Order extends CalloutEngine
{

	public String setWarehouse(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}
		
		final Object value = calloutField.getValue();
		if (value != null)
		{
			final I_C_Order order = calloutField.getModel(I_C_Order.class);
			final I_M_Warehouse warehouse = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
			
			if (warehouse != null)
			{
				order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
			}
		}
		
		return NO_ERROR;
	}
}
