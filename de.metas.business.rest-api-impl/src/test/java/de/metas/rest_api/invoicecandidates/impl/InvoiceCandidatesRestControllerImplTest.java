package de.metas.rest_api.invoicecandidates.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandCreateRequest;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidates;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCand;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandCreateResponse;
import de.metas.rest_api.ordercandidates.response.JsonOLCand;
import de.metas.util.Services;
import de.metas.util.rest.ExternalId;

public class InvoiceCandidatesRestControllerImplTest {
	private static final Logger logger = LogManager.getLogger(InvoiceCandidatesRestControllerImpl.class);

	private IQueryBL queryBL;
	int pInstanceId = 1002265;
	private InvoiceCandidatesRestControllerImpl invoiceCandidatesRestController;
	private List<JsonInvoiceCandidates> jsonInvoices;
	private JsonInvoiceCandCreateRequest request;

	@Before
	public void init() {
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
		jsonInvoices = new ArrayList<JsonInvoiceCandidates>();
		invoiceCandidatesRestController = new InvoiceCandidatesRestControllerImpl(new InvoiceJsonConverters());
	}

	@Test
	public void checkInvoiceCandidateSelection() {
		ExternalId externalId = new ExternalId("Test3");
		List<ExternalId> externalLineIds = new ArrayList<ExternalId>();
		externalLineIds.add(externalId);
		JsonInvoiceCandidates jic = JsonInvoiceCandidates.builder().externalHeaderId("1002")
				.externalLineIds(externalLineIds).build();
		jsonInvoices.add(jic);
		invoiceCandidatesRestController.createAndExecuteICQueryBuilder(jsonInvoices, PInstanceId.ofRepoId(pInstanceId));
		JsonInvoiceCandCreateRequest build = JsonInvoiceCandCreateRequest.builder().dateAcct(LocalDate.now())
				.dateInvoiced(LocalDate.now()).ignoreInvoiceSchedule(false).poReference(null)
				.supplementMissingPaymentTermIds(false).updateLocationAndContactForInvoice(true).jsonInvoices(jsonInvoices).build();
		JsonInvoiceCandCreateResponse response = invoiceCandidatesRestController.createInvoices(build).getBody();
		JsonInvoiceCand result = response.getResult();
		assertThat(result.getInvoiceCandidateEnqueuedCount() == 1);
	}
}
