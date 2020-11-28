package de.metas.handlingunits.shipmentschedule.spi.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.i18n.BooleanWithReason;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

final class CompleteOrderDeliveryRuleChecker
{
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL;
	private final IShipmentSchedulePA shipmentSchedulesRepo;

	final List<ShipmentScheduleWithHU> candidates;

	@Builder
	private CompleteOrderDeliveryRuleChecker(
			@NonNull final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL,
			@NonNull final IShipmentSchedulePA shipmentSchedulesRepo,
			//
			@NonNull final List<ShipmentScheduleWithHU> candidates)
	{
		this.shipmentScheduleEffectiveValuesBL = shipmentScheduleEffectiveValuesBL;
		this.shipmentSchedulesRepo = shipmentSchedulesRepo;

		this.candidates = candidates;
	}

	public BooleanWithReason check()
	{
		//
		// Group candidates by orders
		final HashMap<OrderId, Order> orders = new HashMap<>();
		for (final ShipmentScheduleWithHU candidate : candidates)
		{
			final OrderId orderId = candidate.getOrderId();
			if (orderId == null)
			{
				continue;
			}

			final Order order = orders.computeIfAbsent(orderId, Order::new);
			order.addShipmentScheduleId(candidate.getShipmentScheduleId());

			if (!order.isCompleteOrderDeliveryRequired() && isCompleteOrderDeliveryRule(candidate))
			{
				order.setCompleteOrderDeliveryRequired(true);
			}

			if (order.isCompleteOrderDeliveryRequired() && !isCompletelyDelivered(candidate))
			{
				return BooleanWithReason.falseBecause("schedule not completely delivered: " + candidate.getShipmentScheduleId());
			}
		}

		//
		// Check Orders if they contain all shipment schedules in order to have a complete order delivery
		for (final Order order : orders.values())
		{
			if (!order.isCompleteOrderDeliveryRequired())
			{
				continue;
			}

			final OrderId orderId = order.getOrderId();
			final Set<ShipmentScheduleId> requiredShipmentScheduleIds = shipmentSchedulesRepo.retrieveUnprocessedIdsByOrderId(orderId);

			final Set<ShipmentScheduleId> missingShipmentScheduleIds = order.getMissingShipmentScheduleIds(requiredShipmentScheduleIds);
			if (!missingShipmentScheduleIds.isEmpty())
			{
				return BooleanWithReason.falseBecause("order " + orderId + " needs to be complete but following schedules are missing: " + missingShipmentScheduleIds);
			}
		}

		return BooleanWithReason.TRUE;
	}

	private boolean isCompleteOrderDeliveryRule(final ShipmentScheduleWithHU candidate)
	{
		final DeliveryRule deliveryRule = shipmentScheduleEffectiveValuesBL.getDeliveryRule(candidate.getM_ShipmentSchedule());
		return deliveryRule.isCompleteOrder();
	}

	private boolean isCompletelyDelivered(final ShipmentScheduleWithHU candidate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = candidate.getM_ShipmentSchedule();
		final BigDecimal qtyOrdered = shipmentScheduleEffectiveValuesBL.computeQtyOrdered(shipmentSchedule);
		final BigDecimal qtyToDeliver = shipmentScheduleEffectiveValuesBL.getQtyToDeliverBD(shipmentSchedule);
		final BigDecimal qtyDelivered = shipmentSchedule.getQtyDelivered();

		final BigDecimal qtyDeliveredProjected = qtyDelivered.add(qtyToDeliver);

		return qtyOrdered.compareTo(qtyDeliveredProjected) <= 0;
	}

	@ToString
	private static class Order
	{
		@Getter
		private final OrderId orderId;

		private final Set<ShipmentScheduleId> shipmentScheduleIds = new HashSet<>();

		@Getter
		@Setter
		private boolean completeOrderDeliveryRequired;

		public Order(@NonNull final OrderId orderId)
		{
			this.orderId = orderId;
		}

		public void addShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			shipmentScheduleIds.add(shipmentScheduleId);
		}

		public Set<ShipmentScheduleId> getMissingShipmentScheduleIds(final Set<ShipmentScheduleId> allShipmentScheduleIds)
		{
			return Sets.difference(allShipmentScheduleIds, shipmentScheduleIds);
		}
	}
}
