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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse.JsonExternalReferenceLookupResponseBuilder;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponseItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceId;
import de.metas.externalreference.ExternalReferenceQuery.ExternalReferenceQueryBuilder;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.JsonExternalReferenceCreateRequest;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API + "/externalRef")
@RestController
@Profile(Profiles.PROFILE_App)
public class ExternalReferenceRestController
{
	private final ExternalReferenceRepository externalReferenceRepository;

	public ExternalReferenceRestController(@NonNull final ExternalReferenceRepository externalReferenceRepository)
	{
		this.externalReferenceRepository = externalReferenceRepository;
	}

	// we actually ask for info and don't change anything in metasfresh...that's why i'm having a GET...despite a GET shouldn't have a request body
	@GetMapping
	public JsonExternalReferenceLookupResponse lookup(@NonNull final JsonExternalReferenceLookupRequest request)
	{
		final IExternalSystem externalSystem = ExternalSystems.ofCode(request.getSystemName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));

		final ImmutableList.Builder<ExternalReferenceQuery> queries = ImmutableList.builder();

		final ExternalReferenceQueryBuilder queryBuilder = ExternalReferenceQuery.builder().externalSystem(externalSystem);
		for (final JsonExternalReferenceLookupItem item : request.getItems())
		{
			final IExternalReferenceType type = ExternalReferenceTypes.ofCode(item.getType())
					.orElseThrow(() -> new InvalidIdentifierException("type", item));

			final ExternalReferenceQuery query = queryBuilder
					.externalReferenceType(type)
					.externalReference(item.getId())
					.build();
			queries.add(query);
		}

		final ImmutableMap<ExternalReferenceQuery, ExternalReference> externalReferences = externalReferenceRepository.getExternalReferences(queries.build());

		final JsonExternalReferenceLookupResponseBuilder result = JsonExternalReferenceLookupResponse.builder();
		final ImmutableSet<Entry<ExternalReferenceQuery, ExternalReference>> entries = externalReferences.entrySet();
		for (final Entry<ExternalReferenceQuery, ExternalReference> entry : entries)
		{
			final ExternalReferenceQuery query = entry.getKey();

			final JsonExternalReferenceLookupItem item = JsonExternalReferenceLookupItem.builder()
					.id(query.getExternalReference())
					.type(query.getExternalReferenceType().getCode())
					.build();

			final ExternalReference externalReference = entry.getValue();
			final JsonExternalReferenceLookupResponseItem responseItem;
			if (ExternalReference.NULL.equals(externalReference))
			{
				responseItem = new JsonExternalReferenceLookupResponseItem(null);
			}
			else
			{
				responseItem = new JsonExternalReferenceLookupResponseItem(JsonMetasfreshId.of(externalReference.getRecordId()));
			}
			result.item(item, responseItem);
		}
		return result.build();
	}

	// note that we are not going to update references because they are not supposed to change
	@PostMapping
	public ResponseEntity insert(JsonExternalReferenceCreateRequest request)
	{
		final IExternalSystem externalSystem = ExternalSystems.ofCode(request.getSystemName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));

		final Map<JsonExternalReferenceLookupItem, JsonMetasfreshId> references = request.getReferences();

		final Set<Entry<JsonExternalReferenceLookupItem, JsonMetasfreshId>> entries = references.entrySet();
		for (final Entry<JsonExternalReferenceLookupItem, JsonMetasfreshId> entry : entries)
		{
			final JsonExternalReferenceLookupItem lookupItem = entry.getKey();
			final JsonMetasfreshId metasfreshId = entry.getValue();

			final IExternalReferenceType type = ExternalReferenceTypes.ofCode(lookupItem.getType())
					.orElseThrow(() -> new InvalidIdentifierException("type", lookupItem));

			final ExternalReference externalReference = ExternalReference.builder()
					// TODO Org
					.externalSystem(externalSystem)
					.externalReference(lookupItem.getId())
					.externalReferenceType(type)
					.build();
			externalReferenceRepository.save(externalReference);
		}


		return ResponseEntity.ok().build();
	}
}
