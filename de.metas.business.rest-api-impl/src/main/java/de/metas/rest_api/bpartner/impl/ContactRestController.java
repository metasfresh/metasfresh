package de.metas.rest_api.bpartner.impl;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.CONTACT_IDENTIFIER_DOC;
import static de.metas.rest_api.bpartner.SwaggerDocConstants.NEXT_DOC;
import static de.metas.rest_api.bpartner.SwaggerDocConstants.SINCE_DOC;

import java.util.Optional;

import javax.annotation.Nullable;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.rest_api.bpartner.ContactRestEndpoint;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonContactList;
import de.metas.rest_api.bpartner.JsonContactUpsertRequest;
import de.metas.rest_api.bpartner.JsonUpsertResponse;
import de.metas.rest_api.bpartner.JsonUpsertResponseItem;
import de.metas.util.rest.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiParam;
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
 */

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API + "/contact")
@RestController
@Profile(Profiles.PROFILE_App)
public class ContactRestController implements ContactRestEndpoint
{

	private final IBPartnerEndpointService bPartnerEndpointservice;

	public ContactRestController(@NonNull final IBPartnerEndpointService bpIbPartnerEndpointservice)
	{
		this.bPartnerEndpointservice = bpIbPartnerEndpointservice;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved contact"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("{contactIdentifier}")
	@Override
	public ResponseEntity<JsonContact> retrieveContact(
			@ApiParam(CONTACT_IDENTIFIER_DOC) //
			@PathVariable("contactIdentifier") //
			@NonNull final String contactIdentifier)
	{
		final JsonContact mockContact = bPartnerEndpointservice.retrieveContact(contactIdentifier);
		return ResponseEntity.ok(mockContact);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved contact(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "There is no page for the given 'next' value")
	})
	@GetMapping
	@Override
	public ResponseEntity<JsonContactList> retrieveContactsSince(

			@ApiParam(value = SINCE_DOC, allowEmptyValue = true) //
			@RequestParam(name = "since", required = false) //
			@NonNull final Long epochTimestampMillis,

			@ApiParam(value = NEXT_DOC, allowEmptyValue = true) //
			@RequestParam(name = "next", required = false) //
			@Nullable final String next)
	{
		final Optional<JsonContactList> list = bPartnerEndpointservice.retrieveContactsSince(epochTimestampMillis, next);
		if (list.isPresent())
		{
			return ResponseEntity.ok(list.get());
		}
		return new ResponseEntity<JsonContactList>(
				(JsonContactList)null,
				HttpStatus.NOT_FOUND);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated contact"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
	})
	@PostMapping
	@Override
	public ResponseEntity<JsonUpsertResponse> createOrUpdateContact(
			// the requestBody annotation needs to be present it here; otherwise, at least swagger doesn't get it
			@RequestBody @NonNull final JsonContactUpsertRequest contacts)
	{
		final JsonUpsertResponse response = JsonUpsertResponse.builder()
				.responseItem(JsonUpsertResponseItem.builder().build())
				.build();

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
