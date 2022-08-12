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

import de.metas.RestUtils;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.workorder.data.CreateWOProjectRequest;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WorkOrderProjectRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.project.resource.ResourceIdentifierUtil;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderProjectRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WorkOrderProjectRepository woProjectRepository;
	private final ProjectTypeRepository projectTypeRepository;
	private final WorkOrderMapper woProjectMapper;
	private final WorkOrderProjectObjectUnderTestRestService workOrderProjectObjectUnderTestRestService;
	private final WorkOrderProjectStepRestService workOrderProjectStepRestService;

	public WorkOrderProjectRestService(
			@NonNull final WorkOrderProjectRepository woProjectRepository,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final WorkOrderMapper woProjectMapper,
			@NonNull final WorkOrderProjectObjectUnderTestRestService workOrderProjectObjectUnderTestRestService,
			@NonNull final WorkOrderProjectStepRestService workOrderProjectStepRestService)
	{
		this.woProjectRepository = woProjectRepository;
		this.projectTypeRepository = projectTypeRepository;
		this.woProjectMapper = woProjectMapper;
		this.workOrderProjectObjectUnderTestRestService = workOrderProjectObjectUnderTestRestService;
		this.workOrderProjectStepRestService = workOrderProjectStepRestService;
	}

	@NonNull
	public JsonWorkOrderProjectResponse getWorkOrderProjectById(@NonNull final ProjectId projectId)
	{
		return woProjectRepository.getOptionalById(projectId)
				.map(this::toJsonWorkOrderProjectResponse)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Work Order Project")
						.resourceIdentifier(String.valueOf(projectId.getRepoId()))
						.build());
	}

	@NonNull
	public JsonWorkOrderProjectUpsertResponse upsertWOProject(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertWOProjectWithinTrx(request));
	}

	@NonNull
	private JsonWorkOrderProjectUpsertResponse upsertWOProjectWithinTrx(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		final SyncAdvise woProjectSyncAdvise = request.getSyncAdvise();
		if (woProjectSyncAdvise == null)
		{
			throw new MissingPropertyException("syncAdvise", request);
		}

		validateJsonWorkOrderProjectUpsertRequest(request);

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());

		final Optional<WOProject> existingWOProjectOpt = getExistingWOProject(request, orgId);

		if (existingWOProjectOpt.isPresent() && !woProjectSyncAdvise.getIfExists().isUpdate())
		{
			final WOProject existingWOProject = existingWOProjectOpt.get();

			return JsonWorkOrderProjectUpsertResponse.builder()
					.identifier(request.getIdentifier())
					.metasfreshId(JsonMetasfreshId.of(existingWOProject.getProjectId().getRepoId()))
					.syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE)
					.build();
		}
		else if (existingWOProjectOpt.isPresent() || !woProjectSyncAdvise.isFailIfNotExists())
		{
			final WOProject existingWOProject = existingWOProjectOpt.orElse(null);

			return upsertWOProject(request, existingWOProject, woProjectSyncAdvise, orgId);
		}
		else
		{
			throw MissingResourceException.builder()
					.resourceName("Work Order Project")
					.parentResource(request)
					.build()
					.setParameter("woProjectSyncAdvise", woProjectSyncAdvise);
		}
	}

	@NonNull
	private JsonWorkOrderProjectUpsertResponse upsertWOProject(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@Nullable final WOProject existingWOProject,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final OrgId orgId)
	{
		final JsonResponseUpsertItem.SyncOutcome syncOutcome;

		final WOProject woProject;

		if (existingWOProject == null)
		{
			final CreateWOProjectRequest createWOProjectRequest = woProjectMapper.buildCreateWOProjectRequest(request, orgId);
			woProject = woProjectRepository.save(createWOProjectRequest);

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}
		else
		{
			final WOProject syncedWOProject = woProjectMapper.syncWOProjectWithJson(request, existingWOProject, orgId);
			woProject = woProjectRepository.update(syncedWOProject);

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
		}

		final JsonWorkOrderObjectUnderTestUpsertRequest jsonWorkOrderObjectUnderTestUpsertRequest = buildJsonWorkOrderObjectUnderTestUpsertRequest(request, woProject.getProjectId(), syncAdvise);
		final List<JsonWorkOrderObjectUnderTestUpsertResponse> workOrderObjectUnderTestUpsertResponse = workOrderProjectObjectUnderTestRestService.upsertObjectUnderTest(jsonWorkOrderObjectUnderTestUpsertRequest);

		final JsonWorkOrderStepUpsertRequest jsonWorkOrderStepUpsertRequest = buildJsonWorkOrderStepUpsertRequest(request, woProject.getProjectId(), syncAdvise);
		final List<JsonWorkOrderStepUpsertResponse> workOrderStepUpsertResponse = workOrderProjectStepRestService.upsertStep(jsonWorkOrderStepUpsertRequest);

		return JsonWorkOrderProjectUpsertResponse.builder()
				.metasfreshId(JsonMetasfreshId.of(woProject.getProjectId().getRepoId()))
				.identifier(request.getIdentifier())
				.syncOutcome(syncOutcome)
				.objectsUnderTest(workOrderObjectUnderTestUpsertResponse)
				.steps(workOrderStepUpsertResponse)
				.build();
	}

	@NonNull
	private Optional<WOProject> getExistingWOProject(@NonNull final JsonWorkOrderProjectUpsertRequest request, @NonNull final OrgId orgId)
	{
		final IdentifierString projectIdentifier = IdentifierString.of(request.getIdentifier());

		if (projectIdentifier.isMetasfreshId())
		{
			final ProjectId existingProjectId = ProjectId.ofRepoId(MetasfreshId.toValue(projectIdentifier.asMetasfreshId()));
			return woProjectRepository.getOptionalById(existingProjectId);
		}

		return woProjectRepository.getOptionalBy(ResourceIdentifierUtil.getProjectQueryFromIdentifier(orgId, projectIdentifier));
	}

	private void validateJsonWorkOrderProjectUpsertRequest(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		if (Check.isBlank(request.getIdentifier()))
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
	}

	@NonNull
	private static JsonWorkOrderObjectUnderTestUpsertRequest buildJsonWorkOrderObjectUnderTestUpsertRequest(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final ProjectId projectId,
			@NonNull final SyncAdvise syncAdvise)
	{
		return JsonWorkOrderObjectUnderTestUpsertRequest.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectId)))
				.requestItems(request.getObjectsUnderTest())
				.syncAdvise(syncAdvise)
				.build();
	}

	@NonNull
	private static JsonWorkOrderStepUpsertRequest buildJsonWorkOrderStepUpsertRequest(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final ProjectId projectId,
			@NonNull final SyncAdvise syncAdvise)
	{
		return JsonWorkOrderStepUpsertRequest.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectId)))
				.requestItems(request.getSteps())
				.syncAdvise(syncAdvise)
				.build();
	}

	@NonNull
	private JsonWorkOrderProjectResponse toJsonWorkOrderProjectResponse(@NonNull final WOProject projectData)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(projectData.getOrgId());

		return JsonWorkOrderProjectResponse.builder()
				.projectId(JsonMetasfreshId.of(projectData.getProjectId().getRepoId()))
				.value(projectData.getValue())
				.name(projectData.getName())
				.projectTypeId(JsonMetasfreshId.of(ProjectTypeId.toRepoId(projectData.getProjectTypeId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(projectData.getPriceListVersionId())))
				.currencyId(JsonMetasfreshId.ofOrNull(CurrencyId.toRepoId(projectData.getCurrencyId())))
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(projectData.getSalesRepId())))
				.description(projectData.getDescription())
				.dateContract(TimeUtil.asLocalDate(projectData.getDateContract(), zoneId))
				.dateFinish(TimeUtil.asLocalDate(projectData.getDateFinish(), zoneId))
				.bPartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(projectData.getBPartnerId())))
				.projectReferenceExt(projectData.getProjectReferenceExt())
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(projectData.getProjectParentId())))
				.orgCode(orgDAO.retrieveOrgValue(projectData.getOrgId()))
				.isActive(projectData.getIsActive())
				.specialistConsultantId(projectData.getSpecialistConsultantId())
				.bpartnerDepartment(projectData.getBpartnerDepartment())
				.dateOfProvisionByBPartner(TimeUtil.asLocalDate(projectData.getDateOfProvisionByBPartner(), zoneId))
				.woOwner(projectData.getWoOwner())
				.poReference(projectData.getPoReference())
				.bpartnerTargetDate(TimeUtil.asLocalDate(projectData.getBpartnerTargetDate(), zoneId))
				.woProjectCreatedDate(TimeUtil.asLocalDate(projectData.getWoProjectCreatedDate(), zoneId))
				.steps(workOrderProjectStepRestService.getByProjectId(projectData.getProjectId(), projectData.getOrgId()))
				.objectsUnderTest(workOrderProjectObjectUnderTestRestService.getByProjectId(projectData.getProjectId()))
				.build();
	}
}
