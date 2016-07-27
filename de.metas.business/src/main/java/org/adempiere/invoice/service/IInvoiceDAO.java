package org.adempiere.invoice.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_LandedCost;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;

public interface IInvoiceDAO extends ISingletonService
{

	/**
	 * 
	 * @param invoice
	 * @return
	 * @throws IllegalArgumentException if invoice is not an {@link MInvoice}
	 */
	I_C_InvoiceLine createInvoiceLine(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceLine> retrieveLines(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceLine> retrieveLines(org.compiere.model.I_C_Invoice invoice, String trxName);

	IQueryBuilder<I_C_InvoiceLine> retrieveLinesQuery(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceLine> retrieveLines(I_M_InOutLine inoutLine);

	List<I_C_LandedCost> retrieveLandedCosts(I_C_InvoiceLine invoiceLine,
			String whereClause, String trxName);

	I_C_LandedCost createLandedCost(String trxName);

	I_C_InvoiceLine createInvoiceLine(String trxName);

	Collection<MInvoiceLine> retrieveReferringLines(Properties ctx, int invoiceLineId,
			String trxName);

	/**
	 * Search by the invoice when the document number and the bpartner id are known.
	 * 
	 * @param ctx
	 * @param invoiceNo
	 * @param bPartnerID
	 * @return the I_C_Invoice object if the value was found, null otherwise
	 */
	I_C_Invoice retrieveInvoiceByInvoiceNoAndBPartnerID(Properties ctx, String invoiceNo, int bPartnerID);

	/**
	 * Gets all open invoices for the specific organization.<br>
	 * Not guaranteed iterator. Do not use if modifying the "IsPaid" column.
	 * 
	 * @param adOrg
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveOpenInvoicesByOrg(I_AD_Org adOrg);

	/**
	 * Gets invoice open amount (not paid amount) by calling {@link #retrieveOpenAmt(org.compiere.model.I_C_Invoice, boolean)} with <code>creditMemoAdjusted == true</code>. Please not that the value
	 * is:
	 * <ul>
	 * <li>relative regarding if is a sales or purchase transaction ({@link I_C_Invoice#isSOTrx()})
	 * <li>absolute regarding if is a credit memo or not
	 * </ul>
	 * 
	 * @param invoice
	 * @return open amount
	 */
	BigDecimal retrieveOpenAmt(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceTax> retrieveTaxes(org.compiere.model.I_C_Invoice invoice);

	/**
	 * Retrieves the reversal line for the given invoice line and C_Invoice_ID, using the line's <code>C_InvoiceLine.Line</code> value.
	 * 
	 * @param line
	 * @param reversalInvoiceId
	 * @return the reversal line or <code>null</code> if the reversal invoice has no line with the given <code>line</code>'s number.
	 */
	I_C_InvoiceLine retrieveReversalLine(I_C_InvoiceLine line, int reversalInvoiceId);

	/**
	 * Retrieves the invoice that has crediMemo's ID as Ref_CreditMemo.<br>
	 * Never returns <code>null</code>.
	 * This method retrieves an iterator of invoices that have this ref_creditMemo.<br>
	 * At the moment, there shouldn't be more than one, but in the future, the possibility of allocating several invoices against the same credit memo is not out of question.
	 * 
	 * @param creditMemo
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveParentInvoiceForCreditMemo(I_C_Invoice creditMemo);

	/**
	 * Retrieves the invoice that has the ID of the adjustmentCharge as Ref_AdjustmentCharge_ID.<br>
	 * Never returns <code>null</code>.
	 * This method retrieves an iterator of invoices that have this Ref_AdjustmentCharge_ID.<br>
	 * Similar with <code>org.adempiere.invoice.service.IInvoiceDAO.retrieveReversalLine(I_C_InvoiceLine, int)</code>
	 * 
	 * @param adjustmentCharge
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveParentInvoiceForAdjustmentCharge(I_C_Invoice adjustmentCharge);

	/**
	 * Retrieve all the Invoices that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either GrandTotal or TotalLines. These entries will produce 0 in posting
	 * 
	 * @param ctx
	 * @param startDate
	 * @return
	 */
	List<I_C_Invoice> retrievePostedWithoutFactAcct(Properties ctx, Date startTime);
}
