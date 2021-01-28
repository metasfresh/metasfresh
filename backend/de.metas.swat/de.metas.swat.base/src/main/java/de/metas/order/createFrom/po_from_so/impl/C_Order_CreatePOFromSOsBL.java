package de.metas.order.createFrom.po_from_so.impl;

import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.spi.IC_Order_CreatePOFromSOsListener;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.ToString;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_OrderLine;

import java.util.ArrayList;

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

@ToString
public class C_Order_CreatePOFromSOsBL implements IC_Order_CreatePOFromSOsBL
{
	private static final String SYSCONFIG_PURCHASE_QTY_SOURCE = "de.metas.order.C_Order_CreatePOFromSOs.PurchaseQtySource";
	private static final String SYSCONFIG_PURCHASE_QTY_SOURCE_DEFAULT = I_C_OrderLine.COLUMNNAME_QtyOrdered;

	private final ArrayList<IC_Order_CreatePOFromSOsListener> listeners = new ArrayList<>();

	@Override
	public void registerListener(final IC_Order_CreatePOFromSOsListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public IC_Order_CreatePOFromSOsListener getCompositeListener()
	{
		return (purchaseOrderLine, salesOrderLine) -> {
			for (final IC_Order_CreatePOFromSOsListener actualListener : listeners)
			{
				actualListener.afterPurchaseOrderLineCreatedBeforeSave(purchaseOrderLine, salesOrderLine);
			}
		};
	}

	@Override
	public String getConfigPurchaseQtySource()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		// afaiu, qtyReserved would make more sense, but there seem to be problems in C_OrderLine.QtyReserved
		final String purchaseQtySource = sysConfigBL.getValue(SYSCONFIG_PURCHASE_QTY_SOURCE, SYSCONFIG_PURCHASE_QTY_SOURCE_DEFAULT);

		if (!SYSCONFIG_PURCHASE_QTY_SOURCE_DEFAULT.equalsIgnoreCase(purchaseQtySource)
				&& !I_C_OrderLine.COLUMNNAME_QtyReserved.equalsIgnoreCase(purchaseQtySource))
		{
			Loggables.addLog(
					"AD_SysConfig " + SYSCONFIG_PURCHASE_QTY_SOURCE + " has an unsupported value: " + purchaseQtySource + "; Instead we use the default value: " + SYSCONFIG_PURCHASE_QTY_SOURCE_DEFAULT);

			return SYSCONFIG_PURCHASE_QTY_SOURCE_DEFAULT;
		}
		return purchaseQtySource;
	}
}
