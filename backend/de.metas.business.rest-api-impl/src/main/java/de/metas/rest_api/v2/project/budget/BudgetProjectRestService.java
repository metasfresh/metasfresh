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
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectData;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceData;
import de.metas.project.budget.UpsertBudgetProjectRequest;
import de.metas.project.workorder.data.ProjectQuery;
import de.metas.resource.ResourceGroupId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class BudgetProjectRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private final BudgetProjectRepository budgetProjectRepository;
	private final ProjectTypeRepository projectTypeRepository;
	private final BudgetProjectJsonConverter budgetProjectJsonConverter;

	public BudgetProjectRestService(
			@NonNull final BudgetProjectRepository budgetProjectRepository,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final BudgetProjectJsonConverter budgetProjectJsonConverter)
	{
		this.budgetProjectRepository = budgetProjectRepository;
		this.projectTypeRepository = projectTypeRepository;
		this.budgetProjectJsonConverter = budgetProjectJsonConverter;
	}

	@NonNull
	public JsonBudgetProjectResponse getBudgetProjectDataById(@NonNull final ProjectId projectId)
	{
		final BudgetProject projectData = budgetProjectRepository.getOptionalById(projectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Budget Project")
						.resourceIdentifier(String.valueOf(projectId.getRepoId()))
						.build());

		return toJsonBudgetProjectResponse(projectData);
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

		validateJsonBudgetProjectUpsertRequest(request);

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());

		final Optional<BudgetProject> existingBudgetProjectOpt = getExistingBudgetProject(request, orgId);

		if (existingBudgetProjectOpt.isPresent() && !budgetProjectSyncAdvise.getIfExists().isUpdate())
		{
			final JsonMetasfreshId projectId = JsonMetasfreshId.of(existingBudgetProjectOpt.get().getProjectId().getRepoId());

			return JsonBudgetProjectUpsertResponse.builder()
					.projectId(projectId)
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE)
					.build();
		}
		else if (existingBudgetProjectOpt.isPresent() || !budgetProjectSyncAdvise.isFailIfNotExists())
		{
			final BudgetProject existingBudgetProject = existingBudgetProjectOpt.orElse(null);

			return upsertBudgetProject(request, existingBudgetProject, budgetProjectSyncAdvise, orgId);
		}
		else
		{
			throw MissingResourceException.builder()
					.resourceName("Budget Project")
					.parentResource(request)
					.build()
					.setParameter("budgetProjectSyncAdvise", budgetProjectSyncAdvise);
		}
	}

	@NonNull
	private JsonBudgetProjectUpsertResponse upsertBudgetProject(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@Nullable final BudgetProject existingBudgetProject,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final OrgId orgId)
	{
		final UpsertBudgetProjectRequest upsertBudgetProjectRequest = budgetProjectJsonConverter
				.buildUpsertBudgetProjectRequest(request, existingBudgetProject, orgId, syncAdvise);

		final BudgetProject budgetProject = budgetProjectRepository.save(upsertBudgetProjectRequest);

		final JsonResponseUpsertItem.SyncOutcome syncOutcome = existingBudgetProject == null
				? JsonResponseUpsertItem.SyncOutcome.CREATED
				: JsonResponseUpsertItem.SyncOutcome.UPDATED;

		return JsonBudgetProjectUpsertResponse.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(budgetProject.getProjectId())))
				.syncOutcome(syncOutcome)
				.budgetResourceIds(budgetProject.mapResourceIds((id) -> JsonMetasfreshId.of(id.getRepoId())))
				.build();
	}

	@NonNull
	private JsonBudgetProjectResponse toJsonBudgetProjectResponse(@NonNull final BudgetProject budgetProject)
	{
		final ImmutableList.Builder<JsonBudgetProjectResourceResponse> resourcesResponseBuilder = ImmutableList.builder();

		for (final BudgetProjectResource projectResource : budgetProject.getProjectResources().getBudgets())
		{
			final BudgetProjectResourceData budgetProjectResourceData = projectResource.getBudgetProjectResourceData();

			final ZoneId timeZoneId = orgDAO.getTimeZone(budgetProject.getBudgetProjectData().getOrgId());

			final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(budgetProjectResourceData.getCurrencyId());

			final JsonBudgetProjectResourceResponse resourceResponse = JsonBudgetProjectResourceResponse.builder()
					.budgetProjectResourceId(JsonMetasfreshId.of(projectResource.getId().getRepoId()))
					.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectResource.getProjectId())))
					.currencyCode(currencyCode.toThreeLetterCode())
					.resourceId(JsonMetasfreshId.ofOrNull(ResourceId.toRepoId(budgetProjectResourceData.getResourceId())))
					.resourceGroupId(JsonMetasfreshId.ofOrNull(ResourceGroupId.toRepoId(budgetProjectResourceData.getResourceGroupId())))
					.uomTimeId(JsonMetasfreshId.of(UomId.toRepoId(budgetProjectResourceData.getDurationUomId())))
					.pricePerTimeUOM(budgetProjectResourceData.getPricePerDurationUnit().toBigDecimal())
					.plannedDuration(budgetProjectResourceData.getPlannedDuration().getSourceQty())
					.plannedAmt(budgetProjectResourceData.getPlannedAmount().toBigDecimal())
					.externalId(ExternalId.toValue(budgetProjectResourceData.getExternalId()))
					.dateFinishPlan(LocalDateTime.ofInstant(budgetProjectResourceData.getDateRange().getEndDate(), timeZoneId).toLocalDate())
					.dateStartPlan(LocalDateTime.ofInstant(budgetProjectResourceData.getDateRange().getStartDate(), timeZoneId).toLocalDate())
					.description(budgetProjectResourceData.getDescription())
					.isActive(budgetProjectResourceData.getIsActive())
					.build();

			resourcesResponseBuilder.add(resourceResponse);
		}

		final BudgetProjectData budgetProjectData = budgetProject.getBudgetProjectData();

		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(budgetProjectData.getCurrencyId());

		return JsonBudgetProjectResponse.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(budgetProject.getProjectId())))
				.bpartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(budgetProjectData.getBPartnerId())))
				.currencyCode(currencyCode.toThreeLetterCode())
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(budgetProjectData.getProjectParentId())))
				.projectTypeId(JsonMetasfreshId.of(ProjectTypeId.toRepoId(budgetProjectData.getProjectTypeId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(budgetProjectData.getPriceListVersionId())))
				.orgCode(orgDAO.retrieveOrgValue(budgetProjectData.getOrgId()))
				.dateContract(budgetProjectData.getDateContract())
				.dateFinish(budgetProjectData.getDateFinish())
				.description(budgetProjectData.getDescription())
				.projectReferenceExt(budgetProjectData.getProjectReferenceExt())
				.name(budgetProjectData.getName())
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(budgetProjectData.getSalesRepId())))
				.isActive(budgetProjectData.isActive())
				.value(budgetProjectData.getValue())
				.projectResources(resourcesResponseBuilder.build())
				.build();
	}

	@NonNull
	private Optional<BudgetProject> getExistingBudgetProject(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@NonNull final OrgId orgId)
	{
		final IdentifierString projectIdentifier = IdentifierString.of(request.getProjectIdentifier());

		if (IdentifierString.Type.METASFRESH_ID.equals(projectIdentifier.getType()))
		{
			final ProjectId existingProjectId = ProjectId.ofRepoId(MetasfreshId.toValue(projectIdentifier.asMetasfreshId()));
			return budgetProjectRepository.getOptionalById(existingProjectId);
		}

		final ProjectQuery.ProjectQueryBuilder projectQueryBuilder = ProjectQuery.builder()
				.orgId(orgId);

		switch (projectIdentifier.getType())
		{
			case VALUE:
				projectQueryBuilder.value(projectIdentifier.asValue());
				break;
			case EXTERNAL_ID:
				projectQueryBuilder.externalProjectReference(projectIdentifier.asExternalId());
				break;
			default:
				throw new InvalidIdentifierException(projectIdentifier.getRawIdentifierString(), request);
		}

		return budgetProjectRepository.getOptionalBy(projectQueryBuilder.build());
	}

	private void validateJsonBudgetProjectUpsertRequest(@NonNull final JsonBudgetProjectUpsertRequest request)
	{
		if (Check.isBlank(request.getProjectIdentifier()))
		{
			throw new MissingPropertyException("projectIdentifier", request);
		}

		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getProjectTypeId()));
		if (projectTypeId == null)
		{
			throw new MissingPropertyException("projectTypeId", request);
		}

		final ProjectType projectType = projectTypeRepository.getByIdOrNull(projectTypeId);

		if (projectType == null)
		{
			throw MissingResourceException.builder()
					.resourceName("ProjectType")
					.resourceIdentifier(Integer.toString(projectTypeId.getRepoId()))
					.parentResource(request)
					.build();
		}

		if (!projectType.getProjectCategory().isBudget())
		{
			throw new AdempiereException("ProjectType.ProjectCategory is not meant for budget!")
					.appendParametersToMessage()
					.setParameter("ProjectTypeId", projectType.getId().getRepoId());
		}

		request.getResources().forEach(resourceRequest -> {
			if (Check.isBlank(resourceRequest.getResourceIdentifier()))
			{
				throw new MissingPropertyException("resourceIdentifier", resourceRequest);
			}
		});
	}
}
