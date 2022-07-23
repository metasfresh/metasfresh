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

package de.metas.rest_api.v2.project.budget;

import com.google.common.collect.ImmutableList;
import de.metas.RestUtils;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResourceResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertResponse;
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
import de.metas.project.budget.data.BudgetProject;
import de.metas.project.budget.data.BudgetProjectRepository;
import de.metas.project.budget.data.BudgetProjectResource;
import de.metas.project.workorder.data.ProjectQuery;
import de.metas.resource.ResourceGroupId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.uom.UomId;
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
import java.time.Instant;
import java.util.Optional;

@Service
public class BudgetProjectRestService
{
	private static final Logger logger = LogManager.getLogger(BudgetProjectRestService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final BudgetProjectRepository projectRepository;
	private final ProjectTypeRepository projectTypeRepository;
	private final BudgetProjectJsonToInternalConverter budgetProjectJsonToInternalConverter;

	public BudgetProjectRestService(
			@NonNull final BudgetProjectRepository projectRepository,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final BudgetProjectJsonToInternalConverter budgetProjectJsonToInternalConverter)
	{
		this.projectRepository = projectRepository;
		this.projectTypeRepository = projectTypeRepository;
		this.budgetProjectJsonToInternalConverter = budgetProjectJsonToInternalConverter;
	}

	public JsonBudgetProjectResponse getWorkOrderProjectDataById(@NonNull final ProjectId projectId)
	{
		final BudgetProject projectData = projectRepository.getOptionalById(projectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Work Order Project")
						.resourceIdentifier(String.valueOf(projectId.getRepoId()))
						.build());

		return toJsonWorkOrderProjectResponse(projectData);
	}

	@NonNull
	public JsonBudgetProjectUpsertResponse upsertBudgetProject(@NonNull final JsonBudgetProjectUpsertRequest request)
	{
		return trxManager.callInNewTrx(() -> upsertBudgetProjectWithinTrx(request));
	}

	@NonNull
	private JsonBudgetProjectUpsertResponse upsertBudgetProjectWithinTrx(@NonNull final JsonBudgetProjectUpsertRequest request)
	{
		final SyncAdvise budgetProjectSyncAdvise = request.getSyncAdvise();
		if (budgetProjectSyncAdvise == null)
		{
			throw new MissingPropertyException("syncAdvise", request);
		}

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());
		final Optional<BudgetProject> existingBudgetProjectOpt = getExistingBudgetProject(
				orgId,
				IdentifierString.ofOrNull(request.getProjectIdentifier()),
				request);

		if (existingBudgetProjectOpt.isPresent())
		{
			final BudgetProject existingBudgetProject = existingBudgetProjectOpt.get();

			final ProjectId existingBudgetProjectId = existingBudgetProject.getProjectIdNonNull();
			logger.debug("Found Budget Project with id={}", existingBudgetProjectId);

			final JsonBudgetProjectUpsertResponse.JsonBudgetProjectUpsertResponseBuilder projectResponseBuilder =
					JsonBudgetProjectUpsertResponse.builder()
							.projectId(JsonMetasfreshId.of(existingBudgetProjectId.getRepoId()));

			final BudgetProject jsonUpdatedBudgetProject = budgetProjectJsonToInternalConverter.updateWOProjectFromJson(request, existingBudgetProject);
			updateExistingWOProject(projectResponseBuilder, jsonUpdatedBudgetProject, budgetProjectSyncAdvise);

			return projectResponseBuilder.build();
		}
		else if (budgetProjectSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("Work Order Project")
					.parentResource(request)
					.build()
					.setParameter("budgetProjectSyncAdvise", budgetProjectSyncAdvise);
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

			final ImmutableList.Builder<BudgetProjectResourceId> resourceIdsListBuilder = ImmutableList.builder();

			final BudgetProject projectData = budgetProjectJsonToInternalConverter.updateWOProjectFromJson(request, null);
			final ProjectId createdProjectId = projectRepository.save(projectData, resourceIdsListBuilder).getProjectIdNonNull();

			return JsonBudgetProjectUpsertResponse.builder()
					.projectId(JsonMetasfreshId.of(createdProjectId.getRepoId()))
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
					.createdResourceIds(resourceIdsListBuilder.build().stream()
												.map(BudgetProjectResourceId::getRepoId)
												.map(JsonMetasfreshId::of)
												.collect(ImmutableList.toImmutableList()))
					.build();
		}
	}

