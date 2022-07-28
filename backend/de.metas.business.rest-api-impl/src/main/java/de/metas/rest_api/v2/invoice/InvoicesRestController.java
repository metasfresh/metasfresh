/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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
 */

package de.metas.rest_api.v2.invoice;

import de.metas.Profiles;
import de.metas.common.rest_api.v1.JsonError;
import de.metas.common.rest_api.v2.invoice.JsonInvoicePaymentCreateRequest;
import de.metas.invoice.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.rest_api.invoicecandidates.impl.CheckInvoiceCandidatesStatusService;
import de.metas.rest_api.invoicecandidates.impl.CloseInvoiceCandidatesService;
import de.metas.rest_api.invoicecandidates.impl.CreateInvoiceCandidatesService;
import de.metas.rest_api.invoicecandidates.impl.EnqueueForInvoicingService;
import de.metas.rest_api.invoicecandidates.request.JsonCheckInvoiceCandidatesStatusRequest;
import de.metas.rest_api.invoicecandidates.request.JsonCloseInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.request.JsonCreateInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import de.metas.rest_api.invoicecandidates.response.JsonReverseInvoiceResponse;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.v2.invoice.impl.JSONInvoiceInfoResponse;
import de.metas.rest_api.v2.invoice.impl.JsonInvoiceService;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/invoices")
@Profile(Profiles.PROFILE_App)
public class InvoicesRestController
{
	private static final Logger logger = LogManager.getLogger(InvoicesRestController.class);

	private final JsonInvoiceService jsonInvoiceService;
	private final CheckInvoiceCandidatesStatusService checkInvoiceCandidatesStatusService;
	private final CreateInvoiceCandidatesService createInvoiceCandidatesService;
	private final EnqueueForInvoicingService enqueueForInvoicingService;
	private final CloseInvoiceCandidatesService closeInvoiceCandidatesService;

	public InvoicesRestController(
			@NonNull final CreateInvoiceCandidatesService createInvoiceCandidatesService,
			@NonNull final CheckInvoiceCandidatesStatusService invoiceCandidateInfoService,
			@NonNull final EnqueueForInvoicingService enqueueForInvoicingService,
			@NonNull final CloseInvoiceCandidatesService closeInvoiceCandidatesService,
			@NonNull final JsonInvoiceService jsonInvoiceService)
	{
		this.createInvoiceCandidatesService = createInvoiceCandidatesService;
		this.checkInvoiceCandidatesStatusService = invoiceCandidateInfoService;
		this.enqueueForInvoicingService = enqueueForInvoicingService;
		this.closeInvoiceCandidatesService = closeInvoiceCandidatesService;
		this.jsonInvoiceService = jsonInvoiceService;
	}

	@ApiOperation("Create new invoice candidates")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created new invoice candidate(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to create new invoice candidates"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PostMapping(path = "/createCandidates", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JsonCreateInvoiceCandidatesResponse> createInvoiceCandidates(
			@RequestBody @NonNull final JsonCreateInvoiceCandidatesRequest request)
	{
		// TODO make individual IC accessible via URL, then return "created" instead
		final JsonCreateInvoiceCandidatesResponse resonse = createInvoiceCandidatesService.createInvoiceCandidates(request);
		return ResponseEntity.ok(resonse);
	}

	@PostMapping(path = "/status", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved status for all matching invoice candidates"),
			@ApiResponse(code = 401, message = "You are not authorized to retrieve the invoice candidates' status"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden")
	})
	public ResponseEntity<JsonCheckInvoiceCandidatesStatusResponse> checkInvoiceCandidatesStatus(@RequestBody @NonNull final JsonCheckInvoiceCandidatesStatusRequest request)
	{
		final JsonCheckInvoiceCandidatesStatusResponse response = checkInvoiceCandidatesStatusService.getStatusForInvoiceCandidates(request);
		return ResponseEntity.ok(response);
	}

