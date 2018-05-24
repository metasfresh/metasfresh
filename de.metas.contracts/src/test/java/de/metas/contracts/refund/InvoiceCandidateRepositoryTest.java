package de.metas.contracts.refund;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.money.CurrencyRepository;
import de.metas.money.MoneyFactory;

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

public class InvoiceCandidateRepositoryTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void saveCandidateAssignment()
	{
		final InvoiceCandidateRepository invoiceCandidateRepository = new InvoiceCandidateRepository(
				new RefundConfigRepository(),
				new MoneyFactory(new CurrencyRepository()));

		final RefundInvoiceCandidate refundInvoiceCandidate = RefundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = RefundTestTools.createAssignableCandidate(null);

		final UnassignedPairOfCandidates unAssignedPairOfCandidates = UnassignedPairOfCandidates.builder()
				.assignableInvoiceCandidate(assignableInvoiceCandidate)
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.build();

		final AssignableInvoiceCandidate result = invoiceCandidateRepository.saveCandidateAssignment(unAssignedPairOfCandidates);
		assertThat(result.getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId());

		final List<I_C_Invoice_Candidate_Assignment> assignmentRecords = POJOLookupMap.get().getRecords(I_C_Invoice_Candidate_Assignment.class);
		assertThat(assignmentRecords).hasSize(1);
		final I_C_Invoice_Candidate_Assignment assignmentRecord = assignmentRecords.get(0);
		assertThat(assignmentRecord.getC_Invoice_Candidate_Assigned_ID()).isEqualTo(assignableInvoiceCandidate.getId().getRepoId());
		assertThat(assignmentRecord.getC_Invoice_Candidate_Term_ID()).isEqualTo(refundInvoiceCandidate.getId().getRepoId());
		assertThat(assignmentRecord.getC_Flatrate_Term_ID()).isEqualTo(refundInvoiceCandidate.getRefundContractId().getRepoId());
	}
}
