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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.RestUtils;
import de.metas.common.externalreference.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalreference.JsonSingleExternalReferenceCreateReq;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalreference.ExternalBusinessKey;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.GetExternalReferenceByRecordIdReq;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Check;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Component
public class ExternalReferenceRestControllerService
{
	private static final Logger logger = LogManager.getLogger(ExternalReferenceRestControllerService.class);

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

	public JsonExternalReferenceLookupResponse performLookup(
			@Nullable OrgId orgId,
			@NonNull final JsonExternalReferenceLookupRequest request)
	{
		orgId = orgId != null ? orgId : Env.getOrgId();

		final IExternalSystem externalSystem = externalSystems.ofCode(request.getSystemName().getName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));

		final ImmutableSet<ExternalReferenceQuery> queries = extractRepoQueries(request, orgId, externalSystem);

		final ImmutableMap<ExternalReferenceQuery, ExternalReference> externalReferences = externalReferenceRepository.getExternalReferences(queries);

		return createResponseFromRepoResult(externalReferences);
	}

	public JsonExternalReferenceLookupResponse performLookup(
			@Nullable final String orgCode,
			@NonNull final JsonExternalReferenceLookupRequest request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		return performLookup(orgId, request);
	}

	private ImmutableSet<ExternalReferenceQuery> extractRepoQueries(
			@NonNull final JsonExternalReferenceLookupRequest request,
			@NonNull final OrgId orgId,
			@NonNull final IExternalSystem externalSystem)
	{
		final ImmutableSet.Builder<ExternalReferenceQuery> queriesBuilder = ImmutableSet.builder();

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
		return queriesBuilder.build();
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
				responseItem = JsonExternalReferenceItem.builder()
						.lookupItem(lookupItem)
						.metasfreshId(JsonMetasfreshId.of(externalReference.getRecordId()))
						.version(externalReference.getVersion())
						.build();

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
					.version(reference.getVersion())
					.externalReferenceUrl(reference.getExternalReferenceUrl())
					.build();
			externalReferenceRepository.save(externalReference);
		}
	}

	public void performInsertIfMissing(
			@NonNull final JsonSingleExternalReferenceCreateReq externalRefCreateReq,
			@Nullable final String orgCode)
	{
		final JsonExternalReferenceLookupRequest externalRefLookupReq = JsonExternalReferenceLookupRequest.builder()
				.systemName(externalRefCreateReq.getSystemName())
				.item(externalRefCreateReq.getExternalReferenceItem().getLookupItem())
				.build();

		final JsonExternalReferenceLookupResponse lookupResponse = performLookup(orgCode, externalRefLookupReq);

		if (lookupResponse != null)
		{
			final boolean isExternalRefAlreadyCreated = lookupResponse.getItems()
					.stream()
					.filter(item -> item.getLookupItem().equals(externalRefCreateReq.getExternalReferenceItem().getLookupItem()))
					.findFirst()
					.map(externalRefItem -> externalRefItem.getMetasfreshId() != null)
					.orElse(false);

			if (isExternalRefAlreadyCreated)
			{
				logger.debug("insertExternalReferenceIfMissing: There is already an external reference stored for: " + externalRefCreateReq);
				return; // nothing to do for now, but it might be a good idea to support updates on external ref
			}
		}

		final JsonExternalReferenceCreateRequest jsonExternalReferenceCreateRequest = JsonExternalReferenceCreateRequest.builder()
				.systemName(externalRefCreateReq.getSystemName())
				.item(externalRefCreateReq.getExternalReferenceItem())
				.build();

		performInsert(orgCode, jsonExternalReferenceCreateRequest);
	}

	@NonNull
	public Optional<JsonMetasfreshId> getJsonMetasfreshIdFromExternalReference(
			@NonNull final String orgCode,
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		return getJsonMetasfreshIdFromExternalReference(orgId,externalIdentifier,externalReferenceType);
	}

	@NonNull
	public Optional<JsonMetasfreshId> getJsonMetasfreshIdFromExternalReference(
			@Nullable final OrgId orgId,
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		final OrgId orgIdToUse = CoalesceUtil.coalesceSuppliers(() -> orgId, () -> Env.getOrgId());

		final JsonExternalSystemName externalSystemName = JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem());

		final JsonExternalReferenceLookupRequest lookupRequest = JsonExternalReferenceLookupRequest.builder()
				.systemName(externalSystemName)
				.item(JsonExternalReferenceLookupItem.builder()
							  .type(externalReferenceType.getCode())
							  .id(externalIdentifier.asExternalValueAndSystem().getValue())
							  .build())
				.build();

		final JsonExternalReferenceLookupResponse lookupResponse = performLookup(orgIdToUse, lookupRequest);
		return lookupResponse.getItems()
				.stream()
				.map(JsonExternalReferenceItem::getMetasfreshId)
				.filter(Objects::nonNull)
				.findFirst();
	}

	@NonNull
	public Optional<JsonMetasfreshId> getJsonMetasfreshIdFromExternalBusinessKey(
			@Nullable final OrgId orgId,
			@NonNull final ExternalBusinessKey externalBusinessKey,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		final OrgId orgIdToUse = CoalesceUtil.coalesceSuppliers(() -> orgId, Env::getOrgId);

		final JsonExternalSystemName externalSystemName = JsonExternalSystemName.of(externalBusinessKey.asExternalValueAndSystem().getExternalSystem());

		final JsonExternalReferenceLookupRequest lookupRequest = JsonExternalReferenceLookupRequest.builder()
				.systemName(externalSystemName)
				.item(JsonExternalReferenceLookupItem.builder()
							  .type(externalReferenceType.getCode())
							  .id(externalBusinessKey.asExternalValueAndSystem().getValue())
							  .build())
				.build();

		final JsonExternalReferenceLookupResponse lookupResponse = performLookup(orgIdToUse, lookupRequest);
		return lookupResponse.getItems()
				.stream()
				.map(JsonExternalReferenceItem::getMetasfreshId)
				.filter(Objects::nonNull)
				.findFirst();
	}

	public void performUpsert(@NonNull final JsonRequestExternalReferenceUpsert request, @Nullable final String orgCode)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(orgCode);
		final ExternalReference externalReferenceCandidate = mapJsonToExternalReference(request, orgId);

		final GetExternalReferenceByRecordIdReq getExternalRefRequest = GetExternalReferenceByRecordIdReq.builder()
				.externalReferenceType(externalReferenceCandidate.getExternalReferenceType())
				.externalSystem(externalReferenceCandidate.getExternalSystem())
				.recordId(externalReferenceCandidate.getRecordId())
				.build();

		final ExternalReference externalReferenceToUpsert = externalReferenceRepository.getExternalReferenceByMFReference(getExternalRefRequest)
				.map(existingRecord -> syncCandidateWithExisting(externalReferenceCandidate, existingRecord))
				.orElse(externalReferenceCandidate);

		externalReferenceRepository.save(externalReferenceToUpsert);
	}

	@NonNull
	public Optional<MetasfreshId> resolveExternalReference(
			@Nullable final OrgId orgId,
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		final Optional<JsonMetasfreshId> jsonMetasfreshId = getJsonMetasfreshIdFromExternalReference(orgId, externalIdentifier, externalReferenceType);

		return jsonMetasfreshId.map(metasfreshId -> MetasfreshId.of(metasfreshId.getValue()));
	}

	@NonNull
	private ExternalReference mapJsonToExternalReference(@NonNull final JsonRequestExternalReferenceUpsert request, @NonNull final OrgId orgId)
	{
		Check.assumeNotNull(request.getExternalReferenceItem().getMetasfreshId(), "MetasfreshId cannot be null at this stage!");

		final IExternalReferenceType externalReferenceType = externalReferenceTypes.ofCode(request.getExternalReferenceItem().getLookupItem().getType())
				.orElseThrow(() -> new InvalidIdentifierException("type", request.getExternalReferenceItem().getLookupItem().getType()));

		final IExternalSystem externalSystem = externalSystems.ofCode(request.getSystemName().getName())
				.orElseThrow(() -> new InvalidIdentifierException("externalSystem", request.getSystemName().getName()));

		return ExternalReference.builder()
				.orgId(orgId)
				.externalSystem(externalSystem)
				.externalReferenceType(externalReferenceType)
				.externalReference(request.getExternalReferenceItem().getLookupItem().getId())
				.recordId(request.getExternalReferenceItem().getMetasfreshId().getValue())
				.version(request.getExternalReferenceItem().getVersion())
				.externalReferenceUrl(request.getExternalReferenceItem().getExternalReferenceUrl())
				.build();
	}

	@NonNull
	private ExternalReference syncCandidateWithExisting(
			@NonNull final ExternalReference candidate,
			@NonNull final ExternalReference existingReference)
	{
		return ExternalReference.builder()
				//existing
				.externalReferenceId(existingReference.getExternalReferenceId())
				//candidate
				.orgId(candidate.getOrgId())
				.externalSystem(candidate.getExternalSystem())
				.externalReferenceType(candidate.getExternalReferenceType())
				.externalReference(candidate.getExternalReference())
				.recordId(candidate.getRecordId())
				.version(candidate.getVersion())
				.externalReferenceUrl(candidate.getExternalReferenceUrl())
				.build();
	}
}
