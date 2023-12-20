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

package de.metas.rest_api.v2.project;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.JsonRequestProjectUpsert;
import de.metas.common.rest_api.v2.project.JsonRequestProjectUpsertItem;
import de.metas.common.rest_api.v2.project.JsonResponseProject;
import de.metas.common.rest_api.v2.project.JsonResponseProjectUpsert;
import de.metas.common.rest_api.v2.project.JsonResponseProjectUpsertItem;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.Project;
import de.metas.project.ProjectData;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.status.RStatusId;
import de.metas.project.RequestStatusCategoryId;
import de.metas.project.service.ProjectRepository;
import de.metas.project.service.ProjectService;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectRestService
{
	private static final Logger logger = LogManager.getLogger(ProjectRestService.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);

	private final ProjectRepository projectRepository;
	private final ProjectService projectService;

	public ProjectRestService(
			@NonNull final ProjectRepository projectRepository,
			@NonNull final ProjectService projectService)
	{
		this.projectRepository = projectRepository;
		this.projectService = projectService;
	}

	@NonNull
	public JsonResponseProjectUpsert upsertProjects(@NonNull final JsonRequestProjectUpsert request)
	{
		return trxManager.callInNewTrx(() -> upsertProjectsWithinTrx(request));
	}

	@NonNull
	private JsonResponseProjectUpsert upsertProjectsWithinTrx(@NonNull final JsonRequestProjectUpsert request)
	{
		final SyncAdvise syncAdvise = request.getSyncAdvise();

		final List<JsonResponseProjectUpsertItem> responseList =
				request.getRequestItems()
						.stream()
						.map(reqItem -> upsertProjectItem(reqItem, syncAdvise))
						.collect(ImmutableList.toImmutableList());

		return JsonResponseProjectUpsert.builder()
				.responseItems(responseList)
				.build();
	}

	@NonNull
	public Optional<JsonResponseProject> retrieveProject(@NonNull final ProjectId projectId)
	{
		return projectRepository.getOptionalById(projectId)
				.map(this::toJsonResponseProject);
	}

	@NonNull
	private JsonResponseProject toJsonResponseProject(@NonNull final Project project)
	{
		final ProjectData projectData = project.getProjectData();

		final CurrencyCode currencyCode = currenciesRepo.getCurrencyCodeById(projectData.getCurrencyId());

		final String projectCategory = projectData.getProjectCategory() != null ?
				projectData.getProjectCategory().getCode() :
				null;

		return JsonResponseProject.builder()
				.id(JsonMetasfreshId.of(project.getId().getRepoId()))
				.orgId(JsonMetasfreshId.of(projectData.getOrgId().getRepoId()))
				.currencyCode(currencyCode.toThreeLetterCode())
				.name(projectData.getName())
				.value(projectData.getValue())
				.active(projectData.isActive())
				.bpartnerLocationId(JsonMetasfreshId.ofOrNull(BPartnerLocationId.toRepoId(projectData.getBPartnerLocationId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(projectData.getPriceListVersionId())))
				.warehouseId(JsonMetasfreshId.ofOrNull(WarehouseId.toRepoId(projectData.getWarehouseId())))
				.contactId(JsonMetasfreshId.ofOrNull(BPartnerContactId.toRepoId(projectData.getContactId())))
				.description(projectData.getDescription())
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(projectData.getProjectParentId())))
				.projectTypeId(JsonMetasfreshId.ofOrNull(ProjectTypeId.toRepoId(projectData.getProjectTypeId())))
				.projectCategory(projectCategory)
				.requestStatusCategoryId(JsonMetasfreshId.of(RequestStatusCategoryId.toRepoId(projectData.getRequestStatusCategoryId())))
				.projectStatusId(JsonMetasfreshId.ofOrNull(RStatusId.toRepoId(projectData.getProjectStatusId())))
				.bpartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(projectData.getBPartnerId())))
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(projectData.getSalesRepId())))
				.dateContract(projectData.getDateContract())
				.dateFinish(projectData.getDateFinish())
				.build();
	}

	@NonNull
	private JsonResponseProjectUpsertItem upsertProjectItem(
			@NonNull final JsonRequestProjectUpsertItem jsonRequestProjectUpsertItem,
			@NonNull final SyncAdvise syncAdvise)
	{
		validateJsonRequestProjectUpsertItem(jsonRequestProjectUpsertItem);

		final JsonResponseUpsertItem.SyncOutcome syncOutcome;
		final ProjectId projectId;

		final ProjectId existingProjectId = ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getProjectId()));

		if (existingProjectId != null)
		{
			return updateExistingProject(jsonRequestProjectUpsertItem, existingProjectId, syncAdvise);
		}
		else if (syncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("Project")
					.parentResource(jsonRequestProjectUpsertItem)
					.build()
					.setParameter("syncAdvise", syncAdvise);
		}
		else
		{
			final ProjectData projectData = getProjectData(jsonRequestProjectUpsertItem);
			projectId = projectRepository.create(projectData).getId();

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}

		return JsonResponseProjectUpsertItem.builder()
				.metasfreshId(JsonMetasfreshId.of(projectId.getRepoId()))
				.syncOutcome(syncOutcome)
				.build();
	}

	@NonNull
	private ProjectData getProjectData(@NonNull final JsonRequestProjectUpsertItem jsonRequestProjectUpsertItem)
	{
		final CurrencyId currencyId = currenciesRepo.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(jsonRequestProjectUpsertItem.getCurrencyCode())).getId();

		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(jsonRequestProjectUpsertItem.getProjectTypeId()));

		final String projectValue = Check.isNotBlank(jsonRequestProjectUpsertItem.getValue())
				? jsonRequestProjectUpsertItem.getValue()
				: Optional.ofNullable(projectTypeId).map(projectService::getNextProjectValue).orElse(null);

		if (Check.isBlank(projectValue))
		{
			throw new AdempiereException("Could not compute C_Project.Value for projectTypeId: " + projectTypeId);
		}

		return ProjectData.builder()
				.orgId(OrgId.ofRepoId(jsonRequestProjectUpsertItem.getOrgId().getValue()))
				.name(jsonRequestProjectUpsertItem.getName())
				.currencyId(currencyId)
				.value(projectValue)
				.description(jsonRequestProjectUpsertItem.getDescription())
				.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getProjectParentId())))
				.projectTypeId(ProjectTypeId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getProjectTypeId())))
				.projectStatusId(RStatusId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getProjectStatusId())))
				.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getBpartnerId())))
				.salesRepId(UserId.ofIntegerOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getSalesRepId())))
				.dateContract(jsonRequestProjectUpsertItem.getDateContract())
				.dateFinish(jsonRequestProjectUpsertItem.getDateFinish())
				.active(jsonRequestProjectUpsertItem.getActive())
				.build();
	}

	@NonNull
	private Project syncProjectWithJson(
			@NonNull final JsonRequestProjectUpsertItem jsonRequestProjectUpsertItem,
			@NonNull final Project existingProject)
	{
		final ProjectData.ProjectDataBuilder projectDataBuilder = existingProject.getProjectData().toBuilder();
		final ProjectData existingProjectData = existingProject.getProjectData();

		final CurrencyId currencyId = currenciesRepo.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(jsonRequestProjectUpsertItem.getCurrencyCode())).getId();

		projectDataBuilder
				.orgId(OrgId.ofRepoId(jsonRequestProjectUpsertItem.getOrgId().getValue()))
				.name(jsonRequestProjectUpsertItem.getName())
				.currencyId(currencyId);

		// value
		if (jsonRequestProjectUpsertItem.isValueSet())
		{
			projectDataBuilder.value(jsonRequestProjectUpsertItem.getValue());
		}
		else
		{
			projectDataBuilder.value(existingProjectData.getValue());
		}

		// description
		if (jsonRequestProjectUpsertItem.isDescriptionSet())
		{
			projectDataBuilder.description(jsonRequestProjectUpsertItem.getDescription());
		}
		else
		{
			projectDataBuilder.description(existingProjectData.getDescription());
		}

		// projectParentId
		if (jsonRequestProjectUpsertItem.isProjectParentIdSet())
		{
			projectDataBuilder.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getProjectParentId())));
		}
		else
		{
			projectDataBuilder.projectParentId(existingProjectData.getProjectParentId());
		}

		// projectTypeId
		if (jsonRequestProjectUpsertItem.isProjectTypeIdSet())
		{
			projectDataBuilder.projectTypeId(ProjectTypeId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getProjectTypeId())));
		}
		else
		{
			projectDataBuilder.projectTypeId(existingProjectData.getProjectTypeId());
		}

		// projectStatusId
		if (jsonRequestProjectUpsertItem.isProjectStatusIdSet())
		{
			projectDataBuilder.projectStatusId(RStatusId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getProjectStatusId())));
		}
		else
		{
			projectDataBuilder.projectStatusId(existingProjectData.getProjectStatusId());
		}

		// bPartnerId
		if (jsonRequestProjectUpsertItem.isBpartnerIdSet())
		{
			projectDataBuilder.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getBpartnerId())));
		}
		else
		{
			projectDataBuilder.bPartnerId(existingProjectData.getBPartnerId());
		}

		// salesRepId
		if (jsonRequestProjectUpsertItem.isSalesRepIdSet())
		{
			projectDataBuilder.salesRepId(UserId.ofIntegerOrNull(JsonMetasfreshId.toValue(jsonRequestProjectUpsertItem.getSalesRepId())));
		}
		else
		{
			projectDataBuilder.salesRepId(existingProjectData.getSalesRepId());
		}

		// dateContract
		if (jsonRequestProjectUpsertItem.isDateContractSet())
		{
			projectDataBuilder.dateContract(jsonRequestProjectUpsertItem.getDateContract());
		}
		else
		{
			projectDataBuilder.dateContract(existingProjectData.getDateContract());
		}

		// dateFinish
		if (jsonRequestProjectUpsertItem.isDateFinishSet())
		{
			projectDataBuilder.dateFinish(jsonRequestProjectUpsertItem.getDateFinish());
		}
		else
		{
			projectDataBuilder.dateFinish(existingProjectData.getDateFinish());
		}

		// active
		if (jsonRequestProjectUpsertItem.isActiveSet())
		{
			projectDataBuilder.active(jsonRequestProjectUpsertItem.getActive());
		}
		else
		{
			projectDataBuilder.active(existingProjectData.isActive());
		}

		return Project.builder()
				.id(existingProject.getId())
				.projectData(projectDataBuilder.build())
				.build();
	}

	@NonNull
	private JsonResponseProjectUpsertItem updateExistingProject(
			@NonNull final JsonRequestProjectUpsertItem jsonRequestProjectUpsertItem,
			@NonNull final ProjectId existingProjectId,
			@NonNull final SyncAdvise syncAdvise)
	{
		final Project existingProject = projectRepository.getOptionalById(existingProjectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Project")
						.parentResource(jsonRequestProjectUpsertItem)
						.build()
						.setParameter("syncAdvise", syncAdvise));

		final ProjectId projectId = existingProject.getId();
		logger.debug("Found Project with id={})", projectId);

		final JsonResponseUpsertItem.SyncOutcome syncOutcome;

		if (syncAdvise.getIfExists().isUpdate())
		{
			final Project project = syncProjectWithJson(jsonRequestProjectUpsertItem, existingProject);
			projectRepository.update(project);

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
		}
		else
		{
			syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
		}

		return JsonResponseProjectUpsertItem.builder()
				.metasfreshId(JsonMetasfreshId.of(projectId.getRepoId()))
				.syncOutcome(syncOutcome)
				.build();
	}

	private void validateJsonRequestProjectUpsertItem(@NonNull final JsonRequestProjectUpsertItem jsonRequestProjectUpsertItem)
	{
		if (jsonRequestProjectUpsertItem.getOrgId() == null)
		{
			throw new MissingPropertyException("orgId", jsonRequestProjectUpsertItem);
		}

		if (Check.isEmpty(jsonRequestProjectUpsertItem.getCurrencyCode()))
		{
			throw new MissingPropertyException("currencyCode", jsonRequestProjectUpsertItem);
		}

		if (Check.isEmpty(jsonRequestProjectUpsertItem.getName()))
		{
			throw new MissingPropertyException("name", jsonRequestProjectUpsertItem);
		}
	}
}
