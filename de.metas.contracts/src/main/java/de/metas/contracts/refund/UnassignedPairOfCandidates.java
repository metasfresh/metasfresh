package de.metas.contracts.refund;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import de.metas.money.Money;
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
public class UnassignedPairOfCandidates
{
	RefundInvoiceCandidate refundInvoiceCandidate;

	AssignableInvoiceCandidate assignableInvoiceCandidate;

	Money moneyToAssign;

	@Builder(toBuilder = true)
	private UnassignedPairOfCandidates(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate,
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate,
			@Nullable final Money moneyToAssign)
	{
		Check.assume(
				!assignableInvoiceCandidate.isAssigned(),
				"The given assignableInvoiceCandidate may not have an assignment; assignableInvoiceCandidate={}; refundInvoiceCandidate={}",
				assignableInvoiceCandidate, refundInvoiceCandidate);
		Check.assume(
				moneyToAssign == null || moneyToAssign.getCurrency().equals(refundInvoiceCandidate.getMoney().getCurrency()),
				"The given moneyToAssign needs to be in the given refundInvoiceCandidate's currency; moneyToAssign={}; refundInvoiceCandidate={}",
				moneyToAssign, refundInvoiceCandidate);

		this.refundInvoiceCandidate = refundInvoiceCandidate;
		this.assignableInvoiceCandidate = assignableInvoiceCandidate;
		this.moneyToAssign = moneyToAssign;
	}

	public UnassignedPairOfCandidates withAssignmentToRefundCandidate(
			@NonNull final AssignmentToRefundCandidate assignmentToRefundCandidate)
	{
		return toBuilder()
				.refundInvoiceCandidate(assignmentToRefundCandidate.getRefundInvoiceCandidate())
				.moneyToAssign(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate())
				.build();
	}
}
