package de.metas.handlingunits.reservation;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.handlingunits.reservation.HUReservation.HUReservationBuilder;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
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
public class HUReservationRepository
{

	public HUReservation getBySalesOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		final List<I_M_HU_Reservation> huReservationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_C_OrderLineSO_ID, orderLineId)
				.create()
				.list();

		return ofRecords(huReservationRecords).salesOrderLineId(orderLineId).build();
	}

	private HUReservationBuilder ofRecords(@NonNull final List<I_M_HU_Reservation> huReservationRecords)
	{
		Quantity sum = huReservationRecords.isEmpty()
				? null
				: Quantity.zero(huReservationRecords.get(0).getC_UOM());

		final HUReservationBuilder builder = HUReservation.builder();

		for (final I_M_HU_Reservation huReservationRecord : huReservationRecords)
		{
			final HuId vhuId = HuId.ofRepoId(huReservationRecord.getVHU_ID());
			final Quantity reservedQty = Quantity.of(huReservationRecord.getQtyReserved(), huReservationRecord.getC_UOM());

			builder.vhuId2reservedQty(vhuId, reservedQty);

			sum = sum.add(reservedQty);
		}

		return builder.reservedQtySum(Optional.ofNullable(sum));
	}

	public void save(@NonNull final HUReservation huReservation)
	{
		final Map<HuId, Quantity> vhuId2reservedQtys = huReservation.getVhuId2reservedQtys();
		final Set<Entry<HuId, Quantity>> entrySet = vhuId2reservedQtys.entrySet();
		for (final Entry<HuId, Quantity> entry : entrySet)
		{
			final I_M_HU_Reservation huReservationRecord = createOrLoadRecordFor(entry.getKey());
			huReservationRecord.setC_OrderLineSO_ID(huReservation.getSalesOrderLineId().getRepoId());

			huReservationRecord.setQtyReserved(entry.getValue().getAsBigDecimal());
			huReservationRecord.setC_UOM_ID(entry.getValue().getUOMId());

			saveRecord(huReservationRecord);
		}
	}

	private I_M_HU_Reservation createOrLoadRecordFor(@NonNull final HuId vhuId)
	{
		final I_M_HU_Reservation existingHuReservationRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuId)
				.create()
				.firstOnly(I_M_HU_Reservation.class);
		if (existingHuReservationRecord != null)
		{
			return existingHuReservationRecord;
		}

		final I_M_HU_Reservation newHuReservationRecord = newInstance(I_M_HU_Reservation.class);
		newHuReservationRecord.setVHU_ID(vhuId.getRepoId());
		return newHuReservationRecord;
	}

	public IQuery<I_M_HU_Reservation> createQueryReservedToOtherThan(@NonNull final OrderLineId orderLineId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_M_HU_Reservation.COLUMNNAME_C_OrderLineSO_ID, orderLineId)
				.create();
	}
}
