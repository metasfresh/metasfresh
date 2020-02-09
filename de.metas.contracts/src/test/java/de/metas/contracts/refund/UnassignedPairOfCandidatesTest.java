package de.metas.contracts.refund;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.contracts.refund.AssignCandidatesRequest.AssignCandidatesRequestBuilder;

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

public class UnassignedPairOfCandidatesTest
{
	private RefundTestTools refundTestTools;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		refundTestTools = RefundTestTools.newInstance();
	}

	@Test
	public void build()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();

		final AssignCandidatesRequestBuilder builder = AssignCandidatesRequest
				.builder()
				.assignableInvoiceCandidate(assignableInvoiceCandidate)
				.refundInvoiceCandidate(assignableInvoiceCandidate
						.getAssignmentsToRefundCandidates()
						.get(0)
						.getRefundInvoiceCandidate());

		final Throwable thrown = catchThrowable(() -> builder.build());
		assertThat(thrown)
				.as("UnAssignedPairOfCandidates shall make sure that its two candidates are not assigned")
				.isNotNull();
	}

}
