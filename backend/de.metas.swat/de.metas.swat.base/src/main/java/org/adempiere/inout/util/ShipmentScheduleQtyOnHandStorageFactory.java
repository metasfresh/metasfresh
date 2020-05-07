package org.adempiere.inout.util;

import lombok.NonNull;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockRepository;

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

@Service
public class ShipmentScheduleQtyOnHandStorageFactory
{
	private final StockRepository stockRepository;

	public ShipmentScheduleQtyOnHandStorageFactory(@NonNull final StockRepository stockRepository)
	{
		this.stockRepository = stockRepository;
	}

	public final ShipmentScheduleQtyOnHandStorage ofShipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return new ShipmentScheduleQtyOnHandStorage(ImmutableList.of(shipmentSchedule), stockRepository);
	}

	public final ShipmentScheduleQtyOnHandStorage ofOlAndScheds(@NonNull final List<OlAndSched> lines)
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = lines
				.stream()
				.map(OlAndSched::getSched)
				.collect(ImmutableList.toImmutableList());

		return new ShipmentScheduleQtyOnHandStorage(shipmentSchedules, stockRepository);
	}
}
