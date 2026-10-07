package org.adempiere.inout.util;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Collection;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class ShipmentScheduleAvailableStock
{
	public static ShipmentScheduleAvailableStock of()
	{
		return EMPTY;
	}

	private static final ShipmentScheduleAvailableStock EMPTY = new ShipmentScheduleAvailableStock();

	@NonNull private final ImmutableList<ShipmentScheduleAvailableStockDetail> list;

	@Builder
	private ShipmentScheduleAvailableStock(
			@NonNull final Collection<ShipmentScheduleAvailableStockDetail> stockDetails)
	{
		this.list = ImmutableList.copyOf(stockDetails);
	}

	private ShipmentScheduleAvailableStock()
	{
		this.list = ImmutableList.of();
	}

	public BigDecimal getTotalQtyAvailable(@NonNull final ReservationKey reservationKey)
	{
		return list.stream()
				.map(detail -> detail.getQtyAvailable(reservationKey))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public int size()
	{
		return list.size();
	}

	public BigDecimal getQtyAvailable(final int storageIndex, @NonNull final ReservationKey reservationKey)
	{
		return getStorageDetail(storageIndex).getQtyAvailable(reservationKey);
	}

	public void subtractQtyOnHand(final int storageIndex, @NonNull final BigDecimal qtyOnHandToRemove)
	{
		getStorageDetail(storageIndex).subtractQtyOnHand(qtyOnHandToRemove);
	}

	public ShipmentScheduleAvailableStockDetail getStorageDetail(final int storageIndex)
	{
		return list.get(storageIndex);
	}
}
