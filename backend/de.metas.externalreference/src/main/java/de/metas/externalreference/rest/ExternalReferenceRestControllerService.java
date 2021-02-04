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
import de.metas.RestUtils;
import de.metas.common.externalreference.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponseItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.organization.OrgId;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;

import java.util.Map;
import java.util.Set;

public class ExternalReferenceRestControllerService
{
	private final ExternalReferenceRepository externalReferenceRepository;

	public ExternalReferenceRestControllerService(@NonNull final ExternalReferenceRepository externalReferenceRepository)
	{
		this.externalReferenceRepository = externalReferenceRepository;
	}

	JsonExternalReferenceLookupResponse performLookup(
			@NonNull final String orgCode,
			@NonNull final JsonExternalReferenceLookupRequest request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		final IExternalSystem externalSystem = ExternalSystems.ofCode(request.getSystemName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));

		final ImmutableList<ExternalReferenceQuery> queries = extractRepoQueries(request, orgId, externalSystem);

		final ImmutableMap<ExternalReferenceQuery, ExternalReference> externalReferences = externalReferenceRepository.getExternalReferences(queries);

		return createResponseFromRepoResult(externalReferences);
	}

	private ImmutableList<ExternalReferenceQuery> extractRepoQueries(
			@NonNull final JsonExternalReferenceLookupRequest request,
			@NonNull final OrgId orgId,
			@NonNull final IExternalSystem externalSystem)
	{
		final ImmutableList.Builder<ExternalReferenceQuery> queriesBuilder = ImmutableList.builder();

		final ExternalReferenceQuery.ExternalReferenceQueryBuilder queryBuilder = ExternalReferenceQuery.builder().externalSystem(externalSystem);
		for (final JsonExternalReferenceLookupItem item : request.getItems())
		{
			final IExternalReferenceType type = ExternalReferenceTypes.ofCode(item.getType())
					.orElseThrow(() -> new InvalidIdentifierException("type", item));

			final ExternalReferenceQuery query = queryBuilder
					.orgId(orgId)
					.externalReferenceType(type)
					.externalReference(item.getId())
					.build();
			queriesBuilder.add(query);
		}
		final ImmutableList<ExternalReferenceQuery> queries = queriesBuilder.build();
		return queries;
	}

	private JsonExternalReferenceLookupResponse createResponseFromRepoResult(
			@NonNull final ImmutableMap<ExternalReferenceQuery, ExternalReference> externalReferences)
	{
		final JsonExternalReferenceLookupResponse.JsonExternalReferenceLookupResponseBuilder result = JsonExternalReferenceLookupResponse.builder();
		final ImmutableSet<Map.Entry<ExternalReferenceQuery, ExternalReference>> entries = externalReferences.entrySet();
		for (final Map.Entry<ExternalReferenceQuery, ExternalReference> entry : entries)
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

	void performInsert(@NonNull final String orgCode, @NonNull final JsonExternalReferenceCreateRequest request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		final IExternalSystem externalSystem = ExternalSystems.ofCode(request.getSystemName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));

		final Map<JsonExternalReferenceLookupItem, JsonMetasfreshId> references = request.getReferences();

		final Set<Map.Entry<JsonExternalReferenceLookupItem, JsonMetasfreshId>> entries = references.entrySet();
		for (final Map.Entry<JsonExternalReferenceLookupItem, JsonMetasfreshId> entry : entries)
		{
			final JsonExternalReferenceLookupItem lookupItem = entry.getKey();
			final JsonMetasfreshId metasfreshId = entry.getValue();

			final IExternalReferenceType type = ExternalReferenceTypes.ofCode(lookupItem.getType())
					.orElseThrow(() -> new InvalidIdentifierException("type", lookupItem));

			final ExternalReference externalReference = ExternalReference.builder()
					.orgId(orgId)
					.externalSystem(externalSystem)
					.externalReference(lookupItem.getId())
					.externalReferenceType(type)
					.build();
			externalReferenceRepository.save(externalReference);
		}
	}
}
