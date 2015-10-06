package de.metas.fresh.callout;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.model.I_C_Order;

public class C_Order extends CalloutEngine
{

	public String setWarehouse(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		if (isCalloutActive())
		{
			return "";
		}
		
		final I_C_Order order = InterfaceWrapperHelper.create(mTab, I_C_Order.class);

		if (value != null || mField.getValue() != null)
		{
			final I_M_Warehouse warehouse = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
			
			if (warehouse != null)
			{
				order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
			}
		}
		
		return "";
	}
}
