package de.metas.contracts.refund;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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

public class InvoiceCandidateAssignmentServiceTest
{

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

		invoiceCandidateRepository = new InvoiceCandidateRepository(
				new RefundContractRepository(refundConfigRepository),
				new MoneyFactory(new CurrencyRepository()));

		invoiceCandidateAssignmentService = new InvoiceCandidateAssignmentService(
				new RefundContractRepository(refundConfigRepository),
				invoiceCandidateRepository);

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

		final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate();
		assertThat(refundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED.add(TWO)); // guard

		// invoke the method under test
		final UnassignedPairOfCandidates unAssignedPairOfCandidates = invoiceCandidateAssignmentService.unassignCandidate(assignableInvoiceCandidate);

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

		assertThat(assignableCandidateWithRefundCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId());

		final RefundInvoiceCandidate assignedRefundInvoiceCandidate = assignableCandidateWithRefundCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate();

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

		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getMoneyAssignedToRefundCandidate()).isNotNull();
		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo("2");

		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate()).isNotNull();
		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo("102"); // according to the assignable candidate's money and the config's percentage
	}

	@Test
	public void updateAssignment_already_assinged_according_to_parameter()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate();

		// invoke the method under test
		final AssignableInvoiceCandidate assignedCandidate = invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidate);

		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate()).isNotNull();
		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate()).isEqualTo(refundInvoiceCandidate); // unchanged bc nothing as really altered
	}

	@Test
	public void updateAssignment_already_assinged_only_according_to_backend()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate();

		assertThat(assignableInvoiceCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate()).isEqualTo(refundInvoiceCandidate); // guard

		final AssignableInvoiceCandidate candidateWithRemovedRefundCandidate = assignableInvoiceCandidate.withoutRefundInvoiceCandidate();

		// invoke the method under test
		final AssignableInvoiceCandidate assignedCandidate = invoiceCandidateAssignmentService.updateAssignment(candidateWithRemovedRefundCandidate);

		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate()).isNotNull();
		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(assignedCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getMoney()).isEqualTo(refundInvoiceCandidate.getMoney()); // unchanged bc nothing as really altered
	}

	@Test
	public void updateAssignment_change_money()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate refundInvoiceCandidate = assignableInvoiceCandidate.getAssignmentToRefundCandidate().getRefundInvoiceCandidate();

		assertThat(assignableInvoiceCandidate.getAssignmentToRefundCandidate().getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo("2"); // guard

		final AssignableInvoiceCandidate assignableInvoiceCandidateWithDifferentMoney = assignableInvoiceCandidate
				.toBuilder()
				.money(assignableInvoiceCandidate.getMoney().multiply(TWO))
				.build();

		// invoke the method under test
		final AssignableInvoiceCandidate result = invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidateWithDifferentMoney);

		assertThat(result.getAssignmentToRefundCandidate().getMoneyAssignedToRefundCandidate()).isNotNull();
		assertThat(result.getAssignmentToRefundCandidate().getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo("4"); // guard

		assertThat(result.getAssignmentToRefundCandidate().getRefundInvoiceCandidate()).isNotNull();
		assertThat(result.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(result.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo("104"); // according to the assignable candidate's money and the config's percentage
	}
}
