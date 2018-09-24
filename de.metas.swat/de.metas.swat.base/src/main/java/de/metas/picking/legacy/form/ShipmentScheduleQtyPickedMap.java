package de.metas.picking.legacy.form;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
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

@ToString
@EqualsAndHashCode
public class ShipmentScheduleQtyPickedMap
{
	public static ShipmentScheduleQtyPickedMap newInstance()
	{
		return new ShipmentScheduleQtyPickedMap();
	}

	public static ShipmentScheduleQtyPickedMap singleton(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final Quantity qty)
	{
		final ShipmentScheduleQtyPickedMap result = newInstance();
		result.setQty(shipmentSchedule, qty);
		return result;
	}

	private final LinkedHashMap<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedules;
	private final HashMap<ShipmentScheduleId, Quantity> qtys;

	private ShipmentScheduleQtyPickedMap()
	{
		shipmentSchedules = new LinkedHashMap<>();
		qtys = new HashMap<>();
	}

	private ShipmentScheduleQtyPickedMap(final ShipmentScheduleQtyPickedMap from)
	{
		shipmentSchedules = new LinkedHashMap<>(from.shipmentSchedules);
		qtys = new HashMap<>(from.qtys);
	}

	public ShipmentScheduleQtyPickedMap copy()
	{
		return new ShipmentScheduleQtyPickedMap(this);
	}

	public boolean isEmpty()
	{
		return shipmentSchedules.isEmpty();
	}

	public int size()
	{
		return shipmentSchedules.size();
	}

	public Quantity getQty(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		return qtys.get(shipmentScheduleId);
	}

	public void setQty(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final Quantity qty)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		shipmentSchedules.put(shipmentScheduleId, shipmentSchedule);
		qtys.put(shipmentScheduleId, qty);
	}

	public void setQtyForSched(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final Quantity qty)
	{
		if (!contains(shipmentSchedule))
		{
			throw new IllegalArgumentException(shipmentSchedule + " must be added to " + this);
		}

		setQty(shipmentSchedule, qty);
	}

	public void remove(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		shipmentSchedules.remove(shipmentScheduleId);
		qtys.remove(shipmentScheduleId);
	}

	public boolean contains(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		return shipmentSchedules.containsKey(shipmentScheduleId);
	}

	public List<I_M_ShipmentSchedule> getShipmentSchedules()
	{
		return ImmutableList.copyOf(shipmentSchedules.values());
	}

	public void clear()
	{
		shipmentSchedules.clear();
		qtys.clear();
	}

	public void setFrom(@NonNull final ShipmentScheduleQtyPickedMap from)
	{
		shipmentSchedules.clear();
		shipmentSchedules.putAll(from.shipmentSchedules);

		qtys.clear();
		qtys.putAll(from.qtys);
	}

	public Optional<Quantity> getQtySum()
	{
		return qtys.values()
				.stream()
				.reduce(Quantity::add);
	}

	public ShipmentScheduleQtyPickedMap subset(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final ShipmentScheduleQtyPickedMap result = newInstance();
		shipmentSchedules.stream()
				.filter(this::contains)
				.forEach(s -> result.setQty(s, getQty(s)));
		return result;
	}

	public void forEach(@NonNull final BiConsumer<I_M_ShipmentSchedule, Quantity> consumer)
	{
		shipmentSchedules.forEach((shipmentScheduleId, shipmentSchedule) -> {
			final Quantity qty = qtys.get(shipmentScheduleId);
			consumer.accept(shipmentSchedule, qty);
		});
	}
}
