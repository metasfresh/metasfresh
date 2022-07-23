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

import de.metas.costing.CostPrice;
import de.metas.currency.Amount;
import de.metas.money.CurrencyId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.resource.ResourceGroupId;
import de.metas.uom.UomId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class BudgetProjectResource
{
	@Nullable
	@Getter
	BudgetProjectResourceId budgetProjectResourceId;

	@With
	@Nullable
	ProjectId projectId;

	@NonNull
	UomId uomTimeId;

	@NonNull
	Instant dateStartPlan;

	@NonNull
	Instant dateFinishPlan;

	@Nullable
	String description;

	@NonNull
	Amount plannedAmt;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	BigDecimal plannedDuration;

	@NonNull
	CostPrice pricePerTimeUOM;

	@Nullable
	ResourceGroupId resourceGroupId;

	@Nullable
	ResourceId resourceId;

	@Nullable
	ExternalId externalId;

	@Nullable
	Boolean isActive;

	@NonNull
	public BudgetProjectResourceId getBudgetProjectResourceIdNonNull()
	{
		if (budgetProjectResourceId == null)
		{
			throw new AdempiereException("BudgetProjectResourceId cannot be null at this stage!");
		}
		return budgetProjectResourceId;
	}
}
