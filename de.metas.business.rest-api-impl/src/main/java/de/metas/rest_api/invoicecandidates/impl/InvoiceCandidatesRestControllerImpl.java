package de.metas.rest_api.invoicecandidates.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.InvoiceCandidatesRestEndpoint;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandCreateRequest;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidates;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandCreateResponse;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.security.permissions.Access;
import de.metas.util.Services;
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
class InvoiceCandidatesRestControllerImpl implements InvoiceCandidatesRestEndpoint
{

	private static final Logger logger = LogManager.getLogger(InvoiceCandidatesRestControllerImpl.class);

	final private IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final InvoiceJsonConverters jsonConverters;

	public InvoiceCandidatesRestControllerImpl(
			@NonNull final InvoiceJsonConverters jsonConverters)
	{
		this.jsonConverters = jsonConverters;
	}
	
	@PostMapping(consumes={"application/json"})
	@Override
	public ResponseEntity<JsonInvoiceCandCreateResponse> createInvoices(@RequestBody @NonNull final JsonInvoiceCandCreateRequest request)
	{
		try
		{
			PInstanceId pInstanceId = getPInstanceId();
			createAndExecuteICQueryBuilder(request.getJsonInvoices(), pInstanceId);

			final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
					.setContext(Env.getCtx())
					.setInvoicingParams(createInvoicingParams(request))
					.setFailIfNothingEnqueued(true)
					.enqueueSelection(pInstanceId);

			final ITrxManager trxManager = Services.get(ITrxManager.class);
			final JsonInvoiceCandCreateResponse //
			response = trxManager.callInNewTrx(() -> jsonConverters.toJson(enqueueResult));

			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		}
		catch (final Exception ex)
		{
			logger.warn("Got exception while processing {}", request, ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.badRequest()
					.body(JsonInvoiceCandCreateResponse.error(JsonErrors.ofThrowable(ex, adLanguage)));
		}
	}


	@PostMapping(PATH_TEST)
	@Override
	public void receiveRequest()
	{
		System.out.println("Test 1234");
	}

	private void createAndExecuteICQueryBuilder(List<JsonInvoiceCandidates> jsonInvoices, PInstanceId pInstanceId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions, true)
				.setJoinOr();
		List<String> ids = new ArrayList<>();
		jsonInvoices.stream().forEach(p -> ids.addAll(p.getExternalLineIds().stream().map(e -> e.getValue()).collect(Collectors.toList())));

		for (final JsonInvoiceCandidates cand : jsonInvoices)
		{
			final ICompositeQueryFilter<I_C_Invoice_Candidate> invoiceCandidatesFilter = queryBL
					.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayOrAllFilter(I_C_Invoice_Candidate.COLUMN_ExternalLineId, ids)
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_ExternalHeaderId, cand.getExternalHeaderId());

			queryBuilder.filter(invoiceCandidatesFilter);
		}

		queryBuilder
				.create()
//				.setRequiredAccess(Access.READ)
				.createSelection(pInstanceId);
	}

	private InvoicingParamsObject createInvoicingParams(JsonInvoiceCandCreateRequest request)
	{
		InvoicingParamsObject invoicingParams = new InvoicingParamsObject();
		invoicingParams.setCheck_NetAmtToInvoice(request.getCheck_NetAmtToInvoice());
		invoicingParams.setDateAcct(request.getDateAcct());
		invoicingParams.setDateInvoiced(request.getDateInvoiced());
		invoicingParams.setIgnoreInvoiceSchedule(request.getIgnoreInvoiceSchedule());
		invoicingParams.setOnlyApprovedForInvoicing(request.getOnlyApprovedForInvoicing());
		invoicingParams.setPOReference(request.getPoReference());
		invoicingParams.setSupplementMissingPaymentTermIds(request.getSupplementMissingPaymentTermIds());
		invoicingParams.setUpdateLocationAndContactForInvoice(request.getUpdateLocationAndContactForInvoice());
		return invoicingParams;
	}

	private PInstanceId getPInstanceId()
	{
		return adPInstanceDAO.createSelectionId();
	}
}
