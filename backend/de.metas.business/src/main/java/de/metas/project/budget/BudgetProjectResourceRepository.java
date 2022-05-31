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

@Repository
public class BudgetProjectResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public static BudgetProjectResource fromRecord(@NonNull final I_C_Project_Resource_Budget record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return BudgetProjectResource.builder()
				.id(BudgetProjectResourceId.ofRepoId(record.getC_Project_Resource_Budget_ID()))
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.resourceGroupId(ResourceGroupId.ofRepoId(record.getS_Resource_Group_ID()))
				.resourceId(ResourceId.ofRepoIdOrNull(record.getS_Resource_ID()))
				.durationUomId(UomId.ofRepoId(record.getC_UOM_Time_ID()))
				.plannedDuration(Quantitys.create(record.getPlannedDuration(), UomId.ofRepoId(record.getC_UOM_Time_ID())))
				.plannedAmount(Money.of(record.getPlannedAmt(), currencyId))
				.pricePerDurationUnit(Money.of(record.getPricePerTimeUOM(), currencyId))
				.startDate(TimeUtil.asZonedDateTime(record.getDateStartPlan()))
				.endDate(TimeUtil.asZonedDateTime(record.getDateFinishPlan()))
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
