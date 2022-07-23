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

package de.metas.project.budget.data;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.costing.CostAmount;
import de.metas.costing.CostPrice;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.resource.ResourceGroupId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_Resource_Budget;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
@VisibleForTesting
public class BudgetProjectResourceRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);
	private static final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	@NonNull
	Optional<BudgetProjectResource> getOptionalById(@NonNull final BudgetProjectResourceId id)
	{
		return Optional.ofNullable(getRecordById(id))
				.map(BudgetProjectResourceRepository::ofRecord);
	}

	@Nullable
	I_C_Project_Resource_Budget getRecordById(@NonNull final BudgetProjectResourceId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Project_Resource_Budget.class);
	}

	@NonNull
	List<BudgetProjectResource> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Resource_Budget.class)
				.addEqualsFilter(I_C_Project_Resource_Budget.COLUMNNAME_C_Project_ID, projectId)
				.create()
				.stream()
				.map(BudgetProjectResourceRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	BudgetProjectResource save(@NonNull final BudgetProjectResource resourceData)
	{
		final BudgetProjectResourceId existingBudgetProjectResourceId;
		if (resourceData.getBudgetProjectResourceId() != null)
		{
			existingBudgetProjectResourceId = resourceData.getBudgetProjectResourceIdNonNull();
					
		}
		else
		{
			existingBudgetProjectResourceId = null;
		}
		final I_C_Project_Resource_Budget resourceRecord = InterfaceWrapperHelper.loadOrNew(existingBudgetProjectResourceId, I_C_Project_Resource_Budget.class);
		
		
		if (resourceData.getIsActive() != null)
		{
			resourceRecord.setIsActive(resourceData.getIsActive());
		}

		resourceRecord.setC_Project_ID(ProjectId.toRepoId(resourceData.getProjectId()));
		resourceRecord.setS_Resource_ID(ResourceId.toRepoId(resourceData.getResourceId()));
		resourceRecord.setS_Resource_Group_ID(ResourceGroupId.toRepoId(resourceData.getResourceGroupId()));
		resourceRecord.setDateFinishPlan(TimeUtil.asTimestamp(resourceData.getDateFinishPlan()));
		resourceRecord.setDateStartPlan(TimeUtil.asTimestamp(resourceData.getDateStartPlan()));
		resourceRecord.setC_Currency_ID(CurrencyId.toRepoId(resourceData.getCurrencyId()));
		resourceRecord.setC_UOM_Time_ID(UomId.toRepoId(resourceData.getUomTimeId()));
		resourceRecord.setDescription(resourceData.getDescription());
		resourceRecord.setPlannedAmt(resourceData.getPlannedAmt().getAsBigDecimal());
		resourceRecord.setPlannedDuration(resourceData.getPlannedDuration());
		resourceRecord.setPricePerTimeUOM(resourceData.getPricePerTimeUOM().toBigDecimal());

		resourceRecord.setExternalId(ExternalId.toValue(resourceData.getExternalId()));

		saveRecord(resourceRecord);

		return ofRecord(resourceRecord);
	}

	@NonNull
	private static BudgetProjectResource ofRecord(@NonNull final I_C_Project_Resource_Budget resourceRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(resourceRecord.getAD_Org_ID());
		final Instant dateFinishPlan = TimeUtil.asInstant(resourceRecord.getDateFinishPlan(), orgId);
		final Instant dateStartPlan = TimeUtil.asInstant(resourceRecord.getDateStartPlan(), orgId);
		if (dateStartPlan == null || dateFinishPlan == null)
		{
			throw new AdempiereException("I_C_Project_Resource_Budget.dateFinishPlan and I_C_Project_Resource_Budget.dateStartPlan should be set on the record at this point!");
		}

		final ProjectId projectId = ProjectId.ofRepoId(resourceRecord.getC_Project_ID());

		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(CurrencyId.ofRepoId(resourceRecord.getC_Currency_ID()));
		final Amount plannedAmt = Amount.of(resourceRecord.getPlannedAmt(), currencyCode);
		final CostPrice pricePerTimeUOM = CostPrice.ownCostPrice(CostAmount.of(resourceRecord.getPricePerTimeUOM(), CurrencyId.ofRepoId(resourceRecord.getC_Currency_ID())),
																 UomId.ofRepoId(resourceRecord.getC_UOM_Time_ID()));

		return BudgetProjectResource.builder()
				.budgetProjectResourceId(BudgetProjectResourceId.ofRepoId(projectId.getRepoId(), resourceRecord.getC_Project_Resource_Budget_ID()))
				.currencyId(CurrencyId.ofRepoId(resourceRecord.getC_Currency_ID()))
				.resourceId(ResourceId.ofRepoId(resourceRecord.getS_Resource_ID()))
				.resourceGroupId(ResourceGroupId.ofRepoId(resourceRecord.getS_Resource_Group_ID()))
				.projectId(projectId)
				.dateFinishPlan(dateFinishPlan)
				.dateStartPlan(dateStartPlan)
				.description(resourceRecord.getDescription())
				.isActive(resourceRecord.isActive())
				.plannedAmt(plannedAmt)
				.plannedDuration(resourceRecord.getPlannedDuration())
				.pricePerTimeUOM(pricePerTimeUOM)
				.uomTimeId(UomId.ofRepoId(resourceRecord.getC_UOM_Time_ID()))
				.externalId(ExternalId.ofOrNull(resourceRecord.getExternalId()))
				.build();
	}
}
