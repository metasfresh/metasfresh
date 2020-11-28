package de.metas.fresh.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.CalloutEngine;

import de.metas.adempiere.model.I_C_Order;
import de.metas.util.Services;

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
			final WarehouseId warehouseId = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
			if (warehouseId != null)
			{
				order.setM_Warehouse_ID(warehouseId.getRepoId());
			}
		}
		
		return NO_ERROR;
	}
}
