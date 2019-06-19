package de.metas.handlingunits.inventory;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
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
public final class InventoryLines implements Iterable<InventoryLine>
{
	public static InventoryLines ofList(@NonNull final List<InventoryLine> list)
	{
		return list.isEmpty() ? EMPTY : new InventoryLines(list);
	}

	private static final InventoryLines EMPTY = new InventoryLines();

	private final ImmutableList<InventoryLine> lines;

	private InventoryLines(@NonNull final List<InventoryLine> lines)
	{
		this.lines = ImmutableList.copyOf(lines);
	}

	private InventoryLines()
	{
		this.lines = ImmutableList.of();
	}

	public ImmutableList<InventoryLine> toList()
	{
		return lines;
	}

	@Override
	public Iterator<InventoryLine> iterator()
	{
		return lines.iterator();
	}

	public ImmutableSet<HuId> getHuIds()
	{
		return lines
				.stream()
				.flatMap(inventoryLine -> inventoryLine.getHUIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}
}
