package de.metas.material.planning.ddorder;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.event.SupplyRequiredHandlerUtils;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@RequiredArgsConstructor
public class DDOrderAdvisedEventCreator
{
	@NonNull private final IDistributionNetworkDAO distributionNetworkDAO = Services.get(IDistributionNetworkDAO.class);
	@NonNull private final DDOrderDemandMatcher ddOrderDemandMatcher;
	@NonNull private final DDOrderPojoSupplier ddOrderPojoSupplier;

	public List<DDOrderAdvisedEvent> createDDOrderAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			final IMutableMRPContext mrpContext)
	{
		if (!ddOrderDemandMatcher.matches(mrpContext))
		{
			return ImmutableList.of();
		}

		final List<DDOrderAdvisedEvent> events = new ArrayList<>();

		final List<DDOrder> ddOrders = ddOrderPojoSupplier
				.supplyPojos(
						SupplyRequiredHandlerUtils.mkRequest(supplyRequiredDescriptor, mrpContext));

		final ProductPlanning productPlanningData = mrpContext.getProductPlanning();
		for (final DDOrder ddOrder : ddOrders)
		{
			for (final DDOrderLine ddOrderLine : ddOrder.getLines())
			{
				final DistributionNetworkLine distributionNetworkLine = DistributionNetworkLineId.optionalOfRepoId(ddOrderLine.getNetworkDistributionLineId())
						.map(distributionNetworkDAO::getLineById)
						.orElseThrow(() -> new AdempiereException("Every DDOrderLine pojo created by this planner needs to have networkDistributionLineId > 0, but this one hasn't; ddOrderLine=" + ddOrderLine));

				final DDOrderAdvisedEvent distributionAdvisedEvent = DDOrderAdvisedEvent.builder()
						.supplyRequiredDescriptor(supplyRequiredDescriptor)
						.eventDescriptor(EventDescriptor.ofEventDescriptor(supplyRequiredDescriptor.getEventDescriptor()))
						.fromWarehouseId(distributionNetworkLine.getSourceWarehouseId())
						.toWarehouseId(distributionNetworkLine.getTargetWarehouseId())
						.ddOrder(ddOrder)
						.advisedToCreateDDrder(productPlanningData.isCreatePlan())
						.pickIfFeasible(productPlanningData.isPickDirectlyIfFeasible())
						.build();

				events.add(distributionAdvisedEvent);
			}
		}
		return events;
	}

}
