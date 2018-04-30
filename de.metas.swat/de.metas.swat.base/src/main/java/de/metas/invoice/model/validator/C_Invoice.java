package de.metas.invoice.model.validator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.engine.IDocumentBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;

@Interceptor(I_C_Invoice.class)
public class C_Invoice
{
	@Init
	public void setupCallouts()
	{
		// Setup callout C_Invoice
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.invoice.callout.C_Invoice());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Invoice.COLUMNNAME_C_BPartner_ID, I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID, I_C_Invoice.COLUMNNAME_AD_User_ID })
	public void updateBPartnerAddress(final I_C_Invoice doc)
	{
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(doc);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }
	// exclude columns which are not relevant if they change
			, ignoreColumnsChanged = {
					I_C_Invoice.COLUMNNAME_IsPaid
			})
	public void updateIsReadOnly(final I_C_Invoice invoice)
	{
		Services.get(IInvoiceBL.class).updateInvoiceLineIsReadOnlyFlags(invoice);
	}

	/**
	 * 07634: Remove lines of products which are not in the invoice's price list / version.
	 *
	 * @param invoice
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Invoice.COLUMNNAME_M_PriceList_ID })
	public void removeMaterialLinesNotCorrespondingToPriceList(final I_C_Invoice invoice)
	{
		Date invoiceDate = invoice.getDateInvoiced();
		if (invoiceDate == null)
		{
			invoiceDate = SystemTime.asDate();
		}

		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it
		final I_M_PriceList_Version priceListVersion = priceListDAO
				.retrievePriceListVersionOrNull(invoice.getM_PriceList(), invoiceDate, processedPLVFiltering); // can be null

		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);

		final List<I_C_InvoiceLine> invoiceLines = Services.get(IInvoiceDAO.class).retrieveLines(invoice, trxName);
		for (final I_C_InvoiceLine invoiceLine : invoiceLines)
		{
			if (!ProductPrices.hasMainProductPrice(priceListVersion, invoiceLine.getM_Product_ID()))
			{
				InterfaceWrapperHelper.delete(invoiceLine);
			}
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void onInvoiceReversal(final I_C_Invoice invoice)
	{
		Services.get(IInvoiceBL.class).handleReversalForInvoice(invoice);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setIsDiscountPrinted(final I_C_Invoice invoice)
	{
		// do nothing in case of PO invoice
		if (!invoice.isSOTrx())
		{
			return;
		}

		final boolean isDiscountPrinted;

		// in case the invoice is linked to an order, set the IsDiscountPrinted from there
		final I_C_Order order = invoice.getC_Order();

		if (order != null && order.getC_Order_ID() > 0)
		{
			isDiscountPrinted = order.isDiscountPrinted();
		}
		else
		{
			// in case the invoice is not linked to an order, take the value from the partner

			final I_C_BPartner partner = invoice.getC_BPartner();

			isDiscountPrinted = partner.isDiscountPrinted();
		}

		invoice.setIsDiscountPrinted(isDiscountPrinted);
	}

	/**
	 * Mark invoice as paid if the grand total/open amount is 0
	 *
	 * @param invoice
	 * @task 09489
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void markAsPaid(final I_C_Invoice invoice)
	{
		// services
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final boolean ignoreProcessed = true; // need to ignoreProcessed, because right now, PRocessed not yet set to true by the engine.
		invoiceBL.testAllocation(invoice, ignoreProcessed);
	}

	/**
	 * Allocate the credit memo against it's parent invoices.
	 *
	 * Note: ATM, there should only be one parent invoice for a credit memo, but it's possible to have more in the future.
	 *
	 * @param creditMemo
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void allocateInvoiceAgainstCreditMemo(final I_C_Invoice creditMemo)
	{
		// services
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		// final IInvoiceReferenceDAO invoiceReferenceDAO = Services.get(IInvoiceReferenceDAO.class);
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

		final boolean isCreditMemo = invoiceBL.isCreditMemo(creditMemo);

		if (!isCreditMemo)
		{
			// nothing to do
			return;
		}

		// The amount from the credit memo to be allocated to parent invoices
		final BigDecimal creditMemoLeft = creditMemo.getGrandTotal();

		// the parent invoice might be null if the credit memo was created manually
		if (creditMemo.getRef_Invoice_ID() > 0)
		{
			final I_C_Invoice parentInvoice = InterfaceWrapperHelper.create(creditMemo.getRef_Invoice(), I_C_Invoice.class);
			final BigDecimal invoiceOpenAmt = allocationDAO.retrieveOpenAmt(parentInvoice,
					false); // creditMemoAdjusted = false

			final BigDecimal amtToAllocate = invoiceOpenAmt.min(creditMemoLeft);

			// Allocate the minimum between parent invoice open amt and what is left of the creditMemo's grand Total
			invoiceBL.allocateCreditMemo(parentInvoice, creditMemo, amtToAllocate);
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_DELETE
	})
	public void onDeleteInvoice_DeleteLines(final I_C_Invoice invoice)
	{
		// services
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		// ONLY delete lines for status Draft or In Progress
		final boolean isDraftOrInProgress = Services.get(IDocumentBL.class).issDocumentDraftedOrInProgress(invoice);

		if (!isDraftOrInProgress)
		{
			return;
		}

		// task 09026. Do not touch other invoices because it was not yet required.
		// ONLY delete lines for credit memo or adjustment charge

		final boolean isAdjustmentCharge = invoiceBL.isAdjustmentCharge(invoice);

		if (isAdjustmentCharge)
		{
			deleteInvoiceLines(invoice);
			return;
		}

		final boolean isCreditMemo = invoiceBL.isCreditMemo(invoice);

		if (isCreditMemo)
		{
			deleteInvoiceLines(invoice);
			return;
		}

	}

	/**
	 * task 09026
	 * We need to delete the Invoice Lines before deleting the Invoice itself.
	 * This is not a common thing to be done, therefore I will leave this method only here, as private (not in the DAO class).
	 * Currently, it shall only happen in case of uncompleted invoices that are adjustment charges or credit memos.
	 *
	 * @param invoice
	 */
	private void deleteInvoiceLines(final I_C_Invoice invoice)
	{
		final List<I_C_InvoiceLine> lines = Services.get(IInvoiceDAO.class).retrieveLines(invoice);

		for (final I_C_InvoiceLine line : lines)
		{
			InterfaceWrapperHelper.delete(line);
		}
	}
}
