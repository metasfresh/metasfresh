/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.inout;

import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;
import org.adempiere.inout.util.IShipmentScheduleQtyOnHandProvider;
import org.adempiere.inout.util.IShipmentScheduleQtyOnHandStorage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This implementation should provide details about quantities reserved for Order lines.
 * Main driver of this is because products in the service/repair should no longer count against stock.
 * But they still need to be shipped back to the client.
 */
@Service
public class ShipmentScheduleQtyReservedProvider implements IShipmentScheduleQtyOnHandProvider
{
	private final HUReservationRepository huReservationRepository;

	public ShipmentScheduleQtyReservedProvider(final HUReservationRepository huReservationRepository)
	{
		this.huReservationRepository = huReservationRepository;
	}

	@Override
	public IShipmentScheduleQtyOnHandStorage getStorageFor(@NonNull final List<I_M_ShipmentSchedule> lines)
	{
		return new ShipmentScheduleQtyReservedStorage(huReservationRepository);
	}

}
