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
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.money.JsonMoney;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResourceResponse;
import de.metas.common.rest_api.v2.project.budget.JsonRequestBudgetProjectResourceUpsert;
import de.metas.common.rest_api.v2.project.budget.JsonRequestBudgetProjectResourceUpsertItem;
import de.metas.common.rest_api.v2.project.budget.JsonResponseBudgetProjectResourceUpsert;
import de.metas.common.rest_api.v2.project.budget.JsonResponseBudgetProjectResourceUpsertItem;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.budget.BudgetProjectResourceRepository;
import de.metas.project.budget.CreateBudgetProjectResourceRequest;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.resource.ResourceIdentifierUtil;
import de.metas.uom.UomId;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.project.budget.BudgetProjectResourceRepository.IsAllDay_TRUE;

@Service
public class BudgetProjectResourceRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final ResourceService resourceService;
	private final BudgetProjectResourceRepository budgetProjectResourceRepository;
	private final BudgetProjectRepository budgetProjectRepository;

	public BudgetProjectResourceRestService(
			@NonNull final ResourceService resourceService,
			@NonNull final BudgetProjectResourceRepository budgetProjectResourceRepository,
			@NonNull final BudgetProjectRepository budgetProjectRepository)
	{
		this.resourceService = resourceService;
		this.budgetProjectResourceRepository = budgetProjectResourceRepository;
		this.budgetProjectRepository = budgetProjectRepository;
	}

	@NonNull
	public JsonResponseBudgetProjectResourceUpsert upsertBudgetProjectResources(@NonNull final JsonRequestBudgetProjectResourceUpsert request)
	{
		return trxManager.callInThreadInheritedTrx(() -> upsertBudgetProjectResourcesWithinTrx(request));
	}

	@NonNull
	public List<JsonBudgetProjectResourceResponse> retrieveByProjectId(@NonNull final ProjectId projectId)
	{
		return budgetProjectResourceRepository.getByProjectId(projectId)
				.getBudgets()
				.stream()
				.map(this::toJsonBudgetProjectResourceResponse)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private JsonBudgetProjectResourceResponse toJsonBudgetProjectResourceResponse(@NonNull final BudgetProjectResource projectResource)
	{
		final ZoneId timeZoneId = orgDAO.getTimeZone(projectResource.getOrgId());
		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(projectResource.getCurrencyId());

		return JsonBudgetProjectResourceResponse.builder()
				.budgetProjectResourceId(JsonMetasfreshId.of(projectResource.getId().getRepoId()))
				.projectId(JsonMetasfreshId.of(ProjectId.toRepoId(projectResource.getProjectId())))
				.currencyCode(currencyCode.toThreeLetterCode())
				.resourceId(JsonMetasfreshId.ofOrNull(ResourceId.toRepoId(projectResource.getResourceId())))
				.resourceGroupId(JsonMetasfreshId.ofOrNull(ResourceGroupId.toRepoId(projectResource.getResourceGroupId())))
				.uomTimeId(JsonMetasfreshId.of(UomId.toRepoId(projectResource.getDurationUomId())))
				.pricePerTimeUOM(projectResource.getPricePerDurationUnit().toBigDecimal())
				.plannedDuration(projectResource.getPlannedDuration().toBigDecimal())
				.plannedAmt(projectResource.getPlannedAmount().toBigDecimal())
				.externalId(ExternalId.toValue(projectResource.getExternalId()))
				.dateFinishPlan(LocalDateTime.ofInstant(projectResource.getDateRange().getEndDate(), timeZoneId).toLocalDate())
				.dateStartPlan(LocalDateTime.ofInstant(projectResource.getDateRange().getStartDate(), timeZoneId).toLocalDate())
				.description(projectResource.getDescription())
				.isActive(projectResource.getIsActive())
				.build();
	}

	@NonNull
	private JsonResponseBudgetProjectResourceUpsert upsertBudgetProjectResourcesWithinTrx(@NonNull final JsonRequestBudgetProjectResourceUpsert request)
	{
		if (Check.isEmpty(request.getRequestItems()))
		{
			return JsonResponseBudgetProjectResourceUpsert.builder()
					.responseItems(ImmutableList.of())
					.build();
		}

		validateJsonRequestBudgetProjectResourceUpsert(request);

		final SyncAdvise syncAdvise = request.getSyncAdvise();
		final ProjectId projectId = ProjectId.ofRepoId(JsonMetasfreshId.toValueInt(request.getProjectId()));

		final BudgetProject budgetProject = budgetProjectRepository.getOptionalById(projectId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("Budget Project")
						.parentResource(request)
						.build());

		final Map<IdentifierString, ResourceId> identifier2ResourceId = resolveResourceIdentifiers(request, budgetProject);

		final Map<ResourceId, BudgetProjectResource> resourceId2ExistingResource = budgetProjectResourceRepository
				.getByProjectIdAndResourceIds(projectId, identifier2ResourceId.values());

		final List<JsonResponseBudgetProjectResourceUpsertItem> responseList = upsertBudgetProjectResourceItems(budgetProject,
																												request.getRequestItems(),
																												syncAdvise,
																												identifier2ResourceId,
																												resourceId2ExistingResource);

		return JsonResponseBudgetProjectResourceUpsert.builder()
				.responseItems(responseList)
				.build();
	}

	@NonNull
	private Map<IdentifierString, ResourceId> resolveResourceIdentifiers(
			@NonNull final JsonRequestBudgetProjectResourceUpsert request,
			@NonNull final BudgetProject budgetProject)
	{
		return request.getRequestItems()
				.stream()
				.map(JsonRequestBudgetProjectResourceUpsertItem::getResourceIdentifier)
				.map(IdentifierString::of)
				.collect(Collectors.toMap(Function.identity(),
										  identifierString ->  ResourceIdentifierUtil.resolveResourceIdentifier(budgetProject.getOrgId(), identifierString, resourceService)));
	}

	@NonNull
	private List<JsonResponseBudgetProjectResourceUpsertItem> upsertBudgetProjectResourceItems(
			@NonNull final BudgetProject budgetProject,
			@NonNull final List<JsonRequestBudgetProjectResourceUpsertItem> jsonRequestBudgetProjectResourceUpsertItems,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final Map<IdentifierString, ResourceId> identifier2ResourceId,
			@NonNull final Map<ResourceId, BudgetProjectResource> resourceId2ExistingResource)
	{
		final ImmutableList.Builder<BudgetProjectResource> resourcesToUpdate = ImmutableList.builder();
		final ImmutableList.Builder<CreateBudgetProjectResourceRequest> resourcesToCreate = ImmutableList.builder();
		final ImmutableList.Builder<JsonResponseBudgetProjectResourceUpsertItem> responseItems = ImmutableList.builder();

		for (final JsonRequestBudgetProjectResourceUpsertItem jsonRequestBudgetProjectResourceUpsertItem : jsonRequestBudgetProjectResourceUpsertItems)
		{
			final ResourceId resourceId = identifier2ResourceId.get(IdentifierString.of(jsonRequestBudgetProjectResourceUpsertItem.getResourceIdentifier()));

			final Optional<BudgetProjectResource> existingBudgetResource = Optional.ofNullable(resourceId2ExistingResource.get(resourceId));

			if (existingBudgetResource.isPresent() && !syncAdvise.getIfExists().isUpdate())
			{
				final JsonMetasfreshId budgetProjectResourceId = JsonMetasfreshId.of(BudgetProjectResourceId.toRepoId(existingBudgetResource.get().getId()));

				responseItems.add(JsonResponseBudgetProjectResourceUpsertItem.builder()
										  .metasfreshId(budgetProjectResourceId)
										  .syncOutcome(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE)
										  .build());
			}
			else if (existingBudgetResource.isPresent() || !syncAdvise.isFailIfNotExists())
			{
				final BudgetProjectResource existingBudgetProjectResource = existingBudgetResource.orElse(null);

				if (existingBudgetProjectResource != null)
				{
					resourcesToUpdate.add(syncBudgetProjectResourceWithJson(jsonRequestBudgetProjectResourceUpsertItem, existingBudgetProjectResource, budgetProject));
				}
				else
				{
					resourcesToCreate.add(buildCreateBudgetProjectResourceRequest(jsonRequestBudgetProjectResourceUpsertItem, budgetProject));
				}
			}
			else
			{
				throw MissingResourceException.builder()
						.resourceName("Budget Project Resource")
						.parentResource(jsonRequestBudgetProjectResourceUpsertItem)
						.build()
						.setParameter("budgetProjectResourceSyncAdvise", syncAdvise);
			}

			budgetProjectResourceRepository.updateAll(resourcesToUpdate.build())
					.stream()
					.map(BudgetProjectResource::getId)
					.map(updatedResourceId -> JsonResponseBudgetProjectResourceUpsertItem.builder()
							.metasfreshId(JsonMetasfreshId.of(updatedResourceId.getRepoId()))
							.syncOutcome(JsonResponseUpsertItem.SyncOutcome.UPDATED)
							.build())
					.forEach(responseItems::add);

			budgetProjectResourceRepository.createAll(resourcesToCreate.build())
					.stream()
					.map(BudgetProjectResource::getId)
					.map(createdResourceId -> JsonResponseBudgetProjectResourceUpsertItem.builder()
							.metasfreshId(JsonMetasfreshId.of(createdResourceId.getRepoId()))
							.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
							.build())
					.forEach(responseItems::add);
		}

		return responseItems.build();
	}

	@NonNull
	private CreateBudgetProjectResourceRequest buildCreateBudgetProjectResourceRequest(
			@NonNull final JsonRequestBudgetProjectResourceUpsertItem request,
			@NonNull final BudgetProject budgetProject)
	{
		final OrgId orgId = budgetProject.getOrgId();
		final ProjectId projectId = budgetProject.getProjectId();
		final CurrencyId budgetProjectCurrencyId = budgetProject.getCurrencyId();

		final ResourceId resourceId = ResourceIdentifierUtil.resolveResourceIdentifier(orgId, IdentifierString.of(request.getResourceIdentifier()), resourceService);
		final Money plannedAmount = getPlannedAmount(request, null, budgetProjectCurrencyId);
		final UomId durationUOMId = getDurationUOMId(request, null);
		final Money pricePerDurationUnit = getPricePerDurationUnit(request, budgetProjectCurrencyId, null);
		final Quantity plannedDuration = getPlannedDuration(request, durationUOMId, null);
		final Instant dateStartPlan = getDateStartPlan(request, orgId, null);
		final Instant dateFinishPlan = getDateFinishPlan(request, orgId, null);

		return CreateBudgetProjectResourceRequest.builder()
				.resourceId(resourceId)
				.projectId(projectId)
				.orgId(orgId)
				.plannedAmount(plannedAmount)
				.durationUomId(durationUOMId)
				.pricePerDurationUnit(pricePerDurationUnit)
				.plannedDuration(plannedDuration)
				.dateRange(CalendarDateRange.builder()
								   .startDate(dateStartPlan)
								   .endDate(dateFinishPlan)
								   .allDay(IsAllDay_TRUE)
								   .build())
				.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(request.getExternalId())))
				.description(request.getDescription())
				.isActive(request.getActive())
				.resourceGroupId(ResourceGroupId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getResourceGroupId())))
				.build();
	}

	@NonNull
	private BudgetProjectResource syncBudgetProjectResourceWithJson(
			@NonNull final JsonRequestBudgetProjectResourceUpsertItem request,
			@NonNull final BudgetProjectResource existingBudgetProjectResource,
			@NonNull final BudgetProject budgetProject)
	{
		final OrgId orgId = budgetProject.getOrgId();
		final CurrencyId budgetProjectCurrencyId = budgetProject.getCurrencyId();

		final Money plannedAmount = getPlannedAmount(request, existingBudgetProjectResource, budgetProjectCurrencyId);
		final UomId durationUOMId = getDurationUOMId(request, existingBudgetProjectResource);
		final Money pricePerDurationUnit = getPricePerDurationUnit(request, budgetProjectCurrencyId, existingBudgetProjectResource);
		final Quantity plannedDuration = getPlannedDuration(request, durationUOMId, existingBudgetProjectResource);
		final Instant dateStartPlan = getDateStartPlan(request, orgId, existingBudgetProjectResource);
		final Instant dateFinishPlan = getDateFinishPlan(request, orgId, existingBudgetProjectResource);

		final BudgetProjectResource.BudgetProjectResourceBuilder budgetProjectResourceBuilder = BudgetProjectResource.builder()
				.id(existingBudgetProjectResource.getId())
				.orgId(orgId)
				.resourceId(existingBudgetProjectResource.getResourceId())
				.plannedAmount(plannedAmount)
				.durationUomId(durationUOMId)
				.pricePerDurationUnit(pricePerDurationUnit)
				.plannedDuration(plannedDuration)
				.dateRange(CalendarDateRange.builder()
								   .startDate(dateStartPlan)
								   .endDate(dateFinishPlan)
								   .allDay(IsAllDay_TRUE)
								   .build());

		if (request.isExternalIdSet())
		{
			budgetProjectResourceBuilder.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(request.getExternalId())));
		}
		else
		{
			budgetProjectResourceBuilder.externalId(existingBudgetProjectResource.getExternalId());
		}

		if (request.isDescriptionSet())
		{
			budgetProjectResourceBuilder.description(request.getDescription());
		}
		else
		{
			budgetProjectResourceBuilder.description(existingBudgetProjectResource.getDescription());
		}

		if (request.isActiveSet())
		{
			budgetProjectResourceBuilder.isActive(request.getActive());
		}
		else
		{
			budgetProjectResourceBuilder.isActive(existingBudgetProjectResource.getIsActive());
		}

		if (request.isResourceGroupIdSet())
		{
			budgetProjectResourceBuilder.resourceGroupId(ResourceGroupId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getResourceGroupId())));
		}
		else
		{
			budgetProjectResourceBuilder.resourceGroupId(existingBudgetProjectResource.getResourceGroupId());
		}

		return budgetProjectResourceBuilder.build();
	}

	@NonNull
	private Instant getDateFinishPlan(
			@NonNull final JsonRequestBudgetProjectResourceUpsertItem request,
			@NonNull final OrgId orgId,
			@Nullable final BudgetProjectResource existingBudgetProjectResource)
	{
		final LocalDate dateFinishPlanAsLocalDate = request.getDateFinishPlan();

		if (request.isDateFinishPlanSet() && dateFinishPlanAsLocalDate == null)
		{
			throw new MissingPropertyException("dateFinishPlan", request);
		}
		else if (request.isDateFinishPlanSet())
		{
			return TimeUtil.asEndOfDayInstant(request.getDateFinishPlan(), orgId);
		}
		else if (existingBudgetProjectResource != null)
		{
			return existingBudgetProjectResource.getDateRange().getEndDate();
		}
		else
		{
			throw new MissingPropertyException("DateFinishPlan", request);
		}
	}

	@NonNull
	private Instant getDateStartPlan(
			@NonNull final JsonRequestBudgetProjectResourceUpsertItem request,
			@NonNull final OrgId orgId,
			@Nullable final BudgetProjectResource existingBudgetProjectResource)
	{
		final LocalDate dateStartPlanAsLocalDate = request.getDateStartPlan();

		if (request.isDateStartPlanSet() && dateStartPlanAsLocalDate == null)
		{
			throw new MissingPropertyException("dateStartPlan", request);
		}
		else if (request.isDateStartPlanSet())
		{
			return TimeUtil.asInstant(dateStartPlanAsLocalDate, orgId);
		}
		else if (existingBudgetProjectResource != null)
		{
			return existingBudgetProjectResource.getDateRange().getStartDate();
		}
		else
		{
			throw new MissingPropertyException("dateStartPlan", request);
		}
	}

	@NonNull
	private Quantity getPlannedDuration(
			@NonNull final JsonRequestBudgetProjectResourceUpsertItem request,
			@NonNull final UomId durationUOMId,
			@Nullable final BudgetProjectResource existingBudgetProjectResource)
	{
		final BigDecimal plannedDuration = request.getPlannedDuration();

		if (request.isPlannedDurationSet() && plannedDuration == null)
		{
			return Quantitys.zero(durationUOMId);
		}
		else if (request.isPlannedDurationSet())
		{
			return Quantitys.of(plannedDuration, durationUOMId);
		}
		else if (existingBudgetProjectResource != null)
		{
			Check.assumeEquals(existingBudgetProjectResource.getPlannedDuration().getUomId(), durationUOMId);

			return existingBudgetProjectResource.getPlannedDuration();
		}
		else
		{
			return Quantitys.zero(durationUOMId);
		}
	}

	@NonNull
	private Money getPricePerDurationUnit(
			@NonNull final JsonRequestBudgetProjectResourceUpsertItem request,
			@NonNull final CurrencyId currencyId,
			@Nullable final BudgetProjectResource existingBudgetProjectResource)
	{
		final JsonMoney pricePerTimeUOM = request.getPricePerTimeUOM();

		if (request.isPricePerTimeUOMSet() && pricePerTimeUOM == null)
		{
			return Money.zero(currencyId);
		}
		else if (request.isPricePerTimeUOMSet())
		{
			assertCurrencyIdsMatch(currencyId, request.getPricePerTimeUOM().getCurrencyCode());

			return Money.of(request.getPricePerTimeUOM().getAmount(), currencyId);
		}
		else if (existingBudgetProjectResource != null)
		{
			Check.assumeEquals(existingBudgetProjectResource.getPricePerDurationUnit().getCurrencyId(), currencyId, "BudgetResource currency and project currency");

			return existingBudgetProjectResource.getPricePerDurationUnit();
		}
		else
		{
			return Money.zero(currencyId);
		}
	}

	@NonNull
	private UomId getDurationUOMId(
			@NonNull final JsonRequestBudgetProjectResourceUpsertItem request,
			@Nullable final BudgetProjectResource existingBudgetProjectResource)
	{
		final JsonMetasfreshId timeUOMId = request.getUomTimeId();

		if (request.isUomTimeIdSet() && timeUOMId == null)
		{
			throw new MissingPropertyException("uomTimeId", request);
		}
		else if (request.isUomTimeIdSet())
		{
			return UomId.ofRepoId(JsonMetasfreshId.toValueInt(timeUOMId));
		}
		else if (existingBudgetProjectResource != null)
		{
			return existingBudgetProjectResource.getDurationUomId();
		}
		else
		{
			throw new MissingPropertyException("uomTimeId", request);
		}
	}

	@NonNull
	private Money getPlannedAmount(
			@NonNull final JsonRequestBudgetProjectResourceUpsertItem request,
			@Nullable final BudgetProjectResource existingBudgetProjectResource,
			@NonNull final CurrencyId currencyId)
	{
		if (request.isPlannedAmtSet())
		{
			if (request.getPlannedAmt() == null)
			{
				return Money.zero(currencyId);
			}

			assertCurrencyIdsMatch(currencyId, request.getPlannedAmt().getCurrencyCode());

			return Money.of(request.getPlannedAmt().getAmount(), currencyId);
		}
		else if (existingBudgetProjectResource != null)
		{
			Check.assume(currencyId.equals(existingBudgetProjectResource.getPlannedAmount().getCurrencyId()), "Existing resource currency must match the project currency!");

			return existingBudgetProjectResource.getPlannedAmount();
		}
		else
		{
			return Money.zero(currencyId);
		}
	}

	private void assertCurrencyIdsMatch(
			@NonNull final CurrencyId currencyId,
			@NonNull final String currencyCodeCandidate)
	{
		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(currencyId);

		if (!currencyCode.toThreeLetterCode().equals(currencyCodeCandidate))
		{
			throw new AdempiereException("Currency code of the budget project resource does not match the currency of the budget project!")
					.appendParametersToMessage()
					.setParameter("Project.CurrencyCode", currencyCode.toThreeLetterCode())
					.setParameter("ProjectResource.CurrencyCode", currencyCodeCandidate);
		}
	}

	private static void validateJsonRequestBudgetProjectResourceUpsert(@NonNull final JsonRequestBudgetProjectResourceUpsert request)
	{
		request.getRequestItems().forEach(requestItem -> {
			if (Check.isBlank(requestItem.getResourceIdentifier()))
			{
				throw new MissingPropertyException("resourceIdentifier", requestItem);
			}
		});
	}
}
