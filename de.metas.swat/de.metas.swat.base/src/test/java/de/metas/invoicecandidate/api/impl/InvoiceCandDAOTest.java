package de.metas.invoicecandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refreshAll;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_PaymentTerm;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import de.metas.util.rest.ExternalHeaderAndLineId;
import de.metas.util.rest.ExternalId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
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

public class InvoiceCandDAOTest
{

	private static final String EXTERNAL_LINE_ID1 = "Test1";
	private static final String EXTERNAL_HEADER_ID1 = "1001";

	private static final String EXTERNAL_LINE_ID2 = "Test2";
	private static final String EXTERNAL_HEADER_ID2 = "1002";

	private static final String EXTERNAL_LINE_ID3 = "Test3";
	private static final String EXTERNAL_HEADER_ID3 = "1003";
	
	private static final int P_INSTANCE_ID = 1002265;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void updateMissingPaymentTermIds()
	{
		// create two ICs without a payment term; those shall be updated by the method under test
		final I_C_Invoice_Candidate invoiceCandidateWithoutPaymentTerm1 = newInstance(I_C_Invoice_Candidate.class);
		save(invoiceCandidateWithoutPaymentTerm1);

		final I_C_Invoice_Candidate invoiceCandidateWithoutPaymentTerm2 = newInstance(I_C_Invoice_Candidate.class);
		save(invoiceCandidateWithoutPaymentTerm2);

		// Create two ICs with different payment terms.
		// By virtue of it's lower C_Invoice_Candidate_ID, the first IC's payment term ID shall be set in the first two ICs we creates
		final PaymentTermId paymentTermId1 = createPaymentTerm();
		final I_C_Invoice_Candidate invoiceCandidateWithPaymentTerm1 = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateWithPaymentTerm1.setC_PaymentTerm_ID(paymentTermId1.getRepoId());
		save(invoiceCandidateWithPaymentTerm1);

		final PaymentTermId paymentTermId2 = createPaymentTerm();
		final I_C_Invoice_Candidate invoiceCandidateWithPaymentTerm2 = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateWithPaymentTerm2.setC_PaymentTerm_ID(paymentTermId2.getRepoId());
		save(invoiceCandidateWithPaymentTerm2);

		final PInstanceId selectionId = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class)
				.create()
				.createSelection();

		// create an unrelated IC that also has no payment term; it shall be left untouched
		final I_C_Invoice_Candidate unrelatedInvoiceCandidateWithoutPaymentTerm = newInstance(I_C_Invoice_Candidate.class);
		save(unrelatedInvoiceCandidateWithoutPaymentTerm);

		// invoke the method under test and refresh all ICs
		new InvoiceCandDAO().updateMissingPaymentTermIds(selectionId);

		refreshAll(ImmutableList.of(
				invoiceCandidateWithoutPaymentTerm1,
				invoiceCandidateWithoutPaymentTerm2,
				invoiceCandidateWithPaymentTerm1,
				invoiceCandidateWithPaymentTerm2,
				unrelatedInvoiceCandidateWithoutPaymentTerm));

		// verify
		assertThat(getPaymentTermId(invoiceCandidateWithoutPaymentTerm1)).isEqualTo(paymentTermId1);
		assertThat(getPaymentTermId(invoiceCandidateWithoutPaymentTerm2)).isEqualTo(paymentTermId1);
		assertThat(getPaymentTermId(invoiceCandidateWithPaymentTerm1)).isEqualTo(paymentTermId1);

		assertThat(getPaymentTermId(invoiceCandidateWithPaymentTerm2))
				.as("invoiceCandidateWithPaymentTerm2 shall be left unchanged because it already has a C_PaymentTerm_ID")
				.isEqualTo(paymentTermId2);

