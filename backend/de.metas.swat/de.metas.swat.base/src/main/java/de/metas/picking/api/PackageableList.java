/*
 * #%L
 * de.metas.swat.base
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

package de.metas.picking.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public final class PackageableList implements Iterable<Packageable>
{
	public static PackageableList EMPTY = new PackageableList(ImmutableList.of());

	private final ImmutableList<Packageable> list;

	private PackageableList(@NonNull final Collection<Packageable> list)
	{
		this.list = list.stream()
				.sorted(Comparator.comparing(Packageable::getShipmentScheduleId)) // keep them ordered by shipmentScheduleId which is usually the order line ordering
				.collect(ImmutableList.toImmutableList());
	}

	public static PackageableList ofCollection(final Collection<Packageable> list)
	{
		return !list.isEmpty() ? new PackageableList(list) : EMPTY;
	}

	public static PackageableList of(final Packageable... arr)
	{
		return ofCollection(ImmutableList.copyOf(arr));
	}

	public static Collector<Packageable, ?, PackageableList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(PackageableList::ofCollection);
	}

	public boolean isEmpty() {return list.isEmpty();}

	public int size() {return list.size();}

	public Stream<Packageable> stream() {return list.stream();}

	@Override
	public @NonNull Iterator<Packageable> iterator() {return list.iterator();}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return list.stream().map(Packageable::getShipmentScheduleId).sorted().collect(ImmutableSet.toImmutableSet());
	}

	public Optional<BPartnerId> getSingleCustomerId() {return getSingleValue(Packageable::getCustomerId);}

	public Quantity getQtyToPick()
	{
		return list.stream()
				.map(Packageable::getQtyToPick)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("No QtyToPick found in " + list));
	}

	public <T> Optional<T> getSingleValue(@NonNull final Function<Packageable, T> mapper)
	{
		if (list.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableList<T> values = list.stream()
				.map(mapper)
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		if (values.isEmpty())
		{
			return Optional.empty();
		}
		else if (values.size() == 1)
		{
			return Optional.of(values.get(0));
		}
		else
		{
			//throw new AdempiereException("More than one value were extracted (" + values + ") from " + list);
			return Optional.empty();
		}
	}

	public Stream<PackageableList> groupBy(Function<Packageable, ?> classifier)
	{
		return list.stream()
				.collect(Collectors.groupingBy(classifier, LinkedHashMap::new, PackageableList.collect()))
				.values()
				.stream();
	}
}
