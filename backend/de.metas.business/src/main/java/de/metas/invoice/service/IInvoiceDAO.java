package de.metas.invoice.service;

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
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_LandedCost;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInvoice;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IInvoiceDAO extends ISingletonService
{
	void save(org.compiere.model.I_C_Invoice invoice);

	void delete(org.compiere.model.I_C_Invoice invoice);

	void save(org.compiere.model.I_C_InvoiceLine invoiceLine);

	/**
	 * @throws IllegalArgumentException if invoice is not an {@link MInvoice}
	 */
	I_C_InvoiceLine createInvoiceLine(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceLine> retrieveLines(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceLine> retrieveLines(@NonNull InvoiceId invoiceId);

	List<I_C_InvoiceLine> retrieveLines(org.compiere.model.I_C_Invoice invoice, String trxName);

	IQueryBuilder<I_C_InvoiceLine> retrieveLinesQuery(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceLine> retrieveLines(I_M_InOutLine inoutLine);

	List<I_C_LandedCost> retrieveLandedCosts(I_C_InvoiceLine invoiceLine,
			String whereClause, String trxName);

	I_C_LandedCost createLandedCost(String trxName);

	I_C_InvoiceLine createInvoiceLine(String trxName);

	List<I_C_InvoiceLine> retrieveReferringLines(@NonNull InvoiceLineId invoiceLineId);

	/**
	 * Search by the invoice when the document number and the bpartner id are known.
	 *
	 * @param ctx
	 * @param invoiceNo
	 * @param bPartnerID
	 * @return the I_C_Invoice object if the value was found, null otherwise
	 */
	I_C_Invoice retrieveInvoiceByInvoiceNoAndBPartnerID(Properties ctx, String invoiceNo, BPartnerId bpartnerId);

	/**
	 * Gets all open invoices for the specific organization.<br>
	 * Not guaranteed iterator. Do not use if modifying the "IsPaid" column.
	 *
	 * @param adOrg
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveOpenInvoicesByOrg(I_AD_Org adOrg);

	/**
	 * Gets invoice open amount (not paid amount) by calling {@link IAllocationDAO#retrieveOpenAmt(org.compiere.model.I_C_Invoice, boolean)} with <code>creditMemoAdjusted == true</code>. Please note that the value
	 * is:
	 * <ul>
	 * <li>relative regarding if is a sales or purchase transaction ({@link I_C_Invoice#isSOTrx()})
	 * <li>absolute regarding if is a credit memo or not
	 * </ul>
	 *
	 * @return open amount
	 */
	Amount retrieveOpenAmt(InvoiceId invoiceId);

	/**
	 * Gets invoice open amount (not paid amount) by calling {@link IAllocationDAO#retrieveOpenAmt(org.compiere.model.I_C_Invoice, boolean)} with <code>creditMemoAdjusted == true</code>. Please note that the value
	 * is:
	 * <ul>
	 * <li>relative regarding if is a sales or purchase transaction ({@link I_C_Invoice#isSOTrx()})
	 * <li>absolute regarding if is a credit memo or not
	 * </ul>
	 *
	 * @return open amount
	 * @deprecated Please use {@link #retrieveOpenAmt(InvoiceId)}
	 */
	@Deprecated
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
	 * Retrieve all the Invoices that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either GrandTotal or TotalLines. These entries will produce 0 in posting
	 *
	 * @param ctx
	 * @param startDate
	 * @return
	 */
	List<I_C_Invoice> retrievePostedWithoutFactAcct(Properties ctx, Date startTime);

	/**
	 * Retrieve all Adjustment Charge entries that were created based on the given invoice
	 *
	 * @param invoice
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveAdjustmentChargesForInvoice(I_C_Invoice invoice);

	/**
	 * Retrieve all Credit Memo entries that were created based on the given invoice
	 *
	 * @param invoice
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveCreditMemosForInvoice(I_C_Invoice invoice);

	org.compiere.model.I_C_Invoice getByIdInTrx(InvoiceId invoiceId);

	List<org.compiere.model.I_C_Invoice> getByIdsInTrx(Collection<InvoiceId> invoiceIds);

	List<org.compiere.model.I_C_Invoice> getByIdsOutOfTrx(Collection<InvoiceId> invoiceIds);

	Stream<InvoiceId> streamInvoiceIdsByBPartnerId(BPartnerId bpartnerId);

	ImmutableMap<InvoiceId, String> getDocumentNosByInvoiceIds(@NonNull Collection<InvoiceId> invoiceIds);

	org.compiere.model.I_C_InvoiceLine getByIdOutOfTrx(InvoiceLineId invoiceLineId);

	boolean hasCompletedInvoicesReferencing(InvoiceId invoiceId);
}
