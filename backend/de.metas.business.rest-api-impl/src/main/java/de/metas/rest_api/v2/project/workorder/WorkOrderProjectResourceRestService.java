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
import de.metas.common.rest_api.common.JsonExternalId;
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
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.data.CreateWOProjectResourceRequest;
import de.metas.project.workorder.data.DurationUnit;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.project.workorder.data.WorkOrderProjectResourceRepository;
import de.metas.project.workorder.data.WorkOrderProjectStepRepository;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.resource.ResourceIdentifierUtil;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectResourceResponseMapper;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WorkOrderProjectResourceRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WorkOrderProjectStepRepository workOrderProjectStepRepository;
	private final WorkOrderProjectResourceRepository workOrderProjectResourceRepository;
	private final ResourceService resourceService;

	public WorkOrderProjectResourceRestService(
			@NonNull final WorkOrderProjectStepRepository workOrderProjectStepRepository,
			@NonNull final WorkOrderProjectResourceRepository workOrderProjectResourceRepository,
			@NonNull final ResourceService resourceService)
	{
		this.workOrderProjectStepRepository = workOrderProjectStepRepository;
		this.workOrderProjectResourceRepository = workOrderProjectResourceRepository;
		this.resourceService = resourceService;
	}

	@NonNull
	public List<JsonWorkOrderResourceResponse> getByWOStepId(@NonNull final WOProjectStepId woProjectStepId, @NonNull final ZoneId zoneId)
	{
		return workOrderProjectResourceRepository.getByStepId(woProjectStepId)
				.stream()
				.map(projectResource -> toJsonWorkOrderResourceResponse(projectResource, zoneId))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<JsonWorkOrderResourceUpsertResponse> upsertResource(@NonNull final JsonWorkOrderResourceUpsertRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertResourceWithinTrx(request));
	}

	@NonNull
	private List<JsonWorkOrderResourceUpsertResponse> upsertResourceWithinTrx(@NonNull final JsonWorkOrderResourceUpsertRequest request)
	{
		validateJsonWorkOrderResourceUpsertRequest(request);

		final ProjectId projectId = ProjectId.ofRepoId(JsonMetasfreshId.toValueInt(request.getProjectId()));
		final WOProjectStepId woProjectStepId = WOProjectStepId.ofRepoId(projectId, request.getStepId().getValue());

		final WOProjectStep woProjectStep = workOrderProjectStepRepository.getById(woProjectStepId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Work Order Project Step")
						.parentResource(request)
						.build());

		return upsertResourceItems(request, woProjectStep);
	}

	@NonNull
	private List<JsonWorkOrderResourceUpsertResponse> upsertResourceItems(
			@NonNull final JsonWorkOrderResourceUpsertRequest request,
			@NonNull final WOProjectStep existingStep)
	{
		final Map<IdentifierString, JsonWorkOrderResourceUpsertItemRequest> identifierString2JsonResource = request.getRequestItems().stream()
				.collect(Collectors.toMap((resource) -> IdentifierString.of(resource.getResourceIdentifier()), Function.identity()));

		final Map<IdentifierString, WOProjectResource> identifierString2ExistingResource = woResourceToUpdateMap(existingStep.getOrgId(), request.getRequestItems(), existingStep);

		return upsertResourceItems(request.getRequestItems(), existingStep, request.getSyncAdvise(), identifierString2JsonResource, identifierString2ExistingResource);
	}

	@NonNull
	private List<JsonWorkOrderResourceUpsertResponse> upsertResourceItems(
			@NonNull final List<JsonWorkOrderResourceUpsertItemRequest> jsonResources,
			@NonNull final WOProjectStep existingStep,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final Map<IdentifierString, JsonWorkOrderResourceUpsertItemRequest> identifierString2JsonResource,
			@NonNull final Map<IdentifierString, WOProjectResource> identifierString2ExistingResource)
	{
		final ImmutableList.Builder<WOProjectResource> resourceToUpdate = ImmutableList.builder();
		final ImmutableList.Builder<CreateWOProjectResourceRequest> resourceToCreate = ImmutableList.builder();
		final ImmutableList.Builder<WOProjectResourceResponseMapper> responseMapper = ImmutableList.builder();
		final ImmutableList.Builder<WOProjectResource> resourceCollectorBuilder = ImmutableList.builder();

		for (final JsonWorkOrderResourceUpsertItemRequest jsonWorkOrderResourceUpsertItemRequest : jsonResources)
		{
			final String identifier = jsonWorkOrderResourceUpsertItemRequest.getResourceIdentifier();
			final IdentifierString identifierString = IdentifierString.of(identifier);

			final JsonWorkOrderResourceUpsertItemRequest requestItem = identifierString2JsonResource.get(identifierString);

			final Optional<WOProjectResource> existingResourceOpt = Optional.ofNullable(identifierString2ExistingResource.get(identifierString));

			if (existingResourceOpt.isPresent() && !syncAdvise.getIfExists().isUpdate())
			{
				final WOProjectResource existingResource = existingResourceOpt.get();

				responseMapper.add(WOProjectResourceResponseMapper.builder()
										   .identifier(identifier)
										   .syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE)
										   .resourceService(resourceService)
										   .orgId(existingStep.getOrgId())
										   .build());

				resourceCollectorBuilder.add(existingResource);
			}
			else if (existingResourceOpt.isPresent() || !syncAdvise.isFailIfNotExists())
			{
				final WOProjectResource existingResource = existingResourceOpt.orElse(null);
				final JsonResponseUpsertItem.SyncOutcome syncOutcome;

				if (existingResource == null)
				{
					resourceToCreate.add(buildCreateWOProjectResourceRequest(existingStep.getOrgId(), requestItem, existingStep.getWoProjectStepId()));

					syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
				}
				else
				{
					resourceToUpdate.add(syncWOProjectResourceWithJson(existingStep.getOrgId(), requestItem, existingResource));

					syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
				}

				responseMapper.add(WOProjectResourceResponseMapper.builder()
										   .identifier(identifier)
										   .syncOutcome(syncOutcome)
										   .resourceService(resourceService)
										   .orgId(existingStep.getOrgId())
										   .build());
			}
			else
			{
				throw MissingResourceException.builder()
						.resourceName("Work order project step resource")
						.parentResource(requestItem)
						.build()
						.setParameter("resourceSyncAdvise", syncAdvise);
			}
		}

		resourceCollectorBuilder.addAll(workOrderProjectResourceRepository.updateAll(resourceToUpdate.build()));

		resourceCollectorBuilder.addAll(workOrderProjectResourceRepository.createAll(resourceToCreate.build()));

		final List<WOProjectResource> resourceCollector = resourceCollectorBuilder.build();

		return responseMapper.build()
				.stream()
				.map(resourceMapper -> resourceMapper.map(resourceCollector))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private CreateWOProjectResourceRequest buildCreateWOProjectResourceRequest(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertItemRequest request,
			@NonNull final WOProjectStepId woProjectStepId)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final Instant assignDateFrom = TimeUtil.asInstant(request.getAssignDateFrom(), zoneId);
		final Instant assignDateTo = TimeUtil.asEndOfDayInstant(request.getAssignDateTo(), zoneId);

		final IdentifierString resourceIdentifier = IdentifierString.of(request.getResourceIdentifier());

		final ResourceId resourceId = ResourceIdentifierUtil.resolveResourceIdentifier(orgId, resourceIdentifier, resourceService);

		return CreateWOProjectResourceRequest.builder()
				.woProjectStepId(woProjectStepId)
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.resourceId(resourceId)
				.isActive(request.getIsActive())
				.isAllDay(request.getIsAllDay())
				.duration(request.getDuration())
				.durationUnit(toDurationUnit(request.getDurationUnit()))
				.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(request.getExternalId())))
				.testFacilityGroupName(request.getTestFacilityGroupName())
				.build();
	}

	@NonNull
	private WOProjectResource syncWOProjectResourceWithJson(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertItemRequest request,
			@NonNull final WOProjectResource existingResource)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final Instant assignDateFrom = TimeUtil.asInstant(request.getAssignDateFrom(), zoneId);
		final Instant assignDateTo = TimeUtil.asEndOfDayInstant(request.getAssignDateTo(), zoneId);

		final IdentifierString resourceIdentifier = IdentifierString.of(request.getResourceIdentifier());

		final WOProjectResource.WOProjectResourceBuilder resourceBuilder = existingResource.toBuilder()
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.resourceId(ResourceIdentifierUtil.resolveResourceIdentifier(orgId, resourceIdentifier, resourceService));

		if (request.isActiveSet())
		{
			resourceBuilder.isActive(request.getIsActive());
		}

		if (request.isAllDaySet())
		{
			resourceBuilder.isAllDay(request.getIsAllDay());
		}

		if (request.isExternalIdSet())
		{
			final JsonExternalId jsonExternalId = request.getExternalId();
			resourceBuilder.externalId(ExternalId.ofOrNull(jsonExternalId.getValue()));
		}

		if (request.isDurationSet())
		{
			resourceBuilder.duration(request.getDuration());
		}

		if (request.isDurationUnitSet())
		{
			resourceBuilder.durationUnit(toDurationUnit(request.getDurationUnit()));
		}

		if (request.isTestFacilityGroupNameSet())
		{
			resourceBuilder.testFacilityGroupName(request.getTestFacilityGroupName());
		}

		return resourceBuilder.build();
	}

	@NonNull
	private Map<IdentifierString, WOProjectResource> woResourceToUpdateMap(
			@NonNull final OrgId orgId,
			@NonNull final List<JsonWorkOrderResourceUpsertItemRequest> jsonResources,
			@NonNull final WOProjectStep existingWOStep)
	{

		final List<WOProjectResource> existingResources = workOrderProjectResourceRepository.getByStepId(existingWOStep.getWoProjectStepId());

		if (existingResources == null || existingResources.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<IdentifierString, WOProjectResource> woResourceToUpdateMap = ImmutableMap.builder();

		for (final JsonWorkOrderResourceUpsertItemRequest jsonResource : jsonResources)
		{
			final IdentifierString identifier = IdentifierString.of(jsonResource.getResourceIdentifier());

			WorkOrderMapperUtil.resolveWOResourceForExternalIdentifier(orgId, identifier, existingResources, resourceService)
					.ifPresent(matchingResource -> woResourceToUpdateMap.put(identifier, matchingResource));
		}

		return woResourceToUpdateMap.build();
	}

	private static void validateJsonWorkOrderResourceUpsertRequest(@NonNull final JsonWorkOrderResourceUpsertRequest request)
	{
		request.getRequestItems().forEach(requestItem -> {
			if (Check.isBlank(requestItem.getResourceIdentifier()))
			{
				throw new MissingPropertyException("resourceIdentifier", request);
			}

			if (requestItem.getAssignDateFrom() == null)
			{
				throw new MissingPropertyException("assignDateFrom", request);
			}

			if (requestItem.getAssignDateTo() == null)
			{
				throw new MissingPropertyException("assignDateTo", request);
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
				.resourceId(JsonMetasfreshId.ofOrNull(ResourceId.toRepoId(resourceData.getResourceId())))
				.stepId(JsonMetasfreshId.of(WOProjectStepId.toRepoId(resourceData.getWoProjectStepId())))
				.budgetProjectId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(resourceData.getBudgetProjectId())))
				.projectResourceBudgetId(JsonMetasfreshId.ofOrNull(BudgetProjectResourceId.toRepoId(resourceData.getProjectResourceBudgetId())))
				.assignDateFrom(TimeUtil.asLocalDate(resourceData.getAssignDateFrom(), zoneId))
				.assignDateTo(TimeUtil.asLocalDate(resourceData.getAssignDateTo(), zoneId))
				.duration(resourceData.getDuration())
				.durationUnit(toJsonDurationUnit(resourceData.getDurationUnit()))
				.isAllDay(resourceData.getIsAllDay())
				.isActive(resourceData.getIsActive())
				.testFacilityGroupName(resourceData.getTestFacilityGroupName())
				.externalId(ExternalId.toValue(resourceData.getExternalId()))
				.build();
	}

	@Nullable
	private static DurationUnit toDurationUnit(@Nullable final JsonDurationUnit jsonDurationUnit)
	{
		if (jsonDurationUnit == null)
		{
			return null;
		}

		switch (jsonDurationUnit)
		{
			case Year:
				return DurationUnit.Year;
			case Month:
				return DurationUnit.Month;
			case Day:
				return DurationUnit.Day;
			case Hour:
				return DurationUnit.Hour;
			case Minute:
				return DurationUnit.Minute;
			case Second:
				return DurationUnit.Second;
			default:
				throw new IllegalStateException("JsonDurationUnit not supported: " + jsonDurationUnit);
		}
	}

	@Nullable
	private static JsonDurationUnit toJsonDurationUnit(@Nullable final DurationUnit durationUnit)
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
}
