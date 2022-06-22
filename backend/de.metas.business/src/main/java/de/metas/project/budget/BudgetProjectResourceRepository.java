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
import com.google.common.collect.ImmutableMap;
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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_Resource_Budget;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Repository
public class BudgetProjectResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public BudgetProjectResources getByProjectId(@NonNull final ProjectId projectId)
	{
		final ImmutableList<BudgetProjectResource> budgets = queryBL.createQueryBuilder(I_C_Project_Resource_Budget.class)
				.addEqualsFilter(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_ID, projectId)
				.stream()
				.map(BudgetProjectResourceRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return BudgetProjectResources.builder()
				.projectId(projectId)
				.budgets(budgets)
				.build();
	}

	public Map<ProjectId, BudgetProjectResources> getByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return ImmutableMap.of();
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
				.collect(ImmutableMap.toImmutableMap(BudgetProjectResources::getProjectId, Function.identity()));
	}

	public static BudgetProjectResource fromRecord(@NonNull final I_C_Project_Resource_Budget record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final UomId durationUomId = UomId.ofRepoId(record.getC_UOM_Time_ID());

		return BudgetProjectResource.builder()
				.id(BudgetProjectResourceId.ofRepoId(record.getC_Project_Resource_Budget_ID()))
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.resourceGroupId(ResourceGroupId.ofRepoId(record.getS_Resource_Group_ID()))
				.resourceId(ResourceId.ofRepoIdOrNull(record.getS_Resource_ID()))
				.durationUomId(durationUomId)
				.plannedDuration(Quantitys.create(record.getPlannedDuration(), durationUomId))
				.plannedAmount(Money.of(record.getPlannedAmt(), currencyId))
				.pricePerDurationUnit(Money.of(record.getPricePerTimeUOM(), currencyId))
				.dateRange(CalendarDateRange.builder()
						.startDate(TimeUtil.asZonedDateTime(record.getDateStartPlan()))
						.endDate(TimeUtil.asZonedDateTime(record.getDateFinishPlan()))
						.build())
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.build();
	}

	public void updateAllByProjectId(
			@NonNull final ProjectId projectId,
			@NonNull final OrgId newOrgId,
			@NonNull final CurrencyId newCurrencyId)
	{
		queryBL.createQueryBuilder(I_C_Project_Resource_Budget.class)
				.addEqualsFilter(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_ID, projectId)
				.forEach(record -> updateRecord(record, newOrgId, newCurrencyId));
	}

	private static void updateRecord(
			@NonNull final I_C_Project_Resource_Budget record,
			@NonNull final OrgId newOrgId,
			@NonNull final CurrencyId newCurrencyId)
	{
		record.setAD_Org_ID(newOrgId.getRepoId());
		record.setC_Currency_ID(newCurrencyId.getRepoId());
		InterfaceWrapperHelper.save(record);
	}

}
