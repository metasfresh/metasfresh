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
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
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
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Component
public class ExternalReferenceRestControllerService
{
	private final ExternalReferenceRepository externalReferenceRepository;
	private final ExternalSystems externalSystems;
	private final ExternalReferenceTypes externalReferenceTypes;

	public ExternalReferenceRestControllerService(
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final ExternalSystems externalSystems,
			@NonNull final ExternalReferenceTypes externalReferenceTypes)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.externalSystems = externalSystems;
		this.externalReferenceTypes = externalReferenceTypes;
	}

	JsonExternalReferenceLookupResponse performLookup(
			@NonNull final String orgCode,
			@NonNull final JsonExternalReferenceLookupRequest request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		final IExternalSystem externalSystem = externalSystems.ofCode(request.getSystemName().getName())
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
			final IExternalReferenceType type = externalReferenceTypes.ofCode(item.getType())
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

			final JsonExternalReferenceLookupItem lookupItem = JsonExternalReferenceLookupItem.builder()
					.id(query.getExternalReference())
					.type(query.getExternalReferenceType().getCode())
					.build();

			final ExternalReference externalReference = entry.getValue();
			final JsonExternalReferenceItem responseItem;
			if (ExternalReference.NULL.equals(externalReference))
			{
				responseItem = JsonExternalReferenceItem.of(lookupItem);
			}
			else
			{
				responseItem = JsonExternalReferenceItem.of(lookupItem, JsonMetasfreshId.of(externalReference.getRecordId()));
			}
			result.item(responseItem);
		}
		return result.build();
	}

	public void performInsert(@Nullable final String orgCode, @NonNull final JsonExternalReferenceCreateRequest request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		final IExternalSystem externalSystem = externalSystems.ofCode(request.getSystemName().getName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));

		final List<JsonExternalReferenceItem> references = request.getItems();

		for (final JsonExternalReferenceItem reference : references)
		{
			final JsonExternalReferenceLookupItem lookupItem = reference.getLookupItem();
			final JsonMetasfreshId metasfreshId = reference.getMetasfreshId();

			final IExternalReferenceType type = externalReferenceTypes.ofCode(lookupItem.getType())
					.orElseThrow(() -> new InvalidIdentifierException("type", lookupItem));

			final ExternalReference externalReference = ExternalReference.builder()
					.orgId(orgId)
					.externalSystem(externalSystem)
					.externalReference(lookupItem.getId())
					.externalReferenceType(type)
					.recordId(metasfreshId.getValue())
					.build();
			externalReferenceRepository.save(externalReference);
		}
	}
}
