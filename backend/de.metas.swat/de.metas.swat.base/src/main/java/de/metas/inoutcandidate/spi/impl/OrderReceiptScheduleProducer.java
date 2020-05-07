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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.AbstractReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;

/**
 * Creates receipt schedules for given order by delegating all work to {@link OrderLineReceiptScheduleProducer}.
 * 
 * @author tsa
 * 
 */
public class OrderReceiptScheduleProducer extends AbstractReceiptScheduleProducer
{
	private IReceiptScheduleProducer createOrderLineReceiptScheduleProducer()
	{
		final boolean async = false;
		return getReceiptScheduleProducerFactory().createProducer(I_C_OrderLine.Table_Name, async);
	}

	private I_C_Order checkAndGetOrder(final Object model)
	{
		Check.assumeNotNull(model, "model not null");

		final I_C_Order order = InterfaceWrapperHelper.create(model, I_C_Order.class);
		Check.assume(!order.isSOTrx(), "Only purchase orders are handled: {}", order);

		return order;

	}

	@Override
	public List<I_M_ReceiptSchedule> createOrUpdateReceiptSchedules(final Object model, final List<I_M_ReceiptSchedule> previousSchedules)
	{
		final I_C_Order order = checkAndGetOrder(model);

		final IReceiptScheduleProducer orderLineProducer = createOrderLineReceiptScheduleProducer();

		final List<I_M_ReceiptSchedule> schedulers = new ArrayList<I_M_ReceiptSchedule>();

		final List<I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order);
		for (final I_C_OrderLine ol : orderLines)
		{
			final List<I_M_ReceiptSchedule> orderLineSchedules = orderLineProducer.createOrUpdateReceiptSchedules(ol, previousSchedules);
			schedulers.addAll(orderLineSchedules);
		}

		return schedulers;
	}
	
	@Override
	public void updateReceiptSchedules(final Object model)
	{
		final I_C_Order order = checkAndGetOrder(model);

		final IReceiptScheduleProducer orderLineProducer = createOrderLineReceiptScheduleProducer();

		final List<I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order);
		for (final I_C_OrderLine ol : orderLines)
		{
			orderLineProducer.updateReceiptSchedules(ol);
		}
	}

	@Override
	public void inactivateReceiptSchedules(final Object model)
	{
		final I_C_Order order = checkAndGetOrder(model);

		final IReceiptScheduleProducer orderLineProducer = createOrderLineReceiptScheduleProducer();

		final List<I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order);
		for (final I_C_OrderLine ol : orderLines)
		{
			orderLineProducer.inactivateReceiptSchedules(ol);
		}
	}
}
