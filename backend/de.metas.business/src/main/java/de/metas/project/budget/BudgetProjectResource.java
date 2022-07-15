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

import de.metas.calendar.util.CalendarDateRange;
import de.metas.money.Money;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.resource.ResourceGroupId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class BudgetProjectResource
{
	@NonNull BudgetProjectResourceId id;

	@NonNull ProjectId projectId;

	@NonNull ResourceGroupId resourceGroupId;
	@Nullable ResourceId resourceId;

	@NonNull UomId durationUomId;
	@NonNull Money plannedAmount;
	@NonNull Quantity plannedDuration;
	@NonNull Money pricePerDurationUnit;

	@NonNull CalendarDateRange dateRange;

	@Nullable String description;

	@Builder(toBuilder = true)
	private BudgetProjectResource(
			@NonNull final BudgetProjectResourceId id,
			@NonNull final ProjectId projectId,
			@NonNull final ResourceGroupId resourceGroupId,
			@Nullable final ResourceId resourceId,
			@NonNull final UomId durationUomId,
			@NonNull final Money plannedAmount,
			@NonNull final Quantity plannedDuration,
			@NonNull final Money pricePerDurationUnit,
			@NonNull final CalendarDateRange dateRange,
			@Nullable final String description)
	{
		Money.getCommonCurrencyIdOfAll(plannedAmount, pricePerDurationUnit); // make sure they are in the same currency

		this.id = id;
		this.projectId = projectId;
		this.resourceGroupId = resourceGroupId;
		this.resourceId = resourceId;
		this.durationUomId = durationUomId;
		this.plannedAmount = plannedAmount;
		this.plannedDuration = plannedDuration;
		this.pricePerDurationUnit = pricePerDurationUnit;
		this.dateRange = dateRange;
		this.description = description;
	}

	public BudgetProjectAndResourceId getProjectAndResourceId()
	{
		return BudgetProjectAndResourceId.of(projectId, id);
	}
}
