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

package de.metas.order.paymentschedule.interceptor;

import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.OrderPayScheduleRegularInvoiceService;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.RegularInvoice;
import de.metas.order.paymentschedule.steps.material_receipt.OrderPayScheduleMaterialReceiptStepService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Invoice.class)
@Component
@RequiredArgsConstructor
public class C_Invoice
{
	@NonNull private final OrderPayScheduleRegularInvoiceService regularInvoiceService;
	@NonNull private final OrderPayScheduleMaterialReceiptStepService materialReceiptStepService;
	@NonNull private final ProformaOrderAllocRepository proformaAllocRepo;

	/**
	 * Fires on AFTER_COMPLETE only.
	 * Creates the prepayment allocation first, then recomputes delivery steps.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onComplete(@NonNull final I_C_Invoice invoiceRecord)
	{
		final RegularInvoice regularInvoice = regularInvoiceService.fromRecordIfRegularInvoice(invoiceRecord).orElse(null);
		if (regularInvoice == null)
		{
			return;
		}

		final OrderId orderId = regularInvoice.getOrderId();
		if (!proformaAllocRepo.existsByOrder(orderId))
		{
			return;
		}
		// Allocate BEFORE recompute so that the recompute can see the new allocation
		regularInvoiceService.allocateForInvoice(regularInvoice);
		// Pass the in-memory regularInvoice so the recompute can bypass the stale DocStatus="IP" in the DB.
		// See OrderPayScheduleRegularInvoiceService#getByReceipt(MaterialReceipt, RegularInvoice) for details.
		materialReceiptStepService.recomputeDeliveryStepsAfterInvoiceCompleted(orderId, regularInvoice);
	}

	/**
	 * Fires on AFTER_REVERSECORRECT and AFTER_REVERSEACCRUAL.
	 * Allocation reversal is handled automatically by the standard cascade
	 * ({@code MAllocationHdr.reverseCorrectIt()}); only recompute delivery steps.
	 */
	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL
	})
	public void onReverse(@NonNull final I_C_Invoice invoiceRecord)
	{
		final RegularInvoice regularInvoice = regularInvoiceService.fromRecordIfRegularInvoice(invoiceRecord).orElse(null);
		if (regularInvoice == null)
		{
			return;
		}

		final OrderId orderId = regularInvoice.getOrderId();
		if (!proformaAllocRepo.existsByOrder(orderId))
		{
			return;
		}
		// Thread the in-memory reversed invoice so its DocStatus=RE (not yet saved to DB at this point)
		// is honoured during the receipt→invoice lookup — without this, the reversed invoice still
		// appears as CO in the DB and the receipt sub-row is not properly reset to PR/null.
		materialReceiptStepService.recomputeDeliveryStepsAfterInvoiceReversed(orderId, invoiceRecord);
	}

	/**
	 * Fires on AFTER_CHANGE when {@code C_Invoice.IsPaid} flips.
	 *
	 * <p>The semantic event is "the invoice's paid status changed". Both directions are
	 * covered: N→Y (a payment was allocated to the invoice) and Y→N (the allocation
	 * was reversed). The BL sub-row's {@code Status} must follow the linked invoice's
	 * paid state — {@code Awaiting_Pay} when the invoice is unpaid, {@code Paid}
	 * when it is paid. {@link OrderPayScheduleMaterialReceiptStepService#computeOrderPayScheduleStatus}
	 * reads {@code invoice.isPaid()}; we just need to call the recompute when that
	 * flag flips so the schedule catches up.
	 *
	 * <p>Why AFTER_CHANGE on {@code C_Invoice} and not AFTER_COMPLETE on
	 * {@code C_AllocationHdr}: the chain is {@code C_AllocationHdr} completion →
	 * {@code de.metas.invoice.interceptor.C_AllocationHdr.testInvoiceAllocation0}
	 * runs {@code invoiceBL.testAllocation(invoice, ...)} which updates
	 * {@code C_Invoice.IsPaid}. Hooking at the {@code IsPaid} change is one step
	 * downstream and one less enumeration (no allocation lines to walk).
	 *
	 * <p>The DocStatus=IP threading trick is not needed here — by the time AFTER_CHANGE
	 * fires, the invoice's {@code IsPaid} is already saved in the DB; the no-arg
	 * {@code recomputeDeliverySteps(orderId)} reads fresh DB state.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_Invoice.COLUMNNAME_IsPaid)
	public void onIsPaidChanged(@NonNull final I_C_Invoice invoiceRecord)
	{
		final RegularInvoice regularInvoice = regularInvoiceService.fromRecordIfRegularInvoice(invoiceRecord).orElse(null);
		if (regularInvoice == null)
		{
			return;
		}

		// Skip when the invoice is not Completed/Closed.
		// Two cases to guard:
		// (1) The invoice is being reversed/voided. AFTER_REVERSECORRECT on this
		//     invoice will fire onReverse() above which calls
		//     recomputeDeliveryStepsAfterInvoiceReversed — that variant threads
		//     the reversed invoice through so the state machine accepts the
		//     Paid → Pending transition. Letting this @ModelChange double-fire
		//     attempts Paid → Pending without that threading and the
		//     OrderPayScheduleLine state machine throws
		//     "Cannot change status from Paid to Pending"
		//     (cucumber TC #16 / TC #17 / TC #25).
		// (2) The IsPaid flip happened while the invoice is still mid-completion
		//     (DocStatus=IP). Nothing useful to recompute yet — onComplete will
		//     run shortly with the right threading.
		if (!regularInvoice.isCompletedOrClosed())
		{
			return;
		}

		final OrderId orderId = regularInvoice.getOrderId();
		if (!proformaAllocRepo.existsByOrder(orderId))
		{
			return;
		}
		materialReceiptStepService.recomputeDeliverySteps(orderId);
	}
}
