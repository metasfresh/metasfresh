/*
 * #%L
 * de.metas.business
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

package de.metas.project.budget;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.util.StringUtils;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantitys;
import de.metas.resource.ResourceGroupId;
import de.metas.uom.UomId;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_Resource_Budget;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class BudgetProjectResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public static final boolean IsAllDay_TRUE = true;

	@NonNull
	public BudgetProjectResources getByProjectId(@NonNull final ProjectId projectId)
	{
		return BudgetProjectResources.builder()
				.projectId(projectId)
				.budgets(getResourcesAsListByProjectId(projectId))
				.build();
	}

	@NonNull
	public ImmutableList<BudgetProjectResource> getResourcesAsListByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Resource_Budget.class)
				.addEqualsFilter(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_ID, projectId)
				.stream()
				.map(BudgetProjectResourceRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public BudgetProjectResourcesCollection getByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return BudgetProjectResourcesCollection.EMPTY;
		}

		final ImmutableListMultimap<ProjectId, BudgetProjectResource> budgetsByProjectId = queryBL.createQueryBuilder(I_C_Project_Resource_Budget.class)
				.addInArrayFilter(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_ID, projectIds)
				.stream()
				.map(BudgetProjectResourceRepository::fromRecord)
				.collect(ImmutableListMultimap.toImmutableListMultimap(BudgetProjectResource::getProjectId, Function.identity()));

		return projectIds.stream()
				.map(projectId -> BudgetProjectResources.builder()
						.projectId(projectId)
						.budgets(budgetsByProjectId.get(projectId))
						.build())
				.collect(BudgetProjectResourcesCollection.collect());
	}

	@NonNull
	public static BudgetProjectResource fromRecord(@NonNull final I_C_Project_Resource_Budget record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final UomId durationUomId = UomId.ofRepoId(record.getC_UOM_Time_ID());

		return BudgetProjectResource.builder()
				.id(extractBudgetProjectResourceId(record))
				.budgetProjectResourceData(BudgetProjectResourceData.builder()
												   .resourceGroupId(ResourceGroupId.ofRepoIdOrNull(record.getS_Resource_Group_ID()))
												   .resourceId(ResourceId.ofRepoIdOrNull(record.getS_Resource_ID()))
												   .durationUomId(durationUomId)
												   .plannedDuration(Quantitys.create(record.getPlannedDuration(), durationUomId))
												   .plannedAmount(Money.of(record.getPlannedAmt(), currencyId))
												   .pricePerDurationUnit(Money.of(record.getPricePerTimeUOM(), currencyId))
												   .dateRange(CalendarDateRange.builder()
																	  .startDate(record.getDateStartPlan().toInstant())
																	  .endDate(record.getDateFinishPlan().toInstant())
																	  .allDay(IsAllDay_TRUE)
																	  .build())
												   .description(StringUtils.trimBlankToNull(record.getDescription()))
												   .externalId(ExternalId.ofOrNull(record.getExternalId()))
												   .isActive(record.isActive())
												   .build())
				.build();
	}

	public static BudgetProjectResourceId extractBudgetProjectResourceId(final @NonNull I_C_Project_Resource_Budget record)
	{
		return BudgetProjectResourceId.ofRepoId(record.getC_Project_ID(), record.getC_Project_Resource_Budget_ID());
	}

	private static void updateRecord(
			@NonNull final I_C_Project_Resource_Budget record,
			@NonNull final BudgetProjectResource from)
	{
		final BudgetProjectResourceData fromBudgetProjectResourceData = from.getBudgetProjectResourceData();

		record.setDateStartPlan(Timestamp.from(fromBudgetProjectResourceData.getDateRange().getStartDate()));
		record.setDateFinishPlan(Timestamp.from(fromBudgetProjectResourceData.getDateRange().getEndDate()));
		record.setDescription(fromBudgetProjectResourceData.getDescription());
	}

	public void updateAllByProjectId(
			@NonNull final ProjectId projectId,
			@NonNull final OrgId newOrgId,
			@NonNull final CurrencyId newCurrencyId)
	{
		queryBL.createQueryBuilder(I_C_Project_Resource_Budget.class)
				.addEqualsFilter(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_ID, projectId)
				.forEach(record -> updateRecordAndSave(record, newOrgId, newCurrencyId));
	}

	private static void updateRecordAndSave(
			@NonNull final I_C_Project_Resource_Budget record,
			@NonNull final OrgId newOrgId,
			@NonNull final CurrencyId newCurrencyId)
	{
		record.setAD_Org_ID(newOrgId.getRepoId());
		record.setC_Currency_ID(newCurrencyId.getRepoId());
		saveRecord(record);
	}

	public void updateProjectResourcesByIds(
			@NonNull final ImmutableSet<BudgetProjectResourceId> projectResourceIds,
			@NonNull final UnaryOperator<BudgetProjectResource> mapper)
	{
		if (projectResourceIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_C_Project_Resource_Budget.class)
				.addInArrayFilter(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_Resource_Budget_ID, projectResourceIds)
				.forEach(record -> {
					final BudgetProjectResource projectResource = fromRecord(record);
					final BudgetProjectResource projectResourceChanged = mapper.apply(projectResource);
					if (!Objects.equals(projectResource, projectResourceChanged))
					{
						updateRecord(record, projectResourceChanged);
						saveRecord(record);
					}
				});
	}

	public InSetPredicate<ProjectId> getProjectIdsPredicateByResourceGroupIds(
			@NonNull final InSetPredicate<ResourceGroupId> resourceGroupIds,
			@NonNull final InSetPredicate<ProjectId> projectIds)
	{
		if (resourceGroupIds.isNone() || projectIds.isNone())
		{
			return InSetPredicate.none();
		}

		if (resourceGroupIds.isAny() && projectIds.isAny())
		{
			return InSetPredicate.any();
		}

		final ImmutableList<Integer> projectRepoIdsEffective = queryBL.createQueryBuilder(I_C_Project_Resource_Budget.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Project_Resource_Budget.COLUMNNAME_S_Resource_Group_ID, resourceGroupIds)
				.addInArrayFilter(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_ID, projectIds)
				.create()
				.listDistinct(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_ID, Integer.class);

		final ImmutableSet<ProjectId> projectIdsEffective = ProjectId.ofRepoIds(projectRepoIdsEffective);
		return InSetPredicate.only(projectIdsEffective);
	}

	@NonNull
	public BudgetProjectResource save(
			@NonNull final UpsertBudgetProjectResourceRequest upsertBudgetProjectResourceRequest,
			@NonNull final ProjectId projectId)
	{
		final I_C_Project_Resource_Budget resourceRecord = InterfaceWrapperHelper.loadOrNew(upsertBudgetProjectResourceRequest.getId(), I_C_Project_Resource_Budget.class);

		final BudgetProjectResourceData budgetProjectResourceData = upsertBudgetProjectResourceRequest.getBudgetProjectResourceData();

		resourceRecord.setIsActive(budgetProjectResourceData.getIsActive());
		resourceRecord.setC_Project_ID(ProjectId.toRepoId(projectId));
		resourceRecord.setS_Resource_ID(ResourceId.toRepoId(budgetProjectResourceData.getResourceId()));
		resourceRecord.setS_Resource_Group_ID(ResourceGroupId.toRepoId(budgetProjectResourceData.getResourceGroupId()));
		resourceRecord.setDateFinishPlan(TimeUtil.asTimestamp(budgetProjectResourceData.getDateRange().getEndDate()));
		resourceRecord.setDateStartPlan(TimeUtil.asTimestamp(budgetProjectResourceData.getDateRange().getStartDate()));
		resourceRecord.setC_Currency_ID(CurrencyId.toRepoId(budgetProjectResourceData.getCurrencyId()));
		resourceRecord.setDescription(budgetProjectResourceData.getDescription());
		resourceRecord.setPlannedAmt(budgetProjectResourceData.getPlannedAmount().toBigDecimal());
		resourceRecord.setPlannedDuration(budgetProjectResourceData.getPlannedDuration().toBigDecimal());
		resourceRecord.setPricePerTimeUOM(budgetProjectResourceData.getPricePerDurationUnit().toBigDecimal());
		resourceRecord.setExternalId(ExternalId.toValue(budgetProjectResourceData.getExternalId()));
		resourceRecord.setC_UOM_Time_ID(UomId.toRepoId(budgetProjectResourceData.getDurationUomId()));

		saveRecord(resourceRecord);

		return fromRecord(resourceRecord);
	}
}
