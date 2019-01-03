package de.metas.contracts.refund;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
public class AssignmentToRefundCandidate
{
	@NonNull
	RefundConfigId refundConfigId;

	@NonNull
	InvoiceCandidateId assignableInvoiceCandidateId;

	@NonNull
	RefundInvoiceCandidate refundInvoiceCandidate;

	/** Relevant money amount of the assignable invoice candidate. */
	@NonNull
	Money moneyBase;

	@NonNull
	Money moneyAssignedToRefundCandidate;

	@NonNull
	Quantity quantityAssigendToRefundCandidate;

	boolean useAssignedQtyInSum;

	/**
	 * @return a new instance that has the previously assigned money and quantity subtracted from its {@link #getRefundInvoiceCandidate()}
	 */
	public AssignmentToRefundCandidate withZeroAssignedMoneyAndQuantity()
	{
		final Money moneySubtrahent = getMoneyAssignedToRefundCandidate();
		final Money newMoneyAmount = refundInvoiceCandidate
				.getMoney()
				.subtract(moneySubtrahent);

		final Quantity assignedQuantitySubtrahent = getQuantityAssigendToRefundCandidate();
		final Quantity newQuantity = refundInvoiceCandidate
				.getAssignedQuantity()
				.subtract(assignedQuantitySubtrahent);

		final RefundInvoiceCandidate newRefundCandidate = refundInvoiceCandidate.toBuilder()
				.money(newMoneyAmount)
				.assignedQuantity(newQuantity)
				.build();

		return withZeroAssignedMoneyAndQuantity(newRefundCandidate);
	}

	/**
	 * Like {@link #withZeroAssignedMoneyAndQuantity()}, but the new instance does not contain a different refund invoice candidate.
	 */
	public AssignmentToRefundCandidate withZeroAssignedMoneyAndQuantitySameCandidate()
	{
		return withZeroAssignedMoneyAndQuantity(getRefundInvoiceCandidate());
	}

	private AssignmentToRefundCandidate withZeroAssignedMoneyAndQuantity(@NonNull final RefundInvoiceCandidate refundCandidate)
	{
		return new AssignmentToRefundCandidate(
				refundConfigId,
				assignableInvoiceCandidateId,
				refundCandidate,
				moneyBase,
				moneyAssignedToRefundCandidate.toZero(),
				quantityAssigendToRefundCandidate.toZero(),
				useAssignedQtyInSum);
	}

	public AssignmentToRefundCandidate withRefundInvoiceCandidate(@NonNull final RefundInvoiceCandidate refundInvoiceCandidate)
	{
		return new AssignmentToRefundCandidate(
				refundConfigId,
				assignableInvoiceCandidateId,
				refundInvoiceCandidate,
				moneyBase,
				moneyAssignedToRefundCandidate,
				quantityAssigendToRefundCandidate,
				useAssignedQtyInSum);
	}
}
