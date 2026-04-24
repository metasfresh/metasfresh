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
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.service.OrderPayScheduleLCService;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;

import java.util.List;
import java.util.stream.Collectors;

@Builder
class ProformaOrderAllocateCommand
{
	private static final AdMessageKey MSG_NoLCBreakInOrder            = AdMessageKey.of("de.metas.invoice.proforma.NoLCBreakInOrder");
	private static final AdMessageKey MSG_MultipleLCBreaksUnsupported = AdMessageKey.of("de.metas.invoice.proforma.MultipleLCBreaksUnsupported");
	private static final AdMessageKey MSG_CurrencyMismatch            = AdMessageKey.of("de.metas.invoice.proforma.CurrencyMismatch");
	private static final AdMessageKey MSG_VendorMismatch              = AdMessageKey.of("de.metas.invoice.proforma.VendorMismatch");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final ProformaOrderAllocRepository proformaOrderAllocRepository;
	@NonNull private final OrderPayScheduleLCService orderPayScheduleLCService;

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

		// Re-validate all eligibility conditions — this is the API/script gate that runs even when the
		// Val Rule lookup filter is bypassed (e.g. direct REST call or Cucumber/script scenario).
		validate(invoice, order);

		final ProformaOrderAllocateRequest request = ProformaOrderAllocateRequest.builder()
				.proformaInvoiceId(proformaInvoiceId)
				.purchaseOrderId(purchaseOrderId)
				.build();

		final ProformaOrderAlloc alloc = proformaOrderAllocRepository.create(request);

		order.setLC_Date(invoice.getDateInvoiced());
		orderBL.save(order);

		orderPayScheduleLCService.recomputeLCStep(purchaseOrderId);

		return alloc;
	}

	/**
	 * Validates all four eligibility conditions for allocating a proforma invoice to a purchase order.
	 * This is the API/script gate — it runs even when the Val Rule lookup filter on the UI parameter is bypassed.
	 * <p>
	 * Checks (in order):
	 * <ol>
	 *   <li>Currency match: proforma and order must share the same currency.</li>
	 *   <li>Vendor (BPartner) match: proforma and order must have the same bill-to partner.</li>
	 *   <li>No LC break: order payment term must have at least one Letter-of-Credit break.</li>
	 *   <li>Multiple LC breaks: order payment term must have exactly one LC break (iter 2 limitation).</li>
	 * </ol>
	 *
	 * @throws AdempiereException with a translated user-facing message on any violation
	 */
	private void validate(@NonNull final I_C_Invoice invoice, @NonNull final I_C_Order order)
	{
		// Currency mismatch — {0}=proforma currency, {1}=order currency
		if (invoice.getC_Currency_ID() != order.getC_Currency_ID())
		{
			throw new AdempiereException(MSG_CurrencyMismatch,
					invoice.getC_Currency_ID(),
					order.getC_Currency_ID())
					.markAsUserValidationError();
		}

		// Vendor (BPartner) mismatch — {0}=proforma BPartner, {1}=order BPartner
		final BPartnerId invoiceBPartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final BPartnerId orderBPartnerId = orderBL.getEffectiveBillPartnerId(order);
		if (!BPartnerId.equals(invoiceBPartnerId, orderBPartnerId))
		{
			throw new AdempiereException(MSG_VendorMismatch,
					invoiceBPartnerId.getRepoId(),
					orderBPartnerId.getRepoId())
					.markAsUserValidationError();
		}

		// LC-break count — 0 breaks → reject; >1 break → reject (iter 2 limitation)
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoIdOrNull(order.getC_PaymentTerm_ID());
		if (paymentTermId == null)
		{
			throw new AdempiereException(MSG_NoLCBreakInOrder, order.getDocumentNo())
					.markAsUserValidationError();
		}

		final PaymentTerm paymentTerm = paymentTermService.getById(paymentTermId);
		final List<PaymentTermBreak> lcBreaks = paymentTerm.getSortedBreaks()
				.stream()
				.filter(PaymentTermBreak::isLetterOfCredit)
				.collect(Collectors.toList());

		if (lcBreaks.isEmpty())
		{
			throw new AdempiereException(MSG_NoLCBreakInOrder, order.getDocumentNo())
					.markAsUserValidationError();
		}
		if (lcBreaks.size() > 1)
		{
			throw new AdempiereException(MSG_MultipleLCBreaksUnsupported, order.getDocumentNo())
					.markAsUserValidationError();
		}
	}
}
