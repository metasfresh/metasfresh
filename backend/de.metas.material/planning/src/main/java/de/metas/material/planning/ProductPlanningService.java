package de.metas.material.planning;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.material.planning.pporder.PPRoutingType;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductPlanningService
{
	private final IPPRoutingRepository routingRepository = Services.get(IPPRoutingRepository.class);

	/**
	 * Duration to have this Qty available (i.e. Lead Time + Transfer Time)
	 *
	 * @return return duration [days]
	 */
	public int calculateDurationDays(final int leadTimeDays, @NonNull final ProductPlanning productPlanningData)
	{
		Check.assume(leadTimeDays >= 0, "leadTimeDays >= 0");

		final int transferTimeDays = productPlanningData.getTransferTimeDays();
		Check.assume(transferTimeDays >= 0, "transferTimeDays >= 0");

		return leadTimeDays + transferTimeDays;
	}

	public Optional<ResourceId> getPlantOfWarehouse(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouse = Services.get(IWarehouseBL.class).getById(warehouseId);
		return ResourceId.optionalOfRepoId(warehouse.getPP_Plant_ID());
	}

	public Optional<PPRoutingId> getDefaultRoutingId(@NonNull final PPRoutingType type)
	{
		return routingRepository.getDefaultRoutingIdByType(type);
	}

	public int calculateDurationDays(
			@NonNull final ProductPlanning productPlanning,
			@NonNull final BigDecimal qty)
	{
		final int leadTimeDays = calculateLeadTimeDays(productPlanning, qty);
		return calculateDurationDays(leadTimeDays, productPlanning);
	}

	private int calculateLeadTimeDays(
			@NonNull final ProductPlanning productPlanningRecord,
			@NonNull final BigDecimal qty)
	{
		final int leadTimeDays = productPlanningRecord.getLeadTimeDays();
		if (leadTimeDays > 0)
		{
			// LeadTime was set in Product Planning/ take the leadtime as it is
			return leadTimeDays;
		}

		final PPRoutingId routingId = productPlanningRecord.getWorkflowId();
		final ResourceId plantId = productPlanningRecord.getPlantId();
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService();
		return routingService.calculateDurationDays(routingId, plantId, qty);
	}
}
