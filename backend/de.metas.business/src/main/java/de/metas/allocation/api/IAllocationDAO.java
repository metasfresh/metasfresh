package de.metas.allocation.api;

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

import com.google.common.collect.SetMultimap;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface IAllocationDAO extends ISingletonService
{
	void save(I_C_AllocationHdr allocationHdr);

	void save(I_C_AllocationLine allocationLine);

	List<I_C_AllocationLine> retrieveLines(I_C_AllocationHdr allocHdr);

	/**
	 * @return all lines, also inactive ones, and no matter which AD_Client_ID
	 */
	List<I_C_AllocationLine> retrieveAllLines(I_C_AllocationHdr allocHdr);

	/**
	 * Check all the completed C_Payments with a "matching" doctype for the invoice and IsAutoAllocateAvailableAmt='Y' AND IsAllocated='N' for the partner given as param.
	 * 
	 * task 04193
	 */
	List<I_C_Payment> retrieveAvailablePayments(I_C_Invoice invoice);

	/**
	 * Retrieve that part of the given <code>invoice</code>'s <code>GrandTotal</code> that has not yet been allocated.
	 *
	 * @param invoice the invoice for which we retrieve the open amount
	 * @param creditMemoAdjusted if <code>true</code> and <code>invoice</code> is a credit memo, then the open amount is negated.
	 */
	BigDecimal retrieveOpenAmt(I_C_Invoice invoice, boolean creditMemoAdjusted);

	/**
	 * Retrieve that part of the given <code>invoice</code>'s <code>GrandTotal</code> that has already been allocated.
	 */
	BigDecimal retrieveAllocatedAmt(I_C_Invoice invoice);

	/**
	 * Retrieve the written off amount of an <code>invoice</code>.
	 */
	BigDecimal retrieveWriteoffAmt(I_C_Invoice invoice);

	/**
	 * Similar to {@link #retrieveAllocatedAmt(I_C_Invoice)}, but excludes those allocations from the sum that are related to the given <code>C_Payment_ID</code>s.
	 *
	 * @param paymentIDsToIgnore may be <code>null</code> or empty.
	 */
	BigDecimal retrieveAllocatedAmtIgnoreGivenPaymentIDs(I_C_Invoice invoice, Set<Integer> paymentIDsToIgnore);

	/**
	 * Retrieve allocation lines for specified invoice
	 */
	List<I_C_AllocationLine> retrieveAllocationLines(final I_C_Invoice invoice);

	/**
	 * Retrieve all the AllocationHdr documents that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either Amount, DiscountAmt, WriteOffAmt or OverUnderAmt. These entries will produce 0 in posting.
	 */
	List<I_C_AllocationHdr> retrievePostedWithoutFactAcct(Properties ctx, Date startTime);

	List<I_C_Payment> retrieveInvoicePayments(I_C_Invoice invoice);

	SetMultimap<PaymentId, InvoiceId> retrieveInvoiceIdsByPaymentIds(@NonNull Collection<PaymentId> paymentIds);
}
