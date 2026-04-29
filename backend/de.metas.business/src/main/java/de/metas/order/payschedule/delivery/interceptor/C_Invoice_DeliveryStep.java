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

package de.metas.order.payschedule.delivery.interceptor;

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.OrderId;
import de.metas.order.payschedule.delivery.OrderPayScheduleDeliveryService;
import de.metas.order.payschedule.delivery.allocation.DeliveryPrepaymentAllocationService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Drives {@link OrderPayScheduleDeliveryService#recomputeDeliverySteps} from the
 * {@code C_Invoice} doc-action lifecycle.
 *
 * <p>Only fires for invoices that pass all four trigger filters:
 * <ol>
 *   <li>Financial: {@code IsFinancial = 'Y'} — excludes proforma invoices.
 *   <li>Purchase: {@code IsSOTrx = 'N'} — excludes sales invoices.
 *   <li>Not a credit memo (APC guard): {@code DocBaseType != 'APC'} — AP credit memos
 *       (debit notes) do not drive the delivery step.
 *   <li>Matched to at least one purchase receipt that belongs to an order with an active
 *       proforma-order allocation ({@code C_Proforma_Order_Alloc}) — dormancy guard
 *       restricts the recompute to proforma'd orders.
 * </ol>
 *
 * <p>The service is idempotent; duplicate fires produce no additional change.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Interceptor(I_C_Invoice.class)
@Component
@RequiredArgsConstructor
public class C_Invoice_DeliveryStep
{
	@NonNull private final OrderPayScheduleDeliveryService deliveryService;
	@NonNull private final ProformaOrderAllocRepository proformaAllocRepo;
	@NonNull private final DeliveryPrepaymentAllocationService allocationService;

	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// -----------------------------------------------------------------------
	// DocValidate handlers
	// -----------------------------------------------------------------------

	/**
	 * Fires on AFTER_COMPLETE only.
	 * Creates the prepayment allocation first, then recomputes delivery steps.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onComplete(@NonNull final I_C_Invoice invoice)
	{
		if (!isFinancialPurchaseNonCreditMemoInvoice(invoice))
		{
			return;
		}
		final OrderId orderId = findOrderIdViaMatchInv(invoice);
		if (orderId == null)
		{
			return;
		}
		if (!proformaAllocRepo.existsByOrder(orderId))
		{
			return;
		}
		// Allocate BEFORE recompute so that the recompute can see the new allocation
		allocationService.allocateForInvoice(invoice, orderId);
		deliveryService.recomputeDeliverySteps(orderId);
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
	public void onReverse(@NonNull final I_C_Invoice invoice)
	{
		if (!isFinancialPurchaseNonCreditMemoInvoice(invoice))
		{
			return;
		}
		final OrderId orderId = findOrderIdViaMatchInv(invoice);
		if (orderId == null)
		{
			return;
		}
		if (!proformaAllocRepo.existsByOrder(orderId))
		{
			return;
		}
		deliveryService.recomputeDeliverySteps(orderId);
	}

	// -----------------------------------------------------------------------
	// Trigger filters
	// -----------------------------------------------------------------------

	/**
	 * Returns {@code true} when the invoice is a financial, non-SOTrx, non-credit-memo
	 * purchase invoice (i.e., a regular AP vendor invoice).
	 *
	 * <ul>
	 *   <li>Sales invoices ({@code IsSOTrx=Y}) → {@code false}.
	 *   <li>Proforma invoices ({@code IsFinancial=N}) → {@code false}.
	 *   <li>APC (Accounts-Payable Credit Memo / vendor credit note) → {@code false}.
	 *   <li>Regular AP vendor invoice (API) → {@code true}.
	 * </ul>
	 *
	 * <p>Package-private for testing without Spring context.
	 */
	boolean isFinancialPurchaseNonCreditMemoInvoice(@NonNull final I_C_Invoice invoice)
	{
		// SOTrx guard: only purchase invoices
		if (invoice.isSOTrx())
		{
			return false;
		}
		// Financial guard: proforma invoices have IsFinancial='N'
		if (!invoice.isFinancial())
		{
			return false;
		}
		// APC guard: purchase credit memos do not drive the delivery step
		final InvoiceDocBaseType docBaseType = invoiceBL.getInvoiceDocBaseType(invoice);
		if (docBaseType.isVendorCreditMemo())
		{
			return false;
		}
		return true;
	}

	/**
	 * Looks up the single {@link OrderId} linked to all matched receipts of the invoice,
	 * via {@code M_MatchInv → M_InOutLine → M_InOut.C_Order_ID}.
	 *
	 * <p>Returns {@code null} when:
	 * <ul>
	 *   <li>No matched receipts exist.
	 *   <li>Matched receipts belong to multiple orders (ambiguous).
	 *   <li>No matched receipt has a positive {@code C_Order_ID}.
	 * </ul>
	 *
	 * <p>Package-private for testing without Spring context.
	 */
	@Nullable
	OrderId findOrderIdViaMatchInv(@NonNull final I_C_Invoice invoice)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		// Step 1: collect all M_InOut_IDs matched to this invoice
		final List<I_M_MatchInv> matchRows = queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		if (matchRows.isEmpty())
		{
			return null;
		}

		// Step 2: collect distinct C_Order_IDs from the matched receipts
		final Set<Integer> orderIds = new HashSet<>();
		for (final I_M_MatchInv m : matchRows)
		{
			final int inOutId = m.getM_InOut_ID();
			if (inOutId <= 0)
			{
				continue;
			}
			// Load via IQueryBL to get C_Order_ID (avoid loading the whole document)
			final I_M_InOut inout = queryBL.createQueryBuilder(I_M_InOut.class)
					.addEqualsFilter(I_M_InOut.COLUMNNAME_M_InOut_ID, inOutId)
					.create()
					.firstOnly(I_M_InOut.class);
			if (inout == null || inout.getC_Order_ID() <= 0)
			{
				continue;
			}
			orderIds.add(inout.getC_Order_ID());
		}

		if (orderIds.size() != 1)
		{
			// No order or ambiguous (multiple orders) — skip
			return null;
		}

		return OrderId.ofRepoId(orderIds.iterator().next());
	}
}
