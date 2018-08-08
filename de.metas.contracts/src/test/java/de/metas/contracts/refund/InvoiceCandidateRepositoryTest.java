package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.refund.InvoiceCandidateRepository.RefundInvoiceCandidateQuery;
import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;

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
	private static final BigDecimal TWENTY = new BigDecimal("20");

	private static final LocalDate NOW = LocalDate.now();

	private final static BigDecimal FIVE = new BigDecimal("5");

	private I_C_UOM uomRecord;

	@Rule
	public final AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	private InvoiceCandidateRepository invoiceCandidateRepository;

	private RefundTestTools refundTestTools;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		invoiceCandidateRepository = new InvoiceCandidateRepository(
				new RefundContractRepository(
						new RefundConfigRepository(
								new InvoiceScheduleRepository())));

		refundTestTools = new RefundTestTools();

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

	@Test
	public void saveCandidateAssignment()
	{

		final RefundInvoiceCandidate refundInvoiceCandidate = refundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		final UnassignedPairOfCandidates unAssignedPairOfCandidates = UnassignedPairOfCandidates.builder()
				.assignableInvoiceCandidate(assignableInvoiceCandidate)
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.moneyToAssign(Money.of(new BigDecimal("3"), refundInvoiceCandidate.getMoney().getCurrencyId()))
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
		final List<RefundInvoiceCandidate> result = invoiceCandidateRepository.getRefundInvoiceCandidates(query);
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
		final List<RefundInvoiceCandidate> result = invoiceCandidateRepository.getRefundInvoiceCandidates(query);

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
		final List<RefundInvoiceCandidate> result = invoiceCandidateRepository.getRefundInvoiceCandidates(query);

		assertThat(result)
				.as("Expect empty result bc the query is for a time range coming after refundCandidate's date; query=%s", query)
				.isEmpty();
	}

	@Test
	public void getById_assignableInvoiceCandidate()
	{
		final I_C_Invoice_Candidate assignableCandidateRecord = createAssignableCandidateRecord();

		// invoke the method under test
		final InvoiceCandidate candidate = invoiceCandidateRepository.getById(InvoiceCandidateId.ofRepoId(assignableCandidateRecord.getC_Invoice_Candidate_ID()));

		final AssignableInvoiceCandidate assignableCandidate = AssignableInvoiceCandidate.cast(candidate);

		assertThat(assignableCandidate.getQuantity().getAsBigDecimal()).isEqualByComparingTo("15");
		assertThat(assignableCandidate.getQuantity().getUOMId()).isEqualTo(assignableCandidateRecord.getM_Product().getC_UOM_ID());
		assertThat(assignableCandidate.getBpartnerId().getRepoId()).isEqualTo(20);
		assertThat(assignableCandidate.getMoney().getValue()).isEqualTo(TWENTY);
		assertThat(assignableCandidate.getMoney().getCurrencyId().getRepoId()).isEqualTo(102);
		assertThat(assignableCandidate.getInvoiceableFrom()).isEqualTo(NOW);
	}

	public static I_C_Invoice_Candidate createAssignableCandidateRecord()
	{
		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM(uomRecord);
		saveRecord(productRecord);

		final I_C_Invoice_Candidate assignableCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		assignableCandidateRecord.setBill_BPartner_ID(20);
		assignableCandidateRecord.setDateToInvoice(TimeUtil.asTimestamp(NOW));
		assignableCandidateRecord.setNetAmtToInvoice(TWENTY);
		assignableCandidateRecord.setC_Currency_ID(102);
		assignableCandidateRecord.setM_Product(productRecord);
		assignableCandidateRecord.setQtyInvoiced(TEN);
		assignableCandidateRecord.setQtyToInvoice(FIVE);
		saveRecord(assignableCandidateRecord);
		return assignableCandidateRecord;
	}
}
