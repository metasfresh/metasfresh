package de.metas.order.process.impl;

import java.util.ArrayList;

import org.compiere.model.I_C_OrderLine;

import de.metas.order.process.IC_Order_CreatePOFromSOsBL;
import de.metas.order.process.spi.IC_Order_CreatePOFromSOsListener;

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

public class C_Order_CreatePOFromSOsBL implements IC_Order_CreatePOFromSOsBL
{

	private final ArrayList<IC_Order_CreatePOFromSOsListener> listeners = new ArrayList<>();

	@Override
	public void registerListener(IC_Order_CreatePOFromSOsListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public IC_Order_CreatePOFromSOsListener getCompositeListener()
	{
		return new IC_Order_CreatePOFromSOsListener()
		{
			@Override
			public void afterPurchaseOrderLineCreatedBeforeSave(I_C_OrderLine purchaseOrderLine, I_C_OrderLine salesOrderLine)
			{
				for (IC_Order_CreatePOFromSOsListener actualListener : listeners)
				{
					actualListener.afterPurchaseOrderLineCreatedBeforeSave(purchaseOrderLine, salesOrderLine);
				}
			}
		};
	}
}
