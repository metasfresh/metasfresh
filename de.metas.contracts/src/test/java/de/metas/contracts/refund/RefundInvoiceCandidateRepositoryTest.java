package de.metas.contracts.refund;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.refund.RefundInvoiceCandidateRepository.RefundInvoiceCandidateQuery;
import de.metas.invoice.InvoiceScheduleRepository;

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

public class RefundInvoiceCandidateRepositoryTest
{
	private RefundTestTools refundTestTools;
	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		refundTestTools = new RefundTestTools();

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);
		final AssignmentAggregateService assignmentAggregateService = new AssignmentAggregateService(refundConfigRepository);
		final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(refundContractRepository, assignmentAggregateService);

		refundInvoiceCandidateRepository = new RefundInvoiceCandidateRepository(
				refundContractRepository,
				refundInvoiceCandidateFactory);
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
		final List<RefundInvoiceCandidate> result = refundInvoiceCandidateRepository.getRefundInvoiceCandidates(query);
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(refundCandidate);
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
		final List<RefundInvoiceCandidate> result = refundInvoiceCandidateRepository.getRefundInvoiceCandidates(query);

		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(refundCandidate);
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
		final List<RefundInvoiceCandidate> result = refundInvoiceCandidateRepository.getRefundInvoiceCandidates(query);

		assertThat(result)
				.as("Expect empty result bc the query is for a time range coming after refundCandidate's date; query=%s", query)
				.isEmpty();
	}
}
