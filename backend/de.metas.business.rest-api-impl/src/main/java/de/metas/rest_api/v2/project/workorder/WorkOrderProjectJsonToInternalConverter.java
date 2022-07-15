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

import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.user.UserId;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class WorkOrderProjectJsonToInternalConverter
{
	private final ResourceService resourceService;

	public WorkOrderProjectJsonToInternalConverter(@NonNull final ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	@NonNull
	public WOProject updateWOProjectFromJson(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@Nullable final WOProject existingWOProject)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(request.getOrgCode());

		final WOProject.WOProjectBuilder updatedWOProjectBuilder = WOProject.builder()
				.projectTypeId(ProjectTypeId.ofRepoId(request.getProjectTypeId().getValue()))
				.orgId(orgId);
		if (existingWOProject != null)
		{
			updatedWOProjectBuilder.projectId(existingWOProject.getProjectIdNonNull());
		}

		if (request.isPriceListVersionIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId())));
		}
		else
		{
			updatedWOProjectBuilder.priceListVersionId(existingWOProject.getPriceListVersionId());
		}

		if (request.isCurrencyIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.currencyId(CurrencyId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getCurrencyId())));
		}
		else
		{
			updatedWOProjectBuilder.currencyId(existingWOProject.getCurrencyId());
		}

		if (request.isSalesRepIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.salesRepId(UserId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getSalesRepId())));
		}
		else
		{
			updatedWOProjectBuilder.salesRepId(existingWOProject.getSalesRepId());
		}

		if (request.isProjectReferenceExtSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.projectReferenceExt(request.getProjectReferenceExt());
		}
		else
		{
			updatedWOProjectBuilder.projectReferenceExt(existingWOProject.getProjectReferenceExt());
		}

		if (request.isProjectParentIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getProjectParentId())));
		}
		else
		{
			updatedWOProjectBuilder.projectParentId(existingWOProject.getProjectParentId());
		}

		if (request.isBusinessPartnerIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getBusinessPartnerId())));
		}
		else
		{
			updatedWOProjectBuilder.bPartnerId(existingWOProject.getBPartnerId());
		}

		if (request.isCurrencyIdSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.currencyId(CurrencyId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getCurrencyId())));
		}
		else
		{
			updatedWOProjectBuilder.currencyId(existingWOProject.getCurrencyId());
		}

		if (request.isNameSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.name(request.getName());
		}
		else
		{
			updatedWOProjectBuilder.name(existingWOProject.getName());
		}

		if (request.isValueSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.value(request.getValue());
		}
		else
		{
			updatedWOProjectBuilder.value(existingWOProject.getValue());
		}

		if (request.isDateContractSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.dateContract(request.getDateContract());
		}
		else
		{
			updatedWOProjectBuilder.dateContract(existingWOProject.getDateContract());
		}

		if (request.isDateFinishSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.dateFinish(request.getDateFinish());
		}
		else
		{
			updatedWOProjectBuilder.dateFinish(existingWOProject.getDateFinish());
		}

		if (request.isDescriptionSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.description(request.getDescription());
		}
		else
		{
			updatedWOProjectBuilder.description(existingWOProject.getDescription());
		}

		if (request.isActiveSet() || existingWOProject == null)
		{
			updatedWOProjectBuilder.isActive(CoalesceUtil.coalesceNotNull(request.getIsActive(), true));
		}
		else
		{
			updatedWOProjectBuilder.isActive(existingWOProject.getIsActive());
		}

		final Map<JsonExternalId, JsonWorkOrderStepUpsertRequest> jsonProjectSteps = request.getSteps().stream()
				.collect(Collectors.toMap(JsonWorkOrderStepUpsertRequest::getExternalId, Function.identity()));

		if (existingWOProject != null)
		{
			for (final WOProjectStep existingProjectStep : existingWOProject.getProjectSteps())
			{
				if (existingProjectStep.getExternalId() == null)
				{
					continue; // can't match a step that has no external ID
				}
				final JsonExternalId existingJsonExtenalId = JsonExternalId.of(existingProjectStep.getExternalId().getValue());
				final JsonWorkOrderStepUpsertRequest matchedJsonProjectStep = jsonProjectSteps.remove(existingJsonExtenalId);
				if (matchedJsonProjectStep == null)
				{
					continue;
				}
				final WOProjectStep updatedWOProjectStep = updateWOProjectStepFromJson(
						orgId,
						matchedJsonProjectStep,
						existingProjectStep);
				updatedWOProjectBuilder.projectStep(updatedWOProjectStep);
			}
		}
		for (final JsonWorkOrderStepUpsertRequest remainingJsonProjectStep : jsonProjectSteps.values())
		{
			final WOProjectStep newWOProjectStep = updateWOProjectStepFromJson(
					orgId,
					remainingJsonProjectStep,
					null);
			updatedWOProjectBuilder.projectStep(newWOProjectStep);
		}

		return updatedWOProjectBuilder.build();
	}

	@NonNull
	private WOProjectStep updateWOProjectStepFromJson(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderStepUpsertRequest request,
			@Nullable final WOProjectStep woProjectStepToUpdate)
	{
		final WOProjectStep.WOProjectStepBuilder updatedWOProjectStepBuilder = WOProjectStep.builder()
				.name(request.getName());
		if (woProjectStepToUpdate != null)
		{
			updatedWOProjectStepBuilder.name(request.getName());
		}

		if (request.isSeqNoSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.seqNo(request.getSeqNo());
		}
		else
		{
			updatedWOProjectStepBuilder.seqNo(woProjectStepToUpdate.getSeqNo());
		}

		if (request.isDateStartSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.dateStart(TimeUtil.asInstant(request.getDateStart(), orgId));
		}
		else
		{
			updatedWOProjectStepBuilder.dateStart(woProjectStepToUpdate.getDateStart());
		}

		if (request.isDateEndSet() || woProjectStepToUpdate == null)
		{
			final Instant dateEnd = TimeUtil.asEndOfDayInstant(request.getDateEnd(), orgId);
			updatedWOProjectStepBuilder.dateEnd(dateEnd);
		}
		else
		{
			updatedWOProjectStepBuilder.dateEnd(woProjectStepToUpdate.getDateEnd());
		}

		if (request.isDescriptionSet() || woProjectStepToUpdate == null)
		{
			updatedWOProjectStepBuilder.description(request.getDescription());
		}
		else
		{
			updatedWOProjectStepBuilder.description(woProjectStepToUpdate.getDescription());
		}

		final Map<JsonExternalId, JsonWorkOrderResourceUpsertRequest> jsonProjectResources = request.getResourceRequests().stream()
				.collect(Collectors.toMap(JsonWorkOrderResourceUpsertRequest::getExternalId, Function.identity()));

		if (woProjectStepToUpdate != null)
		{
			for (final WOProjectResource existingProjectResource : woProjectStepToUpdate.getProjectResources())
			{
				if (existingProjectResource.getExternalId() == null)
				{
					continue; // can't match a resource that has no external ID
				}
				final JsonExternalId existingJsonExtenalId = JsonExternalId.of(existingProjectResource.getExternalId().getValue());
				final WOProjectResource updatedResource = updateWOProjectResourceFromJson(
						orgId,
						jsonProjectResources.remove(existingJsonExtenalId),
						existingProjectResource);
				updatedWOProjectStepBuilder.projectResource(updatedResource);
			}
		}

		for (final JsonWorkOrderResourceUpsertRequest remainingJsonProjectResource : jsonProjectResources.values())
		{
			final ResourceId resourceId = extractResourceId(orgId, remainingJsonProjectResource);

			final Instant assignDateFrom = TimeUtil.asInstant(remainingJsonProjectResource.getAssignDateFrom(), orgId);
			final Instant assignDateTo = TimeUtil.asEndOfDayInstant(remainingJsonProjectResource.getAssignDateTo(), orgId);

			final WOProjectResource projectResource = WOProjectResource.builder()
					.resourceId(resourceId)
					.isActive(remainingJsonProjectResource.getIsActive())
					.isAllDay(remainingJsonProjectResource.getIsAllDay())
					.assignDateFrom(assignDateFrom)
					.assignDateTo(assignDateTo)
					.duration(remainingJsonProjectResource.getDuration())
					.durationUnit(remainingJsonProjectResource.getDurationUnit())
					.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(remainingJsonProjectResource.getExternalId())))
					.build();

			updatedWOProjectStepBuilder.projectResource(projectResource);
		}

		return updatedWOProjectStepBuilder.build();
	}

	@NonNull
	private WOProjectResource updateWOProjectResourceFromJson(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertRequest request,
			@NonNull final WOProjectResource existingWOProjectResource)
	{
		final Instant assignDateFrom = TimeUtil.asInstant(request.getAssignDateFrom(), orgId);
		final Instant assignDateTo = TimeUtil.asEndOfDayInstant(request.getAssignDateTo(), orgId);

		final WOProjectResource.WOProjectResourceBuilder updatedWOProjectResourceBuilder = WOProjectResource.builder()
				.externalId(ExternalId.of(request.getExternalId().getValue()))
				.woProjectResourceId(existingWOProjectResource.getWOProjectResourceIdNotNull())
				.budgetProjectId(existingWOProjectResource.getBudgetProjectId())
				.projectResourceBudgetId(existingWOProjectResource.getProjectResourceBudgetId())
				.duration(existingWOProjectResource.getDuration())
				.durationUnit(existingWOProjectResource.getDurationUnit())
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo);

		if (request.isResourceIdentifierSet())
		{
			final ResourceId resourceId = extractResourceId(orgId, request);
			updatedWOProjectResourceBuilder.resourceId(resourceId);
		}
		else
		{
			updatedWOProjectResourceBuilder.resourceId(existingWOProjectResource.getResourceId());
		}

		if (request.isActiveSet())
		{
			updatedWOProjectResourceBuilder.isActive(request.getIsActive());
		}
		else
		{
			updatedWOProjectResourceBuilder.isActive(existingWOProjectResource.getIsActive());
		}

		if (request.isAllDaySet())
		{
			updatedWOProjectResourceBuilder.isAllDay(request.getIsAllDay());
		}
		else
		{
			updatedWOProjectResourceBuilder.isAllDay(existingWOProjectResource.getIsAllDay());
		}

		return updatedWOProjectResourceBuilder.build();
	}

	private ResourceId extractResourceId(
			@NonNull final OrgId orgId,
			@NonNull final JsonWorkOrderResourceUpsertRequest request
	)
	{
		final IdentifierString resourceIdentifier = IdentifierString.of(request.getResourceIdentifier());

		final Predicate<Resource> resourcePredicate;
		switch (resourceIdentifier.getType())
		{
			case METASFRESH_ID:
				resourcePredicate = r -> ResourceId.toRepoId(r.getResourceId()) == resourceIdentifier.asMetasfreshId().getValue();
				break;
			case VALUE:
				resourcePredicate = r -> // make sure that org and value match
						(r.getOrgId().isAny() || orgId.isAny() || Objects.equals(r.getOrgId(), orgId))
								&& Objects.equals(r.getValue(), resourceIdentifier.asValue());
				break;
			case INTERNALNAME:
				resourcePredicate = r -> // make sure that org and value match
						(r.getOrgId().isAny() || orgId.isAny() || Objects.equals(r.getOrgId(), orgId))
								&& Objects.equals(r.getInternalName(), resourceIdentifier.asInternalName());
				break;
			default:
				throw new InvalidIdentifierException(resourceIdentifier.getRawIdentifierString(), request);
		}

		return resourceService.getAllActiveResources()
				.stream()
				.filter(resourcePredicate)
				.findAny()
				.map(Resource::getResourceId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("resourceIdentifier")
						.resourceIdentifier(request.getResourceIdentifier())
						.parentResource(request)
						.build());
	}
}
