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

import de.metas.RestUtils;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResourceResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.budget.JsonRequestBudgetProjectResourceUpsert;
import de.metas.common.rest_api.v2.project.budget.JsonResponseBudgetProjectResourceUpsertItem;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.project.budget.CreateBudgetProjectRequest;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.persistence.custom_columns.CustomColumnService;
import org.adempiere.ad.persistence.custom_columns.CustomColumnsJsonValues;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
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
	private final BudgetProjectResourceRestService budgetProjectResourceRestService;
	private final CustomColumnService customColumnService;

	public BudgetProjectRestService(
			@NonNull final BudgetProjectRepository budgetProjectRepository,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final BudgetProjectJsonConverter budgetProjectJsonConverter,
			@NonNull final BudgetProjectResourceRestService budgetProjectResourceRestService,
			@NonNull final CustomColumnService customColumnService)
	{
		this.budgetProjectRepository = budgetProjectRepository;
		this.projectTypeRepository = projectTypeRepository;
		this.budgetProjectJsonConverter = budgetProjectJsonConverter;
		this.budgetProjectResourceRestService = budgetProjectResourceRestService;
		this.customColumnService = customColumnService;
	}

	@NonNull
	public JsonBudgetProjectResponse retrieveBudgetProjectWithResourcesById(@NonNull final ProjectId projectId)
	{
		final BudgetProject budgetProject = budgetProjectRepository.getOptionalById(projectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Budget Project")
						.resourceIdentifier(String.valueOf(projectId.getRepoId()))
						.build());

		return toJsonBudgetProjectResponse(budgetProject);
	}

	@NonNull
	public JsonBudgetProjectUpsertResponse upsertBudgetProject(@NonNull final JsonBudgetProjectUpsertRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertBudgetProjectWithinTrx(request));
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
		final JsonResponseUpsertItem.SyncOutcome syncOutcome;

		final ProjectId projectId;
		if (existingBudgetProject != null)
		{
			final BudgetProject budgetProject = budgetProjectJsonConverter.syncBudgetProjectWithJson(request, existingBudgetProject, orgId);
			projectId = budgetProjectRepository.update(budgetProject).getProjectId();

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
		}
		else
		{
			final CreateBudgetProjectRequest createBudgetProjectRequest = budgetProjectJsonConverter.buildCreateBudgetProjectRequest(request, orgId);
			projectId = budgetProjectRepository.create(createBudgetProjectRequest).getProjectId();

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}

		saveCustomColumns(projectId, request.getExtendedProps());

		final JsonRequestBudgetProjectResourceUpsert jsonRequestBudgetProjectResourceUpsert = buildJsonRequestBudgetProjectResourceUpsert(request, projectId, syncAdvise);
		final List<JsonResponseBudgetProjectResourceUpsertItem> budgetProjectResourceResponseItems = budgetProjectResourceRestService
				.upsertBudgetProjectResources(jsonRequestBudgetProjectResourceUpsert)
				.getResponseItems();

		return JsonBudgetProjectUpsertResponse.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectId)))
				.syncOutcome(syncOutcome)
				.budgetResources(budgetProjectResourceResponseItems)
				.build();
	}

	@NonNull
	private static JsonRequestBudgetProjectResourceUpsert buildJsonRequestBudgetProjectResourceUpsert(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@NonNull final ProjectId projectId,
			@NonNull final SyncAdvise syncAdvise)
	{
		return JsonRequestBudgetProjectResourceUpsert.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectId)))
				.requestItems(request.getResources())
				.syncAdvise(syncAdvise)
				.build();
	}

	@NonNull
	private JsonBudgetProjectResponse toJsonBudgetProjectResponse(@NonNull final BudgetProject budgetProject)
	{
		final ProjectId projectId = budgetProject.getProjectId();

		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(budgetProject.getCurrencyId());

		final List<JsonBudgetProjectResourceResponse> budgetProjectResourceResponses = budgetProjectResourceRestService.retrieveByProjectId(projectId);

		return JsonBudgetProjectResponse.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectId)))
				.bpartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(budgetProject.getBPartnerId())))
				.currencyCode(currencyCode.toThreeLetterCode())
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(budgetProject.getProjectParentId())))
				.projectTypeId(JsonMetasfreshId.of(ProjectTypeId.toRepoId(budgetProject.getProjectTypeId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(budgetProject.getPriceListVersionId())))
				.orgCode(orgDAO.retrieveOrgValue(budgetProject.getOrgId()))
				.dateContract(budgetProject.getDateContract())
				.dateFinish(budgetProject.getDateFinish())
				.description(budgetProject.getDescription())
				.projectReferenceExt(budgetProject.getProjectReferenceExt())
				.name(budgetProject.getName())
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(budgetProject.getSalesRepId())))
				.isActive(budgetProject.isActive())
				.value(budgetProject.getValue())
				.extendedProps(getCustomColumns(projectId).toMap())
				.projectResources(budgetProjectResourceResponses)
				.build();
	}

	@NonNull
	private Optional<BudgetProject> getExistingBudgetProject(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@NonNull final OrgId orgId)
	{
		final IdentifierString projectIdentifier = IdentifierString.of(request.getProjectIdentifier());

		if (projectIdentifier.isMetasfreshId())
		{
			final ProjectId existingProjectId = ProjectId.ofRepoId(MetasfreshId.toValue(projectIdentifier.asMetasfreshId()));
			return budgetProjectRepository.getOptionalById(existingProjectId);
		}

		final List<BudgetProject> projectList = budgetProjectRepository.getBy(BudgetProjectJsonConverter.getProjectQueryFromIdentifier(orgId, projectIdentifier));
		if (projectList.isEmpty())
		{
			return Optional.empty();
		}
		return Optional.of(CollectionUtils.singleElement(projectList));
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

	private void saveCustomColumns(@NonNull final ProjectId projectId, @NonNull final Map<String, Object> valuesByColumnName)
	{
		budgetProjectRepository.applyAndSave(projectId,
											 (projectRecord) -> customColumnService.setCustomColumns(InterfaceWrapperHelper.getPO(projectRecord), valuesByColumnName));
	}

	@NonNull
	private CustomColumnsJsonValues getCustomColumns(@NonNull final ProjectId projectId)
	{
		return budgetProjectRepository.mapProject(projectId, (record) ->
				customColumnService.getCustomColumnsJsonValues(InterfaceWrapperHelper.getPO(record)));
	}
}
