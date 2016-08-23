package de.metas.payment.api;

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
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_Payment;

import de.metas.adempiere.model.I_C_PaySelectionLine;

public interface IPaymentDAO extends ISingletonService
{
	/**
	 * @param payment
	 * @return payment's available to allocate amount
	 */
	BigDecimal getAvailableAmount(I_C_Payment payment);

	/**
	 * @param payment
	 * @param creditMemoAdjusted True if we want to get absolute values for Credit Memos
	 * @return
	 */
	BigDecimal getInvoiceOpenAmount(I_C_Payment payment, boolean creditMemoAdjusted);

	/**
	 * Return a list of active, processed lines in {@link I_C_PaySelection}
	 *
	 * @param paySelection
	 * @return
	 */
	List<I_C_PaySelectionLine> getProcessedLines(I_C_PaySelection paySelection);

	/**
	 * retrieve payment allocations for specific payment
	 *
	 * @param payment
	 * @return
	 */
	List<I_C_AllocationLine> retrieveAllocationLines(I_C_Payment payment);

	/**
	 * Get the allocated amount for the given payment.<br>
	 * If the payment references a C_Charge, then only return the pay-amount.
	 * Otherwise, return the amount plus payment-writeOff-amount from C_AllocationLines which reference the payment.
	 *
	 * @param payment
	 * @return never return <code>null</code>, even if there are no allocations
	 */
	BigDecimal getAllocatedAmt(I_C_Payment payment);

	/**
	 * Retrieve all the payments that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either PayAmt or OverUnderAmt. These entries will produce 0 in posting
	 * 
	 * @param ctx
	 * @param startTime
	 * @return
	 */
	List<I_C_Payment> retrievePostedWithoutFactAcct(Properties ctx, Timestamp startTime);
}
