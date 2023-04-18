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

package de.metas.rest_api.v2.bpartner;

import de.metas.Profiles;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestBankAccountsUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert.JsonResponseBPartnerCompositeUpsertBuilder;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseCompositeList;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseCreditLimitDelete;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.bpartner.v2.response.JsonResponseUpsert;
import de.metas.common.product.v2.response.JsonResponseProductBPartner;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.SyncAdvise.IfExists;
import de.metas.common.rest_api.v2.SyncAdvise.IfNotExists;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister.JsonPersisterService;
import de.metas.rest_api.v2.bpartner.creditLimit.CreditLimitService;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.compiere.util.Env;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.CONTACT_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.LOCATION_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.NEXT_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.SINCE_DOC;

@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/bpartner" })
@RestController
@Profile(Profiles.PROFILE_App)
// the spelling "Bpartner" is to avoid swagger from spelling it "b-partner-rest.."
public class BpartnerRestController
{
	public static final String ORG_CODE_PARAMETER_DOC = "`AD_Org.Value` of the BPartner identified by the bpartnerIdentifier";

	private final BPartnerEndpointService bpartnerEndpointService;
	private final JsonServiceFactory jsonServiceFactory;

	private final JsonRequestConsolidateService jsonRequestConsolidateService;
	private final CreditLimitService creditLimitService;

	public BpartnerRestController(
			@NonNull final BPartnerEndpointService bpartnerEndpointService,
			@NonNull final JsonServiceFactory jsonServiceFactory,
			@NonNull final JsonRequestConsolidateService jsonRequestConsolidateService,
			@NonNull final CreditLimitService creditLimitService)
	{
		this.bpartnerEndpointService = bpartnerEndpointService;
		this.jsonServiceFactory = jsonServiceFactory;
		this.jsonRequestConsolidateService = jsonRequestConsolidateService;
		this.creditLimitService = creditLimitService;
	}

	//
	@Operation(summary = "The identified bpartner needs to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved bpartner"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{bpartnerIdentifier}")
	public ResponseEntity<JsonResponseComposite> retrieveBPartner(

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr)
	{
		return retrieveBPartner(null, bpartnerIdentifierStr);
	}

	@GetMapping("{orgCode}/{bpartnerIdentifier}")
	public ResponseEntity<JsonResponseComposite> retrieveBPartner(

			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr)
	{
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);
		final Optional<JsonResponseComposite> result = bpartnerEndpointService.retrieveBPartner(orgCode, bpartnerIdentifier);
		return okOrNotFound(result);
	}

	//
	@Operation(summary = "The identified bpartner needs to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved location"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{bpartnerIdentifier}/location/{locationIdentifier}")
	public ResponseEntity<JsonResponseLocation> retrieveBPartnerLocation(

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@Parameter(required = true, description = LOCATION_IDENTIFIER_DOC) //
			@PathVariable("locationIdentifier") //
			@NonNull final String locationIdentifierStr)
	{
		return retrieveBPartnerLocation(null, bpartnerIdentifierStr, locationIdentifierStr);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved location"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{orgCode}/{bpartnerIdentifier}/location/{locationIdentifier}")
	public ResponseEntity<JsonResponseLocation> retrieveBPartnerLocation(

			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@Parameter(required = true, description = LOCATION_IDENTIFIER_DOC) //
			@PathVariable("locationIdentifier") //
			@NonNull final String locationIdentifierStr)
	{
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);
		final ExternalIdentifier locationIdentifier = ExternalIdentifier.of(locationIdentifierStr);

		final Optional<JsonResponseLocation> location = bpartnerEndpointService.retrieveBPartnerLocation(orgCode, bpartnerIdentifier, locationIdentifier);
		return okOrNotFound(location);
	}

