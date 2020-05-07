package de.metas.handlingunits.callout;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.order.api.IHUOrderBL;

public class C_Order extends OrderFastInput
{
	public String UpdateQuickEntry(final ICalloutField calloutField)
	{
		// Check: if is dirty/temporary value skip it
		final Object value = calloutField.getValue();
		if (value == null)
		{
			return NO_ERROR;
		}

		final String FLAGNAME = C_Order.class.getName() + "UpdatingQuickEntry";

		final Properties ctx = calloutField.getCtx();
		final int WindowNo = calloutField.getWindowNo();
		if ("Y".equals(Env.getContext(ctx, WindowNo, FLAGNAME)))
		{
			return NO_ERROR;
		}

		Env.setContext(ctx, WindowNo, FLAGNAME, "Y");
		try
		{

			final I_C_Order order = calloutField.getModel(I_C_Order.class);

			final boolean hasTus = Services.get(IHUOrderBL.class).hasTUs(order);

			// The updates are needed just in case the product is contained in any transportation unit.
			// Otherwise, the fast input shall work as before ( just based on Product and Menge CU)
			if (hasTus)
			{
				Services.get(IHUOrderBL.class).updateQtys(order, calloutField.getColumnName());
			}
			return CalloutEngine.NO_ERROR;

		}
		finally
		{
			Env.setContext(ctx, WindowNo, FLAGNAME, (String)null);
		}
	}

	// not doint fast-input on M_HU_PI_Item_Product
	// public void onM_HU_PI_Item_Product(final Properties ctx, final int WindowNo,
	// final GridTab mTab, final GridField mField, final Object value)
	// {
	// evalProductQtyInput(ctx, WindowNo, mTab);
	// }

	public void onMengeCU(final ICalloutField calloutField)
	{
		evalProductQtyInput(calloutField);
	}

	public String onMProductChange(final ICalloutField calloutField)
	{
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		Check.assumeNotNull(order, "Order cannot be null");
		
		final I_M_Product quickInputProduct = order.getM_Product();

		Services.get(IHUOrderBL.class).findM_HU_PI_Item_Product(order, quickInputProduct, order::setM_HU_PI_Item_Product);
		return NO_ERROR;
	}
	
}
