package de.metas.inoutcandidate.picking_bom;

import java.util.Collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;

import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
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
public final class PickingBOMsReversedIndex
{
	public static PickingBOMsReversedIndex EMPTY = new PickingBOMsReversedIndex();

	public static PickingBOMsReversedIndex ofBOMProductIdsByComponentId(final SetMultimap<ProductId, ProductId> bomProductIdsByComponentId)
	{
		return !bomProductIdsByComponentId.isEmpty()
				? new PickingBOMsReversedIndex(bomProductIdsByComponentId)
				: EMPTY;
	}

	private final ImmutableSetMultimap<ProductId, ProductId> bomProductIdsByComponentId;

	private PickingBOMsReversedIndex()
	{
		bomProductIdsByComponentId = ImmutableSetMultimap.of();
	}

	private PickingBOMsReversedIndex(final SetMultimap<ProductId, ProductId> bomProductIdsByComponentId)
	{
		this.bomProductIdsByComponentId = ImmutableSetMultimap.copyOf(bomProductIdsByComponentId);
	}

	public ImmutableSet<ProductId> getBOMProductIdsByComponentId(@NonNull final ProductId componentId)
	{
		return bomProductIdsByComponentId.get(componentId);
	}

	public ImmutableSet<ProductId> getBOMProductIdsByComponentIds(@NonNull final Collection<ProductId> componentIds)
	{
		if (!componentIds.isEmpty())
		{
			return componentIds.stream()
					.flatMap(componentId -> getBOMProductIdsByComponentId(componentId).stream())
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			return ImmutableSet.of();
		}
	}

}
