package de.metas.material.planning.event;

import java.util.List;

import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedEvent;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.pporder.PPOrderDemandMatcher;
import de.metas.material.planning.pporder.PPOrderPojoSupplier;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class PPOrderAdvisedEventCreator
{
	private final PPOrderDemandMatcher ppOrderDemandMatcher;

	private final PPOrderPojoSupplier ppOrderPojoSupplier;

	public PPOrderAdvisedEventCreator(
			@NonNull final PPOrderDemandMatcher ppOrderDemandMatcher,
			@NonNull final PPOrderPojoSupplier ppOrderPojoSupplier)
	{
		this.ppOrderDemandMatcher = ppOrderDemandMatcher;
		this.ppOrderPojoSupplier = ppOrderPojoSupplier;
	}

	public List<PPOrderAdvisedEvent> createPPOrderAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final IMutableMRPContext mrpContext)
	{
		if (!ppOrderDemandMatcher.matches(mrpContext))
		{
			return ImmutableList.of();
		}

		final IMaterialRequest request = SupplyRequiredHandlerUtils.mkRequest(supplyRequiredDescriptor, mrpContext);

		final PPOrder ppOrder = ppOrderPojoSupplier.supplyPPOrderPojoWithLines(request);

		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();

		final PPOrderAdvisedEvent event = PPOrderAdvisedEvent.builder()
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.eventDescriptor(supplyRequiredDescriptor.getEventDescriptor())
				.ppOrder(ppOrder)
				.directlyCreatePPOrder(productPlanning.isCreatePlan())
				.directlyPickSupply(productPlanning.isPickDirectlyIfFeasible())
				.build();

		return ImmutableList.of(event);
	}

}