		assertThat(getPaymentTermId(unrelatedInvoiceCandidateWithoutPaymentTerm))
				.as("unrelatedInvoiceCandidateWithoutPaymentTerm shall be left unchanged because it's not part of the selection")
				.isNull();
	}

	@Test
	public void testInvoiceCandidates()
	{
		createInvoiceCandidate(EXTERNAL_LINE_ID1, EXTERNAL_HEADER_ID1);
		createInvoiceCandidate(EXTERNAL_LINE_ID2, EXTERNAL_HEADER_ID2);
		createInvoiceCandidate(EXTERNAL_LINE_ID3, EXTERNAL_HEADER_ID3);
		ExternalId externalId1 = ExternalId.of(EXTERNAL_LINE_ID1);
		ExternalId externalId2 = ExternalId.of(EXTERNAL_LINE_ID2);

		List<ExternalId> externalLineIds1 = new ArrayList<ExternalId>();
		externalLineIds1.add(externalId1);
		List<ExternalId> externalLineIds2 = new ArrayList<ExternalId>();
		externalLineIds2.add(externalId2);

		List<ExternalHeaderAndLineId> headerAndLineIds1 = new ArrayList<ExternalHeaderAndLineId>();
		headerAndLineIds1.add(ExternalHeaderAndLineId.builder().externalHeaderId(EXTERNAL_HEADER_ID1).externalLineIds(externalLineIds1).build());
		
		headerAndLineIds1.add(ExternalHeaderAndLineId.builder().externalHeaderId(EXTERNAL_HEADER_ID2).externalLineIds(externalLineIds2).build());
		
		IQuery<I_C_Invoice_Candidate> createQueryByHeaderAndLineId = new InvoiceCandDAO().createQueryByHeaderAndLineId(headerAndLineIds1);
		int size = createQueryByHeaderAndLineId.list().size();
		assert(size==3);
		//need an advice here- because the size will always be 0 in here.
	}
	
	@Test
	public void checkInvoiceCandidateSelection()
	{
		createInvoiceCandidate(EXTERNAL_LINE_ID1, EXTERNAL_HEADER_ID1);
		ExternalId externalId1 = ExternalId.of(EXTERNAL_LINE_ID1);
		List<ExternalId> externalLineIds = new ArrayList<ExternalId>();
		externalLineIds.add(externalId1);
		List<ExternalHeaderAndLineId> headerAndLineIds = new ArrayList<ExternalHeaderAndLineId>();
		headerAndLineIds.add(ExternalHeaderAndLineId.builder().externalHeaderId(EXTERNAL_HEADER_ID1).externalLineIds(externalLineIds).build());
		IQuery<I_C_Invoice_Candidate> createQueryByHeaderAndLineId = new InvoiceCandDAO().createQueryByHeaderAndLineId(headerAndLineIds);
		
		int selection = createQueryByHeaderAndLineId.createSelection(PInstanceId.ofRepoId(P_INSTANCE_ID));

		assertThat(selection).isEqualTo(1);
	}

	@Test
	public void checkInvoiceCandidatesNotSelected()
	{
		createInvoiceCandidate(EXTERNAL_LINE_ID1, EXTERNAL_HEADER_ID1);
		ExternalId externalId2 = ExternalId.of(EXTERNAL_LINE_ID2);
		List<ExternalId> externalLineIds = new ArrayList<ExternalId>();
		externalLineIds.add(externalId2);
		List<ExternalHeaderAndLineId> headerAndLineIds = new ArrayList<ExternalHeaderAndLineId>();
		headerAndLineIds.add(ExternalHeaderAndLineId.builder().externalHeaderId(EXTERNAL_HEADER_ID2).externalLineIds(externalLineIds).build());
		IQuery<I_C_Invoice_Candidate> createQueryByHeaderAndLineId = new InvoiceCandDAO().createQueryByHeaderAndLineId(headerAndLineIds);
		int selection = createQueryByHeaderAndLineId.createSelection(PInstanceId.ofRepoId(P_INSTANCE_ID));
		assertThat(selection).isEqualTo(0);
	}

	@Test
	public void checkEmptyListOfExternalLineIds()
	{
		ExternalId externalId3 = ExternalId.of(EXTERNAL_LINE_ID3);
		List<ExternalId> externalLineIds = new ArrayList<ExternalId>();
		externalLineIds.add(externalId3);
		List<ExternalHeaderAndLineId> headerAndLineIds = new ArrayList<ExternalHeaderAndLineId>();
		headerAndLineIds.add(ExternalHeaderAndLineId.builder().externalHeaderId(EXTERNAL_HEADER_ID3).externalLineIds(externalLineIds).build());
		IQuery<I_C_Invoice_Candidate> createQueryByHeaderAndLineId = new InvoiceCandDAO().createQueryByHeaderAndLineId(headerAndLineIds);
		int selection = createQueryByHeaderAndLineId.createSelection(PInstanceId.ofRepoId(P_INSTANCE_ID));
		assertThat(selection).isEqualTo(0);
	}
	
	private InvoiceCandidateId createInvoiceCandidate(String externalHeaderId, String externalLineId)
	{
		final I_C_Invoice_Candidate invoiceCandidate = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidate.setExternalHeaderId(externalHeaderId);
		invoiceCandidate.setExternalLineId(externalLineId);
		saveRecord(invoiceCandidate);
		return InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
	}

	private PaymentTermId createPaymentTerm()
	{
		final I_C_PaymentTerm record = newInstance(I_C_PaymentTerm.class);
		saveRecord(record);
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}

	private PaymentTermId getPaymentTermId(@NonNull final I_C_Invoice_Candidate ic)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> PaymentTermId.ofRepoIdOrNull(ic.getC_PaymentTerm_Override_ID()),
				() -> PaymentTermId.ofRepoIdOrNull(ic.getC_PaymentTerm_ID()));
	}
}
