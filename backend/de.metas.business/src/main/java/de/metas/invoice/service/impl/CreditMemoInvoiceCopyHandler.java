package de.metas.invoice.service.impl;

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
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.slf4j.Logger;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.IDocCopyHandler;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceCreditContext;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Note: This class is currently instantiated and called directly from BLs in this package.<br>
 * <p>
 * IMPORTANT: this copy handler is special in that it could also complete the target invoice! Make sure that it's called after the other "generic" handlers.<br>
 * Also see the javadoc of {@link IInvoiceBL#creditInvoice(de.metas.adempiere.model.I_C_Invoice, InvoiceCreditContext)}.
 */
class CreditMemoInvoiceCopyHandler implements IDocCopyHandler<I_C_Invoice, I_C_InvoiceLine>
{
	private final InvoiceCreditContext creditCtx;
	private final BigDecimal openAmt;

	@SuppressWarnings("unused")
	private final String trxName;

	private static final transient Logger logger = LogManager.getLogger(CreditMemoInvoiceCopyHandler.class);

	public CreditMemoInvoiceCopyHandler(final Properties ctx,
			final InvoiceCreditContext creditCtx,
			final BigDecimal openAmt,
			final String trxName)
	{
		this.creditCtx = creditCtx;
		this.openAmt = openAmt;
		this.trxName = trxName;
	}

	@Override
	public void copyPreliminaryValues(final I_C_Invoice from, final I_C_Invoice to)
	{
		// do nothing. this is already done by the default copy handler
	}

	@Override
	public void copyValues(final I_C_Invoice from, final I_C_Invoice to)
	{
		final de.metas.adempiere.model.I_C_Invoice invoice = InterfaceWrapperHelper.create(from, de.metas.adempiere.model.I_C_Invoice.class);
		final de.metas.adempiere.model.I_C_Invoice creditMemo = InterfaceWrapperHelper.create(to, de.metas.adempiere.model.I_C_Invoice.class);

		if (creditCtx.isReferenceInvoice())
		{
			creditMemo.setRef_Invoice_ID(invoice.getC_Invoice_ID());
		}

		creditMemo.setIsCreditedInvoiceReinvoicable(creditCtx.isCreditedInvoiceReinvoicable()); // task 08927

		handlePartialRequests(invoice, creditMemo);

		completeAndAllocateCreditMemo(invoice, creditMemo);
	}

	@Override
	public CreditMemoInvoiceLineCopyHandler getDocLineCopyHandler()
	{
		return CreditMemoInvoiceLineCopyHandler.getInstance();
	}

	/**
	 * Task https://github.com/metasfresh/metasfresh/issues/6615
	 * When a credit memo comes after a partial allocation, we no longer do the automatic calculation per lines and taxes,
	 * but create a credit memo based on the whole qtys and prices of the invoice and let the user decide what they do next.
	 */
	private void handlePartialRequests(final de.metas.adempiere.model.I_C_Invoice invoice,
			final de.metas.adempiere.model.I_C_Invoice creditMemo)
	{
		final BigDecimal allocatedAmt = Services.get(IAllocationDAO.class).retrieveAllocatedAmt(invoice);
		if (allocatedAmt == null || allocatedAmt.signum() == 0)
		{
			// skip non-partial lines (a line is partially allocated if the allocated amount is not null or 0)
			return;
		}
		else
		{
			logger.warn("The credit memo {} should be a partial allocation of invoice {}.", creditMemo, invoice);

			return;
		}
	}

	private void completeAndAllocateCreditMemo(final de.metas.adempiere.model.I_C_Invoice invoice, final de.metas.adempiere.model.I_C_Invoice creditMemo)
	{
		if (!creditCtx.isCompleteAndAllocate())
		{
			Services.get(IDocumentBL.class).processEx(creditMemo, IDocument.ACTION_Prepare, IDocument.STATUS_InProgress);

			// nothing left to do
			return;
		}

		Services.get(IDocumentBL.class).processEx(creditMemo, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		// allocate invoice against credit memo will be done in the model validator of creditmemo complete
	}

	/**
	 * Returns the header item class, i.e. <code>I_C_Invoice</code>.
	 */
	@Override
	public Class<I_C_Invoice> getSupportedItemsClass()
	{
		return I_C_Invoice.class;
	}
}
