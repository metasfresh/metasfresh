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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;

import de.metas.adempiere.service.IOrderBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.ReceiptScheduleListenerAdapter;

public class OrderLineReceiptScheduleListener extends ReceiptScheduleListenerAdapter
{
	public static final OrderLineReceiptScheduleListener INSTANCE = new OrderLineReceiptScheduleListener();

	private OrderLineReceiptScheduleListener()
	{
	}

	/**
	 * If a receipt schedule is closed then this method retrieves its <code>orderLine</code> and invokes {@link IOrderBL#closeLine(I_C_OrderLine)} on it.
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

		final IOrderBL orderBL = Services.get(IOrderBL.class);
		orderBL.closeLine(orderLine);
	}

	/**
	 * If a receipt schedule is reopened then this method retrieves its <code>orderLine</code> and invokes {@link IOrderBL#reopenLine(I_C_OrderLine)} on it.
	 * <p>
	 * Note: we open the order line <b>after</b> the reopen,
	 * because this reopening will cause a synchronous updating of the receipt schedule via the {@link IReceiptScheduleProducer} framework
	 * and the <code>receiptSchedule</code> that is to be reopened uses the order line's <code>QtyOrdered</code> value.
	 *
	 * @task <a href="https://github.com/metasfresh/metasfresh/issues/315">issue #315</a>
	 */
	@Override
	public void onAfterReopen(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_OrderLine orderLine = receiptSchedule.getC_OrderLine();
		if (orderLine == null)
		{
			return; // shall not happen
		}

		final IOrderBL orderBL = Services.get(IOrderBL.class);
		orderBL.reopenLine(orderLine);
	}
}
