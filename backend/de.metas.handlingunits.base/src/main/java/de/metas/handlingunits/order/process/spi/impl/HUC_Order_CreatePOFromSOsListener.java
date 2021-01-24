package de.metas.handlingunits.order.process.spi.impl;

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

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.spi.IC_Order_CreatePOFromSOsListener;

/**
 * Copy the handling unit related details from a sales order line to a purchase order line.<br>
 * Does <b>not</b> copy actual quantities.
 * <p>
 * Note: this is a replacement for <code>SOLineToPOLineHUCopyHandler</code> (task 07221).
 *
 * @see IC_Order_CreatePOFromSOsBL#registerListener(IC_Order_CreatePOFromSOsListener)
 */
public class HUC_Order_CreatePOFromSOsListener implements IC_Order_CreatePOFromSOsListener
{

	@Override
	public void afterPurchaseOrderLineCreatedBeforeSave(final org.compiere.model.I_C_OrderLine purchaseOrderLine, final org.compiere.model.I_C_OrderLine salesOrderLine)
	{
		final I_C_OrderLine huPurchaseOrderLine = InterfaceWrapperHelper.create(purchaseOrderLine, I_C_OrderLine.class);
		final I_C_OrderLine huSalesOrderLine = InterfaceWrapperHelper.create(salesOrderLine, I_C_OrderLine.class);

		final BigDecimal qtyItemCapacity = huSalesOrderLine.getQtyItemCapacity();
		huPurchaseOrderLine.setQtyItemCapacity(qtyItemCapacity);

		final String packDescription = huSalesOrderLine.getPackDescription();
		huPurchaseOrderLine.setPackDescription(packDescription);

		final I_M_HU_PI_Item_Product mHUPIItemProduct = huSalesOrderLine.getM_HU_PI_Item_Product();
		huPurchaseOrderLine.setM_HU_PI_Item_Product(mHUPIItemProduct);
	}

}
