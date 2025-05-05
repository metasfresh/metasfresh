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

import de.metas.product.ResourceId;
import de.metas.resource.Resource;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.resource.ResourceType;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project_Resource_Budget;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BudgetProjectResourceService
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final ResourceService resourceService;

	public BudgetProjectResourceService(@NonNull final ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	/**
	 * @return planned duration in budgetResource.getC_UOM_Time_ID()
	 */
	@NonNull
	public Optional<BigDecimal> computePlannedDuration(@NonNull final I_C_Project_Resource_Budget budgetResource)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(budgetResource.getC_UOM_Time_ID());
		if (uomId == null)
		{
			return Optional.empty();
		}

		final BigDecimal pricePerTimeUOM = budgetResource.getPricePerTimeUOM();
		if (pricePerTimeUOM.signum() == 0)
		{
			return Optional.of(BigDecimal.ZERO);
		}

		final BigDecimal plannedAmt = budgetResource.getPlannedAmt();
		if (plannedAmt.signum() == 0)
		{
			return Optional.of(BigDecimal.ZERO);
		}

		final UOMPrecision uomPrecision = uomDAO.getStandardPrecision(uomId);
		final BigDecimal plannedDuration = plannedAmt.divide(pricePerTimeUOM, uomPrecision.toInt(), uomPrecision.getRoundingMode());
		return Optional.of(plannedDuration);
	}

	public void validateDurationUOMFromResource(@NonNull final I_C_Project_Resource_Budget record)
	{
		final ResourceId resourceId = ResourceId.ofRepoIdOrNull(record.getS_Resource_ID());
		final ResourceGroupId resourceGroupId = ResourceGroupId.ofRepoIdOrNull(record.getS_Resource_Group_ID());

		final UomId durationUomId;

		if (resourceId != null)
		{
			final Resource resource = resourceService.getResourceById(resourceId);
			final ResourceType resourceType = resourceService.getResourceTypeById(resource.getResourceTypeId());

			durationUomId = resourceType.getDurationUomId();
		}
		else if (resourceGroupId != null)
		{
			final ResourceGroup resourceGroup = resourceService.getGroupById(resourceGroupId);
			durationUomId = uomDAO.getUomIdByTemporalUnit(resourceGroup.getDurationUnit());
		}
		else
		{
			// do nothing
			return;
		}

		if (record.getC_UOM_Time_ID() != durationUomId.getRepoId())
		{
			throw new AdempiereException("Resource.DurationUOM is not matching C_Project_Resource_Budget.C_UOM_Time_ID!")
					.appendParametersToMessage()
					.setParameter("C_Project_Resource_Budget.C_UOM_Time_ID", record.getC_UOM_Time_ID())
					.setParameter("Resource.DurationUOM", durationUomId.getRepoId());
		}
	}
}
