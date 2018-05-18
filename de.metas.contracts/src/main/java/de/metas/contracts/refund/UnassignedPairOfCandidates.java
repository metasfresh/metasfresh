package de.metas.contracts.refund;

import org.adempiere.util.Check;

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
	@NonNull
	RefundInvoiceCandidate refundInvoiceCandidate;

	@NonNull
	AssignableInvoiceCandidate assignableInvoiceCandidate;

	@Builder(toBuilder = true)
	private UnassignedPairOfCandidates(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate,
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate)
	{
		Check.assume(
				assignableInvoiceCandidate.getRefundInvoiceCandidate() == null,
				"The given assignableInvoiceCandidate may not have an assignment; assignableInvoiceCandidate={}; refundInvoiceCandidate={}",
				assignableInvoiceCandidate, refundInvoiceCandidate);

		this.refundInvoiceCandidate = refundInvoiceCandidate;
		this.assignableInvoiceCandidate = assignableInvoiceCandidate;
	}

	public UnassignedPairOfCandidates withRefundInvoiceCandidate(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate)
	{
		return toBuilder()
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.build();
	}
}
