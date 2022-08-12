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
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWOStepStatus;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertItemRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectStepRepository;
import de.metas.project.workorder.data.CreateWOProjectStepRequest;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.project.workorder.data.WOStepStatus;
import de.metas.project.workorder.data.WorkOrderProjectRepository;
import de.metas.project.workorder.data.WorkOrderProjectStepRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.workorder.responsemapper.WOProjectStepResponseMapper;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WorkOrderProjectStepRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final WorkOrderProjectRepository workOrderProjectRepository;
	private final WorkOrderProjectStepRepository workOrderProjectStepRepository;
	private final WorkOrderProjectResourceRestService workOrderProjectResourceRestService;
	private final WOProjectStepRepository woProjectStepRepository;

	public WorkOrderProjectStepRestService(
			@NonNull final WorkOrderProjectRepository workOrderProjectRepository,
			@NonNull final WorkOrderProjectStepRepository workOrderProjectStepRepository,
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

		return workOrderProjectStepRepository.getByProjectId(woProjectId)
				.stream()
				.map(projectStep -> toJsonWorkOrderStepResponse(projectStep, zoneId))
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
		validateJsonWorkOrderStepUpsertRequest(request);

		final ProjectId projectId = ProjectId.ofRepoId(JsonMetasfreshId.toValueInt(request.getProjectId()));

		final WOProject woProject = workOrderProjectRepository.getOptionalById(projectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Work Order Project")
						.parentResource(request)
						.build());

		return upsertStepItems(request, woProject);
	}

	@NonNull
	private List<JsonWorkOrderStepUpsertResponse> upsertStepItems(@NonNull final JsonWorkOrderStepUpsertRequest request, @NonNull final WOProject existingWOProject)
	{
		final Map<IdentifierString, JsonWorkOrderStepUpsertItemRequest> identifierString2JsonStep = request.getRequestItems().stream()
				.collect(Collectors.toMap((jsonStep) -> IdentifierString.of(jsonStep.getIdentifier()), Function.identity()));

		final Map<IdentifierString, WOProjectStep> identifierString2WOStep = woProjectStepToUpdateMap(request.getRequestItems(), existingWOProject.getProjectId());

		return upsertStepItems(existingWOProject, request.getRequestItems(), request.getSyncAdvise(), identifierString2JsonStep, identifierString2WOStep);
	}

	@NonNull
	private List<JsonWorkOrderStepUpsertResponse> upsertStepItems(
			@NonNull final WOProject woProject,
			@NonNull final List<JsonWorkOrderStepUpsertItemRequest> jsonSteps,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final Map<IdentifierString, JsonWorkOrderStepUpsertItemRequest> identifierString2JsonStep,
			@NonNull final Map<IdentifierString, WOProjectStep> identifierString2WOStep)
	{
		final ImmutableList.Builder<WOProjectStep> stepToUpdate = ImmutableList.builder();
		final ImmutableList.Builder<CreateWOProjectStepRequest> stepToCreate = ImmutableList.builder();
		final ImmutableList.Builder<WOProjectStepResponseMapper> responseMapper = ImmutableList.builder();
		final ImmutableList.Builder<WOProjectStep> stepCollectorBuilder = ImmutableList.builder();

		for (final JsonWorkOrderStepUpsertItemRequest jsonWorkOrderStepUpsertItemRequest : jsonSteps)
		{
			final String identifier = jsonWorkOrderStepUpsertItemRequest.getIdentifier();
			final IdentifierString identifierString = IdentifierString.of(identifier);

			final JsonWorkOrderStepUpsertItemRequest requestItem = identifierString2JsonStep.get(identifierString);

			final Optional<WOProjectStep> existingStepOpt = Optional.ofNullable(identifierString2WOStep.get(identifierString));

			if (existingStepOpt.isPresent() && !syncAdvise.getIfExists().isUpdate())
			{
				final WOProjectStep existingStep = existingStepOpt.get();

				responseMapper.add(WOProjectStepResponseMapper.builder()
										   .identifier(identifier)
										   .syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE)
										   .build());

				stepCollectorBuilder.add(existingStep);
			}
			else if (existingStepOpt.isPresent() || !syncAdvise.isFailIfNotExists())
			{
				final WOProjectStep existingStep = existingStepOpt.orElse(null);

				if (existingStep == null)
				{
					stepToCreate.add(buildCreateWOProjectStepRequest(requestItem, woProject));

					responseMapper.add(WOProjectStepResponseMapper.builder()
											   .identifier(identifier)
											   .syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
											   .build());
				}
				else
				{
					stepToUpdate.add(syncWOProjectStepWithJson(woProject.getOrgId(), requestItem, existingStep));

					responseMapper.add(WOProjectStepResponseMapper.builder()
											   .identifier(identifier)
											   .syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED)
											   .build());
				}
			}
			else
			{
				throw MissingResourceException.builder()
						.resourceName("Work order project Step")
						.parentResource(requestItem)
						.build()
						.setParameter("stepSyncAdvise", syncAdvise);
			}
		}

		stepCollectorBuilder.addAll(workOrderProjectStepRepository.updateAll(stepToUpdate.build()));

		stepCollectorBuilder.addAll(workOrderProjectStepRepository.createAll(stepToCreate.build()));

		final List<WOProjectStep> stepCollector = stepCollectorBuilder.build();

	/*	//todo fp:
		Map<IdentifierString,WOProjectStep> stepIdentifier2WOProjectStep = new HashMap<>();
		for (final IdentifierString stepIdentifier : identifierString2JsonStep.keySet())
		{
			final List<JsonWorkOrderResourceUpsertItemRequest> resourcesToUpsert = identifierString2JsonStep.get(stepIdentifier).getResources();

			final WOProjectStep storedWOProjectStep = stepIdentifier2WOProjectStep.get(stepIdentifier);

			//todo fp: upsertAll resourcesToUpsert; -> response
		}
		//*/

		final Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> stepId2ResourcesResponse = upsertResources(syncAdvise, stepCollector, jsonSteps); //todo fp: drop

		return responseMapper.build()
				.stream()
				.map(stepMapper -> stepMapper.map(stepCollector, stepId2ResourcesResponse))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Map<IdentifierString, WOProjectStep> woProjectStepToUpdateMap(
			@NonNull final List<JsonWorkOrderStepUpsertItemRequest> requestSteps,
			@NonNull final ProjectId woProjectId)
	{
		final List<WOProjectStep> existingSteps = workOrderProjectStepRepository.getByProjectId(woProjectId);

		if (existingSteps == null || existingSteps.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<IdentifierString, WOProjectStep> woProjectStepToUpdateMap = ImmutableMap.builder();

		for (final JsonWorkOrderStepUpsertItemRequest jsonStep : requestSteps)
		{
			final IdentifierString stepIdentifier = IdentifierString.of(jsonStep.getIdentifier());

			WorkOrderMapperUtil.resolveStepForExternalIdentifier(stepIdentifier, existingSteps)
					.ifPresent(matchingStep -> woProjectStepToUpdateMap.put(stepIdentifier, matchingStep));
		}

		return woProjectStepToUpdateMap.build();
	}

	@NonNull
	private CreateWOProjectStepRequest buildCreateWOProjectStepRequest(
			@NonNull final JsonWorkOrderStepUpsertItemRequest request,
			@NonNull final WOProject woProject)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(woProject.getOrgId());

		return CreateWOProjectStepRequest.builder()
				.projectId(woProject.getProjectId())
				.externalId(ExternalId.of(request.getExternalId().getValue()))
				.name(request.getName())
				.description(request.getDescription())
				.seqNo(getSeqNo(request, woProject.getProjectId()))
				.dateStart(TimeUtil.asInstant(request.getDateStart(), zoneId))
				.dateEnd(TimeUtil.asInstant(request.getDateEnd(), zoneId))
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
				.seqNo(getSeqNo(request, existingWOProjectStep.getProjectId()));

		if (request.isExternalIdSet())
		{
			final JsonExternalId jsonExternalId = request.getExternalId();
			final ExternalId externalId = ExternalId.of(jsonExternalId.getValue());
			stepBuilder.externalId(externalId);
		}

		if (request.isDateStartSet())
		{
			stepBuilder.dateStart(TimeUtil.asInstant(request.getDateStart(), zoneId));
		}

		if (request.isDateEndSet())
		{
			final Instant dateEnd = TimeUtil.asEndOfDayInstant(request.getDateEnd(), zoneId);
			stepBuilder.dateEnd(dateEnd);
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
			@NonNull final List<WOProjectStep> existingSteps,
			@NonNull final List<JsonWorkOrderStepUpsertItemRequest> jsonSteps)
	{
		final ImmutableMap.Builder<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> stepId2ResourcesResponse = ImmutableMap.builder();

		for (final JsonWorkOrderStepUpsertItemRequest jsonStep : jsonSteps)
		{
			final IdentifierString stepIdentifier = IdentifierString.of(jsonStep.getIdentifier());

			WorkOrderMapperUtil.resolveStepForExternalIdentifier(stepIdentifier, existingSteps)
					.ifPresent(matchingStep -> {
								   final JsonWorkOrderResourceUpsertRequest jsonWorkOrderResourceUpsertRequest = buildJsonWorkOrderResourceUpsertRequest(jsonStep, matchingStep.getWoProjectStepId(), syncAdvise);
								   final List<JsonWorkOrderResourceUpsertResponse> resourcesResponse = workOrderProjectResourceRestService.upsertResource(jsonWorkOrderResourceUpsertRequest);

								   stepId2ResourcesResponse.put(matchingStep.getWoProjectStepId(), resourcesResponse);
							   }
					);
		}

		return stepId2ResourcesResponse.build();
	}

	@NonNull
	private JsonWorkOrderStepResponse toJsonWorkOrderStepResponse(@NonNull final WOProjectStep woProjectStep, @NonNull final ZoneId zoneId)
	{
		return JsonWorkOrderStepResponse.builder()
				.stepId(JsonMetasfreshId.of(woProjectStep.getWoProjectStepId().getRepoId()))
				.name(woProjectStep.getName())
				.projectId(JsonMetasfreshId.of(woProjectStep.getProjectId().getRepoId()))
				.description(woProjectStep.getDescription())
				.seqNo(woProjectStep.getSeqNo())
				.dateStart(TimeUtil.asLocalDate(woProjectStep.getDateStart(), zoneId))
				.dateEnd(TimeUtil.asLocalDate(woProjectStep.getDateEnd(), zoneId))
				.externalId(JsonMetasfreshId.ofOrNull(Integer.valueOf(ExternalId.toValue(woProjectStep.getExternalId()))))
				.woPartialReportDate(TimeUtil.asLocalDate(woProjectStep.getWoPartialReportDate(), zoneId))
				.woPlannedResourceDurationHours(woProjectStep.getWoPlannedResourceDurationHours())
				.deliveryDate(TimeUtil.asLocalDate(woProjectStep.getDeliveryDate(), zoneId))
				.woTargetStartDate(TimeUtil.asLocalDate(woProjectStep.getWoTargetStartDate(), zoneId))
				.woTargetEndDate(TimeUtil.asLocalDate(woProjectStep.getWoTargetEndDate(), zoneId))
				.woPlannedPersonDurationHours(woProjectStep.getWoPlannedPersonDurationHours())
				.woStepStatus(toJsonWOStepStatus(woProjectStep.getWoStepStatus()))
				.woFindingsReleasedDate(TimeUtil.asLocalDate(woProjectStep.getWoFindingsReleasedDate(), zoneId))
				.woFindingsCreatedDate(TimeUtil.asLocalDate(woProjectStep.getWoFindingsCreatedDate(), zoneId))
				.resources(workOrderProjectResourceRestService.getByWOStepId(woProjectStep.getWoProjectStepId(), zoneId))
				.build();
	}

	@NonNull
	private Integer getSeqNo(
			@NonNull final JsonWorkOrderStepUpsertItemRequest request, //todo fp
			@NonNull final ProjectId projectId)
	{
		if (request.isSeqNoSet())
		{
			return request.getSeqNo();
		}

		return woProjectStepRepository.getNextSeqNo(projectId);
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
		});
	}

	@NonNull
	private static JsonWorkOrderResourceUpsertRequest buildJsonWorkOrderResourceUpsertRequest(
			@NonNull final JsonWorkOrderStepUpsertItemRequest request,
			@NonNull final WOProjectStepId woProjectStepId,
			@NonNull final SyncAdvise syncAdvise)
	{
		return JsonWorkOrderResourceUpsertRequest.builder()
				.projectId(JsonMetasfreshId.of(woProjectStepId.getProjectId().getRepoId()))
				.stepId(JsonMetasfreshId.of(woProjectStepId.getRepoId()))
				.requestItems(request.getResources())
				.syncAdvise(syncAdvise)
				.build();
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
}
