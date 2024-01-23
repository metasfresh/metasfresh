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
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonDurationUnit;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertItemRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertResponse;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.CreateWOProjectResourceRequest;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.resource.ResourceIdentifierUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.time.DurationUtils;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorkOrderProjectResourceRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WOProjectStepRepository workOrderProjectStepRepository;
	private final WOProjectResourceRepository workOrderProjectResourceRepository;
	private final ResourceService resourceService;

	public WorkOrderProjectResourceRestService(
			@NonNull final WOProjectStepRepository workOrderProjectStepRepository,
			@NonNull final WOProjectResourceRepository workOrderProjectResourceRepository,
			@NonNull final ResourceService resourceService)
	{
		this.workOrderProjectStepRepository = workOrderProjectStepRepository;
		this.workOrderProjectResourceRepository = workOrderProjectResourceRepository;
		this.resourceService = resourceService;
	}

	@NonNull
	public List<JsonWorkOrderResourceResponse> getByWOStepId(@NonNull final Set<WOProjectStepId> woProjectStepIds, @NonNull final ZoneId zoneId)
	{
		return workOrderProjectResourceRepository.listByStepIds(woProjectStepIds)
				.stream()
				.map(woProjectResource -> toJsonWorkOrderResourceResponse(woProjectResource, zoneId))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> upsertResource(@NonNull final SyncAdvise syncAdvise, @NonNull final List<JsonWorkOrderResourceUpsertRequest> requests)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertResourceWithinTrx(syncAdvise, requests));
	}

	@NonNull
	private Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> upsertResourceWithinTrx(
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final List<JsonWorkOrderResourceUpsertRequest> requests)
	{
		requests.forEach(WorkOrderProjectResourceRestService::validateJsonWorkOrderResourceUpsertRequest);

		final Map<WOProjectStepId, WOProjectStep> stepId2Step = workOrderProjectStepRepository.getMapByIds(getStepIds(requests));

		final ImmutableMap.Builder<WOProjectResourceIdentifier, JsonWorkOrderResourceUpsertItemRequest> allRequestItemsCollector = ImmutableMap.builder();

		requests.forEach(request -> {
			final WOProjectStepId woProjectStepId = WOProjectStepId.ofRepoId(ProjectId.ofRepoId(request.getProjectId().getValue()), request.getStepId().getValue());

			request.getRequestItems().forEach(requestItem -> {
				final WOProjectResourceIdentifier identifier = WOProjectResourceIdentifier.of(woProjectStepId, requestItem.mapResourceIdentifier(IdentifierString::of));

				allRequestItemsCollector.put(identifier, requestItem);
			});
		});

		final ImmutableMap<WOProjectResourceIdentifier, JsonWorkOrderResourceUpsertItemRequest> allRequestItems = allRequestItemsCollector.build();

		final Function<WOProjectStepId, OrgId> getOrgId = (woProjectStepId -> stepId2Step.get(woProjectStepId).getOrgId());

		final Map<IdentifierString, ResourceId> resourceIdentifier2RepoId = resolveResourceIdentifiers(getOrgId, allRequestItems);

		return upsertResourceItems(getOrgId, stepId2Step.keySet(), allRequestItems, resourceIdentifier2RepoId, syncAdvise);
	}

	@NonNull
	private CreateWOProjectResourceRequest buildCreateWOProjectResourceRequest(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertItemRequest request,
			@NonNull final ResourceId resourceId,
			@NonNull final WOProjectStepId woProjectStepId)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final Instant assignDateFrom = TimeUtil.asInstant(request.getAssignDateFrom(), zoneId);
		final Instant assignDateTo = TimeUtil.asEndOfDayInstant(request.getAssignDateTo(), zoneId);

		return CreateWOProjectResourceRequest.builder()
				.orgId(orgId)
				.woProjectStepId(woProjectStepId)
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.resourceId(resourceId)
				.isActive(request.getIsActive())
				.isAllDay(request.getIsAllDay())
				.duration(request.getDuration())
				.durationUnit(toWFDurationUnit(request.getDurationUnit()))
				.externalId(ExternalId.ofOrNull(request.getExternalId()))
				.testFacilityGroupName(request.getTestFacilityGroupName())
				.build();
	}

	@NonNull
	private WOProjectResource syncWOProjectResourceWithJson(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertItemRequest request,
			@NonNull final WOProjectResource existingResource)
	{
		final WOProjectResource.WOProjectResourceBuilder resourceBuilder = existingResource.toBuilder()
				.dateRange(getDateRange(orgId, request, existingResource));

		if (request.isActiveSet())
		{
			resourceBuilder.isActive(request.getIsActive());
		}

		if (request.isExternalIdSet())
		{
			resourceBuilder.externalId(ExternalId.ofOrNull(request.getExternalId()));
		}

		if (request.isDurationSet() && request.isDurationUnitSet())
		{
			final Duration duration = request.mapDuration((reqDuration, jsonUnit) -> DurationUtils
							.fromBigDecimal(reqDuration, toWFDurationUnit(jsonUnit).getTemporalUnit()))
					.orElse(Duration.ZERO);

			resourceBuilder.duration(duration);
			resourceBuilder.durationUnit(toOptionalWFDurationUnit(request.getDurationUnit()).orElse(WFDurationUnit.Hour));
		}

		if (request.isTestFacilityGroupNameSet())
		{
			resourceBuilder.testFacilityGroupName(request.getTestFacilityGroupName());
		}

		return resourceBuilder.build();
	}

	@NonNull
	private Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> upsertResourceItems(
			@NonNull final Function<WOProjectStepId, OrgId> getOrgId,
			@NonNull final Set<WOProjectStepId> stepIds,
			@NonNull final Map<WOProjectResourceIdentifier, JsonWorkOrderResourceUpsertItemRequest> identifier2RequestItem,
			@NonNull final Map<IdentifierString, ResourceId> resourceIdentifier2RepoId,
			@NonNull final SyncAdvise syncAdvise)
	{
		final List<WOProjectResource> existingWOProjectResource = workOrderProjectResourceRepository.listByStepIds(stepIds);

		final Map<WOProjectResourceIdentifier, WOProjectResource> objectsToUpdate =
				getItemsToUpdate(getOrgId, existingWOProjectResource, identifier2RequestItem, resourceIdentifier2RepoId, syncAdvise);

		final Map<WOProjectResourceIdentifier, CreateWOProjectResourceRequest> objectsToCreate =
				getItemsToCreate(getOrgId, objectsToUpdate.keySet(), identifier2RequestItem, resourceIdentifier2RepoId, syncAdvise);

		final Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> responseCollector = new HashMap<>();

		final JsonResponseUpsertItem.SyncOutcome toUpdateSyncOutcome;
		if (syncAdvise.getIfExists().isUpdate())
		{
			workOrderProjectResourceRepository.updateAll(objectsToUpdate.values());
			toUpdateSyncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
		}
		else
		{
			toUpdateSyncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}

		objectsToUpdate.forEach((identifier, woResource) -> {
			final WOProjectStepId stepId = woResource.getWoProjectStepId();

			final JsonWorkOrderResourceUpsertResponse responseItem = JsonWorkOrderResourceUpsertResponse.builder()
					.identifier(identifier.getResourceIdentifier().getRawIdentifierString())
					.metasfreshId(JsonMetasfreshId.of(woResource.getWoProjectResourceId().getRepoId()))
					.syncOutcome(toUpdateSyncOutcome)
					.build();

			final List<JsonWorkOrderResourceUpsertResponse> responseList = new ArrayList<>();
			responseList.add(responseItem);
			responseCollector.merge(stepId, responseList, (oldList, newList) -> {
				oldList.addAll(newList);
				return oldList;
			});
		});

		objectsToCreate.forEach((identifier, createRequest) -> {
			final JsonWorkOrderResourceUpsertResponse responseItem = createWOProjectResource(identifier, createRequest);

			final List<JsonWorkOrderResourceUpsertResponse> responseList = new ArrayList<>();
			responseList.add(responseItem);
			responseCollector.merge(identifier.getStepId(), responseList, (oldList, newList) -> {
				oldList.addAll(newList);
				return oldList;
			});
		});

		return responseCollector;
	}

	@NonNull
	private Map<WOProjectResourceIdentifier, WOProjectResource> getItemsToUpdate(
			@NonNull final Function<WOProjectStepId, OrgId> getOrgId,
			@NonNull final List<WOProjectResource> existingProjectResources,
			@NonNull final Map<WOProjectResourceIdentifier, JsonWorkOrderResourceUpsertItemRequest> identifier2requestItems,
			@NonNull final Map<IdentifierString, ResourceId> resourceIdentifier2RepoId,
			@NonNull final SyncAdvise syncAdvise)
	{
		final ImmutableMap.Builder<WOProjectResourceIdentifier, WOProjectResource> updatesCollector = ImmutableMap.builder();

		for (final Map.Entry<WOProjectResourceIdentifier, JsonWorkOrderResourceUpsertItemRequest> identifier2item : identifier2requestItems.entrySet())
		{
			final IdentifierString resourceIdentifier = identifier2item.getKey().getResourceIdentifier();
			final WOProjectStepId stepId = identifier2item.getKey().getStepId();

			locateExistingResourceWithinStep(stepId, resourceIdentifier2RepoId.get(resourceIdentifier), existingProjectResources)
					//dev-note: avoid unnecessary processing
					.map(matchingResource -> syncAdvise.getIfExists().isUpdate()
							? syncWOProjectResourceWithJson(getOrgId.apply(stepId), identifier2item.getValue(), matchingResource)
							: matchingResource)

					.ifPresent(syncedResource -> updatesCollector.put(identifier2item.getKey(), syncedResource));
		}

		return updatesCollector.build();
	}

	@NonNull
	private Map<WOProjectResourceIdentifier, CreateWOProjectResourceRequest> getItemsToCreate(
			@NonNull final Function<WOProjectStepId, OrgId> getOrgId,
			@NonNull final Set<WOProjectResourceIdentifier> resourceIdentifiersMatchedForUpdate,
			@NonNull final Map<WOProjectResourceIdentifier, JsonWorkOrderResourceUpsertItemRequest> requestItems,
			@NonNull final Map<IdentifierString, ResourceId> resourceIdentifier2ResourceId,
			@NonNull final SyncAdvise syncAdvise)
	{
		final ImmutableMap.Builder<WOProjectResourceIdentifier, CreateWOProjectResourceRequest> itemsToCreate = ImmutableMap.builder();

		final Set<WOProjectResourceIdentifier> identifiesToCreate = requestItems.keySet()
				.stream()
				.filter(identifier -> !resourceIdentifiersMatchedForUpdate.contains(identifier))
				.collect(ImmutableSet.toImmutableSet());

		if (syncAdvise.getIfNotExists().isFail() && !identifiesToCreate.isEmpty())
		{
			final String missingRawIdentifiers = identifiesToCreate.stream()
					.map(WOProjectResourceIdentifier::getResourceIdentifier)
					.map(IdentifierString::getRawIdentifierString)
					.collect(Collectors.joining(","));

			throw MissingResourceException.builder()
					.resourceName("WOProjectResource")
					.resourceIdentifier(missingRawIdentifiers)
					.build()
					.setParameter("syncAdvise", syncAdvise);
		}

		for (final WOProjectResourceIdentifier identifier : identifiesToCreate)
		{
			final JsonWorkOrderResourceUpsertItemRequest item2Create = requestItems.get(identifier);

			final ResourceId resourceId = resourceIdentifier2ResourceId.get(identifier.getResourceIdentifier());

			final CreateWOProjectResourceRequest createResourceRequest = buildCreateWOProjectResourceRequest(getOrgId.apply(identifier.getStepId()),
																											 item2Create,
																											 resourceId,
																											 identifier.getStepId());

			itemsToCreate.put(identifier, createResourceRequest);
		}

		return itemsToCreate.build();
	}

	@NonNull
	private Map<IdentifierString, ResourceId> resolveResourceIdentifiers(
			@NonNull final Function<WOProjectStepId, OrgId> getOrgId,
			@NonNull final Map<WOProjectResourceIdentifier, JsonWorkOrderResourceUpsertItemRequest> requestItems)
	{
		return requestItems
				.keySet()
				.stream()
				.filter(distinctByResourceIdentifier(WOProjectResourceIdentifier::getResourceIdentifier))
				.collect(Collectors.toMap((WOProjectResourceIdentifier::getResourceIdentifier),
										  woProjectResourceIdentifier -> ResourceIdentifierUtil.resolveResourceIdentifier(getOrgId.apply(woProjectResourceIdentifier.getStepId()),
																														  woProjectResourceIdentifier.getResourceIdentifier(),
																														  resourceService)));
	}

	@NonNull
	private JsonWorkOrderResourceUpsertResponse createWOProjectResource(
			@NonNull final WOProjectResourceIdentifier identifier,
			@NonNull final CreateWOProjectResourceRequest createRequest)
	{
		final WOProjectResource woProjectResource = workOrderProjectResourceRepository.create(createRequest);

		return JsonWorkOrderResourceUpsertResponse.builder()
				.identifier(identifier.getResourceIdentifier().getRawIdentifierString())
				.metasfreshId(JsonMetasfreshId.of(woProjectResource.getWoProjectResourceId().getRepoId()))
				.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
				.build();
	}

	@NonNull
	private static Optional<WOProjectResource> locateExistingResourceWithinStep(
			@NonNull final WOProjectStepId stepId,
			@NonNull final ResourceId resourceId,
			@NonNull final List<WOProjectResource> woProjectResources)
	{
		return woProjectResources.stream()
				.filter(woProjectResource -> stepId.equals(woProjectResource.getWoProjectStepId()) && woProjectResource.getResourceIdAndType().getResourceId().equals(resourceId))
				.findFirst();
	}

	@NonNull
	private static Set<WOProjectStepId> getStepIds(@NonNull final List<JsonWorkOrderResourceUpsertRequest> upsertRequests)
	{
		return upsertRequests
				.stream()
				.map(request -> WOProjectStepId.ofRepoId(ProjectId.ofRepoId(request.getProjectId().getValue()), request.getStepId().getValue()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private static WFDurationUnit toWFDurationUnit(@Nullable final JsonDurationUnit jsonDurationUnit)
	{
		if (jsonDurationUnit == null)
		{
			return null;
		}

		switch (jsonDurationUnit)
		{
			case Year:
				return WFDurationUnit.Year;
			case Month:
				return WFDurationUnit.Month;
			case Day:
				return WFDurationUnit.Day;
			case Hour:
				return WFDurationUnit.Hour;
			case Minute:
				return WFDurationUnit.Minute;
			case Second:
				return WFDurationUnit.Second;
			default:
				throw new IllegalStateException("JsonDurationUnit not supported: " + jsonDurationUnit);
		}
	}

	@Nullable
	private static JsonDurationUnit toJsonDurationUnit(@Nullable final WFDurationUnit durationUnit)
	{
		if (durationUnit == null)
		{
			return null;
		}

		switch (durationUnit)
		{
			case Year:
				return JsonDurationUnit.Year;
			case Month:
				return JsonDurationUnit.Month;
			case Day:
				return JsonDurationUnit.Day;
			case Hour:
				return JsonDurationUnit.Hour;
			case Minute:
				return JsonDurationUnit.Minute;
			case Second:
				return JsonDurationUnit.Second;
			default:
				throw new AdempiereException("Unhandled DurationUnit: " + durationUnit);
		}
	}

	private static void validateJsonWorkOrderResourceUpsertRequest(@NonNull final JsonWorkOrderResourceUpsertRequest request)
	{
		request.getRequestItems().forEach(requestItem -> {
			if (Check.isBlank(requestItem.getResourceIdentifier()))
			{
				throw new MissingPropertyException("resourceIdentifier", request);
			}

			final boolean isDurationInfoPartiallySet = Stream.of(requestItem.getDuration(), requestItem.getDurationUnit())
					.filter(Objects::nonNull)
					.count() == 1;

			if (isDurationInfoPartiallySet)
			{
				throw new AdempiereException("WorkOrderResource.Duration is partially set!")
						.appendParametersToMessage()
						.setParameter("WorkOrderResource.Duration", requestItem.getDuration())
						.setParameter("WorkOrderResource.DurationUnit", requestItem.getDurationUnit());
			}
		});
	}

	@NonNull
	private static JsonWorkOrderResourceResponse toJsonWorkOrderResourceResponse(
			@NonNull final WOProjectResource resourceData,
			@NonNull final ZoneId zoneId)
	{
		return JsonWorkOrderResourceResponse.builder()
				.woResourceId(JsonMetasfreshId.of(WOProjectResourceId.toRepoId(resourceData.getWoProjectResourceId())))
				.stepId(JsonMetasfreshId.of(WOProjectStepId.toRepoId(resourceData.getWoProjectStepId())))
				.resourceId(JsonMetasfreshId.ofOrNull(resourceData.getResourceIdAndType().getResourceId().getRepoId()))
				
				.assignDateFrom(resourceData.getStartDate()
										.map(startDate -> TimeUtil.asLocalDate(startDate, zoneId))
										.orElse(null))
				.assignDateTo(resourceData.getEndDate()
									  .map(endDate -> TimeUtil.asLocalDate(endDate, zoneId))
									  .orElse(null))
				
				.duration(DurationUtils.toBigDecimal(resourceData.getDuration(), resourceData.getDurationUnit().getTemporalUnit()))
				.durationUnit(toJsonDurationUnit(resourceData.getDurationUnit()))
				.isAllDay(resourceData.isAllDay())
				.isActive(resourceData.getIsActive())
				.testFacilityGroupName(resourceData.getTestFacilityGroupName())
				.externalId(ExternalId.toValue(resourceData.getExternalId()))
				.build();
	}

	@NonNull
	private static Optional<WFDurationUnit> toOptionalWFDurationUnit(@Nullable final JsonDurationUnit jsonDurationUnit)
	{
		return Optional.ofNullable(toWFDurationUnit(jsonDurationUnit));
	}

	@Value(staticConstructor = "of")
	private static class WOProjectResourceIdentifier
	{
		@NonNull
		WOProjectStepId stepId;

		@NonNull
		IdentifierString resourceIdentifier;
	}

	@NonNull
	private static Predicate<WOProjectResourceIdentifier> distinctByResourceIdentifier(@NonNull final Function<WOProjectResourceIdentifier, IdentifierString> keyExtractor)
	{
		final Set<IdentifierString> seenResourceIdentifiers = new HashSet<>();
		return t -> seenResourceIdentifiers.add(keyExtractor.apply(t));
	}
	
	@Nullable
	private CalendarDateRange getDateRange(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertItemRequest request,
			@NonNull final WOProjectResource existingResource)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);
		
		final Instant actualDateFrom = request.isAssignDateFromSet()
				? TimeUtil.asInstant(request.getAssignDateFrom(), zoneId)
				: existingResource.getStartDate().orElse(null);
		
		final Instant actualDateTo = request.isAssignDateToSet()
				? TimeUtil.asEndOfDayInstant(request.getAssignDateTo(), zoneId)
				: existingResource.getEndDate().orElse(null);
		
		final boolean actualIsAllDay = request.isAllDaySet()
				? Boolean.TRUE.equals(request.getIsAllDay())
				: existingResource.isAllDay();

		if (actualDateFrom == null && actualDateTo == null && actualIsAllDay)
		{
			throw new AdempiereException("IsAllDay cannot be true if there are no dates!")
					.appendParametersToMessage()
					.setParameter("WOResourceId", existingResource.getWoProjectResourceId());
		}
		else if (actualDateFrom == null && actualDateTo != null)
		{
			throw new AdempiereException("DateFrom cannot be missing when DateTo is set!")
					.appendParametersToMessage()
					.setParameter("WOResourceId", existingResource.getWoProjectResourceId());
		}
		else if (actualDateFrom != null && actualDateTo == null && !actualIsAllDay)
		{
			throw new AdempiereException("DateTo cannot be missing when DateFrom is set and isAllDay is false!")
					.appendParametersToMessage()
					.setParameter("WOResourceId", existingResource.getWoProjectResourceId());
		}
		else if (actualDateFrom == null)
		{
			return null;
		}
		
		final Instant nonNullDateTo = actualDateTo == null 
				? TimeUtil.asEndOfDayInstant(actualDateFrom.atZone(zoneId).toLocalDate(), zoneId)
				: actualDateTo;
		
		Check.assumeNotNull(nonNullDateTo, "Computed DateTo cannot be null!");
		
		return CalendarDateRange.builder()
				.startDate(actualDateFrom)
				.endDate(nonNullDateTo)
				.allDay(actualIsAllDay)
				.build();
	}
}
