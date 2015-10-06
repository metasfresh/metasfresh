package de.metas.inoutcandidate.spi.impl;

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


import org.adempiere.order.service.IOrderPA;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.adempiere.service.IOrderBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.ReceiptScheduleListenerAdapter;

public class OrderLineReceiptScheduleListener extends ReceiptScheduleListenerAdapter
{
	public static final OrderLineReceiptScheduleListener INSTANCE = new OrderLineReceiptScheduleListener();

	private OrderLineReceiptScheduleListener()
	{
	}

	/**
	 * If a receipt schedule is closed then this method retrieves its order line and sets the line's <code>QtyOrdered</code> to the current <code>QtyDelviered</code>, thus effectively "closing" the
	 * line. Is also updates the order line's reservation via {@link IOrderPA#reserveStock(I_C_Order, I_C_OrderLine...)}.
	 * 
	 * @task 08452
	 */
	@Override
	public void onAfterClose(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_OrderLine orderLine = receiptSchedule.getC_OrderLine();
		if (orderLine == null)
		{
			return; // shall not happen
		}

		Services.get(IOrderBL.class).closeLine(orderLine);
	}

	@Override
	public void onAfterReopen(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_OrderLine orderLine = receiptSchedule.getC_OrderLine();
		if (orderLine == null)
		{
			return; // shall not happen
		}

		Services.get(IOrderBL.class).reopenLine(orderLine);
	}
}
