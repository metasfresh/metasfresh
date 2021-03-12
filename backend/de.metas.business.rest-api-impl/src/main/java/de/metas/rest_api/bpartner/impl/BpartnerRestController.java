package de.metas.rest_api.bpartner.impl;

import de.metas.Profiles;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.jsonpersister.JsonPersisterService;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.request.JsonRequestBankAccountsUpsert;
import de.metas.common.bpartner.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.response.JsonResponseBPartnerCompositeUpsert.JsonResponseBPartnerCompositeUpsertBuilder;
import de.metas.common.bpartner.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.response.JsonResponseComposite;
import de.metas.common.bpartner.response.JsonResponseCompositeList;
import de.metas.common.bpartner.response.JsonResponseContact;
import de.metas.common.bpartner.response.JsonResponseLocation;
import de.metas.common.bpartner.response.JsonResponseUpsert;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.common.rest_api.SyncAdvise.IfExists;
import de.metas.common.rest_api.SyncAdvise.IfNotExists;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.compiere.util.Env;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.common.rest_api.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;
import static de.metas.common.rest_api.SwaggerDocConstants.CONTACT_IDENTIFIER_DOC;
import static de.metas.common.rest_api.SwaggerDocConstants.LOCATION_IDENTIFIER_DOC;
import static de.metas.common.rest_api.SwaggerDocConstants.NEXT_DOC;
import static de.metas.common.rest_api.SwaggerDocConstants.SINCE_DOC;

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
 */

