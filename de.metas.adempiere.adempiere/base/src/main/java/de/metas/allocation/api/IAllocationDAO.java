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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;

import de.metas.adempiere.model.I_C_Invoice;

public interface IAllocationDAO extends ISingletonService
{
	List<I_C_AllocationLine> retrieveLines(I_C_AllocationHdr allocHdr);

	/**
	 *
	 * @param allocHdr
	 * @return all lines, also inactive ones, and no matter which AD_Client_ID
	 */
	List<I_C_AllocationLine> retrieveAllLines(I_C_AllocationHdr allocHdr);

	/**
	 * Checks all the completed C_Payments with a "matching" doctype for the invoice and IsAutoAllocateAvailableAmt='Y' AND IsAllocated='N' for the partner given as param.
	 *
	 * @param invoice
	 * @return
	 * @task 04193
	 */
	List<I_C_Payment> retrieveAvailablePayments(I_C_Invoice invoice);

	/**
	 * Retrieves that part of the given <code>invoice</code>'s <code>GrandTotal</code> that has not yet been allocated.
	 *
	 * @param invoice the invoice for which we retrive the open amount
	 * @param creditMemoAdjusted if <code>true</code> and <code>invoice</code> is a credit memo, then the open amount is negated.
	 * @return
	 */
	BigDecimal retrieveOpenAmt(org.compiere.model.I_C_Invoice invoice, boolean creditMemoAdjusted);

	/**
	 * Retrieves that part of the given <code>invoice</code>'s <code>GrandTotal</code> that has already been allocated.
	 *
	 * @param invoice
	 * @return
	 */
	BigDecimal retrieveAllocatedAmt(org.compiere.model.I_C_Invoice invoice);

	/**
	 * Similar to {@link #retrieveAllocatedAmt(org.compiere.model.I_C_Invoice)}, but excludes those allocations from the sum that are related to the given <code>C_Payment_ID</code>s.
	 *
	 * @param invoice
	 * @param paymentIDsToIgnore may be <code>null</code> or empty.
	 * @return
	 */
	BigDecimal retrieveAllocatedAmtIgnoreGivenPaymentIDs(org.compiere.model.I_C_Invoice invoice, Set<Integer> paymentIDsToIgnore);

	/**
	 * retriev allocation lines for specified invoice
	 *
	 * @param invoice
	 * @return
	 */
	List<I_C_AllocationLine> retrieveAllocationLines(final org.compiere.model.I_C_Invoice invoice);

	/**
	 * Retrieve all the AllocationHdr documents that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either Amount, DiscountAmt, WriteOffAmt or OverUnderAmt. These entries will produce 0 in posting.
	 * 
	 * @param ctx
	 * @param startTime
	 * @return
	 */
	List<I_C_AllocationHdr> retrievePostedWithoutFactAcct(Properties ctx, Date startTime);
}
