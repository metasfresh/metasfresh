package de.metas.handlingunits.inventory;

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.document.DocBaseAndSubType;
import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.util.reducers.Reducers;
import lombok.Builder;
import lombok.Getter;
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class Inventory
{
	@Getter
	private final InventoryId id;
	@Getter
	private final DocBaseAndSubType docBaseAndSubType;

	private final InventoryType inventoryType;
	private final ImmutableList<InventoryLine> lines;

	@Builder
	private Inventory(
			@NonNull final InventoryId id,
			@NonNull final DocBaseAndSubType docBaseAndSubType,
			@NonNull final List<InventoryLine> lines)
	{
		this.id = id;
		this.docBaseAndSubType = docBaseAndSubType;
		this.lines = ImmutableList.copyOf(lines);
		this.inventoryType = extractInventoryType(lines, InventoryType.PHYSICAL);
	}

	private static InventoryType extractInventoryType(
			@NonNull final List<InventoryLine> lines,
			@NonNull final InventoryType defaultInventoryTypeWhenEmpty)
	{
		return lines.stream()
				.map(InventoryLine::getInventoryType)
				.reduce(Reducers.singleValue(values -> new AdempiereException("Mixing Physical inventories with Internal Use inventories is not allowed: " + lines)))
				.orElse(defaultInventoryTypeWhenEmpty);
	}

	public boolean isInternalUseInventory()
	{
		return inventoryType.isInternalUse();
	}

	public ImmutableList<InventoryLine> getLines()
	{
		return lines;
	}

	public InventoryLine getLineById(@NonNull final InventoryLineId inventoryLineId)
	{
		return lines.stream()
				.filter(line -> inventoryLineId.equals(line.getId()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for " + inventoryLineId + " in " + this));
	}

	public ImmutableList<InventoryLineHU> getLineHUs()
	{
		return streamLineHUs().collect(ImmutableList.toImmutableList());
	}

	private Stream<InventoryLineHU> streamLineHUs()
	{
		return lines.stream().flatMap(line -> line.getInventoryLineHUs().stream());
	}

	public ImmutableSet<HuId> getHuIds()
	{
		return InventoryLineHU.extractHuIds(streamLineHUs());
	}
}
