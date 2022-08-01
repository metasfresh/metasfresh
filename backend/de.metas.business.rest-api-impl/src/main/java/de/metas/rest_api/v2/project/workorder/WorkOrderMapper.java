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
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.project.workorder.JsonWOStepStatus;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectObjectUnderTest;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.project.workorder.data.WOStepStatus;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectObjectUnderTestResponseMapper;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectResourceResponseMapper;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectResponseMapper;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectStepResponseMapper;
import de.metas.user.UserId;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class WorkOrderMapper
{
	@NonNull
	private final ResourceService resourceService;

	public WorkOrderMapper(@NonNull final ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	@NonNull
	public WOProject mapWOProjectFromJson(
			@NonNull final WOProjectResponseMapper.WOProjectResponseMapperBuilder responseMapper,
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@Nullable final WOProject existingWOProject)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(request.getOrgCode());

		final WOProject.WOProjectBuilder mappedWOProject = mapWOProject(orgId, request, existingWOProject);

		if (request.isStepsSet())
		{
			mappedWOProject.projectSteps(mapWOProjectStep(responseMapper, orgId, request.getSteps(), existingWOProject));
		}

		if (request.isObjectsUnderTestSet())
		{
			mappedWOProject.projectObjectsUnderTest(mapObjectsUnderTest(responseMapper, request.getObjectsUnderTest(), existingWOProject));
		}

		return mappedWOProject.build();
	}

	@NonNull
	private WOProject.WOProjectBuilder mapWOProject(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@Nullable final WOProject existingWOProject)
	{
		final WOProject.WOProjectBuilder updatedWOProjectBuilder = WOProject.builder()
				.projectTypeId(ProjectTypeId.ofRepoId(request.getProjectTypeId().getValue()))
				.orgId(orgId);

		if (existingWOProject != null)
		{
			updatedWOProjectBuilder.projectId(existingWOProject.getProjectIdNonNull());
		}

		if (request.isPriceListVersionIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId())));
		}
		else
		{
			updatedWOProjectBuilder.priceListVersionId(existingWOProject.getPriceListVersionId());
		}

		if (request.isCurrencyIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.currencyId(CurrencyId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getCurrencyId())));
		}
		else
		{
			updatedWOProjectBuilder.currencyId(existingWOProject.getCurrencyId());
		}

		if (request.isSalesRepIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.salesRepId(UserId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getSalesRepId())));
		}
		else
		{
			updatedWOProjectBuilder.salesRepId(existingWOProject.getSalesRepId());
		}

		if (request.isProjectReferenceExtSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.projectReferenceExt(request.getProjectReferenceExt());
		}
		else
		{
			updatedWOProjectBuilder.projectReferenceExt(existingWOProject.getProjectReferenceExt());
		}

		if (request.isProjectParentIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getProjectParentId())));
		}
		else
		{
			updatedWOProjectBuilder.projectParentId(existingWOProject.getProjectParentId());
		}

		if (request.isBusinessPartnerIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getBusinessPartnerId())));
		}
		else
		{
			updatedWOProjectBuilder.bPartnerId(existingWOProject.getBPartnerId());
		}

		if (request.isNameSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.name(request.getName());
		}
		else
		{
			updatedWOProjectBuilder.name(existingWOProject.getName());
		}

		if (request.isValueSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.value(request.getValue());
		}
		else
		{
			updatedWOProjectBuilder.value(existingWOProject.getValue());
		}

		if (request.isDateContractSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.dateContract(request.getDateContract());
		}
		else
		{
			updatedWOProjectBuilder.dateContract(existingWOProject.getDateContract());
		}

		if (request.isDateFinishSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.dateFinish(request.getDateFinish());
		}
		else
		{
			updatedWOProjectBuilder.dateFinish(existingWOProject.getDateFinish());
		}

		if (request.isDescriptionSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.description(request.getDescription());
		}
		else
		{
			updatedWOProjectBuilder.description(existingWOProject.getDescription());
		}

		if (request.isActiveSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.isActive(CoalesceUtil.coalesceNotNull(request.getIsActive(), true));
		}
		else
		{
			updatedWOProjectBuilder.isActive(existingWOProject.getIsActive());
		}

		if (request.isSpecialistConsultantIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.specialistConsultantId(request.getSpecialistConsultantId());
		}
		else
		{
			updatedWOProjectBuilder.specialistConsultantId(existingWOProject.getSpecialistConsultantId());
		}

		if (request.isDateOfProvisionByBPartnerSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.dateOfProvisionByBPartner(TimeUtil.asInstant(request.getDateOfProvisionByBPartner(), orgId));
		}
		else
		{
			updatedWOProjectBuilder.dateOfProvisionByBPartner(existingWOProject.getDateOfProvisionByBPartner());
		}

		return updatedWOProjectBuilder;
	}

	@NonNull
	private List<WOProjectStep> mapWOProjectStep(
			@NonNull final WOProjectResponseMapper.WOProjectResponseMapperBuilder responseMapper,
			@NonNull final OrgId orgId,
			@NonNull final List<JsonWorkOrderStepUpsertRequest> requestSteps,
			@Nullable final WOProject existingWOProject)
	{
		final Map<JsonExternalId, JsonWorkOrderStepUpsertRequest> jsonProjectStepsMap = requestSteps.stream()
				.collect(Collectors.toMap(JsonWorkOrderStepUpsertRequest::getExternalId, Function.identity()));

		final Map<ExternalId, WOProjectStep> wpProjectStepsToUpdate = woProjectStepToUpdateMap(existingWOProject);

		final ImmutableList.Builder<WOProjectStep> projectSteps = ImmutableList.builder();
		final ImmutableMap.Builder<ExternalId, WOProjectStepResponseMapper> stepToExternalIdMap = ImmutableMap.builder();

		for (final Map.Entry<ExternalId, WOProjectStep> woProjectStepEntry : wpProjectStepsToUpdate.entrySet())
		{
			final ExternalId externalId = woProjectStepEntry.getKey();
			final JsonExternalId jsonExternalId = JsonExternalId.of(externalId.getValue());

			final WOProjectStep existingWOProjectStep = woProjectStepEntry.getValue();
			final JsonWorkOrderStepUpsertRequest matchedRequest = jsonProjectStepsMap.get(jsonExternalId);

			if (matchedRequest == null)
			{
				continue;
			}

			final WOProjectStepResponseMapper.WOProjectStepResponseMapperBuilder stepResponseMapper = WOProjectStepResponseMapper.builder();

			final WOProjectStep updatedWOProjectStep = updateWOProjectStepFromJson(
					stepResponseMapper,
					orgId,
					matchedRequest,
					existingWOProjectStep);

			stepResponseMapper.stepExternalId(updatedWOProjectStep.getExternalIdNonNull())
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);

			stepToExternalIdMap.put(updatedWOProjectStep.getExternalIdNonNull(), stepResponseMapper.build());

			projectSteps.add(updatedWOProjectStep);

			//remove the updated keys from map
			jsonProjectStepsMap.remove(jsonExternalId);
		}

		for (final JsonWorkOrderStepUpsertRequest jsonStep : jsonProjectStepsMap.values())
		{
			final WOProjectStepResponseMapper.WOProjectStepResponseMapperBuilder stepResponseMapper = WOProjectStepResponseMapper.builder();

			final WOProjectStep createdWoProjectStep = updateWOProjectStepFromJson(stepResponseMapper, orgId, jsonStep, null);

			projectSteps.add(createdWoProjectStep);

			stepResponseMapper.stepExternalId(createdWoProjectStep.getExternalIdNonNull())
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED);

			stepToExternalIdMap.put(createdWoProjectStep.getExternalIdNonNull(), stepResponseMapper.build());
		}

		responseMapper.stepToExternalIdMap(stepToExternalIdMap.build());

		return projectSteps.build();
	}

	@NonNull
	private Map<ExternalId, WOProjectStep> woProjectStepToUpdateMap(@Nullable final WOProject existingWOProject)
	{
		if (existingWOProject == null)
		{
			return ImmutableMap.of();
		}

		final List<WOProjectStep> woProjectSteps = existingWOProject.getProjectSteps();

		if (woProjectSteps == null || woProjectSteps.isEmpty())
		{
			return ImmutableMap.of();
		}

		return woProjectSteps.stream()
				.filter(step -> step.getExternalId() != null)
				.collect(Collectors.toMap(WOProjectStep::getExternalId, Function.identity()));
	}

	@NonNull
	private WOProjectStep updateWOProjectStepFromJson(
			@NonNull final WOProjectStepResponseMapper.WOProjectStepResponseMapperBuilder stepResponseMapper,
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderStepUpsertRequest request,
			@Nullable final WOProjectStep woProjectStepToUpdate)
	{
		final WOProjectStep.WOProjectStepBuilder updatedWOProjectStepBuilder = WOProjectStep.builder()
				.name(request.getName());

		if (request.isSeqNoSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.seqNo(request.getSeqNo());
		}
		else
		{
			updatedWOProjectStepBuilder.seqNo(woProjectStepToUpdate.getSeqNo());
		}

		if (request.isDateStartSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.dateStart(TimeUtil.asInstant(request.getDateStart(), orgId));
		}
		else
		{
			updatedWOProjectStepBuilder.dateStart(woProjectStepToUpdate.getDateStart());
		}

		if (request.isDateEndSet() || woProjectStepToUpdate == null)
		{
			final Instant dateEnd = TimeUtil.asEndOfDayInstant(request.getDateEnd(), orgId);
			updatedWOProjectStepBuilder.dateEnd(dateEnd);
		}
		else
		{
			updatedWOProjectStepBuilder.dateEnd(woProjectStepToUpdate.getDateEnd());
		}

		if (request.isDescriptionSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.description(request.getDescription());
		}
		else
		{
			updatedWOProjectStepBuilder.description(woProjectStepToUpdate.getDescription());
		}

		if (request.isWoPartialReportDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woPartialReportDate(TimeUtil.asInstant(request.getWoPartialReportDate()));
		}
		else
		{
			updatedWOProjectStepBuilder.woPartialReportDate(woProjectStepToUpdate.getWoPartialReportDate());
		}

		if (request.isWoPlannedResourceDurationHoursSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woPlannedResourceDurationHours(request.getWoPlannedResourceDurationHours());
		}
		else
		{
			updatedWOProjectStepBuilder.woPlannedResourceDurationHours(woProjectStepToUpdate.getWoPlannedResourceDurationHours());
		}

		if (request.isDeliveryDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.deliveryDate(TimeUtil.asInstant(request.getDeliveryDate()));
		}
		else
		{
			updatedWOProjectStepBuilder.deliveryDate(woProjectStepToUpdate.getDeliveryDate());
		}

		if (request.isWoTargetStartDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woTargetStartDate(TimeUtil.asInstant(request.getWoTargetStartDate()));
		}
		else
		{
			updatedWOProjectStepBuilder.woTargetStartDate(woProjectStepToUpdate.getWoTargetStartDate());
		}

		if (request.isWoTargetEndDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woTargetEndDate(TimeUtil.asInstant(request.getWoTargetEndDate()));
		}
		else
		{
			updatedWOProjectStepBuilder.woTargetEndDate(woProjectStepToUpdate.getWoTargetEndDate());
		}

		if (request.isWoPlannedPersonDurationHoursSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woPlannedPersonDurationHours(request.getWoPlannedPersonDurationHours());
		}
		else
		{
			updatedWOProjectStepBuilder.woPlannedPersonDurationHours(woProjectStepToUpdate.getWoPlannedPersonDurationHours());
		}

		if (request.isWoStepStatusSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woStepStatus(toWoStepStatus(request.getWoStepStatus()));
		}
		else
		{
			updatedWOProjectStepBuilder.woStepStatus(woProjectStepToUpdate.getWoStepStatus());
		}

		if (request.isWoFindingsReleasedDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woFindingsReleasedDate(TimeUtil.asInstant(request.getWoFindingsReleasedDate()));
		}
		else
		{
			updatedWOProjectStepBuilder.woFindingsReleasedDate(woProjectStepToUpdate.getWoFindingsReleasedDate());
		}

		if (request.isWoFindingsCreatedDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woFindingsCreatedDate(TimeUtil.asInstant(request.getWoFindingsCreatedDate()));
		}
		else
		{
			updatedWOProjectStepBuilder.woFindingsCreatedDate(woProjectStepToUpdate.getWoFindingsCreatedDate());
		}

		return updatedWOProjectStepBuilder
				.projectResources(mapWOProjectResource(stepResponseMapper, orgId, request.getResourceRequests(), woProjectStepToUpdate))
				.build();
	}

	@NonNull
	private List<WOProjectResource> mapWOProjectResource(
			@NonNull final WOProjectStepResponseMapper.WOProjectStepResponseMapperBuilder stepResponseMapper,
			@NonNull final OrgId orgId,
			@Nullable final List<JsonWorkOrderResourceUpsertRequest> jsonResources,
			@Nullable final WOProjectStep woProjectStepToUpdate)
	{
		if (jsonResources == null || jsonResources.isEmpty())
		{
			return ImmutableList.of();
		}

		final Map<JsonExternalId, JsonWorkOrderResourceUpsertRequest> jsonProjectResources = jsonResources.stream()
				.collect(Collectors.toMap(JsonWorkOrderResourceUpsertRequest::getExternalId, Function.identity()));

		final Map<ExternalId, WOProjectResource> woProjectResourceToUpdateMap = woProjectResourceToUpdateMap(woProjectStepToUpdate);

		final ImmutableList.Builder<WOProjectResource> resourcesResponse = ImmutableList.builder();
		final ImmutableMap.Builder<ExternalId, WOProjectResourceResponseMapper> resourceToExternalIdMap = ImmutableMap.builder();

		for (final Map.Entry<ExternalId, WOProjectResource> woProjectResourceEntry : woProjectResourceToUpdateMap.entrySet())
		{
			final ExternalId externalId = woProjectResourceEntry.getKey();
			final JsonExternalId jsonExternalId = JsonExternalId.of(externalId.getValue());

			final JsonWorkOrderResourceUpsertRequest jsonResource = jsonProjectResources.get(jsonExternalId);

			if (jsonResource == null)
			{
				continue;
			}

			final WOProjectResource woProjectResource = woProjectResourceEntry.getValue();

			final WOProjectResource updatedResource = updateWOProjectResource(
					orgId,
					jsonResource,
					woProjectResource);

			final WOProjectResourceResponseMapper resourceResponseMapper = WOProjectResourceResponseMapper.builder()
					.resourceExternalId(updatedResource.getExternalIdNotNull())
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED)
					.build();

			resourceToExternalIdMap.put(updatedResource.getExternalIdNotNull(), resourceResponseMapper);
			resourcesResponse.add(updatedResource);

			//remove updated keys
			jsonProjectResources.remove(jsonExternalId);
		}

		jsonProjectResources.values()
				.forEach(jsonResource -> {
							 final WOProjectResource createdWOResource = createWOProjectResource(orgId, jsonResource);

							 resourcesResponse.add(createdWOResource);

							 final WOProjectResourceResponseMapper resourceResponseMapper = WOProjectResourceResponseMapper.builder()
									 .resourceExternalId(createdWOResource.getExternalIdNotNull())
									 .syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED)
									 .build();

							 resourceToExternalIdMap.put(createdWOResource.getExternalIdNotNull(), resourceResponseMapper);
						 }
				);

		stepResponseMapper.resourceToExternalIdMap(resourceToExternalIdMap.build());

		return resourcesResponse.build();
	}

	@NonNull
	private Map<ExternalId, WOProjectResource> woProjectResourceToUpdateMap(@Nullable final WOProjectStep existingWOStep)
	{
		if (existingWOStep == null)
		{
			return ImmutableMap.of();
		}

		final List<WOProjectResource> woProjectResources = existingWOStep.getProjectResources();

		if (woProjectResources == null || woProjectResources.isEmpty())
		{
			return ImmutableMap.of();
		}

		return woProjectResources.stream()
				.filter(step -> step.getExternalId() != null)
				.collect(Collectors.toMap(WOProjectResource::getExternalId, Function.identity()));
	}

	@NonNull
	private WOProjectResource updateWOProjectResource(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertRequest request,
			@NonNull final WOProjectResource existingWOProjectResource)
	{
		final Instant assignDateFrom = TimeUtil.asInstant(request.getAssignDateFrom(), orgId);
		final Instant assignDateTo = TimeUtil.asEndOfDayInstant(request.getAssignDateTo(), orgId);

		final WOProjectResource.WOProjectResourceBuilder updatedWOProjectResourceBuilder = WOProjectResource.builder()
				.externalId(ExternalId.of(request.getExternalId().getValue()))
				.woProjectResourceId(existingWOProjectResource.getWOProjectResourceIdNotNull())
				.budgetProjectId(existingWOProjectResource.getBudgetProjectId())
				.projectResourceBudgetId(existingWOProjectResource.getProjectResourceBudgetId())
				.duration(existingWOProjectResource.getDuration())
				.durationUnit(existingWOProjectResource.getDurationUnit())
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo);

		if (request.isResourceIdentifierSet())
		{
			final ResourceId resourceId = extractResourceId(orgId, request);
			updatedWOProjectResourceBuilder.resourceId(resourceId);
		}
		else
		{
			updatedWOProjectResourceBuilder.resourceId(existingWOProjectResource.getResourceId());
		}

		if (request.isActiveSet())
		{
			updatedWOProjectResourceBuilder.isActive(request.getIsActive());
		}
		else
		{
			updatedWOProjectResourceBuilder.isActive(existingWOProjectResource.getIsActive());
		}

		if (request.isAllDaySet())
		{
			updatedWOProjectResourceBuilder.isAllDay(request.getIsAllDay());
		}
		else
		{
			updatedWOProjectResourceBuilder.isAllDay(existingWOProjectResource.getIsAllDay());
		}

		return updatedWOProjectResourceBuilder.build();
	}

	@NonNull
	private WOProjectResource createWOProjectResource(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertRequest resourceRequest)
	{
		final ResourceId resourceId = extractResourceId(orgId, resourceRequest);

		final Instant assignDateFrom = TimeUtil.asInstant(resourceRequest.getAssignDateFrom(), orgId);
		final Instant assignDateTo = TimeUtil.asEndOfDayInstant(resourceRequest.getAssignDateTo(), orgId);

		return WOProjectResource.builder()
				.resourceId(resourceId)
				.isActive(resourceRequest.getIsActive())
				.isAllDay(resourceRequest.getIsAllDay())
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.duration(resourceRequest.getDuration())
				.durationUnit(resourceRequest.getDurationUnit().name())
				.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(resourceRequest.getExternalId())))
				.build();
	}

	@NonNull
	private ResourceId extractResourceId(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertRequest request
	)
	{
		final IdentifierString resourceIdentifier = IdentifierString.of(request.getResourceIdentifier());

		final Predicate<Resource> resourcePredicate;
		switch (resourceIdentifier.getType())
		{
			case METASFRESH_ID:
				resourcePredicate = r -> ResourceId.toRepoId(r.getResourceId()) == resourceIdentifier.asMetasfreshId().getValue();
				break;
			case VALUE:
				resourcePredicate = r -> // make sure that org and value match
						(r.getOrgId().isAny() || orgId.isAny() || Objects.equals(r.getOrgId(), orgId))
								&& Objects.equals(r.getValue(), resourceIdentifier.asValue());
				break;
			case INTERNALNAME:
				resourcePredicate = r -> // make sure that org and value match
						(r.getOrgId().isAny() || orgId.isAny() || Objects.equals(r.getOrgId(), orgId))
								&& Objects.equals(r.getInternalName(), resourceIdentifier.asInternalName());
				break;
			default:
				throw new InvalidIdentifierException(resourceIdentifier.getRawIdentifierString(), request);
		}

		return resourceService.getAllActiveResources()
				.stream()
				.filter(resourcePredicate)
				.findAny()
				.map(Resource::getResourceId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("resourceIdentifier")
						.resourceIdentifier(request.getResourceIdentifier())
						.parentResource(request)
						.build());
	}

	@NonNull
	private List<WOProjectObjectUnderTest> mapObjectsUnderTest(
			@NonNull final WOProjectResponseMapper.WOProjectResponseMapperBuilder responseMapper,
			@NonNull final List<JsonWorkOrderObjectUnderTestUpsertRequest> jsonObjectUnderTests,
			@Nullable final WOProject existingWOProject)
	{
		final Map<JsonExternalId, JsonWorkOrderObjectUnderTestUpsertRequest> jsonObjectUnderTestMap = jsonObjectUnderTests
				.stream()
				.collect(Collectors.toMap(JsonWorkOrderObjectUnderTestUpsertRequest::getExternalId, Function.identity()));

		final ImmutableList.Builder<WOProjectObjectUnderTest> objectsUnderTest = ImmutableList.builder();
		final ImmutableMap.Builder<ExternalId, WOProjectObjectUnderTestResponseMapper> objectUnderTestToExternalIdMap = ImmutableMap.builder();

		if (existingWOProject != null)
		{
			for (final WOProjectObjectUnderTest existingObjectUnderTest : existingWOProject.getProjectObjectsUnderTest())
			{
				if (existingObjectUnderTest.getExternalId() == null)
				{
					continue; // can't match an object that has no external ID
				}

				final JsonExternalId externalId = JsonExternalId.of(existingObjectUnderTest.getExternalId().getValue());
				final JsonWorkOrderObjectUnderTestUpsertRequest matchedJsonObjectUnderTest = jsonObjectUnderTestMap.remove(externalId);

				if (matchedJsonObjectUnderTest == null)
				{
					continue;
				}

				final WOProjectObjectUnderTest updatedObjectUnderTest = mapObjectUnderTest(
						existingObjectUnderTest,
						matchedJsonObjectUnderTest);

				objectsUnderTest.add(updatedObjectUnderTest);

				final WOProjectObjectUnderTestResponseMapper objectUnderTestResponseMapper = WOProjectObjectUnderTestResponseMapper.builder()
						.objectUnderTestExternalId(updatedObjectUnderTest.getExternalIdNonNull())
						.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED)
						.build();

				objectUnderTestToExternalIdMap.put(updatedObjectUnderTest.getExternalIdNonNull(), objectUnderTestResponseMapper);
			}
		}

		jsonObjectUnderTestMap.values()
				.stream()
				.map(WorkOrderMapper::createNewObjectUnderTestFromJson)
				.forEach(objectUnderTest -> {
					objectsUnderTest.add(objectUnderTest);

					final WOProjectObjectUnderTestResponseMapper objectUnderTestResponseMapper = WOProjectObjectUnderTestResponseMapper.builder()
							.objectUnderTestExternalId(objectUnderTest.getExternalIdNonNull())
							.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
							.build();

					objectUnderTestToExternalIdMap.put(objectUnderTest.getExternalIdNonNull(), objectUnderTestResponseMapper);

				});

		responseMapper.objectUnderTestToExternalIdMap(objectUnderTestToExternalIdMap.build());

		return objectsUnderTest.build();
	}

	@NonNull
	private WOProjectObjectUnderTest mapObjectUnderTest(
			@NonNull final WOProjectObjectUnderTest objectUnderTest,
			@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request)
	{
		final WOProjectObjectUnderTest.WOProjectObjectUnderTestBuilder builder = WOProjectObjectUnderTest.builder();

		if (request.isNumberOfObjectsUnderTestSet())
		{
			builder.numberOfObjectsUnderTest(request.getNumberOfObjectsUnderTest());
		}
		else
		{
			builder.numberOfObjectsUnderTest(objectUnderTest.getNumberOfObjectsUnderTest());
		}

		if (request.isWoDeliveryNoteSet())
		{
			builder.woDeliveryNote(request.getWoDeliveryNote());
		}
		else
		{
			builder.woDeliveryNote(objectUnderTest.getWoDeliveryNote());
		}

		if (request.isWoManufacturerSet())
		{
			builder.woManufacturer(request.getWoManufacturer());
		}
		else
		{
			builder.woManufacturer(objectUnderTest.getWoManufacturer());
		}

		if (request.isWoObjectTypeSet())
		{
			builder.woObjectType(request.getWoObjectType());
		}
		else
		{
			builder.woObjectType(objectUnderTest.getWoObjectType());
		}

		if (request.isWoObjectNameSet())
		{
			builder.woObjectName(request.getWoObjectName());
		}
		else
		{
			builder.woObjectName(objectUnderTest.getWoObjectName());
		}

		if (request.isWoObjectWhereaboutsSet())
		{
			builder.woObjectWhereabouts(request.getWoObjectWhereabouts());
		}
		else
		{
			builder.woObjectWhereabouts(objectUnderTest.getWoObjectWhereabouts());
		}

		if (request.isExternalIdSet())
		{
			builder.externalId(ExternalId.of(request.getExternalId().getValue()));
		}
		else
		{
			builder.externalId(objectUnderTest.getExternalId());
		}

		return builder.build();
	}

	@NonNull
	public static WOProjectObjectUnderTest createNewObjectUnderTestFromJson(@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request)
	{
		return WOProjectObjectUnderTest.builder()
				.numberOfObjectsUnderTest(request.getNumberOfObjectsUnderTest())
				.externalId(ExternalId.of(request.getExternalId().getValue()))
				.woDeliveryNote(request.getWoDeliveryNote())
				.woManufacturer(request.getWoManufacturer())
				.woObjectType(request.getWoObjectType())
				.woObjectName(request.getWoObjectName())
				.woObjectWhereabouts(request.getWoObjectWhereabouts())
				.build();
	}

	@Nullable
	private static WOStepStatus toWoStepStatus(@Nullable JsonWOStepStatus jsonWOStepStatus)
	{
		if (jsonWOStepStatus == null)
		{
			return null;
		}

		switch (jsonWOStepStatus)
		{
			case CREATED:
				return WOStepStatus.CREATED;
			case RECEIVED:
				return WOStepStatus.RECEIVED;
			case RELEASED:
				return WOStepStatus.RELEASED;
			case EARMARKED:
				return WOStepStatus.EARMARKED;
			case READYFORTESTING:
				return WOStepStatus.READYFORTESTING;
			case INTESTING:
				return WOStepStatus.INTESTING;
			case EXECUTED:
				return WOStepStatus.EXECUTED;
			case READY:
				return WOStepStatus.READY;
			case CANCELED:
				return WOStepStatus.CANCELED;
			default:
				throw new IllegalStateException("JsonWOStepStatus not supported: " + jsonWOStepStatus);
		}
	}
}
