package de.metas.rest_api.invoicecandidates.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidate;
import de.metas.util.Services;
import de.metas.util.rest.ExternalHeaderAndLineId;
import de.metas.util.rest.ExternalId;

public class InvoiceCandidatesRestControllerImplTest
{

	private static final String EXTERNAL_LINE_ID1 = "Test1";
	private static final String EXTERNAL_HEADER_ID1 = "1001";

	private static final String EXTERNAL_LINE_ID2 = "Test2";
	private static final String EXTERNAL_HEADER_ID2 = "1002";

	private static final String EXTERNAL_LINE_ID3 = "Test3";
	private static final String EXTERNAL_HEADER_ID3 = "1003";

	private static final int P_INSTANCE_ID = 1002265;
	private IInvoiceCandBL invoiceCandBL;
	private InvoiceJsonConverterService jsonConverter;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		jsonConverter = new InvoiceJsonConverterService();
		invoiceCandBL = Services.get(IInvoiceCandBL.class);

		createInvoiceCandidate(EXTERNAL_LINE_ID1, EXTERNAL_HEADER_ID1);
	}

	@Test
	public void checkInvoiceCandidateSelection()
	{
		final JsonInvoiceCandidate jic = JsonInvoiceCandidate.builder()
				.externalHeaderId(ExternalId.of(EXTERNAL_HEADER_ID1))
				.externalLineId(ExternalId.of(EXTERNAL_LINE_ID1))
				.build();

		final List<ExternalHeaderAndLineId> headerAndLineIds = jsonConverter.convertJICToExternalHeaderAndLineIds(ImmutableList.of(jic));
		final int selection = invoiceCandBL.createSelectionForInvoiceCandidates(headerAndLineIds, PInstanceId.ofRepoId(P_INSTANCE_ID));

		assertThat(selection).isEqualTo(1);
	}

	@Test
	public void checkInvoiceCandidatesNotSelected()
	{
		final JsonInvoiceCandidate jic = JsonInvoiceCandidate.builder()
				.externalHeaderId(ExternalId.of(EXTERNAL_HEADER_ID3))
				.externalLineId(ExternalId.of(EXTERNAL_LINE_ID3))
				.build();

		final List<ExternalHeaderAndLineId> headerAndLineIds = jsonConverter.convertJICToExternalHeaderAndLineIds(ImmutableList.of(jic));
		createInvoiceCandidate(EXTERNAL_LINE_ID2, EXTERNAL_HEADER_ID2);

		final int selection = invoiceCandBL.createSelectionForInvoiceCandidates(headerAndLineIds, PInstanceId.ofRepoId(P_INSTANCE_ID));
		assertThat(selection).isEqualTo(0);
	}

	@Test
	public void checkEmptyListOfExternalLineIds()
	{
		final JsonInvoiceCandidate jic = JsonInvoiceCandidate.builder()
				.externalHeaderId(ExternalId.of(EXTERNAL_HEADER_ID3))
				.build();

		final List<ExternalHeaderAndLineId> headerAndLineIds = jsonConverter.convertJICToExternalHeaderAndLineIds(ImmutableList.of(jic));
		final int selection = invoiceCandBL.createSelectionForInvoiceCandidates(headerAndLineIds, PInstanceId.ofRepoId(P_INSTANCE_ID));
		assertThat(selection).isEqualTo(0);
	}

	private InvoiceCandidateId createInvoiceCandidate(final String externalHeaderId, final String externalLineId)
	{
		final I_C_Invoice_Candidate invoiceCandidate = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidate.setExternalHeaderId(EXTERNAL_HEADER_ID1);
		invoiceCandidate.setExternalLineId(EXTERNAL_LINE_ID1);
		saveRecord(invoiceCandidate);
		return InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
	}

}
