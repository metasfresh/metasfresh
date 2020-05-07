package de.metas.order.inoutcandidate;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
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

@ToString
final class CompositeOrderLineShipmentScheduleHandlerExtension implements OrderLineShipmentScheduleHandlerExtension
{
	public static OrderLineShipmentScheduleHandlerExtension of(final Optional<List<OrderLineShipmentScheduleHandlerExtension>> extensions)
	{
		final ImmutableList<OrderLineShipmentScheduleHandlerExtension> extensionsList = extensions
				.map(ImmutableList::copyOf)
				.orElseGet(ImmutableList::of);

		if (extensionsList.isEmpty())
		{
			return EMPTY;
		}
		else if (extensionsList.size() == 1)
		{
			return extensionsList.get(0);
		}
		else
		{
			return new CompositeOrderLineShipmentScheduleHandlerExtension(extensionsList);
		}
	}

	private static final CompositeOrderLineShipmentScheduleHandlerExtension EMPTY = new CompositeOrderLineShipmentScheduleHandlerExtension(ImmutableList.of());

	private final ImmutableList<OrderLineShipmentScheduleHandlerExtension> extensionsList;

	private CompositeOrderLineShipmentScheduleHandlerExtension(@NonNull final ImmutableList<OrderLineShipmentScheduleHandlerExtension> extensionsList)
	{
		this.extensionsList = extensionsList;
	}

	@Override
	public void updateShipmentScheduleFromOrderLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_OrderLine orderLine)
	{
		for (final OrderLineShipmentScheduleHandlerExtension extension : extensionsList)
		{
			extension.updateShipmentScheduleFromOrderLine(shipmentSchedule, orderLine);
		}
	}
}
