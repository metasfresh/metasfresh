package de.metas.contracts.refund;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.refund.InvoiceCandidateRepository.RefundInvoiceCandidateQuery;
import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
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
	@Rule
	public final AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	private InvoiceCandidateRepository invoiceCandidateRepository;

	private RefundTestTools refundTestTools;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		invoiceCandidateRepository = new InvoiceCandidateRepository(
				new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository())),
				new MoneyFactory(new CurrencyRepository()));

		refundTestTools = new RefundTestTools();
	}

	@Test
	public void saveCandidateAssignment()
	{

		final RefundInvoiceCandidate refundInvoiceCandidate = refundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		final UnassignedPairOfCandidates unAssignedPairOfCandidates = UnassignedPairOfCandidates.builder()
				.assignableInvoiceCandidate(assignableInvoiceCandidate)
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.moneyToAssign(Money.of(new BigDecimal("3"), refundInvoiceCandidate.getMoney().getCurrency()))
				.build();

		// invoke the method under test
		final AssignableInvoiceCandidate result = invoiceCandidateRepository.saveCandidateAssignment(unAssignedPairOfCandidates);

		assertThat(result.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId());

		final List<I_C_Invoice_Candidate_Assignment> assignmentRecords = POJOLookupMap.get().getRecords(I_C_Invoice_Candidate_Assignment.class);
		assertThat(assignmentRecords).hasSize(1);
		final I_C_Invoice_Candidate_Assignment assignmentRecord = assignmentRecords.get(0);
		assertThat(assignmentRecord.getC_Invoice_Candidate_Assigned_ID()).isEqualTo(assignableInvoiceCandidate.getId().getRepoId());
		assertThat(assignmentRecord.getC_Invoice_Candidate_Term_ID()).isEqualTo(refundInvoiceCandidate.getId().getRepoId());
		assertThat(assignmentRecord.getC_Flatrate_Term_ID()).isEqualTo(refundInvoiceCandidate.getRefundContract().getId().getRepoId());
	}

	@Test
	public void getRefundInvoiceCandidate_same_invoicableFrom()
	{
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate();

		final RefundInvoiceCandidateQuery query = RefundInvoiceCandidateQuery.builder()
				.refundContract(refundCandidate.getRefundContract())
				.invoicableFrom(refundCandidate.getInvoiceableFrom())
				.build();

		// invoke the method under test
		final Optional<RefundInvoiceCandidate> result = invoiceCandidateRepository.getRefundInvoiceCandidate(query);
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(refundCandidate);
	}

	@Test
	public void getRefundInvoiceCandidate_earlier_invoicableFrom()
	{
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate();

		final LocalDate earlierInvoiceableFrom = refundCandidate
				.getInvoiceableFrom()
				.minusDays(2); // the contract starts 3 days before this, so we are still within

		final RefundInvoiceCandidateQuery query = RefundInvoiceCandidateQuery.builder()
				.refundContract(refundCandidate.getRefundContract())
				.invoicableFrom(earlierInvoiceableFrom)
				.build();

		// invoke the method under test
		final Optional<RefundInvoiceCandidate> result = invoiceCandidateRepository.getRefundInvoiceCandidate(query);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(refundCandidate);
	}

	@Test
	public void getRefundInvoiceCandidate_later_invoicableFrom()
	{
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate();

		final LocalDate earlierInvoiceableFrom = refundCandidate
				.getInvoiceableFrom()
				.plusDays(1);

		final RefundInvoiceCandidateQuery query = RefundInvoiceCandidateQuery.builder()
				.refundContract(refundCandidate.getRefundContract())
				.invoicableFrom(earlierInvoiceableFrom)
				.build();

		// invoke the method under test
		final Optional<RefundInvoiceCandidate> result = invoiceCandidateRepository.getRefundInvoiceCandidate(query);

		assertThat(result)
				.as("Expect empty result bc the query is for a time range coming after refundCandidate's date; query=%s", query)
				.isEmpty();
	}
}
