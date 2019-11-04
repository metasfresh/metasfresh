package de.metas.rest_api.invoicecandidates.impl;

import java.util.List;

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
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.IInvoicesRestEndpoint;
import de.metas.rest_api.invoicecandidates.request.JsonRequestEnqueueForInvoicing;
import de.metas.rest_api.invoicecandidates.request.JsonRequestInvoiceCandidateUpsert;
import de.metas.rest_api.invoicecandidates.response.JsonResponseEnqueueForInvoicing;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsert;
import de.metas.util.Services;
import de.metas.util.rest.ExternalHeaderAndLineId;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = IInvoicesRestEndpoint.ENDPOINT, consumes = "application/json", produces = "application/json")
@Profile(Profiles.PROFILE_App)
class InvoicesRestControllerImpl implements IInvoicesRestEndpoint
{
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final InvoiceJsonConverterService jsonConverter;
	private final JsonInvoiceCandidateUpsertService jsonInvoiceCandidateUpsertService;

	public InvoicesRestControllerImpl(
			@NonNull final InvoiceJsonConverterService jsonConverter,
			@NonNull final JsonInvoiceCandidateUpsertService jsonInvoiceCandidateUpsertService)
	{
		this.jsonConverter = jsonConverter;
		this.jsonInvoiceCandidateUpsertService = jsonInvoiceCandidateUpsertService;
	}

	@ApiOperation("Create new invoice candidates or update existing invoice candidates")
	@PostMapping(path = "createOrUpdateCandidates")
	@Override
	public ResponseEntity<JsonResponseInvoiceCandidateUpsert> createOrUpdateInvoiceCandidates(
			@RequestBody @NonNull final JsonRequestInvoiceCandidateUpsert request)
	{
		return ResponseEntity.ok(jsonInvoiceCandidateUpsertService.createOrUpdateInvoiceCandidates(request));
	}

	@ApiOperation("Enqueues invoice candidates for invoicing")
	@PostMapping(path = "enqueueForInvoicing")
	@Override
	public ResponseEntity<JsonResponseEnqueueForInvoicing> enqueueForInvoicing(@RequestBody @NonNull final JsonRequestEnqueueForInvoicing request)
	{
		final PInstanceId pInstanceId = adPInstanceDAO.createSelectionId();
		final List<ExternalHeaderAndLineId> headerAndLineIds = jsonConverter.convertJICToExternalHeaderAndLineIds(request.getInvoiceCandidates());
		invoiceCandBL.createSelectionForInvoiceCandidates(headerAndLineIds, pInstanceId);

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL
				.enqueueForInvoicing()
				.setInvoicingParams(jsonConverter.createInvoicingParams(request))
				.setFailIfNothingEnqueued(true)
				.enqueueSelection(pInstanceId);

		final JsonResponseEnqueueForInvoicing response = jsonConverter.toJson(enqueueResult);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
