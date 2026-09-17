package org.adempiere.inout.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.qty_reservation.QtyReservationRepository;
import de.metas.material.cockpit.stock.StockRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorageLoader.ShipmentScheduleQtyOnHandStorageLoaderBuilder;
import org.compiere.Adempiere;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

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
@RequiredArgsConstructor
public class ShipmentScheduleQtyOnHandStorageFactory
{
	@NonNull private final StockRepository stockRepository;
	@NonNull private final QtyReservationRepository qtyReservationRepository;

	@VisibleForTesting
	public static ShipmentScheduleQtyOnHandStorageFactory newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ShipmentScheduleQtyOnHandStorageFactory(
				new StockRepository(),
				new QtyReservationRepository()
		);
	}

	public final ShipmentScheduleQtyOnHandStorage ofShipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return newLoader()
				.shipmentSchedules(ImmutableList.of(shipmentSchedule))
				.build().execute();
	}

	public final ShipmentScheduleQtyOnHandStorage ofOlAndScheds(@NonNull final List<OlAndSched> lines)
	{
		return newLoader()
				.shipmentSchedules(extractShipmentSchedules(lines))
				.build().execute();
	}

	private ShipmentScheduleQtyOnHandStorageLoaderBuilder newLoader()
	{
		return ShipmentScheduleQtyOnHandStorageLoader.builder()
				.stockRepository(stockRepository)
				.qtyReservationRepository(qtyReservationRepository);
	}

	private static List<I_M_ShipmentSchedule> extractShipmentSchedules(final @NotNull List<OlAndSched> lines)
	{
		return lines.stream().map(OlAndSched::getSched).collect(ImmutableList.toImmutableList());
	}
}
