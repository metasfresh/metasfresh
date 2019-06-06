package de.metas.rest_api.bpartner.impl;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.CONTACT_IDENTIFIER_DOC;
import static de.metas.rest_api.bpartner.SwaggerDocConstants.NEXT_DOC;
import static de.metas.rest_api.bpartner.SwaggerDocConstants.SINCE_DOC;

import javax.annotation.Nullable;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.rest_api.JsonPagingDescriptor;
import de.metas.rest_api.bpartner.ContactRestEndpoint;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonContactList;
import de.metas.rest_api.bpartner.JsonContactUpsertRequest;
import de.metas.rest_api.bpartner.JsonUpsertResponse;
import de.metas.rest_api.bpartner.JsonUpsertResponseItem;
import de.metas.util.time.SystemTime;
import io.swagger.annotations.ApiParam;
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

@RestController
@Profile(Profiles.PROFILE_App)
public class ContactRestController implements ContactRestEndpoint
{

	@Override
	public ResponseEntity<JsonContact> retrieveContact(
			@ApiParam(value = CONTACT_IDENTIFIER_DOC, allowEmptyValue = false) //
			@NonNull final String contactIdentifier)
	{
		final JsonContact mockContact = MockDataUtil.createMockContact(contactIdentifier);
		return ResponseEntity.ok(mockContact);
	}

	@Override
	public ResponseEntity<JsonContactList> retrieveContactsSince(

			@ApiParam(value = SINCE_DOC, allowEmptyValue = true) //
			@NonNull final Long epochTimestampMillis,

			@ApiParam(value = NEXT_DOC, allowEmptyValue = true) //
			@Nullable final String next)
	{
		JsonContactList list = JsonContactList
				.builder()
				.contact(MockDataUtil.createMockContact("c1"))
				.pagingDescriptor(JsonPagingDescriptor.builder()
						.pageSize(40)
						.totalSize(1)
						.resultTimestamp(SystemTime.millis())
						.build())
				.build();

		return ResponseEntity.ok(list);
	}

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
