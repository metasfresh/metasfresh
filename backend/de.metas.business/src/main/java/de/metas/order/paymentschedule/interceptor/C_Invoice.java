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

import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.OrderPayScheduleRegularInvoiceService;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.RegularInvoice;
import de.metas.order.paymentschedule.steps.material_receipt.OrderPayScheduleMaterialReceiptStepService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
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
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

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

		// Skip when the invoice is being reversed or not yet Completed/Closed
		// at the time we see it. This is a fast preliminary check — we re-verify
		// with a fresh DB read inside the after-commit block below.
		if (!regularInvoice.isCompletedOrClosed())
		{
			return;
		}

		final OrderId orderId = regularInvoice.getOrderId();
		if (!proformaAllocRepo.existsByOrder(orderId))
		{
			return;
		}

		final InvoiceId invoiceId = regularInvoice.getId();

		// Defer the actual recompute via runAfterCommit so that by the time it
		// runs, MInvoice.reverseCorrectIt() (when applicable) has fully completed
		// including the Reversal_ID writes and DocStatus → RE update.
		//
		// Why the deferral is needed (cucumber TC #16 / #17 / #25):
		//   MInvoice.reverseCorrectIt() runs in this order:
		//     1) AllocationHdr.reverseCorrectIt() — reverses M_MatchInv +
		//        C_AllocationHdr → triggers the existing
		//        de.metas.invoice.interceptor.C_AllocationHdr after-commit hook
		//        which calls invoiceBL.testAllocation(invoice) — that flips
		//        C_Invoice.IsPaid Y→N and fires THIS @ModelChange.
		//     2) Lines 1412 / 1455: setReversal_ID on counterpart and original
		//        invoice; DocStatus → RE.
		//
		//   At step (1) the invoice's Reversal_ID is still 0 and DocStatus is
		//   still 'CO' on the in-memory `invoiceRecord`, so a same-stack-frame
		//   check on Reversal_ID can't tell us "this invoice is mid-reversal".
		//   Worse, M_MatchInv has already been reversed by this point, so the
		//   no-arg recomputeDeliverySteps(orderId) lookup via getByReceipt
		//   finds no invoice for receipts that previously matched → status
		//   computed as Pending → OrderPayScheduleLine.applyAndProcess line 134
		//   rejects the direct Paid → Pending transition.
		//
		//   By contrast, in the after-commit block, the OUTERMOST transaction
		//   (the one driving the MInvoice.reverseCorrectIt() call) has fully
		//   committed — meaning steps (1) and (2) are both visible in the DB.
		//   A fresh DB read of the invoice then carries the final state, and
		//   our Reversal_ID guard reliably detects the reversal scenario.
		trxManager.runAfterCommit(() -> {
			final I_C_Invoice freshInvoice = invoiceBL.getById(invoiceId);

			// If the invoice is reversed (or voided), the AFTER_REVERSECORRECT
			// path via onReverse() above has already done the right thing using
			// recomputeDeliveryStepsAfterInvoiceReversed which threads the
			// reversed invoice through. Don't double-recompute.
			if (freshInvoice.getReversal_ID() > 0)
			{
				return;
			}

			// Re-check DocStatus from the fresh DB record; the in-memory record
			// from the @ModelChange may have been stale.
			final String docStatus = freshInvoice.getDocStatus();
			if (!"CO".equals(docStatus) && !"CL".equals(docStatus))
			{
				return;
			}

			// Plain payment-only reversal (invoice itself stays CO, only the
			// payment-side allocation was reversed → invoice.IsPaid flipped
			// Y→N): this is the case we WANT to handle — recompute the BL row
			// so it transitions Paid → Awaiting_Pay. Also the regular N→Y case
			// from a fresh payment.
			materialReceiptStepService.recomputeDeliverySteps(orderId);
		});
	}
}
