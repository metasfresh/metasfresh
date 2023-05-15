package de.metas.material.planning.ddorder;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.event.SupplyRequiredHandlerUtils;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class DDOrderAdvisedEventCreator
{
	private final DDOrderDemandMatcher ddOrderDemandMatcher;

	private final DDOrderPojoSupplier ddOrderPojoSupplier;

	public DDOrderAdvisedEventCreator(
			@NonNull final DDOrderDemandMatcher ddOrderDemandMatcher,
			@NonNull final DDOrderPojoSupplier ddOrderPojoSupplier)
	{
		this.ddOrderDemandMatcher = ddOrderDemandMatcher;
		this.ddOrderPojoSupplier = ddOrderPojoSupplier;
	}

	public List<DDOrderAdvisedEvent> createDDOrderAdvisedEvents(
			@NonNull SupplyRequiredDescriptor supplyRequiredDescriptor,
			final IMutableMRPContext mrpContext)
	{
		if(!ddOrderDemandMatcher.matches(mrpContext))
		{
			return ImmutableList.of();
		}

		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning(); // won't be null; if there was no productPlanningData, we wouldn't be here.
		final BigDecimal requiredQty = supplyRequiredDescriptor.getMaterialDescriptor().getQuantity();
		if(!productPlanningData.isLotForLot() && requiredQty.signum() <= 0)
		{
			Loggables.addLog("Didn't create DDOrderAdvisedEvent because LotForLot=false and requiredQty={}", requiredQty);
			return ImmutableList.of();
		}

		if(productPlanningData.isLotForLot())
		{
			final BigDecimal usedQty;
			if(!supplyRequiredDescriptor.isUpdated())
			{
				usedQty = supplyRequiredDescriptor.getFullDemandQty();
				Loggables.addLog("Using fullDemandQty={}, because of LotForLot=true and updated=false", usedQty);
			}
			// we don't reduce Quantity of DDOrders atm
			else if(supplyRequiredDescriptor.getDeltaQuantity().signum() > 0)
			{
				usedQty = supplyRequiredDescriptor.getDeltaQuantity();
				Loggables.addLog("Using deltaQty={}, because of LotForLot=true and updated=true", usedQty);
			}
			else
			{
				Loggables.addLog("Didn't create DDOrderAdvisedEvent because LotForLot=true and updated=true, but deltaQty={}", supplyRequiredDescriptor.getDeltaQuantity());
				return ImmutableList.of();
			}
			supplyRequiredDescriptor = supplyRequiredDescriptor.toBuilder()
					.isLotForLot("Y")
					.materialDescriptor(supplyRequiredDescriptor.getMaterialDescriptor().withQuantity(usedQty))
					.build();
		}
		else
		{
			supplyRequiredDescriptor = supplyRequiredDescriptor.toBuilder().isLotForLot("N").build();
		}

		if(requiredQty.signum() == 0)
		{
			SupplyRequiredHandlerUtils.updateMainData(supplyRequiredDescriptor);
		}

		final List<DDOrderAdvisedEvent> events = new ArrayList<>();

		final List<DDOrder> ddOrders = ddOrderPojoSupplier
				.supplyPojos(
						SupplyRequiredHandlerUtils.mkRequest(supplyRequiredDescriptor, mrpContext));


		for (final DDOrder ddOrder : ddOrders)
		{
			for (final DDOrderLine ddOrderLine : ddOrder.getLines())
			{
				Check.errorIf(ddOrderLine.getNetworkDistributionLineId() <= 0,
						"Every DDOrderLine pojo created by this planner needs to have networkDistributionLineId > 0, but this one hasn't; ddOrderLine={}",
						ddOrderLine);

				final I_DD_NetworkDistributionLine networkLine = InterfaceWrapperHelper.create(
						mrpContext.getCtx(),
						ddOrderLine.getNetworkDistributionLineId(),
						I_DD_NetworkDistributionLine.class,
						mrpContext.getTrxName());

				final DDOrderAdvisedEvent distributionAdvisedEvent = DDOrderAdvisedEvent.builder()
						.supplyRequiredDescriptor(supplyRequiredDescriptor)
						.eventDescriptor(EventDescriptor.ofEventDescriptor(supplyRequiredDescriptor.getEventDescriptor()))
						.fromWarehouseId(WarehouseId.ofRepoId(networkLine.getM_WarehouseSource_ID()))
						.toWarehouseId(WarehouseId.ofRepoId(networkLine.getM_Warehouse_ID()))
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
