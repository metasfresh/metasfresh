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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertItemRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectsUnderTestResponse;
import de.metas.project.ProjectId;
import de.metas.project.workorder.data.CreateWOProjectObjectUnderTestRequest;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectObjectUnderTest;
import de.metas.project.workorder.data.WorkOrderProjectObjectUnderTestRepository;
import de.metas.project.workorder.data.WorkOrderProjectRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectObjectUnderTestResponseMapper;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WorkOrderProjectObjectUnderTestRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final WorkOrderProjectRepository workOrderProjectRepository;
	private final WorkOrderProjectObjectUnderTestRepository workOrderProjectObjectUnderTestRepository;

	public WorkOrderProjectObjectUnderTestRestService(
			@NonNull final WorkOrderProjectRepository workOrderProjectRepository,
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
		validateJsonWorkOrderObjectUnderTestUpsertRequest(request);

		final ProjectId projectId = ProjectId.ofRepoId(JsonMetasfreshId.toValueInt(request.getProjectId()));

		final WOProject woProject = workOrderProjectRepository.getOptionalById(projectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Work Order Project")
						.parentResource(request)
						.build());

		return upsertObjectUnderTestItems(request, woProject.getProjectId());
	}

	@NonNull
	private List<JsonWorkOrderObjectUnderTestUpsertResponse> upsertObjectUnderTestItems(
			@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request,
			@NonNull final ProjectId woProjectId)
	{
		final Map<IdentifierString, JsonWorkOrderObjectUnderTestUpsertItemRequest> identifierString2JsonObjectUnderTest = request.getRequestItems().stream()
				.collect(Collectors.toMap((jsonObjectUnderTest) -> IdentifierString.of(jsonObjectUnderTest.getIdentifier()), Function.identity()));

		final Map<IdentifierString, WOProjectObjectUnderTest> identifierString2ExistingObjectUnderTest = woProjectObjectUnderTestToUpdateMap(request.getRequestItems(), woProjectId);

		return upsertObjectUnderTestItems(woProjectId, request.getRequestItems(), request.getSyncAdvise(), identifierString2JsonObjectUnderTest, identifierString2ExistingObjectUnderTest);
	}

	@NonNull
	private Map<IdentifierString, WOProjectObjectUnderTest> woProjectObjectUnderTestToUpdateMap(
			@NonNull final List<JsonWorkOrderObjectUnderTestUpsertItemRequest> jsonObjectsUnderTest,
			@NonNull final ProjectId woProjectId)
	{
		final List<WOProjectObjectUnderTest> existingObjectUnderTests = workOrderProjectObjectUnderTestRepository.getByProjectId(woProjectId);

		if (existingObjectUnderTests == null || existingObjectUnderTests.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<IdentifierString, WOProjectObjectUnderTest> woProjectObjectUnderTestToUpdateMap = ImmutableMap.builder();

		for (final JsonWorkOrderObjectUnderTestUpsertItemRequest jsonObjectUnderTest : jsonObjectsUnderTest)
		{
			final IdentifierString identifier = IdentifierString.of(jsonObjectUnderTest.getIdentifier());

			WorkOrderMapperUtil.resolveObjectUnderTestForExternalIdentifier(identifier, existingObjectUnderTests)
					.ifPresent(matchingProject -> woProjectObjectUnderTestToUpdateMap.put(identifier, matchingProject));
		}

		return woProjectObjectUnderTestToUpdateMap.build();
	}

	@NonNull
	private List<JsonWorkOrderObjectUnderTestUpsertResponse> upsertObjectUnderTestItems(
			@NonNull final ProjectId woProjectId,
			@NonNull final List<JsonWorkOrderObjectUnderTestUpsertItemRequest> jsonObjectsUnderTest,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final Map<IdentifierString, JsonWorkOrderObjectUnderTestUpsertItemRequest> identifierString2JsonObjectUnderTest,
			@NonNull final Map<IdentifierString, WOProjectObjectUnderTest> identifierString2ExistingObjectUnderTest)
	{
		final ImmutableList.Builder<WOProjectObjectUnderTest> objectUnderTestToUpdate = ImmutableList.builder();
		final ImmutableList.Builder<CreateWOProjectObjectUnderTestRequest> objectUnderTestToCreate = ImmutableList.builder();
		final ImmutableList.Builder<WOProjectObjectUnderTestResponseMapper> responseMapper = ImmutableList.builder();
		final ImmutableList.Builder<WOProjectObjectUnderTest> objectUnderTestCollectorBuilder = ImmutableList.builder();

		for (final JsonWorkOrderObjectUnderTestUpsertItemRequest jsonWorkOrderObjectUnderTestUpsertItemRequest : jsonObjectsUnderTest)
		{
			final String identifier = jsonWorkOrderObjectUnderTestUpsertItemRequest.getIdentifier();
			final IdentifierString identifierString = IdentifierString.of(identifier);

			final JsonWorkOrderObjectUnderTestUpsertItemRequest requestItem = identifierString2JsonObjectUnderTest.get(identifierString);

			final Optional<WOProjectObjectUnderTest> existingObjectUnderTestOpt = Optional.ofNullable(identifierString2ExistingObjectUnderTest.get(identifierString));

			if (existingObjectUnderTestOpt.isPresent() && !syncAdvise.getIfExists().isUpdate())
			{
				final WOProjectObjectUnderTest existingObjectUnderTest = existingObjectUnderTestOpt.get();

				responseMapper.add(WOProjectObjectUnderTestResponseMapper.builder()
										   .identifier(identifier)
										   .syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE)
										   .build());

				objectUnderTestCollectorBuilder.add(existingObjectUnderTest);
			}
			else if (existingObjectUnderTestOpt.isPresent() || !syncAdvise.isFailIfNotExists())
			{
				final JsonResponseUpsertItem.SyncOutcome syncOutcome;

				final WOProjectObjectUnderTest existingObjectUnderTest = existingObjectUnderTestOpt.orElse(null);

				if (existingObjectUnderTest == null)
				{
					objectUnderTestToCreate.add(buildCreateWOProjectObjectUnderTestRequest(requestItem, woProjectId));

					syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
				}
				else
				{
					objectUnderTestToUpdate.add(syncWOProjectObjectUnderTestWithJson(requestItem, existingObjectUnderTest));

					syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
				}

				responseMapper.add(WOProjectObjectUnderTestResponseMapper.builder()
										   .identifier(identifier)
										   .syncOutcome(syncOutcome)
										   .build());
			}
			else
			{
				throw MissingResourceException.builder()
						.resourceName("Work order project object under test")
						.parentResource(requestItem)
						.build()
						.setParameter("objectUnderTestSyncAdvise", syncAdvise);
			}

			objectUnderTestCollectorBuilder.addAll(workOrderProjectObjectUnderTestRepository.updateAll(objectUnderTestToUpdate.build()));

			objectUnderTestCollectorBuilder.addAll(workOrderProjectObjectUnderTestRepository.createAll(objectUnderTestToCreate.build()));
		}

		final List<WOProjectObjectUnderTest> objectUnderTestCollector = objectUnderTestCollectorBuilder.build();

		return responseMapper.build()
				.stream()
				.map(objectUnderTestMapper -> objectUnderTestMapper.map(objectUnderTestCollector)) //todo fp: drop mapper
				.collect(ImmutableList.toImmutableList());
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
			objectUnderTestBuilder.externalId(ExternalId.of(request.getExternalId().getValue()));
		}

		return objectUnderTestBuilder.build();
	}

	@NonNull
	public static CreateWOProjectObjectUnderTestRequest buildCreateWOProjectObjectUnderTestRequest(
			@NonNull final JsonWorkOrderObjectUnderTestUpsertItemRequest request,
			@NonNull final ProjectId woProjectId)
	{
		return CreateWOProjectObjectUnderTestRequest.builder()
				.projectId(woProjectId)
				.numberOfObjectsUnderTest(request.getNumberOfObjectsUnderTest())
				.externalId(ExternalId.of(request.getExternalId().getValue()))
				.woDeliveryNote(request.getWoDeliveryNote())
				.woManufacturer(request.getWoManufacturer())
				.woObjectType(request.getWoObjectType())
				.woObjectName(request.getWoObjectName())
				.woObjectWhereabouts(request.getWoObjectWhereabouts())
				.build();
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
		});
	}

	@NonNull
	private static JsonWorkOrderObjectsUnderTestResponse toJsonWorkOrderObjectsUnderTestResponse(@NonNull final WOProjectObjectUnderTest objectUnderTest)
	{
		return JsonWorkOrderObjectsUnderTestResponse.builder()
				.objectUnderTestId(JsonMetasfreshId.of(objectUnderTest.getObjectUnderTestId().getRepoId()))
				.numberOfObjectsUnderTest(objectUnderTest.getNumberOfObjectsUnderTest())
				.externalId(JsonMetasfreshId.ofOrNull(Integer.valueOf(ExternalId.toValue(objectUnderTest.getExternalId()))))
				.woDeliveryNote(objectUnderTest.getWoDeliveryNote())
				.woManufacturer(objectUnderTest.getWoManufacturer())
				.woObjectType(objectUnderTest.getWoObjectType())
				.woObjectName(objectUnderTest.getWoObjectName())
				.woObjectWhereabouts(objectUnderTest.getWoObjectWhereabouts())
				.build();
	}
}
