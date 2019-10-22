package de.metas.rest_api.invoicecandidates.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.IQuery;
import org.junit.Before;
import org.junit.Test;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidate;
import de.metas.util.rest.ExternalId;

public class InvoiceCandidatesRestControllerImplTest {

	private static final String EXTERNAL_LINE_ID = "Test3";
	private static final String EXTERNAL_HEADER_ID = "1002";
	private static final int P_INSTANCE_ID = 1002265;
	private InvoiceCandidatesRestControllerImpl invoiceCandidatesRestController;
	private List<JsonInvoiceCandidate> jsonInvoiceCandidates;

	@Before
	public void init() {
		AdempiereTestHelper.get().init();
		jsonInvoiceCandidates = new ArrayList<JsonInvoiceCandidate>();
		invoiceCandidatesRestController = new InvoiceCandidatesRestControllerImpl(new InvoiceJsonConverters());
		ExternalId externalId = new ExternalId(EXTERNAL_LINE_ID);
		List<ExternalId> externalLineIds = new ArrayList<ExternalId>();
		externalLineIds.add(externalId);
		JsonInvoiceCandidate jic = JsonInvoiceCandidate.builder().externalHeaderId(EXTERNAL_HEADER_ID)
				.externalLineIds(externalLineIds).build();
		jsonInvoiceCandidates.add(jic);
		createInvoiceCandidate();
	}

	@Test
	public void checkInvoiceCandidateSelection() {
		IQuery<I_C_Invoice_Candidate> queryBuilder = invoiceCandidatesRestController
				.createICQueryBuilder(jsonInvoiceCandidates);
		assertThat(queryBuilder.list()).isNotEmpty();
		int selection = queryBuilder.createSelection(PInstanceId.ofRepoId(P_INSTANCE_ID));
		assertThat(selection).isGreaterThan(0);
	}

	private InvoiceCandidateId createInvoiceCandidate() {
		final I_C_Invoice_Candidate invoiceCandidate = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidate.setExternalHeaderId(EXTERNAL_HEADER_ID);
		invoiceCandidate.setExternalLineId(EXTERNAL_LINE_ID);
		saveRecord(invoiceCandidate);
		return InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
	}
}
