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
import de.metas.project.workorder.data.WOProjectQuery;
import de.metas.project.workorder.data.WorkOrderProjectRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
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
		return toJsonWorkOrderProjectResponse(woProjectRepository.getById(projectId));
	}

	@NonNull
	public JsonWorkOrderProjectUpsertResponse upsertWOProject(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertWOProjectWithinTrx(request));
	}

	@NonNull
	private JsonWorkOrderProjectUpsertResponse upsertWOProjectWithinTrx(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		validateJsonWorkOrderProjectUpsertRequest(request);

		final SyncAdvise woProjectSyncAdvise = request.getSyncAdvise();

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());

		final Optional<WOProject> existingWOProjectOpt = getExistingWOProject(request.mapProjectIdentifier(IdentifierString::of), orgId);

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
			woProject = woProjectRepository.create(createWOProjectRequest);

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}
		else
		{
			final WOProject syncedWOProject = woProjectMapper.syncWOProjectWithJson(request, existingWOProject, orgId);
			woProject = woProjectRepository.update(syncedWOProject);

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
		}

		final List<JsonWorkOrderObjectUnderTestUpsertResponse> workOrderObjectUnderTestUpsertResponse = upsertObjectsUnderTest(request, woProject.getProjectId(), syncAdvise);

		final List<JsonWorkOrderStepUpsertResponse> workOrderStepUpsertResponse = upsertWOProjectSteps(request, woProject.getProjectId(), syncAdvise);

		return JsonWorkOrderProjectUpsertResponse.builder()
				.metasfreshId(JsonMetasfreshId.of(woProject.getProjectId().getRepoId()))
				.identifier(request.getIdentifier())
				.syncOutcome(syncOutcome)
				.objectsUnderTest(workOrderObjectUnderTestUpsertResponse)
				.steps(workOrderStepUpsertResponse)
				.build();
	}

	@NonNull
	private Optional<WOProject> getExistingWOProject(@NonNull final IdentifierString projectIdentifier, @NonNull final OrgId orgId)
	{
		if (projectIdentifier.isMetasfreshId())
		{
			final ProjectId existingProjectId = ProjectId.ofRepoId(projectIdentifier.asMetasfreshId().getValue());
			return Optional.of(woProjectRepository.getById(existingProjectId));
		}

		return woProjectRepository.getOptionalBy(getWOProjectQueryFromIdentifier(orgId, projectIdentifier));
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

		final SyncAdvise woProjectSyncAdvise = request.getSyncAdvise();
		if (woProjectSyncAdvise == null)
		{
			throw new MissingPropertyException("syncAdvise", request);
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

		if (!projectType.getProjectCategory().isWorkOrder())
		{
			throw new AdempiereException("Given C_Project_ID is not a work order project!")
					.appendParametersToMessage()
					.setParameter("ProjectCategory", projectType.getProjectCategory());
		}

		final IdentifierString projectIdentifier = request.mapProjectIdentifier(IdentifierString::of);

		if (projectIdentifier.isExternalId() && !projectIdentifier.asExternalId().getValue().equals(request.getPoReference()))
		{
			throw new AdempiereException("WorkOrderProject.Identifier doesn't match with WorkOrderProject.POReference")
					.appendParametersToMessage()
					.setParameter("WorkOrderProject.Identifier", projectIdentifier.getRawIdentifierString())
					.setParameter("WorkOrderProject.POReference", request.getPoReference());
		}
	}

	@NonNull
	private JsonWorkOrderProjectResponse toJsonWorkOrderProjectResponse(@NonNull final WOProject project)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(project.getOrgId());

		return JsonWorkOrderProjectResponse.builder()
				.projectId(JsonMetasfreshId.of(project.getProjectId().getRepoId()))
				.value(project.getValue())
				.name(project.getName())
				.projectTypeId(JsonMetasfreshId.of(ProjectTypeId.toRepoId(project.getProjectTypeId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(project.getPriceListVersionId())))
				.currencyId(JsonMetasfreshId.ofOrNull(CurrencyId.toRepoId(project.getCurrencyId())))
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(project.getSalesRepId())))
				.description(project.getDescription())
				.dateContract(TimeUtil.asLocalDate(project.getDateContract(), zoneId))
				.dateFinish(TimeUtil.asLocalDate(project.getDateFinish(), zoneId))
				.bPartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(project.getBPartnerId())))
				.projectReferenceExt(project.getProjectReferenceExt())
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(project.getProjectParentId())))
				.orgCode(orgDAO.retrieveOrgValue(project.getOrgId()))
				.isActive(project.getIsActive())
				.bpartnerDepartment(project.getBpartnerDepartment())
				.dateOfProvisionByBPartner(TimeUtil.asLocalDate(project.getDateOfProvisionByBPartner(), zoneId))
				.woOwner(project.getWoOwner())
				.poReference(project.getPoReference())
				.bpartnerTargetDate(TimeUtil.asLocalDate(project.getBpartnerTargetDate(), zoneId))
				.woProjectCreatedDate(TimeUtil.asLocalDate(project.getWoProjectCreatedDate(), zoneId))
				.steps(workOrderProjectStepRestService.getByProjectId(project.getProjectId(), project.getOrgId()))
				.objectsUnderTest(workOrderProjectObjectUnderTestRestService.getByProjectId(project.getProjectId()))
				.build();
	}

	@NonNull
	private List<JsonWorkOrderObjectUnderTestUpsertResponse> upsertObjectsUnderTest(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final ProjectId projectId,
			@NonNull final SyncAdvise syncAdvise)
	{
		final JsonWorkOrderObjectUnderTestUpsertRequest jsonWorkOrderObjectUnderTestUpsertRequest = JsonWorkOrderObjectUnderTestUpsertRequest.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectId)))
				.requestItems(request.getObjectsUnderTest())
				.syncAdvise(syncAdvise)
				.build();

		return workOrderProjectObjectUnderTestRestService.upsertObjectUnderTest(jsonWorkOrderObjectUnderTestUpsertRequest);
	}

	@NonNull
	private List<JsonWorkOrderStepUpsertResponse> upsertWOProjectSteps(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final ProjectId projectId,
			@NonNull final SyncAdvise syncAdvise)
	{
		final JsonWorkOrderStepUpsertRequest jsonWorkOrderStepUpsertRequest = JsonWorkOrderStepUpsertRequest.builder()
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectId)))
				.requestItems(request.getSteps())
				.syncAdvise(syncAdvise)
				.build();

		return workOrderProjectStepRestService.upsertStep(jsonWorkOrderStepUpsertRequest);
	}

	@NonNull
	private static WOProjectQuery getWOProjectQueryFromIdentifier(
			@NonNull final OrgId orgId,
			@NonNull final IdentifierString identifier)
	{
		final WOProjectQuery.WOProjectQueryBuilder projectQueryBuilder = WOProjectQuery.builder()
				.orgId(orgId);

		switch (identifier.getType())
		{
			case VALUE:
				projectQueryBuilder.value(identifier.asValue());
				break;
			case EXTERNAL_ID:
				projectQueryBuilder.externalProjectReference(identifier.asExternalId());
				break;
			default:
				throw new InvalidIdentifierException(identifier.getRawIdentifierString());
		}

		return projectQueryBuilder.build();
	}
}
