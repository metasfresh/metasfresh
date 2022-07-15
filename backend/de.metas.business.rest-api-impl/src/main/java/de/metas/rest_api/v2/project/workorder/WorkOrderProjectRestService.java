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
import de.metas.RestUtils;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.data.ProjectQuery;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.project.workorder.data.WorkOrderProjectRepository;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class WorkOrderProjectRestService
{
	private static final Logger logger = LogManager.getLogger(WorkOrderProjectRestService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WorkOrderProjectRepository projectRepository;
	private final ResourceService resourceService;
	private final ProjectTypeRepository projectTypeRepository;

	public WorkOrderProjectRestService(
			@NonNull final WorkOrderProjectRepository projectRepository,
			@NonNull final ResourceService resourceService,
			@NonNull final ProjectTypeRepository projectTypeRepository)
	{
		this.projectRepository = projectRepository;
		this.resourceService = resourceService;
		this.projectTypeRepository = projectTypeRepository;
	}

	public JsonWorkOrderProjectResponse getWorkOrderProjectDataById(@NonNull final ProjectId projectId)
	{
		final WOProject projectData = projectRepository.getOptionalById(projectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Work Order Project")
						.resourceIdentifier(String.valueOf(projectId.getRepoId()))
						.build());

		return toJsonWorkOrderProjectResponse(projectData);
	}

	@NonNull
	public JsonWorkOrderProjectUpsertResponse upsertWOProject(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		return trxManager.callInNewTrx(() -> upsertWOProjectWithinTrx(request));
	}

	@NonNull
	private JsonWorkOrderProjectUpsertResponse upsertWOProjectWithinTrx(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		final SyncAdvise woProjectSyncAdvise = request.getSyncAdvise();
		if (woProjectSyncAdvise == null)
		{
			throw new MissingPropertyException("syncAdvise", request);
		}

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());
		final Optional<WOProject> existingWOProjectOpt = getExistingWOProject(
				orgId,
				IdentifierString.ofOrNull(request.getProjectIdentifier()),
				request);

		if (existingWOProjectOpt.isPresent())
		{
			final WOProject existingWOProject = existingWOProjectOpt.get();

			final ProjectId existingWOProjectId = existingWOProject.getProjectIdNonNull();
			logger.debug("Found Work Order Project with id={}", existingWOProjectId);

			final JsonWorkOrderProjectUpsertResponse.JsonWorkOrderProjectUpsertResponseBuilder projectResponseBuilder =
					JsonWorkOrderProjectUpsertResponse.builder()
							.projectId(JsonMetasfreshId.of(existingWOProjectId.getRepoId()));

			final WOProject jsonUpdatedWOProject = updateWOProjectFromJson(request, existingWOProject);
			updateExistingWOProject(projectResponseBuilder, jsonUpdatedWOProject, woProjectSyncAdvise);

			return projectResponseBuilder.build();
		}
		else if (woProjectSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("Work Order Project")
					.parentResource(request)
					.build()
					.setParameter("woProjectSyncAdvise", woProjectSyncAdvise);
		}
		else
		{
			final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getCurrencyId()));
			if (currencyId == null)
			{
				throw new MissingPropertyException("currencyId", request);
			}
			final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getProjectTypeId()));
			if (projectTypeId == null)
			{
				throw new MissingPropertyException("projectTypeId", request);
			}
			if (projectTypeRepository.getByIdOrNull(projectTypeId) == null)
			{
				throw MissingResourceException.builder()
						.resourceName("projectTypeId")
						.resourceIdentifier(Integer.toString(projectTypeId.getRepoId()))
						.parentResource(request)
						.build();
			}
			
			final OrgId woProjectOrgId = retrieveOrgIdOrDefault(request.getOrgCode());

			final WOProject projectData = FromJSONUtil.fromJson(request, woProjectOrgId);
			final ProjectId createdProjectId = projectRepository.save(projectData).getProjectIdNonNull();

			final ImmutableList.Builder<JsonWorkOrderStepUpsertResponse> stepListBuilder = ImmutableList.builder();

			return JsonWorkOrderProjectUpsertResponse.builder()
					.projectId(JsonMetasfreshId.of(createdProjectId.getRepoId()))
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
					.createdStepIds(stepListBuilder.build())
					.build();
		}
	}

	private void updateExistingWOProject(
			@NonNull final JsonWorkOrderProjectUpsertResponse.JsonWorkOrderProjectUpsertResponseBuilder responseBuilder,
			@NonNull final WOProject existingWOProject,
			@NonNull final SyncAdvise syncAdvise)
	{
		if (syncAdvise.getIfExists().isUpdate())
		{
			projectRepository.save(existingWOProject);
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);
		}
		else
		{
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);
		}
	}

	@NonNull
	private WOProject updateWOProjectFromJson(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final WOProject existingWOProject)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(request.getOrgCode());

		final WOProject.WOProjectBuilder updatedWOProjectBuilder = WOProject.builder()
				.projectId(existingWOProject.getProjectIdNonNull())
				.projectTypeId(ProjectTypeId.ofRepoId(request.getProjectTypeId().getValue()))
				.orgId(orgId);

		if (request.isPriceListVersionIdSet())
		{
			updatedWOProjectBuilder.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(request.getPriceListVersionId().getValue()));
		}
		else
		{
			updatedWOProjectBuilder.priceListVersionId(existingWOProject.getPriceListVersionId());
		}

		if (request.isCurrencyIdSet())
		{
			updatedWOProjectBuilder.currencyId(CurrencyId.ofRepoId(request.getCurrencyId().getValue()));
		}
		else
		{
			updatedWOProjectBuilder.currencyId(existingWOProject.getCurrencyId());
		}
				
		if (request.isSalesRepIdSet())
		{
			updatedWOProjectBuilder.salesRepId(UserId.ofRepoIdOrNull(request.getSalesRepId().getValue()));
		}
		else
		{
			updatedWOProjectBuilder.salesRepId(existingWOProject.getSalesRepId());
		}

		if (request.isProjectReferenceExtSet())
		{
			updatedWOProjectBuilder.projectReferenceExt(request.getProjectReferenceExt());
		}
		else
		{
			updatedWOProjectBuilder.projectReferenceExt(existingWOProject.getProjectReferenceExt());
		}

		if (request.isProjectParentIdSet())
		{
			updatedWOProjectBuilder.projectParentId(ProjectId.ofRepoIdOrNull(request.getProjectParentId().getValue()));
		}
		else
		{
			updatedWOProjectBuilder.projectParentId(existingWOProject.getProjectParentId());
		}

		if (request.isBusinessPartnerIdSet())
		{
			updatedWOProjectBuilder.bPartnerId(BPartnerId.ofRepoIdOrNull(request.getBusinessPartnerId().getValue()));
		}
		else
		{
			updatedWOProjectBuilder.bPartnerId(existingWOProject.getBPartnerId());
		}

		if (request.isCurrencyIdSet())
		{
			updatedWOProjectBuilder.currencyId(CurrencyId.ofRepoIdOrNull(request.getCurrencyId().getValue()));
		}
		else
		{
			updatedWOProjectBuilder.currencyId(existingWOProject.getCurrencyId());
		}

		if (request.isNameSet())
		{
			updatedWOProjectBuilder.name(request.getName());
		}
		else
		{
			updatedWOProjectBuilder.name(existingWOProject.getName());
		}

		if (request.isValueSet())
		{
			updatedWOProjectBuilder.value(request.getValue());
		}
		else
		{
			updatedWOProjectBuilder.value(existingWOProject.getValue());
		}

		if (request.isDateContractSet())
		{
			updatedWOProjectBuilder.dateContract(request.getDateContract());
		}
		else
		{
			updatedWOProjectBuilder.dateContract(existingWOProject.getDateContract());
		}

		if (request.isDateFinishSet())
		{
			updatedWOProjectBuilder.dateFinish(request.getDateFinish());
		}
		else
		{
			updatedWOProjectBuilder.dateFinish(existingWOProject.getDateFinish());
		}

		if (request.isDescriptionSet())
		{
			updatedWOProjectBuilder.description(request.getDescription());
		}
		else
		{
			updatedWOProjectBuilder.description(existingWOProject.getDescription());
		}

		if (request.isActiveSet())
		{
			updatedWOProjectBuilder.isActive(request.getIsActive());
		}
		else
		{
			updatedWOProjectBuilder.isActive(existingWOProject.getIsActive());
		}

		final Map<JsonExternalId, JsonWorkOrderStepUpsertRequest> jsonProjectSteps = request.getSteps().stream()
				.collect(Collectors.toMap(JsonWorkOrderStepUpsertRequest::getExternalId, Function.identity()));

		for (final WOProjectStep existingProjectStep : existingWOProject.getProjectSteps())
		{
			if (existingProjectStep.getExternalId() == null)
			{
				continue; // can't match a step that has no external ID
			}
			final JsonExternalId existingJsonExtenalId = JsonExternalId.of(existingProjectStep.getExternalId().getValue());
			updateWOProjectStepFromJson(
					orgId,
					jsonProjectSteps.remove(existingJsonExtenalId),
					existingProjectStep);
		}

		for (final JsonWorkOrderStepUpsertRequest remainingJsonProjectStep : jsonProjectSteps.values())
		{
			updatedWOProjectBuilder.projectStep(FromJSONUtil.fromJson(remainingJsonProjectStep, null));
		}

		return updatedWOProjectBuilder.build();
	}

	@NonNull
	private WOProjectStep updateWOProjectStepFromJson(
			@NonNull final OrgId orgId,
			@Nullable final JsonWorkOrderStepUpsertRequest request,
			@NonNull final WOProjectStep existingWOProjectStep)
	{
		if (request == null)
		{
			return existingWOProjectStep; // nothing to do
		}

		final WOProjectStep.WOProjectStepBuilder updatedWOProjectStepBuilder = WOProjectStep.builder()
				.woProjectStepId(existingWOProjectStep.getWOProjectStepIdNonNull())
				.name(request.getName());

		if (request.isSeqNoSet())
		{
			updatedWOProjectStepBuilder.seqNo(request.getSeqNo());
		}
		else
		{
			updatedWOProjectStepBuilder.seqNo(existingWOProjectStep.getSeqNo());
		}

		if (request.isDateStartSet())
		{
			updatedWOProjectStepBuilder.dateStart(request.getDateStart());
		}
		else
		{
			updatedWOProjectStepBuilder.dateStart(existingWOProjectStep.getDateStart());
		}

		if (request.isDateEndSet())
		{
			updatedWOProjectStepBuilder.dateEnd(request.getDateEnd());
		}
		else
		{
			updatedWOProjectStepBuilder.dateEnd(existingWOProjectStep.getDateEnd());
		}

		if (request.isDescriptionSet())
		{
			updatedWOProjectStepBuilder.description(request.getDescription());
		}
		else
		{
			updatedWOProjectStepBuilder.description(existingWOProjectStep.getDescription());
		}

		final Map<JsonExternalId, JsonWorkOrderResourceUpsertRequest> jsonProjectResources = request.getResourceRequests().stream()
				.collect(Collectors.toMap(JsonWorkOrderResourceUpsertRequest::getExternalId, Function.identity()));

		for (final WOProjectResource existingProjectResource : existingWOProjectStep.getProjectResources())
		{
			if (existingProjectResource.getExternalId() == null)
			{
				continue; // can't match a resource that has no external ID
			}
			final JsonExternalId existingJsonExtenalId = JsonExternalId.of(existingProjectResource.getExternalId().getValue());
			updateWOProjectResourceFromJson(
					orgId,
					jsonProjectResources.remove(existingJsonExtenalId),
					existingProjectResource);
		}

		for (final JsonWorkOrderResourceUpsertRequest remainingJsonProjectResource : jsonProjectResources.values())
		{
			final ResourceId resourceId = extractResourceid(orgId, remainingJsonProjectResource);
			final FromJSONUtil.AdditionalWOProjectResourceProperties additionalProps = FromJSONUtil.AdditionalWOProjectResourceProperties
					.builder()
					.resourceId(resourceId).build();

			final WOProjectResource projectResource = FromJSONUtil.fromJson(remainingJsonProjectResource, additionalProps);
			updatedWOProjectStepBuilder.projectResource(projectResource);
		}

		return updatedWOProjectStepBuilder.build();
	}

	@NonNull
	private WOProjectResource updateWOProjectResourceFromJson(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertRequest request,
			@NonNull final WOProjectResource existingWOProjectResource)
	{
		final WOProjectResource.WOProjectResourceBuilder updatedWOProjectResourceBuilder = WOProjectResource.builder()
				.externalId(ExternalId.of(request.getExternalId().getValue()))
				.woProjectResourceId(existingWOProjectResource.getWOProjectResourceIdNotNull())
				.budgetProjectId(existingWOProjectResource.getBudgetProjectId())
				.projectResourceBudgetId(existingWOProjectResource.getProjectResourceBudgetId())
				.duration(existingWOProjectResource.getDuration())
				.durationUnit(existingWOProjectResource.getDurationUnit())
				.assignDateFrom(request.getAssignDateFrom())
				.assignDateTo(request.getAssignDateTo());

		if (request.isResourceIdentifierSet())
		{
			ResourceId resourceId = extractResourceid(orgId, request);
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

	private ResourceId extractResourceid(
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
	private Optional<WOProject> getExistingWOProject(
			@NonNull final OrgId orgId,
			@Nullable final IdentifierString identifier,
			@Nullable final Object objectWithIdentifier)
	{
		if (identifier == null)
		{
			return Optional.empty();
		}
		if (identifier.getType().equals(IdentifierString.Type.METASFRESH_ID))
		{
			final MetasfreshId metasfreshId = identifier.asMetasfreshId();
			return projectRepository.getOptionalById(ProjectId.ofRepoId(metasfreshId.getValue()));
		}

		final ProjectQuery.ProjectQueryBuilder projectQueryBuilder = ProjectQuery.builder().orgId(orgId);
		switch (identifier.getType())
		{
			case VALUE:
				projectQueryBuilder.value(identifier.asValue());
				break;
			case EXTERNAL_ID:
				projectQueryBuilder.externalProjectReference(identifier.asExternalId());
				break;
			default:
				throw new InvalidIdentifierException(identifier.getRawIdentifierString(), objectWithIdentifier);
		}

		return projectRepository.getOptionalBy(projectQueryBuilder.build());
	}

	@NonNull
	private JsonWorkOrderProjectResponse toJsonWorkOrderProjectResponse(@NonNull final WOProject projectData)
	{
		final ImmutableList.Builder<JsonWorkOrderStepResponse> stepsResponseBuilder = ImmutableList.builder();

		for (final WOProjectStep stepData : projectData.getProjectSteps())
		{
			final JsonWorkOrderStepResponse.JsonWorkOrderStepResponseBuilder jsonWorkOrderStepResponseBuilder = JsonWorkOrderStepResponse.builder()
					.stepId(JsonMetasfreshId.of(WOProjectStepId.toRepoId(stepData.getWOProjectStepIdNonNull())))
					.dateEnd(Optional.ofNullable(stepData.getDateEnd()).map(LocalDate::toString).orElse(""))
					.dateStart(Optional.ofNullable(stepData.getDateStart()).map(LocalDate::toString).orElse(""))
					.seqNo(stepData.getSeqNoNonNull())
					.description(stepData.getDescription())
					.name(stepData.getName())
					.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(stepData.getProjectId())));

			for (final WOProjectResource resourceData : stepData.getProjectResources())
			{
				final JsonWorkOrderResourceResponse jsonWorkOrderResourceResponse = JsonWorkOrderResourceResponse.builder()
						.woResourceId(JsonMetasfreshId.of(WOProjectResourceId.toRepoId(resourceData.getWOProjectResourceIdNotNull())))
						.resourceId(JsonMetasfreshId.ofOrNull(ResourceId.toRepoId(resourceData.getResourceId())))
						.stepId(JsonMetasfreshId.of(WOProjectStepId.toRepoId(resourceData.getWoProjectStepId())))
						.budgetProjectId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(resourceData.getBudgetProjectId())))
						.projectResourceBudgetId(JsonMetasfreshId.ofOrNull(BudgetProjectResourceId.toRepoId(resourceData.getProjectResourceBudgetId())))
						.assignDateFrom(resourceData.getAssignDateFrom().toString())
						.assignDateTo(resourceData.getAssignDateTo().toString())
						.duration(resourceData.getDuration())
						.durationUnit(resourceData.getDurationUnit())
						.isAllDay(resourceData.getIsAllDay())
						.isActive(resourceData.getIsActive())
						.build();
				jsonWorkOrderStepResponseBuilder.resource(jsonWorkOrderResourceResponse);
			}

			stepsResponseBuilder.add(jsonWorkOrderStepResponseBuilder.build());
		}

		return JsonWorkOrderProjectResponse.builder()
				.bPartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(projectData.getBPartnerId())))
				.currencyId(JsonMetasfreshId.ofOrNull(CurrencyId.toRepoId(projectData.getCurrencyId())))
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(projectData.getProjectParentId())))
				.projectTypeId(JsonMetasfreshId.of(ProjectTypeId.toRepoId(projectData.getProjectTypeId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(projectData.getPriceListVersionId())))
				.orgCode(orgDAO.retrieveOrgValue(projectData.getOrgId()))
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectData.getProjectIdNonNull())))
				.dateContract(Optional.ofNullable(projectData.getDateContract()).map(LocalDate::toString).orElse(""))
				.dateFinish(Optional.ofNullable(projectData.getDateFinish()).map(LocalDate::toString).orElse(""))
				.description(projectData.getDescription())
				.projectReferenceExt(projectData.getProjectReferenceExt())
				.name(projectData.getNameNonNull())
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(projectData.getSalesRepId())))
				.isActive(projectData.getIsActive())
				.value(projectData.getValueNonNull())
				.steps(stepsResponseBuilder.build())
				.build();
	}
}
