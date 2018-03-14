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


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.IOrderBL;

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
		final String deliveryViaRule = Services.get(IOrderBL.class).evaluateOrderDeliveryViaRule(order);

		if (!Check.isEmpty(deliveryViaRule, true))
		{
			order.setDeliveryViaRule(deliveryViaRule);
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

		final I_M_Warehouse warehouse = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);

		if (warehouse != null)
		{
			order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		}
	}
}