	@Operation(summary = "The identified bpartner needs to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved contact"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{bpartnerIdentifier}/contact/{contactIdentifier}")
	public ResponseEntity<JsonResponseContact> retrieveBPartnerContact(

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@Parameter(required = true, description = CONTACT_IDENTIFIER_DOC) //
			@PathVariable("contactIdentifier") //
			@NonNull final String contactIdentifierStr)
	{
		return retrieveBPartnerContact(null, bpartnerIdentifierStr, contactIdentifierStr);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved contact"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{orgCode}/{bpartnerIdentifier}/contact/{contactIdentifier}")
	public ResponseEntity<JsonResponseContact> retrieveBPartnerContact(

			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@Parameter(required = true, description = CONTACT_IDENTIFIER_DOC) //
			@PathVariable("contactIdentifier") //
			@NonNull final String contactIdentifierStr)
	{
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);
		final ExternalIdentifier contactIdentifier = ExternalIdentifier.of(contactIdentifierStr);

		final Optional<JsonResponseContact> contact = bpartnerEndpointService.retrieveBPartnerContact(orgCode, bpartnerIdentifier, contactIdentifier);
		return okOrNotFound(contact);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved bpartner(s)"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "There is no page for the given 'next' value")
	})
	@GetMapping
	public ResponseEntity<JsonResponseCompositeList> retrieveBPartnersSince(

			@Parameter(description = SINCE_DOC) //
			@RequestParam(name = "since", required = false) //
			@Nullable final Long epochTimestampMillis,

			@Parameter(description = NEXT_DOC) //
			@RequestParam(name = "next", required = false) //
			@Nullable final String next)
	{
		try
		{
			final Optional<JsonResponseCompositeList> result = bpartnerEndpointService.retrieveBPartnersSince(epochTimestampMillis, next);
			return okOrNotFound(result);
		}
		catch (final Exception ex)
		{
			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(JsonResponseCompositeList.error(JsonErrors.ofThrowable(ex, adLanguage)));
		}
	}

	@Operation(summary = "The identified bpartner products need to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved bpartner products"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{bpartnerIdentifier}/products")
	public ResponseEntity<JsonResponseProductBPartner> retrieveBPartnerProducts(

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr)
	{
		return retrieveBPartnerProducts(null, bpartnerIdentifierStr);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved bpartner products"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{orgCode}/{bpartnerIdentifier}/products")
	public ResponseEntity<JsonResponseProductBPartner> retrieveBPartnerProducts(

			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr)
	{
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);

		final JsonResponseProductBPartner bPartnerProduct = bpartnerEndpointService.retrieveBPartnerProduct(orgCode, bpartnerIdentifier);
		return ResponseEntity.ok(bPartnerProduct);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated bpartner(s)"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@PutMapping("{orgCode}")
	public ResponseEntity<JsonResponseBPartnerCompositeUpsert> createOrUpdateBPartner(

			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@RequestBody @NonNull final JsonRequestBPartnerUpsert bpartnerUpsertRequest)
	{
		final JsonPersisterService persister = jsonServiceFactory.createPersister();

		final SyncAdvise defaultSyncAdvise = bpartnerUpsertRequest.getSyncAdvise();

		final JsonResponseBPartnerCompositeUpsertBuilder response = JsonResponseBPartnerCompositeUpsert.builder();

		for (final JsonRequestBPartnerUpsertItem requestItem : bpartnerUpsertRequest.getRequestItems())
		{
			try (final MDCCloseable ignored = MDC.putCloseable("bpartnerIdentifier", requestItem.getBpartnerIdentifier()))
			{
				JsonRequestConsolidateService.consolidateWithOrg(requestItem, orgCode);

				jsonRequestConsolidateService.consolidateWithIdentifier(requestItem);

				final JsonResponseBPartnerCompositeUpsertItem persist = persister.persist(
						requestItem,
						defaultSyncAdvise);
				response.responseItem(persist);
			}
		}
		return new ResponseEntity<>(response.build(), HttpStatus.CREATED);
	}

	@Operation(summary = "The identified bpartner needs to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated bpartner(s)"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@PutMapping
	public ResponseEntity<JsonResponseBPartnerCompositeUpsert> createOrUpdateBPartner(
			@RequestBody @NonNull final JsonRequestBPartnerUpsert bpartnerUpsertRequest)
	{
		return createOrUpdateBPartner(null, bpartnerUpsertRequest);
	}

	//
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated location"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@Operation(summary = "Create or update a locations for a particular bpartner.\n"
			+ "The identified bpartner needs to be in the current user's organisation.\n"
			+ "If a location exists, then those of its properties that are *not* specified are left untouched.")
	@PutMapping("{bpartnerIdentifier}/location")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateLocation(

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestLocationUpsert jsonLocation)
	{
		return createOrUpdateLocation(null, bpartnerIdentifierStr, jsonLocation);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated location"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@Operation(summary = "Create or update a locations for a particular bpartner. If a location exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{orgCode}/{bpartnerIdentifier}/location")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateLocation(

			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestLocationUpsert jsonLocation)
	{
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);

		final JsonPersisterService persister = jsonServiceFactory.createPersister();

		jsonRequestConsolidateService.consolidateWithIdentifier(jsonLocation);
		final Optional<JsonResponseUpsert> jsonLocationId = persister.persistForBPartner(
				orgCode,
				bpartnerIdentifier,
				jsonLocation,
				SyncAdvise.builder().ifExists(IfExists.UPDATE_MERGE).ifNotExists(IfNotExists.CREATE).build());

		return createdOrNotFound(jsonLocationId);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated contact"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The bpartner you were trying to reach is not found"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@Operation(summary = "Create or update a contacts for a particular bpartner.\n"
			+ "The identified bpartner needs to be in the current user's organisation.\n"
			+ "If a contact exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{bpartnerIdentifier}/contact")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateContact(

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestContactUpsert jsonContactUpsert)
	{
		return createOrUpdateContact(null, bpartnerIdentifierStr, jsonContactUpsert);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated contact"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The bpartner you were trying to reach is not found"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@Operation(summary = "Create or update a contacts for a particular bpartner.\n"
			+ "If a contact exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{orgCode}/{bpartnerIdentifier}/contact")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateContact(

			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestContactUpsert jsonContactUpsert)
	{
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);

		final JsonPersisterService persister = jsonServiceFactory.createPersister();

		jsonRequestConsolidateService.consolidateWithIdentifier(jsonContactUpsert);

		final Optional<JsonResponseUpsert> response = persister.persistForBPartner(
				orgCode,
				bpartnerIdentifier,
				jsonContactUpsert,
				SyncAdvise.CREATE_OR_MERGE);

		return createdOrNotFound(response);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated bank account"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The bpartner you were trying to reach is not found"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@Operation(summary = "Create or update a bank account for a particular bpartner.\n"
			+ "The identified bpartner needs to be in the current user's organisation.\n"
			+ "If a bank account exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{bpartnerIdentifier}/bankAccount")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateBankAccount(

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestBankAccountsUpsert bankAccounts)
	{
		return createOrUpdateBankAccount(null, bpartnerIdentifierStr, bankAccounts);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated bank account"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The bpartner you were trying to reach is not found"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@Operation(summary = "Create or update a bank account for a particular bpartner. If a bank account exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{orgCode}/{bpartnerIdentifier}/bankAccount")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateBankAccount(

			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestBankAccountsUpsert bankAccounts)
	{
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);

		final JsonPersisterService persister = jsonServiceFactory.createPersister();
		final Optional<JsonResponseUpsert> response = persister.persistForBPartner(
				orgCode,
				bpartnerIdentifier,
				bankAccounts,
				SyncAdvise.CREATE_OR_MERGE);

		return createdOrNotFound(response);
	}

	@DeleteMapping("{orgCode}/{bpartnerIdentifier}/credit-limit")
	public ResponseEntity<JsonResponseCreditLimitDelete> deleteExistingForBPartner(
			@PathVariable("orgCode") //
			@Nullable final String orgCode,

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifier,

			@Parameter(description = "If true, processed records will also be deleted, otherwise, they will be ignored.", example = "false") //
			@RequestParam("includingProcessed")
			final boolean includingProcessed)
	{
		final JsonResponseCreditLimitDelete responseCreditLimitDelete = creditLimitService.deleteExistingRecordsForBPartnerAndOrg(bpartnerIdentifier, orgCode, includingProcessed);

		return ResponseEntity.ok(responseCreditLimitDelete);
	}

	private static <T> ResponseEntity<T> okOrNotFound(@NonNull final Optional<T> optionalResult)
	{
		return optionalResult
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	private static <T> ResponseEntity<T> createdOrNotFound(@NonNull final Optional<T> optionalResult)
	{
		return optionalResult
				.map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
