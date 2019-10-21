package de.metas.rest_api.invoicecandidates.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

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
		jsonInvoices=new ArrayList<JsonInvoiceCandidates>();
		
		invoiceCandidatesRestController = new InvoiceCandidatesRestControllerImpl(new InvoiceJsonConverters());
	}

	@Test
	public void insertIntoT_Selection() {
		ExternalId externalId=new ExternalId("Test3");
		List<ExternalId> externalLineIds=new ArrayList<ExternalId>();
		externalLineIds.add(externalId);
		JsonInvoiceCandidates jic = JsonInvoiceCandidates.builder().externalHeaderId("1002").externalLineIds(externalLineIds).build();
		jsonInvoices.add(jic);
		invoiceCandidatesRestController.createAndExecuteICQueryBuilder(jsonInvoices, PInstanceId.ofRepoId(pInstanceId));

	}
}
