package de.metas.contracts.refund.exceedingqty;

import static de.metas.contracts.refund.RefundTestTools.extractSingleConfig;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.contracts.refund.AssignCandidatesRequest;
import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.contracts.refund.RefundTestTools;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.quantity.Quantity;

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

public class CandidateAssignServiceExceedingQtyTest
{
	private static final BigDecimal TWO = new BigDecimal("2");

	private static final BigDecimal HUNDRED = new BigDecimal("100");

	private CandidateAssignServiceExceedingQty candidateAssignServiceExceedingQty;

	private RefundTestTools refundTestTools;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateAssignServiceExceedingQty = CandidateAssignServiceExceedingQty.createInstanceForUnitTesting();

		refundTestTools = RefundTestTools.newInstance();
	}

	@Test
	public void assignCandidate()
	{
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateStandlone();
		final RefundConfig refundConfig = extractSingleConfig(refundCandidate);

		final AssignCandidatesRequest assignCandidatesRequest = AssignCandidatesRequest.builder()
				.refundInvoiceCandidate(refundCandidate)
				.assignableInvoiceCandidate(assignableCandidate)
				.refundConfig(refundConfig)
				.build();

		// invoke the method under test
		final IPair<AssignableInvoiceCandidate, Quantity> result = //
				candidateAssignServiceExceedingQty.assignCandidates(
						assignCandidatesRequest,
						assignableCandidate.getQuantity());

		assertThat(result.getRight().isZero()).isTrue(); // everything was assigned

		final RefundInvoiceCandidate assignedRefundInvoiceCandidate = result.getLeft()
				.getAssignmentsToRefundCandidates()
				.get(0)
				.getRefundInvoiceCandidate();

		assertThat(assignedRefundInvoiceCandidate.getId()).isEqualTo(refundCandidate.getId());

		final InvoiceCandidateId invoiceCandidateId = assignedRefundInvoiceCandidate.getId();
		assertThat(invoiceCandidateId).isEqualTo(refundCandidate.getId());
		assertThat(assignedRefundInvoiceCandidate.getMoney().toBigDecimal()).isEqualByComparingTo(HUNDRED.add(TWO)); // // we add 20% of 10; 20% is set in the refund config

		final I_C_Invoice_Candidate assignedRefundInvoiceCandidateRecord = RefundTestTools.retrieveRecord(invoiceCandidateId);
		assertThat(assignedRefundInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo(HUNDRED.add(TWO));
	}
}
