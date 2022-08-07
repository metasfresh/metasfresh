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
import de.metas.bpartner.BPartnerId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.money.JsonMoney;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetResourceUpsertRequest;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectData;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceData;
import de.metas.project.budget.UpsertBudgetProjectRequest;
import de.metas.project.budget.UpsertBudgetProjectResourceRequest;
import de.metas.project.shared.ProjectSharedService;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.resource.ResourceGroupId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.resource.ResourceRestService;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static de.metas.project.budget.BudgetProjectResourceRepository.IsAllDay_TRUE;

@Service
public class BudgetProjectJsonConverter
{
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	private final ResourceRestService resourceRestService;
	private final ProjectSharedService projectSharedService;

	public BudgetProjectJsonConverter(
			@NonNull final ResourceRestService resourceRestService,
			@NonNull final ProjectSharedService projectSharedService)
	{
		this.resourceRestService = resourceRestService;
		this.projectSharedService = projectSharedService;
	}

	@NonNull
	public UpsertBudgetProjectRequest buildUpsertBudgetProjectRequest(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@Nullable final BudgetProject existingBudgetProject,
			@NonNull final OrgId orgId,
			@NonNull final SyncAdvise syncAdvise)
	{
		final BudgetProjectData existingBudgetData = Optional.ofNullable(existingBudgetProject)
				.map(BudgetProject::getBudgetProjectData)
				.orElse(null);

		final BudgetProjectData updatedProjectData = buildBudgetProjectData(request, existingBudgetData, orgId);

		final List<UpsertBudgetProjectResourceRequest> resourceRequestList = getUpsertBudgetResourceRequests(existingBudgetProject,
																											 request.getResources(),
																											 syncAdvise,
																											 updatedProjectData);

		return UpsertBudgetProjectRequest.builder()
				.projectId(existingBudgetProject != null ? existingBudgetProject.getProjectId() : null)
				.budgetProjectData(updatedProjectData)
				.projectResourceRequests(resourceRequestList)
				.build();
	}

