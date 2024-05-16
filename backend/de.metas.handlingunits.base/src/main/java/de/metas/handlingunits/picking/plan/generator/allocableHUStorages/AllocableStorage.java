/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.picking.plan.generator.allocableHUStorages;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.plan.generator.AllocablePackageable;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ToString
public class AllocableStorage
{
	private final ImmutableList<VHUAllocableStorage> vhuStorages;

	AllocableStorage(@NonNull final List<VHUAllocableStorage> vhuStorages)
	{
		this.vhuStorages = ImmutableList.copyOf(vhuStorages);
	}

	public Quantity allocate(@NonNull final AllocablePackageable allocable)
	{
		Quantity qtyAllocatedTotal = null;

		for (final VHUAllocableStorage vhuStorage : getStoragesOrderedFor(allocable))
		{
			if (allocable.isAllocated())
			{
				break;
			}

			final Quantity qtyAllocated = vhuStorage.allocate(allocable);
			qtyAllocatedTotal = Quantity.addNullables(qtyAllocatedTotal, qtyAllocated);
		}

		return qtyAllocatedTotal != null ? qtyAllocatedTotal : zero(allocable);
	}

	private static Quantity zero(final AllocablePackageable allocable) {return allocable.getQtyToAllocate().toZero();}

	public void forceAllocate(@NonNull final AllocablePackageable allocable, @NonNull final Quantity qtyToAllocate)
	{
		if (qtyToAllocate.isZero())
		{
			return;
		}

		final ImmutableList<VHUAllocableStorage> vhuStoragesSorted = getStoragesOrderedFor(allocable);

		Quantity qtyToAllocateRemaining = qtyToAllocate;
		for (final VHUAllocableStorage vhuStorage : vhuStoragesSorted)
		{
			if (qtyToAllocateRemaining.isZero())
			{
				break;
			}

			final Quantity qtyAllocated = vhuStorage.allocate(allocable, qtyToAllocateRemaining);
			qtyToAllocateRemaining = qtyToAllocateRemaining.subtract(qtyAllocated);
		}

		if (qtyToAllocateRemaining.signum() != 0)
		{
			final VHUAllocableStorage lastVHUStorage = vhuStorages.get(vhuStorages.size() - 1);
			lastVHUStorage.forceAllocate(allocable, qtyToAllocateRemaining);
		}
	}

	private ImmutableList<VHUAllocableStorage> getStoragesOrderedFor(final @NonNull AllocablePackageable allocable)
	{
		return vhuStorages.stream()
				.sorted(Comparator.
						<VHUAllocableStorage>comparingInt(vhuStorage -> vhuStorage.isReservedOnlyFor(allocable) ? 0 : 1) // consider reserved VHU first
						.thenComparingInt(VHUAllocableStorage::getSeqNo)) // then by seqno just to have a predictable order
				.collect(ImmutableList.toImmutableList());
	}

	public boolean hasReservedQtyFor(@NonNull final AllocablePackageable allocable)
	{
		return vhuStorages.stream().anyMatch(vhuStorage -> vhuStorage.isReservedOnlyFor(allocable));
	}

	public boolean hasQtyFreeToAllocate()
	{
		return vhuStorages.stream()
				.anyMatch(vhuStorage -> vhuStorage.hasQtyFreeToAllocateFor(null));
	}

	public Optional<Quantity> getQtyFreeToAllocate()
	{
		return vhuStorages.stream()
				.map(vhuStorage -> vhuStorage.getQtyFreeToAllocateFor(null))
				.reduce(Quantity::add)
				.filter(Quantity::isPositive);
	}
}