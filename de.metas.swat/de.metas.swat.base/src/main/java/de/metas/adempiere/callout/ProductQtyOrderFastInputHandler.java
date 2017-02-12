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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.model.GridTab;

import de.metas.adempiere.gui.search.OrderLineProductQtyGridRowBuilder;
import de.metas.adempiere.model.I_C_Order;

public class ProductQtyOrderFastInputHandler implements IOrderFastInputHandler
{
	@Override
	public void clearFields(final GridTab gridTab)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(gridTab, I_C_Order.class);
		order.setM_Product_ID(-1);
		order.setQty_FastInput(null);
		
		// these changes will be propagated to the GUI component
		gridTab.setValue(I_C_Order.COLUMNNAME_M_Product_ID, null);
		gridTab.setValue(I_C_Order.COLUMNNAME_Qty_FastInput, null);
	}

	@Override
	public boolean requestFocus(final GridTab gridTab)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(gridTab, I_C_Order.class);
		
		final Integer productId = order.getM_Product_ID();
		if (productId <= 0
				&& gridTab.getField(I_C_Order.COLUMNNAME_M_Product_ID).isDisplayed())
		{
			gridTab.getField(I_C_Order.COLUMNNAME_M_Product_ID).requestFocus();
			return true;
		}

		final BigDecimal qty = order.getQty_FastInput();

		if (qty == null || qty.signum() <= 0
				&& gridTab.getField(I_C_Order.COLUMNNAME_Qty_FastInput).isDisplayed())
		{
			// product has been set, but qty hasn't
			gridTab.getField(I_C_Order.COLUMNNAME_Qty_FastInput).requestFocus();
			return true;
		}

		// no focus was requested
		return false;
	}

	@Override
	public IGridTabRowBuilder createLineBuilderFromHeader(final Object model)
	{
		final OrderLineProductQtyGridRowBuilder builder = new OrderLineProductQtyGridRowBuilder();
		builder.setSource(model);
		return builder;
	}
}
