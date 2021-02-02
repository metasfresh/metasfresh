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
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.JsonExternalReferenceCreateRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API + "/externalRef")
@RestController
@Profile(Profiles.PROFILE_App)
public class ExternalReferenceRestController
{

	// we actually ask for info and don't change anything in metasfresh...that's why i'm having a GET...despite a GET shouldn't have a request body
	@GetMapping
	public JsonExternalReferenceLookupResponse lookup(JsonExternalReferenceLookupRequest request)
	{
		// suggestion: iterate the given request's items and create a union IQuery..but not in here.
		// instead, create a multiquery of GetReferencedIdRequests for the ExternalReferenceRepository and do the nitty-gritty query-builder stuff in there
		return JsonExternalReferenceLookupResponse.builder().build();
	}

	// note that we are not going to update references because they are not supposed to change
	@PostMapping
	public ResponseEntity insert(JsonExternalReferenceCreateRequest request)
	{
		return ResponseEntity.ok().build();
	}
}
