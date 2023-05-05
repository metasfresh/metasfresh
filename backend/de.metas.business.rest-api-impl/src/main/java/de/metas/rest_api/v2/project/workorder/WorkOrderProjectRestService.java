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
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectQuery;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponses;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.workorder.project.CreateWOProjectRequest;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectQuery;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.rest_api.utils.IdentifierString;
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
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private final WOProjectRepository woProjectRepository;
	private final ProjectTypeRepository projectTypeRepository;
	private final WorkOrderMapper woProjectMapper;
	private final WorkOrderProjectObjectUnderTestRestService workOrderProjectObjectUnderTestRestService;
	private final WorkOrderProjectStepRestService workOrderProjectStepRestService;

	public WorkOrderProjectRestService(
			@NonNull final WOProjectRepository woProjectRepository,
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
	public JsonWorkOrderProjectResponses getWorkOrderProjectsByQuery(@NonNull final JsonWorkOrderProjectQuery queryRequest)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(queryRequest.getOrgCode());

		final WOProjectQuery query = WOProjectQuery.builder()
				.orgId(orgId)
				.externalProjectReferencePattern(queryRequest.getProjectReferenceExtPattern())
				.build();

		final List<JsonWorkOrderProjectResponse> projects = woProjectRepository.listBy(query)
				.stream()
				.map(this::toJsonWorkOrderProjectResponse)
				.collect(ImmutableList.toImmutableList());

		return JsonWorkOrderProjectResponses.builder()
				.projects(projects)
				.build();
	}

	@NonNull
	private JsonWorkOrderProjectUpsertResponse upsertWOProjectWithinTrx(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		validateJsonWorkOrderProjectUpsertRequest(request);

		final SyncAdvise woProjectSyncAdvise = request.getSyncAdvise();

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());

		final IdentifierString projectIdentifier = request.mapProjectIdentifier(IdentifierString::of);
		final Optional<WOProject> existingWOProjectOpt = getExistingWOProject(projectIdentifier, orgId);

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
			throw new AdempiereException("Given C_ProjectType_ID=" + ProjectTypeId.toRepoId(projectTypeId) + " is not a work order project!")
					.appendParametersToMessage()
					.setParameter("ProjectCategory", projectType.getProjectCategory());
		}

		// Note: we can have one ext-<ExternalId>, but update the project to another one. 
		// So the identifier's externalId might differ from the request's actual externalId
	}

	@NonNull
	private JsonWorkOrderProjectResponse toJsonWorkOrderProjectResponse(@NonNull final WOProject project)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(project.getOrgId());

		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(project.getCurrencyId());

		return JsonWorkOrderProjectResponse.builder()
				.projectId(JsonMetasfreshId.of(project.getProjectId().getRepoId()))
				.value(project.getValue())
				.name(project.getName())
				.projectTypeId(JsonMetasfreshId.of(ProjectTypeId.toRepoId(project.getProjectTypeId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(project.getPriceListVersionId())))
				.currencyCode(currencyCode.toThreeLetterCode())
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(project.getSalesRepId())))
				.description(project.getDescription())
				.dateContract(TimeUtil.asLocalDate(project.getDateContract(), zoneId))
				.dateFinish(TimeUtil.asLocalDate(project.getDateFinish(), zoneId))
				.bpartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(project.getBPartnerId())))
				.projectReferenceExt(project.getProjectReferenceExt())
				.externalId(JsonExternalId.ofOrNull(ExternalId.toValue(project.getExternalId())))
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(project.getProjectParentId())))
				.orgCode(orgDAO.retrieveOrgValue(project.getOrgId()))
				.isActive(project.getIsActive())
				.bpartnerDepartment(project.getBpartnerDepartment())
				.dateOfProvisionByBPartner(TimeUtil.asLocalDate(project.getDateOfProvisionByBPartner(), zoneId))
				.woOwner(project.getWoOwner())
				.poReference(project.getPoReference())
				.specialistConsultantId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(project.getSpecialistConsultantID())))
				.internalPriority(InternalPriority.toCode(project.getInternalPriority()))
				.bpartnerTargetDate(TimeUtil.asLocalDate(project.getBpartnerTargetDate(), zoneId))
				.woProjectCreatedDate(TimeUtil.asLocalDate(project.getWoProjectCreatedDate(), zoneId))
				.steps(workOrderProjectStepRestService.getByProjectId(project.getProjectId(), project.getOrgId()))
				.objectsUnderTest(workOrderProjectObjectUnderTestRestService.getByProjectId(project.getProjectId(), zoneId))
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
				projectQueryBuilder.externalId(identifier.asExternalId());
				break;
			default:
				throw new InvalidIdentifierException(identifier.getRawIdentifierString());
		}

		return projectQueryBuilder.build();
	}
}
