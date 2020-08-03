package de.metas.picking.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

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

@ToString(of = "partsMap")
public final class PackingItemParts implements Iterable<PackingItemPart>
{
	public static PackingItemParts newInstance()
	{
		return new PackingItemParts();
	}

	public static PackingItemParts of(@NonNull final PackingItemPart part)
	{
		return new PackingItemParts(ImmutableList.of(part));
	}

	public static Collector<PackingItemPart, ?, PackingItemParts> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(PackingItemParts::new);
	}

	private final LinkedHashMap<PackingItemPartId, PackingItemPart> partsMap;

	private PackingItemParts()
	{
		partsMap = new LinkedHashMap<>();
	}

	private PackingItemParts(@NonNull final PackingItemParts from)
	{
		partsMap = new LinkedHashMap<>(from.partsMap);
	}

	private PackingItemParts(@NonNull final List<PackingItemPart> partsList)
	{
		this.partsMap = partsList.stream()
				.map(part -> GuavaCollectors.entry(part.getId(), part))
				.collect(GuavaCollectors.toMap(LinkedHashMap::new));
	}

	public PackingItemParts copy()
	{
		return new PackingItemParts(this);
	}

	public boolean isEmpty()
	{
		return partsMap.isEmpty();
	}

	public Object size()
	{
		return partsMap.size();
	}

	public <T> Stream<T> map(@NonNull final Function<PackingItemPart, T> mapper)
	{
		return partsMap
				.values()
				.stream()
				.map(mapper);
	}

	public <T> Optional<T> mapReduce(@NonNull final Function<PackingItemPart, T> mapper)
	{
		final ImmutableSet<T> result = map(mapper)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		if (result.isEmpty())
		{
			return Optional.empty();
		}
		else if (result.size() == 1)
		{
			final T singleResult = result.iterator().next();
			return Optional.of(singleResult);
		}
		else
		{
			throw new AdempiereException("Got more than one result: " + result);
		}
	}

	public void setFrom(final PackingItemParts from)
	{
		partsMap.clear();
		partsMap.putAll(from.partsMap);
	}

	public Optional<Quantity> getQtySum()
	{
		return map(PackingItemPart::getQty)
				.reduce(Quantity::add);
	}

	public List<PackingItemPart> toList()
	{
		return ImmutableList.copyOf(partsMap.values());
	}

	public I_C_UOM getCommonUOM()
	{
		//validate that all qtys have the same UOM
		mapReduce(part -> part.getQty().getUomId())
				.orElseThrow(()-> new AdempiereException("Missing I_C_UOM!")
						.appendParametersToMessage()
						.setParameter("Parts", this));

		return toList().get(0).getQty().getUOM();
	}

	@Override
	public Iterator<PackingItemPart> iterator()
	{
		return toList().iterator();
	}

	public void clear()
	{
		partsMap.clear();
	}

	public void addQtys(final PackingItemParts partsToAdd)
	{
		partsToAdd.toList()
				.forEach(this::addQty);
	}

	private void addQty(final PackingItemPart part)
	{
		partsMap.compute(part.getId(),
				(id, existingPart) -> existingPart != null ? existingPart.addQty(part.getQty()) : part);
	}

	public void removePart(final PackingItemPart part)
	{
		partsMap.remove(part.getId(), part);
	}

	public void updatePart(@NonNull final PackingItemPart part)
	{
		partsMap.put(part.getId(), part);
	}
}
