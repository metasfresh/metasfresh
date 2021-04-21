/*
 * #%L
 * de.metas.externalreference
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

package de.metas.externalreference.rest;

import de.metas.Profiles;
import de.metas.common.externalreference.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.JsonRequestExternalReferenceUpsert;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/externalRef",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/externalRef",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/externalRefs" })
@RestController
@Profile(Profiles.PROFILE_App)
public class ExternalReferenceRestController
{

private final ExternalReferenceRestControllerService externalReferenceRestControllerService;

	public ExternalReferenceRestController(@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService)
	{
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
	}

	// we actually ask for info and don't change anything in metasfresh...that's why would have a GET...despite a GET shouldn't have a request body
	@PutMapping("{orgCode}")
	public JsonExternalReferenceLookupResponse lookup(
			@ApiParam(required = true, value = "`AD_Org.Value` of the external references we are looking for") //
			@PathVariable("orgCode") //
			@NonNull final String orgCode,

			@RequestBody @NonNull final JsonExternalReferenceLookupRequest request)
	{
		return externalReferenceRestControllerService.performLookup(orgCode, request);
	}


	// note that we are not going to update references because they are not supposed to change
	@PostMapping("{orgCode}")
	public ResponseEntity<?> insert(

			@ApiParam(required = true, value = "`AD_Org.Value` of the external references we are inserting") //
			@PathVariable("orgCode") //
			@NonNull final String orgCode,

			@RequestBody @NonNull final JsonExternalReferenceCreateRequest request)
	{
		externalReferenceRestControllerService.performInsert(orgCode, request);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/upsert/{orgCode}")
	public ResponseEntity<?> upsert(
			@ApiParam(required = true, value = "`AD_Org.Value` of the external references we are upserting") //
			@PathVariable("orgCode") //
			@Nullable final String orgCode,

			@RequestBody @NonNull final JsonRequestExternalReferenceUpsert request)
	{
		externalReferenceRestControllerService.performUpsert(request, orgCode);

		return ResponseEntity.ok().build();
	}

}
