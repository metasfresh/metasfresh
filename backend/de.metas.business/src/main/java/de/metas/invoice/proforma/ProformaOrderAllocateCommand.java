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

package de.metas.invoice.proforma;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;

@Builder
class ProformaOrderAllocateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final ProformaOrderAllocRepository proformaOrderAllocRepository;

	@NonNull private final ProformaOrderAllocateRequest request;

	public ProformaOrderAlloc execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private ProformaOrderAlloc execute0()
	{
		final InvoiceId proformaInvoiceId = request.getProformaInvoiceId();
		final OrderId purchaseOrderId = request.getPurchaseOrderId();


		final I_C_Invoice invoice = invoiceBL.getById(proformaInvoiceId);
		Check.assume(invoiceBL.isPurchaseProforma(invoice), "Invoice should be a Purchase Proforma Invoice (APF)");

		final I_C_Order order = orderBL.getById(purchaseOrderId);
		Check.assume(!order.isSOTrx(), "Order should be a Purchase Order (PO)");

		Check.assume(!proformaOrderAllocRepository.existsByInvoiceAndOrder(proformaInvoiceId, purchaseOrderId), "Allocation shouldn't already exists");
		Check.assume(!proformaOrderAllocRepository.existsByOrder(purchaseOrderId), "Order can only be allocated to one Proforma invoice (1:1 allocation)");
		Check.assume(BPartnerId.equals(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()), orderBL.getEffectiveBillPartnerId(order)), "Invoice and Order should have the same Bill Partner");

		validatePaymentTermWithLetterOfCredit(order);

		final ProformaOrderAllocateRequest request = ProformaOrderAllocateRequest.builder()
				.proformaInvoiceId(proformaInvoiceId)
				.purchaseOrderId(purchaseOrderId)
				.build();

		final ProformaOrderAlloc alloc = proformaOrderAllocRepository.create(request);

		order.setLC_Date(invoice.getDateInvoiced());
		orderBL.save(order);

		// TODO Task 35: Recompute LC step after proforma allocation via OrderPayScheduleLCService.recomputeLCStep(orderId)
		// The authority function (Task 35) will be the only writer of LC-step amounts (DueAmt = GrandTotal × break%).

		return alloc;
	}

	private void validatePaymentTermWithLetterOfCredit(@NonNull final I_C_Order order)
	{
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoIdOrNull(order.getC_PaymentTerm_ID());
		Check.assumeNotNull(paymentTermId, "Order should have a Payment Term");

		final PaymentTerm paymentTerm = paymentTermService.getById(paymentTermId);
		Check.assume(paymentTerm.isComplex(), "Order Payment Term should be complex");

		final boolean hasLetterOfCreditBreak = paymentTerm.getSortedBreaks()
				.stream()
				.anyMatch(PaymentTermBreak::isLetterOfCredit);
		Check.assume(hasLetterOfCreditBreak, "Order Payment Term should have a Letter of Credit break");
	}
}
