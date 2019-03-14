package de.metas.handlingunits.reservation;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

import de.metas.cache.CCache;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Reservation;
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
	private final CCache<HuId, Optional<OrderLineId>> salesOrderLinesByVhuId = CCache.newLRUCache(
			I_M_HU_Reservation.Table_Name + "#by#" + I_M_HU_Reservation.COLUMNNAME_VHU_ID, 500, 5);

	public Optional<HUReservation> getBySalesOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		final List<I_M_HU_Reservation> huReservationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_C_OrderLineSO_ID, orderLineId)
				.create()
				.list();

		if (huReservationRecords.isEmpty())
		{
			return Optional.empty();
		}

		final HUReservation huReservation = toHUReservation(orderLineId, huReservationRecords);
		return Optional.of(huReservation);
	}

	private static HUReservation toHUReservation(
			@NonNull final OrderLineId orderLineId,
			@NonNull final List<I_M_HU_Reservation> huReservationRecords)
	{
		final Map<HuId, Quantity> reservedQtyByVhuId = new HashMap<>();
		for (final I_M_HU_Reservation huReservationRecord : huReservationRecords)
		{
			final HuId vhuId = HuId.ofRepoId(huReservationRecord.getVHU_ID());
			final Quantity reservedQty = Quantity.of(huReservationRecord.getQtyReserved(), huReservationRecord.getC_UOM());

			reservedQtyByVhuId.put(vhuId, reservedQty);
		}

		return HUReservation.builder()
				.salesOrderLineId(orderLineId)
				.reservedQtyByVhuIds(reservedQtyByVhuId)
				.build();
	}

	public void save(@NonNull final HUReservation huReservation)
	{
		for (final HuId vhuId : huReservation.getVhuIds())
		{
			final Quantity qtyReserved = huReservation.getReservedQtyByVhuId(vhuId);

			final I_M_HU_Reservation huReservationRecord = createOrLoadRecordFor(vhuId);
			huReservationRecord.setC_OrderLineSO_ID(huReservation.getSalesOrderLineId().getRepoId());
			huReservationRecord.setQtyReserved(qtyReserved.getAsBigDecimal());
			huReservationRecord.setC_UOM_ID(qtyReserved.getUOMId());
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

	public void deleteReservationsByVhuIds(@NonNull final Collection<HuId> vhuIds)
	{
		if (vhuIds.isEmpty())
		{
			return;
		}

		Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuIds)
				.create()
				.delete();
	}

	public void warmup(@NonNull final Collection<HuId> huIds)
	{
		salesOrderLinesByVhuId.getAllOrLoad(huIds, this::retrieveReservedForOrderLineId);
	}

	private Map<HuId, Optional<OrderLineId>> retrieveReservedForOrderLineId(@NonNull final Collection<HuId> huIds)
	{
		final HashMap<HuId, Optional<OrderLineId>> map = new HashMap<>(huIds.size());

		huIds.forEach(huId -> map.put(huId, Optional.empty()));

		final List<I_M_HU_Reservation> huReservationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_Reservation.COLUMN_VHU_ID, huIds)
				.create()
				.list();
		for (final I_M_HU_Reservation huReservationRecord : huReservationRecords)
		{
			final HuId huId = HuId.ofRepoId(huReservationRecord.getVHU_ID());
			final OrderLineId orderLineId = OrderLineId.ofRepoId(huReservationRecord.getC_OrderLineSO_ID());
			map.put(huId, Optional.of(orderLineId));
		}
		return ImmutableMap.copyOf(map);
	}

	public Optional<OrderLineId> getOrderLineIdByReservedVhuId(@NonNull final HuId vhuId)
	{
		return salesOrderLinesByVhuId.getOrLoad(vhuId, this::retrieveOrderLineIdByReservedVhuId);
	}

	private Optional<OrderLineId> retrieveOrderLineIdByReservedVhuId(@NonNull final HuId huId)
	{
		final I_M_HU_Reservation huReservationRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_VHU_ID, huId) // we have a UC constraint on VHU_ID
				.create()
				.firstOnly(I_M_HU_Reservation.class);
		if (huReservationRecord == null)
		{
			return Optional.empty();
		}
		return Optional.of(OrderLineId.ofRepoId(huReservationRecord.getC_OrderLineSO_ID()));
	}

}
