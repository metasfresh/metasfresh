package org.adempiere.inout.util;

import com.google.common.annotations.VisibleForTesting;
import de.metas.inoutcandidate.api.OlAndSchedCollection;
import de.metas.inoutcandidate.qty_reservation.QtyReservationRepository;
import de.metas.material.cockpit.stock.StockRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorageLoader.ShipmentScheduleQtyOnHandStorageLoaderBuilder;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

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

	public final ShipmentScheduleQtyOnHandStorage ofOlAndScheds(@NonNull final OlAndSchedCollection lines)
	{
		return newLoader()
				.segments(lines.getQtyOnHandSegments())
				.build().execute();
	}

	private ShipmentScheduleQtyOnHandStorageLoaderBuilder newLoader()
	{
		return ShipmentScheduleQtyOnHandStorageLoader.builder()
				.stockRepository(stockRepository)
				.qtyReservationRepository(qtyReservationRepository);
	}
}
