/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.project.workorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertItemRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectsUnderTestResponse;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.project.workorder.undertest.CreateWOProjectObjectUnderTestRequest;
import de.metas.project.workorder.undertest.WOObjectUnderTestQuery;
import de.metas.project.workorder.undertest.WOProjectObjectUnderTest;
import de.metas.project.workorder.undertest.WorkOrderProjectObjectUnderTestRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkOrderProjectObjectUnderTestRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final WOProjectRepository workOrderProjectRepository;
	private final WorkOrderProjectObjectUnderTestRepository workOrderProjectObjectUnderTestRepository;

	public WorkOrderProjectObjectUnderTestRestService(
			@NonNull final WOProjectRepository workOrderProjectRepository,
			@NonNull final WorkOrderProjectObjectUnderTestRepository workOrderProjectObjectUnderTestRepository)
	{
		this.workOrderProjectRepository = workOrderProjectRepository;
		this.workOrderProjectObjectUnderTestRepository = workOrderProjectObjectUnderTestRepository;
	}

	@NonNull
	public List<JsonWorkOrderObjectsUnderTestResponse> getByProjectId(@NonNull final ProjectId woProjectId)
	{
		return workOrderProjectObjectUnderTestRepository.getByProjectId(woProjectId)
				.stream()
				.map(WorkOrderProjectObjectUnderTestRestService::toJsonWorkOrderObjectsUnderTestResponse)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<JsonWorkOrderObjectUnderTestUpsertResponse> upsertObjectUnderTest(@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertObjectUnderTestWithinTrx(request));
	}

	@NonNull
	private List<JsonWorkOrderObjectUnderTestUpsertResponse> upsertObjectUnderTestWithinTrx(@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request)
	{
		if (Check.isEmpty(request.getRequestItems()))
		{
			return ImmutableList.of();
		}

		validateJsonWorkOrderObjectUnderTestUpsertRequest(request);

		final ProjectId projectId = request.getProjectId().mapValue(ProjectId::ofRepoId);

		final WOProject woProject = workOrderProjectRepository.getById(projectId);

		return upsertObjectUnderTestItems(request, woProject);
	}

	@NonNull
	private List<JsonWorkOrderObjectUnderTestUpsertResponse> upsertObjectUnderTestItems(
			@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request,
			@NonNull final WOProject woProject)
	{
		final Map<IdentifierString, JsonWorkOrderObjectUnderTestUpsertItemRequest> identifier2ObjectUnderTest = Maps.uniqueIndex(
				request.getRequestItems(),
				(requestItem) -> requestItem.mapObjectIdentifier(IdentifierString::of));

		return upsertObjectUnderTestItems(woProject.getProjectId(),
										  woProject.getOrgId(),
										  identifier2ObjectUnderTest,
										  request.getSyncAdvise());
	}

	@NonNull
	private List<JsonWorkOrderObjectUnderTestUpsertResponse> upsertObjectUnderTestItems(
			@NonNull final ProjectId woProjectId,
			@NonNull final OrgId orgId,
			@NonNull final Map<IdentifierString, JsonWorkOrderObjectUnderTestUpsertItemRequest> identifier2RequestItem,
			@NonNull final SyncAdvise syncAdvise)
	{
		final List<WOProjectObjectUnderTest> existingObjectsUnderTest = workOrderProjectObjectUnderTestRepository.getByProjectId(woProjectId);

		final Map<IdentifierString, WOProjectObjectUnderTest> objectsToUpdate =
				getItemsToUpdate(existingObjectsUnderTest, identifier2RequestItem.values(), syncAdvise);

		final Map<IdentifierString, CreateWOProjectObjectUnderTestRequest> objectsToCreate =
				getItemsToCreate(woProjectId, orgId, objectsToUpdate.keySet(), identifier2RequestItem, syncAdvise);

		final ImmutableList.Builder<JsonWorkOrderObjectUnderTestUpsertResponse> responseCollector = ImmutableList.builder();

		if (syncAdvise.getIfExists().isUpdate())
		{
			workOrderProjectObjectUnderTestRepository.updateAll(objectsToUpdate.values());
		}

		final JsonResponseUpsertItem.SyncOutcome toUpdateSyncOutcome = syncAdvise.getIfExists().isUpdate()
			? JsonResponseUpsertItem.SyncOutcome.UPDATED
			: JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;

		objectsToUpdate.forEach((identifier,objectUnderTest) -> responseCollector
				.add(JsonWorkOrderObjectUnderTestUpsertResponse.builder()
							 .identifier(identifier.getRawIdentifierString())
							 .metasfreshId(JsonMetasfreshId.of(objectUnderTest.getObjectUnderTestId().getRepoId()))
							 .syncOutcome(toUpdateSyncOutcome)
							 .build()));

		objectsToCreate.forEach((identifier,createRequest) -> {
			final WOProjectObjectUnderTest objectUnderTest = workOrderProjectObjectUnderTestRepository.create(createRequest);

			responseCollector.add(JsonWorkOrderObjectUnderTestUpsertResponse.builder()
										  .identifier(identifier.getRawIdentifierString())
										  .metasfreshId(JsonMetasfreshId.of(objectUnderTest.getObjectUnderTestId().getRepoId()))
										  .syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
										  .build());
		});

		return responseCollector.build();
	}

	@NonNull
	private WOProjectObjectUnderTest syncWOProjectObjectUnderTestWithJson(
			@NonNull final JsonWorkOrderObjectUnderTestUpsertItemRequest request,
			@NonNull final WOProjectObjectUnderTest existingObjectUnderTest)
	{
		final WOProjectObjectUnderTest.WOProjectObjectUnderTestBuilder objectUnderTestBuilder = existingObjectUnderTest.toBuilder()
				.numberOfObjectsUnderTest(request.getNumberOfObjectsUnderTest());

		if (request.isWoDeliveryNoteSet())
		{
			objectUnderTestBuilder.woDeliveryNote(request.getWoDeliveryNote());
		}

		if (request.isWoManufacturerSet())
		{
			objectUnderTestBuilder.woManufacturer(request.getWoManufacturer());
		}

		if (request.isWoObjectTypeSet())
		{
			objectUnderTestBuilder.woObjectType(request.getWoObjectType());
		}

		if (request.isWoObjectNameSet())
		{
			objectUnderTestBuilder.woObjectName(request.getWoObjectName());
		}

		if (request.isWoObjectWhereaboutsSet())
		{
			objectUnderTestBuilder.woObjectWhereabouts(request.getWoObjectWhereabouts());
		}

		if (request.isExternalIdSet())
		{
			objectUnderTestBuilder.externalId(ExternalId.ofOrNull(request.getExternalId()));
		}

		return objectUnderTestBuilder.build();
	}

	@NonNull
	private Map<IdentifierString, WOProjectObjectUnderTest> getItemsToUpdate(
			@NonNull final List<WOProjectObjectUnderTest> existingProjectUnderTestForProject,
			@NonNull final Collection<JsonWorkOrderObjectUnderTestUpsertItemRequest> requestItems,
			@NonNull final SyncAdvise syncAdvise)
	{
		final ImmutableMap.Builder<IdentifierString, WOProjectObjectUnderTest> updatesCollector = ImmutableMap.builder();

		for (final JsonWorkOrderObjectUnderTestUpsertItemRequest item : requestItems)
		{
			final IdentifierString objectUnderTestIdentifier = item.mapObjectIdentifier(IdentifierString::of);

			resolveObjectUnderTestForExternalIdentifier(objectUnderTestIdentifier, existingProjectUnderTestForProject)
					//dev-note: avoid unnecessary processing
					.map(matchingWOProjectUnderTest -> syncAdvise.getIfExists().isUpdate()
							? syncWOProjectObjectUnderTestWithJson(item, matchingWOProjectUnderTest)
							: matchingWOProjectUnderTest)

					.ifPresent(syncedWOProjectUnderTest -> updatesCollector.put(objectUnderTestIdentifier, syncedWOProjectUnderTest));
		}

		return updatesCollector.build();
	}

	@NonNull
	private Map<IdentifierString, CreateWOProjectObjectUnderTestRequest> getItemsToCreate(
			@NonNull final ProjectId projectId,
			@NonNull final OrgId orgId,
			@NonNull final Set<IdentifierString> objectIdentifiersMatchedForUpdate,
			@NonNull final Map<IdentifierString, JsonWorkOrderObjectUnderTestUpsertItemRequest> requestItems,
			@NonNull final SyncAdvise syncAdvise)
	{
		final ImmutableMap.Builder<IdentifierString, CreateWOProjectObjectUnderTestRequest> itemsToCreate = ImmutableMap.builder();

		final Set<IdentifierString> identifiesToCreate = getIdentifiersToCreate(orgId, objectIdentifiersMatchedForUpdate, requestItems.values(), projectId);

		if (syncAdvise.getIfNotExists().isFail() && !identifiesToCreate.isEmpty())
		{
			final String missingRawIdentifiers = identifiesToCreate.stream()
					.map(IdentifierString::getRawIdentifierString)
					.collect(Collectors.joining(","));

			throw MissingResourceException.builder()
					.resourceName("WOProjectObjectUnderTest")
					.resourceIdentifier(missingRawIdentifiers)
					.build()
					.setParameter("objectUnderTestSyncAdvise", syncAdvise);
		}

		for (final IdentifierString identifier : identifiesToCreate)
		{
			final JsonWorkOrderObjectUnderTestUpsertItemRequest item2Create = requestItems.get(identifier);

			final CreateWOProjectObjectUnderTestRequest createWOProjectObjectUnderTestRequest = buildCreateWOProjectObjectUnderTestRequest(item2Create, projectId, orgId);

			itemsToCreate.put(identifier, createWOProjectObjectUnderTestRequest);
		}

		return itemsToCreate.build();
	}

	/**
	 * @param requestProjectId see {@link #validateAreNotStoredUnderDifferentProject(Set, OrgId, ProjectId)} 
	 */
	@NonNull
	private Set<IdentifierString> getIdentifiersToCreate(
			@NonNull final OrgId orgId,
			@NonNull final Set<IdentifierString> objectIdentifiersMatchedForUpdate,
			@NonNull final Collection<JsonWorkOrderObjectUnderTestUpsertItemRequest> requestItems,
			@NonNull final ProjectId requestProjectId)
	{
		final ImmutableSet.Builder<IdentifierString> externalIdsToInsertCollector = ImmutableSet.builder();

		for (final JsonWorkOrderObjectUnderTestUpsertItemRequest item : requestItems)
		{
			final IdentifierString objectUnderTestIdentifier = item.mapObjectIdentifier(IdentifierString::of);

			if (objectIdentifiersMatchedForUpdate.contains(objectUnderTestIdentifier))
			{
				continue;
			}

			if (objectUnderTestIdentifier.isMetasfreshId())
			{
				throw new AdempiereException("C_Project_WO_ObjectUnderTest_ID is already placed below another project!")
						.appendParametersToMessage()
						.setParameter("Request-ProjectId", requestProjectId)
						.setParameter("C_Project_WO_ObjectUnderTest_ID", objectUnderTestIdentifier.asMetasfreshId().getValue());
			}
			else if (objectUnderTestIdentifier.isExternalId())
			{
				externalIdsToInsertCollector.add(objectUnderTestIdentifier);
			}
			else
			{
				throw new AdempiereException("Unsupported identifier type=" + objectUnderTestIdentifier.getType());
			}
		}

		final Set<IdentifierString> externalIdsToCreate = externalIdsToInsertCollector.build();

		validateAreNotStoredUnderDifferentProject(externalIdsToCreate, orgId, requestProjectId);

		return externalIdsToCreate;
	}

	/**
	 * Checks if the objectUnderTest - as identified by its externalId - is already stored below a C_Project that is different from the one specified in our overall JSON request-body which the step is a part of.
	 * 
	 * @param requestProjectId there for information in case the method throws an exception
	 */
	private void validateAreNotStoredUnderDifferentProject(
			@NonNull final Set<IdentifierString> externalIdsToCreate, 
			@NonNull final OrgId orgId,
			@NonNull final ProjectId requestProjectId)
	{
		if (externalIdsToCreate.isEmpty())
		{
			return;
		}

		final Set<String> rawExternalIds = externalIdsToCreate.stream()
				.map(IdentifierString::asExternalId)
				.map(ExternalId::getValue)
				.collect(ImmutableSet.toImmutableSet());

		final WOObjectUnderTestQuery query = WOObjectUnderTestQuery.builder()
				.orgId(orgId)
				.externalIds(rawExternalIds)
				.build();

		final List<WOProjectObjectUnderTest> existingWOProjectUnderTest = workOrderProjectObjectUnderTestRepository.getByQuery(query);

		if (!existingWOProjectUnderTest.isEmpty())
		{
			final String projectIds = existingWOProjectUnderTest.stream()
					.map(WOProjectObjectUnderTest::getProjectId)
					.map(ProjectId::getRepoId)
					.map(String::valueOf)
					.collect(Collectors.joining(","));

			throw new AdempiereException("WOProjectUnderTest.ExternalId already stored under a different project!")
					.appendParametersToMessage()
					.setParameter("ExternalIds", StringUtils.join(rawExternalIds, ", "))
					.setParameter("Request-ProjectId", requestProjectId)
					.setParameter("Already stored under projectIds", projectIds);
		}
	}

	@NonNull
	private static CreateWOProjectObjectUnderTestRequest buildCreateWOProjectObjectUnderTestRequest(
			@NonNull final JsonWorkOrderObjectUnderTestUpsertItemRequest request,
			@NonNull final ProjectId woProjectId,
			@NonNull final OrgId orgId)
	{
		return CreateWOProjectObjectUnderTestRequest.builder()
				.orgId(orgId)
				.projectId(woProjectId)
				.numberOfObjectsUnderTest(request.getNumberOfObjectsUnderTest())
				.externalId(ExternalId.of(request.getExternalId()))
				.woDeliveryNote(request.getWoDeliveryNote())
				.woManufacturer(request.getWoManufacturer())
				.woObjectType(request.getWoObjectType())
				.woObjectName(request.getWoObjectName())
				.woObjectWhereabouts(request.getWoObjectWhereabouts())
				.build();
	}

	@NonNull
	private static JsonWorkOrderObjectsUnderTestResponse toJsonWorkOrderObjectsUnderTestResponse(@NonNull final WOProjectObjectUnderTest objectUnderTest)
	{
		return JsonWorkOrderObjectsUnderTestResponse.builder()
				.objectUnderTestId(JsonMetasfreshId.of(objectUnderTest.getObjectUnderTestId().getRepoId()))
				.numberOfObjectsUnderTest(objectUnderTest.getNumberOfObjectsUnderTest())
				.externalId(ExternalId.toValue(objectUnderTest.getExternalId()))
				.woDeliveryNote(objectUnderTest.getWoDeliveryNote())
				.woManufacturer(objectUnderTest.getWoManufacturer())
				.woObjectType(objectUnderTest.getWoObjectType())
				.woObjectName(objectUnderTest.getWoObjectName())
				.woObjectWhereabouts(objectUnderTest.getWoObjectWhereabouts())
				.objectDeliveredDate(objectUnderTest.getObjectDeliveredDate())
				.build();
	}

	@NonNull
	public Optional<WOProjectObjectUnderTest> resolveObjectUnderTestForExternalIdentifier(
			@NonNull final IdentifierString identifier,
			@NonNull final List<WOProjectObjectUnderTest> objectsUnderTest)
	{
		switch (identifier.getType())
		{
			case METASFRESH_ID:
				return objectsUnderTest.stream()
						.filter(objectUnderTest -> {
							final int id = objectUnderTest.getObjectUnderTestId().getRepoId();
							return id == identifier.asMetasfreshId().getValue();
						})
						.findFirst();
			case EXTERNAL_ID:
				return objectsUnderTest.stream()
						.filter(objectUnderTest -> objectUnderTest.getExternalId() != null)
						.filter(objectUnderTest -> objectUnderTest.getExternalId().equals(identifier.asExternalId()))
						.findFirst();
			default:
				throw new AdempiereException("Unhandled IdentifierString type=" + identifier);
		}
	}

	private static void validateJsonWorkOrderObjectUnderTestUpsertRequest(@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request)
	{
		request.getRequestItems().forEach(requestItem -> {
			if (Check.isBlank(requestItem.getIdentifier()))
			{
				throw new MissingPropertyException("identifier", requestItem);
			}

			if (requestItem.getNumberOfObjectsUnderTest() == null)
			{
				throw new MissingPropertyException("numberOfObjectsUnderTest", requestItem);
			}

			final IdentifierString identifierString = requestItem.mapObjectIdentifier(IdentifierString::of);

			if (identifierString.isExternalId() && !identifierString.asExternalId().getValue().equals(requestItem.getExternalId()))
			{
				throw new AdempiereException("WorkOrderObjectUnderTest.Identifier doesn't match with WorkOrderObjectUnderTest.ExternalId")
						.appendParametersToMessage()
						.setParameter("WorkOrderObjectUnderTest.Identifier", identifierString.getRawIdentifierString())
						.setParameter("WorkOrderObjectUnderTest.ExternalId", requestItem.getExternalId());
			}
		});
	}
}
