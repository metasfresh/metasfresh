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

import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.OrderPayScheduleLine;
import de.metas.order.paymentschedule.OrderPayScheduleLineContext;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Authority for the Letter-of-Credit pay-schedule step.
 * <p>
 * {@link #recomputeLCStep(OrderId)} is the <b>sole writer</b> of the LC step's {@code Status},
 * {@code DueAmt_Actual}, and of the order's {@code LC_Date}. It is idempotent and total:
 * calling it twice yields the same result; it handles every state of (allocation × payment)
 * without throwing (except for unsupported topologies, see below).
 * <p>
 * LC step is identified by {@link de.metas.payment.paymentterm.ReferenceDateType#LetterOfCreditDate}.
 * Orders with 0 LC steps are a no-op (normally rejected upstream by the allocation preconditions).
 */
@Service
@RequiredArgsConstructor
public class OrderPayScheduleLCService
{
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
		recomputeLCStep(orderId, null);
	}

	/**
	 * Variant for invocation from {@code @DocValidate(TIMING_AFTER_COMPLETE)} on the proforma
	 * payment. The validator fires before the framework commits {@code DocStatus="CO"} on the
	 * completing payment, so the in-memory model still reads {@code DocStatus="IP"} and a fresh
	 * {@link IPaymentDAO#findCompletedOrClosedByProformaInvoiceId} cannot see it. Passing the
	 * in-flight payment lets the service recognize it without depending on the not-yet-persisted
	 * DB state.
	 *
	 * <p>For payment reversal flows ({@code TIMING_AFTER_REVERSECORRECT} /
	 * {@code TIMING_AFTER_REVERSEACCRUAL}), call the single-arg {@link #recomputeLCStep(OrderId)}
	 * — by that timing the reversed payment is persisted with {@code DocStatus="RE"} and the DAO
	 * query correctly returns no completed payment.
	 *
	 * @implNote This overload must only be called from {@code @DocValidate(TIMING_AFTER_COMPLETE)}.
	 * Calling it from any other context bypasses the DAO query that is the canonical
	 * authority for LC-step state and silently trusts the caller's payment as
	 * "completing". Use the single-arg {@link #recomputeLCStep(OrderId)} for every
	 * other trigger.
	 */
	public void recomputeLCStepAfterPaymentCompleted(@NonNull final OrderId orderId, @NonNull final I_C_Payment completingPayment)
	{
		recomputeLCStep(orderId, completingPayment);
	}

	private void recomputeLCStep(@NonNull final OrderId orderId, @Nullable final I_C_Payment trustedCompletingPayment)
	{
		final OrderPaySchedule schedule = orderPayScheduleService.getByOrderId(orderId).orElse(null);
		if (schedule == null)
		{
			return;  // no pay-schedule at all — no-op
		}

		final OrderPayScheduleLine lcStep = schedule.getSingleLCLine().orElse(null);
		if (lcStep == null)
		{
			return;  // no LC break in payment term — no-op
		}

		final InvoiceId proformaInvoiceId = proformaAllocRepo.findProformaInvoiceIdByOrderId(orderId).orElse(null);
		if (proformaInvoiceId == null)
		{
			// No proforma allocation — reset to Pending
			schedule.markAsPending(lcStep.getId());
			orderPayScheduleService.save(schedule);
			clearLCDateOnOrder(orderId);
			return;
		}

		// Proforma allocation is present: determine payment state
		final ProformaInvoice proforma = getProformaInvoiceById(proformaInvoiceId);
		final Prepayment prepayment = getPrepayment(proformaInvoiceId, trustedCompletingPayment).orElse(null);

		// final LocalDate lcStepDueDate = proforma.getDateInvoiced().plusDays(lcStep.getOffsetDays());

		if (prepayment != null)
		{
			// Payment completed → Paid
			schedule.applyAndProcess(lcStep.getId(), OrderPayScheduleLineContext.paid(proforma.getDueDate(), proforma.getGrandTotal()));
			orderPayScheduleService.save(schedule);
			stampLCDateOnOrder(orderId, proforma);
		}
		else
		{
			// Payment absent, drafted, or reversed → Awaiting_Pay
			schedule.markAsAwaitingPayment(lcStep.getId(), proforma.getGrandTotal());
			orderPayScheduleService.save(schedule);
			stampLCDateOnOrder(orderId, proforma);
		}
	}

	@NonNull
	private ProformaInvoice getProformaInvoiceById(final InvoiceId proformaInvoiceId)
	{
		final I_C_Invoice proforma = invoiceBL.getById(proformaInvoiceId);
		final CurrencyId proformaCurrencyId = CurrencyId.ofRepoId(proforma.getC_Currency_ID());

		return ProformaInvoice.builder()
				.id(InvoiceId.ofRepoId(proforma.getC_Invoice_ID()))
				.grandTotal(Money.of(proforma.getGrandTotal(), proformaCurrencyId))
				.dateInvoiced(TimeUtil.asLocalDate(proforma.getDateInvoiced()))
				.dueDate(Objects.requireNonNull(TimeUtil.asLocalDate(proforma.getDueDate())))
				.build();
	}

	@NonNull
	private @NotNull Optional<Prepayment> getPrepayment(
			@NonNull final InvoiceId proformaInvoiceId,
			@Nullable final I_C_Payment trustedCompletingPayment)
	{
		I_C_Payment completedPayment = paymentDAO.findCompletedOrClosedByProformaInvoiceId(proformaInvoiceId).orElse(null);

		// If the DAO didn't see the in-flight trigger payment (because TIMING_AFTER_COMPLETE
		// fires *before* the caller's setDocStatus("CO") + saveEx — see MPayment.completeIt),
		// trust the trigger and treat it as the completed payment.
		if (completedPayment == null
				&& trustedCompletingPayment != null
				&& trustedCompletingPayment.getProforma_Invoice_ID() == proformaInvoiceId.getRepoId())
		{
			completedPayment = trustedCompletingPayment;
		}

		if (completedPayment == null) {return Optional.empty();}

		return Optional.of(
				Prepayment.builder()
						.build()
		);
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
