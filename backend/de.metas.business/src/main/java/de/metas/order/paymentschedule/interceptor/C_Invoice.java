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
}
