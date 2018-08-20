package de.metas.contracts.refund;

import javax.annotation.Nullable;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
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
public class AssignmentToRefundCandidate
{
	@Nullable
	InvoiceCandidateId assignableInvoiceCandidateId;

	@NonNull
	RefundInvoiceCandidate refundInvoiceCandidate;

	@NonNull
	RefundConfigId refundConfigId;

	@NonNull
	Money moneyAssignedToRefundCandidate;

	@NonNull
	Quantity quantityAssigendToRefundCandidate;

	public AssignmentToRefundCandidate withSubtractedMoneyAndQuantity()
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

		return new AssignmentToRefundCandidate(
				assignableInvoiceCandidateId,
				newRefundCandidate,
				refundConfigId,
				moneyAssignedToRefundCandidate.toZero(),
				quantityAssigendToRefundCandidate.toZero());
	}
}
