/**
 * 
 */
package de.metas.order.callout;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import de.metas.adempiere.model.I_C_Order;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;

/**
 * @author cg
 * 
 */
@Callout(I_C_Order.class)
public class C_Order
{

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_BPartner_ID })
	public void setDeliveryViaRule(final I_C_Order order, final ICalloutField field)
	{
		final DeliveryViaRule deliveryViaRule = Services.get(IOrderBL.class).evaluateOrderDeliveryViaRule(order);

		if (deliveryViaRule != null)
		{
			order.setDeliveryViaRule(deliveryViaRule.getCode());
		}
	}

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_IsDropShip })
	public void setDropShipWarehouse(final I_C_Order order, final ICalloutField field)
	{

		if (order == null)
		{
			return;
		}

		if (order.isDropShip() == false)
		{
			return;
		}

		final WarehouseId warehouseId = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
		if (warehouseId != null)
		{
			order.setM_Warehouse_ID(warehouseId.getRepoId());
		}
	}
}
