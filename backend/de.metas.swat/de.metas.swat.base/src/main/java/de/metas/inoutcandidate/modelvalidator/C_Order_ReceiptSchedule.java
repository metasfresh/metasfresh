package de.metas.inoutcandidate.modelvalidator;

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

import java.util.Collections;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;

import de.metas.document.exception.DocumentActionException;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.util.Check;
import de.metas.util.Services;

@Validator(I_C_Order.class)
public class C_Order_ReceiptSchedule
{
	private static final AdMessageKey ERR_NoReactivationIfReceiptsCreated = AdMessageKey.of("ERR_NoReactivationIfReceiptsCreated");
	private static final AdMessageKey ERR_NoReactivationIfProcessedReceiptSchedules = AdMessageKey.of("ERR_NoReactivationIfProcessedReceiptSchedules");
	private static final AdMessageKey ERR_NoVoidIfProcessedReceiptSchedules = AdMessageKey.of("ERR_NoVoidIfProcessedReceiptSchedules");

	public static boolean isEligibleForReceiptSchedule(final I_C_Order order)
	{
		Check.assumeNotNull(order, "order not null");

		// Only Purchase Orders are handled
		if (order.isSOTrx())
		{
			return false;
		}

		return true;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createReceiptSchedules(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		// services
		final IReceiptScheduleProducerFactory receiptScheduleProducerFactory = Services.get(IReceiptScheduleProducerFactory.class);
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		//
		// Generate receipt schedules from this order.
		// NOTE: because we got performance issues when there are a lot of data to be generated for each order line
		// (e.g. de.metas.handlingunits had to generate a lot of HUs for each order line)
		// We decided to create a workpackage for each order line (08168).

		// Get the receipt schedule producer
		final boolean createReceiptSchedulesAsync = true;
		final IReceiptScheduleProducer receiptScheduleProducer = receiptScheduleProducerFactory.createProducer(I_C_OrderLine.Table_Name, createReceiptSchedulesAsync);

		// Iterate order lines and call the producer for each of them.
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final List<I_M_ReceiptSchedule> previousSchedules = Collections.emptyList();
			receiptScheduleProducer.createOrUpdateReceiptSchedules(orderLine, previousSchedules);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_VOID })
	public void inactivateRecepitSchedules(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		// NOTE: we are doing the inactivation synchronously because we need to have it done right away
		final IReceiptScheduleProducer producer = Services.get(IReceiptScheduleProducerFactory.class)
				.createProducer(I_C_Order.Table_Name, false);

		producer.inactivateReceiptSchedules(order);
	}

	/**
	 * Never reactivate a purchase order that already has created receipts.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void reactivateIfNoReceipts(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		final boolean hasReceipts = hasReceipts(order);

		// Throw exception if at least one (even partial) receipt is linked to this order
		if (hasReceipts)
		{
			throw new DocumentActionException(ERR_NoReactivationIfReceiptsCreated);
		}

	}

	private boolean hasReceipts(final I_C_Order order)
	{
		final List<I_M_InOut> inouts = Services.get(IOrderDAO.class).retrieveInOutsForMatchingOrderLines(order);

		if (inouts.isEmpty())
		{
			return false;
		}

		return true;
	}

	/**
	 * Never reactivate an order if it already has processed receipt schedules.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void reactivateIfNoReceiptScheduleProcessed(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		if (hasProcessedReceiptSchedules(order))
		{
			throw new DocumentActionException(ERR_NoReactivationIfProcessedReceiptSchedules);

		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID })
	public void VoidIfNoReceiptScheduleProcessed(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		if (hasProcessedReceiptSchedules(order))
		{
			throw new DocumentActionException(ERR_NoVoidIfProcessedReceiptSchedules);

		}
	}

	public boolean hasProcessedReceiptSchedules(final I_C_Order order)
	{
		// services
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		for (I_C_OrderLine orderLine : orderLines)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleDAO.retrieveForRecord(orderLine);

			// Throw exception if at least one processed receipt schedule is linked to a line of this order
			if (receiptSchedule != null && receiptSchedule.isProcessed())
			{
				return true;
			}
		}

		return false;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_CLOSE)
	public void closeReceiptSchedules(final I_C_Order order)
	{
		if (!isEligibleForReceiptSchedule(order))
		{
			return;
		}

		// services
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		for (I_C_OrderLine orderLine : orderLines)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleDAO.retrieveForRecord(orderLine);

			if (receiptScheduleBL.isClosed(receiptSchedule))
			{
				continue;
			}

			receiptScheduleBL.close(receiptSchedule);
		}
	}
}
