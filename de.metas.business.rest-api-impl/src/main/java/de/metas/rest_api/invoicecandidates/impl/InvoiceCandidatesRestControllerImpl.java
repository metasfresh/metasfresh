package de.metas.rest_api.invoicecandidates.impl;

import java.util.List;

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
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.ICEnqueueingForInvoiceGenerationRestEndpoint;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandCreateRequest;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandCreateResponse;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.Services;
import de.metas.util.rest.ExternalHeaderAndLineId;
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
@RequestMapping(ICEnqueueingForInvoiceGenerationRestEndpoint.ENDPOINT)
@Profile(Profiles.PROFILE_App)
class InvoiceCandidatesRestControllerImpl implements ICEnqueueingForInvoiceGenerationRestEndpoint
{

	private static final Logger logger = LogManager.getLogger(InvoiceCandidatesRestControllerImpl.class);

	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final InvoiceJsonConverterService jsonConverter;

	public InvoiceCandidatesRestControllerImpl(@NonNull final InvoiceJsonConverterService jsonConverter)
	{
		this.jsonConverter = jsonConverter;
	}

	@PostMapping(consumes = { "application/json" })
	@Override
	public ResponseEntity<JsonInvoiceCandCreateResponse> createInvoices(
			@RequestBody @NonNull final JsonInvoiceCandCreateRequest request)
	{
		try
		{
			PInstanceId pInstanceId = adPInstanceDAO.createSelectionId();
			List<ExternalHeaderAndLineId> headerAndLineIds = jsonConverter.convertJICToExternalHeaderAndLineIds(request.getInvoiceCandidates());
			invoiceCandBL.createSelectionForInvoiceCandidates(headerAndLineIds, pInstanceId);

			final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
					.setInvoicingParams(createInvoicingParams(request)).setFailIfNothingEnqueued(true)
					.enqueueSelection(pInstanceId);

			final JsonInvoiceCandCreateResponse response = Services.get(ITrxManager.class)
					.callInNewTrx(() -> jsonConverter.toJson(enqueueResult));

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

	private PlainInvoicingParams createInvoicingParams(JsonInvoiceCandCreateRequest request)
	{
		PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setDateAcct(request.getDateAcct());
		invoicingParams.setDateInvoiced(request.getDateInvoiced());
		invoicingParams.setIgnoreInvoiceSchedule(request.getIgnoreInvoiceSchedule());
		invoicingParams.setPOReference(request.getPoReference());
		invoicingParams.setSupplementMissingPaymentTermIds(request.getSupplementMissingPaymentTermIds());
		invoicingParams.setUpdateLocationAndContactForInvoice(request.getUpdateLocationAndContactForInvoice());
		return invoicingParams;
	}
}
