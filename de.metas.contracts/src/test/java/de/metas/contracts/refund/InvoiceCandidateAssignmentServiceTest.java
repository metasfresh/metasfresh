package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		invoiceCandidateRepository = new InvoiceCandidateRepository(
				new RefundConfigRepository(),
				new MoneyFactory(new CurrencyRepository()));

		invoiceCandidateAssignmentService = new InvoiceCandidateAssignmentService(
				new RefundContractRepository(),
				invoiceCandidateRepository);
	}

	@Test
	public void unassignCandidate_AssignableInvoiceCandidate_without_assignment()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = RefundTestTools.createAssignableCandidate(null);

		// invoke the method under test
		final Throwable thrown = catchThrowable(() -> invoiceCandidateAssignmentService.unassignCandidate(assignableInvoiceCandidate));
		assertThat(thrown).isNotNull();
	}

	@Test
	public void unassignCandidate()
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = RefundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = RefundTestTools.createAssignableCandidate(refundInvoiceCandidate);

		// invoke the method under test
		final UnassignedPairOfCandidates unAssignedPairOfCandidates = invoiceCandidateAssignmentService.unassignCandidate(assignableInvoiceCandidate);

		final AssignableInvoiceCandidate unassignedAssignableInvoiceCandidate = unAssignedPairOfCandidates.getAssignableInvoiceCandidate();
		assertThat(unassignedAssignableInvoiceCandidate.getRefundInvoiceCandidate()).isNull();

		final I_C_Invoice_Candidate unassigneAssignableInvoiceCandidateRecord = RefundTestTools.retrieveRecord(unassignedAssignableInvoiceCandidate.getId());
		assertThat(unassigneAssignableInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo(TEN); // still unchanged

		final RefundInvoiceCandidate unAssignedRefundInvoiceCandidate = unAssignedPairOfCandidates.getRefundInvoiceCandidate();

		final InvoiceCandidateId invoiceCandidateId = unAssignedRefundInvoiceCandidate.getId();
		assertThat(invoiceCandidateId).isEqualTo(refundInvoiceCandidate.getId());
		assertThat(unAssignedRefundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED.subtract(TWO)); // we subtract 20% of 10; 20% is set in the refund config

		final I_C_Invoice_Candidate unAssignedRefundInvoiceCandidateRecord = RefundTestTools.retrieveRecord(invoiceCandidateId);
		assertThat(unAssignedRefundInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo(HUNDRED.subtract(TWO));
	}

	@Test
	public void assignCandidate()
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = RefundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = RefundTestTools.createAssignableCandidate(null);

		final UnassignedPairOfCandidates unAssignedPairOfCandidates = UnassignedPairOfCandidates.builder()
				.assignableInvoiceCandidate(assignableInvoiceCandidate)
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.build();

		// invoke the method under test
		final AssignableInvoiceCandidate assignableCandidateWithRefundCandidate = invoiceCandidateAssignmentService.assignCandidates(unAssignedPairOfCandidates);
		assertThat(assignableCandidateWithRefundCandidate.getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId());

		final RefundInvoiceCandidate assignedRefundInvoiceCandidate = assignableCandidateWithRefundCandidate.getRefundInvoiceCandidate();

		final InvoiceCandidateId invoiceCandidateId = assignedRefundInvoiceCandidate.getId();
		assertThat(invoiceCandidateId).isEqualTo(refundInvoiceCandidate.getId());
		assertThat(assignedRefundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED.add(TWO)); // // we add 20% of 10; 20% is set in the refund config

		final I_C_Invoice_Candidate assignedRefundInvoiceCandidateRecord = RefundTestTools.retrieveRecord(invoiceCandidateId);
		assertThat(assignedRefundInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo(HUNDRED.add(TWO));
	}
}
