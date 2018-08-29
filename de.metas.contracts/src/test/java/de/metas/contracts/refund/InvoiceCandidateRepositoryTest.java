package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Rule;

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

	private static final BigDecimal THREE = new BigDecimal("3");

	private I_C_UOM uomRecord;

	@Rule
	public final AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	private InvoiceCandidateRepository invoiceCandidateRepository;

	private RefundTestTools refundTestTools;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		invoiceCandidateRepository = new InvoiceCandidateRepository();

		refundTestTools = new RefundTestTools();

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

//	@Test
//	public void saveCandidateAssignment()
//	{
//		final RefundInvoiceCandidate refundInvoiceCandidate = refundTestTools.createRefundCandidate();
//		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();
//
//		final RefundConfig config = RefundTestTools.extractSingleConfig(refundInvoiceCandidate);
//
//		final AssignCandidatesRequest assignCandidatesRequest = AssignCandidatesRequest.builder()
//				.assignableInvoiceCandidate(assignableInvoiceCandidate)
//				.refundInvoiceCandidate(refundInvoiceCandidate)
//				.moneyToAssign(Money.of(THREE, refundInvoiceCandidate.getMoney().getCurrencyId()))
//				.quantityToAssign(Quantity.of(THREE, refundTestTools.getUomRecord()))
//				.refundConfig(config)
//				.build();
//
//		// invoke the method under test
//		final AssignableInvoiceCandidate result = invoiceCandidateRepository.saveCandidateAssignment(assignCandidatesRequest);
//
//		assertThat(result.getAssignmentsToRefundCandidates().get(0).getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId());
//
//		final List<I_C_Invoice_Candidate_Assignment> assignmentRecords = POJOLookupMap.get().getRecords(I_C_Invoice_Candidate_Assignment.class);
//		assertThat(assignmentRecords).hasSize(1);
//		final I_C_Invoice_Candidate_Assignment assignmentRecord = assignmentRecords.get(0);
//		assertThat(assignmentRecord.getC_Invoice_Candidate_Assigned_ID()).isEqualTo(assignableInvoiceCandidate.getId().getRepoId());
//		assertThat(assignmentRecord.getC_Invoice_Candidate_Term_ID()).isEqualTo(refundInvoiceCandidate.getId().getRepoId());
//		assertThat(assignmentRecord.getC_Flatrate_Term_ID()).isEqualTo(refundInvoiceCandidate.getRefundContract().getId().getRepoId());
//		assertThat(assignmentRecord.getC_Flatrate_RefundConfig_ID()).isEqualTo(config.getId().getRepoId());
//	}
}
