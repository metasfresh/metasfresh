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

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.rest_api.v2.invoice.JsonInvoicePaymentCreateRequest;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.ManualInvoice;
import de.metas.logging.LogManager;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import de.metas.rest_api.invoicecandidates.response.JsonReverseInvoiceResponse;
import de.metas.rest_api.invoicecandidates.v2.request.JsonCheckInvoiceCandidatesStatusRequest;
import de.metas.rest_api.invoicecandidates.v2.request.JsonCloseInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.v2.request.JsonCreateInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.v2.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.invoice.impl.JSONInvoiceInfoResponse;
import de.metas.rest_api.v2.invoice.impl.JsonInvoiceService;
<<<<<<< HEAD
import de.metas.rest_api.v2.invoicecandidates.impl.CheckInvoiceCandidatesStatusService;
import de.metas.rest_api.v2.invoicecandidates.impl.CloseInvoiceCandidatesService;
import de.metas.rest_api.v2.invoicecandidates.impl.CreateInvoiceCandidatesService;
import de.metas.rest_api.v2.invoicecandidates.impl.EnqueueForInvoicingService;
=======
import de.metas.rest_api.v2.invoice.review.JsonInvoiceReviewService;
>>>>>>> a76b8c750bd (create C_Invoice_Review (#15076))
import de.metas.rest_api.v2.ordercandidates.impl.MasterdataProvider;
import de.metas.sectionCode.SectionCodeService;
import de.metas.security.permissions2.PermissionServiceFactories;
import de.metas.security.permissions2.PermissionServiceFactory;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

	private final PermissionServiceFactory permissionServiceFactory;
	private final BpartnerRestController bpartnerRestController;
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final JsonRetrieverService jsonRetrieverService;
	private final SectionCodeService sectionCodeService;

	private final JsonInvoiceReviewService jsonInvoiceReviewService;

	public InvoicesRestController(
			@NonNull final CreateInvoiceCandidatesService createInvoiceCandidatesService,
			@NonNull final CheckInvoiceCandidatesStatusService invoiceCandidateInfoService,
			@NonNull final EnqueueForInvoicingService enqueueForInvoicingService,
			@NonNull final CloseInvoiceCandidatesService closeInvoiceCandidatesService,
			@NonNull final JsonInvoiceService jsonInvoiceService,
			@NonNull final BpartnerRestController bpartnerRestController,
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final JsonServiceFactory jsonServiceFactory,
			@NonNull final SectionCodeService sectionCodeService,
			@NonNull final JsonInvoiceReviewService jsonInvoiceReviewService)
	{
		this.createInvoiceCandidatesService = createInvoiceCandidatesService;
		this.checkInvoiceCandidatesStatusService = invoiceCandidateInfoService;
		this.enqueueForInvoicingService = enqueueForInvoicingService;
		this.closeInvoiceCandidatesService = closeInvoiceCandidatesService;
		this.jsonInvoiceService = jsonInvoiceService;
		this.bpartnerRestController = bpartnerRestController;
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
		this.sectionCodeService = sectionCodeService;
		this.jsonInvoiceReviewService = jsonInvoiceReviewService;
		this.permissionServiceFactory = PermissionServiceFactories.currentContext();
	}

	@Operation(summary = "Create new invoice candidates")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created new invoice candidate(s)"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create new invoice candidates"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
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
			@ApiResponse(responseCode = "200", description = "Successfully retrieved status for all matching invoice candidates"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to retrieve the invoice candidates' status"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden")
	})
	public ResponseEntity<JsonCheckInvoiceCandidatesStatusResponse> checkInvoiceCandidatesStatus(@RequestBody @NonNull final JsonCheckInvoiceCandidatesStatusRequest request)
	{
		final JsonCheckInvoiceCandidatesStatusResponse response = checkInvoiceCandidatesStatusService.getStatusForInvoiceCandidates(request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Enqueues invoice candidates for invoicing")
	@PostMapping(path = "/enqueueForInvoicing", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JsonEnqueueForInvoicingResponse> enqueueForInvoicing(@RequestBody @NonNull final JsonEnqueueForInvoicingRequest request)
	{
		final JsonEnqueueForInvoicingResponse response = enqueueForInvoicingService.enqueueForInvoicing(request);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping(path = "/close", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully closed all matching invoice candidates"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to close the invoice candidates"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden")
	})
	public ResponseEntity<JsonCloseInvoiceCandidatesResponse> closeInvoiceCandidates(@RequestBody @NonNull final JsonCloseInvoiceCandidatesRequest request)
	{
		final JsonCloseInvoiceCandidatesResponse response = closeInvoiceCandidatesService.closeInvoiceCandidates(request);
		return ResponseEntity.ok(response);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "PDF retrieved for invoice"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to see the invoice PDF"),
			@ApiResponse(responseCode = "404", description = "No archive found for the invoice")
	})
	@GetMapping(path = "/{invoiceId}/pdf")
	public ResponseEntity<byte[]> getInvoicePDF(
			@Parameter(required = true, description = "metasfreshId of the invoice to get the PDF of") //
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
			@ApiResponse(responseCode = "200", description = "Invoice info retrieved"),
			@ApiResponse(responseCode = "401", description = "You are not authorized"),
			@ApiResponse(responseCode = "404", description = "No Invoice found"),
			@ApiResponse(responseCode = "422", description = "An error happened during Invoice info retrieval"),
	})
	@GetMapping(path = "{invoiceId}/invoiceInfo", produces = "application/json")
	public ResponseEntity<?> getInvoiceInfo(
			@Parameter(required = true, description = "metasfreshId of the invoice")
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
			@PathVariable("invoiceId") @Parameter(required = true, description = "metasfreshId of the invoice to revert") final int invoiceRecordId)
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

	@Operation(summary = "Create new invoice payment")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created new invoice payment"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create a new invoice payment"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})
	@PostMapping("/payment")
	public ResponseEntity<?> createInvoicePayment(@NonNull @RequestBody final JsonInvoicePaymentCreateRequest request)
	{
		try
		{
			jsonInvoiceService.createPaymentFromJson(request);

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

	@Operation(summary = "Create new invoice")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created new invoice"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create a new invoice"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})
	@PostMapping("/new")
	public ResponseEntity<JsonCreateInvoiceResponse> createInvoice(@NonNull @RequestBody final JsonCreateInvoiceRequest request)
	{
		try
		{
			final MasterdataProvider masterdataProvider = MasterdataProvider.builder()
					.permissionService(permissionServiceFactory.createPermissionService())
					.bpartnerRestController(bpartnerRestController)
					.externalReferenceRestControllerService(externalReferenceRestControllerService)
					.jsonRetrieverService(jsonRetrieverService)
					.sectionCodeService(sectionCodeService)
					.build();

			final ManualInvoice invoice = jsonInvoiceService.createInvoice(request, masterdataProvider);

			return ResponseEntity.ok().body(JsonCreateInvoiceResponse.builder()
					.result(JsonCreateInvoiceResponseResult.builder()
							.documentNo(invoice.getDocNumber())
							.build())
					.build());
		}
		catch (final Exception ex)
		{
			logger.error("failed to create invoice", ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();

			return ResponseEntity.unprocessableEntity()
					.body(JsonCreateInvoiceResponse.builder()
							.errors(ImmutableList.of(JsonErrors.ofThrowable(ex, adLanguage)))
							.build());
		}
	}

	@PostMapping("/docReview")
	public ResponseEntity<JsonCreateInvoiceReviewResponse> createReview(@NonNull @RequestBody final JsonInvoiceReviewUpsertItem jsonInvoiceReviewUpsertItem)
	{
		try
		{
			return jsonInvoiceReviewService.upsert(jsonInvoiceReviewUpsertItem)
					.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
		}
		catch (final Exception ex)
		{
			logger.error("Failed to create invoice review", ex);

			return ResponseEntity.unprocessableEntity()
					.body(JsonCreateInvoiceReviewResponse.builder()
							.errors(ImmutableList.of(JsonErrors.ofThrowable(ex, Env.getADLanguageOrBaseLanguage())))
							.build());
		}
	}
}
