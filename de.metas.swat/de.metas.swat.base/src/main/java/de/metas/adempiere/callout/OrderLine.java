package de.metas.adempiere.callout;

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


import java.util.Properties;

import org.adempiere.model.GridTabWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.CalloutOrder;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MOrgInfo;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.interfaces.I_C_OrderLine;

public class OrderLine extends CalloutEngine
{
	public String product(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		I_C_OrderLine orderLine = GridTabWrapper.create(mTab, I_C_OrderLine.class);
		if (orderLine.getM_Product_ID() > 0)
		{
			I_M_Product product = POWrapper.create(orderLine.getM_Product(), I_M_Product.class);
			orderLine.setIsDiverse(product.isDiverse());
		}
		else
		{
			orderLine.setIsDiverse(false);
		}

		return "";
	}

	public String warehouse(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		final I_C_OrderLine orderLine = GridTabWrapper.create(mTab, I_C_OrderLine.class);
		final I_C_Order order = POWrapper.create(orderLine.getC_Order(), I_C_Order.class);

		if (orderLine.getM_Warehouse_ID() == order.getM_Warehouse_ID())
		{
			// warehouse of order and order line are the same; nothing to do
			return "";
		}

		if (orderLine.getM_Warehouse_ID() == MOrgInfo.get(ctx, order.getAD_Org_ID(), null).getDropShip_Warehouse_ID())
		{
			// order line's warehouse is the dropship warehouse; nothing to do
			return "";
		}

		// correcting order line's warehouse id
		orderLine.setM_Warehouse_ID(order.getM_Warehouse_ID());
		return "";
	}

	/**
	 * Called for: C_OrderLine.IsPriceManual
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String chkManualPrice(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);
		if (!orderLine.isManualPrice())
		{
			CalloutOrder.updatePrices(orderLine);
		}

		return NO_ERROR;
	}

}
