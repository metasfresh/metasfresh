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
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonDurationUnit;
import de.metas.common.rest_api.v2.project.workorder.JsonWOStepStatus;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.workorder.data.DurationUnit;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectObjectUnderTest;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.project.workorder.data.WOStepStatus;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectObjectUnderTestResponseMapper;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectResourceResponseMapper;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectResponseMapper;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectStepResponseMapper;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class WorkOrderMapper
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

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
			@NonNull final SyncAdvise parentSyncAdvise,
			@Nullable final WOProject existingWOProject)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(request.getOrgCode());

		final WOProject.WOProjectBuilder mappedWOProject = mapWOProject(orgId, request, existingWOProject);

		mappedWOProject.projectSteps(mapWOProjectStep(responseMapper, parentSyncAdvise, orgId, request.getSteps(), existingWOProject));
		mappedWOProject.projectObjectsUnderTest(mapObjectsUnderTest(responseMapper, parentSyncAdvise, request.getObjectsUnderTest(), existingWOProject));

		return mappedWOProject.build();
	}

	@NonNull
	private WOProject.WOProjectBuilder mapWOProject(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@Nullable final WOProject existingWOProject)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

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
			updatedWOProjectBuilder.dateContract(TimeUtil.asInstant(request.getDateContract(), zoneId));
		}
		else
		{
			updatedWOProjectBuilder.dateContract(existingWOProject.getDateContract());
		}

		if (request.isDateFinishSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.dateFinish(TimeUtil.asInstant(request.getDateFinish(), zoneId));
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

		if (request.isBpartnerDepartmentSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.bpartnerDepartment(request.getBpartnerDepartment());
		}
		else
		{
			updatedWOProjectBuilder.bpartnerDepartment(existingWOProject.getBpartnerDepartment());
		}

		if (request.isWoOwnerSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.woOwner(request.getWoOwner());
		}
		else
		{
			updatedWOProjectBuilder.woOwner(existingWOProject.getWoOwner());
		}

		if (request.isPoReferenceSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.poReference(request.getPoReference());
		}
		else
		{
			updatedWOProjectBuilder.poReference(existingWOProject.getPoReference());
		}

		if (request.isBpartnerTargetDateSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.bpartnerTargetDate(TimeUtil.asInstant(request.getBpartnerTargetDate(), zoneId));
		}
		else
		{
			updatedWOProjectBuilder.bpartnerTargetDate(existingWOProject.getBpartnerTargetDate());
		}

		if (request.isWoProjectCreatedDateSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.woProjectCreatedDate(TimeUtil.asInstant(request.getWoProjectCreatedDate(), zoneId));
		}
		else
		{
			updatedWOProjectBuilder.woProjectCreatedDate(existingWOProject.getWoProjectCreatedDate());
		}

		return updatedWOProjectBuilder;
	}

	@NonNull
	private List<WOProjectStep> mapWOProjectStep(
			@NonNull final WOProjectResponseMapper.WOProjectResponseMapperBuilder responseMapper,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final OrgId orgId,
			@NonNull final List<JsonWorkOrderStepUpsertRequest> requestSteps,
			@Nullable final WOProject existingWOProject)
	{
		final Map<IdentifierString, JsonWorkOrderStepUpsertRequest> identifierString2JsonStep = requestSteps.stream()
				.collect(Collectors.toMap((jsonStep) -> IdentifierString.of(jsonStep.getIdentifier()), Function.identity()));

		final Map<IdentifierString, WOProjectStep> woProjectStepsToUpdate = woProjectStepToUpdateMap(requestSteps, existingWOProject);

		final ImmutableList.Builder<WOProjectStep> projectSteps = ImmutableList.builder();
		final ImmutableMap.Builder<String, WOProjectStepResponseMapper> identifier2ResponseMapper = ImmutableMap.builder();

		for (final Map.Entry<IdentifierString, JsonWorkOrderStepUpsertRequest> identifier2JsonStepEntry : identifierString2JsonStep.entrySet())
		{
			final IdentifierString stepIdentifierString = identifier2JsonStepEntry.getKey();

			final WOProjectStep matchedStep = woProjectStepsToUpdate.get(stepIdentifierString);

			if (matchedStep == null)
			{
				continue;
			}

			final JsonWorkOrderStepUpsertRequest jsonStep = identifier2JsonStepEntry.getValue();

			final String identifier = jsonStep.getIdentifier();

			final WOProjectStepResponseMapper.WOProjectStepResponseMapperBuilder stepResponseMapper = WOProjectStepResponseMapper.builder();

			if (parentSyncAdvise.getIfExists().isUpdate())
			{
				final WOProjectStep woProjectStepToUpdate = updateWOProjectStepFromJson(
						stepResponseMapper,
						parentSyncAdvise,
						orgId,
						jsonStep,
						matchedStep);

				stepResponseMapper
						.identifier(identifier)
						.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);

				identifier2ResponseMapper.put(identifier, stepResponseMapper.build());

				projectSteps.add(woProjectStepToUpdate);
			}
			else
			{
				stepResponseMapper
						.identifier(identifier)
						.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);

				identifier2ResponseMapper.put(identifier, stepResponseMapper.build());

				projectSteps.add(matchedStep);
			}
		}

		final Map<IdentifierString, Object> identifierString2JsonStepToCreate = Maps.difference(identifierString2JsonStep, woProjectStepsToUpdate).entriesOnlyOnLeft();

		for (final Object jsonObject : identifierString2JsonStepToCreate.values())
		{
			final JsonWorkOrderStepUpsertRequest jsonStep = (JsonWorkOrderStepUpsertRequest)jsonObject;

			final WOProjectStepResponseMapper.WOProjectStepResponseMapperBuilder stepResponseMapper = WOProjectStepResponseMapper.builder();

			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("C_Project_WO_Step")
						.resourceIdentifier(jsonStep.getExternalId().getValue())
						.build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}

			final WOProjectStep createdWoProjectStep = updateWOProjectStepFromJson(stepResponseMapper, parentSyncAdvise, orgId, jsonStep, null);

			projectSteps.add(createdWoProjectStep);

			final String identifier = jsonStep.getIdentifier();

			stepResponseMapper.identifier(identifier)
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED);

			identifier2ResponseMapper.put(identifier, stepResponseMapper.build());
		}

		responseMapper.identifier2StepMapper(identifier2ResponseMapper.build());

		return projectSteps.build();
	}

	@NonNull
	private Map<IdentifierString, WOProjectStep> woProjectStepToUpdateMap(
			@NonNull List<JsonWorkOrderStepUpsertRequest> requestSteps,
			@Nullable final WOProject existingWOProject)
	{
		if (existingWOProject == null)
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<IdentifierString, WOProjectStep> woProjectStepToUpdateMap = ImmutableMap.builder();

		for (final JsonWorkOrderStepUpsertRequest upsertRequest : requestSteps)
		{
			final IdentifierString stepIdentifier = IdentifierString.of(upsertRequest.getIdentifier());

			existingWOProject.getStepForLookupFunction((woProjectSteps) -> WorkOrderMapperUtil.resolveStepForExternalIdentifier(stepIdentifier, woProjectSteps))
					.ifPresent(matchingProject -> woProjectStepToUpdateMap.put(stepIdentifier, matchingProject));
		}

		return woProjectStepToUpdateMap.build();
	}

	@NonNull
	private WOProjectStep updateWOProjectStepFromJson(
			@NonNull final WOProjectStepResponseMapper.WOProjectStepResponseMapperBuilder stepResponseMapper,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderStepUpsertRequest request,
			@Nullable final WOProjectStep woProjectStepToUpdate)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final WOProjectStep.WOProjectStepBuilder updatedWOProjectStepBuilder = WOProjectStep.builder()
				.name(request.getName());

		if (request.isExternalIdSet() || woProjectStepToUpdate == null)
		{
			final JsonExternalId jsonExternalId = request.getExternalId();
			final ExternalId externalId = ExternalId.of(jsonExternalId.getValue());
			updatedWOProjectStepBuilder.externalId(externalId);
		}
		else
		{
			updatedWOProjectStepBuilder.externalId(woProjectStepToUpdate.getExternalId());
		}

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
			updatedWOProjectStepBuilder.dateStart(TimeUtil.asInstant(request.getDateStart(), zoneId));
		}
		else
		{
			updatedWOProjectStepBuilder.dateStart(woProjectStepToUpdate.getDateStart());
		}

		if (request.isDateEndSet() || woProjectStepToUpdate == null)
		{
			final Instant dateEnd = TimeUtil.asEndOfDayInstant(request.getDateEnd(), zoneId);
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
			updatedWOProjectStepBuilder.woPartialReportDate(TimeUtil.asInstant(request.getWoPartialReportDate(), zoneId));
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
			updatedWOProjectStepBuilder.deliveryDate(TimeUtil.asInstant(request.getDeliveryDate(), zoneId));
		}
		else
		{
			updatedWOProjectStepBuilder.deliveryDate(woProjectStepToUpdate.getDeliveryDate());
		}

		if (request.isWoTargetStartDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woTargetStartDate(TimeUtil.asInstant(request.getWoTargetStartDate(), zoneId));
		}
		else
		{
			updatedWOProjectStepBuilder.woTargetStartDate(woProjectStepToUpdate.getWoTargetStartDate());
		}

		if (request.isWoTargetEndDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woTargetEndDate(TimeUtil.asInstant(request.getWoTargetEndDate(), zoneId));
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
			updatedWOProjectStepBuilder.woFindingsReleasedDate(TimeUtil.asInstant(request.getWoFindingsReleasedDate(), zoneId));
		}
		else
		{
			updatedWOProjectStepBuilder.woFindingsReleasedDate(woProjectStepToUpdate.getWoFindingsReleasedDate());
		}

		if (request.isWoFindingsCreatedDateSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.woFindingsCreatedDate(TimeUtil.asInstant(request.getWoFindingsCreatedDate(), zoneId));
		}
		else
		{
			updatedWOProjectStepBuilder.woFindingsCreatedDate(woProjectStepToUpdate.getWoFindingsCreatedDate());
		}

		return updatedWOProjectStepBuilder
				.projectResources(mapWOProjectResource(stepResponseMapper, parentSyncAdvise, orgId, request.getResources(), woProjectStepToUpdate))
				.build();
	}

	@NonNull
	private List<WOProjectResource> mapWOProjectResource(
			@NonNull final WOProjectStepResponseMapper.WOProjectStepResponseMapperBuilder stepResponseMapper,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final OrgId orgId,
			@Nullable final List<JsonWorkOrderResourceUpsertRequest> jsonResources,
			@Nullable final WOProjectStep woProjectStepToUpdate)
	{
		if (jsonResources == null || jsonResources.isEmpty())
		{
			return ImmutableList.of();
		}

		final Map<IdentifierString, JsonWorkOrderResourceUpsertRequest> identifierString2JsonResource = jsonResources.stream()
				.collect(Collectors.toMap((resource) -> IdentifierString.of(resource.getResourceIdentifier()), Function.identity()));

		final Map<IdentifierString, WOProjectResource> woProjectResourceToUpdateMap = woResourceToUpdateMap(orgId, jsonResources, woProjectStepToUpdate);

		final ImmutableList.Builder<WOProjectResource> resourcesResponse = ImmutableList.builder();

		final ImmutableMap.Builder<String, WOProjectResourceResponseMapper> resourceIdentifier2ResponseMapper = ImmutableMap.builder();

		for (final Map.Entry<IdentifierString, JsonWorkOrderResourceUpsertRequest> jsonResourceEntry : identifierString2JsonResource.entrySet())
		{
			final IdentifierString identifier = jsonResourceEntry.getKey();

			final WOProjectResource woResource = woProjectResourceToUpdateMap.get(identifier);

			if (woResource == null)
			{
				continue;
			}

			final JsonWorkOrderResourceUpsertRequest jsonResource = jsonResourceEntry.getValue();

			final String jsonIdentifier = jsonResource.getResourceIdentifier();

			final WOProjectResourceResponseMapper.WOProjectResourceResponseMapperBuilder responseMapperBuilder = WOProjectResourceResponseMapper.builder()
					.identifier(jsonIdentifier)
					.orgId(orgId)
					.resourceService(resourceService);

			if (parentSyncAdvise.getIfExists().isUpdate())
			{
				final WOProjectResource updatedResource = updateWOProjectResource(
						orgId,
						jsonResource,
						woResource);

				responseMapperBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);

				resourceIdentifier2ResponseMapper.put(jsonIdentifier, responseMapperBuilder.build());

				resourcesResponse.add(updatedResource);
			}
			else
			{
				responseMapperBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);

				resourceIdentifier2ResponseMapper.put(jsonIdentifier, responseMapperBuilder.build());

				resourcesResponse.add(woResource);
			}
		}

		final Map<IdentifierString, Object> identifierString2JsonResourceToCreate = Maps.difference(identifierString2JsonResource, woProjectResourceToUpdateMap).entriesOnlyOnLeft();

		for (final Object value : identifierString2JsonResourceToCreate.values())
		{
			final JsonWorkOrderResourceUpsertRequest jsonResource = (JsonWorkOrderResourceUpsertRequest)value;

			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("C_Project_WO_Resource")
						.build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}

			final WOProjectResource createdWOResource = createWOProjectResource(orgId, jsonResource);

			resourcesResponse.add(createdWOResource);

			final WOProjectResourceResponseMapper resourceResponseMapper = WOProjectResourceResponseMapper.builder()
					.identifier(jsonResource.getResourceIdentifier())
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
					.orgId(orgId)
					.resourceService(resourceService)
					.build();

			resourceIdentifier2ResponseMapper.put(jsonResource.getResourceIdentifier(), resourceResponseMapper);
		}

		stepResponseMapper.resourceIdentifier2ResourceMapper(resourceIdentifier2ResponseMapper.build());

		return resourcesResponse.build();
	}

	@NonNull
	private Map<IdentifierString, WOProjectResource> woResourceToUpdateMap(
			@NonNull final OrgId orgId,
			@NonNull final List<JsonWorkOrderResourceUpsertRequest> jsonResources,
			@Nullable final WOProjectStep existingWOStep)
	{
		if (existingWOStep == null || existingWOStep.getProjectResources().isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<IdentifierString, WOProjectResource> woResourceToUpdateMap = ImmutableMap.builder();

		for (final JsonWorkOrderResourceUpsertRequest jsonResource : jsonResources)
		{
			final IdentifierString identifier = IdentifierString.of(jsonResource.getResourceIdentifier());

			existingWOStep
					.getResourceForLookupFunction((woProjectResources) -> WorkOrderMapperUtil.resolveWOResourceForExternalIdentifier(orgId, identifier, woProjectResources, resourceService))
					.ifPresent(matchingResource -> woResourceToUpdateMap.put(identifier, matchingResource));
		}

		return woResourceToUpdateMap.build();
	}

	@NonNull
	private WOProjectResource updateWOProjectResource(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertRequest request,
			@NonNull final WOProjectResource existingWOProjectResource)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final Instant assignDateFrom = TimeUtil.asInstant(request.getAssignDateFrom(), zoneId);
		final Instant assignDateTo = TimeUtil.asEndOfDayInstant(request.getAssignDateTo(), zoneId);

		final IdentifierString resourceIdentifier = IdentifierString.of(request.getResourceIdentifier());

		final WOProjectResource.WOProjectResourceBuilder updatedWOProjectResourceBuilder = WOProjectResource.builder()
				.woProjectResourceId(existingWOProjectResource.getWOProjectResourceIdNotNull())
				.budgetProjectId(existingWOProjectResource.getBudgetProjectId())
				.projectResourceBudgetId(existingWOProjectResource.getProjectResourceBudgetId())
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.resourceId(ResourceIdentifierUtil.resolveResourceForExternalIdentifier(orgId, resourceIdentifier, resourceService));

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

		if (request.isExternalIdSet())
		{
			final JsonExternalId jsonExternalId = request.getExternalId();
			updatedWOProjectResourceBuilder.externalId(ExternalId.ofOrNull(jsonExternalId.getValue()));
		}
		else
		{
			updatedWOProjectResourceBuilder.externalId(existingWOProjectResource.getExternalId());
		}

		if (request.isDurationSet())
		{
			updatedWOProjectResourceBuilder.duration(request.getDuration());
		}
		else
		{
			updatedWOProjectResourceBuilder.duration(existingWOProjectResource.getDuration());
		}

		if (request.isDurationUnitSet())
		{
			updatedWOProjectResourceBuilder.durationUnit(toDurationUnit(request.getDurationUnit()));
		}
		else
		{
			updatedWOProjectResourceBuilder.durationUnit(existingWOProjectResource.getDurationUnit());
		}

		if (request.isTestFacilityGroupNameSet())
		{
			updatedWOProjectResourceBuilder.testFacilityGroupName(request.getTestFacilityGroupName());
		}
		else
		{
			updatedWOProjectResourceBuilder.testFacilityGroupName(existingWOProjectResource.getTestFacilityGroupName());
		}

		return updatedWOProjectResourceBuilder.build();
	}

	@NonNull
	private WOProjectResource createWOProjectResource(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertRequest resourceRequest)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final Instant assignDateFrom = TimeUtil.asInstant(resourceRequest.getAssignDateFrom(), zoneId);
		final Instant assignDateTo = TimeUtil.asEndOfDayInstant(resourceRequest.getAssignDateTo(), zoneId);

		final IdentifierString resourceIdentifier = IdentifierString.of(resourceRequest.getResourceIdentifier());

		final ResourceId resourceId = ResourceIdentifierUtil.resolveResourceForExternalIdentifier(orgId, resourceIdentifier, resourceService);

		return WOProjectResource.builder()
				.resourceId(resourceId)
				.isActive(resourceRequest.getIsActive())
				.isAllDay(resourceRequest.getIsAllDay())
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.duration(resourceRequest.getDuration())
				.durationUnit(toDurationUnit(resourceRequest.getDurationUnit()))
				.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(resourceRequest.getExternalId())))
				.testFacilityGroupName(resourceRequest.getTestFacilityGroupName())
				.build();
	}

	@NonNull
	private List<WOProjectObjectUnderTest> mapObjectsUnderTest(
			@NonNull final WOProjectResponseMapper.WOProjectResponseMapperBuilder responseMapper,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final List<JsonWorkOrderObjectUnderTestUpsertRequest> jsonObjectsUnderTest,
			@Nullable final WOProject existingWOProject)
	{
		final Map<IdentifierString, JsonWorkOrderObjectUnderTestUpsertRequest> identifierString2JsonObjectUnderTest = jsonObjectsUnderTest.stream()
				.collect(Collectors.toMap((jsonObjectUnderTest) -> IdentifierString.of(jsonObjectUnderTest.getIdentifier()), Function.identity()));

		final Map<IdentifierString, WOProjectObjectUnderTest> woProjectObjectUnderTestToUpdateMap = woProjectObjectUnderTestToUpdateMap(jsonObjectsUnderTest, existingWOProject);

		final ImmutableList.Builder<WOProjectObjectUnderTest> objectsUnderTest = ImmutableList.builder();
		final ImmutableMap.Builder<String, WOProjectObjectUnderTestResponseMapper> identifier2ObjectUnderTestMap = ImmutableMap.builder();

		for (final Map.Entry<IdentifierString, JsonWorkOrderObjectUnderTestUpsertRequest> jsonObjectUnderTestEntry : identifierString2JsonObjectUnderTest.entrySet())
		{
			final IdentifierString identifierString = jsonObjectUnderTestEntry.getKey();

			final WOProjectObjectUnderTest matchedObjectUnderTest = woProjectObjectUnderTestToUpdateMap.get(identifierString);

			if (matchedObjectUnderTest == null)
			{
				continue;
			}

			final JsonWorkOrderObjectUnderTestUpsertRequest jsonObjectUnderTest = jsonObjectUnderTestEntry.getValue();

			final String identifier = jsonObjectUnderTest.getIdentifier();

			final WOProjectObjectUnderTestResponseMapper.WOProjectObjectUnderTestResponseMapperBuilder responseMapperBuilder = WOProjectObjectUnderTestResponseMapper.builder();

			if (parentSyncAdvise.getIfExists().isUpdate())
			{
				final WOProjectObjectUnderTest updatedObjectUnderTest = mapObjectUnderTest(
						matchedObjectUnderTest,
						jsonObjectUnderTest);

				responseMapperBuilder
						.identifier(identifier)
						.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);

				identifier2ObjectUnderTestMap.put(identifier, responseMapperBuilder.build());
				objectsUnderTest.add(updatedObjectUnderTest);
			}
			else
			{
				responseMapperBuilder
						.identifier(identifier)
						.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);

				identifier2ObjectUnderTestMap.put(identifier, responseMapperBuilder.build());
				objectsUnderTest.add(matchedObjectUnderTest);
			}
		}

		final Map<IdentifierString, Object> identifierString2JsonObjectUnderTestToCreate = Maps
				.difference(identifierString2JsonObjectUnderTest, woProjectObjectUnderTestToUpdateMap)
				.entriesOnlyOnLeft();

		for (final Object value : identifierString2JsonObjectUnderTestToCreate.values())
		{
			final JsonWorkOrderObjectUnderTestUpsertRequest jsonObjectUnderTest = (JsonWorkOrderObjectUnderTestUpsertRequest)value;

			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("C_Project_WO_ObjectUnderTest")
						.build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}

			final WOProjectObjectUnderTest objectUnderTest = createNewObjectUnderTestFromJson(jsonObjectUnderTest);

			final String identifier = jsonObjectUnderTest.getIdentifier();

			final WOProjectObjectUnderTestResponseMapper objectUnderTestResponseMapper = WOProjectObjectUnderTestResponseMapper.builder()
					.identifier(identifier)
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
					.build();

			identifier2ObjectUnderTestMap.put(identifier, objectUnderTestResponseMapper);
			objectsUnderTest.add(objectUnderTest);
		}

		responseMapper.identifier2ObjectUnderTestMapper(identifier2ObjectUnderTestMap.build());

		return objectsUnderTest.build();
	}

	@NonNull
	private Map<IdentifierString, WOProjectObjectUnderTest> woProjectObjectUnderTestToUpdateMap(
			@NonNull List<JsonWorkOrderObjectUnderTestUpsertRequest> jsonObjectsUnderTest,
			@Nullable final WOProject existingWOProject)
	{
		if (existingWOProject == null)
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<IdentifierString, WOProjectObjectUnderTest> woProjectObjectUnderTestToUpdateMap = ImmutableMap.builder();

		for (final JsonWorkOrderObjectUnderTestUpsertRequest jsonObjectUnderTest : jsonObjectsUnderTest)
		{
			final IdentifierString identifier = IdentifierString.of(jsonObjectUnderTest.getIdentifier());

			existingWOProject
					.getObjectUnderTestForLookupFunction((woObjectsUnderTest) -> WorkOrderMapperUtil.resolveObjectUnderTestForExternalIdentifier(identifier, woObjectsUnderTest))
					.ifPresent(matchingProject -> woProjectObjectUnderTestToUpdateMap.put(identifier, matchingProject));
		}

		return woProjectObjectUnderTestToUpdateMap.build();
	}

	@NonNull
	private WOProjectObjectUnderTest mapObjectUnderTest(
			@NonNull final WOProjectObjectUnderTest objectUnderTest,
			@NonNull final JsonWorkOrderObjectUnderTestUpsertRequest request)
	{
		final WOProjectObjectUnderTest.WOProjectObjectUnderTestBuilder builder = WOProjectObjectUnderTest.builder()
				.numberOfObjectsUnderTest(request.getNumberOfObjectsUnderTest());

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

	@NonNull
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
}