	@ApiOperation("Enqueues invoice candidates for invoicing")
	@PostMapping(path = "/enqueueForInvoicing", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JsonEnqueueForInvoicingResponse> enqueueForInvoicing(@RequestBody @NonNull final JsonEnqueueForInvoicingRequest request)
	{
		final JsonEnqueueForInvoicingResponse response = enqueueForInvoicingService.enqueueForInvoicing(request);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping(path = "/close", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully closed all matching invoice candidates"),
			@ApiResponse(code = 401, message = "You are not authorized to close the invoice candidates"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden")
	})
	public ResponseEntity<JsonCloseInvoiceCandidatesResponse> closeInvoiceCandidates(@RequestBody @NonNull final JsonCloseInvoiceCandidatesRequest request)
	{
		final JsonCloseInvoiceCandidatesResponse response = closeInvoiceCandidatesService.closeInvoiceCandidates(request);
		return ResponseEntity.ok(response);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "PDF retrieved for invoice"),
			@ApiResponse(code = 401, message = "You are not authorized to see the invoice PDF"),
			@ApiResponse(code = 404, message = "No archive found for the invoice")
	})
	@GetMapping(path = "/{invoiceId}/pdf")
	public ResponseEntity<byte[]> getInvoicePDF(
			@ApiParam(required = true, value = "metasfreshId of the invoice to get the PDF of") //
			@PathVariable("invoiceId") final int invoiceRecordId)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(invoiceRecordId);
		if (invoiceId == null)
		{
			return ResponseEntity.notFound().build();
		}

		final Optional<byte[]> invoicePDF = jsonInvoiceService.getInvoicePDF(invoiceId);

		if (invoicePDF.isPresent())
		{
			return ResponseEntity.ok(invoicePDF.get());
		}

		return ResponseEntity.notFound().build();
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Invoice info retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized"),
			@ApiResponse(code = 404, message = "No Invoice found"),
			@ApiResponse(code = 422, message = "An error happened during Invoice info retrieval"),
	})
	@GetMapping(path = "{invoiceId}/invoiceInfo", produces = "application/json")
	public ResponseEntity<?> getInvoiceInfo(
			@ApiParam(required = true, value = "metasfreshId of the invoice")
			@PathVariable("invoiceId") final int invoiceRecordId)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(invoiceRecordId);
		if (invoiceId == null)
		{
			return ResponseEntity.notFound().build();
		}

		final String ad_language = Env.getAD_Language();
		try
		{
			final JSONInvoiceInfoResponse invoiceInfo = jsonInvoiceService.getInvoiceInfo(invoiceId, ad_language);
			return ResponseEntity.ok(invoiceInfo);
		}
		catch (final Exception ex)
		{
			final JsonError error = JsonError.ofSingleItem(JsonErrors.ofThrowable(ex, ad_language));

			return ResponseEntity.unprocessableEntity().body(error);
		}
	}

	@PutMapping(path = "/{invoiceId}/revert")
	public ResponseEntity<?> revertInvoice(
			@PathVariable("invoiceId") @ApiParam(required = true, value = "metasfreshId of the invoice to revert") final int invoiceRecordId)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(invoiceRecordId);

		if (invoiceId == null)
		{
			return ResponseEntity.notFound().build();
		}

		try
		{
			final Optional<JsonReverseInvoiceResponse> response = jsonInvoiceService.reverseInvoice(invoiceId);

			return response.isPresent()
					? ResponseEntity.ok(response.get())
					: ResponseEntity.notFound().build();
		}
		catch (final Exception ex)
		{
			final JsonError error = JsonError.ofSingleItem(JsonErrors.ofThrowable(ex, Env.getADLanguageOrBaseLanguage()));

			return ResponseEntity.unprocessableEntity().body(error);
		}
	}

	@ApiOperation("Create new invoice payment")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created new invoice payment"),
			@ApiResponse(code = 401, message = "You are not authorized to create a new invoice payment"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PostMapping("/payment")
	public ResponseEntity<?> createInvoicePayment(@NonNull @RequestBody final JsonInvoicePaymentCreateRequest request)
	{
		try
		{
			jsonInvoiceService.createInboundPaymentFromJson(request);

			return ResponseEntity.ok().build();
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();

			return ResponseEntity.unprocessableEntity()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}

	}
}
