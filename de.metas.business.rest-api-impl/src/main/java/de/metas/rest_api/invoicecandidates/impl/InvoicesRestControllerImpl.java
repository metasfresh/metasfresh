package de.metas.rest_api.invoicecandidates.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.rest_api.invoicecandidates.IInvoicesRestEndpoint;
import de.metas.rest_api.invoicecandidates.request.JsonCheckInvoiceCandidatesStatusRequest;
import de.metas.rest_api.invoicecandidates.request.JsonCloseInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.request.JsonCreateInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
	private final CheckInvoiceCandidatesStatusService checkInvoiceCandidatesStatusService;
	private final CreateInvoiceCandidatesService createInvoiceCandidatesService;
	private final EnqueueForInvoicingService enqueueForInvoicingService;
	private final CloseInvoiceCandidatesService closeInvoiceCandidatesService;

	public InvoicesRestControllerImpl(
			@NonNull final CreateInvoiceCandidatesService createInvoiceCandidatesService,
			@NonNull final CheckInvoiceCandidatesStatusService invoiceCandidateInfoService,
			@NonNull final EnqueueForInvoicingService enqueueForInvoicingService,
			@NonNull final CloseInvoiceCandidatesService closeInvoiceCandidatesService)
	{
		this.createInvoiceCandidatesService = createInvoiceCandidatesService;
		this.checkInvoiceCandidatesStatusService = invoiceCandidateInfoService;
		this.enqueueForInvoicingService = enqueueForInvoicingService;
		this.closeInvoiceCandidatesService = closeInvoiceCandidatesService;
	}

	@ApiOperation("Create new invoice candidates")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created new invoice candidate(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to create new invoice candidates"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PostMapping(path = "/createCandidates")
	@Override
	public ResponseEntity<JsonCreateInvoiceCandidatesResponse> createInvoiceCandidates(
			@RequestBody @NonNull final JsonCreateInvoiceCandidatesRequest request)
	{
		// TODO make individual IC accessible via URL, then return "created" instead
		final JsonCreateInvoiceCandidatesResponse resonse = createInvoiceCandidatesService.createInvoiceCandidates(request);
		return ResponseEntity.ok(resonse);
	}

	@PostMapping("/status")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved status for all matching invoice candidates"),
			@ApiResponse(code = 401, message = "You are not authorized to retrieve the invoice candidates' status"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden")
	})
	@Override
	public ResponseEntity<JsonCheckInvoiceCandidatesStatusResponse> checkInvoiceCandidatesStatus(@RequestBody @NonNull final JsonCheckInvoiceCandidatesStatusRequest request)
	{
		final JsonCheckInvoiceCandidatesStatusResponse response = checkInvoiceCandidatesStatusService.getStatusForInvoiceCandidates(request);
		return ResponseEntity.ok(response);
	}

	@ApiOperation("Enqueues invoice candidates for invoicing")
	@PostMapping("/enqueueForInvoicing")
	@Override
	public ResponseEntity<JsonEnqueueForInvoicingResponse> enqueueForInvoicing(@RequestBody @NonNull final JsonEnqueueForInvoicingRequest request)
	{
		final JsonEnqueueForInvoicingResponse response = enqueueForInvoicingService.enqueueForInvoicing(request);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping("/close")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully closed all matching invoice candidates"),
			@ApiResponse(code = 401, message = "You are not close the invoice candidates"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden")
	})
	@Override
	public ResponseEntity<JsonCloseInvoiceCandidatesResponse> closeInvoiceCandidates(@RequestBody @NonNull final JsonCloseInvoiceCandidatesRequest request)
	{
		final JsonCloseInvoiceCandidatesResponse response = closeInvoiceCandidatesService.closeInvoiceCandidates(request);
		return ResponseEntity.ok(response);
	}
}
