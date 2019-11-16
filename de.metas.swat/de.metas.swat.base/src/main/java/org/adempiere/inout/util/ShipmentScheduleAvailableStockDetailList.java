package org.adempiere.inout.util;

import java.math.BigDecimal;
import java.util.Collection;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;
import lombok.ToString;

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
public class ShipmentScheduleAvailableStockDetailList
{
	public static ShipmentScheduleAvailableStockDetailList of(@NonNull final Collection<ShipmentScheduleAvailableStockDetail> list)
	{
		return !list.isEmpty()
				? new ShipmentScheduleAvailableStockDetailList(list)
				: EMPTY;
	}

	public static ShipmentScheduleAvailableStockDetailList of()
	{
		return EMPTY;
	}

	private static final ShipmentScheduleAvailableStockDetailList EMPTY = new ShipmentScheduleAvailableStockDetailList(ImmutableList.of());

	private final ImmutableList<ShipmentScheduleAvailableStockDetail> list;

	private ShipmentScheduleAvailableStockDetailList(@NonNull final Collection<ShipmentScheduleAvailableStockDetail> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public BigDecimal getTotalQtyOnHand()
	{
		return list.stream()
				.map(ShipmentScheduleAvailableStockDetail::getQtyOnHand)
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

	public BigDecimal getQtyOnHand(final int storageIndex)
	{
		return getStorageDetail(storageIndex).getQtyOnHand();
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
