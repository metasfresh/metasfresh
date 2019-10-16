package de.metas.rest_api.invoicecandidates.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.api.IParams;
import org.compiere.model.I_AD_Process;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.rest_api.invoicecandidates.InvoiceCandidatesRestEndpoint;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandCreateRequest;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandCreateResponse;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * Used for managing invoices and invoice candidates(create, query)
 */
@RestController
@RequestMapping(InvoiceCandidatesRestEndpoint.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class InvoiceCandidatesRestControllerImpl implements InvoiceCandidatesRestEndpoint
{

	private static final Logger logger = LogManager.getLogger(InvoiceCandidatesRestControllerImpl.class);

	private IInvoicingParams invoicingParams;


	@PostMapping
	@Override
	public ResponseEntity<JsonInvoiceCandCreateResponse> createInvoices(@RequestBody @NonNull final JsonInvoiceCandCreateRequest request)
	{
		final ProcessExecutionResult result = ProcessExecutor.builder(createProcessInfo(request))
				// .switchContextWhenRunning() // NOTE: not needed, context was already switched in caller method
				.executeSync()
				.getResult();
		final boolean ok = !result.isError();

//		
//		try
//		{
//
//			final ITrxManager trxManager = Services.get(ITrxManager.class);
//
//			final JsonInvoiceCandCreateResponse response = trxManager.callInNewTrx(() -> createInvoiceCandidates());
//
//			//
//			return new ResponseEntity<>(response, HttpStatus.CREATED);
//		}
//		catch (final Exception ex)
//		{
//
//			final String adLanguage = Env.getADLanguageOrBaseLanguage();
//			return ResponseEntity.badRequest()
//					.body(JsonInvoiceCandCreateResponse.error(JsonErrors.ofThrowable(ex, adLanguage)));
//		}

		return null;
	}
	@PostMapping(PATH_TEST)
	@Override
	public void receiveRequest() {
		System.out.println("Test 1234");
	}
	
	private static final ProcessInfo createProcessInfo(final JsonInvoiceCandCreateRequest request)
	{
		ProcessInfo pi = ProcessInfo.builder()
				.setAD_Process_ID(AdProcessId.ofRepoId(540304))
				.setPInstanceId(PInstanceId.ofRepoId(0))//we need to create in DB an "AD_PInstance" record and AD_PInstance_Para, and then use the ID from there
				.setInvokedByScheduler(false)
				.setWhereClause("C_Invoice_Candidate.C_Invoice_Candidate_ID IN (1000003,1000002))")//ids from JsonInvoiceCandCreateRequest
				.setJRDesiredOutputType(OutputType.PDF)
				.addParameters(createProcessInfoParameters(request))
				.build();

		return pi;
	}

	private static List<ProcessInfoParameter> createProcessInfoParameters(JsonInvoiceCandCreateRequest request)
	{
		List<ProcessInfoParameter> params = new ArrayList<ProcessInfoParameter>();
		//the params needed for the process should be taken from the request and they need to be saved in the db -> X_AD_PROCESS_PARA
		return params;
	}
	
	
}
