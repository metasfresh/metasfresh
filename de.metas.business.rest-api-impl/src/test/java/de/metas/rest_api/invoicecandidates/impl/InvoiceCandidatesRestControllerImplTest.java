package de.metas.rest_api.invoicecandidates.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.IQuery;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidates;
import de.metas.util.Services;
import de.metas.util.rest.ExternalId;

public class InvoiceCandidatesRestControllerImplTest {
	private static final Logger logger = LogManager.getLogger(InvoiceCandidatesRestControllerImpl.class);

	private IQueryBL queryBL;
	int pInstanceId = 1002265;
	private InvoiceCandidatesRestControllerImpl invoiceCandidatesRestController;
	private List<JsonInvoiceCandidates> jsonInvoices;

	@Before
	public void init() {
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
		jsonInvoices = new ArrayList<JsonInvoiceCandidates>();
		invoiceCandidatesRestController = new InvoiceCandidatesRestControllerImpl(new InvoiceJsonConverters());
		ExternalId externalId = new ExternalId("Test3");
		List<ExternalId> externalLineIds = new ArrayList<ExternalId>();
		externalLineIds.add(externalId);
		JsonInvoiceCandidates jic = JsonInvoiceCandidates.builder().externalHeaderId("1002")
				.externalLineIds(externalLineIds).build();
		jsonInvoices.add(jic);
	}

	@Test
	public void checkInvoiceCandidateSelection() {
		IQuery<I_C_Invoice_Candidate> queryBuilder = invoiceCandidatesRestController.createICQueryBuilder(jsonInvoices);
		assertThat(queryBuilder.list()).isNotEmpty();
		int selection = queryBuilder.createSelection(PInstanceId.ofRepoId(pInstanceId));
		assertThat(selection > 0);
	}
}
