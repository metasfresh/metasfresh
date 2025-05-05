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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.document.DocBaseAndSubType;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.InvoiceQuery;
import de.metas.invoice.InvoiceTax;
import de.metas.invoice.UnpaidInvoiceQuery;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import de.metas.util.time.InstantInterval;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_LandedCost;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInvoice;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public interface IInvoiceDAO extends ISingletonService
{
	void save(org.compiere.model.I_C_Invoice invoice);

	void delete(org.compiere.model.I_C_Invoice invoice);

	void save(org.compiere.model.I_C_InvoiceLine invoiceLine);

	Map<OrderId, InvoiceId> getInvoiceIdsForOrderIds(List<OrderId> orderIds);

	List<I_C_Invoice> getInvoicesForOrderIds(List<OrderId> orderIds);

	/**
	 * @throws IllegalArgumentException if invoice is not an {@link MInvoice}
	 */
	I_C_InvoiceLine createInvoiceLine(org.compiere.model.I_C_Invoice invoice);

	I_C_InvoiceLine retrieveLineById(InvoiceAndLineId invoiceAndLineId);

	List<I_C_InvoiceLine> retrieveLines(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceLine> retrieveLines(@NonNull InvoiceId invoiceId);

	Amount retrieveOpenAmt(org.compiere.model.I_C_Invoice invoice, boolean creditMemoAdjusted);

	List<I_C_InvoiceLine> retrieveLines(org.compiere.model.I_C_Invoice invoice, String trxName);

	IQueryBuilder<I_C_InvoiceLine> retrieveLinesQuery(org.compiere.model.I_C_Invoice invoice);

	List<I_C_InvoiceLine> retrieveLines(I_M_InOutLine inoutLine);

	List<I_C_LandedCost> retrieveLandedCosts(I_C_InvoiceLine invoiceLine,
											 String whereClause, String trxName);

	I_C_LandedCost createLandedCost(String trxName);

	I_C_InvoiceLine createInvoiceLine(String trxName);

	ImmutableSet<InvoiceId> retainReferencingCompletedInvoices(Collection<InvoiceId> invoiceIds, DocBaseAndSubType targetDocType);

	List<I_C_InvoiceLine> retrieveReferringLines(@NonNull InvoiceAndLineId invoiceAndLineId);

	I_C_InvoiceLine retrieveLineById(@NonNull InvoiceLineId invoiceLineId);

	/**
	 * Search by the invoice when the document number and the bpartner id are known.
	 *
	 * @return the I_C_Invoice object if the value was found, null otherwise
	 */
	I_C_Invoice retrieveInvoiceByInvoiceNoAndBPartnerID(Properties ctx, String invoiceNo, BPartnerId bpartnerId);

	/**
	 * Gets all open invoices for the specific organization.<br>
	 * Not guaranteed iterator. Do not use if modifying the "IsPaid" column.
	 */
	Iterator<I_C_Invoice> retrieveOpenInvoicesByOrg(I_AD_Org adOrg);

	/**
	 * Gets invoice open amount (not paid amount) by calling {@link IAllocationDAO#retrieveOpenAmtInInvoiceCurrency(org.compiere.model.I_C_Invoice, boolean)} with <code>creditMemoAdjusted == true</code>. Please note that the value
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
	 * Gets invoice open amount (not paid amount) by calling {@link IAllocationDAO#retrieveOpenAmtInInvoiceCurrency(org.compiere.model.I_C_Invoice, boolean)} with <code>creditMemoAdjusted == true</code>. Please note that the value
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

	/**
	 * Retrieves the reversal line for the given invoice line and C_Invoice_ID, using the line's <code>C_InvoiceLine.Line</code> value.
	 *
	 * @return the reversal line or <code>null</code> if the reversal invoice has no line with the given <code>line</code>'s number.
	 */
	I_C_InvoiceLine retrieveReversalLine(I_C_InvoiceLine line, int reversalInvoiceId);

	List<InvoiceTax> retrieveTaxes(@NonNull InvoiceId invoiceId);

	List<I_C_InvoiceTax> retrieveTaxRecords(@NonNull InvoiceId invoiceId);

	void deleteTaxes(@NonNull InvoiceId invoiceId);

	/**
	 * Retrieve all the Invoices that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either GrandTotal or TotalLines. These entries will produce 0 in posting
	 */
	List<I_C_Invoice> retrievePostedWithoutFactAcct(Properties ctx, Date startTime);

	/**
	 * Retrieve all Adjustment Charge entries that were created based on the given invoice
	 */
	Iterator<I_C_Invoice> retrieveAdjustmentChargesForInvoice(I_C_Invoice invoice);

	/**
	 * Retrieve all Credit Memo entries that were created based on the given invoice
	 */
	Iterator<I_C_Invoice> retrieveCreditMemosForInvoice(I_C_Invoice invoice);

	org.compiere.model.I_C_Invoice getByIdInTrx(InvoiceId invoiceId);

	@Nullable
	default org.compiere.model.I_C_Invoice getByIdInTrxIfExists(@NonNull final InvoiceId invoiceId)
	{
		final List<? extends org.compiere.model.I_C_Invoice> invoices = getByIdsInTrx(ImmutableSet.of(invoiceId));
		return invoices.size() == 1 ? invoices.get(0) : null;
	}

	List<? extends org.compiere.model.I_C_Invoice> getByIdsInTrx(Collection<InvoiceId> invoiceIds);

	List<org.compiere.model.I_C_Invoice> getByIdsOutOfTrx(Collection<InvoiceId> invoiceIds);

	Stream<InvoiceId> streamInvoiceIdsByBPartnerId(BPartnerId bpartnerId);

	ImmutableMap<InvoiceId, String> getDocumentNosByInvoiceIds(@NonNull Collection<InvoiceId> invoiceIds);

	org.compiere.model.I_C_InvoiceLine getByIdOutOfTrx(InvoiceAndLineId invoiceAndLineId);

	List<I_C_Invoice> retrieveBySalesrepPartnerId(BPartnerId salesRepBPartnerId, InstantInterval invoicedDateInterval);

	List<I_C_Invoice> retrieveSalesInvoiceByPartnerId(BPartnerId salesRepBPartnerId, InstantInterval invoicedDateInterval);

	Optional<InvoiceId> retrieveIdByInvoiceQuery(InvoiceQuery query);

	<T extends org.compiere.model.I_C_Invoice> List<T> getByDocumentNo(String documentNo, OrgId orgId, Class<T> modelClass);

	ImmutableList<I_C_Invoice> retrieveUnpaid(UnpaidInvoiceQuery query);

	Collection<InvoiceAndLineId> getInvoiceLineIds(final InvoiceId id);

	boolean isReferencedInvoiceReversed(I_C_Invoice invoiceExt);

	Collection<String> retrievePaidInvoiceDocNosForFilter(IQueryFilter<org.compiere.model.I_C_Invoice> filter);

	@Nullable
	I_C_InvoiceLine getOfInOutLine(@Nullable final I_M_InOutLine inOutLine);

	Stream<org.compiere.model.I_C_Invoice> stream(@NonNull IQueryFilter<org.compiere.model.I_C_Invoice> invoiceFilter);
}
