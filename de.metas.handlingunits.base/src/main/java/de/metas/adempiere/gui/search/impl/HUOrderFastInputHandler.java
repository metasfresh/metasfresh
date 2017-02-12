package de.metas.adempiere.gui.search.impl;

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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.model.GridTab;

import de.metas.adempiere.callout.IOrderFastInputHandler;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.order.api.IHUOrderBL;

public class HUOrderFastInputHandler implements IOrderFastInputHandler
{

	@Override
	public void clearFields(final GridTab gridTab)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(gridTab, I_C_Order.class);
		order.setM_HU_PI_Item_Product_ID(-1);
		order.setQty_FastInput_TU(null);

		// these changes will be propagated to the GUI component
		gridTab.setValue(I_C_Order.COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		gridTab.setValue(I_C_Order.COLUMNNAME_Qty_FastInput_TU, null);

	}

	@Override
	public boolean requestFocus(final GridTab gridTab)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(gridTab, I_C_Order.class);

		final Integer productId = order.getM_Product_ID();
		if (productId <= 0
				&& gridTab.getField(de.metas.adempiere.model.I_C_Order.COLUMNNAME_M_Product_ID).isDisplayed())
		{
			gridTab.getField(de.metas.adempiere.model.I_C_Order.COLUMNNAME_M_Product_ID).requestFocus();
			return true;
		}

		// task 06300: focus shall go from product to TU-qty (not CU-qty)
		final BigDecimal qtyTU = order.getQty_FastInput_TU();
		final BigDecimal qtyCU = order.getQty_FastInput();

		final boolean hasTUs = Services.get(IHUOrderBL.class).hasTUs(order);
		if (!hasTUs || null != qtyCU && qtyCU.signum() > 0)
		{
			// the product is not assigned to a TU, so we return false and leave it to the default handler which will probably request the focus for the "CU Qty" field
			// 06730: Also, we leave it for the default handler if we have a TU quantity with no HU.
			return false;
		}

		if ((qtyTU == null || qtyTU.signum() <= 0)
				&& gridTab.getField(I_C_Order.COLUMNNAME_Qty_FastInput_TU).isDisplayed())
		{
			// product has been set, but qty hasn't
			gridTab.getField(I_C_Order.COLUMNNAME_Qty_FastInput_TU).requestFocus();
			return true;
		}

		final int huPIPId = order.getM_HU_PI_Item_Product_ID();
		if (huPIPId <= 0
				&& gridTab.getField(I_C_Order.COLUMNNAME_M_HU_PI_Item_Product_ID).isDisplayed())
		{
			gridTab.getField(I_C_Order.COLUMNNAME_M_HU_PI_Item_Product_ID).requestFocus();
			return true;
		}

		// no focus was requested
		return false;
	}

	@Override
	public IGridTabRowBuilder createLineBuilderFromHeader(final Object model)
	{
		return new OrderLineHUPackingGridRowBuilder();
	}

}
