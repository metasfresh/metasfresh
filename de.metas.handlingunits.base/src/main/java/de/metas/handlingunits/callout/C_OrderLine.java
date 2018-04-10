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


import java.math.BigDecimal;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.order.IOrderLineBL;

@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	/**
	 * Task 06915: only updating on QtyEntered, but not on M_HU_PI_Item_Product_ID, because when the HU_PI_Item_Product changes, we want QtyEnteredTU to stay the same.
	 *
	 * @param orderLine
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_QtyEntered })
	public void updateQtyTU(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		Services.get(IHUPackingAwareBL.class).setQtyTU(packingAware);
		packingAware.setQty(packingAware.getQty());
	}

	/**
	 * Task 06915: If QtyEnteredTU or M_HU_PI_Item_Product_ID change, then update QtyEntered (i.e. the CU qty).
	 *
	 * @param orderLine
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_QtyEnteredTU, I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID })
	public void updateQtyCU(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		final Integer qtyPacks = packingAware.getQtyTU().intValue();
		Services.get(IHUPackingAwareBL.class).setQtyCUFromQtyTU(packingAware, qtyPacks);

		// Update lineNetAmt, because QtyEnteredCU changed : see task 06727
		Services.get(IOrderLineBL.class).updateLineNetAmt(orderLine, orderLine.getQtyEntered(), BigDecimal.ONE);
	}

	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_C_BPartner_ID
			, I_C_OrderLine.COLUMNNAME_QtyEntered
			, I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID
			, I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void onHURelevantChange(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);

		Services.get(IHUOrderBL.class).updateOrderLine(ol, field.getColumnName());
	}
}
