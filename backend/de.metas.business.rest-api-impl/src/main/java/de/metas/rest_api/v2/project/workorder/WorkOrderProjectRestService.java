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
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.service.WorkOrderProjectRepository;
import de.metas.project.service.WorkOrderProjectResourceRepository;
import de.metas.project.service.WorkOrderProjectStepRepository;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectService;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.resource.ResourceService;
import de.metas.resource.ResourceType;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class WorkOrderProjectRestService
{
	private static final Logger logger = LogManager.getLogger(WorkOrderProjectRestService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private static final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WorkOrderProjectRepository projectRepository;
	private final WorkOrderProjectStepRepository projectStepRepository;
	private final WorkOrderProjectResourceRepository projectResourceRepository;
	private final ResourceService resourceService;
	private final WOProjectService woProjectService;
	private final BudgetProjectService budgetProjectService;

	public WorkOrderProjectRestService(
			@NonNull final WorkOrderProjectRepository projectRepository,
			@NonNull final WorkOrderProjectStepRepository projectStepRepository,
			@NonNull final WorkOrderProjectResourceRepository projectResourceRepository,
			@NonNull final ResourceService resourceService,
			@NonNull final WOProjectService woProjectService,
			@NonNull final BudgetProjectService budgetProjectService
	)
	{
		this.projectRepository = projectRepository;
		this.projectStepRepository = projectStepRepository;
		this.projectResourceRepository = projectResourceRepository;
		this.resourceService = resourceService;
		this.woProjectService = woProjectService;
		this.budgetProjectService = budgetProjectService;
	}

	public JsonWorkOrderProjectResponse getWorkOrderProjectDataById(@NonNull final ProjectId projectId)
	{
		final WOProject projectData = projectRepository.getOptionalById(projectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Work Order Project")
						.resourceIdentifier(String.valueOf(projectId.getRepoId()))
						.build());

		final List<WOProjectStep> stepDataList = projectStepRepository.getByProjectId(projectId);
		final List<WOProjectResource> resourceDataList = projectResourceRepository.getByProjectId(projectId);

		return toJsonWorkOrderProjectResponse(projectData, stepDataList, resourceDataList);
	}

	@NonNull
	public JsonWorkOrderProjectUpsertResponse upsertWOProject(@NonNull final JsonWorkOrderProjectRequest request)
	{
		return trxManager.callInNewTrx(() -> upsertWOProjectWithinTrx(request));
	}

	@NonNull
	public JsonWorkOrderResourceUpsertResponse upsertResource(@NonNull final JsonWorkOrderResourceRequest jsonWorkOrderResourceRequest, @NonNull final JsonMetasfreshId projectId)
	{
		return trxManager.callInNewTrx(() -> upsertResourceWithinTrx(jsonWorkOrderResourceRequest, ProjectId.ofRepoId(projectId.getValue())));
	}

	@NonNull
	private JsonWorkOrderProjectUpsertResponse upsertWOProjectWithinTrx(@NonNull final JsonWorkOrderProjectRequest request)
	{
		final SyncAdvise woProjectSyncAdvise = request.getSyncAdvise();

		final Optional<WOProject> existingWOProjectOpt = getExistingWOProject(request.getProjectId());

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

			final ImmutableList.Builder<JsonWorkOrderStepUpsertResponse> stepListResponseBuilder = ImmutableList.builder();

			request.getSteps().forEach(jsonWorkOrderStep -> {
				final JsonWorkOrderStepUpsertResponse step = upsertWOProjectStep(jsonWorkOrderStep, woProjectSyncAdvise, existingWOProjectId);

				stepListResponseBuilder.add(step);
			});

			return projectResponseBuilder
					.createdStepIds(stepListResponseBuilder.build())
					.build();
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
			final OrgId woProjectOrgId = retrieveOrgIdOrDefault(request.getOrgCode());

			final WOProject projectData = WOProject.fromJson(request, woProjectOrgId);

			final ProjectId createdProjectId = projectRepository.create(projectData).getProjectIdNonNull();

			final ImmutableList.Builder<JsonWorkOrderStepUpsertResponse> stepListBuilder = ImmutableList.builder();

			request.getSteps().forEach(jsonWorkOrderStep ->
											   stepListBuilder.add(upsertWOProjectStep(jsonWorkOrderStep, woProjectSyncAdvise, createdProjectId)));

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
			projectRepository.update(existingWOProject);
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);
		}
		else
		{
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);
		}
	}

	@NonNull
	private WOProject updateWOProjectFromJson(@NonNull final JsonWorkOrderProjectRequest request, @NonNull final WOProject existingWOProject)
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
			updatedWOProjectBuilder.active(request.getIsActive());
		}
		else
		{
			updatedWOProjectBuilder.active(existingWOProject.getActive());
		}

		return updatedWOProjectBuilder.build();
	}

	@NonNull
	private JsonWorkOrderStepUpsertResponse upsertWOProjectStep(
			@NonNull final JsonWorkOrderStepRequest request,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ProjectId projectId)
	{
		final SyncAdvise syncAdvise = CoalesceUtil.coalesceNotNull(request.getSyncAdvise(), parentSyncAdvise);

		final Optional<WOProjectStep> existingWOProjectStepOpt = getExistingWOProjectStep(request.getStepId());

		if (existingWOProjectStepOpt.isPresent())
		{
			final WOProjectStep existingWOProjectStep = existingWOProjectStepOpt.get();

			final WOProjectStepId existingWOProjectStepId = existingWOProjectStep.getWOProjectStepIdNonNull();

			logger.debug("Found Work Order Project Step with id={}", existingWOProjectStepId);

			final JsonWorkOrderStepUpsertResponse.JsonWorkOrderStepUpsertResponseBuilder responseBuilder =
					JsonWorkOrderStepUpsertResponse.builder()
							.createdStepId(JsonMetasfreshId.of(existingWOProjectStepId.getRepoId()));

			final WOProjectStep jsonUpdatedWOProjectStep = updateWOProjectStepFromJson(request, existingWOProjectStep);

			updateExistingProjectSteps(responseBuilder, jsonUpdatedWOProjectStep, syncAdvise);

			return responseBuilder.build();
		}
		else if (syncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("Work Order Project Step")
					.parentResource(request)
					.build()
					.setParameter("syncAdvise", syncAdvise);
		}
		else
		{
			final WOProjectStep woProjectStep = WOProjectStep.fromJson(request, projectId);

			final WOProjectStep createdWOProjectStep = projectStepRepository.create(woProjectStep);

			return JsonWorkOrderStepUpsertResponse.builder()
					.createdStepId(JsonMetasfreshId.of(createdWOProjectStep.getWOProjectStepIdNonNull().getRepoId()))
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
					.build();
		}
	}

	private void updateExistingProjectSteps(
			@NonNull final JsonWorkOrderStepUpsertResponse.JsonWorkOrderStepUpsertResponseBuilder responseBuilder,
			@NonNull final WOProjectStep existingWOProjectStep,
			@NonNull final SyncAdvise syncAdvise)
	{

		if (syncAdvise.getIfExists().isUpdate())
		{
			projectStepRepository.update(existingWOProjectStep);
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);
		}
		else
		{
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);
		}
	}

	@NonNull
	private WOProjectStep updateWOProjectStepFromJson(@NonNull final JsonWorkOrderStepRequest request, @NonNull final WOProjectStep existingWOProjectStep)
	{
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

		return updatedWOProjectStepBuilder.build();
	}

	@NonNull
	private JsonWorkOrderResourceUpsertResponse upsertResourceWithinTrx(
			@NonNull final JsonWorkOrderResourceRequest request,
			@NonNull final ProjectId projectId)
	{
		final SyncAdvise woProjectResourceSyncAdvise = request.getSyncAdvise();

		final Optional<WOProjectResource> existingWOProjectResourceOpt = getExistingWOProjectResource(request.getWoResourceId());

		if (existingWOProjectResourceOpt.isPresent())
		{
			final WOProjectResource existingWOProjectResource = existingWOProjectResourceOpt.get();

			final WOProjectResourceId existingWOProjectResourceId = existingWOProjectResource.getWOProjectResourceIdNotNull();

			logger.debug("Found Work Order Project Resource with id={}", existingWOProjectResourceId);

			final JsonWorkOrderResourceUpsertResponse.JsonWorkOrderResourceUpsertResponseBuilder responseBuilder = JsonWorkOrderResourceUpsertResponse.builder()
					.createdResourceId(JsonMetasfreshId.of(existingWOProjectResourceId.getRepoId()));

			updateExistingResource(responseBuilder, request, projectId, woProjectResourceSyncAdvise);

			return responseBuilder.build();
		}
		else if (woProjectResourceSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("Work Order Project Resource")
					.parentResource(request)
					.build()
					.setParameter("woProjectResourceSyncAdvise", woProjectResourceSyncAdvise);
		}
		else
		{
			final WOProjectResource createdResourceId = projectResourceRepository.create(buildResourceToBeUpserted(request, projectId));

			return JsonWorkOrderResourceUpsertResponse.builder()
					.createdResourceId(JsonMetasfreshId.of(createdResourceId.getWOProjectResourceIdNotNull().getRepoId()))
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
					.build();
		}
	}

	private void updateExistingResource(
			@NonNull final JsonWorkOrderResourceUpsertResponse.JsonWorkOrderResourceUpsertResponseBuilder responseBuilder,
			@NonNull final JsonWorkOrderResourceRequest request,
			@NonNull final ProjectId projectId,
			@NonNull final SyncAdvise syncAdvise)
	{
		if (syncAdvise.getIfExists().isUpdate())
		{
			final WOProjectResource woProjectResourceWithDurationAndBudget = buildResourceToBeUpserted(request, projectId);

			final WOProjectResource jsonUpdatedWOProjectResource = updateWOProjectResourceFromJson(request, woProjectResourceWithDurationAndBudget);

			projectResourceRepository.update(jsonUpdatedWOProjectResource);
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);
		}
		else
		{
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);
		}
	}

	@NonNull
	private WOProjectResource updateWOProjectResourceFromJson(@NonNull final JsonWorkOrderResourceRequest request, @NonNull final WOProjectResource existingWOProjectResource)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(request.getOrgCode());

		final WOProjectResource.WOProjectResourceBuilder updatedWOProjectResourceBuilder = WOProjectResource.builder()
				.woProjectResourceId(existingWOProjectResource.getWOProjectResourceIdNotNull())
				.projectId(existingWOProjectResource.getProjectId())
				.budgetProjectId(existingWOProjectResource.getBudgetProjectId())
				.projectResourceBudgetId(existingWOProjectResource.getProjectResourceBudgetId())
				.duration(existingWOProjectResource.getDuration())
				.durationUnit(existingWOProjectResource.getDurationUnit())
				.woProjectStepId(WOProjectStepId.ofRepoId(request.getStepId().getValue()))
				.assignDateFrom(request.getAssignDateFrom())
				.assignDateTo(request.getAssignDateTo())
				.orgId(orgId);

		if (request.isResourceIdSet())
		{
			updatedWOProjectResourceBuilder.resourceId(ResourceId.ofRepoId(request.getResourceId().getValue()));
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

		if (request.getIsAllDay())
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
	private WOProjectResource buildResourceToBeUpserted(
			@NonNull final JsonWorkOrderResourceRequest request,
			@NonNull final ProjectId projectId
	)
	{
		final String durationUnit = getDurationUnit(request);
		final BigDecimal duration = getDuration(request, durationUnit);

		final Optional<BudgetProjectResource> budget = computeBudget(request, projectId);

		final OrgId orgId = retrieveOrgIdOrDefault(request.getOrgCode());

		return WOProjectResource.fromJson(
				request,
				orgId,
				projectId,
				duration,
				durationUnit,
				budget.map(BudgetProjectResource::getProjectId).orElse(null),
				budget.map(BudgetProjectResource::getId).orElse(null));
	}

	@NonNull
	private String getDurationUnit(final @NonNull JsonWorkOrderResourceRequest woResource)
	{
		if (woResource.getResourceId() != null)
		{
			final ResourceType resourceType = resourceService.getResourceTypeByResourceId(ResourceId.ofRepoId(woResource.getResourceId().getValue()));
			return WFDurationUnit.ofTemporalUnit(resourceType.getDurationUnit()).getCode();
		}
		else
		{
			return WFDurationUnit.Hour.getCode();
		}
	}

	@NonNull
	private BigDecimal getDuration(final @NonNull JsonWorkOrderResourceRequest woResource, final @NonNull String durationUnitStr)
	{
		final ZonedDateTime dateFrom = TimeUtil.asZonedDateTime(woResource.getAssignDateFrom());

		final ZonedDateTime dateTo = TimeUtil.asZonedDateTime(woResource.getAssignDateTo());

		final TemporalUnit durationUnit = WFDurationUnit.ofCode(durationUnitStr).getTemporalUnit();

		final Duration duration = Duration.between(dateFrom, dateTo);

		return DurationUtils.toBigDecimal(duration, durationUnit);
	}

	@NonNull
	private Optional<BudgetProjectResource> computeBudget(@NonNull final JsonWorkOrderResourceRequest woResource, @NonNull final ProjectId projectId)
	{
		if (woResource.getResourceId() == null)
		{
			return Optional.empty();
		}

		final de.metas.project.workorder.WOProject woProject = woProjectService.getById(projectId);
		if (woProject.getParentProjectId() == null)
		{
			return Optional.empty();
		}

		final BudgetProject budgetProject = budgetProjectService.getById(woProject.getParentProjectId()).orElse(null);
		if (budgetProject == null)
		{
			return Optional.empty();
		}

		return budgetProjectService.findBudgetForResource(budgetProject.getProjectId(), ResourceId.ofRepoId(woResource.getResourceId().getValue()));
	}

	@NonNull
	private Optional<WOProject> getExistingWOProject(@Nullable final JsonMetasfreshId woProjectId)
	{
		return Optional.ofNullable(woProjectId)
				.map(JsonMetasfreshId::getValue)
				.map(ProjectId::ofRepoId)
				.flatMap(projectRepository::getOptionalById);
	}

	@NonNull
	private Optional<WOProjectStep> getExistingWOProjectStep(@Nullable final JsonMetasfreshId woProjectStepId)
	{
		return Optional.ofNullable(woProjectStepId)
				.map(JsonMetasfreshId::getValue)
				.map(WOProjectStepId::ofRepoId)
				.flatMap(projectStepRepository::getOptionalById);
	}

	@NonNull
	private Optional<WOProjectResource> getExistingWOProjectResource(@Nullable final JsonMetasfreshId woProjectResourceId)
	{
		return Optional.ofNullable(woProjectResourceId)
				.map(JsonMetasfreshId::getValue)
				.map(WOProjectResourceId::ofRepoId)
				.flatMap(projectResourceRepository::getOptionalById);
	}

	@NonNull
	private static JsonWorkOrderProjectResponse toJsonWorkOrderProjectResponse(
			@NonNull final WOProject projectData,
			@NonNull final List<WOProjectStep> stepsData,
			@NonNull final List<WOProjectResource> resourcesData)
	{
		final ImmutableList.Builder<JsonWorkOrderStepResponse> stepsResponseBuilder = ImmutableList.builder();

		stepsData.forEach(stepData -> stepsResponseBuilder.add(JsonWorkOrderStepResponse.builder()
																	   .stepId(JsonMetasfreshId.of(WOProjectStepId.toRepoId(stepData.getWOProjectStepIdNonNull())))
																	   .dateEnd(Optional.ofNullable(stepData.getDateEnd()).map(LocalDate::toString).orElse(""))
																	   .dateStart(Optional.ofNullable(stepData.getDateStart()).map(LocalDate::toString).orElse(""))
																	   .seqNo(stepData.getSeqNoNonNull())
																	   .description(stepData.getDescription())
																	   .name(stepData.getName())
																	   .projectId(JsonMetasfreshId.of(ProjectId.toRepoId(stepData.getProjectId())))
																	   .build()));

		final ImmutableList.Builder<JsonWorkOrderResourceResponse> resourcesResponseBuilder = ImmutableList.builder();

		resourcesData.forEach(resourceData -> resourcesResponseBuilder.add(JsonWorkOrderResourceResponse.builder()
																				   .woResourceId(JsonMetasfreshId.of(WOProjectResourceId.toRepoId(resourceData.getWOProjectResourceIdNotNull())))
																				   .resourceId(JsonMetasfreshId.ofOrNull(ResourceId.toRepoId(resourceData.getResourceId())))
																				   .stepId(JsonMetasfreshId.of(WOProjectStepId.toRepoId(resourceData.getWoProjectStepId())))
																				   .budgetProjectId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(resourceData.getBudgetProjectId())))
																				   .projectId(JsonMetasfreshId.of(ProjectId.toRepoId(resourceData.getProjectId())))
																				   .projectResourceBudgetId(JsonMetasfreshId.ofOrNull(BudgetProjectResourceId.toRepoId(resourceData.getProjectResourceBudgetId())))
																				   .assignDateFrom(resourceData.getAssignDateFrom().toString())
																				   .assignDateTo(resourceData.getAssignDateTo().toString())
																				   .duration(resourceData.getDuration())
																				   .durationUnit(resourceData.getDurationUnit())
																				   .isAllDay(resourceData.getIsAllDay())
																				   .isActive(resourceData.getIsActive())
																				   .orgCode(orgDAO.retrieveOrgValue(resourceData.getOrgId()))
																				   .build()));

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
				.salesRepId(JsonMetasfreshId.of(UserId.toRepoId(projectData.getSalesRepId())))
				.isActive(projectData.getActive())
				.value(projectData.getValueNonNull())
				.steps(stepsResponseBuilder.build())
				.resources(resourcesResponseBuilder.build())
				.build();
	}
}
