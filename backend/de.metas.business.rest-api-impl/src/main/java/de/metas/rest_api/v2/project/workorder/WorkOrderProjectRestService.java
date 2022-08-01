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
import de.metas.common.rest_api.v2.project.workorder.JsonWOStepStatus;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectsUnderTestResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
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
import de.metas.project.workorder.data.WOProjectObjectUnderTest;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.project.workorder.data.WOStepStatus;
import de.metas.project.workorder.data.WorkOrderProjectRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectResponseMapper;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderProjectRestService
{
	private static final Logger logger = LogManager.getLogger(WorkOrderProjectRestService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WorkOrderProjectRepository projectRepository;
	private final ProjectTypeRepository projectTypeRepository;
	private final WorkOrderMapper workOrderProjectJsonToInternalConverter;

	public WorkOrderProjectRestService(
			@NonNull final WorkOrderProjectRepository projectRepository,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final WorkOrderMapper workOrderProjectJsonToInternalConverter)
	{
		this.projectRepository = projectRepository;
		this.projectTypeRepository = projectTypeRepository;
		this.workOrderProjectJsonToInternalConverter = workOrderProjectJsonToInternalConverter;
	}

	public JsonWorkOrderProjectResponse getWorkOrderProjectDataById(@NonNull final ProjectId projectId)
	{
		return projectRepository.getOptionalById(projectId)
				.map(projectData -> toJsonWorkOrderProjectResponse(projectData))
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Work Order Project")
						.resourceIdentifier(String.valueOf(projectId.getRepoId()))
						.build());
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

		final WOProjectResponseMapper.WOProjectResponseMapperBuilder responseMapper = WOProjectResponseMapper.builder();

		if (existingWOProjectOpt.isPresent())
		{
			final WOProject existingWOProject = existingWOProjectOpt.get();

			final ProjectId existingWOProjectId = existingWOProject.getProjectIdNonNull();
			logger.debug("Found Work Order Project with id={}", existingWOProjectId);

			final WOProject jsonUpdatedWOProject = workOrderProjectJsonToInternalConverter.mapWOProjectFromJson(responseMapper, request, existingWOProject);
			final JsonResponseUpsertItem.SyncOutcome projectSyncOutcome = updateExistingWOProject(responseMapper, jsonUpdatedWOProject, woProjectSyncAdvise);

			return null; //todo fp return response + handle DO NOTHING for children
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

			final WOProject projectData = workOrderProjectJsonToInternalConverter.mapWOProjectFromJson(responseMapper, request, null);
			final WOProject updatedProjectData = projectRepository.save(projectData);

			return null;
		}
	}

	@NonNull
	private JsonResponseUpsertItem.SyncOutcome updateExistingWOProject(
			@NonNull final WOProjectResponseMapper.WOProjectResponseMapperBuilder responseMapper,
			@NonNull final WOProject existingWOProject,
			@NonNull final SyncAdvise syncAdvise)
	{
		if (syncAdvise.getIfExists().isUpdate())
		{
			final WOProject updatedWOProject = projectRepository.save(existingWOProject);
			responseMapper.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);
			return JsonResponseUpsertItem.SyncOutcome.UPDATED; //todo fp handle here
		}
		else
		{
			responseMapper.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED);
			return JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
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
		return JsonWorkOrderProjectResponse.builder()
				.projectId(JsonMetasfreshId.of(projectData.getProjectIdNonNull().getRepoId()))
				.value(projectData.getValueNonNull())
				.name(projectData.getNameNonNull())
				.projectTypeId(JsonMetasfreshId.of(ProjectTypeId.toRepoId(projectData.getProjectTypeId())))
				.priceListVersionId(JsonMetasfreshId.ofOrNull(PriceListVersionId.toRepoId(projectData.getPriceListVersionId())))
				.currencyId(JsonMetasfreshId.ofOrNull(CurrencyId.toRepoId(projectData.getCurrencyId())))
				.salesRepId(JsonMetasfreshId.ofOrNull(UserId.toRepoId(projectData.getSalesRepId())))
				.description(projectData.getDescription())
				.dateContract(TimeUtil.asLocalDate(projectData.getDateContract()))
				.dateFinish(TimeUtil.asLocalDate(projectData.getDateFinish()))
				.bPartnerId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(projectData.getBPartnerId())))
				.projectReferenceExt(projectData.getProjectReferenceExt())
				.projectParentId(JsonMetasfreshId.ofOrNull(ProjectId.toRepoId(projectData.getProjectParentId())))
				.orgCode(orgDAO.retrieveOrgValue(projectData.getOrgId()))
				.isActive(projectData.getIsActive())
				.specialistConsultantId(projectData.getSpecialistConsultantId())
				.dateOfProvisionByBPartner(TimeUtil.asLocalDate(projectData.getDateOfProvisionByBPartner()))
				.steps(toJsonWorkOrderStepResponse(projectData.getProjectSteps()))
				.build();
	}

	@NonNull
	private ImmutableList<JsonWorkOrderStepResponse> toJsonWorkOrderStepResponse(@Nullable final List<WOProjectStep> woProjectSteps)
	{
		if (woProjectSteps == null || woProjectSteps.isEmpty())
		{
			return ImmutableList.of();
		}

		return woProjectSteps.stream()
				.map(WorkOrderProjectRestService::toJsonWorkOrderStepResponse)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static JsonWorkOrderStepResponse toJsonWorkOrderStepResponse(@NonNull final WOProjectStep woProjectStep)
	{
		return JsonWorkOrderStepResponse.builder()
				.stepId(JsonMetasfreshId.of(WOProjectStepId.toRepoId(woProjectStep.getWOProjectStepIdNonNull())))
				.name(woProjectStep.getName())
				.projectId(JsonMetasfreshId.of(woProjectStep.getProjectIdNonNull().getRepoId()))
				.description(woProjectStep.getDescription())
				.seqNo(woProjectStep.getSeqNoNonNull())
				.dateStart(TimeUtil.asLocalDate(woProjectStep.getDateStart()))
				.dateEnd(TimeUtil.asLocalDate(woProjectStep.getDateEnd()))
				// .externalId(JsonMetasfreshId.ofOrNull(Integer.valueOf(ExternalId.toValue(woProjectStep.getExternalId())))) //todo fp resolve this
				.woPartialReportDate(TimeUtil.asLocalDate(woProjectStep.getWoPartialReportDate()))
				.woPlannedResourceDurationHours(woProjectStep.getWoPlannedResourceDurationHours())
				.deliveryDate(TimeUtil.asLocalDate(woProjectStep.getDeliveryDate()))
				.woTargetStartDate(TimeUtil.asLocalDate(woProjectStep.getWoTargetStartDate()))
				.woTargetEndDate(TimeUtil.asLocalDate(woProjectStep.getWoTargetEndDate()))
				.woPlannedPersonDurationHours(woProjectStep.getWoPlannedPersonDurationHours())
				.woStepStatus(toJsonWOStepStatus(woProjectStep.getWoStepStatus()))
				.woFindingsReleasedDate(TimeUtil.asLocalDate(woProjectStep.getWoFindingsReleasedDate()))
				.woFindingsCreatedDate(TimeUtil.asLocalDate(woProjectStep.getWoFindingsCreatedDate()))
				.resources(toJsonWorkOrderResourceResponse(woProjectStep.getProjectResources()))
				.build();
	}

	@Nullable
	private static JsonWOStepStatus toJsonWOStepStatus(@Nullable final WOStepStatus woStepStatus)
	{
		if (woStepStatus == null)
		{
			return null;
		}

		switch (woStepStatus)
		{
			case CREATED:
				return JsonWOStepStatus.CREATED;
			case RECEIVED:
				return JsonWOStepStatus.RECEIVED;
			case RELEASED:
				return JsonWOStepStatus.RELEASED;
			case EARMARKED:
				return JsonWOStepStatus.EARMARKED;
			case READYFORTESTING:
				return JsonWOStepStatus.READYFORTESTING;
			case INTESTING:
				return JsonWOStepStatus.INTESTING;
			case EXECUTED:
				return JsonWOStepStatus.EXECUTED;
			case READY:
				return JsonWOStepStatus.READY;
			case CANCELED:
				return JsonWOStepStatus.CANCELED;
			default:
				throw new AdempiereException("Unhandeled woStepStatus: " + woStepStatus);
		}
	}

	@Nullable
	private static ImmutableList<JsonWorkOrderResourceResponse> toJsonWorkOrderResourceResponse(@Nullable final List<WOProjectResource> resources)
	{
		if (resources == null || resources.isEmpty())
		{
			return ImmutableList.of();
		}

		return resources.stream()
				.map(WorkOrderProjectRestService::toJsonWorkOrderResourceResponse)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static JsonWorkOrderResourceResponse toJsonWorkOrderResourceResponse(@NonNull final WOProjectResource resourceData)
	{
		return JsonWorkOrderResourceResponse.builder()
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
	}

	@Nullable
	private static List<JsonWorkOrderObjectsUnderTestResponse> toJsonWorkOrderObjectsUnderTestResponse(@Nullable final List<WOProjectObjectUnderTest> objectsUnderTest)
	{
		if (objectsUnderTest == null || objectsUnderTest.isEmpty())
		{
			return ImmutableList.of();
		}

		return objectsUnderTest.stream()
				.map(WorkOrderProjectRestService::toJsonWorkOrderObjectsUnderTestResponse)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static JsonWorkOrderObjectsUnderTestResponse toJsonWorkOrderObjectsUnderTestResponse(@Nullable final WOProjectObjectUnderTest objectUnderTest)
	{
		return JsonWorkOrderObjectsUnderTestResponse.builder()
				.objectUnderTestId(JsonMetasfreshId.of(objectUnderTest.getIdNonNull().getRepoId()))
				.numberOfObjectsUnderTest(objectUnderTest.getNumberOfObjectsUnderTest())
				// .externalId(JsonMetasfreshId.ofOrNull(Integer.valueOf(ExternalId.toValue(objectUnderTest.getExternalId())))) //todo fp resolve this
				.woDeliveryNote(objectUnderTest.getWoDeliveryNote())
				.woManufacturer(objectUnderTest.getWoManufacturer())
				.woObjectType(objectUnderTest.getWoObjectType())
				.woObjectName(objectUnderTest.getWoObjectName())
				.woObjectWhereabouts(objectUnderTest.getWoObjectWhereabouts())
				.build();
	}
}
