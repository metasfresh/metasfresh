package de.metas.payment.api;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.I_C_Payment;

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

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;

import javax.annotation.Nullable;

public interface IPaymentDAO extends ISingletonService
{
	I_C_Payment getById(PaymentId paymentId);

	Optional<I_C_Payment> getByExternalOrderId(@NonNull ExternalId externalId, @NonNull OrgId orgId);

	@Nullable
	ExternalId getExternalId(@NonNull PaymentId paymentId);

	List<I_C_Payment> getByIds(Set<PaymentId> paymentIds);

	/**
	 * @return payment's available to allocate amount (i.e. open amount)
	 */
	BigDecimal getAvailableAmount(PaymentId paymentId);

	/**
	 * @param creditMemoAdjusted True if we want to get absolute values for Credit Memos
	 */
	BigDecimal getInvoiceOpenAmount(I_C_Payment payment, boolean creditMemoAdjusted);

	/**
	 * Return a list of active, processed lines in {@link I_C_PaySelection}
	 */
	@Deprecated
	List<I_C_PaySelectionLine> getProcessedLines(I_C_PaySelection paySelection);

	/**
	 * retrieve payment allocations for specific payment
	 */
	List<I_C_AllocationLine> retrieveAllocationLines(I_C_Payment payment);

	/**
	 * Get the allocated amount for the given payment.<br>
	 * If the payment references a C_Charge, then only return the pay-amount.
	 * Otherwise, return the amount plus payment-writeOff-amount from C_AllocationLines which reference the payment.
	 *
	 * @return never return <code>null</code>, even if there are no allocations
	 */
	BigDecimal getAllocatedAmt(I_C_Payment payment);

	/**
	 * Retrieve all the payments that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either PayAmt or OverUnderAmt. These entries will produce 0 in posting
	 */
	List<I_C_Payment> retrievePostedWithoutFactAcct(Properties ctx, Timestamp startTime);

	Stream<PaymentId> streamPaymentIdsByBPartnerId(BPartnerId bpartnerId);

	/**
	 * Updates the discount and the payment based on DateTrx and the payment term policy.
	 */
	void updateDiscountAndPayment(I_C_Payment payment, int c_Invoice_ID, I_C_DocType c_DocType);

	ImmutableSet<PaymentId> retrievePaymentIds(PaymentQuery query);

	void save(@NonNull final I_C_Payment payment);
}