@RequestMapping(BpartnerRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
// the spelling "Bpartner" is to avoid swagger from spelling it "b-partner-rest.."
public class BpartnerRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/bpartner";
	public static final String ORG_CODE_PARAMETER_DOC = "`AD_Org.Value` of the BPartner identified by the bpartnerIdentifier";

	private final BPartnerEndpointService bpartnerEndpointService;
	private final JsonServiceFactory jsonServiceFactory;

	private final JsonRequestConsolidateService jsonRequestConsolidateService;

	public BpartnerRestController(
			@NonNull final BPartnerEndpointService bpartnerEndpointService,
			@NonNull final JsonServiceFactory jsonServiceFactory,
			@NonNull final JsonRequestConsolidateService jsonRequestConsolidateService)
	{
		this.bpartnerEndpointService = bpartnerEndpointService;
		this.jsonServiceFactory = jsonServiceFactory;
		this.jsonRequestConsolidateService = jsonRequestConsolidateService;
	}

	//
	@ApiOperation("The identified bpartner needs to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved bpartner"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("{bpartnerIdentifier}")
	public ResponseEntity<JsonResponseComposite> retrieveBPartner(

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr)
	{
		return retrieveBPartner(null, bpartnerIdentifierStr);
	}

	@GetMapping("{orgCode}/{bpartnerIdentifier}")
	public ResponseEntity<JsonResponseComposite> retrieveBPartner(

			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		final Optional<JsonResponseComposite> result = bpartnerEndpointService.retrieveBPartner(orgCode, bpartnerIdentifier);
		return okOrNotFound(result);
	}

	//
	@ApiOperation("The identified bpartner needs to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved location"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("{bpartnerIdentifier}/location/{locationIdentifier}")
	public ResponseEntity<JsonResponseLocation> retrieveBPartnerLocation(

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@ApiParam(required = true, value = LOCATION_IDENTIFIER_DOC) //
			@PathVariable("locationIdentifier") //
			@NonNull final String locationIdentifierStr)
	{
		return retrieveBPartnerLocation(null, bpartnerIdentifierStr, locationIdentifierStr);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved location"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("{orgCode}/{bpartnerIdentifier}/location/{locationIdentifier}")
	public ResponseEntity<JsonResponseLocation> retrieveBPartnerLocation(

			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@ApiParam(required = true, value = LOCATION_IDENTIFIER_DOC) //
			@PathVariable("locationIdentifier") //
			@NonNull final String locationIdentifierStr)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		final IdentifierString locationIdentifier = IdentifierString.of(locationIdentifierStr);

		final Optional<JsonResponseLocation> location = bpartnerEndpointService.retrieveBPartnerLocation(orgCode, bpartnerIdentifier, locationIdentifier);
		return okOrNotFound(location);
	}

	@ApiOperation("The identified bpartner needs to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved contact"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("{bpartnerIdentifier}/contact/{contactIdentifier}")
	public ResponseEntity<JsonResponseContact> retrieveBPartnerContact(

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@ApiParam(required = true, value = CONTACT_IDENTIFIER_DOC) //
			@PathVariable("contactIdentifier") //
			@NonNull final String contactIdentifierStr)
	{
		return retrieveBPartnerContact(null, bpartnerIdentifierStr, contactIdentifierStr);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved contact"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("{orgCode}/{bpartnerIdentifier}/contact/{contactIdentifier}")
	public ResponseEntity<JsonResponseContact> retrieveBPartnerContact(

			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@ApiParam(required = true, value = CONTACT_IDENTIFIER_DOC) //
			@PathVariable("contactIdentifier") //
			@NonNull final String contactIdentifierStr)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		final IdentifierString contactIdentifier = IdentifierString.of(contactIdentifierStr);

		final Optional<JsonResponseContact> contact = bpartnerEndpointService.retrieveBPartnerContact(orgCode, bpartnerIdentifier, contactIdentifier);
		return okOrNotFound(contact);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved bpartner(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "There is no page for the given 'next' value")
	})
	@GetMapping
	public ResponseEntity<JsonResponseCompositeList> retrieveBPartnersSince(

			@ApiParam(SINCE_DOC) //
			@RequestParam(name = "since", required = false) //
			@Nullable final Long epochTimestampMillis,

			@ApiParam(NEXT_DOC) //
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

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated bpartner(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@PutMapping("{orgCode}")
	public ResponseEntity<JsonResponseBPartnerCompositeUpsert> createOrUpdateBPartner(

			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
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
				jsonRequestConsolidateService.consolidateWithIdentifier(requestItem);

				final JsonResponseBPartnerCompositeUpsertItem persist = persister.persist(
						orgCode,
						requestItem,
						defaultSyncAdvise);
				response.responseItem(persist);
			}
		}
		return new ResponseEntity<>(response.build(), HttpStatus.CREATED);
	}

	@ApiOperation("The identified bpartner needs to be in the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated bpartner(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@PutMapping
	public ResponseEntity<JsonResponseBPartnerCompositeUpsert> createOrUpdateBPartner(
			@RequestBody @NonNull final JsonRequestBPartnerUpsert bpartnerUpsertRequest)
	{
		return createOrUpdateBPartner(null, bpartnerUpsertRequest);
	}

	//
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated location"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@ApiOperation("Create or update a locations for a particular bpartner.\n"
			+ "The identified bpartner needs to be in the current user's organisation.\n"
			+ "If a location exists, then those of its properties that are *not* specified are left untouched.")
	@PutMapping("{bpartnerIdentifier}/location")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateLocation(

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestLocationUpsert jsonLocation)
	{
		return createOrUpdateLocation(null, bpartnerIdentifierStr, jsonLocation);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated location"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@ApiOperation("Create or update a locations for a particular bpartner. If a location exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{orgCode}/{bpartnerIdentifier}/location")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateLocation(

			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestLocationUpsert jsonLocation)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);

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
			@ApiResponse(code = 201, message = "Successfully created or updated contact"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The bpartner you were trying to reach is not found"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@ApiOperation("Create or update a contacts for a particular bpartner.\n"
			+ "The identified bpartner needs to be in the current user's organisation.\n"
			+ "If a contact exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{bpartnerIdentifier}/contact")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateContact(

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestContactUpsert jsonContactUpsert)
	{
		return createOrUpdateContact(null, bpartnerIdentifierStr, jsonContactUpsert);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated contact"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The bpartner you were trying to reach is not found"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@ApiOperation("Create or update a contacts for a particular bpartner.\n"
			+ "If a contact exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{orgCode}/{bpartnerIdentifier}/contact")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateContact(

			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestContactUpsert jsonContactUpsert)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);

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
			@ApiResponse(code = 201, message = "Successfully created or updated bank account"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The bpartner you were trying to reach is not found"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@ApiOperation("Create or update a bank account for a particular bpartner.\n"
			+ "The identified bpartner needs to be in the current user's organisation.\n"
			+ "If a bank account exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{bpartnerIdentifier}/bankAccount")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateBankAccount(

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestBankAccountsUpsert bankAccounts)
	{
		return createOrUpdateBankAccount(null, bpartnerIdentifierStr, bankAccounts);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated bank account"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The bpartner you were trying to reach is not found"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@ApiOperation("Create or update a bank account for a particular bpartner. If a bank account exists, then its properties that are *not* specified are left untouched.")
	@PutMapping("{orgCode}/{bpartnerIdentifier}/bankAccount")
	public ResponseEntity<JsonResponseUpsert> createOrUpdateBankAccount(

			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,

			@RequestBody @NonNull final JsonRequestBankAccountsUpsert bankAccounts)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);

		final JsonPersisterService persister = jsonServiceFactory.createPersister();
		final Optional<JsonResponseUpsert> response = persister.persistForBPartner(
				orgCode,
				bpartnerIdentifier,
				bankAccounts,
				SyncAdvise.CREATE_OR_MERGE);

		return createdOrNotFound(response);
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
