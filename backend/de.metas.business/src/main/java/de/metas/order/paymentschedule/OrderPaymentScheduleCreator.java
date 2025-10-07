/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.paymentschedule;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class OrderPaymentScheduleCreator
{

	private static final Timestamp PENDING_DATE = Timestamp.valueOf("9999-01-01 00:00:00");
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final PaymentTermService paymentTermService;
	private final OrderPayScheduleService orderPayScheduleService;

	public OrderPaymentScheduleCreator(@NonNull final PaymentTermService paymentTermService, @NonNull final OrderPayScheduleService orderPayScheduleService)
	{
		this.paymentTermService = paymentTermService;
		this.orderPayScheduleService = orderPayScheduleService;
	}

	/**
	 * Entry point to create schedules for a completed Order.
	 */
	public void createSchedules(@NonNull final I_C_Order orderRecord)
	{
		final PaymentTermId paymenttermId = orderBL.getPaymentTermId(orderRecord);
		final PaymentTerm paymentTerm = paymentTermService.getById(paymenttermId);

		if (paymentTerm.hasBreaks())
		{
			return;
		}

		if (orderHasLegacyPaySchedule(orderRecord))
		{
			throw new AdempiereException("Payment Term " + paymentTerm.getValue() +
					" is flagged as complex and cannot coexist with legacy C_PaySchedule records.");
		}

		final Amount grandTotal = orderBL.getGrandTotal(orderRecord);

		if (grandTotal.isZero())
		{
			return; // Nothing to schedule
		}

		final CurrencyPrecision precision = orderBL.getAmountPrecision(orderRecord);
		final OrderId orderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());

		for (final PaymentTermBreak termBreak : paymentTerm.getSortedBreaks())
		{

			// Calculate portion amount (e.g., 40% of total)
			final Percent percentage = termBreak.getPercent();
			final Amount dueAmount = grandTotal.multiply(percentage, precision);

			// Determine reference date and status
			final ReferenceDateResult result = getReferenceDate(orderRecord, termBreak);

			final OrderPayScheduleRequest orderPayScheduleRequest = OrderPayScheduleRequest.builder()
					.orderId(orderId)
					.dueDate(result.getCalculatedDueDate())
					.dueAmount(dueAmount)
					.paymentTermBreakId(termBreak.getId())
					.referenceDateType(termBreak.getReferenceDateType())
					.build();

			orderPayScheduleService.createSchedule(orderPayScheduleRequest);
		}
	}

	/**
	 * Determines the initial reference date and calculates the due date.
	 */
	private ReferenceDateResult getReferenceDate(final @NonNull I_C_Order order, final @NonNull PaymentTermBreak termBreak)
	{

		final ReferenceDateType referenceDateType = termBreak.getReferenceDateType();
		Timestamp referenceDate = null;
		String status = "Pending reference";

		// --- Get the initial reference date from available order data ---
		switch (referenceDateType)
		{
			case OrderDate:
				referenceDate = order.getDateOrdered();
				break;

			case LCDate:
				referenceDate = order.getLC_Date();
				break;

			case BLDate:
			case ETADate:
				// BL/ETA is captured later on M_ShipperTransportation. Not available here.
				break;

			case InvoiceDate:
				// InvoiceDate is captured later on Invoice. Not available here.
				break;
			default:
		}

		Timestamp finalDueDate;

		if (referenceDate != null)
		{
			finalDueDate = TimeUtil.addDays(referenceDate, termBreak.getOffsetDays());
			status = "WaitingPayment"; // Financial obligation is now calculable/existing
		}
		else
		{
			// Reference date is missing (BLDate, ETA, or missing LCDate)
			finalDueDate = PENDING_DATE;
			status = "Pending reference"; // Status remains Pending
		}

		return new ReferenceDateResult(finalDueDate, status);
	}

	private boolean orderHasLegacyPaySchedule(@NonNull final I_C_Order order)
	{
		//TODO to be implemented
		return false;
	}

	@Value
	static class ReferenceDateResult
	{
		@NonNull Timestamp calculatedDueDate;
		@NonNull String status;
	}
}
