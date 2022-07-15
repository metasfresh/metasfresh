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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
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
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class WorkOrderProjectRestService
{
	private static final Logger logger = LogManager.getLogger(WorkOrderProjectRestService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WorkOrderProjectRepository projectRepository;
	private final ProjectTypeRepository projectTypeRepository;
	private final WorkOrderProjectJsonToInternalConverter workOrderProjectJsonToInternalConverter;

	public WorkOrderProjectRestService(
			@NonNull final WorkOrderProjectRepository projectRepository,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final WorkOrderProjectJsonToInternalConverter workOrderProjectJsonToInternalConverter)
	{
		this.projectRepository = projectRepository;
		this.projectTypeRepository = projectTypeRepository;
		this.workOrderProjectJsonToInternalConverter = workOrderProjectJsonToInternalConverter;
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

			final WOProject jsonUpdatedWOProject = workOrderProjectJsonToInternalConverter.updateWOProjectFromJson(request, existingWOProject);
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

			final WOProject projectData = workOrderProjectJsonToInternalConverter.updateWOProjectFromJson(request, null);
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
					.dateEnd(Optional.ofNullable(stepData.getDateEnd()).map(Instant::toString).orElse(""))
					.dateStart(Optional.ofNullable(stepData.getDateStart()).map(Instant::toString).orElse(""))
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
