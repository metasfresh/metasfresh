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
import de.metas.order.paymentschedule.steps.letter_of_credit.OrderPayScheduleLCStepService;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
class ProformaOrderAllocateCommand
{
	private static final AdMessageKey MSG_MultipleLCBreaksUnsupported      = AdMessageKey.of("de.metas.invoice.proforma.MultipleLCBreaksUnsupported");
	private static final AdMessageKey MSG_MultipleAdvanceBreaksUnsupported = AdMessageKey.of("de.metas.invoice.proforma.MultipleAdvanceBreaksUnsupported");
	private static final AdMessageKey MSG_CurrencyMismatch                 = AdMessageKey.of("de.metas.invoice.proforma.CurrencyMismatch");
	private static final AdMessageKey MSG_VendorMismatch                   = AdMessageKey.of("de.metas.invoice.proforma.VendorMismatch");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final ProformaOrderAllocRepository proformaOrderAllocRepository;
	@NonNull private final OrderPayScheduleLCStepService orderPayScheduleLCStepService;

	@NonNull private final InvoiceId proformaInvoiceId;
	@NonNull private final OrderId purchaseOrderId;

	public ProformaOrderAlloc execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private ProformaOrderAlloc execute0()
	{
		final I_C_Invoice invoice = invoiceBL.getById(proformaInvoiceId);
		Check.assume(invoiceBL.isPurchaseProforma(invoice), "Invoice should be a Purchase Proforma Invoice (APF)");

		final I_C_Order order = orderBL.getById(purchaseOrderId);
		Check.assume(!order.isSOTrx(), "Order should be a Purchase Order (PO)");

		Check.assume(!proformaOrderAllocRepository.existsByInvoiceAndOrder(proformaInvoiceId, purchaseOrderId), "Allocation shouldn't already exists");
		Check.assume(!proformaOrderAllocRepository.existsByOrder(purchaseOrderId), "Order can only be allocated to one Proforma invoice (1:1 allocation)");

		// Re-validate all eligibility conditions — this is the API/script gate that runs even when the
		// Val Rule lookup filter is bypassed (e.g. direct REST call or Cucumber/script scenario).
		validate(invoice, order);

		final ProformaOrderAlloc alloc = proformaOrderAllocRepository.create(proformaInvoiceId, purchaseOrderId);

		// Delegate LC_Date stamping to the authority function — recomputeLCStep is the sole writer of LC_Date.
		orderPayScheduleLCStepService.recomputeLCStep(purchaseOrderId);

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
	 *   <li>Multiple LC breaks: order payment term must have at most one LC break (only one LC break per term is currently supported).</li>
	 *   <li>Multiple advance breaks: a payment term with no LC break must have at most one advance
	 *       (non-material-receipt) break.</li>
	 * </ol>
	 * <p>
	 * Note on the prepaid target: the step a proforma payment settles is the Letter-of-Credit or the
	 * order-date (advance) break (see {@link de.metas.order.paymentschedule.core.OrderPayScheduleLine#isPrepaidLine()}).
	 * A term whose only non-material-receipt break is an invoice-date break has no prepaid step: allocation
	 * still records the proforma↔order link, but the proforma payment marks no pay-schedule line paid, since
	 * an invoice-date break is a regular post-invoice term rather than an up-front advance.
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

		// LC-break count — >1 LC break → reject (only one LC break per term is currently supported); a term
		// with no LC break is allowed, as long as it does not have more than one advance (non-material-receipt) break.
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoIdOrNull(order.getC_PaymentTerm_ID());
		final List<PaymentTermBreak> breaks = paymentTermId != null
				? paymentTermService.getById(paymentTermId).getSortedBreaks()
				: Collections.emptyList();

		final List<PaymentTermBreak> lcBreaks = breaks
				.stream()
				.filter(PaymentTermBreak::isLetterOfCredit)
				.collect(Collectors.toList());

		if (lcBreaks.size() > 1)
		{
			throw new AdempiereException(MSG_MultipleLCBreaksUnsupported, order.getDocumentNo())
					.markAsUserValidationError();
		}
		else if (lcBreaks.isEmpty())
		{
			final List<PaymentTermBreak> advanceBreaks = breaks
					.stream()
					.filter(paymentTermBreak -> !paymentTermBreak.getReferenceDateType().isMaterialReceiptDate())
					.collect(Collectors.toList());

			if (advanceBreaks.size() > 1)
			{
				throw new AdempiereException(MSG_MultipleAdvanceBreaksUnsupported, order.getDocumentNo())
						.markAsUserValidationError();
			}
		}
	}
}
