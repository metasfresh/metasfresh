package de.metas.material.planning.ddorder;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.event.SupplyRequiredHandlerUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

// TODO delete me
@Service
@RequiredArgsConstructor
public class DDOrderAdvisedEventCreator
{
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final DDOrderDemandMatcher ddOrderDemandMatcher;
	@NonNull private final DDOrderPojoSupplier ddOrderPojoSupplier;

	public List<DDOrderAdvisedEvent> createDDOrderAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			final MaterialPlanningContext context)
	{
		if (!ddOrderDemandMatcher.matches(context))
		{
			return ImmutableList.of();
		}

		final List<DDOrderAdvisedEvent> events = new ArrayList<>();

		final List<DDOrder> ddOrders = ddOrderPojoSupplier
				.supplyPojos(
						SupplyRequiredHandlerUtils.mkRequest(supplyRequiredDescriptor, context));

		final ProductPlanning productPlanningData = context.getProductPlanning();
		for (final DDOrder ddOrder : ddOrders)
		{
			for (final DDOrderLine ddOrderLine : ddOrder.getLines())
			{
				final DistributionNetworkLine distributionNetworkLine = Optional.ofNullable(ddOrderLine.getDistributionNetworkAndLineId())
						.map(distributionNetworkRepository::getLineById)
						.orElseThrow(() -> new AdempiereException("Every DDOrderLine pojo created by this planner needs to have networkDistributionLineId set, but this one hasn't; ddOrderLine=" + ddOrderLine));

				final DDOrderAdvisedEvent distributionAdvisedEvent = DDOrderAdvisedEvent.builder()
						.eventDescriptor(supplyRequiredDescriptor.newEventDescriptor())
						.supplyRequiredDescriptor(supplyRequiredDescriptor)
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
