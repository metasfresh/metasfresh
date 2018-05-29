package de.metas.material.planning.purchaseorder;

import java.util.List;

import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseDemandAdvisedEvent;
import de.metas.material.planning.IMutableMRPContext;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class PurchaseOrderAdvisedEventCreator
{
	private final PurchaseOrderDemandMatcher purchaseOrderDemandMatcher;

	public PurchaseOrderAdvisedEventCreator(
			@NonNull final PurchaseOrderDemandMatcher purchaseOrderDemandMatcher)
	{
		this.purchaseOrderDemandMatcher = purchaseOrderDemandMatcher;
	}

	public List<PurchaseDemandAdvisedEvent> createPurchaseAdvisedEvent(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final IMutableMRPContext mrpContext)
	{
		if (!purchaseOrderDemandMatcher.matches(mrpContext))
		{
			return ImmutableList.of();
		}

		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();

		final PurchaseDemandAdvisedEvent event = PurchaseDemandAdvisedEvent
				.builder()
				.eventDescriptor(supplyRequiredDescriptor.getEventDescriptor())
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.directlyCreatePurchaseDemand(productPlanning.isCreatePlan())
				.productPlanningId(productPlanning.getPP_Product_Planning_ID())
				.build();
		return ImmutableList.of(event);
	}
}
