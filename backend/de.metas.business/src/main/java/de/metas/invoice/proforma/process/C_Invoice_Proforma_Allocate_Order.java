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

package de.metas.invoice.proforma.process;

import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocService;
import de.metas.invoice.proforma.ProformaOrderAllocateRequest;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;

import java.util.List;
import java.util.stream.Collectors;

public class C_Invoice_Proforma_Allocate_Order extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_NoLCBreakInOrder            = AdMessageKey.of("de.metas.invoice.proforma.NoLCBreakInOrder");
	private static final AdMessageKey MSG_MultipleLCBreaksUnsupported = AdMessageKey.of("de.metas.invoice.proforma.MultipleLCBreaksUnsupported");
	private static final AdMessageKey MSG_CurrencyMismatch            = AdMessageKey.of("de.metas.invoice.proforma.CurrencyMismatch");
	private static final AdMessageKey MSG_VendorMismatch              = AdMessageKey.of("de.metas.invoice.proforma.VendorMismatch");

	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final ProformaOrderAllocService proformaOrderAllocService = SpringContextHolder.instance.getBean(ProformaOrderAllocService.class);
	@NonNull private final PaymentTermService paymentTermService = SpringContextHolder.instance.getBean(PaymentTermService.class);

	private static final String PARAM_C_Order_ID = "C_Order_ID";
	@Param(parameterName = PARAM_C_Order_ID, mandatory = true)
	private int p_C_Order_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_Invoice.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Process must be run from Invoice window");
		}

		final I_C_Invoice invoice = invoiceBL.getById(context.getSingleSelectedRecordId(InvoiceId.class));
		if (!invoiceBL.isPurchaseProforma(invoice))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Invoice must be a Purchase Proforma Invoice (APF)");
		}

		if (!invoiceBL.isCompletedOrClosed(invoice) || invoiceBL.isReversal(invoice))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot allocate reversed or voided invoice");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final InvoiceId proformaInvoiceId = InvoiceId.ofRepoId(getRecord_ID());
		final OrderId purchaseOrderId = OrderId.ofRepoIdOrNull(p_C_Order_ID);

		Check.assumeNotNull(purchaseOrderId, "Purchase Order Para should be set");

		// Belt-and-braces: validate order eligibility before delegating to the command.
		// The Val Rule already filters the lookup, but a direct API/script call can bypass it.
		validateOrderEligibility(proformaInvoiceId, purchaseOrderId);

		final ProformaOrderAllocateRequest request = ProformaOrderAllocateRequest.builder()
				.proformaInvoiceId(proformaInvoiceId)
				.purchaseOrderId(purchaseOrderId)
				.build();

		proformaOrderAllocService.allocate(request);

		return MSG_OK;
	}

	/**
	 * Validates that the given purchase order is eligible to be allocated to the given proforma invoice.
	 * Checks: currency match, vendor match, exactly one LC break in the payment term.
	 * Throws {@link AdempiereException} with a user-visible translated message on violation.
	 */
	private void validateOrderEligibility(
			@NonNull final InvoiceId proformaInvoiceId,
			@NonNull final OrderId purchaseOrderId)
	{
		final I_C_Invoice invoice = invoiceBL.getById(proformaInvoiceId);
		final I_C_Order order = orderBL.getById(purchaseOrderId);

		// Currency mismatch — {0}=proforma currency, {1}=order currency
		if (invoice.getC_Currency_ID() != order.getC_Currency_ID())
		{
			throw new AdempiereException(MSG_CurrencyMismatch,
					invoice.getC_Currency_ID(),
					order.getC_Currency_ID())
					.markAsUserValidationError();
		}

		// Vendor (BPartner) mismatch — {0}=proforma BPartner, {1}=order BPartner
		final int invoiceBPartnerId = invoice.getC_BPartner_ID();
		final int orderBPartnerId = orderBL.getEffectiveBillPartnerId(order).getRepoId();
		if (invoiceBPartnerId != orderBPartnerId)
		{
			throw new AdempiereException(MSG_VendorMismatch,
					invoiceBPartnerId,
					orderBPartnerId)
					.markAsUserValidationError();
		}

		// LC-break count — 0 breaks → reject; >1 break → reject (iter 2 limitation)
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoIdOrNull(order.getC_PaymentTerm_ID());
		if (paymentTermId != null)
		{
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
		else
		{
			throw new AdempiereException(MSG_NoLCBreakInOrder, order.getDocumentNo())
					.markAsUserValidationError();
		}
	}
}