	@NonNull
	private UpsertBudgetProjectResourceRequest buildUpsertBudgetProjectResourceRequest(
			@NonNull final ResourceId resourceId,
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyId,
			@NonNull final JsonBudgetResourceUpsertRequest request,
			@Nullable final BudgetProjectResource existingProjectResource)
	{
		final UpsertBudgetProjectResourceRequest.UpsertBudgetProjectResourceRequestBuilder budgetProjectResourceRequestBuilder = UpsertBudgetProjectResourceRequest.builder();

		final BudgetProjectResourceData existingBudgetProjectResourceData;
		if (existingProjectResource != null)
		{
			budgetProjectResourceRequestBuilder.id(existingProjectResource.getId());
			existingBudgetProjectResourceData = existingProjectResource.getBudgetProjectResourceData();
		}
		else
		{
			existingBudgetProjectResourceData = null;
		}

		final Money plannedAmount = getPlannedAmount(request, currencyId, existingBudgetProjectResourceData);
		final UomId durationUOMId = getDurationUOMId(request, existingBudgetProjectResourceData);
		final Money pricePerDurationUnit = getPricePerDurationUnit(request, currencyId, existingBudgetProjectResourceData);
		final Quantity plannedDuration = getPlannedDuration(request, durationUOMId, existingBudgetProjectResourceData);
		final Instant dateStartPlan = getDateStartPlan(request, orgId, existingBudgetProjectResourceData);
		final Instant dateFinishPlan = getDateFinishPlan(request, orgId, existingBudgetProjectResourceData);
		final BudgetProjectResourceData.BudgetProjectResourceDataBuilder updatedBudgetProjectResourceDataBuilder = BudgetProjectResourceData.builder()
				.resourceId(resourceId)
				.plannedAmount(plannedAmount)
				.durationUomId(durationUOMId)
				.pricePerDurationUnit(pricePerDurationUnit)
				.plannedDuration(plannedDuration)
				.dateRange(CalendarDateRange.builder()
								   .startDate(dateStartPlan)
								   .endDate(dateFinishPlan)
								   .allDay(IsAllDay_TRUE)
								   .build());

		if (request.isExternalIdSet() || existingBudgetProjectResourceData == null)
		{
			updatedBudgetProjectResourceDataBuilder.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(request.getExternalId())));
		}
		else
		{
			updatedBudgetProjectResourceDataBuilder.externalId(existingBudgetProjectResourceData.getExternalId());
		}

		if (request.isDescriptionSet() || existingBudgetProjectResourceData == null)
		{
			updatedBudgetProjectResourceDataBuilder.description(request.getDescription());
		}
		else
		{
			updatedBudgetProjectResourceDataBuilder.description(existingBudgetProjectResourceData.getDescription());
		}

		if (request.isActiveSet() || existingBudgetProjectResourceData == null)
		{
			updatedBudgetProjectResourceDataBuilder.isActive(request.getActive());
		}
		else
		{
			updatedBudgetProjectResourceDataBuilder.isActive(existingBudgetProjectResourceData.getIsActive());
		}

		if (request.isResourceGroupIdSet() || existingBudgetProjectResourceData == null)
		{
			updatedBudgetProjectResourceDataBuilder.resourceGroupId(ResourceGroupId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getResourceGroupId())));
		}
		else
		{
			updatedBudgetProjectResourceDataBuilder.resourceGroupId(existingBudgetProjectResourceData.getResourceGroupId());
		}

		return budgetProjectResourceRequestBuilder
				.budgetProjectResourceData(updatedBudgetProjectResourceDataBuilder.build())
				.build();
	}

	@NonNull
	private UomId getDurationUOMId(
			@NonNull final JsonBudgetResourceUpsertRequest request,
			@Nullable final BudgetProjectResourceData existingBudgetProjectResourceData)
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
		else if (existingBudgetProjectResourceData != null)
		{
			return existingBudgetProjectResourceData.getDurationUomId();
		}
		else
		{
			throw new MissingPropertyException("uomTimeId", request);
		}
	}

	@NonNull
	private Money getPricePerDurationUnit(
			@NonNull final JsonBudgetResourceUpsertRequest request,
			@NonNull final CurrencyId currencyId,
			@Nullable final BudgetProjectResourceData existingBudgetProjectResourceData)
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
		else if (existingBudgetProjectResourceData != null)
		{
			Check.assumeEquals(existingBudgetProjectResourceData.getPricePerDurationUnit().getCurrencyId(), currencyId, "BudgetResource currency and project currency");

			return existingBudgetProjectResourceData.getPricePerDurationUnit();
		}
		else
		{
			return Money.zero(currencyId);
		}
	}

	@NonNull
	private Instant getDateStartPlan(
			@NonNull final JsonBudgetResourceUpsertRequest request,
			@NonNull final OrgId orgId,
			@Nullable final BudgetProjectResourceData existingBudgetProjectResourceData)
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
		else if (existingBudgetProjectResourceData != null)
		{
			return existingBudgetProjectResourceData.getDateRange().getStartDate();
		}
		else
		{
			throw new MissingPropertyException("dateStartPlan", request);
		}
	}

	@NonNull
	private Instant getDateFinishPlan(
			@NonNull final JsonBudgetResourceUpsertRequest request,
			@NonNull final OrgId orgId,
			@Nullable final BudgetProjectResourceData existingBudgetProjectResourceData)
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
		else if (existingBudgetProjectResourceData != null)
		{
			return existingBudgetProjectResourceData.getDateRange().getEndDate();
		}
		else
		{
			throw new MissingPropertyException("DateFinishPlan", request);
		}
	}

	@NonNull
	private Quantity getPlannedDuration(
			@NonNull final JsonBudgetResourceUpsertRequest request,
			@NonNull final UomId durationUOMId,
			@Nullable final BudgetProjectResourceData existingBudgetProjectResourceData)
	{
		final BigDecimal plannedDuration = request.getPlannedDuration();

		if (request.isPlannedDurationSet() && plannedDuration == null)
		{
			return Quantitys.createZero(durationUOMId);
		}
		else if (request.isPlannedDurationSet())
		{
			return Quantitys.create(plannedDuration, durationUOMId);
		}
		else if (existingBudgetProjectResourceData != null)
		{
			Check.assumeEquals(existingBudgetProjectResourceData.getPlannedDuration().getUomId(), durationUOMId);

			return existingBudgetProjectResourceData.getPlannedDuration();
		}
		else
		{
			return Quantitys.createZero(durationUOMId);
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

	@NonNull
	private Money getPlannedAmount(
			@NonNull final JsonBudgetResourceUpsertRequest request,
			@NonNull final CurrencyId currencyId,
			@Nullable final BudgetProjectResourceData existingBudgetProjectResourceData)
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
		else if (existingBudgetProjectResourceData != null)
		{
			Check.assume(currencyId.equals(existingBudgetProjectResourceData.getPlannedAmount().getCurrencyId()), "Existing resource currency must match the project currency!");

			return existingBudgetProjectResourceData.getPlannedAmount();
		}
		else
		{
			return Money.zero(currencyId);
		}
	}

	@NonNull
	private List<UpsertBudgetProjectResourceRequest> getUpsertBudgetResourceRequests(
			@Nullable final BudgetProject existingBudgetProject,
			@NonNull final List<JsonBudgetResourceUpsertRequest> resourceUpsertRequestList,
			@NonNull final SyncAdvise budgetProjectSyncAdvise,
			@NonNull final BudgetProjectData updatedProjectData)
	{
		Check.assume(existingBudgetProject == null || budgetProjectSyncAdvise.getIfExists().isUpdate(), "SyncAdvise should allow updates at this point!");

		final OrgId orgId = updatedProjectData.getOrgId();
		final CurrencyId currencyId = updatedProjectData.getCurrencyId();

		final ImmutableList.Builder<UpsertBudgetProjectResourceRequest> upsertRequests = ImmutableList.builder();

		for (final JsonBudgetResourceUpsertRequest request : resourceUpsertRequestList)
		{
			final ResourceId resourceId = resourceRestService.resolveResourceIdentifier(orgId, IdentifierString.of(request.getResourceIdentifier()));

			final BudgetProjectResource existingBudgetResource = Optional.ofNullable(existingBudgetProject)
					.flatMap(budgetProject -> budgetProject.findBudgetResource(resourceId))
					.orElse(null);

			if (existingBudgetResource != null || budgetProjectSyncAdvise.getIfNotExists().isCreate())
			{
				upsertRequests.add(buildUpsertBudgetProjectResourceRequest(resourceId,
																		   orgId,
																		   currencyId,
																		   request,
																		   existingBudgetResource));
			}
			else
			{
				throw MissingResourceException.builder()
						.resourceName("Budget Project Resource")
						.parentResource(request)
						.build()
						.setParameter("budgetProjectSyncAdvise", budgetProjectSyncAdvise);
			}
		}

		return upsertRequests.build();
	}

	@NonNull
	private BudgetProjectData buildBudgetProjectData(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@Nullable final BudgetProjectData existingBudgetProjectData,
			@NonNull final OrgId orgId)
	{
		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoId(JsonMetasfreshId.toValueInt(request.getProjectTypeId()));
		final String projectValue = getProjectValue(request, projectTypeId, existingBudgetProjectData);
		final String projectName = getName(request, existingBudgetProjectData, projectValue);
		final CurrencyId currencyId = getCurrencyId(request, existingBudgetProjectData);
		final PriceListVersionId priceListVersionId = getPriceListVersionId(request, existingBudgetProjectData);

		assertCurrencyIdsMatch(currencyId, priceListVersionId);

		final BudgetProjectData.BudgetProjectDataBuilder budgetProjectDataBuilder = BudgetProjectData.builder()
				.projectTypeId(projectTypeId)
				.value(projectValue)
				.name(projectName)
				.orgId(orgId)
				.currencyId(currencyId)
				.priceListVersionId(priceListVersionId);

		if (request.isSalesRepIdSet() || existingBudgetProjectData == null)
		{
			budgetProjectDataBuilder.salesRepId(UserId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getSalesRepId())));
		}
		else
		{
			budgetProjectDataBuilder.salesRepId(existingBudgetProjectData.getSalesRepId());
		}

		if (request.isProjectReferenceExtSet() || existingBudgetProjectData == null)
		{
			budgetProjectDataBuilder.projectReferenceExt(request.getProjectReferenceExt());
		}
		else
		{
			budgetProjectDataBuilder.projectReferenceExt(existingBudgetProjectData.getProjectReferenceExt());
		}

		if (request.isProjectParentIdSet() || existingBudgetProjectData == null)
		{
			budgetProjectDataBuilder.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getProjectParentId())));
		}
		else
		{
			budgetProjectDataBuilder.projectParentId(existingBudgetProjectData.getProjectParentId());
		}

		if (request.isBpartnerIdSet() || existingBudgetProjectData == null)
		{
			budgetProjectDataBuilder.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getBpartnerId())));
		}
		else
		{
			budgetProjectDataBuilder.bPartnerId(existingBudgetProjectData.getBPartnerId());
		}

		if (request.isDateContractSet() || existingBudgetProjectData == null)
		{
			budgetProjectDataBuilder.dateContract(request.getDateContract());
		}
		else
		{
			budgetProjectDataBuilder.dateContract(existingBudgetProjectData.getDateContract());
		}

		if (request.isDateFinishSet() || existingBudgetProjectData == null)
		{
			budgetProjectDataBuilder.dateFinish(request.getDateFinish());
		}
		else
		{
			budgetProjectDataBuilder.dateFinish(existingBudgetProjectData.getDateFinish());
		}

		if (request.isDescriptionSet() || existingBudgetProjectData == null)
		{
			budgetProjectDataBuilder.description(request.getDescription());
		}
		else
		{
			budgetProjectDataBuilder.description(existingBudgetProjectData.getDescription());
		}

		if (request.isActiveSet() || existingBudgetProjectData == null)
		{
			budgetProjectDataBuilder.isActive(request.getActive());
		}
		else
		{
			budgetProjectDataBuilder.isActive(existingBudgetProjectData.isActive());
		}

		return budgetProjectDataBuilder.build();
	}

	@NonNull
	private String getName(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@Nullable final BudgetProjectData existingProjectData,
			@NonNull final String updatedProjectValue)
	{
		if (request.isNameSet())
		{
			return request.getName();
		}
		else if (existingProjectData != null)
		{
			return existingProjectData.getName();
		}
		else
		{
			return updatedProjectValue;
		}
	}

	@NonNull
	private String getProjectValue(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@NonNull final ProjectTypeId projectTypeId,
			@Nullable final BudgetProjectData currentProjectData)
	{
		final String projectValue;

		if (request.isValueSet())
		{
			projectValue = request.getValue();
		}
		else if (currentProjectData != null)
		{
			projectValue = currentProjectData.getValue();
		}
		else
		{
			projectValue = projectSharedService.getValueForProjectType(projectTypeId);
		}

		if (Check.isBlank(projectValue))
		{
			throw new RuntimeException("Couldn't determine project value for projectIdentifier:" + request.getProjectIdentifier());
		}

		return projectValue;
	}

	@NonNull
	private CurrencyId getCurrencyId(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@Nullable final BudgetProjectData existingBudgetProjectData)
	{
		if (request.isCurrencyCodeSet() || existingBudgetProjectData == null)
		{
			if (!request.isCurrencyCodeSet())
			{
				// currencyId missing on "create" scenario
				throw new MissingPropertyException("currencyCode", request);
			}

			final Currency currency = currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getCurrencyCode()));

			return currency.getId();
		}
		else
		{
			return existingBudgetProjectData.getCurrencyId();
		}
	}

	@Nullable
	private PriceListVersionId getPriceListVersionId(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@Nullable final BudgetProjectData existingBudgetProjectData)
	{
		if (request.isPriceListVersionIdSet() || existingBudgetProjectData == null)
		{
			return PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId()));
		}
		else
		{
			return existingBudgetProjectData.getPriceListVersionId();
		}
	}

	private void assertCurrencyIdsMatch(
			@NonNull final CurrencyId currencyId,
			@Nullable final PriceListVersionId priceListVersionId)
	{
		if (priceListVersionId == null)
		{
			return;
		}

		final I_M_PriceList priceListRecord = priceListDAO.getPriceListByPriceListVersionId(priceListVersionId);

		if (priceListRecord == null)
		{
			return;
		}

		final CurrencyId priceListCurrencyId = CurrencyId.ofRepoId(priceListRecord.getC_Currency_ID());

		if (currencyId.getRepoId() != priceListCurrencyId.getRepoId())
		{
			throw new AdempiereException("Currency of the budget project does not match the currency of the price list!")
					.appendParametersToMessage()
					.setParameter("Project.CurrencyId", currencyId)
					.setParameter("PriceList.CurrencyId", priceListCurrencyId);
		}
	}
}
