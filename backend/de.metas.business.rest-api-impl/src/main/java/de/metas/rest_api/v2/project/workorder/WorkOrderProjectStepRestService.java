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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWOStepStatus;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertItemRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.project.workorder.step.CreateWOProjectStepRequest;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepQuery;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.project.workorder.step.WOStepStatus;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WorkOrderProjectStepRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WOProjectRepository workOrderProjectRepository;
	private final WOProjectStepRepository workOrderProjectStepRepository;
	private final WorkOrderProjectResourceRestService workOrderProjectResourceRestService;
	private final WOProjectStepRepository woProjectStepRepository;

	public WorkOrderProjectStepRestService(
			@NonNull final WOProjectRepository workOrderProjectRepository,
			@NonNull final WOProjectStepRepository workOrderProjectStepRepository,
			@NonNull final WorkOrderProjectResourceRestService workOrderProjectResourceRestService,
			@NonNull final WOProjectStepRepository woProjectStepRepository)
	{
		this.workOrderProjectRepository = workOrderProjectRepository;
		this.workOrderProjectStepRepository = workOrderProjectStepRepository;
		this.workOrderProjectResourceRestService = workOrderProjectResourceRestService;
		this.woProjectStepRepository = woProjectStepRepository;
	}

	@NonNull
	public List<JsonWorkOrderStepResponse> getByProjectId(@NonNull final ProjectId woProjectId, @NonNull final OrgId orgId)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final List<WOProjectStep> steps = workOrderProjectStepRepository.getByProjectId(woProjectId);

		final Set<WOProjectStepId> stepIds = steps.stream().map(WOProjectStep::getWoProjectStepId).collect(ImmutableSet.toImmutableSet());

		final List<JsonWorkOrderResourceResponse> woResources = workOrderProjectResourceRestService.getByWOStepId(stepIds, zoneId);
		final Map<JsonMetasfreshId, List<JsonWorkOrderResourceResponse>> stepId2WOResources = mapResourcesByStepId(woResources);

		return workOrderProjectStepRepository.getByProjectId(woProjectId)
				.stream()
				.map(projectStep -> toJsonWorkOrderStepResponse(projectStep, zoneId, stepId2WOResources))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<JsonWorkOrderStepUpsertResponse> upsertStep(@NonNull final JsonWorkOrderStepUpsertRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertStepWithinTrx(request));
	}

	@NonNull
	private List<JsonWorkOrderStepUpsertResponse> upsertStepWithinTrx(@NonNull final JsonWorkOrderStepUpsertRequest request)
	{
		if (Check.isEmpty(request.getRequestItems()))
		{
			return ImmutableList.of();
		}

		validateJsonWorkOrderStepUpsertRequest(request);

		final ProjectId projectId = request.getProjectId().mapValue(ProjectId::ofRepoId);

		final WOProject woProject = workOrderProjectRepository.getById(projectId);

		return upsertStepItems(request, woProject);
	}

	@NonNull
	private List<JsonWorkOrderStepUpsertResponse> upsertStepItems(@NonNull final JsonWorkOrderStepUpsertRequest request, @NonNull final WOProject existingWOProject)
	{
		final Map<IdentifierString, JsonWorkOrderStepUpsertItemRequest> identifierString2JsonStep = request.getRequestItems().stream()
				.collect(Collectors.toMap((jsonStep) -> IdentifierString.of(jsonStep.getIdentifier()), Function.identity()));

		return upsertWOSteps(existingWOProject.getProjectId(),
							 existingWOProject.getOrgId(),
							 identifierString2JsonStep,
							 request.getSyncAdvise());
	}

	@NonNull
	private CreateWOProjectStepRequest buildCreateWOProjectStepRequest(
			@NonNull final JsonWorkOrderStepUpsertItemRequest request,
			@NonNull final ProjectId projectId,
			@NonNull final OrgId orgId)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final Integer seqNo = request.isSeqNoSet()
				? request.getSeqNo()
				: woProjectStepRepository.getNextSeqNo(projectId);
		return CreateWOProjectStepRequest.builder()
				.orgId(orgId)
				.projectId(projectId)
				.seqNo(seqNo)
				.externalId(ExternalId.of(request.getExternalId()))
				.name(request.getName())
				.description(request.getDescription())
				.dateStart(TimeUtil.asStartOfDayInstant(request.getDateStart(), zoneId))
				.dateEnd(TimeUtil.asEndOfDayInstant(request.getDateEnd(), zoneId))
				.woPartialReportDate(TimeUtil.asInstant(request.getWoPartialReportDate(), zoneId))
				.woPlannedResourceDurationHours(request.getWoPlannedResourceDurationHours())
				.deliveryDate(TimeUtil.asInstant(request.getDeliveryDate(), zoneId))
				.woTargetStartDate(TimeUtil.asInstant(request.getWoTargetStartDate(), zoneId))
				.woTargetEndDate(TimeUtil.asInstant(request.getWoTargetEndDate(), zoneId))
				.woPlannedPersonDurationHours(request.getWoPlannedPersonDurationHours())
				.woStepStatus(toWoStepStatus(request.getWoStepStatus()))
				.woFindingsReleasedDate(TimeUtil.asInstant(request.getWoFindingsReleasedDate(), zoneId))
				.woFindingsCreatedDate(TimeUtil.asInstant(request.getWoFindingsCreatedDate(), zoneId))
				.build();
	}

	@NonNull
	private WOProjectStep syncWOProjectStepWithJson(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderStepUpsertItemRequest request,
			@NonNull final WOProjectStep existingWOProjectStep)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final WOProjectStep.WOProjectStepBuilder stepBuilder = existingWOProjectStep.toBuilder()
				.name(request.getName())
				.dateRange(getDateRange(zoneId, request, existingWOProjectStep));

		if (request.isSeqNoSet())
		{
			stepBuilder.seqNo(request.getSeqNo());
		}

		if (request.isExternalIdSet())
		{
			final ExternalId externalId = ExternalId.ofOrNull(request.getExternalId());
			stepBuilder.externalId(externalId);
		}

		if (request.isDescriptionSet())
		{
			stepBuilder.description(request.getDescription());
		}

		if (request.isWoPartialReportDateSet())
		{
			stepBuilder.woPartialReportDate(TimeUtil.asInstant(request.getWoPartialReportDate(), zoneId));
		}

		if (request.isWoPlannedResourceDurationHoursSet())
		{
			stepBuilder.woPlannedResourceDurationHours(request.getWoPlannedResourceDurationHours());
		}

		if (request.isDeliveryDateSet())
		{
			stepBuilder.deliveryDate(TimeUtil.asInstant(request.getDeliveryDate(), zoneId));
		}

		if (request.isWoTargetStartDateSet())
		{
			stepBuilder.woTargetStartDate(TimeUtil.asInstant(request.getWoTargetStartDate(), zoneId));
		}

		if (request.isWoTargetEndDateSet())
		{
			stepBuilder.woTargetEndDate(TimeUtil.asInstant(request.getWoTargetEndDate(), zoneId));
		}

		if (request.isWoPlannedPersonDurationHoursSet())
		{
			stepBuilder.woPlannedPersonDurationHours(request.getWoPlannedPersonDurationHours());
		}

		if (request.isWoStepStatusSet())
		{
			stepBuilder.woStepStatus(toWoStepStatus(request.getWoStepStatus()));
		}

		if (request.isWoFindingsReleasedDateSet())
		{
			stepBuilder.woFindingsReleasedDate(TimeUtil.asInstant(request.getWoFindingsReleasedDate(), zoneId));
		}

		if (request.isWoFindingsCreatedDateSet())
		{
			stepBuilder.woFindingsCreatedDate(TimeUtil.asInstant(request.getWoFindingsCreatedDate(), zoneId));
		}

		return stepBuilder.build();
	}

	@NonNull
	private Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> upsertResources(
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final Map<IdentifierString, WOProjectStepId> stepIdentifier2MetasfreshId,
			@NonNull final Collection<JsonWorkOrderStepUpsertItemRequest> jsonSteps)
	{

		final ImmutableList.Builder<JsonWorkOrderResourceUpsertRequest> upsertResourceRequestCollector = ImmutableList.builder();

		for (final JsonWorkOrderStepUpsertItemRequest jsonStep : jsonSteps)
		{
			if (Check.isEmpty(jsonStep.getResources()))
			{
				continue;
			}

			final IdentifierString stepIdentifier = jsonStep.mapStepIdentifier(IdentifierString::of);
			final WOProjectStepId woProjectStepId = stepIdentifier2MetasfreshId.get(stepIdentifier);

			final JsonWorkOrderResourceUpsertRequest upsertResourceRequest = JsonWorkOrderResourceUpsertRequest.builder()
					.projectId(JsonMetasfreshId.of(woProjectStepId.getProjectId().getRepoId()))
					.stepId(JsonMetasfreshId.of(woProjectStepId.getRepoId()))
					.requestItems(jsonStep.getResources())
					.build();

			upsertResourceRequestCollector.add(upsertResourceRequest);
		}

		return workOrderProjectResourceRestService.upsertResource(syncAdvise, upsertResourceRequestCollector.build());
	}

	@NonNull
	private JsonWorkOrderStepResponse toJsonWorkOrderStepResponse(
			@NonNull final WOProjectStep woProjectStep,
			@NonNull final ZoneId zoneId,
			@NonNull final Map<JsonMetasfreshId, List<JsonWorkOrderResourceResponse>> stepId2Resources)
	{
		final JsonMetasfreshId stepId = JsonMetasfreshId.of(woProjectStep.getWoProjectStepId().getRepoId());

		return JsonWorkOrderStepResponse.builder()
				.stepId(JsonMetasfreshId.of(woProjectStep.getWoProjectStepId().getRepoId()))
				.name(woProjectStep.getName())
				.projectId(JsonMetasfreshId.of(woProjectStep.getProjectId().getRepoId()))
				.description(woProjectStep.getDescription())
				.seqNo(woProjectStep.getSeqNo())

				.dateStart(woProjectStep.getStartDate()
								   .map(startDate -> TimeUtil.asLocalDate(startDate, zoneId))
								   .orElse(null))
				.dateEnd(woProjectStep.getEndDate()
								 .map(endDate -> TimeUtil.asLocalDate(endDate, zoneId))
								 .orElse(null))

				.externalId(ExternalId.toValue(woProjectStep.getExternalId()))
				.woPartialReportDate(TimeUtil.asLocalDate(woProjectStep.getWoPartialReportDate(), zoneId))
				.woPlannedResourceDurationHours(woProjectStep.getWoPlannedResourceDurationHours())
				.deliveryDate(TimeUtil.asLocalDate(woProjectStep.getDeliveryDate(), zoneId))
				.woTargetStartDate(TimeUtil.asLocalDate(woProjectStep.getWoTargetStartDate(), zoneId))
				.woTargetEndDate(TimeUtil.asLocalDate(woProjectStep.getWoTargetEndDate(), zoneId))
				.woPlannedPersonDurationHours(woProjectStep.getWoPlannedPersonDurationHours())
				.woStepStatus(toJsonWOStepStatus(woProjectStep.getWoStepStatus()))
				.woFindingsReleasedDate(TimeUtil.asLocalDate(woProjectStep.getWoFindingsReleasedDate(), zoneId))
				.woFindingsCreatedDate(TimeUtil.asLocalDate(woProjectStep.getWoFindingsCreatedDate(), zoneId))
				.resources(stepId2Resources.get(stepId))
				.build();
	}

	@NonNull
	private List<JsonWorkOrderStepUpsertResponse> upsertWOSteps(
			@NonNull final ProjectId woProjectId,
			@NonNull final OrgId orgId,
			@NonNull final Map<IdentifierString, JsonWorkOrderStepUpsertItemRequest> identifier2RequestItem,
			@NonNull final SyncAdvise syncAdvise)
	{
		final List<WOProjectStep> existingWOSteps = workOrderProjectStepRepository.getByProjectId(woProjectId);

		final Map<IdentifierString, WOProjectStep> objectsToUpdate =
				getItemsToUpdate(orgId, existingWOSteps, identifier2RequestItem.values(), syncAdvise);

		final Map<IdentifierString, CreateWOProjectStepRequest> objectsToCreate =
				getItemsToCreate(woProjectId, orgId, objectsToUpdate.keySet(), identifier2RequestItem, syncAdvise);

		final ImmutableList.Builder<JsonWorkOrderStepUpsertResponse> responseCollector = ImmutableList.builder();

		final JsonResponseUpsertItem.SyncOutcome toUpdateSyncOutcome;
		if (syncAdvise.getIfExists().isUpdate())
		{
			workOrderProjectStepRepository.updateAll(objectsToUpdate.values());
			toUpdateSyncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
		}
		else
		{
			toUpdateSyncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
		}

		final Map<IdentifierString, WOProjectStepId> identifier2MetasfreshId = new HashMap<>();
		objectsToUpdate.forEach((identifier, woStep) -> identifier2MetasfreshId.put(identifier, woStep.getWoProjectStepId()));

		objectsToCreate.forEach((identifier, createRequest) -> {
			final WOProjectStep woProjectStep = workOrderProjectStepRepository.create(createRequest);

			identifier2MetasfreshId.put(identifier, woProjectStep.getWoProjectStepId());
		});

		final Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> step2ResourceResponse =
				upsertResources(syncAdvise, identifier2MetasfreshId, identifier2RequestItem.values());

		objectsToUpdate.forEach((identifier, woProjectStep) -> responseCollector
				.add(JsonWorkOrderStepUpsertResponse.builder()
							 .identifier(identifier.getRawIdentifierString())
							 .metasfreshId(JsonMetasfreshId.of(woProjectStep.getWoProjectStepId().getRepoId()))
							 .syncOutcome(toUpdateSyncOutcome)
							 .resources(step2ResourceResponse.get(woProjectStep.getWoProjectStepId()))
							 .build()));

		objectsToCreate.keySet().forEach(identifier -> responseCollector
				.add(JsonWorkOrderStepUpsertResponse.builder()
							 .identifier(identifier.getRawIdentifierString())
							 .metasfreshId(JsonMetasfreshId.of(identifier2MetasfreshId.get(identifier).getRepoId()))
							 .syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
							 .resources(step2ResourceResponse.get(identifier2MetasfreshId.get(identifier)))
							 .build()));

		return responseCollector.build();
	}

	@NonNull
	private Map<IdentifierString, WOProjectStep> getItemsToUpdate(
			@NonNull final OrgId orgId,
			@NonNull final List<WOProjectStep> existingProjectSteps,
			@NonNull final Collection<JsonWorkOrderStepUpsertItemRequest> requestItems,
			@NonNull final SyncAdvise syncAdvise)
	{
		final ImmutableMap.Builder<IdentifierString, WOProjectStep> updatesCollector = ImmutableMap.builder();

		for (final JsonWorkOrderStepUpsertItemRequest item : requestItems)
		{
			final IdentifierString stepIdentifier = item.mapStepIdentifier(IdentifierString::of);

			resolveStepForExternalIdentifier(stepIdentifier, existingProjectSteps)
					//dev-note: avoid unnecessary processing
					.map(matchingStep -> syncAdvise.getIfExists().isUpdate()
							? syncWOProjectStepWithJson(orgId, item, matchingStep)
							: matchingStep)

					.ifPresent(syncedWOProjectUnderTest -> updatesCollector.put(stepIdentifier, syncedWOProjectUnderTest));
		}

		return updatesCollector.build();
	}

	@NonNull
	private Map<IdentifierString, CreateWOProjectStepRequest> getItemsToCreate(
			@NonNull final ProjectId projectId,
			@NonNull final OrgId orgId,
			@NonNull final Set<IdentifierString> stepIdentifiersMatchedForUpdate,
			@NonNull final Map<IdentifierString, JsonWorkOrderStepUpsertItemRequest> requestItems,
			@NonNull final SyncAdvise syncAdvise)
	{
		final ImmutableMap.Builder<IdentifierString, CreateWOProjectStepRequest> itemsToCreate = ImmutableMap.builder();

		final Set<IdentifierString> identifiesToCreate = getIdentifiersToCreate(orgId, stepIdentifiersMatchedForUpdate, requestItems.values(), projectId);

		if (syncAdvise.getIfNotExists().isFail() && !identifiesToCreate.isEmpty())
		{
			final String missingRawIdentifiers = identifiesToCreate.stream()
					.map(IdentifierString::getRawIdentifierString)
					.collect(Collectors.joining(","));

			throw MissingResourceException.builder()
					.resourceName("WOProjectStep")
					.resourceIdentifier(missingRawIdentifiers)
					.build()
					.setParameter("WOProjectStepSyncAdvise", syncAdvise);
		}

		for (final IdentifierString identifier : identifiesToCreate)
		{
			final JsonWorkOrderStepUpsertItemRequest item2Create = requestItems.get(identifier);

			final CreateWOProjectStepRequest createStepRequest = buildCreateWOProjectStepRequest(item2Create, projectId, orgId);

			itemsToCreate.put(identifier, createStepRequest);
		}

		return itemsToCreate.build();
	}

	/**
	 * @param requestProjectId see {@link #validateAreNotStoredUnderDifferentProject(Set, OrgId, ProjectId)}.
	 */
	@NonNull
	private Set<IdentifierString> getIdentifiersToCreate(
			@NonNull final OrgId orgId,
			@NonNull final Set<IdentifierString> stepIdentifiersMatchedForUpdate,
			@NonNull final Collection<JsonWorkOrderStepUpsertItemRequest> requestItems,
			@NonNull final ProjectId requestProjectId)
	{
		final ImmutableSet.Builder<IdentifierString> externalIdsToInsertCollector = ImmutableSet.builder();

		for (final JsonWorkOrderStepUpsertItemRequest item : requestItems)
		{
			final IdentifierString stepIdentifier = item.mapStepIdentifier(IdentifierString::of);

			if (stepIdentifiersMatchedForUpdate.contains(stepIdentifier))
			{
				continue;
			}

			if (stepIdentifier.isMetasfreshId())
			{
				throw new AdempiereException("WorkOrderStep is already placed below another project!")
						.setParameter("WorkOrderStepId", stepIdentifier.asMetasfreshId().getValue());
			}
			else if (stepIdentifier.isExternalId())
			{
				externalIdsToInsertCollector.add(stepIdentifier);
			}
			else
			{
				throw new AdempiereException("Unsupported identifier type=" + stepIdentifier.getType());
			}
		}

		final Set<IdentifierString> externalIdsToCreate = externalIdsToInsertCollector.build();

		validateAreNotStoredUnderDifferentProject(externalIdsToCreate, orgId, requestProjectId);

		return externalIdsToCreate;
	}

	/**
	 * Checks if the testStep - as identified by its externalId - is already stored below a C_Project that is different from the one specified in our overall JSON request-body which the step is a part of.
	 *
	 * @param requestProjectId there for information in case the method throws an exception
	 */
	private void validateAreNotStoredUnderDifferentProject(
			@NonNull final Set<IdentifierString> externalIdsToCreate,
			@NonNull final OrgId orgId,
			@NonNull final ProjectId requestProjectId)
	{
		if (externalIdsToCreate.isEmpty())
		{
			return;
		}

		final Set<String> rawExternalIds = externalIdsToCreate.stream()
				.map(IdentifierString::asExternalId)
				.map(ExternalId::getValue)
				.collect(ImmutableSet.toImmutableSet());

		final WOProjectStepQuery query = WOProjectStepQuery.builder()
				.orgId(orgId)
				.externalIds(rawExternalIds)
				.build();

		final List<WOProjectStep> existingProjectSteps = workOrderProjectStepRepository.getByQuery(query);

		if (!existingProjectSteps.isEmpty())
		{
			final String projectIds = existingProjectSteps.stream()
					.map(WOProjectStep::getProjectId)
					.map(ProjectId::getRepoId)
					.map(String::valueOf)
					.collect(Collectors.joining(","));

			throw new AdempiereException("WOProjectStep.ExternalId already stored under a different project!")
					.appendParametersToMessage()
					.setParameter("ExternalIds", StringUtils.join(rawExternalIds, ", "))
					.setParameter("Request-ProjectId", requestProjectId)
					.setParameter("Already stored under projectIds", projectIds);
		}
	}

	@NonNull
	private Optional<WOProjectStep> resolveStepForExternalIdentifier(
			@NonNull final IdentifierString externalIdentifier,
			@NonNull final List<WOProjectStep> projectSteps)
	{
		switch (externalIdentifier.getType())
		{
			case METASFRESH_ID:
				return projectSteps.stream()
						.filter(step -> {
							final WOProjectStepId stepId = step.getWoProjectStepId();
							final MetasfreshId metasfreshStepId = MetasfreshId.of(stepId);

							return metasfreshStepId.equals(externalIdentifier.asMetasfreshId());
						})
						.findFirst();

			case EXTERNAL_ID:
				return projectSteps.stream()
						.filter(step -> externalIdentifier.asExternalId().equals(step.getExternalId()))
						.findFirst();
			default:
				throw new AdempiereException("Unhandled IdentifierString type=" + externalIdentifier);
		}
	}

	@Nullable
	private static WOStepStatus toWoStepStatus(@Nullable final JsonWOStepStatus jsonWOStepStatus)
	{
		if (jsonWOStepStatus == null)
		{
			return null;
		}

		switch (jsonWOStepStatus)
		{
			case CREATED:
				return WOStepStatus.CREATED;
			case RECEIVED:
				return WOStepStatus.RECEIVED;
			case RELEASED:
				return WOStepStatus.RELEASED;
			case EARMARKED:
				return WOStepStatus.EARMARKED;
			case READYFORTESTING:
				return WOStepStatus.READYFORTESTING;
			case INTESTING:
				return WOStepStatus.INTESTING;
			case EXECUTED:
				return WOStepStatus.EXECUTED;
			case READY:
				return WOStepStatus.READY;
			case CANCELED:
				return WOStepStatus.CANCELED;
			default:
				throw new IllegalStateException("JsonWOStepStatus not supported: " + jsonWOStepStatus);
		}
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
				throw new AdempiereException("Unhandled woStepStatus: " + woStepStatus);
		}
	}

	private static void validateJsonWorkOrderStepUpsertRequest(@NonNull final JsonWorkOrderStepUpsertRequest request)
	{
		request.getRequestItems().forEach(requestItem -> {
			if (Check.isBlank(requestItem.getIdentifier()))
			{
				throw new MissingPropertyException("identifier", request);
			}

			if (Check.isBlank(requestItem.getName()))
			{
				throw new MissingPropertyException("name", request);
			}

			final IdentifierString identifierString = requestItem.mapStepIdentifier(IdentifierString::of);

			if (identifierString.isExternalId() && !identifierString.asExternalId().getValue().equals(requestItem.getExternalId()))
			{
				throw new AdempiereException("WorkOrderStep.Identifier doesn't match with WorkOrderObjectUnderTest.ExternalId")
						.appendParametersToMessage()
						.setParameter("WorkOrderStep.Identifier", identifierString.getRawIdentifierString())
						.setParameter("WorkOrderStep.ExternalId", requestItem.getExternalId());
			}
		});
	}

	@NonNull
	private static Map<JsonMetasfreshId, List<JsonWorkOrderResourceResponse>> mapResourcesByStepId(@NonNull final List<JsonWorkOrderResourceResponse> resourceResponses)
	{
		final Map<JsonMetasfreshId, List<JsonWorkOrderResourceResponse>> stepId2Resources = new HashMap<>();

		resourceResponses.forEach(resourceResponse -> {
			final List<JsonWorkOrderResourceResponse> resourceListByStepId = new ArrayList<>();
			resourceListByStepId.add(resourceResponse);

			stepId2Resources.merge(resourceResponse.getStepId(), resourceListByStepId, (oldList, newList) -> {
				oldList.addAll(newList);
				return oldList;
			});
		});

		return stepId2Resources;
	}

	@Nullable
	private CalendarDateRange getDateRange(
			@NonNull final ZoneId zoneId,
			@NonNull final JsonWorkOrderStepUpsertItemRequest request,
			@NonNull final WOProjectStep existingWOProjectStep)
	{
		final Instant actualDateStart = request.isDateStartSet()
				? TimeUtil.asInstant(request.getDateStart(), zoneId)
				: existingWOProjectStep.getStartDate().orElse(null);

		final Instant actualDateEnd = request.isDateEndSet()
				? TimeUtil.asInstant(request.getDateEnd(), zoneId)
				: existingWOProjectStep.getEndDate().orElse(null);

		if (actualDateStart == null && actualDateEnd != null)
		{
			throw new AdempiereException("DateStart cannot be missing when DateEnd is set!")
					.appendParametersToMessage()
					.setParameter("WOProjectStepId", existingWOProjectStep.getWoProjectStepId());
		}
		else if (actualDateStart == null)
		{
			return null;
		}

		final boolean isSameDate = actualDateStart.equals(actualDateEnd);

		final Instant computedDateStart = isSameDate
				? TimeUtil.asStartOfDayInstant(actualDateStart, zoneId)
				: actualDateStart;

		final Instant computedDateEnd = isSameDate
				? TimeUtil.asEndOfDayInstant(actualDateEnd, zoneId)
				: Optional.ofNullable(actualDateEnd).orElse(Instant.MAX);

		return CalendarDateRange.builder()
				.startDate(computedDateStart)
				.endDate(computedDateEnd)
				.allDay(Boolean.FALSE)
				.build();
	}
}