	private void updateExistingWOProject(
			@NonNull final JsonBudgetProjectUpsertResponse.JsonBudgetProjectUpsertResponseBuilder responseBuilder,
			@NonNull final BudgetProject existingBudgetProject,
			@NonNull final SyncAdvise syncAdvise)
	{
		final ImmutableList.Builder<BudgetProjectResourceId> resourceIdsListBuilder = ImmutableList.builder();

		if (syncAdvise.getIfExists().isUpdate())
		{
			projectRepository.save(existingBudgetProject, resourceIdsListBuilder);

			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);
		}
		else
		{
			responseBuilder.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);
		}

		responseBuilder.createdResourceIds(resourceIdsListBuilder.build().stream()
												   .map(BudgetProjectResourceId::getRepoId)
												   .map(JsonMetasfreshId::of)
												   .collect(ImmutableList.toImmutableList()));
	}

	@NonNull
	private Optional<BudgetProject> getExistingBudgetProject(
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
	private JsonBudgetProjectResponse toJsonWorkOrderProjectResponse(@NonNull final BudgetProject projectData)
	{
		final ImmutableList.Builder<JsonBudgetProjectResourceResponse> resourcesResponseBuilder = ImmutableList.builder();

		for (final BudgetProjectResource projectResource : projectData.getProjectResources())
		{
			final JsonBudgetProjectResourceResponse resourceResponse = JsonBudgetProjectResourceResponse.builder()
					.budgetProjectResourceId(JsonMetasfreshId.of(projectResource.getBudgetProjectResourceIdNonNull().getRepoId()))
					.currencyId(JsonMetasfreshId.of(projectResource.getCurrencyId().getRepoId()))
					.resourceId(JsonMetasfreshId.of(ResourceId.toRepoId(projectResource.getResourceId())))
					.resourceGroupId(JsonMetasfreshId.of(ResourceGroupId.toRepoId(projectResource.getResourceGroupId())))
					.uomTimeId(JsonMetasfreshId.of(UomId.toRepoId(projectResource.getUomTimeId())))
					.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectResource.getProjectId())))
					.pricePerTimeUOM(projectResource.getPricePerTimeUOM().toBigDecimal())
					.plannedDuration(projectResource.getPlannedDuration())
					.plannedAmt(projectResource.getPlannedAmt().getAsBigDecimal())
					.externalId(ExternalId.toValue(projectResource.getExternalId()))
					.dateFinishPlan(projectResource.getDateFinishPlan().toString())
					.dateStartPlan(projectResource.getDateStartPlan().toString())
					.description(projectResource.getDescription())
					.isActive(projectResource.getIsActive())
					.build();

			resourcesResponseBuilder.add(resourceResponse);
		}

		return JsonBudgetProjectResponse.builder()
				.bPartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(projectData.getBPartnerId())))
				.currencyId(JsonMetasfreshId.ofOrNull(CurrencyId.toRepoId(projectData.getCurrencyId())))
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(projectData.getProjectParentId())))
				.projectTypeId(JsonMetasfreshId.of(ProjectTypeId.toRepoId(projectData.getProjectTypeId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(projectData.getPriceListVersionId())))
				.orgCode(orgDAO.retrieveOrgValue(projectData.getOrgId()))
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectData.getProjectIdNonNull())))
				.dateContract(Optional.ofNullable(projectData.getDateContract()).map(Instant::toString).orElse(""))
				.dateFinish(Optional.ofNullable(projectData.getDateFinish()).map(Instant::toString).orElse(""))
				.description(projectData.getDescription())
				.projectReferenceExt(projectData.getProjectReferenceExt())
				.name(projectData.getNameNonNull())
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(projectData.getSalesRepId())))
				.isActive(projectData.getIsActive())
				.value(projectData.getValueNonNull())
				.projectResources(resourcesResponseBuilder.build())
				.build();
	}
}
