package de.metas.handlingunits.reservation;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.springframework.stereotype.Repository;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.handlingunits.reservation.HuReservation.HuReservationBuilder;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Repository
public class HuReservationRepository
{

	public HuReservation getBySalesOrderLineId(@NonNull final OrderLineId id)
	{
		final List<I_M_HU_Reservation> huReservationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_C_OrderLineSO_ID, id)
				.create()
				.list();

		return ofRecords(huReservationRecords);
	}

	private HuReservation ofRecords(@NonNull final List<I_M_HU_Reservation> huReservationRecords)
	{
		Quantity sum = huReservationRecords.isEmpty()
				? null
				: Quantity.zero(huReservationRecords.get(0).getC_UOM());

		final HuReservationBuilder builder = HuReservation.builder();
		for (final I_M_HU_Reservation huReservationRecord : huReservationRecords)
		{
			final HuId vhuId = HuId.ofRepoId(huReservationRecord.getVHU_ID());
			final Quantity reservedQty = Quantity.of(huReservationRecord.getQtyReserved(), huReservationRecord.getC_UOM());

			builder.vhuId2reservedQty(vhuId, reservedQty);

			sum = sum.add(reservedQty);
		}

		return builder
				.reservedQtySum(Optional.ofNullable(sum))
				.build();
	}

}
