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

package de.metas.order.paymentschedule.service;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAlloc;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.OrderPayScheduleLine;
import de.metas.order.paymentschedule.OrderPayScheduleStatus;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Authority for the Letter-of-Credit pay-schedule step.
 * <p>
 * {@link #recomputeLCStep(OrderId)} is the <b>sole writer</b> of the LC step's {@code Status},
 * {@code DueAmt_Actual}, and of the order's {@code LC_Date}. It is idempotent and total:
 * calling it twice yields the same result; it handles every state of (allocation × payment)
 * without throwing (except for unsupported topologies, see below).
 * <p>
 * The function deliberately bypasses
 * {@link de.metas.order.paymentschedule.OrderPayScheduleLine#applyAndProcess applyAndProcess}
 * — that method enforces forward-only transitions {@code Pending → Awaiting_Pay → Paid}, but
 * this service must also write the reverse transitions {@code Paid → Awaiting_Pay} (on payment
 * reversal) and {@code Awaiting_Pay → Pending} (on proforma de-allocation). Therefore it uses
 * the raw {@link OrderPayScheduleLine#setStatus setStatus} setter directly.
 * <p>
 * LC step is identified by {@link de.metas.payment.paymentterm.ReferenceDateType#LetterOfCreditDate}.
 * Orders with 0 LC steps are a no-op (normally rejected upstream by the allocation preconditions).
 * Orders with multiple LC steps are iteration-3+ scope
 * (https://github.com/metasfresh/me03/issues/29369) and throw a translated exception.
 */
@Service
@RequiredArgsConstructor
public class OrderPayScheduleLCService
{
	private static final AdMessageKey MSG_MultipleLCBreaksUnsupported =
			AdMessageKey.of("de.metas.invoice.proforma.MultipleLCBreaksUnsupported");

	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final ProformaOrderAllocRepository proformaAllocRepo;
	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	/**
	 * Recomputes the LC-step status for the given order.
	 *
	 * <pre>
	 * alloc=NONE                            → Status=Pending,      DueAmt_Actual=NULL,            LC_Date=NULL
	 * alloc=PRESENT, payment=NONE/DRAFTED   → Status=Awaiting_Pay, DueAmt_Actual=proforma.GrandTotal, LC_Date=invoice.DateInvoiced
	 * alloc=PRESENT, payment=COMPLETED      → Status=Paid,          DueAmt_Actual=proforma.GrandTotal, LC_Date=invoice.DateInvoiced
	 * alloc=PRESENT, payment=REVERSED       → Status=Awaiting_Pay  (same as NONE/DRAFTED — reversed payment no longer counts)
	 * </pre>
	 */
	public void recomputeLCStep(@NonNull final OrderId orderId)
	{
		final Optional<OrderPaySchedule> scheduleOpt = orderPayScheduleService.getByOrderId(orderId);
		if (!scheduleOpt.isPresent())
		{
			return;  // no pay-schedule at all — no-op
		}
		final OrderPaySchedule schedule = scheduleOpt.get();

		final ImmutableList<OrderPayScheduleLine> lcLines = schedule.getLCLines();

		if (lcLines.isEmpty())
		{
			return;  // no LC break in payment term — no-op
		}
		if (lcLines.size() > 1)
		{
			throw new AdempiereException(MSG_MultipleLCBreaksUnsupported, orderId);
		}

		final OrderPayScheduleLine lcStep = lcLines.get(0);

		final ProformaOrderAlloc allocation = proformaAllocRepo.findActiveByOrderId(orderId).orElse(null);

		if (allocation == null)
		{
			// No proforma allocation — reset to Pending
			lcStep.setStatus(OrderPayScheduleStatus.Pending);
			lcStep.setDueAmtActual(null);
			lcStep.setDueDate(SENTINEL_NO_LC_DATE);
			orderPayScheduleService.save(schedule);
			clearLCDateOnOrder(orderId);
			return;
		}

		// Proforma allocation is present: determine payment state
		final InvoiceId proformaInvoiceId = allocation.getInvoiceId();
		final I_C_Invoice proforma = invoiceBL.getById(proformaInvoiceId);
		final I_C_Payment completedPayment = paymentDAO.findCompletedOrClosedByProformaInvoiceId(proformaInvoiceId).orElse(null);

		final CurrencyId proformaCurrencyId = CurrencyId.ofRepoId(proforma.getC_Currency_ID());
		final Money proformaActualAmount = Money.of(proforma.getGrandTotal(), proformaCurrencyId);
		final LocalDate proformaDate = TimeUtil.asLocalDate(proforma.getDateInvoiced());
		final LocalDate lcStepDueDate = proformaDate.plusDays(lcStep.getOffsetDays());

		if (completedPayment != null)
		{
			// Payment completed → Paid
			lcStep.setStatus(OrderPayScheduleStatus.Paid);
			lcStep.setDueAmtActual(proformaActualAmount);
			lcStep.setDueDate(lcStepDueDate);
			orderPayScheduleService.save(schedule);
			stampLCDateOnOrder(orderId, proforma);
		}
		else
		{
			// Payment absent, drafted, or reversed → Awaiting_Pay
			lcStep.setStatus(OrderPayScheduleStatus.Awaiting_Pay);
			lcStep.setDueAmtActual(proformaActualAmount);
			lcStep.setDueDate(lcStepDueDate);
			orderPayScheduleService.save(schedule);
			stampLCDateOnOrder(orderId, proforma);
		}
	}

	/**
	 * Sentinel value written to {@code C_OrderPaySchedule.DueDate} when the LC step has no allocation
	 * (and therefore no real LC_Date to derive its due date from). Mirrors what {@code OrderPayScheduleService}
	 * uses on order completion. Comparing this date with a realistic PayDate keeps the LC line outside an
	 * "OnlyDue" pay-selection result.
	 */
	private static final LocalDate SENTINEL_NO_LC_DATE = LocalDate.of(9999, 1, 1);

	private void clearLCDateOnOrder(@NonNull final OrderId orderId)
	{
		final I_C_Order order = orderDAO.getById(orderId);
		order.setLC_Date(null);
		orderDAO.save(order);
	}

	private void stampLCDateOnOrder(@NonNull final OrderId orderId, @NonNull final I_C_Invoice proformaInvoice)
	{
		final I_C_Order order = orderDAO.getById(orderId);
		order.setLC_Date(proformaInvoice.getDateInvoiced());
		orderDAO.save(order);
	}
}
