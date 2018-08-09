package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyRepository;
import de.metas.money.MoneyService;

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

public class InvoiceCandidateAssignmentServiceTest
{

	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal TWO = new BigDecimal("2");;
	private static final BigDecimal HUNDRED = new BigDecimal("100");

	private InvoiceCandidateRepository invoiceCandidateRepository;

	private InvoiceCandidateAssignmentService invoiceCandidateAssignmentService;

	private RefundTestTools refundTestTools;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());

		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);

		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = new RefundInvoiceCandidateRepository(refundContractRepository);

		invoiceCandidateRepository = new InvoiceCandidateRepository(
				new AssignmentToRefundCandidateRepository(refundInvoiceCandidateRepository),
				refundContractRepository);

		final MoneyService moneyService = new MoneyService(new CurrencyRepository());

		invoiceCandidateAssignmentService = new InvoiceCandidateAssignmentService(
				refundContractRepository,
				invoiceCandidateRepository,
				new RefundInvoiceCandidateService(
						refundInvoiceCandidateRepository,
						new RefundInvoiceCandidateFactory(new AssignmentToRefundCandidateRepository(refundInvoiceCandidateRepository)),
						moneyService));

		refundTestTools = new RefundTestTools();
	}

	@Test
	public void unassignCandidate_AssignableInvoiceCandidate_without_assignment()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		// invoke the method under test
		final Throwable thrown = catchThrowable(() -> invoiceCandidateAssignmentService.unassignCandidate(assignableInvoiceCandidate));
		assertThat(thrown).isNotNull();
	}

	@Test
	public void unassignCandidate()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();

		final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate
				.getAssignmentsToRefundCandidates()
				.get(0)
				.getRefundInvoiceCandidate();
		assertThat(refundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED.add(TWO)); // guard

		// invoke the method under test
		final List<UnassignedPairOfCandidates> unAssignedPairsOfCandidates = invoiceCandidateAssignmentService.unassignCandidate(assignableInvoiceCandidate);

		assertThat(unAssignedPairsOfCandidates).hasSize(1);
		final UnassignedPairOfCandidates unAssignedPairOfCandidates = unAssignedPairsOfCandidates.get(0);

		final AssignableInvoiceCandidate unassignedAssignableInvoiceCandidate = unAssignedPairOfCandidates.getAssignableInvoiceCandidate();
		assertThat(unassignedAssignableInvoiceCandidate.isAssigned()).isFalse();

		final RefundInvoiceCandidate unAssignedRefundInvoiceCandidate = unAssignedPairOfCandidates.getRefundInvoiceCandidate();

		final InvoiceCandidateId invoiceCandidateId = unAssignedRefundInvoiceCandidate.getId();
		assertThat(invoiceCandidateId).isEqualTo(refundInvoiceCandidate.getId());
		assertThat(unAssignedRefundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED); // we subtract 20% of 10 from 102; 20% is set in the refund config

		final I_C_Invoice_Candidate unAssignedRefundInvoiceCandidateRecord = RefundTestTools.retrieveRecord(invoiceCandidateId);
		assertThat(unAssignedRefundInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo(HUNDRED);
	}

	@Test
	public void assignCandidate()
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = refundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		final UnassignedPairOfCandidates unAssignedPairOfCandidates = UnassignedPairOfCandidates.builder()
				.assignableInvoiceCandidate(assignableInvoiceCandidate)
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.build();

		// invoke the method under test
		final AssignableInvoiceCandidate assignableCandidateWithRefundCandidate = //
				invoiceCandidateAssignmentService.assignCandidates(unAssignedPairOfCandidates);

		final RefundInvoiceCandidate assignedRefundInvoiceCandidate = assignableCandidateWithRefundCandidate
				.getAssignmentsToRefundCandidates()
				.get(0)
				.getRefundInvoiceCandidate();

		assertThat(assignedRefundInvoiceCandidate.getId()).isEqualTo(refundInvoiceCandidate.getId());

		final InvoiceCandidateId invoiceCandidateId = assignedRefundInvoiceCandidate.getId();
		assertThat(invoiceCandidateId).isEqualTo(refundInvoiceCandidate.getId());
		assertThat(assignedRefundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED.add(TWO)); // // we add 20% of 10; 20% is set in the refund config

		final I_C_Invoice_Candidate assignedRefundInvoiceCandidateRecord = RefundTestTools.retrieveRecord(invoiceCandidateId);
		assertThat(assignedRefundInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo(HUNDRED.add(TWO));
	}

	@Test
	public void updateAssignment_not_yet_assigned()
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = refundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		// invoke the method under test
		final AssignableInvoiceCandidate assignedCandidate = invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidate);

		final AssignmentToRefundCandidate assignmentToRefundCandidate = assignedCandidate
				.getAssignmentsToRefundCandidates()
				.get(0);
		assertThat(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate()).isNotNull();
		assertThat(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo("2");

		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate()).isNotNull();
		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo("102"); // according to the assignable candidate's money and the config's percentage
	}

	@Test
	public void updateAssignment_already_assinged_according_to_parameter()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate
				.getAssignmentsToRefundCandidates()
				.get(0).getRefundInvoiceCandidate();

		// invoke the method under test
		final AssignableInvoiceCandidate assignedCandidate = invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidate);

		final RefundInvoiceCandidate resultRefundInvoiceCandidate = assignedCandidate
				.getAssignmentsToRefundCandidates().get(0)
				.getRefundInvoiceCandidate();
		assertThat(resultRefundInvoiceCandidate).isNotNull();
		assertThat(resultRefundInvoiceCandidate).isEqualTo(refundInvoiceCandidate); // unchanged bc nothing as really altered
	}

	@Test
	public void updateAssignment_already_assinged_only_according_to_backend()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate
				.getAssignmentsToRefundCandidates().get(0)
				.getRefundInvoiceCandidate();

		final AssignableInvoiceCandidate candidateWithRemovedRefundCandidate = assignableInvoiceCandidate.withoutRefundInvoiceCandidate();

		// invoke the method under test
		final AssignableInvoiceCandidate assignedCandidate = invoiceCandidateAssignmentService.updateAssignment(candidateWithRemovedRefundCandidate);

		final RefundInvoiceCandidate resultRefundInvoiceCandidate = assignedCandidate.getAssignmentsToRefundCandidates().get(0).getRefundInvoiceCandidate();
		assertThat(resultRefundInvoiceCandidate).isNotNull();
		assertThat(resultRefundInvoiceCandidate.getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(resultRefundInvoiceCandidate.getMoney()).isEqualTo(refundInvoiceCandidate.getMoney()); // unchanged bc nothing as really altered
	}

	@Test
	public void updateAssignment_change_money()
	{
		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		assertThat(assignableCandidate.getAssignmentsToRefundCandidates()).hasSize(1); // guard

		final AssignmentToRefundCandidate assignementToRefundCandidate = assignableCandidate
				.getAssignmentsToRefundCandidates()
				.get(0);
		assertThat(assignableCandidate.getMoney().getValue()).isEqualByComparingTo(TEN);// guard
		assertThat(assignementToRefundCandidate.getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo(TWO); // guard

		// guard: we work with 20%, so if the assignableInvoiceCandidate has 10, then 2 is assigned to the refundCandiate
		assertThat(assignementToRefundCandidate.getRefundInvoiceCandidate().getRefundConfig().getPercent().getValueAsBigDecimal()).isEqualByComparingTo(TWENTY);

		// guard: we assume that the refund candidate has already 102 assigned, and btw, we know that 2 of those are "contributed" by 20% of 10 = 2of our 'assignableCandidate'
		assertThat(assignementToRefundCandidate.getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo("102");

		final AssignableInvoiceCandidate assignableCandidateWithChange = assignableCandidate
				.toBuilder()
				.money(assignableCandidate.getMoney().multiply(TWO))
				.build();
		assertThat(assignableCandidateWithChange.getMoney().getValue()).isEqualByComparingTo(TWENTY); // guard

		// invoke the method under test
		final AssignableInvoiceCandidate result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidateWithChange);

		// basically, expect that instead of 2, assignableCandidate(WithChange) now adds 20% of 20 = 4. so it shall be 104 instead of 102
		assertThat(result.getAssignmentsToRefundCandidates()).hasSize(1);
		final AssignmentToRefundCandidate resultAssignmentToRefundCandidate = result.getAssignmentsToRefundCandidates().get(0);

		assertThat(resultAssignmentToRefundCandidate.getMoneyAssignedToRefundCandidate()).isNotNull();
		assertThat(resultAssignmentToRefundCandidate.getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo("4"); // guard

		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate()).isNotNull();
		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate().getId()).isEqualTo(assignementToRefundCandidate.getRefundInvoiceCandidate().getId()); // guard
		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo("104"); // according to the assignable candidate's money and the config's percentage
	}

	@Test
	public void assignCandidate_perScaleConfig1()
	{
		// TODO
		// existing refund-candidate with assignedQty=14
		// per-scale-configs with qties zero and 15
		// assignable candidate with qty 3

		// expected: 1 is assigned to the existing refund-candidate, 2 to a new refund-candidate
	}

	@Test
	public void assignCandidate_perScaleConfig2()
	{
		// TODO
		// *no* existing refund-candidate
		// per-scale-configs with qties zero and 15
		// assignable candidate with qty 18

		// expected: 15 is assigned one new refund-candidate, 3 to a new refund-candidate
	}

	@Test
	public void unassignCandidate_perScaleConfig1()
	{
		// TODO
		// per-scale-configs with qties zero and 15
		// two existing assignable-candidates; one with 8, one with 10
		// two existing refund-candidates; one with assigned 15, one with assigned 3
		// the assignable-candidate with 10 is exclusively assigned to the refund-candidates with 15
		// guard: the assignable-candidate with 8 is partially assigned to both refund candidates
		// unassign the assignable-candidates with 10

		// expected:
		// * the refund-candidate that had 3 now has zero;
		// * the refund-candidate that had 15 now has 15-7=8
		// * the assignable-candidate with 8 is now exclusively assigned to the refund-candidates with 0
	}
}
