/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalreference.rest.v2;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.RestUtils;
import de.metas.common.externalreference.v2.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceResponseItem;
import de.metas.common.externalreference.v2.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalreference.v2.JsonSingleExternalReferenceCreateReq;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.externalreference.ExternalBusinessKey;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Check;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
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
							  .externalReference(externalBusinessKey.asExternalValueAndSystem().getValue())
							  .build())
				.build();

		final JsonExternalReferenceLookupResponse lookupResponse = performLookup(orgIdToUse, lookupRequest);
		return lookupResponse.getItems()
				.stream()
				.map(JsonExternalReferenceResponseItem::getMetasfreshId)
				.filter(Objects::nonNull)
				.findFirst();
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
							  .externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
							  .build())
				.build();

		final JsonExternalReferenceLookupResponse lookupResponse = performLookup(orgIdToUse, lookupRequest);
		return lookupResponse.getItems()
				.stream()
				.map(JsonExternalReferenceResponseItem::getMetasfreshId)
				.filter(Objects::nonNull)
				.findFirst();
	}

	@NonNull
	public Optional<JsonMetasfreshId> getJsonMetasfreshIdFromExternalReference(
			@NonNull final String orgCode,
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		return getJsonMetasfreshIdFromExternalReference(orgId, externalIdentifier, externalReferenceType);
	}

	@NonNull
	public JsonExternalReferenceLookupResponse performLookup(
			@Nullable final OrgId orgId,
			@NonNull final JsonExternalReferenceLookupRequest request)
	{
		final OrgId orgIdToUse = CoalesceUtil.coalesceSuppliersNotNull(() -> orgId, Env::getOrgId);

		final IExternalSystem externalSystem = externalSystems.ofCode(request.getSystemName().getName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));
		if (EmptyUtil.isEmpty(request.getItems()))
		{
			throw new MissingPropertyException("items", request);
		}

		final ImmutableSet<JsonExternalReferenceLookupItem> items = ImmutableSet.copyOf(request.getItems());

		final ImmutableMap<JsonExternalReferenceLookupItem, ExternalReferenceQuery> item2Query = extractRepoQueries(items, orgIdToUse, externalSystem);

		final ImmutableMap<ExternalReferenceQuery, ExternalReference> externalReferences = externalReferenceRepository.getExternalReferences(item2Query.values());

		return createResponseFromRepoResult(item2Query, externalReferences);
	}

	public JsonExternalReferenceLookupResponse performLookup(
			@Nullable final String orgCode,
			@NonNull final JsonExternalReferenceLookupRequest request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		return performLookup(orgId, request);
	}

	public void performInsert(@Nullable final String orgCode, @NonNull final JsonExternalReferenceCreateRequest request)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		final IExternalSystem externalSystem = externalSystems.ofCode(request.getSystemName().getName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));

		final List<JsonExternalReferenceRequestItem> references = request.getItems();

		for (final JsonExternalReferenceRequestItem reference : references)
		{
			final JsonExternalReferenceLookupItem lookupItem = reference.getLookupItem();
			final JsonMetasfreshId metasfreshId = reference.getMetasfreshId();
			if (metasfreshId == null)
			{
				throw new MissingPropertyException("metasfreshId", reference);
			}
			final String externalReferenceStr = lookupItem.getExternalReference();
			if (Check.isBlank(externalReferenceStr))
			{
				throw new MissingPropertyException("externalReference", lookupItem);
			}

			final IExternalReferenceType type = externalReferenceTypes.ofCode(lookupItem.getType())
					.orElseThrow(() -> new InvalidIdentifierException("type", lookupItem));

			final ExternalReference externalReference = ExternalReference.builder()
					.orgId(orgId)
					.externalSystem(externalSystem)
					.externalReference(externalReferenceStr)
					.externalReferenceType(type)
					.recordId(metasfreshId.getValue())
					.version(reference.getVersion())
					.externalReferenceUrl(reference.getExternalReferenceUrl())
					.externalSystemParentConfigId(JsonMetasfreshId.toValue(reference.getExternalSystemConfigId()))
					.readOnlyInMetasfresh(Boolean.TRUE.equals(reference.getReadOnlyMetasfresh()))
					.build();
			externalReferenceRepository.save(externalReference);
		}
	}

	public void performUpsert(@NonNull final JsonRequestExternalReferenceUpsert request, @Nullable final String orgCode)
	{
		performUpsert(request, retrieveOrgIdOrDefault(orgCode));
	}

	public void performUpsert(@NonNull final JsonRequestExternalReferenceUpsert request, @NonNull final OrgId orgId)
	{
		final IExternalSystem externalSystem = externalSystems.ofCode(request.getSystemName().getName())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", request));

		final JsonExternalReferenceRequestItem jsonItemToSyncFrom = request.getExternalReferenceItem();
		final ExternalReferenceQuery query = extractRepoQuery(jsonItemToSyncFrom.getLookupItem(), orgId, externalSystem);

		final ExternalReference existingReference = externalReferenceRepository.getExternalReferenceOrNull(query);
		final ExternalReference externalReferenceToUpsert;
		if (existingReference != null)
		{
			final ExternalReference.ExternalReferenceBuilder builder = existingReference.toBuilder();
			final String newReference = jsonItemToSyncFrom.getExternalReference();
			if (jsonItemToSyncFrom.isExternalReferenceSet() && EmptyUtil.isNotBlank(newReference))
			{
				builder.externalReference(newReference);
			}
			if (jsonItemToSyncFrom.isVersionSet())
			{
				builder.version(jsonItemToSyncFrom.getVersion());
			}
			if (jsonItemToSyncFrom.isExternalReferenceUrlSet())
			{
				builder.externalReferenceUrl(jsonItemToSyncFrom.getExternalReferenceUrl());
			}
			final int recordId = JsonMetasfreshId.toValueInt(jsonItemToSyncFrom.getMetasfreshId());
			if (jsonItemToSyncFrom.isMetasfreshIdSet() && recordId > 0)
			{
				builder.recordId(recordId);
			}
			if (jsonItemToSyncFrom.isReadOnlyMetasfreshSet())
			{
				builder.readOnlyInMetasfresh(jsonItemToSyncFrom.getReadOnlyMetasfresh());
			}
			if (jsonItemToSyncFrom.isExternalSystemConfigIdSet())
			{
				builder.externalSystemParentConfigId(JsonMetasfreshId.toValue(jsonItemToSyncFrom.getExternalSystemConfigId()));
			}
			externalReferenceToUpsert = builder.build();
		}
		else
		{
			externalReferenceToUpsert = asExternalReference(request, orgId);
		}

		externalReferenceRepository.save(externalReferenceToUpsert);
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
	private ExternalReference asExternalReference(@NonNull final JsonRequestExternalReferenceUpsert request, @NonNull final OrgId orgId)
	{
		// metasfreshId and externalReference are mandatory
		// if we are here, we don't want to update, but insert a new S_ExternalReferenceRecord.
		// we don't want to try to lookup "A" and then insert "B", so we prefer the lookup-values for our new reference if provided
		final JsonMetasfreshId metasfreshId = CoalesceUtil.coalesce(request.getExternalReferenceItem().getLookupItem().getMetasfreshId(),
																	request.getExternalReferenceItem().getMetasfreshId());
		if (metasfreshId == null)
		{
			throw new MissingPropertyException("metasfreshId", request.getExternalReferenceItem());
		}
		final String externalReference = CoalesceUtil.firstNotBlank(request.getExternalReferenceItem().getLookupItem().getExternalReference(),
																	request.getExternalReferenceItem().getExternalReference());
		if (EmptyUtil.isBlank(externalReference))
		{
			throw new MissingPropertyException("externalReference", request.getExternalReferenceItem());
		}

		final IExternalReferenceType externalReferenceType = externalReferenceTypes.ofCode(request.getExternalReferenceItem().getLookupItem().getType())
				.orElseThrow(() -> new InvalidIdentifierException("type", request.getExternalReferenceItem().getLookupItem().getType()));

		final IExternalSystem externalSystem = externalSystems.ofCode(request.getSystemName().getName())
				.orElseThrow(() -> new InvalidIdentifierException("externalSystem", request.getSystemName().getName()));

		final ExternalReference.ExternalReferenceBuilder externalReferenceBuilder = ExternalReference.builder();

		if (request.getExternalReferenceItem().getReadOnlyMetasfresh() != null)
		{
			externalReferenceBuilder.readOnlyInMetasfresh(request.getExternalReferenceItem().getReadOnlyMetasfresh());
		}

		if (request.getExternalReferenceItem().getExternalSystemConfigId() != null)
		{
			externalReferenceBuilder.externalSystemParentConfigId(JsonMetasfreshId.toValue(request.getExternalReferenceItem().getExternalSystemConfigId()));
		}

		return externalReferenceBuilder
				.orgId(orgId)
				.externalSystem(externalSystem)
				.externalReferenceType(externalReferenceType)
				.externalReference(externalReference)
				.recordId(metasfreshId.getValue())
				.version(request.getExternalReferenceItem().getVersion())
				.externalReferenceUrl(request.getExternalReferenceItem().getExternalReferenceUrl())
				.build();
	}

	@NonNull
	private ImmutableMap<JsonExternalReferenceLookupItem, ExternalReferenceQuery> extractRepoQueries(
			@NonNull final ImmutableSet<JsonExternalReferenceLookupItem> items,
			@NonNull final OrgId orgId,
			@NonNull final IExternalSystem externalSystem)
	{
		final ImmutableMap.Builder<JsonExternalReferenceLookupItem, ExternalReferenceQuery> item2Query = ImmutableMap.builder();

		for (final JsonExternalReferenceLookupItem item : items)
		{
			final ExternalReferenceQuery query = extractRepoQuery(item, orgId, externalSystem);
			item2Query.put(item, query);
		}

		return item2Query.build();
	}

	private ExternalReferenceQuery extractRepoQuery(
			@NonNull final JsonExternalReferenceLookupItem item,
			@NonNull final OrgId orgId,
			@NonNull final IExternalSystem externalSystem)
	{
		final IExternalReferenceType type = externalReferenceTypes.ofCode(item.getType())
				.orElseThrow(() -> new InvalidIdentifierException("type", item));

		return ExternalReferenceQuery.builder()
				.orgId(orgId)
				.externalSystem(externalSystem)
				.externalReferenceType(type)
				.externalReference(item.getExternalReference())
				.metasfreshId(MetasfreshId.ofOrNull(item.getMetasfreshId()))
				.build();
	}

	@NonNull
	private JsonExternalReferenceLookupResponse createResponseFromRepoResult(
			@NonNull final ImmutableMap<JsonExternalReferenceLookupItem, ExternalReferenceQuery> item2Query,
			@NonNull final ImmutableMap<ExternalReferenceQuery, ExternalReference> externalReferences)
	{
		final JsonExternalReferenceLookupResponse.JsonExternalReferenceLookupResponseBuilder result = JsonExternalReferenceLookupResponse.builder();

		for (final Map.Entry<JsonExternalReferenceLookupItem, ExternalReferenceQuery> lookupItem2QueryEntry : item2Query.entrySet())
		{
			final JsonExternalReferenceLookupItem lookupItem = lookupItem2QueryEntry.getKey();

			final ExternalReference externalReference = externalReferences.get(lookupItem2QueryEntry.getValue());

			final JsonExternalReferenceResponseItem responseItem;

			if (ExternalReference.NULL.equals(externalReference))
			{
				responseItem = JsonExternalReferenceResponseItem.of(lookupItem);
			}
			else
			{
				responseItem = JsonExternalReferenceResponseItem.builder()
						.lookupItem(lookupItem)
						.metasfreshId(JsonMetasfreshId.of(externalReference.getRecordId()))
						.externalReference(externalReference.getExternalReference())
						.version(externalReference.getVersion())
						.externalReferenceUrl(externalReference.getExternalReferenceUrl())
						.systemName(JsonExternalSystemName.of(externalReference.getExternalSystem().getCode()))
						.externalReferenceId(JsonMetasfreshId.of(externalReference.getExternalReferenceId().getRepoId()))
						.externalSystemConfigId(JsonMetasfreshId.ofOrNull(externalReference.getExternalSystemParentConfigId()))
						.readOnlyMetasfresh(externalReference.isReadOnlyInMetasfresh())
						.build();
			}
			result.item(responseItem);
		}
		return result.build();
	}
}