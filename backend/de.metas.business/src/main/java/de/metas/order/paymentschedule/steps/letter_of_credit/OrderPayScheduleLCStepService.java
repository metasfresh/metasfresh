/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.order.paymentschedule.steps.letter_of_credit;

import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleLineContext;
import de.metas.order.paymentschedule.core.service.OrderPayScheduleService;
import de.metas.order.paymentschedule.referenced_docs.prepayment.OrderPaySchedulePrepaymentService;
import de.metas.order.paymentschedule.referenced_docs.prepayment.Prepayment;
import de.metas.order.paymentschedule.referenced_docs.proforma_invoice.OrderPayScheduleProformaService;
import de.metas.order.paymentschedule.referenced_docs.proforma_invoice.ProformaInvoice;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.OrderPayScheduleRegularInvoiceService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleLCStepService
{
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final OrderPayScheduleProformaService proformaService;
	@NonNull private final OrderPaySchedulePrepaymentService prepaymentService;
	@NonNull private final OrderPayScheduleRegularInvoiceService regularInvoiceService;

	public static OrderPayScheduleLCStepService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new OrderPayScheduleLCStepService(
				OrderPayScheduleService.newInstanceForUnitTesting(),
				OrderPayScheduleProformaService.newInstanceForUnitTesting(),
				OrderPaySchedulePrepaymentService.newInstanceForUnitTesting(),
				OrderPayScheduleRegularInvoiceService.newInstanceForUnitTesting()
		);
	}

	public void recomputeLCStepAfterPaymentCompleted(@NonNull final I_C_Payment paymentRecord)
	{
		final Prepayment prepayment = prepaymentService.fromRecordIfEligible(paymentRecord).orElse(null);
		if (prepayment == null)
		{
			return;
		}

		recomputeLCStep(prepayment.getOrderId(), prepayment);

		regularInvoiceService.retroAllocateUnallocatedInvoices(prepayment);
	}

	public void recomputeLCStepAfterPaymentReversed(@NonNull final I_C_Payment paymentRecord)
	{
		final OrderId orderId = prepaymentService.findOrderId(paymentRecord).orElse(null);
		if (orderId == null)
		{
			return;
		}

		// At AFTER_REVERSECORRECT both the original and the reversal sit at DocStatus="RE",
		// so the DAO query correctly returns no CO/CL payment for the proforma.
		recomputeLCStep(orderId);
	}

	public void recomputeLCStep(@NonNull OrderId orderId)
	{
		recomputeLCStep(orderId, null);
	}

	private void recomputeLCStep(@NonNull OrderId orderId, @Nullable final Prepayment triggeringPrepayment)
	{
		if (triggeringPrepayment != null && !OrderId.equals(orderId, triggeringPrepayment.getOrderId()))
		{
			throw new AdempiereException("Prepayment order is not matching")
					.setParameter("prepayment", triggeringPrepayment)
					.setParameter("orderId", orderId);
		}

		final OrderPaySchedule schedule = orderPayScheduleService.getByOrderId(orderId).orElse(null);
		if (schedule == null) {return;} // no pay-schedule at all — no-op

		final OrderPayScheduleLine lcStep = schedule.getSingleLCLine().orElse(null);
		if (lcStep == null) {return;} // no LC break in payment term — no-op

		//
		// Proforma invoice
		final ProformaInvoice proforma;
		if (triggeringPrepayment != null)
		{
			proforma = proformaService.getById(triggeringPrepayment.getProformaInvoiceId());
		}
		else
		{
			proforma = proformaService.getByOrderId(orderId).orElse(null);
		}
		//
		if (proforma == null)
		{
			// No proforma allocation — reset to Pending
			schedule.markAsPending(lcStep.getIdNotNull());
			orderPayScheduleService.save(schedule);
			clearLCDateOnOrder(orderId);
			return;
		}

		//
		// Prepayment
		final Prepayment prepayment;
		if (triggeringPrepayment != null)
		{
			prepayment = triggeringPrepayment;
		}
		else
		{
			prepayment = prepaymentService.getByProformaInvoiceId(proforma.getId(), orderId).orElse(null);
		}
		//
		if (prepayment != null)
		{
			// Payment completed → Paid
			schedule.applyAndProcess(
					lcStep.getIdNotNull(),
					OrderPayScheduleLineContext.paid()
							.referenceDate(proforma.getDateInvoiced())
							.dueDate(proforma.getDueDate())
							.dueAmountActual(proforma.getGrandTotal())
							.build()
			);
		}
		else
		{
			// Payment absent, drafted, or reversed → Awaiting_Pay
			schedule.applyAndProcess(
					lcStep.getIdNotNull(),
					OrderPayScheduleLineContext.awaitingPayment()
							.referenceDate(proforma.getDateInvoiced())
							.dueDate(proforma.getDueDate())
							.dueAmountActual(proforma.getGrandTotal())
							.build()
			);
		}

		orderPayScheduleService.save(schedule);
		stampLCDateOnOrder(orderId, proforma);
	}

	private void clearLCDateOnOrder(@NonNull final OrderId orderId)
	{
		final I_C_Order order = orderDAO.getById(orderId);
		order.setLC_Date(null);
		orderDAO.save(order);
	}

	private void stampLCDateOnOrder(@NonNull final OrderId orderId, @NonNull final ProformaInvoice proformaInvoice)
	{
		final I_C_Order order = orderDAO.getById(orderId);
		order.setLC_Date(TimeUtil.asTimestamp(proformaInvoice.getDateInvoiced()));
		orderDAO.save(order);
	}
}
